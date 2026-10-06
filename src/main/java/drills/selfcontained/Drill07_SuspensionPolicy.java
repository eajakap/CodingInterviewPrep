/*
=== DRILL 07 — Suspension policy: per-type failure limits ===
Focus area: inheritance, what is and is not polymorphic
Difficulty: medium — reads correctly, behaves as if the subclasses were not there

We are building drills.citi.Device Fleet, a back-end system that manages the lifecycle of IoT
devices across multiple locations.

Definitions:
* A device accumulates a failureCount. When the count reaches the limit for that class
  of hardware, the device is automatically moved to SUSPENDED and stops being polled.

* The limits are:
  - a plain device (the default):        5 failures
  - CameraDevice:                        1 failure   (an optical fault is never transient)
  - GatewayDevice:                      20 failures  (a gateway is expected to be noisy;
                                                      suspending one takes a whole site
                                                      offline, so the bar is high)

* SuspensionPolicy processes failure reports for a mixed fleet held as a
  List<drills.citi.Device> — the policy code knows nothing about the concrete hardware classes and
  must not need to.

Tasks:

7-1) Read through and understand the code below. Feel free to run it.
     Run with:  java selfcontained/Drill07_SuspensionPolicy.java   (from the fleet-drills root)

7-2) The test for SuspensionPolicy is not passing due to a bug in the code. Make the
     necessary changes to the code under test to fix the bug. Do not change the tests.
*/

package drills.selfcontained;

import java.util.*;

public class Drill07_SuspensionPolicy {

    // ------------------------------------------------------------------
    // Tests. Do not change these.
    // ------------------------------------------------------------------

    /** A plain device suspends on its fifth failure. */
    static void testDefaultLimit() {
        Device sensor = new Device("D-1", "SENSOR", "WAREHOUSE-A");
        SuspensionPolicy policy = new SuspensionPolicy(Arrays.asList(sensor));

        for (int i = 0; i < 4; i++) {
            policy.reportFailure("D-1");
        }
        assertEquals("ACTIVE", sensor.status);

        policy.reportFailure("D-1");
        assertEquals("SUSPENDED", sensor.status);
    }

    /** A camera suspends on its first failure. */
    static void testCameraLimit() {
        Device camera = new CameraDevice("D-2", "WAREHOUSE-A");
        SuspensionPolicy policy = new SuspensionPolicy(Arrays.asList(camera));

        policy.reportFailure("D-2");
        assertEquals("SUSPENDED", camera.status);
    }

    /** A gateway tolerates nineteen failures and suspends on the twentieth. */
    static void testGatewayLimit() {
        Device gateway = new GatewayDevice("D-3", "WAREHOUSE-B");
        SuspensionPolicy policy = new SuspensionPolicy(Arrays.asList(gateway));

        for (int i = 0; i < 19; i++) {
            policy.reportFailure("D-3");
        }
        assertEquals("ACTIVE", gateway.status);

        policy.reportFailure("D-3");
        assertEquals("SUSPENDED", gateway.status);
    }

    /** A mixed fleet applies each device's own limit. */
    static void testMixedFleet() {
        Device sensor = new Device("D-1", "SENSOR", "WAREHOUSE-A");
        Device camera = new CameraDevice("D-2", "WAREHOUSE-A");
        Device gateway = new GatewayDevice("D-3", "WAREHOUSE-B");
        SuspensionPolicy policy = new SuspensionPolicy(Arrays.asList(sensor, camera, gateway));

        for (int i = 0; i < 6; i++) {
            policy.reportFailure("D-1");
            policy.reportFailure("D-2");
            policy.reportFailure("D-3");
        }

        assertEquals(Arrays.asList("D-1", "D-2"), policy.suspendedDeviceIds());
    }

    public static void main(String[] args) {
//        run("testDefaultLimit", Drill07_SuspensionPolicy::testDefaultLimit);
        run("testCameraLimit", Drill07_SuspensionPolicy::testCameraLimit);
//        run("testGatewayLimit", Drill07_SuspensionPolicy::testGatewayLimit);
//        run("testMixedFleet", Drill07_SuspensionPolicy::testMixedFleet);
        report();
    }

    // ------------------------------------------------------------------
    // Code under test.
    // ------------------------------------------------------------------

    /** Represents a single device with its identifying and lifecycle attributes. */
    static class Device {
        final String deviceId;
        final String deviceType;
        final String location;
        String status = "ACTIVE";
        int failureCount = 0;

        /** Failures this class of hardware tolerates before it is suspended. */
        protected int failureLimit = 5;

        Device(String deviceId, String deviceType, String location) {
            this.deviceId = deviceId;
            this.deviceType = deviceType;
            this.location = location;
        }

        /** Records a failure and suspends the device once it has reached its limit. */
        void recordFailure() {
            failureCount++;
            if (failureCount >= failureLimit) {
                status = "SUSPENDED";
            }
        }
    }

    /** An optical fault is never transient, so a camera is suspended on first failure. */
    static class CameraDevice extends Device {
//        protected int failureLimit = 1;

        CameraDevice(String deviceId, String location) {
            super(deviceId, "CAMERA", location);
            this.failureLimit = 1;
        }
    }

    /** Suspending a gateway takes a whole site offline, so the bar is deliberately high. */
    static class GatewayDevice extends Device {
//        protected int failureLimit = 20;

        GatewayDevice(String deviceId, String location) {
            super(deviceId, "GATEWAY", location);
            this.failureLimit = 20;
        }
    }

    static class SuspensionPolicy {

        private final Map<String, Device> devices = new LinkedHashMap<>();

        SuspensionPolicy(List<Device> fleet) {
            for (Device device : fleet) {
                devices.put(device.deviceId, device);
            }
        }

        /** Applies one failure report to the named device. */
        public void reportFailure(String deviceId) {
            Device device = devices.get(deviceId);
            if (device == null || "SUSPENDED".equals(device.status)) {
                return;
            }
            device.recordFailure();
        }

        /** Returns the ids of the devices currently suspended, in fleet order. */
        public List<String> suspendedDeviceIds() {
            List<String> suspended = new ArrayList<>();
            for (Device device : devices.values()) {
                if ("SUSPENDED".equals(device.status)) {
                    suspended.add(device.deviceId);
                }
            }
            return suspended;
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

    static void assertEquals(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) {
            throw new AssertionError("expected <" + expected + "> but was <" + actual + ">");
        }
    }
}
