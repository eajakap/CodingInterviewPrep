package org.educative.cip.m2.sliding_window;

import java.util.ArrayList;
import java.util.List;

/**
 * Count Subarrays Less Than K
 * Problem: Given an integer array nums and an integer k,
 *          return the number of contiguous subarrays where the
 *          (sum of all the elements in the subarray * window size) is strictly less than k.
 * Example 1:
 * Input: nums = [2,1,4,3,5], k = 10
 * Output: 6
 * Explanation: The contiguous subarrays that satisfy the condition are:
 * [2], [1], [4], [3], [5], [2,1]
 * Example 2:
 * Input: nums = [10,1,2], k = 15
 * Output: 4
 * Explanation: The contiguous subarrays that satisfy the condition are:
 * [10], [1], [2], [1,2]
 * Example 3:
 * Input: nums = [12,2,2,3], k = 18
 * Output: 5
 * Explanation: The contiguous subarrays that satisfy the condition are:
 * [12], [2], [2], [3], [2,2]
 * Constraints:
 * 1 <= nums.length <= 10^3
 * 1 <= nums[i] <= 10^3
 * 1 <= k <= 10^5
 *
 * Steps to solve the problem:
 * 1. Initialize two pointers, left and right, to represent the current window of elements.
 * 2. Initialize a variable to keep track of the current sum of the window and a variable to store the count of valid subarrays.
 * 3. Expand the window by moving the right pointer and adding the current element to the sum.
 * 4. While the current sum multiplied by the window size is greater than or equal to k,
 *    shrink the window by moving the left pointer and subtracting the element at the left pointer from the sum.
 * 5. Update the count of valid subarrays by adding the current window size (right - left + 1) to the result.
 * 6. Repeat steps 3 to 5 until the right pointer reaches the end of the array.
 * 7. Return the count of valid subarrays after traversing the entire array.
 *
 * Time Complexity: O(n), where n is the length of the array. We traverse through the array once.
 * Space Complexity: O(1), as we are using constant space for pointers and counters.
 */
public class CountSubArrayLessThanK {

    /**
     * Counts the number of contiguous subarrays where the (sum of all the elements in the subarray * window size) is strictly less than k.
     * Steps to solve the problem:
     * 1. Initialize two pointers, left and right, to represent the current window of elements.
     * 2. Initialize a variable to keep track of the current sum of the window and a variable to store the count of valid subarrays.
     * 3. Expand the window by moving the right pointer and adding the current element to the sum.
     * 4. While the current sum multiplied by the window size is greater than or equal to k, shrink the window by moving the left pointer and subtracting the element at the left pointer from the sum.
     * 5. Update the count of valid subarrays by adding the current window size (right - left + 1) to the result.
     * 6. Repeat steps 3 to 5 until the right pointer reaches the end of the array.
     * 7. Return the count of valid subarrays after traversing the entire array.
     *
     * @param nums the input array of integers
     * @param k the threshold value for the product of sum and window size
     * @return the count of valid contiguous subarrays
     */
    public long countSubarrays(int[] nums, long k) {
        // k threshold is given as long, but the array elements are int.
        // So, we need to be careful with the multiplication to avoid overflow.
        int n = nums.length;
        int left = 0; // start index of the sliding window
        long runningSum = 0; // This will hold the sum of the current window
        int result = 0; // This will hold the count of valid subarrays (window size)

        for (int right = 0; right < n; right++) {
            runningSum += nums[right];
            // Calculate the score for the current window
            long score = runningSum * (right - left + 1);
            // Shrink the window from the left if the sum * window size exceeds k
            while (score >= k && left <= right) {
                // Shrink the window from the left
                runningSum -= nums[left]; // Remove the leftmost element from the sum
                left++; // Move the left pointer to the right
                score = runningSum * (right - left + 1); // Recalculate the score after shrinking the window
            }

            // At this point, all subarrays ending at 'right' and starting from 'left' to 'right' are valid
            // The number of such subarrays is (right - left + 1)
            result += (right - left + 1);
        }

        // Replace this placeholder return statement with your code
        return result;
    }

    /**
     * Lists every contiguous subarray whose score is strictly less than {@code k}.
     *
     * <p>The method uses the same sliding-window invariant as {@link #countSubarrays(int[], long)}: while the current
     * window violates the condition, it shrinks from the left. Once the window is valid, every subarray ending at the
     * current right index and starting between {@code left} and the current index is also valid.</p>
     *
     * @param nums the input array
     * @param k the threshold value for the score
     * @return a list of all valid contiguous subarrays whose score is less than {@code k}
     * @implNote Time complexity is O(n^2) in the worst case because it enumerates each valid subarray, while the sliding-window
     * logic itself is O(n). Space complexity is O(1) extra besides the output list.
     */
    public List<List<Integer>> listSubarrays(int[] nums, long k) {
        // k threshold is given as long, but the array elements are int.
        // So, we need to be careful with the multiplication to avoid overflow.
        int n = nums.length;
        int left = 0; // start index of the sliding window
        long runningSum = 0; // This will hold the sum of the current window

        List<List<Integer>> result = new ArrayList<>(); // This will hold the list of valid subarrays

        for (int right = 0; right < n; right++) {
            runningSum += nums[right];
            // Calculate the score for the current window
            long score = runningSum * (right - left + 1);
            // Shrink the window from the left if the sum * window size exceeds k
            while (score >= k && left <= right) {
                // Shrink the window from the left
                runningSum -= nums[left]; // Remove the leftmost element from the sum
                left++; // Move the left pointer to the right
                score = runningSum * (right - left + 1); // Recalculate the score after shrinking the window
            }

            // At this point, all subarrays ending at 'right' and starting from 'left' to 'right' are valid
            // Now all subarrays ending at 'right' and starting from 'left' to 'right' are valid
            for (int start = left; start <= right; start++) {
                List<Integer> sub = new ArrayList<>();
                for (int i = start; i <= right; i++) {
                    sub.add(nums[i]);
                }
                result.add(sub);
            }
        }

        // Replace this placeholder return statement with your code
        return result;
    }

    // Driver code
    public static void main(String[] args) {
        CountSubArrayLessThanK sol = new CountSubArrayLessThanK();

        int[][] testArrays = {
                {2, 1, 4, 3, 5},
                {10, 1, 2},
                {12, 2, 2, 3},
                {5, 4, 2, 10},
                {7, 2, 9, 4, 6},
                {20, 30, 40},
                {11, 1, 3},
                {15, 22, 18, 30, 14, 28, 33, 19, 26, 12},
                {45, 31, 27, 38, 40, 29, 22, 47, 36, 25, 44, 33, 21, 30, 26},
                {100, 200, 300, 400, 500, 99, 98, 97}
        };

        long[] ks = {
                10, 15, 18, 25, 40, 10, 10, 600, 1000, 50
        };

        for (int i = 0; i < testArrays.length; i++) {
            int[] nums = testArrays[i];
            long k = ks[i];

            System.out.println((i + 1) + ".\tnums: " + java.util.Arrays.toString(nums));
            System.out.println("\tk: " + k);
            System.out.println("\n\tCount of subarrays = " + sol.countSubarrays(nums, k));
            System.out.println("\tList subarrays = " + sol.listSubarrays(nums, k));
            System.out.println("-".repeat(100));

        }
    }

}
