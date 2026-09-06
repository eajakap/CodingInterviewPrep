package org.educative.cip.m2.two_pointers;
/*
 * Count Fixed Bounded Subarrays:
 * Problem: Count the number of subarrays where the minimum element is minK and the maximum element is maxK.
 * Given an integer array nums and two integers minK and maxK, a fixed-bound subarray of nums is a subarray that satisfies the following conditions:
 * The minimum value in the subarray is equal to minK.
 * The maximum value in the subarray is equal to maxK
 * Steps to solve the problem:
 * 1. Initialize pointers and counters:
 *    - minPos: to track the last position of minK in the array.(initial value: -1)
 *    - maxPos: to track the last position of maxK in the array. (initial value: -1)
 *    - leftBound: to track the last position where an element was outside the range [minK, maxK]. (initial value: -1)
 *    - count: to accumulate the total number of valid subarrays found. (initial value: 0)
 * 2. Iterate through the array:
 *    - If the current element is less than minK or greater than maxK, update leftBound to the current index and reset minPos and maxPos to -1.
 *    - If the current element is equal to minK, update minPos to the current index.
 *    - If the current element is equal to maxK, update maxPos to the current index.
 *    - If both minPos and maxPos are valid (not -1),
 *          calculate the number of valid subarrays ending at the current index by taking
 *          the minimum of minPos and maxPos, subtracting leftBound, and adding this to count.
 * 3. Return the total count of valid subarrays.
 * Time Complexity: O(n) - We traverse the array once to count the subarrays.
 * Space Complexity: O(1) - We use a constant amount of space for pointers and counters.
 */
public class CountSubArrays {

    public long countSubarrays(int[] nums, int minK, int maxK) {
        int n = nums.length;
        int minPos = -1, maxPos = -1, leftBound = -1;
        long count = 0;

        for (int i = 0; i < n; i++) {
            if (nums[i] < minK || nums[i] > maxK) {
                leftBound = i; // invalid number found
                minPos = -1; // reset minPos since the current number is out of bounds
                maxPos = -1; // reset maxPos since the current number is out of bounds
            }

            if (nums[i] == minK) {
                minPos = i;
            }

            if (nums[i] == maxK) {
                maxPos = i;
            }

            if (minPos != -1 && maxPos != -1) {
                // Calculate the number of valid subarrays ending at index i
                count += Math.max(0, Math.min(minPos, maxPos) - leftBound);
            }
        }

        return count;
    }

    // Driver code
    public static void main(String[] args) {
        int[][] testCases = {
                {1, 3, 5, 2, 7, 5},
                {1, 5},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 2, 3, 4},
                {1, 5, 1, 5, 1, 5}
        };
        int[] minKs = {1, 1, 1, 2, 1};
        int[] maxKs = {5, 5, 1, 5, 5};

        for (int i = 0; i < testCases.length; i++) {
            int[] nums = testCases[i];
            int minK = minKs[i];
            int maxK = maxKs[i];
            System.out.println((i + 1) + ".\tnums = " + java.util.Arrays.toString(nums));
            System.out.println("\tminK = " + minK);
            System.out.println("\tmaxK = " + maxK);

            CountSubArrays sol = new CountSubArrays();
            long result = sol.countSubarrays(nums, minK, maxK);
            System.out.println("\n\tOutput: " + result);
            System.out.println("-".repeat(100));
        }
    }
}
