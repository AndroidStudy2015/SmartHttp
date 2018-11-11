package com.http.www.smarthttp.base;

public abstract class SmartDownloadCallBack extends SmartCallBack {

   public abstract void onDownloadIncomplete();

   public abstract void onDownloading(int progress);


}
