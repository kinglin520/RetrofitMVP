package com.example.base.test.link;

/**
 * 单向链表
 */
public class SingleLinkList {

    private Node head;
    private int size;

    public SingleLinkList() {
        this.head = null;
        size = 0;
    }

    private class Node {
        private Object data;
        private Node nextNode;

        public Node(Object data) {
            this.data = data;
        }
    }

    /**
     * 在头部插入节点
     */
    public void addHead(Object data) {
        Node newNode = new Node(data);
        if (size == 0) {
            head = newNode;
        } else {
            newNode.nextNode = head;
            head = newNode;
        }
        size ++;
    }

    public Object deleteFromTHead() {
        if (size == 0) {
            return null;
        } else if (size == 1) {
            size = 0;
            return head.data;
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
    public Node find(Object obj) {
        Node current = head;
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

    public Node find2(Object object){
        Node current = head;
        while (current != null){
            if(current.data.equals(object)){
                return current;
            }else {
                current = current.nextNode;
            }
        }
        return null;
    }

    /**
     * 删除指定节点
     */
    private boolean delelteNode(Object obj) {
        if (size == 0) {
            return false;
        }
        if (size == 1 && head.data.equals(obj)) {
            head = null;
            size = 0;
            return true;
        }
        Node current = head.nextNode;
        Node preNode = head;
        int tempSize = size;
        while (tempSize > 1) {
            if (current.data.equals(obj)) {
                current.data = null;
                preNode.nextNode = current.nextNode;
                return true;
            } else {
                preNode = current;
                current = current.nextNode;
            }
            tempSize--;
        }
        return false;
    }

    /**
     * 删除指定节点
     */
    private boolean deleteBest(Object value) {
        Node current = head;
        Node preNode = head;
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
        } else {
            current.data = null;
            preNode.nextNode = current.nextNode;
            size--;
        }
        return true;
    }

    public boolean isEmpty(){
        if(size == 0){
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        SingleLinkList list = new SingleLinkList();
        list.addHead(1);
        list.addHead(2);
        list.addHead(3);
        list.deleteFromTHead();
        list.addHead(4);
        list.addHead(5);
        list.delelteNode(2);
        list.addHead(10);
        list.deleteBest(4);
        Node node = list.head;
        while (node != null){
            System.out.print(node.data+",");
            node = node.nextNode;
        }

    }
}
