package drills.citi;/*
We are building drills.citi.Device Fleet, a back-end system that manages the lifecycle of IoT devices across multiple locations.

Definitions:
* A "device" is an object representing a single piece of IoT hardware deployed at a location. A device has:
  - deviceId
  - deviceType
  - location
  - status
  - registeredDay (the day number, relative to a fixed reference point, on which the device was registered into the fleet)
  - nextMaintenanceDay (the day number by which the device's next scheduled maintenance must occur)
  - failureCount (the running count of failures reported by the device since registration)

* A device's status can be one of:
  - REGISTERED
  - ACTIVE
  - SUSPENDED
  - MAINTENANCE_DUE
  - RETIRED

The drills.citi.DeviceLifecycleManager class manages devices.

To begin with, we present you with two tasks:

1-1) Read through and understand the code below. Feel free to run it.
1-2) The test for drills.citi.DeviceLifecycleManager is not passing due to a bug in the code. Make the necessary changes to drills.citi.DeviceLifecycleManager to fix the bug.
*/

//import org.junit.runner.JUnitCore;
//import org.junit.runner.Result;
//import org.junit.runner.notification.Failure;
//import org.junit.Test;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertEquals;
import java.util.*;

public class Device {
    String deviceId;
    String deviceType;
    String location;
    String status;
    int registeredDay;
    int nextMaintenanceDay;
    int failureCount;

    /**
     * Represents a single device with its identifying and lifecycle attributes.
     */
    public Device(
            String deviceId,
            String deviceType,
            String location,
            String status,
            int registeredDay,
            int nextMaintenanceDay,
            int failureCount
    ) {
        this.deviceId = deviceId;
        this.deviceType = deviceType;
        this.location = location;
        this.status = status;
        this.registeredDay = registeredDay;
        this.nextMaintenanceDay = nextMaintenanceDay;
        this.failureCount = failureCount;
    }
}

class DeviceLifecycleManager {
    static final Set<String> SUPPORTED_DEVICE_TYPES = new HashSet<>(
            Arrays.asList("SENSOR", "CAMERA", "GATEWAY")
    );

    static final Set<String> VALID_STATUSES = new HashSet<>(
            Arrays.asList("REGISTERED", "ACTIVE", "SUSPENDED", "MAINTENANCE_DUE", "RETIRED")
    );

    static final Map<String, Set<String>> VALID_TRANSITIONS = new HashMap<>();
    static {
        VALID_TRANSITIONS.put("REGISTERED", new HashSet<>(Arrays.asList("ACTIVE", "RETIRED")));
        VALID_TRANSITIONS.put("ACTIVE", new HashSet<>(Arrays.asList("SUSPENDED", "MAINTENANCE_DUE", "RETIRED")));
        VALID_TRANSITIONS.put("SUSPENDED", new HashSet<>(Arrays.asList("ACTIVE", "RETIRED")));
        VALID_TRANSITIONS.put("MAINTENANCE_DUE", new HashSet<>(Arrays.asList("ACTIVE", "RETIRED")));
        VALID_TRANSITIONS.put("RETIRED", new HashSet<>());
    }

    /**
     * Manages the collection of registered devices, keyed by device ID.
     */
    Map<String, Device> devices = new HashMap<>();

    /**
     * Updates the device's status if the transition is valid, and returns whether the update was applied.
     */
    public boolean updateDeviceStatus(String deviceId, String newStatus) {
        Device device = devices.get(deviceId);

        if (device == null) {
            return false;
        }

        if (!VALID_STATUSES.contains(newStatus)) {
            return false;
        }

        String currentStatus = device.status;
        // Same-status transition is invalid
        if (currentStatus.equals(newStatus)) {
            return false;
        }

        // Validate allowed transition
        Set<String> allowed = VALID_TRANSITIONS.get(currentStatus);
        if (!allowed.contains(newStatus)) {
            return false;
        }

        // Apply transition
        device.status = newStatus;
        System.out.println(device.status);
        return true;
    }
}

//public class Solution {

//    public static class TestSuite {
//
//        /**
//         * Test updateDeviceStatus.
//         */
//        @Test
//        public void testUpdateDeviceStatus1() {
//            System.out.println("Running testUpdateDeviceStatus1");
//
//            // ACTIVE -> SUSPENDED: valid transition.
//            drills.citi.DeviceLifecycleManager managerA = new drills.citi.DeviceLifecycleManager();
//            managerA.devices.put(
//                    "D-1",
//                    new drills.citi.Device("D-1", "SENSOR", "WAREHOUSE-A", "ACTIVE", 10, 50, 1)
//            );
//            boolean resultA = managerA.updateDeviceStatus("D-1", "SUSPENDED");
//            assertTrue(resultA);
//            assertEquals("SUSPENDED", managerA.devices.get("D-1").status);
//
//            // MAINTENANCE_DUE -> ACTIVE.
//            drills.citi.DeviceLifecycleManager managerB = new drills.citi.DeviceLifecycleManager();
//            managerB.devices.put(
//                    "D-3",
//                    new drills.citi.Device("D-3", "GATEWAY", "WAREHOUSE-B", "MAINTENANCE_DUE", 1, 15, 2)
//            );
//            boolean resultB = managerB.updateDeviceStatus("D-3", "ACTIVE");
//            assertTrue(resultB);
//            assertEquals("ACTIVE", managerB.devices.get("D-3").status);
//
//            // REGISTERED -> RETIRED.
//            drills.citi.DeviceLifecycleManager managerC = new drills.citi.DeviceLifecycleManager();
//            managerC.devices.put(
//                    "D-4",
//                    new drills.citi.Device("D-4", "SENSOR", "WAREHOUSE-B", "REGISTERED", 3, 30, 0)
//            );
//            boolean resultC = managerC.updateDeviceStatus("D-4", "RETIRED");
//            assertTrue(resultC);
//            assertEquals("RETIRED", managerC.devices.get("D-4").status);
//
//            // SUSPENDED -> ACTIVE.
//            drills.citi.DeviceLifecycleManager managerD = new drills.citi.DeviceLifecycleManager();
//            managerD.devices.put(
//                    "D-5",
//                    new drills.citi.Device("D-5", "CAMERA", "WAREHOUSE-A", "SUSPENDED", 7, 40, 1)
//            );
//            boolean resultD = managerD.updateDeviceStatus("D-5", "ACTIVE");
//            assertTrue(resultD);
//            assertEquals("ACTIVE", managerD.devices.get("D-5").status);
//        }
//
//        /**
//         * Test updateDeviceStatus.
//         */
//        @Test
//        public void testUpdateDeviceStatus2() {
//            System.out.println("Running testUpdateDeviceStatus2");
//
//            // ACTIVE -> REGISTERED: invalid transition.
//            drills.citi.DeviceLifecycleManager managerA = new drills.citi.DeviceLifecycleManager();
//            managerA.devices.put(
//                    "D-1",
//                    new drills.citi.Device("D-1", "SENSOR", "WAREHOUSE-A", "ACTIVE", 10, 50, 1)
//            );
//            boolean resultA = managerA.updateDeviceStatus("D-1", "REGISTERED");
//            assertFalse(resultA);
//            System.out.println("resultA: " + resultA);
//            assertEquals("ACTIVE", managerA.devices.get("D-1").status);
//
//            // RETIRED device.
//            drills.citi.DeviceLifecycleManager managerB = new drills.citi.DeviceLifecycleManager();
//            managerB.devices.put(
//                    "D-2",
//                    new drills.citi.Device("D-2", "CAMERA", "WAREHOUSE-A", "RETIRED", 5, 20, 4)
//            );
//            boolean resultB = managerB.updateDeviceStatus("D-2", "ACTIVE");
//            assertFalse(resultB);
//            assertEquals("RETIRED", managerB.devices.get("D-2").status);
//            // System.out.println("resultB: ", resultB);
//
//            // Same-status transition.
//            drills.citi.DeviceLifecycleManager managerC = new drills.citi.DeviceLifecycleManager();
//            managerC.devices.put(
//                    "D-1",
//                    new drills.citi.Device("D-1", "SENSOR", "WAREHOUSE-A", "ACTIVE", 10, 50, 1)
//            );
//            boolean resultC = managerC.updateDeviceStatus("D-1", "ACTIVE");
//            assertFalse(resultC);
//            assertEquals("ACTIVE", managerC.devices.get("D-1").status);
//            // System.out.println("resultC: ", resultC);
//
//        }
//
//        /**
//         * Test updateDeviceStatus.
//         */
//        @Test
//        public void testUpdateDeviceStatus3() {
//            System.out.println("Running testUpdateDeviceStatus3");
//
//            // Unknown device ID.
//            drills.citi.DeviceLifecycleManager managerA = new drills.citi.DeviceLifecycleManager();
//            boolean resultA = managerA.updateDeviceStatus("UNKNOWN", "ACTIVE");
//            System.out.println("resultA: " + resultA);
//            assertFalse(resultA);
//
//            // Unrecognized status.
//            drills.citi.DeviceLifecycleManager managerB = new drills.citi.DeviceLifecycleManager();
//            managerB.devices.put(
//                    "D-1",
//                    new drills.citi.Device("D-1", "SENSOR", "WAREHOUSE-A", "ACTIVE", 10, 50, 1)
//            );
//            boolean resultB = managerB.updateDeviceStatus("D-1", "BROKEN");
//            System.out.println("resultB: " + resultB);
//            assertFalse(resultB);
//            System.out.println(managerB.devices.get("D-1").status);
//            assertEquals("ACTIVE", managerB.devices.get("D-1").status);
//        }
//    }
//
//    public static void main(String[] argv) {
//        Result result = JUnitCore.runClasses(TestSuite.class);
//
//        for (Failure failure : result.getFailures()) {
//            System.out.println(failure.getTrace());
//        }
//
//        if (result.wasSuccessful()) {
//            System.out.println("All tests passed.");
//        } else {
//            System.out.println("Some tests failed.");
//        }
//    }
//}