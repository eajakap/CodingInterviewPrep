package org.educative.cip.m2.two_pointers;

import java.util.*;

/**
 * Problem: Merge Strings Alternately
 * Given two strings word1 and word2, merge the strings by adding letters in alternating order,
 * starting with word1. If a string is longer than the other,
 * append the additional letters onto the end of the merged string.
 * Example 1:
 * Input: word1 = "abc", word2 = "pqr"
 * Output: "apbqcr"
 * Example 2:
 * Input: word1 = "ab", word2 = "pqrs"
 * Output: "apbqrs"
 * Constraints:
 * 1 <= word1.length, word2.length <= 100
 * word1 and word2 consist of lowercase English letters.
 *
 * Steps to solve the problem:
 * 1. Initialize two pointers, one for each string.
 * 2. Use a StringBuilder to build the merged string.
 * 3. Traverse both strings using the pointers, appending characters from each string alternately.
 * 4. Append the remaining characters from the longer string, if any.
 * 5. Return the merged string.
 *
 * Time Complexity: O(n + m), where n is the length of word1 and m is the length of word2.
 * We traverse through both strings once.
 * Space Complexity: O(n + m), as we are using a StringBuilder to store the merged result.
 */
public class MergeStringsAlternately {

    /**
     * Merges two strings alternately, starting with the first string.
     * If one string is longer, appends the remaining characters of that string to the result.
     * Steps:
     * 1. Initialize two pointers, one for each string.
     * 2. Use a StringBuilder to build the merged string.
     * 3. Traverse both strings using the pointers, appending characters from each string alternately.
     * 4. Append the remaining characters from the longer string, if any.
     * 5. Return the merged string.
     *
     * @param word1 The first input string.
     * @param word2 The second input string.
     * @return The merged string with characters from both strings in alternating order.
     */
    public String mergeAlternatelyV1(String word1, String word2) {
        int i = 0; // word1 pointer
        int j = 0; // word2 pointer

        // Use StringBuilder for efficient string concatenation
        StringBuilder result = new StringBuilder();

        // Traverse both strings and append characters alternately
        while (i < word1.length() && j < word2.length()) {
            result.append(word1.charAt(i)); // Append character from word1
            result.append(word2.charAt(j)); // Append character from word2
            i++; // Move to the next character in word1
            j++; // Move to the next character in word2
        }

        // Append any remaining characters from word1
        while (i < word1.length()) {
            result.append(word1.charAt(i)); // Append remaining character from word1
            i++; // Move to the next character in word1
        }

        // Append any remaining characters from word2
        while (j < word2.length()) {
            result.append(word2.charAt(j)); // Append remaining character from word2
            j++; // Move to the next character in word2
        }

        return result.toString(); // Convert StringBuilder to String and return
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