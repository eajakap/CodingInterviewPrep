package org.educative.cip.m2.two_pointers;

import java.util.*;
/**
 * Problem: Backspace String Compare
 * Given two strings s and t, return true if they are equal when both are typed into empty text editors. '#' means a backspace character.
 * Note that after backspacing an empty text, the text will continue empty.
 * Example 1:
 * Input: s = "ab#c", t = "ad#c"
 * Output: true
 * Explanation: Both s and t become "ac".
 * Example 2:
 * Input: s = "ab##", t = "c#d#"
 * Output: true
 * Explanation: Both s and t become "".
 * Example 3:
 * Input: s = "a#c", t = "b"
 * Output: false
 * Explanation: s becomes "c" while t becomes "b".
 * Constraints:
 * 1 <= s.length, t.length <= 200
 * s and t only contain lowercase letters and '#' characters.
 *
 * Steps to solve the problem:
 * 1. Initialize two pointers iS and iT to point to the end of strings s and t respectively.
 * 2. Initialize two skip counters skipS and skipT to 0.
 * 3. While either pointer is valid (iS >= 0 or iT >= 0):
 *    a. For string s:
 *       - While iS is valid:
 *         - If s[iS] is '#', increment skipS and decrement iS.
 *         - Else if skipS > 0, decrement skipS and decrement iS.
 *         - Else, break the loop (we found a valid character).
 *    b. For string t:
 *       - While iT is valid:
 *         - If t[iT] is '#', increment skipT and decrement iT.
 *         - Else if skipT > 0, decrement skipT and decrement iT.
 *         - Else, break the loop (we found a valid character).
 *    c. Compare the characters at iS and iT:
 *       - If both pointers are valid and the characters are different, return false.
 *       - If one pointer is valid and the other is not, return false.
 *    d. Decrement both pointers to move to the next character.
 * 4. If the loop completes without returning false, return true (the strings are equal after processing backspaces).
 *
 * Time Complexity: O(n + m), where n is the length of string s and m is the length of string t.
 * We traverse through both strings once.
 * Space Complexity: O(1), as we are using a constant amount of space for the skip counters.
 */
public class BackSpaceStringCompare {
    /**
     * Compares two strings s and t to determine if they are equal when typed into empty text editors,
     * considering '#' as a backspace character.
     *
     * @param s the first string
     * @param t the second string
     * @return true if the strings are equal after processing backspaces, false otherwise
     */
    public boolean backspaceCompare(String s, String t) {
        // Start from the end so we can process backspaces naturally.
        int iS = s.length() - 1, iT = t.length() - 1;

        // skipS / skipT count how many characters are going to be deleted by backspaces.
        int skipS = 0, skipT = 0;

        while (iS >= 0 || iT >= 0) {
            // Find the next visible character in s after applying pending backspaces.
            while (iS >= 0) {
                if (s.charAt(iS) == '#') {
                    skipS++;
                    iS--;
                } else if (skipS > 0) {
                    skipS--;
                    iS--;
                } else {
                    break;
                }
            }

            // Find the next visible character in t after applying pending backspaces.
            while (iT >= 0) {
                if (t.charAt(iT) == '#') {
                    skipT++;
                    iT--;
                } else if (skipT > 0) {
                    skipT--;
                    iT--;
                } else {
                    break;
                }
            }

            // Compare the next remaining visible characters from both strings.
            if (iS >= 0 && iT >= 0) {
                if (s.charAt(iS) != t.charAt(iT)) {
                    return false;
                }
            } else if (iS >= 0 || iT >= 0) {
                // One string still has a visible character while the other does not.
                return false;
            }

            // Move to the previous character pair in both strings.
            iS--;
            iT--;
        }

        // If we have processed both strings completely and found no mismatches, they are equal.
        return true;
    }

    public static void main(String[] args) {
        BackSpaceStringCompare sol = new BackSpaceStringCompare();

        String[][] testCases = {
                {"ab#c", "bc"},
                {"abc###", ""},
                {"x#y#z", "z"},
                {"hello##", "hel"},
                {"a##c", "#ac"},
                {"abc#d##", "ac"},
        };

        for (int idx = 0; idx < testCases.length; idx++) {
            String s = testCases[idx][0];
            String t = testCases[idx][1];
            boolean result = sol.backspaceCompare(s, t);
            System.out.println((idx + 1) + ".\tInput s: \"" + s + "\"");
            System.out.println("\tInput t: \"" + t + "\"");
            System.out.println("\tResult: " + result);
            System.out.println("-".repeat(100));
        }
    }
}