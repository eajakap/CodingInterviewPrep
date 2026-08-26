package org.educative.cip.m2.two_pointers;

import java.util.*;
/**
 * Time Complexity: O(n + m), where n is the length of string s and m is the length of string t.
 * We traverse through both strings once.
 * Space Complexity: O(1), as we are using a constant amount of space for the skip counters.
 * Given two strings s and t, return true if they are equal when both are typed into empty text editors.
 * '#' means a backspace character.
 */
public class BackSpaceStringCompare {
    public boolean backspaceCompare(String s, String t) {
        int iS = s.length() - 1, iT = t.length() - 1;
        int skipS = 0, skipT = 0;

        while (iS >= 0 || iT >= 0) {
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

            // Compare characters
            if (iS >= 0 && iT >= 0) {
                if (s.charAt(iS) != t.charAt(iT)) {
                    return false;
                }
            } else if (iS >= 0 || iT >= 0) {
                // One string ended before the other
                return false;
            }

            iS--;
            iT--;
        }

        return true;
    }

    public static void main(String[] args) {
        BackSpaceStringCompare sol = new BackSpaceStringCompare();

        String[][] testCases = {
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