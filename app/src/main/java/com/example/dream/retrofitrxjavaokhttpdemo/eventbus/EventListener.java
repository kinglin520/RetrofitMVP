package com.example.dream.retrofitrxjavaokhttpdemo.eventbus;

/**
 * eventbus 通知回调函数
 */
public interface EventListener {

    void onEvent(int eventCode, Object o);
}
