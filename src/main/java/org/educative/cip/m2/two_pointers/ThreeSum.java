package org.educative.cip.m2.two_pointers;

import java.util.*;

public class ThreeSum {
    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums); // O(nlogn)
        List<List<Integer>> result = new ArrayList<>(); // Space Complexity: O(n) for the result list
        int n = nums.length;

        // Time Complexity: O(n^2)
        // Outer Loop: O(n)
        for (int i = 0; i < n - 2; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) continue;
            if (nums[i] > 0) break;
            int left = i + 1;
            int right = n - 1;
            // Inner Loop: O(n)
            while (left < right) {
                int currentSum = nums[i] + nums[left] + nums[right];
                if (currentSum < 0) {
                    left++;
                } else if (currentSum > 0) {
                    right--;
                } else {
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    while (left < right && nums[left] == nums[left + 1]) left++;
                    while (left < right && nums[right] == nums[right - 1]) right--;
                    left++;
                    right--;
                }
            }
        }

        return result;
    }

    public static void main(String[] args) {
        ThreeSum sol = new ThreeSum();
        int[][] testCases = {
                {-2, 0, 1, 1, 2},
                {1, -1, -1, 0},
                {-4, -2, -1, 0, 1, 2, 3, 4},
                {3, -3, 0, -1, 1, 2, -2},
                {-5, -3, -1, 0, 1, 2, 3, 5},
        };

        for (int idx = 0; idx < testCases.length; idx++) {
            int[] nums = testCases[idx].clone();
            List<List<Integer>> result = sol.threeSum(nums);
            System.out.println((idx + 1) + ".\tInput array: " + Arrays.toString(testCases[idx]));
            System.out.println("\tResult: " + result);
            System.out.println("-".repeat(100));
        }
    }
}