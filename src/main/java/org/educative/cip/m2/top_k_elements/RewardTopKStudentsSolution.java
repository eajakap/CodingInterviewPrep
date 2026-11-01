package org.educative.cip.m2.top_k_elements;

import java.util.*;

/**
 * Reward Top K Students Based on Feedback Scores
 * Given positive and negative feedback words, a list of student reports, and their corresponding IDs,
 * this class calculates the scores for each student based on the feedback in their reports.
 * It then returns the top k students with the highest scores.
 * Time complexity: O(n * m), where n is the number of reports and m is the average number of words in each report.
 * Space complexity: O(p + q + n), where p is the number of positive feedback
 * words, q is the number of negative feedback words, and n is the number of students.
 */
public class RewardTopKStudentsSolution {

    /**
     * Returns the IDs of the top {@code k} students ranked by feedback score.
     *
     * Scoring: each word in a student's report that appears in {@code positive_feedback}
     * adds 3 points, and each word that appears in {@code negative_feedback} subtracts 1 point.
     * Other words do not change the score.
     *
     * Ranking: students are ordered by score, highest first. Students with the same score
     * are ordered by ID, lowest first.
     *
     * @param positive_feedback words that earn 3 points each
     * @param negative_feedback words that cost 1 point each
     * @param report            space-separated feedback report for each student
     * @param student_id        the ID of each student; {@code student_id[i]} owns {@code report[i]}
     * @param k                 the number of students to return ({@code k <= report.length})
     * @return the IDs of the top {@code k} students, best first
     */
    public List<Integer> topStudents(String[] positive_feedback,
                                     String[] negative_feedback,
                                     String[] report,
                                     int[] student_id,
                                     int k) {
        // Put the feedback words in sets so each word lookup is O(1)
        Set<String> positiveSet = new HashSet<>(Arrays.asList(positive_feedback));
        Set<String> negativeSet = new HashSet<>(Arrays.asList(negative_feedback));
        // Each entry is [score, student ID]
        List<int[]> scoredStudents = new ArrayList<>();

        // Score every student's report
        for (int i = 0; i < report.length; i++) {
            int score = 0;

            for (String word : report[i].split(" ")) {
                if (positiveSet.contains(word)) {
                    score += 3;  // Positive word: +3
                } else if (negativeSet.contains(word)) {
                    score -= 1;  // Negative word: -1
                }
            }

            scoredStudents.add(new int[]{score, student_id[i]});
        }

        // Rank students: higher score first; on a tie, lower ID first
        scoredStudents.sort((a, b) -> {
            if (a[0] != b[0]) {
                return Integer.compare(b[0], a[0]);  // Descending by score
            }
            return Integer.compare(a[1], b[1]);  // Ascending by ID
        });

        // Take the IDs of the first k students in the ranking
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            result.add(scoredStudents.get(i)[1]);
        }
        return result;
    }

    /**
     * Same as {@link #topStudents(String[], String[], String[], int[], int)}, but ranks the
     * students with a max-heap instead of sorting the whole list.
     *
     * Approach: score every student, push each [score, ID] pair onto a max-heap whose top is
     * the best student (highest score; on a tie, lowest ID), then poll the heap k times.
     * Each poll returns the next-best student, so the IDs come out already in ranked order.
     *
     * Time complexity: O(n * m + n log n + k log n), where n is the number of reports and
     * m is the average number of words in a report. Building the heap one entry at a time is
     * O(n log n), and the k polls are O(k log n).
     * Space complexity: O(p + q + n) for the two word sets and the heap.
     *
     * @param positive_feedback words that earn 3 points each
     * @param negative_feedback words that cost 1 point each
     * @param report            space-separated feedback report for each student
     * @param student_id        the ID of each student; {@code student_id[i]} owns {@code report[i]}
     * @param k                 the number of students to return ({@code k <= report.length})
     * @return the IDs of the top {@code k} students, best first
     */
    public List<Integer> topStudentsMaxHeap(String[] positive_feedback,
                                            String[] negative_feedback,
                                            String[] report,
                                            int[] student_id,
                                            int k) {
        // Put the feedback words in sets so each word lookup is O(1)
        Set<String> positiveSet = new HashSet<>(Arrays.asList(positive_feedback));
        Set<String> negativeSet = new HashSet<>(Arrays.asList(negative_feedback));

        // Max-heap of [score, student ID]: the best student is always on top.
        // Higher score wins; on a tie, the lower ID wins.
        PriorityQueue<int[]> maxHeap = new PriorityQueue<>((a, b) -> {
            if (a[0] != b[0]) {
                return Integer.compare(b[0], a[0]);  // Higher score comes first
            }
            return Integer.compare(a[1], b[1]);  // Lower ID comes first
        });

        // Score every student's report and push the result onto the heap
        for (int i = 0; i < report.length; i++) {
            int score = 0;

            for (String word : report[i].split(" ")) {
                if (positiveSet.contains(word)) {
                    score += 3;  // Positive word: +3
                } else if (negativeSet.contains(word)) {
                    score -= 1;  // Negative word: -1
                }
            }

            maxHeap.offer(new int[]{score, student_id[i]});
        }

        // Poll the best remaining student k times; the IDs come out in ranked order
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            result.add(maxHeap.poll()[1]);
        }
        return result;
    }

    /**
     * Same as {@link #topStudents(String[], String[], String[], int[], int)}, but keeps only
     * the best k students in a min-heap capped at size k.
     *
     * Approach: the heap's top is the worst of the current top k (lowest score; on a tie,
     * highest ID). Each scored student is pushed onto the heap, and whenever the heap grows
     * past k the worst student is removed. At the end the heap holds exactly the top k.
     * Polling it returns them worst first, so the list is reversed to put the best first.
     *
     * Time complexity: O(n * m + n log k), where n is the number of reports and m is the
     * average number of words in a report. Each push or pop costs O(log k) because the heap
     * never holds more than k + 1 entries; emptying the heap at the end is O(k log k).
     * Space complexity: O(p + q + k) for the two word sets and the heap.
     *
     * @param positive_feedback words that earn 3 points each
     * @param negative_feedback words that cost 1 point each
     * @param report            space-separated feedback report for each student
     * @param student_id        the ID of each student; {@code student_id[i]} owns {@code report[i]}
     * @param k                 the number of students to return ({@code k <= report.length})
     * @return the IDs of the top {@code k} students, best first
     */
    public List<Integer> topStudentsMinHeap(String[] positive_feedback,
                                            String[] negative_feedback,
                                            String[] report,
                                            int[] student_id,
                                            int k) {
        // Put the feedback words in sets so each word lookup is O(1)
        Set<String> positiveSet = new HashSet<>(Arrays.asList(positive_feedback));
        Set<String> negativeSet = new HashSet<>(Arrays.asList(negative_feedback));

        // Min-heap of [score, student ID]: the worst of the current top k is always on top.
        // Lower score is worse; on a tie, the higher ID is worse.
        PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> {
            if (a[0] != b[0]) {
                return Integer.compare(a[0], b[0]);  // Lower score comes first
            }
            return Integer.compare(b[1], a[1]);  // Higher ID comes first
        });

        // Score every student's report and keep only the best k in the heap
        for (int i = 0; i < report.length; i++) {
            int score = 0;

            for (String word : report[i].split(" ")) {
                if (positiveSet.contains(word)) {
                    score += 3;  // Positive word: +3
                } else if (negativeSet.contains(word)) {
                    score -= 1;  // Negative word: -1
                }
            }

            minHeap.offer(new int[]{score, student_id[i]});

            // Heap is over capacity: drop the worst student so only the best k remain
            if (minHeap.size() > k) {
                minHeap.poll();
            }
        }

        // Empty the heap; students come out worst first
        List<Integer> result = new ArrayList<>(k);
        while (!minHeap.isEmpty()) {
            result.add(minHeap.poll()[1]);
        }
        // Reverse so the best student is first
        Collections.reverse(result);
        return result;
    }

    /**
     * Driver code: runs {@link #topStudents(String[], String[], String[], int[], int)},
     * {@link #topStudentsMaxHeap(String[], String[], String[], int[], int)}, and
     * {@link #topStudentsMinHeap(String[], String[], String[], int[], int)} against
     * sample inputs and prints the results.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        // Test case i uses row i of each array below
        String[][] positive_feedback = {
                {"excellent", "kind"},
                {"helpful", "calm"},
                {"bright"},
                {"fast", "accurate"},
                {"fair"}
        };

        String[][] negative_feedback = {
                {"late"},
                {"rude", "slow"},
                {"dark"},
                {"wrong"},
                {"bad"}
        };

        String[][] report = {
                {"excellent kind late", "kind kind"},
                {"helpful rude calm", "slow slow", "calm"},
                {"bright bright dark", "dark bright", "quiet"},
                {"fast wrong accurate", "wrong wrong", "accurate fast"},
                {"fair", "fair", "bad fair"}
        };

        int[][] student_id = {
                {10, 4},
                {31, 12, 25},
                {100, 50, 75},
                {8, 3, 11},
                {42, 7, 19}
        };

        int[] k = {1, 2, 3, 2, 2};
        RewardTopKStudentsSolution solution = new RewardTopKStudentsSolution();

        for (int i = 0; i < 5; i++) {
            // Time each approach with System.nanoTime(); start = time before the call.
            // Times are printed in microseconds (us). The first test case is slower because
            // the JVM is still loading classes and warming up the JIT compiler.
            long start = System.nanoTime();
            List<Integer> result = solution.topStudents(
                    positive_feedback[i],
                    negative_feedback[i],
                    report[i],
                    student_id[i],
                    k[i]
            );
            long sortTime = System.nanoTime() - start;

            System.out.println((i + 1) + ".\tpositive_feedback = "
                    + Arrays.toString(positive_feedback[i]));
            System.out.println("\tnegative_feedback = "
                    + Arrays.toString(negative_feedback[i]));
            System.out.println("\treport = " + Arrays.toString(report[i]));
            System.out.println("\tstudent_id = " + Arrays.toString(student_id[i]));
            System.out.println("\tk = " + k[i]);
            System.out.println("\t");
            System.out.println("\tOutput: " + result
                    + "\t\tTime (sort): " + sortTime / 1_000 + " us");
            // Run the max-heap version on the same input; it should match the sorted version
            start = System.nanoTime();
            List<Integer> maxHeapResult = solution.topStudentsMaxHeap(
                    positive_feedback[i],
                    negative_feedback[i],
                    report[i],
                    student_id[i],
                    k[i]
            );
            long maxHeapTime = System.nanoTime() - start;
            System.out.println("\tOutput (max-heap): " + maxHeapResult
                    + "\tTime (max-heap): " + maxHeapTime / 1_000 + " us");
            // Run the min-heap version on the same input; it should also match
            start = System.nanoTime();
            List<Integer> minHeapResult = solution.topStudentsMinHeap(
                    positive_feedback[i],
                    negative_feedback[i],
                    report[i],
                    student_id[i],
                    k[i]
            );
            long minHeapTime = System.nanoTime() - start;
            System.out.println("\tOutput (min-heap): " + minHeapResult
                    + "\tTime (min-heap): " + minHeapTime / 1_000 + " us");
            // Print a separator line of 100 dashes
            System.out.println("-".repeat(100));
        }
    }
}