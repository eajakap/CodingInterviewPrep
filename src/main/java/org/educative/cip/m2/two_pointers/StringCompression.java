package org.educative.cip.m2.two_pointers;

import java.util.*;
/*
 * Time Complexity: O(n) - We traverse the array once to compress the characters.
 * Space Complexity: O(1) - We use a constant amount of space for pointers and counters.
 */
public class StringCompression {
    public static int compress(char[] chars) {
        int n = chars.length;
        int w = 0; // write pointer
        int i = 0; // read pointer - unique element

        while (i < n) {
            int j = i; // count pointer - duplicate elements
            while (j < n && chars[j] == chars[i]) {
                j++;
            }
            int count = j - i;
            chars[w++] = chars[i];
            if (count > 1) {
                for (char c : String.valueOf(count).toCharArray()) {
                    chars[w++] = c;
                }
            }

            i = j;
        }

        return w;
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