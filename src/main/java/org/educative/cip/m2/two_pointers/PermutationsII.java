package org.educative.cip.m2.two_pointers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Problem: Given an integer array nums that may contain duplicates, return all possible unique permutations.
 * The result must contain no duplicate permutations and is printed in lexicographical order,
 * one permutation per line, numbers space-separated.
 * Constraints: 1 <= n <= 8, -10 <= nums[i] <= 10
 * Example:
 * Input:
 * 3
 * 1 1 2
 * Output:
 * 1 1 2
 * 1 2 1
 * 2 1 1
 * Steps to solve the problem:
 * 1. Sort the array; this is the lexicographically smallest permutation.
 * 2. Repeatedly generate the next permutation with two pointers:
 *    a. From the right, find the first index i with nums[i] < nums[i + 1] (the pivot). If none, stop.
 *    b. From the right, find the first index j with nums[j] > nums[i] and swap them.
 *    c. Reverse the suffix after i.
 * 3. The strict comparisons skip equal values, so every permutation is produced exactly once and in order.
 * Time Complexity: O(n * P) where P is the number of unique permutations (at most n!)
 * Space Complexity: O(1) beyond the output
 */
public class PermutationsII {
    public static List<List<Integer>> permuteUnique(int[] nums) {
        int[] arr = nums.clone();
        Arrays.sort(arr);
        List<List<Integer>> result = new ArrayList<>();
        do {
            List<Integer> perm = new ArrayList<>(arr.length);
            for (int num : arr) {
                perm.add(num);
            }
            result.add(perm);
        } while (nextPermutation(arr));
        return result;
    }

    // swap function to swap two elements in the array
    private static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    // reverse function to reverse a subarray from start to end indices
    private static void reverse(int[] nums, int start, int end) {
        while (start < end) {
            swap(nums, start, end);
            start++;
            end--;
        }
    }

    private static boolean nextPermutation(int[] nums) {
        int length = nums.length;

        // Find the first element starting from the second last element (n-2)
        // that is smaller than its next rightmost element from the end
        int pivotIndex = length - 2; // start from the second last element
        while (pivotIndex >= 0 && nums[pivotIndex] >= nums[pivotIndex + 1]) {
            pivotIndex--;
        }

        // If no pivot exists, the digits are in descending order and no larger permutation is possible.
        if (pivotIndex < 0) {
            // reverse entire array (highest permutation → lowest)
            reverse(nums, 0, length - 1);
            return false;
        }

        // At this point, we have found the pivot element that is smaller than its next immediate right neighbour.
        // If such an element is found, find the first element that is larger than the
        // pivot index element from the end (n-1).
        int pivotSwapIdx = length - 1;
        while (nums[pivotSwapIdx] <= nums[pivotIndex]) {
            pivotSwapIdx--;
        }

        // swap the two elements - pivot Index Element and the pivot Swap Index Element
        swap(nums, pivotIndex, pivotSwapIdx);

        // Reverse the elements to get the next permutation
        // start from the element next to pivot to the end of the array
        int left = pivotIndex + 1, right = length - 1;
        while (left < right) {
            // swap the elements at left and right indices
            swap(nums, left, right);
            left++; // move left pointer to the next element
            right--; // move right pointer to the previous element
        }
        return true;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = scanner.nextInt();
        }
        StringBuilder out = new StringBuilder();
        for (List<Integer> perm : permuteUnique(nums)) {
            for (int k = 0; k < perm.size(); k++) {
                if (k > 0) {
                    out.append(' ');
                }
                out.append(perm.get(k));
            }
            out.append('\n');
        }
        System.out.print(out);
    }
}
