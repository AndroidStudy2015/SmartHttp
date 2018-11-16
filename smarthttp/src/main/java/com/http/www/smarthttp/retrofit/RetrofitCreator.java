package com.http.www.smarthttp.retrofit;

import android.text.TextUtils;

import com.http.www.smarthttp.base.SmartConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitCreator {
    private static RetrofitCreator sRetrofitCreator;
    public static APIService apiService;

    private RetrofitCreator(SmartConfig config) {
        if (config.getBaseUrl() == null || TextUtils.isEmpty(config.getBaseUrl())) {
            throw new RuntimeException("您还没有指定baseurl，要使用FastNet,必须指定一个baseurl");
        }

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        OkHttpClient okHttpClient = builder.connectTimeout(config.getConnectTimeOut(), TimeUnit.SECONDS) // 设置连接超时时间
                .writeTimeout(config.getWriteTimeOut(), TimeUnit.SECONDS)// 设置写入超时时间
                .readTimeout(config.getReadTimeOut(), TimeUnit.SECONDS)// 设置读取数据超时时间
                .retryOnConnectionFailure(true)// 设置进行连接失败重试
                .cache(config.getCache())// 设置缓存
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(config.getBaseUrl())
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(okHttpClient)
                .build();

        apiService = retrofit.create(APIService.class);


    }


    public static RetrofitCreator getInstance(SmartConfig config) {

        if (sRetrofitCreator == null) {
            synchronized (RetrofitCreator.class) {
                if (sRetrofitCreator == null) {
                    sRetrofitCreator = new RetrofitCreator(config);
                }
            }
        }
        return sRetrofitCreator;
    }


}
