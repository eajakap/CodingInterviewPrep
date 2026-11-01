package org.educative.cip.m2.binary_search;

import java.util.Arrays;

/**
 * Search in Rotated Sorted Array — solved with the Modified Binary Search pattern.
 *
 * Given a sorted array of distinct integers that has been rotated at some unknown pivot
 * (for example, [1, 2, 3, 4, 5, 6, 7] rotated to [5, 6, 7, 1, 2, 3, 4]) and a target value,
 * return the index of the target, or -1 if it is not in the array.
 *
 * Example 1:
 * Input: nums = [5, 6, 7, 1, 2, 3, 4], target = 1
 * Output: 3
 */
class BinarySearchRotatedSolution {

    /**
     * Finds the index of {@code target} in a rotated sorted array.
     *
     * Approach: when a rotated array is split at any midpoint, at least one of the two halves
     * is still sorted. Each step checks which half is sorted, then checks whether the target
     * falls inside that half's range. If it does, the search continues in that half;
     * otherwise it continues in the other half. Either way, half the array is discarded.
     *
     * Time complexity: O(log n) — the search range halves on every step.
     * Space complexity: O(1) — only a few index variables are used.
     *
     * @param nums   a sorted array of distinct integers, rotated at some pivot
     * @param target the value to search for
     * @return the index of {@code target} in {@code nums}, or -1 if it is not present
     */
    public static int binarySearchRotated(int[] nums, int target) {
        // Search range is nums[low..high], inclusive on both ends
        int low = 0;
        int high = nums.length - 1;

        while (low <= high) {
            // Written this way instead of (low + high) / 2 to avoid integer overflow
            int mid = low + (high - low) / 2;
            if (nums[mid] == target)
                return mid;
            if (nums[low] <= nums[mid]) {
                // Left half nums[low..mid] is sorted
                if (nums[low] <= target && target < nums[mid]) {
                    // Target is inside the sorted left half: search there
                    high = mid - 1;
                } else
                    // Target is not in the left half: search the right half
                    low = mid + 1;
            } else {
                // Otherwise the right half nums[mid..high] is sorted
                if (nums[mid] < target && target <= nums[high])
                    // Target is inside the sorted right half: search there
                    low = mid + 1;
                else
                    // Target is not in the right half: search the left half
                    high = mid - 1;
            }
        }
        // Search range is empty, so the target is not in the array
        return -1;
    }

    /**
     * Driver code: runs {@link #binarySearchRotated(int[], int)} against sample inputs and
     * prints the results.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String args[]) {
        int[][] numArrays = {
                {5, 6, 7, 1, 2, 3, 4},
                {40, 50, 60, 10, 20, 30},
                {47, 58, 69, 72, 83, 94, 12, 24, 35},
                {77, 82, 99, 105, 5, 13, 28, 41, 56, 63},
                {48, 52, 57, 62, 68, 72, 5, 7, 12, 17, 21, 28, 33, 37, 41}
        };

        // targetArray[i] is the value to search for in numArrays[i]
        int[] targetArray = {1, 50, 12, 56, 5};

        for (int i = 0; i < targetArray.length; i++) {
            System.out.println((i + 1) + ".\tRotated array: " + Arrays.toString(numArrays[i]) +
                    "\n\ttarget " + targetArray[i] +
                    " found at index " + binarySearchRotated(numArrays[i], targetArray[i]));
            // Print a separator line of 100 dashes
            System.out.println(new String(new char[100]).replace('\0', '-'));
        }
    }
}
