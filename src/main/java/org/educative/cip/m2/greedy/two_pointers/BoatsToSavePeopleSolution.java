package org.educative.cip.m2.greedy.two_pointers;

import java.util.Arrays;

/**
 * Greedy Pattern: Two Pointers + Greedy
 * Basic Idea:
 * Sort
 * ↓
 * left = smallest
 * right = largest
 * ↓
 * Make greedy decision
 * ↓
 * Move one or both pointers
 *
 * Problem:
 * You are given an array people where people[i] is the weight of the ith person, and an infinite number of boats where each boat can carry a maximum weight of limit.
 * Each boat carries at most two people at the same time, provided the sum of the weight of those people is at most limit.
 * Return the minimum number of boats to carry every given person.
 * Steps:
 * 1. Sort the array of people's weights in ascending order.
 * 2. Use two pointers: one starting at the beginning (lightest person) and the other at the end (heaviest person).
 * 3. Check if the sum of the weights of the two people pointed to by the pointers is less than or equal to the limit.
 *   - If yes, move both pointers inward (i.e., the lightest person and the heaviest person can share a boat).
 *   - If no, move only the pointer for the heaviest person inward (i.e., the heaviest person goes alone).
 * 4. Increment the boat count for each iteration of the loop.
 * 5. Continue this process until the two pointers meet or cross each other.
 * 6. Return the total number of boats used.
 *
 * Example 1:
 * Input: people = [1,2], limit = 3
 * Output: 1
 * Explanation: 1 boat (1, 2)
 *
 * Example 2:
 * Input: people = [3,2,2,1], limit = 3
 * Output: 3
 * Explanation: 3 boats (1, 2), (2) and (3)
 *
 * Example 3:
 * Input: people = [3,5,3,4], limit = 5
 * Output: 4
 * Explanation: 4 boats (3), (3), (4), (5)
 *
 * Constraints:
 * 1 <= people.length <= 5 * 10^4
 * 1 <= people[i] <= limit <= 3 * 10^4
 *
 * Why pair the heaviest with the lightest:
 * The heaviest person needs a boat no matter what. The lightest person is the one most
 * likely to fit beside them. If even the lightest does not fit, nobody does, so the heaviest
 * goes alone. Either way, no other pairing could use fewer boats.
 *
 * Time complexity: O(n log n), where n is the number of people, due to the sorting step.
 * The two-pointer pass itself is O(n).
 * Space complexity: O(log n) for the stack space Arrays.sort (dual-pivot quicksort) uses on an int array.
 * The two-pointer pass itself uses O(1) extra space.
 */
public class BoatsToSavePeopleSolution {

    /**
     * Returns the minimum number of boats needed to carry every person.
     *
     * Each boat holds at most two people whose combined weight is at most {@code limit}.
     * Every iteration fills exactly one boat: the heaviest remaining person always boards,
     * and the lightest remaining person joins them if the pair fits under the limit.
     *
     * Note: the people array is sorted in place, so the caller's array is reordered.
     *
     * @param people the weight of each person; every weight is at most {@code limit}
     * @param limit  the maximum total weight a boat can carry
     * @return the minimum number of boats needed
     */
    public static int numRescueBoats(int[] people, int limit) {

        // Sort weights in ascending order so the lightest is at the start and the heaviest at the end
        Arrays.sort(people);

        // left: lightest person still waiting for a boat
        // right: heaviest person still waiting for a boat
        int left = 0;
        int right = people.length - 1;

        // Number of boats used so far
        int boats = 0;
        // peopleonboat[b] holds the weights of the people on boat b: {heaviest, lightest} or {heaviest}.
        // At most people.length boats are needed (everyone alone), so that many rows are reserved.
        int[][] peopleonboat = new int[people.length][];

        // Loop while someone is still waiting.
        // left == right means one person is left, and they still need a boat.
        while (left <= right) {

            // Can the lightest person go with the heaviest?
            // left < right makes sure they are two different people, so the last
            // person left is never paired with themselves.
            if (left < right && people[left] + people[right] <= limit) {
                // Yes: the lightest person shares this boat
                peopleonboat[boats] = new int[]{people[right], people[left]};
                left++;
            } else {
                // No: the heaviest person goes alone
                peopleonboat[boats] = new int[]{people[right]};
            }

            // Heaviest person always gets a boat
            right--;

            // One boat used for this trip (one or two people)
            boats++;
        }

        // Print only the first `boats` rows; the remaining rows were never used
        System.out.println("\tPeople on boats: " + Arrays.deepToString(Arrays.copyOf(peopleonboat, boats)));
        // Every person has been placed in a boat
        return boats;
    }

    /**
     * Driver code: runs {@link #numRescueBoats(int[], int)} on the examples above and prints the results.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        // peopleCases[i] and limits[i] together form test case i; expected outputs: 1, 3, 4, 1
        // Case 4 checks that a single person who fits under the limit is not paired with themselves
        int[][] peopleCases = {
                {1, 2},
                {3, 2, 2, 1},
                {3, 5, 3, 4},
                {1}
        };
        int[] limits = {3, 3, 5, 3};

        for (int i = 0; i < peopleCases.length; i++) {
            // Print the input before calling the method, because it sorts the array in place
            System.out.println((i + 1) + ".\tpeople = " + Arrays.toString(peopleCases[i])
                    + ", limit = " + limits[i]);
            System.out.println("\tMinimum boats: " + numRescueBoats(peopleCases[i], limits[i]));
            // Print a separator line of 100 dashes
            System.out.println("-".repeat(100));
        }
    }
}

/**
 * Notes:
 * Recognition Suggestions:
 * 1. pairing people/items
 * 2. minimum number of groups
 * 3. weight/capacity limits
 * 4. matching smallest with largest
 * 5. maximizing the number of valid pairs
 * 6. sorted values where choices at both ends matter
 *
 * Mental Template:
 * 1. Sort the input
 * 2. left  = smallest
 *    right = largest
 * 3. Ask:
 *    Can smallest + largest work together?
 * 4. YES:
 *       Use/pair both
 *       left++
 *       right--
 * 5. NO:
 *       Handle largest alone
 *       right--
 * 6. Repeat until pointers cross.
 */