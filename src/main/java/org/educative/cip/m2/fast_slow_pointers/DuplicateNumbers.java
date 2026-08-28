package org.educative.cip.m2.fast_slow_pointers;

import java.util.Arrays;
/*
 * Time Complexity: O(n) - We traverse the array to find the duplicate number.
 * Space Complexity: O(1) - We use a constant amount of space for pointers.
 */
public class DuplicateNumbers {
    public static int findDuplicate(int[] nums) {
        int fast = nums[0];
        int slow = nums[0];

        while (true) {
            slow = nums[slow];
            fast = nums[nums[fast]];

            if (slow == fast) {
                break;
            }
        }

        slow = nums[0];

        while (slow != fast) {
            slow = nums[slow];
            fast = nums[fast];
        }

        return fast;
    }
    // Driver code
    public static void main(String[] args) {
        int[][] nums = {
                {1, 3, 2, 3, 5, 4},
                {2, 4, 5, 4, 1, 3},
                {1, 6, 3, 5, 1, 2, 7, 4},
                {1, 2, 2, 4, 3},
                {3, 1, 3, 5, 6, 4, 2}
        };
        for (int i = 0; i < nums.length; i++) {
            System.out.print(i + 1);
            System.out.println(".\tnums = "+ Arrays.toString(nums[i]));
            System.out.println("\tDuplicate number = "+ findDuplicate(nums[i]));
            System.out.println(new String(new char[100]).replace('\0', '-'));
        }
    }
}