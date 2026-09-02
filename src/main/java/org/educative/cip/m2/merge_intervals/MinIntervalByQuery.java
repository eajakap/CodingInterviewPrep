package org.educative.cip.m2.merge_intervals;

import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;

/*
 * You are given a list of intervals, each represented as [start, end], and a list of queries.
 * Each query is a single integer q. For each query, return the length of the smallest interval from the list that contains q. If no interval contains q, return -1 for that query.
    Input:

    intervals: List of integer arrays, where each array is of size 2: [start, end]
    queries: List of integers
    1 <= intervals.length, queries.length <= 10^5
    -10^9 <= start <= end <= 10^9
    -10^9 <= q <= 10^9
    Output:

    List of integers, where the i-th value is the length of the smallest interval containing queries[i], or -1 if none.
    Example:
    Input:
    intervals = [[1,4],[2,4],[3,6],[2,8]]
    queries = [2,3,4,5]
    Output: [3,2,2,4]

    Query 2: intervals [1,4], [2,4], [2,8] contain 2; smallest length is 3 ([2,4]).
    Query 3: intervals [1,4], [2,4], [3,6], [2,8] contain 3; smallest is 2 ([3,6]).
    Query 4: intervals [1,4], [2,4], [3,6], [2,8] contain 4; smallest is 2 ([3,6]).
    Query 5: only [3,6], [2,8] contain 5; smallest is 4 ([3,6]).
 */
public class MinIntervalByQuery {

    /*
     * Time Complexity: O(n log n + m log m) - Sorting intervals takes O(n log n),
     * sorting queries takes O(m log m), and processing each query with the priority queue takes O(log n) for each of the m queries.
     * Space Complexity: O(n + m) - We store the intervals in a priority queue and the queries in an array, which takes O(n + m) space.
     */
    public int[] minInterval(int[][] intervals, int[] queries) {
        int n = queries.length;
        int[] result = new int[n];  // Initialize result array - copies the minimum length of intervals matching its query index

        // Sort intervals by start
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);

        // Pair queries with original index
        int[][] q = new int[n][2];
        for (int i = 0; i < n; i++) {
            q[i][0] = queries[i];
            q[i][1] = i;
        }

        // Sort queries by value
        Arrays.sort(q, (a, b) -> a[0] - b[0]);

        // Min-heap: [interval length, end]
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);

        int i = 0;
        for (int[] query : q) {
            int qVal = query[0];
            int qIdx = query[1];

            // Add all intervals whose start <= q
            while (i < intervals.length && intervals[i][0] <= qVal) {
                int start = intervals[i][0];
                int end = intervals[i][1];
                pq.offer(new int[]{end - start + 1, end});
                i++;
            }

            // Remove intervals whose end < q
            while (!pq.isEmpty() && pq.peek()[1] < qVal) {
                pq.poll();
            }

            // Smallest interval containing q
            result[qIdx] = pq.isEmpty() ? -1 : pq.peek()[0];
        }

        return result;
    }

    // Driver code
    public static void main(String[] args) {
        int[][][] intervalsList = new int[][][] {
                { {1, 4}, {2, 4}, {3, 6}, {4, 4} },
                { {2, 3}, {2, 5}, {1, 8}, {20, 25} },
                { {5, 10} },
                { {1, 10}, {2, 3}, {4, 8}, {6, 6} },
                { {1, 2}, {4, 4}, {7, 9} }
        };

        int[][] queriesList = new int[][] {
                {2, 3, 4, 5},
                {2, 19, 5, 22},
                {4, 5, 7, 10, 11},
                {1, 2, 6, 9, 10},
                {1, 3, 4, 8, 10}
        };
        MinIntervalByQuery sol = new MinIntervalByQuery();

        for (int t = 0; t < intervalsList.length; t++) {
            // Create a copy so the original test data stays unchanged after sorting
            int[][] intervals = new int[intervalsList[t].length][2];
            for (int k = 0; k < intervalsList[t].length; k++) {
                intervals[k][0] = intervalsList[t][k][0];
                intervals[k][1] = intervalsList[t][k][1];
            }

            int[] queries = Arrays.copyOf(queriesList[t], queriesList[t].length);

            int[] result = sol.minInterval(intervals, queries);

            System.out.println((t + 1) + ".\tintervals: " + Arrays.deepToString(intervalsList[t]));
            System.out.println("\tqueries: " + Arrays.toString(queriesList[t]));
            System.out.println("\toutput: " + Arrays.toString(result));
            System.out.println("-".repeat(100));
        }
    }

}
