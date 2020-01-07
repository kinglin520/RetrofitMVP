package com.example.base.test.treenode;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class BinaryTree implements Tree {
    //表示根节点
    private Node root;

    //查找节点
    public Node find(int key) {
        Node current = root;
        while (current != null) {
            //当前值比查找值大，搜索左子树
            if (current.data > key) {
                current = current.leftChild;
            } else if (current.data < key) {
                //当前值比查找值小，搜索右子树
                current = current.rightChild;
            } else {
                return current;
            }
        }
        //遍历完整个树没找到，返回null
        return null;
    }

    //插入节点
    public boolean insert(int data) {
        Node newNode = new Node(data);
        //当前树为空树，没有任何节点
        if (root == null) {
            root = newNode;
            return true;
        } else {
            Node current = root;
            Node parentNode = null;
            while (current != null) {
                parentNode = current;
                //当前值比插入值大，搜索左子节点
                if (current.data > data) {
                    current = current.leftChild;
                    //左子节点为空，直接将新值插入到该节点
                    if (current == null) {
                        parentNode.leftChild = newNode;
                        return true;
                    }
                } else {
                    current = current.rightChild;
                    //右子节点为空，直接将新值插入到该节点
                    if (current == null) {
                        parentNode.rightChild = newNode;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    //插入节点（红黑树）
    public void insertRB(int data) {
        Node newNode = new Node(data);
        Node parent = null;
        Node x = this.root;
        while (x != null) {
            parent = x;
            //当前值比插入值小，搜索右子节点
            if (newNode.data > x.data) {
                x = x.rightChild;
            } else {
                x = x.leftChild;
            }
        }
        //找到了插入的位置，将当前current作为node的父节点
        newNode.parent = parent;
        if (parent == null) {
            this.root = newNode;
        } else {
            //大于父节点，直接插入到父右子节点
            if (newNode.data > parent.data) {
                parent.rightChild = newNode;
            } else {
                parent.leftChild = newNode;
            }
        }
        //3.利用旋转操作将其修正为一颗红黑树
        insertFixUp(newNode);
    }

    /**
     * 翻转二叉树
     */
    private Node mirror(Node node) {
        if (node == null) {
            return null;
        }
        if (node.leftChild == null && node.rightChild == null) {
            return null;
        }
        Node temp = node.leftChild;
        node.leftChild = node.rightChild;
        node.rightChild = temp;
        mirror(node.leftChild);
        mirror(node.rightChild);
        return node;
    }


    //中序遍历
    public void infixOrder(Node current) {
        if (current != null) {
            infixOrder(current.leftChild);
            System.out.print(current.getColor() + "=" + current.data);
            infixOrder(current.rightChild);
        }
    }

    //前序遍历(二叉树的深度优先遍历)
    public void preOrder(Node current) {
        if (current != null) {
            System.out.print(current.data + " ");
            preOrder(current.leftChild);
            preOrder(current.rightChild);
        }
    }

    //后序遍历
    public void postOrder(Node current) {
        if (current != null) {
            postOrder(current.leftChild);
            postOrder(current.rightChild);
            System.out.print(current.getColor() + " ");
        }
    }

    /**
     * 二叉树的深度优先遍历
     *
     * @param root
     * @return
     */
    public List<Integer> dfs(Node root) {
        Stack<Node> stack = new Stack<Node>();
        List<Integer> list = new LinkedList<Integer>();

        if (root == null)
            return list;
        //压入根节点
        stack.push(root);
        //然后就循环取出和压入节点，直到栈为空，结束循环
        while (!stack.isEmpty()) {
            Node t = stack.pop();
            if (t.rightChild != null)
                stack.push(t.rightChild);
            if (t.leftChild != null)
                stack.push(t.leftChild);
            list.add(t.data);
        }
        return list;

    }


    /**
     * 二叉树的广度优先遍历 （从上往下（所在行遍历完 下一行）从左往右 ）
     */
    public List<Integer> bfs(Node root) {
        Queue<Node> queue = new LinkedList<Node>();
        List<Integer> list = new LinkedList<Integer>();

        if (root == null)
            return list;
        queue.add(root);
        while (!queue.isEmpty()) {
            Node t = queue.poll();
            if (t.leftChild != null)
                queue.add(t.leftChild);
            if (t.rightChild != null)
                queue.add(t.rightChild);
            list.add(t.data);
        }
        return list;
    }

    //找到最大值
    public Node findMax() {
        Node current = root;
        Node maxNode = current;
        while (current != null) {
            maxNode = current;
            current = current.rightChild;
        }
        return maxNode;
    }

    //找到最小值
    public Node findMin() {
        Node current = root;
        Node minNode = current;
        while (current != null) {
            minNode = current;
            current = current.leftChild;
        }
        return minNode;
    }

    @Override
    public boolean delete(int key) {
        Node current = root;
        Node parent = root;
        boolean isLeftChild = false;
        //查找删除值，找不到直接返回false
        while (current.data != key) {
            parent = current;
            if (current.data > key) {
                isLeftChild = true;
                current = current.leftChild;
            } else {
                isLeftChild = false;
                current = current.rightChild;
            }
            if (current == null) {
                return false;
            }
        }
        //如果当前节点没有子节点
        if (current.leftChild == null && current.rightChild == null) {
            if (current == root) {
                root = null;
            } else if (isLeftChild) {
                parent.leftChild = null;
            } else {
                parent.rightChild = null;
            }
            return true;

            //当前节点有一个子节点，右子节点
        } else if (current.leftChild == null && current.rightChild != null) {
            if (current == root) {
                root = current.rightChild;
            } else if (isLeftChild) {
                parent.leftChild = current.rightChild;
            } else {
                parent.rightChild = current.rightChild;
            }
            return true;
            //当前节点有一个子节点，左子节点
        } else if (current.leftChild != null && current.rightChild == null) {
            if (current == root) {
                root = current.leftChild;
            } else if (isLeftChild) {
                parent.leftChild = current.leftChild;
            } else {
                parent.rightChild = current.leftChild;
            }
            return true;
        } else {
            //当前节点存在两个子节点
            Node successor = getSuccessor(current);
            if (current == root) {
                root = successor;
            } else if (isLeftChild) {
                parent.leftChild = successor;
            } else {
                parent.rightChild = successor;
            }
            successor.leftChild = current.leftChild;
        }
        return false;

    }

    public Node getSuccessor(Node delNode) {
        Node successorParent = delNode;
        Node successor = delNode;
        Node current = delNode.rightChild;
        while (current != null) {
            successorParent = successor;
            successor = current;
            current = current.leftChild;
        }
        //后继节点不是删除节点的右子节点，将后继节点替换删除节点
        if (successor != delNode.rightChild) {
            successorParent.leftChild = successor.rightChild;
            successor.rightChild = delNode.rightChild;
        }

        return successor;
    }

    private void insertFixUp(Node node) {
        Node parent, gparent;//定义父节点和祖父节点

        //需要修正的条件：父节点存在，且父节点的颜色是红色
        while (((parent = parentOf(node)) != null) && isRed(parent)) {
            gparent = parentOf(parent);//获得祖父节点

            //若父节点是祖父节点的左子节点，下面的else相反
            if (parent == gparent.leftChild) {
                Node uncle = gparent.rightChild;//获得叔叔节点

                //case1:叔叔节点也是红色
                if (uncle != null && isRed(uncle)) {
                    setBlack(parent);//把父节点和叔叔节点涂黑
                    setBlack(gparent);
                    setRed(gparent);//把祖父节点涂红
                    node = gparent;//把位置放到祖父节点处
                    continue;//继续while循环，重新判断
                }

                //case2:叔叔节点是黑色，且当前节点是右子节点
                if (node == parent.rightChild) {
                    leftRotate(parent);//从父节点出左旋
                    Node tmp = parent;//然后将父节点和自己调换一下，为下面右旋做准备
                    parent = node;
                    node = tmp;
                }

                //case3:叔叔节点是黑色，且当前节点是左子节点
                setBlack(parent);
                setRed(gparent);
                rightRotate(gparent);
            } else {//若父节点是祖父节点的右子节点，与上面的情况完全相反，本质是一样的
                Node uncle = gparent.leftChild;

                //case1:叔叔节点也是红色的
                if (uncle != null && isRed(uncle)) {
                    setBlack(parent);
                    setBlack(uncle);
                    setRed(gparent);
                    node = gparent;
                    continue;
                }

                //case2:叔叔节点是黑色的，且当前节点是左子节点
                if (node == parent.leftChild) {
                    rightRotate(parent);
                    Node tmp = parent;
                    parent = node;
                    node = tmp;
                }

                //case3:叔叔节点是黑色的，且当前节点是右子节点
                setBlack(parent);
                setRed(gparent);
                leftRotate(gparent);
            }
        }
        setBlack(root);//将根节点设置为黑色
    }

    /*************对红黑树节点x进行左旋操作 ******************/
    /*
     * 左旋示意图：对节点x进行左旋
     *     p                       p
     *    /                       /
     *   x                       y
     *  / \                     / \
     * lx  y      ----->       x  ry
     *    / \                 / \
     *   ly ry               lx ly
     * 左旋做了三件事：
     * 1. 将y的左子节点赋给x的右子节点,并将x赋给y左子节点的父节点(y左子节点非空时)
     * 2. 将x的父节点p(非空时)赋给y的父节点，同时更新p的子节点为y(左或右)
     * 3. 将y的左子节点设为x，将x的父节点设为y
     */
    private void leftRotate(Node x) {
        //1. 将y的左子节点赋给x的右子节点，并将x赋给y左子节点的父节点(y左子节点非空时)
        Node y = x.rightChild;
        x.rightChild = y.leftChild;
        if (y.leftChild != null) {
            y.leftChild.parent = x;
        }

        //2. 将x的父节点p(非空时)赋给y的父节点，同时更新p的子节点为y(左或右)
        y.parent = x.parent;
        if (x.parent == null) {
            this.root = y;//如果x的父节点为空(即x为根节点)，则将y设为根节点
        } else {
            if (x == x.parent.leftChild) {//如果x是左子节点
                x.parent.leftChild = y;//则也将y设为左子节点
            } else {
                x.parent.rightChild = y;//否则将y设为右子节点
            }
        }

        //3. 将y的左子节点设为x，将x的父节点设为y
        y.leftChild = x;
        x.parent = y;
    }

    /*************对红黑树节点y进行右旋操作 ******************/
    /*
     * 右旋示意图：对节点y进行右旋
     *        p                   p
     *       /                   /
     *      y                   x
     *     / \                 / \
     *    x  ry   ----->      lx  y
     *   / \                     / \
     * lx  rx                   rx ry
     * 右旋做了三件事：
     * 1. 将x的右子节点赋给y的左子节点,并将y赋给x右子节点的父节点(x右子节点非空时)
     * 2. 将y的父节点p(非空时)赋给x的父节点，同时更新p的子节点为x(左或右)
     * 3. 将x的右子节点设为y，将y的父节点设为x
     */
    private void rightRotate(Node y) {
        //1. 将y的左子节点赋给x的右子节点，并将x赋给y左子节点的父节点(y左子节点非空时)
        Node x = y.leftChild;
        y.leftChild = x.rightChild;
        if (x.rightChild != null) {
            x.rightChild.parent = y;
        }

        //2. 将x的父节点p(非空时)赋给y的父节点，同时更新p的子节点为y(左或右)
        x.parent = y.parent;
        if (y.parent == null) {
            this.root = x;//如果y的父节点为空(即y为根节点)，则旋转后将x设为根节点
        } else {
            if (y == y.parent.leftChild) {//如果y是左子节点
                y.parent.leftChild = x;//则将x也设置为左子节点
            } else {
                y.parent.rightChild = x;//否则将x设置为右子节点
            }
        }

        //3. 将x的左子节点设为y，将y的父节点设为y
        x.rightChild = y;
        y.parent = x;
    }


    public void setBlack(Node node) {
        node.color = false;
    }

    public void setRed(Node node) {
        node.color = true;
    }

    public Node parentOf(Node node) {
        return node.parent;
    }

    public boolean isRed(Node node) {
        if (node.color) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * *        3                   p
     * *       / \                 /
     * *      2  7                 x
     * *        / \
     * 4  8
     * \
     * 5
     */
    public static void main(String[] args) {
        BinaryTree bt = new BinaryTree();
        bt.insert(3);
        bt.insert(2);
        bt.insert(7);
        bt.insert(4);
        bt.insert(5);
        bt.insert(8);

//        bt.insert(8);
//        bt.insert(9);
//        bt.insert(4);
//        bt.insert(6);
//        bt.insert(10);
//        bt.insert(11);


//        bt.delete(10);//删除没有子节点的节点
//        bt.delete(30);//删除有一个子节点的节点
//        bt.delete(80);//删除有两个子节点的节点
//        System.out.println(bt.findMax().data);
//        System.out.println(bt.findMin().data);
//        System.out.println(bt.find(100).data);
//        System.out.println(bt.find(20).data);
//        System.out.print(bt.root.data);

//        bt.infixOrder(bt.root);
//        bt.preOrder(bt.root);
//        bt.postOrder(bt.root);
        System.out.print("先序遍历二叉树：");
        bt.preOrder(bt.root);
//        bt.mirror(bt.root);
        System.out.print("先序遍历翻转二叉树：");
        bt.preOrder(bt.root);

//        int n = 5;
//        int i = n - 1;
//        while (i > 0) {
//            n = n * i;
//            i--;
//        }
//        System.out.print("n! =" + n);
        System.out.print("深度优先搜索1: ");
        bt.preOrder(bt.root);
        System.out.print("2: ");
        for (Integer i : bt.dfs(bt.root)) {
            System.out.print(i + " ");
        }

        System.out.print("广度优先搜索1: ");
        for (Integer j : bt.bfs(bt.root)) {
            System.out.print(j + " ");
        }
    }
}
