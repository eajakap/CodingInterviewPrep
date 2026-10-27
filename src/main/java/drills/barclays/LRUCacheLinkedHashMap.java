package drills.barclays;

import java.util.*;

/**
 * LRU cache built on {@link LinkedHashMap} with access ordering.
 * See {@link LRUCache} for the hand-rolled HashMap + doubly linked list version.
 */
public class LRUCacheLinkedHashMap<K, V> extends LinkedHashMap<K, V> {
    private final int capacity;

    public LRUCacheLinkedHashMap(int capacity) {
        super(capacity, 0.75f, true);   // true = access order, not insertion order
        this.capacity = capacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > capacity;       // evict LRU after the insert pushes us over capacity
    }

    /** Runs the sample trace from the problem statement and prints each step. */
    public static void main(String[] args) {
        LRUCacheLinkedHashMap<String, Double> cache = new LRUCacheLinkedHashMap<>(3);

        cache.put("AAPL", 150.25);
        System.out.println("put(AAPL, 150.25)  -> " + cache.keySet());
        cache.put("MSFT", 420.10);
        System.out.println("put(MSFT, 420.10)  -> " + cache.keySet());
        cache.put("GOOGL", 175.50);
        System.out.println("put(GOOGL, 175.50) -> " + cache.keySet());
        System.out.println("get(AAPL)          = " + cache.getOrDefault("AAPL", -1.0) + "  " + cache.keySet());
        cache.put("TSLA", 245.00);
        System.out.println("put(TSLA, 245.00)  -> " + cache.keySet() + "  (evicted MSFT)");
        System.out.println("get(MSFT)          = " + cache.getOrDefault("MSFT", -1.0) + "  " + cache.keySet());
        cache.put("AAPL", 151.00);
        System.out.println("put(AAPL, 151.00)  -> " + cache.keySet());
        System.out.println("get(GOOGL)         = " + cache.getOrDefault("GOOGL", -1.0) + "  " + cache.keySet());
        cache.put("META", 500.00);
        System.out.println("put(META, 500.00)  -> " + cache.keySet() + "  (evicted TSLA)");
        System.out.println("get(TSLA)          = " + cache.getOrDefault("TSLA", -1.0) + "  " + cache.keySet());
        System.out.println("get(AAPL)          = " + cache.getOrDefault("AAPL", -1.0) + "  " + cache.keySet());

        // Check against the expected output trace
        LRUCacheLinkedHashMap<String, Double> check = new LRUCacheLinkedHashMap<>(3);
        check.put("AAPL", 150.25);
        check.put("MSFT", 420.10);
        check.put("GOOGL", 175.50);
        double[] actual = new double[5];
        actual[0] = check.getOrDefault("AAPL", -1.0);
        check.put("TSLA", 245.00);
        actual[1] = check.getOrDefault("MSFT", -1.0);
        check.put("AAPL", 151.00);
        actual[2] = check.getOrDefault("GOOGL", -1.0);
        check.put("META", 500.00);
        actual[3] = check.getOrDefault("TSLA", -1.0);
        actual[4] = check.getOrDefault("AAPL", -1.0);

        double[] expected = {150.25, -1, 175.50, -1, 151.00};
        System.out.println(new String(new char[60]).replace('\0', '-'));
        System.out.println("Expected: " + Arrays.toString(expected));
        System.out.println("Actual:   " + Arrays.toString(actual));
        System.out.println(Arrays.equals(expected, actual) ? "PASS" : "FAIL");
    }
}
