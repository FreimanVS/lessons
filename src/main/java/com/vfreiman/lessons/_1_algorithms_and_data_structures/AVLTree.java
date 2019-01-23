package com.vfreiman.lessons._1_algorithms_and_data_structures;

public class AVLTree {
    public Node root;

    public class Node {
        public Node left;
        public Node right;
        public Node parent;
        public int value;
        public long sum;
        public int h;
        public int size;

        public void reSize() {
            int leftSize = left != null ? left.size : 0;
            int rightSize = right != null ? right.size : 0;
            size = leftSize
                    + rightSize
                    + 1;
        }

        public void reSum() {
            long leftSum = left != null ? left.sum : 0L;
            long rightSum = right != null ? right.sum : 0L;
            sum = leftSum + rightSum + value;
        }

        public void reH() {
            int leftH = left != null ? left.h : 0;
            int rightH = right != null ? right.h : 0;
            int max = leftH > rightH ? leftH : rightH;
            h = max + 1;
            if (parent != null) {
                parent.update();
            }
        }

        public void update() {
            reH();
            reSum();
            reSize();
        }

        public Node(int value) {
            this.value = value;
            update();
        }

        public void setLeft(Node left) {
            this.left = left;
            update();

        }

        public void setRight(Node right) {
            this.right = right;
            update();
        }

        public void setParent(Node parent) {
            this.parent = parent;
            if (parent != null) {
                this.parent.update();
            }
        }

        public int getValue() {
            return value;
        }
    }


    public Node orderStatistics(int k) {
        if (root == null) {
            return null;
        }
        return orderStatistics(root, k);
    }

    public Node orderStatistics(Node v, int index) {
        int leftSize = v.left != null ? v.left.size : 0;
        if (index == leftSize + 1) {
            return v;
        } else if (index < leftSize + 1) {
            return orderStatistics(v.left, index);
        } else {
            return orderStatistics(v.right, index - leftSize - 1);
        }
    }

    public boolean exists(int k) {
        return search(k).value == k;
    }

    Node workNode = null;
    public void rotate(Node node) {
        workNode = node;
        int leftH = workNode.left != null ? workNode.left.h : 0;
        int rightH = workNode.right != null ? workNode.right.h : 0;

        while (workNode.parent != null) {
            if (Math.abs(rightH - leftH) > 1) {
                if ((rightH - leftH) > 1) {
                    rotateRight(workNode);
                } else if ((leftH - rightH) > 1) {
                    rotateLeft(workNode);
                }
            }
            if (workNode != null) {
                if (workNode.parent != null) {
                    workNode = workNode.parent;
                }
            }
            leftH = workNode.left != null ? workNode.left.h : 0;
            rightH = workNode.right != null ? workNode.right.h : 0;
        }
        if (Math.abs(rightH - leftH) > 1) {
            if ((rightH - leftH) > 1) {
                rotateRight(workNode);
            } else if ((leftH - rightH) > 1) {
                rotateLeft(workNode);
            }
        }
        while (workNode.parent != null) {
            workNode = workNode.parent;
        }
        root = workNode;
    }

    public void rotateRight(Node node) {
        //a small right rotate
        if (node.right.right != null && node.right.left != null && node.right.right.h >= node.right.left.h
                || node.right.left == null && node.right.h > 1
        ) {
            Node parentA = node.parent;
            Node a = node;
            Node b = node.right;
            Node leftB = node.right.left;
            //disjunction
            a.setRight(null);
            b.setParent(null);
            if (leftB != null) {
                b.setLeft(null);
                leftB.setParent(null);
            }
            //if A is the left child
            boolean isLeft = parentA != null && parentA.left != null && parentA.left == a;
            if (parentA != null) {
                if (isLeft) {
                    parentA.setLeft(null);
                } else { // if A is the right child
                    parentA.setRight(null);
                }
                a.setParent(null);
            }
            //conjuction
            if (leftB != null) {
                a.setRight(leftB);
                leftB.setParent(a);
            }
            b.setLeft(a);
            a.setParent(b);
            if (parentA != null) {
                if (isLeft) {
                    parentA.setLeft(b);
                } else {
                    parentA.setRight(b);
                }
                b.setParent(parentA);
            }
            workNode = b;
        } else if(node.right.right != null && node.right.left != null && node.right.left.h > node.right.right.h
                || node.right.right == null && node.right.h > 1)
        { // a big right rotate
            Node parentA = node.parent;
            Node a = node;
            Node b = node.right;
            Node g = node.right.left;
            Node B = null;
            Node C = null;
            if (g != null) {
                B = node.right.left.left;
                C = node.right.left.right;
            }
            //disjunction
            boolean isLeft = parentA != null && parentA.left != null && parentA.left == a;

            if (parentA != null) {
                if (isLeft) {
                    parentA.setLeft(null);
                } else {
                    parentA.setRight(null);
                }
                a.setParent(null);
            }
            a.setRight(null);
            b.setParent(null);
            if (g != null)
                g.setParent(null);
            b.setLeft(null);
            if (g != null) {
                g.setLeft(null);
                g.setRight(null);
            }
            if (B != null)
                B.setParent(null);
            if (C != null)
                C.setParent(null);
            //conjuction
            if (parentA != null) {
                if (isLeft) {
                    parentA.setLeft(g);
                } else {
                    parentA.setRight(g);
                }
                if (g != null) {
                    g.setParent(parentA);
                }
            }
            if (g != null) {
                g.setLeft(a);
                g.setRight(b);
            }
            a.setParent(g);
            b.setParent(g);
            a.setRight(B);
            if (B != null)
                B.setParent(a);
            b.setLeft(C);
            if (C != null)
                C.setParent(b);
            workNode = g;
        }
    }

    public void rotateLeft(Node node) {
        //a small left rotate
        if (

                node.left.left != null && node.left.right != null && node.left.left.h >= node.left.right.h
                        || node.left.right == null && node.left.h > 1) {
            Node parentA = node.parent;
            Node a = node;
            Node b = node.left;
            Node rightB = node.left.right;
            //disjunction
            a.setLeft(null);
            b.setParent(null);
            if (rightB != null) {
                b.setRight(null);
                rightB.setParent(null);
            }
            //if A is the left child
            boolean isRight = parentA != null && parentA.right != null && parentA.right == a;
            if (parentA != null) {
                if (isRight) {
                    parentA.setRight(null);
                } else { // if A is the right child
                    parentA.setLeft(null);
                }
                a.setParent(null);
            }
            //conjuction
            if (rightB != null) {
                a.setLeft(rightB);
                rightB.setParent(a);
            }
            b.setRight(a);
            a.setParent(b);
            if (parentA != null) {
                if (isRight) {
                    parentA.setRight(b);
                } else {
                    parentA.setLeft(b);
                }
                b.setParent(parentA);
            }
            workNode = b;
        } else if (node.left.left != null && node.left.right != null && node.left.right.h > node.left.left.h
                || node.left.left == null && node.left.h > 1)
        { // a big left rotate
            Node parentA = node.parent;
            Node a = node;
            Node b = node.left;
            Node g = node.left.right;
            Node B = null;
            Node C = null;
            if (g != null) {
                B = node.left.right.right;
                C = node.left.right.left;
            }
            //disjunction
            boolean isRight = parentA != null && parentA.right != null && parentA.right == a;
            if (parentA != null) {
                if (isRight) {
                    parentA.setRight(null);
                } else {
                    parentA.setLeft(null);
                }
                a.setParent(null);
            }
            a.setLeft(null);
            b.setParent(null);
            if (g != null)
                g.setParent(null);
            b.setRight(null);
            if (g != null) {
                g.setRight(null);
                g.setLeft(null);
            }
            if (B != null) {
                B.setParent(null);
            }
            if (C != null) {
                C.setParent(null);
            }
            //conjuction
            if (parentA != null) {
                if (isRight) {
                    parentA.setRight(g);
                } else {
                    parentA.setLeft(g);
                }
                if (g != null)
                    g.setParent(parentA);
            }
            if (g != null) {
                g.setRight(a);
                g.setLeft(b);
            }
            a.setParent(g);
            b.setParent(g);
            a.setLeft(B);
            if (B != null) {
                B.setParent(a);
            }
            b.setRight(C);
            if (C != null) {
                C.setParent(b);
            }
            workNode = g;
        }
    }

    public void remove(int k) {
        if (root == null) {
            return;
        }
        Node removingNode = search(k);
        if (removingNode.value != k) {
            return;
        }
        remove(removingNode);
    }

    public void remove(Node removingNode) {
        if (root == null) {
            return;
        }

            /*if (removingNode == root) {
                if (root.left != null && root.right != null) {

//                    Node newRoot = root.right.h > root.left.h ? root.right : root.left;
                    Node newRoot = min(root.right);
                    Node hisParent = newRoot.parent;
                    if (hisParent == root) {
                        root.left.setParent(newRoot);
                        newRoot.setLeft(root.left);
                        root = newRoot;
                        root.setParent(null);
                        //probably NOT
                        rotate(root.left);
                    } else {
                        boolean isLeftChild = hisParent.left != null && hisParent.left == newRoot;
                        if (isLeftChild) {
                            hisParent.setLeft(null);
                        } else {
                            hisParent.setRight(null);
                        }
                        Node right = root.right;
                        root.left.setParent(newRoot);
                        newRoot.setLeft(root.left);
                        newRoot.setParent(null);
                        newRoot.setRight(right);
                        right.setParent(newRoot);
                        root = newRoot;
                        rotate(hisParent);
                    }
                } else if (root.left == null && root.right == null) {
                    root = null;
                } else if (root.left == null) {
                    root = root.right;
                    root.setParent(null);
                    rotate(root);
                } else {
                    root = root.left;
                    root.setParent(null);
                    rotate(root);
                }
                return;
            }*/

        //if it's a leaf
        if (removingNode.left == null && removingNode.right == null) {

            //if it's a left child
            Node parent = removingNode.parent;
            if (parent != null) {
                if (parent.left != null && parent.left == removingNode) {
                    parent.setLeft(null);
                } else { //if it's a right child
                    parent.setRight(null);
                }
                removingNode.setParent(null);
                rotate(parent);
            } else {
                root = null;
            }

            //if there is only 1 child
        } else if ((removingNode.left != null && removingNode.right == null)
                || (removingNode.left == null && removingNode.right != null)) {
            Node parent = removingNode.parent;
            Node child = removingNode.left != null ? removingNode.left : removingNode.right;
            if (parent != null) {
                if (removingNode.parent.left != null && removingNode.parent.left == removingNode) {
                    parent.setLeft(child);
                } else {
                    parent.setRight(child);
                }
                child.setParent(parent);
                rotate(child);
            } else {
                child.setParent(null);
                root = child;
            }
            //if there are 2 childrens
        } else if (removingNode.left != null && removingNode.right != null) {
            Node v = removingNode;
            Node v1 = max(removingNode.left);
            Node vParent = v.parent;
            Node vLeft = v.left;
            Node vRight = v.right;
            Node v1Parent = v1.parent;
            Node v1Left = v1.left;
            Node v1Right = v1.right;
            boolean vIsRight = vParent != null && vParent.right == v;
            boolean v1IsRight = v1Parent != v;

            if (v1IsRight) {
                v.setParent(null);
                v.setLeft(null);
                v.setRight(null);
                if (vParent !=null ) {
                    if (vIsRight) {
                        vParent.setRight(null);
                    } else {
                        vParent.setLeft(null);
                    }
                }
                vLeft.setParent(null);
                vRight.setParent(null);

                v1.setParent(null);
                v1.setLeft(null);
                v1.setRight(null);
                if (v1Left != null) {
                    v1Left.setParent(null);
                }
                if (v1Right != null) {
                    v1Right.setParent(null);
                }
                v1Parent.setRight(null);

                v1.setParent(vParent);
                v1.setLeft(vLeft);
                v1.setRight(vRight);
                if (vParent != null) {
                    if (vIsRight) {
                        vParent.setRight(v1);
                    } else {
                        vParent.setLeft(v1);
                    }
                }
                vLeft.setParent(v1);
                vRight.setParent(v1);

                v.setParent(v1Parent);
                v.setLeft(v1Left);
                v.setRight(v1Right);
                v1Parent.setRight(v);
                if (v1Left != null) {
                    v1Left.setParent(v);
                }
                if (v1Right != null) {
                    v1Right.setParent(v);
                }
                   /* if (vParent == null)
                        root = v1;*/

                remove(v);
            } else {
                v1.setLeft(null);
                v1.setRight(null);
                v1.setParent(null);
                v.setLeft(null);
                v.setRight(null);
                v.setParent(null);
                if (v1Left != null) {
                    v1Left.setParent(null);
                }
                if (v1Right != null) {
                    v1Right.setParent(null);
                }
                if (vParent !=null ) {
                    if (vIsRight) {
                        vParent.setRight(null);
                    } else {
                        vParent.setLeft(null);
                    }
                }
                vRight.setParent(null);

                v1.setParent(vParent);
                v1.setLeft(v);
                v1.setRight(vRight);
                if (vParent != null) {
                    if (vIsRight) {
                        vParent.setRight(v1);
                    } else {
                        vParent.setLeft(v1);
                    }
                }
                vRight.setParent(v1);
                v.setParent(v1);
                v.setLeft(v1Left);
                v.setRight(v1Right);
                if (v1Left != null) {
                    v1Left.setParent(v);
                }
                if (v1Right != null) {
                    v1Right.setParent(v);
                }
                   /* if (vParent == null)
                        root = v1;*/

                remove(v);
            }
        }
    }

    public void insert(int k) {
        if (root == null) {
            root = new Node(k);
            return;
        }
        Node insertNode = search(k);

        Node newNode;

        //if root == null
        //if exists
        if (insertNode.value == k) {
            return;
        } else
        if (k < insertNode.value) {
            newNode = new Node(k);
            insertNode.setLeft(newNode);
            insertNode.left.setParent(insertNode);
        } else {
            newNode = new Node(k);
            insertNode.setRight(newNode);
            insertNode.right.setParent(insertNode);
        }
        rotate(newNode);
    }

    public Node search(int k) {
        if (root == null) {
            return null;
        }
        Node findingNode = root;
        while (findingNode.value != k) {
            if (findingNode.value > k) {
                if (findingNode.left == null) {
                    break;
                }
                findingNode = findingNode.left;
            } else {
                if (findingNode.right == null) {
                    break;
                }
                findingNode = findingNode.right;
            }
        }
        return findingNode;
    }

    public Node min(Node node) {
        if (node == null) {
            return null;
        }
        Node min = node;
        while (min.left != null) {
            min = min.left;
        }
        return min;
    }

    public Node max(Node node) {
        if (node == null) {
            return null;
        }
        Node max = node;
        while (max.right != null) {
            max = max.right;
        }
        return max;
    }

    public void inorder() {
        inorder(root);
        System.out.println();
        inorderSum(root);
        System.out.println();
    }

    public void inorder(Node node) {
        if (node != null) {
            inorder(node.left);
            System.out.print(node.value + "(" +  (node.left == null ? "null" : node.left.value)
                    + ", " + (node.right == null ? "null" : node.right.value)
                    + ", " + (node.parent == null ? "null" : node.parent.value) + ")" + "[" + node.h + "]"
                    + "{" + node.sum + "}" + "  *  ");

            inorder(node.right);
        }
    }

    public int showHeight() {
        return root != null ? root.h : 0;
    }

    public void inorderSum(Node node) {
        if (node != null) {
            inorderSum(node.left);
            System.out.print(node.value + "(" +  node.sum + ")" +" ");
            inorderSum(node.right);
        }
    }
}
