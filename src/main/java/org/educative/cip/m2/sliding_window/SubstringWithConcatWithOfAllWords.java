package org.educative.cip.m2.sliding_window;

import java.util.*;

/**
 * Substring with Concatenation of All Words
 * Problem: Given a string s and an array of strings words, return all starting indices of substring(s) in s
 *          that is a concatenation of each word in words exactly once, in any order,
 *          and without any intervening characters.
 * Example 1:
 * Input: s = "barfoothefoobarman", words = ["foo","bar"]
 * Output: [0,9]
 * Explanation: Substrings starting at index 0 and 9 are "barfoo" and "foobar" respectively. The output order does not matter.
 * Example 2:
 * Input: s = "wordgoodgoodgoodbestword", words = ["word","good","best","word"]
 * Output: []
 * Explanation: There is no substring in s that contains all the words in words exactly once.
 * Example 3:
 * Input: s = "barfoofoobarthefoobarman", words = ["bar","foo","the"]
 * Output: [6,9,12]
 * Explanation: Substrings starting at index 6, 9, and 12 are "foobarthe", "barthefoo", and "thefoobar" respectively. The output order does not matter.
 * Constraints:
 * 1 <= s.length <= 10^3
 * s consists of lower-case English letters.
 * 1 <= words.length <= 1000
 * 1 <= words[i].length <= 30
 * words[i] consists of lower-case English letters.
 *
 *
 */
public class SubstringWithConcatWithOfAllWords {
    public enum SolutionType {
        BRUTE_FORCE,
        SLIDING_WINDOW
    }

    /**
     * Finds all starting indices of substring(s) in "s" that is a concatenation of each word in words exactly once,
     * in any order, and without any intervening characters.
     *
     * @param s the input string
     * @param words the array of words to concatenate
     * @param solutionType the type of solution to use (BRUTE_FORCE or SLIDING_WINDOW)
     * @return a list of starting indices of valid substrings
     */
    public static List<Integer> findSubstring(String s, String[] words, SolutionType solutionType) {
        switch (solutionType) {
            case BRUTE_FORCE:
                return findSubstringBruteForce(s, words);
            case SLIDING_WINDOW:
                return findSubstringSlidingWindow(s, words);
            default:
                throw new IllegalArgumentException("Invalid solution type");
        }
    }

    /**
     * Finds all starting indices of substring(s) in "s" that is a concatenation of each word in words exactly once,
     * in any order, and without any intervening characters using the sliding window technique.
     *
     * Steps to solve the problem:
     * 1. Create a frequency map of the words in the "words" array.
     * 2. Calculate the total length of the concatenated substring (totalLength = number of words * length of each word).
     * 3. Iterate through the string "s", checking each substring of length totalLength.
     * 4. For each substring, split it into words of the same length as the words in the "words" array and
     *   check if the frequency matches the frequency map created in step 1.
     * 5. If a match is found, add the starting index of that substring to the result list.
     * 6. Return the list of starting indices after checking all possible substrings in "s".
     *
     *  Time Complexity: O(n * m), where n is the length of the string "s" and m is the number of words in the "words" array.
     *  Space Complexity: O(m), where m is the number of unique words in the "words" array, used for the frequency map.
     *
     * @param s the input string
     * @param words the array of words to concatenate
     * @return a list of starting indices of valid substrings
     */
    public static List<Integer> findSubstringSlidingWindow(String s, String[] words) {
        List<Integer> result = new ArrayList<>();
        if (s.isEmpty() || words.length == 0) return result;

        int wordLen = words[0].length();
        int wordCount = words.length;
        int totalLen = wordLen * wordCount;

        Map<String, Integer> wordsFreq = new HashMap<>();
        for (String word : words) {
            wordsFreq.put(word, wordsFreq.getOrDefault(word, 0) + 1);
        }

        // Try each possible offset
        for (int offset = 0; offset < wordLen; offset++) {
            int left = offset;
            int matched = 0;
            Map<String, Integer> window = new HashMap<>();
            // Slide the window in increments of wordLen
            for (int right = offset; right + wordLen <= s.length(); right += wordLen) {
                String word = s.substring(right, right + wordLen);

                if (wordsFreq.containsKey(word)) {
                    window.put(word, window.getOrDefault(word, 0) + 1);

                    if (window.get(word) <= wordsFreq.get(word)) {
                        matched++;
                    } else {
                        // Too many occurrences → shrink
                        while (window.get(word) > wordsFreq.get(word)) {
                            String leftWord = s.substring(left, left + wordLen);
                            window.put(leftWord, window.get(leftWord) - 1);
                            if (window.get(leftWord) < wordsFreq.get(leftWord)) {
                                matched--;
                            }
                            left += wordLen;
                        }
                    }

                    // Full match
                    if (matched == wordCount) {
                        result.add(left);
                        // Slide one word forward
                        String leftWord = s.substring(left, left + wordLen);
                        window.put(leftWord, window.get(leftWord) - 1);
                        matched--;
                        left += wordLen; //
                    }

                } else {
                    // Reset window
                    window.clear();
                    matched = 0;
                    left = right + wordLen;
                }
            }
        }

        return result;
    }

    /**
     * Finds all starting indices of substring(s) in "s" that is a concatenation of each word in words exactly once,
     * in any order, and without any intervening characters.
     * Steps to solve the problem:
     * 1. Create a frequency map of the words in the "words" array.
     * 2. Calculate the total length of the concatenated substring (totalLength = number of words * length of each word).
     * 3. Iterate through the string "s", checking each substring of length totalLength.
     * 4. For each substring, split it into words of the same length as the words in the "words" array and
     *    check if the frequency matches the frequency map created in step 1.
     * 5. If a match is found, add the starting index of that substring to the result list.
     * 6. Return the list of starting indices after checking all possible substrings in "s".
     *
     * Time Complexity: O(n * m), where n is the length of the string "s" and m is the number of words in the "words" array.
     * Space Complexity: O(m), where m is the number of unique words in the "words" array, used for the frequency map.
     *
     * @param s the input string
     * @param words the array of words to concatenate
     * @return a list of starting indices of valid substrings
     */
    public static List<Integer> findSubstringBruteForce(String s, String[] words) {
        Map<String, Integer> wordsFreq = new HashMap<>();
        int totalLength = 0;
        for (String word : words) {
            totalLength += word.length();
            wordsFreq.put(word, wordsFreq.getOrDefault(word, 0) + 1);
        }
        int left = 0;
        List<Integer> result = new ArrayList<>();
        // Iterate through the string "s" to find valid starting indices
        for (int right = 0; right <= s.length() - totalLength; right++) {
            String substring = s.substring(right, right + totalLength);
            Map<String, Integer> seenWords = new HashMap<>();
            // Split the substring into words of the same length as the words in the "words" array
            for (int i = 0; i < substring.length(); i += words[0].length()) {
                String word = substring.substring(i, i + words[0].length());
                seenWords.put(word, seenWords.getOrDefault(word, 0) + 1);
            }
            if (seenWords.equals(wordsFreq)) {
                result.add(right);
            }
        }
        return result;
    }

    // ===============================
    // Driver code to test the function
    // ===============================
    public static void main(String[] args) {

        // 2D array containing lists of words for each test case
        String[][] twoDArray = {
//                {"one", "two"},
//                {"cat", "dog"},
//                {"foo", "bar"},                         // Basic example
//                {"word", "good", "best", "word"},       // Repeated words
//                {"bar", "foo", "the"},                  // Multiple valid indices
                {"foo", "bar"},                         // Single match
//                {"hi", "jk"}                            // No valid substring
        };

        // Corresponding list of test strings (1D array)
        String[] testStrings = {
//                "oneonetwo",
//                "catcatdogdog",
//                "barfoothefoobarman",
//                "wordgoodgoodgoodbestword",
                "barfoofoobarthefoobarman",
//                "foobar",
//                "abcdefg"
        };

        // Loop through both arrays together
        for (int i = 0; i < testStrings.length; i++) {
            List<Integer> result = findSubstring(testStrings[i], twoDArray[i], SolutionType.SLIDING_WINDOW);

            System.out.println((i + 1));
            System.out.println("\tString: " + testStrings[i]);
            System.out.println("\tWords: " + Arrays.toString(twoDArray[i]));
            System.out.println("\tOutput: " + result);
            System.out.println(new String(new char[100]).replace('\0', '-'));
        }
    }

}
