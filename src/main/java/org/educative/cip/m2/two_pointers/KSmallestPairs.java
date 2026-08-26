package org.educative.cip.m2.two_pointers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Time Complexity: O(k * n), where n is the length of array A and k is the number of pairs to find.
 * In the worst case, we may need to iterate through all elements of A for each of the k pairs.
 * Space Complexity: O(n), where n is the length of array A. We use an additional array to track pointers for each element in A.
 * Given two integer arrays A and B sorted in ascending order, return the k pairs (u1,v1),(u2,v2) ... (uk,vk) with the smallest sums.
 */

public class KSmallestPairs {

    public List<List<Integer>> kSmallestPairs(int[] A, int[] B, int k) {
        List<List<Integer>> result = new ArrayList<>();
        int n = A.length, m = B.length;

        if (n == 0 || m == 0 || k == 0) return result;

        // Each pointer tracks the index in B for A[i]
        int[] ptr = new int[n];

        while (k > 0) {
            int bestIndex = -1;
            int bestSum = Integer.MAX_VALUE;

            // Find the smallest available pair among A[i] + B[ptr[i]]
            for (int i = 0; i < n; i++) {
                if (ptr[i] < m) {
                    int sum = A[i] + B[ptr[i]];
                    if (sum < bestSum) {
                        bestSum = sum;
                        bestIndex = i;
                    }
                }
            }

            if (bestIndex == -1) break; // no more pairs

            result.add(Arrays.asList(A[bestIndex], B[ptr[bestIndex]]));
            ptr[bestIndex]++; // move pointer for that row in A
            k--;
        }

        return result;
    }

    // Driver code to test the implementation
    public static void main(String[] args) {
        KSmallestPairs sol = new KSmallestPairs();
        int[][] testCasesA = {
                {1, 7, 11},
                {1, 1, 2},
                {1, 2},
                {1, 2, 3},
                {1, 2, 3}
        };
        int[][] testCasesB = {
                {2, 4, 6},
                {1, 2, 3},
                {3, 4},
                {1, 2, 3},
                {4, 5, 6}
        };
        int[] testCasesK = {3, 3, 3, 3, 3};

        for (int i = 0; i < testCasesA.length; i++) {
            int[] A = testCasesA[i];
            int[] B = testCasesB[i];
            int k = testCasesK[i];
            List<List<Integer>> result = sol.kSmallestPairs(A, B, k);
            System.out.println("Test Case " + (i + 1) + ":");
            System.out.println("A: " + Arrays.toString(A));
            System.out.println("B: " + Arrays.toString(B));
            System.out.println("k: " + k);
            System.out.println("Result: " + result);
            System.out.println("-".repeat(50));
        }
    }
}
