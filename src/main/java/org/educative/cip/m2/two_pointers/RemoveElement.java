package org.educative.cip.m2.two_pointers;
/*
    * Problem: Remove Element
    * Given an integer array nums and an integer val, remove all occurrences of val in nums in-place. The relative order of the elements may be changed.
    * Since it is impossible to change the length of the array in some languages, you must instead have the result be placed in the first part of the array nums.
    * More formally, if there are k elements after removing the duplicates, then the first k elements of nums should hold the final result.
    * It does not matter what you leave beyond the first k elements.
    * Return k after placing the final result in the first k slots of nums.
    * Do not allocate extra space for another array. You must do this by modifying the input array in-place with O(1) extra memory.
    *
    * Example:
    * Input: nums = [3,2,2,3], val = 3
    * Output: 2, nums = [2,2]
    *
    * Input: nums = [0,1,2,2,3,0,4,2], val = 2
    * Output: 5, nums = [0,1,4,0,3]
    *
    * Constraints:
    * - 0 <= nums.length <= 100
    * - 0 <= nums[i] <= 50
    * - 0 <= val <= 100
    *
 * Time Complexity: O(n) - We traverse the array once.
 * Space Complexity: O(1) - We use a constant amount of extra space.
 */
public class RemoveElement {
    // Function to remove all occurrences of val in nums in-place
    // and return the new length of the array after removal
    public int removeElement(int[] nums, int val) {
        int k = 0;

        for (int j = 0; j < nums.length; j++) {
            if (nums[j] != val) {
                nums[k] = nums[j];
                k++;
            }
        }

        return k;
    }

    // Driver code
    public static void main(String[] args) {
        int[][] numsArr = {
                {5, 8, 8, 5, 3},
                {50, 49, 48, 47, 46, 45},
                {0, 0, 0, 0, 1, 0, 0, 0, 0},
                {10, 20, 30, 40, 50},
                {0, 50}
        };

