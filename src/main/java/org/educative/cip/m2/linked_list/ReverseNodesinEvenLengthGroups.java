package org.educative.cip.m2.linked_list;

import java.util.Arrays;
import java.util.List;

/**
 * Reverse Nodes in Even Length Groups
 * Problem: Given the head of a linked list,the nodes in it are assigned to each group in a sequential manner.
 *           The length of these groups follows the sequence of natural numbers.
 *           Reverse the nodes of the list in groups of even length and return the modified list.
 * Example 1:
 * Input: head = [1,2,3,4,5]
 * Output: [1,2,3,4,5]
 * Explanation: The first group has length 1 (odd), so it is not reversed.
 *              The second group has length 2 (even), so it is reversed.
 *              The third group has length 2 (even), so it is reversed.
 * Example 2:
 * Input: head = [1,2,3,4,5,6]
 * Output: [1,2,3,4,5,6]
 * Explanation: The first group has length 1 (odd), so it is not reversed.
 *              The second group has length 2 (even), so it is reversed.
 *              The third group has length 3 (odd), so it is not reversed.
 * Constraints:
 * - The number of nodes in the list is n.
 * - 1 <= n <= 10^5
 * - 0 <= Node.val <= 10^3
 */
public class ReverseNodesinEvenLengthGroups {

    /**
     * Represents a single node in a singly linked list.
     */
    static class ListNode {
        int val;
        ListNode next;

        /**
         * Creates a list node with the given value.
         *
         * @param val the integer value stored in this node
         */
        public ListNode(int val) {
            this.val = val;
            this.next = null;
        }
    }

    /**
     * Utility for building and managing a singly linked list.
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
         * @param values node values in list order
         */
        public LinkedList(int[] values) {
            head = null;
            createLinkedList(values);
        }

        /**
         * Creates a linked list from a list of integer values.
         *
         * @param values node values in list order
         */
        public LinkedList(List<Integer> values) {
            head = null;
            createLinkedList(values);
        }

        /**
         * Builds the linked list from a list of integer values.
         *
         * @param values node values in list order
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
         * @param values node values in list order
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
     * Provides a helper to print a linked list in a readable format.
     */
    static class PrintList {
        /**
         * Prints the values of the list from head to tail.
         *
         * @param head the head of the linked list to display
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
     * Reverses the next {@code groupSize} nodes starting at {@code head}.
     * This helper is meant to reverse a contiguous segment of the list, not the
     * entire list structure.
     *
     * @param head the first node of the group to reverse
     * @param groupSize the number of nodes in the group
     * @return the new head of the reversed group
     */
    public static ListNode reverseGroup(ListNode head, int groupSize) {
       if (head == null || groupSize <= 1) {
           return head;
       }

       ListNode prev = null;
       ListNode current = head;

       for (int i = 0; i < groupSize && current != null; i++) {
           ListNode next = current.next;
           current.next = prev;
           prev = current;
           current = next;
       }

       if (head != null) {
           head.next = current;
       }

       return prev;
    }

    /**
     * Reverses the nodes of the linked list in groups of even length.
     * Steps through the linked list, counting nodes in groups of increasing length.
     * If a group has an even number of nodes, it reverses that group in place
     * and connects it back to the previous and next nodes.
     * Steps to solve the problem:
     * 1. Initialize a pointer to the head of the list and a variable for the current group length.
     * 2. While there are more nodes in the list:
     *    a. Count the number of nodes in the current group.
     *    b. If the group length is even, reverse the nodes in that group.
     *    c. Move the pointer to the end of the current group and increment the group length for the next iteration.
     * 3. Return the modified head of the list.
     *
     * @param head the head of the linked list
     * @return the modified linked list with even length groups reversed
     */
    public static ListNode reverseEvenLengthGroups(ListNode head)
    {
        ListNode prev = head; // prev is the last node of the previous group, at the start it is the head of the list
        ListNode node, reverse, currNext, curr, prevNext = null;
        int groupLen = 2; // groupLen is the length of the current group, starting from 2 for second group as the first group is of length 1
        int numNodes = 0; // numNodes is the number of nodes in the current group
        // First Group is of length 1, so we start with groupLen = 2 for the second group
        while(prev.next!= null) // starts from the second group, as the first group is of length 1
        {
            node = prev; // node is the last node of the previous group
            numNodes = 0; // numNodes is the number of nodes in the current group
            for (int i = 0; i < groupLen; i ++)
            {
                if(node.next == null) {
                    // If we reach the end of the list, we break out of the loop
                    break;
                }
                numNodes += 1; // We count the number of nodes in the current group
                node=node.next; // We move the node pointer to the next node in the current group
            }

            if(numNodes % 2 != 0) {
                prev = node; // If the number of nodes in the current group is odd, we do not reverse the group and move prev to the last node of the current group
            } else {
                // If the number of nodes in the current group is even, we reverse the group
                reverse = node.next; // reverse is the first node of the next group, which will be connected to the last node of the current group after reversing
                curr = prev.next; // curr is the first node of the current group, which will be reversed
                for(int j=0; j < numNodes;j++) {
                    // We reverse the nodes in the current group by changing their next pointers
                    currNext = curr.next; // currNext is the next node of the current node, which will be used to move to the next node in the group
                    curr.next = reverse; // We change the next pointer of the current node to point to the last node of the previous group, which is reverse
                    reverse = curr; // We move the reverse pointer to the current node, which will be the last node of the current group after reversing
                    curr = currNext; // We move the curr pointer to the next node in the group, which is currNext
                }
                // After reversing the group, we connect the previous group to the current group
                prevNext = prev.next; // prevNext is the first node of the current group, which will be connected to the last node of the previous group after reversing
                prev.next = node; // We change the next pointer of the last node of the previous group to point to the last node of the current group, which is node
                prev = prevNext; // We move the prev pointer to the first node of the current group, which will be the last node of the current group after reversing
            }
            groupLen += 1; // We increment the group length for the next group
        }
        return head; // We return the head of the modified list
    }

    public static void main(String[] args) {
        List<List<Integer>> inputLists = Arrays.asList(
                Arrays.asList(5, 2, 6, 3, 9, 1, 7, 3),
                Arrays.asList(1, 2, 3, 4),
                Arrays.asList(10, 11, 12, 13, 14),
                Arrays.asList(15),
                Arrays.asList(16, 17)
        );

        for (int i = 0; i < inputLists.size(); i++) {
            LinkedList inputLinkedList = new LinkedList(inputLists.get(i));

            System.out.print((i + 1) + ".\tIf we reverse the even length groups of the linked list: ");
            PrintList.display(inputLinkedList.head);
            System.out.print("\n\n\twe will get: ");
            PrintList.display(reverseEvenLengthGroups(inputLinkedList.head));
            System.out.println();
            System.out.println(new String(new char[100]).replace('\0', '-'));
        }
    }

}
