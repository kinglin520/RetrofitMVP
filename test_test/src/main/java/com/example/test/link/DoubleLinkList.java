package com.example.test.link;

public class DoubleLinkList {
    private DoubleLinkList.Node head;
    private DoubleLinkList.Node tail;
    private int size;

    public DoubleLinkList() {
        this.head = null;
        size = 0;
    }

    public int getSize() {
        return size;
    }

    private class Node {
        private Object data;
        private DoubleLinkList.Node nextNode;

        public Node(Object data) {
            this.data = data;
        }
    }

    /**
     * 在头部插入节点
     */
    public void addHead(Object data) {
        DoubleLinkList.Node newNode = new DoubleLinkList.Node(data);
        if (size == 0) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.nextNode = head;
            head = newNode;
        }
        size++;
    }

    /**
     * 在尾部插入节点
     */
    public void addTail(Object value) {
        DoubleLinkList.Node newNode = new DoubleLinkList.Node(value);
        if (size == 0) {
            head = newNode;
            tail = newNode;
        } else {
            tail.nextNode = newNode;
            tail = newNode;
        }
        size++;
    }

    public Object deleteFromTHead() {
        if (size == 0) {
            return null;
        } else if (size == 1) {
            size = 0;
            Object temp = head.data;
            head = null;
            tail = null;
            return temp;
        } else {
            Object temp = head.data;
            head.data = null;
            head = head.nextNode;
            size--;
            return temp;
        }
    }


    /**
     * 查找指定元素，从头节点开始查找，找到了返回节点Node，找不到返回null
     */
    public DoubleLinkList.Node find(Object obj) {
        DoubleLinkList.Node current = head;
        int tempSize = size;
        while (tempSize > 0) {
            if (current.data.equals(obj)) {
                return current;
            } else {
                current = current.nextNode;
            }
            tempSize--;
        }
        return null;
    }

    /**
     * 删除指定节点
     */
    private boolean deleteBest(Object value) {
        DoubleLinkList.Node current = head;
        DoubleLinkList.Node preNode = head;
        while (current.data != value) {
            if (current.nextNode == null) {
                return false;
            } else {
                preNode = current;
                current = current.nextNode;
            }
        }
        if (current == head) {
            head = current.nextNode;
            size--;
        } else if (current == tail) {
            current.data = null;
            tail = preNode;
            size--;
        } else {
            current.data = null;
            preNode.nextNode = current.nextNode;
            size--;
        }
        return true;
    }

    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    public void display() {
        Node current = head;
        while (current != null) {
            System.out.print(current.data + ",");
            current = current.nextNode;
        }
    }

    public static void main(String[] args) {
        DoubleLinkList list = new DoubleLinkList();
        list.addHead(1);
        list.addHead(2);
        list.addHead(3);
        list.deleteFromTHead();
        list.addHead(4);
        list.addHead(5);
        list.addHead(10);
        list.deleteBest(4);
        DoubleLinkList.Node node = list.head;
        while (node != null) {
            System.out.print(node.data + ",");
            node = node.nextNode;
        }

    }
}
