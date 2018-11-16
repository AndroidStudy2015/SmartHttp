package com.lucky.www.smarthttp;

import android.app.Application;

import com.http.www.smarthttp.base.SmartHttp;
import com.http.www.smarthttp.utils.LoggerUtil;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LoggerUtil.LEVEL = LoggerUtil.INFO;
        SmartHttp.init()
                .baseUrl("http://tcc.taobao.com")
                .config();
    }
}
