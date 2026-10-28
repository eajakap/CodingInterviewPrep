package drills.blackrock;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class SlidingWindowLimiter {
    private final int k;
    private final long w;
    private final Map<String, Deque<Long>> log = new ConcurrentHashMap<>();

    public SlidingWindowLimiter(int k, long w) {
        this.k = k;
        this.w = w;
    }

    boolean allow(String client, long t) {
        Deque<Long> q = log.computeIfAbsent(client, c -> new ArrayDeque<>());
        synchronized (q) {                            // per-client lock, not global
            while (!q.isEmpty() && q.peekFirst() <= t - w) {
                q.pollFirst();
            }
            if (q.size() < k) {
                q.addLast(t);
                return true;
            }
            return false;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // At most 3 requests per client in any 1000 ms window; window at time t is (t-1000, t]
        SlidingWindowLimiter limiter = new SlidingWindowLimiter(3, 1_000);

        System.out.println("-- single client, explicit timestamps --");
        long[][] script = {          // {time, client: 0=A, 1=B}
                {0, 0}, {100, 0}, {200, 0},   // fills A's window
                {300, 0},                     // 4th in window -> denied
                {300, 1},                     // B has its own window -> allowed
                {999, 0},                     // t=0 still inside (-1, 999] -> denied
                {1000, 0},                    // t=0 expires (0 <= 1000-1000) -> allowed
                {1050, 0},                    // 100, 200, 1000 still in window -> denied
                {1100, 0}, {1200, 0}          // 100, then 200 expire -> allowed
        };
        for (long[] r : script) {
            String client = r[1] == 0 ? "A" : "B";
            System.out.printf("t=%4d  %s  %s%n", r[0], client, limiter.allow(client, r[0]) ? "ALLOW" : "deny");
        }

        System.out.println("\n-- 16 threads x 100 requests, same client, same instant --");
        int threads = 16, perThread = 100;
        AtomicInteger allowed = new AtomicInteger();
        CountDownLatch start = new CountDownLatch(1);
        ExecutorService pool = Executors.newFixedThreadPool(threads);
        for (int i = 0; i < threads; i++) {
            pool.submit(() -> {
                start.await();                                  // release all threads together
                for (int j = 0; j < perThread; j++)
                    if (limiter.allow("C", 5_000)) allowed.incrementAndGet();
                return null;
            });
        }
        start.countDown();
        pool.shutdown();
        pool.awaitTermination(10, TimeUnit.SECONDS);
        System.out.printf("%d requests -> %d allowed (expected exactly 3)%n", threads * perThread, allowed.get());
    }
}