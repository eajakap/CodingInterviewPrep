package drills.barclays;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

// ---------------------------------------------------------------- demo

public class TradeEventStoreApp {
    static LocalTime t(String s) { return LocalTime.parse(s); }
    static BigDecimal px(String s) { return new BigDecimal(s); }

    public static void main(String[] args) {
        List<TradeEvent> events = List.of(
            new NewEvent(1, "T001", "VOD.L", Side.BUY, 1000, px("108.50"), "GOLDMAN", t("09:00:01")),
            new NewEvent(2, "T002", "AAPL", Side.SELL, 500, px("172.30"), "JPMORGAN", t("09:00:05")),
            new NewEvent(3, "T003", "BARC.L", Side.BUY, 2000, px("185.00"), "CITI", t("09:01:00")),
            new AmendEvent(4, "T001", 1200L, null, null, t("09:02:00")),
            new AmendEvent(5, "T001", null, px("109.00"), null, t("09:03:00")),
            new CancelEvent(6, "T002", "Client request", t("09:04:00")),
            new AmendEvent(7, "T002", 600L, null, null, t("09:05:00")),
            new NewEvent(8, "T004", "MSFT", Side.BUY, 300, px("420.00"), "BARCLAYS", t("09:06:00")),
            new CancelEvent(9, "T999", "Error", t("09:07:00")),
            new AmendEvent(10, "T003", 2500L, px("184.50"), "DEUTSCHE", t("09:08:00")),
            new CancelEvent(11, "T003", "Duplicate", t("09:09:00")),
            new CancelEvent(12, "T003", "Re-cancel", t("09:10:00")));

        EventStore store = new EventStore();
        events.forEach(store::append);

        StateRebuilder rebuilder = new StateRebuilder(store);
        Reports reports = new Reports(store, rebuilder);
        reports.processingResults();
        reports.currentState();
        System.out.println("\n### 3-4. Time Travel");
        reports.timeTravel("T001", "09:01:30");
        reports.timeTravel("T001", "09:02:30");
        System.out.println("\n### 5. Audit Trail for T003");
        reports.auditTrail("T003");
        reports.summary();
    }
}

/*
 * Trade Event Store — event-sourced trade lifecycle with validation, time travel and audit trail.
 *
 * Run (Java 21+):  java TradeEventStoreApp.java
 *
 * Design
 *   TradeEvent (sealed)  -> NewEvent | AmendEvent | CancelEvent   immutable records
 *   TradeState           immutable snapshot; apply(event) returns a NEW state
 *   FieldChange          one diff entry (field, old, new)
 *   EventStore           append-only journal + per-trade stream of accepted events
 *   TradeLifecycle       the state machine: validates an event against the current state
 *   StateRebuilder       folds events into state (current or as-of a timestamp)
 *   Reports              formatting only (processing log, state table, audit, stats)
 */

// ---------------------------------------------------------------- events

enum EventType { NEW, AMEND, CANCEL }

enum Side { BUY, SELL }

enum TradeStatus { ACTIVE, CANCELLED }

sealed interface TradeEvent permits NewEvent, AmendEvent, CancelEvent {
    long eventId();
    String tradeId();
    LocalTime timestamp();
    EventType type();
}

record NewEvent(long eventId, String tradeId, String instrument, Side side, long quantity,
                BigDecimal price, String counterparty, LocalTime timestamp) implements TradeEvent {
    public NewEvent {
        Objects.requireNonNull(tradeId); Objects.requireNonNull(instrument); Objects.requireNonNull(side);
        Objects.requireNonNull(price); Objects.requireNonNull(counterparty); Objects.requireNonNull(timestamp);
        if (quantity <= 0) throw new IllegalArgumentException("quantity must be > 0");
    }
    public EventType type() { return EventType.NEW; }
}

/** Partial update: null means "field not amended". */
record AmendEvent(long eventId, String tradeId, Long quantity, BigDecimal price, String counterparty,
                  LocalTime timestamp) implements TradeEvent {
    public AmendEvent {
        Objects.requireNonNull(tradeId); Objects.requireNonNull(timestamp);
        if (quantity == null && price == null && counterparty == null)
            throw new IllegalArgumentException("AMEND must change at least one field");
    }
    public EventType type() { return EventType.AMEND; }
}

record CancelEvent(long eventId, String tradeId, String reason, LocalTime timestamp) implements TradeEvent {
    public CancelEvent { Objects.requireNonNull(tradeId); Objects.requireNonNull(timestamp); }
    public EventType type() { return EventType.CANCEL; }
}

// ---------------------------------------------------------------- state

record FieldChange(String field, String oldValue, String newValue) {
    String format(String arrow) { return field + ": " + oldValue + arrow + newValue; }
}

record TradeState(String tradeId, TradeStatus status, String instrument, Side side, long quantity,
                  BigDecimal price, String counterparty, int version) {

    static TradeState from(NewEvent e) {
        return new TradeState(e.tradeId(), TradeStatus.ACTIVE, e.instrument(), e.side(), e.quantity(),
                              e.price(), e.counterparty(), 1);
    }

    TradeState apply(TradeEvent e) {
        return switch (e) {
            case NewEvent n -> throw new IllegalStateException("NEW cannot be applied to an existing trade");
            case AmendEvent a -> new TradeState(tradeId, status, instrument, side,
                    a.quantity() != null ? a.quantity() : quantity,
                    a.price() != null ? a.price() : price,
                    a.counterparty() != null ? a.counterparty() : counterparty,
                    version + 1);
            case CancelEvent c -> new TradeState(tradeId, TradeStatus.CANCELLED, instrument, side, quantity,
                    price, counterparty, version + 1);
        };
    }

    /** Field-level diff between this (old) and next. Only fields that actually changed. */
    List<FieldChange> diff(TradeState next) {
        List<FieldChange> changes = new ArrayList<>();
        if (quantity != next.quantity)
            changes.add(new FieldChange("quantity", String.valueOf(quantity), String.valueOf(next.quantity)));
        if (price.compareTo(next.price) != 0)
            changes.add(new FieldChange("price", Fmt.price(price), Fmt.price(next.price)));
        if (!counterparty.equals(next.counterparty))
            changes.add(new FieldChange("counterparty", counterparty, next.counterparty));
        if (status != next.status)
            changes.add(new FieldChange("Status", status.name(), next.status.name()));
        return changes;
    }
}

// ---------------------------------------------------------------- lifecycle rules

/** The state machine. Returns an error message, or empty if the event is valid. */
final class TradeLifecycle {
    private TradeLifecycle() {}

    static Optional<String> validate(TradeEvent e, TradeState current) {
        return switch (e) {
            case NewEvent n -> current != null
                    ? Optional.of("Cannot create " + n.tradeId() + ": trade already exists")
                    : Optional.empty();
            case AmendEvent a -> {
                if (current == null) yield Optional.of("Cannot amend " + a.tradeId() + ": trade does not exist");
                if (current.status() == TradeStatus.CANCELLED)
                    yield Optional.of("Cannot amend " + a.tradeId() + ": trade is CANCELLED");
                yield Optional.empty();
            }
            case CancelEvent c -> {
                if (current == null) yield Optional.of("Cannot cancel " + c.tradeId() + ": trade does not exist");
                if (current.status() == TradeStatus.CANCELLED)
                    yield Optional.of("Cannot cancel " + c.tradeId() + ": trade is already CANCELLED");
                yield Optional.empty();
            }
        };
    }
}

// ---------------------------------------------------------------- store

/** Outcome of submitting one event. Kept in the journal forever (valid or not). */
record ProcessingResult(TradeEvent event, boolean accepted, String message) {}

final class EventStore {
    private final List<ProcessingResult> journal = new ArrayList<>();             // every event, in arrival order
    private final Map<String, List<TradeEvent>> streams = new LinkedHashMap<>();  // accepted events per trade
    private final Map<String, TradeState> current = new HashMap<>();              // projection cache

    /** Validate and append. Never throws for business-rule violations: they are recorded as rejected. */
    ProcessingResult append(TradeEvent e) {
        TradeState before = current.get(e.tradeId());
        Optional<String> error = TradeLifecycle.validate(e, before);
        ProcessingResult result;
        if (error.isPresent()) {
            result = new ProcessingResult(e, false, error.get());
        } else {
            TradeState after = (e instanceof NewEvent n) ? TradeState.from(n) : before.apply(e);
            streams.computeIfAbsent(e.tradeId(), k -> new ArrayList<>()).add(e);
            current.put(e.tradeId(), after);
            result = new ProcessingResult(e, true, describe(e, before, after));
        }
        journal.add(result);
        return result;
    }

    private static String describe(TradeEvent e, TradeState before, TradeState after) {
        return switch (e) {
            case NewEvent n -> n.tradeId() + " created (NEW)";
            case AmendEvent a -> a.tradeId() + " amended (" + before.diff(after).stream()
                    .map(c -> c.format(" -> ")).collect(Collectors.joining(", ")) + ")";
            case CancelEvent c -> c.tradeId() + " cancelled (reason: " + c.reason() + ")";
        };
    }

    // Read side: all views are unmodifiable — the store cannot be mutated from outside.
    List<ProcessingResult> journal() { return Collections.unmodifiableList(journal); }
    List<TradeEvent> eventsFor(String tradeId) { return List.copyOf(streams.getOrDefault(tradeId, List.of())); }
    Set<String> tradeIds() { return Collections.unmodifiableSet(streams.keySet()); }
}

// ---------------------------------------------------------------- rebuild / time travel / audit

record AuditEntry(int version, LocalTime timestamp, EventType type, String changes) {}

final class StateRebuilder {
    private final EventStore store;
    StateRebuilder(EventStore store) { this.store = store; }

    Optional<TradeState> currentState(String tradeId) {
        return replay(store.eventsFor(tradeId));
    }

    /** State as of a time: replay only events with timestamp <= asOf. */
    Optional<TradeState> stateAt(String tradeId, LocalTime asOf) {
        return replay(eventsUpTo(tradeId, asOf));
    }

    List<TradeEvent> eventsUpTo(String tradeId, LocalTime asOf) {
        return store.eventsFor(tradeId).stream().filter(e -> !e.timestamp().isAfter(asOf)).toList();
    }

    private static Optional<TradeState> replay(List<TradeEvent> events) {
        TradeState state = null;
        for (TradeEvent e : events) state = (state == null) ? TradeState.from((NewEvent) e) : state.apply(e);
        return Optional.ofNullable(state);
    }

    List<AuditEntry> auditTrail(String tradeId) {
        List<AuditEntry> trail = new ArrayList<>();
        TradeState state = null;
        for (TradeEvent e : store.eventsFor(tradeId)) {
            TradeState next = (state == null) ? TradeState.from((NewEvent) e) : state.apply(e);
            String changes = switch (e) {
                case NewEvent n -> "Created: %s %s %d @ %s, cpty=%s".formatted(
                        n.instrument(), n.side(), n.quantity(), Fmt.price(n.price()), n.counterparty());
                case AmendEvent a -> state.diff(next).stream().map(c -> c.format("->"))
                        .collect(Collectors.joining(", "));
                case CancelEvent c -> state.diff(next).stream().map(ch -> ch.format("->"))
                        .collect(Collectors.joining(", ")) + ", reason: " + c.reason();
            };
            trail.add(new AuditEntry(next.version(), e.timestamp(), e.type(), changes));
            state = next;
        }
        return trail;
    }
}

// ---------------------------------------------------------------- formatting

final class Fmt {
    private Fmt() {}
    private static final java.time.format.DateTimeFormatter HMS =
            java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss");
    static String time(LocalTime t) { return t.format(HMS); }
    static String price(BigDecimal p) { return p.setScale(2, java.math.RoundingMode.HALF_UP).toPlainString(); }

    static String stateRow(TradeState s) {
        return "%-8s| %-10s| %-11s| %-5s| %-5s| %-7s| %-13s| %d".formatted(s.tradeId(), s.status(),
                s.instrument(), s.side(), s.quantity(), price(s.price()), s.counterparty(), s.version());
    }

    static String compact(TradeState s) {
        return "%s | %s | %s | %s | %d | %s | %s | Version %d".formatted(s.tradeId(), s.status(), s.instrument(),
                s.side(), s.quantity(), price(s.price()), s.counterparty(), s.version());
    }
}

final class Reports {
    private final EventStore store;
    private final StateRebuilder rebuilder;
    Reports(EventStore store, StateRebuilder rebuilder) { this.store = store; this.rebuilder = rebuilder; }

    void processingResults() {
        System.out.println("### 1. Event Processing Results");
        for (ProcessingResult r : store.journal())
            System.out.printf("Event %-4s%-6s- %s%n", r.event().eventId() + ":", r.accepted() ? "OK" : "ERROR",
                    r.message());
    }

    void currentState() {
        System.out.println("\n### 2. Current State (after all events)");
        System.out.println("TradeId | Status    | Instrument | Side | Qty  | Price  | Counterparty | Version");
        System.out.println("--------|-----------|------------|------|------|--------|--------------|--------");
        store.tradeIds().stream().sorted()
                .forEach(id -> rebuilder.currentState(id).ifPresent(s -> System.out.println(Fmt.stateRow(s))));
    }

    void timeTravel(String tradeId, String hhmmss) {
        LocalTime t = LocalTime.parse(hhmmss);
        System.out.printf("%nQuery: getStateAt(\"%s\", \"%s\")%n", tradeId, hhmmss);
        System.out.println("Events up to " + hhmmss + ":");
        TradeState state = null;
        for (TradeEvent e : rebuilder.eventsUpTo(tradeId, t)) {
            TradeState next = state == null ? TradeState.from((NewEvent) e) : state.apply(e);
            String detail = switch (e) {
                case NewEvent n -> "NEW -> %s created with qty=%d, price=%s"
                        .formatted(n.tradeId(), n.quantity(), Fmt.price(n.price()));
                case AmendEvent a -> "AMEND " + state.diff(next).stream()
                        .map(c -> c.field() + " -> " + c.newValue()).collect(Collectors.joining(", "));
                case CancelEvent c -> "CANCEL (reason: " + c.reason() + ")";
            };
            System.out.printf("  Event %d (%s): %s%n", e.eventId(), Fmt.time(e.timestamp()), detail);
            state = next;
        }
        System.out.println("Result:");
        System.out.println("  " + rebuilder.stateAt(tradeId, t).map(Fmt::compact).orElse(tradeId + " did not exist yet"));
    }

    void auditTrail(String tradeId) {
        System.out.printf("%nQuery: getAuditTrail(\"%s\")%n", tradeId);
        System.out.println("Version | Timestamp | Event   | Changes");
        System.out.println("--------|-----------|---------|------------------------------------------");
        for (AuditEntry a : rebuilder.auditTrail(tradeId))
            System.out.printf("%-8s| %-10s| %-8s| %s%n", a.version(), Fmt.time(a.timestamp()), a.type(), a.changes());
    }

    void summary() {
        List<ProcessingResult> j = store.journal();
        long valid = j.stream().filter(ProcessingResult::accepted).count();
        Map<TradeStatus, Long> byStatus = store.tradeIds().stream()
                .map(rebuilder::currentState).flatMap(Optional::stream)
                .collect(Collectors.groupingBy(TradeState::status, () -> new EnumMap<>(TradeStatus.class),
                        Collectors.counting()));
        Map.Entry<String, Long> mostAmended = store.tradeIds().stream()
                .collect(Collectors.toMap(Function.identity(),
                        id -> store.eventsFor(id).stream().filter(e -> e instanceof AmendEvent).count()))
                .entrySet().stream()
                .max(Map.Entry.<String, Long>comparingByValue()
                        .thenComparing(Map.Entry.<String, Long>comparingByKey().reversed()))  // tie -> lowest id
                .orElseThrow();

        System.out.println("\n### 6. Summary Statistics");
        System.out.printf("Total events received:     %d%n", j.size());
        System.out.printf("Valid events processed:     %d%n", valid);
        System.out.printf("Invalid events rejected:    %d%n", j.size() - valid);
        System.out.println("Trades by status: " + byStatus.entrySet().stream()
                .map(e -> e.getKey() + "=" + e.getValue()).collect(Collectors.joining(", ")));
        System.out.printf("Most amended trade: %s (%d amendments)%n", mostAmended.getKey(), mostAmended.getValue());
    }

}

