package org.educative.cip.m2.sliding_window;

/**
 * Minimum Window Subsequence
 * Given two strings str1 and str2, return the minimum window in str1 which will contain all the characters in str2 in order.
 * If there is no such window in str1 that covers all characters in str2, return the empty string "".
 * If there are multiple minimum windows, return the one with the left-most starting index.
 * Example:
 * Input: str1 = "azssstaszaztf", str2 = "saz"
 * Output: "ssta"
 * Explanation: The minimum window in str1 that contains all characters of str2 in order is "ssta".
 * Constraints:
 * 1 <= str1.length <= 2 * 10^3, 1 <= str2.length <= 100
 * str1 and str2 consist of both uppercase and lowercase English letters.
 *
 * Steps to solve the problem:
 * 1. Initialize two pointers, indexS1 and indexS2, to traverse str1 and str2 respectively.
 * 2. Use a while loop to traverse str1 with indexS1.
 * 3. If the characters at indexS1 and indexS2 match, increment indexS2 to check for the next character in str2.
 * 4. If indexS2 reaches the end of str2, it means we found a valid window. Now, we need to find the start of
 *    this window by moving indexS1 backwards and decrementing indexS2 until we find the first character of str2.
 * 5. Update the minimum length and the corresponding subsequence if the current window is smaller than the previously found minimum.
 * 6. Reset indexS1 to the start of the current window and indexS2 to 0 to look for the next possible window.
 * 7. Continue this process until we have traversed the entire str1.
 * 8. Return the minimum subsequence found, or an empty string if no valid window was found.
 *
 * Time Complexity: O(n * m), where n is the length of str1 and m is the length of str2.
 * We traverse through str1 and for each character, we may traverse through str2.
 * Space Complexity: O(1), as we are using constant space for pointers and counters.
 */
public class MinimumWindowSubsequence {

    public static String minWindow(String str1, String str2) {
        // Save the size of str1 and str2
        int sizeStr1 = str1.length();
        int sizeStr2 = str2.length();
        // Initialize the minimum length to positive infinity and indices for str1 and str2
        float length = Float.POSITIVE_INFINITY;
        // Initialize indices for str1 and str2
        int indexS1 = 0;
        int indexS2 = 0;
        int start = 0, end = 0;
        String minSubsequence = "";
        while (indexS1 < sizeStr1) {
            if (str1.charAt(indexS1) == str2.charAt(indexS2)) {
                indexS2 += 1; // increment indexS2 to check for next character in str2
                if (indexS2 == sizeStr2) {
                    start = indexS1;
                    end = indexS1;
                    indexS2 -= 1; // decrement indexS2 to check for last character in str2
                    while (indexS2 >= 0) {
                        if (str1.charAt(start) == str2.charAt(indexS2)) {
                            indexS2 -= 1;
                        }
                        start -= 1;
                    }
                    // increment start to point to the first character of the subsequence
                    start += 1;
                    // check if the length of the current subsequence is less than the minimum length found so far
                    if ((end - start + 1) < length) {
                        length = end - start + 1;
                        minSubsequence = str1.substring(start, end + 1);
                    }
                    indexS1 = start;
                    indexS2 = 0;
                }
            }
            indexS1 += 1;
        }
        return minSubsequence;
    }

    public static void main(String[] args) {
        // Driver code
        String[] str1 = {
                "azssstaszaztf",
                "abcdedeaqdweq", "fgrqsqsnodwmxzkzxwqegkndaa", "zxcvnhss", "alpha", "beta"
        };
        String[] str2 = {
                "saz",
                "adeq", "kzed", "css", "la", "ab"
        };
        for (int i = 0; i < str1.length; i++) {
            System.out.println(i + 1 + ".\tInput String: " + "(" + str1[i] + ", " + str2[i] + ")");
            System.out.println("\tSubsequence string: " + minWindow(str1[i], str2[i]));
            System.out.println(new String(new char[100]).replace('\0', '-'));
        }
    }
}