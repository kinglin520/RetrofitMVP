# 封装Retrofit网络架构和应用MVP模式
### 1.Retrofit封装
######  基于Retrofit2.3+Rxjava2.3+Okhttp3.9 封装的网络层数据操作基础架构，架构思想围绕Retrofit内部封装的RxJava响应式编程展开，并封装了完善的网络错误信息传递机制方便后期维护。封装代码清晰易读，使用上简易方便，封装框架虽瘦却扩展性很强，欢迎志同道合人士前来互相学习沟通；</br> ######
#### 示例：首先，创建被观察者Observable，包括：请求方式和URL，请求参数，请求返回bean...
```
   public interface LoginService {
    @GET("/message/sms")
    Observable<BaseBean> test(@Query("mobile") String mobile);
    /**
     * @param mobile 手机号
     * @param code   验证码
     */
    @FormUrlEncoded
    @POST("/auth/mobile/token")
    Observable<BaseBean<TokenBean>> getToken(@Field("mobile") String mobile, @Field("code") String code);
 }
 ```
#### 其次，整个网络服务请求使用时，创建被观察者-》Api.getService(LoginService.class).getToken("18518762090","111111")，并通过.doRequest订阅观察者RxSubscriber创建观察者时，指定返回Bean，在_onSuccess方法中获取请求结果在_onError处理本次请求错误响应...
```
    /**
     * 获取Token
     */
    public void getToken() {
        Api.observable(Api.getService(LoginService.class).getToken("18518762090","111111"))
                .doRequest(new RxSubscriber<TokenBean, BaseBean<TokenBean>>() {
                    @Override
                    protected void _onSuccess(TokenBean test, String successMessage) {
                        System.out.print(successMessage);
                    }
                    @Override
                    protected void _onError(ErrorType errorType, String errorCode, String message, TokenBean data) {
                        System.out.print(message);
                    }
                });
    }
```
#### 最后，在项目的Application中，配置网络相关信息
```
        ApiConfig apiConfig = new ApiConfig();
        apiConfig.setConnectTimeOut(100000);
        apiConfig.setReadTimeOut(100000);
        apiConfig.setHostServer("http://39.96.204.48:9999");
        Api.setConfig(apiConfig);
```

### 2.封装MVP基类；
