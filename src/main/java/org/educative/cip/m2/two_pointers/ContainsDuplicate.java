package org.educative.cip.m2.two_pointers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Problem: Given an array of integers, find if the array contains any duplicates.
 * Your function should return true if any value appears at least twice in the array, and it should return false if every element is distinct.
 * Example 1:
 * Input: [1,2,3,1]
 * Output: true
 * Example 2:
 * Input: [1,2,3,4]
 * Output: false
 * Steps to solve the problem:
 * 1. Sort the array.
 * 2. Iterate through the array and check if the current element is equal to the previous element.
 * 3. If the current element is equal to the previous element, return true.
 * 4. If the loop completes without finding any duplicates, return false.
 * Time Complexity: O(nlogn)
 * Space Complexity: O(1)
 */
public class ContainsDuplicate {
    public static boolean containsDuplicate(List<Integer> lst) {
        Collections.sort(lst);
        for (int left = 0, right = 1; right < lst.size(); right++, left++) {
            if (lst.get(right).equals(lst.get(left))) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        List<Integer> lst = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 1));
        boolean result = containsDuplicate(lst);
        System.out.println("Contains Duplicate: " + (result ? "Yes" : "No"));
    }

}
