package org.educative.cip.m2.greedy.minmax;

import java.util.Arrays;

/**
 * Greedy Pattern: Running Minimum / Maximum
 * Central Idea: While scanning the array, remember the best value seen so far, and use it to make the best decision for the current element.
 * Approach:
 * Scan left → right
 * Current element
 *       +
 * Best value seen earlier
 *       ↓
 * Can I improve my answer?
 * Problem: Best Time to Buy and Sell Stock
 * Given an array of integers representing the prices of a stock on different days,
 * find the maximum profit that can be achieved by buying on one day and selling on
 * another day after the buying day.
 * Example: Given the array [7, 1, 5, 3, 6, 4], the maximum profit is 5 (buy on day 2 at price 1 and sell on day 5 at price 6).
 * If no profit can be made, return 0.
 * Example 2: Given the array [7, 6, 4, 3, 1], the maximum profit is 0, because the price only falls.
 *
 * Constraints:
 * 1 <= prices.length <= 10^5
 * 0 <= prices[i] <= 10^4
 *
 * Why the running minimum works:
 * For any selling day, the best buying day is the cheapest day before it.
 * Keeping the lowest price seen so far gives that answer in O(1) for each day,
 * so every possible selling day is checked in one pass.
 *
 * Time complexity: O(n), where n is the number of days (length of the prices
 * array).
 * Space complexity: O(1)
 *
 */
public class BestTimeToBuyAndSellStockSolution {

    /**
     * Returns the maximum profit from buying the stock on one day and selling it on a later day.
     *
     * Steps:
     * 1. Treat the first day's price as the cheapest buying price so far.
     * 2. For each later day, work out the profit from selling today at the cheapest earlier price,
     *    and keep the largest profit seen.
     * 3. Then update the cheapest price, so today can be the buying day for a later sale.
     *
     * @param prices the stock price on each day; must contain at least one price
     * @return the maximum profit, or 0 if no profitable trade exists
     */
    public static int maxProfit(int[] prices) {

        // minPrice: cheapest price seen so far (the best day to have bought)
        // maxProfit: best profit found so far; starts at 0, meaning "don't trade"
        int minPrice = prices[0];
        int maxProfit = 0;

        // Start at day 1: selling on day 0 is impossible, because nothing was bought before it
        for (int i = 1; i < prices.length; i++) {

            // Profit if we sell today
            // (minPrice comes from earlier days only, so buying always happens before selling)
            int profit = prices[i] - minPrice;

            // Update best profit
            // (a negative profit never replaces 0, so a falling price returns 0)
            maxProfit = Math.max(maxProfit, profit);

            // Update cheapest buying price
            // This happens after the profit check, so today's price is only used for future sales
            minPrice = Math.min(minPrice, prices[i]);
        }

        // Best profit over every possible pair of buy day < sell day
        return maxProfit;
    }

    /**
     * Driver code: runs {@link #maxProfit(int[])} against sample inputs and prints the results.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        // Expected outputs: 5, 0, 1, 2, 0
        int[][] testCases = {
                {7, 1, 5, 3, 6, 4},  // buy at 1, sell at 6
                {7, 6, 4, 3, 1},     // price only falls: no profitable trade
                {1, 2},              // buy at 1, sell at 2
                {2, 4, 1},           // the later low of 1 has no higher price after it, so buy at 2, sell at 4
                {3}                  // a single day: no trade possible
        };

        for (int i = 0; i < testCases.length; i++) {
            System.out.println((i + 1) + ".\tprices = " + Arrays.toString(testCases[i]));
            System.out.println("\tMaximum profit: " + maxProfit(testCases[i]));
            // Print a separator line of 100 dashes
            System.out.println("-".repeat(100));
        }
    }
}

/**
 * Notes:
 * Mental Template
 * 1. Scan left → right.
 * 2. Maintain:
 *       bestSoFar
 * 3. At current element:
 *       use current + bestSoFar
 *       to calculate candidate answer
 * 4. Update:
 *       answer
 * 5. Update:
 *       bestSoFar
 * 6. Continue.
 * Recognition Pattern
 * Look for wording such as:
 * 1. maximum difference
 * 2. minimum difference
 * 3. best previous value
 * 4. buy before sell
 * 5. earlier/later
 * 6. i < j
 * 7. maximum gain
 * 8. minimum value before current position
 * 9. maximum value before current position
 */
