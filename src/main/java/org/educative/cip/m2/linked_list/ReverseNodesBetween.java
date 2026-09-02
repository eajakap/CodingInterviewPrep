package org.educative.cip.m2.linked_list;

import java.util.Arrays;
import java.util.List;

public class ReverseNodesBetween {
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

        // Function to reverse the sublist within the linked list
        public static ListNode reverseBetween(ListNode head, int left, int right) {

            // If the list is empty or left position is the same as right, return the original list
            if (head == null || left == right) {
                return head;
            }

            // Create a dummy node to handle edge case when left = 1
            ListNode dummy = new ListNode(0);
            dummy.next = head;
            ListNode prev = dummy;

            // Move prev to the node just before the left position
            for (int i = 0; i < left - 1; i++) {
                prev = prev.next;
            }

            // Current node is the node at left position
            ListNode curr = prev.next;

            // Reverse the portion of the linked list between left and right positions
            for (int i = 0; i < right - left; i++) {
                ListNode nextNode = curr.next;
                curr.next = nextNode.next;
                nextNode.next = prev.next;
                prev.next = nextNode;
            }

            // Return the updated head of the linked list
            return dummy.next;
        }

        // Driver Code
        public static void main(String[] args) {
            List<List<Integer>> input = Arrays.asList(
                    Arrays.asList(1, 2, 3, 4, 5, 6, 7),
                    Arrays.asList(6, 9, 3, 10, 7, 4, 6),
                    Arrays.asList(6, 9, 3, 4),
                    Arrays.asList(6, 2, 3, 6, 9),
                    Arrays.asList(6, 2)
            );

            int[] left = {1, 3, 2, 1, 1};
            int[] right = {5, 6, 4, 3, 2};
            for(int i=0; i<input.size(); i++){
                System.out.print(i+1);
                LinkedList list = new LinkedList(input.get(i));
                System.out.print(".\tOriginal linked list:  ");
                PrintList.display(list.head);
                System.out.print("\tLeft: " + left[i] + ", Right: " + right[i] + "\n\n");
                System.out.print("\tReversed linked list:  " );
                PrintList.display(reverseBetween(list.head,left[i],right[i]));
                System.out.println("_".repeat(100));
            }
        }
    }
}
