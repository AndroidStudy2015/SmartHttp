# SmartHttp
基于retrofit2.0 支持下载、上传、post、get的小框架 

[![](https://jitpack.io/v/AndroidStudy2015/SmartHttp.svg)](https://jitpack.io/#AndroidStudy2015/SmartHttp)


## 引入项目

```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

```
dependencies {
	        implementation 'com.github.AndroidStudy2015:SmartHttp:lastest-version'
	}
```

### 使用
```

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LoggerUtil.LEVEL = LoggerUtil.INFO;//控制调试信息log打印
        SmartHttp.init()//必须先初始化
                .baseUrl("http://tcc.taobao.com")
                .config();
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
```
