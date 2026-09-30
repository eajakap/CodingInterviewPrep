package org.educative.cip.m2.two_pointers;


import java.util.List;

/*
 * Given a list of integers representing the water levels of a river at different times,
 * find the maximum trailing level, which is defined as the maximum difference between
 * any two levels where the later level is greater than the earlier level.
 * For example, given the list [5, 3, 6, 7, 4], the maximum trailing level is 4 (7 - 3).
 * If no such pair exists (i.e., the levels are in non-increasing order), return -1.
 */
public class RiverRecordsMaxTrailingLevel {

    /**
     * Brute force approach to find the maximum trailing level in an array of integers.
     * Finds the maximum trailing level in an array of integers.
     * Time complexity: O(n^2), where n is the number of levels in the array.
     * Space complexity: O(1), as we are using a constant amount of extra space
     * @param levels an array of integers representing water levels
     * @return the maximum trailing level, or -1 if no such pair exists
     */
    public static int maxTrailingLevel(int[] levels) {
        int maxTrailing = -1;
        for (int i = 0; i < levels.length; i++) {
            for (int j = i + 1; j < levels.length; j++) {
                if (levels[j] > levels[i]) {
                    maxTrailing = Math.max(maxTrailing, levels[j] - levels[i]);
                }
            }
        }
        return maxTrailing;
    }

    /**
     * Optimized approach to find the maximum trailing level in a list of integers.
     * Finds the maximum trailing level in a list of integers.
     * Time complexity: O(n), where n is the number of levels in the list.
     * Space complexity: O(1), as we are using a constant amount of extra space
     * @param levels a list of integers representing water levels
     * @return the maximum trailing level, or -1 if no such pair exists
     */
    public static int maxTrailingLevel(List<Integer> levels) {

        if (levels == null || levels.size() < 2) {
            return -1; // no trailing possible
        }

        int minSoFar = levels.get(0);
        int maxDiff = -1;

        for (int i = 1; i < levels.size(); i++) {
            int current = levels.get(i);

            if (current > minSoFar) {
                maxDiff = Math.max(maxDiff, current - minSoFar);
            } else {
                minSoFar = current;
            }
        }

        return maxDiff;
    }

    public static void main(String[] args) {
        List<Integer> levels = List.of(5, 3, 6, 7, 4);
        System.out.println(maxTrailingLevel(levels)); // Output: 4
        int[] levelsArray = {5, 3, 6, 7, 4};
        System.out.println(maxTrailingLevel(levelsArray)); // Output: 4
    }
}
