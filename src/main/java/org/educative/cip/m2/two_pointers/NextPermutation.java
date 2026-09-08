package org.educative.cip.m2.two_pointers;

import java.util.*;
/*
 * Next Permutation
 * Problem: Given an array of integers, find the next lexicographical permutation of the array.
 * If such a permutation is not possible (i.e., the array is sorted in descending order),
 * rearrange the array to the lowest possible order (i.e., sorted in ascending order).
 * The replacement must be in place and use only constant extra memory.
 *
 * Steps to generate the next lexicographically larger permutation:
 * 1. Scan the array from right to left to find the first index `i` such that
 *    nums[i] < nums[i + 1]. This identifies the pivot — the position where
 *    the current permutation can be increased. If no such index exists,
 *    the array is strictly descending and represents the highest permutation.
 * 2. If a pivot is found, scan again from the right to find the first index `j`
 *    such that nums[j] > nums[i]. This element is the successor — the smallest
 *    number greater than the pivot within the suffix.
 * 3. Swap nums[i] (pivot) with nums[j] (successor). This increases the permutation
 *    minimally at position `i`.
 * 4. Reverse the subarray from following pivot index - `i + 1` to the end.
 *    The suffix was originally in descending order; reversing it produces the smallest lexicographical order
 *    for that suffix, ensuring the overall permutation is the next immediate one.
 * 5. If no pivot was found in step 1 (meaning the array is in descending order),
 *    reverse the entire array to obtain the lowest permutation (ascending order).
 *
 * Time Complexity: O(n) - We traverse the array once to find the next permutation.
 * Space Complexity: O(1) - We use a constant amount of space for pointers and swaps.
 */
public class NextPermutation {

    // Find the rightmost pivot index where chars[i] < chars[i + 1]
    // If no such index exists, return -1 indicating the array is in descending order
    private int pivotIndex(int[] nums) {
        int n = nums.length;
        // Start from the second last element and move leftwards
        for (int pivotIndex = n - 2; pivotIndex >= 0; pivotIndex--) {
            if (nums[pivotIndex] < nums[pivotIndex + 1]) {
                return pivotIndex;
            }
        }
        return -1;
    }

    // Find the smallest index to the right of pivotIndex where
    // chars[i] > chars[pivotIndex]
    private int pivotSwapIndex(int[] nums, int pivotIndex) {
        int n = nums.length;
        // Find the smallest index to the right of pivotIndex
        // where chars[i] > chars[pivotIndex]
        for (int swapIndex = n - 1; swapIndex > pivotIndex; swapIndex--) {
            if (nums[swapIndex] > nums[pivotIndex]) {
                return swapIndex;
            }
        }
        return -1; // No valid swap index found
    }

    // swap function to swap two elements in the array
    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    // reverse function to reverse a subarray from start to end indices
    private void reverse(int[] nums, int start, int end) {
        while (start < end) {
            swap(nums, start, end);
            start++;
            end--;
        }
    }

    public void nextPermutation2(int[] nums) {
        int length = nums.length;

        // Find the first element that is smaller than its next rightmost element from the end
        int pivot = pivotIndex(nums); // start from the second last element

        // If no pivot exists, the digits are in descending order and no larger permutation is possible.
        if (pivot < 0) return;

        // If such an element is found, find the first element that is larger
        // than it from the end
        int swapIdx = pivotSwapIndex(nums, pivot);

        // swap the two elements - pivot and successor
        swap(nums, pivot, swapIdx);


        // Reverse the elements to get the next permutation
        // start from the element next to pivot to the end of the array
        reverse(nums, pivot + 1, nums.length - 1);
    }

    public void nextPermutation(int[] nums) {
        int length = nums.length;

        // Find the first element that is smaller than its next rightmost element from the end
        int pivot = length - 2; // start from the second last element
        while (pivot >= 0 && nums[pivot] >= nums[pivot + 1]) {
            pivot--;
        }

        // If no pivot exists, the digits are in descending order and no larger permutation is possible.
        if (pivot < 0) return;

        // If such an element is found, find the first element that is larger
        // than it from the end
        int swapIdx = nums.length - 1;
        while (nums[swapIdx] <= nums[pivot]) {
            swapIdx--;
        }

        // swap the two elements - pivot and successor
        int temp = nums[pivot];
        nums[pivot] = nums[swapIdx];
        nums[swapIdx] = temp;


        // Reverse the elements to get the next permutation
        // start from the element next to pivot to the end of the array
        int left = pivot + 1, right = length - 1;
        while (left < right) {
            int tmp = nums[left];
            nums[left] = nums[right];
            nums[right] = tmp;
            left++;
            right--;
        }
    }


    public static void main(String[] args) {
        int[][] testCases = {
                {4, 1, 5, 2, 9, 7, 3},
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

            // Call the nextPermutation method to find the next permutation
            NextPermutation sol = new NextPermutation();
            sol.nextPermutation(testCases[i]);
            System.out.print("\t Next permutation: [");
            for (int j = 0; j < testCases[i].length; ++j) {
                System.out.print(testCases[i][j]);
                if (j != testCases[i].length - 1)
                    System.out.print(", ");
            }
            System.out.println("]");

            // Call the nextPermutation2 method to find the next permutation
            sol.nextPermutation2(testCases[i]);
            System.out.print("\t Next permutation-2: [");
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