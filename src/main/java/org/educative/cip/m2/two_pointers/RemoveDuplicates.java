package org.educative.cip.m2.two_pointers;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
/*
 * Problem: Remove duplicates from a sorted array in-place.
 * Given a sorted array nums, remove the duplicates in-place such that each element appears only once and returns the new length.
 * Do not allocate extra space for another array; you must do this by modifying the input array in-place with O(1) extra memory.
 * Steps to solve the problem:
 * 1. Initialize a pointer i to track the position of the last unique element found.
 * 2. Iterate through the array with another pointer j starting from the second element.
 * 3. For each element nums[j], compare it with nums[i]. If they are different, it means nums[j] is a unique element.
 * 4. Increment i and update nums[i] to nums[j] to store the unique element in the correct position.
 * 5. Continue this process until the end of the array is reached.
 * 6. The new length of the array will be i + 1, as i is zero-based.
 * 7. Return the new length of the array.
 *
 * Time Complexity: O(n) - We traverse the array once to remove duplicates.
 * Space Complexity: O(1) - We use a constant amount of space for pointers and swaps.
 */

public class RemoveDuplicates {
    public static int removeSortedArrayDuplicates(int[] nums) {

        // Replace this placeholder return statement with your code
        int i = 0;
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

    public static int removeUnSortedArrayDuplicates(int[] nums) {

        int left = 0;
        int right = nums.length - 1;

        while (left <= right) {

            // Check if nums[left] appears anywhere in nums[left+1 .. right]
            boolean isDuplicate = false;
            for (int i = left + 1; i <= right; i++) {
                if (nums[i] == nums[left]) {
                    isDuplicate = true;
                    break;
                }
            }

            if (isDuplicate) {
                // Duplicate found → remove it by swapping with the last valid element
                nums[left] = nums[right];
                right--;
            } else {
                // Unique → keep it
                left++;
            }
        }

        return left;  // number of unique elements
    }

    public static int removeUnSortedArrayDuplicatesWithSet(int[] nums) {
        Set<Integer> seen = new HashSet<>();
        int write = 0;

        for (int num : nums) {
            if (!seen.contains(num)) {
                seen.add(num);
                nums[write++] = num;
            }
        }
        return write;
    }

    public static int removeDuplicates(int[] nums) {
        // Step 1: Sort the array to bring duplicates together
        Arrays.sort(nums);

        // Step 2: Slow/Fast pointer technique
        return removeSortedArrayDuplicates(nums); // number of unique elements
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

            int k = removeSortedArrayDuplicates(arr);

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
