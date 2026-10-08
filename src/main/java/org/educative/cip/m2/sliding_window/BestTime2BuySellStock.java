package org.educative.cip.m2.sliding_window;

import java.util.Arrays;

public class BestTime2BuySellStock {
    public static int maxProfit(int[] prices) {
        int buyDay = 0;
        int sellDay = 1;
        int maxProfit = 0;

        while (sellDay < prices.length) {
            if (prices[buyDay] < prices[sellDay]) {
                int profit = prices[sellDay] - prices[buyDay];
                maxProfit = Math.max(maxProfit, profit);
            } else {
                buyDay = sellDay;
            }
            sellDay++;
        }
        return maxProfit;
    }

    public static void main(String[] args) {
        int[][] pricesList = {
                {1, 2, 4, 2, 5, 7, 2, 4, 9, 0, 9},
                {8, 2, 6, 4, 7, 5},
                {7, 6, 4, 3, 1},
                {2, 6, 8, 7, 8, 7, 9, 4, 1, 2, 4, 5, 8},
                {1, 2}
        };

        for (int i = 0; i < pricesList.length; i++) {
            System.out.println((i + 1) + ". Stock prices = " + Arrays.toString(pricesList[i]));
            System.out.println("   Maximum profit = " + maxProfit(pricesList[i]));
            System.out.println(new String(new char[100]).replace('\0', '-'));
        }
    }

}
