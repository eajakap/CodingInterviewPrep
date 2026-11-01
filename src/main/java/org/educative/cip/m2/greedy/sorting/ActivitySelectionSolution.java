package org.educative.cip.m2.greedy.sorting;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Greedy Pattern 2: Sorting + Greedy
 * Idea: Sorting puts choices in an order that makes the greedy decision easy.
 * Approach:
 * 1. Unordered input
 *       ↓
 * 2. Sort
 *       ↓
 * 3. Process left → right
 *       ↓
 * 4. Make the best local choice
 *       ↓
 * 5. Never reconsider previous choices
 * Problem:
 * Given a set of activities with start and end times,
 * find the maximum number of activities that can be performed by a single person,
 * assuming that a person can only work on a single activity at a time.
 * Constraints:
 * - The start and end times of the activities are given as a 2D array,
 *   where each activity is represented as an array of two integers [start, end].
 * - The activities may overlap in time.
 * - The goal is to select the maximum number of non-overlapping activities.
 * - An activity may start at the exact time the previous one ends (touching is not overlapping).
 *
 * Why sort by end time:
 * The activity that ends earliest leaves the most time for everything after it.
 * Picking it never makes the final answer worse, so it is always a safe greedy choice.
 *
 * Time complexity: O(n log n), where n is the number of activities, due to the sorting step.
 * Space complexity: O(n) in the worst case, because Arrays.sort on an object array (TimSort)
 * may use up to n / 2 temporary slots. The selection loop itself uses O(1) extra space.
 */
public class ActivitySelectionSolution {
    /**
     * Returns the maximum number of non-overlapping activities that can be performed by a single person.
     * Steps:
     * 1. Sort the activities based on their end times.
     * 2. Initialize a counter for the number of activities and a variable to track the end time of the last selected activity.
     * 3. Iterate through the sorted activities and select an activity if its start time is greater than or equal to the end time of the last selected activity.
     * 4. Update the end time of the last selected activity and increment the counter.
     * 5. Return the counter as the maximum number of non-overlapping activities.
     * Time complexity: O(n log n) due to the sorting step, where n is the number of activities.
     * Space complexity: O(n) in the worst case, because Arrays.sort on an object array (TimSort) may use up to n / 2 temporary slots.
     * The selection loop itself uses O(1) extra space.
     * Note: the activities array is sorted in place, so the caller's array is reordered.
     *
     * @param activities a 2D array where each activity is represented as an array of two integers [start, end]
     * @return the maximum number of non-overlapping activities
     */
    public static int maxActivities(int[][] activities) {
        // Sort the activities based on their end times - activity[start, end]
        // so the earliest-finishing activity is always considered first
        Arrays.sort(activities, Comparator.comparingInt(a -> a[1]));

        // count: number of activities selected so far
        // lastEnd: end time of the most recently selected activity.
        // It starts at Integer.MIN_VALUE so the first activity is always selected.
        int count = 0;
        int lastEnd = Integer.MIN_VALUE;
        int[][] selected = new int[activities.length][2];
        // Iterate through the sorted activities - left → right,
        // making the greedy choice of selecting the earliest-finishing activity
        // that does not overlap with the last selected activity.
        for (int[] activity : activities) {
            // Unpack the start and end times of the current activity
            int start = activity[0];
            int end = activity[1];
            // Check If the current activity starts after or when the last selected activity ends,
            if (start >= lastEnd) {
                // No overlap with the last selected activity: take this one.
                // It ends earliest among the remaining compatible activities,
                // leaving the most room for the ones that follow.
                selected[count] = activity;
                count++;
                lastEnd = end;
            }
            // Otherwise it starts before the last selected activity ends,
            // so it overlaps and is skipped
        }
        System.out.println("Selected activities:");
        for (int i = 0; i < count; i++) {
            System.out.println("Selected Activity " + (i + 1) + ": Start = " + selected[i][0] + ", End = " + selected[i][1]);
        }
        // count is the size of the largest set of non-overlapping activities
        return count;
    }

    public static void main(String[] args) {
        int[][] activities = {
            {1, 3},
            {2, 4},
            {3, 5},
            {0, 6},
            {5, 7},
            {8, 9}
        };
        System.out.println("Maximum number of non-overlapping activities: " + maxActivities(activities));
    }
}

/**
 * Notes:
 * Recognition Rule: maximize/minimize something while selecting items with constraints.
 * Recognizing the greedy choice:
 * - The greedy choice is to always select the activity that ends earliest among the remaining activities.
 * - This choice is safe because it leaves the most room for subsequent activities, maximizing the total number of non-overlapping activities that can be selected.
 * - By sorting the activities by their end times, we ensure that we always consider the earliest-finishing activity first, which is the optimal greedy choice.
 * - The algorithm never needs to reconsider previous choices because once an activity is selected, it cannot be unselected, and the next activity is always chosen based on the updated end time of the last selected activity.
 * - This approach guarantees that we find the maximum number of non-overlapping activities
 * Mental Template: Sorting + Greedy
 * 1. Ask: What should I sort by?
 * 2. Sort so the "best choice" appears first.
 * 3. Initialize the state needed to remember the previous greedy choice.
 * 4. Scan from left → right.
 * 5. For each item:
 *    Is this a valid/better choice?
 *          YES → take it and update state
 *          NO  → skip it
 * 6. Never go backward or reconsider previous choices.
 */