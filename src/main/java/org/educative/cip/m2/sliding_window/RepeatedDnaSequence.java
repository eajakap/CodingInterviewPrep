package org.educative.cip.m2.sliding_window;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/*
 * Problem: Repeated DNA Sequences
 * Given a string s that represents a DNA sequence, return all the 10-letter-long sequences (substrings) that occur
 * more than once in a DNA molecule. You may return the answer in any order.
 * A DNA sequence consists of a series of nucleotides, each represented by one of the characters 'A', 'C', 'G', or 'T'.
 *
 * Steps to solve the problem:
 * 1. Initialize a HashMap to store the frequency of each 10-letter-long substring
 * 2. Use a sliding window of size 10 to traverse the string and extract each substring
 * 3. Update the frequency of the substring in the HashMap
 * 4. Collect all substrings with frequency greater than 1 and return them as a list
 * 5. Handle edge cases where the string length is less than 10 by returning an empty list
 * 6. Return the list of repeated sequences.
 *
 * Time Complexity: O(n) - We traverse the string once to find all 10-letter-long sequences.
 * Space Complexity: O(n) - We use a set to store the sequences, which can grow up to n/10 in size.
 */
public class RepeatedDnaSequence {

    public List<String> findRepeatedDnaSequences(String s) {
        // Replace this placeholder return statement with your code
        int slidingWindow = 10;
        int left = 0;
        int right = slidingWindow - 1;
        HashMap<String,Integer> freqMap = new HashMap<>();
        while (left <= right && right < s.length()) {
            String substring = s.substring(left, right + 1);
            System.out.println(substring);
            freqMap.put(substring, freqMap.getOrDefault(substring, 0) + 1);
            left++; // Move the left pointer to the right for the next sliding window - substring
            right++; // Move the right pointer to the right for the next sliding window - substring
        }
        List<String> result = freqMap.keySet().stream().filter(key -> freqMap.get(key) > 1).toList();
        return result;
    }

    public static void main(String[] args) {
        RepeatedDnaSequence sol = new RepeatedDnaSequence();
        String[] testCases = {
                "ACGTACGTACGTACGT",
                "ACGTACGTACAATGTACGTACGTACGT",
                "AAAAAAAAAAAAAAAAAAAA",
                "ACGTACGTACACGTACGTAC",
                "GAGAGAGAGAGAGAGAGAGA",
                "ATCGATCGATCGATCGATCGATCG",
        };

        for (int idx = 0; idx < testCases.length; idx++) {
            List<String> result = sol.findRepeatedDnaSequences(testCases[idx]);
            System.out.println((idx + 1) + ".\ts: \"" + testCases[idx] + "\"");
            System.out.println("\tResult: " + result);
            System.out.println("-".repeat(100));
        }
    }

}
