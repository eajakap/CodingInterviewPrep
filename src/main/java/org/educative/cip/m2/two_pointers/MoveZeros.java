package org.educative.cip.m2.two_pointers;

import java.util.Arrays;

/**
 * Problem: Move Zeros
 * Given an integer array nums, move all 0's to the end of it while maintaining the relative order of the non-zero elements.
 * You must do this in-place without making a copy of the array.
 * Example 1:
 * Input: nums = [0,1,0,3,12]
 * Output: [1,3,12,0,0]
 * Example 2:
 * Input: nums = [0]
 * Output: [0]
 * Constraints:
 * 1 <= nums.length <= 10^4
 * -2^31 <= nums[i] <= 2^31 - 1
 * Follow up: Could you minimize the total number of operations done?
 *
 * Step-by-step approach:
 * 1. Initialize a pointer `left` to 0. This pointer will track the position where the next non-zero element should be placed.
 * 2. Iterate through the array with a pointer `right` from 0 to the end of the array.
 * 3. For each element at `nums[right]`, check if it is non-zero.
 * 4. If it is non-zero, swap the elements at `left` and `right`, and increment the `left` pointer.
 * 5. Continue this process until the end of the array is reached.
 * 6. After the loop, all non-zero elements will be at the beginning of the array in their original order, and all zeros will be moved to the end.
 *
 * Time Complexity: O(n), where n is the length of the input array nums. We traverse through the array once.
 * Space Complexity: O(1), as we are using a constant amount of space for the left and right pointers.
 * Given an integer array nums, move all 0's to the end of it while maintaining the relative order of the non-zero elements.
 */
public class MoveZeros {

    /**
     * Moves all zeros in the input array to the end while maintaining the relative order of non-zero elements.
     * Steps:
     * 1. Initialize a pointer `left` to 0. This pointer will track the position where the next non-zero element should be placed.
     * 2. Iterate through the array with a pointer `right` from 0 to the end of the array.
     * 3. For each element at `nums[right]`, check if it is non-zero.
     * 4. If it is non-zero, swap the elements at `left` and `right`, and increment the `left` pointer.
     * 5. Continue this process until the end of the array is reached.
     * 6. After the loop, all non-zero elements will be at the beginning of the array in their original order, and all zeros will be moved to the end.
     *
     * @param nums The input array of integers.
     */
    public static void moveZeros(int[] nums) {
        int left = 0; // Pointer to track the position for the next non-zero element
        for (int right = 0; right < nums.length; right++) { // Pointer to iterate through the array
            if (nums[right] != 0) { // Check if the current element pointed by right is non-zero
                // Swap the elements at left and right pointers
                int temp = nums[left];
                nums[left] = nums[right];
                nums[right] = temp;
                left++; // Move the left pointer to the next position for the next non-zero element
            }
        }
    }

    public static void main(String[] args) {
        MoveZeros sol = new MoveZeros();

        int[][] testCases = {
                {4, 0, 5, 0, 0, 3},
                {0, 0, 1},
                {7, 8, 9, 1},
                {0, -5, 0, 2147483647, -2147483648},
                {0, 0, 0, 42},
        };

        for (int idx = 0; idx < testCases.length; idx++) {
            int[] inputDisplay = Arrays.copyOf(testCases[idx], testCases[idx].length);
            sol.moveZeros(testCases[idx]);
            System.out.println((idx + 1) + ".\tInput array: " + Arrays.toString(inputDisplay));
            System.out.println("\tResult: " + Arrays.toString(testCases[idx]));
            System.out.println("-".repeat(100));
        }
    }
}
