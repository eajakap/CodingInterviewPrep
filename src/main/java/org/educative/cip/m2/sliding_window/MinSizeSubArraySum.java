package org.educative.cip.m2.sliding_window;

import java.util.Arrays;

/*
 * Time Complexity: O(n) - We traverse the array to find the minimum length of subarray.
 * Space Complexity: O(1) - We use a constant amount of space for pointers.
 */
public class MinSizeSubArraySum {

    public static int minSubArrayLen(int target, int[] nums) {
        int windowSize = Integer.MAX_VALUE;
        int left = 0;
        int sum = 0;
        int end = nums.length;
        int right = 0;
        while (right < end) {
            sum += nums[right];
            while (sum >= target) {
                windowSize = Math.min(windowSize, right - left + 1);
                // shrink the window from the left
                sum -= nums[left];
                left++;
            }
            // expand the window to the right
            right++;
        }
        return windowSize == Integer.MAX_VALUE ? 0 : windowSize;
    }

    // Driver code
    public static void main(String[] args) {
        int[] target = {7, 4, 11, 10, 5, 15};
        int[][] inputArr = {
                {2, 3, 1, 2, 4, 3},
                {1, 4, 4},
                {1, 1, 1, 1, 1, 1, 1, 1},
                {1, 2, 3, 4},
                {1, 2, 1, 3},
                {5, 4, 9, 8, 11, 3, 7, 12, 15, 44}
        };
        for (int i = 0; i < target.length; i++) {
            int windowSize = minSubArrayLen(target[i], inputArr[i]);
            System.out.print((i + 1) + ".\tInput array: " + Arrays.toString(inputArr[i]));
            System.out.print("\n\tTarget: " + target[i]);
            System.out.println("\n\tMinimum Length of Subarray: " + windowSize);
            System.out.println(new String(new char[100]).replace('\0', '-'));
        }
    }

}
