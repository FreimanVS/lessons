package com.vfreiman.lessons._1_algorithms_and_data_structures;

public class Tree {
    private Node root;

    private class Node {
        int value;
        Node left;
        Node right;

        public Node(int value, Node left, Node right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }

        public Node(int value) {
            this(value, null, null);
        }
    }

    private void insert(int n) {
        insert(root, n);
    }

    private void insert(Node node, int n) {
        if (root == null) {
            root = new Node(n);
            return;
        }
        if (n >= node.value) {
            if (node.right == null) {
                node.right = new Node(n);
            } else {
                insert(node.right, n);
            }
        } else {
            if (node.left == null) {
                node.left = new Node(n);
            } else {
                insert(node.left, n);
            }
        }
    }

    private void postOrder() {
        postOrder(root);
    }

    private void postOrder(Node node) {
        if (node == null)
            return;
        postOrder(node.left);
        postOrder(node.right);
        System.out.print(node.value + " ");
    }
}
