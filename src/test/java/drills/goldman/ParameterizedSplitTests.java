package drills.goldman;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParameterizedSplitTests {

    @ParameterizedTest
    @CsvSource({
            "100,3,Ana,34,33,33",
            "101,3,Ben,33,34,34",
            "4500,3,Cara,1500,1500,1500"
    })
    void testParameterizedSplits(long total, int nFriends, String payer,
                                 long anaShare, long benShare, long caraShare) {

        List<String> friends = Arrays.asList("Ana", "Ben", "Cara");
        BillSplitter splitter = new BillSplitter(friends);
        splitter.addExpense(new Expense(payer, total));

        Map<String, Long> balance = splitter.getBalance();

        // Convert balances back to shares for comparison
        long totalPaid = total;
        long payerBalance = balance.get(payer);

        long payerShare = totalPaid - payerBalance;

        long anaBalance = balance.get("Ana");
        long benBalance = balance.get("Ben");
        long caraBalance = balance.get("Cara");

        long anaActualShare = "Ana".equals(payer) ? payerShare : -anaBalance;
        long benActualShare = "Ben".equals(payer) ? payerShare : -benBalance;
        long caraActualShare = "Cara".equals(payer) ? payerShare : -caraBalance;

        assertEquals(anaShare, anaActualShare);
        assertEquals(benShare, benActualShare);
        assertEquals(caraShare, caraActualShare);
    }
}
