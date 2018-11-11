package com.http.www.smarthttp.retrofit;

import android.text.TextUtils;

import com.http.www.smarthttp.base.SmartConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitCreator {

    public static APIService apiService;

    static {
        if (SmartConfig.baseUrl == null|| TextUtils.isEmpty(SmartConfig.baseUrl)) {
            throw new RuntimeException("您还没有指定baseurl，要使用FastNet,必须指定一个baseurl");
        }

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        OkHttpClient okHttpClient = builder.connectTimeout(SmartConfig.connectTimeOut, TimeUnit.SECONDS) // 设置连接超时时间
                .writeTimeout(SmartConfig.writeTimeOut, TimeUnit.SECONDS)// 设置写入超时时间
                .readTimeout(SmartConfig.readTimeOut, TimeUnit.SECONDS)// 设置读取数据超时时间
                .retryOnConnectionFailure(true)// 设置进行连接失败重试
                .cache(SmartConfig.cache)// 设置缓存
                .build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SmartConfig.baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(okHttpClient)
                .build();

        apiService = retrofit.create(APIService.class);

    }


}
