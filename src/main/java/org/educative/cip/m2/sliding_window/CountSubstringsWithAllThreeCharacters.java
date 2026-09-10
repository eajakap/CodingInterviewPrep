package org.educative.cip.m2.sliding_window;

import java.util.HashMap;
import java.util.Map;

/*
 * Count Substrings With All Three Characters
 * Problem: Given a string s consisting of only 'a', 'b', and 'c',
 *          return the number of substrings containing at least one occurrence of all three characters.
 * Example 1:
 * Input: s = "abcabc"
 * Output: 10
 * Explanation: The substrings containing at least one occurrence of 'a', 'b', and 'c' are:
 * "abc", "abca", "abcab", "abcabc", "bca", "bcab", "bcabc", "cab", "cabc", and "abc".
 * Example 2:
 * Input: s = "aaacb"
 * Output: 3
 * Explanation: The substrings containing at least one occurrence of 'a', 'b', and 'c' are:
 * "aaacb", "aacb", and "acb".
 * Example 3:
 * Input: s = "abc"
 * Output: 1
 * Explanation: The only substring containing at least one occurrence of 'a', 'b', and 'c' is "abc".
 * Constraints:
 * 3 <= s.length <= 5 * 10^4
 * s consists of lowercase English letters 'a', 'b', and 'c' only.
 */
public class CountSubstringsWithAllThreeCharacters {

    /**
     * Counts the number of substrings containing at least one occurrence of all three characters 'a', 'b', and 'c'.
     * Steps to solve the problem:
     * 1. Initialize a map to keep track of the count of 'a', 'b', and 'c' in the current window.
     * 2. Use two pointers, left and right, to represent the current window of characters in the string.
     * 3. Expand the window by moving the right pointer and updating the count of the current character.
     * 4. While the counts of 'a', 'b', and 'c' are all greater than 0, shrink the window by moving the
     *    left pointer and updating the count of the character at the left pointer.
     * 5. For each valid window, add the number of substrings that can be formed with the current right pointer to the total count.
     * 6. Repeat steps 3 to 5 until the right pointer reaches the end of the string.
     * 7. Return the total count of valid substrings after traversing the entire string.
     *
     * @param s the input string consisting of only 'a', 'b', and 'c'
     * @return the count of substrings containing at least one occurrence of all three characters
     */
    public static int numberOfSubstrings(String s) {
        // Look up in string "s" for all substrings that contain at least one occurrence of 'a', 'b', and 'c'
        int size = s.length();
        Map<Character, Integer> count = new HashMap<>();
        count.put('a', 0);
        count.put('b', 0);
        count.put('c', 0);
        int totalCount = 0;

        int left = 0;
        for (int right = 0; right < s.length(); right++) {
            char currentChar = s.charAt(right);
            count.put(currentChar, count.get(currentChar) + 1);
            while (count.get('a') > 0 && count.get('b') > 0 && count.get('c') > 0) {
                totalCount += s.length() - right;
                // Shrink the window from the left to find more substrings
                char leftChar = s.charAt(left); // get the character at the left pointer
                count.put(leftChar, count.get(leftChar) - 1); // decrement the count of the character at the left pointer
                left++; // move the left pointer to the right
            }
            // Expand the window by moving the right pointer to the right
            // Next iteration of the for loop will handle this
        }
        return totalCount;
    }

    public static void main(String[] args) {
        CountSubstringsWithAllThreeCharacters sol = new CountSubstringsWithAllThreeCharacters();

        String[] inputs = {"abcabc", "aaacb", "abc", "aabbcc", "abababc"};

        for (int i = 0; i < inputs.length; i++) {
            int result = sol.numberOfSubstrings(inputs[i]);
            System.out.println((i + 1) + ".\tInput string: \"" + inputs[i] + "\"");
            System.out.println("\tResult: " + result);
            System.out.println("-".repeat(100));
        }
    }

}
