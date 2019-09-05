package com.example.dream.retrofitrxjavaokhttpdemo.http;

import com.blankj.utilcode.util.LogUtils;
import com.example.dream.retrofitrxjavaokhttpdemo.base.BaseApplication;
import com.example.dream.retrofitrxjavaokhttpdemo.base.ui.BaseBean;
import com.example.dream.retrofitrxjavaokhttpdemo.http.config.ApiConfig;
import com.example.dream.retrofitrxjavaokhttpdemo.http.config.RequestConfig;
import com.example.dream.retrofitrxjavaokhttpdemo.http.config.RequestString;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class Api {

    /**
     * 钱包网络请求添加认证头
     */
    public static final int TYPE_HEADER = 1;

    private static ApiConfig mApiConfig;

    private static List<Retrofit> mRetrofitList = new ArrayList<>();

    private static Retrofit mRetrofit = null;

    /*************************缓存设置*********************/
/*
   1. noCache 不使用缓存，全部走网络

    2. noStore 不使用缓存，也不存储缓存

    3. onlyIfCached 只使用缓存

    4. maxAge 设置最大失效时间，失效则不使用 需要服务器配合

    5. maxStale 设置最大失效时间，失效则不使用 需要服务器配合 感觉这两个类似 还没怎么弄清楚，清楚的同学欢迎留言

    6. minFresh 设置有效时间，依旧如上

    7. FORCE_NETWORK 只走网络

    8. FORCE_CACHE 只走缓存*/

    /**
     * 设缓存有效期为两天
     */
    public static final long CACHE_STALE_SEC = 60 * 60 * 24 * 2;

    /**
     * 查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
     * max-stale 指示客户机可以接收超出超时期间的响应消息。如果指定max-stale消息的值，那么客户机可接收超出超时期指定值之内的响应消息。
     */
    private static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
    /**
     * 查询网络的Cache-Control设置，头部Cache-Control设为max-age=0
     * (假如请求了服务器并在a时刻返回响应结果，则在max-age规定的秒数内，浏览器将不会发送对应的请求到服务器，数据由缓存直接返回)时则不会使用缓存而请求服务器
     */
    private static final String CACHE_CONTROL_AGE = "max-age=0";

    public static OkHttpClient getOkHttpClient() {
        //Log拦截器
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //网络缓存文件夹
        File cacheFile = new File(BaseApplication.getApplication().getCacheDir(), "cache");
        //100Mb;
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100);

        return new OkHttpClient.Builder()
                .readTimeout(mApiConfig.getReadTimeOut(), TimeUnit.MILLISECONDS)
                .connectTimeout(mApiConfig.getConnectTimeOut(), TimeUnit.MILLISECONDS)
                .addInterceptor(logInterceptor)
                .addInterceptor(new ParamsInterceptor())
                .cache(cache)
                .build();
    }

    private static Retrofit getRetrofit() {
        if (mRetrofit == null) {
            synchronized (Api.class) {
                if (mRetrofit == null) {
                    LogUtils.e("HostServer---API" + mApiConfig.toString());
                    String url = mApiConfig.getHostServer();
                    mRetrofit = new Retrofit.Builder()
                            .client(getOkHttpClient())
                            .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .baseUrl(url)
                            .build();
                }
            }
        }
        return mRetrofit;
    }

    public static <T> T getService(Class<T> clazz) {
        return getRetrofit().create(clazz);
    }

    public static <R, T extends BaseBean<R>> RequestConfig observable(Observable<T> observable) {
        RequestConfig<R, T> mRequestConfig = new RequestConfig<R, T>();
        mRequestConfig.observable(observable);
        return mRequestConfig;
    }

    public static RequestString observable2(Observable observable) {
        RequestString mRequestConfig = new RequestString();
        mRequestConfig.observable(observable);
        return mRequestConfig;
    }
    /**
     * 设置Api的配置类，该方法请在Application中调用
     *
     * @param config
     */
    public static void setConfig(ApiConfig config) {
        mApiConfig = config;
        LogUtils.e("HostServer---set" + config.toString());
    }


}