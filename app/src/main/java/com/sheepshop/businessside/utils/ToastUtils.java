package com.sheepshop.businessside.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import com.sheepshop.businessside.MyApp;

/**
 * Created by Administrator on 2016/8/3.
 * 土司工具类
 */
public class ToastUtils {

    private ToastUtils()
    {
		/* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static boolean isShow = true;
    private static Toast toast;

    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showShort(Context context, CharSequence message)
    {
        if (toast == null){
            newToast(context, message);
        }else{
            toast.setText(message);
        }
        toast.show();
    }

    private static void newToast(Context context, CharSequence message) {
        toast = Toast.makeText(context, null, Toast.LENGTH_SHORT);
        toast.setText(message);
        toast.setGravity(Gravity.CENTER,0,0);
    }

    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showShort(Context context, int message)
    {
        if (toast == null){
            newToast(context, message);
        }else{
            toast.setText(message);
        }
        toast.show();
    }

    private static void newToast(Context context, int message) {
        toast = Toast.makeText(context, null, Toast.LENGTH_SHORT);
        toast.setText(message);
        toast.setGravity(Gravity.CENTER,0,0);
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showLong(Context context, CharSequence message)
    {
        if (toast == null){
            toast = Toast.makeText(context, null, Toast.LENGTH_LONG);
            toast.setText(message);
        }else{
            toast.setText(message);
        }
        toast.show();
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showLong(Context context, int message)
    {
        if (toast == null){
            toast = Toast.makeText(context, null, Toast.LENGTH_LONG);
            toast.setText(message);
        }else{
            toast.setText(message);
        }
        toast.show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     */
    public static void show(Context context, CharSequence message, int duration)
    {
        if (toast == null){
            toast = Toast.makeText(context, null,duration);
            toast.setText(message);
        }else{
            toast.setText(message);
        }
        toast.show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     */
    public static void show(Context context, int message, int duration)
    {
        if (toast == null){
            toast = Toast.makeText(context, null,duration);
            toast.setText(message);
        }else{
            toast.setText(message);
        }
        toast.show();
    }

    public static void makeText(Context context, String message, int lengthShort) {
        if (toast == null){
            toast = Toast.makeText(context, null,lengthShort);
            toast.setText(message);
        }else{
            toast.setText(message);
        }
        toast.show();
    }

    private static Toast mToast;
    private static Context context = MyApp.getContext();

    /********************** 非连续弹出的Toast ***********************/
    public static void showSingleToast(int resId) { //R.string.**
        getSingleToast(resId, Toast.LENGTH_SHORT).show();
    }

    public static void showSingleToast(String text) {
        getSingleToast(text, Toast.LENGTH_SHORT).show();
    }

    public static void showSingleLongToast(int resId) {
        getSingleToast(resId, Toast.LENGTH_LONG).show();
    }

    public static void showSingleLongToast(String text) {
        getSingleToast(text, Toast.LENGTH_LONG).show();
    }

    /*********************** 连续弹出的Toast ************************/
    public static void showToast(int resId) {
        getToast(resId, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(String text) {
        getToast(text, Toast.LENGTH_SHORT).show();
    }

    public static void showLongToast(int resId) {
        getToast(resId, Toast.LENGTH_LONG).show();
    }

    public static void showLongToast(String text) {
        getToast(text, Toast.LENGTH_LONG).show();
    }

    public static Toast getSingleToast(int resId, int duration) { // 连续调用不会连续弹出，只是替换文本
        return getSingleToast(context.getResources().getText(resId).toString(), duration);
    }

    public static Toast getSingleToast(String text, int duration) {
        if (mToast == null) {
            mToast = Toast.makeText(context, text, duration);
        } else {
            mToast.setText(text);
        }
        return mToast;
    }

    public static Toast getToast(int resId, int duration) { // 连续调用会连续弹出
        return getToast(context.getResources().getText(resId).toString(), duration);
    }

    public static Toast getToast(String text, int duration) {
        return Toast.makeText(context, text, duration);
    }
}
