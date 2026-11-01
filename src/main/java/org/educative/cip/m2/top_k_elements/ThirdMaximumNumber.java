package org.educative.cip.m2.top_k_elements;

import java.util.*;

/**
 * Third Maximum Number — solved with the Top K Elements pattern.
 * <p>
 * Given an integer array {@code nums}, return the third distinct maximum number in the array.
 * If the third distinct maximum does not exist, return the maximum number instead.
 */
public class ThirdMaximumNumber {

    /**
     * Finds the third distinct maximum value in the given array.
     * <p>
     * Approach: maintain a min-heap of at most 3 <em>distinct</em> values, which always holds
     * the 3 largest distinct numbers seen so far. The heap's root is the smallest of those,
     * i.e. the third maximum. A companion set tracks which values are currently in the heap
     * so duplicates can be skipped in O(1).
     * <p>
     * Time complexity: O(n) — each heap operation is O(log 3) = O(1).<br>
     * Space complexity: O(1) — the heap and set never hold more than 3 elements.
     *
     * @param nums the input array (assumed non-empty)
     * @return the third distinct maximum if it exists; otherwise the maximum value
     */
    public static int thirdMax(int[] nums) {
        // Min-heap holding the (up to) 3 largest distinct values; root = smallest of them
        PriorityQueue<Integer> heap = new PriorityQueue<>();

        // Mirrors the heap contents to detect duplicates in O(1)
        Set<Integer> taken = new HashSet<>();

        for (int num : nums) {
            // Skip values already in the heap — we only care about distinct numbers
            if (taken.contains(num)) {
                continue;
            }

            if (heap.size() == 3) {
                // Heap is full: replace the smallest only if the new number is larger
                if (heap.peek() < num) {
                    // Evict the smallest and keep the set in sync
                    taken.remove(heap.poll());
                    // Add the new number to the heap and set
                    heap.offer(num);
                    taken.add(num);
                }
            } else {
                // Heap not yet full: accept any new distinct value
                heap.offer(num);
                taken.add(num);
            }
        }

        if (heap.size() == 1) {
            // Only one distinct value exists — it is the maximum
            return heap.peek();
        } else if (heap.size() == 2) {
            // Only two distinct values — no third max, so return the larger one
            int firstNum = heap.poll();
            return Math.max(firstNum, heap.peek());
        }

        // Three distinct values — the heap root is the third maximum
        return heap.peek();
    }

    /**
     * Driver code: runs {@link #thirdMax(int[])} against sample inputs and prints the results.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        int[][] testCases = {
                {3, 2, 1},
                {1, 2},
                {2, 2, 3, 1},
                {5, 5, 4, 3, 2},
                {1, 1, 1, 1}
        };

        for (int i = 0; i < testCases.length; i++) {
            System.out.print((i + 1) + ".\tnums: " + Arrays.toString(testCases[i]));
            System.out.println("\n\tThe third maximum is: " + thirdMax(testCases[i]));
            // Print a separator line of 100 dashes
            System.out.println(new String(new char[100]).replace('\0', '-'));
        }
    }
}
