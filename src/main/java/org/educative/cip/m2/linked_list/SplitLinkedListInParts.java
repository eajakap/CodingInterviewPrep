package org.educative.cip.m2.linked_list;

import java.util.Arrays;
import java.util.List;

/**
 * Split Linked List in Parts
 * Problem: Given the head of a singly linked list and an integer k, split the linked list into k
 *      consecutive linked list parts.
 *      The length of each part should be as equal as possible: no two parts should have a size
 *      differing by more than one. This may lead to some parts being null.
 *      The parts should be in the order of occurrence in the input list, and parts occurring
 *      earlier should always have a size greater than or equal to parts occurring later.
 *      Return an array of the k parts.
 * Example 1:
 * Input: head = [1,2,3], k = 5
 * Output: [[1],[2],[3],[],[]]
 * Example 2:
 * Input: head = [1,2,3,4,5,6,7,8,9,10], k = 3
 * Output: [[1,2,3,4],[5,6,7],[8,9,10]]
 * Constraints:
 * The number of nodes in the list is n.
 * 0 <= n <= 1000
 * 0 <= Node.val <= 1000
 * 1 <= k <= 50
 *
 * Time Complexity: O(n), where n is the number of nodes in the linked list.
 *                  We traverse the entire list once to count the nodes and then again to split it into parts.
 * Space Complexity: O(k), where k is the number of parts. We create an array of size k to store the heads of the split parts.
 *                  Therefore, the space complexity is O(1) for the result array.
 *
 */
public class SplitLinkedListInParts {
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
     * Splits the linked list into k parts.
     * Steps:
     * 1. Count the total number of nodes in the linked list.
     * 2. Calculate the base size of each part and the number of extra nodes to be distributed.
     * 3. Iterate through the linked list and split it into k parts.
     * 4. For each part, determine its size and break the link to the next part.
     * 5. Store the head of each part in the result array.
     * 6. Return the array containing the heads of the split parts.
     *
     * @param head the head of the linked list
     * @param k    the number of parts to split the list into
     * @return an array containing the heads of the split parts
     */
    public static ListNode[] splitListToParts(ListNode head, int k) {
        int size = countNodes(head);
        int baseSize = size / k;
        int extraNodes = size % k;
        ListNode[] ans = new ListNode[k];
        ListNode curr = head;

        // Split the linked list into k parts - sharing baseSize nodes first
        for (int i = 0; i < k; i++) {
            // Determine the head of the current part and its size
            ListNode partHead = curr;
            // Calculate the size of the current part
            int partSize = baseSize + (i < extraNodes ? 1 : 0);
            // Move curr to the end of the current part
            for (int j = 0; j < partSize - 1; j++) {
                if (curr != null) {
                    curr = curr.next;
                }
            }
            // Break the link to the next part if curr is not null
            if (curr != null) {
                ListNode nextPartHead = curr.next;
                curr.next = null; // Break the link
                curr = nextPartHead; // Move to the head of the next part
            }
            ans[i] = partHead;
        }
        return ans;
    }

    public static void main(String[] args) {
        List<List<Integer>> lists = Arrays.asList(
                Arrays.asList(9, 7, 8, 7, 7, 6),
                Arrays.asList(2, 3, 5, 7, 11),
                Arrays.asList(4, 4, 4, 4, 4),
                Arrays.asList(1, 2, 3, 11, 22, 33),
                Arrays.asList(1, 2, 6, 3, 4, 5, 6)
        );

        int[] ks = {7, 2, 4, 3, 6};

        for (int i = 0; i < lists.size(); i++) {
            LinkedList inputLinkedList = new LinkedList(lists.get(i));
            System.out.print((i + 1) + ". \tLinked list: ");
            PrintList.display(inputLinkedList.head);

            System.out.println("\n\tk: " + ks[i]);
            ListNode[] result = splitListToParts(inputLinkedList.head, ks[i]);

            System.out.print("\tLinked list parts: [");
            for (int j = 0; j < result.length; j++) {
                if (result[j] != null) {
                    System.out.print("[");
                    ListNode part = result[j];
                    while (part != null) {
                        System.out.print(part.val);
                        if (part.next != null) System.out.print(", ");
                        part = part.next;
                    }
                    System.out.print("]");
                } else {
                    System.out.print("[]");
                }
                if (j < result.length - 1) System.out.print(", ");
            }
            System.out.println("]");
            System.out.println(new String(new char[100]).replace('\0', '-'));
        }
    }

}
