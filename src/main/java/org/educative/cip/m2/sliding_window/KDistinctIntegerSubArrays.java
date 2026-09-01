package org.educative.cip.m2.sliding_window;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Time Complexity: O(n), where n is the length of the array.
 * We traverse through the array once.
 * Space Complexity: O(n), as we are using a frequency array to keep track of distinct integers.
 * Given an integer array arr and an integer k, return the number of subarrays with at most k distinct integers.
 */
public class KDistinctIntegerSubArrays {

    public static int subarraysWithKDistinctV2(int[] arr, int k) {
        // Number of subarrays with exactly k distinct integers=atMostKDistinct(k)−atMostKDistinct(k−1)
        return atMostKDistinct(arr, k) - atMostKDistinct(arr, k - 1);
    }

    private static int atMostKDistinct(int[] nums, int k) {
        Map<Integer, Integer> freq = new HashMap<>();
        int left = 0;
        int count = 0;

        for (int right = 0; right < nums.length; right++) {
            // count the frequency of the current number
            freq.put(nums[right], freq.getOrDefault(nums[right], 0) + 1);
            // k - allowed distinct integers,
            // if we have more than k distinct integers, we need to shrink the window from the left
            while (freq.size() > k) {
                freq.put(nums[left], freq.get(nums[left]) - 1);
                if (freq.get(nums[left]) == 0) {
                    freq.remove(nums[left]);
                }
                left++;
            }

            count += right - left + 1;
        }

        return count;
    }

    public static int subarraysWithKDistinct(int[] nums, int k) {
        // Number of subarrays with exactly k distinct integers=atMostK(k)−atMostK(k−1)
        return atMostK(nums, k) - atMostK(nums, k - 1);
    }

    private static int atMostK(int[] nums, int k) {
        int count = 0;
        int left = 0;
        Map<Integer, Integer> freq = new HashMap<>();

        for (int right = 0; right < nums.length; right++) {
            freq.put(nums[right], freq.getOrDefault(nums[right], 0) + 1);
            if (freq.get(nums[right]) == 1) {
                k--;
            }

            while (k < 0) {
                freq.put(nums[left], freq.get(nums[left]) - 1);
                if (freq.get(nums[left]) == 0) {
                    k++;
                }
                left++;
            }
            count += right - left + 1;
        }

        return count;
    }

    // Driver code
    public static void main(String[] args) {
        int[][] testCases = {
                {3, 3, 3},
                {1},
                {1, 2, 3, 4, 5},
                {1, 2, 1, 2, 3},
                {1, 2, 1, 3, 4}
        };

        int[] ks = {2, 1, 3, 2, 3};


        for (int i = 0; i < testCases.length; i++) {
            int result = subarraysWithKDistinct(testCases[i], ks[i]);
            System.out.println((i + 1) + ".\tnums: " + Arrays.toString(testCases[i]) + ", k: " + ks[i]);
            System.out.println("\tresult: " + result);
            System.out.println(new String(new char[100]).replace('\0', '-'));
            int resultV2 = subarraysWithKDistinctV2(testCases[i], ks[i]);
            System.out.println((i + 1) + ".\tnums: " + Arrays.toString(testCases[i]) + ", k: " + ks[i]);
            System.out.println("\tresultV2: " + resultV2);
            System.out.println(new String(new char[100]).replace('\0', '-'));

        }
    }
}
