package com.example.dream.retrofitrxjavaokhttpdemo.base.ui;

import com.example.dream.retrofitrxjavaokhttpdemo.base.rx.RxManager;


public abstract class BasePresenter<T extends BaseView> {

    private BaseActivity mActivity;
    private BaseFragment mFragment;

    //View层
    public T mView;
    //管理Subscription,解除Rxjava订阅
    public RxManager mRxManager = new RxManager();

    public void setVM(T v) {
        this.mView = v;
    }

    public void setActivity(BaseActivity activity) {
        this.mActivity = activity;
    }

    public void setFragment(BaseFragment fragment) {
        this.mFragment = fragment;
    }

    public BaseActivity getActivity() {
        return mActivity;
    }

    public BaseFragment getFragment() {
        return mFragment;
    }

    public RxManager getRxManager() {
        return mRxManager;
    }

    public void onDestroy() {
        mActivity = null;
        mFragment = null;
        mView = null;
        mRxManager.clear();
    }
}
