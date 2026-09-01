package org.educative.cip.m2.merge_intervals;

import java.util.Arrays;

/*  Time Complexity: O(n log n) - The sorting step takes O(n log n) time, and the merging step takes O(n) time, where n is the number of intervals.
 *  Space Complexity: O(n) - In the worst case, we may need to store all intervals in the merged array.
*/
public class MergeIntervals {
    public static int[][] mergeIntervals(int[][] intervals) {
        // Replace this placeholder return statement with your code
        if  (intervals.length == 0) {
            return new int[][]{};
        }
        // sort the intervals based on the start time
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
        System.out.println("\tSorted Intervals: " + Arrays.deepToString(intervals));

        // create a new array to hold the merged intervals
        int[][] merged = new int[intervals.length][2];
        int index = 0;

        // save the first interval
        merged[index] = intervals[0];

        // iterate through the intervals
        for (int i = 1; i < intervals.length; i++) {
            // if the current interval overlaps with the previous interval, merge them
            if (merged[index][1] >= intervals[i][0]) { // current start interval is greater than or equal to the previous end interval
                merged[index][1] = Math.max(merged[index][1], intervals[i][1]); // end time of the merged interval is the maximum of the two end times
            } else {
                // if they don't overlap, move to the next interval
                index++;
                merged[index] = intervals[i];
            }
        }

        // return the merged intervals
        return Arrays.copyOf(merged, index + 1);
    }

    public static void main(String[] args) {
        int[][][] allIntervals = {
                { {3, 7}, {1, 5}, {4, 6} },
                { {1, 5}, {6, 8}, {4, 6}, {11, 15} },
                { {3, 7}, {10, 12}, {6, 8}, {11, 15} },
                { {1, 5} },
                { {1, 9}, {4, 4}, {3, 8} },
                { {1, 2}, {8, 8}, {3, 4} },
                { {1, 5}, {1, 3} },
                { {1, 5}, {6, 9} },
                { {0, 0}, {1, 18}, {1, 3} }
        };

        for (int i = 0; i < allIntervals.length; i++) {
            System.out.println((i + 1) + ".\tIntervals to merge: " + Arrays.deepToString(allIntervals[i]));
            mergeIntervals(allIntervals[i]);
            System.out.println(new String(new char[100]).replace('\0', '-'));
        }
    }

}
