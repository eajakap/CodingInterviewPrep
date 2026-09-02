package org.educative.cip.m2.linked_list;

import java.util.Arrays;
import java.util.List;

/*
 * Time Complexity: O(n) - We traverse the linked list once to reverse nodes in k-groups.
 * Space Complexity: O(1) - We use a constant amount of space for pointers.
 */
public class ReverseNodesKGroups {
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

    static class LinkedListReversal{
        // Function to reverse k nodes in the linked list
        // Returns an array containing the new head of the reversed list and the next node after the reversed group
        static ListNode[] reverseLinkedList(ListNode node, int k){

            ListNode previous = null;
            ListNode current = node;
            ListNode next = null;

            for (int i = 0; i < k; i++) {
                next = current.next;
                current.next = previous;
                previous = current;
                current = next;
            }

            return new ListNode[]{previous, current};
        }
    }

    static class Solution
    {
        public static ListNode reverseKGroups(ListNode head, int k) {

            ListNode dummy = new ListNode(0);
            dummy.next = head;
            ListNode ptr = dummy;

            while (ptr != null) {

                // Check if there are at least k nodes left in the linked list
                ListNode tracker = ptr;
                for (int i = 0; i < k; i++) {
                    if (tracker == null) {
                        break;
                    }
                    tracker = tracker.next;
                }

                if (tracker == null) {
                    // If there are fewer than k nodes left, we don't reverse them
                    break;
                }

                ListNode[] updatedNodes = LinkedListReversal.reverseLinkedList(ptr.next, k);
                ListNode previous = updatedNodes[0]; // This is the new head of the reversed group
                ListNode current = updatedNodes[1]; // This is the node after the reversed group

                // This is the last node of the previous reversed group
                ListNode lastNodeOfReversedGroup = ptr.next;
                // Connect the last node of the reversed group to the next node after the reversed group
                lastNodeOfReversedGroup.next = current;
                // Connect the previous node to the new head of the reversed group
                ptr.next = previous;
                // Move the pointer to the last node of the reversed group for the next iteration
                ptr = lastNodeOfReversedGroup;
            }

            return dummy.next;
        }

        // Driver code
        public static void main(String[] args) {
            List<List<Integer>> inputList = Arrays.asList(
                    Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8),
                    Arrays.asList(3, 4, 5, 6, 2, 8, 7, 7),
                    Arrays.asList(1, 2, 3, 4, 5),
                    Arrays.asList(1, 2, 3, 4, 5, 6, 7),
                    Arrays.asList(1)
            );

            int[] k = {3, 2, 1, 7, 1};

            for (int i = 0; i < inputList.size(); ++i) {
                LinkedList inputLinkedList = new LinkedList(inputList.get(i));

                System.out.print((i + 1) + ".\tLinked list: ");
                PrintList.display(inputLinkedList.head);
                System.out.println();

                System.out.print("\n\tReversed linked list: ");
                ListNode result = reverseKGroups(inputLinkedList.head, k[i]);
                PrintList.display(result);
                System.out.println();

                String hyphens = new String(new char[100]).replace('\0', '-');
                System.out.println(hyphens);
            }
        }
    }

    public static void main(String[] args) {
        Solution.main(args);
    }
}
