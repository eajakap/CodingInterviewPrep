package org.educative.cip.m2.two_pointers;

import java.util.*;

/**
 * Lexicographically Largest String Problem:
 * Given a string and an integer numFriends, the task is to find the lexicographically largest substring of
 * the string that can be formed by removing characters such that the resulting substring has a length
 * of (original length - numFriends + 1).
 *
 * Steps to solve the problem:
 * 1. Initialize two pointers i and j to traverse the string.
 * 2. Compare the characters at positions i and j.
 * 3. Finds the first mismatch between the two suffixes - continues comparing characters until a mismatch
 *    is found or the end of the string is reached. k = matched characters count between the two suffixes.
 * 4. If the character at j+k is greater than the character at i+k, update i to j and move j forward. (This means the suffix starting at j is now the best candidate.)
 * 5. If the character at j+k is less than the character at i+k, move j forward. (This means the suffix starting at i is still the best candidate.)
 * 6. Continue this process until j reaches the end of the string.
 * 7. The substring starting from index i to the end of the string will be the lexicographically largest substring.
 * 8. Return the substring of length (original length - numFriends + 1) starting from index i.
 *
 * This class provides a method to find the lexicographically largest substring of a given string
 * that can be formed by removing characters such that the resulting substring has a length of
 * (original length - numFriends + 1).
 *
 * Time Complexity: O(n), where n is the length of the input string. The algorithm traverses the string once.
 * Space Complexity: O(1), as we are using a constant amount of extra space.
 */
public class LargestLexigraphicString
{
    /**
     * Returns the lexicographically largest substring of length (n - numFriends + 1).
     *
     * The algorithm compares suffixes starting at different indices (i and j)
     * and keeps the index 'i' that leads to the lexicographically largest suffix.
     *
     * This is similar to Duval's algorithm for finding the largest suffix.
     */
    public String answerString(String word, int numFriends) {

        // If only one friend, we can take the entire string.
        if (numFriends == 1) {
            return word;
        }

        int n = word.length();

        // i = best starting index found so far
        // j = candidate index to compare against i
        int i = 0;
        int j = 1;

        // Compare suffixes word[i..] and word[j..]
        while (j < n) {

            int k = 0;

            // Move forward while characters match
            // This finds the first mismatch between the two suffixes.
            while (j + k < n && word.charAt(i + k) == word.charAt(j + k)) {
                // characters match, move k forward
                k++;
            }
            // at this point characters at i+k and j+k are different or j+k has reached the end of the string
            // k is the length of the characters that matched so far
            System.out.println("Comparing suffixes starting at i=" + i + " and j=" + j + ", matched length k=" + k);

            // Case 1: mismatch found and suffix at j is lexicographically larger
            // i.e., word[j+k] > word[i+k]
            if (j + k < n && word.charAt(i + k) < word.charAt(j + k)) {

                // Update best starting index
                int oldI = i;
                i = j;

                // Move j forward intelligently:
                // - j + 1: next candidate
                // - oldI + k + 1: skip positions that cannot beat the new best suffix
                j = Math.max(j + 1, oldI + k + 1);

            } else {
                // Case 2: suffix at i is better or equal
                // Skip ahead past the matched region
                j = j + k + 1;
            }
        }

        // Length of substring we must return
        int length = n - numFriends + 1;

        // Return the lexicographically largest substring starting at index i
        return word.substring(i, Math.min(n, i + length));
    }

    // Driver code
    public static void main(String[] args) {
        List<String> words = Arrays.asList("abczd", "dbca", "gggg", "acbd", "zxya", "mnopqr");
        List<Integer> friends = Arrays.asList(2, 2, 4, 2, 3, 3);

        for (int idx = 0; idx < words.size(); idx++) {
            String word = words.get(idx);
            LargestLexigraphicString obj = new LargestLexigraphicString();
            int numFriends = friends.get(idx);
            System.out.println((idx + 1) + ".\tInput: word = '" + word + "', numFriends = " + numFriends);
            String result = obj.answerString(word, numFriends);
            System.out.println("\tLexicographically Largest String: '" + result + "'");
            System.out.println("-".repeat(100));
        }
    }
}