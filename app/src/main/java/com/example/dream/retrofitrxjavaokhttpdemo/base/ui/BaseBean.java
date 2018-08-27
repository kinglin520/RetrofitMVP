package com.example.dream.retrofitrxjavaokhttpdemo.base.ui;

import java.io.Serializable;

/**
 * @Desc 接口返回数据JavaBean的基类，所有JavaBean务必继承该基类，方便封装错误统一处理
 */

public class BaseBean<T> implements Serializable {

    //接口返回的业务码
    private int status;
    //接口返回信息
    private String message;
    //接口返回数据
    private T results;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }
}
