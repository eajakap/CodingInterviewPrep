package drills.blackrock;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SlidingWindowLimiter {
    private final int k;
    private final long w;
    private final Map<String, Deque<Long>> log = new ConcurrentHashMap<>();

    public SlidingWindowLimiter(int k, long w) { this.k = k; this.w = w; }

    boolean allow(String client, long t) {
        Deque<Long> q = log.computeIfAbsent(client, c -> new ArrayDeque<>());
        synchronized (q) {                            // per-client lock, not global
            while (!q.isEmpty() && q.peekFirst() <= t - w) q.pollFirst();
            if (q.size() < k) { q.addLast(t); return true; }
            return false;
        }
    }

    public static void main(String[] args){

    }
}