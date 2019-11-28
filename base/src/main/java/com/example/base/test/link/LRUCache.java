package com.example.base.test.link;

import java.util.HashMap;
import java.util.Map;

/**
 * 双向链表+哈希表
 * 最近最少使用淘汰算法
 */
public class LRUCache {
    Map<String, LRUNode> map = new HashMap<>();
    LRUNode head;
    LRUNode tail;
    /**
     * 缓存长度
     */
    int capacity;

    public LRUCache(int capacity) {
        this.capacity = capacity;
    }

    /**
     * 将值存入map，如果已存在更新值，并操作链表
     */
    private void put(String key, Object value) {
        if (head == null) {
            head = new LRUNode(key, value);
            tail = head;
            map.put(key, head);
        }
        LRUNode node = map.get(key);
        if (node != null) {
            // 更新节点新值
            node.value = value;

            removeAndInsert(node);

        } else {
            LRUNode tmp = new LRUNode(key, value);
            // 缓存长度已满
            // 删除尾部节点
            if (map.size() >= capacity) {
                map.remove(tail.key);
                tail = tail.pre;
                tail.next = null;
            }
            map.put(key, tmp);
            // 并插入到头部
            tmp.next = head;
            head.pre = tmp;
            head = tmp;
        }
    }

    /**
     * 从map中获取node，并操作链表，删除node和将node插入到头节点
     */
    private Object get(String key) {
        LRUNode node = map.get(key);
        if (node == null) {
            return null;
        }
        removeAndInsert(node);
        return node.value;
    }

    /**
     * 删除当前节点，并插入到头节点
     */
    private void removeAndInsert(LRUNode node) {
        // 特殊情况先判断，例如该节点是头结点或是尾部节点
        if (node == head) {
            return;
        } else if (node == tail) {
            tail = node.pre;
            tail.next = null;
        } else {
            node.pre.next = node.next;
            node.next.pre = node.pre;
        }
        // 将节点插入到头部
        head.pre = node;
        node.next = head;
        node.pre = null;
        head = node;
    }

    private void disPlay() {
        System.out.print("\n");
        for (LRUNode o : map.values()) {
            System.out.print(o.value + ",");
        }
        System.out.print("\n");
        LRUNode current = head;
        while (current != null) {
            System.out.print(current.value);
            current = current.next;
        }
    }

    public static void main(String[] args) {
        LRUCache lruCache = new LRUCache(3);
        lruCache.put("1", "h");
        lruCache.put("2", "e");
        lruCache.put("3", "l");
        lruCache.disPlay();
        lruCache.put("1", "H");
        lruCache.put("4", "o");

        lruCache.disPlay();
//        lruCache.put("4", "o");
//        lruCache.put("5", "W");

//        lruCache.disPlay();
    }

}
