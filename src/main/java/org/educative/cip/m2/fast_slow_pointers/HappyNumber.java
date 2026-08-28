package org.educative.cip.m2.fast_slow_pointers;
/*
 * Time Complexity: O(log n) - The number of iterations is logarithmic in relation to the input number.
 * Space Complexity: O(1) - We use a constant amount of space for pointers and calculations.
 */
public class HappyNumber {
    public static boolean isHappy(int n) {
        int slow = n;
        int fast = n;

        do {
            slow = getNext(slow);
            fast = getNext(getNext(fast));
        } while (slow != fast);

        return slow == 1;
    }

    private static int getNext(int n) {
        int totalSum = 0;
        while (n > 0) {
            int d = n % 10;
            n /= 10;
            totalSum += d * d;
        }
        return totalSum;
    }

    public static int sumOfSquaredDigits(int number) {
        int totalSum = 0;
        while (number > 0) {
            int digit = number % 10;
            number = number / 10;
            totalSum += (Math.pow(digit, 2));
        }
        return totalSum;
    }
    public static boolean isHappyNumber(int n) {
        int slowPointer = n;
        int fastPointer = sumOfSquaredDigits(n);

        while (fastPointer != 1 && slowPointer != fastPointer) {
            slowPointer = sumOfSquaredDigits(slowPointer);
            fastPointer =  sumOfSquaredDigits(sumOfSquaredDigits(fastPointer));
        }
        return fastPointer == 1;
    }

    public static void main(String[] args) {
        int[] testCases = {19, 2, 7, 20, 4};
        for (int i = 0; i < testCases.length; i++) {
            System.out.println((i + 1) + ".\tInput: " + testCases[i]);
            System.out.println("\tIs Happy Number? " + isHappy(testCases[i]));
            System.out.println("-".repeat(100));
            String output = isHappyNumber(testCases[i]) ? "True" : "False";
            System.out.println((i + 1) + ".\tInput: " + testCases[i]);
            System.out.println("\tIs Happy Number? " + output);
            System.out.println("-".repeat(100));
        }
    }
}
