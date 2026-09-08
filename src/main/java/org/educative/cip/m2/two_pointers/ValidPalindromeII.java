package org.educative.cip.m2.two_pointers;
/**
 * Problem: Valid Palindrome II
 * Given a string s, return true if the s can be palindrome after deleting at most one character from it.
 * Example 1:
 * Input: s = "aba"
 * Output: true
 * Example 2:
 * Input: s = "abca"
 * Output: true
 * Explanation: You could delete the character 'c'.
 * Example 3:
 * Input: s = "abc"
 * Output: false
 * Constraints:
 * 1 <= s.length <= 10^5
 * s consists of lowercase English letters.
 *
 * Steps to solve the problem:
 * 1. Initialize two pointers, left and right, at the start and end of the string, respectively.
 * 2. While left < right:
 *    a. If the characters at left and right are equal, move both pointers inward (left++, right--).
 *    b. If the characters are not equal, check if skipping either the left character or the right character results in a palindrome:
 *       i. Check if the substring from left+1 to right is a palindrome.
 *       ii. Check if the substring from left to right-1 is a palindrome.
 *       iii. If either check returns true, return true.
 *       iv. If both checks return false, return false.
 * 3. If the loop completes without finding a mismatch, return true (the string is already a palindrome).
 *
 * Time Complexity: O(n), where n is the length of the string. We traverse through the string once.
 * Space Complexity: O(1), as we are using constant space for pointers and counters.
 */
public class ValidPalindromeII {

    /*
     * This method checks if a given string can be a palindrome after deleting at most one character.
     */
    public static boolean isPalindromeV1(String string) {

        // Replace this placeholder return statement with your code
        int left = 0;
        int right = string.length() - 1;
        int mismatch = 0;

        while(left < right) {
            if (string.charAt(left) == string.charAt(right)) {
                // match - continue checking
                left++;
                right--;
            } else {
                mismatch++;
                if (mismatch > 2) {
                    return false;
                }
                right--;
            }
        }

        return true;
    }

    /*
     * This method checks if a given string can be a palindrome after deleting at most one character.
     */
    public static boolean isPalindrome(String s) {
        int left = 0;
        int right = s.length() - 1;

        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
                return isPalindrome(s, left + 1, right) || isPalindrome(s, left, right - 1);
            }
            left++;
            right--;
        }
        return true;
    }

    /*
     * This helper method checks if a substring of the given string is a palindrome.
     * It takes the string and two indices (left and right) as input and returns true if the substring is a palindrome, false otherwise.
     */
    private static boolean isPalindrome(String s, int left, int right) {
        // Iteratively check traversing left to right if the substring is a palindrome
        while (left < right) {
            // If characters at the current left and right indices are not equal, return false
            if (s.charAt(left) != s.charAt(right)) {
                return false;
            }
            // Move the left pointer to the right and the right pointer to the left for the next comparison
            left++;
            right--;
        }
        // If the loop completes without finding a mismatch, the substring is a palindrome, so return true
        return true;
    }

    public static void main(String[] args) {
        System.out.println(isPalindrome("madam"));
        String[] testCases = {
                "madame",
                "dead",
                "abca",
                "tebbem",
                "eeccccbebaeeabebccceea",
                "12321"
        };

        for (String test : testCases) {
            System.out.println("\tString: " + test);
            boolean result = isPalindrome(test);
            System.out.println("\n\tResult: " + result);
            System.out.println(new String(new char[100]).replace('\0', '-'));
        }

    }

}
