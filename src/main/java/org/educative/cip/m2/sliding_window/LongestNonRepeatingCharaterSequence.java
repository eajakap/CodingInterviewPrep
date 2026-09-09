package org.educative.cip.m2.sliding_window;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Longest Non-Repeating Character Sequence
 * Problem: Given a string s, find the length of the longest substring without repeating characters.
 * Example 1:
 * Input: s = "abcabcbb"
 * Output: 3
 * Explanation: The answer is "abc", with the length of 3.
 * Example 2:
 * Input: s = "bbbbb"
 * Output: 1
 * Explanation: The answer is "b", with the length of 1.
 * Example 3:
 * Input: s = "pwwkew"
 * Output: 3
 * Explanation: The answer is "wke", with the length of 3.
 * Notice that the answer must be a substring, "pwke" is a subsequence and not a substring.
 * Constraints:
 * 0 <= s.length <= 5 * 10^5
 * s consists of English letters, digits, symbols and spaces.
 *
 * Steps to solve the problem:
 * 1. Initialize two pointers, start and end, to represent the current window of characters.
 * 2. Use a HashMap to keep track of the last seen index of each character in the current window.
 * 3. Iterate through the string with the end pointer, expanding the window.
 * 4. If the current character has been seen before and its last seen index is within the current window,
 *    move the start pointer to one position after the last seen index of that character to avoid repetition.
 * 5. Update the last seen index of the current character in the HashMap.
 * 6. Calculate the length of the current window and update the length of the longest substring found so far.
 * 7. Return the length of the longest substring found after iterating through the string.
 *
 * Time Complexity: O(n) - We traverse the string once to find the longest substring
 * Space Complexity: O(1) - We use a constant amount of space for frequency
 */
public class LongestNonRepeatingCharaterSequence {
    /**
     * Finds the length of the longest substring without repeating characters.
     *
     * <p>The implementation maintains a sliding window and tracks the last seen index of each character.
     * When a repeated character is encountered within the current window, the left boundary is moved just past
     * its previous occurrence so the window stays unique.</p>
     * Steps:
     * 1. Traverse the input string.
     * 2. Use a hash map to store elements along with their respective indexes.
     *      - If the current element is present in the hash map, check whether it’s already present in the current window.
     *          If it is, we have found the end of the current window and the start of the next.
     *          We check if it’s longer than the longest window seen so far and update it accordingly.
     *      - Store the current element in the hash map with the key as the element and the value as the current index.
     * 3. At the end of the traversal, we have the length of the longest substring with all distinct characters.
     *
     * @param s the input string
     * @return the length of the longest substring containing no repeated characters
     * @implNote Time complexity is O(n), where n is the length of the string, and space complexity is O(min(n, charset size)).
     */
    public static int findLongestSubstring(String s) {
        int stringLength = s.length();
        int longest = 0; // Tracks the length of the longest non-repeating substring found so far.
        int windowStart = 0; // Left boundary of the current sliding window.
        int windowLength = 0; // Length of the current valid window before adjusting the start pointer.
        Map<Character, Integer> charFreq = new HashMap<>(); // Last index of each character in the current window.

        if (stringLength == 0) {
            return 0;
        }

        // Expand the right side of the window one position at a time.
        for (int end = 0; end < stringLength; end++) {
            char currentChar = s.charAt(end);

            // If the character has not been seen before, store its index.
            if (!charFreq.containsKey(currentChar)) {
                charFreq.put(currentChar, end);
            } else {
                // If the previous occurrence is inside the current window, shrink the window to eliminate repetition.
                if (charFreq.get(currentChar) >= windowStart) {
                    // Calculate the length of the current valid window before adjusting the start pointer.
                    windowLength = end - windowStart;
                    // Move the start pointer to one position after the last occurrence of the current character.
                    windowStart = charFreq.get(currentChar) + 1;
                    // Update the longest length found so far.
                    longest = Math.max(longest, windowLength);
                }
                // Update the last seen index of the current character to the current position.
                charFreq.put(currentChar, end);
            }

            // The current window is still valid; compare its size against the best answer so far.
            longest = Math.max(longest, end - windowStart + 1);
        }

        return longest;
    }

    // Driver code
    public static void main(String[] arg) {
        String[] inputs = {
                "abcdbea",
                "abcabcbb",
                "pwwkew",
                "bbbbb",
                "ababababa",
                "",
                "ABCDEFGHI",
                "ABCDEDCBA",
                "AAAABBBBCCCCDDDD"
        };
        for (int i = 0; i < inputs.length; i++) {
            int str = LongestNonRepeatingCharaterSequence.findLongestSubstring(inputs[i]);
            System.out.print(i + 1);
            System.out.println("\tInput string: " + inputs[i]);
            System.out.println("\n\tLength of longest substring: " + str);
            System.out.println(new String(new char[100]).replace('\0', '-'));
        }
    }

}
