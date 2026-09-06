package org.educative.cip.m2.two_pointers;
import java.util.*;

/*
 * Problem: Given a numeric string, numStr, representing a palindrome (composed only of digits).
 * Return the smallest palindrome larger than numStr that can be created by rearranging its digits.
 * If no such palindrome exists, return an empty string "".
 * Consider the following example to understand the expected output for a given numeric string:
 * input string = "123321"
 * The valid palindromes made from the exact digits are "213312", "231132", "312213", "132231", "321123".
 * We return the palindrome "132231" because it is the smallest palindrome larger than the input string "123321".
 * Steps:
 * 1. Split the palindrome into two halves (left and right).
 *    For even-length palindromes, divide the string into two equal halves.
 *    For odd-length palindromes, preserve the middle digit and only modify the left half.
 * 2. Find the next permutation of the left half.
 *    - Traverse the left half from right to left to find the first pair of digits where the left digit is smaller than the right digit.
 *      - Start from the end of leftHalf and move left to find the first index i where digits[i] < digits[i + 1].
 *      - Then, scan again from the end to find index j where digits[j] > digits[i].
 *      - If such a pair exists, it indicates that a next permutation is possible.
 *      - If no such pair exists, the left half is in descending order, and no next permutation exists.
 *    - Swap this left digit with the smallest digit on its right that is larger than it.
 *    - Reverse the digits to the right of the original left digit's position to get the next permutation.
 * 3. If a next permutation exists, construct the new palindrome by mirroring the left half and adding the middle character (if the length is odd).
 * 4. If no next permutation exists, return "-1" indicating that no greater palindrome can be formed.
 *
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
            // ( First index from the end where the digit is greater than digits[i])
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
            String[] testCases = {"454213456","875137623", "1221", "2354532", "54345", "999", "12321", "89798"};

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
