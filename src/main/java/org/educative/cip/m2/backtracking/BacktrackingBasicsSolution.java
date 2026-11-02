package org.educative.cip.m2.backtracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Backtracking:
 * This class shows the backtracking pattern on three classic problems,
 * each in its own nested class:
 * 1. Subsets (Subsets.subsets): every subset of a set of distinct numbers.
 * 2. Permutations (Permutations.permute): every ordering of a set of distinct numbers.
 * 3. Combination Sum (CombinationSum.combinationSum): every combination of numbers that adds up to a target,
 *    where each number may be used any number of times.
 *
 * What is Backtracking?
 * Backtracking is a general algorithm for finding all (or some) solutions to computational problems,
 * incrementally building candidates to the solutions, and abandoning a candidate as soon as it is determined
 * that the candidate cannot possibly be completed to a valid solution.
 *  Approach:
 *  Start
 *   |
 * Choose path A
 *   |
 *   +---- dead end
 *   |
 * BACKTRACK
 *   |
 * Choose path B
 *   |
 *  Solution
 *
 * General Backtracking Pattern:
 * void backtrack(...) {
 *
 *     // 1. Base case
 *     if (solutionFound) {
 *         saveSolution();
 *         return;
 *     }
 *
 *     // 2. Try available choices
 *     for (choice : choices) {
 *
 *         // 3. Make a choice
 *         choose(choice);
 *
 *         // 4. Explore
 *         backtrack(...);
 *
 *         // 5. Undo the choice
 *         unchoose(choice);
 *     }
 * }
 *
 * Each nested class has its own private backtrack helper that follows this template.
 * The helpers share a name but live in different classes, so they never clash.
 */
public class BacktrackingBasicsSolution {

    /**
     * Subsets: generate every subset (the power set) of a set of distinct numbers.
     * Each element is either included or skipped, so n elements give 2^n subsets.
     */
    static class Subsets {
        /**
         * Returns every subset (the power set) of {@code nums}.
         *
         * Example: nums = [1, 2, 3]
         * Output: [[], [1], [1, 2], [1, 2, 3], [1, 3], [2], [2, 3], [3]]
         *
         * Time complexity: O(n * 2^n), where n is the length of nums. There are 2^n subsets,
         * and copying each one into the result takes up to O(n).
         * Space complexity: O(n) for the recursion stack and the current subset,
         * not counting the output.
         *
         * @param nums distinct integers
         * @return a list containing every subset of {@code nums}, including the empty subset
         */
        public static List<List<Integer>> subsets(int[] nums) {

            // Collects every subset found
            List<List<Integer>> result = new ArrayList<>();

            // Start with an empty subset, considering elements from index 0
            backtrack(nums, 0, new ArrayList<>(), result);

            return result;
        }

        /**
         * Backtracking helper for {@link #subsets(int[])}.
         *
         * Every partial subset is itself a valid subset, so it is saved on entry. The loop
         * then tries adding each element from {@code start} onward. Only moving forward from
         * {@code start} means each subset is built in one order only, so no duplicates appear.
         *
         * @param nums    the input numbers
         * @param start   the first index that may still be added to the current subset
         * @param current the subset being built
         * @param result  collects every subset found
         */
        private static void backtrack(
                int[] nums,
                int start,
                List<Integer> current,
                List<List<Integer>> result) {

            // Save current subset
            // (a copy, because current keeps changing as the search continues)
            result.add(new ArrayList<>(current));

            // No explicit base case: when start reaches nums.length, the loop simply does not run
            for (int i = start; i < nums.length; i++) {

                // CHOOSE
                current.add(nums[i]);

                // EXPLORE
                // i + 1: each element is used at most once, and only later elements may follow it
                backtrack(nums, i + 1, current, result);

                // UNDO
                // Remove the element just added so the next loop iteration starts from the same state
                current.remove(current.size() - 1);
            }
        }
    }

    /**
     * Permutations: generate every ordering of a set of distinct numbers.
     * Order matters, so n numbers give n! permutations.
     */
    static class Permutations {
        /**
         * Returns every permutation (ordering) of {@code nums}.
         *
         * Example: nums = [1, 2, 3]
         * Output: [[1, 2, 3], [1, 3, 2], [2, 1, 3], [2, 3, 1], [3, 1, 2], [3, 2, 1]]
         *
         * Time complexity: O(n^2 * n!), where n is the length of nums. There are n! permutations,
         * each built over n levels, and the current.contains check at each level costs O(n).
         * Space complexity: O(n) for the recursion stack and the current permutation,
         * not counting the output.
         *
         * @param nums distinct integers; duplicates are not supported, because the
         *             contains check would treat equal values as already used
         * @return a list containing every permutation of {@code nums}
         */
        public static List<List<Integer>> permute(int[] nums) {

            // Collects every permutation found
            List<List<Integer>> result = new ArrayList<>();

            // Start with an empty permutation
            backtrack(nums, new ArrayList<>(), result);

            return result;
        }

        /**
         * Backtracking helper for {@link #permute(int[])}.
         *
         * Each level picks the next position's value from the numbers not used yet.
         * Unlike subsets, every number is considered at every level (order matters),
         * and a permutation is saved only once it contains all the numbers.
         *
         * @param nums    the input numbers
         * @param current the permutation being built
         * @param result  collects every complete permutation
         */
        private static void backtrack(
                int[] nums,
                List<Integer> current,
                List<List<Integer>> result) {

            // Base case
            // Every number has been placed, so this permutation is complete
            if (current.size() == nums.length) {
                result.add(new ArrayList<>(current));
                return;
            }

            // Try each number in the next position
            for (int num : nums) {

                // Skip numbers already placed in this permutation
                if (current.contains(num)) {
                    continue;
                }

                // CHOOSE
                current.add(num);

                // EXPLORE
                backtrack(nums, current, result);

                // UNDO
                // Remove the number so the next one can be tried in this position
                current.remove(current.size() - 1);
            }
        }
    }

    /**
     * Combination Sum: find every combination of numbers that adds up to a target,
     * where each number may be reused any number of times.
     */
    static class CombinationSum {
        /**
         * Returns every combination of {@code nums} that adds up to {@code target}.
         * Each number may be used any number of times (Combination Sum).
         *
         * Example: nums = [2, 3, 6, 7], target = 7
         * Output: [[2, 2, 3], [7]]
         *
         * Time complexity: exponential. In the worst case the search tree has a depth of up to
         * target / min(nums) and up to n branches at each level, so roughly O(n^(target / min(nums))).
         * Space complexity: O(target / min(nums)) for the recursion stack and the current combination,
         * not counting the output.
         *
         * @param nums   distinct positive integers; a 0 or negative value would let the
         *               search recurse forever, because the remaining sum would never drop below 0
         * @param target the sum each combination must reach
         * @return a list of every combination that sums to {@code target}, each in non-decreasing index order
         */
        public static List<List<Integer>> combinationSum(int[] nums, int target) {

            // Collects every valid combination found
            List<List<Integer>> result = new ArrayList<>();

            // Start with an empty combination and the full target still to reach
            backtrack(nums, 0, target, new ArrayList<>(), result);

            return result;
        }

        /**
         * Backtracking helper for {@link #combinationSum(int[], int)}.
         *
         * Subtracts each chosen number from {@code remaining}. A combination is saved when
         * {@code remaining} reaches exactly 0, and abandoned (pruned) once it goes below 0.
         *
         * @param candidates the input numbers
         * @param start      the first index that may still be chosen; earlier indexes are skipped
         *                   so the same combination is not found in a different order
         * @param remaining  how much is still needed to reach the target
         * @param current    the combination being built
         * @param result     collects every valid combination
         */
        private static void backtrack(
                int[] candidates,
                int start,
                int remaining,
                List<Integer> current,
                List<List<Integer>> result) {

            // Exactly reached the target: save a copy of this combination
            if (remaining == 0) {
                result.add(new ArrayList<>(current));
                return;
            }

            // Overshot the target: this path can never become valid, so prune it
            if (remaining < 0) {
                return;
            }

            for (int i = start; i < candidates.length; i++) {

                // Choose
                current.add(candidates[i]);

                // Explore
                // Pass i (not i + 1) so the same number can be chosen again
                backtrack(
                        candidates,
                        i,
                        remaining - candidates[i],
                        current,
                        result);

                // Undo
                // Remove the number so the next candidate can be tried instead
                current.remove(current.size() - 1);
            }
        }
    }

    /**
     * Driver code: runs {@link Subsets#subsets(int[])}, {@link Permutations#permute(int[])}, and
     * {@link CombinationSum#combinationSum(int[], int)} on sample inputs and prints the results.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        // Methods now live in nested classes, so each call is qualified with its class name

        // 1. Subsets - expected: 8 subsets (2^3), including the empty subset []
        int[] nums = {1, 2, 3};
        List<List<Integer>> subsets = Subsets.subsets(nums);
        System.out.println("1.\tSubsets of " + Arrays.toString(nums) + ":");
        System.out.println("\t" + subsets + "  (count: " + subsets.size() + ")");
        // Print a separator line of 100 dashes
        System.out.println("-".repeat(100));

        // 2. Permutations - expected: 3! = 6 permutations
        List<List<Integer>> permutations = Permutations.permute(nums);
        System.out.println("2.\tPermutations of " + Arrays.toString(nums) + ":");
        System.out.println("\t" + permutations + "  (count: " + permutations.size() + ")");
        System.out.println("-".repeat(100));

        // 3. Combination Sum - expected: [[2, 2, 3], [7]]
        int[] candidates = {2, 3, 6, 7};
        int target = 7;
        List<List<Integer>> combinations = CombinationSum.combinationSum(candidates, target);
        System.out.println("3.\tCombinations of " + Arrays.toString(candidates) + " that sum to " + target + ":");
        System.out.println("\t" + combinations + "  (count: " + combinations.size() + ")");
        System.out.println("-".repeat(100));
    }
}

/**
 * Notes:
 * How to Recognize Backtracking Problems:
 * Look for phrases such as:
 * 1. Generate all combinations
 * 2. Generate all permutations
 * 3. Find all subsets
 * 4. Find all possible arrangements
 * 5. Find all paths
 * 6. Choose numbers that satisfy a target
 * 7. Place objects subject to constraints
 * 8. Solve a puzzle
 *
 *                  BACKTRACKING
 *
 *               Current State
 *                     |
 *               Available Choices
 *                     |
 *                   Choose
 *                     |
 *                   Explore
 *                     |
 *                 Valid?
 *               /        \
 *             YES         NO
 *              |           |
 *            Save        Prune
 *               \         /
 *                   Undo
 *                     |
 *                Next Choice
 */
