package org.educative.cip.m2.two_pointers;

import java.util.Arrays;
/*
 * Time Complexity: O(n) - We traverse the array once to sort the colors.
 * Space Complexity: O(1) - We use a constant amount of space for pointers and swaps.
 */
public class SortColors {
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