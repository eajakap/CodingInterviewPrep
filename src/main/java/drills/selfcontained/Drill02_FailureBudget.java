/*
=== DRILL 02 — Failure budget: escalation threshold ===
Focus area: autoboxing, reference vs. value comparison
Difficulty: medium — the bug is invisible on small fleets

We are building drills.citi.Device Fleet, a back-end system that manages the lifecycle of IoT
devices across multiple locations.

Definitions:
* Every device reports failures. FailureBudget keeps a running failureCount per
  deviceId.

* Every device type has a "failure budget": the number of failures the type is
  allowed to accumulate before the device must be escalated to a human operator.
  Budgets differ wildly by hardware class — a GATEWAY handles millions of requests a
  day and is expected to log failures in the thousands, whereas a SENSOR that fails
  three times is almost certainly dead.

* A device must be escalated exactly when its failureCount has reached the budget for
  its device type. Devices below budget are handled by automatic retry; devices past
  budget have already been escalated on an earlier report and must not be escalated
  again.

Tasks:

2-1) Read through and understand the code below. Feel free to run it.
     Run with:  java selfcontained/Drill02_FailureBudget.java   (from the fleet-drills root)

2-2) The test for FailureBudget is not passing due to a bug in the code. Make the
     necessary changes to the code under test to fix the bug. Do not change the tests.
*/

package drills.selfcontained;

import java.util.*;

public class Drill02_FailureBudget {

    // ------------------------------------------------------------------
    // Tests. Do not change these.
    // ------------------------------------------------------------------

    /** A SENSOR escalates on the failure that reaches its budget of 3. */
    static void testSensorEscalatesAtBudget() {
        FailureBudget budget = new FailureBudget();
        budget.register("D-1", "SENSOR");

        assertFalse("1 failure is below the SENSOR budget", budget.reportFailure("D-1"));
        assertFalse("2 failures is below the SENSOR budget", budget.reportFailure("D-1"));
        assertTrue("3 failures reaches the SENSOR budget", budget.reportFailure("D-1"));
        assertFalse("4 failures was already escalated at 3", budget.reportFailure("D-1"));
    }

    /** A GATEWAY escalates on the failure that reaches its budget of 2000. */
    static void testGatewayEscalatesAtBudget() {
        FailureBudget budget = new FailureBudget();
        budget.register("D-2", "GATEWAY");

        for (int i = 1; i < 2000; i++) {
            assertFalse("failure " + i + " is below the GATEWAY budget",
                    budget.reportFailure("D-2"));
        }
        assertTrue("failure 2000 reaches the GATEWAY budget", budget.reportFailure("D-2"));
        assertFalse("failure 2001 was already escalated at 2000",
                budget.reportFailure("D-2"));
    }

    /** A CAMERA escalates on the failure that reaches its budget of 128. */
    static void testCameraEscalatesAtBudget() {
        FailureBudget budget = new FailureBudget();
        budget.register("D-3", "CAMERA");

        for (int i = 1; i < 128; i++) {
            assertFalse("failure " + i + " is below the CAMERA budget",
                    budget.reportFailure("D-3"));
        }
        assertTrue("failure 128 reaches the CAMERA budget", budget.reportFailure("D-3"));
    }

    /** Unknown devices and unknown types never escalate. */
    static void testUnknownDevice() {
        FailureBudget budget = new FailureBudget();
        assertFalse("unknown device does not escalate", budget.reportFailure("NOPE"));

        budget.register("D-4", "DRONE");
        assertFalse("unbudgeted device type does not escalate", budget.reportFailure("D-4"));
    }

    public static void main(String[] args) {
        run("testSensorEscalatesAtBudget", Drill02_FailureBudget::testSensorEscalatesAtBudget);
        run("testGatewayEscalatesAtBudget", Drill02_FailureBudget::testGatewayEscalatesAtBudget);
        run("testCameraEscalatesAtBudget", Drill02_FailureBudget::testCameraEscalatesAtBudget);
        run("testUnknownDevice", Drill02_FailureBudget::testUnknownDevice);
        report();
    }

    // ------------------------------------------------------------------
    // Code under test.
    // ------------------------------------------------------------------

    static class FailureBudget {

        /** Failures a device type may accumulate before it must be escalated. */
        static final Map<String, Integer> BUDGET_BY_TYPE = new HashMap<>();
        static {
            BUDGET_BY_TYPE.put("SENSOR", 3);
            BUDGET_BY_TYPE.put("CAMERA", 128);
            BUDGET_BY_TYPE.put("GATEWAY", 2000);
        }

        private final Map<String, String> typeByDevice = new HashMap<>();
        private final Map<String, Integer> failureCountByDevice = new HashMap<>();

        /** Adds a device to the fleet with a failure count of zero. */
        public void register(String deviceId, String deviceType) {
            typeByDevice.put(deviceId, deviceType);
            failureCountByDevice.put(deviceId, 0);
        }

        /**
         * Records one failure for the device and returns whether this failure is the one
         * that reached the device type's budget (and therefore must be escalated).
         */
        public boolean reportFailure(String deviceId) {
            String deviceType = typeByDevice.get(deviceId);
            if (deviceType == null) {
                return false;
            }

            Integer budget = BUDGET_BY_TYPE.get(deviceType);
            if (budget == null) {
                return false;
            }

            Integer count = failureCountByDevice.get(deviceId) + 1;
            failureCountByDevice.put(deviceId, count);

            Boolean rv = atBudget(count, budget);

            return rv;
        }

        /** Returns the recorded failure count for a device. */
        public int failureCount(String deviceId) {
            return failureCountByDevice.getOrDefault(deviceId, 0);
        }

        /** Returns whether the failure count has landed exactly on the budget. */
        private static boolean atBudget(Integer count, Integer budget) {
            return count.intValue() == budget.intValue();
        }
    }

    // ------------------------------------------------------------------
    // Minimal test harness. Nothing below here is part of the exercise.
    // ------------------------------------------------------------------

    private static int failed = 0;
    private static int passed = 0;

    static void run(String name, Runnable body) {
        System.out.println("Running " + name);
        try {
            body.run();
            passed++;
        } catch (AssertionError e) {
            failed++;
            System.out.println("  FAILED: " + e.getMessage());
        } catch (RuntimeException e) {
            failed++;
            System.out.println("  ERROR:  " + e);
        }
    }

    static void report() {
        System.out.println();
        System.out.println(failed == 0
                ? "All tests passed. (" + passed + ")"
                : "Some tests failed. (" + failed + " of " + (failed + passed) + ")");
    }

    static void assertTrue(String msg, boolean condition) {
        if (!condition) throw new AssertionError(msg + " - expected true, was false");
    }

    static void assertFalse(String msg, boolean condition) {
        if (condition) throw new AssertionError(msg + " - expected false, was true");
    }

    static void assertEquals(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) {
            throw new AssertionError("expected <" + expected + "> but was <" + actual + ">");
        }
    }
}
