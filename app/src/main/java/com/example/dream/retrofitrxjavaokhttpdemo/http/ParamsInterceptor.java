package com.example.dream.retrofitrxjavaokhttpdemo.http;

import android.text.TextUtils;
import android.util.Log;

import com.blankj.utilcode.util.StringUtils;
import com.example.dream.retrofitrxjavaokhttpdemo.base.BaseApplication;
import com.example.dream.retrofitrxjavaokhttpdemo.cache.CacheInject;
import com.example.dream.retrofitrxjavaokhttpdemo.utils.SharedPreferencesUtil;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.Util;
import okio.Buffer;
import okio.BufferedSource;


/**
 * 封装公共参数
 *
 * @author wenlin
 * @date 2019/4/10
 */
public class ParamsInterceptor implements Interceptor {
    private static final Charset UTF8 = Charset.forName("UTF-8");

    public static final String CHANNEL_ID = "1";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();

        String method = originalRequest.url().toString();

        //组装参数
        String bodyStr = bodyToString(originalRequest.body());

        Request.Builder requestBuilder = originalRequest.newBuilder();

        StringBuilder stringBuilder = new StringBuilder();

        requestBuilder.addHeader("Content-Type", "application/json;charset=UTF-8")
                .addHeader("Authorization", stringBuilder.toString());  // token

        if ("POST".equals(originalRequest.method())) {
            requestBuilder.post(RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), bodyStr));
        }

        originalRequest = requestBuilder.build();

        Response response;

        MediaType contentType;

        try {
            response = chain.proceed(originalRequest);
        } catch (Exception e) {
            // 创建response 放入缓存数据
            Response newResponse = new Response.Builder()
                    .request(chain.request())
                    .protocol(Protocol.HTTP_1_1)
                    .code(504)
                    .message("Unsatisfiable Request (only-if-cached)")
                    .body(Util.EMPTY_RESPONSE)
                    .sentRequestAtMillis(-1L)
                    .receivedResponseAtMillis(System.currentTimeMillis())
                    .build();
            contentType = newResponse.body().contentType();
            // 取出缓存
            String result = SharedPreferencesUtil.getString(BaseApplication.getApplication(), method + bodyStr, "");
            if (!StringUtils.isEmpty(result)) {
                if (CacheInject.map.containsKey(method) && (boolean) CacheInject.map.get(method)) {
                    return newResponse.newBuilder()
                            .body(ResponseBody.create(contentType, result))
                            .build();
                } else {
                    return newResponse.newBuilder().code(200)
                            .body(ResponseBody.create(contentType, result))
                            .build();
                }
            }
            throw e;
        }
        Log.e("APi请求URL:", method);
        Log.e("headers参数:", originalRequest.headers().toString());
        Log.e("body参数:", bodyStr);

        int code = response.code();

        ResponseBody responseBody = response.body();

        contentType = responseBody.contentType();
        if (code == 200) {
            try { // 日志保存

                long contentLength = responseBody.contentLength();
                String result = "";
                if (contentLength != 0) {
                    Charset charset = UTF8;
                    if (contentType != null) {
                        charset = contentType.charset(UTF8);
                    }
                    BufferedSource source = responseBody.source();
                    source.request(Long.MAX_VALUE); // Buffer the entire body.
                    Buffer buffer = source.buffer();
                    result = buffer.clone().readString(charset);
                }
                if (!TextUtils.isEmpty(result)) {
                    if (CacheInject.map.containsKey(method)) {
                        SharedPreferencesUtil.putString(BaseApplication.getApplication(), method + bodyStr, result);
                    }
//                    boolean isSuccess = result.contains("SUCCESS");
//                    Map<String, String> map = new HashMap<>();
////                    map.put("date", TimeUtils.getNowTimeString());
//                    map.put("type", method);
//                    map.put("url", method);
//                    map.put("flag", isSuccess || result.contains("成功") ? "1" : "0");
//                    map.put("request", originalRequest.headers().toString());
//                    map.put("response", result);
//                    new ApiLogManager().saveLog(map);
//                    if (isSuccess) {
//                        DBManager.putCache(method, bodyStr, result);//存入缓存
//                    } else {
//                        result = DBManager.getCache(method, bodyStr);//取出缓存
//                        if (!StringUtils.isEmpty(result)) {
//                            return response.newBuilder()
//                                    .body(ResponseBody.create(contentType, result))
//                                    .build();
//                        }
//                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return response;
    }

    /**
     * 设置requestBody的编码格式为json
     */
    private static String bodyToString(final RequestBody request) {
        try {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            if (copy != null)
                copy.writeTo(buffer);
            else
                return "";
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }
}
