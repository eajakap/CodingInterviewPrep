package org.educative.cip.m2.two_pointers;
/**
 * Time Complexity: O(n), where n is the length of the string.
 * We traverse through the string once.
 * Space Complexity: O(1), as we are using constant space for pointers and counters.
 * Given a string s, return true if the s can be palindrome after deleting at most one character from it.
 */
public class ValidPalindromeII {

    public static boolean isPalindrome(String string) {

        // Replace this placeholder return statement with your code
        int left = 0;
        int right = string.length() - 1;
        int mismatch = 0;

        while(left < right) {
            if (string.charAt(left) == string.charAt(right)) {
                // match - continue checking
                left++;
                right--;
            } else {
                mismatch++;
                if (mismatch > 2) {
                    return false;
                }
                right--;
            }
        }

        return true;
    }

    public static boolean validPalindrome(String s) {
        int left = 0;
        int right = s.length() - 1;

        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
                return isPalindrome(s, left + 1, right) || isPalindrome(s, left, right - 1);
            }
            left++;
            right--;
        }
        return true;
    }

    private static boolean isPalindrome(String s, int left, int right) {
        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(isPalindrome("madam"));
        String[] testCases = {
                "madame",
                "dead",
                "abca",
                "tebbem",
                "eeccccbebaeeabebccceea",
                "12321"
        };

        for (String test : testCases) {
            System.out.println("\tString: " + test);
            boolean result = isPalindrome(test);
//            boolean result = validPalindrome(test);
            System.out.println("\n\tResult: " + result);
            System.out.println(new String(new char[100]).replace('\0', '-'));
        }

    }

}
