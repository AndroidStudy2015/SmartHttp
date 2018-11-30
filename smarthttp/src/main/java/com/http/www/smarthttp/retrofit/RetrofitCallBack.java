package com.http.www.smarthttp.retrofit;

import com.http.www.smarthttp.base.SmartCallBack;
import com.http.www.smarthttp.utils.DebugUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitCallBack implements Callback<String> {
    private SmartCallBack mSmartCallBack;

    public RetrofitCallBack(SmartCallBack smartCallBack) {
        mSmartCallBack = smartCallBack;
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {

        if (response.isSuccessful()) {
            // isSuccessful() 方法的 HTTP 状态码 是 200 到 300之间，
            // 在这之间都算是请求成功,并且在正常情况下只有 200 的时候后台才会返回数据，其他是没有数据的,
            // 所以更严格讲话，应该是(200 == response.code();
            if (call.isExecuted()) {
                if (mSmartCallBack != null) {
                    mSmartCallBack.onSuccess(response.body());
                    DebugUtil.printResponse("onSuccess:"+response.body());
                }
            }
        } else {
            if (mSmartCallBack != null) {
                mSmartCallBack.onError(response.code(), response.message());
                DebugUtil.printResponse("onError: code:"+response.code()+"\n message:"+response.message());

            }
        }
        if (mSmartCallBack != null) {
            mSmartCallBack.onRequestEnd();
        }
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        if (mSmartCallBack != null) {
            mSmartCallBack.onFailure(t);
            mSmartCallBack.onRequestEnd();
            DebugUtil.printResponse("onFailure:"+t.getLocalizedMessage());

        }
    }
}
