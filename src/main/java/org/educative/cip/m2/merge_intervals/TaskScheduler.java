package org.educative.cip.m2.merge_intervals;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 * Task Scheduler
 * Problem:
 * Given a characters array tasks, representing the tasks a CPU needs to do, where each letter represents a different task.
 * Tasks could be done in any order. Each task is done in one unit of time. For each unit of time, the CPU could complete either one task or just be idle.
 * However, there is a non-negative integer n that represents the cooldown period between two same tasks (the same letter in the array),
 * that is that there must be at least n units of time.
 * Time is represented as the number of units of time that the CPU will take to finish all the given tasks.
 * Time complexity: O(nlogn) where n is the number of tasks
 * Space complexity: O(1) since the frequency array is of fixed size 26
 */
public class TaskScheduler {
    /**
     * Returns the least number of units of times that the CPU will take to finish all the given tasks.
     * Steps:
     * 1. Count the frequency of each task and store it in an array of size 26 (for each letter A-Z).
     * 2. Sort the frequency array in descending order.
     * 3. Calculate the maximum number of gaps between the most frequent task (maxGaps = frequencies[0] - 1).
     * 4. Calculate the number of idle slots (idleSlots = maxGaps * n).
     * 5. For each of the remaining tasks, reduce the number of idle slots by the minimum of maxGaps and the frequency of the task.
     * 6. If there are still idle slots left, add them to the total time. Otherwise, the total time is just the length of the tasks array.
     *
     * Time Complexity: O(nlogn) where n is the number of tasks
     * Space Complexity: O(1) since the frequency array is of fixed size 26
     *
     * @param tasks an array of characters representing the tasks a CPU needs to do
     * @param n     a non-negative integer that represents the cooldown period between two same tasks
     * @return the least number of units of times that the CPU will take to finish all the given tasks
     */
    public static int leastInterval(char[] tasks, int n) {
        int[] frequencies = new int[26];

        for (char task : tasks)
            frequencies[task - 'A']++;

        frequencies = Arrays.stream(frequencies)
                .boxed()
                .sorted(Comparator.reverseOrder())
                .mapToInt(Integer::intValue)
                .toArray();

        int maxGaps = frequencies[0] - 1;

        int idleSlots = maxGaps * n;

        for (int i = 1; i < 26; i++) {
            idleSlots -= Math.min(maxGaps, frequencies[i]);
        }

        idleSlots = Math.max(0, idleSlots);

        return tasks.length + idleSlots;
    }

    // Driver code
    public static void main(String[] args) {
        char[][] allTasks = {
                {'A', 'A', 'B', 'B'},
                {'A', 'A', 'A', 'B', 'B', 'C', 'C'},
                {'S', 'I', 'V', 'U', 'W', 'D', 'U', 'X'},
                {'M', 'A', 'B', 'M', 'A', 'A', 'Y', 'B', 'M'},
                {'A', 'K', 'X', 'M', 'W', 'D', 'X', 'B', 'D', 'C', 'O', 'Z', 'D', 'E', 'Q'}};

        int[] allNs = {2, 1, 0, 3, 3};

        for (int i = 0; i < allTasks.length; i++) {
            System.out.print((i + 1) + ".\tTasks: ");
            char[] tasks = allTasks[i];
            for(int j = 0; j < tasks.length; j++) {
                System.out.print(tasks[j]);
                if (j != tasks.length - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println("\n\tn: " + allNs[i]);

            int minTime = leastInterval(allTasks[i], allNs[i]);
            System.out.println("\tMinimum time required to execute the tasks: " + minTime);
            System.out.println('-' + String.join("", Collections.nCopies(100, "-")) + '\n');
        }
    }

}
