package org.educative.cip.m2.sliding_window;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
 * Time Complexity: O(n) - We traverse the string once to find all 10-letter-long sequences.
 * Space Complexity: O(n) - We use a set to store the sequences, which can grow up to n/10 in size.
 * A DNA sequence consists of a series of nucleotides, each represented by one of the characters 'A', 'C', 'G', or 'T'.
 * Given a string s representing a DNA sequence, find and return all 10-letter-long substrings
 * that appear more than once within s. The result may be returned in any order.
 */
public class RepeatedDnaSequence {

    public List<String> findRepeatedDnaSequences(String s) {
        // Replace this placeholder return statement with your code
        int slidingWindow = 10;
        int left = 0;
        int right = slidingWindow - 1;
        HashMap<String,Integer> map = new HashMap<>();
        while (left <= right && right < s.length()) {
            String substring = s.substring(left, right + 1);
            System.out.println(substring);
            if (map.containsKey(substring)) {
                map.put(substring, map.get(substring) + 1);
            } else {
                map.put(substring, 1);
            }
            left++;
            right++;
        }
        List<String> result = new ArrayList<>();
        for (String key : map.keySet()) {
            if (map.get(key) > 1) {
                result.add(key);
            }
        }
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
