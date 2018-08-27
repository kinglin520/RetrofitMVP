package com.example.dream.retrofitrxjavaokhttpdemo.http.error;

/**
 * @Desc 网络请求错误码
 */

public class ErrorCode {

    //未知错误
    public static final int CODE_UNKNOWN = 10000;

    //解析错误
    public static final int CODE_PARSE = 10001;

    //网络连接错误
    public static final int CODE_NETWORK = 10002;

    /**
     * 警告：该错误码并未在RxSubscriber中错误统一封装处理中进行处理，设定该值只是用于在_onError()方法中方便统一处理空界面或错误界面的视图类型。调用显示空界面或错误界面视图的方法传递ErrorCode即可
     */
//    public static final int CODE_EMPTY_LIST_DATA=10003;

    //接口定义错误码
    public static final int CODE_SERVER_1000 = 0;

    public static final int CODE_SERVER_SUCCESS = CODE_SERVER_1000;  //接口返回码为1000时视为成功

    public static final int CODE_SERVER_1001 = 1001;

    public static final int CODE_SERVER_1006 = 1006;

    public static final int CODE_SERVER_1103 = 1103;

    public static final int CODE_SERVER_1104 = 1104;

    public static final int CODE_SERVER_1105 = 1105;

    public static final int CODE_SERVER_1106 = 1106;

    public static final int CODE_SERVER_1107 = 1107;

    public static final int CODE_SERVER_1200 = 1200;
    //token失效
    public static final int CODE_SERVER_9105 = 9105;
    //临时token失效
    public static final int CODE_SERVER_9106 = 9106;
    //多设备登录
    public static final int CODE_SERVER_9107 = 9107;
    //账户被冻结
    public static final int CODE_SERVER_9108 = 9108;
    //服务器维护中
    public static final int CODE_SERVER_9109 = 9109;


    //http 错误码
    public static final int CODE_UNAUTHORIZED = 401;

    public static final int CODE_FORBIDDEN = 403;

    public static final int CODE_NOT_FOUND = 404;

    public static final int CODE_REQUEST_TIMEOUT = 408;

    public static final int CODE_INTERNAL_SERVER_ERROR = 500;

    public static final int CODE_BAD_GATEWAY = 502;

    public static final int CODE_SERVICE_UNAVAILABLE = 503;

    public static final int CODE_GATEWAY_TIMEOUT = 504;


}
