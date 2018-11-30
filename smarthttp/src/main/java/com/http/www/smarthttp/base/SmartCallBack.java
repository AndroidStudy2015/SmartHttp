package com.http.www.smarthttp.base;

public abstract class SmartCallBack {

    /**
     * 服务器响应成功，状态码httpcode为200~300之间时候的回调（严格讲，一般情后台是200才返回数据的，目前写法有点不严谨）
     *
     * @param response
     */
    public abstract void onSuccess(String response);

    /**
     * 服务器响应成功，但是服务器返回的状态码httpcode，不在200~300之间
     *
     * @param code http响应码
     * @param msg
     */

    public abstract void onError(int code, String msg);


    /**
     * 服务器响应失败，没有返回状态码httpcode（网络连接有问题，或者服务器接口内部错误，没走到响应这一步）
     *
     * @param t
     */
    public abstract void onFailure(Throwable t);


    /**
     * 请求开始时的回调
     */
    public void onRequestStart() {

    }

    /**
     * 请求结束时候的回调
     */
    public void onRequestEnd() {

    }

}
