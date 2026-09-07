package org.educative.cip.m2.two_pointers;

import java.util.Arrays;

/*
 * Problem:
 * Given a sorted array of integers, return a new array containing the squares of each number, also sorted in non-decreasing order.
 *
 * Steps to solve the problem:
 * 1. Initialize two pointers, one at the start (left) and one at the end (right) of the array.
 * 2. Create a result array of the same length as the input array to store the squared values.
 * 3. Iterate while the left pointer is less than or equal to the right pointer:
 *    a. Compare the absolute values of the elements at the left and right pointers.
 *    b. Square the larger absolute value and place it at the current position in the result array (starting from the end).
 *    c. Move the corresponding pointer (left or right) inward and decrement the position in the result array.
 * 4. Return the result array after the loop ends.
 *
 * Time Complexity: O(n) - We traverse the array once to compute the squares and sort them.
 * Space Complexity: O(n) - We use an additional array to store the squared values.
 */
public class SortedSquaredArray {

    public static int[] sortedSquares(int[] nums) {
        int n = nums.length;

        int[] res = new int[n];

        int left = 0, right = n - 1;

        int pos = n - 1;

        while (left <= right) {
            if (Math.abs(nums[left]) > Math.abs(nums[right])) {
                res[pos] = nums[left] * nums[left];
                left++;
            } else {
                res[pos] = nums[right] * nums[right];
                right--;
            }

            pos--;
        }

        return res;
    }

    public static void main(String[] args) {
        // 5 test cases
        int[][] testCases = {
                {-4, -1, 0, 3, 10},    // mix of negatives and positives
                {-7, -3, 2, 3, 11},    // another mix
                {0, 1, 2, 3, 4},       // all non-negative
                {-5, -4, -3, -2, -1},  // all negative
                {1}                    // single element
        };

        for (int i = 0; i < testCases.length; i++) {
            System.out.println((i + 1) + ".\tnums = " + Arrays.toString(testCases[i]));
            System.out.println("\tOutput = " + Arrays.toString(sortedSquares(testCases[i])));
            System.out.println("----------------------------------------------------------------------------------------------------");
        }
    }
}