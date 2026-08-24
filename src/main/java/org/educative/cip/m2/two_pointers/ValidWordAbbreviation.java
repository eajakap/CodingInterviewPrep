package org.educative.cip.m2.two_pointers;

/*
 * Time Complexity: O(n+m) - We traverse the abbreviation string (length m) once to validate it
 * against the word (length n).
 * Space Complexity: O(1) - We use a constant amount of space for pointers and counters.
 */
public class ValidWordAbbreviation {
    public static boolean validWordAbbreviation(String word, String abbr) {

        // Replace the following return statement with your code
        int lengthWord = word.length();
        int lengthAbbr = abbr.length();
        int i = 0; // Pointer for word
        int j = 0; // Pointer for abbr
        while (i < lengthWord && j < lengthAbbr) {
            if (Character.isLetter(abbr.charAt(j))) {
                if (word.charAt(i) != abbr.charAt(j)) {
                    return false; // Characters do not match
                }
                i++;
                j++;
            } else if (Character.isDigit(abbr.charAt(j))) {
                if (abbr.charAt(j) == '0') {
                    return false; // Leading zeros are not allowed
                }
                int num = 0;
                while (j < lengthAbbr && Character.isDigit(abbr.charAt(j))) {
                    num = num * 10 + (abbr.charAt(j) - '0');
                    j++;
                }
                i += num; // Move the pointer in word by the number found in abbr
            } else {
                return false; // Unsupported characters do not match
            }
        }
        return i == lengthWord && j == lengthAbbr;
    }

    public static void main(String[] args) {
        ValidWordAbbreviation sol = new ValidWordAbbreviation();

        String[][] testCases = {
                {"substitution", "s10n", "true"},
                {"word", "4", "true"},
                {"hello", "h3o", "true"},
                {"abbreviation", "a]2r0eviati2", "false"},
                {"abcdefg", "a05g", "false"},
        };

        // Iterate through each test case and print results
        for (int idx = 0; idx < testCases.length; idx++) {
            String word = testCases[idx][0];
            String abbr = testCases[idx][1];
            boolean result = sol.validWordAbbreviation(word, abbr);
            System.out.println((idx + 1) + ".\tword: [\"" + word + "\"]");
            System.out.println("\tabbr: \"" + abbr + "\"");
            System.out.println("\tResult: " + result);
            System.out.println("-".repeat(100));
        }
    }

}
