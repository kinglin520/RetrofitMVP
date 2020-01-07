package com.example.base.test.link;

/**
 * 双向链表
 */
public class TwoWayLinkList {
    private Node head;
    private Node tail;
    private int size;

    private class Node {
        private int data;
        private Node next;
        private Node pre;

        public Node(int data) {
            this.data = data;
            next = null;
            pre = null;
        }
    }

    private void addHead(int value) {
        Node newNode = new Node(value);
        if (size == 0) {
            head = newNode;
            tail = newNode;
            size++;
        } else {
            newNode.next = head;
            head.pre = newNode;
            head = newNode;
            size++;
        }
    }

//    private void addTail(int value) {
//        Node newNode = new Node(value);
//        if (size == 0) {
//            head = newNode;
//            tail = newNode;
//            size++;
//        } else {
//            newNode.pre = tail;
//            tail.next = newNode;
//            tail = newNode;
//            size++;
//        }
//    }

    //在链表尾增加节点
    public void addTail(int value) {
        Node newNode = new Node(value);
        if (size == 0) {
            head = newNode;
            tail = newNode;
            size++;
        } else {
            newNode.pre = tail;
            tail.next = newNode;
            tail = newNode;
            size++;
        }
    }

    private void deleteHead() {
        head.data = -1;
        head = head.next;
        size--;

    }

    private void deleteTail() {
        tail.data = -1;
        tail = tail.pre;
        size--;
    }

    private void display() {
        Node current = head;
        while (current != null && current.data >= 0) {
            System.out.print(current.data + ",");
            current = current.next;
        }
    }

    private static void quickSort(Node head, Node tail) {
        if (head == null || tail == null || head == tail || head.next == tail) {
            return;
        }
        if (head != tail) {
            Node mid = getMid(head, tail);
            quickSort(head, mid);
            quickSort(mid.next, tail);
        }
    }

    /**
     * 双向链表快速排序
     * 核心思想：1、同数组快速排序思路2、只交换节点值，不交换节点3、顺序：小的放左边 再大的放右边
     */
    private static Node getMid(Node start, Node end) {
        int base = start.data;
        while (start != end) {
            while (start != end && base <= end.data) {
                end = end.pre;
            }
            start.data = end.data;
            while (start != end && base >= start.data) {
                start = start.next;
            }
            end.data = start.data;
        }
        start.data = base;
        return start;
    }

    public static void main(String[] args) {
        TwoWayLinkList twoWayLinkList = new TwoWayLinkList();
        twoWayLinkList.addTail(3);
        twoWayLinkList.addTail(2);
        twoWayLinkList.addTail(9);
        twoWayLinkList.addTail(4);
        twoWayLinkList.addTail(6);
        twoWayLinkList.addTail(5);
//        twoWayLinkList.display();
//        twoWayLinkList.deleteHead();
//        twoWayLinkList.display();
//        twoWayLinkList.deleteTail();


        quickSort(twoWayLinkList.head, twoWayLinkList.tail);
        twoWayLinkList.display();
    }
}
