package com.http.www.smarthttp.base;

public abstract class SmartCallBack {


    public abstract void onSuccess(String response);

    public abstract void onFailure(Throwable t);


    public abstract void onError(int code, String msg);

    public void onRequestStart() {

    }


    public void onRequestEnd() {

    }

}
