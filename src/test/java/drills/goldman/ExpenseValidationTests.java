package drills.goldman;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class ExpenseValidationTests {

    @Test
    void testNegativeCostThrows() {
        assertThrows(IllegalArgumentException.class, () ->
                new Expense("Ana", -100));
    }

    @Test
    void testNullPayerThrows() {
        assertThrows(IllegalArgumentException.class, () ->
                new Expense(null, 1000));
    }

    @Test
    void testInvalidPayerThrows() {
        List<String> friends = Arrays.asList("Ana", "Ben", "Cara");
        BillSplitter splitter = new BillSplitter(friends);
        assertThrows(IllegalArgumentException.class, () ->
                splitter.addExpense(new Expense("David", 1000)));
    }

    @Test
    void testZeroFriendsThrows() {
        assertThrows(IllegalArgumentException.class, () ->
                new BillSplitter(Collections.emptyList()));
    }

    @Test
    void testNegativeFriendsThrows() {
        assertThrows(IllegalArgumentException.class, () ->
                new BillSplitter(null));
    }
}
