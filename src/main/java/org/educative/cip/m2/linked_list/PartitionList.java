package org.educative.cip.m2.linked_list;

import java.util.*;

/**
 * Given the head of a linked list and a value x, partition it such that all nodes less than x
 * come before nodes greater than or equal to x.
 * You should preserve the original relative order of the nodes in each of the two partitions.
 * Time Complexity: O(n), where n is the number of nodes in the linked list. We traverse the list once.
 * Space Complexity: O(1), as we are using a constant amount of extra space for
 */
public class PartitionList {
    /**
     * Definition for singly-linked list.
     * public class ListNode {
     *     int val;
     *     ListNode next;
     *     ListNode() {}
     *     ListNode(int val) { this.val = val; }
     *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
     * }
     */
    public static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    public ListNode partition(ListNode head, int x) {
        // Dummy heads for < x and >= x partitions
        ListNode lessDummy = new ListNode(0);
        ListNode greaterDummy = new ListNode(0);

        // Tails to build the two lists in original relative order
        ListNode lessTail = lessDummy;
        ListNode greaterTail = greaterDummy;

        // Traverse and split nodes into two stable partitions
        ListNode curr = head;
        while (curr != null) {
            ListNode nextNode = curr.next; // Save next pointer
            curr.next = null;              // Detach current node to avoid cycles

            if (curr.val < x) {
                lessTail.next = curr;
                lessTail = lessTail.next;
            } else {
                greaterTail.next = curr;
                greaterTail = greaterTail.next;
            }
            curr = nextNode;
        }

        // Connect the two partitions
        lessTail.next = greaterDummy.next;

        // Return new head
        return lessDummy.next;
    }

    private static ListNode buildLinkedList(int[] values) {
        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;
        for (int v : values) {
            tail.next = new ListNode(v);
            tail = tail.next;
        }
        return dummy.next;
    }

    private static List<Integer> linkedListToList(ListNode head) {
        List<Integer> result = new ArrayList<>();
        ListNode curr = head;
        while (curr != null) {
            result.add(curr.val);
            curr = curr.next;
        }
        return result;
    }

    private static String listToString(List<Integer> v) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < v.size(); i++) {
            if (i > 0) sb.append(", ");
            sb.append(v.get(i));
        }
        sb.append("]");
        return sb.toString();
    }

    private static String arrayToString(int[] a) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < a.length; i++) {
            if (i > 0) sb.append(", ");
            sb.append(a[i]);
        }
        sb.append("]");
        return sb.toString();
    }

    public static void main(String[] args) {
        int[][] heads = new int[][]{
                new int[]{3, 1, 2},
                new int[]{},
                new int[]{0, -1, 5, -2, 3},
                new int[]{5, 4, 3, 2, 1},
                new int[]{2, 2, 2, 1, 1, 3}
        };
        int[] xs = new int[]{3, 0, 1, 3, 2};

        PartitionList sol = new PartitionList();

        for (int i = 0; i < heads.length; i++) {
            int[] headArr = heads[i];
            int x = xs[i];

            ListNode headNode = buildLinkedList(headArr);
            ListNode resultHead = sol.partition(headNode, x);
            List<Integer> resultList = linkedListToList(resultHead);

            System.out.println((i + 1) + ".\thead = " + arrayToString(headArr));
            System.out.println("\tx = " + x);
            System.out.println("\n\tOutput: " + listToString(resultList));
            System.out.println("-".repeat(100));
        }
    }
}