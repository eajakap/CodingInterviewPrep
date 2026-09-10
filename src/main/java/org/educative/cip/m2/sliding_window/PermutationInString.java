package org.educative.cip.m2.sliding_window;

/*
 * Permutation in String
 * Problem: Given two strings s1 and s2, return true if s2 contains a permutation of s1, or false otherwise.
 *         In other words, return true if one of s1's permutations is the substring of s2.
 * Example 1:
 * Input: s1 = "ab", s2 = "eidbaooo"
 * Output: true
 * Explanation: s2 contains one permutation of s1 ("ba").
 * Example 2:
 * Input: s1 = "ab", s2 = "eidboaoo"
 *  Output: false
 * Explanation: s2 does not contain any permutation of s1.
 * Constraints:
 * 1 <= s1.length, s2.length <= 10^4
 * s1 and s2 consist of lowercase English letters.
 *
 * Steps to solve the problem:
 * 1. Create two frequency arrays of size 26 (for each letter of the alphabet).
 * 2. Populate the frequency array for s1 and the first window of s2 (of size equal to s1).
 * 3. Slide the window over s2, updating the frequency array for s2 by
 *   adding the new character and removing the old character from the window.
 * 4. After each update, compare the two frequency arrays. If they match, return true.
 * 5. If no match is found after sliding through s2, return false.
 * Time Complexity: O(n), where n is the length of s2. We traverse through s2 once.
 * Space Complexity: O(1), as we use fixed-size arrays of size 26 for the frequency counts.
*/
public class PermutationInString {
    public static final int ALPHABET_SIZE = 26;

    /**
     * Checks if s2 contains a permutation of s1.
     *
     * @param s1 the string for which we want to find a permutation
     * @param s2 the string in which we are searching for a permutation of s1
     * @return true if s2 contains a permutation of s1, false otherwise
     */
    public static boolean checkInclusion(String s1, String s2) {
        // If s1 is longer than s2, s2 cannot contain a permutation of s1
        if (s1.length() > s2.length()) {
            return false;
        }
        // Frequency arrays to count occurrences of each character in s1 and the current window of s2
        int[] s1Count = new int[ALPHABET_SIZE]; // Frequency array for s1
        int[] s2Count = new int[ALPHABET_SIZE]; // Frequency array for the current window in s2
        // Populate the frequency arrays for s1 and the first window of s2
        for (int i = 0; i < s1.length(); i++) {
            s1Count[s1.charAt(i) - 'a']++;
            s2Count[s2.charAt(i) - 'a']++;
        }
        // Check if the current window's frequency matches s1's frequency
        if (matches(s1Count, s2Count)) {
            return true;
        }

        // Slide the window over s2 and update the frequency array for s2
        for (int i = 0; i < s2.length() - s1.length(); i++) {
            // Update the frequency array for s2 by adding the new character and removing the old character
            s2Count[s2.charAt(i + s1.length()) - 'a']++; // i + s1.length() is the new character entering the window (expanding from the rightmost character of the previous window)
            s2Count[s2.charAt(i) - 'a']--; // i is the old character leaving the window (the leftmost character of the previous window)
            // Check if the current window's frequency matches s1's frequency
            if (matches(s1Count, s2Count)) {
                return true;
            }
        }
        // No match found after sliding through s2
        return false;
    }

    /**
     * Compares two frequency arrays to check if they match.
     *
     * @param s1Count the frequency array for s1
     * @param s2Count the frequency array for the current window in s2
     * @return true if the frequency arrays match, false otherwise
     */
    private static boolean matches(int[] s1Count, int[] s2Count) {
        for (int i = 0; i < ALPHABET_SIZE; i++) {
            // If the counts for any character do not match, return false
            if (s1Count[i] != s2Count[i]) {
                return false;
            }
        }
        // If all counts match, return true
        return true;
    }

    // Driver code
    public static void main(String[] args) {
        String[][] testCases = new String[][]{
                {"abc", "okbacof"},
                {"abc", "okbancof"},
                {"adc", "dcda"},
                {"xyz", "x"},
                {"hello", "ooolleoooleh"}
        };

        int i = 1;
        for (String[] tc : testCases) {
            String s1 = tc[0], s2 = tc[1];
            boolean result = checkInclusion(s1, s2);

            System.out.println(i++ + ".\ts1 = \"" + s1 + "\"");
            System.out.println("\ts2 = \"" + s2 + "\"");
            System.out.println("\n\tOutput: " + result);
            System.out.println("-".repeat(100));
        }
    }

}
