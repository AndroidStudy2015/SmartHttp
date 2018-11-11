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
