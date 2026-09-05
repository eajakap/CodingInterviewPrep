package org.educative.cip.m2.fast_slow_pointers;

import java.util.Arrays;

import java.util.*;
/*
    * Problem: Circular Array Loop
    * Given a circular array of integers, determine if there is a cycle in the array. A cycle must be "forward" or "backward" and must have more than one element.
    * Explore techniques to detect circular loops in an array of integers using fast and slow pointers.
    * Understand how to identify cycles where all steps move in one direction, mastering a common problem pattern for coding interviews.
    * Approach:
    * 1. Use two pointers (slow and fast) to traverse the array.
    * 2. For each element, check if it can be part of a cycle by moving the slow pointer one step and the fast pointer two steps.
    * 3. If the slow and fast pointers meet, a cycle exists.
    * 4. Ensure that the direction of movement is consistent (all positive or all negative) and that the cycle has more than one element.
    *
    * Example:
    * Input: [2, -1, 1, 2, 2]
    * Output: true (There is a cycle: index 0 -> index 2 -> index 3 -> index 0)
    *
 * Time Complexity: O(n^2) - We traverse the array to detect a cycle and there is while loop too.
 * Space Complexity: O(1) - We use a constant amount of space for pointers.
 */

public class CircularArrayLoop {

    public static boolean circularArrayLoop(int[] nums) {
        int size = nums.length;

        for (int i = 0; i < size; i++) {
            int slow = i, fast = i;
            boolean forward = nums[i] > 0;

            while (true) {
                slow = nextStep(slow, nums[slow], size);

                if (isNotCycle(nums, forward, slow))
                    break;

                fast = nextStep(fast, nums[fast], size);

                if (isNotCycle(nums, forward, fast))
                    break;

                fast = nextStep(fast, nums[fast], size);

                if (isNotCycle(nums, forward, fast))
                    break;

                if (slow == fast)
                    return true;
            }
        }

        return false;
    }

    // A function to calculate the next step
    public static int nextStep(int pointer, int value, int size) {
        int result = (pointer + value) % size;
        if (result < 0)
            result += size;
        return result;
    }

    // A function to detect a cycle doesn't exist
    public static boolean isNotCycle(int[] nums, boolean prevDirection, int pointer) {
        boolean currDirection = nums[pointer] >= 0;

        // If the direction changes or the next step is a self-loop, return true (not a cycle)
        if (prevDirection != currDirection || nums[pointer] % nums.length == 0) {
            return true;
        }
        // If the direction is consistent and the next step is not a self-loop, return false (potential cycle)
        return false;
    }
    // Driver code
    public static void main(String[] args) {
        int[][] input = {
                {-2, -3, -9},
                {-5, -4, -3, -2, -1},
                {-1, -2, -3, -4, -5},
                {2, 1, -1, -2},
                {-1, -2, -3, -4, -5, 6},
                {1, 2, -3, 3, 4, 7, 1},
                {2, 2, 2, 7, 2, -1, 2, -1, -1}
        };

        for (int i = 0; i < input.length; i++) {
            System.out.println((i + 1) + ".\tCircular array = " + Arrays.toString(input[i]) + "\n");
            boolean result = circularArrayLoop(input[i]);
            System.out.println("\tFound Loop = " + result);
            System.out.println(new String(new char[100]).replace('\0', '-'));
        }
    }
}