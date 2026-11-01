package org.educative.cip.m2.greedy_techniques.greedy;

import java.util.Arrays;

/**
 * Greedy Choice / Take the Best Available:
 * Basic Idea: Always make the choice that looks best at the moment.
 *              In this case, we will try to satisfy the least greedy child with the smallest available cookie.
 * Problem:
 * Given two integer arrays children and cookies, where children[i] is the greed factor of the ith child and cookies[j] is the size of the jth cookie,
 * find the maximum number of children that can be satisfied with the given cookies.
 * A child can be satisfied if they receive a cookie with a size greater than or equal to their greed factor.
 * Constraints:
 * 1 <= children.length, cookies.length <= 3 * 10^4
 * 1 <= children[i], cookies[j] <= 2^31 - 1
 *
 */
public class AssignCookiesSolution {

    /**
     * This method finds the maximum number of children that can be satisfied with the given cookies.
     * It sorts both the children and cookies arrays, then uses a two-pointer technique to match the least greedy child with the smallest available cookie.
     * smallest child
     *       ↓
     * smallest sufficient cookie
     *       ↓
     * preserve bigger cookies
     *       ↓
     * more opportunities later
     *
     * Time complexity: O(n log n + m log m), where n is the number of children and m is the number of cookies (due to sorting).
     * Space complexity: O(1), as we are using a constant amount of extra space.
     * @param children an array of integers representing the greed factors of the children
     * @param cookies an array of integers representing the sizes of the cookies
     * @return the maximum number of children that can be satisfied
     */
    public static int findContentChildren(int[] children, int[] cookies) {

        // Sort both arrays so children go from least to most greedy
        // and cookies go from smallest to largest
        Arrays.sort(children);
        Arrays.sort(cookies);

        // child: index of the least greedy child who has not been satisfied yet
        // cookie: index of the smallest cookie that has not been tried yet
        int child = 0;
        int cookie = 0;

        // Stop when every child is satisfied or there are no cookies left
        while (child < children.length && cookie < cookies.length) {

            if (cookies[cookie] >= children[child]) {
                // This cookie satisfies the current child.
                // It is the smallest cookie that works, so the larger cookies
                // are saved for the greedier children who come next.
                child++;
            }
            // Otherwise the cookie is too small for this child. Every remaining child
            // is at least as greedy, so this cookie cannot satisfy anyone and is skipped.

            // Whether used or too small,
            // move to the next cookie
            cookie++;
        }

        // Each child before index `child` was given a cookie,
        // so `child` is the number of satisfied children
        return child;
    }

    /**
     * Driver code: runs {@link #findContentChildren(int[], int[])} against sample inputs and
     * prints the results.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        // childrenCases[i] and cookiesCases[i] together form test case i
        int[][] childrenCases = {
                {1, 2, 3},
                {1, 2, 4, 5},
                {10, 9, 8, 7},
                {2},
                {1, 2, 3}
        };
        int[][] cookiesCases = {
                {1, 1, 2, 3},
                {1, 2, 3, 5},
                {5, 6, 7, 8},
                {1},
                {3}
        };

        for (int i = 0; i < childrenCases.length; i++) {
            // Print the inputs before calling the method, because it sorts the arrays in place
            System.out.println((i + 1) + ".\tchildren = " + Arrays.toString(childrenCases[i])
                    + ", cookies = " + Arrays.toString(cookiesCases[i]));
            int result = findContentChildren(childrenCases[i], cookiesCases[i]);
            System.out.println("\tSatisfied children: " + result);
            // Print a separator line of 100 dashes
            System.out.println("-".repeat(100));
        }
    }
}

/**
 * How to Recognize This Pattern
 * In an interview, look for language like:
 * 1. maximize the number of matches
 * 2. assign resources
 * 3. minimum requirement
 * 4. smallest sufficient value
 * 5. each resource can be used once
 * 6. maximize/minimize something without reconsidering previous choices
 *
 * Mental Template:
 * 1. Sort
 * 2. Start with smallest requirement
 * 3. Find smallest resource satisfying it
 * 4. Use it
 * 5. Move forward
 * 6. Never go backward
 */