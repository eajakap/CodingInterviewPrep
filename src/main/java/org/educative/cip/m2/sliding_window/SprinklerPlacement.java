package org.educative.cip.m2.sliding_window;

import java.util.*;

/**
 * There is a garden laid out along a single straight path. Each flowerbed in the garden sits at its own distinct
 * integer coordinate on this path; you're given these coordinates, sorted in increasing order, as an array of
 * integers flowerbeds.
 * You have exactly one sprinkler to install, and it can go at any integer coordinate along the path, even directly
 * on top of an existing flowerbed. Once installed at coordinate c, the sprinkler reaches every flowerbed within
 * radius units on either side of it, i.e. any flowerbed whose coordinate falls within the range
 * [c - radius, c + radius] (inclusive) gets watered.
 * Determine the coordinate to install the sprinkler at so that it waters the maximal number of flowerbeds.
 * If multiple coordinates would water the same maximal number of flowerbeds, return the smallest such coordinate.
 *
 * Example
 * For flowerbeds = [-6, -1, 0, 8] and radius = 4, the output should be solution(flowerbeds, radius) = -4.
 * Watering all four flowerbeds is impossible - the leftmost and rightmost sit 14 units apart, farther than twice
 * the radius. The three flowerbeds at -6, -1, and 0 can be watered together, though: the sprinkler may be installed
 * anywhere from -4 through -2, and the smallest of those coordinates is -4.
 *
 * For flowerbeds = [-3, 2, 3, 4, 9] and radius = 2, the output should be solution(flowerbeds, radius) = 2.
 * Watering all five flowerbeds isn't possible either, since the leftmost and rightmost are 12 units apart.
 * The best achievable is watering three flowerbeds - 2, 3, and 4 - by installing the sprinkler anywhere from
 * 2 through 4. The smallest of those coordinates is 2.
 */
public class SprinklerPlacement {

    static class Solution {

        /**
         * Finds the smallest coordinate at which a single sprinkler waters the most flowerbeds.
         * Key observation: whichever coordinate we pick, the flowerbeds it waters form a contiguous run of the
         * sorted array, and a run [left, right] is waterable by one sprinkler exactly when
         * flowerbeds[right] - flowerbeds[left] <= 2 * radius. So the answer is the longest such run.
         * Steps:
         * 1. Slide a window over the sorted coordinates with 'right' as the window's end.
         * 2. Shrink from the left while the window is too wide to be covered by one sprinkler,
         *    i.e. while flowerbeds[right] - flowerbeds[left] > 2 * radius.
         * 3. The window is now waterable. Its smallest valid sprinkler coordinate is flowerbeds[right] - radius:
         *    the sprinkler must reach the rightmost flowerbed, and that coordinate still reaches the leftmost
         *    one because the window width is at most 2 * radius.
         * 4. Record the window only when it is strictly longer than the best seen so far. Windows are scanned
         *    left to right and their candidate coordinates increase with 'right', so the first window of maximal
         *    length yields the smallest coordinate - later ties must not overwrite it.
         * Time Complexity: O(N), where N is the number of flowerbeds; each index enters and leaves the window once.
         * Space Complexity: O(1).
         * @param flowerbeds: distinct flowerbed coordinates, sorted in increasing order.
         * @param radius: the reach of the sprinkler on either side.
         * @return: the smallest coordinate watering the maximal number of flowerbeds.
         */
        public static int solution(int[] flowerbeds, int radius) {
            // The widest span a single sprinkler can cover.
            int span = 2 * radius;

            int left = 0;
            int best = 0;
            int bestCoordinate = 0;

            // 1. Slide a window over the sorted coordinates.
            for (int right = 0; right < flowerbeds.length; right++) {
                // 2. Shrink from the left while one sprinkler cannot span the whole window.
                while (flowerbeds[right] - flowerbeds[left] > span) {
                    left++;
                }

                // 4. Keep the first window of each new maximal length, so ties keep the smaller coordinate.
                int watered = right - left + 1;
                if (watered > best) {
                    best = watered;
                    // 3. Smallest coordinate that still reaches flowerbeds[right].
                    bestCoordinate = flowerbeds[right] - radius;
                }
            }

            return bestCoordinate;
        }

        // Driver code
        public static void main(String[] args) {
            int[][] inputs = {
                    {-6, -1, 0, 8},
                    {-3, 2, 3, 4, 9},
                    {5},
                    {0, 10},
                    {0, 10},
                    {1, 2, 3, 4, 5},
                    {-10, -5, 0, 5, 10},
                    {0, 1, 100, 101, 102}
            };
            int[] radii = {4, 2, 3, 5, 4, 10, 0, 1};

            for (int i = 0; i < inputs.length; i++) {
                System.out.println((i + 1) + ".\tFlowerbeds: " + Arrays.toString(inputs[i]));
                System.out.println("\tRadius: " + radii[i]);
                System.out.println("\tSprinkler coordinate: " + solution(inputs[i], radii[i]));
                System.out.println(new String(new char[100]).replace('\0', '-'));
            }
        }
    }
}
