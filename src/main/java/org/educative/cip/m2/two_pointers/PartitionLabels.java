package org.educative.cip.m2.two_pointers;

import java.util.*;

public class PartitionLabels
{

    public List<Integer> partitionLabels(String s)
    {
        int[] lastOccurrence = new int[26];
        for (int i = 0; i < s.length(); i++) {
            lastOccurrence[s.charAt(i) - 'a'] = i;
        }

        int partitionEnd = 0;
        int partitionStart = 0;
        List<Integer> partitionSizes = new ArrayList<>();

        for (int i = 0; i < s.length(); i++) {
            partitionEnd = Math.max(partitionEnd, lastOccurrence[s.charAt(i) - 'a']);

            if (i == partitionEnd) {
                partitionSizes.add(i - partitionStart + 1);
                partitionStart = i + 1;
            }
        }

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