package org.educative.cip.m2.sliding_window;

import java.util.Arrays;

/**
 * Minimum Size Subarray Sum
 * Problem: Given an array of positive integers nums and a positive integer target,
 *          return the minimal length of a contiguous subarray [numsl, numsl+1, ..., numsr-1, numsr] of which the
 *          sum is greater than or equal to target. If there is no such subarray, return 0 instead.
 * Example 1:
 * Input: target = 7, nums = [2,3,1,2,4,3]
 * Output: 2
 * Explanation: The subarray [4,3] has the minimal length under the problem constraint.
 * Example 2:
 * Input: target = 4, nums = [1,4,4]
 * Output: 1
 * Example 3:
 * Input: target = 11, nums = [1,1,1,1,1,1,1,1]
 * Output: 0
 * Constraints:
 * 1 <= target <= 10^4
 * 1 <= nums.length <= 10^3
 * 1 <= nums[i] <= 10^3
 *
 * Steps to solve the problem:
 * 1. Initialize two pointers, left and right, to represent the current window of elements.
 * 2. Initialize a variable to keep track of the current sum of the window and a variable to store the minimum length found.
 * 3. Expand the window by moving the right pointer and adding the current element to the sum.
 * 4. While the current sum is greater than or equal to the target, update the minimum length if the current window
 *    is smaller than the previously found minimum length. Then, shrink the window by moving the left pointer and
 *    subtracting the element at the left pointer from the sum.
 * 5. Repeat steps 3 and 4 until the right pointer reaches the end of the array.
 * 6. If a valid subarray was found, return the minimum length; otherwise, return 0.
 * 
 * Time Complexity: O(n) - We traverse the array to find the minimum length of subarray.
 * Space Complexity: O(1) - We use a constant amount of space for pointers.
 */
public class MinSizeSubArraySum {

    /**
     * Finds the minimum length of a contiguous subarray whose sum is at least {@code target}.
     *
     * <p>The method uses a sliding window where the right pointer grows to expand the sum and the left pointer
     * shrinks the window whenever the sum is large enough. Each valid window updates the best minimum length.</p>
     *
     * @param target the minimum required sum of a valid subarray
     * @param nums the array of positive integers to search through
     * @return the minimum length of a contiguous subarray with sum at least {@code target}, or {@code 0} if no such subarray exists
     * @implNote Time complexity is O(n) and space complexity is O(1).
     */
    public static int minSubArrayLen(int target, int[] nums) {
        int windowSize = Integer.MAX_VALUE;
        int left = 0; // start of the window
        int sum = 0; // current sum of the window
        int end = nums.length; // length of the array
        int right = 0; // end of the window
        while (right < end) { // expand the window to the right
            sum += nums[right]; // add the current element to the sum
            while (sum >= target) { // shrink the window from the left while the sum is greater than or equal to the target
                // update the minimum window size if the current window is smaller
                windowSize = Math.min(windowSize, right - left + 1);
                // shrink the window from the left
                sum -= nums[left];
                // move the left pointer to the right
                left++;
            }
            // expand the window to the right
            right++;
        }
        // return the minimum window size found, or 0 if no valid window was found
        return windowSize == Integer.MAX_VALUE ? 0 : windowSize;
    }

    public static int minSubArrayLenEducative(int target, int[] nums) {
        // Initializing windowSize to a max number
        int windowSize = Integer.MAX_VALUE;
        int currSubArrSize = 0; // Variable to store the size of the current subarray
        // Initialize start pointer to 0 and sum to 0
        int start = 0; // Start pointer for the sliding window
        int sum = 0; // Variable to store the sum of the current subarray

        // Iterate over the input array
        for (int end = 0; end < nums.length; end++) { // End pointer for the sliding window
            sum += nums[end]; // Add the current element to the sum
            // check if we can remove elements from the start of the subarray
            // while still satisfying the target condition
            while (sum >= target) {
                // Shrink the window from the left and update the sum
                // Update the minimum window size if the current subarray is smaller
                // Finding size of current subarray
                currSubArrSize = (end + 1) - start; // Update the size of the current subarray
                windowSize = Math.min(windowSize, currSubArrSize); // Update the minimum window size if the current subarray is smaller
                sum -= nums[start]; // Remove the element at the start pointer from the sum
                start += 1; // Move the start pointer to the right to shrink the window
            }
        }

        // If windowSize is still Integer.MAX_VALUE, it means no valid subarray was found
        return (windowSize != Integer.MAX_VALUE) ? windowSize : 0;
    }

    // Driver code
    public static void main(String[] args) {
        int[] target = {7, 4, 11, 10, 5, 15};
        int[][] inputArr = {
                {2, 3, 1, 2, 4, 3},
                {1, 4, 4},
                {1, 1, 1, 1, 1, 1, 1, 1},
                {1, 2, 3, 4},
                {1, 2, 1, 3},
                {5, 4, 9, 8, 11, 3, 7, 12, 15, 44}
        };
        for (int i = 0; i < target.length; i++) {
            int windowSize = minSubArrayLen(target[i], inputArr[i]);
            System.out.print((i + 1) + ".\tInput array: " + Arrays.toString(inputArr[i]));
            System.out.print("\n\tTarget: " + target[i]);
            System.out.println("\n\tMinimum Length of Subarray: " + windowSize);
            System.out.println(new String(new char[100]).replace('\0', '-'));
        }
    }

}
