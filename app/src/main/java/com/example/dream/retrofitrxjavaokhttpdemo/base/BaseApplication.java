package com.example.dream.retrofitrxjavaokhttpdemo.base;

import android.app.Application;

import com.blankj.utilcode.util.Utils;
import com.example.dream.retrofitrxjavaokhttpdemo.base.rx.RxSubscriber;
import com.example.dream.retrofitrxjavaokhttpdemo.http.Api;
import com.example.dream.retrofitrxjavaokhttpdemo.http.config.ApiConfig;

public class BaseApplication extends Application {
    private static BaseApplication baseApplication;

    public static BaseApplication getApplication() {
        return baseApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        baseApplication = this;
        Utils.init(this);
        initApiConfig();

    }

    private void initApiConfig() {
        ApiConfig apiConfig = new ApiConfig();
        apiConfig.setConnectTimeOut(30000);
        apiConfig.setReadTimeOut(30000);
        apiConfig.setHostServer("https://www.baidu.com");
        Api.setConfig(apiConfig);
    }

    private void initGlobalError() {
        RxSubscriber.registerGlobalErrorListener(new RxSubscriber.GlobalErrorListener() {
            @Override
            public void onReturn401Code(RxSubscriber rxSubscriber, String message) {

            }

            @Override
            public void onReturn9105Code(RxSubscriber rxSubscriber, String message) {

            }

            @Override
            public void onReturn9107Code(RxSubscriber rxSubscriber, String message) {

            }

            @Override
            public void onReturn9108Code(RxSubscriber rxSubscriber, String message) {

            }

            @Override
            public void onReturn9109Code(RxSubscriber rxSubscriber, String message) {

            }
        });
    }
}
