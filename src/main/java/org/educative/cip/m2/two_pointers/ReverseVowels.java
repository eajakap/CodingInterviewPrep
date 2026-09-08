package org.educative.cip.m2.two_pointers;

import java.util.HashSet;
import java.util.Set;

/**
 * Problem: Reverse Vowels of a String
 * Given a string s, reverse only all the vowels in the string and return it.
 * Example 1:
 * Input: s = "hello"
 * Output: "holle"
 * Example 2:
 * Input: s = "leetcode"
 * Output: "leotcede"
 * Constraints:
 * 1 <= s.length <= 3 * 10^5
 * s consist of printable ASCII characters.
 *
 *  Steps to solve the problem:
 *  1. Create a set of vowels for quick lookup.
 *  2. Convert the string to a character array for in-place modification.
 *  3. Use two pointers, one starting from the beginning (left) and the other from the end (right) of the character array.
 *  4. Move the left pointer to the right until it points to a vowel, and move the right pointer to the left until it points to a vowel.
 *  5. Swap the vowels at the left and right pointers
 *  6. Move both pointers towards the center and repeat steps 4-5 until the pointers meet or cross.
 *  7. Convert the modified character array back to a string and return it.
 *
 * Time Complexity: O(n), where n is the length of string s. We traverse through string s once.
 * Space Complexity: O(1), as we are using a constant amount of space for the set of vowels.
 * Given a string s, reverse only all the vowels in the string and return it.
 */
public class ReverseVowels {

    // Function to reverse the vowels in a given string
    // Steps:
    //  1. Create a set of vowels for quick lookup.
    //  2. Convert the string to a character array for in-place modification.
    //  3. Use two pointers, one starting from the beginning (left) and the other from the end (right) of the character array.
    //  4. Move the left pointer to the right until it points to a vowel, and move the right pointer to the left until it points to a vowel.
    //  5. Swap the vowels at the left and right pointers
    //  6. Move both pointers towards the center and repeat steps 4-5 until the pointers meet or cross.
    //  7. Convert the modified character array back to a string and return it.
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