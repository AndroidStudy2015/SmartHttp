package com.http.www.smarthttp.base;

import android.content.Context;

import com.http.www.smarthttp.retrofit.RetrofitRequest;

public class SmartHttp {

    public static ISmartRequest sISmartRequest = new RetrofitRequest();
    public static Context sContext;


    public static ISmartRequest with(Context context) {
        sContext = context;
        return sISmartRequest;
    }
}
