package org.educative.cip.m2.two_pointers;

import java.util.*;

/**
 * Time Complexity: O(n + m), where n is the length of version1 and m is the length of version2.
 * We traverse through both version strings once.
 * Space Complexity: O(n + m), as we are using lists to store the revisions of both versions.
 * Given two version numbers, version1 and version2, compare them.
 * If version1 > version2 return 1, if version1 < version2 return -1, otherwise return 0.
 */

public class VersionNameComparision
{
    public int compareVersionV2(String version1, String version2) {
        String[] revs1 = version1.split("\\.");
        String[] revs2 = version2.split("\\.");

        int p1 = 0;
        int p2 = 0;

        int maxLen = Math.max(revs1.length, revs2.length);

        while (p1 < maxLen || p2 < maxLen) {
            int val1 = p1 < revs1.length ? Integer.parseInt(revs1[p1]) : 0;
            int val2 = p2 < revs2.length ? Integer.parseInt(revs2[p2]) : 0;

            if (val1 < val2) {
                return -1;
            } else if (val1 > val2) {
                return 1;
            }

            p1 += 1;
            p2 += 1;
        }

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
        VersionNameComparision solution = new VersionNameComparision();

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