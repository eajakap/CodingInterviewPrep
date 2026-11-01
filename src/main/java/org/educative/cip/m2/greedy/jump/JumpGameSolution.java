package org.educative.cip.m2.greedy.jump;

/**
 * Greedy Pattern: Jump / Reachability
 * Central Idea: As you scan the array, keep track of the farthest position you can reach so far.
 * The greedy approach is to always jump to the farthest reachable index from the current position.
 * This ensures that we minimize the number of jumps needed to reach the end of the array.
 * Steps:
 * 1. Initialize a variable to keep track of the farthest reachable index.
 * 2. Iterate through the array, and for each index, update the farthest reachable index based on the current index and the jump length at that index.
 * 3. If the farthest reachable index is greater than or equal to the last index of the array, return true (indicating that the end is reachable).
 * 4. If the current index exceeds the farthest reachable index, return false (indicating that the end is not reachable).
 * Time Complexity: O(n), where n is the number of elements in the array, since we are iterating through the array once.
 * Space Complexity: O(1), as we are using a constant amount of extra space.
 */
public class JumpGameSolution {
    /**
     * Determines if it is possible to reach the last index of the array starting from the first index.
     *
     * @param nums an array of non-negative integers where each element represents the maximum jump length at that position
     * @return true if it is possible to reach the last index, false otherwise
     */
    public boolean canJump(int[] nums) {
        int farthest = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i > farthest) {
                return false;
            }
            farthest = Math.max(farthest, i + nums[i]);
        }
        return true;
    }

    public static void main(String[] args) {
        JumpGameSolution solution = new JumpGameSolution();
        int[][] testCases = {
                {2, 3, 1, 1, 4},
                {3, 2, 1, 0, 4},
                {0},
                {2, 0},
                {1, 2, 3},
                {1, 0, 1}
        };

        for (int[] testCase : testCases) {
            boolean result = solution.canJump(testCase);
            System.out.println("Input: " + java.util.Arrays.toString(testCase) + " => Can jump to end: " + result);
        }
    }
}

/**
 * Mental Cheat Sheet:
 *           JUMP PROBLEM
 *                |
 *                v
 *      What is being asked?
 *         /             \
 *        /               \
 *  Can reach end?      Min jumps?
 *       |                  |
 *   maxReach          currentEnd
 *                     + farthest
 *                     + jumps
 * Interview Recognition
 * Look for problems involving:
 * 1. jumping through an array
 * 2. reaching destination
 * 3. maximum movement from each position
 * 4. can you reach the end?
 * 5. minimum jumps
 * 6. maximum reachable position
 */
