package com.example.dream.retrofitrxjavaokhttpdemo.http.error;

import android.net.ParseException;

import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import okhttp3.ResponseBody;
import retrofit2.HttpException;

/**
 * @Desc 异常转换器，
 */

public class ExceptionConverter {

    /**
     * 将非{@link ApiException}转换为该种异常
     * @param throwable
     * @return ApiException
     */
    public static ApiException convertException(Throwable throwable){

        ApiException apiException;
        String errorMessage="";
        ErrorType errorType=null;
        int errorCode;

        if (throwable instanceof ApiException){
            apiException = (ApiException) throwable;
        } else if (throwable instanceof HttpException){

            HttpException httpException = (HttpException) throwable;
            switch (httpException.code()) {
                case ErrorCode.CODE_REQUEST_TIMEOUT:
                    errorMessage="网络请求超时";
                    errorType=ErrorType.ERROR_HTTP;
                    errorCode= ErrorCode.CODE_REQUEST_TIMEOUT;
                    break;
                case ErrorCode.CODE_NOT_FOUND:
                    errorMessage = "抱歉!!您访问的页面不存在";
                    errorType=ErrorType.ERROR_HTTP;
                    errorCode= ErrorCode.CODE_NOT_FOUND;
                    break;
                //Http码为401时需要用户重新登录，网关对用户Token进行了验证，防止多设备登录，多设备登陆时接口请求返回401码
                case ErrorCode.CODE_UNAUTHORIZED:
                    //401码需要获取服务器返回的数据
                    ResponseBody mResponseBody=httpException.response().errorBody();
                    try {
                        errorMessage = mResponseBody == null ? "网络错误":mResponseBody.string();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    errorType=ErrorType.ERROR_HTTP;
                    errorCode= ErrorCode.CODE_UNAUTHORIZED;
                    break;
                case ErrorCode.CODE_FORBIDDEN:
                    errorMessage = "网络错误";
                    errorType=ErrorType.ERROR_HTTP;
                    errorCode= ErrorCode.CODE_FORBIDDEN;
                    break;
                case ErrorCode.CODE_INTERNAL_SERVER_ERROR:
                    errorMessage = "服务器维护中，请稍后重试";
                    errorType=ErrorType.ERROR_HTTP;
                    errorCode= ErrorCode.CODE_INTERNAL_SERVER_ERROR;
                    break;
                case ErrorCode.CODE_BAD_GATEWAY:
                    errorMessage = "网络错误";
                    errorType=ErrorType.ERROR_HTTP;
                    errorCode= ErrorCode.CODE_BAD_GATEWAY;
                    break;
                case ErrorCode.CODE_SERVICE_UNAVAILABLE:
                    errorMessage = "网络错误";
                    errorType=ErrorType.ERROR_HTTP;
                    errorCode= ErrorCode.CODE_SERVICE_UNAVAILABLE;
                    break;
                case ErrorCode.CODE_GATEWAY_TIMEOUT:
                    errorMessage = "网络连接超时";
                    errorType=ErrorType.ERROR_HTTP;
                    errorCode= ErrorCode.CODE_GATEWAY_TIMEOUT;
                    break;
                default:
                    errorMessage="未知网络错误";
                    errorType=ErrorType.ERROR_UNKNOWN_HTTP;
                    errorCode= ErrorCode.CODE_UNKNOWN;
                    break;
            }

            apiException = new ApiException(errorCode,errorType,errorMessage,throwable);

        }else if (throwable instanceof JsonSyntaxException ||
                throwable instanceof JsonParseException
                ||throwable instanceof JSONException
                ||throwable instanceof ParseException){

            errorMessage="数据解析错误";
            errorType=ErrorType.ERROR_PARSE;
            errorCode= ErrorCode.CODE_PARSE;

            apiException = new ApiException(errorCode,errorType,errorMessage,throwable);

        }else if (throwable instanceof ConnectException){
            errorMessage="当前无网络，请检查网络";
            errorType=ErrorType.ERROR_NETWORK;
            errorCode= ErrorCode.CODE_NETWORK;

            apiException = new ApiException(errorCode,errorType,errorMessage,throwable);

        }else if (throwable instanceof UnknownHostException){
            errorMessage="似乎已断开与互联网的连接";
            errorType=ErrorType.ERROR_NETWORK;
            errorCode= ErrorCode.CODE_NETWORK;

            apiException = new ApiException(errorCode,errorType,errorMessage,throwable);

        }else if (throwable instanceof SocketTimeoutException){
            errorMessage="网络请求超时";
            errorType=ErrorType.ERROR_NETWORK;
            errorCode= ErrorCode.CODE_NETWORK;

            apiException = new ApiException(errorCode,errorType,errorMessage,throwable);

        }else {
            errorMessage="未知错误";
            errorType=ErrorType.ERROR_UNKNOWN;
            errorCode= ErrorCode.CODE_UNKNOWN;

            apiException = new ApiException(errorCode,errorType,errorMessage,throwable);

        }

        return apiException;
    }
}
