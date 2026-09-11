package drills.goldman;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;

public class BillSplitterTest {

    @Test
    void testUnevenSplit() {
        BillSplitter splitter = new BillSplitter(List.of("Ana", "Ben", "Cara"));

        // 10000 cents, paid by Ana
        splitter.addExpense(new Expense("Ana", 10000));

        Map<String, Long> balance = splitter.getBalance();

        // 10000 / 3 = 3333 each, remainder = 1 → goes to Ana
        // Ana share = 3334 → Ana is owed 10000 - 3334 = 6666
        assertEquals(6666, balance.get("Ana"));
        assertEquals(-3333, balance.get("Ben"));
        assertEquals(-3333, balance.get("Cara"));
    }

    @Test
    void testMultipleActivities() {
        List<String> participants = List.of("Ana", "Ben", "Cara");
        BillSplitter splitter = new BillSplitter(participants);

        splitter.addExpense(new Expense("Ana", 10000)); // Restaurant
        splitter.addExpense(new Expense("Cara", 4500)); // Movie
        splitter.addExpense(new Expense("Ben", 850));   // Coffee
        splitter.addExpense(new Expense("Ben", 200), List.of("Ben", "Cara"));   // Coffee

        Map<String, Long> balance = splitter.getBalance();

        // Expected final balances after all three activities:
        // Ana: +4883 (6666 -1500 -283)
        // Ben: -4267 (-3333 -1500 +566, +100)
        // Cara: -616 (-3333 +3000 -283, -100)
        assertEquals(4883, balance.get("Ana"));
        assertEquals(-4167, balance.get("Ben"));
        assertEquals(-716, balance.get("Cara"));
    }

    @Test
    void testSettlementMinimalTransactions() {
        List<String> participants = List.of("Ana", "Ben", "Cara");
        BillSplitter splitter = new BillSplitter(participants);

        splitter.addExpense(new Expense("Ana", 10000));
        splitter.addExpense(new Expense("Cara", 4500));
        splitter.addExpense(new Expense("Ben", 850));

        Settlement settlement = new Settlement();
        List<Settlement.Transaction> txns = settlement.settle(splitter.getBalance());

        // Expected payments after all three activities:
        // Ben pays Ana: 4267
        // Cara pays Ana: 616

        assertEquals(2, txns.size());

        Settlement.Transaction t1 = txns.get(0);
        Settlement.Transaction t2 = txns.get(1);

        // Order may vary, so check both combinations
        boolean match1 =
                t1.from.equals("Ben") && t1.to.equals("Ana") && t1.amount == 4267 &&
                        t2.from.equals("Cara") && t2.to.equals("Ana") && t2.amount == 616;

        boolean match2 =
                t2.from.equals("Ben") && t2.to.equals("Ana") && t2.amount == 4267 &&
                        t1.from.equals("Cara") && t1.to.equals("Ana") && t1.amount == 616;

        assertTrue(match1 || match2);
    }

    @Test
    void testZeroRemainderSplit() {
        List<String> participants = List.of("Ana", "Ben", "Cara");
        BillSplitter splitter = new BillSplitter(participants);

        // 4500 cents, paid by Cara → 4500 / 3 = 1500 each, no remainder
        splitter.addExpense(new Expense("Cara", 4500));

        Map<String, Long> balance = splitter.getBalance();

        assertEquals(-1500, balance.get("Ana"));
        assertEquals(-1500, balance.get("Ben"));
        assertEquals(3000, balance.get("Cara")); // Cara paid 4500 but owes only 1500
    }
}
