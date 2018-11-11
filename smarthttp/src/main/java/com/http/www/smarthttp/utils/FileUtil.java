package com.http.www.smarthttp.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.webkit.MimeTypeMap;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.ResponseBody;

/**
 * Created by apple
 */
public final class FileUtil {

    //格式化的模板
    private static final String TIME_FORMAT = "_yyyyMMdd_HHmmss";

    private static final String SDCARD_DIR =
            Environment.getExternalStorageDirectory().getPath();


    private static String getTimeFormatName(String timeFormatHeader) {
        final Date date = new Date(System.currentTimeMillis());
        //必须要加上单引号
        final SimpleDateFormat dateFormat = new SimpleDateFormat("'" + timeFormatHeader + "'" + TIME_FORMAT, Locale.getDefault());
        return dateFormat.format(date);
    }

    /**
     * @param timeFormatHeader 格式化的头(除去时间部分)
     * @param extension        后缀名
     * @return 返回时间格式化后的文件名
     */
    public static String getFileNameByTime(String timeFormatHeader, String extension) {
        return getTimeFormatName(timeFormatHeader) + "." + extension;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static File createDir(String sdcardDirName) {
        //拼接成SD卡中完整的dir
        final String dir = SDCARD_DIR + "/" + sdcardDirName + "/";
        final File fileDir = new File(dir);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        return fileDir;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static File createFile(String sdcardDirName, String fileName) {
        return new File(createDir(sdcardDirName), fileName);
    }

    private static File createFileByTime(String sdcardDirName, String timeFormatHeader, String extension) {
        final String fileName = getFileNameByTime(timeFormatHeader, extension);
        return createFile(sdcardDirName, fileName);
    }

    //获取文件的MIME
    public static String getMimeType(String filePath) {
        final String extension = getExtension(filePath);
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
    }

    //获取文件的后缀名
    public static String getExtension(String filePath) {
        String suffix = "";
        final File file = new File(filePath);
        final String name = file.getName();
        final int idx = name.lastIndexOf('.');
        if (idx > 0) {
            suffix = name.substring(idx + 1);
        }
        return suffix;
    }


    public interface ProgressCallBack {
        void onProgressCallBack(int progress);
    }

    public static File writeToDisk(ResponseBody body, InputStream is, String dir, String name, ProgressCallBack progressCallBack) {
        int lastProgress = 0;
        long contentLength = body.contentLength();
        long downLoadLength = 0;
        final File file = FileUtil.createFile(dir, name);

        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;

        try {
            bis = new BufferedInputStream(is);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);

            byte data[] = new byte[1024 * 4];
            int count;
            while ((count = bis.read(data)) != -1) {
                bos.write(data, 0, count);
                downLoadLength = downLoadLength + count;

                if (progressCallBack != null) {
                    int progress = (int) ((downLoadLength / (float) contentLength) * 100);

                    if (progress >=lastProgress ) {//为了怕更新太频繁，可以写成progress >=lastProgress +3，目前没做限制处理

                        progressCallBack.onProgressCallBack(progress);

                        lastProgress = progress;
                    }
                }

            }

            bos.flush();
            fos.flush();


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bos != null) {
                    bos.close();
                }
                if (fos != null) {
                    fos.close();
                }
                if (bis != null) {
                    bis.close();
                }
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return file;
    }


    public static void autoInstallApk(Context context, File file) {
        if (FileUtil.getExtension(file.getPath()).equals("apk")) {
            Intent intent = new Intent(Intent.ACTION_INSTALL_PACKAGE);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                //必须添加这句话，因为后面使用的时候getApplicationContext.startActivity
                //如果是Activity.startActivity的话可以不添加FLAG_ACTIVITY_NEW_TASK
                //使用getApplicationContext.startActivity开启Activity有一个好处是，
                //即使是现在这个Activity在后台了，也会执行打开另一个activity的效果
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            } else {
                // 声明需要的临时的权限
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                // 第二个参数，即第一步中配置的authorities,注意第二个参数，必须与清单文件配置的路径一样
                // 参阅 https://blog.csdn.net/Android_Study_OK/article/details/82414659
                Uri contentUri = FileProvider.getUriForFile(context, "com.http.www.smarthttp" + ".fileprovider", file);
                intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
            }
            context.startActivity(intent);
        }
    }


}
