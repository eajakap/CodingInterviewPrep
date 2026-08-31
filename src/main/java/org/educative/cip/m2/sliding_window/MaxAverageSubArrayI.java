package org.educative.cip.m2.sliding_window;

public class MaxAverageSubArrayI {

    /**
     * Time Complexity: O(n), where n is the length of the array.
     * We traverse through the array once.
     * Space Complexity: O(1), as we are using constant space for pointers and counters.
     * Given an integer array nums consisting of n elements, find the contiguous subarray of given length k that has the maximum average value and return this value. Any answer with a calculation error less than 10-5 will be accepted.
     */
    public static double findMaxAverage(int[] nums, int k) {

        // Replace this placeholder return statement with your code
        int left = 0; // start index of the sliding window
        int right = k; // end index of the sliding window (exclusive)
        int sum = 0;
        double maxSum = Double.NEGATIVE_INFINITY;

        // Calculate the sum of the first window
        for (int j = left; j < right; j++) {
            sum += nums[j];
        }
        maxSum = Math.max(maxSum, sum);
        // Slide the window across the array
        // Remaining elements in the array after the first window
        // Remove the leftmost element and add the rightmost element to the sum for each window
        while (right < nums.length) {
            sum = sum - nums[left] + nums[right];
            double average = sum / (double) k;
            maxSum = Math.max(maxSum, sum);
            left++;
            right++;
        }
        return (double) maxSum / k;
    }

    // Driver code
    public static void main(String[] args) {
        int[][] inputData = {
                {2, 4, 6, 8, 10, 1},
                {10, 5, 2, -1, 6, 3, -2, -4, 4, 1, -3, -6, -1, -2, -5, -7},
                {7, 3, 1, -2, 6, 2, -1, -3, 4, 1, -2, -5, 2, 0, -4, -6},
                {12, 9, 5, 2, 8, 6, 4, 1, 7, 5, 3, 0, 4, 2, 0, -3},
                {-10, -11, -12, -13, -20, -21, -22, -23, -30, -31, -32, -33, -40, -41, -42, -43},
                {5, 3, -2, -3, 4, 2, -3, -4, 3, 1, -4, -5, 2, 0, -5, -6}
        };

        int k = 4;

        for (int i = 0; i < inputData.length; i++) {
            int[] nums = inputData[i];
            double result = findMaxAverage(nums, k);
            System.out.print((i + 1) + ".\tInput: nums = {");
            for (int num : nums) {
                System.out.print(num + " ");
            }
            System.out.println("}, k = " + k);
            System.out.printf("\tMaximum Average: %.2f%n", result);
            System.out.println(new String(new char[100]).replace('\0', '-'));
        }
    }


}
