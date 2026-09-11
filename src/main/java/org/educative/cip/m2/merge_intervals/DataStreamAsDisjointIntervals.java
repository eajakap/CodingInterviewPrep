package org.educative.cip.m2.merge_intervals;
import java.util.*;
import java.io.*;

/**
 * Data Stream as Disjoint Intervals
 * Problem: Given a data stream input of non-negative integers a1, a2, ..., an, ..., summarize the numbers seen so far as a list of disjoint intervals.
 * Implement the SummaryRanges class:
 * 1. SummaryRanges() Initializes the object with an empty stream.
 * 2. void addNum(int value) Adds the integer value to the stream.
 * 3. int[][] getIntervals() Returns a summary of the integers in the stream currently as a list of disjoint intervals [starti, endi]. The answer should be sorted by starti.
 * Example 1:
 * Input
 * ["SummaryRanges", "addNum", "getIntervals", "addNum", "getIntervals", "addNum", "addNum", "addNum", "getIntervals"]
 * [[], [1], [], [3], [], [7], [2], [6], []]
 * Output
 * [null, null, [[1, 1]], null, [[1, 1], [3, 3]], null, null, null, [[1, 3], [6, 7]]]
 * Explanation
 * SummaryRanges summaryRanges = new SummaryRanges(); // arr = []
 * summaryRanges.addNum(1);      // arr = [1]
 * summaryRanges.getIntervals(); // return [[1, 1]]
 * summaryRanges.addNum(3);      // arr = [1, 3]
 * summaryRanges.getIntervals(); // return [[1, 1], [3, 3]]
 * summaryRanges.addNum(7);      // arr = [1, 3, 7]
 * summaryRanges.addNum(2);      // arr = [1, 2, 3, 7]
 * summaryRanges.addNum(6);      // arr = [1, 2, 3, 6, 7]
 * summaryRanges.getIntervals(); // return [[1, 3], [6, 7]]
 * Constraints:
 * 0 <= value <= 10^4
 * At most 3 * 10^4 calls will be made to addNum and getIntervals.
 *
 * Time Complexity: O(log n) for addNum and O(n) for getIntervals, where n is the number of disjoint intervals.
 * Space Complexity: O(n) for storing the disjoint intervals.
 */

public class DataStreamAsDisjointIntervals {

    /**
     * SummaryRanges class maintains a collection of disjoint intervals based on the numbers added to the stream.
     * It uses a TreeMap to store the intervals, where the key is the start of the interval and the value is the end of the interval.
     */
    static class SummaryRanges {
        private final TreeMap<Integer, Integer> intervals;

        public SummaryRanges() {
            intervals = new TreeMap<>();
        }

        /**
         * Adds a number to the stream and updates the disjoint intervals accordingly.
         * If the number is already covered by an existing interval, it does nothing.
         * If the number can merge with adjacent intervals, it merges them into a single interval.
         *
         * @param value the integer value to be added to the stream
         */
        public void addNum(int value) {
            int newStart = value;
            int newEnd = value;

            Map.Entry<Integer, Integer> nextInterval = intervals.higherEntry(value);
            Map.Entry<Integer, Integer> prevInterval = intervals.floorEntry(value);

            if (prevInterval != null) {
                if (prevInterval.getValue() >= value) {
                    return;
                }

                if (prevInterval.getValue() == value - 1) {
                    newStart = prevInterval.getKey();
                }
            }

            if (nextInterval != null && nextInterval.getKey() == value + 1) {
                newEnd = nextInterval.getValue();
                intervals.remove(nextInterval.getKey());
            }

            intervals.put(newStart, newEnd);
        }

        /**
         * Returns the current list of disjoint intervals as a 2D array.
         * Each interval is represented as [start, end].
         *
         * @return a 2D array of disjoint intervals
         */
        public int[][] getIntervals() {
            int[][] result = new int[intervals.size()][2];
            int i = 0;
            for (Map.Entry<Integer, Integer> e : intervals.entrySet()) {
                result[i][0] = e.getKey();
                result[i][1] = e.getValue();
                i++;
            }
            return result;
        }

        /**
         * Converts a 2D array of intervals into a string representation for easy visualization.
         *
         * @param ivals a 2D array of intervals
         * @return a string representation of the intervals
         */
        public static String intervalsToString(int[][] ivals) {
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            for (int i = 0; i < ivals.length; ++i) {
                sb.append("[").append(ivals[i][0]).append(", ").append(ivals[i][1]).append("]");
                if (i + 1 < ivals.length) sb.append(", ");
            }
            sb.append("]");
            return sb.toString();
        }

        public static void main(String[] args) {
            List<String[]> commandsList = Arrays.asList(
                    new String[] {"SummaryRanges", "addNum", "getIntervals"},
                    new String[] {"SummaryRanges", "addNum", "addNum", "addNum", "getIntervals"},
                    new String[] {"SummaryRanges", "addNum", "addNum", "getIntervals", "addNum", "getIntervals", "addNum", "getIntervals"},
                    new String[] {"SummaryRanges", "addNum", "addNum", "addNum", "addNum", "addNum", "getIntervals"},
                    new String[] {"SummaryRanges", "addNum", "addNum", "addNum", "addNum", "addNum", "getIntervals"}
            );

            List<int[][]> argsList = Arrays.asList(
                    new int[][] { {}, {5}, {} },
                    new int[][] { {}, {1}, {3}, {2}, {} },
                    new int[][] { {}, {3}, {5}, {}, {2}, {}, {6}, {} },
                    new int[][] { {}, {1}, {4}, {2}, {9}, {3}, {} },
                    new int[][] { {}, {1}, {0}, {8}, {7}, {6}, {} }
            );

            for (int t = 0; t < commandsList.size(); ++t) {
                String[] commands = commandsList.get(t);
                int[][] argBlocks = argsList.get(t);

                List<String> outputs = new ArrayList<>();
                SummaryRanges obj = null;

                for (int i = 0; i < commands.length; ++i) {
                    String cmd = commands[i];
                    if (cmd.equals("SummaryRanges")) {
                        obj = new SummaryRanges();
                        outputs.add("null");
                    } else if (cmd.equals("addNum")) {
                        int val = argBlocks[i][0];
                        obj.addNum(val);
                        outputs.add("null");
                    } else if (cmd.equals("getIntervals")) {
                        int[][] res = obj.getIntervals();
                        outputs.add(intervalsToString(res));
                    }
                }

                // Print commands
                System.out.print((t + 1) + "\t [");
                for (int i = 0; i < commands.length; ++i) {
                    System.out.print("\"" + commands[i] + "\"");
                    if (i + 1 < commands.length) System.out.print(", ");
                }
                System.out.println("]");

                // Print args
                System.out.print("\t [");
                for (int i = 0; i < argBlocks.length; ++i) {
                    System.out.print("[");
                    for (int j = 0; j < argBlocks[i].length; ++j) {
                        System.out.print(argBlocks[i][j]);
                        if (j + 1 < argBlocks[i].length) System.out.print(", ");
                    }
                    System.out.print("]");
                    if (i + 1 < argBlocks.length) System.out.print(", ");
                }
                System.out.println("]");

                // Print outputs
                System.out.print("\n\t Output: [");
                for (int i = 0; i < outputs.size(); ++i) {
                    System.out.print(outputs.get(i));
                    if (i + 1 < outputs.size()) System.out.print(", ");
                }
                System.out.println("]");
                System.out.println("-".repeat(100));
            }
        }
    }

    public static void main(String[] args) {
        SummaryRanges.main(args);
    }
}
