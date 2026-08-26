package org.educative.cip.m2.two_pointers;

import java.util.HashSet;
import java.util.Set;

/**
 * Time Complexity: O(n), where n is the length of string s. We traverse through string s once.
 * Space Complexity: O(1), as we are using a constant amount of space for the set of vowels.
 * Given a string s, reverse only all the vowels in the string and return it.
 */
public class ReverseVowels {

    public String reverseVowels(String s) {
        Set<Character> vowels = new HashSet<>();
        for (char c : "aeiouAEIOU".toCharArray()) {
            vowels.add(c);
        }
        char[] chars = s.toCharArray();
        int left = 0;
        int right = chars.length - 1;

        while (left < right) {
            // look for vowels from left end
            while (left < right && !vowels.contains(chars[left])) {
                left++;
            }
            // look for vowels from right end
            while (left < right && !vowels.contains(chars[right])) {
                right--;
            }
            // found vowel at left and right, swap them
            char temp = chars[left];
            chars[left] = chars[right];
            chars[right] = temp;
            left++;
            right--;
        }

        return new String(chars);
    }

    public static void main(String[] args) {
        ReverseVowels sol = new ReverseVowels();
        String[] testCases = {
                "photosynthesis",
                "celebrity",
                "artificial",
                "intelligence",
                "Python",
        };

        for (int i = 0; i < testCases.length; i++) {
            String result = sol.reverseVowels(testCases[i]);
            System.out.println((i + 1) + ".\tInput string: \"" + testCases[i] + "\"");
            System.out.println("\tResult: \"" + result + "\"");
            System.out.println("-".repeat(100));
        }
    }
}