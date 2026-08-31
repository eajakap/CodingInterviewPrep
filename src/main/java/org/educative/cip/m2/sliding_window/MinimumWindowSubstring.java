package org.educative.cip.m2.sliding_window;

import java.util.HashMap;
import java.util.Map;
import java.util.*;
/*
 * Time Complexity: O(n) - We traverse the string `s` once to find the minimum window.
 * Can be considered O(n + m) where `n` is the length of `s` and `m` is the length of `t`, but since we are only traversing `s` in the main loop, we can simplify it to O(n).
 * Space Complexity: O(m) - We use a hashmap to store the character counts of `t`, where `m` is the length of `t`.
 */
public class MinimumWindowSubstring {

    // Function to find the minimum window in `s` that contains all characters of `t`
    public static String minWindow(String s, String t) {
        // If `t` is empty, return an empty string as no window is possible
        if (t.isEmpty()) {
            return "";
        }

        // Maps to store the required character counts and the current window's character counts
        Map<Character, Integer> reqCount = new HashMap<>();
        Map<Character, Integer> window = new HashMap<>();

        // Populate `reqCount` with the character frequencies of `t`
        for (char c : t.toCharArray()) {
            reqCount.put(c, reqCount.getOrDefault(c, 0) + 1);
        }

        // Variables to track the number of characters that match the required frequencies
        int current = 0; // Count of characters in the current window that meet the required frequency
        int required = reqCount.size(); // Total number of unique characters in `t`

        // Result variables to track the best window
        int[] res = {-1, -1}; // Stores the start and end indices of the minimum window
        int resLen = Integer.MAX_VALUE; // Length of the minimum window

        // Sliding window pointers
        int left = 0; // Left pointer of the window
        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);

            // If `c` is in `t`, update the window count
            if (reqCount.containsKey(c)) {
                window.put(c, window.getOrDefault(c, 0) + 1);
                // If the frequency of `c` in the window matches the required frequency, update `current`
                if (window.get(c).equals(reqCount.get(c))) {
                    current++;
                }
            }

            // Try to contract the window while all required characters are present
            while (current == required) {
                // Update the result if the current window is smaller than the previous best
                if ((right - left + 1) < resLen) {
                    res[0] = left;
                    res[1] = right;
                    resLen = (right - left + 1);
                }

                // Shrink the window from the left
                char leftChar = s.charAt(left);
                if (reqCount.containsKey(leftChar)) {
                    // Decrement the count of `leftChar` in the window
                    window.put(leftChar, window.get(leftChar) - 1);
                    // If the frequency of `leftChar` in the window is less than required, update `current`
                    if (window.get(leftChar) < reqCount.get(leftChar)) {
                        current--;
                    }
                }
                left++; // Move the left pointer to shrink the window
            }
            // else, continue expanding the window by moving the right pointer
        }

        // Return the minimum window if found, otherwise return an empty string
        return res[0] == -1 ? "" : s.substring(res[0], res[1] + 1);
    }

    public static void main(String[] args) {
        // Test cases: strings `s` and corresponding substrings `t`
        String[] s = {"ABDOEDECOBE", "PATTERN", "LIFE", "ABRACADABRA", "STRIKER", "DFFDFDFVD", "AJAY"};
        String[] t = {"BC", "TN", "I", "ABC", "RK", "VDD","AJAY"};

        for (int i = 0; i < s.length; i++) {
            System.out.printf("%d.\ts: %s\n\tt: %s\n\tThe minimum substring containing %s is: %s\n",
                    i + 1, s[i], t[i], t[i], minWindow(s[i], t[i]));
            System.out.println(new String(new char[100]).replace('\0', '-'));
        }
    }
}