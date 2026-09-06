package org.educative.cip.m2.two_pointers;

import java.util.Arrays;
import java.util.List;

/*
 * Time Complexity: O(n^2) - In the worst case, we may need to check each character against every other character.
 * Space Complexity: O(n) - We use a character array to store the string.
 */
public class MinMovesPalindrome {
    /**
     * This method calculates the minimum number of adjacent swaps required to convert a given string into a palindrome.
     * It uses a two-pointer approach, where one pointer starts from the beginning of the string and the other from the end.
     * The algorithm looks for matching characters and counts the number of swaps needed to bring them together.
     * Steps:
     * 1. Convert the input string into a character array for easier manipulation.
     * 2. Initialize a counter for the number of moves (swaps).
     * 3. Use two pointers, one starting from the beginning (i) and the other from the end (j) of the character array.
     * 4. For each character at position i, search for a matching character from the end (j) towards the beginning.
     * 5. If a match is found, perform adjacent swaps to bring the matching character to the position j and increment the move counter accordingly.
     * 6. If no match is found (i.e., the character at position i is unique),
     *      increment the move counter by the number of characters that need to be swapped to bring the unique character to the middle of the palindrome.
     * 7. Continue this process until the two pointers meet or cross each other.
     * 8. Return the total number of moves (swaps) required to make the string a palindrome.
     *
     * @param s The input string that needs to be transformed into a palindrome.
     * @return The minimum number of adjacent swaps required to make the string a palindrome.
     */
    public int minMovesToMakePalindrome(String s) {
        char[] chars = s.toCharArray();

        int moves = 0;

        for (int i = 0, j = chars.length - 1; i < j; ++i) {
            int k = j;
            for (; k > i; --k) {
                if (chars[i] == chars[k]) {
                    for (; k < j; ++k) {
                        char temp = chars[k];
                        chars[k] = chars[k + 1];
                        chars[k + 1] = temp;
                        ++moves;
                    }
                    --j;
                    break;
                }
            }
            if (k == i) {
                moves += chars.length / 2 - i;
            }
        }
        return moves;
    }

    private void swap(char[] arr, int a, int b) {
        char temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    public int minMovesToMakePalindrome2(String s) {
        char[] chars = s.toCharArray();

        int moves = 0;
        int i =0;
        int j = chars.length - 1;

        while (i < j) {
            if  (chars[i] == chars[j]) {
                i++;
                j--;
                continue;
            }

            int k = j;
            // find the matching character for chars[i] from the right side
            while (k > i && chars[k] != chars[i]) {
                k--;
            }

            if (k == i) {
                // Unique middle character
                swap(chars, i, i + 1);
                moves++;
            } else {
                // Bubble matching character to j
                while (k < j) {
                    swap(chars, k, k + 1);
                    moves++;
                    k++;
                }
                i++;
                j--;
            }
        }
        return moves;
    }

    // Driver code
    public static void main(String[] args) {
        List<String> strings = Arrays.asList("ntiin", "mamda", "ccxx", "arcacer", "w", "ooooooo", "eggeekgbbeg");

        MinMovesPalindrome sol = new MinMovesPalindrome();
        for (int i = 0; i < strings.size(); ++i) {
            System.out.println((i + 1) + ".\ts: " + strings.get(i));
            System.out.println("\tMoves: " + sol.minMovesToMakePalindrome2(strings.get(i)));
            System.out.println(new String(new char[100]).replace("\0", "-"));
        }
    }
}
