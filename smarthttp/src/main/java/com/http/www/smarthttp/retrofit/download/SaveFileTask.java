package com.http.www.smarthttp.retrofit.download;

import android.os.AsyncTask;
import android.util.Log;

import com.http.www.smarthttp.base.SmartDownloadCallBack;
import com.http.www.smarthttp.utils.FileUtil;
import com.http.www.smarthttp.utils.LoggerUtil;

import java.io.File;
import java.io.InputStream;

import okhttp3.ResponseBody;

/**
 * Created by apple on 2017/4/2
 */

final class SaveFileTask extends AsyncTask<Object, Integer, File> {


    private long mFileLength;

    SmartDownloadCallBack mSmartDownloadCallBack;


    public SaveFileTask(SmartDownloadCallBack smartDownloadCallBack) {
        mSmartDownloadCallBack = smartDownloadCallBack;
    }


    @Override
    protected void onProgressUpdate(Integer... progeress) {//主线程，更新UI
        super.onProgressUpdate(progeress);
//        在这里加载一个progressbar，显示进度
        if (mSmartDownloadCallBack != null) {
            mSmartDownloadCallBack.onDownloading(progeress[0]);
        }
    }

    @Override
    protected File doInBackground(Object... params) {//子线程，请求数据，写入本地数据
        String downloadDir = (String) params[0];
        final ResponseBody body = (ResponseBody) params[1];
        final String name = (String) params[2];
        final InputStream is = body.byteStream();

        mFileLength = body.contentLength();

        if (downloadDir == null || downloadDir.equals("")) {
            downloadDir = "down_loads";
        }


        FileUtil.ProgressCallBack progressCallBack = new FileUtil.ProgressCallBack() {

            @Override
            public void onProgressCallBack(int progress) {
                publishProgress(progress);

            }
        };


        File file = FileUtil.writeToDisk(body, is, downloadDir, name, progressCallBack);
        return file;

    }

    @Override
    protected void onPostExecute(File file) {//主线程。更新UI
        super.onPostExecute(file);
        //        中途网断了，会使得文件下载不完全
        Log.e("ccc", file.length() + "   " + mFileLength + "");
        if (file.length() < mFileLength) {
            LoggerUtil.e("SaveFileTask", "可能是中途网断了，使得文件下载不完全");
            if (mSmartDownloadCallBack != null) {
                mSmartDownloadCallBack.onDownloadIncomplete();
            }
        } else {
            if (mSmartDownloadCallBack != null) {
                mSmartDownloadCallBack.onSuccess(file.getAbsolutePath());
            }
            if (mSmartDownloadCallBack != null) {
                mSmartDownloadCallBack.onRequestEnd();
            }
        }
    }






}
