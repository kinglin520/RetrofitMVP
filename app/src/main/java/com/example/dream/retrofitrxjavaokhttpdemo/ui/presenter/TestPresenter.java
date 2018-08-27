package com.example.dream.retrofitrxjavaokhttpdemo.ui.presenter;

import com.example.dream.retrofitrxjavaokhttpdemo.api.GankService;
import com.example.dream.retrofitrxjavaokhttpdemo.base.BaseBean;
import com.example.dream.retrofitrxjavaokhttpdemo.base.BasePresenter;
import com.example.dream.retrofitrxjavaokhttpdemo.base.rx.RxSubscriber;
import com.example.dream.retrofitrxjavaokhttpdemo.bean.GirlInfoBean;
import com.example.dream.retrofitrxjavaokhttpdemo.bean.UserInfoBean;
import com.example.dream.retrofitrxjavaokhttpdemo.http.Api;
import com.example.dream.retrofitrxjavaokhttpdemo.http.error.ErrorType;
import com.example.dream.retrofitrxjavaokhttpdemo.ui.contract.TestContract;

import java.util.List;

public class TestPresenter extends BasePresenter<TestContract> {
    public void getUserInfo(String uid) {
        Api.observable(Api.getService(GankService.class).test(uid))
                .presenter(this).doRequest(new RxSubscriber<UserInfoBean, BaseBean<UserInfoBean>>() {

            @Override
            protected void _onSuccess(UserInfoBean userInfoBean, String successMessage) {
                mView.setUserInfo(userInfoBean);
            }

            @Override
            protected void _onError(ErrorType errorType, int errorCode, String message, UserInfoBean data) {
                mView.showErrorTip(errorType,errorCode,message);
            }
        });
    }

    public void getGirlInfo(String page){
        Api.observable(Api.getService(GankService.class).getGirlInfo(page))
                .presenter(this).doRequest(new RxSubscriber<List<GirlInfoBean> , BaseBean<List<GirlInfoBean>>>() {

            @Override
            protected void _onSuccess(List<GirlInfoBean> girlInfoBeans, String successMessage) {
                mView.setGirlInfo(girlInfoBeans);
            }

            @Override
            protected void _onError(ErrorType errorType, int errorCode, String message, List<GirlInfoBean> data) {
                mView.showErrorTip(errorType,errorCode,message);
            }
        });
    }
}
