package org.educative.cip.m2.two_heaps;

import java.util.*;

/**
 * Furthest Building You Can Reach
 * You are given an integer array heights representing the heights of buildings, some bricks, and some ladders.
 * You start your journey from building 0 and move to the next building by possibly using bricks or ladders.
 * While moving from building i to building i+1 (0-indexed),
 * - If the current building's height is greater than or equal to the next building's height, you do not need a ladder or bricks.
 * - If the current building's height is less than the next building's height, you can either use one ladder or (h[i+1] - h[i]) bricks.
 * Return the furthest building index (0-indexed) you can reach if you use the given ladders and bricks optimally.
 * Time complexity: O(n log k), where n is the number of buildings and k is the number of ladders.
 * Space complexity: O(k), where k is the number of ladders.
 */
public class FurthestBuildingSolution {
    /**
     * Returns the furthest building index that can be reached using the given number of bricks and ladders.
     * Steps:
     * 1. Initialize a min-heap (priority queue) to keep track of the climbs (height differences) currently covered by ladders.
     * 2. Iterate through the heights array, calculating the climb needed to reach the next building.
     * 3. If the climb is positive, add it to the min-heap.
     * 4. If the size of the min-heap exceeds the number of ladders, use bricks for the smallest climb.
     * 5. If at any point the number of bricks becomes negative, return the current building index.
     *
     * @param heights an array of integers representing the heights of buildings
     * @param bricks  the number of bricks available to use
     * @param ladders the number of ladders available to use
     * @return the index of the furthest building that can be reached
     */
    public int furthestBuilding(int[] heights, int bricks, int ladders) {
        // Holds the climbs currently assigned to ladders; the smallest one sits on top
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        for (int i = 0; i < heights.length - 1; i++) {
            int climb = heights[i + 1] - heights[i];
            // Going down or staying level is free
            if (climb <= 0) {
                continue;
            }
            // Tentatively cover this climb with a ladder
            minHeap.offer(climb);
            // More climbs than ladders: pay for the smallest one with bricks instead,
            // so ladders are saved for the largest climbs seen so far
            if (minHeap.size() > ladders) {
                bricks -= minHeap.poll();
            }
            // Not enough bricks to make this move, so building i is as far as we get
            if (bricks < 0) {
                return i;
            }
        }
        // Every climb was covered, so the last building is reachable
        return heights.length - 1;
    }

    public static void main(String[] args) {
        List<Object[]> testCases = new ArrayList<>();
        testCases.add(new Object[]{new int[]{1, 3, 2, 4, 7, 6}, 4, 1});
        testCases.add(new Object[]{new int[]{5, 5, 5, 6, 7, 20}, 10, 1});
        testCases.add(new Object[]{new int[]{10, 13, 15, 12, 18, 19, 17}, 6, 2});
        testCases.add(new Object[]{new int[]{2, 9, 10, 12, 3, 4, 100}, 15, 2});
        testCases.add(new Object[]{new int[]{8, 7, 6, 5, 4, 3, 2, 1}, 0, 0});

        FurthestBuildingSolution sol = new FurthestBuildingSolution();

        for (int idx = 0; idx < testCases.size(); idx++) {
            Object[] tc = testCases.get(idx);
            int[] heights = (int[]) tc[0];
            int bricks = (int) tc[1];
            int ladders = (int) tc[2];

            int result = sol.furthestBuilding(heights, bricks, ladders);

            System.out.println((idx + 1) + ".\tInput array: " + Arrays.toString(heights));
            System.out.println("\tTarget: " + bricks + ", " + ladders);
            System.out.println("\tResult: " + result);
            System.out.println("-".repeat(100));
        }
    }
}