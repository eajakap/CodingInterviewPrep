package org.educative.cip.m2.merge_intervals;

import java.util.Arrays;
import java.util.Collections;

/*
 * Time Complexity: O(n log n) - Sorting the start and end times takes O(n log n) time.
 * Space Complexity: O(n) - We use additional space to store the start and end times.
 */
public class MinimumMeetingRooms {

    public int minMeetingRooms(int[][] intervals) {
        int n = intervals.length;

        int[] starts = new int[n];
        int[] ends = new int[n];

        for (int i = 0; i < n; i++) {
            starts[i] = intervals[i][0];
            ends[i] = intervals[i][1];
        }

        Arrays.sort(starts);
        Arrays.sort(ends);

        int rooms = 0;
        int endPtr = 0;

        for (int startPtr = 0; startPtr < n; startPtr++) {
            // If the current meeting starts before the earliest ending meeting ends,
            // we need a new room.
            if (starts[startPtr] < ends[endPtr]) {
                rooms++;
            } else {
                // Otherwise, one meeting ended → free a room.
                endPtr++;
            }
        }

        return rooms;
    }

    public static void main(String[] args) {
        MinimumMeetingRooms solution = new MinimumMeetingRooms();
        // Driver code
        int[][][] inputMeetings = {
                {{5, 6}, {9, 11}, {1, 3}},
                {{0, 30}, {5, 10}, {15, 20}},
                {{2, 4}, {5, 5}},
                {{1, 100000}},
                {{361, 570}, {420, 1225}, {72, 144}, {987, 1444}},
                {{1, 2}, {3, 4}, {5, 6}, {7, 8}, {9, 10}, {11, 12}}
        };

        for (int i = 0; i < inputMeetings.length; i++) {
            System.out.println((i + 1) + "\tmeetings: " + Arrays.deepToString(inputMeetings[i]));
            System.out.println("\tMinimum meeting rooms: " + solution.minMeetingRooms(inputMeetings[i]));
            System.out.println(String.join("", Collections.nCopies(100, "-")));
        }
    }

}
