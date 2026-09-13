package org.educative.cip.m2.linked_list;

import java.util.Arrays;
import java.util.List;

/**
 * Rotate List
 * Problem: Given the head of a linked list, rotate the list to the right by k places.
 *          Rotate the list in such a way that the last k nodes are moved to the front of the list.
 *          Return the head of the rotated list.
 * Example 1:
 * Input: head = [1,2,3,4,5], k = 2
 * Output: [4,5,1,2,3]
 * Example 2:
 * Input: head = [0,1,2], k = 4
 * Output: [2,0,1]
 * Constraints:
 * The number of nodes in the list is in the range [0, 500].
 * -100 <= Node.val <= 100
 * 0 <= k <= 2 * 10^9
 *
 * Time Complexity: O(n), where n is the number of nodes in the linked list. Each node is visited at most once.
 * Space Complexity: O(1), as we are using a constant amount of extra space for pointers and counters.
 *
 */
public class RotateList {
    /**
     * Represents a single node in a singly linked list.
     */
    static class ListNode {
        int val;
        ListNode next;

        // Constructor
        public ListNode(int val) {
            this.val = val;
            this.next = null;
        }
    }

    /**
     * Represents a singly linked list.
     */
    static class LinkedList {
        ListNode head;

        // Default constructor
        public LinkedList() {
            head = null;
        }

        // Constructor to initialize from a list of values
        public LinkedList(int[] values) {
            head = null;
            createLinkedList(values);
        }

        // Constructor to initialize from a list of values
        public LinkedList(List<Integer> values) {
            head = null;
            createLinkedList(values);
        }

        // Function to create a linked list from a list of values
        private void createLinkedList(List<Integer> values) {
            int[] array = values.stream().mapToInt(Integer::intValue).toArray();
            createLinkedList(array);
        }

        // Function to create a linked list from an array of values
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
     * Utility class to print the linked list.
     */
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

    /**
     * Rotates the linked list to the right by k places.
     * Steps:
     * 1. If the list is empty, has one node, or no rotation is needed, return the head as is.
     * 2. Compute the length of the list.
     * 3. Make the list circular by connecting the tail to the head.
     * 4. Find the new head and tail based on the number of rotations.
     * 5. Break the circular link to finalize the rotation.
     * 6. Return the new head of the rotated list.
     *
     * @param head The head of the linked list.
     * @param k    The number of places to rotate the list.
     * @return The head of the rotated linked list.
     */
    public ListNode rotateRight(ListNode head, int k) {
        if (head == null || head.next == null || k == 0) {
            // If the list is empty, has one node, or no rotation is needed, return the head as is.
            return head;
        }

        // Compute the length of the list
        int length = 1;
        ListNode tail = head;
        while (tail.next != null) {
            tail = tail.next;
            length++;
        }

        // Make the list circular
        tail.next = head;

        // Find the new head and tail
        k = k % length;
        int stepsToNewHead = length - k;
        ListNode newTail = tail;
        // Move to the new tail position
        while (stepsToNewHead-- > 0) {
            newTail = newTail.next;
        }
        // The new head is the node after the new tail
        ListNode newHead = newTail.next;
        // Break the circular link to finalize the rotation
        newTail.next = null;

        return newHead;
    }

    /**
     * Rotates the linked list to the left by k places.
     * Steps:
     * 1. If the list is empty, has one node, or no rotation is needed, return the head as is.
     * 2. Compute the length of the list.
     * 3. Make the list circular by connecting the tail to the head.
     * 4. Find the new head and tail based on the number of rotations.
     * 5. Break the circular link to finalize the rotation.
     * 6. Return the new head of the rotated list.
     *
     * @param head The head of the linked list.
     * @param k    The number of places to rotate the list.
     * @return The head of the rotated linked list.
     */
    public ListNode rotateLeft(ListNode head, int k) {
        if (head == null || head.next == null || k == 0) {
            // If the list is empty, has one node, or no rotation is needed, return the head as is.
            return head;
        }

        // Compute the length of the list
        int length = 1;
        ListNode tail = head;
        while (tail.next != null) {
            tail = tail.next;
            length++;
        }

        // Make the list circular
        tail.next = head;

        // Find the new head and tail
        k = k % length;
        int stepsToNewHead = k;
        ListNode newTail = tail;
        while (stepsToNewHead-- > 0) {
            newTail = newTail.next;
        }
        ListNode newHead = newTail.next;
        newTail.next = null;

        return newHead;
    }

    public static void main(String[] args) {
        RotateList sol = new RotateList();

        List<List<Integer>> inputLists = Arrays.asList(
                Arrays.asList(1, 1, 2, 2, 3, -1, 10, 12),
                Arrays.asList(8, 3, 6, 1, 7),
                Arrays.asList(10, 20, -22, 21, -12),
                Arrays.asList(1, 1, 1),
                Arrays.asList(-2, -5, -6, 0, -1, -4),
                Arrays.asList(3, 1, 5, 7, -4, -2, -1, -6)
        );

        List<Integer> kList = Arrays.asList(3, 3, 2, 1, 4, 7);

        for (int i = 0; i < inputLists.size(); i++) {
            LinkedList obj = new LinkedList(inputLists.get(i));
            int k = kList.get(i);

            System.out.print((i + 1) + ".\tOriginal list: ");
            PrintList.display(obj.head);

            obj.head = sol.rotateRight(obj.head, k);

            System.out.print("\tAfter rotating right by k=" + k + ": ");
            PrintList.display(obj.head);

            obj.head = sol.rotateLeft(obj.head, k);

            System.out.print("\tAfter rotating left by k=" + k + ": ");
            PrintList.display(obj.head);

            if (i != inputLists.size() - 1) {
                System.out.println(new String(new char[100]).replace('\0', '-'));
            }
        }
    }


}
