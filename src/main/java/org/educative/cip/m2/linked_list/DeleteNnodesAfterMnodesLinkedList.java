package org.educative.cip.m2.linked_list;

import java.util.Arrays;
import java.util.List;

/**
 * Deletes N nodes after every M nodes in a sorted-or-unspecified singly linked list.
 * This problem requires skipping the first M nodes, deleting the next N nodes,
 * and continuing the pattern until the end of the list.
 *
 * <p>Example 1:
 * Input: M = 2, N = 2
 * Linked List: 1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7 -> 8
 * Output: 1 -> 2 -> 5 -> 6
 *
 * <p>Example 2:
 * Input: M = 1, N = 1
 * Linked List: 1 -> 2 -> 3 -> 4 -> 5
 * Output: 1 -> 3 -> 5
 *
 * <p>Example 3:
 * Input: M = 3, N = 2
 * Linked List: 1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7 -> 8 -> 9
 * Output: 1 -> 2 -> 3 -> 6 -> 7 -> 8
 *
 * <p>Constraints:
 * The number of nodes in the linked list is in the range [1, 10^5].
 * 1 <= M, N <= 10^5
 * 0 <= Node.val <= 10^5
 *
 * Time Complexity: O(n), where n is the number of nodes in the linked list. Each node is visited at most once.
 * Space Complexity: O(1), as we are using a constant amount of extra space for pointers and counters.
 */
public class DeleteNnodesAfterMnodesLinkedList {
    /**
     * Represents a single node in a singly linked list.
     */
    static class ListNode {
       int val;
       ListNode next;

       /**
        * Creates a new node with the specified value.
        *
        * @param val the value stored in the node
        */
       public ListNode(int val) {
           this.val = val;
           this.next = null;
       }
    }

    /**
     * Represents a singly linked list backed by a head node.
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
        * Creates a linked list from the provided values.
        *
        * @param values the node values to insert in order
        */
       public LinkedList(List<Integer> values) {
           head = null;
           createLinkedList(values);
       }

       /**
        * Builds the linked list from a list of integer values.
        *
        * @param values the node values to insert in order
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

    }

    /**
     * Utility class for printing the contents of a linked list.
     */
    static class PrintList {
       /**
        * Prints the linked list in arrow notation.
        *
        * @param head the head node of the list to display
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
     * Deletes N nodes after every M nodes in the linked list and returns the updated head.
     * The algorithm advances through the list in repeated phases: skip M nodes, delete N nodes,
     * and continue until the end of the list is reached.
     *
     * @param head the head of the linked list
     * @param m the number of nodes to skip before each deletion cycle
     * @param n the number of nodes to delete after each skip cycle
     * @return the head of the modified linked list
     */
    public static ListNode deleteNodes(ListNode head, int m, int n) {

        // Replace this placeholder return statement with your code
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode current = head;
        ListNode prev = dummy;
        int count = 0;
        int skipCount = 0;
        while (current != null) {
            if (count < m) {
                prev = current;
                current = current.next;
                count++;
            } else if (skipCount < n) {
                current = current.next;
                skipCount++;
            } else {
                prev.next = current;
                count = 0;
                skipCount = 0;
            }
        }
        if (skipCount > 0) {
            prev.next = null;
        }

        return dummy.next;
    }

    // Driver code
    public static void main(String[] args) {
        List<List<Integer>> inputLists = Arrays.asList(
                Arrays.asList(5),
                Arrays.asList(1, 2, 2, 3, 3, 3),
                Arrays.asList(3, 7, 9),
                Arrays.asList(10, 10, 100, 100, 100),
                Arrays.asList(7, 7, 7, 7, 77, 77, 77, 77)
        );

        int[] allMs = {1, 3, 1, 4, 5};
        int[] allNs = {1, 1, 3, 2, 7};

        for (int i = 0; i < inputLists.size(); i++) {
            LinkedList inputLinkedList = new LinkedList(inputLists.get(i));

            System.out.println((i + 1) + ".\tInput:");
            System.out.print("\t");
            PrintList.display(inputLinkedList.head);
            System.out.println("\tm = " + allMs[i]);
            System.out.println("\tn = " + allNs[i]);

            inputLinkedList.head = deleteNodes(inputLinkedList.head, allMs[i], allNs[i]);
            System.out.println("\n\tOutput:");
            System.out.print("\t");
            PrintList.display(inputLinkedList.head);
            System.out.println(new String(new char[100]).replace('\0', '-'));
        }
    }

}
