package org.educative.cip.m2.dynamic_programming;

import java.util.Arrays;

/**
 * Dynamic Programming Solutions
 * Dynamic Programming = solve smaller subproblems once, save their answers, and reuse them.
 * Examples of problems that can be solved using dynamic programming include:
 * - Fibonacci sequence : F(n) = F(n-1) + F(n-2)
 * - Longest common subsequence
 * - Knapsack problem
 * - Coin change problem
 * - Matrix chain multiplication
 * - Minimum edit distance
 * - Maximum subarray sum
 * - Longest increasing subsequence
 * - Optimal binary search tree
 * - Rod cutting problem
 * - Egg dropping problem
 * - Traveling salesman problem
 * - Partition problem
 * - Subset sum problem
 * - Word break problem
 * - Palindrome partitioning
 * - Maximum product subarray
 * Dynamic Programming Approaches:
 * 1. Top-down approach (Memoization): Solve the problem recursively and store the results of subproblems in a
 *      data structure (like an array or a hash map) to avoid redundant calculations.
 *    - Recursion + Memoization
 *          Big problem
 *              ↓
 *           Smaller problem
 *              ↓
 *           Smaller problem
 *              ↓
 *           Base case
 * 2. Bottom-up approach (Tabulation): Solve the problem iteratively by building up a table of solutions
 *      to subproblems, starting from the smallest subproblem and working up to the original problem.
 *      - Iteration + Tabulation
 *           Base cases
 *              ↓
 *           Small problem
 *              ↓
 *           Bigger problem
 *              ↓
 *           Final answer
 * 3. Space optimization: Reduce the space complexity of the bottom-up approach by only storing the
 *      necessary previous states instead of the entire table.
 * 4. State compression: Use bit manipulation or other techniques to represent the state of the
 *      problem in a more compact form, reducing the space complexity.
 * 5. Divide and conquer with dynamic programming: Break the problem into smaller subproblems,
 *      solve them independently, and combine their solutions to solve the original problem.
 *
 * Problem: Climbing Stairs
 * You are climbing a staircase with n steps. Each move climbs either 1 or 2 steps.
 * Return the number of distinct ways to reach the top.
 *
 * Example 1:
 * Input: n = 2
 * Output: 2
 * Explanation: 1 + 1, or 2
 *
 * Example 2:
 * Input: n = 3
 * Output: 3
 * Explanation: 1 + 1 + 1, 1 + 2, or 2 + 1
 *
 * Constraints:
 * 0 <= n <= 45
 *
 * Recurrence:
 * The last move onto stair n is either a 1-step from stair n - 1 or a 2-step from stair n - 2.
 * Every way to reach stair n ends in exactly one of those two moves, so
 *     ways(n) = ways(n - 1) + ways(n - 2)
 * with base cases ways(0) = 1 (do nothing) and ways(1) = 1 (a single 1-step).
 *
 * Link to Fibonacci:
 * This is the Fibonacci recurrence shifted by one: ways(n) = F(n + 1).
 * n:        0  1  2  3  4  5   6   7
 * ways(n):  1  1  2  3  5  8  13  21
 *
 * This class solves the problem four ways, one per nested class, to show how
 * each step improves on the one before:
 *
 * Approach                          Time      Space
 * 1. NormalRecursion                O(2^n)    O(n)   recursion stack
 * 2. TopDownDPMemoization           O(n)      O(n)   memo array + recursion stack
 * 3. BottomUpDPTabulation           O(n)      O(n)   dp table
 * 4. SpaceOptimizedDP               O(n)      O(1)   two variables
 *
 * Note: the results are int values. ways(45) = 1,836,311,903 is the largest that fits;
 * ways(46) is larger than Integer.MAX_VALUE, so every approach rejects n > 45.
 */
public class ClimbingStairsDPSolution {

    /**
     * Largest number of stairs whose answer fits in an int.
     * ways(45) = 1,836,311,903, but ways(46) = 2,971,215,073 would overflow.
     */
    private static final int MAX_STAIRS = 45;

    /**
     * Approach 1: Plain recursion without storing previously computed results.
     *
     * To reach stair n, the final move must be either:
     * 1. A one-step move from stair n - 1
     * 2. A two-step move from stair n - 2
     *
     * Recurrence:
     * ways(n) = ways(n - 1) + ways(n - 2)
     */
    static class NormalRecursion {

        /**
         * Returns the number of distinct ways to climb n stairs.
         *
         * Time complexity: O(2^n)
         * Space complexity: O(n) for the recursion stack
         *
         * @param n number of stairs; must be between 0 and 45
         * @return number of distinct ways to reach the top
         * @throws IllegalArgumentException if n is negative or greater than 45
         */
        public static int climbStairs(int n) {
            // Validate once here, not on every recursive call
            validateInput(n);

            return countWays(n);
        }

        /**
         * Recursive helper for {@link #climbStairs(int)}. The input has already been validated.
         *
         * @param n current number of stairs
         * @return number of distinct ways to climb n stairs
         */
        private static int countWays(int n) {

            // There is one way to climb 0 stairs and one way to climb 1 stair.
            if (n <= 1) {
                return 1;
            }

            // Last move was a 1-step from n - 1 or a 2-step from n - 2.
            // Both subproblems are solved again from scratch every time, which is why this is O(2^n).
            return countWays(n - 1)
                    + countWays(n - 2);
        }
    }

    /**
     * Approach 2: Top-down dynamic programming.
     *
     * Uses recursion with memoization so that every subproblem is
     * calculated only once.
     */
    static class TopDownDPMemoization {

        /**
         * Returns the number of distinct ways to climb n stairs.
         *
         * Time complexity: O(n)
         * Space complexity: O(n) for the memo array and recursion stack
         *
         * @param n number of stairs; must be between 0 and 45
         * @return number of distinct ways to reach the top
         * @throws IllegalArgumentException if n is negative or greater than 45
         */
        public static int climbStairs(int n) {
            validateInput(n);

            // memo[i] holds ways(i) once computed; -1 means "not computed yet".
            // -1 is safe as a marker because a number of ways is never negative.
            int[] memo = new int[n + 1];
            Arrays.fill(memo, -1);

            return climbStairs(n, memo);
        }

        /**
         * Recursive helper that calculates and saves the result for
         * each number of stairs.
         *
         * @param n     current number of stairs
         * @param memo  memo[i] stores the number of ways to climb i stairs
         * @return number of distinct ways to climb n stairs
         */
        private static int climbStairs(int n, int[] memo) {

            // Base cases: one way to climb 0 or 1 stair.
            if (n <= 1) {
                return 1;
            }

            // Return the previously calculated result.
            if (memo[n] != -1) {
                return memo[n];
            }

            // Calculate the result once and store it.
            // Last move was a 1-step from n - 1 or a 2-step from n - 2.
            memo[n] = climbStairs(n - 1, memo)
                    + climbStairs(n - 2, memo);

            return memo[n];
        }
    }

    /**
     * Approach 3: Bottom-up dynamic programming using tabulation.
     *
     * Starts with the base cases and builds the answer iteratively.
     */
    static class BottomUpDPTabulation {

        /**
         * Returns the number of distinct ways to climb n stairs.
         *
         * Time complexity: O(n)
         * Space complexity: O(n) for the DP array
         *
         * @param n number of stairs; must be between 0 and 45
         * @return number of distinct ways to reach the top
         * @throws IllegalArgumentException if n is negative or greater than 45
         */
        public static int climbStairs(int n) {
            validateInput(n);

            // Base cases: one way to climb 0 or 1 stair.
            // Returning early also keeps dp[1] below from going out of bounds when n = 0.
            if (n < 2) {
                return n;
            }

            // dp[i] represents the number of ways to climb i stairs.
            int[] dp = new int[n + 1];

            dp[0] = 1; // One way to climb 0 stairs: do nothing
            dp[1] = 1; // One way to climb 1 stair: a single 1-step

            // Fill the table upward; dp[i - 1] and dp[i - 2] are always already filled
            for (int i = 2; i <= n; i++) {
                // Last move was a 1-step from i - 1 or a 2-step from i - 2.
                dp[i] = dp[i - 1] + dp[i - 2];
            }

            return dp[n];
        }
    }

    /**
     * Approach 4: Space-optimized bottom-up dynamic programming.
     *
     * Since the current answer only depends on the previous two answers,
     * the entire DP array is unnecessary.
     */
    static class SpaceOptimizedDP {

        /**
         * Returns the number of distinct ways to climb n stairs.
         *
         * Time complexity: O(n)
         * Space complexity: O(1)
         *
         * @param n number of stairs; must be between 0 and 45
         * @return number of distinct ways to reach the top
         * @throws IllegalArgumentException if n is negative or greater than 45
         */
        public static int climbStairs(int n) {
            validateInput(n);

            // Base cases: one way to climb 0 or 1 stair.
            if (n <= 1) {
                return 1;
            }

            // Number of ways to climb 0 and 1 stairs.
            // twoStepsBefore = ways(i - 2), oneStepBefore = ways(i - 1)
            int twoStepsBefore = 1;
            int oneStepBefore = 1;

            for (int i = 2; i <= n; i++) {
                // ways(i) = ways(i - 1) + ways(i - 2)
                int current = oneStepBefore + twoStepsBefore;

                // Shift the window forward by one stair for the next iteration
                twoStepsBefore = oneStepBefore;
                oneStepBefore = current;
            }

            // After the loop, oneStepBefore holds ways(n)
            return oneStepBefore;
        }
    }

    /**
     * Validates the number of stairs.
     *
     * @param n number of stairs
     * @throws IllegalArgumentException when n is negative, or greater than 45
     *                                  (the answer would overflow an int)
     */
    private static void validateInput(int n) {
        if (n < 0) {
            throw new IllegalArgumentException(
                    "The number of stairs cannot be negative.");
        }
        if (n > MAX_STAIRS) {
            throw new IllegalArgumentException(
                    "The number of stairs cannot be greater than " + MAX_STAIRS
                            + ", because the answer would overflow an int.");
        }
    }

    /**
     * Driver code: runs all four approaches on the same inputs and prints the results,
     * so you can check that they agree. Also shows that invalid inputs are rejected.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {

        // Expected outputs: 1, 1, 2, 8, 89, 10946
        int[] testCases = {0, 1, 2, 5, 10, 20};

        for (int i = 0; i < testCases.length; i++) {
            int n = testCases[i];

            System.out.println((i + 1) + ".\tn = " + n);

            System.out.println(
                    "\tNormal recursion:     "
                            + NormalRecursion.climbStairs(n));

            System.out.println(
                    "\tTop-down memoization: "
                            + TopDownDPMemoization.climbStairs(n));

            System.out.println(
                    "\tBottom-up tabulation: "
                            + BottomUpDPTabulation.climbStairs(n));

            System.out.println(
                    "\tSpace-optimized:      "
                            + SpaceOptimizedDP.climbStairs(n));

            // Print a separator line of 100 dashes
            System.out.println("-".repeat(100));
        }

        // Invalid inputs: a negative count, and a count whose answer would overflow an int
        int[] invalidCases = {-1, MAX_STAIRS + 1};

        for (int n : invalidCases) {
            try {
                SpaceOptimizedDP.climbStairs(n);
            } catch (IllegalArgumentException e) {
                System.out.println("n = " + n + "\tRejected: " + e.getMessage());
            }
        }
    }
}