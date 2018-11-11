package com.http.www.smarthttp.retrofit;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by apple on 2017/9/17.
 */

public interface APIService {

    @GET
    Call<String> get(@Url String url, @QueryMap Map<String, Object> params);

    @Headers("Cache-Control:max-age=30")
    //缓存策略我设置在这里，注意要想缓存生效，必须所有参数都一与上次请求一样，注意时间戳会变，所以想使用缓存，参数不能传递时间戳，可以把时间戳放在请求头里
    @GET
    Call<String> getWithCache(@Url String url, @QueryMap Map<String, Object> params);


    @FormUrlEncoded
    @POST
    Call<String> post(@Url String url, @FieldMap Map<String, Object> params);

    @Streaming//边下载，边写入文件，不加这个注解的话，会把下载的内容先写到内存中，下载太大的内容会内存溢出
    @GET
    Call<ResponseBody> download(@Url String url, @QueryMap Map<String, Object> params);

//    一个文件+一个参数   因为是@Part，所以这个参数不会拼接在url后面
    @Multipart
    @POST
    Call<String> uploadOnlyOneParam(@Url String url, @Part MultipartBody.Part file, @Part("description") RequestBody description);


    /**
     * 一个文件+多个参数MAP（但是参数为get方式拼接在了url后面）
     */
    @Multipart
    @POST
    Call<String> upload(@Url String url, @Part MultipartBody.Part file, @QueryMap Map<String, Object> params);




}
