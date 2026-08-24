package org.educative.cip.m2.two_pointers;

import java.util.Arrays;
import java.util.List;

/*
 * Time Complexity: O(n^2) - In the worst case, we may need to check each character against every other character.
 * Space Complexity: O(n) - We use a character array to store the string.
 */
public class MinMovesPalindrome {
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
