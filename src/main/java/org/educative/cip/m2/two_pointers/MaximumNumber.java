package org.educative.cip.m2.two_pointers;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Given two digit arrays, nums1 and nums2, and a target length k,
 * build the lexicographically largest sequence of length k while preserving
 * the relative order of digits within each input array.
 *
 * Approach:
 * 1. For every possible split i of how many digits we take from nums1,
 *    compute the best subsequence of length i from nums1 and the best
 *    subsequence of length k - i from nums2.
 * 2. Merge the two subsequences greedily to maximize the resulting number.
 * 3. Compare the candidate against the best result seen so far and keep the
 *    larger one.
 * Time Complexity: O((m + n)^3), where m and n are the lengths of nums1 and nums2.
 * Space Complexity: O(m + n) for the merged sequence.
 */

public class MaximumNumber {
    /**
    * Builds the maximum subsequence of a given length by removing smaller digits
    * whenever possible while keeping the original relative order intact.
    *
    * This is the standard monotonic-stack approach for "remove digits to form
    * largest number" style problems.
    */
    private static int[] pickMaxSubsequence(int[] digits, int subseqLength) {
       if (subseqLength == 0) {
           return new int[0];
       }

       // Number of elements we must discard from the left while preserving order.
       int toRemove = digits.length - subseqLength;
       ArrayList<Integer> stack = new ArrayList<>();

       for (int digit : digits) {
           // If a smaller number appears before a larger one, pop the smaller one
           // to keep the sequence as large as possible.
           while (toRemove > 0 && !stack.isEmpty() && stack.get(stack.size() - 1) < digit) {
               stack.remove(stack.size() - 1);
               --toRemove;
           }
           stack.add(digit);
       }

       int[] res = new int[subseqLength];
       for (int i = 0; i < subseqLength; i++) {
           res[i] = stack.get(i);
       }
       return res;
    }

    /**
    * Compares two sequences lexicographically, starting at the given positions.
    *
    * If seq1 and seq2 have the same prefix, the longer sequence wins when it
    * continues with a larger remaining digit. This is exactly what we need when
    * choosing between candidate merged numbers.
    */
    private static boolean isGreaterSuffix(int[] seq1, int i, int[] seq2, int j) {
       while (i < seq1.length && j < seq2.length && seq1[i] == seq2[j]) {
           ++i;
           ++j;
       }

       // If seq2 is exhausted first, seq1 is not smaller; it is either larger or
       // equal in the compared suffix.
       if (j == seq2.length) {
           return true;
       }

       if (i < seq1.length && seq1[i] > seq2[j]) {
           return true;
       }

       return false;
    }

    /**
    * Merges the two subsequences greedily by comparing which next digit should be
    * chosen at each step. The larger digit wins, and when digits are equal, we
    * continue comparing the remaining suffixes.
    */
    private static int[] mergeSequences(int[] seq1, int[] seq2) {
       int p1 = 0, p2 = 0;
       int[] merged = new int[seq1.length + seq2.length];
       int m = 0;

       while (p1 < seq1.length || p2 < seq2.length) {
           if (isGreaterSuffix(seq1, p1, seq2, p2)) {
               merged[m++] = seq1[p1++];
           } else {
               merged[m++] = seq2[p2++];
           }
       }

       return merged;
    }

    /**
    * Finds the maximum k-digit number that can be formed by taking digits from
    * both arrays while maintaining the original order within each array.
    *
    * We iterate over all valid split counts between nums1 and nums2, build the
    * best subsequence for each side, merge the results, and retain the largest
    * candidate seen.
    */
    public static int[] maxNumber(int[] nums1, int[] nums2, int k) {
       int m = nums1.length;
       int n = nums2.length;

       // We cannot use more than k digits total, and we cannot take negative digits.
       int minDigitsFromNums1 = Math.max(0, k - n);
       int maxDigitsFromNums1 = Math.min(k, m);

       int[] bestSequence = new int[0];

       for (int digitsFromNums1 = minDigitsFromNums1;
            digitsFromNums1 <= maxDigitsFromNums1; ++digitsFromNums1) {

           int[] subsequence1 = pickMaxSubsequence(nums1, digitsFromNums1);
           int[] subsequence2 = pickMaxSubsequence(nums2, k - digitsFromNums1);
           int[] candidateSequence = mergeSequences(subsequence1, subsequence2);

           // Keep the best candidate for the final answer.
           if (isGreaterSuffix(candidateSequence, 0, bestSequence, 0)) {
               bestSequence = candidateSequence;
           }
       }

       return bestSequence;
    }

    public static void main(String[] args) {
        int[][][] testCases = new int[][][] {
                { {5, 1, 0},        {9, 2, 3}       },
                { {4, 6, 2},        {1, 7, 8, 3}    },
                { {2, 2, 1},        {2, 9}          },
                { {7, 5, 3},        {4, 6, 8}       },
                { {1, 4, 9},        {9, 1, 4}       },
        };
        int[] ks = new int[] {4, 5, 3, 4, 5};

        MaximumNumber sol = new MaximumNumber();
        for (int i = 0; i < testCases.length; i++) {
            int[] nums1 = testCases[i][0];
            int[] nums2 = testCases[i][1];
            int k = ks[i];

            System.out.printf("%d.\t nums1 = %s, nums2 = %s, k = %d%n",
                    (i + 1), Arrays.toString(nums1), Arrays.toString(nums2), k);

            int[] actual = sol.maxNumber(nums1, nums2, k);
            System.out.printf("\t The maximum number possible is: %s%n", Arrays.toString(actual));
            System.out.println(new String(new char[100]).replace('\0', '-'));
        }
    }
}