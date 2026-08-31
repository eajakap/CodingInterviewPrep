package org.educative.cip.m2.sliding_window;

import java.util.List;

/*
 * Time Complexity: O(n) - We traverse the calories list once to calculate the performance points.
 * Space Complexity: O(1) - We use a constant amount of space for variables.
 */
public class DietPlanPerformance {
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

}
