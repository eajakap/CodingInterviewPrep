package org.educative.cip.m2.two_pointers;
import java.util.Arrays;

/*
 * Problem: Rotate Array
 * Given an array, rotate the array to the right by k steps, where k is non-negative.
 * Example 1:
 * Input: nums = [1,2,3,4,5,6], k = 2
 * Output: [5,6,1,2,3,4]
 * Example 2:
 * Input: nums = [-1,-100,3,99], k = 2
 * Output: [3,99,-1,-100]
 * Constraints:
 * 1 <= nums.length <= 10^5
 * -2^31 <= nums[i] <= 2^31 - 1
 * 0 <= k <= 10^5
 * Steps to solve the problem:
 * 1. Reverse the entire array.
 * 2. Reverse the first k elements.
 * 3. Reverse the remaining n-k elements.
 *
 * Time Complexity: O(n) - We are reversing the array elements a constant number of times.
 * Space Complexity: O(1) - We are modifying the input array in place and not using any additional data structures.
 */

public class RotateArray {

    /*
     * Function to rotate the array to the right by k steps using a brute-force approach.
     * Steps:
     * 1. For each step, store the last element of the array.
     * 2. Shift all elements to the right by one position.
     * 3. Place the stored last element at the first position.
     * @param nums The input array to be rotated.
     * @param k The number of steps to rotate the array to the right.
     * Time Complexity: O(n*k) - We are shifting n elements k times.
     * Space Complexity: O(1) - We are modifying the input array in place and not using any additional data structures.
     */
    public static void rotateRightBruteForce(int[] nums, int k) {
        for (int step = 0; step < k; step++) {
            int right = nums.length - 1;
            int temp = nums[right];
            // Shift all elements to the right by one position
            while (right > 0 ) {
                nums[right] = nums[right-1];
                right--;
            }
            nums[right]=temp;
        }
    }

    /*
     * Function to rotate the array to the right by k steps.
     * Steps:
     * 1. Reverse the entire array.
     * 2. Reverse the first k elements.
     * 3. Reverse the remaining n-k elements.
     * @param nums The input array to be rotated.
     * @param k The number of steps to rotate the array to the right.
     * Time Complexity: O(n) - We are reversing the array elements a constant number of times.
     * Space Complexity: O(1) - We are modifying the input array in place and not using any additional data structures.
     */
    public static void rotateRight(int[] nums, int k) {
        int n = nums.length;
        if (k == 0) return;
        // set cases where k is greater than n - ensures that we don't rotate more than necessary steps
        k = k % n;
        // reverse the entire array
        reverse(nums, 0, n - 1);
        // reverse the first k elements
        reverse(nums, 0, k - 1);
        // reverse the remaining n-k elements
        reverse(nums, k, n - 1);
    }

    /*
     * Function to rotate the array to the left by k steps.
     * Steps:
     * 1. Reverse the first k elements.
     * 2. Reverse the remaining n-k elements.
     * 3. Reverse the entire array.
     * @param nums The input array to be rotated.
     * @param k The number of steps to rotate the array to the left.
     * Time Complexity: O(n) - We are reversing the array elements a constant number of times.
     * Space Complexity: O(1) - We are modifying the input array in place and not using any additional data structures.
     */
    public static void rotateLeft(int[] nums, int k) {
        int n = nums.length;
        if (k == 0) return;
        // set cases where k is greater than n - ensures that we don't rotate more than necessary steps
        k = k % n; // normalize

        // Step 1: reverse first k elements
        reverse(nums, 0, k - 1);

        // Step 2: reverse remaining n-k elements
        reverse(nums, k, n - 1);

        // Step 3: reverse entire array
        reverse(nums, 0, n - 1);
    }
    private static void reverse(int[] nums, int left, int right) {
        while (left < right) {
            // swap the elements at left and right indices
            int temp = nums[left];
            nums[left] = nums[right];
            nums[right] = temp;
            left++;
            right--;
        }
    }

    // Driver code
    public static void main(String[] args) {

        Object[][] testCases = {
                new Object[] { new int[]{1, 2, 3, 4, 5}, 2 },
                new Object[] { new int[]{-35, -29, -7, 8, 6}, 3 },
                new Object[] { new int[]{1}, 5 },
                new Object[] { new int[]{10, 20, 30, 40, 50}, 7 },
                new Object[] { new int[]{0, 0, 0, 0}, 10 },
                new Object[] { new int[]{1, 2}, 1 },
                new Object[] { new int[]{2, 4, 6, 8, 10}, 0 }
        };

        for (int i = 0; i < testCases.length; i++) {
            int[] nums = (int[]) testCases[i][0];
            int k = (int) testCases[i][1];

            System.out.println((i + 1) + ".\tInput:");
            System.out.println("\tnums = " + Arrays.toString(nums));
            System.out.println("\tk = " + k);
            rotateRight(nums, k);  // perform right (clockwise) rotation
            System.out.println("\tRotate Right Output = " + Arrays.toString(nums));
            System.out.println("-".repeat(50));
            rotateLeft(nums, k);  // perform left (counter-clockwise) rotation
            System.out.println("\tRotate Left Output = " + Arrays.toString(nums));
            System.out.println("-".repeat(100));
        }
    }
}
