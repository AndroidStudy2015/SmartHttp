package com.http.www.smarthttp.retrofit.download;

import android.os.AsyncTask;
import android.util.Log;

import com.http.www.smarthttp.base.SmartDownloadCallBack;
import com.http.www.smarthttp.retrofit.RetrofitCreator;

import java.util.WeakHashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by apple on 2017/4/2
 */

public final class DownloadHandler {


    private String mUrl;
    private WeakHashMap<String, Object> mParams;



    private String mDownloadFileName;//下载的文件名
    private String mDownDir;//下载文件存储的目录




    public DownloadHandler(String url, WeakHashMap<String, Object> params, String downDir, String downloadFileName) {
        mUrl=url;
        mParams=params;
        mDownDir=downDir;
        mDownloadFileName=downloadFileName;
    }

    public final void handleDownload(final SmartDownloadCallBack smartDownloadCallBack) {
        if (smartDownloadCallBack != null) {
            smartDownloadCallBack.onRequestStart();
        }
        if (mParams == null) {
            mParams = new WeakHashMap<>();
        }
        RetrofitCreator.apiService
                .download(mUrl, mParams)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        if (response.isSuccessful()) {
                            final ResponseBody responseBody = response.body();

//                            把流保存文件到本地
                            final SaveFileTask task = new SaveFileTask(smartDownloadCallBack);
                            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
                                    mDownDir, responseBody, mDownloadFileName);

                            //这里一定要注意判断，否则文件下载不全
                            if (task.isCancelled()) {
                                if (smartDownloadCallBack != null) {
                                    smartDownloadCallBack.onRequestEnd();
                                }
                            }
                        } else {
                            if (smartDownloadCallBack != null) {
                                smartDownloadCallBack.onError(response.code(), response.message());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("ccc","vvvvvvvvv");

                        if (smartDownloadCallBack != null) {
                            smartDownloadCallBack.onFailure(t);
                        }
                    }
                });
    }
}
