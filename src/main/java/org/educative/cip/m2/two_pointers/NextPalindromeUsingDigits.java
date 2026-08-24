package org.educative.cip.m2.two_pointers;
import java.util.*;

/*
    * Time Complexity: O(n) - We traverse the digits to find the next permutation and then construct the palindrome.
    * Space Complexity: O(n) - We use a list to store the left half of the digits.
 */
public class NextPalindromeUsingDigits {

    private static boolean nextPermutation(char[] a) {
        int i = a.length - 2;
        while (i >= 0 && a[i] >= a[i + 1]) i--;
        if (i < 0) return false;

        int j = a.length - 1;
        while (a[j] <= a[i]) j--;

        swap(a, i, j);
        reverse(a, i + 1, a.length - 1);
        return true;
    }

    private static void swap(char[] a, int i, int j) {
        char t = a[i]; a[i] = a[j]; a[j] = t;
    }

    private static void reverse(char[] a, int l, int r) {
        while (l < r) swap(a, l++, r--);
    }

    public static String nextPalindromeUsingSameDigits(String s) {
        int n = s.length();
        char[] arr = s.toCharArray();

        int mid = n / 2;
        boolean isOdd = (n % 2 == 1);

        char[] left = Arrays.copyOfRange(arr, 0, mid);

        if (!nextPermutation(left)) {
            return "-1"; // no next palindrome
        }

        StringBuilder sb = new StringBuilder();
        sb.append(left);

        if (isOdd) {
            sb.append(arr[mid]); // middle digit unchanged
        }

        // mirror left
        for (int i = left.length - 1; i >= 0; i--) {
            sb.append(left[i]);
        }

        return sb.toString();
    }

    static class Solution {
        public static boolean findNextPermutation(List<Character> digits) {
            // Start from the end of leftHalf and move left to find the first index i where digits[i] < digits[i + 1].
            int i = digits.size() - 2;
            while (i >= 0 && digits.get(i) >= digits.get(i + 1)) {
                i--;
            }
            // If no such index exists, the digits are in descending order, and we cannot find a next permutation.
            if (i == -1) {
                return false;
            }
            // Find the largest index j greater than i such that digits[j] > digits[i].
            int j = digits.size() - 1;
            while (digits.get(j) <= digits.get(i)) {
                j--;
            }

            // Swap the values at indices i and j.
            Collections.swap(digits, i, j);
            // Reverse the sublist from i + 1 to the end of the list.
            Collections.reverse(digits.subList(i + 1, digits.size()));
            return true;
        }

        public static String findNextPalindrome(String numStr) {
            int n = numStr.length();

            if (n == 1) {
                return "";
            }

            int halfLength = n / 2;
            List<Character> leftHalf = new ArrayList<>();
            for (int i = 0; i < halfLength; i++) {
                leftHalf.add(numStr.charAt(i));
            }

            if (!findNextPermutation(leftHalf)) {
                return "";
            }

            StringBuilder nextPalindrome = new StringBuilder();
            for (char c : leftHalf) {
                nextPalindrome.append(c);
            }

            if (n % 2 == 0) {
                // even length palindrome - mirror the left half to form the right half.
                nextPalindrome.append(new StringBuilder(nextPalindrome).reverse());
            } else {
                // odd length palindrome - mirror the left half and include the middle character.
                nextPalindrome.append(numStr.charAt(halfLength));
                nextPalindrome.append(new StringBuilder(nextPalindrome.substring(0, halfLength)).reverse());
            }

            if (nextPalindrome.toString().compareTo(numStr) > 0) {
                return nextPalindrome.toString();
            }
            return "";
        }

        public static void main(String[] args) {
            String[] testCases = {"1221", "54345", "999", "12321", "89798"};

            for (int i = 0; i < testCases.length; i++) {
                System.out.println(i + 1 + ".\t Original palindrome: '" + testCases[i] + "'");
                String nextPalindrome = findNextPalindrome(testCases[i]);
                System.out.println("\t Next greater palindrome: '" + nextPalindrome + "'");
                System.out.println(new String(new char[100]).replace('\0', '-'));
            }
        }
    }

    public static void main(String[] args) {
//        Solution.main(args);
        String[] testCases = {"387654714321"};

        for (int i = 0; i < testCases.length; i++) {
            System.out.println(i + 1 + ".\t Original palindrome: '" + testCases[i] + "'");
            String nextPalindrome = nextPalindromeUsingSameDigits(testCases[i]);
            System.out.println("\t Next greater palindrome: '" + nextPalindrome + "'");
            System.out.println(new String(new char[100]).replace('\0', '-'));
        }
    }
}
