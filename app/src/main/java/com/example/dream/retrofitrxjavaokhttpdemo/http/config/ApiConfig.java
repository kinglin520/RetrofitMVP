package com.example.dream.retrofitrxjavaokhttpdemo.http.config;

/**
 * Created by Administrator on 2018/3/26 0026.
 *
 * @Desc: Api的配置类
 */

public class ApiConfig {
    /**
     * 服务器域名
     */
    private String mHostServer;

    /**
     * 读超时
     */
    private int mReadTimeOut;
    /**
     * 连接超时
     */
    private int mConnectTimeOut;

    public String getHostServer() {
        return mHostServer;
    }

    public void setHostServer(String hostServer) {
        this.mHostServer = hostServer;
    }


    public int getReadTimeOut() {
        return mReadTimeOut;
    }

    public void setReadTimeOut(int readTimeOut) {
        this.mReadTimeOut = readTimeOut;
    }

    public int getConnectTimeOut() {
        return mConnectTimeOut;
    }

    public void setConnectTimeOut(int writeTimeOut) {
        this.mConnectTimeOut = writeTimeOut;
    }

    @Override
    public String toString() {
        return mHostServer + "///" + mReadTimeOut + "///" + mConnectTimeOut;
    }
}
