package org.educative.cip.m2.merge_intervals;

import java.util.*;

/**
 * You are given an array schedules representing existing meetings for all employees over the course of an entire day,
 * and an integer length representing the length of a new meeting in minutes.
 * Each element in schedules is an array, such that schedules[i][j] represents the j-th meeting for
 * the i-th employee. Each meeting is represented by a pair of integers: [startTime, finishTime],
 * where each integer represents the number of minutes passed since the beginning of the day.
 * startTime and finishTime do not exceed 24 * 60.
 * Your task is to find the earliest possible time when a meeting of length can be scheduled for all employees.
 * If there is no time block which suits everyone, return -1.
 * Note: The new meeting should also fit within the same day, so the finish time for this meeting should not exceed 24 * 60.
 * Note: You are not expected to provide the most optimal solution, but a solution with time complexity not worse than
 *       O(schedules.length^2 * max(schedules[i].length)^2) will fit within the execution time limit.
 *
 * Example
 * For
 * schedules = [
 *   [[60, 150], [180, 240]],
 *   [[0, 210], [360, 420]]
 * ]
 * and length = 120, the output should be solution(schedules, length) = 240.
 */
public class EarliestMeetingSlotForAll {

    static class Solution {

        // The new meeting must finish within the same day.
        private static final int DAY_END = 24 * 60;

        /**
         * Finds the earliest minute at which a meeting of the given length is free for every employee.
         * Steps:
         * 1. Flatten every employee's meetings into a single list of busy intervals
         *    (who owns an interval is irrelevant: the slot has to be free for everyone).
         * 2. Sort the busy intervals by start time.
         * 3. Sweep the sorted intervals, tracking 'free' - the earliest minute not covered by any
         *    interval processed so far.
         *    a. If the next interval starts at least 'length' minutes after 'free',
         *       the gap [free, free + length] suits everyone, so return 'free'.
         *    b. Otherwise the interval either overlaps or touches the gap,
         *       so push 'free' to the maximum of itself and the interval's finish time.
         *       (Taking the max is what merges overlapping/nested intervals.)
         * 4. After the last interval, check the tail of the day [free, DAY_END].
         * 5. If no gap is wide enough, return -1.
         * Time Complexity: O(N log N), where N is the total number of meetings across all employees.
         * Space Complexity: O(N) for the flattened list of busy intervals.
         * @param schedules: schedules[i][j] = [startTime, finishTime] - the j-th meeting of the i-th employee.
         * @param length: the length of the new meeting in minutes.
         * @return: the earliest start time for the new meeting, or -1 if no such time exists.
         */
        public static int solution(int[][][] schedules, int length) {
            // A meeting longer than the whole day never fits.
            if (length > DAY_END) {
                return -1;
            }

            // The number of employees is irrelevant to the algorithm, but it is a useful sanity check.
            int numberOfEmployees = schedules.length;
            if (numberOfEmployees == 0) {
                // No employees means no meetings, so the whole day is free.
                return -1;
            }
            // 1. Flatten every employee's meetings into a single list of busy intervals.
            List<int[]> busy = new ArrayList<>();
            for (int[][] employee : schedules) {
                if (employee == null) {
                    continue;
                }
                for (int[] meeting : employee) {
                    // Skip empty meetings: they block nobody.
                    if (meeting[0] < meeting[1]) {
                        busy.add(meeting);
                    }
                }
            }

            // 2. Sort the busy intervals by start time.
            busy.sort((a, b) -> a[0] - b[0]);

            // 3. Sweep the sorted intervals, tracking the earliest minute that is still free.
            int free = 0;
            for (int[] meeting : busy) {
                // a. The gap in front of this meeting is wide enough for everyone.
                if (meeting[0] - free >= length) {
                    return free;
                }
                // b. This meeting overlaps (or touches) the gap, so the gap restarts after it.
                free = Math.max(free, meeting[1]);
            }

            // 4. Everybody is free from the last meeting until the end of the day.
            if (DAY_END - free >= length) {
                return free;
            }

            // 5. No time block suits everyone.
            return -1;
        }

        // Function for displaying the schedules of all employees
        private static String display(int[][][] schedules) {
            StringJoiner employees = new StringJoiner("\n\t\t");

            for (int[][] employee : schedules) {
                StringJoiner meetings = new StringJoiner(", ", "[", "]");

                for (int[] meeting : employee) {
                    meetings.add("[" + meeting[0] + ", " + meeting[1] + "]");
                }

                employees.add(meetings.toString());
            }

            return employees.toString();
        }

        // Driver code
        public static void main(String[] args) {
            int[][][][] inputs = {
                    // Sample: merged busy time is [0, 240] and [360, 420], so [240, 360] is the first fit.
                    {{{60, 150}, {180, 240}}, {{0, 210}, {360, 420}}},
                    // The very start of the day is free.
                    {{{600, 700}}, {{800, 900}}},
                    // Nested and overlapping intervals must merge into one busy block.
                    {{{0, 1000}, {100, 200}}, {{500, 1380}}},
                    // Back-to-back meetings leave no gap at all until the end of the day.
                    {{{0, 720}}, {{720, 1440}}},
                    // Only the tail of the day is wide enough.
                    {{{0, 1340}}, {{1340, 1380}}},
                    // Nobody is busy, so the meeting starts at minute 0.
                    {{}, {}},
                    // A full-day meeting fits exactly when everyone is free.
                    {{}, {}}
            };
            int[] lengths = {120, 600, 60, 1, 60, 30, 1440};

            for (int i = 0; i < inputs.length; i++) {
                System.out.println((i + 1) + ".\tEmployee Schedules:\n");
                System.out.println("\t\t" + display(inputs[i]));
                System.out.println("\n\tMeeting length: " + lengths[i]);
                System.out.println("\tEarliest start time: " + solution(inputs[i], lengths[i]));
                System.out.println(new String(new char[100]).replace('\0', '-'));
            }
        }
    }
}
