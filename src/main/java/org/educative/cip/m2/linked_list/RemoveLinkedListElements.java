package org.educative.cip.m2.linked_list;

import java.util.Arrays;
import java.util.List;

/**
 * Remove Linked List Elements
 * Problem: Given the head of a linked list and an integer val,
 *          remove all the nodes of the linked list that has Node.val == val, and return the new head.
 * Example 1:
 * Input: head = [1,2,6,3,4,5,6], val = 6
 * Output: [1,2,3,4,5]
 * Example 2:
 * Input: head = [], val = 1
 * Output: []
 * Example 3:
 * Input: head = [7,7,7,7], val = 7
 * Output: []
 * Constraints:
 * The number of nodes in the list is in the range [0, 10^4].
 * 1 <= Node.val <= 50
 * 0 <= val <= 50
 *
 * Steps to solve the problem:
 * 1. Create a dummy node that points to the head of the linked list.
 * 2. Initialize a current pointer to the head node. Initialize a previous pointer to the dummy node.
 * 3. Traverse the linked list using the current pointer.
 * 4. For each node, check if the next node's value is equal to val.
 * 5. If it is, skip the next node by setting previous.next to current.next. Move the current pointer to the current.next node.
 * 6. If it is not, move the previous pointer to the current node and move the current pointer to the current.next node.
 * 7. Continue this process until the end of the linked list is reached.
 * 8. Return dummy.next as the new head of the modified linked list.
 *
 * Time Complexity: O(n), where n is the number of nodes in the linked list.
 * Space Complexity: O(1), as we are using a constant amount of extra space.
 */
public class RemoveLinkedListElements {
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

    /**
     * Utility class to print the linked list.
     */
    class PrintList{
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
     * Removes all nodes with the specified value from the linked list.
     *
     * @param head the head of the linked list
     * @param k    the value to remove
     * @return the new head of the modified linked list
     */
    public static ListNode removeElements(ListNode head, int k) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode prev = dummy, curr = head;

        while (curr != null) {
            if (curr.val == k) {
                prev.next = curr.next;
                curr = curr.next;
            } else {
                prev = curr;
                curr = curr.next;
            }
        }

        return dummy.next;
    }

    public static void main(String[] args) {
        List<List<Integer>> lists = Arrays.asList(
                Arrays.asList(9, 7, 8, 7, 7, 6),
                Arrays.asList(2, 3, 5, 7, 11),
                Arrays.asList(4, 4, 4, 4, 4),
                Arrays.asList(1, 2, 3, 11, 22, 33),
                Arrays.asList(1, 2, 6, 3, 4, 5, 6)
        );

        int[] ks = {7, 8, 4, 3, 6};

        for (int i = 0; i < lists.size(); i++) {
            LinkedList inputLinkedList = new LinkedList(lists.get(i));

            System.out.print((i + 1) + ".\tLinked list: ");
            PrintList.display(inputLinkedList.head);

            System.out.println("\n\tk: " + ks[i]);

            System.out.print("\tLinked list after removing elements: ");
            ListNode res = removeElements(inputLinkedList.head, ks[i]);
            PrintList.display(res);
            System.out.println("\n");
            System.out.println(new String(new char[100]).replace('\0', '-'));
        }
    }

}
