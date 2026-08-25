package org.educative.cip.m2.two_pointers;
/*
 * Time Complexity: O(n) - We traverse the array once.
 * Space Complexity: O(1) - We use a constant amount of extra space.
 */
public class RemoveElement {
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

        int[] valArr = {5, 48, 0, 25, 0};

        RemoveElement sol = new RemoveElement();
        for (int i = 0; i < numsArr.length; i++) {
            System.out.println((i + 1) + ".\tnums: " + java.util.Arrays.toString(numsArr[i]));
            System.out.println("\tval: " + valArr[i]);
            System.out.println("\tk: " + sol.removeElement(numsArr[i], valArr[i]));
            System.out.println("-".repeat(100));
        }
    }
}