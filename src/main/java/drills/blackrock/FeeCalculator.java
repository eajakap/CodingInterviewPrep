package drills.blackrock;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

enum AssetType { EQUITY, BOND, ETF }
record Trade(String id, AssetType type, BigDecimal notional) {}
interface FeeStrategy { BigDecimal fee(Trade t); }
interface FeeListener { void onFee(Trade t, BigDecimal fee); }

class EquityFee implements FeeStrategy {
    public BigDecimal fee(Trade t) {
        return t.notional().multiply(new BigDecimal("0.001")).max(BigDecimal.ONE);
    }
}
class BondFee implements FeeStrategy { public BigDecimal fee(Trade t) { return new BigDecimal("5"); } }
class EtfFee implements FeeStrategy {
    public BigDecimal fee(Trade t) {
        return t.notional().compareTo(new BigDecimal("10000")) < 0 ? BigDecimal.ZERO
                : t.notional().multiply(new BigDecimal("0.0005"));
    }
}

public class FeeCalculator {
    private final Map<AssetType, FeeStrategy> strategies;
    private final List<FeeListener> listeners = new CopyOnWriteArrayList<>();
    FeeCalculator(Map<AssetType, FeeStrategy> s) { this.strategies = Map.copyOf(s); }
    void subscribe(FeeListener l) { listeners.add(l); }
    BigDecimal calculate(Trade t) {
        FeeStrategy s = Optional.ofNullable(strategies.get(t.type()))
                .orElseThrow(() -> new IllegalArgumentException("No fee rule for " + t.type()));
        BigDecimal fee = s.fee(t).setScale(2, RoundingMode.HALF_EVEN);
        listeners.forEach(l -> l.onFee(t, fee));
        return fee;
    }

    public static void main(String[] args) {
        FeeCalculator calc = new FeeCalculator(Map.of(
                AssetType.EQUITY, new EquityFee(),
                AssetType.BOND, new BondFee(),
                AssetType.ETF, new EtfFee()));
        calc.subscribe((t, fee) -> System.out.printf("%-3s %-6s notional=%-10s fee=%s%n",
                t.id(), t.type(), t.notional(), fee));

        List.of(
                new Trade("T1", AssetType.EQUITY, new BigDecimal("250000")),   // 0.1% -> 250.00
                new Trade("T2", AssetType.EQUITY, new BigDecimal("500")),      // 0.50 -> min 1.00
                new Trade("T3", AssetType.BOND, new BigDecimal("1000000")),    // flat 5.00
                new Trade("T4", AssetType.ETF, new BigDecimal("9999.99")),     // below 10k -> 0.00
                new Trade("T5", AssetType.ETF, new BigDecimal("12345"))        // 0.05% -> 6.17
        ).forEach(calc::calculate);

        FeeCalculator equityOnly = new FeeCalculator(Map.of(AssetType.EQUITY, new EquityFee()));
        try {
            equityOnly.calculate(new Trade("T6", AssetType.BOND, new BigDecimal("100")));
        } catch (IllegalArgumentException e) {
            System.out.println("error: " + e.getMessage());
        }
    }
}