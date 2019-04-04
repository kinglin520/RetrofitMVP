package com.example.dream.retrofitrxjavaokhttpdemo.http.config;


import com.example.dream.retrofitrxjavaokhttpdemo.base.ui.BaseBean;
import com.example.dream.retrofitrxjavaokhttpdemo.base.ui.BasePresenter;
import com.example.dream.retrofitrxjavaokhttpdemo.base.rx.RxSubscriber;

import io.reactivex.Observable;

/**
 * @Desc: 网络请求配置类
 */

public class RequestConfig<R, T extends BaseBean<R>> {

    private String mTag;
    private BasePresenter mPresenter;
    private String mAsSuccessCondition;
    private Observable<T> mObservable;

    public RequestConfig<R, T> observable(Observable<T> observable) {
        this.mObservable = observable;
        return this;
    }

    /**
     * 网络请求Tag，用于打印日志，方便追踪调试。
     * 描述：可不配置
     *
     * @param tag
     * @return
     */
    public RequestConfig<R, T> tag(String tag) {
        this.mTag = tag;
        return this;
    }

    public RequestConfig<R, T> presenter(BasePresenter presenter) {
        this.mPresenter = presenter;
        return this;
    }

    /**
     * @param condition 当服务器返回的code不为1000时，框架认为接口请求错误最终回调错误方法，此方法可以配置条件，将非1000的服务器返回码视作成功
     *                  参数规范示例  {"1001,1002,1003"}  即将code值用","拼接在一起
     * @return
     */
    public RequestConfig<R, T> asSuccessWhen(String condition) {
        this.mAsSuccessCondition = condition;
        return this;
    }

    public void doRequest(RxSubscriber rxSubscriber) {

        RxSubscriber<R, T> mRxSubscriber = rxSubscriber;

        mRxSubscriber.setRequestConfig(this);
        mRxSubscriber.doSubscribe(mObservable);
//        mPresenter.getRxManager().add(rxSubscriber.getDisposable());
    }


    /*****************************************************以下是属性的get方法*****************************************************/
    public String getTag() {
        return mTag;
    }

    public BasePresenter getPresenter() {
        return mPresenter;
    }

    public String getAsSuccessCondition() {
        return mAsSuccessCondition;
    }

    public Observable<T> getObservable() {
        return mObservable;
    }
}
