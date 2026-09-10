package org.educative.cip.m2.sliding_window;

/*
 * Find the Index of the First Occurrence of a substring in a string
 * Problem: Given two strings needle and haystack, return the index of the first occurrence of needle in haystack, or -1 if needle is not part of haystack.
 * Example 1:
 * Input: haystack = "sadbutsad", needle = "sad"
 * Output: 0
 * Explanation: "sad" occurs at index 0 and 6. The first occurrence is at index 0, so we return 0.
 * Example 2:
 * Input: haystack = "leetcode", needle = "leeto"
 * Output: -1
 * Explanation: "leeto" did not occur in "leetcode", so we return -1.
 * Constraints:
 * 1 <= haystack.length, needle.length <= 10^4
 * haystack and needle consist of only lowercase English characters.
 *
 */
public class IndexFirstOccurrenceSubstringFinder {

    /**
     * Returns the index of the first occurrence of needle in haystack, or -1 if needle is not part of haystack.
     *
     * @param haystack the string to search within
     * @param needle   the substring to search for
     * @return the index of the first occurrence of needle in haystack, or -1 if not found
     */
    public static int strStr(String haystack, String needle) {
        // If needle is empty, return 0 as per problem statement
        if (needle.isEmpty()) {
            return 0;
        }
        // If haystack is empty or shorter than needle, return -1
        if (haystack.isEmpty() || haystack.length() < needle.length()) {
            return -1;
        }
        // Iterate through haystack to find the first occurrence of needle
        for (int i = 0; i <= haystack.length() - needle.length(); i++) {
            // Check if the substring starting at index i matches needle
            if (haystack.substring(i, i + needle.length()).equals(needle)) {
                return i; // Return the index of the first occurrence
            }
        }
        return -1; // Return -1 if needle is not found in haystack
    }

    public static void main(String[] args) {
        IndexFirstOccurrenceSubstringFinder sol = new IndexFirstOccurrenceSubstringFinder();

        String[][] testCases = {
                {"helloworld", "world"},
                {"abcdefgh", "xyz"},
                {"mississippi", "issi"},
                {"aaaaaab", "aab"},
                {"programming", "gram"},
        };

        for (int idx = 0; idx < testCases.length; idx++) {
            String haystack = testCases[idx][0];
            String needle = testCases[idx][1];
            int result = sol.strStr(haystack, needle);

            System.out.println("\thaystack: \"" + haystack + "\"");
            System.out.println("\tneedle: \"" + needle + "\"");
            System.out.println("\n\tResult: " + result);
            System.out.println("-".repeat(100));
        }
    }

}
