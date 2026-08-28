package org.educative.cip.m2.two_pointers;
import java.util.Arrays;

/*
 * Time Complexity: O(n) - We are reversing the array elements a constant number of times.
 * Space Complexity: O(1) - We are modifying the input array in place and not using any additional data structures.
 */

public class RotateArray {

//    public static void rotate(int[] nums, int k) {
//        for (int step = 0; step < k; step++) {
//            int right = nums.length - 1;
//            int temp = nums[right];
//            while (right > 0 ) {
//                nums[right] = nums[right-1];
//                right--;
//            }
//            nums[right]=temp;
//        }
//    }

    // Optimal Solution: Reverse the entire array, then reverse the first k elements, and
    // finally reverse the remaining n-k elements.
    public static void rotate(int[] nums, int k) {
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

    private static void reverse(int[] nums, int left, int right) {
        while (left < right) {
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

            rotate(nums, k);  // perform rotation

            System.out.println("\n\tOutput = " + Arrays.toString(nums));
            System.out.println("-".repeat(100));
        }
    }
}
