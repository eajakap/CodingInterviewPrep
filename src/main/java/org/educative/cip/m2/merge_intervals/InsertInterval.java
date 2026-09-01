package org.educative.cip.m2.merge_intervals;

import java.util.*;

public class InsertInterval {


    static class Solution {

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
                    {5, 7}, {8, 9}, {10, 12}, {1, 3}, {1, 10}
            };

            int[][][] existingIntervals = {
                    {{1, 2}, {3, 5}, {6, 8}},
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
