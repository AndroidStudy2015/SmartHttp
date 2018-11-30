package com.http.www.smarthttp.base;

import com.http.www.smarthttp.retrofit.RetrofitCreator;
import com.http.www.smarthttp.retrofit.RetrofitRequest;

public class SmartHttp {
    public static boolean IS_CONGIG_FINISHED = false;

    public static ISmartRequest with() {
        if (IS_CONGIG_FINISHED) {
            return new RetrofitRequest( RetrofitCreator.apiService);
        } else {
            throw new RuntimeException(
                    "\n******************请调用******************\n" +
                            "SmartHttp.init()\n" +
                            "         .baseUrl(xxx)\n" +
                            "         .cache(xxx,xxx)\n" +
                            "         .connectTimeOut(xxx)\n" +
                            "         .writeTimeOut(xxx)\n" +
                            "         .config();\n" +
                            "******************完成初始化******************");
        }
    }

    public static SmartConfig init() {
        return SmartConfig.getInstance();
    }
}