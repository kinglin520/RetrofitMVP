package com.example.dream.retrofitrxjavaokhttpdemo.base.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;


import butterknife.ButterKnife;

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity {
//    public RxManager mRxManager;
    public T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

//        mRxManager = new RxManager();
//        mPresenter = TUtil.getT(this, 0);
        mPresenter = initPresenter();
//        if (mPresenter != null) {
//            mPresenter.setActivity(this);
//            mPresenter.setFragment(null);
//        }
//        initPresenter();
        initView();
    }

    /**
     * 简单页面无需mvp就不用管此方法即可,完美兼容各种实际场景的变通
     */
    protected abstract T initPresenter();

    protected abstract void initView();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onViewDestory(this);
    }
}
