package org.educative.cip.m2.linked_list;

import java.util.Arrays;
import java.util.List;

/**
 * Reorder Single Linked List
 * Problem: Given the head of a singly linked list, reorder the list as if it were folded on itself
 *          to follow the pattern:
 *          Input List: L0 → L1 → … → Ln-1 → Ln
 *          Reordered List: L0 → Ln → L1 → Ln-1 → L2 → Ln-2 → …
 * You may not modify the values in the list's nodes. Only the links between nodes need to be changed.
 * Example 1:
 * Input: head = [1,2,3,4] ==> HEAD->1->2->3->4->NULL
 * Output: [1,4,2,3] ==> HEAD->1->4->2->3->NULL
 * Example 2:
 * Input: head = [1,2,3,4,5] ==> HEAD->1->2->3->4->5->NULL
 * Output: [1,5,2,4,3] ==> HEAD->1->5->2->4->3->NULL
 * Constraints:
 * The number of nodes in the list is in the range [1, 5 * 10^4].
 * 1 <= Node.val <= 1000
 *
 * Approach: Find the middle of the list with slow/fast pointers, reverse the second half in place,
 * then interleave the first half with the reversed second half.
 *
 * Time Complexity: O(n) - each phase (find middle, reverse, merge) is a single pass.
 * Space Complexity: O(1) - links are rewired in place, with no extra data structures.
 */
public class ReorderSingleLinkedList {
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
         * Creates a linked list from an array of values, preserving their order.
         *
         * @param values the values to insert into the list in order
         */
        public LinkedList(int[] values) {
            head = null;
            createLinkedList(values);
        }

        /**
         * Creates a linked list from a list of values, preserving their order.
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

        /**
         * Builds the linked list from an array of values.
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
     * Reverses a singly linked list in place.
     *
     * @param head the head of the list to reverse
     * @return the new head (the former tail), or null for an empty list
     */
    public static ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode current = head;
        while (current != null) {
            // Save the next node, then point the current node backwards
            ListNode next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }
        return prev;
    }

    /**
     * Finds the middle node of a list using slow and fast pointers.
     * For an even number of nodes, returns the second of the two middle nodes.
     *
     * @param head the head of the list
     * @return the middle node, or null for an empty list
     */
    public static ListNode middleNode(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        // Fast moves two steps for every one step of slow, so slow ends at the middle
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    /**
     * Interleaves two lists, alternating nodes: first, second, first, second, ...
     * The second list must not be longer than the first.
     *
     * @param firstHead  the head of the first list
     * @param secondHead the head of the second list
     * @return the head of the merged list (the head of the first list)
     */
    public static ListNode mergeLists(ListNode firstHead, ListNode secondHead) {
        ListNode first = firstHead;
        ListNode second = secondHead;
        while (second != null) {
            // Save the next nodes, then splice the second node in right after the first
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
     * Reorders the list in place to the pattern L0 → Ln → L1 → Ln-1 → L2 → Ln-2 → ...
     * Only links are changed; node values are untouched.
     *
     * @param head the head of the list to reorder
     */
    public static void reorderList(ListNode head) {
        if (head == null || head.next == null) return;

        // Find the middle of the list; for an even length, slow stops at the end of the first half
        ListNode slow = head, fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // Reverse the second half of the list
        ListNode prev = null, curr = slow.next;
        while (curr != null) {
            ListNode nextTemp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nextTemp;
        }
        // Cut the first half off from the reversed second half
        slow.next = null;

        // Merge the two halves by alternating nodes
        ListNode first = head, second = prev;
        while (second != null) {
            ListNode temp1 = first.next;
            ListNode temp2 = second.next;
            first.next = second;
            second.next = temp1;
            first = temp1;
            second = temp2;
        }
    }

    /**
     * Driver code that reorders sample lists and prints them before and after.
     *
     * @param args unused
     */
    public static void main(String[] args) {
        List<List<Integer>> inputLists = Arrays.asList(
                Arrays.asList(1, 1, 2, 2, 3, -1, 10, 12),
                Arrays.asList(10, 20, -22, 21, -12),
                Arrays.asList(1, 1, 1),
                Arrays.asList(-2, -5, -6, 0, -1, -4),
                Arrays.asList(3, 1, 5, 7, -4, -2, -1, -6)
        );

        for (int i = 0; i < inputLists.size(); i++) {
            LinkedList obj = new LinkedList(inputLists.get(i));

            System.out.print((i + 1) + ".\tOriginal list: ");
            PrintList.display(obj.head);

            reorderList(obj.head);

            System.out.print("\tAfter folding: ");
            PrintList.display(obj.head);

            System.out.println("-".repeat(100));
        }
    }

}
