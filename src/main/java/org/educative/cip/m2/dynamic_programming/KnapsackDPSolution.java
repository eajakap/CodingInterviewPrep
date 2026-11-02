package org.educative.cip.m2.dynamic_programming;

import java.util.Arrays;

/**
 * Problem: 0/1 Knapsack
 * You are given n items, where item i has weight weights[i] and value values[i],
 * and a knapsack that can hold a total weight of at most capacity.
 * Each item can be taken at most once (0 = leave it, 1 = take it), and items cannot be split.
 * Return the maximum total value that fits in the knapsack.
 *
 * Example:
 * Input: weights = [1, 2, 3, 4], values = [3, 4, 5, 6], capacity = 5
 * Output: 9
 * Explanation: take the items with weights 1 and 4 (values 3 + 6 = 9),
 * or the items with weights 2 and 3 (values 4 + 5 = 9). Both fill the capacity of 5 exactly.
 *
 * Recurrence:
 * Let best(i, c) be the maximum value using items i, i + 1, ..., n - 1 with remaining capacity c.
 * For item i there are two choices:
 * 1. Skip it:              best(i + 1, c)
 * 2. Take it, if it fits:  values[i] + best(i + 1, c - weights[i])
 * so best(i, c) = max(skip, take), with base case best(n, c) = 0 (no items left).
 * The answer is best(0, capacity).
 *
 * Why greedy does not work:
 * Taking the item with the best value-to-weight ratio first can leave wasted space.
 * With weights [1, 2, 3, 4] and values [3, 4, 5, 6], the best ratio is item 0 (3 per unit),
 * then item 1 (2 per unit), using weight 3 for value 7; the remaining 2 units fit nothing else.
 * The optimal answer is 9, so every combination has to be considered, which is what DP does efficiently.
 *
 * This class solves the problem four ways, one per nested class, to show how
 * each step improves on the one before (n = number of items):
 *
 * Approach                          Time               Space
 * 1. NormalRecursion                O(2^n)             O(n)              recursion stack
 * 2. TopDownDPMemoization           O(n * capacity)    O(n * capacity)   memo table + recursion stack
 * 3. BottomUpDPTabulation           O(n * capacity)    O(n * capacity)   dp table
 * 4. SpaceOptimizedDP               O(n * capacity)    O(capacity)       one dp row
 */
public class KnapsackDPSolution {

    /**
     * Approach 1: Plain recursion
     *
     * For every item, we make one of two decisions:
     *
     * 1. Skip the current item.
     * 2. Take the current item, if it fits.
     *
     * No results are stored, so the same subproblems may be
     * calculated repeatedly.
     */
    static class NormalRecursion {

        /**
         * Returns the maximum value that can fit in the knapsack.
         *
         * Time complexity: O(2^n) — each item branches into skip and take.
         * Space complexity: O(n) for the recursion stack
         *
         * @param weights  weights[i] is the weight of item i; every weight must be positive
         * @param values   values[i] is the value of item i; same length as weights
         * @param capacity the maximum total weight the knapsack can hold; must be non-negative
         * @return the maximum total value that fits in the knapsack
         * @throws IllegalArgumentException if the input is invalid (see validateInput)
         */
        public static int knapsack(
                int[] weights,
                int[] values,
                int capacity) {

            // Validate once here, not on every recursive call
            validateInput(weights, values, capacity);

            // Start with the first item and the full capacity available
            return knapsack(
                    weights,
                    values,
                    capacity,
                    0);
        }

        /**
         * Recursive helper for {@link #knapsack(int[], int[], int)}.
         * The input has already been validated.
         *
         * @param weights           weights[i] is the weight of item i
         * @param values            values[i] is the value of item i
         * @param remainingCapacity how much weight can still be added
         * @param index             the item being decided on; items before it are already decided
         * @return the maximum value obtainable from items index..n-1 with remainingCapacity
         */
        private static int knapsack(
                int[] weights,
                int[] values,
                int remainingCapacity,
                int index) {

            // No more items are available.
            if (index == weights.length) {
                return 0;
            }

            // Option 1: Skip the current item.
            // Capacity is unchanged; move on to the next item.
            int skip = knapsack(
                    weights,
                    values,
                    remainingCapacity,
                    index + 1);

            // Stays 0 if the item does not fit, so skip always wins in that case
            int take = 0;

            // Option 2: Take the current item if it fits.
            // Gain its value, lose its weight from the capacity, and move on.
            // index + 1 (not index) because each item can be taken at most once.
            if (weights[index] <= remainingCapacity) {

                take = values[index]
                        + knapsack(
                        weights,
                        values,
                        remainingCapacity - weights[index],
                        index + 1);
            }

            // Keep the better of the two choices
            return Math.max(take, skip);
        }
    }

    /**
     * Approach 2: Top-down dynamic programming
     *
     * This uses the same recursive structure, but stores the answer
     * for every (index, remainingCapacity) state.
     * There are n * (capacity + 1) states, and each is solved only once.
     */
    static class TopDownDPMemoization {

        /**
         * Returns the maximum value using recursion and memoization.
         *
         * Time complexity: O(n * capacity) — each (index, capacity) state is solved once.
         * Space complexity: O(n * capacity) for the memo table, plus O(n) for the recursion stack
         *
         * @param weights  weights[i] is the weight of item i; every weight must be positive
         * @param values   values[i] is the value of item i; same length as weights
         * @param capacity the maximum total weight the knapsack can hold; must be non-negative
         * @return the maximum total value that fits in the knapsack
         * @throws IllegalArgumentException if the input is invalid (see validateInput)
         */
        public static int knapsack(
                int[] weights,
                int[] values,
                int capacity) {

            validateInput(weights, values, capacity);

            // memo[index][remainingCapacity] holds the best value for that state once computed.
            // Capacities run from 0 to capacity, so each row needs capacity + 1 slots.
            int[][] memo =
                    new int[weights.length][capacity + 1];

            // -1 means "not computed yet". It is safe as a marker because every answer is at
            // least 0 (skipping all remaining items always gives 0).
            for (int[] row : memo) {
                Arrays.fill(row, -1);
            }

            // Start with the first item and the full capacity available
            return knapsack(
                    weights,
                    values,
                    capacity,
                    0,
                    memo);
        }

        /**
         * Recursive helper for {@link #knapsack(int[], int[], int)} that reads and fills the memo table.
         *
         * @param weights           weights[i] is the weight of item i
         * @param values            values[i] is the value of item i
         * @param remainingCapacity how much weight can still be added
         * @param index             the item being decided on; items before it are already decided
         * @param memo              saved answers, where memo[index][remainingCapacity] is the best value
         *                          for that state, or -1 if not computed yet
         * @return the maximum value obtainable from items index..n-1 with remainingCapacity
         */
        private static int knapsack(
                int[] weights,
                int[] values,
                int remainingCapacity,
                int index,
                int[][] memo) {

            // No more items are available.
            if (index == weights.length) {
                return 0;
            }

            // Return the previously computed answer.
            if (memo[index][remainingCapacity] != -1) {
                return memo[index][remainingCapacity];
            }

            // Option 1: Skip the current item.
            int skip = knapsack(
                    weights,
                    values,
                    remainingCapacity,
                    index + 1,
                    memo);

            // Stays 0 if the item does not fit
            int take = 0;

            // Option 2: Take the current item if it fits.
            if (weights[index] <= remainingCapacity) {

                take = values[index]
                        + knapsack(
                        weights,
                        values,
                        remainingCapacity - weights[index],
                        index + 1,
                        memo);
            }

            // Save the better choice so later calls for this state can reuse it
            memo[index][remainingCapacity] =
                    Math.max(take, skip);

            return memo[index][remainingCapacity];
        }
    }

    /**
     * Approach 3: Bottom-up dynamic programming
     *
     * dp[i][c] represents the maximum value obtainable using
     * the first i items when the knapsack capacity is c.
     *
     * Row 0 (no items) and column 0 (no capacity) are all 0, which Java
     * gives for free when it creates the array. Each later row is built only
     * from the row above it.
     */
    static class BottomUpDPTabulation {

        /**
         * Returns the maximum value using a 2D DP table.
         *
         * Time complexity: O(n * capacity) — one table cell per (item, capacity) pair.
         * Space complexity: O(n * capacity) for the dp table
         *
         * @param weights  weights[i] is the weight of item i; every weight must be positive
         * @param values   values[i] is the value of item i; same length as weights
         * @param capacity the maximum total weight the knapsack can hold; must be non-negative
         * @return the maximum total value that fits in the knapsack
         * @throws IllegalArgumentException if the input is invalid (see validateInput)
         */
        public static int knapsack(
                int[] weights,
                int[] values,
                int capacity) {

            validateInput(weights, values, capacity);

            int numberOfItems = weights.length;

            // One extra row for "no items yet" and one extra column for capacity 0.
            // Every cell starts at 0, which is the correct base case.
            int[][] dp =
                    new int[numberOfItems + 1][capacity + 1];

            // item counts items (1-based), so the item itself is at index item - 1
            for (int item = 1;
                 item <= numberOfItems;
                 item++) {

                int currentWeight = weights[item - 1];
                int currentValue = values[item - 1];

                // Try every capacity from 0 up to the full capacity
                for (int currentCapacity = 0;
                     currentCapacity <= capacity;
                     currentCapacity++) {

                    // Option 1: Skip the current item.
                    // Best value without it, from the row above.
                    int skip =
                            dp[item - 1][currentCapacity];

                    // Stays 0 if the item does not fit
                    int take = 0;

                    // Option 2: Take the current item if it fits.
                    // Its value plus the best value of the earlier items in the capacity left over.
                    // Reading from row item - 1 makes sure this item is used at most once.
                    if (currentWeight <= currentCapacity) {

                        take = currentValue
                                + dp[item - 1]
                                [currentCapacity - currentWeight];
                    }

                    // Keep the better of the two choices
                    dp[item][currentCapacity] =
                            Math.max(take, skip);
                }
            }

            // Best value using all the items with the full capacity
            return dp[numberOfItems][capacity];
        }
    }

    /**
     * Approach 4: Space-optimized bottom-up DP
     *
     * The 2D table is reduced to a 1D array.
     * Each row of the 2D table only reads the row directly above it,
     * so one row can be reused and updated in place.
     *
     * dp[c] represents the maximum value obtainable with
     * capacity c using the items processed so far.
     */
    static class SpaceOptimizedDP {

        /**
         * Returns the maximum value using a 1D DP array.
         *
         * Time complexity: O(n * capacity)
         * Space complexity: O(capacity) for the single dp row
         *
         * @param weights  weights[i] is the weight of item i; every weight must be positive
         * @param values   values[i] is the value of item i; same length as weights
         * @param capacity the maximum total weight the knapsack can hold; must be non-negative
         * @return the maximum total value that fits in the knapsack
         * @throws IllegalArgumentException if the input is invalid (see validateInput)
         */
        public static int knapsack(
                int[] weights,
                int[] values,
                int capacity) {

            validateInput(weights, values, capacity);

            // Before any item is processed, every capacity has a best value of 0
            int[] dp = new int[capacity + 1];

            for (int item = 0;
                 item < weights.length;
                 item++) {

                /*
                 * Capacities must be processed from right to left.
                 *
                 * This prevents the same item from being selected
                 * more than once during the current iteration.
                 *
                 * dp[currentCapacity - weights[item]] is a smaller index. Going right to left,
                 * it has not been updated for this item yet, so it still holds the value
                 * from before this item (the "row above" in the 2D version).
                 * Going left to right would read an already-updated value and could take
                 * the same item twice, which would solve the unbounded knapsack instead.
                 *
                 * The loop stops at weights[item]: smaller capacities cannot fit this item,
                 * so their values stay the same as before (skip).
                 */
                for (int currentCapacity = capacity;
                     currentCapacity >= weights[item];
                     currentCapacity--) {

                    // Option 1: Skip the current item (value from before this item)
                    int skip = dp[currentCapacity];

                    // Option 2: Take the current item plus the best value of the earlier items
                    // in the capacity left over
                    int take = values[item]
                            + dp[currentCapacity - weights[item]];

                    // Keep the better of the two choices, overwriting in place
                    dp[currentCapacity] =
                            Math.max(take, skip);
                }
            }

            // Best value using all the items with the full capacity
            return dp[capacity];
        }
    }

    /**
     * Validates the input arrays and capacity.
     *
     * @param weights  weights[i] is the weight of item i
     * @param values   values[i] is the value of item i
     * @param capacity the maximum total weight the knapsack can hold
     * @throws IllegalArgumentException if weights or values is null, their lengths differ,
     *                                  capacity is negative, or any weight is not positive
     */
    private static void validateInput(
            int[] weights,
            int[] values,
            int capacity) {

        // Both arrays are needed to describe the items
        if (weights == null || values == null) {
            throw new IllegalArgumentException(
                    "Weights and values cannot be null.");
        }

        // Each item needs exactly one weight and one value
        if (weights.length != values.length) {
            throw new IllegalArgumentException(
                    "Weights and values must have the same length.");
        }

        // A negative capacity would also make the memo and dp arrays invalid
        if (capacity < 0) {
            throw new IllegalArgumentException(
                    "Capacity cannot be negative.");
        }

        // A weight of 0 or less would let an item be "taken" without using capacity,
        // and a negative weight would push the remaining capacity past the memo/dp array bounds
        for (int weight : weights) {
            if (weight <= 0) {
                throw new IllegalArgumentException(
                        "Every weight must be positive.");
            }
        }
    }

    /**
     * Driver code: runs all four approaches on the same input and prints the results,
     * so you can check that they agree.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {

        // Expected output: 9 for every approach
        // (weights 1 + 4 for values 3 + 6, or weights 2 + 3 for values 4 + 5)
        int[] weights = {1, 2, 3, 4};
        int[] values = {3, 4, 5, 6};
        int capacity = 5;

        System.out.println(
                "Normal recursion:     "
                        + NormalRecursion.knapsack(
                        weights,
                        values,
                        capacity));

        System.out.println(
                "Top-down memoization: "
                        + TopDownDPMemoization.knapsack(
                        weights,
                        values,
                        capacity));

        System.out.println(
                "Bottom-up tabulation: "
                        + BottomUpDPTabulation.knapsack(
                        weights,
                        values,
                        capacity));

        System.out.println(
                "Space-optimized DP:   "
                        + SpaceOptimizedDP.knapsack(
                        weights,
                        values,
                        capacity));
    }
}
