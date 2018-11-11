package com.http.www.smarthttp.base;

import java.io.File;
import java.util.WeakHashMap;

public interface ISmartRequest {

    /**
     * 设置url
     * @param url
     * @return
     */
    ISmartRequest url(String url);

    /**
     * 设置请求参数
     * @param params
     * @return
     */
    ISmartRequest params(WeakHashMap<String, Object> params);

    /**
     * 设置要上传的文件
     * @param uploadFile
     * @return
     */
    ISmartRequest uploadFile(File uploadFile);

    /**
     * 执行get请求
     * @param smartCallBack
     */
    void get(SmartCallBack smartCallBack);
    /**
     * 执行get请求，带缓存
     * @param smartCallBack
     */
    void getWithCache(SmartCallBack smartCallBack);

    /**
     * 执行post请求
     * @param smartCallBack
     */
    void post(SmartCallBack smartCallBack);

    /**
     * 执行上传操作
     * @param smartCallBack
     */
    void upload(SmartCallBack smartCallBack);

    /**
     * 执行下载操作
     * @param smartDownloadCallBack
     */
    void download(SmartDownloadCallBack smartDownloadCallBack);

    /**
     * 设置下载的文件名称
     * @param downloadFileName
     * @return
     */

    ISmartRequest downloadFileName(String downloadFileName);

    /**
     * 设置下载文件目录
     * @param downDir
     * @return
     */
    ISmartRequest downDir(String downDir);
}
