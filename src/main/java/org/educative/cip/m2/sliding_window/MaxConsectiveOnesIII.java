package org.educative.cip.m2.sliding_window;

import java.util.Arrays;

/**
 * Max Consecutive Ones III
 * Problem: Given a binary array nums and an integer k, return the maximum number of consecutive 1's in the array if you can flip at most k 0's.
 * Example 1:
 * Input: nums = [1,1,1,0,0,0,1,1,1,1,0], k = 2
 * Output: 6
 * Explanation: [1,1,1,0,0,1,1,1,1,1,1] contains 6 consecutive 1's after flipping two 0's.
 * Example 2:
 * Input: nums = [0,0,1,1,1,0,0,1,1,0,1,1], k = 3
 * Output: 10
 * Explanation: [0,0,1,1,1,1,1,1,1,1,1,1] contains 10 consecutive 1's after flipping three 0's.
 * Constraints:
 * 1 <= nums.length <= 10^5
 * nums[i] is either 0 or 1.
 * 0 <= k <= nums.length
 *
 */
public class MaxConsectiveOnesIII {
    /*
     * Returns the maximum number of consecutive 1's in the binary array if you can flip at most k 0's.
     *
     * Steps to solve the problem:
     * 1. Initialize two pointers, left and right, to represent the current window of elements.
     * 2. Initialize a variable to keep track of the count of zeros in the current window
     *    and a variable to store the maximum length of valid subarrays.
     * 3. Expand the window by moving the right pointer and updating the count of zeros if a zero is encountered.
     * 4. While the count of zeros in the current window exceeds k,
     *    shrink the window by moving the left pointer and
     *    updating the count of zeros if a zero is removed from the window.
     * 5. Update the maximum length of valid subarrays by calculating (right - left + 1) for each valid window.
     * 6. Repeat steps 3 to 5 until the right pointer reaches the end of the array.
     * 7. Return the maximum length of valid subarrays after traversing the entire array.
     *
     * Time Complexity: O(n), where n is the length of the binary array. We traverse through the array once.
     * Space Complexity: O(1), as we are using constant space for pointers and counters
     *
     * @param nums the input binary array
     * @param k    the maximum number of 0's that can be flipped
     * @return the maximum number of consecutive 1's after flipping at most k 0's
     */
    public int longestOnes(int[] nums, int k)
    {
        int left = 0, right = 0;
        int zeroCount = 0;
        int maxLength = 0;

        while (right < nums.length) {
            if (nums[right] == 0) {
                zeroCount++;
            }
            // Shrink the window from the left if the count of zeros exceeds k
            while (zeroCount > k) {
                // If the leftmost element is zero, decrement the zero count
                if (nums[left] == 0) {
                    zeroCount--; // Decrement the count of zeros in the current window - Reflects to flipping a zero back to one
                }
                // Move the left pointer to shrink the window
                left++;
            }
            // Update the maximum length of valid subarrays by calculating (right - left + 1)
            maxLength = Math.max(maxLength, right - left + 1);
            // Expand the window by moving the right pointer
            right++;
        }
        return maxLength;
    }

    public static void main(String[] args) {
        MaxConsectiveOnesIII sol = new MaxConsectiveOnesIII();

        int[][][] numsArray = {
                {{1, 0, 1, 1, 0, 1, 1, 1, 0, 1}},
                {{0, 0, 0, 0, 1, 1, 1, 1, 1}},
                {{1, 1, 0, 0, 1, 1, 0, 1, 1, 1}},
                {{0, 1, 0, 1, 0, 1, 0, 1}},
                {{1, 1, 1, 1, 1, 0, 0, 0, 0}},
        };
        int[] kValues = {1, 2, 3, 0, 4};

        for (int i = 0; i < numsArray.length; i++) {
            int[] nums = numsArray[i][0];
            int k = kValues[i];
            int result = sol.longestOnes(nums, k);

            System.out.print((i + 1) + ".\tInput array: " + Arrays.toString(nums) + "\n");
            System.out.println("\tTarget: " + k);
            System.out.println("\tResult: " + result);
            System.out.println("-".repeat(100));
        }
    }

}
