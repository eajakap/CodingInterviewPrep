package org.educative.cip.m2.two_pointers;

import java.util.*;

/**
 * Time Complexity: O(n + m), where n is the length of word1 and m is the length of word2.
 * We traverse through both strings once.
 * Space Complexity: O(n + m), as we are using a StringBuilder to store the merged result.
 * Given two strings word1 and word2, merge the strings by adding letters in alternating order,
 * starting with word1. If a string is longer than the other,
 * append the additional letters onto the end of the merged string.
 */
public class MergeStringsAlternately {

    public String mergeAlternatelyV1(String word1, String word2) {
        int i = 0;
        int j = 0;
        StringBuilder result = new StringBuilder();

        while (i < word1.length() && j < word2.length()) {
            result.append(word1.charAt(i));
            result.append(word2.charAt(j));
            i++;
            j++;
        }

        while (i < word1.length()) {
            result.append(word1.charAt(i));
            i++;
        }

        while (j < word2.length()) {
            result.append(word2.charAt(j));
            j++;
        }

        return result.toString();
    }

    public String mergeAlternatelyV2(String word1, String word2)
    {
        // Replace this placeholder return statement with your code
        char [] word1Array= word1.toCharArray();
        char [] word2Array= word2.toCharArray();
        int len1 = word1.length();
        int len2 = word2.length();
        int mergedIndex = 0;
        char [] mergedWord = new char[len1+len2];
        int word1Ptr = 0, word2Ptr=0;
        while (word1Ptr < len1 && word2Ptr < len2) {
            mergedWord[mergedIndex++] = word1Array[word1Ptr++];
            mergedWord[mergedIndex++] = word2Array[word2Ptr++];
        }
        // append the other charaters
        while (word1Ptr < len1) {
            mergedWord[mergedIndex++] = word1Array[word1Ptr++];
        }
        // append the other charaters
        while (word2Ptr < len2) {
            mergedWord[mergedIndex++] = word2Array[word2Ptr++];
        }
        return String.valueOf(mergedWord);
    }


    public static void main(String[] args) {
        MergeStringsAlternately sol = new MergeStringsAlternately();

        String[][] testCases = {
                {"x", "y"},
                {"hello", "world"},
                {"a", "bcdef"},
                {"zyxwv", "m"},
                {"cat", "dogs"},
        };

        for (int idx = 0; idx < testCases.length; idx++) {
            String w1 = testCases[idx][0];
            String w2 = testCases[idx][1];
            String result = sol.mergeAlternatelyV1(w1, w2);
            System.out.println((idx + 1) + ".\tInput array: [\"" + w1 + "\", \"" + w2 + "\"]");
            System.out.println("\tResult: \"" + result + "\"");
            System.out.println("-".repeat(100));
            String result2 = sol.mergeAlternatelyV2(w1, w2);
            System.out.println((idx + 1) + ".\tInput array: [\"" + w1 + "\", \"" + w2 + "\"]");
            System.out.println("\tResult: \"" + result2 + "\"");
            System.out.println("-".repeat(100));

        }
    }
}