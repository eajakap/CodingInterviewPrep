package org.educative.cip.m2.two_pointers;

/**
 * Time Complexity: O(n), where n is the number of digits in the input integer.
 * We traverse through the digits once to find the pivot and swap, and then reverse the suffix.
 * Space Complexity: O(n), as we are using a char array to store the digits of the integer.
 * Given a positive integer n, find the smallest integer which has exactly the same digits existing in the integer n and is greater in value than n. If no such positive integer exists, return -1.
 */
public class NextGreaterElementIII {

    public int nextGreaterElement(int n) {
        // Convert integer to char array of digits for manipulation
        char[] digits = Integer.toString(n).toCharArray();
        int length = digits.length;
        System.out.println("Input Digits: " + new String(digits));

        // Step 1: Find the rightmost digit smaller than the digit to its right (pivot)
        // Find the pivot by scanning from right to left for the first position where a digit is
        // smaller than the digit immediately to its right.
        int pivot = length - 2;
        while (pivot >= 0 && digits[pivot] >= digits[pivot + 1]) {
            pivot--;
        }
        System.out.println("Pivot index: " + pivot + ", Pivot value: " + (pivot >= 0 ? digits[pivot] : "None"));

        // If no pivot found, no greater permutation is possible
        if (pivot < 0) return -1;

        // Step 2: Find the smallest digit to the right of pivot that is greater than digits[pivot]
        // Find the swap candidate by scanning from the end of the array toward the pivot until finding
        // the first digit greater than the pivot digit.
        int swapIdx = length - 1;
        while (digits[swapIdx] <= digits[pivot]) {
            swapIdx--;
        }
        System.out.println("Swap index: " + swapIdx + ", Swap value: " + digits[swapIdx]);

        // Step 3: Swap the pivot digit with the found digit
        char temp = digits[pivot];
        digits[pivot] = digits[swapIdx];
        digits[swapIdx] = temp;
        System.out.println("Digits after swap: " + new String(digits));

        // Step 4: Reverse the suffix to the right of pivot
        int left = pivot + 1, right = length - 1;
        while (left < right) {
            char tmp = digits[left];
            digits[left] = digits[right];
            digits[right] = tmp;
            left++;
            right--;
        }
        System.out.println("Digits after reversing suffix: " + new String(digits));

        // Convert back to long to safely check 32-bit signed integer range
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
            System.out.println("-".repeat(100));
        }
    }
}