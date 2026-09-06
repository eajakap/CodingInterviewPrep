package org.educative.cip.m2.two_pointers;

import java.util.*;
/*
 * Problem: Given two nodes in a binary tree, find their lowest common ancestor (LCA).
 * The LCA of two nodes p and q in a binary tree is defined as the lowest node that has both p and q as descendants (where we allow a node to be a descendant of itself).
 * Steps to solve the problem:
 * 1. Start with two pointers, one for each node (p and q).
 * 2. Move each pointer up to its parent node until they meet.
 * 3. If a pointer reaches the root (null), redirect it to the other node (p or q).
 * 4. Continue this process until the two pointers meet at the lowest common ancestor.
 * 5. Return the node where the two pointers meet.
 *
 * Time Complexity: O(h) - where h is the height of the binary tree. In the worst case, we may need to traverse from a leaf node to the root.
 * Space Complexity: O(1) - We use a constant amount of space for pointers.
 */
public class LowestCommonAncestorBinaryTree {
    // Definiton of a binary tree node class
    static class EduTreeNode {
         int data;
         EduTreeNode left;
         EduTreeNode right;
         EduTreeNode parent;

         EduTreeNode(int value) {
             this.data = value;
             this.left = null;
             this.right = null;
             this.parent = null;
         }
     }

    static class EduBinaryTree {
        private EduTreeNode root;

        private EduTreeNode createBinaryTree(List<Integer> nodes) {
            if (nodes.isEmpty() || nodes.get(0) == 0) {
                return null;
            }
            EduTreeNode root = new EduTreeNode(nodes.get(0));
            Queue<EduTreeNode> q = new LinkedList<>();
            q.offer(root);
            int i = 1;
            while (i < nodes.size()) {
                EduTreeNode curr = q.poll();
                if (i < nodes.size() && nodes.get(i) != 0) {
                    curr.left = new EduTreeNode(nodes.get(i));
                    curr.left.parent = curr;
                    q.offer(curr.left);
                }
                i++;
                if (i < nodes.size() && nodes.get(i) != 0) {
                    curr.right = new EduTreeNode(nodes.get(i));
                    curr.right.parent = curr;
                    q.offer(curr.right);
                }
                i++;
            }
            return root;
        }

        public EduBinaryTree(List<Integer> nodes) {
            this.root = createBinaryTree(nodes);
        }

        public EduTreeNode find(EduTreeNode root, int value) {
            if (root == null) {
                return null;
            }
            Queue<EduTreeNode> q = new LinkedList<>();
            q.offer(root);
            while (!q.isEmpty()) {
                EduTreeNode currentNode = q.poll();
                if (currentNode.data == value) {
                    return currentNode;
                }
                if (currentNode.left != null) {
                    q.offer(currentNode.left);
                }
                if (currentNode.right != null) {
                    q.offer(currentNode.right);
                }
            }
            return null;
        }

        public EduTreeNode getRoot() {
            return root;
        }

        private void drawNode(List<StringBuilder> output, List<StringBuilder> linkAbove,
                              EduTreeNode node, int level, int p, char linkChar) {
            if (node == null) return;
            String SP = " ";
            int h = output.size();

            if (p < 0) {
                for (StringBuilder s : output)
                    if (s.length() > 0)
                        s.insert(0, " ".repeat(-p));
                for (StringBuilder s : linkAbove)
                    if (s.length() > 0)
                        s.insert(0, " ".repeat(-p));
            }

            if (level < h - 1) p = Math.max(p, output.get(level + 1).length());
            if (level > 0) p = Math.max(p, output.get(level - 1).length());
            p = Math.max(p, output.get(level).length());

            if (node.left != null) {
                String leftData = SP + node.left.data + SP;
                drawNode(output, linkAbove, node.left, level + 1, p - leftData.length(), 'L');
                p = Math.max(p, output.get(level + 1).length());
            }

            int space = p - output.get(level).length();
            if (space > 0) output.get(level).append(" ".repeat(space));
            output.get(level).append(SP + node.data + SP);

            space = p + SP.length() - linkAbove.get(level).length();
            if (space > 0) linkAbove.get(level).append(" ".repeat(space));
            linkAbove.get(level).append(linkChar);

            if (node.right != null)
                drawNode(output, linkAbove, node.right, level + 1, output.get(level).length(), 'R');
        }

        // --- Display tree methods ---
        private int treeHeight(EduTreeNode node) {
            if (node == null) return 0;
            return 1 + Math.max(treeHeight(node.left), treeHeight(node.right));
        }

        public void displayTree(EduTreeNode root) {
            if (root == null) {
                System.out.println("\tnull");
                return;
            }
            int h = treeHeight(root);
            List<StringBuilder> output = new ArrayList<>();
            List<StringBuilder> linkAbove = new ArrayList<>();
            for (int i = 0; i < h; i++) {
                output.add(new StringBuilder());
                linkAbove.add(new StringBuilder());
            }
            drawNode(output, linkAbove, root, 0, 5, ' ');

            for (int i = 1; i < h; i++) {
                for (int j = 0; j < linkAbove.get(i).length(); j++) {
                    if (linkAbove.get(i).charAt(j) != ' ') {
                        int size = output.get(i - 1).length();
                        if (size < j + 1)
                            output.get(i - 1).append(" ".repeat(j + 1 - size));
                        int jj = j;
                        if (linkAbove.get(i).charAt(j) == 'L') {
                            while (jj < output.get(i - 1).length() && output.get(i - 1).charAt(jj) == ' ')
                                jj++;
                            for (int k = j + 1; k < jj - 1 && k < output.get(i - 1).length(); k++)
                                output.get(i - 1).setCharAt(k, '_');
                        } else if (linkAbove.get(i).charAt(j) == 'R') {
                            while (jj >= 0 && output.get(i - 1).charAt(jj) == ' ')
                                jj--;
                            for (int k = j - 1; k > jj && k >= 0; k--)
                                output.get(i - 1).setCharAt(k, '_');
                        }
                        linkAbove.get(i).setCharAt(j, '|');
                    }
                }
            }

            for (int i = 0; i < h; i++) {
                if (i > 0) System.out.println("\t" + linkAbove.get(i));
                System.out.println("\t" + output.get(i));
            }
        }

    }

    public static EduTreeNode LowestCommonAncestor(EduTreeNode p, EduTreeNode q) {
        EduTreeNode ptr1 = p;
        EduTreeNode ptr2 = q;

        while (ptr1 != ptr2) {
            if (ptr1.parent != null) {
                ptr1 = ptr1.parent;
            }
            else {
                ptr1 = q;
            }

            if (ptr2.parent != null) {
                ptr2 = ptr2.parent;
            } else {
                ptr2 = p;
            }
        }

        return ptr1;
    }

    public static void main(String[] args) {
        List<List<Integer>> input_trees = Arrays.asList(
                Arrays.asList(100, 50, 200, 25, 75, 350),
                Arrays.asList(100, 200, 75, 50, 25, 350),
                Arrays.asList(350, 100, 75, 50, 200, 25),
                Arrays.asList(100, 50, 200, 25, 75, 350),
                Arrays.asList(25, 50, 75, 100, 200, 350)
        );
        List<List<Integer>> input_nodes = Arrays.asList(
                Arrays.asList(25, 75),
                Arrays.asList(50, 350),
                Arrays.asList(100, 200),
                Arrays.asList(50, 25),
                Arrays.asList(350, 200)
        );

        for (int i = 0; i < input_trees.size(); i++) {
            EduBinaryTree tree = new EduBinaryTree(input_trees.get(i));
            System.out.println((i + 1) + ".\tBinary tree:");
            tree.displayTree(tree.getRoot());
            System.out.println("\n\tp = " + input_nodes.get(i).get(0));
            System.out.println("\tq = " + input_nodes.get(i).get(1));
            EduTreeNode p = tree.find(tree.getRoot(), input_nodes.get(i).get(0));
            EduTreeNode q = tree.find(tree.getRoot(), input_nodes.get(i).get(1));
            EduTreeNode lca = LowestCommonAncestor(p, q);
            System.out.println("\n\tLowest common ancestor: " + lca.data);
            System.out.println(new String(new char[100]).replace("\0", "-"));
        }
    }

}
