package org.educative.cip.m2.linked_list;

import java.util.Arrays;
import java.util.List;

/**
 * This class provides a solution to reverse a sublist of a singly linked list between two given positions.
 * It includes the definition of the ListNode and LinkedList classes, as well as a method to reverse the sublist.
 *
 * Problem: Given the head of a singly linked list and two 1-based positions left and right (left <= right),
 * reverse the nodes from position left to position right and return the updated list.
 * Example: [1,2,3,4,5], left = 2, right = 4 -> [1,4,3,2,5]
 *
 * Approach: One-pass head insertion. Walk prev to the node before position left, then repeatedly move the node
 * after curr to the front of the sublist (right - left times).
 *
 * Time Complexity: O(n) - a single traversal of the list.
 * Space Complexity: O(1) - the list is reversed in place using a few pointers.
 */
public class ReverseSingleLinkedListII {
    /**
     * Represents a single node in a singly linked list.
     */
    static class ListNode {
        int val;
        ListNode next;

        /**
         * Creates a node with the given value and no successor.
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
         * Creates a linked list from the given values, preserving their order.
         *
         * @param values the values to insert into the list in order
         */
        public LinkedList(List<Integer> values) {
            head = null;
            createLinkedList(values);
        }

        /**
         * Builds the linked list from a list of values.
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

    }

    /**
     * Utility class for printing a linked list to standard output.
     */
    static class PrintList{
        /**
         * Prints the list in arrow notation, ending with "None".
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
     * Holds the solution and driver code for the problem.
     */
    static class Solution {

        /**
         * Reverses the nodes of the list between positions left and right (1-based, inclusive).
         *
         * @param head  the head of the singly linked list
         * @param left  the position of the first node to reverse
         * @param right the position of the last node to reverse
         * @return the head of the list after reversing the sublist
         */
        public static ListNode reverseBetween(ListNode head, int left, int right) {

            // If the list is empty or left position is the same as right, return the original list
            if (head == null || left == right) {
                return head;
            }

            // Create a dummy node to handle edge case when left = 1
            ListNode dummy = new ListNode(0);
            dummy.next = head;
            ListNode prev = dummy;

            // Move prev to the node just before the left position
            for (int i = 0; i < left - 1; i++) {
                prev = prev.next;
            }

            // Current node is the node at left position
            ListNode curr = prev.next;

            // Reverse the sublist: each pass moves the node after curr to the front of the sublist (right after prev)
            for (int i = 0; i < right - left; i++) {
                ListNode nextNode = curr.next;
                curr.next = nextNode.next;
                nextNode.next = prev.next;
                prev.next = nextNode;
            }

            // Return the updated head of the linked list
            return dummy.next;
        }

        /**
         * Driver code that reverses sample sublists and prints the before and after lists.
         *
         * @param args unused
         */
        public static void main(String[] args) {
            List<List<Integer>> input = Arrays.asList(
                    Arrays.asList(1, 2, 3, 4, 5, 6, 7),
                    Arrays.asList(6, 9, 3, 10, 7, 4, 6),
                    Arrays.asList(6, 9, 3, 4),
                    Arrays.asList(6, 2, 3, 6, 9),
                    Arrays.asList(3, 6, 7, 4, 2),
                    Arrays.asList(6, 2)
            );

            int[] left = {1, 3, 2, 1, 2, 1};
            int[] right = {5, 6, 4, 3, 4, 2};
            for(int i=0; i<input.size(); i++){
                System.out.print(i+1);
                LinkedList list = new LinkedList(input.get(i));
                System.out.print(".\tOriginal linked list:  ");
                PrintList.display(list.head);
                System.out.print("\tLeft: " + left[i] + ", Right: " + right[i] + "\n\n");
                System.out.print("\tReversed linked list:  " );
                PrintList.display(reverseBetween(list.head,left[i],right[i]));
                System.out.println("_".repeat(100));
            }
        }
    }
}
