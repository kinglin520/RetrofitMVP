package com.example.base.test.link;

/**
 * 栈（单向链表实现）
 */
public class StackSingleLink {
    SingleLinkList singleLinkList;

    public StackSingleLink() {
        singleLinkList = new SingleLinkList();
    }

    private void push(Object value) {
        singleLinkList.addHead(value);
    }

    private Object pop() {
        return singleLinkList.deleteFromTHead();
    }

    private boolean isEmpty() {
        return singleLinkList.isEmpty();
    }

    public static void main(String[] args) {
        StackSingleLink stackSingleLink = new StackSingleLink();
        stackSingleLink.push(1);
        stackSingleLink.push(2);
        stackSingleLink.push(3);
        while (!stackSingleLink.isEmpty()) {
            System.out.print(stackSingleLink.pop() + ",");
        }
    }

}
