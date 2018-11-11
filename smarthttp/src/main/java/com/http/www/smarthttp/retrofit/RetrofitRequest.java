package com.http.www.smarthttp.retrofit;

import com.http.www.smarthttp.base.HttpMethod;
import com.http.www.smarthttp.base.ISmartRequest;
import com.http.www.smarthttp.base.SmartCallBack;
import com.http.www.smarthttp.base.SmartConfig;
import com.http.www.smarthttp.base.SmartDownloadCallBack;
import com.http.www.smarthttp.base.SmartHttp;
import com.http.www.smarthttp.retrofit.download.DownloadHandler;
import com.http.www.smarthttp.utils.DebugUtil;
import com.http.www.smarthttp.utils.LoggerUtil;
import com.http.www.smarthttp.utils.NetworkUtil;

import java.io.File;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

public class RetrofitRequest implements ISmartRequest {
    private final APIService mApiService = RetrofitCreator.apiService;
    private Call<String> call;
    private String mUrl;
    private WeakHashMap<String, Object> mParams;
    private File mUploadFile;//要上传的文件

    private String mDownloadFileName;//下载的文件名
    private String mDownDir;//下载文件存储的目录


    @Override
    public ISmartRequest url(String url) {
        mUrl = url;
        return this;
    }

    @Override

    public ISmartRequest params(WeakHashMap<String, Object> params) {
        mParams = params;
        return this;
    }


    @Override
    public ISmartRequest uploadFile(File uploadFile) {
        mUploadFile = uploadFile;
        return this;
    }

    @Override
    public ISmartRequest downloadFileName(String downloadFileName) {
        mDownloadFileName = downloadFileName;
        return this;
    }

    @Override
    public ISmartRequest downDir(String downDir) {
        mDownDir = downDir;
        return this;
    }

    @Override
    public void get(SmartCallBack smartCallBack) {
        execute(HttpMethod.GET, smartCallBack);
    }

    @Override
    public void getWithCache(SmartCallBack smartCallBack) {
        execute(HttpMethod.GET_WITH_CACHE, smartCallBack);

    }

    @Override
    public void post(SmartCallBack smartCallBack) {
        execute(HttpMethod.POST, smartCallBack);

    }

    @Override
    public void upload(SmartCallBack smartCallBack) {
        execute(HttpMethod.UPLOAD, smartCallBack);

    }

    @Override
    public void download(SmartDownloadCallBack smartDownloadCallBack) {
        new DownloadHandler(mUrl, mParams, mDownDir, mDownloadFileName)
                .handleDownload(smartDownloadCallBack);
    }


    private void execute(HttpMethod method, SmartCallBack smartCallBack) {
        if (!NetworkUtil.isNetworkAvailable(SmartHttp.sContext)) {
            LoggerUtil.e("RetrofitRequest", "没连接到网络");
            return;
        }

        if (mUrl == null) {
            throw new RuntimeException("************请指定url************");
        }
        if (mParams == null) {
            mParams = new WeakHashMap<>();
        }

        if (smartCallBack != null) {
            smartCallBack.onRequestStart();
        }
        switch (method) {
            case GET:
                call = mApiService.get(mUrl, mParams);
                break;
            case GET_WITH_CACHE:
                call = mApiService.getWithCache(mUrl, mParams);
                break;
            case POST:
                call = mApiService.post(mUrl, mParams);
                break;
            case UPLOAD:
                // 一个文件+多个参数MAP（但是参数为get方式拼接在了url后面）
                RequestBody requestBody = RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()), mUploadFile);
                MultipartBody.Part body = MultipartBody.Part.createFormData("file", mUploadFile.getName(), requestBody);
                call = mApiService.upload(mUrl, body, mParams);
                break;
            default:
                call = mApiService.get(mUrl, mParams);//默认get
                break;
        }
        if (call != null) {
            call.enqueue(new RetrofitCallBack(smartCallBack));
        }
        DebugUtil.printUrlAndParams(mParams, SmartConfig.baseUrl, mUrl);
    }
}
