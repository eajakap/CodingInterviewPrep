package org.educative.cip.m2.two_pointers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/*
 * Problem: Count the number of pairs in a list of integers whose sum is lies between lower and upper value.
 * Approach: Sort the list and use a two-pointer technique to find pairs efficiently.
 * Steps:
 * 1. Sort the list of integers.
 * 2. Initialize two pointers: one at the start (low) and one at the end (high) of the list.
 * 3. Count pairs with sum <= upper using two pointers: if nums[low] + nums[high] <= limit, all
 *    (high - low) pairs ending at low are valid, so move low up; otherwise move high down.
 *    Subtract the count of pairs with sum <= lower - 1. Use long to avoid overflow.
 * 4. Return the count of valid pairs.
 * This class provides a method to count the number of pairs in a list of integers whose sum is lies between lower and upper value.
 * The approach uses sorting and a two-pointer technique to efficiently find the pairs.
 * Time Complexity: O(n log n) - Sorting the list takes O(n log n) time, and the two-pointer traversal takes O(n) time.
 * Space Complexity: O(1) - We use a constant amount of space for pointers and counters.
 */
public class CountFairPairs {

    public static long countFairPairs(int[] nums, int lower, int upper) {
        Arrays.sort(nums);
        return countPairsAtMost(nums, upper) - countPairsAtMost(nums, (long) lower - 1);
    }

    // Counts pairs i < j with nums[i] + nums[j] <= limit; nums must be sorted.
    private static long countPairsAtMost(int[] nums, long limit) {
        long count = 0;
        int low = 0, high = nums.length - 1;
        while (low < high) {
            if ((long) nums[low] + nums[high] <= limit) {
                count += high - low;
                low++;
            } else {
                high--;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        List<Object[]> testCases = new ArrayList<>();
        testCases.add(new Object[]{new int[]{-2, 0, 1, 3, 5}, 1, 4});
        testCases.add(new Object[]{new int[]{10, -10, 2, 8, -3, 7}, -1, 9});
        testCases.add(new Object[]{new int[]{4, 4, 4, 4, 4}, 8, 8});
        testCases.add(new Object[]{new int[]{-5, -1, -2, 6, 9, 0}, -3, 4});
        testCases.add(new Object[]{new int[]{1000000000, -1000000000, 0, 1, -1}, -1, 1});
        CountFairPairs sol = new CountFairPairs();
        for (int i = 0; i < testCases.size(); i++) {
            Object[] tc = testCases.get(i);
            int[] nums = (int[]) tc[0];
            int lower = (int) tc[1];
            int upper = (int) tc[2];
            int[] numsForPrint = Arrays.copyOf(nums, nums.length);
            long result = sol.countFairPairs(nums, lower, upper);
            System.out.println((i + 1) + ".\tInput array: " + Arrays.toString(numsForPrint));
            System.out.println("\tTarget: " + lower + ", " + upper);
            System.out.println("\tResult: " + result);
            System.out.println("-".repeat(100));
        }
    }
}