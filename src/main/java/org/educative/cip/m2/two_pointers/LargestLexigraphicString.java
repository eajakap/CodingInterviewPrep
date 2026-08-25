package org.educative.cip.m2.two_pointers;

import java.util.*;

public class LargestLexigraphicString
{
    // Function to find the lexicographically largest string
    public String answerString(String word, int numFriends) {
        if (numFriends == 1) {
            return word;
        }

        int n = word.length();
        int i = 0, j = 1;

        while (j < n) {
            int k = 0;
            while (j + k < n && word.charAt(i + k) == word.charAt(j + k)) {
                k++;
            }

            if (j + k < n && word.charAt(i + k) < word.charAt(j + k)) {
                int tempIndex = i;
                i = j;
                j = Math.max(j + 1, tempIndex + k + 1);
            } else {
                j = j + k + 1;
            }
        }

        return word.substring(i, Math.min(n, i + n - numFriends + 1));
    }

    // Driver code
    public static void main(String[] args) {
        List<String> words = Arrays.asList("dbca", "gggg", "acbd", "zxya", "mnopqr");
        List<Integer> friends = Arrays.asList(2, 4, 2, 3, 3);

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