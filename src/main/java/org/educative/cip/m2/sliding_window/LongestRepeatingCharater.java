package org.educative.cip.m2.sliding_window;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LongestRepeatingCharater {
    // Brute Force Approach
    // Time Complexity: O(n^3) - We traverse the string to find the longest substring and there is a nested loop to calculate the frequency of characters.
    // Space Complexity: O(1) - We use a constant amount of space for frequency
    // Inefficient for large strings, but works for small strings.
    public static int BruteForceLongestRepeatingCharacterReplacement(String s, int k) {
        int n = s.length();
        int longest = 0;

        for (int start = 0; start < n; start++) {
            for (int end = start; end < n; end++) {

                int[] freq = new int[26];
                for (int i = start; i <= end; i++) {
                    freq[s.charAt(i) - 'A']++;
                }

                int length = end - start + 1;
                int maxFreq = 0;

                for (int f : freq) {
                    maxFreq = Math.max(maxFreq, f);
                }

                int replacementsNeeded = length - maxFreq;

                if (replacementsNeeded <= k) {
                    longest = Math.max(longest, length);
                }
            }
        }

        return longest;
    }

    // Optimized Approach using Sliding Window
    // Time Complexity: O(n) - We traverse the string once to find the longest substring
    // Space Complexity: O(1) - We use a constant amount of space for frequency
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
            int currentWindowSize = end - start + 1;
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

    public static void main(String[] args) {
        List<String> inputStrings = Arrays.asList("AABCCBB", "ABBCB", "ABCCDE", "ABBCAB", "BBBBBBBBB", "AAACBBBAABAB");
        List<Integer> k = Arrays.asList(2, 1, 1, 2, 4, 2);

        for (int i = 0; i < inputStrings.size(); ++i) {
            System.out.println((i + 1) + ".\tInput String: '" + inputStrings.get(i) + "'");
            System.out.println("\tk: " + k.get(i));
            System.out.println("\tLength of the longest substring with repeating characters - Inefficient Brute Force: "
                    + BruteForceLongestRepeatingCharacterReplacement(inputStrings.get(i), k.get(i)));
            System.out.println("\tLength of the longest substring with repeating characters - Optimized Sliding Window: "
                    + longestRepeatingCharacterReplacement(inputStrings.get(i), k.get(i)));

            System.out.println(new String(new char[100]).replace("\0", "-"));
        }
    }

}
