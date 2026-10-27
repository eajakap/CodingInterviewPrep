package drills.barclays;

import java.util.*;

/**
 * You are building a **market data cache** that stores the latest prices for financial instruments.
 * The cache has a **fixed capacity** and must evict the **least recently used** entry when full.
 *
 * Implement an `LRUCache` class with the following operations:
 *
 * | Method | Description | Time Complexity |
 * |--------|-------------|-----------------|
 * | `LRUCache(capacity)` | Initialize with given capacity | O(1) |
 * | `get(key)` | Return value if present, else -1. Marks as recently used. | **O(1)** |
 * | `put(key, value)` | Insert or update. If at capacity, evict LRU before insert. | **O(1)** |
 *
 * **Both `get` and `put` must be O(1) time complexity.**
 *
 * ---
 *
 * ## Sample Input / Output
 *
 * ```
 * cache = LRUCache(3)
 *
 * cache.put("AAPL", 150.25)     # cache: [AAPL]
 * cache.put("MSFT", 420.10)     # cache: [AAPL, MSFT]
 * cache.put("GOOGL", 175.50)    # cache: [AAPL, MSFT, GOOGL]
 * cache.get("AAPL")             # returns 150.25, cache: [MSFT, GOOGL, AAPL]
 * cache.put("TSLA", 245.00)     # evicts MSFT (LRU), cache: [GOOGL, AAPL, TSLA]
 * cache.get("MSFT")             # returns -1 (evicted)
 * cache.put("AAPL", 151.00)     # updates AAPL, cache: [GOOGL, TSLA, AAPL]
 * cache.get("GOOGL")            # returns 175.50, cache: [TSLA, AAPL, GOOGL]
 * cache.put("META", 500.00)     # evicts TSLA (LRU), cache: [AAPL, GOOGL, META]
 * cache.get("TSLA")             # returns -1 (evicted)
 * cache.get("AAPL")             # returns 151.00 (updated value)
 * ```
 *
 * ### Expected Output Trace
 *
 * ```
 * get("AAPL")  -> 150.25
 * get("MSFT")  -> -1
 * get("GOOGL") -> 175.50
 * get("TSLA")  -> -1
 * get("AAPL")  -> 151.00
 * ```
 *
 * ---
 *
 * ## Constraints
 *
 * - Capacity is always >= 1
 * - Keys are strings, values are doubles
 */
public class LRUCache {
    static class Node {
        String key;
        double value;
        Node next;
        Node prev;

        public Node(String key, double value) {
            this.key = key;
            this.value = value;
        }
    }

    Map<String, Node> cache;
    int capacity;
    int size;
    Node head;
    Node tail;

    public LRUCache(int capacity) {
        this.cache = new HashMap<>();
        this.capacity = capacity;
        this.head = new Node(null, 0D);
        this.tail = new Node(null, 0D);
        this.head.next = tail;
        this.tail.prev = head;
        this.size = 0;
    }

    public void addNodeToFront(Node node) {
        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;
    }

    public void removeNode(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    public double get(String key) {
        if (cache.containsKey(key)) {
            Node node = cache.get(key);
            removeNode(node);
            addNodeToFront(node);
            return node.value;
        }
        return -1;
    }

    public void put(String key, double value) {
        if (!cache.containsKey(key)){
            Node node = new Node(key, value);
            cache.put(key, node);
            addNodeToFront(node);
            size++;
            if (size > capacity) {
                Node lru = tail.prev;
                removeNode(lru);
                cache.remove(lru.key);
                size--;
            }
        } else {
            Node node = cache.get(key);
            node.value = value;
            cache.put(key,node);
            removeNode(node);
            addNodeToFront(node);
        }
    }

    public void clear() {
        cache.clear();
        this.head = null;
        this.tail = null;
        this.size=0;
    }
    public String printLRUCache() {
        StringBuilder sb = new StringBuilder();
        if (cache.isEmpty()) {
            return String.format("Cache is empty");
        }
        int size = cache.size(), count = 0;
        for (Node node : cache.values()) {
            count++;
            sb.append(node.key).append("=")
                    .append(node.value)
                    .append(count != size ? "," : "");
        }
        return sb.toString();
    }


    /**
     * Executes a sequence of cache operations in the format used by coding
     * challenge test cases.
     *
     * @param operations operation names such as {@code LRUCache}, {@code get},
     *                    {@code put}, and {@code clear}
     * @param arguments arguments corresponding to each operation
     * @return one result per operation; constructor and {@code put} results are
     *         {@code null}, {@code get} results are integer values, and
     *         {@code clear} results are {@code "clear"}
     */
    public static List<Object> runLRUCache(String[] operations, String[][] arguments) {
        List<Object> results = new ArrayList<>();
        LRUCache cache = null;

        for (int i = 0; i < operations.length; i++) {
            String op = operations[i];
            String[] args = arguments[i];

            switch (op) {
                case "LRUCache":
                    cache = new LRUCache(Integer.parseInt(args[0]));
                    results.add("capacity: {" + cache.capacity + "}");
                    break;
                case "get":
                    results.add("get: {" + args[0] + ": "+ cache.get(args[0]) + "}");
                    break;
                case "put":
                    cache.put(args[0], Double.parseDouble(args[1]));
                    results.add("put: {" + args[0] + ": " + args[1] + "}");
                    break;
                case "print":
                    results.add("print: {" + cache.printLRUCache() + "}");
                    break;
                case "clear":
                    cache.clear();
                    results.add("clear: {}");
                    break;
                default:
                    results.add("Invalid operation");
            }
        }

        return results;
    }

    /** Runs the built-in LRU cache examples and prints each input and result. */
    public static void main(String[] args) {
        // Each row of arguments supplies the constructor or method arguments
        // for the operation at the same index.
        String[][][] testCases = {
                //{{LRUCacheCapacity}, {putKey, putValue}, {putKey, putValue}, {getKey}, {getKey}, {clear}},
                {{"1"}, {"AAPL", "150.25"}, {"MSFT", "420.10"}, {"AAPL"}, {"MSFT"}, {}, {}},
                {{"3"}, {"AAPL", "150.25"}, {"MSFT", "420.10"}, {"GOOGL", "175.50"}, {"AAPL"}, {"TSLA", "245.00"}, {"MSFT"},
                        {"AAPL", "151.00"}, {"GOOGL"}, {"META", "500.00"}, {"TSLA"}, {"AAPL"}, {}, {}},
        };

        String[][] operations = {
                {"LRUCache", "put", "put", "get", "get", "print", "clear"},
                {"LRUCache", "put", "put", "put", "get", "put", "get", "put", "get", "put", "get", "get", "print", "clear"},
        };

        int y = 1;
        for (int t = 0; t < testCases.length; t++) {
            System.out.println(y++ + ".\tInput operations: " + Arrays.toString(operations[t]));
            System.out.println("\tInput arguments: " + Arrays.deepToString(testCases[t]));
            List<Object> results = runLRUCache(operations[t], testCases[t]);
            System.out.println("\tResult: " + results);
            System.out.println(new String(new char[500]).replace('\0', '-'));
        }
    }

}
