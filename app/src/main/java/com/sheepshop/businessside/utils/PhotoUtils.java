package com.sheepshop.businessside.utils;

import android.app.Activity;
import android.os.Environment;
import android.widget.PopupWindow;

import java.io.File;

/**
 * Created by CH
 * on 2019/11/13 10:16
 */
public class PhotoUtils {
    public static final File PHOTO_DIR = Environment
            .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
    private static Activity mActivity;
    public static final int TAKE_OR_CHOOSE_PHOTO = 3024;
    public static final int CAMERA_PERMISSION = 1003;
    public static final int STORAGE_PERMISSION = 1004;
    private static File mCurrentPhotoFile;
    private static PopupWindow stakepopwindow;

}
