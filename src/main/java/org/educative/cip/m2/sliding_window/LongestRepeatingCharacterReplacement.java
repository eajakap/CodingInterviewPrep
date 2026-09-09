package org.educative.cip.m2.sliding_window;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Longest Repeating Character Replacement
 * Problem: Given a string s and an integer k, you can choose any character of the string and change it to any other
 * uppercase English character.You can perform this operation at most k times.
 * Return the length of the longest substring containing the same letter you can get after performing the above operations.
 * Example 1:
 * Input: s = "ABAB", k = 2
 * Output: 4
 * Explanation: Replace the two 'A's with two 'B's or vice versa.
 * Example 2:
 * Input: s = "AABABBA", k = 1
 * Output: 4
 * Explanation: Replace the one 'A' in the middle with 'B' and form "BBBB".
 * Constraints:
 * 1 <= s.length <= 10^3
 * s consists of only uppercase English letters.
 *
 * Steps to solve the problem:
 * 1. Initialize two pointers, start and end, to represent the current window of characters.
 * 2. Use a HashMap to keep track of the frequency of characters in the current window.
 * 3. Iterate through the string with the end pointer, expanding the window.
 * 4. Update the frequency of the current character in the HashMap.
 * 5. Calculate the length of the current window and the count of the most frequent character in the window.
 * 6. If the number of characters to replace (current window size - most frequent character count) is greater than k,
 *    shrink the window from the left by moving the start pointer and updating the frequency of the character at the
 *    start pointer in the HashMap.
 * 7. Update the length of the longest substring found so far.
 * 8. Return the length of the longest substring found after iterating through the string.
 *
 * Time Complexity: O(n) - We traverse the string once to find the longest substring
 * Space Complexity: O(1) - We use a constant amount of space for frequency
 */
public class LongestRepeatingCharacterReplacement {
    /**
     * Supported implementations for the problem.
     */
    public enum SolutionApproach {
        BRUTE_FORCE,
        SLIDING_WINDOW
    }

    /**
     * Delegates execution to the requested algorithmic strategy.
     *
     * @param s the input string
     * @param k the maximum number of replacements allowed
     * @param approach the implementation to execute
     * @return the length of the longest substring that can be made uniform with at most {@code k} replacements
     * @throws IllegalArgumentException if the selected approach is not supported
     */
    public static int longestRepeatingCharacterReplacement(String s, int k, SolutionApproach approach) {
        switch (approach) {
            case BRUTE_FORCE:
                return longestRepeatingCharacterReplacementBruteForce(s, k);
            case SLIDING_WINDOW:
                return longestRepeatingCharacterReplacement(s, k);
            default:
                throw new IllegalArgumentException("Invalid solution approach: " + approach);
        }
    }

    /**
     * Solves the problem by checking every possible substring and computing how many replacements are necessary.
     *
     * <p>This brute-force approach is straightforward and easy to verify, but it is inefficient for large inputs.</p>
     *
     * @param s the input string
     * @param k the maximum number of replacements allowed
     * @return the length of the longest substring that can be transformed into a single repeating character
     * @implNote Time complexity is O(n^3) and space complexity is O(1).
     */
    public static int longestRepeatingCharacterReplacementBruteForce(String s, int k) {
        int n = s.length();
        int longest = 0;

        // Try every possible substring window: [start, end]
        for (int start = 0; start < n; start++) {
            for (int end = start; end < n; end++) {

                // Count each character inside the current substring window.
                int[] freq = new int[26];
                for (int i = start; i <= end; i++) {
                    freq[s.charAt(i) - 'A']++;
                }

                // The current window length and the highest frequency in that window.
                int length = end - start + 1;
                int maxFreq = 0;

                // Find the character that appears most often in this substring.
                for (int f : freq) {
                    maxFreq = Math.max(maxFreq, f);
                }

                // If replacing all other characters with the most common one needs at most k changes,
                // the current substring is valid and may become the longest answer.
                int replacementsNeeded = length - maxFreq;

                if (replacementsNeeded <= k) {
                    longest = Math.max(longest, length);
                }
            }
        }

        return longest;
    }

    /**
     * Uses a sliding window to track the most frequent character in the current window while shrinking the window
     * whenever the number of replacements needed exceeds {@code k}.
     *
     * @param s the input string
     * @param k the maximum number of character replacements allowed
     * @return the length of the longest substring that can be made uniform with at most {@code k} replacements
     * @implNote Time complexity is O(n) and space complexity is O(1).
     */
    public static int longestRepeatingCharacterReplacement(String s, int k) {
        int stringLength = s.length();
        int lengthOfMaxSubstring = 0; // This will hold the length of the longest substring found
        int start = 0; // This will be the starting index of our sliding window
        Map<Character, Integer> charFreq = new HashMap<>(); // This map will hold the frequency of characters in the current window
        int mostFreqChar = 0; // This will hold the count of the most frequent character in the current window

        // We will expand the window by moving the 'end' pointer
        for (int end = 0; end < stringLength; end++) {
            // Get the current character at the 'end' pointer
            char currentChar = s.charAt(end);
            // Update the frequency of the current character in the map
            charFreq.put(currentChar, charFreq.getOrDefault(currentChar, 0) + 1);
            // Update the count of the most frequent character in the current window
            mostFreqChar = Math.max(mostFreqChar, charFreq.get(currentChar));

            // Number of characters to replace = (current window size) - (count of the most frequent character)
            // If the number of characters to replace is greater than k, we need to shrink the window
            // Note: currentWindowSize = end - start + 1;
            while((end - start + 1 - mostFreqChar > k)) {
                // Shrink the window from the left
                // remove the character at the 'start' pointer from the frequency map
                char leftChar = s.charAt(start);
                charFreq.put(leftChar, charFreq.get(leftChar) - 1);
                start++; // Shrink the window from the left
            }
            // Update the length of the longest substring found
            lengthOfMaxSubstring = Math.max(lengthOfMaxSubstring, end - start + 1);
        }
        // Return the length of the longest substring found
        return lengthOfMaxSubstring;
    }

    /**
     * Demonstrates the brute-force and optimized sliding-window solutions on a set of sample inputs.
     *
     * @param args command-line arguments ignored by this demo
     */
    public static void main(String[] args) {
        List<String> inputStrings = Arrays.asList("AABCCBB", "ABBCB", "ABCCDE", "ABBCAB", "BBBBBBBBB", "AAACBBBAABAB");
        List<Integer> k = Arrays.asList(2, 1, 1, 2, 4, 2);

        for (int i = 0; i < inputStrings.size(); ++i) {
            System.out.println((i + 1) + ".\tInput String: '" + inputStrings.get(i) + "'");
            System.out.println("\tk: " + k.get(i));
            System.out.println("\tLength of the longest substring with repeating characters - Inefficient Brute Force: "
                    + longestRepeatingCharacterReplacement(inputStrings.get(i), k.get(i), SolutionApproach.BRUTE_FORCE));
            System.out.println("\tLength of the longest substring with repeating characters - Optimized Sliding Window: "
                    + longestRepeatingCharacterReplacement(inputStrings.get(i), k.get(i), SolutionApproach.SLIDING_WINDOW));
            System.out.println(new String(new char[100]).replace("\0", "-"));
        }
    }

}
