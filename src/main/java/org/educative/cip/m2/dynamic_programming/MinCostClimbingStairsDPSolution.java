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
 * Problem: Min Cost Climbing Stairs
 * You are given an integer array cost, where cost[i] is the cost of stepping on stair i.
 * Once you pay the cost of a stair, you can climb either 1 or 2 stairs.
 * You can start on stair 0 or stair 1 for free.
 * Return the minimum cost to reach the top, which is the position just past the last stair (index n).
 *
 * Example 1:
 * Input: cost = [10, 15, 20]
 * Output: 15
 * Explanation: start on stair 1, pay 15, and climb 2 stairs to the top.
 *
 * Example 2:
 * Input: cost = [1, 100, 1, 1, 1, 100, 1, 1, 100, 1]
 * Output: 6
 * Explanation: start on stair 0 and step only on the stairs that cost 1, paying 1 six times.
 *
 * Constraints:
 * {@code 2 <= cost.length <= 1000}
 * {@code 0 <= cost[i] <= 999}
 *
 * Recurrence:
 * Let minCost(i) be the cheapest way to stand on stair i (before paying cost[i]).
 * You arrive on stair i either from stair i - 1 (after paying cost[i - 1])
 * or from stair i - 2 (after paying cost[i - 2]), so
 *     minCost(i) = min(minCost(i - 1) + cost[i - 1], minCost(i - 2) + cost[i - 2])
 * with base cases minCost(0) = 0 and minCost(1) = 0, because starting on either is free.
 * The answer is minCost(n), where n = cost.length is the top.
 *
 * Link to Climbing Stairs:
 * Same two moves and the same "last move came from i - 1 or i - 2" idea, but instead of
 * adding the two options (counting ways), take the cheaper one (min).
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
 * Note: the largest possible answer is about 1000 * 999, so an int never overflows.
 */
public class MinCostClimbingStairsDPSolution {

    /**
     * Approach 1: Plain recursion without storing previously computed results.
     *
     * To stand on stair i, the last move must be either:
     * 1. A one-step move from stair i - 1, paying cost[i - 1]
     * 2. A two-step move from stair i - 2, paying cost[i - 2]
     *
     * Recurrence:
     * minCost(i) = min(minCost(i - 1) + cost[i - 1], minCost(i - 2) + cost[i - 2])
     *
     * The same stairs are solved over and over, so the number of calls grows exponentially.
     */
    static class NormalRecursion {

        /**
         * Returns the minimum cost to reach the top of the stairs.
         *
         * Time complexity: O(2^n), where n is the length of cost — each call makes two more calls.
         * Space complexity: O(n) for the recursion stack
         *
         * @param cost cost[i] is the price of stepping on stair i; must have at least 2 stairs
         * @return minimum cost to reach the top
         * @throws IllegalArgumentException if cost is null or has fewer than 2 stairs
         */
        public static int minCostClimbingStairs(int[] cost) {
            // Validate once here, not on every recursive call
            validateInput(cost);

            // The top is the position just past the last stair
            return minCost(cost, cost.length);
        }

        /**
         * Recursive helper for {@link #minCostClimbingStairs(int[])}. The input has already been validated.
         *
         * @param cost cost[i] is the price of stepping on stair i
         * @param n    the stair to reach (cost.length means the top)
         * @return minimum cost to stand on stair n
         */
        private static int minCost(int[] cost, int n) {

            // Base cases: starting on stair 0 or stair 1 is free
            if (n <= 1) {
                return 0;
            }

            // Reach n from n - 1: pay for stair n - 1, then climb 1
            int oneStep =
                    minCost(cost, n - 1) + cost[n - 1];

            // Reach n from n - 2: pay for stair n - 2, then climb 2
            int twoSteps =
                    minCost(cost, n - 2) + cost[n - 2];

            // Choose the cheaper path
            return Math.min(oneStep, twoSteps);
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
         * Returns the minimum cost to reach the top of the stairs.
         *
         * Time complexity: O(n), where n is the length of cost — each stair is solved once.
         * Space complexity: O(n) for the memo array and recursion stack
         *
         * @param cost cost[i] is the price of stepping on stair i; must have at least 2 stairs
         * @return minimum cost to reach the top
         * @throws IllegalArgumentException if cost is null or has fewer than 2 stairs
         */
        public static int minCostClimbingStairs(int[] cost) {
            validateInput(cost);

            // memo[i] holds minCost(i) once computed; -1 means "not computed yet".
            // -1 is safe as a marker because a cost is never negative.
            // Size cost.length + 1 so there is a slot for the top (index cost.length).
            int[] memo = new int[cost.length + 1];

            Arrays.fill(memo, -1);

            // The top is the position just past the last stair
            return minCost(cost.length, cost, memo);
        }

        /**
         * Recursive helper for {@link #minCostClimbingStairs(int[])} that reads and fills the memo array.
         *
         * @param i    the stair to reach (cost.length means the top)
         * @param cost cost[i] is the price of stepping on stair i
         * @param memo saved answers, where memo[i] is minCost(i) or -1 if not computed yet
         * @return minimum cost to stand on stair i
         */
        private static int minCost(
                int i,
                int[] cost,
                int[] memo) {

            // Base cases: starting on stair 0 or stair 1 is free
            if (i <= 1) {
                return 0;
            }

            // Already computed: reuse the saved answer instead of recursing again
            if (memo[i] != -1) {
                return memo[i];
            }

            // Reach i from i - 1: pay for stair i - 1, then climb 1
            int oneStep =
                    minCost(i - 1, cost, memo)
                            + cost[i - 1];

            // Reach i from i - 2: pay for stair i - 2, then climb 2
            int twoSteps =
                    minCost(i - 2, cost, memo)
                            + cost[i - 2];

            // Save the cheaper option so later calls can reuse it
            memo[i] = Math.min(oneStep, twoSteps);

            return memo[i];
        }
    }

    /**
     * Approach 3: Bottom-up dynamic programming using tabulation.
     *
     * Starts with the base cases and builds the answer iteratively,
     * with no recursion.
     */
    static class BottomUpDPTabulation {

        /**
         * Returns the minimum cost to reach the top of the stairs.
         *
         * Time complexity: O(n), where n is the length of cost — one loop from 2 to n.
         * Space complexity: O(n) for the dp table
         *
         * @param cost cost[i] is the price of stepping on stair i; must have at least 2 stairs
         * @return minimum cost to reach the top
         * @throws IllegalArgumentException if cost is null or has fewer than 2 stairs
         */
        public static int minCostClimbingStairs(int[] cost) {
            validateInput(cost);

            // n is both the number of stairs and the index of the top
            int n = cost.length;

            // dp[i] is the minimum cost to stand on stair i; dp[n] is the top
            int[] dp = new int[n + 1];

            // It costs nothing to start at stair 0 or stair 1.
            dp[0] = 0;
            dp[1] = 0;

            // Fill the table upward; dp[i - 1] and dp[i - 2] are always already filled
            for (int i = 2; i <= n; i++) {

                // Reach i from i - 1: pay for stair i - 1, then climb 1
                int oneStep =
                        dp[i - 1] + cost[i - 1];

                // Reach i from i - 2: pay for stair i - 2, then climb 2
                int twoSteps =
                        dp[i - 2] + cost[i - 2];

                // Keep the cheaper option
                dp[i] = Math.min(oneStep, twoSteps);
            }

            // Minimum cost to reach the top
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
         * Returns the minimum cost to reach the top of the stairs, keeping only the last two answers.
         *
         * Time complexity: O(n), where n is the length of cost — one loop from 2 to n.
         * Space complexity: O(1) — only two previous answers are stored.
         *
         * @param cost cost[i] is the price of stepping on stair i; must have at least 2 stairs
         * @return minimum cost to reach the top
         * @throws IllegalArgumentException if cost is null or has fewer than 2 stairs
         */
        public static int minCostClimbingStairs(int[] cost) {
            validateInput(cost);

            // prev2 = minCost(i - 2) and prev1 = minCost(i - 1).
            // They start as minCost(0) and minCost(1), which are both 0 because starting is free.
            int prev2 = 0;
            int prev1 = 0;

            // i runs up to cost.length, which is the top
            for (int i = 2; i <= cost.length; i++) {

                // Reach i from i - 1: pay for stair i - 1, then climb 1
                int oneStep =
                        prev1 + cost[i - 1];

                // Reach i from i - 2: pay for stair i - 2, then climb 2
                int twoSteps =
                        prev2 + cost[i - 2];

                // minCost(i) is the cheaper of the two
                int current =
                        Math.min(oneStep, twoSteps);

                // Shift the window forward by one stair for the next iteration
                prev2 = prev1;
                prev1 = current;
            }

            // After the loop, prev1 holds minCost(top)
            return prev1;
        }
    }

    /**
     * Validates the cost array.
     *
     * @param cost cost[i] is the price of stepping on stair i
     * @throws IllegalArgumentException when cost is null or has fewer than 2 stairs
     */
    private static void validateInput(int[] cost) {
        // The problem guarantees at least 2 stairs. An empty array would also make
        // the tabulation approach write dp[1] into an array of size 1.
        if (cost == null || cost.length < 2) {
            throw new IllegalArgumentException(
                    "The cost array must contain at least 2 stairs.");
        }
    }

    /**
     * Driver code: runs all four approaches on the same cost arrays and prints the results,
     * so you can check that they agree.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {

        // Each test case is a cost array: cost[i] is the price of stepping on stair i.
        // Expected outputs: 15, 6, 0, 5, 4
        int[][] testCases = {
                {10, 15, 20},                          // start at stair 1, pay 15, jump 2 to the top
                {1, 100, 1, 1, 1, 100, 1, 1, 100, 1},  // step only on the 1s: 1 + 1 + 1 + 1 + 1 + 1
                {0, 0},                                // every stair is free
                {5, 10},                               // start at stair 0, pay 5, jump 2 to the top
                {1, 2, 3, 4}                           // stair 0 (1) → stair 2 (3) → top
        };

        for (int i = 0; i < testCases.length; i++) {
            int[] cost = testCases[i];

            System.out.println((i + 1) + ".\tcost = " + Arrays.toString(cost));

            System.out.println(
                    "\tNormal recursion:     "
                            + NormalRecursion.minCostClimbingStairs(cost));

            System.out.println(
                    "\tTop-down memoization: "
                            + TopDownDPMemoization.minCostClimbingStairs(cost));

            System.out.println(
                    "\tBottom-up tabulation: "
                            + BottomUpDPTabulation.minCostClimbingStairs(cost));

            System.out.println(
                    "\tSpace-optimized:      "
                            + SpaceOptimizedDP.minCostClimbingStairs(cost));

            // Print a separator line of 100 dashes
            System.out.println("-".repeat(100));
        }
    }
}