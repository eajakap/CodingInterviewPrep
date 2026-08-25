package org.educative.cip.m2.two_pointers;

import java.util.*;
/*
 * Time Complexity: O(n + m) - We traverse both the source and target strings once.
 * Space Complexity: O(1) - We use a constant amount of space for pointers and counters.
 */
public class AppendCharactersSubsequence {
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