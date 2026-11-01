package org.educative.cip.m2.greedy.heap;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * Greedy Pattern: Heap / PriorityQueue + Greedy
 * Basic Idea: At every step, repeatedly choose the best available option. A heap lets us retrieve that option efficiently.
 *
 * Why do we need a Heap?
 * Sort once
 *    ↓
 * Scan left → right
 *    ↓
 * Make greedy decisions
 *
 * Why do we need a Priority Queue?
 *  Available choices
 *       ↓
 *  PriorityQueue
 *       ↓
 *  Best choice
 *       ↓
 *  Remove/use it
 *       ↓
 *  Add newly available choices
 *       ↓
 *  Repeat
 *
 * Problem:
 * You are given an array sticks, where sticks[i] is the length of the ith stick.
 * You can connect any two sticks of lengths x and y into one stick at a cost of x + y.
 * Connect all the sticks until only one stick remains, and return the minimum total cost.
 *
 * Example 1:
 * Input: sticks = [2,4,3]
 * Output: 14
 * Explanation: connect 2 + 3 = 5 (cost 5), then 5 + 4 = 9 (cost 9). Total = 5 + 9 = 14
 *
 * Example 2:
 * Input: sticks = [1,8,3,5]
 * Output: 30
 * Explanation: 1 + 3 = 4 (cost 4), 4 + 5 = 9 (cost 9), 9 + 8 = 17 (cost 17). Total = 4 + 9 + 17 = 30
 *
 * Example 3:
 * Input: sticks = [5]
 * Output: 0
 * Explanation: there is only one stick, so nothing needs to be connected.
 *
 * Constraints:
 * 1 <= sticks.length <= 10^4
 * 1 <= sticks[i] <= 10^4
 *
 * Why always connect the two shortest sticks:
 * A stick's length is paid again every time the stick it belongs to is connected later.
 * Sticks joined early are paid for the most times, so the shortest sticks should be joined first
 * and the longest ones last. This is the same idea as building a Huffman code.
 *
 * Time complexity: O(n log n), where n is the number of sticks.
 * Building the heap takes n insertions, and each of the n - 1 connections does two polls and one offer,
 * each O(log n).
 * Space complexity: O(n) for the heap.
 */
public class ConnectSticksSolution {

    /**
     * Returns the minimum total cost to connect all the sticks into one.
     *
     * Steps:
     * 1. Put every stick length into a min-heap.
     * 2. While more than one stick remains, take the two shortest, connect them,
     *    add the cost to the total, and put the new stick back in the heap.
     * 3. When only one stick is left, return the total cost.
     *
     * @param sticks the length of each stick
     * @return the minimum total cost to connect all the sticks; 0 if there is only one stick
     */
    public static int connectSticks(int[] sticks) {

        // Min-heap: the shortest stick is always on top
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        // Add every stick to the heap
        for (int stick : sticks) {
            minHeap.offer(stick);
        }

        // Sum of the cost of every connection made so far
        int totalCost = 0;

        // Keep connecting until only one stick remains
        while (minHeap.size() > 1) {

            // Take the two shortest sticks currently available
            int first = minHeap.poll();
            int second = minHeap.poll();

            // Connecting them costs the sum of their lengths,
            // and the new stick has that same length
            int cost = first + second;

            totalCost += cost;

            // Put the new stick back so it can be connected again later
            minHeap.offer(cost);
        }

        // Only one stick is left, so every connection has been paid for
        return totalCost;
    }

    /**
     * Driver code: runs {@link #connectSticks(int[])} against sample inputs and prints the results.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        // Expected outputs: 14, 30, 0, 8, 57
        int[][] testCases = {
                {2, 4, 3},
                {1, 8, 3, 5},
                {5},
                {1, 1, 1, 1},
                {3, 4, 5, 6, 7}
        };

        for (int i = 0; i < testCases.length; i++) {
            System.out.println((i + 1) + ".\tsticks = " + Arrays.toString(testCases[i]));
            System.out.println("\tMinimum cost to connect sticks: " + connectSticks(testCases[i]));
            // Print a separator line of 100 dashes
            System.out.println("-".repeat(100));
        }
    }
}

/**
 * Priority Queue (Heap) + Greedy Pattern
 *  1.  Get smallest
 *  2.  Get second-smallest
 *  3.  Combine them
 *  4.  Put result back
 * Min-Heap:  O(n log n)
 * - n insertions: O(n log n)
 * - n extractions: O(n log n)
 * 1. Smallest value first :
 *          PriorityQueue<Integer> minHeap = new PriorityQueue<>();
 * Max-Heap:  O(n log n)
 * - n insertions: O(n log n)
 * - n extractions: O(n log n)
 * 1. Largest value first :
 *         PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
 *
 * Mental Template:
 * 1. Determine what "best" means.
 *       smallest?
 *       largest?
 *       earliest?
 *       highest profit?
 *
 * 2. Put available candidates into a heap.
 * 3. Get the best candidate:
 *       heap.poll()
 * 4. Make the greedy decision.
 * 5. Add newly available candidates/results:
 *       heap.offer(...)
 * 6. Repeat.
 *
 * Recognizing the Pattern
 * Repeatedly choose...
 * 1. smallest available
 * 2. largest available
 * 3. highest priority
 * 4. lowest cost
 * 5. earliest finishing
 * 6. most profitable available
 */