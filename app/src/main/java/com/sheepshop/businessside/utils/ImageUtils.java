package com.sheepshop.businessside.utils;

import android.Manifest;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lxj.xpopup.interfaces.XPopupImageLoader;
import com.sheepshop.businessside.BuildConfig;
import com.sheepshop.businessside.R;
import com.sheepshop.businessside.ui.openshop.ShopBusinessInformationActivity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.core.content.PermissionChecker;
import ch.chtool.utils.FileUtil;

/**
 * Demo class
 *
 * @author Administrator
 * @date 2019/11/2
 * @decs：上传图片封装
 */
public class ImageUtils {

    public static final File PHOTO_DIR = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
    //    public static final File PHOTO_DIR = new File(FileUtil.checkDirPath(Environment.getExternalStorageDirectory().getPath() + "/image/"), System.currentTimeMillis() + ".jpg");;
    private static Activity mActivity;
    public static final int TAKE_OR_CHOOSE_PHOTO = 3024;
    public static final int CAMERA_PERMISSION = 1003;
    public static final int STORAGE_PERMISSION = 1004;
    private static File mCurrentPhotoFile;
    private static PopupWindow stakepopwindow;

    public static void takeOrChoosePhoto(final Activity context, final int requestCodeUser, int type) {
//        type = 1 身份证正面 ,type = 2 身份证反面, type =3 营业执照 ,type =4 许可证 , type = 5 店铺logo, type = 6 店铺logo
//        type = 7店内环境照片
        mActivity = context;
        if (!PHOTO_DIR.exists()) {
            PHOTO_DIR.mkdirs();
        }
//        mCurrentPhotoFile = new File(PHOTO_DIR, getPhotoFileName());
        mCurrentPhotoFile = new File(FileUtil.checkDirPath(Environment.getExternalStorageDirectory().getPath() + "/image/"), System.currentTimeMillis() + ".jpg");
        final Uri outputFileUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileProvider", mCurrentPhotoFile);
//        final Uri outputFileUri = Uri.fromFile(mCurrentPhotoFile);
        backgroundAlpha(0.8f);//让背景变暗
        View popupWindow_view = context.getLayoutInflater().inflate(R.layout.item_popview_choose_header, null, false);
        TextView mTvPopwindowCapture = popupWindow_view.findViewById(R.id.tv_popwindow_capture);
        TextView mTvPopwindowFile = popupWindow_view.findViewById(R.id.tv_popwindow_file);
        ImageView img_choose_pic_show = popupWindow_view.findViewById(R.id.img_choose_pic_show);
        TextView txt_choose_title = popupWindow_view.findViewById(R.id.txt_choose_title);
        TextView txt_hint = popupWindow_view.findViewById(R.id.txt_hint);
        if (type == 1) {
            txt_choose_title.setText("法人代表手持身份证正面照");
            img_choose_pic_show.setImageResource(R.drawable.icon_identity_card);
            txt_hint.setText("照片要求：\n1.需清晰展示人物五官及身份证信息\n2.需手持身份证正面\n3.不可自拍、不可只拍身份证\n4.可使用临时身份证");
        } else if (type == 2) {
            txt_choose_title.setText("法人代表手持身份证反面照");
            img_choose_pic_show.setImageResource(R.drawable.icon_identity_card);
            txt_hint.setText("照片要求：\n1.需清晰展示人物五官及身份证信息\n2.需手持身份证正面\n3.不可自拍、不可只拍身份证\n4.可使用临时身份证");
        } else if (type == 3) {
            txt_choose_title.setText("营业执照");
            img_choose_pic_show.setImageResource(R.drawable.icon_business_license);
            txt_hint.setText("照片要求：\n1.需文字清晰、边框完整、露出国徽\n2.复印件需加盖印章\n3.也可提供监管部门任可的具有营\n 业执照同等法律效力的证件");
        } else if (type == 4) {
            txt_choose_title.setText("许可证");
            img_choose_pic_show.setImageResource(R.drawable.icon_license);
            txt_hint.setText("照片要求：\n1.需文字清晰、边框完整\n2.复印件需加盖印章\n3.有同食品经营许可证同等法律效力的证件\n");
        } else if (type == 5) {
            txt_choose_title.setText("店铺LOGO");
            img_choose_pic_show.setImageResource(R.drawable.icon_store_logo);
            txt_hint.setText("照片要求：\n图片需与店家实际经营相关（支持jpg、\njpeg、png格式，大小不超过500k）\n\n");
        } else if (type == 6) {
            txt_choose_title.setText("门脸图");
            img_choose_pic_show.setImageResource(R.drawable.icon_iv_store_front);
            txt_hint.setText("照片要求：\n需拍全，包含完整的店铺牌匾，门槛（建议正\n对店门2米处拍摄）\n1.建议拍摄营业中的店铺门脸\n2.照片需清晰，无黑、白");
        } else if (type == 7) {
            txt_choose_title.setText("店内环境照片");
            img_choose_pic_show.setImageResource(R.drawable.icon_store_inside);
            txt_hint.setText("照片要求：\n拍摄完整的堂食区域（餐桌、餐椅等）\n将会展示在用户店铺详情\n需上传三张图片\n\n");
        } else if (type == 8) {
            txt_choose_title.setText("店内环境照片");
            img_choose_pic_show.setImageResource(R.drawable.icon_store_inside);
            txt_hint.setText("照片要求：\n拍摄完整的堂食区域（餐桌、餐椅等）\n将会展示在用户店铺详情\n需上传三张图片\n\n");
        } else if (type == 9) {
            txt_choose_title.setText("店内环境照片");
            img_choose_pic_show.setImageResource(R.drawable.icon_store_inside);
            txt_hint.setText("照片要求：\n拍摄完整的堂食区域（餐桌、餐椅等）\n将会展示在用户店铺详情\n需上传三张图片\n\n");
        } else if (type == 001||type == 002||type == 003) {
            txt_choose_title.setText("商品图片");
            img_choose_pic_show.setImageResource(R.drawable.icon_store_inside);
            txt_hint.setText("照片要求：\n拍摄完整的堂食区域（餐桌、餐椅等）\n将会展示在用户店铺详情\n需上传三张图片\n\n");
        }


        ImageView mImgPopwindowCancle = popupWindow_view.findViewById(R.id.img_camera_pop_delete);
        RelativeLayout rootLayout = popupWindow_view.findViewById(R.id.rl_popwindow_rootLayout);
        stakepopwindow = new PopupWindow(popupWindow_view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        stakepopwindow.setAnimationStyle(R.style.popWindowAnimation);//设置弹出和消失的动画
        stakepopwindow.setOutsideTouchable(true);
        stakepopwindow.setBackgroundDrawable(new ColorDrawable());
        stakepopwindow.showAtLocation(popupWindow_view, Gravity.BOTTOM, 0, 0);
        mTvPopwindowCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //拍照发起
                if (Build.VERSION.SDK_INT > 22) {
                    int cameraPermission = ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA);
                    int recordPermission = ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
                    ArrayList<String> permissions = new ArrayList<>();
                    if (cameraPermission != PermissionChecker.PERMISSION_GRANTED) {
                        permissions.add(Manifest.permission.CAMERA);
                    }
                    if (recordPermission != PermissionChecker.PERMISSION_GRANTED) {
                        permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
                    }
                    if (permissions.size() > 0) {
                        context.requestPermissions(permissions.toArray(new String[permissions.size()]), CAMERA_PERMISSION);
                    } else {
//                        openCamera(outputFileUri, context, requestCodeUser);
                        gotoCamera(outputFileUri, context, requestCodeUser);
                    }
                } else {
//                    openCamera(outputFileUri, context, requestCodeUser);
                    gotoCamera(outputFileUri, context, requestCodeUser);
                }
            }
        });
        mTvPopwindowFile.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT > 22) {
                    int permission = ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
                    if (permission != PermissionChecker.PERMISSION_GRANTED) {
                        context.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION);
                    } else {
                        choosePic(context, requestCodeUser);
                    }
                } else {
                    choosePic(context, requestCodeUser);
                }
            }
        });
        mImgPopwindowCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stakepopwindow.dismiss();
            }
        });
        //设置消失的监听
        stakepopwindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1);
            }
        });
        rootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stakepopwindow != null && stakepopwindow.isShowing()) {
                    stakepopwindow.dismiss();
                }
            }
        });
    }

    public static void openCamera(Uri outputFileUri, Activity context, int requestCodeUser) {
        final Intent captureIntent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        captureIntent2.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
        context.startActivityForResult(captureIntent2, requestCodeUser);
        stakepopwindow.dismiss();
    }

    /**
     * 跳转到照相机
     */
    public static void gotoCamera(Uri outputFileUri, Activity activity, int requestCodeUser) {
        Log.d("evan", "*****************打开相机********************");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
        activity.startActivityForResult(intent, requestCodeUser);
        stakepopwindow.dismiss();
    }


    public static void choosePic(Activity context, int requestCode) {
        final Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        context.startActivityForResult(galleryIntent, requestCode);
        stakepopwindow.dismiss();
    }


    public static String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss", Locale.SIMPLIFIED_CHINESE);
        return dateFormat.format(date) + ".jpg";
    }

    public static void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        mActivity.getWindow().setAttributes(lp);
    }

    /**
     * 加载一般图片
     *
     * @param url       图片地址
     * @param imageView 图片控件
     */
    public static void loadNormalImage(Context context, String url, ImageView imageView) {
/**
 * PhotoViewer
 *           .setClickSingleImg(url, iv)   //因为本框架不参与加载图片，所以还是要写回调方法
 *           .setShowImageViewInterface(object : PhotoViewer.ShowImageViewInterface {
 *               override fun show(iv: ImageView, url: String) {
 *                   Glide.with(iv.context).load(url).into(iv)
 *               }
 *           })
 *           .start(this)
 */
        Glide.with(context)
                .load(url)
//                .placeholder(R.drawable.icon_login_logo)
//                .error(R.drawable.icon_shop)
//                .diskCacheStrategy(DiskCacheStrategy.RESULT)
//                .thumbnail(0.8f)//缩略图
//                .centerCrop()
                .into(imageView);
    }

    /**
     * 加载一般图片
     *
     * @param file      本地地址
     * @param imageView 图片控件
     */
    public static void loadNormalImage(Context context, File file, ImageView imageView) {
        Glide.with(context)
                .load(file)
//                .placeholder(com.huayu.coorlib.R.mipmap.default_image_normal)
//                .error(R.drawable.icon_login_logo)
////                .diskCacheStrategy(DiskCacheStrategy.RESULT)
////                .thumbnail(0.8f)//缩略图
//                .centerCrop()
                .into(imageView);
    }


    public static void loadGifImage(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
//                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(imageView);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static File getPhotoFromResult(Context context, Intent data) {
        final boolean isCamera;
        if (data == null) {
            isCamera = true;
        } else {
            isCamera = MediaStore.ACTION_IMAGE_CAPTURE.equals(data.getAction());
        }
        File f = null;
        if (isCamera) {
            f = mCurrentPhotoFile;
//            f = new File(getRealPathFromURI(context, data.getData()));
        } else {
            if (data != null) {
                try {
                    String path = getRealPathFromURI(context, data.getData());
                    f = new File(path);
                    if ((f == null) || !f.exists()) {
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if ((f == null) || !f.exists()) {
                }
            }
        }
        return f;
    }

    /**
     * 根据获取到的Uri得到文件路径
     *
     * @param context 上下文
     * @param uri     获得的Uri
     * @return 返回文件路径
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private static String getRealPathFromURI(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = {
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        } else if (uri == null) {
            //FIXME: workaround for null uri, will try to get the last picture!
            Cursor myCursor = null;
            try {
                String[] largeFileProjection = {
                        MediaStore.Images.ImageColumns._ID,
                        MediaStore.Images.ImageColumns.DATA};

                String largeFileSort = MediaStore.Images.ImageColumns._ID + " DESC";
                myCursor = context.getContentResolver().query(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        largeFileProjection, null, null, largeFileSort);
                String largeImagePath = "";

                if (myCursor.moveToFirst()) {
                    largeImagePath = myCursor.getString(1);
                    Uri imageCaptureUri = Uri.fromFile(new File(largeImagePath));
                    return imageCaptureUri.getPath();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (myCursor != null) {
                    myCursor.close();
                }
            }

        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if ((cursor != null) && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static void picPopup(Activity activity, File file) {
        View popView = View.inflate(activity, R.layout.popwindow_full_pic, null);
        RelativeLayout layout = popView.findViewById(R.id.popup_pic_rl);
        ImageView imageView = popView.findViewById(R.id.full_pic_img);
        //获取屏幕宽高
        int weight = activity.getResources().getDisplayMetrics().widthPixels;
        int height = activity.getResources().getDisplayMetrics().heightPixels;
        PopupWindow popupWindow = new PopupWindow(popView, weight, height);
        popupWindow.setFocusable(true);
        //点击外部popueWindow消失
        popupWindow.setOutsideTouchable(true);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                popupWindow.dismiss();
            }
        });
        Glide.with(activity.getApplication()).load(file).into(imageView);
        //popupWindow出现屏幕变为半透明
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = 0.5f;
        activity.getWindow().setAttributes(lp);
        popupWindow.showAtLocation(popView, Gravity.FILL, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
                lp.alpha = 1f; //0.0-1.0
                activity.getWindow().setAttributes(lp);
            }
        });

    }

    class ImageLoader implements XPopupImageLoader {
        @Override
        public void loadImage(int position, @NonNull Object uri, @NonNull ImageView imageView) {
            Glide.with(imageView).load(uri).into(imageView);
        }

        @Override
        public File getImageFile(@NonNull Context context, @NonNull Object uri) {
            return null;
        }

        //必须实现这个方法，返回uri对应的缓存文件，可参照下面的实现，内部保存图片会用到。如果你不需要保存图片这个功能，可以返回null。

    }

}
