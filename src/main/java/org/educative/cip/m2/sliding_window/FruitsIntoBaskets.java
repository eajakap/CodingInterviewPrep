package org.educative.cip.m2.sliding_window;

import java.util.HashMap;
import java.util.Map;

/*
 * Time Complexity: O(n) - We traverse the array once to find the maximum number of fruits that can be collected.
 * Space Complexity: O(1) - We use a constant amount of space for the baskets (at most 2 types of fruits).
 */
public class FruitsIntoBaskets {

    public static int totalFruit(int[] fruits) {
        // Map to count the frequency of fruit types in the current window
        Map<Integer, Integer> baskets = new HashMap<>();

        // Maximum number of fruits collected so far
        int collected = 0;

        // Left boundary of the sliding window
        int left = 0;

        // Iterate over each tree (right boundary of the sliding window)
        for (int right = 0; right < fruits.length; right++) {
            // Add the current fruit to the baskets and increment its count
            baskets.put(fruits[right], baskets.getOrDefault(fruits[right], 0) + 1);

            // If there are more than two types of fruits in the baskets
            while (baskets.size() > 2) {
                // Decrease the count of the fruit at the left boundary
                baskets.put(fruits[left], baskets.get(fruits[left]) - 1);

                // Remove the fruit type from the baskets if its count becomes zero
                if (baskets.get(fruits[left]) == 0) {
                    baskets.remove(fruits[left]);
                }

                // Move the left boundary to the right
                left++;
            }

            // Update the maximum number of fruits collected
            collected = Math.max(collected, right - left + 1);
        }

        // Return the maximum number of fruits that can be collected
        return collected;
    }

    public static void main(String[] args) {
        int[][] fruits = {
                {3,2,1,1,2,3},
                {3,4,2,1,3,2},
                {2,2,2,3,1,2,4,4,4,4},
                {1,1,1,1,1,1,1,1,1,1},
                {2,3,4,1,3,3,1,2,3,4,1,5,2,5,7,7},
                {5,4,3,2,1,1}};

        for (int i = 0; i < fruits.length; i++) {
            System.out.print((i + 1) + ".\tFruits: [");
            for (int j = 0; j < fruits[i].length; j++) {
                if (j > 0) System.out.print(", ");
                System.out.print(fruits[i][j]);
            }
            System.out.println("]");
            System.out.println("\n\tMaximum number of fruit(s) collected: " + totalFruit(fruits[i]));
            System.out.println(new String(new char[100]).replace('\0', '-'));
        }
    }

}
