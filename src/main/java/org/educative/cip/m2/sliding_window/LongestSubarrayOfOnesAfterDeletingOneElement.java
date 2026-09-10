package org.educative.cip.m2.sliding_window;

import java.util.Arrays;

/*
 * Longest Subarray of 1's After Deleting One Element
 * Problem: Given a binary array nums, you should delete one element from it.
 *          Return the size of the longest non-empty subarray containing only 1's in the resulting array.
 *          Return 0 if there is no such subarray.
 * Example 1:
 * Input: nums = [1,1,0,1]
 * Output: 3
 * Explanation: After deleting the number in position 2, [1,1,1] contains 3 numbers with value of 1's.
 * Example 2:
 * Input: nums = [0,1,1,1,0,1,1,0,1]
 * Output: 5
 * Explanation: After deleting the number in position 4, [0,1,1,1,1,1] longest subarray with value of 1's is [1,1,1,1,1].
 * Example 3:
 * Input: nums = [1,1,1]
 * Output: 2
 * Explanation: You must delete one element.
 * Constraints:
 * 1 <= nums.length <= 10^5
 * nums[i] is either 0 or 1.
 *
 */
public class LongestSubarrayOfOnesAfterDeletingOneElement {

    /**
     * Finds the size of the longest non-empty subarray containing only 1's after deleting one element from the binary array.
     *
     * Steps to solve the problem:
     * 1. Initialize two pointers, left and right, to represent the current window of elements.
     * 2. Initialize a variable to keep track of the count of zeros in the current window and a variable to store the maximum length of valid subarrays.
     * 3. Expand the window by moving the right pointer and updating the count of zeros if a zero is encountered.
     * 4. While the count of zeros in the current window exceeds 1, shrink the window by moving the left pointer and updating the count of zeros if a zero is removed from the window.
     * 5. Update the maximum length of valid subarrays by calculating (right - left) for each valid window.
     * 6. Repeat steps 3 to 5 until the right pointer reaches the end of the array.
     * 7. Return the maximum length of valid subarrays after traversing the entire array.
     *
     * Time Complexity: O(n), where n is the length of the binary array. We traverse through the array once.
     * Space Complexity: O(1), as we are using constant space for pointers and counters
     * @param nums the input binary array
     * @return the size of the longest non-empty subarray containing only 1's after deleting one element
     */
    public int longestSubarray(int[] nums)
    {
        int left = 0; // Initialize the left pointer for the sliding window
        int zeroCount = 0; // Count of zeros in the current window
        int maxLength = 0; // Maximum length of valid subarrays
        for (int right = 0; right < nums.length; right++) {
            // Expand the window by moving the right pointer and updating the count of zeros
            if (nums[right] == 0) {
                zeroCount++;
            }
            // Shrink the window from the left if the count of zeros exceeds 1
            while (zeroCount > 1) {
                // If the leftmost element is zero, decrement the zero count
                if (nums[left] == 0) {
                    zeroCount--;
                }
                left++; // Move the left pointer to shrink the window
            }
            // Update the maximum length of valid subarrays by calculating (right - left)
            maxLength = Math.max(maxLength, right - left);
        }
        return maxLength;
    }

    public static void main(String[] args) {
        LongestSubarrayOfOnesAfterDeletingOneElement sol = new LongestSubarrayOfOnesAfterDeletingOneElement();

        int[][][] testCases = {
                {{1, 0, 1, 1, 0, 1, 1, 1}},
                {{0, 0, 1, 1, 1, 1, 0, 0}},
                {{1, 1, 0, 0, 1, 1, 1, 0, 1}},
                {{0}},
                {{1, 1, 1, 1, 1, 1, 0}},
        };

        for (int i = 0; i < testCases.length; i++) {
            int[] nums = testCases[i][0];
            int result = sol.longestSubarray(nums);
            System.out.println((i + 1) + ".\tInput array: " + Arrays.toString(nums));
            System.out.println("\tResult: " + result);
            System.out.println("-".repeat(100));
        }
    }

}
