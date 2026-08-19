package org.educative.cip.m2.two_pointers;

import java.util.Arrays;

public class RemoveDuplicates {
    public static int removeDuplicates(int[] nums) {

        // Replace this placeholder return statement with your code
        int i = 0;
        int second = 1;
        for (int j = 1; j < nums.length; j++) {
            if  (nums[i] != nums[j]) {
                // unique element
                i++;
                // in place insertion
                nums[i]=nums[j];
            }
            // duplicate found - find next unique
        }
        return i+1;
    }

    public static void main(String[] args) {
        int[][] testCases = {
                {1, 1, 2, 2, 3},
                {-1, -1, 0, 0, 1, 1, 2},
                {5, 5, 5, 5},
                {1, 2, 3, 4},
                {0}
        };

        for (int idx = 0; idx < testCases.length; idx++) {
            int[] nums = testCases[idx];

            System.out.println((idx + 1) + ".\tnums: " + Arrays.toString(nums));

            // because function modifies in-place
            int[] arr = Arrays.copyOf(nums, nums.length);

            int k = removeDuplicates(arr);

            System.out.println("\n\tUnique Count (k): " + k);

            System.out.print("\tArray After Removing Duplicates: [");
            for (int i = 0; i < k; i++) {
                if (i > 0) System.out.print(", ");
                System.out.print(arr[i]);
            }
            System.out.println("]");
            System.out.println("----------------------------------------------------------------------------------------------------");
        }
    }

}
