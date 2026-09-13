package org.educative.cip.m2.linked_list;

import java.util.Arrays;
import java.util.List;

/**
 * Odd Even Linked List
 * Problem: Given the head of a singly linked list, group all the nodes with odd indices together followed by the nodes with even indices, and return the reordered list.
 * The first node is considered odd, and the second node is even, and so on.
 * Note: The relative order inside both the even and odd groups should remain as it was in the input.
 * Example 1:
 * Input: head = [1,2,3,4,5]
 * Output: [1,3,5,2,4]
 * Example 2:
 * Input: head = [2,1,3,5,6,4,7]
 * Output: [2,3,6,7,1,5,4]
 * Constraints:
 * The number of nodes in the linked list is in the range [0, 10^4].
 * -10^3 <= Node.val <= 10^3
 * Time Complexity: O(n), where n is the number of nodes in the linked list. We traverse the entire list once to rearrange the nodes.
 * Space Complexity: O(1), as we are using a constant amount of extra space for pointers and variables.
 */
public class OddEvenLinkedList {

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
     * Reorders the linked list such that all nodes at odd indices are grouped together followed by nodes at even indices.
     * Steps:
     * 1. Check if the list is empty or has only one node. If so, return the head as no reordering is needed.
     * 2. Initialize two pointers:
     *    - `odd` pointing to the head (first node).
     *    - `even` pointing to the second node (head.next).
     *    - Store the head of the even list in `evenHead` to connect later.
     * 3. Traverse the list while both `even` and `even.next` are not null:
     *    - Link the next odd node to the current odd node's next (which is `even.next`).
     *    - Move the `odd` pointer to the next odd node.
     *    - Link the next even node to the current even node's next (which is `odd.next`).
     *    - Move the `even` pointer to the next even node.
     * 4. After the loop, connect the last odd node to the head of the even list (`evenHead`).
     * 5. Return the head of the reordered linked list.
     * Time Complexity: O(n), where n is the number of nodes in the linked list. We traverse the entire list once to rearrange the nodes.
     * Space Complexity: O(1), as we are using a constant amount of extra space for pointers and variables.
     *
     * @param head The head of the singly linked list.
     * @return The head of the reordered linked list.
     */
    public ListNode oddEvenList(ListNode head) {
        // Write your code here
        if (head == null || head.next == null) {
            return head;
        }
        ListNode odd = head;
        ListNode even = head.next;
        ListNode evenHead = even;

        while (even != null && even.next != null) {
            odd.next = even.next;
            odd = odd.next;
            even.next = odd.next;
            even = even.next;
        }

        odd.next = evenHead;

        return head;
    }

    public static void main(String[] args) {
        OddEvenLinkedList sol = new OddEvenLinkedList();
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

            sol.oddEvenList(obj.head);

            System.out.print("\tAfter odd-even reordering: ");
            PrintList.display(obj.head);

            System.out.println("-".repeat(100));
        }
    }

}
