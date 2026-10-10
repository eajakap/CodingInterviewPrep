package org.educative.cip.m2.merge_intervals;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * Problem:
 * Explore how to solve the Meeting Rooms II problem by analyzing meeting intervals with start and exclusive end times.
 * Understand key interval patterns and develop an efficient O(n log n) approach for allocating meeting rooms.
 * Practice applying these patterns to optimize scheduling and resource management.
 *Steatement:
 * Given an array of meeting time intervals consisting of start and end times [[s1,e1],[s2,e2],...] (si < ei),
 * find the minimum number of conference rooms required.
 *
 * Example 1:
 * Input: [[0, 30],[5, 10],[15, 20]]
 * Output: 2
 *
 * Example 2:
 * Input: [[7,10],[2,4]]
 * Output: 1
 * Constraints:
 * 1 <= intervals.length <= 10^3
 * 0 <= start < end <= 10^6
 */
public class MeetingRoomsII {
    /**
     * Available strategies for computing the minimum number of meeting rooms.
     */
    public enum SolutionApproach {
        SORT_AND_HEAP,
        SORT_AND_ARRAYS
    }

    /**
     * Approach 1: Using a Min Heap (Priority Queue)
     * Time Complexity: O(n log n) - Sorting the intervals takes O(n log n) and adding/removing from the heap takes O(log n).
     * Space Complexity: O(n) - In the worst case, all meetings overlap and we need to store all end times in the heap.
     *
     * @param intervals meeting intervals as {@code [start, end)} pairs; sorted in place by start time
     * @return the minimum number of rooms required, or 0 if there are no meetings
     */
    private static int findSetsUsingHeap(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return 0;
        }

        // Sort the intervals by start time
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));

        // Use a min heap to track the minimum end time of merged intervals
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        // Add the first meeting's end time to the heap
        minHeap.add(intervals[0][1]);

        for (int i = 1; i < intervals.length; i++) {
            // If the current meeting starts after the earliest ended meeting, remove it from the heap
            if (intervals[i][0] >= minHeap.peek()) {
                minHeap.poll();
            }
            // Add the current meeting's end time to the heap
            minHeap.add(intervals[i][1]);
        }

        // The size of the heap is the number of rooms required
        return minHeap.size();
    }

    /**
     * Approach 2: Using Two Pointers and Sorted Arrays
     * Time Complexity: O(n log n) - Sorting the start and end times takes O(n log n).
     * Space Complexity: O(n) - We need to store the start and end times in separate arrays.
     *
     * @param intervals meeting intervals as {@code [start, end)} pairs; not modified
     * @return the minimum number of rooms required, or 0 if there are no meetings
     */
    private static int findSetsUsingArrays(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return 0;
        }

        int n = intervals.length;
        int[] startIntervals = new int[n];
        int[] endIntervals = new int[n];
        // populate the start and end times
        for (int i = 0; i < n; i++) {
            startIntervals[i] = intervals[i][0];
            endIntervals[i] = intervals[i][1];
        }
        // sort the start and end times
        Arrays.sort(startIntervals);
        Arrays.sort(endIntervals);
        // two pointers to traverse the start and end times
        int rooms = 0;
        int endPtr = 0;
        // iterate through the start times
        for (int startPtr = 0; startPtr < n; startPtr++) {
            // If the current meeting starts before the earliest ending meeting ends,
            // we need a new room.
            if (startIntervals[startPtr] < endIntervals[endPtr]) {
                // A new meeting has started before the earliest meeting ended → need a new room.
                rooms++;
            } else {
                // Otherwise, one meeting ended → free a room.
                endPtr++;
            }
        }
        // The number of rooms needed is the maximum number of overlapping meetings at any point in time.
        return rooms;
    }

    /**
     * Finds the minimum number of conference rooms needed to hold all meetings.
     *
     * @param intervals meeting intervals as {@code [start, end)} pairs
     * @param approach  the strategy to use; defaults to the heap approach unless
     *                  {@link SolutionApproach#SORT_AND_ARRAYS} is given
     * @return the minimum number of rooms required, or 0 if there are no meetings
     */
    public static int findSets(int[][] intervals, SolutionApproach approach) {
        if (approach == SolutionApproach.SORT_AND_ARRAYS) {
            return findSetsUsingArrays(intervals);
        }
        return findSetsUsingHeap(intervals);
    }

    /**
     * Driver code that runs both approaches on sample meeting schedules and prints the results.
     *
     * @param args unused
     */
    public static void main(String[] args) {
        int[][][] scheduleMeetings = {
                {{0, 10}, {2, 10}, {11, 30}},
                {{3, 7}, {2, 12}, {10, 20}, {8, 24}},
                {{1, 9}, {5, 8}, {4, 14}, {3, 10}, {11, 25}},
                {{1, 4}, {3, 8}, {8, 11}, {3, 17}, {9, 15}, {16, 18}},
                {{4, 12}, {5, 11}, {4, 9}, {2, 12}, {9, 22}}
        };

        for (int i = 0; i < scheduleMeetings.length; i++) {
            System.out.println((i + 1) + ".\tScheduled meetings: " + Arrays.deepToString(scheduleMeetings[i]));
            System.out.println("\tRooms required: Using Approach - Sort and Arrays: " + findSets(scheduleMeetings[i], SolutionApproach.SORT_AND_ARRAYS));
            System.out.println("\tRooms required: Using Approach - Sort and Heap: " + findSets(scheduleMeetings[i], SolutionApproach.SORT_AND_HEAP));
            System.out.println(new String(new char[100]).replace('\0', '-'));
        }
    }
}
