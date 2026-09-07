package org.educative.cip.m2.two_pointers;

import java.util.*;

/*
    * Problem: Partition Labels
    *
    * Given a string s, partition the string into as many parts as possible so that each letter appears in at most one part.
    * Return a list of integers representing the size of these parts.
    *
    * Example 1:
    * Input: s = "ababcbacadefegdehijhklij"
    * Output: [9,7,8]
    * Explanation:
    * The partition is "ababcbaca", "defegde", "hijhklij".
    * This is a partition so that each letter appears in at most one part.
    * A partition like "ababcbacadefegde", "hijhklij" is incorrect, because it splits s into less parts.
    *
    * Example 2:
    * Input: s = "eccbbbbdec"
    * Output: [10]
    *
    * Constraints:
    * 1 <= s.length <= 500
    * s consists of lowercase English letters.
    *
    * Steps to solve the problem:
    * 1. Create an array to store the last occurrence of each character in the string
    * 2. Iterate through the string and update the end of the current partition based on the last occurrence of the characters seen so far
    * 3. When the current index reaches the end of the partition, record the size of the partition and start a new partition
    * 4. Return the list of partition sizes
    *
    * Time Complexity: O(n), where n is the length of the string s. We traverse the string twice:
    *                  once to record the last occurrences and once to determine the partitions.
    * Space Complexity: O(1), since the last occurrence array has a fixed size of 26 (for each letter of the alphabet),
    *                         and the output list will contain at most 26 elements.
    *
 */
public class PartitionLabels
{
    /*
     * Function to partition the string into as many parts as possible so that each letter appears in at most one part.
     * Steps:
     * 1. Create an array to store the last occurrence of each character in the string.
     * 2. Iterate through the string to mark the last occureance index value int the lastOccurance[] Array.
     * 3. Iterate through the string and update the end of the current partition based on the last occurrence of the characters seen so far
     * 4. When the current index reaches the end of the partition, record the size of the partition and start a new partition
     * 5. Return the list of partition sizes
     */
    public List<Integer> partitionLabels(String s)
    {
        // Step 1: Create an array to store the last occurrence of each character in the string
        int[] lastOccurrence = new int[26]; // Array to store the last occurrence of each character
        // Step 2: Fill the last occurrence array based on the last index of each character in the string
        for (int i = 0; i < s.length(); i++) {
            lastOccurrence[s.charAt(i) - 'a'] = i;
        }

        int partitionEnd = 0; // Variable to track the end of the current partition
        int partitionStart = 0; // Variable to track the start of the current partition
        List<Integer> partitionSizes = new ArrayList<>(); // List to store the sizes of the partitions

        // Step 3: Traverse the string to form partitions
        for (int i = 0; i < s.length(); i++) {
            // Update the end of the current partition based on the last occurrence of the characters seen so far
            partitionEnd = Math.max(partitionEnd, lastOccurrence[s.charAt(i) - 'a']);
            // Step 4: If the current index reaches the end of the partition, record the size of the partition and start a new partition
            if (i == partitionEnd) {
                // Add the size of the current partition to the list
                int partitionSize = i - partitionStart + 1;
                partitionSizes.add(partitionSize);
                // Start a new partition
                partitionStart = i + 1;
            }
        }
        // Step 5: Return the list of partition sizes
        return partitionSizes;
    }

    // Driver code
    public static void main(String[] args) {
        List<String> strings = Arrays.asList(
                "ababcbacadefegdehijhklij",
                "eccbbbbdec",
                "caedbdedda",
                "abcdef",
                "bcbcdd"
        );

        int i = 0;
        for (String s : strings) {
            PartitionLabels obj=new PartitionLabels();
            System.out.println((i + 1) + ".\ts: " + s);
            List<Integer> result = obj.partitionLabels(s);
            System.out.println("\n\tPartition sizes: " + result);
            System.out.println("----------------------------------------------------------------------------------------------------");
            i++;
        }
    }
}