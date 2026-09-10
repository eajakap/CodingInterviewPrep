package org.educative.cip.m2.sliding_window;

import java.util.HashMap;
import java.util.Map;

/*
 * Count Substrings with K Frequency Characters
 * Problem: Given a string s and an integer k, return the number of substrings that contain exactly k distinct characters.
 * Example 1:
 * Input: s = "abcba", k = 2
 * Output: 7
 * Explanation: The substrings that contain exactly 2 distinct characters are: "ab", "bc", "cb", "ba", "abc", "bcb", "cba".
 * Example 2:
 * Input: s = "aa", k = 1
 * Output: 3
 * Explanation: The substrings that contain exactly 1 distinct character are: "a", "a", "aa".
 * Constraints:
 * 1 <= s.length <= 10^4
 * s consists of lowercase English letters.
 * 1 <= k <= 26
 *
 * Steps to solve the problem:
 *  - See the relevant methods - numberOfSubstringsBruteForce, numberOfSubstringsSlidingWindowList, numberOfSubstringsSlidingWindowMap
 *
 * Time Complexity: O(n), where n is the length of the string. We traverse through the string once.
 * Space Complexity: O(1), as we are using a frequency array to keep track of distinct characters (at most 26 lowercase English letters).
 */
public class CountSubstringsWithKFreqChars {
    public enum CharFrequency {
        LESS_THAN_K,
        EQUAL_TO_K,
        GREATER_THAN_K
    }

    public enum SlidingWindowState {
        EXPANDING,
        SHRINKING
    }

    public enum SolutionApproach {
        BRUTE_FORCE,
        SLIDING_WINDOW_LIST,
        SLIDING_WINDOW_MAP
    }

    public long numberOfSubstrings(String s, int k, SolutionApproach solutionApproach ) {
        switch(solutionApproach) {
            case BRUTE_FORCE:
                return numberOfSubstringsBruteForce(s, k);
            case SLIDING_WINDOW_LIST:
                return numberOfSubstringsSlidingWindowList(s, k);
            case SLIDING_WINDOW_MAP:
                return numberOfSubstringsSlidingWindowMap(s, k);
            default:
                throw new IllegalArgumentException("Invalid solution approach: " + solutionApproach);
        }
    }

    /**
     * Brute force approach to count the number of substrings with at least one character having frequency k.
     * Time Complexity: O(n^2) - We have a nested loop to generate all substrings and count the frequency of characters.
     * Space Complexity: O(1) - We use a constant amount of space for counters.
     * Steps to solve the problem:
     * 1. Iterate through all possible substrings of the input string.
     * 2. For each substring, count the frequency of each character using a frequency array.
     * 3. Check if any character in the substring has a frequency equal to k.
     * 4. If such a character exists, increment the total count of valid substrings.
     * 5. Return the total count of valid substrings after evaluating all substrings.
     *
     * @param s the input string
     * @param k the frequency threshold
     * @return the count of substrings with at least one character having frequency k
     */
    public long numberOfSubstringsBruteForce(String s, int k) {
        int n = s.length();
        long total = 0L;

        for (int i = 0; i < n; i++) {
            int[] freq = new int[26];
            int charsWithK = 0;

            for (int j = i; j < n; j++) {
                int charFreqIdx = s.charAt(j) - 'a';
                freq[charFreqIdx]++;
                if (freq[charFreqIdx] == k) {
                    charsWithK++;
                } else if (freq[charFreqIdx] == k + 1) {
                    charsWithK--;
                }
                if (charsWithK > 0) {
                    total += 1;
                }
            }
        }

        return total;
    }

    /**
     * Sliding window approach to count the number of substrings with at least one character having frequency k.
     * Time Complexity: O(n) - We traverse through the string once.
     * Space Complexity: O(n) - We use a frequency Map to keep track of distinct characters.
     *
     * Steps to solve the problem:
     * 1. Initialize two pointers, left and right, to represent the current window of characters.
     * 2. Use a HashMap to count the frequency of each character in the current window.
     * 3. Expand the window by moving the right pointer and adding the current character to the HashMap.
     * 4. If the HashMap contains at least one character with frequency k, shrink the window from the left until there are no characters with frequency k in the HashMap.
     * 5. Keep track of the number of valid substrings formed during the process.
     * 6. Return the total count of valid substrings after traversing the entire string.
     *
     * @param s the input string
     * @param k the frequency threshold
     * @return the count of substrings with at least one character having frequency k
     */
    public long numberOfSubstringsSlidingWindowMap(String s, int k) {
        // Implementation for sliding window with map approach
        int n = s.length();
        Map<Character, Integer> freq = new HashMap<>();
        int charsWithK = 0;
        long total = 0L;
        int left = 0;

        for (int right = 0; right < n; right++) {
            char currentChar = s.charAt(right);
            freq.put(currentChar, freq.getOrDefault(currentChar, 0) + 1);
            if (freq.get(currentChar) == k) {
                charsWithK++;
            } else if (freq.get(currentChar) == k + 1) {
                charsWithK--;
            }

            while (charsWithK > 0) {
                total += (long) n - right;
                char leftChar = s.charAt(left);
                if (freq.get(leftChar) == k) {
                    charsWithK--;
                }
                freq.put(leftChar, freq.get(leftChar) - 1);
                left++;
            }
        }
        return total;
    }

    /**
     * Sliding window approach to count the number of substrings with at least one character having frequency k.
     * Time Complexity: O(n) - We traverse through the string once.
     * Space Complexity: O(1) - We use a frequency array to keep track of distinct characters (at most 26 lowercase English letters).
     *
     * Steps to solve the problem:
     * 1. Initialize two pointers, left and right, to represent the current window of characters.
     * 2. Use an array to count the frequency of each character in the current window.
     * 3. Expand the window by moving the right pointer and adding the current character to the frequency array.
     * 4. If the frequency array contains at least one character with frequency k,
     *    shrink the window from the left until there are no characters with frequency k in the frequency array.
     * 5. Keep track of the number of valid substrings formed during the process.
     * 6. Return the total count of valid substrings after traversing the entire string
     *
     * @param s the input string
     * @param k the frequency threshold
     * @return the count of substrings with at least one character having frequency k
     */
    public long numberOfSubstringsSlidingWindowList(String s, int k) {
        int n = s.length();
        int[] freq = new int[26];
        int charsWithK = 0;
        long total = 0L;
        int left = 0;

        for (int right = 0; right < n; right++) {
            // Compute the frequency index of the current character
            int charFreqIdx = s.charAt(right) - 'a';
            // Increment the frequency of the current character in the window
            freq[charFreqIdx]++;
            // If the frequency of the current character reaches k, increment the count of characters with frequency k
            if (freq[charFreqIdx] == k) {
                charsWithK++;
            }

            while (charsWithK > 0) {
                total += (long) n - right;
                int leftCharFreqIdx = s.charAt(left) - 'a';
                // If the frequency of the character at the left pointer in the window is equal to k,
                // decrement the count of characters with frequency k
                if (freq[leftCharFreqIdx] == k) {
                    charsWithK--;
                }
                // Decrement the frequency of the character at the left pointer in the window
                freq[leftCharFreqIdx]--;
                left++;
            }
        }

        return total;
    }

    // Driver code
    public static void main(String[] args) {
        String[][] testCases = {
                {"abcabc", "2"},
                {"vvvvvvvv", "2"},
                {"xyxyxyxy", "3"},
                {"mnolllonm", "3"},
                {"abcdefg", "1"}
        };

        for (int i = 0; i < testCases.length; i++) {
            String s = testCases[i][0];
            int k = Integer.parseInt(testCases[i][1]);
            CountSubstringsWithKFreqChars sol = new CountSubstringsWithKFreqChars();
            long resultSlidingWindowList = sol.numberOfSubstrings(s, k, SolutionApproach.SLIDING_WINDOW_LIST);
            System.out.printf("%d:\ts = \"%s\"\n\tk = %d\n\n\tOutput (Sliding Window List) = %d\n", i + 1, s, k, resultSlidingWindowList);
            long resultSlidingWindowMap = sol.numberOfSubstrings(s, k, SolutionApproach.SLIDING_WINDOW_MAP);
            System.out.printf("\tOutput (Sliding Window Map) = %d\n", resultSlidingWindowMap);
            long resultBruteForce = sol.numberOfSubstrings(s, k, SolutionApproach.BRUTE_FORCE);
            System.out.printf("\tOutput (Brute Force) = %d\n", resultBruteForce);
            System.out.println("-".repeat(100));
        }
    }

}
