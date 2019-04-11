package com.example.test.stack;

import java.util.Arrays;
import java.util.EmptyStackException;

/**
 * 栈作为一种数据结构，是一种只能在一端进行插入和删除操作的特殊线性表
 */
public class MyStack {
    int top;
    int maxSize = 10;
    /**
     * 存储元素的数组,声明为Object类型能存储任意类型的数据
     */
    Object[] arrs;

    public MyStack(int size) {
        this.maxSize = size;
        arrs = new Object[size];
        this.top = -1;
    }

    private void push(Object value) {
        isGrow(top + 1);
        if (top < maxSize - 1) {
            arrs[++top] = value;
        }
    }

    private Object pop() {
        Object temp = peek();
        remove(top);
        return temp;
    }

    private Object peek() {
        if (top == -1) {
            throw new EmptyStackException();
        }
        return arrs[top];
    }

    private boolean isEmpty() {
        if (top == -1) {
            return true;
        }
        return false;
    }

    private void remove(int top) {
        arrs[top] = null;
        this.top--;
    }

    private boolean isGrow(int minCapacity) {
        if (minCapacity >= maxSize) {
            while (minCapacity >= maxSize) {
                maxSize = 2 * maxSize;
            }
            arrs = Arrays.copyOf(arrs, maxSize);
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        MyStack stack = new MyStack(1);
        String s = "how are u";
        char[] chars = s.toCharArray();
        for (char i : chars) {
            stack.push(i);
        }
        while (!stack.isEmpty()) {
            System.out.print(stack.pop());
        }
    }
}
