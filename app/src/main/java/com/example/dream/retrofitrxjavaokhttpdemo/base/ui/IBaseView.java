package com.example.dream.retrofitrxjavaokhttpdemo.base.ui;


import com.example.dream.retrofitrxjavaokhttpdemo.http.error.ErrorType;

public interface IBaseView {
    void showErrorTip(ErrorType errorType, int errorCode, String message);
}
