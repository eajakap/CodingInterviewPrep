package org.educative.cip.m2.two_pointers;

import java.util.*;

/**
 * Problem: Compare Version Numbers
 * Given two version numbers, version1 and version2, compare them.
 * If version1 > version2 return 1, if version1 < version2 return -1, otherwise return 0.
 * You may assume that the version strings are non-empty and contain only digits and the '.' character.
 * The '.' character does not represent a decimal point and is used to separate number sequences.
 * For instance, "2.5" is not "two and a half" or "half way to version three", it is the fifth
 * second-level revision of the second first-level revision.
 * You may assume the default revision number for each level of a version number to be 0.
 * For example, version number "3.4" has a revision number of 3 and 4 for its first and
 * second level revision number. Its third and fourth level revision number are both 0.
 * Steps to solve the problem:
 * 1. Split both version strings by the '.' character to get the individual revision numbers.
 * 2. Initialize two pointers to traverse through the revision numbers of both versions.
 * 3. Compare the revision numbers at the current pointers.
 * 4. If the revision numbers are not equal, return 1 or -1 based on which version is greater.
 * 5. If the revision numbers are equal, move both pointers to the next revision number.
 * 6. If one version has more revision numbers than the other, treat the missing revision numbers as 0.
 * 7. Continue the comparison until all revision numbers have been compared.
 * 8. If all revision numbers are equal, return 0.
 * 9. Return the result of the comparison.
 *
 * Time Complexity: O(n + m), where n is the length of version1 and m is the length of version2.
 * We traverse through both version strings once.
 * Space Complexity: O(n + m), as we are using lists to store the revisions of both versions.
 */

public class CompareVersionNumber
{
    public int compareVersionV2(String version1, String version2) {
        String[] revs1 = version1.split("\\."); // Split version1 into its revision numbers
        String[] revs2 = version2.split("\\."); // Split version2 into its revision numbers

        int p1 = 0; // Pointer for traversing revs1
        int p2 = 0; // Pointer for traversing revs2

        int maxLen = Math.max(revs1.length, revs2.length); // Determine the maximum length of the two revision arrays

        // Traverse through both revision arrays and compare their values
        while (p1 < maxLen || p2 < maxLen) {
            // Compare the revision numbers of both versions
            int val1 = p1 < revs1.length ? Integer.parseInt(revs1[p1]) : 0;
            int val2 = p2 < revs2.length ? Integer.parseInt(revs2[p2]) : 0;

            if (val1 < val2) {
                return -1;
            } else if (val1 > val2) {
                return 1;
            }
            // Move to the next revision number in both versions
            p1 += 1;
            p2 += 1;
        }
        // If all revision numbers are equal, return 0
        return 0;
    }

    public int compareVersion(String version1, String version2)
    {
        // Replace this placeholder return statement with your code
        List<String> version1_Revisions = Arrays.asList(version1.split("\\."));
        List<String> version2_Revisions = Arrays.asList(version2.split("\\."));
        int version1_start = 0;
        int version2_start = 0;
        int version1_size = version1_Revisions.size();
        int version2_size = version2_Revisions.size();

        while (version1_start < version1_size || version2_start < version2_size) {
            int version1_element = version1_start < version1_size ?
                    Integer.valueOf(version1_Revisions.get(version1_start)) : 0;
            int version2_element = version2_start < version2_size ?
                    Integer.valueOf(version2_Revisions.get(version2_start)) : 0;
            if (version1_element > version2_element) {
                return 1;
            } else if (version1_element < version2_element) {
                return -1;
            } else {
                version1_start++;
                version2_start++;
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        CompareVersionNumber solution = new CompareVersionNumber();

        String[][] testCases = {
                {"0.1", "1.1"},
                {"1.0.1", "1"},
                {"7.5.2.4", "7.5.3"},
                {"1.0.0", "1"},
                {"2.0.0.1", "2.0.0.2"},
        };

        for (int i = 0; i < testCases.length; i++) {
            String v1 = testCases[i][0];
            String v2 = testCases[i][1];
            int result = solution.compareVersion(v1, v2);
            System.out.println((i + 1) + ".\tInput array: [\"" + v1 + "\", \"" + v2 + "\"]");
            System.out.println("\tResult: " + result);
            System.out.println("-".repeat(100));
        }
    }

}