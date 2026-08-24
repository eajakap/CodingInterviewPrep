package org.educative.cip.m2.two_pointers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
/*
 * Time Complexity: O(n log n) - Sorting the list takes O(n log n) time, and the two-pointer traversal takes O(n) time.
 * Space Complexity: O(1) - We use a constant amount of space for pointers and counters.
 */
public class CountPairs {

    public static int countPairs(List<Integer> nums, int target) {
        Collections.sort(nums);
        int count = 0;
        int low = 0, high = nums.size() - 1;

        while (low < high) {
            if (nums.get(low) + nums.get(high) < target) {
                count += (high - low);
                low++;
            } else {
                high--;
            }
        }

        return count;
    }

    public static void main(String[] args) {
        List<List<Integer>> testCases = Arrays.asList(
                Arrays.asList(10, 1, 6, 2, 3, 8),
                Arrays.asList(1, 3, 5, 7),
                Arrays.asList(1, 2, 3, 6),
                Arrays.asList(2, 4, 6, 8, 10),
                Arrays.asList(5, 1, 9, 2)
        );
        List<Integer> targets = Arrays.asList(9, 8, 6, 12, 10);

        for (int i = 0; i < testCases.size(); i++) {
            List<Integer> nums = testCases.get(i);
            int target = targets.get(i);
            System.out.println((i + 1) + "\tnums: " + nums);
            System.out.println("\ttarget: " + target);

            int result = countPairs(nums, target);
            System.out.println("\n\tNumber of valid pairs: " + result);
            System.out.println(new String(new char[100]).replace('\0', '-'));
        }
    }
}