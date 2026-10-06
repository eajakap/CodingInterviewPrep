/*
=== DRILL 03 — Heartbeat monitor: staleness ranking ===
Focus area: the Comparator contract, numeric width
Difficulty: hard — passes on freshly generated data, fails on real data

We are building drills.citi.Device Fleet, a back-end system that manages the lifecycle of IoT
devices across multiple locations.

Definitions:
* Every device sends a heartbeat. HeartbeatMonitor records the wall-clock time of the
  most recent heartbeat per device, as milliseconds since the Unix epoch.

* A device's "staleness" is how long it has been silent. Ranking the fleet by
  staleness — oldest heartbeat first — is how the on-call operator decides who to
  chase first.

* The fleet includes long-lived hardware. A GATEWAY in a decommissioned warehouse may
  not have been heard from in months, while an ACTIVE sensor reports every 30 seconds.
  The monitor must rank the whole fleet correctly regardless of how far apart the
  heartbeats are.

Tasks:

3-1) Read through and understand the code below. Feel free to run it.
     Run with:  ./run_junit.sh Drill03_HeartbeatMonitor   (from the fleet-drills root)

3-2) The test for HeartbeatMonitor is not passing due to a bug in the code. Make the
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

public class Drill03_HeartbeatMonitor {

    // A fixed reference instant, so the tests do not depend on the current time.
    static final long T0 = 1_700_000_000_000L;   // 2023-11-14T22:13:20Z
    static final long MINUTE = 60_000L;
    static final long DAY = 24L * 60 * MINUTE;

    // ------------------------------------------------------------------
    // Code under test.
    // ------------------------------------------------------------------

    /** The last heartbeat observed from one device. */
    static class Heartbeat {
        final String deviceId;
        final long observedAtMillis;

        Heartbeat(String deviceId, long observedAtMillis) {
            this.deviceId = deviceId;
            this.observedAtMillis = observedAtMillis;
        }
    }

    static class HeartbeatMonitor {

        /** Orders heartbeats oldest-first, so the stalest device sorts to the front. */
        static final Comparator<Heartbeat> OLDEST_FIRST =
                (a, b) -> (int) (a.observedAtMillis - b.observedAtMillis);

        private final Map<String, Heartbeat> latestByDevice = new HashMap<>();

        /** Records a heartbeat, replacing any earlier one for the same device. */
        public void record(String deviceId, long observedAtMillis) {
            latestByDevice.put(deviceId, new Heartbeat(deviceId, observedAtMillis));
        }

        /** Returns every device id, ordered from longest-silent to most recently heard. */
        public List<String> rankByStaleness() {
            List<Heartbeat> heartbeats = new ArrayList<>(latestByDevice.values());
            heartbeats.sort(OLDEST_FIRST);

            List<String> ranked = new ArrayList<>();
            for (Heartbeat heartbeat : heartbeats) {
                ranked.add(heartbeat.deviceId);
            }
            return ranked;
        }

        /** Returns the id of the device that has been silent the longest, or null. */
        public String stalest() {
            Heartbeat oldest = null;
            for (Heartbeat heartbeat : latestByDevice.values()) {
                if (oldest == null || OLDEST_FIRST.compare(heartbeat, oldest) < 0) {
                    oldest = heartbeat;
                }
            }
            return oldest == null ? null : oldest.deviceId;
        }
    }

    // ------------------------------------------------------------------
    // Tests. Do not change these.
    // ------------------------------------------------------------------

    public static class TestSuite {

        /** Devices heard from within the same hour rank correctly. */
        @Test
        public void testRankDevicesMinutesApart() {
            HeartbeatMonitor monitor = new HeartbeatMonitor();
            monitor.record("D-2", T0 + 30 * MINUTE);
            monitor.record("D-1", T0 + 5 * MINUTE);
            monitor.record("D-3", T0 + 50 * MINUTE);

            assertEquals(Arrays.asList("D-1", "D-2", "D-3"), monitor.rankByStaleness());
            assertEquals("D-1", monitor.stalest());
        }

        /** Devices heard from months apart rank correctly. */
        @Test
        public void testRankDevicesMonthsApart() {
            HeartbeatMonitor monitor = new HeartbeatMonitor();
            monitor.record("D-2", T0 + 40 * DAY);     // quiet for a while
            monitor.record("D-1", T0);                // silent the longest
            monitor.record("D-3", T0 + 80 * DAY);     // most recently heard

            assertEquals(Arrays.asList("D-1", "D-2", "D-3"), monitor.rankByStaleness());
            assertEquals("D-1", monitor.stalest());
        }

        /** A retired gateway silent for a year outranks everything else. */
        @Test
        public void testLongSilentGateway() {
            HeartbeatMonitor monitor = new HeartbeatMonitor();
            monitor.record("D-ACTIVE-1", T0 + 365 * DAY);
            monitor.record("D-ACTIVE-2", T0 + 365 * DAY + MINUTE);
            monitor.record("D-GATEWAY", T0);

            assertEquals("D-GATEWAY", monitor.stalest());
            assertEquals(Arrays.asList("D-GATEWAY", "D-ACTIVE-1", "D-ACTIVE-2"),
                    monitor.rankByStaleness());
        }

        /** An empty fleet has no stalest device. */
        @Test
        public void testEmptyFleet() {
            HeartbeatMonitor monitor = new HeartbeatMonitor();
            assertEquals(Collections.emptyList(), monitor.rankByStaleness());
            assertNull("no devices, no stalest", monitor.stalest());
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
