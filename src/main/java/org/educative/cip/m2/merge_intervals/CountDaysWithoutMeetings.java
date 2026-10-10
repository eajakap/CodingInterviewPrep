package org.educative.cip.m2.merge_intervals;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 * Count Days Without Meetings
 * Problem: Given the total number of working days (numbered 1 to days) and a list of inclusive
 * meeting intervals [start, end], return the number of days on which no meeting is scheduled.
 * Meetings may overlap.
 *
 * Approach: Sort the meetings by start time, merge overlapping intervals while scanning,
 * sum the days covered by the merged intervals, and subtract that from the total days.
 *
 * Time Complexity: O(n log n) - We sort the meetings based on their start time, which takes O(n log n) time.
 * Space Complexity: O(1) - We use a constant amount of space for variables (ignoring the sort).
 */
public class CountDaysWithoutMeetings {

    /**
     * Holds the solution and driver code for the problem.
     */
    static class Solution {
        /**
         * Counts the days on which the employee has no meetings.
         * Note: the meetings array is sorted in place by start time, and it must contain at least one meeting.
         *
         * @param days     the total number of days available
         * @param meetings inclusive meeting intervals as {@code [start, end]} pairs, possibly overlapping
         * @return the number of days with no meeting scheduled
         */
        public int countDays(int days, int[][] meetings) {
            // Sort the meetings based on their start time to process them in order - O(n log n)
            Arrays.sort(meetings, Comparator.comparingInt(a -> a[0]));

            // Initialize a variable with 0 to count the number of days when the employee has meetings scheduled
            int occupied = 0;

            // Initialize two variables with the first meeting’s start and end times
            int start = meetings[0][0], end = meetings[0][1];

            // Iterate through the remaining meetings
            for (int i = 1; i < meetings.length; i++) {
                // If a current meeting overlaps with the previous meeting
                if (meetings[i][0] <= end) {
                    // Extend the end time to merge it
                    end = Math.max(end, meetings[i][1]);
                } else {
                    // Add the days of the merged meeting
                    occupied += (end - start + 1); // busy days are inclusive, so we add 1

                    // Update start and end for the next interval
                    start = meetings[i][0];
                    end = meetings[i][1];
                }
            }

            // Add the days of the last merged meeting
            occupied += (end - start + 1); // busy days are inclusive, so we add 1

            // Return the free days
            return days - occupied;
        }

        /**
         * Driver code that runs sample inputs through {@link #countDays(int, int[][])} and prints the results.
         *
         * @param args unused
         */
        public static void main(String[] args) {
            int[] inputDays = {12, 6, 100000, 3136, 786};
            int[][][] inputMeetings = {
                    {{5, 6}, {9, 11}, {1, 3}},
                    {{2, 4}, {5, 5}},
                    {{1, 100000}},
                    {{361, 570}, {420, 1225}, {72, 144}, {987, 1444}},
                    {{1, 2}, {3, 4}, {5, 6}, {7, 8}, {9, 10}, {11, 12}}
            };

            Solution sol = new Solution();

            for (int i = 0; i < inputDays.length; i++) {
                System.out.println((i + 1) + ".\tdays: " + inputDays[i]);
                System.out.println("\tmeetings: " + Arrays.deepToString(inputMeetings[i]));
                System.out.println("\n\tNumber of free days: " + sol.countDays(inputDays[i], inputMeetings[i]));
                System.out.println(String.join("", Collections.nCopies(100, "-")));
            }
        }
    }
    /**
     * Entry point that delegates to {@link Solution#main(String[])}.
     *
     * @param args unused
     */
    public static void main(String[] args) {
        Solution.main(args);
    }
}
