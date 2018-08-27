package com.example.dream.retrofitrxjavaokhttpdemo.http.error;

/**
 * @Desc 自定义Api异常
 */
public class ApiException extends Exception {

    private int code;  //错误码
    private ErrorType errorType;  //错误类型
    public ApiException(int code, ErrorType errorType, String message, Throwable cause){
        this(message,cause);
        this.code=code;
        this.errorType=errorType;
    }


    private ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 获取错误码
     * @return
     */
    public int getCode() {
        return code;
    }

    public ErrorType getErrorType() {
        return errorType;
    }

}