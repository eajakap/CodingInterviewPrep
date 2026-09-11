package org.educative.cip.m2.linked_list;

import java.util.Arrays;
import java.util.List;

/**
 * Swapping Nodes in a Single Linked List
 * Problem: Given the head of a singly linked list, swap the k-th node from the beginning with the k-th node from the end.
 *      Return the head of the modified linked list.
 * Example 1:
 * Input: head = [1,2,3,4,5], k = 2
 * Output: [1,4,3,2,5]
 * Example 2:
 * Input: head = [7,9,6,6,7,8,3,0,9,5], k = 5
 * Output: [7,9,6,6,8,7,3,0,9,5]
 * Example 3:
 * Input: head = [1], k = 1
 * Output: [1]
 * Example 4:
 * Input: head = [1,2], k = 1
 * Output: [2,1]
 * Example 5:
 * Input: head = [1,2,3], k = 2
 * Output: [1,2,3]
 * Constraints:
 * The number of nodes in the list is n.
 * 1 <= k <= n <= 10^5
 * 0 <= Node.val <= 100
 *
 * Steps to solve the problem:
 * 1. Initialize two pointers, front and end, to null.
 * 2. Initialize a counter variable to keep track of the current node's position.
 * 3. Traverse the linked list using a current pointer.
 * 4. Increment the counter for each node visited.
 * 5. When the counter reaches k, set the front pointer to the current node and set the end pointer to the head of the list.
 * 6. Continue traversing the list, moving the end pointer one step forward for each subsequent node visited.
 * 7. When the traversal is complete, the front pointer will point to the k-th node from the beginning, and the end pointer will point to the k-th node from the end.
 * 8. Swap the values of the nodes pointed to by the front and end pointers.
 * 9. Return the head of the modified linked list.
 */
public class SwappingNodesSingleLinkedList {
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
     * Reverses a singly linked list in place.
     *
     * @param head the head of the list to reverse
     * @return the new head of the reversed list
     */
    public static ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode current = head;
        while (current != null) {
            ListNode temp = current.next;
            current.next = prev;
            prev = current;
            current = temp;
        }
        return prev;
    }

    /**
     * Finds the middle node of a singly linked list using the slow/fast pointer technique.
     *
     * @param head the head of the list
     * @return the middle node when the list length is odd, or the upper-middle node when even
     */
    public static ListNode middleNode(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    /**
     * Merges two lists by re-linking nodes from the second list into the first list.
     *
     * @param firstHead the head of the first list
     * @param secondHead the head of the second list
     * @return the head of the first list after merging
     */
    public static ListNode mergeLists(ListNode firstHead, ListNode secondHead) {
        ListNode first = firstHead;
        ListNode second = secondHead;
        while (second != null) {
            ListNode temp1 = first.next;
            ListNode temp2 = second.next;
            first.next = second;
            second.next = temp1;
            first = temp1;
            second = temp2;
        }
        return firstHead;
    }

    /**
     * Swaps the values stored in two nodes without altering their positions in the list.
     *
     * @param node1 the first node whose value is swapped
     * @param node2 the second node whose value is swapped
     */
    public static void swap(ListNode node1, ListNode node2) {
        int temp = node1.val;
        node1.val = node2.val;
        node2.val = temp;
    }

    /**
     * Swaps the values of the k-th node from the beginning and the k-th node from the end.
     *
     * @param head the head of the linked list
     * @param k the position from the front to swap with the equally indexed node from the back
     * @return the head of the list after the swap
     */
    public static ListNode swapNodes(ListNode head, int k) {
        if (head == null) {
            return head;
        }
        int count = 0;

        ListNode front = null;
        ListNode end = null;
        ListNode curr = head;

        while (curr != null) {
            count += 1;
            if (end != null) {
                end = end.next;
            }
            if (count == k) {
                front = curr;
                end = head;
            }
            curr = curr.next;
        }
        swap(front, end);
        return head;
    }

    public static void main(String[] args) {

        List<List<Integer>> input = Arrays.asList(
                Arrays.asList(1, 2, 3, 4, 5, 6, 7),
                Arrays.asList(6, 9, 3, 10, 7, 4, 6),
                Arrays.asList(6, 9, 3, 4),
                Arrays.asList(6, 2, 3, 6, 9),
                Arrays.asList(6, 2)
        );
        int[] k = {
                2, 3, 2, 3, 1
        };

        for(int i=0; i<input.size(); i++){
            System.out.print(i+1);
            LinkedList list = new LinkedList(input.get(i));
            System.out.print(".\tOriginal linked list is: ");
            PrintList.display(list.head);
            System.out.println("\tk: "+k[i]);
            System.out.print("\tLinked list with swapped values: ");
            PrintList.display(swapNodes(list.head,k[i]));
            System.out.println(new String(new char[100]).replace('\0', '-'));
        }
    }

}
