package org.educative.cip.m2.two_pointers;

import java.util.ArrayList;
import java.util.Arrays;

public class MaximumNumber {
    private static int[] pickMaxSubsequence(int[] digits, int subseqLength) {
        if (subseqLength == 0) {
            return new int[0];
        }
        int toRemove = digits.length - subseqLength;
        ArrayList<Integer> stack = new ArrayList<>();
        for (int digit : digits) {
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

    private static boolean isGreaterSuffix(int[] seq1, int i, int[] seq2, int j) {

        while (i < seq1.length &&
                j < seq2.length &&
                seq1[i] == seq2[j]) {
            ++i;
            ++j;
        }
        if (j == seq2.length) {
            return true;
        }
        if (i < seq1.length && seq1[i] > seq2[j]) {
            return true;
        }
        return false;
    }

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

    public static int[] maxNumber(int[] nums1, int[] nums2, int k) {
        int m = nums1.length;
        int n = nums2.length;
        int minDigitsFromNums1 = Math.max(0, k - n);
        int maxDigitsFromNums1 = Math.min(k, m);

        int[] bestSequence = new int[0];
        for (int digitsFromNums1 = minDigitsFromNums1;
             digitsFromNums1 <= maxDigitsFromNums1; ++digitsFromNums1) {

            int[] subsequence1 = pickMaxSubsequence(nums1, digitsFromNums1);
            int[] subsequence2 = pickMaxSubsequence(nums2, k - digitsFromNums1);
            int[] candidateSequence = mergeSequences(subsequence1, subsequence2);
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