package org.educative.cip.m2.two_pointers;
/*
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
                leftBound = i;
                minPos = -1;
                maxPos = -1;
                continue;
            }

            if (nums[i] == minK) {
                minPos = i;
            }

            if (nums[i] == maxK) {
                maxPos = i;
            }

            if (minPos != -1 && maxPos != -1) {
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
