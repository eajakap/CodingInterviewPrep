package org.educative.cip.m2.sliding_window;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.stream.Stream;

/**
 * Sliding Window Minimum
 * Given an array of integers and a window size, find the minimum value in each sliding window.
 * For example, given the array [1, 3, -1, -3, 5, 3, 6, 7] and a window size of 3,
 * the minimum values in each sliding window are [1, -1, -3, -3, 3, 3].
 *
 * Steps to solve the problem:
 * 1. Initialize a deque to store indices of elements in the current window.
 * 2. Iterate through the array:
 *    a. Remove indices from the front of the deque that are out of the current window.
 *    b. Remove indices from the back of the deque while the current element is smaller than the elements at those indices.
 *    c. Add the current index to the back of the deque.
 *    d. If the current index is greater than or equal to the window size - 1,
 *    add the element at the front of the deque to the result array as it represents the minimum of the current window.
 * 3. Return the result array containing the minimum values for each sliding window.
 *
 * Time Complexity: O(n) - We traverse the array once, and each element is added and removed from the deque at most once.
 * Space Complexity: O(n-w) - The deque can hold at most n-w elements, where w is the window size.
 */
public class SlidingWindowMinimum {
    private enum SolutionType {
        BRUTE_FORCE,
        OPTIMIZED
    }

    /*
     * This is Brute Force solution with O(n * w) time complexity,
     * where n is the length of the input array and w is the window size.
     */
    public static int[] findMinSlidingWindowBruteForce(int[] nums, int w) {
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
            int min = Integer.MAX_VALUE;
            for (int i = left; i <= right; i++) {
                min = Math.min(min, nums[i]);
            }
            result[left] = min;      // left is already the output index
            left++;
            right++;
        }
        return result;
    }

    /*
     * This is an optimized solution with O(n) time complexity,
     * where n is the length of the input array.
     * We use a deque to keep track of the indices of the minimum elements in the current window.
     */
    // This is an optimized solution with O(n) time complexity,
    // where n is the length of the input array.
    // We use a deque to keep track of the indices of the minimum elements in the current window.

    public static int[] findMinSlidingWindow(int[] nums, int w) {
        if (nums == null || nums.length == 0 || w <= 0) return new int[0];
        int n = nums.length;
        if (n == 1) return nums;
        if (w > n) w = n;

        int currentWindowIndex = 0;
        int[] result = new int[n - w + 1];
        Deque<Integer> deque = new ArrayDeque<>();   // indices, values increasing

        for (int i = 0; i < n; i++) {
//            System.out.println("\tIn:i = " + i + ", windowIndex = " + currentWindowIndex + ", deque = " + deque + ", result = " + Arrays.toString(result));
            // 1. front has slid out of the window
            if (!deque.isEmpty() && deque.peekFirst() <= i - w) {
                deque.pollFirst(); // remove the index of the element that is out of the current window
            }
            // 2. anything larger than nums[i] can never be a min again
            while (!deque.isEmpty() && nums[deque.peekLast()] >= nums[i]) {
                deque.pollLast(); // remove the index of the element that is larger than the current element
            }
            deque.offerLast(i); // add the index of the current element to the deque

            // 3. first full window ends at index w-1
            if (i >= w - 1) {
                // the first element in the deque is the index of the minimum element in the current window
                result[i - w + 1] = nums[deque.peekFirst()];
                currentWindowIndex++;
            }
//            System.out.println("\tOut:i = " + i + ", windowIndex = " + currentWindowIndex + ", deque = " + deque + ", result = " + Arrays.toString(result));
        }
        return result;
    }

    public static int[] findMinSlidingWindow(int[] nums, int w, SolutionType solutionType) {
        switch (solutionType) {
            case BRUTE_FORCE:
                return findMinSlidingWindowBruteForce(nums, w);
            case OPTIMIZED:
                return findMinSlidingWindow(nums, w);
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
            System.out.println("\tMinimum in each sliding window (optimized):\t" + Arrays.toString(findMinSlidingWindow(numLists[i], windowSizes[i], SolutionType.OPTIMIZED)));
            System.out.println("\tMinimum in each sliding window (brute force):\t" + Arrays.toString(findMinSlidingWindow(numLists[i], windowSizes[i], SolutionType.BRUTE_FORCE)));
            Stream.generate(() -> "-").limit(100).forEach(System.out::print);
            System.out.println();
        }
    }

}
