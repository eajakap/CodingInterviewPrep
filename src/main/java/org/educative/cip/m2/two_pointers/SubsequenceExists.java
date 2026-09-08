package org.educative.cip.m2.two_pointers;

/**
 * Is Subsequence:
 * Problem:
 * Given two strings s and t, return true if s is a subsequence of t, or false otherwise.
 * A subsequence of a string is a new string that is formed from the original string by deleting some (can be none) of
 * the characters without disturbing the relative positions of the remaining characters.
 * (i.e., "ace" is a subsequence of "abcde" while "aec" is not).
 * Steps to solve the problem:
 * 1. Initialize two pointers, one for each string (sPointer for s and tPointer for t).
 * 2. Traverse through string t using tPointer. For each character in t, check if it matches the current character in s (pointed by sPointer).
 *    If it matches, increment sPointer.
 * 3. After traversing t, check if sPointer has reached the end of s. If yes, s is a subsequence of t; otherwise, it is not.
 * 4. Return true if sPointer equals the length of s, otherwise return false.
 *
 * Time Complexity: O(n), where n is the length of string t. We traverse through string t once.
 * Space Complexity: O(1), as we are using a constant amount of space.
 * Given two strings s and t, return true if s is a subsequence of t, or false otherwise.
 */
public class SubsequenceExists {

    /**
     * Determines if string s is a subsequence of string t.
     * Steps:
     * 1. Initialize two pointers, one for each string (sPointer for s and tPointer for t).
     * 2. Traverse through string t using tPointer.
     *      - For each character in t, check if it matches the current character in s (pointed by sPointer).
     *      - If it matches, increment sPointer.
     * 3. After traversing t, check if sPointer has reached the end of s. If yes, s is a subsequence of t; otherwise, it is not.
     * 4. Return true if sPointer equals the length of s, otherwise return false
     *
     * @param s The string to check as a subsequence.
     * @param t The string to check against.
     * @return true if s is a subsequence of t, false otherwise.
     */
    public boolean isSubsequence(String s, String t) {
        // Step 1: Initialize two pointers, one for each string (sPointer for s and tPointer for t).
        int sPointer = 0;
        int tPointer = 0;

        // Step 2: Traverse through string t using tPointer.
        while (sPointer < s.length() && tPointer < t.length()) {
            // For each character in t, check if it matches the current character in s (pointed by sPointer).
            if (s.charAt(sPointer) == t.charAt(tPointer)) {
                // If it matches, increment sPointer.
                sPointer++;
            }
            // Increment tPointer to continue traversing t.
            tPointer++;
        }
        // Step 3: After traversing t, check if sPointer has reached the end of s. If yes, s is a subsequence of t; otherwise, it is not.
        return sPointer == s.length();
    }

    // Driver code
    public static void main(String[] args) {
        SubsequenceExists sol = new SubsequenceExists();

        String[][] testCases = {
                {"abc", "ahbgdc"},
                {"axc", "ahbgdc"},
                {"", "ahbgdc"},
                {"abc", ""},
                {"ace", "abcde"},
        };

        for (int i = 0; i < testCases.length; i++) {
            String s = testCases[i][0];
            String t = testCases[i][1];
            boolean result = sol.isSubsequence(s, t);
            System.out.println((i + 1) + ".\ts: \"" + s + "\"");
            System.out.println("\tt: \"" + t + "\"");
            System.out.println("\n\tOutput: " + result);
            System.out.println("-".repeat(100));
        }
    }
}