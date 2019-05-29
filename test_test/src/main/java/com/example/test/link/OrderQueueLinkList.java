package com.example.test.link;

/**
 * 有序队列
 */
public class OrderQueueLinkList {
    private Node head;
    private int size;

    public OrderQueueLinkList() {
        head = null;
        size = 0;
    }

    private class Node {
        private int data;
        private Node next;

        public Node(int data) {
            this.data = data;
        }
    }

    private void insert(int value) {
        Node newNode = new Node(value);
        if (size == 0) {
            head = newNode;
            size++;
            return;
        } else {
            Node current = head;
            Node preNode = null;
            while (current != null && value > current.data) {
                preNode = current;
                current = current.next;
            }
            if (preNode == null) {
                head = newNode;
                head.next = current;
            } else {
                preNode.next = newNode;
                newNode.next = current;
            }
            size++;
        }
    }

    private Node find(int value) {
        Node current = head;
        while (current != null && current.data <= value) {
            if (current.data == value) {
                return current;
            }
            current = current.next;
        }
        return null;
    }

    private void deleteFromHead() {
        if (size > 0) {
            head = head.next;
            size--;
        }
    }

    private void delete(int value) {
        Node current = head;
        Node preNode = null;
        while (current != null && value != current.data) {
            preNode = current;
            current = current.next;
        }
        if (current == head) {
            head = head.next;
            size--;
        } else {
            preNode.next = current.next;
            size--;
        }
    }

    private void display() {
        Node current = head;
        while (current != null) {
            System.out.print(current.data + ",");
            current = current.next;
        }
    }

    public static void main(String[] args) {
        OrderQueueLinkList orderQueueLinkList = new OrderQueueLinkList();
        orderQueueLinkList.insert(2);
        orderQueueLinkList.insert(1);
        orderQueueLinkList.insert(4);
        orderQueueLinkList.insert(45);
        orderQueueLinkList.insert(6);
        orderQueueLinkList.insert(3);
        orderQueueLinkList.display();


    }
}
