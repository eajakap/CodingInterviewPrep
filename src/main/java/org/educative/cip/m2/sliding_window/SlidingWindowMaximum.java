package org.educative.cip.m2.sliding_window;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.stream.Stream;

/**
 * Sliding Window Maximum
 * Given an array of integers and a window size, find the maximum value in each sliding window.
 * For example, given the array [1, 3, -1, -3, 5, 3, 6, 7] and a window size of 3,
 * the maximum values in each sliding window are [3, 3, 5, 5, 6, 7].
 *
 * Steps to solve the problem:
 * 1. Initialize a deque to store indices of elements in the current window.
 * 2. Iterate through the array:
 *    a. Remove indices that are out of the current window from the front of the deque.
 *    b. Remove indices of elements that are smaller than the current element from the back of the deque.
 *    c. Add the current index to the back of the deque.
 *    d. If the current index is greater than or equal to the window size -1,
 *       add the element at the front of the deque to the result array as it is the maximum of the current window.
 * 3. Return the result array containing the maximum values for each sliding window.
 *
 * Time Complexity: O(n) - We traverse the array once, and each element is added and removed from the deque at most once.
 * Space Complexity: O(n-w) - The deque can hold at most n-w elements, where w is the window size.
 */
public class SlidingWindowMaximum {
    private enum SolutionType {
        BRUTE_FORCE,
        OPTIMIZED
    }

    /*
     * This is Brute Force solution with O(n * w) time complexity,
     * where n is the length of the input array and w is the window size.
     * 1. Initialize two pointers, left and right, to represent the current window.
     * 2. Iterate through the array while the right pointer is less than the length of the array:
     *    a. For each window, find the maximum value by iterating from left to right and comparing the elements.
     *    b. Store the maximum value in the result array at the index corresponding to the left pointer.
     *    c. Increment both left and right pointers to slide the window to the right.
     * 3. Return the result array containing the maximum values for each sliding window.
     * Time Complexity: O(n * w) - For each of the n-w+1 windows, we perform a linear scan of size w to find the maximum.
     * Space Complexity: O(n-w) - The result array holds the maximum values for each sliding window, which is of size n-w+1.
     */
    public static int[] findMaxSlidingWindowBruteForce(int[] nums, int w) {
        if ( w <= 0 || nums == null || nums.length == 0) {
            return new int[0];
        }
        if (w > nums.length) {
            w = nums.length;
        }
        int left = 0;
        int right = w-1;
        int[] result = new int[nums.length - w + 1];
        while (right < nums.length) {
            int max = Integer.MIN_VALUE;
            for (int i = left; i <= right; i++) {
                max = Math.max(max, nums[i]);
            }
            result[left] = max;      // left is already the output index
            left++;
            right++;
        }
        return result;
    }

    /*
     * This is an optimized solution with O(n) time complexity,
     * where n is the length of the input array.
     * We use a deque to keep track of the indices of the maximum elements in the current window.
     * Steps:
     * 1. Initialize a deque to store indices of elements in the current window.
     * 2. Iterate through the array:
     *    a. Remove indices that are out of the current window from the front of the deque.
     *    b. Remove indices of elements that are smaller than the current element from the back of the deque.
     *    c. Add the current index to the back of the deque.
     *    d. If the current index is greater than or equal to the window size -1,
     *       add the element at the front of the deque to the result array as it is the maximum of the current window.
     * 3. Return the result array containing the maximum values for each sliding window.
     *
     * Time Complexity: O(n) - We traverse the array once, and each element is added and removed from the deque at most once.
     * Space Complexity: O(n-w) - The deque can hold at most n-w elements, where w is the window size.
     */
    public static int[] findMaxSlidingWindow(int[] nums, int w) {
        if (nums == null || nums.length == 0 || w <= 0) return new int[0];
        int n = nums.length;
        if (n == 1) return nums;
        if (w > n) w = n;

        int currentWindowIndex = 0;
        int[] result = new int[n - w + 1]; // result array to store the maximum values for each sliding window
        Deque<Integer> deque = new ArrayDeque<>();   // indices, values decreasing

        for (int i = 0; i < n; i++) {
//            System.out.println("\tIn:i = " + i + ", windowIndex = " + currentWindowIndex + ", deque = " + deque + ", result = " + Arrays.toString(result));
            // 1. front has slid out of the window
            if (!deque.isEmpty() && deque.peekFirst() <= i - w) {
                deque.pollFirst(); // remove the index of the element that is out of the current window
            }
            // 2. anything smaller than nums[i] can never be a max again
            while (!deque.isEmpty() && nums[deque.peekLast()] <= nums[i]) {
                deque.pollLast(); // remove the index of the element that is smaller than the current element
            }
            deque.offerLast(i); // add the index of the current element to the deque

            // 3. first full window ends at index w-1
            if (i >= w - 1) {
                // the first element in the deque is the index of the maximum element in the current window
                result[i - w + 1] = nums[deque.peekFirst()];
                currentWindowIndex++;
            }
//            System.out.println("\tOut:i = " + i + ", windowIndex = " + currentWindowIndex + ", deque = " + deque + ", result = " + Arrays.toString(result));
        }
        return result;
    }

    public static int[] findMaxSlidingWindow(int[] nums, int w, SolutionType solutionType) {
        switch (solutionType) {
            case BRUTE_FORCE:
                return findMaxSlidingWindowBruteForce(nums, w);
            case OPTIMIZED:
                return findMaxSlidingWindow(nums, w);
            default:
                throw new IllegalArgumentException("Invalid solution type: " + solutionType);
        }
    }

    public static void main(String args[]) {
        int windowSizes [] = {3, 3, 3, 3, 2, 4, 3, 2, 3, 6, 3, 1};
        int [][] numLists = {
                {1, 2, 3, 4, 5, 6, 7, 8, 9, 10},
                {10, 9, 8, 7, 6, 5, 4, 3, 2, 1},
                {10, 10, 10, 10, 10, 10, 10, 10, 10, 10},
                {1, 5, 8, 10, 10, 10, 12, 14, 15, 19, 19, 19, 17, 14, 13, 12, 12, 12, 14, 18, 22, 26, 26, 26, 28, 29, 30},
                {10, 6, 9, -3, 23, -1, 34, 56, 67, -1, -4, -8, -2, 9, 10, 34, 67},
                {4, 5, 6, 1, 2, 3},
                {9, 5, 3, 1, 6, 3},
                {2, 4, 6, 8, 10, 12, 14, 16},
                {-1, -1, -2, -4, -6, -7},
                {4, 4, 4, 4, 4, 4},
                {2, 4, 3, 6, 5, 4, 1, 10},
                {10}
        };

        for (int i = 0; i < numLists.length; i++) {
            System.out.println(i + 1 + ".\tInput array:\t" + Arrays.toString(numLists[i]));
            System.out.println("\tWindow size:\t" + windowSizes[i]);
            System.out.println("\tMaximum in each sliding window (optimized):\t" + Arrays.toString(findMaxSlidingWindow(numLists[i], windowSizes[i], SolutionType.OPTIMIZED)));
            System.out.println("\tMaximum in each sliding window (brute force):\t" + Arrays.toString(findMaxSlidingWindow(numLists[i], windowSizes[i], SolutionType.BRUTE_FORCE)));
            Stream.generate(() -> "-").limit(100).forEach(System.out::print);
            System.out.println();
        }
    }

}
