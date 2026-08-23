package org.educative.cip.m2.two_pointers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/*
 * Time Complexity: O(n) - We traverse the linked list once to find the length and then again to remove the nth node from the end.
 * Space Complexity: O(1) - We use a constant amount of space for pointers and
 */
public class RemoveNthNodeFromEndList {

    // Definition for a Linked List node
     static class ListNode {
         int val;
         ListNode next;

         // Constructor
         public ListNode(int val) {
             this.val = val;
             this.next = null;
         }
     }

    public static ListNode removeNthLastNode(ListNode head, int n) {

        // Replace this placeholder return statement with your code
        if (head == null) {
            return null;
        }
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode fast = dummy;
        ListNode slow = dummy;

        // Move fast n+1 steps ahead
        for (int i = 0; i <= n; i++) {
            fast = fast.next;
        }

        // Move fast to the end, maintaining the gap
        while (fast != null) {
            slow = slow.next;
            fast = fast.next;
        }

        // Optional: Clear the next pointer of the removed node
        ListNode remove = slow.next;

        // Skip the desired node
        slow.next = slow.next.next;
        remove.next=null;

        return dummy.next;
    }

    public ListNode buildList(int[] values) {
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        for (int val : values) {
            current.next = new ListNode(val);
            current = current.next;
        }
        return dummy.next;
    }

    public List<Integer> toList(ListNode head) {
        List<Integer> result = new ArrayList<>();
        ListNode current = head;
        while (current != null) {
            result.add(current.val);
            current = current.next;
        }
        return result;
    }

    public static void main(String[] args) {
        RemoveNthNodeFromEndList sol = new RemoveNthNodeFromEndList();
        int[][][] testCases = {
                {{10, 20, 30, 40, 50}, {1}},
                {{5, 10, 15}, {3}},
                {{7, 14, 21, 28}, {2}},
                {{0, 1, 2, 3, 4, 5, 6}, {4}},
                {{99, 50}, {2}},
        };
        for (int i = 0; i < testCases.length; i++) {
            int[] values = testCases[i][0];
            int n = testCases[i][1][0];
            ListNode head = sol.buildList(values);
            ListNode result = sol.removeNthLastNode(head, n);
            System.out.println((i + 1) + ".\tInput array: " + Arrays.toString(values));
            System.out.println("\tTarget: " + n);
            System.out.println("\tResult: " + sol.toList(result));
            System.out.println("-".repeat(100));
        }
    }

}
