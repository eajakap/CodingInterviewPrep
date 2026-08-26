package org.educative.cip.m2.two_pointers;

/**
 * Time Complexity: O(n), where n is the length of string t. We traverse through string t once.
 * Space Complexity: O(1), as we are using a constant amount of space.
 * Given two strings s and t, return true if s is a subsequence of t, or false otherwise.
 * A subsequence of a string is a new string that is formed from the original string by deleting some (can be none) of the characters without disturbing the relative positions of the remaining characters. (i.e., "ace" is a subsequence of "abcde" while "aec" is not).
 */
public class Subsequence {

    public boolean isSubsequence(String s, String t) {
        int sPointer = 0;
        int tPointer = 0;

        while (sPointer < s.length() && tPointer < t.length()) {
            if (s.charAt(sPointer) == t.charAt(tPointer)) {
                sPointer++;
            }
            tPointer++;
        }

        return sPointer == s.length();
    }

    // Driver code
    public static void main(String[] args) {
        Subsequence sol = new Subsequence();

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