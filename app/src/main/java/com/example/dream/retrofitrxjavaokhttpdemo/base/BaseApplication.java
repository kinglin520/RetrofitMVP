package com.example.dream.retrofitrxjavaokhttpdemo.base;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.Utils;
import com.example.dream.retrofitrxjavaokhttpdemo.app.GankService;
import com.example.dream.retrofitrxjavaokhttpdemo.base.rx.RxSubscriber;
import com.example.dream.retrofitrxjavaokhttpdemo.cache.CacheInject;
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
        CacheInject.inject(GankService.class);
        Utils.init(this);
        initApiConfig();
//        if (isDebug()) {           // These two lines must be written before init, otherwise these configurations will be invalid in the init process
        ARouter.openLog();     // Print log
        ARouter.openDebug();   // Turn on debugging mode (If you are running in InstantRun mode, you must turn on debug mode! Online version needs to be closed, otherwise there is a security risk)
//        }
        ARouter.init(baseApplication); // As early as possible, it is recommended to initialize in the Application

        // 默认设置为日间模式
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
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
