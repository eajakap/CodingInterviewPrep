package org.educative.cip.m2.merge_intervals;

import java.util.Arrays;
import java.util.List;

/**
 * Car Pooling
 * Problem: You are driving a vehicle that has capacity empty seats initially available for passengers.
 *          The vehicle only drives east (i.e., it cannot turn around and drive west.)
 *          Given a list of trips, trip[i] = [numPassengers, startLocation, endLocation] contains information
 *          about the i-th trip: the number of passengers that must be picked up,
 *          and the locations to pick them up and drop them off.
 *          The locations are given as the number of kilometers due east from your vehicle's initial location.
 * Return true if it is possible to pick up and drop off all passengers for all the given trips, or false otherwise.
 * Example 1:
 * Input: trips = [[2,1,5],[3,3,7]], capacity = 4
 * Output: false
 * Explanation: The vehicle cannot pick up 3 passengers at location 3 because it only has 4 empty seats
 *              initially available, and it already picked up 2 passengers at location 1.
 * Example 2:
 * Input: trips = [[2,1,5],[3,3,7]], capacity = 5
 * Output: true
 * Explanation: The vehicle can pick up 2 passengers at location 1 and drop them off at location 5.
 * Example 3:
 * Input: trips = [[3,2,6],[1,4,7],[2,5,8]], capacity = 5
 * Output: false
 * Explanation: At time 4, there will be 3 + 1 + 2 = 6 passengers in the vehicle, which exceeds the capacity.
 * Constraints:
 * 1 <= trips.length <= 1000
 * trips[i].length == 3
 * 1 <= numPassengersi <= 100
 * 0 <= startLocationi < endLocationi <= 1000
 * 1 <= capacity <= 10^5
 */
public class CarPooling {

    public enum SolutionApproach {
        PREFIX_SUM,
        MERGE_INTERVALS
    }

    public boolean carPooling(int[][] trips, int capacity, SolutionApproach approach) {
        switch (approach) {
            case PREFIX_SUM:
                return carPoolingPrefixSum(trips, capacity);
            case MERGE_INTERVALS:
                // Implement the merge intervals approach here
                return false;
            default:
                throw new IllegalArgumentException("Invalid solution approach");
        }
    }
    /**
     * Determines if it is possible to pick up and drop off all passengers for all the given trips without exceeding the vehicle's capacity.
     *
     * Steps to solve the problem:
     * 1. Create an array to track the number of passengers at each location.
     * 2. Iterate through the trips and update the passenger count at the start and end locations.
     * 3. Traverse through the passenger count array to check if at any point the number of passengers exceeds the capacity.
     * 4. If it does, return false; otherwise, return true after checking all locations.
     *
     * Time Complexity: O(n + m), where n is the number of trips and m is the range of locations (up to 1000).
     * Space Complexity: O(m), for storing passenger counts at each location.
     *
     * @param trips    a 2D array where each trip is represented as [numPassengers, startLocation, endLocation]
     * @param capacity the maximum number of passengers that can be in the vehicle at any time
     * @return true if it is possible to pick up and drop off all passengers without exceeding capacity, false otherwise
     */
    public boolean carPoolingPrefixSum(int[][] trips, int capacity)
    {
        int[] passengerCount = new int[1001]; // Array to track the number of passengers at each location
        for (int[] trip : trips) {
            int numPassengers = trip[0];
            int startLocation = trip[1];
            int endLocation = trip[2];
            passengerCount[startLocation] += numPassengers;
            passengerCount[endLocation] -= numPassengers;
        }

        int currentPassengers = 0;
        for (int count : passengerCount) {
            currentPassengers += count;
            if (currentPassengers > capacity) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        List<Object[]> testCases = Arrays.asList(
                new Object[]{ new int[][]{ {2, 1, 5}, {3, 3, 7} }, 4 },
                new Object[]{ new int[][]{ {2, 1, 5}, {3, 3, 7} }, 5 },
                new Object[]{ new int[][]{ {3, 2, 6}, {1, 4, 7}, {2, 5, 8} }, 5 },
                new Object[]{ new int[][]{ {1, 0, 4}, {2, 2, 6}, {3, 5, 8} }, 6 },
                new Object[]{ new int[][]{ {4, 1, 5}, {1, 3, 7}, {2, 6, 8} }, 5 }
        );

        int i = 1;
        for (Object[] testCase : testCases) {
            int[][] trips = (int[][]) testCase[0];
            int capacity = (Integer) testCase[1];
            System.out.print(i + ".\tInput: trips = [");
            for (int j = 0; j < trips.length; j++) {
                System.out.print(Arrays.toString(trips[j]));
                if (j != trips.length - 1) System.out.print(", ");
            }
            System.out.println("], capacity = " + capacity);
            CarPooling obj = new CarPooling();
            boolean result = obj.carPooling(trips, capacity, SolutionApproach.PREFIX_SUM);
            System.out.println("\tCan complete all trips? " + result);
            System.out.println(new String(new char[100]).replace("\0", "-"));
            i++;
        }
    }

}
