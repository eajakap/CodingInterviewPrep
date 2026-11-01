package org.educative.cip.m2.greedy.jump;

import java.util.Arrays;

/**
 * Greedy Pattern: Jump / Reachability (minimum jumps)
 * Also known as Jump Game II.
 *
 * Problem:
 * A frog starts at index 0 of an array nums. From index i, the frog can jump forward
 * anywhere from 1 to nums[i] positions. Return the minimum number of jumps the frog needs
 * to reach the last index, or -1 if the last index cannot be reached.
 *
 * Example 1:
 * Input: nums = [2,3,1,1,4]
 * Output: 2
 * Explanation: jump from index 0 to index 1, then from index 1 to index 4.
 *
 * Example 2:
 * Input: nums = [3,2,1,0,4]
 * Output: -1
 * Explanation: every path lands on index 3, whose jump length is 0, so index 4 is never reached.
 *
 * Constraints:
 * 1 <= nums.length <= 10^4
 * 0 <= nums[i] <= 1000
 *
 * Central Idea:
 * Think of the jumps as levels. Every index reachable with 1 jump forms level 1, every index
 * reachable with 2 jumps forms level 2, and so on. Scan the array once:
 * - currentEnd is the last index reachable with the jumps counted so far.
 * - farthest is the farthest index reachable with one more jump.
 * When the scan reaches currentEnd, the current level is finished: one more jump is needed,
 * and the next level ends at farthest.
 *
 * Approach:
 * Scan left → right
 *       ↓
 * farthest = max(farthest, i + nums[i])
 *       ↓
 * Reached currentEnd?
 *    /          \
 *  YES           NO
 *   ↓             ↓
 * jumps++       Keep scanning
 * currentEnd = farthest
 *
 * Why it is greedy:
 * The frog never has to decide which index to land on during the scan. It only needs
 * to know how far the next jump could possibly reach, and taking the farthest reach for
 * each level can never need more jumps than any other choice.
 *
 * Time complexity: O(n), where n is the length of nums, since the array is scanned once.
 * Space complexity: O(1), as only a few variables are used.
 */
public class FrogJumpSolution {

    /**
     * Returns the minimum number of jumps needed to reach the last index of {@code nums}.
     *
     * Steps:
     * 1. Scan indexes 0 to n - 2, updating the farthest index reachable with one more jump.
     * 2. When the scan reaches the end of the current level (currentEnd), count a jump
     *    and move currentEnd to farthest.
     * 3. If farthest does not move past the current index at that point, the frog is stuck,
     *    so return -1.
     *
     * @param nums the maximum jump length from each index; every value is non-negative
     * @return the minimum number of jumps to reach the last index, 0 if the array has
     *         one element, or -1 if the last index cannot be reached
     */
    public static int minJumps(int[] nums) {

        // jumps: number of jumps counted so far
        // currentEnd: last index reachable using that many jumps
        // farthest: farthest index reachable using one more jump
        int jumps = 0;
        int currentEnd = 0;
        int farthest = 0;

        // Stop before the last index: once the frog is there, no more jumps are needed
        for (int i = 0; i < nums.length - 1; i++) {

            // The farthest the next jump could reach, from any index in the current level
            farthest = Math.max(farthest, i + nums[i]);

            // Reached the end of the current level: the frog must jump again
            if (i == currentEnd) {

                // No index in this level reaches past i, so the frog is stuck
                if (farthest <= i) {
                    return -1;
                }

                jumps++;
                // The next level covers every index up to farthest
                currentEnd = farthest;

                // The last index is within reach of this jump: done
                if (currentEnd >= nums.length - 1) {
                    break;
                }
            }
        }

        // Minimum number of jumps to reach the last index
        return jumps;
    }

    /**
     * Driver code: runs {@link #minJumps(int[])} against sample inputs and prints the results.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        // Expected outputs: 2, 2, 3, 0, -1, 2, 1
        int[][] testCases = {
                {2, 3, 1, 1, 4},  // 0 → 1 → 4
                {2, 3, 0, 1, 4},  // 0 → 1 → 4
                {1, 1, 1, 1},     // one step at a time: 0 → 1 → 2 → 3
                {0},              // already at the last index
                {3, 2, 1, 0, 4},  // stuck at index 3
                {1, 2, 3},        // 0 → 1 → 2
                {10, 1, 1}        // one jump covers the whole array
        };

        for (int i = 0; i < testCases.length; i++) {
            System.out.println((i + 1) + ".\tnums = " + Arrays.toString(testCases[i]));
            System.out.println("\tMinimum jumps: " + minJumps(testCases[i]));
            // Print a separator line of 100 dashes
            System.out.println("-".repeat(100));
        }
    }
}
