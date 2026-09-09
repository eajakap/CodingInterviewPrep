package org.educative.cip.m2.sliding_window;

import java.util.Arrays;
import java.util.List;

/**
 * Diet Plan Performance
 * Problem: You are given an integer array calories where calories[i] is the number of calories you consumed on the ith day.
 * You are also given an integer k.
 * You have a diet plan that lasts for k consecutive days. The performance of your diet plan is calculated as follows:
 *  - If the total calories consumed in a k-day period is less than lower, you lose 1 point;
 *  - If the total calories consumed in a k-day period is greater than upper, you gain 1 point;
 *  - Otherwise, you gain 0 points.
 * Return the total number of points you gain after all k-day periods.
 * Example 1:
 * Input: calories = [1,2,3,4,5], k = 3, lower = 6, upper = 10
 * Output: 0
 * Explanation: The k-day periods are as follows:
 * - Days 1 to 3: Total calories = 1 + 2 + 3 = 6. This is equal to lower, so you gain 0 points.
 * - Days 2 to 4: Total calories = 2 + 3 + 4 = 9. This is between lower and upper, so you gain 0 points.
 * - Days 3 to 5: Total calories = 3 + 4 + 5 = 12. This is greater than upper, so you gain 1 point.
 *
 * Steps to solve the problem:
 * 1. Initialize a variable to keep track of the total points.
 * 2. Calculate the sum of calories for the first k days and update the points based on the sum.
 * 3. Use a sliding window approach to calculate the sum of calories for the subsequent k-day periods by adding
 *    the next day's calories and subtracting the calories of the day that is no longer in the k-day period.
 * 4. Update the points based on the new sum for each k-day period.
 * 5. Return the total points after evaluating all k-day periods.
 *
 * Time Complexity: O(n) - We traverse the calories list once to calculate the performance points.
 * Space Complexity: O(1) - We use a constant amount of space for variables.
 */
public class DietPlanPerformance {
    /**
     * Calculates the total performance points for all consecutive {@code k}-day windows in the calorie log.
     *
     * <p>For each window, the sum is compared against the lower and upper thresholds. A sum below the lower bound
     * subtracts one point, a sum above the upper bound adds one point, and values in between add zero.</p>
     *
     * @param calories the number of calories consumed each day
     * @param k the number of consecutive days in each evaluation window
     * @param lower the minimum calorie total for a neutral score
     * @param upper the maximum calorie total for a neutral score
     * @return the total points earned across all {@code k}-day windows
     * @implNote Time complexity is O(n), where n is the number of days, and space complexity is O(1).
     */
    public static int dietPlanPerformance(List<Integer> calories, int k, int lower, int upper) {

        // Replace this placeholder return statement with your code
        int points = 0;
        int currentSum = 0;
        // initialize the sum of the first k days
        for (int i =0; i < k ; i++) {
            currentSum += calories.get(i);
        }
        if (currentSum < lower) {
            points--;
        } else if (currentSum > upper) {
            points++;
        }
        // slide the window and update the sum and points
        for (int right = k; right < calories.size(); right++) {
            // update the sum by adding the new element and removing the old element
            currentSum += calories.get(right) - calories.get(right - k);
            if (currentSum < lower) {
                points--;
            } else if (currentSum > upper) {
                points++;
            }
        }
        return points;
    }

    // Driver code
    public static void main(String[] args) {
        List<List<Integer>> testCases = Arrays.asList(
                Arrays.asList(3, 5, 8, 2, 6),     // Test Case 1: Mixed performance
                Arrays.asList(1, 1, 1, 1, 1),     // Test Case 2: All sums below lower limit
                Arrays.asList(10, 12, 15, 20, 25), // Test Case 3: All sums above upper limit
                Arrays.asList(5, 10, 15, 20, 25, 30), // Test Case 4: Mix of poor, normal, and good performances
                Arrays.asList(3, 8, 7, 4, 5, 6)   // Test Case 5: Sliding window with variable performance
        );

        int[] ks = {2, 2, 3, 3, 2};
        int[] lowers = {7, 5, 10, 20, 7};
        int[] uppers = {10, 10, 30, 40, 10};

        // Run each test case
        for (int i = 0; i < testCases.size(); i++) {
            System.out.println("Test Case " + (i + 1) + ":");
            System.out.print("\tcalories = [");
            String s = "";
            for (int j = 0; j < testCases.get(i).size(); j++) {
                s += testCases.get(i).get(j);
                if (j != testCases.get(i).size() - 1) {
                    s += ", ";
                }
            }
            System.out.println(s + "]");
            System.out.println("\tk = " + ks[i]);
            System.out.println("\tlower = " + lowers[i]);
            System.out.println("\tupper = " + uppers[i]);
            int result = dietPlanPerformance(testCases.get(i), ks[i], lowers[i], uppers[i]);
            System.out.println("\n\tpoints = " + result);
            System.out.println(new String(new char[100]).replace('\0', '-'));
        }
    }

}
