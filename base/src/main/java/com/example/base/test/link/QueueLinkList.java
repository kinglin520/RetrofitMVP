package com.example.base.test.link;

/**
 * 队列（双端链表实现）
 */
public class QueueLinkList {
    private DoubleLinkList doubleLinkList;

    public QueueLinkList() {
        doubleLinkList = new DoubleLinkList();
    }

    public void insert(Object value) {
        doubleLinkList.addTail(value);
    }

    public void delete() {
        doubleLinkList.deleteFromTHead();
    }

    public int size() {
        return doubleLinkList.getSize();
    }

    public boolean isEmpty() {
        return doubleLinkList.isEmpty();
    }

    public void display() {
        doubleLinkList.display();
    }

    public static void main(String[] args) {
        QueueLinkList queueLinkList = new QueueLinkList();
        queueLinkList.insert(1);
        queueLinkList.insert(2);
        queueLinkList.insert(3);
        queueLinkList.insert(4);
        queueLinkList.delete();
        queueLinkList.display();
    }

}
