package org.educative.cip.m2.two_pointers;

import java.util.*;
/*
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