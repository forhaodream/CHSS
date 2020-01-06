package com.sheepshop.businessside.utils;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.util.Log;


import com.sheepshop.businessside.MyApp;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 文件存储utils
 */
public class FileUtils {

    public static File getCacheDir() {
        Context context = MyApp.getContext();
        File cacheDir = context.getExternalCacheDir();
        if (cacheDir == null) {
            File sdDir = Environment.getExternalStorageDirectory();
            cacheDir = new File(sdDir, "/android/data/" + context.getPackageName()
                    + "/cache");
        }
        return cacheDir;

    }

    public static boolean hasSDCard() {
        String t = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(t);
    }

    private static final String SPLASH_FILE = "splash";

    public static File getSplashFile() {
        return new File(MyApp.getContext().getCacheDir(), SPLASH_FILE);
    }

    public static BitmapDrawable getSplashImage() {
        return new BitmapDrawable(MyApp.getContext().getResources(),
                getSplashFile().getAbsolutePath());
    }

    private static final String AVATAR_FILE = "avatar.jpg";

    public static File getAvatarFile() {
        return new File(getCacheDir(), AVATAR_FILE);
    }


    public static void writeAvatarFile(byte[] data) {
        FileOutputStream fos;
        try {
            File dir = MyApp.getContext().getCacheDir();
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File f = getAvatarFile();
            fos = new FileOutputStream(f);
            fos.write(data, 0, data.length);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static byte[] inputStreamToByteArray(InputStream is) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[16384];

        try {
            while ((nRead = is.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            buffer.flush();
        } catch (Exception e) {
            Log.e("FileUtils", Log.getStackTraceString(e));
        }
        return buffer.toByteArray();
    }

    public static void copyFile(File sourceFile, File targetFile) throws IOException {
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        try {
            inBuff = new BufferedInputStream(new FileInputStream(sourceFile));
            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));
            byte[] b = new byte[1024 * 10];
            int len;
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            outBuff.flush();
        } finally {
            if (inBuff != null) {
                inBuff.close();
            }
            if (outBuff != null) {
                outBuff.close();
            }
        }
    }

    public static void copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                int length;
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        } catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();

        }

    }


}
