package drills.goldman;

import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class DynamicFriendListTests {

    @Test
    void testFourFriendsSplit() {
        List<String> friends = Arrays.asList("Ana", "Ben", "Cara", "Dan");
        BillSplitter splitter = new BillSplitter(friends);

        splitter.addExpense(new Expense("Ana", 1000)); // 1000 / 4 = 250 each

        Map<String, Long> balance = splitter.getBalance();

        assertEquals(750, balance.get("Ana"));
        assertEquals(-250, balance.get("Ben"));
        assertEquals(-250, balance.get("Cara"));
        assertEquals(-250, balance.get("Dan"));
    }

    @Test
    void testFiveFriendsUnevenSplit() {
        List<String> friends = Arrays.asList("A", "B", "C", "D", "E");
        BillSplitter splitter = new BillSplitter(Arrays.asList("A", "B", "C", "D", "E"));

        splitter.addExpense(new Expense("C", 1003)); // 1003 / 5 = 200 each, remainder = 3

        // Remainder goes to C, D, E
        Map<String, Long> balance = splitter.getBalance();

        assertEquals(-200, balance.get("A"));
        assertEquals(-200, balance.get("B"));
        assertEquals(1003 - 201, balance.get("C")); // payer gets first extra cent
        assertEquals(-201, balance.get("D"));
        assertEquals(-201, balance.get("E"));
    }
}
