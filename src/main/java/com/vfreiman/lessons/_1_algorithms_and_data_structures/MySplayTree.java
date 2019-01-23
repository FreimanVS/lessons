package com.vfreiman.lessons._1_algorithms_and_data_structures;

import java.util.Scanner;

class MySplayTree {
    private Node root;

    private class Node {
        private Node left;
        private Node right;
        private Node parent;
        private int fullAmount;
        private int ch;

        private void reNumber() {
            int leftFullAmount = left != null ? left.fullAmount : 0;
            int rightFullAmount = right != null ? right.fullAmount : 0;
            fullAmount = leftFullAmount + rightFullAmount + 1;
        }

        private Node(int ch) {
            this.ch = ch;
            reNumber();
        }

        private void setLeft(Node left) {
            this.left = left;
            reNumber();
        }

        private void setRight(Node right) {
            this.right = right;
            reNumber();
        }

        private void setParent(Node parent) {
            this.parent = parent;
            if (parent != null) {
                this.parent.reNumber();
            }
        }
    }

    public void merge(Node left, Node right) {
        if (left != null)
            mergeLeft(left);
        if (right != null)
            mergeRight(right);
    }

    public void mergeLeft(Node v1) {
        if (root != null && v1 != null) {
            min(root);
            root.setLeft(v1);
            v1.setParent(root);
        } else if (root == null && v1 != null) {
            root = v1;
        }
    }

    public void mergeRight(Node v2) {
        if (root != null && v2 != null) {
            max(root);
            root.setRight(v2);
            v2.setParent(root);
        } else if (root == null && v2 != null) {
            root = v2;
        }
    }

    public void insert(int ch) {
        if (root == null) {
            root = new Node(ch);
            return;
        }
        if (root.right == null) {
            root.setRight(new Node(ch));
            root.right.setParent(root);
            splay(root.right);
        } else {
            min(root.right);
            root.setLeft(new Node(ch));
            root.left.setParent(root);
            splay(root.left);
        }
    }

    public Node[] split(int from, int to) {
        if (this.root == null) {
            return null;
        }
        Node leftPart = splitLeft(from);
        Node rightPart = splitRight(to);
        return new Node[]{leftPart, this.root, rightPart};
    }

    public Node splitLeft(int k) {
        if (this.root == null) {
            return null;
        }
        search(k);
        if (root.left != null) {
            Node left = root.left;
            root.left.setParent(null);
            root.setLeft(null);
            return left;
        } else {
            return null;
        }
    }

    public Node splitRight(int k) {
        if (this.root == null) {
            return null;
        }
        search(k);
        if (root.right != null) {
            Node right = root.right;
            root.right.setParent(null);
            root.setRight(null);
            return right;
        } else {
            return null;
        }
    }

    public Node search(int k) {
        if (root == null) {
            return null;
        }
        Node node = root;
        int curIndex;
        while (true) {
            curIndex = node.left != null ? node.left.fullAmount + 1 : 1;
            if (curIndex == k) {
                break;
            }
            if (k < curIndex) {
                node = node.left;
            } else {
                node = node.right;
                k -= curIndex;
            }
        }
        splay(node);
        return node;
    }

    public void min(Node node) {
        if (node == null) {
            return;
        }
        Node min = node;
        while (min.left != null) {
            min = min.left;
        }
        splay(min);
    }

    public void max(Node node) {
        if (node == null) {
            return;
        }
        Node max = node;
        while (max.right != null) {
            max = max.right;
        }
        splay(max);
    }

    private void splay(Node u) {
        if (u == null) {
            return;
        }
        while (u.parent != null) {
            //if it's a left child
            if (u.parent.left == u) {
                // if there's a grandfather
                // u.parent != root
                if (u.parent.parent != null) {
                    // if its father is the grandfather's left child
                    // ZIGZIG
                    if (u.parent.parent.left == u.parent) {
                        Node uRight = u.right;
                        Node p = u.parent;
                        Node pRight = u.parent.right;
                        Node gp = u.parent.parent;
                        Node gpParent = u.parent.parent.parent;
                        boolean gpIsLeft = u.parent.parent.parent != null
                                && u.parent.parent.parent.left == u.parent.parent;

                        //disjunction
                        if (uRight != null) {
                            u.setRight(null);
                            uRight.setParent(null);
                        }

                        u.setParent(null);
                        p.setLeft(null);

                        if (pRight != null) {
                            pRight.setParent(null);
                            p.setRight(null);
                        }

                        p.setParent(null);
                        gp.setLeft(null);

                        //conjunction
                        if (pRight != null) {
                            gp.setLeft(pRight);
                            pRight.setParent(gp);
                        }

                        if (uRight != null) {
                            p.setLeft(uRight);
                            uRight.setParent(p);
                        }

                        p.setRight(gp);
                        gp.setParent(p);

                        u.setRight(p);
                        p.setParent(u);

                        if (gpParent != null) {
                            u.setParent(gpParent);
                            if (gpIsLeft) {
                                gpParent.setLeft(u);
                            } else {
                                gpParent.setRight(u);
                            }
                        } else {
                            u.setParent(null);
                        }
                    } else { //its father is the grandfather's right child //ZIGZAG
                        Node uLeft = u.left;
                        Node uRight = u.right;
                        Node p = u.parent;
                        Node gp = u.parent.parent;

                        Node gpParent = u.parent.parent.parent;
                        boolean gpIsRight = u.parent.parent.parent != null
                                && u.parent.parent.parent.right == u.parent.parent;

                        //disjunction
                        if (uLeft != null) {
                            uLeft.setParent(null);
                            u.setLeft(null);
                        }

                        if (uRight != null) {
                            uRight.setParent(null);
                            u.setRight(null);
                        }

                        u.setParent(null);
                        p.setLeft(null);
                        p.setParent(null);
                        gp.setRight(null);

                        //conjunction
                        if (uLeft != null) {
                            gp.setRight(uLeft);
                            uLeft.setParent(gp);
                        }

                        if (uRight != null) {
                            p.setLeft(uRight);
                            uRight.setParent(p);
                        }

                        u.setLeft(gp);
                        gp.setParent(u);

                        u.setRight(p);
                        p.setParent(u);

                        if (gpParent != null) {
                            u.setParent(gpParent);
                            if (gpIsRight) {
                                gpParent.setRight(u);
                            } else {
                                gpParent.setLeft(u);
                            }
                        } else {
                            u.setParent(null);
                        }
                    }
                } else { //u.parent == root      ZIG
                    Node uRight = u.right;
                    Node p = u.parent;

                    if (uRight != null) {
                        u.setRight(null);
                        uRight.setParent(null);
                    }

                    u.setParent(null);
                    p.setLeft(null);

                    if (uRight != null) {
                        p.setLeft(uRight);
                        uRight.setParent(p);
                    }

                    u.setRight(p);
                    p.setParent(u);
                }

            } else { // if it's a right child

                // if there's a grandfather
                // u.parent != root
                if (u.parent.parent != null) {
                    // if its father is the grandfather's right child
                    // ZAGZAG
                    if (u.parent.parent.right == u.parent) {
                        Node uLeft = u.left;
                        Node p = u.parent;
                        Node pLeft = u.parent.left;
                        Node gp = u.parent.parent;
                        Node gpParent = u.parent.parent.parent;
                        boolean gpIsRight = u.parent.parent.parent != null
                                && u.parent.parent.parent.right == u.parent.parent;

                        //disjunction
                        if (uLeft != null) {
                            u.setLeft(null);
                            uLeft.setParent(null);
                        }

                        u.setParent(null);
                        p.setRight(null);

                        if (pLeft != null) {
                            pLeft.setParent(null);
                            p.setLeft(null);
                        }

                        p.setParent(null);
                        gp.setRight(null);

                        //conjunction
                        if (pLeft != null) {
                            gp.setRight(pLeft);
                            pLeft.setParent(gp);
                        }

                        if (uLeft != null) {
                            p.setRight(uLeft);
                            uLeft.setParent(p);
                        }

                        p.setLeft(gp);
                        gp.setParent(p);

                        u.setLeft(p);
                        p.setParent(u);

                        if (gpParent != null) {
                            u.setParent(gpParent);
                            if (gpIsRight) {
                                gpParent.setRight(u);
                            } else {
                                gpParent.setLeft(u);
                            }
                        } else {
                            u.setParent(null);
                        }
                    } else { //its father is the grandfather's left child //ZAGZIG
                        Node uLeft = u.left;
                        Node uRight = u.right;
                        Node p = u.parent;
                        Node gp = u.parent.parent;

                        Node gpParent = u.parent.parent.parent;
                        boolean gpIsLeft = u.parent.parent.parent != null
                                && u.parent.parent.parent.left == u.parent.parent;

                        //disjunction
                        if (uRight != null) {
                            uRight.setParent(null);
                            u.setRight(null);
                        }


                        if (uLeft != null) {
                            uLeft.setParent(null);
                            u.setLeft(null);
                        }

                        u.setParent(null);
                        p.setRight(null);
                        p.setParent(null);
                        gp.setLeft(null);

                        //conjunction
                        if (uRight != null) {
                            gp.setLeft(uRight);
                            uRight.setParent(gp);
                        }


                        if (uLeft != null) {
                            p.setRight(uLeft);
                            uLeft.setParent(p);
                        }

                        u.setRight(gp);
                        gp.setParent(u);

                        u.setLeft(p);
                        p.setParent(u);

                        if (gpParent != null) {
                            u.setParent(gpParent);
                            if (gpIsLeft) {
                                gpParent.setLeft(u);
                            } else {
                                gpParent.setRight(u);
                            }
                        } else {
                            u.setParent(null);
                        }
                    }
                } else { //u.parent == root      ZAG
                    Node uLeft = u.left;
                    Node p = u.parent;

                    if (uLeft != null) {
                        u.setLeft(null);
                        uLeft.setParent(null);
                    }

                    u.setParent(null);
                    p.setRight(null);

                    if (uLeft != null) {
                        p.setRight(uLeft);
                        uLeft.setParent(p);
                    }

                    u.setLeft(p);
                    p.setParent(u);
                }
            }
        }
        root = u;
    }

    public void inorder() {
        inorder(root);
    }

    public void inorder(Node node) {
        if (node != null) {
            inorder(node.left);
            System.out.print((char) node.ch);
            inorder(node.right);
        }
    }


    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            String s = sc.next();
            MySplayTree splayTree = new MySplayTree();
            s.chars().forEach(splayTree::insert);
            int numberOfRequests = sc.nextInt();
            for (int i = 0; i < numberOfRequests; i++) {
                int from = sc.nextInt() + 1;
                int to = sc.nextInt() - from + 2;
                int input = sc.nextInt();
                MySplayTree.Node[] threeParts = splayTree.split(from, to);
                MySplayTree.Node leftPart = threeParts[0];
                MySplayTree.Node rightPart = threeParts[2];
                MySplayTree.Node thisPart = threeParts[1];
                if (input != 0) {
                    splayTree.root = rightPart;
                    splayTree.merge(leftPart, null);
                    MySplayTree.Node newRightPart = splayTree.splitRight(input);
                    splayTree.merge(null, thisPart);
                    splayTree.merge(null, newRightPart);
                } else {
                    splayTree.merge(null, leftPart);
                    splayTree.merge(null, rightPart);
                }
            }
            splayTree.inorder();
        }
    }
}
