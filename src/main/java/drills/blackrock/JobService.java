package drills.blackrock;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

class JobService {
    private final AtomicLong seq = new AtomicLong();
    private final PriorityQueue<QueuedJob> queue = new PriorityQueue<>(
            Comparator.comparing((QueuedJob q) -> q.job().priority()).thenComparingLong(QueuedJob::seq));
    private final Set<String> seen = ConcurrentHashMap.newKeySet();
    private final Notifier notifier;               // injected, not new'd
    JobService(Notifier notifier) { this.notifier = notifier; }

    synchronized boolean submit(Job j) {
        if (!seen.add(j.id())) return false;       // duplicate
        return queue.offer(new QueuedJob(j, seq.incrementAndGet()));
    }
    void runNext() {
        QueuedJob q; synchronized (this) { q = queue.poll(); }
        if (q == null) return;
        for (int attempt = 1; attempt <= 3; attempt++) {
            try { q.job().run(); return; }
            catch (Exception e) { if (attempt == 3) notifier.failed(q.job(), e); }
        }
    }
    synchronized boolean hasPending() { return !queue.isEmpty(); }

    public static void main(String[] args) {
        JobService service = new JobService((job, e) ->
                System.out.println("FAILED " + job.id() + ": " + e.getMessage()));

        service.submit(new Job("A", 2, () -> System.out.println("run A (p2)")));
        service.submit(new Job("B", 1, () -> System.out.println("run B (p1)")));
        service.submit(new Job("C", 2, () -> System.out.println("run C (p2)")));
        service.submit(new Job("D", 1, () -> { throw new IllegalStateException("boom"); }));
        System.out.println("duplicate A accepted? " + service.submit(new Job("A", 0, () -> {})));

        while (service.hasPending()) service.runNext();   // B, D (fails x3), A, C
    }
}

record QueuedJob(Job job, long seq) {}

/** Unit of work; lower priority value runs first. */
record Job(String id, int priority, Task task) {
    void run() throws Exception { task.run(); }
}

/** Like Runnable, but allowed to throw checked exceptions. */
@FunctionalInterface
interface Task {
    void run() throws Exception;
}

/** Called when a job has failed all retry attempts. */
@FunctionalInterface
interface Notifier {
    void failed(Job job, Exception cause);
}
