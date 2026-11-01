package org.educative.cip.m2.top_k_elements;

import java.util.*;

/**
 * Given an array of integers, return the k most frequent elements in sorted order.
 * For example, given the array [1, 3, 5, 12,11, 12, 11, 12, 5] and k = 3, the output should be [5, 11, 12].
 * If there are fewer than k unique elements, return all unique elements in sorted order.
 * Time complexity: O(n log k), where n is the number of elements in the array and k is the number of top frequent elements to return.
 * Space complexity: O(n), where n is the number of elements in the array.
 */
class FrequentElements {
    /**
     * Returns the k most frequent elements in sorted order.
     * @param arr an array of integers
     * @param k the number of top frequent elements to return
     * @return a list of the k most frequent elements in sorted order
     */
    public static List<Integer> topKFrequent(int[] arr, int k) {
        // Count how many times each number appears
        Map<Integer, Integer> numFrequencyMap = new HashMap<>();
        for (int n : arr)
            numFrequencyMap.put(n, numFrequencyMap.getOrDefault(n, 0) + 1);

        // Min-heap ordered by frequency, so the least frequent entry sits on top
        PriorityQueue<Map.Entry<Integer, Integer>> topKElements = new PriorityQueue<>(
                (e1, e2) -> e1.getValue() - e2.getValue()
        );

        // Iterate over the frequency map and maintain a heap of size k
        for (Map.Entry<Integer, Integer> entry : numFrequencyMap.entrySet()) {
            topKElements.add(entry);
            // Keep the heap at size k by dropping the least frequent entry,
            // leaving the k most frequent numbers seen so far
            if (topKElements.size() > k) {
                topKElements.poll();
            }
        }

        // Drain the heap; numbers come out in ascending frequency, not by value
        List<Integer> topNumbers = new ArrayList<>(k);
        while (!topKElements.isEmpty()) {
            topNumbers.add(topKElements.poll().getKey());
        }
        // The result must be sorted by value
        Collections.sort(topNumbers);
        return topNumbers;
    }

    // Driver code
    public static void main(String[] args) {
        int[][] inputs = {
                {1, 3, 5, 12, 11, 12, 11, 12, 5},
                {1, 3, 5, 14, 18, 14, 5},
                {2, 3, 4, 5, 6, 7, 7},
                {9, 8, 7, 6, 6, 5, 4, 3, 2, 1},
                {2, 4, 3, 2, 3, 4, 5, 4, 4, 4},
                {1, 1, 1, 1, 1, 1},
                {2, 3}
        };
        int[] inputK = {3, 2, 1, 1, 3, 1, 2};
        for (int i = 0; i < inputK.length; i++) {
            List<Integer> result = topKFrequent(inputs[i], inputK[i]);
            System.out.print(i + 1);
            System.out.println(".\tInput: (" + Arrays.toString(inputs[i]) + ", " + inputK[i] + ")");
            System.out.println("\n\tTop " + inputK[i] + " frequent elements: " + result);
            System.out.println(new String(new char[100]).replace('\0', '-'));
        }
    }
}