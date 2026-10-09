package org.educative.cip.m2.merge_intervals;

import java.util.*;

/**
 * Given a set of non-overlapping intervals, insert a new interval into the intervals (merge if necessary).
 * You may assume that the intervals were initially sorted according to their start times.
 * Time Complexity: O(n), where n is the number of intervals. We traverse the list of intervals once.
 * Space Complexity: O(n), where n is the number of intervals. In the worst case, we may need to store all the intervals in the output list.
 */
public class InsertInterval {


    static class Solution {

        /**
         * Inserts a new interval into a list of non-overlapping intervals and merges if necessary.
         *
         * @param intervals    A 2D array representing the existing non-overlapping intervals.
         * @param newInterval  An array representing the new interval to be inserted.
         * @return A 2D array representing the updated list of intervals after insertion and merging.
         */
        public static int[][] insertInterval(int[][] intervals, int[] newInterval) {
            List<int[]> output = new ArrayList<>();
            int i = 0;

            // Add intervals before the new interval
            while (i < intervals.length && intervals[i][0] < newInterval[0]) {
                output.add(intervals[i]);
                i++;
            }

            // Merge or add the new interval - check for overlapping with the last interval in the output
            if (output.isEmpty() || output.get(output.size() - 1)[1] < newInterval[0]) {
                // No overlap, add the new interval
                output.add(newInterval);
            } else {
                // Overlap, merge with the last interval in the output
                output.get(output.size() - 1)[1] = Math.max(output.get(output.size() - 1)[1], newInterval[1]);
            }

            // Merge remaining intervals
            while (i < intervals.length) {
                // check for overlap with the last interval in the output
                int[] last = output.get(output.size() - 1);
                if (last[1] < intervals[i][0]) {
                    // No overlap, add the current interval
                    output.add(intervals[i]);
                } else {
                    // Overlap, merge with the last interval in the output
                    last[1] = Math.max(last[1], intervals[i][1]);
                }
                i++;
            }

            return output.toArray(new int[output.size()][]);
        }

        public static void main(String[] args) {
            int[][] newIntervals = {
                    {5, 7}, {6,8}, {8, 9}, {10, 12}, {1, 3}, {1, 10}
            };

            int[][][] existingIntervals = {
                    {{1, 2}, {3, 5}, {6, 8}},
                    {{1, 3}, {5, 7}, {9, 10}},
                    {{1, 3}, {5, 7}, {10, 12}},
                    {{8, 10}, {12, 15}},
                    {{5, 7}, {8, 9}},
                    {{3, 5}}
            };

            for (int i = 0; i < newIntervals.length; i++) {
                System.out.println((i + 1) + ".\tExisting intervals: " + Arrays.deepToString(existingIntervals[i]));
                System.out.println("\tNew interval: " + Arrays.toString(newIntervals[i]));
                int[][] output = insertInterval(existingIntervals[i], newIntervals[i]);
                System.out.println("\tUpdated intervals: " + Arrays.deepToString(output));
                System.out.println(String.join("", Collections.nCopies(100, "-")));
            }
        }
    }

    public static void main(String[] args) {
        Solution.main(args);
    }

}
