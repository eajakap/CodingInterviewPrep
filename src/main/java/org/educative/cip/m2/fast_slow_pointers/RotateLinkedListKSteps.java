package org.educative.cip.m2.fast_slow_pointers;

import java.util.Arrays;
import java.util.List;

public class RotateLinkedListKSteps {
    static class ListNode {
        int val;
        ListNode next;

        // Constructor
        public ListNode(int val) {
            this.val = val;
            this.next = null;
        }
    }

    static class LinkedList {
        ListNode head;

        // Default constructor
        public LinkedList() {
            head = null;
        }

        // Constructor to initialize from a list of values
        public LinkedList(List<Integer> values) {
            head = null;
            createLinkedList(values);
        }

        // Function to create a linked list from a list of values
        private void createLinkedList(List<Integer> values) {
            if (values.isEmpty()) {
                head = null;
                return;
            }

            head = new ListNode(values.get(0));
            ListNode current = head;
            for (int i = 1; i < values.size(); i++) {
                current.next = new ListNode(values.get(i));
                current = current.next;
            }
        }
    }

    static class PrintList{
        // Function to display the linked list
        public static void display(ListNode head) {
            ListNode current = head;
            while (current != null) {
                System.out.print(current.val + " -> ");
                current = current.next;
            }
            System.out.println("None");
        }
    }

    public ListNode rotateRight(ListNode head, int k) {
        if (head == null || head.next == null || k == 0) return head;

        // Try to put fast k nodes ahead of head. If the list ends first,
        // the attempt wasn't wasted: we've just counted it, n = walked + 1.
        ListNode fast = head;
        int walked = 0;
        while (walked < k && fast.next != null) {
            fast = fast.next;
            walked++;
        }

        int shift = k;
        if (walked < k) {                    // k >= n, so reduce and redo the gap
            int n = walked + 1;            // total number of nodes in the list
            shift = k % n;                   // shift is now in the range [0, n-1]
            if (shift == 0) return head;     // no mutation has happened yet
            fast = head;
            for (int i = 0; i < shift; i++) {
                fast = fast.next;            // fast now sits at index `shift`
            }
        }
        // Invariant on both paths: fast is at index `shift`, with 0 < shift < n.

        ListNode slow = head;                // slow at index 0
        while (fast.next != null) {          // advance until fast is at index n-1
            fast = fast.next;
            slow = slow.next;                // gap is preserved, so slow ends at n-1-shift
        }

        ListNode newHead = slow.next;
        slow.next = null;                    // slow is the new tail
        fast.next = head;                    // old tail joins to old head
        return newHead;
    }

    // Driver code
    public static void main(String[] args) {
        List<List<Integer>> inputs = Arrays.asList(
                Arrays.asList(1, 2, 3, 4, 5),
                Arrays.asList(1, 2, 3, 4, 5, 6),
                Arrays.asList(3, 2, 1),
                Arrays.asList(10),
                Arrays.asList(1, 2)
        );

        int[] ks = {2, 3, 4, 0, 1};
        RotateLinkedListKSteps solution = new RotateLinkedListKSteps();

        for (int i = 0; i < inputs.size(); i++) {
            LinkedList inputLinkedList = new LinkedList(inputs.get(i));
            int k = ks[i];

            System.out.println((i + 1) + ". Input linked list: ");
            PrintList.display(inputLinkedList.head);
            System.out.println("   k = " + k);
            ListNode rotatedHead = solution.rotateRight(inputLinkedList.head, k);
            System.out.print("   Rotated linked list: ");
            PrintList.display(rotatedHead);
            System.out.println(new String(new char[100]).replace('\0', '-'));
        }
    }

}
