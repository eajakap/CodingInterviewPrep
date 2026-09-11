package org.educative.cip.m2.sliding_window;

import java.util.Deque;
import java.util.LinkedList;

/**
 * Longest Subarray With Diff At Most Limit
 * Problem: Given an integer array nums and an integer limit,
 *          return the size of the longest continuous subarray such that the absolute difference
 *          between any two elements within that subarray is less than or equal to limit.
 * Example 1:
 * Input: nums = [8,2,4,7], limit = 4
 * Output: 2
 * Explanation: All subarrays are:
 *             [8] with max absolute diff |8-8|=0 <= 4,
 *             [8,2] with max absolute diff |8-2|=6 > 4, [8,2,4] with max absolute diff |8-2|=6 > 4,
 *             [8,2,4,7] with max absolute diff |8-2|=6 > 4, [2] with max absolute diff |2-2|=0 <= 4,
 *             [2,4] with max absolute diff |4-2|=2 <= 4, [2,4,7] with max absolute diff |7-2|=5 > 4,
 *             [4] with max absolute diff |4-4|=0 <= 4, [4,7] with max absolute diff |7-4|=3 <= 4,
 *             [7] with max absolute diff |7-7|=0 <= 4.
 *             Therefore, the longest subarray is either [2,4] or [4,7], both of length 2.
 * Example 2:
 * Input: nums = [10,1,2,4,7,2], limit = 5
 * Output: 4
 * Explanation: The subarray [2,4,7,2] has a maximum absolute difference of |7-2|=5 which is equal to the limit.
 * Example 3:
 * Input: nums = [4,2,2,2,4,4,2,2], limit = 0
 * Output: 3
 * Explanation: The longest subarray is either [2,2,2] or [4,4].
 * Constraints:
 * 1 <= nums.length <= 10^5
 * 1 <= nums[i] <= 10^9
 * 0 <= limit <= 10^9
 *
 */
public class LongestSubarrayWithDiffLimit {

    /**
     * Returns the size of the longest continuous subarray such that the absolute difference
     * between any two elements within that subarray is less than or equal to limit.
     *
     * Steps to solve the problem:
     * 1. Initialize two pointers, left and right, to represent the current window of elements.
     * 2. Use two deques to keep track of the maximum and minimum values in the current window.
     * 3. Expand the window by moving the right pointer and updating the deques with the new element.
     * 4. While the absolute difference between the maximum and minimum values in the current window exceeds limit,
     *    shrink the window by moving the left pointer and updating the deques accordingly.
     * 5. Update the maximum length of valid subarrays by calculating (right - left + 1) for each valid window.
     * 6. Repeat steps 3 to 5 until the right pointer reaches the end of the array.
     * 7. Return the maximum length of valid subarrays after traversing the entire array.
     *
     * Time Complexity: O(n), where n is the length of nums. Each element is added and removed from deques at most once.
     * Space Complexity: O(n), for storing elements in deques.
     *
     * @param nums  the input integer array
     * @param limit the maximum allowed absolute difference between any two elements in a valid subarray
     * @return the size of the longest continuous subarray satisfying the condition
     */
    public int longestSubarray(int[] nums, int limit)
    {
        int left = 0, right = 0;
        int maxLength = 0;
        Deque<Integer> maxDeque = new LinkedList<>();
        Deque<Integer> minDeque = new LinkedList<>();

        while (right < nums.length){
            // Update the max deques - maintain the decreasing order of elements in the max deque [5,4,3,2,1]
            while (!maxDeque.isEmpty() && maxDeque.peekLast() <= nums[right]) {
                // Remove elements from the max deque that are smaller than or equal to the current element
                maxDeque.pollLast();
            }
            // Add the current element to the max deque
            maxDeque.offerLast(nums[right]);

            // Update the min deque - maintain the increasing order of elements in the min deque [1,2,3,4,5]
            while (!minDeque.isEmpty() && minDeque.peekLast() >= nums[right]) {
                // Remove elements from the min deque that are larger than or equal to the current element
                minDeque.pollLast();
            }
            // Add the current element to the min deque
            minDeque.offerLast(nums[right]);

            // Check if the current window is valid
            while (maxDeque.peekFirst() - minDeque.peekFirst() > limit) {
                // Shrink the window from the left
                if (nums[left] == maxDeque.peekFirst()) {
                    maxDeque.pollFirst();
                }
                if (nums[left] == minDeque.peekFirst()) {
                    minDeque.pollFirst();
                }
                left++;
            }

            // Update the maximum length of valid subarrays
            maxLength = Math.max(maxLength, right - left + 1);
            right++;
        }
        return maxLength;
    }

    public static void main(String[] args) {
        LongestSubarrayWithDiffLimit sol = new LongestSubarrayWithDiffLimit();

        int[][][] testNums = {
                {{1, 5, 6, 7, 8, 10, 6}},
                {{3, 3, 3, 3, 3}},
                {{1, 100, 1, 100, 1}},
                {{5, 10, 15, 20, 25}},
                {{1000000000, 1, 1000000000, 1}}
        };
        int[] limits = {4, 0, 99, 10, 999999999};

        for (int i = 0; i < testNums.length; i++) {
            int[] nums = testNums[i][0];
            int limit = limits[i];
            int result = sol.longestSubarray(nums, limit);

            System.out.print((i + 1) + ".\tInput array: [");
            for (int j = 0; j < nums.length; j++) {
                System.out.print(nums[j]);
                if (j + 1 < nums.length) System.out.print(", ");
            }
            System.out.println("]");
            System.out.println("\tTarget: " + limit);
            System.out.println("\tResult: " + result);
            System.out.println("-".repeat(100));
        }
    }
}
