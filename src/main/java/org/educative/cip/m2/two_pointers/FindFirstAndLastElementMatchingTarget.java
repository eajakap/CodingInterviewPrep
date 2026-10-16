package org.educative.cip.m2.two_pointers;

import java.util.Arrays;

/**
 * Find the first and last positions of a target element in a sorted array.
 * Return [-1, -1] if the target is not found.
 * Time Complexity: O(log n) - Two binary searches, each O(log n).
 * Space Complexity: O(1) - We use a constant amount of space for variables.
 *
 */
public class FindFirstAndLastElementMatchingTarget {

    public int[] searchRange(int[] nums, int target) {
        int first = findBoundary(nums, target, true);
        int last = first == -1 ? -1 : findBoundary(nums, target, false);
        return new int[]{first, last};
    }

    /**
     * Binary search for an edge of the target's run of duplicates.
     * On a match we record the index and keep searching the side where a
     * better candidate could live: left for the first occurrence, right for the last.
     *
     * @param findFirst true to find the leftmost match, false for the rightmost
     * @return the index of the matching boundary, or -1 if the target is absent
     */
    private int findBoundary(int[] nums, int target, boolean findFirst) {
        int left = 0, right = nums.length - 1;
        int boundary = -1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (nums[mid] == target) {
                boundary = mid;
                if (findFirst) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return boundary;
    }

    public static void main(String[] args) {
        FindFirstAndLastElementMatchingTarget finder = new FindFirstAndLastElementMatchingTarget();
        int[][] inputs = {
                {5, 7, 7, 8, 8, 10},
                {5, 7, 7, 8, 8, 10},
                {1},
                {2, 2, 2, 2, 2},
                {1, 2, 3, 4, 5}
        };
        int[] targets = {8, 6, 1, 2, 3};

        for (int i = 0; i < inputs.length; i++) {
            int[] result = finder.searchRange(inputs[i], targets[i]);
            System.out.printf("Test Case %d: Input: %s, Target: %d => Output: %s%n",
                    i + 1, Arrays.toString(inputs[i]), targets[i], Arrays.toString(result));
        }
    }

}
