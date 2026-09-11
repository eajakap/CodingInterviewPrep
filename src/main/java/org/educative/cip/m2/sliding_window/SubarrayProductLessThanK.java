package org.educative.cip.m2.sliding_window;

import java.util.Arrays;

/**
 * Subarray Product Less Than K
 * Problem: Given an array of integers nums and an integer k,
 *          return the number of contiguous subarrays where the product of
 *          all the elements in the subarray is strictly less than k.
 * Example 1:
 * Input: nums = [10,5,2,6], k = 100
 * Output: 8
 * Explanation: The 8 subarrays that have a product less than 100 are:
 * [10], [5], [2], [6], [10,5], [5,2], [2,6], [5,2,6]
 * Note that [10,5,2] is not included as the product is 100.
 * Example 2:
 * Input: nums = [1,2,3], k = 0
 * Output: 0
 * Explanation: There are no subarrays with a product less than 0.
 * Constraints:
 * 1 <= nums.length <= 3 * 10^4
 * 1 <= nums[i] <= 1000
 * 0 <= k <= 10^6
 *
 */
public class SubarrayProductLessThanK {

    /**
     * Returns the number of contiguous subarrays where the product of all the elements in the subarray is strictly less than k.
     *
     * Steps to solve the problem:
     * 1. Initialize two pointers, left and right, to represent the current window of elements.
     * 2. Initialize a variable to keep track of the product of the current window and a variable to store the count of valid subarrays.
     * 3. Expand the window by moving the right pointer and multiplying the product by the new element.
     * 4. While the product of the current window is greater than or equal to k,
     *    shrink the window by moving the left pointer and dividing the product by the leftmost element.
     * 5. Update the count of valid subarrays by adding (right - left + 1) for each valid window.
     * 6. Repeat steps 3 to 5 until the right pointer reaches the end of the array.
     * 7. Return the count of valid subarrays after traversing the entire array.
     *
     * Time Complexity: O(n), where n is the length of nums. Each element is added and removed from the product at most once.
     * Space Complexity: O(1), as we are using constant space for pointers and counters.
     *
     * @param nums an array of integers
     * @param k    an integer threshold for product comparison
     * @return the number of contiguous subarrays with a product less than k
     */
    public int numSubarrayProductLessThanK(int[] nums, int k) {
        if (k <= 1) return 0; // If k is less than or equal to 1, no products can be less than k

        int product = 1; // Initialize the product of the current window
        int left = 0; // Left pointer for the sliding window
        int count = 0; // Count of valid subarrays

        for (int right = 0; right < nums.length; right++) {
            product *= nums[right]; // Expand the window by multiplying the rightmost element

            // Shrink the window from the left if the product is greater than or equal to k
            while (product >= k) {
                product /= nums[left]; // Divide by the leftmost element to shrink the window
                left++; // Move the left pointer to the right
            }

            // All subarrays ending at 'right' and starting from 'left' to 'right' are valid
            count += right - left + 1; // Add the number of valid subarrays to the count
        }

        return count; // Return the total count of valid subarrays
    }

    public static void main(String[] args) {
        SubarrayProductLessThanK sol = new SubarrayProductLessThanK();

        int[][][] numsArray = {
                {{2, 4, 3, 7}},
                {{1, 1, 1, 1, 1}},
                {{100, 200, 300}},
                {{5, 3, 8, 2, 6}},
                {{1000, 1000, 1000}}
        };
        int[] kValues = {50, 2, 1000000, 120, 1};

        for (int i = 0; i < numsArray.length; i++) {
            int[] nums = numsArray[i][0];
            int k = kValues[i];
            int result = sol.numSubarrayProductLessThanK(nums, k);

            System.out.println((i + 1) + ".\tInput array: " + Arrays.toString(nums));
            System.out.println("\tTarget: " + k);
            System.out.println("\tResult: " + result);
            System.out.println("-".repeat(100));
        }
    }

}
