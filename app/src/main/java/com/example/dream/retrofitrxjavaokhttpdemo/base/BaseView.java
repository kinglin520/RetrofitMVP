package com.example.dream.retrofitrxjavaokhttpdemo.base;


import com.example.dream.retrofitrxjavaokhttpdemo.http.error.ErrorType;

public interface BaseView {
    void showErrorTip(ErrorType errorType, int errorCode, String message);
}
