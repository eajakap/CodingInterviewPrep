/*
=== DRILL 04 — Maintenance queue: scheduling by due day ===
Focus area: sorted collections, ordering vs. equality
Difficulty: hard — the failure mode is silent data loss

We are building drills.citi.Device Fleet, a back-end system that manages the lifecycle of IoT
devices across multiple locations.

Definitions:
* Every device carries a nextMaintenanceDay: the day number, relative to a fixed
  reference point, by which its next scheduled maintenance must occur.

* MaintenanceQueue holds every device awaiting maintenance, ordered so that the
  technician can repeatedly ask for the next device due. Ordering rules:
  - earlier nextMaintenanceDay is serviced first;
  - when two devices are due on the same day, the one with the lexicographically
    smaller deviceId is serviced first.

* Two devices are the same device only if they have the same deviceId. Many devices
  legitimately share a due day — maintenance windows are scheduled in batches, so a
  whole warehouse often comes due at once. Every scheduled device must be serviced.

Tasks:

4-1) Read through and understand the code below. Feel free to run it.
     Run with:  ./run_junit.sh Drill04_MaintenanceQueue   (from the fleet-drills root)

4-2) The test for MaintenanceQueue is not passing due to a bug in the code. Make the
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

public class Drill04_MaintenanceQueue {

    // ------------------------------------------------------------------
    // Code under test.
    // ------------------------------------------------------------------

    /** Represents a single device with its identifying and lifecycle attributes. */
    static class Device {
        final String deviceId;
        final String deviceType;
        final String location;
        String status;
        int nextMaintenanceDay;

        Device(String deviceId, String deviceType, String location, String status,
               int nextMaintenanceDay) {
            this.deviceId = deviceId;
            this.deviceType = deviceType;
            this.location = location;
            this.status = status;
            this.nextMaintenanceDay = nextMaintenanceDay;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Device)) return false;
            return deviceId.equals(((Device) o).deviceId);
        }

        @Override
        public int hashCode() {
            return deviceId.hashCode();
        }

        @Override
        public String toString() {
            return deviceId + "@day" + nextMaintenanceDay;
        }
    }

    static class MaintenanceQueue {

        /** Earliest due day first. */
        static final Comparator<Device> BY_DUE_DAY =
                Comparator.comparingInt(d -> d.nextMaintenanceDay);

        private final TreeSet<Device> pending = new TreeSet<>(BY_DUE_DAY);

        /** Adds a device to the queue. */
        public void schedule(Device device) {
            pending.add(device);
        }

        /** Moves an already-scheduled device to a new due day. */
        public void reschedule(String deviceId, int newDueDay) {
            Device found = null;
            for (Device device : pending) {
                if (device.deviceId.equals(deviceId)) {
                    found = device;
                    break;
                }
            }
            if (found == null) {
                return;
            }
            pending.remove(found);
            found.nextMaintenanceDay = newDueDay;
            pending.add(found);
        }

        public int size() {
            return pending.size();
        }

        /** Removes and returns the next device due, or null if nothing is pending. */
        public Device pollNextDue() {
            return pending.pollFirst();
        }

        /** Removes every device from the queue, in service order. */
        public List<String> drain() {
            List<String> order = new ArrayList<>();
            Device device;
            while ((device = pollNextDue()) != null) {
                order.add(device.deviceId);
            }
            return order;
        }
    }

    // ------------------------------------------------------------------
    // Tests. Do not change these.
    // ------------------------------------------------------------------

    public static class TestSuite {

        /** Devices due on distinct days are serviced earliest-first. */
        @Test
        public void testDistinctDueDays() {
            MaintenanceQueue queue = new MaintenanceQueue();
            queue.schedule(device("D-3", 45));
            queue.schedule(device("D-1", 10));
            queue.schedule(device("D-2", 30));

            assertEquals(3, queue.size());
            assertEquals("D-1", queue.pollNextDue().deviceId);
            assertEquals("D-2", queue.pollNextDue().deviceId);
            assertEquals("D-3", queue.pollNextDue().deviceId);
            assertNull("queue is drained", queue.pollNextDue());
        }

        /** A batch of devices sharing one maintenance window are all retained. */
        @Test
        public void testBatchSharesDueDay() {
            MaintenanceQueue queue = new MaintenanceQueue();
            queue.schedule(device("D-C", 30));
            queue.schedule(device("D-A", 30));
            queue.schedule(device("D-B", 30));

            assertEquals(3, queue.size());
            assertEquals("D-A", queue.pollNextDue().deviceId);
            assertEquals("D-B", queue.pollNextDue().deviceId);
            assertEquals("D-C", queue.pollNextDue().deviceId);
        }

        /** Mixed due days: day order first, deviceId as the tiebreaker. */
        @Test
        public void testDrainOrder() {
            MaintenanceQueue queue = new MaintenanceQueue();
            queue.schedule(device("D-D", 45));
            queue.schedule(device("D-C", 30));
            queue.schedule(device("D-A", 10));
            queue.schedule(device("D-B", 30));

            assertEquals(Arrays.asList("D-A", "D-B", "D-C", "D-D"), queue.drain());
        }

        /** Re-scheduling the same deviceId updates it rather than duplicating it. */
        @Test
        public void testRescheduleSameDevice() {
            MaintenanceQueue queue = new MaintenanceQueue();
            queue.schedule(device("D-1", 30));
            queue.reschedule("D-1", 12);

            assertEquals(1, queue.size());
            assertEquals(12, queue.pollNextDue().nextMaintenanceDay);
        }


        static Device device(String id, int dueDay) {
            return new Device(id, "SENSOR", "WAREHOUSE-A", "ACTIVE", dueDay);
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
