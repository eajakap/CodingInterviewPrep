package org.educative.cip.m2.linked_list;

import java.util.Arrays;
import java.util.List;

/**
 * Swap Nodes in Pairs
 * Problem: Given the head of a linked list, swap every two adjacent nodes and return its head.
 *          You must solve the problem without modifying the values in the list's nodes (i.e., only nodes themselves may be changed.)
 * Example 1:
 * Input: head = [1,2,3,4]
 * Output: [2,1,4,3]
 * Example 2:
 * Input: head = []
 * Output: []
 * Example 3:
 * Input: head = [1]
 * Output: [1]
 * Constraints:
 * The number of nodes in the list is in the range [0, 100].
 * 0 <= Node.val <= 100
 *
 * Time Complexity: O(n), where n is the number of nodes in the linked list. Each node is visited at most once.
 * Space Complexity: O(1), as we are using a constant amount of extra space for pointers and counters.
 *
 */
public class SwapNodesInPairs {
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

    private static int countNodes(ListNode head) {
        int count = 0;
        ListNode current = head;
        while (current != null) {
            count++;
            current = current.next;
        }
        return count;
    }

    /**
     * Swaps every two adjacent nodes in the linked list by modifying the values of the nodes.
     * Steps:
     * 1. Count the number of nodes in the linked list.
     * 2. If the list has fewer than two nodes, return the head as is.
     * 3. Iterate through the list in pairs, swapping the values of each pair of nodes.
     * 4. Return the head of the modified linked list.
     *
     * @param head The head of the linked list.
     * @return The head of the modified linked list after swapping pairs.
     */
    public static ListNode swapPairsUsingValues(ListNode head) {
        int count = countNodes(head);
        if  (head == null || head.next == null) {
            return head;
        }
        if (count >=2 ) {
            ListNode current = head;
            while (current != null && current.next != null) {
                // Swap the values of the current node and the next node
                ListNode firstNode = current;
                ListNode secondNode = current.next;

                int temp = firstNode.val;
                firstNode.val = secondNode.val;
                secondNode.val = temp;
//                firstNode.next = secondNode.next;
//                secondNode.next = firstNode;
//                if (current == head) {
//                    head = secondNode; // Update head to the new first node after swap
//                } else {
//                    // Link the previous pair's second node to the new first node after swap
//                    ListNode prev = head;
//                    while (prev.next != firstNode) {
//                        prev = prev.next;
//                    }
//                    prev.next = secondNode;
//                }
                // Move to the next pair
                current = secondNode.next;
            }
        }
        return head;
    }

    /**
     * Swaps every two adjacent nodes in the linked list by modifying the references (pointers) of the nodes.
     * Steps:
     * 1. Create a dummy node that points to the head of the list. This simplifies edge cases.
     * 2. Iterate through the list in pairs, swapping the references of each pair of nodes.
     * 3. Return the head of the modified linked list.
     * @param head The head of the linked list.
     * @return The head of the modified linked list after swapping pairs.
     */
    public static ListNode swapPairsUsingReferences(ListNode head) {
        if  (head == null || head.next == null) {
            return head;
        }

        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prev = dummy;

        while (prev.next != null && prev.next.next != null) {
            ListNode firstNode = prev.next;
            ListNode secondNode = firstNode.next;

            firstNode.next = secondNode.next;
            secondNode.next = firstNode;
            prev.next = secondNode;

            prev = firstNode;
        }

        return dummy.next;
    }

    public static void main(String[] args) {
        SwapNodesInPairs sol = new SwapNodesInPairs();
        List<List<Integer>> inputLists = Arrays.asList(
                Arrays.asList(1, 1, 2, 2, 3, -1, 10, 12),
                Arrays.asList(10, 20, -22, 21, -12),
                Arrays.asList(1, 1, 1),
                Arrays.asList(-2, -5, -6, 0, -1, -4),
                Arrays.asList(3, 1, 5, 7, -4, -2, -1, -6)
        );

        for (int i = 0; i < inputLists.size(); i++) {
            SwapNodesInPairs.LinkedList obj = new SwapNodesInPairs.LinkedList(inputLists.get(i));
            SwapNodesInPairs.LinkedList objForReferences = new SwapNodesInPairs.LinkedList(inputLists.get(i));

            System.out.print((i + 1) + ".\tOriginal list: ");
            SwapNodesInPairs.PrintList.display(obj.head);

            sol.swapPairsUsingValues(obj.head);

            System.out.print("\tAfter swapping pairs using values: ");
            SwapNodesInPairs.PrintList.display(obj.head);
            objForReferences.head = sol.swapPairsUsingReferences(objForReferences.head);

            System.out.print("\tAfter swapping pairs using references: ");
            SwapNodesInPairs.PrintList.display(objForReferences.head);

            System.out.println("-".repeat(100));
        }
    }

}
