package com.example.dream.retrofitrxjavaokhttpdemo.base.ui;


public abstract class BasePresenter<V extends IBaseView> implements IBasePresenter {
    //View层
    public V mView;

    public BasePresenter(V mView) {
        this.mView = mView;
    }

    @Override
    public void onDestroy() {
        mView = null;
    }

//    @Override
//    public void onViewDestory(Object tag) {
//        mView = null;
//
//    }

}
