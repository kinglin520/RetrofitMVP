package com.example.dream.retrofitrxjavaokhttpdemo.app;


import com.example.dream.retrofitrxjavaokhttpdemo.base.ui.BaseBean;
import com.example.dream.retrofitrxjavaokhttpdemo.bean.GirlInfoBean;
import com.example.dream.retrofitrxjavaokhttpdemo.bean.UserInfoBean;
import com.example.dream.retrofitrxjavaokhttpdemo.cache.Cache;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface GankService {
    @FormUrlEncoded
    @POST("/api/user/getUser")
    Observable<BaseBean<UserInfoBean>> test(@Field("getuid") String uid);

    @Cache(path = "http://gank.io/api/data/%E7%A6%8F%E5%88%A9/{page}")
    @GET("http://gank.io/api/data/%E7%A6%8F%E5%88%A9/20/{page}")
    Observable<BaseBean<List<GirlInfoBean>>> getGirlInfo(@Path("page") String page);

}
