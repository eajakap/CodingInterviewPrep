package org.educative.cip.m2.two_pointers;

/*
 * Given a non-negative integer c, your task is to determine if there are two integers a and b such that a^2 + b^2 = c.
 * Example 1:
 * Input: c = 5
 * Output: true
 * Explanation: 1 * 1 + 2 * 2 = 5
 * Example 2:
 * Input: c = 3
 * Output: false
 */
public class SumOfSquareNumbers {

    public boolean judgeSquareSum(int c) {
        long left = 0;
        long right = (long)Math.floor( Math.sqrt(c));
        while (left <= right) {
            long sum = left * left + right * right;
            if (sum == c) {
                return true;
            } else if (sum < c) {
                left++;
            } else {
                right--;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        int[] testCases = new int[] {0, 1, 2, 50, 2147483647};
        SumOfSquareNumbers sol = new SumOfSquareNumbers();

        for (int i = 0; i < testCases.length; i++) {
            int c = testCases[i];
            boolean result = sol.judgeSquareSum(c);
            System.out.println((i + 1) + ".\tInput array: [" + c + "]");
            System.out.println("\tTarget: " + c);
            System.out.println("\tResult: " + result);
            System.out.println("-".repeat(100));
        }
    }

}
