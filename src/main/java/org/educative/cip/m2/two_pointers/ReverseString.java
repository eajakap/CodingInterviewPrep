package org.educative.cip.m2.two_pointers;

import java.util.*;
/*
 * Problem:
 * Given an array of characters, reverse the array in place.
 * Step-by-step solution:
 * 1. Initialize two pointers, left and right, at the start and end of the array, respectively.
 * 2. While left is less than right:
 *    a. Swap the characters at the left and right pointers.
 *    b. Move the left pointer one step to the right and the right pointer one step to the left.
 * 3. Continue until the pointers meet or cross each other.
 * 4. The array is now reversed in place.
 * 5. Return the reversed array.
 * Time Complexity: O(n) - We traverse the array once to reverse the string.
 * Space Complexity: O(1) - We use a constant amount of space for pointers and swaps.
 */
class ReverseString {
    public void reverseString(char[] s) {
        int left = 0, right = s.length - 1;
        while (left < right) {
            char temp = s[left];
            s[left] = s[right];
            s[right] = temp;
            left++;
            right--;
        }
    }

    public static void main(String[] args) {
        char[][] testCases = {
                {'h','e','l','l','o'},
                {'m','o','r','n','i','n','g'},
                {'p','y','t','h','o','n'},
                {'a'},
                {'r','a','c','e','c','a','r'}
        };

        ReverseString sol = new ReverseString();

        for (int i = 0; i < testCases.length; i++) {
            char[] original = Arrays.copyOf(testCases[i], testCases[i].length);

            sol.reverseString(testCases[i]);

            System.out.println((i + 1) + "\tInput string: " + Arrays.toString(original));
            System.out.println("\n\tReversed string: " + Arrays.toString(testCases[i]));
            System.out.println("----------------------------------------------------------------------------------------------------\n");
        }
    }
}