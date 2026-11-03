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
 * This class solves the Fibonacci problem four ways, one per nested class, to show how
 * each step improves on the one before:
 *
 * Approach                          Time      Space
 * 1. NormalRecursion                O(2^n)    O(n)   recursion stack
 * 2. TopDownDPMemoization           O(n)      O(n)   memo array + recursion stack
 * 3. BottomUpDPTabulation           O(n)      O(n)   dp table
 * 4. SpaceOptimizedDP               O(n)      O(1)   two variables
 *
 * Fibonacci problem:
 * F(0) = 0, F(1) = 1, and F(n) = F(n - 1) + F(n - 2) for n >= 2.
 * Example: F(10) = 55. The sequence starts 0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, ...
 *
 * Note: the results are int values, so they are only correct up to n = 46.
 * F(47) is larger than Integer.MAX_VALUE and overflows.
 */
public class FibonacciDPSolution {

    /**
     * Approach 1: plain recursion, with no saved results.
     * Directly follows the definition F(n) = F(n - 1) + F(n - 2). It is simple, but it
     * solves the same subproblems over and over: fib(5) calls fib(3) twice, fib(2) three times,
     * and so on, so the number of calls grows exponentially.
     */
    static class NormalRecursion {

        /**
         * Returns the nth Fibonacci number using plain recursion.
         *
         * Time complexity: O(2^n) — each call makes two more calls.
         * Space complexity: O(n) for the recursion stack, which is at most n calls deep.
         *
         * @param n the index of the Fibonacci number to return; must be non-negative
         * @return the nth Fibonacci number
         */
        public static int fib(int n) {

            // Base cases: F(0) = 0 and F(1) = 1
            if (n <= 1) {
                return n;
            }

            // Recurrence: solve both smaller subproblems again from scratch every time
            return fib(n - 1) + fib(n - 2);
        }
    }

    /**
     * Approach 2: top-down dynamic programming (recursion + memoization).
     * Same recursion as approach 1, but each answer is saved in a memo array the first time
     * it is computed. Later calls for the same n return the saved answer instead of recursing,
     * so each subproblem is solved only once.
     */
    static class TopDownDPMemoization {

        /**
         * Returns the nth Fibonacci number using recursion with memoization.
         *
         * Time complexity: O(n) — each value from 2 to n is computed once.
         * Space complexity: O(n) for the memo array and the recursion stack.
         *
         * @param n the index of the Fibonacci number to return; must be non-negative
         * @return the nth Fibonacci number
         */
        public static int fib(int n) {

            // memo[i] holds F(i) once it has been computed; -1 means "not computed yet".
            // -1 is safe as a marker because no Fibonacci number is negative.
            int[] memo = new int[n + 1];
            Arrays.fill(memo, -1);

            return fib(n, memo);
        }

        /**
         * Recursive helper for {@link #fib(int)} that reads and fills the memo array.
         *
         * @param n    the index of the Fibonacci number to return
         * @param memo saved answers, where memo[i] is F(i) or -1 if not computed yet
         * @return the nth Fibonacci number
         */
        public static int fib(int n, int[] memo) {
            // Base cases: F(0) = 0 and F(1) = 1
            if (n <= 1) {
                return n;
            }
            // Already computed: reuse the saved answer instead of recursing again
            if (memo[n] != -1) {
                return memo[n];
            }
            // First time seeing n: compute it once and save it for later calls
            memo[n] = fib(n - 1, memo) + fib(n - 2, memo);
            return memo[n];
        }
    }

    /**
     * Approach 3: bottom-up dynamic programming (iteration + tabulation).
     * Instead of starting at n and recursing down, start at the base cases and fill a table
     * upward until reaching n. There is no recursion, so there is no risk of a stack overflow
     * for large n.
     */
    static class BottomUpDPTabulation {

        /**
         * Returns the nth Fibonacci number by filling a table from the base cases up.
         *
         * Time complexity: O(n) — one loop from 2 to n.
         * Space complexity: O(n) for the dp table.
         *
         * @param n the index of the Fibonacci number to return; must be non-negative
         * @return the nth Fibonacci number
         */
        public static int fib(int n) {
            // Base cases: F(0) = 0 and F(1) = 1
            if (n <= 1) {
                return n;
            }
            // dp[i] holds F(i)
            int[] dp = new int[n + 1];
            dp[0] = 0;
            dp[1] = 1;
            // Build each answer from the two answers just below it, which are already in the table
            for (int i = 2; i <= n; i++) {
                dp[i] = dp[i - 1] + dp[i - 2];
            }
            return dp[n];
        }
    }

    /**
     * Approach 4: space-optimized bottom-up dynamic programming.
     * Each step of the table in approach 3 only reads the two previous values, so the rest
     * of the table is never needed again. Keeping just those two values cuts the space to O(1).
     */
    static class SpaceOptimizedDP {

        /**
         * Returns the nth Fibonacci number, keeping only the last two values.
         *
         * Time complexity: O(n) — one loop from 2 to n.
         * Space complexity: O(1) — only two previous values are stored.
         *
         * @param n the index of the Fibonacci number to return; must be non-negative
         * @return the nth Fibonacci number
         */
        public static int fib(int n) {
            // Base cases: F(0) = 0 and F(1) = 1
            if (n <= 1) {
                return n;
            }
            // prev2 = F(i - 2) and prev1 = F(i - 1); they start as F(0) and F(1)
            int prev2 = 0;
            int prev1 = 1;
            for (int i = 2; i <= n; i++) {
                // F(i) = F(i - 1) + F(i - 2)
                int curr = prev1 + prev2;
                // Shift the window forward by one for the next iteration
                prev2 = prev1;
                prev1 = curr;
            }
            // After the loop, prev1 holds F(n)
            return prev1;
        }
    }

    static class Memoize {
        public static int fib(int n, int lookupTable[])
        {
            if (lookupTable[n] == -1) { // Check if already present
                // Adding entry to table when not present
                if (n <= 1)
                    lookupTable[n] = n;
                else
                    lookupTable[n] = fib(n - 1, lookupTable) + fib(n - 2, lookupTable);
            }
            return lookupTable[n];
        }

        public static void main(String args[])
        {
            int n = 6; // Finding the nth Fibonacci number
            int [] lookupTable = new int[n+1];
            Arrays.fill(lookupTable, -1); // Initializing the look up table to have -1
            System.out.println(fib(n, lookupTable));
        }
    }

    static class Tabulation {
        public static int fib(int n, int[] lookupTable)
        {
            if (n <= 1)
                return n;

            lookupTable[0] = 0; // Set zeroth and first values
            lookupTable[1] = 1;

            for (int i = 2; i <= n; i++) {
                lookupTable[i] = lookupTable[i-1] + lookupTable[i-2]; // Fill up the table by summing up previous two values
            }
            return lookupTable[n]; // Return the nth Fibonacci number
        }

        public static void main(String args[]) {
            int n = 6;
            int[] lookupTable = new int[n+1];
            System.out.print(fib(n, lookupTable));
        }
    }
    /**
     * Driver code: runs all four Fibonacci approaches on the same inputs and prints the results,
     * so you can check that they agree.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        // Expected outputs: 0, 1, 1, 5, 8, 55, 6765
        int[] testCases = {0, 1, 2, 5, 6, 10, 20};

        for (int i = 0; i < testCases.length; i++) {
            int n = testCases[i];
            System.out.println((i + 1) + ".\tn = " + n);
            System.out.println("\tNormal recursion:     " + NormalRecursion.fib(n));
            System.out.println("\tTop-down memoization: " + TopDownDPMemoization.fib(n));
            System.out.println("\tBottom-up tabulation: " + BottomUpDPTabulation.fib(n));
            System.out.println("\tSpace-optimized:      " + SpaceOptimizedDP.fib(n));
            int [] lookupTable = new int[n+1];
            Arrays.fill(lookupTable, -1); // Initializing the look up table to have -1
            System.out.println("\tMemoization:         " + Memoize.fib(n, lookupTable));
            Arrays.fill(lookupTable, -1); // Initializing the look up table to have -1
            System.out.println("\tTabulation:         " + Tabulation.fib(n, lookupTable));
            // Print a separator line of 100 dashes
            System.out.println("-".repeat(100));
        }
    }
}
