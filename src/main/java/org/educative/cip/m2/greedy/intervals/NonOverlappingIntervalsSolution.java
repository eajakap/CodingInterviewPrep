package org.educative.cip.m2.greedy.intervals;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Greedy Pattern: Interval Scheduling
 * What is an interval:
 * An interval is a range of time between a start and an end point. Example: [start, end]
 * Greedy idea: Always choose the next interval that ends the earliest and is compatible with the previously selected intervals.
 * This approach ensures that we leave as much room as possible for future intervals, maximizing the number of non-overlapping intervals we can select.
 * Two intervals that only touch (one ends exactly when the next starts) do not overlap.
 *
 * Problems covered:
 * 1. Maximum number of non-overlapping intervals (maxIntervals).
 * 2. Minimum number of intervals to remove so the rest do not overlap (eraseOverlapIntervals).
 *    This is the same greedy run: the intervals removed are the ones not kept,
 *    so the answer is always intervals.length - maxIntervals(intervals).
 *
 * Time complexity: O(n log n), where n is the number of intervals. This is due to the sorting step, which dominates the overall time complexity.
 * Space complexity: O(n) in the worst case, for the temporary storage Arrays.sort (TimSort) uses on an object array.
 * The selection loop itself uses O(1) extra space.
 */
public class NonOverlappingIntervalsSolution {

    /**
     * Returns the maximum number of intervals that can be selected without any two overlapping.
     *
     * Steps:
     * 1. Sort the intervals by end time.
     * 2. Walk through them, keeping the end time of the last selected interval.
     * 3. Select an interval if it starts at or after that end time.
     *
     * Note: the intervals array is sorted in place, so the caller's array is reordered.
     *
     * @param intervals a 2D array where each interval is an array of two integers [start, end]
     * @return the maximum number of non-overlapping intervals
     */
    public static int maxIntervals(int[][] intervals) {

        // Sort by end time - interval[start, end] - so the earliest-ending interval comes first
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[1]));

        // count: number of intervals selected so far
        // lastEnd: end time of the most recently selected interval.
        // It starts at Integer.MIN_VALUE so the first interval is always selected.
        int count = 0;
        int lastEnd = Integer.MIN_VALUE;

        for (int[] interval : intervals) {

            int start = interval[0];
            int end = interval[1];

            if (start >= lastEnd) {
                // No overlap with the last selected interval: select this one.
                // It ends earliest among the remaining compatible intervals,
                // leaving the most room for the ones that follow.
                count++;
                lastEnd = end;
            }
            // Otherwise it starts before the last selected interval ends,
            // so it overlaps and is skipped
        }

        // count is the size of the largest set of non-overlapping intervals
        return count;
    }

    /**
     * Returns the minimum number of intervals to remove so that the remaining intervals do not overlap.
     *
     * Steps:
     * 1. Sort the intervals by end time.
     * 2. Keep each interval that starts at or after the end of the last kept interval.
     * 3. Count every interval that overlaps the last kept interval as removed.
     *
     * Keeping the earliest-ending interval keeps as many intervals as possible,
     * which means removing as few as possible.
     *
     * Note: the intervals array is sorted in place, so the caller's array is reordered.
     *
     * @param intervals a 2D array where each interval is an array of two integers [start, end]
     * @return the minimum number of intervals to remove
     */
    public static int eraseOverlapIntervals(int[][] intervals) {

        // Sort by end time - interval[start, end] - so the earliest-ending interval comes first
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[1]));

        // count: number of intervals removed so far
        // lastEnd: end time of the most recently kept interval.
        // It starts at Integer.MIN_VALUE so the first interval is always kept.
        int count = 0;
        int lastEnd = Integer.MIN_VALUE;

        for (int[] interval : intervals) {

            int start = interval[0];
            int end = interval[1];

            if (start >= lastEnd) {
                // No overlap with the last kept interval: keep this one
                lastEnd = end;
            } else {
                // Overlaps the last kept interval: remove this one.
                // The kept interval ends no later than this one (the list is sorted by end),
                // so removing this one leaves more room for the intervals that follow.
                count++;
            }
        }

        // count is the fewest removals needed to leave no overlaps
        return count;
    }

    /**
     * Driver code: runs {@link #maxIntervals(int[][])} and {@link #eraseOverlapIntervals(int[][])}
     * on a sample input and prints the results.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        // Expected: keep [1, 3], [3, 5], [6, 8] (3 intervals) and remove [2, 4], [7, 9] (2 intervals).
        // maxIntervals sorts this array in place; that does not change the second result,
        // because eraseOverlapIntervals sorts it again by end time anyway.
        int[][] intervals = {{1, 3}, {2, 4}, {3, 5}, {6, 8}, {7, 9}};
        System.out.println("Maximum number of non-overlapping intervals: " + maxIntervals(intervals));
        System.out.println("Minimum number of intervals to remove: " + eraseOverlapIntervals(intervals));
    }
}

/**
 * Notes:
 * Recognition Signals:
 * 1. intervals + maximum number + non-overlapping
 * 2. minimum number of intervals you must remove
 *
 * Mental Template:
 * Intervals?
 *      ↓
 * Sort by END time
 *      ↓
 * Keep track of lastEnd
 *      ↓
 * For each interval:
 *      ↓
 * start >= lastEnd ?
 *    /          \
 *  YES           NO
 *   ↓             ↓
 * Take           Skip
 *   ↓
 * lastEnd = end
 *
 * Interview Cheat Sheet:
 * Maximum non-overlapping intervals
 *         ↓
 * Sort by END
 *
 * Minimum intervals to remove
 *         ↓
 * Sort by END
 *
 * Maximum activities
 *         ↓
 * Sort by END
 */