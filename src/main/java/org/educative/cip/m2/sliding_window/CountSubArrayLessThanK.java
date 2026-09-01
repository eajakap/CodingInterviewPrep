package org.educative.cip.m2.sliding_window;

/**
 * Time Complexity: O(n), where n is the length of the array.
 * We traverse through the array once.
 * Space Complexity: O(1), as we are using constant space for pointers and counters.
 * Given an integer array nums and an integer k, return the number of contiguous subarrays
 * where the (sum of all the elements in the subarray * window size) is strictly less than k.
 */
public class CountSubArrayLessThanK {
    public long countSubarrays(int[] nums, long k) {
        // k threshold is given as long, but the array elements are int.
        // So, we need to be careful with the multiplication to avoid overflow.
        int n = nums.length;
        int left = 0;
        long runningSum = 0;
        int result = 0; // This will hold the count of valid subarrays

        for (int right = 0; right < n; right++) {
            runningSum += nums[right];

            long score = runningSum * (right - left + 1);
            // Shrink the window from the left if the sum * window size exceeds k
            while (score >= k && left <= right) {
                runningSum -= nums[left];
                left++;
                score = runningSum * (right - left + 1);
            }

            // At this point, all subarrays ending at 'right' and starting from 'left' to 'right' are valid
            // The number of such subarrays is (right - left + 1)
            result += (right - left + 1);
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
            System.out.println("-".repeat(100));
        }
    }

}
