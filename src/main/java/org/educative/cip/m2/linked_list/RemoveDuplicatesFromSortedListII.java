package org.educative.cip.m2.linked_list;

import java.util.Arrays;
import java.util.List;

/**
 * Given the head node head of a singly linked list sorted in ascending order, remove every value that appears more
 * than once so that only values that occur exactly once remain in the list.
 * Return the head of the modified linked list.
 * Note: Nodes with duplicate values are removed entirely, not reduced to a single occurrence.
 * Constraints:
 * The number of nodes in the list is in the range [0, 300].
 * -100 <= Node.val <= 10
 * The linked list is guaranteed to be sorted in ascending order.
 */
public class RemoveDuplicatesFromSortedListII {
    /**
     * Represents a single node in a singly linked list.
     */
    static class ListNode {
        int val;
        ListNode next;

        /**
         * Creates a node with the specified value.
         *
         * @param val the node value
         */
        public ListNode(int val) {
            this.val = val;
            this.next = null;
        }
    }

    /**
     * Utility class for building a linked list from a collection of integer values.
     */
    static class LinkedList {
        ListNode head;

        /**
         * Creates an empty linked list.
         */
        public LinkedList() {
            head = null;
        }

        /**
         * Creates a linked list from an array of integer values.
         *
         * @param values the values to insert into the list in order
         */
        public LinkedList(int[] values) {
            head = null;
            createLinkedList(values);
        }

        /**
         * Creates a linked list from a list of integer values.
         *
         * @param values the values to insert into the list in order
         */
        public LinkedList(List<Integer> values) {
            head = null;
            createLinkedList(values);
        }

        /**
         * Builds the linked list from a list of integer values.
         *
         * @param values the values to insert into the list in order
         */
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

        /**
         * Builds the linked list from an array of integer values.
         *
         * @param values the values to insert into the list in order
         */
        private void createLinkedList(int[] values) {
            if (values.length == 0) {
                head = null;
                return;
            }

            head = new ListNode(values[0]);
            ListNode current = head;
            for (int i = 1; i < values.length; i++) {
                current.next = new ListNode(values[i]);
                current = current.next;
            }
        }
    }

    /**
     * Utility class for printing a linked list to standard output.
     */
    static class PrintList {
        /**
         * Displays the contents of the list in arrow notation.
         *
         * @param head the head node of the list to print
         */
        public static void display(ListNode head) {
            ListNode current = head;
            while (current != null) {
                System.out.print(current.val + " -> ");
                current = current.next;
            }
            System.out.println("None");
        }
    }

    /**
     * Removes all nodes with duplicate values from a sorted linked list.
     * Algorithm:
     * 1. Create a dummy node that points to the head of the list.
     * 2. Use two pointers: prev (initially pointing to the dummy) and current (initially pointing to the head).
     * 3. Traverse the list:
     *    - If current has a duplicate (current.val == current.next.val), skip all nodes with that value.
     *    - If current does not have a duplicate, move prev to current.
     * 4. Return dummy.next as the new head of the modified list.
     * @param head the head node of the sorted linked list
     * @return the head node of the modified list with duplicates removed
     */
    public static ListNode deleteDuplicates(ListNode head) {
        if (head == null) {
            return null;
        }

        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prev = dummy;

        while (head != null) {
            // Check if the current node has duplicates
            if (head.next != null && head.val == head.next.val) {
                // Duplicates - Skip all nodes with the same value
                while (head.next != null && head.val == head.next.val) {
                    // Skip all nodes with the same value
                    head = head.next;
                }
                prev.next = head.next;
            } else {
                prev = prev.next;
            }
            head = head.next;
        }

        return dummy.next;
    }

    public static void main(String[] args) {
        List<List<Integer>> inputList = Arrays.asList(
                Arrays.asList(),
                Arrays.asList(1, 1),
                Arrays.asList(-3, -2, -2, -1, 0, 0, 1),
                Arrays.asList(0, 1, 1, 2, 3, 3, 4),
                Arrays.asList(-1, 0, 1, 2, 3)
        );

        for (int i = 0; i < inputList.size(); i++) {
            LinkedList inputLinkedList = new LinkedList(inputList.get(i));

            System.out.print((i + 1) + ".\tInput: ");
            PrintList.display(inputLinkedList.head);

            System.out.print("\n\tOutput: ");
            inputLinkedList.head = deleteDuplicates(inputLinkedList.head);
            PrintList.display(inputLinkedList.head);

            System.out.println(new String(new char[100]).replace('\0', '-'));
        }
    }

}
