package org.educative.cip.m2.merge_intervals;

import java.util.Arrays;
import java.util.Comparator;
/*
 * Time Complexity: O(n log n) - Sorting the intervals takes O(n log n) time.
 * Space Complexity: O(1) - We use a constant amount of space for variables.
 */
public class RemoveCoveredIntervals {
    static class Solution {
        public int removeCoveredIntervals(int[][] intervals) {
            // Sort the intervals based on their start time to process them in order - O(n log n)
            Arrays.sort(intervals, Comparator.comparingInt((int[] a) -> a[0]) // Sort by start time - ascending
                    .thenComparingInt(a -> -a[1])); // if equal, Sort by end time descending

            // Initialize count of non-covered intervals and the end of the last added interval
            int count = 0; // To track the number of non-covered intervals
            int prevEnd = 0;  // To track the maximum end seen so far
            int prevStart = 0; // To track the start of the last added interval

            // Iterate through the intervals
            for (int i = 0; i < intervals.length; i++) {
                int start = intervals[i][0];
                int end = intervals[i][1];
                // Explicit coverage check:
                // current interval is covered if:
                // prevStart <= start AND prevEnd > end
                if (prevStart <= start && prevEnd > end) {
                    // The current interval is covered by the previous interval
                    continue;
                }

                // Update the start and end of the last added interval
                prevStart = start;
                prevEnd = end;
                count++;
            }

            // Return the count of non-covered intervals
            return count;
        }

        // Driver code
        public static void main(String[] args) {
            int[][][] testCases = {
                    {{1, 6}, {2, 4}},
                    {{1, 4}, {3, 6}, {2, 8}},
                    {{1, 2}, {1, 4}, {3, 4}},
                    {{1, 10}, {2, 9}, {3, 8}, {4, 7}},
                    {{1, 3}, {4, 6}, {7, 9}},
                    {{1, 5}, {2, 3}, {4, 6}}
            };

            Solution solution = new Solution();

            for (int i = 0; i < testCases.length; i++) {
                int[][] intervals = testCases[i];
                System.out.print((i + 1) + ".\tIntervals: [");
                for (int j = 0; j < intervals.length; j++) {
                    System.out.print("[" + intervals[j][0] + ", " + intervals[j][1] + "]");
                    if (j != intervals.length - 1) System.out.print(", ");
                }
                System.out.println("]");

                int result = solution.removeCoveredIntervals(intervals);
                System.out.println("\tResult: " + result);
                System.out.println(new String(new char[100]).replace("\0", "-"));
            }
        }
    }

    public static void main(String[] args) {
        Solution.main(args);
    }

}
