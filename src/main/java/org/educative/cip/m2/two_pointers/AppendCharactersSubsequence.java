package org.educative.cip.m2.two_pointers;

import java.util.*;
/*
 * Problem:
 * Given two strings, source and target, determine the minimum number of characters that need to be appended
 * to the end of source to make target a subsequence of source.
 * A subsequence of a string is a new string generated from the original string with some characters(can be none)
 * deleted without changing the relative order of the remaining characters.
 *
 * Steps to solve the problem:
 * 1. Initialize two pointers, sourceIndex and targetIndex, to 0.
 * 2. Traverse the source string using sourceIndex and the target string using targetIndex.
 * 3. If the characters at source[sourceIndex] and target[targetIndex] match, increment targetIndex to check the next character in the target string.
 * 4. Always increment sourceIndex to continue traversing the source string.
 * 5. After traversing the source string, the number of characters that need to be appended to source is equal to the length of target minus targetIndex,
 *    which represents the number of characters in target that were not matched in source.
 * 6. Return the result.
 *
 * Time Complexity: O(n + m) - We traverse both the source and target strings once.
 * Space Complexity: O(1) - We use a constant amount of space for pointers and counters.
 */
public class AppendCharactersSubsequence {
    /**
     * This method calculates the minimum number of characters that need to be appended to the end of the source string
     * to make the target string a subsequence of the source string.
     * Steps:
     * 1. Initialize two pointers, sourceIndex and targetIndex, to 0.
     * 2. Traverse the source string using sourceIndex and the target string using targetIndex.
     * 3. If the characters at source[sourceIndex] and target[targetIndex] match, increment targetIndex to check the next character in the target string.
     * 4. Always increment sourceIndex to continue traversing the source string.
     * 5. After traversing the source string, the number of characters that need to be appended to source is equal to the length of target minus targetIndex, which represents the number of characters in target that were not matched in source.
     * 6. Return the result.
     *
     * @param source The source string.
     * @param target The target string.
     * @return The minimum number of characters to append to source to make target a subsequence.
     */
    public int appendCharacters(String source, String target) {
        int sourceIndex = 0;
        int targetIndex = 0;
        final int sourceLength = source.length();
        final int targetLength = target.length();

        while (sourceIndex < sourceLength && targetIndex < targetLength) {
            if (source.charAt(sourceIndex) == target.charAt(targetIndex)) {
                targetIndex += 1;
            }
            sourceIndex += 1;
        }

        return targetLength - targetIndex;
    }

    public static void main(String[] args) {
        AppendCharactersSubsequence solution = new AppendCharactersSubsequence();
        String[] sources = {
                "axbyc",
                "abc",
                "a",
                "ab",
                "xyz"
        };

        String[] targets = {
                "abcde",
                "abcbc",
                "a",
                "aba",
                "abc"
        };

        for (int i = 0; i < sources.length; ++i) {
            int result = solution.appendCharacters(sources[i], targets[i]);
            System.out.println((i + 1) + "\tSource: '" + sources[i] + "'");
            System.out.println("\tTarget: '" + targets[i] + "'");
            System.out.println("\tResult: " + result);
            System.out.println(new String(new char[100]).replace('\0', '-'));
        }
    }
}