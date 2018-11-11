package com.http.www.smarthttp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

public class NetworkUtil {

    /**
     * 检查网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {

        ConnectivityManager manager = (ConnectivityManager) context
                .getApplicationContext().getSystemService(
                        Context.CONNECTIVITY_SERVICE);

        if (manager == null) {
            return false;
        }

        NetworkInfo networkinfo = manager.getActiveNetworkInfo();

        if (networkinfo == null || !networkinfo.isAvailable()) {
            return false;
        }

        return true;
    }


    /**
     * 检查wifi是否处开连接状态
     *
     * @return
     */
    public static boolean isWifiConnect(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifiInfo;
        if (connManager != null) {
            mWifiInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            return mWifiInfo.isConnected();
        } else {
            return false;
        }
    }

    /**
     * 检查wifi强弱
     */
    public static void checkWifiState(Context context) {
        if (isWifiConnect(context)) {
            WifiManager mWifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            if (mWifiManager != null) {
                WifiInfo mWifiInfo = mWifiManager.getConnectionInfo();
                int wifi = mWifiInfo.getRssi();//获取wifi信号强度
                if (wifi > -50 && wifi < 0) {//最强

                    LoggerUtil.e("ccc===", "WIFI信号=最强");
                } else if (wifi > -70 && wifi < -50) {//较强
                    LoggerUtil.e("ccc===", "WIFI信号=较强");

                } else if (wifi > -80 && wifi < -70) {//较弱
                    LoggerUtil.e("ccc===", "WIFI信号=较弱");

                } else if (wifi > -100 && wifi < -80) {//微弱
                    LoggerUtil.e("ccc===", "WIFI信号=微弱");

                }
            }

        } else {
            //无连接
            LoggerUtil.e("ccc===", "WIFI已断开");

        }
    }
}