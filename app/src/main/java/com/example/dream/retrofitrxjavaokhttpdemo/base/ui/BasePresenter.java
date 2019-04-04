package com.example.dream.retrofitrxjavaokhttpdemo.base.ui;


public abstract class BasePresenter<V extends IBaseView> implements IBasePresenter {
    //View层
    public V mView;
//    private BaseActivity mActivity;
//    private BaseFragment mFragment;

    public BasePresenter(V mView) {
        this.mView = mView;
    }


    //管理Subscription,解除Rxjava订阅
//    public RxManager mRxManager = new RxManager();

//    public void setVM(V v) {
//        this.mView = v;
//    }

//    public void setActivity(BaseActivity activity) {
//        this.mActivity = activity;
//    }
//
//    public void setFragment(BaseFragment fragment) {
//        this.mFragment = fragment;
//    }
//
//    public BaseActivity getActivity() {
//        return mActivity;
//    }
//
//    public BaseFragment getFragment() {
//        return mFragment;
//    }

//    public RxManager getRxManager() {
//        return mRxManager;
//    }

    @Override
    public void onViewDestory(Object tag) {
        mView = null;

    }

//    public void onDestroy() {
//        mActivity = null;
//        mFragment = null;
//        mView = null;
//        mRxManager.clear();
//    }
}
