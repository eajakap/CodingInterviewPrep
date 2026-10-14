package org.educative.cip.m2.linked_list;

/**
 * Given two linked lists and two positions a and b, merge the second linked list into the first linked list
 *  between the nodes at positions a and b. The nodes at positions a and b in the first linked list should be removed,
 *  and the second linked list should be inserted in their place.
 *  Example:
 *  Input: list1 = [0,1,2,3,4,5], a = 3, b = 4, list2 = [1000000,1000001,1000002]
 *  Output: [0,1,2,1000000,1000001,1000002,5]
 *  Explanation: The nodes at positions 3 and 4 in list1 are removed, and list2 is inserted in their place.
 *  Time Complexity: O(n + m), where n is the length of list1 and m is the length of list2.
 *  We traverse both lists once.
 *  Space Complexity: O(1), as we are modifying the lists in place and not using any extra space.
 */
public class MergeInBetweenLinkedLists {

    private static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
            this.next = null;
        }
    }

    public ListNode mergeInBetween(ListNode list1, int a, int b, ListNode list2) {
        ListNode current = list1;
        ListNode prevA = null;
        ListNode nextB = null;

        // Traverse to the node just before position a
        for (int i = 0; i < a; i++) {
            prevA = current;
            current = current.next;
        }

        // Traverse to the node at position b
        for (int i = a; i <= b; i++) {
            current = current.next;
        }
        nextB = current;

        // Connect the previous node of a to the head of list2
        if (prevA != null) {
            prevA.next = list2;
        }

        // Find the tail of list2
        ListNode tailList2 = list2;
        while (tailList2.next != null) {
            tailList2 = tailList2.next;
        }

        // Connect the tail of list2 to the node after b
        tailList2.next = nextB;

        return list1;
    }

    public static void main(String[] args) {
        MergeInBetweenLinkedLists solution = new MergeInBetweenLinkedLists();

        // Create list1: 0 -> 1 -> 2 -> 3 -> 4 -> 5
        ListNode list1 = new ListNode(0);
        list1.next = new ListNode(1);
        list1.next.next = new ListNode(2);
        list1.next.next.next = new ListNode(3);
        list1.next.next.next.next = new ListNode(4);
        list1.next.next.next.next.next = new ListNode(5);

        // Create list2: 1000000 -> 1000001 -> 1000002
        ListNode list2 = new ListNode(1000000);
        list2.next = new ListNode(1000001);
        list2.next.next = new ListNode(1000002);

        // Merge list2 into list1 between positions 3 and 4
        ListNode mergedList = solution.mergeInBetween(list1, 3, 4, list2);

        // Print the merged list
        while (mergedList != null) {
            System.out.print(mergedList.val + " ");
            mergedList = mergedList.next;
        }
    }
}
