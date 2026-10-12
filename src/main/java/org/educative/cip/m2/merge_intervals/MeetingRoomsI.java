package org.educative.cip.m2.merge_intervals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;

/**
 * You are given an array schedules representing existing meetings for all employees over the course of an entire day,
 * and an integer length representing the length of a new meeting in minutes.
 * Each element in schedules is an array, such that schedules[i][j] represents the j^{\text{th}} meeting for
 * the ith employee. Each meeting is represented by a pair of integers: [startTime, finishTime],
 * where each integer represents the number of minutes passed since the beginning of the day.
 * startTime and finishTime do not exceed 24 * 60.
 * Your task is to find the earliest possible time when a meeting of length can be scheduled for all employees.
 * If there is no time block which suits everyone, return -1.
 * Note: The new meeting should also fit within the same day, so the finish time for this meeting should not exceed 24 * 60.
 * Note: You are not expected to provide the most optimal solution, but a solution with time complexity not worse than
 *       O(schedules.length^2 * max(schedules[i].length)^2) will fit within the execution time limit.
 * Example
 * For
 * schedules = [
 *   [[60, 150], [180, 240]],
 *   [[0, 210], [360, 420]]
 * ]
 * and length = 120, the output should be solution(schedules, length) = 240.
 */
public class MeetingRoomsI {

    static class Solution {
        /**
         * Finds the earliest possible time when a meeting of the specified length can be scheduled for all employees.
         *
         * @param schedules An array representing existing meetings for all employees.
         * @param length    The length of the new meeting in minutes.
         * @return The earliest possible start time for the new meeting, or -1 if no suitable time block exists.
         */
        public static int solution(int[][][] schedules, int length) {
            // Implementation goes here
            List<Integer> candidates = new ArrayList<>();
            candidates.add(0);
            for (int[][] emp : schedules) {
                for (int[] m : emp) {
                    //candidates.add(m[0]); // every start time
                    candidates.add(m[1]); // every finish time
                }
            }
            Collections.sort(candidates);

            for (int t : candidates) {
                int end = t + length;
                if (end > 24 * 60) break;          // past this point nothing fits in the day
                boolean free = true;
                for (int[][] emp : schedules) {
                    for (int[] m : emp) {
                        if (m[0] < end && t < m[1]) {
                            free = false;
                            break;
                        }
                    }
                    if (!free) break;
                }
                if (free) return t;
            }
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
