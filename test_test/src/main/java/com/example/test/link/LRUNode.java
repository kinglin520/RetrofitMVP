package com.example.test.link;

public class LRUNode {
    LRUNode pre;
    LRUNode next;
    String key;
    Object value;

    public LRUNode(String key, Object value) {
        this.key = key;
        this.value = value;
    }
}
