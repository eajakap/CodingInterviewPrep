package org.educative.cip.m2.sliding_window;

import java.util.*;

/**
 * This class contains methods to find the most frequent element in an array and to find the maximum frequency
 * of an element after performing at most k increment operations.
 * Time Complexity: O(n^2) for the brute force method and O(n) for the HashMap method.
 * Space Complexity: O(1) for the brute force method and O(n) for the HashMap method.
 * Time Complexity for maxFrequency method: O(n log n) due to sorting the array.
 * Space Complexity for maxFrequency method: O(log n) - As sort needs O(n log n) space. We also use a constant amount of extra space.
 */
public class MostFrequentElement {

    /*
        * Function to find the most frequent element in an array
        * Time Complexity: O(n^2) - We have a nested loop to count the frequency of each element.
        * Space Complexity: O(1) - We use a constant amount of space for counters.
        * This is a brute force approach and can be optimized using a HashMap to store the frequency of each element.
     */
    public static int mostFrequentElementBruteForce(int[] arr) {
        int maxCount = 0;
        int mostFrequent = arr[0];

        for (int i = 0; i < arr.length; i++) {
            int count = 1;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] == arr[i]) {
                    count++;
                }
            }
            if (count > maxCount) {
                maxCount = count;
                mostFrequent = arr[i];
            }
        }

        return mostFrequent;
    }

    /*
        * Function to find the most frequent element in an array using HashMap
        * Time Complexity: O(n) - We traverse the array once to count the frequency of each element.
        * Space Complexity: O(n) - We use a HashMap to store the frequency of each element.
     */
    public static int mostFrequentElementHashMap(int[] arr) {
        Map<Integer, Integer> freq = new HashMap<>();

        int maxCount = 0;
        int mostFrequent = arr[0];

        for (int num : arr) {
            int count = freq.getOrDefault(num, 0) + 1;
            freq.put(num, count);

            if (count > maxCount) {
                maxCount = count;
                mostFrequent = num;
            }
        }

        return mostFrequent;
    }

    // Function to find the maximum frequency of an element in the array after performing at most k increment operations
    public static int maxFrequency(int[] nums, int k) {
        Arrays.sort(nums); // sort the array to make it easier to find the maximum frequency
        int left = 0; // left pointer for the sliding window
        int windowSum = 0; // Sum of the elements in the current window
        int windowSize = 0; // Size of the current window
        int maxFrequency = 0; // Most frequent element in the current window
        long target = 0; // Target value to which we want to increment the elements in the current window
        for (int right = 0; right < nums.length; right++) {
            target = nums[right];
            windowSum += target;
            windowSize = right - left + 1;
            // Check if we can make all elements in the current window equal to nums[right] with at most k increments
            // Formula: increments needed = (windowSize * target) - windowSum
            // If increments needed > k, we need to shrink the window from the left where k = permissible increments
            while (windowSize * target - windowSum > k) {
                windowSum -= nums[left];
                left++;
                windowSize = right - left + 1;
            }
            maxFrequency = Math.max(maxFrequency, windowSize);
        }

        return maxFrequency;
    }

    // Driver code
    public static void main(String[] args) {
        int[][] testCases = {
                {1, 2, 4},
                {1, 4, 8, 13},
                {3, 6, 9},
                {2, 3, 5},
                {1, 1, 2},
                {4, 6, 8, 10},
                {10, 12, 5, 1, 15, 20, 13, 4, 7, 3, 9, 14, 2, 8, 6, 16, 11, 18, 19, 17},
                {5, 5, 5, 5, 6, 7, 8, 9, 10, 10, 10, 10, 10, 11, 12, 13, 14, 15, 16, 17}
        };
        int[] kValues = {5, 5, 2, 3, 2, 7, 50, 30};

        for (int i = 0; i < testCases.length; i++) {
            System.out.println((i + 1) + ".\tnums = " + Arrays.toString(testCases[i]));
            System.out.println("\tk = " + kValues[i]);
            System.out.println("\n\tOutput = " + maxFrequency(testCases[i], kValues[i]));
            System.out.println("-" + new String(new char[100]).replace('\0', '-') + "\n");
        }
    }
}
