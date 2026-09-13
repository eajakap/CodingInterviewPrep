package org.educative.cip.m2.linked_list;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * INSERT INTO SORTED CIRCULAR LINKED LIST
 * Problem: Given a sorted circular linked list and a value to insert, insert the value into the list while maintaining the sorted order. Return the head of the modified list.
 * Example 1:
 * Input: head = [1,2,4], insertVal = 3
 * Output: [1,2,3,4]
 * Example 2:
 * Input: head = [], insertVal = 1
 * Output: [1]
 * Example 3:
 * Input: head = [3,5,1], insertVal = 0
 * Output: [3,5,0,1]
 * Constraints:
 * The number of nodes in the list is in the range [0, 10^3].
 * -10^3 <= Node.val <= 10^3
 * -10^3 <= insertVal <= 10^3
 * The list is sorted in ascending order.
 *
 * Steps to solve the problem:
 * 1. If the list is empty, create a new node with the given value and point its next to itself. Return the new node as the head of the list.
 * 2. If the list is not empty, traverse the list to find the appropriate position to insert the new value while maintaining the sorted order.
 * 3. If the new value is less than or equal to the current node's value and greater than or equal to the previous node's value, insert the new node between the previous and current nodes.
 * 4. If the new value is greater than the current node's value and less than or equal to the next node's value, insert the new node between the current and next nodes.
 * 5. If the new value is greater than all existing values in the list, insert the new node after the last node and point its next to the head of the list.
 * 6. Return the head of the modified list.
 *
 * Time Complexity: O(n), where n is the number of nodes in the circular linked list. In the worst case, we may need to traverse all nodes to find the appropriate position for insertion.
 * Space Complexity: O(1), as we are using a constant amount of extra space for pointers and variables.
 */
public class InsertIntoSortedCircularLinkedList {
    /**
     * Represents a single node in a singly linked list.
     */
    static class Node {
        int val;
        Node next;

        // Constructor
        public Node(int val) {
            this.val = val;
            this.next = null;
        }

        public Node(int val, Node next) {
            this.val = val;
            this.next = next;
        }
    }
    /**
     * Utility class for building a linked list from a collection of integer values.
     */
    static class EduLinkedList {
        Node head;

        // Constructor to initialize from a list of values
        public EduLinkedList(List<Integer> values) {
            this.head = null;
            if (values != null && !values.isEmpty()) {
                createLinkedList(values);
            }
        }

        // Function to create the linked list from a list of values
        public void createLinkedList(List<Integer> values) {
            if (values == null || values.isEmpty()) {
                head = null;
                return;
            }

            head = new Node(values.get(0));
            Node current = head;
            for (int i = 1; i < values.size(); i++) {
                current.next = new Node(values.get(i));
                current = current.next;
            }
            head = makeCircular(head);
        }

        // Function to make the list circular
        public Node makeCircular(Node head) {
            if (head == null) return null;

            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = head;  // Make it circular
            return head;
        }

        // Function to display the linked list
        public static void display(Node head, int limit) {
            if (head == null) {
                System.out.println("[]");
                return;
            }

            Node current = head;
            Node first = head;
            int count = 0;

            while (current != null && count < limit) {
                System.out.print(current.val);
                current = current.next;
                if (current != first && count < limit - 1) {
                    System.out.print(" -> ");
                }
                count++;
                if (current == first) break;
            }
            if (count >= limit) {
                System.out.println(" -> ... (back to head)");
            } else {
                System.out.println(" -> ... (back to head)");
            }
        }
    }

    /**
     * Inserts a new value into a sorted circular linked list while maintaining the sorted order.
     * Steps:
     * 1. If the list is empty, create a new node with the given value and point its next to itself. Return the new node as the head of the list.
     * 2. If the list is not empty, traverse the list to find the appropriate position to insert the new value while maintaining the sorted order.
     * 3. If the new value is less than or equal to the current node's value and greater than or equal to the previous node's value, insert the new node between the previous and current nodes.
     * 4. If the new value is greater than the current node's value and less than or equal to the next node's value, insert the new node between the current and next nodes.
     * 5. If the new value is greater than all existing values in the list, insert the new node after the last node and point its next to the head of the list.
     * 6. Return the head of the modified list.
     *
     * Time Complexity: O(n), where n is the number of nodes in the circular linked list. In the worst case, we may need to traverse all nodes to find the appropriate position for insertion.
     * Space Complexity: O(1), as we are using a constant amount of extra space for pointers and variables.
     *
     * @param head      The head of the circular linked list.
     * @param insertVal The value to be inserted.
     * @return The head of the modified circular linked list.
     */
    public Node insert(Node head, int insertVal)
    {
        if (head == null) {
            Node newNode = new Node(insertVal);
            newNode.next = newNode;  // Make it circular
            return newNode;
        }

        Node prev = head;
        Node curr = head.next;
        boolean flag = false;

        while (true) {
            if (prev.val <= insertVal && insertVal <= curr.val) {
                flag = true;
            }
            else if (prev.val > curr.val) {
                if (insertVal >= prev.val || insertVal <= curr.val) {
                    flag = true;
                }
            }

            if (flag) {
                prev.next = new Node(insertVal, curr);
                return head;
            }

            prev = curr;
            curr = curr.next;

            if (prev == head) {
                // If we have traversed the entire list and didn't find a suitable position
                break;
            }
        }
        // If we reach here, it means the new value is either the largest or smallest in the list
        prev.next = new Node(insertVal, curr);
        return head;
    }

    public static void main(String[] args) {
        List<List<Integer>> inputLists = Arrays.asList(
                Arrays.asList(3, 4, 1),
                Arrays.asList(),
                Arrays.asList(1),
                Arrays.asList(2, 2, 2),
                Arrays.asList(5, 1, 3)
        );

        List<Integer> insertVals = Arrays.asList(2, 1, 0, 3, 6);

        for (int i = 0; i < inputLists.size(); i++) {
            List<Integer> lst = inputLists.get(i);
            int insertVal = insertVals.get(i);

            EduLinkedList inputLinkedList = new EduLinkedList(lst);
            System.out.print((i + 1) + ". \tInput linked list: ");
            EduLinkedList.display(inputLinkedList.head, 20);

            System.out.println("\tValue to insert: " + insertVal);

            System.out.print("\n\tUpdated linked list: ");
            InsertIntoSortedCircularLinkedList obj=new InsertIntoSortedCircularLinkedList();
            EduLinkedList.display(obj.insert(inputLinkedList.head, insertVal), 20);

            System.out.println(String.join("", Collections.nCopies(100, "-")));
        }
    }

}
