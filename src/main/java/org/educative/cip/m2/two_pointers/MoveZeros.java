package org.educative.cip.m2.two_pointers;

import java.util.Arrays;

/**
 * Time Complexity: O(n), where n is the length of the input array nums. We traverse through the array once.
 * Space Complexity: O(1), as we are using a constant amount of space for the left and right pointers.
 * Given an integer array nums, move all 0's to the end of it while maintaining the relative order of the non-zero elements.
 */
public class MoveZeros {

    public static void moveZeros(int[] nums) {
        int left = 0;
        for (int right = 0; right < nums.length; right++) {
            if (nums[right] != 0) {
                // Swap the elements at left and right pointers
                int temp = nums[left];
                nums[left] = nums[right];
                nums[right] = temp;
                left++;
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
