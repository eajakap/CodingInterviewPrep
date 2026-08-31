package org.educative.cip.m2.sliding_window;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LongestNonRepeatingCharaterSequence {
    // Optimized Approach using Sliding Window
    // Time Complexity: O(n) - We traverse the string once to find the longest substring
    // Space Complexity: O(1) - We use a constant amount of space for frequency
    public static int findLongestSubstring(String s) {
        int stringLength = s.length();
        int longest = 0; // This will hold the length of the longest substring found
        int windowStart = 0; // This will be the starting index of our sliding window
        int windowLength = 0; // This will hold the count of the most frequent character in the current window
        Map<Character, Integer> charFreq = new HashMap<>(); // This map will hold the frequency of characters in the current window

        if (stringLength == 0) {
            return 0;
        }

        // We will expand the window by moving the 'end' pointer
        for (int end = 0; end < stringLength; end++) {
            // Get the current character at the 'end' pointer
            char currentChar = s.charAt(end);
            if  (!charFreq.containsKey(currentChar)) {
                charFreq.put(currentChar, end); // Map the character to its index in the string
            } else {
                // compute the length of the substring from the last occurrence of the character to the current index
                if (charFreq.get(currentChar) >= windowStart) {
                    windowLength = end - windowStart; // Calculate the length of the current window
                    windowStart = charFreq.get(currentChar) + 1; // Start of the Next window.
                    longest = Math.max(longest, windowLength);
                }
                charFreq.put(currentChar, end);
            }
            // Update the length of the longest substring found
            longest = Math.max(longest, end - windowStart + 1);
        }
        // Return the length of the longest substring found
        return longest;
    }

    // Driver code
    public static void main(String[] arg) {
        String[] inputs = {
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
