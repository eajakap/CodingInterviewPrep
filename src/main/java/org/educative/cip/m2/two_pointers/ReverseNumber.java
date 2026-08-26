package org.educative.cip.m2.two_pointers;

import java.util.Arrays;

/*
 * Time Complexity: O(d), where d is the number of digits in the input number n. We traverse through the digits of n once.
 *  Space Complexity: O(1) - We use a constant amount of space for pointers and swaps.
 */
class ReverseNumber {
    public int reverseNumber(int number) {
        if (number < Integer.MIN_VALUE || number > Integer.MAX_VALUE) {
            System.out.println("Invalid Number" + number);
            return 0;
        }
        int reversed = 0;
        int prev_reverse = 0;
        int n = Math.abs(number);
        while (n > 0) {
            int digit = n % 10;
            reversed = reversed * 10 + digit;
            if ((reversed - digit) / 10 != prev_reverse) {
                System.out.println("Overflow detected for number: " + number);
                return 0;
            }
            prev_reverse = reversed;
            n /= 10;
        }
        //System.out.println("Reverse Number = " + reversed);
        return (int) ((number > 0) ? reversed : -1 * reversed);
    }

    public static void main(String[] args) {
        int [] testCases = {12345, -52341, 123456789, -987654321, 1000000011};

        ReverseNumber sol = new ReverseNumber();

        for (int i = 0; i < testCases.length; i++) {
            int original = testCases[i];

            int reversed = sol.reverseNumber(testCases[i]);

            System.out.println((i + 1) + "\tInput number: " + original);
            System.out.println("\n\tReversed number: " + reversed);
            System.out.println("----------------------------------------------------------------------------------------------------\n");
        }
    }
}