# Answer key

Spoilers for all eight drills. Work the drill first.

---

## 0. About the `drills.citi.Device.java` you were given

Worth saying before anything else: **the file as you pasted it passes.** I reconstructed
the three commented-out JUnit tests as plain assertions and ran them against
`drills.citi.DeviceLifecycleManager` unchanged — all twelve assertions pass, on JDK 21. So if you
sat there hunting for the defect and could not find it, that may be because the defect
is not in the code you were looking at.

Two things could have been going on.

**a) The visible tests were not the graded tests.** These platforms usually run a hidden
suite. The one real latent defect in `updateDeviceStatus` is here:

```java
Set<String> allowed = VALID_TRANSITIONS.get(currentStatus);
if (!allowed.contains(newStatus)) {     // NPE if currentStatus isn't a key
```

`newStatus` is validated against `VALID_STATUSES`; `currentStatus` never is. Every one
of the five statuses happens to be a key in `VALID_TRANSITIONS`, so the visible tests
never reach it — but a device constructed with any other status string (`"PENDING"`,
`"decommissioned"`, `"active"` in the wrong case, a status read back from a database
written by an older version of the service) throws `NullPointerException` where the
contract says the method returns `false`. That is exactly the kind of thing a hidden
test checks, and exactly the kind of asymmetry worth flagging out loud: *this input is
validated, that one isn't.*

The related smell is `SUPPORTED_DEVICE_TYPES` — declared, populated, never read. Unused
state in a problem this small is usually a deleted requirement, and it hints the graded
version also expected the device's `deviceType` to be checked.

A defensive version of the method:

```java
import drills.citi.Device;

public boolean updateDeviceStatus(String deviceId, String newStatus) {
    Device device = devices.get(deviceId);
    if (device == null || newStatus == null) {
        return false;
    }
    if (!VALID_STATUSES.contains(newStatus)) {
        return false;
    }
    Set<String> allowed = VALID_TRANSITIONS.getOrDefault(device.status, Collections.emptySet());
    if (!allowed.contains(newStatus)) {   // covers same-status: no status maps to itself
        return false;
    }
    device.status = newStatus;
    return true;
}
```

Note that the explicit same-status check becomes redundant once you trust the transition
table, since no status lists itself as a legal successor. Collapsing two checks into one
table lookup is a good thing to point out in an interview; leaving both in is also
defensible if you argue it documents intent.

**b) It did not compile.** As given, the file has `public class drills.citi.Device` at top level and
a commented-out `public class Solution`. Uncomment the tests and you have two public
top-level classes in one file, which is a compile error under any filename. On the
platform the file was almost certainly `Solution.java`, which makes `public class drills.citi.Device`
the error. "Failing test" and "does not compile" look identical in those web runners.

**The transferable lesson.** When a test suite is red and the code looks right, the next
move is not to reread the code harder — it is to *find out what red actually means*.
Run it. Read the runner's output, not the assertion you expect to fail. Check whether it
compiled. Ask the interviewer to show you the failure. Saying "these three tests pass on
my machine — can you show me the failing output?" is a strong answer, not a dodge. The
worst outcome is twenty minutes of silent staring, which is what the exercise is
designed to provoke.

---

## Drill 01 — `FleetRegistry`: object identity

**Symptom.** `resolve` returns `null` for a key that is obviously equal. Installing
twice into "the same" slot leaves two entries. `HashSet.contains` says no.

**Root cause.**

```java
public boolean equals(SlotKey other) { ... }   // overload, not override
```

`HashMap` calls `equals(Object)`. That method was never overridden, so it is
`Object`'s — reference identity. The `SlotKey.equals(SlotKey)` you wrote is only ever
reached when the compiler can see a `SlotKey` on both sides, which happens in your own
code and never inside the collections framework.

The nasty part is that `hashCode` **is** correctly overridden. Both keys hash to the
same bucket; the map walks that bucket, calls `Object.equals` on the entry, gets false,
and reports a miss. So the symptom is "the hashing is clearly working and the lookup
still fails", which sends people looking at hash distribution instead of at `equals`.

**Fix.**

```java
@Override
public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof SlotKey)) return false;
    SlotKey other = (SlotKey) o;
    return slotNumber == other.slotNumber && Objects.equals(location, other.location);
}
```

**Lesson.** `@Override` on `equals` and `hashCode` is not decoration — it is the only
thing that makes the compiler check you actually overrode something. The same family:
`hashcode()` with a lowercase c, `compareTo(Object)` vs `compareTo(T)`, `toString(int)`.
Modern answer: make the key a `record`, which generates both correctly and can't be
half-overridden.

**Diagnostic that localises it in ten seconds.** `k1.equals(k2)` prints `true` (statically
bound to your overload) while `map.get(k2)` misses. The moment "it's equal when I ask it
directly but not when the map asks it" appears, you are looking at an overload.

**Say out loud:** "equals with a narrower parameter type is an overload, not an override
— let me put `@Override` on it and see if it still compiles."

---

## Drill 02 — `FailureBudget`: boxed identity

**Symptom.** SENSOR (budget 3) escalates correctly. CAMERA (128) and GATEWAY (2000)
never escalate at all.

**Root cause.**

```java
private static boolean atBudget(Integer count, Integer budget) {
    return count == budget;      // reference comparison
}
```

Both parameters are `Integer`, so `==` compares references. `Integer.valueOf` caches
boxes for −128..127, so for small budgets the two references happen to be the same
object and the comparison is accidentally right. At 128 the cache ends, every box is a
fresh object, and the comparison is false forever after.

The 127/128 boundary is the whole tell. Any predicate that works for small numbers and
fails for large ones is boxed identity — there is essentially no other cause.

**Fix.** Change the helper's parameters to `int`:

```java
private static boolean atBudget(int count, int budget) {
    return count == budget;
}
```

The call site now unboxes and `==` means what it looks like. `count.equals(budget)` also
works, but fixing the *types* removes the whole bug class instead of this instance —
a distinction worth articulating in an interview.

**Lesson.** `==` on two boxed types is a reference comparison and the compiler will not
warn you. Note also that the cache's upper bound is tunable with
`-XX:AutoBoxCacheMax=<n>`, so this bug can behave differently in production than in a
test JVM — a fun thing to know and a genuinely horrible thing to debug.

**Say out loud:** "small values pass, large ones fail — that's the Integer cache. Both
sides are boxed and `==` is comparing references."

---

## Drill 03 — `HeartbeatMonitor`: the Comparator contract

**Symptom.** Devices minutes apart rank correctly. Devices 40 and 80 days apart come out
exactly reversed. A device silent for 365 days ranks correctly again.

**Root cause.**

```java
(a, b) -> (int) (a.observedAtMillis - b.observedAtMillis)
```

The difference is a `long`; the cast truncates it to its low 32 bits. Any gap larger
than 2³¹ ms — about **24.9 days** — can come back with the wrong sign. Whether it does
depends on where in the 4.29-billion-millisecond cycle the difference falls, which is
why a 40-day gap breaks and a 365-day gap silently works. The same defect appears with
plain `int` subtraction whenever the operands can straddle overflow.

**Fix.**

```java
static final Comparator<Heartbeat> OLDEST_FIRST =
        Comparator.comparingLong(h -> h.observedAtMillis);
```

**Lesson.** Never subtract to compare. `Integer.compare`, `Long.compare`,
`Comparator.comparingInt/Long/Double` exist for exactly this and cost nothing.

There is a second failure mode worth knowing: a comparator that violates the contract
can make `List.sort` throw `IllegalArgumentException: Comparison method violates its
general contract!`. TimSort only detects it on runs of 32+ elements, so the identical
bug is silent on small lists and throws in production — which is how most people meet
it. Being able to name that exception and explain that it comes from TimSort's merge
invariant check is a strong senior signal.

**Say out loud:** "subtraction-based comparator — that overflows past 2³¹, roughly 25
days in milliseconds."

---

## Drill 04 — `MaintenanceQueue`: ordering vs. equality

**Symptom.** Three devices scheduled for the same maintenance window; `size()` reports 1.
Two of them are gone, with no exception and no return value ignored — `TreeSet.add`
returned `false` and nobody looked.

**Root cause.**

```java
new TreeSet<>(Comparator.comparingInt(d -> d.nextMaintenanceDay))
```

`TreeSet` and `TreeMap` decide element *identity* with the comparator, not with
`equals`. `compare(a, b) == 0` means "already present". `drills.citi.Device.equals`/`hashCode` are
implemented perfectly here, by `deviceId`, and are **never called**. Because the
comparator only looks at the due day, every device in a shared maintenance window
collapses to one.

**Fix.** Make the ordering consistent with equals by adding a tiebreaker:

```java
import drills.citi.Device;

static final Comparator<Device> BY_DUE_DAY =
        Comparator.<Device>comparingInt(d -> d.nextMaintenanceDay)
                .thenComparing(d -> d.deviceId);
```

**Lesson.** The `Comparable` javadoc's "strongly recommended that `compareTo` be
consistent with `equals`" is a hard requirement the moment a sorted collection is
involved: an inconsistent comparator turns a `TreeSet` into a lossy deduplicator. If
your comparator can legitimately return 0 for objects you consider distinct, you need
either a tiebreaker, or a different structure — `PriorityQueue` (doesn't dedupe, but no
ordered iteration and O(n) `remove`), or `TreeMap<Integer, List<drills.citi.Device>>` (a bucket per
day, which also models "maintenance window" more honestly).

**What the author got right, and why it matters.** `reschedule` removes the device
*before* mutating `nextMaintenanceDay` and re-adds it after. Mutating a field the
comparator reads while the object sits in a `TreeSet` corrupts the tree — the element
becomes unfindable and unremovable. Notice it and say so; it shows you're reading for
invariants rather than scanning for typos.

**Say out loud:** "`TreeSet` is a set under the comparator, not under `equals` — is
`add` returning false?"

---

## Drill 05 — `AlertLog`: shared mutable state

**Symptom.** Alerts raised on `D-1` show up on `D-2`. Then a *freshly constructed*
`AlertLog` reports alerts that were raised on a different instance in a different test.
Which tests fail depends on the order they run in.

**Root cause.**

```java
private static final List<String> NO_ALERTS = new ArrayList<>();
...
List<String> history = alertsByDevice.getOrDefault(deviceId, NO_ALERTS);
history.add(alert);                      // mutates the shared default
alertsByDevice.put(deviceId, history);
```

`getOrDefault` does not insert anything — it hands back the default object you passed.
So the first alert for every device mutates the same `ArrayList` and then stores *that
same list* under the new key. Every device that was ever empty ends up aliasing one
list. And because the field is `static`, the corruption outlives the instance.

**Fix.**

```java
private static final List<String> NO_ALERTS = Collections.emptyList();   // immutable

public void raise(String deviceId, String alert) {
    alertsByDevice.computeIfAbsent(deviceId, id -> new ArrayList<>()).add(alert);
}
```

Two changes, and the second one matters as much as the first: making the shared default
immutable converts this entire bug class from silent corruption into an immediate
`UnsupportedOperationException` at the offending line.

**Lesson.** A mutable object used as a default value, a constant, or a "empty" sentinel
is shared state. `getOrDefault(k, new ArrayList<>())` is fine (fresh object each call,
though it allocates whether or not it's needed); `getOrDefault(k, SHARED_LIST)` is a
landmine. Reach for `computeIfAbsent` for the multimap pattern.

**Also still wrong, even after the fix:** `alertsFor` hands out the live internal list.
A caller can `log.alertsFor("D-1").clear()`. Return
`Collections.unmodifiableList(...)` or a copy. Spotting the *second* encapsulation leak
after fixing the first is the senior move.

**The diagnostic that cracks it.** A brand-new instance having data means the state is
static. A test that only fails when other tests ran first means the state is static.
Both signals point at the same word.

**Say out loud:** "a fresh `AlertLog` has data in it, so something here is static and
mutable."

---

## Drill 06 — `CapacityPlanner`: boxed types across a stream boundary

**Symptom.** `overCapacityLocations()` is correct. `isAtCapacity()` — twenty lines
away, same data, same maps — is `false` for everything, always.

**Root cause.**

```java
Long deployed = deviceCountByLocation().getOrDefault(location, 0L);
return deployed.equals(capacity);        // capacity is Integer
```

`Collectors.counting()` produces `Long`. `Long.equals(Object)` returns `false` for an
`Integer` no matter what the numbers are, because it type-checks before it
value-checks. It takes `Object`, so there is no compile error and no warning.

The neighbouring method works because `>` forces both operands to numeric promotion —
the boxes are unwrapped and compared as primitives. Two comparisons of the same two
values, one correct and one structurally incapable of ever being true.

**Fix.** Either compare primitives:

```java
return deployed.longValue() == capacity.longValue();
```

or stop mixing the types in the first place, by collecting to the type you actually
want:

```java
Collectors.groupingBy(d -> d.location, Collectors.summingInt(d -> 1))   // Map<String,Integer>
```

**Lesson.** `equals` between different boxed types is always `false`:
`Long.valueOf(2).equals(Integer.valueOf(2))` is `false`. This bites hardest where a
collector picks the type for you and it isn't the one you assumed — `counting()` → `Long`,
`summingInt` → `Integer`, `summingLong` → `Long`, `averagingInt` → `Double`. The same
trap lives in `List<Long>.contains(someInt)` and in `Map<Long,V>.get(someIntKey)`, both
of which take `Object` and silently miss.

**Diagnostic.** Print `getClass().getSimpleName()` on both sides of any `equals` that is
inexplicably false. (The self-contained harness for this drill does that for you in its
failure messages — it will not spoil the answer, but it will reward you when you get
close.)

**Say out loud:** "what type does `counting()` return? Because if that's a `Long` and
capacity is an `Integer`, that `equals` can never be true."

---

## Drill 07 — `SuspensionPolicy`: fields are not polymorphic

**Symptom.** Every device suspends at 5 failures, whatever its class. The camera's 1
and the gateway's 20 are ignored — but they're right there in the source.

**Root cause.** The subclasses **redeclare** the field rather than assigning it:

```java
static class Device {
    protected int failureLimit = 5;  ...
}

static class CameraDevice extends drills.citi.Device {
    protected int failureLimit = 1;
}
```

`CameraDevice` now has *two* `failureLimit` fields. Field access is resolved at compile
time from the static type of the expression, and `drills.citi.Device.recordFailure()` was compiled
against `drills.citi.Device.failureLimit`. So the base method reads the base field — 5 — forever.
`CameraDevice.failureLimit` holds 1 and is never read by anything.

This is field **hiding**, and it is not overriding. Methods dispatch on the runtime
type; fields never do.

**Fix.** Delete the subclass fields and set the inherited one:

```java
CameraDevice(String deviceId, String location) {
    super(deviceId, "CAMERA", location);
    this.failureLimit = 1;
}
```

Better, because it cannot be got wrong the same way again — make the limit a method,
since methods *do* dispatch dynamically:

```java
class drills.citi.Device       { protected int failureLimit() { return 5; } }
class CameraDevice { @Override protected int failureLimit() { return 1; } }
```

Best of all, if the hierarchy is closed: make it a constructor parameter, so a new
subclass cannot forget to supply one.

**Lesson.** Any per-subclass "constant" expressed as a redeclared field is a bug. If
you want polymorphic behaviour, it has to be a method call or a constructor-injected
value. Related traps in the same family: `static` methods are *hidden*, not overridden,
so `Base.create()` called on a `Derived` reference runs `Base`'s; and private methods
are never virtual, so a `protected` method you "override" that the base calls privately
never runs.

**Diagnostic.** In a debugger, a hidden field shows up twice on the same object. In
code, `((drills.citi.Device) camera).failureLimit` and `camera.failureLimit` printing different
values is conclusive.

**Say out loud:** "fields are hidden, not overridden — the base method is reading the
base field."

---

## Drill 08 — `TelemetryCollector`: atomic operations vs. atomic sequences

**Symptom.** After 200,000 concurrent records, `totalReadings` is exactly 200,000 but
the per-device counts sum to something less, and the shortfall differs every run. No
exception, no corruption, just quietly missing readings.

**Root cause.**

```java
int current = readingCount(deviceId);        // read
readingCounts.put(deviceId, current + 1);    // ...modify, write
```

`ConcurrentHashMap` guarantees that each individual `get` and each individual `put` is
atomic and safely published. It guarantees nothing about a *sequence* of them. Two
threads read 41, both write 42, and one reading is gone.

The `AtomicLong` total is right precisely because it does the whole increment in one
atomic operation. That contrast is the diagnostic: the same method, the same call
count, one counter correct and one short. Whatever is wrong is in the difference
between them — and the difference is compound-vs-single.

**Fix.**

```java
readingCounts.merge(deviceId, 1, Integer::sum);
```

`merge` performs the whole read-modify-write under the map's per-bin lock. Alternatives:
`compute(id, (k, v) -> v == null ? 1 : v + 1)`, or hold
`ConcurrentHashMap<String, LongAdder>` and `computeIfAbsent(id, k -> new LongAdder()).increment()`
— `LongAdder` is the right choice under real contention on few keys, because it spreads
the writes across cells instead of hammering one CAS. `AtomicInteger` is fine at low
contention.

`latestValues.put(deviceId, value)` is left alone deliberately: last-writer-wins is the
actual specification there, so a plain `put` is correct.

**Lesson.** A thread-safe container does not give you a thread-safe algorithm. Any
`get` … `put` pair on a shared map is a race, however concurrent the map is. Learn the
atomic-compound API by name — `merge`, `compute`, `computeIfAbsent`, `computeIfPresent`,
`putIfAbsent`, `replace(k, old, new)` — because the fix is almost always one of them,
and reaching for `synchronized` around the map instead is the answer that costs you the
offer.

**Say out loud:** "which invariant broke, and is any single operation non-atomic, or is
the *sequence* non-atomic?"

---

## The shapes, condensed

Worth having as a checklist. In every one of these the code reads correctly and at
least one test passes.

| What you observe | Suspect |
|---|---|
| Works when I compare directly, fails inside a collection | `equals` overloaded not overridden; missing `hashCode` |
| Works for small numbers, fails for large | boxed `==`, Integer cache boundary at 127 |
| Works for small gaps, fails for large; or `IllegalArgumentException: Comparison method violates its general contract` | subtraction-based comparator, `int`/`long` overflow |
| Elements silently disappear from a set or map | comparator inconsistent with `equals`, in `TreeSet`/`TreeMap` |
| A fresh instance already has data; failures move when tests are reordered | `static` mutable state, shared mutable default |
| `equals` that could never be true; one comparison works, the adjacent one doesn't | different boxed types across an `Object`-typed API |
| Subclass configuration ignored, base value always wins | field hiding; fields resolve statically |
| Counts are short and the shortfall varies per run | compound read-modify-write on a concurrent structure |

## The method, condensed

1. **Run it first.** Read the runner's actual output before you read the code. Confirm
   it even compiled.
2. **Read the passing tests.** In six of these eight drills the passing test tells you
   more than the failing one, because it bounds where the bug can be.
3. **Find the smallest difference** between a passing case and a failing case — 127 vs
   128, minutes vs days, one device vs two. That difference *is* the bug's signature.
4. **Form one hypothesis and test it cheaply** — a `println`, `getClass()`, an
   `identityHashCode`, a shrunk input — before editing anything.
5. **Narrate all of it.** In an interview the search is the deliverable. An interviewer
   watching you bisect a failure methodically will pass you even if the clock runs out;
   twenty silent minutes of rereading reads as being stuck whether or not you are.
6. **When the tests pass on your machine, say so and ask.** That is a finding, not a
   failure to find something.
