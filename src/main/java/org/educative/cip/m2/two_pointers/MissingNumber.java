package org.educative.cip.m2.two_pointers;

import java.util.Arrays;

/**
 * An array consists of all positive numbers between 1 and n except for one number.
 * Find that number.
 */
public class MissingNumber {

    public static int findMissingNumber(int[] number) {
        int n = number.length;
        // Calculate the expected sum of numbers from 1 to n+1
        int sum = (n + 1) * (n + 2) / 2;
        int expectedSum = 0;
        for (int num : number) {
            expectedSum += num;
        }
        return sum - expectedSum;
    }
    /**
     * The main method serves as an entry point to test the findMissingNumber method with sample inputs.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        // Define various test cases
        int[][] testCases = {
                {1, 2, 4, 5, 6},             // Missing number: 3
                {2, 1, 4, 5, 6},             // Missing number: 3
                {1, 2, 3, 4, 5},             // Missing number: 6
                {2, 3, 4, 5, 6, 7, 8, 9},    // Missing number: 1
                {1},                          // Missing number: 2
                {},                           // Missing number: 1
                {1, 3},                        // Missing number: 2
                {2, 1},                        // Missing number: 3
                {1, 2, 3, 5},                  // Missing number: 4
                {1, 2, 3, 4, 5, 7, 8, 9, 10}  // Missing number: 6
        };

        // Iterate through each test case and display the result
        for (int i = 0; i < testCases.length; i++) {
            int[] testCase = testCases[i];
            int missingNumber = findMissingNumber(testCase);
            System.out.println("Test Case " + (i + 1) + ": " + Arrays.toString(testCase));
            System.out.println("Missing Number: " + missingNumber + "\n");
        }
    }
}
