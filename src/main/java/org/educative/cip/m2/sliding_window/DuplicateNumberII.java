package org.educative.cip.m2.sliding_window;

import java.util.*;

/*
 * Time Complexity: O(n) - We traverse the array once to check for duplicates.
 * Space Complexity: O(n) - We use a HashMap to store the last index of each number.
 */
public class DuplicateNumberII {

    public static boolean containsNearbyDuplicate(int[] nums, int k) {
        // Create a HashMap to store the last index of each number
        Map<Integer, Integer> numToIndex = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];

            // If the number is already in the map and the difference between indices is <= k
            if (numToIndex.containsKey(num) && i - numToIndex.get(num) <= k) {
                return true;
            }

            // Update the last index of the current number
            numToIndex.put(num, i);
        }

        return false;
    }

    /*
     * Time Complexity: O(n) - We traverse the array once to check for duplicates.
     * Space Complexity: O(n) - We use a HashMap to store the last index of each number.
     */
    public static boolean containsNearbyDuplicateSlidingWindow(int[] nums, int k) {
        // Create a HashSet to store the numbers in the current window
        Set<Integer> seen = new HashSet<>();

        int left = 0;
        for (int right = 0; right < nums.length; right++) {
            int num = nums[right];

            // If the number is already in the set, we found a duplicate within the window
            if (seen.contains(num)) {
                return true;
            }

            // Add the current number to the set
            seen.add(num);

            // Remove the number that is now outside the window
            if (seen.size() > k) {
                seen.remove(nums[left]); // Remove the leftmost number from the set
                left++;
            }
      }

        return false;
    }

    public static void main(String[] args) {
        int[] nums1 = {1, 2, 3, 1};
        int k1 = 3;
        System.out.println(containsNearbyDuplicate(nums1, k1)); // Output: true

        int[] nums2 = {1, 0, 1, 1};
        int k2 = 1;
        System.out.println(containsNearbyDuplicate(nums2, k2)); // Output: true

        int[] nums3 = {1, 2, 3, 4, 5};
        int k3 = 2;
        System.out.println(containsNearbyDuplicate(nums3, k3));

        int[][] arrs = {
                {7, 8, 6, 7, 9},
                {-1, 2, -3, 4, -5},
                {900},
                {9, -6, 3, 0, -3, 6, 9},
                {-1000, 1000}
        };

        int[] ks = {3, 5, 1, 6, 10000};

        for (int i = 0; i < arrs.length; i++) {
            System.out.print((i + 1) + ".\tarr: [");
            for (int j = 0; j < arrs[i].length; j++) {
                System.out.print(arrs[i][j]);
                if (j < arrs[i].length - 1)
                    System.out.print(", ");
            }
            System.out.println("]");
            System.out.println("\tk: " + ks[i]);
            System.out.println("\n\tDo duplicates exist? " + (containsNearbyDuplicateSlidingWindow(arrs[i], ks[i]) ? "Yes" : "No"));
            System.out.println(String.join("", Collections.nCopies(100, "-")));
        }
    }
}
