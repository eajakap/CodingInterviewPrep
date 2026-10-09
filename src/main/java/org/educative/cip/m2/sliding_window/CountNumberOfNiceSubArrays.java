package org.educative.cip.m2.sliding_window;

import java.util.Arrays;

/**
 * Problem: Count Number of Nice Subarrays
 *
 * Given an array of integers nums and an integer k. A subarray is called nice if there are k odd numbers on it.
 * Return the number of nice sub-arrays.
 *
 * Example 1:
 * Input: nums = [1,1,2,1,1], k = 3
 * Output: 2
 * Explanation: The only sub-arrays with 3 odd numbers are [1,1,2,1] and [1,2,1,1].
 *
 * Example 2:
 * Input: nums = [2,4,6], k = 1
 * Output: 0
 * Explanation: There is no odd numbers in the array.
 *
 * Example 3:
 * Input: nums = [2,2,2,1,2,2,1,2,2,2], k = 2
 * Output: 16
 */
public class CountNumberOfNiceSubArrays {
    /**
     * Returns the number of nice subarrays in the given array.
     *
     * @param nums the input array of integers
     * @param k the target number of odd numbers in a nice subarray
     * @return the number of nice subarrays
     */
    public int numberOfSubarrays(int[] nums, int k) {
        class AtMostHelper {
            long atMost(int kVal) {
                int left = 0;
                int oddCount = 0;
                long total = 0;
                for (int right = 0; right < nums.length; right++) {
                    oddCount += (nums[right] & 1);
                    while (oddCount > kVal) {
                        oddCount -= (nums[left] & 1);
                        left++;
                    }
                    total += (right - left + 1);
                }
                return total;
            }
        }
        AtMostHelper helper = new AtMostHelper();
        return (int)(helper.atMost(k) - helper.atMost(k - 1));
    }

    public static void main(String[] args) {
        int[][] arrays = {
                {2, 1, 4, 3, 6, 5},
                {1, 2, 3, 4, 5, 6, 7},
                {8, 10, 12, 14, 16, 18, 20},
                {9, 9, 9, 2, 4, 6, 1},
                {2, 2, 1, 2, 1, 2, 1, 2}
        };
        int[] ks = {2, 3, 1, 4, 2};

        CountNumberOfNiceSubArrays sol = new CountNumberOfNiceSubArrays();

        for (int i = 0; i < arrays.length; i++) {
            int[] nums = arrays[i];
            int k = ks[i];
            int result = sol.numberOfSubarrays(nums, k);

            System.out.println((i + 1) + ".\tInput array: " + Arrays.toString(nums));
            System.out.println("\tTarget: " + k);
            System.out.println("\tResult: " + result);
            System.out.println("-".repeat(100));
        }
    }
}
