/*
=== DRILL 06 — Capacity planner: locations at capacity ===
Focus area: streams and collectors, boxed-type equality
Difficulty: medium-hard — one comparison works, the neighbouring one never does

We are building drills.citi.Device Fleet, a back-end system that manages the lifecycle of IoT
devices across multiple locations.

Definitions:
* Each location has a "capacity": the maximum number of devices that may be deployed
  there, dictated by power and network headroom.

* CapacityPlanner reports, for the current fleet:
  - how many devices are deployed at each location;
  - which locations are over capacity (more devices deployed than allowed) — these
    are a live incident;
  - which locations are exactly at capacity — these are not an incident, but they
    block new deployments, so the planner must flag them for the procurement team.

* A location with no configured capacity is unmanaged: it is never at capacity and
  never over capacity.

Tasks:

6-1) Read through and understand the code below. Feel free to run it.
     Run with:  java selfcontained/Drill06_CapacityPlanner.java   (from the fleet-drills root)

6-2) The test for CapacityPlanner is not passing due to a bug in the code. Make the
     necessary changes to the code under test to fix the bug. Do not change the tests.
*/

package drills.selfcontained;

import java.util.*;
import java.util.stream.Collectors;

public class Drill06_CapacityPlanner {

    // ------------------------------------------------------------------
    // Tests. Do not change these.
    // ------------------------------------------------------------------

    static CapacityPlanner planner() {
        CapacityPlanner planner = new CapacityPlanner();
        planner.setCapacity("WAREHOUSE-A", 2);   // will hold exactly 2 -> at capacity
        planner.setCapacity("WAREHOUSE-B", 1);   // will hold 3         -> over capacity
        planner.setCapacity("WAREHOUSE-C", 5);   // will hold 1         -> has headroom

        planner.deploy(new Device("D-1", "SENSOR", "WAREHOUSE-A"));
        planner.deploy(new Device("D-2", "CAMERA", "WAREHOUSE-A"));
        planner.deploy(new Device("D-3", "SENSOR", "WAREHOUSE-B"));
        planner.deploy(new Device("D-4", "SENSOR", "WAREHOUSE-B"));
        planner.deploy(new Device("D-5", "GATEWAY", "WAREHOUSE-B"));
        planner.deploy(new Device("D-6", "GATEWAY", "WAREHOUSE-C"));
        planner.deploy(new Device("D-7", "SENSOR", "SITE-UNMANAGED"));
        return planner;
    }

    /** drills.citi.Device counts are tallied per location. */
    static void testDeviceCountByLocation() {
        Map<String, Long> counts = planner().deviceCountByLocation();

        assertEquals(4, counts.size());
        assertEquals(2L, counts.get("WAREHOUSE-A").longValue());
        assertEquals(3L, counts.get("WAREHOUSE-B").longValue());
        assertEquals(1L, counts.get("WAREHOUSE-C").longValue());
        assertEquals(1L, counts.get("SITE-UNMANAGED").longValue());
    }

    /** Locations holding more devices than allowed are reported as over capacity. */
    static void testOverCapacityLocations() {
        assertEquals(Arrays.asList("WAREHOUSE-B"), planner().overCapacityLocations());
    }

    /** Locations holding exactly their allowed number of devices are at capacity. */
    static void testAtCapacityLocations() {
        assertEquals(Arrays.asList("WAREHOUSE-A"), planner().atCapacityLocations());
        assertTrue("WAREHOUSE-A holds 2 of 2", planner().isAtCapacity("WAREHOUSE-A"));
    }

    /** Locations with headroom, and unmanaged locations, are neither at nor over capacity. */
    static void testHeadroomAndUnmanaged() {
        CapacityPlanner planner = planner();
        assertFalse("WAREHOUSE-C holds 1 of 5", planner.isAtCapacity("WAREHOUSE-C"));
        assertFalse("SITE-UNMANAGED has no configured capacity",
                planner.isAtCapacity("SITE-UNMANAGED"));
        assertEquals(4, planner.remainingCapacity("WAREHOUSE-C"));
    }

    public static void main(String[] args) {
        run("testDeviceCountByLocation", Drill06_CapacityPlanner::testDeviceCountByLocation);
        run("testOverCapacityLocations", Drill06_CapacityPlanner::testOverCapacityLocations);
        run("testAtCapacityLocations", Drill06_CapacityPlanner::testAtCapacityLocations);
        run("testHeadroomAndUnmanaged", Drill06_CapacityPlanner::testHeadroomAndUnmanaged);
        report();
    }

    // ------------------------------------------------------------------
    // Code under test.
    // ------------------------------------------------------------------

    /** Represents a single device with its identifying attributes. */
    static class Device {
        final String deviceId;
        final String deviceType;
        final String location;

        Device(String deviceId, String deviceType, String location) {
            this.deviceId = deviceId;
            this.deviceType = deviceType;
            this.location = location;
        }
    }

    static class CapacityPlanner {

        private final List<Device> devices = new ArrayList<>();
        private final Map<String, Integer> capacityByLocation = new HashMap<>();

        public void deploy(Device device) {
            devices.add(device);
        }

        public void setCapacity(String location, int capacity) {
            capacityByLocation.put(location, capacity);
        }

        /** Returns how many devices are deployed at each location. */
        public Map<String, Long> deviceCountByLocation() {
            return devices.stream().collect(
                    Collectors.groupingBy(device -> device.location, Collectors.counting()));
        }

        /** Returns the managed locations holding more devices than their capacity allows. */
        public List<String> overCapacityLocations() {
            Map<String, Long> counts = deviceCountByLocation();
            return capacityByLocation.keySet().stream()
                    .filter(location -> counts.getOrDefault(location, 0L)
                            > capacityByLocation.get(location))
                    .sorted()
                    .collect(Collectors.toList());
        }

        /** Returns the managed locations holding exactly their capacity in devices. */
        public List<String> atCapacityLocations() {
            return capacityByLocation.keySet().stream()
                    .filter(this::isAtCapacity)
                    .sorted()
                    .collect(Collectors.toList());
        }

        /** Returns whether the location is managed and holds exactly its capacity. */
        public boolean isAtCapacity(String location) {
            Integer capacity = capacityByLocation.get(location);
            if (capacity == null) {
                return false;
            }
            Long deployed = deviceCountByLocation().getOrDefault(location, 0L);
            return deployed.intValue() == capacity.intValue();
        }

        /** Returns how many more devices the location can take, or 0 if unmanaged. */
        public int remainingCapacity(String location) {
            Integer capacity = capacityByLocation.get(location);
            if (capacity == null) {
                return 0;
            }
            long deployed = deviceCountByLocation().getOrDefault(location, 0L);
            return (int) Math.max(0, capacity - deployed);
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
            throw new AssertionError("expected <" + expected + "> (" + typeOf(expected)
                    + ") but was <" + actual + "> (" + typeOf(actual) + ")");
        }
    }

    static String typeOf(Object o) {
        return o == null ? "null" : o.getClass().getSimpleName();
    }
}
