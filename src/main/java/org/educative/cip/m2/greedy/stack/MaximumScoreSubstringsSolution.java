package org.educative.cip.m2.greedy.stack;

/**
 * Given a string s and two integers x and y, return the maximum score you can get by removing
 * either "ab" or "ba" substrings from s. You can remove the substrings in any order.
 * The score for removing "ab" is x and the score for removing "ba" is y.
 * Time complexity: O(n), where n is the length of the string s.
 * Space complexity: O(n), for the stack used to process the string.
 */
public class MaximumScoreSubstringsSolution {
    /**
     * Helper class to hold the result of the remove operation.
     */
    private static class Result {
        int score;
        String remaining;

        Result(int score, String remaining) {
            this.score = score;
            this.remaining = remaining;
        }
    }

    private Result remove(String s, char first, char second, int score) {
        StringBuilder stack = new StringBuilder();
        int totalScore = 0;
        for (char c : s.toCharArray()) {
            if (stack.length() > 0 && stack.charAt(stack.length() - 1) == first && c == second) {
                stack.deleteCharAt(stack.length() - 1);
                totalScore += score;
            } else {
                stack.append(c);
            }
        }
        return new Result(totalScore, stack.toString());
    }

    public int maximumGain(String s, int x, int y) {

        if (x >= y) {
            Result firstPass = remove(s, 'a', 'b', x);
            Result secondPass = remove(firstPass.remaining, 'b', 'a', y);
            return firstPass.score + secondPass.score;
        } else {
            Result firstPass = remove(s, 'b', 'a', y);
            Result secondPass = remove(firstPass.remaining, 'a', 'b', x);
            return firstPass.score + secondPass.score;
        }
    }

    public static void main(String[] args) {
        String[] strings = {"abxba", "baba", "aabbab", "zzabyyba", "ababab"};
        int[][] scores = {
                {7, 3},
                {2, 9},
                {10, 1},
                {6, 8},
                {3, 7}
        };

        MaximumScoreSubstringsSolution solution = new MaximumScoreSubstringsSolution();

        for (int i = 0; i < strings.length; i++) {
            String s = strings[i];
            int x = scores[i][0];
            int y = scores[i][1];
            int result = solution.maximumGain(s, x, y);

            System.out.println((i + 1) + ".\ts = '" + s + "'");
            System.out.println("\tx = " + x);
            System.out.println("\ty = " + y);
            System.out.println("\t");
            System.out.println("\tOutput: " + result);
            System.out.println("-".repeat(100));
        }
    }

}
