package org.educative.cip.m2.two_pointers;

/**
 * Problem: Next Greater Element III
 * Given a positive integer n, find the smallest integer greater than n that is composed of
 * the same digits as n. If no such number exists, return -1.
 *
 * Example 1:
 * Input: n = 12
 * Output: 21
 *
 * Example 2:
 * Input: n = 21
 * Output: -1
 *
 * Constraints:
 * 1 <= n <= 2^31 - 1
 *
 * Approach:
 * 1. Convert the number to a character array so its digits can be rearranged.
 * 2. Find the rightmost pivot digit that is smaller than the digit to its right.
 * 3. If no pivot exists, then no greater permutation is possible and we return -1.
 * 4. Find the smallest digit to the right of the pivot that is greater than the pivot.
 * 5. Swap them and reverse the suffix to the right of the pivot to create the smallest possible larger number.
 * 6. Convert the result back to an integer and ensure it fits in a 32-bit signed integer.
 *
 * Time Complexity: O(n), where n is the number of digits in the input integer.
 * Space Complexity: O(n), because we store the digits in a character array.
 */
public class NextGreaterElementIII {

    // Find the rightmost pivot index where chars[i] < chars[i + 1]
    private int pivotIndex(char[] chars) {
        int n = chars.length;
        // Find the rightmost pivot index where nums[i] < nums[i + 1]
        // starts from the second last index and moves leftwards
        for (int pivotIndex = n - 2; pivotIndex >= 0; pivotIndex--) {
            if (chars[pivotIndex] < chars[pivotIndex + 1]) {
                return pivotIndex;
            }
        }
        return -1; // No pivot found
    }

    // Find the smallest index to the right of pivotIndex where
    // chars[i] > chars[pivotIndex]
    private int pivotSwapIndex(char[] chars, int pivotIndex) {
        int n = chars.length;
        // Find the smallest index to the right of pivotIndex
        // where chars[i] > chars[pivotIndex]
        for (int swapIndex = n - 1; swapIndex > pivotIndex; swapIndex--) {
            if (chars[swapIndex] > chars[pivotIndex]) {
                return swapIndex;
            }
        }
        return -1; // No valid swap index found
    }

    // Swap two characters in the array
    private void swap(char[] chars, int i, int j) {
        char temp = chars[i];
        chars[i] = chars[j];
        chars[j] = temp;
    }

    // Reverse the characters in the array from start to end indices
    private void reverse(char[] chars, int start, int end) {
        while (start < end) {
            swap(chars, start, end);
            start++;
            end--;
        }
    }

    public int nextGreaterElement2(int n) {
        // Convert the number to a char array so digits can be rearranged.
        char[] chars = Integer.toString(n).toCharArray();

        // Find the rightmost pivot index where chars[i] < chars[i + 1]
        int pivotIndex = pivotIndex(chars);
        if (pivotIndex == -1) {
            return -1; // No greater permutation possible
        }

        // Find the smallest index to the right of pivotIndex
        // where chars[i] > chars[pivotIndex]
        int swapIndex = pivotSwapIndex(chars, pivotIndex);

        // Swap the pivot and the selected digit
        swap(chars, pivotIndex, swapIndex);

        // Reverse the suffix to produce the smallest possible larger number
        reverse(chars, pivotIndex + 1, chars.length - 1);

        // Convert back to a long to safely check the 32-bit signed integer range
        long result = Long.parseLong(new String(chars));
        return result <= Integer.MAX_VALUE ? (int) result : -1;
    }
    /**
     * Returns the smallest integer greater than n that uses the same digits as n.
     *
     * @param n the input value
     * @return the next greater permutation, or -1 if no valid result exists
     */
    public int nextGreaterElement(int n) {
        // Convert the number to a char array so digits can be rearranged.
        char[] digits = Integer.toString(n).toCharArray();
        int length = digits.length;

        // Find the rightmost pivot digit that is smaller than the digit to its right.
        int pivot = length - 2;
        while (pivot >= 0 && digits[pivot] >= digits[pivot + 1]) {
            pivot--;
        }

        // If no pivot exists, the digits are in descending order and no larger permutation is possible.
        if (pivot < 0) return -1;

        // Find the smallest digit to the right of the pivot that is greater than the pivot.
        int swapIdx = length - 1;
        while (digits[swapIdx] <= digits[pivot]) {
            swapIdx--;
        }

        // Swap the pivot and the selected digit.
        char temp = digits[pivot];
        digits[pivot] = digits[swapIdx];
        digits[swapIdx] = temp;

        // Reverse the suffix to produce the smallest possible larger number.
        int left = pivot + 1, right = length - 1;
        while (left < right) {
            char tmp = digits[left];
            digits[left] = digits[right];
            digits[right] = tmp;
            left++;
            right--;
        }

        // Convert back to a long to safely check the 32-bit signed integer range.
        long result = Long.parseLong(new String(digits));
        return result <= Integer.MAX_VALUE ? (int) result : -1;
    }

    public static void main(String[] args) {
        NextGreaterElementIII sol = new NextGreaterElementIII();
        int[] testCases = {2302431, 1234, 4321, 534976, 2147483647, 1999999999};

        for (int i = 0; i < testCases.length; i++) {
            int n = testCases[i];
            int result = sol.nextGreaterElement(n);
            System.out.println((i + 1) + ".\tn = " + n);
            System.out.println("\tResult: " + result);
            int result2 = sol.nextGreaterElement2(n);
            System.out.println("-".repeat(50));
            System.out.println("\tResult2: " + result2);
            System.out.println("-".repeat(100));
        }
    }
}