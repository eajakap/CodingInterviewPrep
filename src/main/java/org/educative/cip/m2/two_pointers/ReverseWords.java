package org.educative.cip.m2.two_pointers;

import java.util.Arrays;
import java.util.List;

/*
 * Time Complexity: O(n) - We traverse the sentence once to split it into words and then again to reverse the order of the words.
 * Space Complexity: O(n) - We use additional space to store the words in an array and a StringBuilder for the reversed sentence.
 */
public class ReverseWords {
    public static String reverseWords(String sentence) {

        // Replace this placeholder return statement with your code
        String[] words = sentence.trim().split("\\s+");
        List<String> wordlist = Arrays.asList(words);
        StringBuilder sb = new StringBuilder();
        for (int i = wordlist.size() -1; i >= 0; i--){
            String word = wordlist.get(i).trim();
            sb.append(word);
            if (i > 0) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    /*
        * Time Complexity: O(n) - We traverse the sentence once to split it into words and then again to reverse the order of the words.
        * Space Complexity: O(n) - We use additional space to store the words in an array and a StringBuilder for the reversed sentence.
     */
    static class Solution {
        public static String reverseWords(String sentence) {
            sentence = sentence.trim();
            String[] words = sentence.split("\\s+");
            int left = 0, right = words.length - 1;

            while (left < right) {
                String temp = words[left];
                words[left] = words[right];
                words[right] = temp;
                left++;
                right--;
            }

            return String.join(" ", words);
        }

        public static void main(String[] args) {
            List<String> stringsToReverse = Arrays.asList(
                    "Hello World",
                    "a   string   with   multiple   spaces",
                    "Case Sensitive Test 1234",
                    "a 1 b 2 c 3 d 4 e 5",
                    "     trailing spaces",
                    "case test interesting an is this"
            );

            for (int i = 0; i < stringsToReverse.size(); i++) {
                System.out.println((i + 1) + ".\tOriginal string: '" + stringsToReverse.get(i) + "'");
                System.out.println("\tReversed string: '" + reverseWords(stringsToReverse.get(i)) + "'");
                System.out.println(new String(new char[100]).replace('\0', '-'));
            }
        }
    }

    public static void main(String[] args) {
        Solution.main(args);
    }

}
