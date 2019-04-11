package com.example.test.link;

/**
 * 双向链表
 */
public class TwoWayLinkList {
    private Node head;
    private Node tail;
    private int size;

    private class Node {
        private Object data;
        private Node next;
        private Node pre;

        public Node(Object data) {
            this.data = data;
            next = null;
            pre = null;
        }
    }

    private void addHead(Object value) {
        Node newNode = new Node(value);
        if (size == 0) {
            head = newNode;
            tail = newNode;
            size++;
        } else {
            head.pre = newNode;
            newNode.next = head;
            head = newNode;
            size++;
        }
    }

    private void addTail(Object value) {
        Node newNode = new Node(value);
        if (size == 0) {
            head = newNode;
            tail = newNode;
            size++;
        } else {
            tail.next = newNode;
            newNode.pre = tail;
            tail = newNode;
            size++;
        }
    }

    private void deleteHead() {
        head.data = null;
        head = head.next;
        size--;

    }

    private void deleteTail() {
        tail.data = null;
        tail = tail.pre;
        size--;
    }

    private void display() {
        Node current = head;
        while (current != null && current.data != null) {
            System.out.print(current.data + ",");
            current = current.next;
        }
    }

    public static void main(String[] args) {
        TwoWayLinkList twoWayLinkList = new TwoWayLinkList();
        twoWayLinkList.addHead(1);
        twoWayLinkList.addHead(2);
        twoWayLinkList.addHead(3);
//        twoWayLinkList.display();
        twoWayLinkList.addTail(4);
        twoWayLinkList.addTail(5);
//        twoWayLinkList.display();
        twoWayLinkList.deleteHead();
//        twoWayLinkList.display();
        twoWayLinkList.deleteTail();
        twoWayLinkList.display();
    }
}
