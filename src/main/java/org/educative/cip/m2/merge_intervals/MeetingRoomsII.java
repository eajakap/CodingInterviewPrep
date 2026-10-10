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
    public enum SolutionApproach {
        SORT_AND_HEAP,
        SORT_AND_ARRAYS
    }

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

    private static int findSetsUsingArrays(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return 0;
        }

        int n = intervals.length;
        int[] startIntervals = new int[n];
        int[] endIntervals = new int[n];

        for (int i = 0; i < n; i++) {
            startIntervals[i] = intervals[i][0];
            endIntervals[i] = intervals[i][1];
        }

        Arrays.sort(startIntervals);
        Arrays.sort(endIntervals);

        int rooms = 0;
        int endPtr = 0;

        for (int startPtr = 0; startPtr < n; startPtr++) {
            // If the current meeting starts before the earliest ending meeting ends,
            // we need a new room.
            if (startIntervals[startPtr] < endIntervals[endPtr]) {
                rooms++;
            } else {
                // Otherwise, one meeting ended → free a room.
                endPtr++;
            }
        }

        return rooms;
    }

    public static int findSets(int[][] intervals, SolutionApproach approach) {
        if (approach == SolutionApproach.SORT_AND_ARRAYS) {
            return findSetsUsingArrays(intervals);
        }
        return findSetsUsingHeap(intervals);
    }

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
