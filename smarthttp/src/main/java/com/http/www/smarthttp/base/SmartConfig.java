package com.http.www.smarthttp.base;

import android.os.Environment;

import com.http.www.smarthttp.BuildConfig;
import com.http.www.smarthttp.utils.LoggerUtil;

import java.io.File;

import okhttp3.Cache;

public class SmartConfig {


    public static String baseUrl;


    static {
        if (BuildConfig.serverUrlType == 1) {
            baseUrl = BuildConfig.LocalBeatUrl;
        } else if (BuildConfig.serverUrlType == 2) {
            baseUrl = BuildConfig.OpenBeatUrl;
        } else if (BuildConfig.serverUrlType == 3) {
            baseUrl = BuildConfig.ProductionUrl;
        }

        LoggerUtil.e("BuildConfig", BuildConfig.serverUrlType + baseUrl);

    }


    public static long connectTimeOut = 10;//设置网络请求超时时间
    public static long writeTimeOut = 10;
    public static long readTimeOut = 10;

    private static long cacheSize = 1024 * 1024 * 20;// 缓存文件最大限制大小20M
    private static String cacheDirectory = Environment.getExternalStorageDirectory() + "/okHttpCaches"; // 设置缓存文件路径
    public static Cache cache = new Cache(new File(cacheDirectory), cacheSize);  //


}
