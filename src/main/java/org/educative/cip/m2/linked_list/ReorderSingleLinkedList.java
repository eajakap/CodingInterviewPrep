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
 */
public class ReorderSingleLinkedList {
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

    static class PrintList {
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

    public static ListNode middleNode(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

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

    // Function to reorder the linked list
    public static void reorderList(ListNode head) {
        if (head == null || head.next == null) return;

        // Find the middle of the list
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
        slow.next = null;

        // Merge the two halves
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
