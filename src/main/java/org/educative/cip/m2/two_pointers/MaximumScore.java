package org.educative.cip.m2.two_pointers;

import java.util.Arrays;
/*
 * Maximum Score from Two Arrays
 * Problem: Given two sorted arrays nums1 and nums2, return the maximum score you can obtain by traversing both arrays.
 *          You can switch from one array to the other at common elements.
 *          The score is the sum of the elements you traverse.
 * Steps:
 * 1. Initialize two pointers, pointer1 and pointer2, to traverse nums1 and nums2 respectively.
 * 2. Initialize two sums, sum_path1 and sum_path2, to keep track of the scores for each path.
 * 3. Traverse both arrays using the pointers:
 *    - If the current element in nums1 is less than the current element in nums2, add it to sum_path1 and move pointer1 forward.
 *    - If the current element in nums2 is less than the current element in nums1, add it to sum_path2 and move pointer2 forward.
 *    - If the current elements in both arrays are equal, update both sums to the maximum of the two sums plus the current element, and move both pointers forward.
 * 4. After traversing both arrays, return the maximum of sum_path1 and sum_path2 modulo 10^9 + 7.
 *
 * Time Complexity: O(n + m) - We traverse both arrays once, where n and m are the lengths of nums1 and nums2.
 * Space Complexity: O(1) - We use a constant amount of space for pointers and sums.
 */

public class MaximumScore {
    // Method to calculate the maximum score from two sorted arrays
    public static int maxSum(int[] nums1, int[] nums2) {
        int pointer1 = 0, pointer2 = 0;
        int len1 = nums1.length, len2 = nums2.length;
        long sum_path1 = 0, sum_path2 = 0;
        int MOD = 1_000_000_007; // Constant for modulo operation 10^9 + 7 - prevent integer overflow

        while (pointer1 < len1 || pointer2 < len2) {
            // If we reach the end of one array, continue with the other
            if (pointer1 < len1 &&
                    (pointer2 == len2 || nums1[pointer1] < nums2[pointer2])) {
                // Add the current element from nums1 to sum_path1 and move pointer1 forward
                sum_path1 += nums1[pointer1++];
            } else if (pointer2 < len2 &&
                    (pointer1 == len1 || nums1[pointer1] > nums2[pointer2])) {
                // Add the current element from nums2 to sum_path2 and move pointer2 forward
                sum_path2 += nums2[pointer2++];
            } else {
                // When nums1[pointer1] == nums2[pointer2], we can switch paths
                // Update both sums to the maximum of the two sums plus the current element
                sum_path1 = sum_path2 = Math.max(sum_path1, sum_path2) + nums1[pointer1];
                // Move both pointers forward
                pointer1++;
                pointer2++;
            }
        }
        return (int)(Math.max(sum_path1, sum_path2) % MOD);
    }

    // Driver method
    public static void main(String[] args) {
        int[][][] testCases = {
                {{2, 4, 5, 8, 10}, {4, 6, 8, 9}},
                {{1, 3, 5, 7, 9}, {3, 5, 100}},
                {{1, 2, 3, 4, 5}, {6, 7, 8, 9, 10}},
                {{2, 5, 7, 11, 13}, {1, 4, 6, 8, 10}},
                {{1, 2, 4, 6, 8}, {2, 4, 6, 7, 9}}
        };

        for (int i = 0; i < testCases.length; i++) {
            int[] nums1 = testCases[i][0];
            int[] nums2 = testCases[i][1];
            int result = maxSum(nums1, nums2);

            System.out.println((i + 1) + ".\tnums1: " + Arrays.toString(nums1));
            System.out.println("\tnums2: " + Arrays.toString(nums2));
            System.out.println("\tresult: " + result);
            System.out.println(new String(new char[100]).replace('\0', '-'));
        }
    }
}