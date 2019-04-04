package com.example.dream.retrofitrxjavaokhttpdemo.base.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.example.dream.retrofitrxjavaokhttpdemo.utils.TUtil;

public abstract class BaseFragment<T extends BasePresenter> extends Fragment {
    protected BaseActivity mActivity;
    public T mPresenter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mActivity = (BaseActivity) getActivity();
        mPresenter = initPresenter();
//        mPresenter = TUtil.getT(this, 0);
//        if (mPresenter != null) {
//            mPresenter.setActivity(mActivity);
//            mPresenter.setFragment(this);
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
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onViewDestory(this);
    }
}
