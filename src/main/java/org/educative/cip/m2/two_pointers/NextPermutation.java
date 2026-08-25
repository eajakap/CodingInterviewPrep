package org.educative.cip.m2.two_pointers;

import java.util.*;

public class NextPermutation {
    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    public void nextPermutation(int[] nums) {
        int i = nums.length - 2;

        while (i >= 0 && nums[i + 1] <= nums[i]) {
            i--;
        }

        if (i >= 0) {
            int j = nums.length - 1;
            while (nums[j] <= nums[i]) {
                j--;
            }

            swap(nums, i, j);
        }

        reverse(nums, i + 1, nums.length - 1);
    }

    private void reverse(int[] nums, int start, int end) {
        while (start < end) {
            swap(nums, start, end);
            start++;
            end--;
        }
    }

    public static void main(String[] args) {
        int[][] testCases = {
                {4, 1, 5, 2, 9, 3, 7},
                {8, 2, 6, 4, 7, 5},
                {7, 6, 4, 3, 1},
                {2, 6, 8, 7, 8, 7, 9, 4, 1, 2, 4, 5, 8},
                {1, 2}
        };

        for (int i = 0; i < testCases.length; ++i) {
            System.out.print((i + 1) + ".\t Original array: [");
            for (int j = 0; j < testCases[i].length; ++j) {
                System.out.print(testCases[i][j]);
                if (j != testCases[i].length - 1)
                    System.out.print(", ");
            }
            System.out.println("]");

            NextPermutation sol = new NextPermutation();
            sol.nextPermutation(testCases[i]);

            System.out.print("\t Next permutation: [");
            for (int j = 0; j < testCases[i].length; ++j) {
                System.out.print(testCases[i][j]);
                if (j != testCases[i].length - 1)
                    System.out.print(", ");
            }
            System.out.println("]");

            System.out.println("-".repeat(100));
        }
    }
}