package org.educative.cip.m2.two_pointers;

import java.util.Arrays;
/*
 * Time Complexity: O(n) - We traverse the array once to sort the colors.
 * Space Complexity: O(1) - We use a constant amount of space for pointers and swaps.
 */
public class SortColors {
    /**
     * Sorts an array of colors represented by integers (0, 1, 2) in-place.
     * The function uses the Dutch National Flag algorithm to sort the colors in a single pass.
     * Steps:
     * 1. Initialize three pointers: low, mid, and high.
     * 2. Traverse the array with the mid pointer:
     *    - If the current element is 0, swap it with the element at the low pointer and move both pointers forward.
     *    - If the current element is 1, move the mid pointer forward.
     *    - If the current element is 2, swap it with the element at the high pointer and move the high pointer backward.
     * 3. Continue until the mid pointer surpasses the high pointer, ensuring all colors are sorted.
     *
     * @param colors An array of integers where each integer represents a color (0, 1, or 2).
     * @return The sorted array of colors.
     */
    public static int[] sortColors (int[] colors) {
        int low = 0;
        int mid = 0;
        int high = colors.length - 1;

        while (mid <= high) {
            if (colors[mid] == 0) {
                int temp = colors[low];
                colors[low] = colors[mid];
                colors[mid] = temp;
                low++;
                mid++;
            } else if (colors[mid] == 1) {
                mid++;
            } else {
                int temp = colors[mid];
                colors[mid] = colors[high];
                colors[high] = temp;
                high--;
            }
        }
        return colors;
    }

    public static void main(String[] args) {
        int[][] testCases = {
                {1, 2, 0},
                {0},
                {2, 2, 1, 1, 0, 0},
                {1, 0, 2, 1, 0, 2, 1},
                {2, 1, 0, 0, 1, 2, 2, 0, 1},
        };

        for (int i = 0; i < testCases.length; i++) {
            int[] nums = testCases[i];
            int[] inputCopy = nums.clone();
            SortColors.sortColors(nums);
            System.out.println((i + 1) + ".\tInput array: " + Arrays.toString(inputCopy));
            System.out.println("\tResult: " + Arrays.toString(nums));
            System.out.println("-".repeat(100));
        }
    }
}