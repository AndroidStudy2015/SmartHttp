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
	        implementation 'com.github.AndroidStudy2015:SmartHttp:1.0.0'
	}
```

### 注意
```
-------------------服务器地址切换-------------------
 服务器地址类型：1-本地 2-公测 3-生产
serverUrlType=3
# 本地环境url-1
LocalBeatUrl="http://www.xxx.com";
# 公测环境url-2
OpenBeatUrl="http://www.xxx.com";
# 生产环境url-3
ProductionUrl="http://www.xxx.com";
# 控制log打印
isPrintLog=true
```
