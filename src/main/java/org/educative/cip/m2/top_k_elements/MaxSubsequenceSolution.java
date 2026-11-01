package org.educative.cip.m2.top_k_elements;

import java.util.*;

/**
 * Find Subsequence of Length K With the Largest Sum — solved with the Top K Elements pattern.
 * A subsequence of an array is a sequence you get by removing zero or more elements while keeping
 * the remaining elements in the same relative order.
 *
 * Given an integer array {@code nums} and an integer {@code k}, return any subsequence of
 * {@code nums} of length {@code k} that has the largest sum. A subsequence keeps the
 * relative order of the original elements.
 *
 * Constraints:
 * - {@code 1 <= k <= nums.length <= 1000}
 * - {@code -10^5 <= nums[i] <= 10^5}
 *
 * Example 1:
 * Input: nums = [3,4,3,3], k = 2
 * Output: [4,3]
 */
public class MaxSubsequenceSolution {

    /**
     * Returns a length-{@code k} subsequence of {@code nums} with the largest possible sum.
     *
     * Approach: the largest sum comes from the {@code k} largest values. A min-heap of
     * {@code [value, index]} pairs, capped at size {@code k}, keeps the {@code k} largest
     * values seen so far by evicting the smallest whenever it overflows. The surviving pairs
     * are then sorted by their original index to restore subsequence order.
     *
     * Time complexity: O(n log k + k log k) — n heap operations on a heap of size k,
     * plus sorting the k survivors.
     * Space complexity: O(k) — the heap and the result list hold k elements.
     *
     * @param nums the input array
     * @param k    the length of the subsequence to return ({@code 1 <= k <= nums.length})
     * @return the {@code k} largest values of {@code nums}, in their original order
     */
    public static int[] maxSubsequence(int[] nums, int k) {
        // Min-heap ordered by value; each entry is [value, original index]
        // a[0] is the value, a[1] is the index - here we use a lambda to compare by the first element of the array and keep the smallest value on top
        PriorityQueue<int[]> minHeap = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        // Iterate over the input array, keeping only the k largest values in the heap
        for (int i = 0; i < nums.length; i++) {
            // Keep the index alongside the value so the original order can be restored later
            minHeap.offer(new int[]{nums[i], i});

            // Evict the smallest value once the heap exceeds k, keeping only the k largest
            if (minHeap.size() > k) {
                minHeap.poll();
            }
        }

        // Heap order is by value, not position, so copy the pairs out to reorder them
        List<int[]> pairsList = new ArrayList<>(minHeap);
        // Sort the surviving pairs by their original index to restore subsequence order
        // a[1] is the original index, so we sort by that to get the original order of the elements in the subsequence
        pairsList.sort(Comparator.comparingInt(a -> a[1]));  // Sort by the original index

        // Extract just the values, now in subsequence order
        int[] result = new int[k];
        for (int i = 0; i < k; i++) {
            result[i] = pairsList.get(i)[0];
        }

        return result;
    }

    /**
     * Driver code: runs {@link #maxSubsequence(int[], int)} against sample inputs and prints the results.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        int[][] testCases = {
                {3, 4, 3, 3},
                {2, 1, 3, 3},
                {-1, -2, 3, 4},
                {1, 2, 3, 4, 5},
                {9, -1, -3, 8, 7}
        };
        // ks[i] is the subsequence length for testCases[i]
        int[] ks = {2, 2, 3, 3, 2};

        for (int i = 0; i < testCases.length; i++) {
            int[] nums = testCases[i];
            int k = ks[i];

            System.out.println((i + 1) + "\tnums = " + Arrays.toString(nums) + ", k = " + k);
            int[] result = maxSubsequence(nums, k);
            System.out.println("\n\tResult: " + Arrays.toString(result) + "\n");
            // Print a separator line of 100 dashes
            System.out.println(new String(new char[100]).replace('\0', '-'));
        }
    }
}
