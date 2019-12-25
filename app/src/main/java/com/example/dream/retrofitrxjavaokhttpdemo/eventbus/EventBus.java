package com.example.dream.retrofitrxjavaokhttpdemo.eventbus;

import android.util.SparseArray;

import java.util.HashSet;
import java.util.Iterator;

/**
 * 观察者 和 被观察者 实现
 */
public class EventBus {

    /**
     * 使用单例
     */

    private static volatile EventBus instance;

    private EventBus() {
    }

    /**
     * 双重校验
     */
    public static EventBus getInstance() {
        if (instance == null) {
            synchronized (EventBus.class) {
                if (instance == null) {
                    instance = new EventBus();
                }
            }
        }
        return instance;
    }

    /**
     * 使用SparseArray 高效存储
     * 每一个被观察者，都保存观察者的回调
     */
    private static SparseArray<HashSet<EventListener>> observables = new SparseArray<>();


    /**
     * 注册事件
     */
    public static void registerEvent(int eventCode, EventListener eventListener) {
        HashSet<EventListener> observer = observables.get(eventCode);

        if (observer == null) {
            observer = new HashSet<>();
            observables.put(eventCode, observer);
        }
        observer.add(eventListener);
    }

    /**
     * 解绑事件
     */
    public static void unRegisterEvent(int eventCode, EventListener eventListener) {
        HashSet<EventListener> observer = observables.get(eventCode);
        if (observer != null) {
            observer.remove(eventListener);
        }
    }

    /**
     * 发送消息
     */
    public void sendEvent(int eventCode, EventListener eventListener) {

        EventListener[] arraylocal;

        synchronized (this) {
            arraylocal = toArray(observables.get(eventCode));
        }

        for (int i = arraylocal.length - 1; i >= 0; i--) {
            arraylocal[i].onEvent(eventCode, eventListener);
        }
    }

    public EventListener[] toArray(HashSet<EventListener> set) {
        EventListener[] r = new EventListener[set.size()];
        Iterator<EventListener> it = set.iterator();
        for (int i = 0; i < r.length; i++) {
            r[i] = it.next();
        }
        return r;
    }

    public void clear() {
        observables.clear();
    }
}



