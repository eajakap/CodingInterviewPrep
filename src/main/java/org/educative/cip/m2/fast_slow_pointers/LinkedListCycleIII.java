package org.educative.cip.m2.fast_slow_pointers;

import java.util.Arrays;
import java.util.List;
/*
 * Time Complexity: O(n) - We traverse the linked list once to detect the cycle and count its length.
 * Space Complexity: O(1) - We use a constant amount of space for pointers.
 */
public class LinkedListCycleIII {
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

        // returns the node at the specified position(index) of the linked list
        public static ListNode getNode(ListNode head, int pos){
            ListNode ptr = head;
            if (pos != -1){
                int p = 0;

                while (p < pos){
                    ptr = ptr.next;
                    p += 1;
                }

                return ptr;
            }
            return ptr;
        }

        // returns the number of nodes in the linked list
        public static int getLength(ListNode head)
        {
            ListNode temp = head;
            int count = 0;
            while (temp != null) {
                count++;
                temp = temp.next;
            }
            return count;
        }

    }

    static class PrintList{
        // Function to display the linked list
        public static void printListWithForwardArrow(ListNode head) {
            ListNode temp = head;

            while (temp != null) {
                System.out.print(temp.val); // print node value
                temp = temp.next;
                if (temp != null) {
                    System.out.print(" → ");
                } else{
                    // if this is the last node, print null at the end
                    System.out.print(" → null \n ");
                }
            }
        }
        public static void printListWithForwardArrowLoop(ListNode head) {
            ListNode temp = head;

            while (temp != null) {
                System.out.print(temp.val); // print node value
                temp = temp.next;
                if (temp != null) {
                    System.out.print(" → ");
                }
            }
        }
    }

    static class Solution {
        public static int countCycleLength(ListNode head) {
            ListNode slow = head, fast = head;

            while (fast != null && fast.next != null) {
                slow = slow.next;
                fast = fast.next.next;

                if (slow == fast) {
                    int length = 1; // count the start node - slow = slow.next;
                    slow = slow.next;

                    while (slow != fast) { // count the rest of the nodes in the cycle
                        length++;
                        slow = slow.next;
                    }

                    return length;
                }
            }
            return 0;
        }
        // Driver code
        public static void main(String args[]) {
            List<List<Integer>> inputList = Arrays.asList(
                    Arrays.asList(2, 4, 6, 8, 10, 12),
                    Arrays.asList(1, 3, 5, 7, 9, 11),
                    Arrays.asList(0, 1, 2, 3, 4, 6),
                    Arrays.asList(3, 4, 7, 9, 11, 17),
                    Arrays.asList(5, 1, 4, 9, 2, 3)
            );
            int[] pos = { 0, -1, 1, -1, 2 };
            for (int i = 0; i < inputList.size(); i++) {
                LinkedList list = new LinkedList(inputList.get(i));
                System.out.print(i + 1 + ".\tInput:");
                System.out.print("\t");
                if (pos[i] == -1) {
                    PrintList.printListWithForwardArrow(list.head);
                } else {
                    PrintList.printListWithForwardArrowLoop(list.head);
                }
                System.out.println("\n\tpos: " + pos[i]);

                if (pos[i] != -1) {
                    int length = list.getLength(list.head);
                    ListNode lastNode = list.getNode(list.head, length - 1);
                    lastNode.next = list.getNode(list.head, pos[i]);
                }
                System.out.println("\n\tCycle length = " + countCycleLength(list.head));
                System.out.println(new String(new char[100]).replace('\0', '-'));
            }
        }
    }

    public static void main(String[] args) {
        Solution.main(args);
    }

}
