package com.http.www.smarthttp.utils;

import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

public class DebugUtil {


    public static void printUrlAndParams(WeakHashMap<String, Object> params, String baseUrl, String url) {
        if (params==null){
            return;
        }

        if (LoggerUtil.LEVEL < 6) {
            //调试的时候LEVEL是小于6的，打包后会变为6
            Set<Map.Entry<String, Object>> entries = params.entrySet();
            StringBuilder sbParams = new StringBuilder();

            for (Map.Entry<String, Object> entry : entries) {
                String key = entry.getKey();
                if (sbParams.length() == 0) {
                    sbParams = sbParams.append(key).append("=").append(params.get(key));
                } else {
                    sbParams = sbParams.append("&").append(key).append("=").append(params.get(key));
                }
            }

            LoggerUtil.e("debug_request", baseUrl + url + "?" + sbParams.toString());

        }
    }

    public static void printResponse(String response) {
            LoggerUtil.e("debug_response", response);

    }
}
