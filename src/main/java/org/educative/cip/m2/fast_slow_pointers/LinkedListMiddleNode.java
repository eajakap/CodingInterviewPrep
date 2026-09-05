package org.educative.cip.m2.fast_slow_pointers;
import java.util.Arrays;
import java.util.List;
/*
 * Time Complexity: O(n) - We traverse the linked list once to find the middle node.
 * Space Complexity: O(1) - We use a constant amount of space for pointers.
 */

public class LinkedListMiddleNode {

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

    public static ListNode middleNode(ListNode head) {

        ListNode slow = head;
        ListNode fast = head;

        // Odd Nodes : Slow will be at the middle node, Fast will be at the last node (fast.next == null/none)
        // Even Nodes : Slow will be at the second middle node, Fast will be at the None node (fast == null/none)
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    // Driver code
    public static void main( String args[] ) {

        List<List<Integer>> inputs = Arrays.asList(
                Arrays.asList(1, 2, 3, 4, 5),
                Arrays.asList(1, 2, 3, 4, 5, 6),
                Arrays.asList(3, 2, 1),
                Arrays.asList(10),
                Arrays.asList(1, 2)
        );

        for(int i=0; i<inputs.size(); i++){
            System.out.print(i+1);
            LinkedList inputLinkedList = new LinkedList(inputs.get(i));
            System.out.print(".\tInput linked list:  ");
            PrintList.display(inputLinkedList.head);
            System.out.print("\tMiddle of the linked list is:  " );
            System.out.println(middleNode(inputLinkedList.head).val);
            System.out.println(new String(new char[100]).replace('\0', '-'));
        }
    }

}
