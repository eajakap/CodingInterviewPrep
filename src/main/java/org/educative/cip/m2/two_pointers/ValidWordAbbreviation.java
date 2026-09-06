package org.educative.cip.m2.two_pointers;

/*
 * Time Complexity: O(n+m) - We traverse the abbreviation string (length m) once to validate it
 * against the word (length n).
 * Space Complexity: O(1) - We use a constant amount of space for pointers and counters.
 */
public class ValidWordAbbreviation {
    /*
     * Steps:
     * 1. Initialize two pointers, i for the word and j for the abbreviation.
     * 2. Traverse both the word and the abbreviation string:
     *    - If the current character in the abbreviation is a letter, check if it matches the corresponding character in the word.
     *      if it matches, move both pointers forward. If it does not match, return false.
     *    - If the current character in the abbreviation is a digit. Verify if the digit is not a leading zero. if leading zero, return false.
     *      Otherwise, calculate the number and move the pointer in the word accordingly.
     *    - If the characters do not match or there are leading zeros in the abbreviation, return false.
     * 3. After traversing both strings, check if both pointers have reached the end of their respective strings.
     *
     * This method checks if the given abbreviation is a valid abbreviation of the word.
     * @param word The original word to be checked against.
     * @param abbr The abbreviation to validate.
     * @return true if the abbreviation is valid, false otherwise.
     */
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
