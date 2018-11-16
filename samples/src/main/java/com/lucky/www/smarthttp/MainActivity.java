package com.lucky.www.smarthttp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.http.www.smarthttp.base.SmartCallBack;
import com.http.www.smarthttp.base.SmartHttp;
import com.http.www.smarthttp.utils.LoggerUtil;

import java.util.WeakHashMap;

public class MainActivity extends AppCompatActivity {

    private Button mBtGet;
    private Button mBtDownLoad;
    private Button bt_test_download;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtGet = (Button) findViewById(R.id.bt_test_get);
        bt_test_download = (Button) findViewById(R.id.bt_test_download);

        mBtGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                get();//get

            }
        });

        bt_test_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                get();//get
            }
        });


    }

    private void get() {
        WeakHashMap<String, Object> params = new WeakHashMap<>();
        params.put("tel", "13912345678");
        String url = "/cc/json/mobile_tel_segment.htm";

        SmartHttp.with(this)
                .url(url)
                .params(params)
                .get(new SmartCallBack() {
                    @Override
                    public void onSuccess(String response) {
                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }


                    @Override
                    public void onError(int code, String msg) {

                    }

                    @Override
                    public void onRequestStart() {
                        super.onRequestStart();
                        LoggerUtil.e("ccc", "onRequestStart");

                    }

                    @Override
                    public void onRequestEnd() {
                        super.onRequestEnd();
                        LoggerUtil.e("ccc", "onRequestEnd");

                    }
                });
    }


}
