package org.educative.cip.m2.linked_list;

import java.util.Arrays;
import java.util.List;

/**
 * Remove Duplicates from Sorted List
 * Problem: Given the head of a sorted linked list, delete all duplicates such that each element appears only once. Return the linked list sorted as well.
 * Example 1:
 * Input: head = [1,1,2]
 * Output: [1,2]
 * Example 2:
 * Input: head = [1,1,2,3,3]
 * Output: [1,2,3]
 * Constraints:
 * The number of nodes in the list is in the range [0, 300].
 * -100 <= Node.val <= 100
 * The list is guaranteed to be sorted in ascending order.
 *
 * Steps to solve the problem:
 * 1. Initialize a pointer, current, to the head of the linked list.
 * 2. While current is not null and current.next is not null:
 *    a. If current.val is equal to current.next.val, it means we have found a duplicate.
 *       In this case, we skip the next node by setting current.next to current.next.next.
 *    b. If current.val is not equal to current.next.val, it means we have found a unique value.
 *       In this case, we move the current pointer to the next node by setting current to current.next.
 * 3. Return the head of the modified linked list.
 *
 * Time Complexity: O(n), where n is the number of nodes in the linked list.
 *                  We traverse the list once, making a constant-time comparison for each node.
 * Space Complexity: O(1), as we are using a constant amount of extra space for the current pointer and
 *                   not using any additional data structures.
 */
public class RemoveDuplicatesFromSortedList {
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
     * Removes all duplicate values from a sorted linked list while preserving the list order.
     * Each value appears at most once in the returned list.
     *
     * @param head the head of the sorted linked list
     * @return the head of the deduplicated list
     */
    public static ListNode removeDuplicates(ListNode head)
    {
        ListNode current = head;
        while (current!=null && current.next!=null) {
            if (current.val != current.next.val) {
                current = current.next;
            } else {
                current.next = current.next.next;
            }
        }
        return head;
    }

    // Driver code
    public static void main(String[] args) {
        List<List<Integer>> inputList = Arrays.asList(
                Arrays.asList(1, 2, 2, 3, 3, 3),
                Arrays.asList(-21, -21, -21, -21, -21, -21, -21),
                Arrays.asList(3, 7, 9),
                Arrays.asList(-100, -100, -100, -10, -10, 0, 10, 10, 100, 100, 100),
                Arrays.asList(-77, -77, -7, -7, -7, -7, 7, 7, 7, 7, 77, 77, 77, 77)
        );

        for (int i = 0; i < inputList.size(); i++) {
            LinkedList inputLinkedList = new LinkedList(inputList.get(i));

            System.out.print((i + 1) + ".\tInput: ");
            PrintList.display(inputLinkedList.head);

            System.out.print("\n\tOutput: ");
            inputLinkedList.head = removeDuplicates(inputLinkedList.head);
            PrintList.display(inputLinkedList.head);

            System.out.println(new String(new char[100]).replace('\0', '-'));
        }
    }

}
