package com.http.www.smarthttp.base;

import android.os.Environment;

import com.http.www.smarthttp.retrofit.RetrofitCreator;

import java.io.File;

import okhttp3.Cache;

public class SmartConfig {
    private String baseUrl;

    private long connectTimeOut = 10;//设置网络请求超时时间
    private long writeTimeOut = 10;
    private long readTimeOut = 10;

    private long cacheSize = 1024 * 1024 * 20;// 缓存文件最大限制大小20M
    private String cacheDirectory = Environment.getExternalStorageDirectory() + "/okHttpCaches"; // 设置缓存文件路径
    private Cache cache = new Cache(new File(cacheDirectory), cacheSize);  //


    public static SmartConfig getInstance() {
        return mConfig;
    }

    private static SmartConfig mConfig = new SmartConfig();

    private SmartConfig() {

    }

    public SmartConfig baseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    public SmartConfig connectTimeOut(long connectTimeOut) {
        this.connectTimeOut = connectTimeOut;
        return this;

    }

    public SmartConfig writeTimeOut(long writeTimeOut) {
        this.writeTimeOut = writeTimeOut;
        return this;

    }

    public SmartConfig readTimeOut(long readTimeOut) {
        this.readTimeOut = readTimeOut;
        return this;

    }


    public SmartConfig cache(String cacheDirectory, long cacheSize) {
        File file = new File(cacheDirectory);
        this.cache = new Cache(file, cacheSize);
        return this;

    }

    public void config() {
        RetrofitCreator.getInstance(this);
        SmartHttp.IS_CONGIG_FINISHED = true;
    }

//*********************************************************************

    public String getBaseUrl() {
        return baseUrl;
    }

    public long getConnectTimeOut() {
        return connectTimeOut;
    }

    public long getWriteTimeOut() {
        return writeTimeOut;
    }

    public long getReadTimeOut() {
        return readTimeOut;
    }


    public Cache getCache() {
        return cache;
    }
}
