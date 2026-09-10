package org.educative.cip.m2.sliding_window;

import java.util.HashMap;
import java.util.Map;

/*
 * Binary Subarrays With Sum
 * Problem: Given a binary array nums and an integer goal, return the number of non-empty subarrays with a sum equal to goal.
 * Example 1:
 * Input: nums = [1,0,1,0,1], goal = 2
 * Output: 4
 * Explanation: The 4 subarrays are [1,0,1], [1,0,1], [0,1,0,1], and [1,0,1].
 * Example 2:
 * Input: nums = [0,0,0,0,0], goal = 0
 * Output: 15
 * Explanation: The 15 subarrays are all the possible subarrays of nums.
 * Constraints:
 * 1 <= nums.length <= 3 * 10^4
 * nums[i] is either 0 or 1.
 * 0 <= goal <= nums.length
 *
 * Steps to solve the problem:
 * 1. Initialize a variable to keep track of the current sum of the subarray and a variable to store the count of valid subarrays.
 * 2. Use a HashMap to store the frequency of prefix sums encountered so far.
 * 3. Iterate through the binary array, updating the current sum and checking if (current sum - goal) exists in the HashMap.
 * 4. If it exists, add the frequency of (current sum - goal) to the count of valid subarrays.
 * 5. Update the frequency of the current sum in the HashMap.
 * 6. Return the count of valid subarrays after traversing the entire array.
 */
public class BinarySubarraysWithSum {
    public enum SolutionType {
        BRUTE_FORCE,
        SLIDING_WINDOW,
        PREFIX_SUM
    }

    public int numSubarraysWithSum(int[] nums, int goal, SolutionType solutionType) {
        switch (solutionType) {
            case BRUTE_FORCE:
                // Implement brute force solution if needed
                break;
            case SLIDING_WINDOW:
                // Implement sliding window solution if needed
                return numSubarraysWithSumSlidingWindow(nums, goal);
            case PREFIX_SUM:
            default:
                return numSubarraysWithSumPrefixSum(nums, goal);
        }
        return 0; // Default return value if no solution type matches
    }

    /**
     * Counts the number of non-empty subarrays with a sum equal to the specified goal using a brute force approach.
     *
     * Steps to solve the problem:
     * 1. Initialize a variable to keep track of the count of valid subarrays.
     * 2. Use two nested loops to generate all possible subarrays of the binary array.
     * 3. For each subarray, calculate the sum and check if it equals the goal.
     * 4. If the sum equals the goal, increment the count of valid subarrays.
     * 5. Return the count of valid subarrays after checking all possible subarrays.
     *
     * Time Complexity: O(n^2), where n is the length of the binary array. We check all possible subarrays.
     * Space Complexity: O(1), as we are using constant space for counters.
     * @param nums the input binary array
     * @param goal the target sum for the subarrays
     * @return the count of valid subarrays with sum equal to goal
     */
    public int numSubarraysWithSumBruteForce(int[] nums, int goal) {
        int n = nums.length;
        int count = 0;

        // Iterate through all possible starting indices of subarrays
        for (int i = 0; i < n; i++) {
            int sum = 0; // Initialize the sum for the current subarray starting at index i
            for (int j = i; j < n; j++) {
                sum += nums[j]; // Update the sum for the current subarray ending at index j
                // Check if the current sum equals the goal
                if (sum == goal) {
                    // Increment the count of valid subarrays
                    count++;
                }
            }
        }

        return count;
    }

    /**
     * Counts the number of non-empty subarrays with a sum equal to the specified goal using the sliding window technique.
     *
     * Steps to solve the problem:
     * 1. Initialize two pointers, left and right, to represent the current window of elements.
     * 2. Initialize a variable to keep track of the current sum of the window and a variable to store the count
     *    of valid subarrays. Another variable is used to count the number of leading zeros in the current window.
     * 3. Expand the window by moving the right pointer and adding the current element to the sum.
     * 4. While the current sum exceeds the goal, shrink the window by moving the left pointer and subtracting the
     *    element at the left pointer from the sum.
     * 5. If the current sum equals the goal, increment the count of valid subarrays by the number of leading
     *    zeros in the current window plus one.
     * 6. Repeat steps 3 to 5 until the right pointer reaches the end of the array.
     * 7. Return the count of valid subarrays after traversing the entire array.
     *
     * Time Complexity: O(n), where n is the length of the binary array. We traverse through the array once.
     * Space Complexity: O(1), as we are using constant space for pointers and counters.
     * @param nums the input binary array
     * @param goal the target sum for the subarrays
     * @return the count of valid subarrays with sum equal to goal
     */
    public int numSubarraysWithSumSlidingWindow(int[] nums, int goal) {
        int totalCount = 0; // Initialize the count of valid subarrays
        int left = 0, right = 0; // Initialize two pointers for the sliding window
        int currentSum = 0; // Initialize the current sum of the subarray
        int prefixZeros = 0; // Count of leading zeros in the current window

        while (right < nums.length) {
            currentSum += nums[right];
            // Shrink the window from the left if the current sum exceeds the goal
            while (currentSum > goal && left <= right) {
                currentSum -= nums[left];
                left++;
                prefixZeros = 0;
            }
            // If the current sum equals the goal, increment the count
            while (left < right && nums[left] == 0 && currentSum == goal) {
                prefixZeros++;
                currentSum -= nums[left];
                left++;
            }
            // Increment the count by the number of prefix zeros plus one
            if (left <= right && currentSum == goal) {
                totalCount += prefixZeros + 1;
            }
            // Expand the window by moving the right pointer
            right++;
        }

        return totalCount;
    }

    /**
     * Counts the number of non-empty subarrays with a sum equal to the specified goal.
     * Time Complexity: O(n), where n is the length of the binary array. We traverse through the array once.
     * Space Complexity: O(n), as we use a HashMap to store the frequency of prefix sums.
     * @param nums the input binary array
     * @param goal the target sum for the subarrays
     * @return the count of valid subarrays with sum equal to goal
     */
    public int numSubarraysWithSumPrefixSum(int[] nums, int goal) {
        int count = 0; // Initialize the count of valid subarrays
        int currentSum = 0; // Initialize the current sum of the subarray
        Map<Integer, Integer> prefixSumCount = new HashMap<>(); // HashMap to store the frequency of prefix sums
        prefixSumCount.put(0, 1); // Initialize with sum 0 having one occurrence

        for (int num : nums) {
            currentSum += num;

            // Check if there is a prefix sum that would make the current subarray sum equal to goal
            count += prefixSumCount.getOrDefault(currentSum - goal, 0);

            // Update the frequency of the current sum in the HashMap
            prefixSumCount.put(currentSum, prefixSumCount.getOrDefault(currentSum, 0) + 1);
        }

        return count;
    }

    public static void main(String[] args) {
        BinarySubarraysWithSum sol = new BinarySubarraysWithSum();

        int[][] arrays = {
                {1,0,1,0,1},
                {0,0,0,0,0},
                {1,1,1},
                {0,1,0,1,0,1},
                {1}
        };

        int[] goals = {2, 0, 2, 2, 1};

        for (int i = 0; i < arrays.length; i++) {
            System.out.println((i+1) + ".\tnums: " + java.util.Arrays.toString(arrays[i]));
            System.out.println("\tgoal: " + goals[i]);
            int res = sol.numSubarraysWithSum(arrays[i], goals[i], SolutionType.PREFIX_SUM);
            System.out.println("\tNumber of subarrays with sum (PREFIX_SUM) = " + goals[i] + " are " + res + ".");
            int res2 = sol.numSubarraysWithSum(arrays[i], goals[i], SolutionType.SLIDING_WINDOW);
            System.out.println("\tNumber of subarrays with sum (SLIDING_WINDOW) = " + goals[i] + " are " + res2 + ".");
            int res3 = sol.numSubarraysWithSum(arrays[i], goals[i], SolutionType.BRUTE_FORCE);
            System.out.println("\tNumber of subarrays with sum (BRUTE_FORCE) = " + goals[i] + " are " + res3 + ".");
            System.out.println("-".repeat(100));
        }
    }

}
