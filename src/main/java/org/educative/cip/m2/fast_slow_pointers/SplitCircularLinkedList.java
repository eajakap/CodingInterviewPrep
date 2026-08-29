package org.educative.cip.m2.fast_slow_pointers;

import java.util.*;
/*
 * Time Complexity: O(n) - We traverse the circular linked list once to split it into two halves.
 * Space Complexity: O(1) - We use a constant amount of space for pointers.
 */
public class SplitCircularLinkedList {

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

        // Constructor to create a CircularLinkedList
        public LinkedList() {
            this.head = null;
        }

        // Method to insert a node at the head of the circular linked list
        public void insertNodeAtHead(ListNode node) {
            if (head == null) {
                // If list is empty, the new node will point to itself
                head = node;
                node.next = node;
            } else {
                // Insert at head and make last node point to the new head
                ListNode last = head;
                while (last.next != head) {
                    last = last.next;
                }
                node.next = head; // Point new node to current head
                head = node;      // Update head to the new node
                last.next = head; // Update last node to point to new head
            }
        }

        // Method to create the linked list using an integer array
        public void createLinkedList(int[] arr) {
            for (int i = arr.length - 1; i >= 0; i--) {
                ListNode newNode = new ListNode(arr[i]);
                insertNodeAtHead(newNode);
            }
        }

        // Method to convert the circular linked list to an array
        public int[] toArray() {
            if (head == null) {
                return new int[0];
            }

            HashSet<ListNode> seenNodes = new HashSet<>();
            int[] result = new int[100]; // Arbitrary size; can be adjusted or dynamically resized
            int count = 0;

            ListNode current = head;
            do {
                result[count++] = current.val;
                seenNodes.add(current);
                current = current.next;
            } while (current != head && !seenNodes.contains(current));

            // Trim the array to the actual size
            return Arrays.copyOf(result, count);
        }
    }

    static class PrintList {

        // Method to print the circular linked list
        public static void printCircularLinkedList(ListNode head) {
            if (head == null) {
                System.out.print("List is empty ");
                return;
            }

            ListNode current = head;
            HashSet<ListNode> seenNodes = new HashSet<>(); // To track nodes we've already printed

            do {
                System.out.print(current.val + " "); // Print node value
                seenNodes.add(current); // Add current node to seen nodes
                current = current.next;
                if (current == head) { // When we come back to the head node
                    System.out.print("→ (head) ");
                    break;
                }
                if (current != null) {
                    System.out.print("→ ");
                }
            } while (current != null && !seenNodes.contains(current));
            System.out.println(); // Move to the next line after printing
        }

        // Method to convert a linked list to an array
        public static int[] linkedListToArray(ListNode head) {
            if (head == null) {
                return new int[0];
            }

            HashSet<ListNode> seenNodes = new HashSet<>();
            int[] result = new int[100]; // Arbitrary size, can be adjusted
            int count = 0;

            ListNode current = head;
            do {
                result[count++] = current.val;
                seenNodes.add(current);
                current = current.next;
            } while (current != head && !seenNodes.contains(current));

            // Trim the array to the actual size
            return java.util.Arrays.copyOf(result, count);
        }
    }

    static class Solution {
        // Method to split the circular linked list
        // Method to split a circular linked list into two halves
        public static ListNode[] splitCircularLinkedList(ListNode head) {
            // Initialize slow and fast pointers to the head of the list
            ListNode slow = head;
            ListNode fast = head;

            // Move slow by one step and fast by two steps to find the middle of the list
            while (fast.next != head && fast.next.next != head) {
                slow = slow.next; // Move slow pointer one step
                fast = fast.next.next; // Move fast pointer two steps
            }

            // Set head1 to the start of the first half
            ListNode head1 = head;
            // Set head2 to the start of the second half, which is the node after slow
            ListNode head2 = slow.next;
            // Make the first half circular by pointing slow's next to head1
            slow.next = head1;

            // Reuse the fast pointer to find the end of the second half
            fast = head2;
            while (fast.next != head) {
                fast = fast.next; // Move to the end of the second half
            }
            // Make the second half circular by linking the last node to head2
            fast.next = head2;

            // Return the heads of the two split circular linked lists
            return new ListNode[]{head1, head2};
        }

        // Driver code
        public static void main(String[] args) {
            int[][] lists = {
                    {1},
                    {1, 5, 7},
                    {2, 6, 1, 5},
                    {3, 1, 4, 2, 5},
                    {8, 10, 12, 14, 16, 18},
                    {9, 10}
            };

            for (int i = 0; i < lists.length; i++) {
                LinkedList inputLinkedList = new LinkedList();
                inputLinkedList.createLinkedList(lists[i]);
                System.out.print((i + 1) + ". Linked list: ");
                PrintList.printCircularLinkedList(inputLinkedList.head);

                // Get the split lists
                ListNode[] splitLists = splitCircularLinkedList(inputLinkedList.head);
                int[] splitList1 = PrintList.linkedListToArray(splitLists[0]);
                int[] splitList2 = PrintList.linkedListToArray(splitLists[1]);
                System.out.println("\n   Split Lists: [" + java.util.Arrays.toString(splitList1) + ", " + java.util.Arrays.toString(splitList2) + "]");
                System.out.println(new String(new char[100]).replace('\0', '-')); // Printing 100 dashes
            }
        }
    }

    public static void main(String[] args) {
        Solution.main(args);
    }
}
