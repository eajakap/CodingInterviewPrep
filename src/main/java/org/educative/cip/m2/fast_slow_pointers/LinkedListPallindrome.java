package org.educative.cip.m2.fast_slow_pointers;

import java.util.*;

public class LinkedListPallindrome {

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
        public static ListNode reverseLinkedList(ListNode slowPtr){
            ListNode prev = null;
            ListNode next = null;
            ListNode curr = slowPtr;

            while (curr != null)
            {
                next = curr.next;
                curr.next = prev;
                prev = curr;
                curr = next;
            }
            return prev;
        }
    }

    static class Solution {
        public static boolean palindrome(ListNode head) {
            // Initialize slow and fast pointers to the head of the linked list
            ListNode slow = head;
            ListNode fast = head;

            // Find the middle of the linked list using the slow and fast pointers
            while (fast != null && fast.next != null) {
                // move slow one step forward
                slow = slow.next;
                // move fast two steps forward
                fast = fast.next.next;
            }
            // Reverse the second half of the linked list starting from the middle node
            ListNode revertData = LinkedListReversal.reverseLinkedList(slow);
            // Compare the first half of the linked list with the reversed second half of the linked list
            boolean check = compareTwoHalves(head, revertData);
            // Re-reverse the second half of the linked list to restore the original linked list
            LinkedListReversal.reverseLinkedList(revertData);
            // Return True if the linked list is a palindrome, else False
            if (check) {
                return true;
            }

            return false;

        }

        public static boolean compareTwoHalves(ListNode firstHalf, ListNode secondHalf) {
            // Compare the corresponding nodes of the first and second halves of the linked list
            while (firstHalf != null && secondHalf != null) {
                if (firstHalf.val != secondHalf.val) {
                    return false;
                } else {
                    firstHalf = firstHalf.next;
                    secondHalf = secondHalf.next;
                }


            }
            return true;
        }

        // Driver code
        public static void main( String args[] ) {

            List<List<Integer>> inputList = Arrays.asList(
                    Arrays.asList(2, 4, 6, 4, 2),
                    Arrays.asList(0, 3, 5, 5, 0),
                    Arrays.asList(9, 27, 4, 4, 27, 9),
                    Arrays.asList(5, 4, 7, 9, 4, 5),
                    Arrays.asList(5, 10, 15, 20, 15, 10, 5)
            );

            for(int i=0; i<inputList.size(); i++){
                System.out.print(i+1);
                LinkedList list = new LinkedList(inputList.get(i));
                System.out.print(".\tLinked list:  ");
                PrintList.display(list.head);
                System.out.print("\tIs it a palindrome?  ");
                boolean result = palindrome(list.head);
                if(result){
                    System.out.println("Yes");
                }
                else{
                    System.out.println("No");
                }
                System.out.println(new String(new char[100]).replace('\0', '-'));
            }

        }
    }

    public static boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null) {
            return true; // An empty list or a single node is a palindrome
        }

        ListNode slow = head;
        ListNode fast = head;

        // Find the middle of the linked list
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // Reverse the second half of the linked list
        ListNode secondHalfHead = LinkedListReversal.reverseLinkedList(slow);

        // Compare the first half and the reversed second half
        ListNode firstHalfHead = head;
        while (secondHalfHead != null) {
            if (firstHalfHead.val != secondHalfHead.val) {
                return false; // Not a palindrome
            }
            firstHalfHead = firstHalfHead.next;
            secondHalfHead = secondHalfHead.next;
        }

        return true; // It's a palindrome
    }

    public static void main(String[] args) {
        ListNode head1 = new ListNode(1);
        head1.next = new ListNode(2);
        head1.next.next = new ListNode(2);
        head1.next.next.next = new ListNode(1);

        System.out.println("Is the linked list a palindrome? " + isPalindrome(head1)); // Output: true

        ListNode head2 = new ListNode(1);
        head2.next = new ListNode(2);

        System.out.println("Is the linked list a palindrome? " + isPalindrome(head2)); // Output: false
        Solution.main(args);
    }

}
