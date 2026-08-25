package org.educative.cip.m2.two_pointers;

import java.util.Arrays;
/*
 * Time Complexity: O(n + m) - We traverse both arrays once, where n and m are the lengths of nums1 and nums2.
 * Space Complexity: O(1) - We use a constant amount of space for pointers and sums.
 */

public class MaximumScore {
    public static int maxSum(int[] nums1, int[] nums2) {
        int pointer1 = 0, pointer2 = 0;
        int len1 = nums1.length, len2 = nums2.length;
        long sum_path1 = 0, sum_path2 = 0;
        int MOD = 1_000_000_007;

        while (pointer1 < len1 || pointer2 < len2) {
            if (pointer1 < len1 && (pointer2 == len2 || nums1[pointer1] < nums2[pointer2])) {
                sum_path1 += nums1[pointer1++];
            } else if (pointer2 < len2 && (pointer1 == len1 || nums1[pointer1] > nums2[pointer2])) {
                sum_path2 += nums2[pointer2++];
            } else {
                sum_path1 = sum_path2 = Math.max(sum_path1, sum_path2) + nums1[pointer1];
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