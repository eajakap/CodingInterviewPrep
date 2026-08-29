package org.educative.cip.m2.fast_slow_pointers;

import java.util.Arrays;
import java.util.List;

/*
 * Time Complexity: O(n) - We traverse the linked list once to find the maximum twin sum.
 * Space Complexity: O(1) - We use a constant amount of space for pointers.
 */
public class MaxTwinSumLinkedList {

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

    static class Solution {
        public static int twinSum(ListNode head) {
            // Initialize fast and slow pointers at the head of the linked list
            ListNode slow = head;
            ListNode fast = head;

            // Find the middle node of the linked list using fast and slow pointers
            while (fast != null && fast.next != null) {
                // Move the slow pointer one step forward
                slow = slow.next;
                // Move the fast pointer two steps forward
                fast = fast.next.next;
            }

            // Set curr at the middle node (slow) to reverse the second half of the linked list
            ListNode curr = slow;
            ListNode prev = null;

            // Iterate through the list until curr reaches null
            while (curr != null) {
                // Save curr.next for later use
                ListNode temp = curr.next;
                curr.next = prev;
                prev = curr;
                curr = temp;
            }

            // Initialize maxSum with 0 to keep track of the maximum twin sum found so far
            int maxSum = 0;

            // Set curr at the head of the linked list
            curr = head;

            // Iterate through the list until prev reaches null
            while (prev != null) {
                // Update maxSum if the current twin sum is greater than maxSum
                maxSum = Math.max(maxSum, curr.val + prev.val);

                // Move both prev and curr pointers forward
                prev = prev.next;
                curr = curr.next;
            }

            // Return maxSum as the maximum twin sum of the given linked list
            return maxSum;
        }

        // Driver code
        public static void main(String[] args) {
            List<List<Integer>> lists = Arrays.asList(
                    Arrays.asList(2, 3, 5, 7),
                    Arrays.asList(81, 144, 64, 121, 25, 49),
                    Arrays.asList(4, 5, 6, 7),
                    Arrays.asList(1, 1000),
                    Arrays.asList(11, 77, 44, 99, 22, 66, 55, 88)
            );

            for (int i = 0; i < lists.size(); ++i) {
                LinkedList inputLinkedList = new LinkedList(lists.get(i));
                System.out.print((i + 1) + ".\tLinked list: ");
                PrintList.display(inputLinkedList.head);
                System.out.println("\tMaximum twin sum: " + twinSum(inputLinkedList.head));
                System.out.println(new String(new char[100]).replace('\0', '-'));
            }
        }
    }

    public static ListNode reverse(ListNode head) {
        ListNode prev = null;
        while (head != null) {
            ListNode next = head.next;
            head.next = prev;
            prev = head;
            head = next;
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

    public static int twinSum(ListNode head) {

        // Replace this placeholder return statement with your code
        ListNode mid = middleNode(head);
        ListNode prev = reverse(mid);

        int maxSum = 0;
        ListNode  current = head;
        while (prev != null ) {
            maxSum = Math.max(maxSum, current.val + prev.val);
            current = current.next;
            prev = prev.next;
        }
        return maxSum;
    }

    public static void main(String[] args) {
        Solution.main(args);
        // create a linked list from an array of integers
        LinkedList linkedList = new LinkedList(Arrays.asList(2, 3, 5, 7));
        int i =1;
        System.out.print((i + 1) + ".\tLinked list: ");
        PrintList.display(linkedList.head);
        System.out.println("Maximum twin sum: " +
        twinSum(linkedList.head));
        System.out.println(new String(new char[100]).replace('\0', '-'));
    }
}
