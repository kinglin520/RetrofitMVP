package com.example.dream.retrofitrxjavaokhttpdemo.http.config;

import com.example.dream.retrofitrxjavaokhttpdemo.base.rx.StringRxSubscriber;

import io.reactivex.Observable;

/**
 * 网络请求配置类(直接处理String)
 *
 * @author wenlin
 * @date 2019/4/25
 */
public class RequestString {
    private Observable mObservable;

    public RequestString observable(Observable observable) {
        this.mObservable = observable;
        return this;
    }

    public void doRequest(StringRxSubscriber rxSubscriber) {

        StringRxSubscriber mRxSubscriber = rxSubscriber;

        mRxSubscriber.doSubscribe(mObservable);
    }
}
