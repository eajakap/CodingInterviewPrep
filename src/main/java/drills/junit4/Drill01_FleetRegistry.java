/*
=== DRILL 01 — Fleet Registry: slot lookup ===
Focus area: object identity, hash-based collections
Difficulty: warm-up

We are building drills.citi.Device Fleet, a back-end system that manages the lifecycle of IoT
devices across multiple locations.

Definitions:
* A "device" is an object representing a single piece of IoT hardware deployed at a
  location. A device has:
  - deviceId
  - deviceType
  - location
  - status

* Physical hardware is installed into a numbered "slot" at a location. A slot is
  identified by the pair (location, slotNumber), and at most one device may occupy a
  given slot at a time.

* FleetRegistry indexes the fleet by slot so that field technicians, who know only
  "rack 4 in WAREHOUSE-A", can resolve a slot to the device installed there. The
  technician's client constructs its own SlotKey from the location and slot number it
  was given; it does not have a handle on the SlotKey object the registry used when
  the device was installed.

Tasks:

1-1) Read through and understand the code below. Feel free to run it.
     Run with:  ./run_junit.sh Drill01_FleetRegistry   (from the fleet-drills root)

1-2) The test for FleetRegistry is not passing due to a bug in the code. Make the
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

public class Drill01_FleetRegistry {

    // ------------------------------------------------------------------
    // Code under test.
    // ------------------------------------------------------------------

    /** Represents a single device with its identifying and lifecycle attributes. */
    static class Device {
        String deviceId;
        String deviceType;
        String location;
        String status;

        Device(String deviceId, String deviceType, String location, String status) {
            this.deviceId = deviceId;
            this.deviceType = deviceType;
            this.location = location;
            this.status = status;
        }
    }

    /** Identifies one physical installation slot: a numbered position at a location. */
    static class SlotKey {
        final String location;
        final int slotNumber;

        SlotKey(String location, int slotNumber) {
            this.location = location;
            this.slotNumber = slotNumber;
        }

        public boolean equals(SlotKey other) {
            if (other == null) {
                return false;
            }
            return this.slotNumber == other.slotNumber
                    && Objects.equals(this.location, other.location);
        }

        @Override
        public int hashCode() {
            return Objects.hash(location, slotNumber);
        }

        @Override
        public String toString() {
            return location + "/" + slotNumber;
        }
    }

    /** Maps each occupied installation slot to the device installed in it. */
    static class FleetRegistry {
        private final Map<SlotKey, Device> bySlot = new HashMap<>();

        /** Installs a device into a slot, replacing any device already in that slot. */
        public void install(SlotKey slot, Device device) {
            bySlot.put(slot, device);
        }

        /** Returns the device installed in the given slot, or null if the slot is empty. */
        public Device resolve(SlotKey slot) {
            return bySlot.get(slot);
        }

        /** Returns the slots that currently hold a device. */
        public Set<SlotKey> occupiedSlots() {
            return new HashSet<>(bySlot.keySet());
        }

        public int occupiedSlotCount() {
            return bySlot.size();
        }
    }

    // ------------------------------------------------------------------
    // Tests. Do not change these.
    // ------------------------------------------------------------------

    public static class TestSuite {

        /** A slot can be resolved by an equal key built independently by the caller. */
        @Test
        public void testResolveSlot() {
            FleetRegistry registry = new FleetRegistry();
            registry.install(new SlotKey("WAREHOUSE-A", 4),
                    new Device("D-1", "SENSOR", "WAREHOUSE-A", "ACTIVE"));

            // The technician builds their own key from the location and slot number.
            Device found = registry.resolve(new SlotKey("WAREHOUSE-A", 4));

            assertNotNull("device should be found in slot WAREHOUSE-A/4", found);
            assertEquals("D-1", found.deviceId);
        }

        /** A slot that has nothing installed resolves to null. */
        @Test
        public void testResolveEmptySlot() {
            FleetRegistry registry = new FleetRegistry();
            registry.install(new SlotKey("WAREHOUSE-A", 4),
                    new Device("D-1", "SENSOR", "WAREHOUSE-A", "ACTIVE"));

            assertNull("nothing is installed in slot WAREHOUSE-B/4",
                    registry.resolve(new SlotKey("WAREHOUSE-B", 4)));
            assertNull("nothing is installed in slot WAREHOUSE-A/9",
                    registry.resolve(new SlotKey("WAREHOUSE-A", 9)));
        }

        /** Installing into an occupied slot replaces the occupant rather than duplicating it. */
        @Test
        public void testInstallReplacesOccupant() {
            FleetRegistry registry = new FleetRegistry();
            registry.install(new SlotKey("WAREHOUSE-A", 4),
                    new Device("D-1", "SENSOR", "WAREHOUSE-A", "ACTIVE"));
            registry.install(new SlotKey("WAREHOUSE-A", 4),
                    new Device("D-2", "CAMERA", "WAREHOUSE-A", "REGISTERED"));

            assertEquals(1, registry.occupiedSlotCount());
            assertEquals("D-2", registry.resolve(new SlotKey("WAREHOUSE-A", 4)).deviceId);
        }

        /** The set of occupied slots deduplicates equal keys. */
        @Test
        public void testOccupiedSlots() {
            FleetRegistry registry = new FleetRegistry();
            registry.install(new SlotKey("WAREHOUSE-A", 4),
                    new Device("D-1", "SENSOR", "WAREHOUSE-A", "ACTIVE"));
            registry.install(new SlotKey("WAREHOUSE-B", 1),
                    new Device("D-3", "GATEWAY", "WAREHOUSE-B", "ACTIVE"));

            Set<SlotKey> slots = registry.occupiedSlots();
            assertEquals(2, slots.size());
            assertTrue("WAREHOUSE-B/1 should be reported as occupied",
                    slots.contains(new SlotKey("WAREHOUSE-B", 1)));
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
