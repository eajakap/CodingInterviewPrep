package org.educative.cip.m2.two_pointers;

import java.util.*;
/*
 * Problem:
 * Given an array of characters, compress it in-place. The length after compression must always be smaller than or equal to the original array.
 * Every element of the array should be a character (not int) of length 1.
 * After you are done modifying the input array in-place, return the new length of the array.
 * Constraints:
 * 1 <= chars.length <= 2000
 * chars[i] is a lowercase English letter, uppercase English letter, digit, or symbol.
 * Follow up: Could you solve it using only O(1) extra space?
 *
 * Step-by-step approach:
 * 1. Initialize two pointers: a read pointer (i) and a write pointer (w).
 * 2. Traverse the array using the read pointer (i) to identify groups of consecutive identical characters.
 * 3. For each group, write the character to the position indicated by the write pointer (w).
 * 4. If the group has more than one character, convert the count to a string and write each digit to the array.
 * 5. Move the write pointer (w) accordingly and continue until the entire array is processed.
 * 6. Return the final position of the write pointer (w) as the new length of the compressed array.
 *
 * Time Complexity: O(n) - We traverse the array once to compress the characters.
 * Space Complexity: O(1) - We use a constant amount of space for pointers and counters.
 */
public class StringCompression {
    /**
     * Compresses the input character array in-place and returns the new length of the compressed array.
     * Steps:
     * 1. Initialize two pointers: a read pointer (i) and a write pointer (w).
     * 2. Traverse the array using the read pointer (i) to identify groups of consecutive identical characters.
     * 3. For each group, write the character to the position indicated by the write pointer (w).
     * 4. If the group has more than one character, convert the count to a string and write each digit to the array.
     * 5. Move the write pointer (w) accordingly and continue until the entire array is processed.
     * 6. Return the final position of the write pointer (w) as the new length of the compressed array.
     *
     * @param chars The input character array to be compressed.
     * @return The new length of the compressed array.
     */
    public static int compress(char[] chars) {
        int length = chars.length;
        int write = 0; // write pointer
        int read = 0; // read pointer - unique element

        // Traverse the array using the read pointer to identify groups of consecutive identical characters
        while (read < length) {
            int j = read; // count pointer - duplicate elements
            while (j < length && chars[j] == chars[read]) {
                j++;
            }
            int count = j - read; // count of duplicate elements
            chars[write++] = chars[read]; // write the unique element
            if (count > 1) { // if count is greater than 1, write the count as characters
                for (char c : String.valueOf(count).toCharArray()) {
                    chars[write++] = c;
                }
            }
            read = j; // move the read pointer to the next unique element
        }
        // Return the final position of the write pointer as the new length of the compressed array
        return write;
    }

    public static void main(String[] args) {
        List<char[]> testCases = List.of(
                new char[]{'a','a','b','b','c','c','c'},                   // multiple small runs
                new char[]{'a'},                                           // single char
                new char[]{'a','b','b','b','b','b','b','b','b','b','b','b','b'}, // 12 b's
                new char[]{'x','y','z'},                                   // all distinct
                new char[]{'a','b','b','c','c','c','c','c','c','c','c','c','c','c'} // long c-run
        );

        int idx = 1;
        for (char[] chars : testCases) {
            System.out.println("\n" + idx++ + ".\tInput = " + Arrays.toString(chars));
            int result = compress(chars);
            System.out.println("\n\tCompressed Length = " + result);
            System.out.println("\tCompressed Array  = " + Arrays.toString(Arrays.copyOf(chars, result)));
            System.out.println("-".repeat(100));
        }
    }
}