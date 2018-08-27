package com.example.dream.retrofitrxjavaokhttpdemo.http.error;


public enum ErrorType {

    //未知错误
    ERROR_UNKNOWN,

    //未知http错误
    ERROR_UNKNOWN_HTTP,

    //数据解析错误
    ERROR_PARSE,

    //网络错误
    ERROR_NETWORK,

    //http错误
    ERROR_HTTP,

    //接口返回的业务错误
    ERROR_API;
}
