/*
=== DRILL 05 — Alert log: per-device alert history ===
Focus area: aliasing, shared mutable state, Map defaults
Difficulty: hard — the failure appears in a test that never touched the broken code

We are building drills.citi.Device Fleet, a back-end system that manages the lifecycle of IoT
devices across multiple locations.

Definitions:
* When a device misbehaves, the fleet raises an "alert" against it: a short message
  describing what happened.

* AlertLog keeps the alert history per deviceId, in the order the alerts were raised.
  A device that has never misbehaved has an empty history. Clearing a device's alerts
  returns it to an empty history.

* Alert histories are strictly per device. An alert raised against D-1 must never be
  visible on D-2, and a brand-new AlertLog must start out with nothing in it.

Tasks:

5-1) Read through and understand the code below. Feel free to run it.
     Run with:  ./run_junit.sh Drill05_AlertLog   (from the fleet-drills root)

5-2) The test for AlertLog is not passing due to a bug in the code. Make the necessary
     changes to the code under test to fix the bug. Do not change the tests.

     Note: JUnit does not guarantee the order in which test methods run, and which
     of these tests fail depends on that order. A test whose result depends on which
     other tests ran before it is a clue, not a flaw in the tests.
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

public class Drill05_AlertLog {

    // ------------------------------------------------------------------
    // Code under test.
    // ------------------------------------------------------------------

    static class AlertLog {

        /** The history handed back for a device that has no alerts. */
        private static final List<String> NO_ALERTS = new ArrayList<>();

        private final Map<String, List<String>> alertsByDevice = new HashMap<>();

        /** Records one alert against a device, at the end of its history. */
        public void raise(String deviceId, String alert) {
            List<String> history = alertsByDevice.getOrDefault(deviceId, NO_ALERTS);
            history.add(alert);
            alertsByDevice.put(deviceId, history);
        }

        /** Returns the alert history for a device, oldest first. */
        public List<String> alertsFor(String deviceId) {
            return alertsByDevice.getOrDefault(deviceId, NO_ALERTS);
        }

        /** Discards the alert history for a device. */
        public void clear(String deviceId) {
            alertsByDevice.remove(deviceId);
        }

        /** Returns the number of alerts held across the whole fleet. */
        public int totalAlerts() {
            int total = 0;
            for (List<String> history : alertsByDevice.values()) {
                total += history.size();
            }
            return total;
        }
    }

    // ------------------------------------------------------------------
    // Tests. Do not change these.
    // ------------------------------------------------------------------

    public static class TestSuite {

        /** A device that has never been alerted on has an empty history. */
        @Test
        public void testUnknownDeviceHasNoAlerts() {
            AlertLog log = new AlertLog();
            assertEquals(Collections.emptyList(), log.alertsFor("D-99"));
        }

        /** Alerts are recorded in order for a single device. */
        @Test
        public void testAlertsForSingleDevice() {
            AlertLog log = new AlertLog();
            log.raise("D-1", "HIGH_TEMPERATURE");
            log.raise("D-1", "PACKET_LOSS");

            assertEquals(Arrays.asList("HIGH_TEMPERATURE", "PACKET_LOSS"), log.alertsFor("D-1"));
        }

        /** Alerts raised on one device are not visible on another. */
        @Test
        public void testAlertsAreNotSharedBetweenDevices() {
            AlertLog log = new AlertLog();
            log.raise("D-1", "HIGH_TEMPERATURE");
            log.raise("D-2", "LOW_BATTERY");

            assertEquals(Arrays.asList("HIGH_TEMPERATURE"), log.alertsFor("D-1"));
            assertEquals(Arrays.asList("LOW_BATTERY"), log.alertsFor("D-2"));
            assertEquals(2, log.totalAlerts());
        }

        /** Clearing a device returns it to an empty history. */
        @Test
        public void testClearAlerts() {
            AlertLog log = new AlertLog();
            log.raise("D-3", "DISK_FULL");
            log.clear("D-3");

            assertEquals(Collections.emptyList(), log.alertsFor("D-3"));
            assertEquals(0, log.totalAlerts());
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
