/*
=== DRILL 08 — Telemetry collector: concurrent ingest ===
Focus area: concurrency, atomicity of compound operations
Difficulty: hard — nothing throws, the numbers are just quietly short

We are building drills.citi.Device Fleet, a back-end system that manages the lifecycle of IoT
devices across multiple locations.

Definitions:
* Devices push telemetry readings into the fleet back end. Readings arrive on many
  ingest threads at once; a single device's readings are not pinned to one thread.

* TelemetryCollector accumulates, per device:
  - readingCount: how many readings that device has pushed;
  - latestValue: the most recent value pushed by that device.

  It also keeps a fleet-wide totalReadings counter, used by the billing job.

* The invariant the billing job depends on: totalReadings must equal the sum of the
  per-device reading counts. If it does not, customers are billed for readings the
  fleet cannot account for.

Tasks:

8-1) Read through and understand the code below. Feel free to run it.
     Run with:  ./run_junit.sh Drill08_TelemetryCollector   (from the fleet-drills root)

8-2) The test for TelemetryCollector is not passing due to a bug in the code. Make the
     necessary changes to the code under test to fix the bug. Do not change the tests.
*/

package junit4;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

public class Drill08_TelemetryCollector {

    static final int THREADS = 8;
    static final int READINGS_PER_THREAD = 25_000;
    static final List<String> DEVICE_IDS = Arrays.asList("D-1", "D-2", "D-3", "D-4");

    // ------------------------------------------------------------------
    // Code under test.
    // ------------------------------------------------------------------

    static class TelemetryCollector {

        private final ConcurrentMap<String, Integer> readingCounts = new ConcurrentHashMap<>();
        private final ConcurrentMap<String, Double> latestValues = new ConcurrentHashMap<>();
        private final AtomicLong totalReadings = new AtomicLong();

        /** Records one reading pushed by a device. Called from many ingest threads. */
        public void record(String deviceId, double value) {
            int current = readingCount(deviceId);
            readingCounts.put(deviceId, current + 1);
            latestValues.put(deviceId, value);
            totalReadings.incrementAndGet();
        }

        /** Returns how many readings the device has pushed. */
        public int readingCount(String deviceId) {
            return readingCounts.getOrDefault(deviceId, 0);
        }

        /** Returns the most recent value pushed by the device, or null. */
        public Double latestValue(String deviceId) {
            return latestValues.get(deviceId);
        }

        /** Returns the fleet-wide reading count used by the billing job. */
        public long totalReadings() {
            return totalReadings.get();
        }
    }

    // ------------------------------------------------------------------
    // Tests. Do not change these.
    // ------------------------------------------------------------------

    public static class TestSuite {

        /** A single ingest thread counts every reading. */
        @Test
        public void testSingleThreadedIngest() {
            TelemetryCollector collector = new TelemetryCollector();
            for (int i = 0; i < 1000; i++) {
                collector.record("D-1", i * 0.5);
            }

            assertEquals(1000, collector.readingCount("D-1"));
            assertEquals(1000L, collector.totalReadings());
            assertEquals(499.5, collector.latestValue("D-1"));
        }

        /** Every reading pushed concurrently is counted against its device. */
        @Test
        public void testConcurrentIngestCountsPerDevice() throws Exception {
            TelemetryCollector collector = ingestConcurrently();

            int expectedPerDevice = THREADS * READINGS_PER_THREAD / DEVICE_IDS.size();
            for (String deviceId : DEVICE_IDS) {
                assertEquals("count for " + deviceId, expectedPerDevice,
                        collector.readingCount(deviceId));
            }
        }

        /** The billing invariant holds: fleet total equals the sum of the per-device counts. */
        @Test
        public void testBillingInvariant() throws Exception {
            TelemetryCollector collector = ingestConcurrently();

            long total = collector.totalReadings();
            long sumOfCounts = 0;
            for (String deviceId : DEVICE_IDS) {
                sumOfCounts += collector.readingCount(deviceId);
            }

            assertEquals((long) THREADS * READINGS_PER_THREAD, total);
            assertEquals("sum of per-device counts must equal the fleet total",
                    total, sumOfCounts);
        }

        /** Drives THREADS ingest threads that all start at once. */
        static TelemetryCollector ingestConcurrently() throws Exception {
            TelemetryCollector collector = new TelemetryCollector();
            ExecutorService pool = Executors.newFixedThreadPool(THREADS);
            CountDownLatch startGate = new CountDownLatch(1);
            CountDownLatch doneGate = new CountDownLatch(THREADS);

            for (int t = 0; t < THREADS; t++) {
                pool.submit(() -> {
                    try {
                        startGate.await();
                        for (int i = 0; i < READINGS_PER_THREAD; i++) {
                            collector.record(DEVICE_IDS.get(i % DEVICE_IDS.size()), i);
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } finally {
                        doneGate.countDown();
                    }
                });
            }

            startGate.countDown();
            doneGate.await(60, TimeUnit.SECONDS);
            pool.shutdown();
            return collector;
        }
    }

    public static void main(String[] argv) {
        Result result = JUnitCore.runClasses(TestSuite.class);

        for (Failure failure : result.getFailures()) {
            System.out.println(failure.getTrace());
        }

        if (result.wasSuccessful()) {
            System.out.println("All tests passed.");
        } else {
            System.out.println("Some tests failed. ("
                    + result.getFailureCount() + " of " + result.getRunCount() + ")");
        }
    }
}
