package org.educative.cip.m2.sliding_window;

import java.util.HashMap;
import java.util.Map;

/**
 * Fruits Into Baskets
 * Problem: You are visiting a farm that has a single row of fruit trees arranged from left to right.
 * The trees are represented by an integer array fruits where fruits[i] is the type of fruit the ith tree produces.
 * You want to collect as much fruit as possible. However, the owner has some strict rules that you must follow:
 *  - You only have two baskets, and each basket can only hold a single type of fruit.
 *    There is no limit on the amount of fruit each basket can hold.
 *  - Starting from any tree of your choice, you must pick exactly one fruit from every tree (including the start tree)
 *    while moving to the right. The picked fruits must fit in one of your baskets.
 *  - Once you reach a tree with a fruit type that cannot fit in your baskets, you must stop.
 * Given the integer array fruits, return the maximum number of fruits you can pick.
 * Example 1:
 * Input: fruits = [1,2,1]
 * Output: 3
 * Explanation: We can pick from all 3 trees.
 * Example 2:
 * Input: fruits = [0,1,2,2]
 * Output: 3
 * Explanation: We can pick from trees [1,2,2]. If we had started at the first tree, we would only pick from trees [0,1].
 * Example 3:
 * Input: fruits = [1,2,3,2,2]
 * Output: 4
 *  Explanation: We can pick from trees [2,3,2,2]. If we had started at the first tree, we would only pick from trees [1,2].
 * Constraints:
 *  - 1 <= fruits.length <= 10^3
 *  - 0 <= fruits[i] < fruits.length
 *
 * Steps to solve the problem:
 * 1. Use a sliding window approach to keep track of the current range of trees being considered.
 * 2. Use a HashMap to count the frequency of each fruit type in the current window.
 * 3. Expand the window by moving the right pointer and adding the current fruit to the HashMap.
 * 4. If the HashMap contains more than two types of fruits, shrink the window from the left until there are at most two types of fruits in the HashMap.
 * 5. Keep track of the maximum number of fruits collected during the process.
 * 6. Return the maximum number of fruits collected after traversing the entire array.
 *
 * Time Complexity: O(n) - We traverse the array once to find the maximum number of fruits that can be collected.
 * Space Complexity: O(1) - We use a constant amount of space for the baskets (at most 2 types of fruits).
 */
public class FruitsIntoBaskets {

    /**
     * Returns the maximum number of fruits that can be collected while using at most two baskets.
     *
     * <p>The method keeps a sliding window over the tree row and tracks the fruit counts for at most two fruit types.
     * When a third type appears, the left pointer moves forward until the window contains only two types again.</p>
     *
     * @param fruits the array of fruit types on each tree from left to right
     * @return the maximum number of consecutive fruits that can be collected without exceeding two fruit types
     * @implNote Time complexity is O(n) and space complexity is O(1), because there are at most two fruit types in the active window.
     */
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
