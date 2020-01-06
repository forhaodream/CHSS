package com.sheepshop.businessside.config;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


import java.util.Set;

//import com.quyum.luckysheep.ui.login.bean.UserBean;

/**
 * 保存用户信息
 * <p>
 * commit 方式会阻塞调用的线程
 * apply 方法不会阻塞调用的线程，但是如果写入任务比较耗时，会阻塞住主线程，因为主线程有调用的代码，需要等写入任务执行完了才会继续往下执行
 */
public class SystemParams {

    private static SystemParams instance;
    private static SharedPreferences sharedPrederences = null;
    public static final String ui_id = "ui_id";//当前用户ui_id
    public static final String ui_money = "ui_money";//当前用户金钱
    public static final String incomeMoney = "incomeMoney";//当前用户收入
    public static final String ui_headurl = "ui_headurl";//当前用户头像
    public static final String ui_nickname = "ui_nickname";//当前用户昵称
    public static final String ui_withdraw_alis = "ui_withdraw_alis";//当前用户绑定支付宝
    public static final String ui_withdraw_wx = "ui_withdraw_wx";//当前用户绑定微信
    public static final String ui_invitation_code = "ui_invitation_code";//当前用户邀请码
    public static final String ui_pay_password = "ui_pay_password";//当前用户是否设置提现密码
    public static final String ui_integral = "ui_integral";//积分
    public static final String ui_wool = "ui_wool";//羊毛
    public static final String ui_lamb_coupon = "ui_lamb_coupon";//羊肉券
    public static final String ubbr_price = "ubbr_price";//回购价格
    public static final String gwccnt = "gwccnt";//当前用户购物车数量

    public static final String dfkcnt = "dfkcnt";
    public static final String dfhcnt = "dfhcnt";
    public static final String dshcnt = "dshcnt";
    public static final String dpjcnt = "dpjcnt";
    public static final String tkcnt = "tkcnt";
    public static final String ui_fruit = "ui_fruit";
    public static final String game_url = "game_url";
    public static final String ui_isWXLogin = "ui_isWXLogin"; //是否微信登录 0未绑定 1绑定

    public static final String wxPayImg = "wxPayImg";//微信绑定提现图片
    public static final String aliPayImg = "aliPayImg";//支付宝绑定提现图片
    //游戏分享透传过来的type值
    public static final String gameType = "gameType";
    private Context mContext;

    private SystemParams() {
    }

    public static void init(Context context) {
        sharedPrederences = context.getSharedPreferences("luckysheepToken", Context.MODE_PRIVATE);
    }

    public static SystemParams getInstance() {

        if (instance == null) {
            synchronized (SystemParams.class) {
                if (instance == null) {
                    instance = new SystemParams();
                }
            }
        }
        return instance;
    }

    public int getInt(String key) {
        return sharedPrederences.getInt(key, 0);
    }

    public int getInt(String key, int defValue) {
        return sharedPrederences.getInt(key, defValue);
    }

    public float getFloat(String key) {
        return sharedPrederences.getFloat(key, 0);
    }

    public float getFloat(String key, float defValue) {
        return sharedPrederences.getFloat(key, defValue);
    }

    public long getLong(String key) {
        return sharedPrederences.getLong(key, 0);
    }

    public long getLong(String key, long defValue) {
        return sharedPrederences.getLong(key, defValue);
    }

    public String getMobile() {
        return sharedPrederences.getString("mobile", "");
    }

    public String getToken() {
        return sharedPrederences.getString("Token", "");
    }

    public void setMobile(String value) {
        Editor editor = sharedPrederences.edit();
        editor.putString("mobile", value);
        editor.commit();
    }

    public String getUi_id() {
        return sharedPrederences.getString("ui_id", "");
    }

    public void setToken(String value) {
        Editor editor = sharedPrederences.edit();
        editor.putString("Token", value);
        editor.commit();
    }

    public String getString(String key) {
        return sharedPrederences.getString(key, "");
    }

    public String getString(String key, String defValue) {
        return sharedPrederences.getString(key, defValue);
    }

    public boolean getBoolean(String key) {
        return sharedPrederences.getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean defValue) {
        return sharedPrederences.getBoolean(key, defValue);
    }

    public void setInt(String key, int value) {
        Editor editor = sharedPrederences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public void setFloat(String key, float value) {
        Editor editor = sharedPrederences.edit();
        editor.putFloat(key, value);
        editor.commit();
    }

    public void setLong(String key, long value) {
        Editor editor = sharedPrederences.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public void setString(String key, String value) {
        Editor editor = sharedPrederences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void setBoolean(String key, boolean value) {
        Editor editor = sharedPrederences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public void setSetString(String key, Set<String> values) {
        Editor editor = sharedPrederences.edit();
        editor.putStringSet(key, values);
        editor.commit();
    }

    public void setClintId(String key, String values) {
        Editor editor = sharedPrederences.edit();
        editor.putString(key, values);
        editor.commit();
    }

    public void getClintId(String key) {
        sharedPrederences.getString(key, "");
    }

    public void remove(String key) {
        Editor editor = sharedPrederences.edit();
        editor.remove(key);
        editor.commit();
    }

    public void clear() {
        Editor editor = sharedPrederences.edit();
        editor.clear().commit();
    }

    /**
     * 清除用户数据---不清除cookie
     */
    public void clearUserData() {
        clearUserAllData();
    }

    public static void updateUserData(String key, String value) {
        SystemParams.getInstance().setString(key, value);
    }

    public void clearOneData(String key) {
        Editor editor = sharedPrederences.edit();
        editor.remove(key);
        editor.commit();
    }

    //清除当前用户信息
    public static void clearUserAllData() {
        Editor editor = sharedPrederences.edit();
        editor.remove("Token");
        editor.remove("mobile");
        editor.remove(SystemParams.ui_id);
        editor.remove(SystemParams.ui_headurl);
        editor.remove(SystemParams.ui_nickname);
        editor.remove(SystemParams.ui_money);
        editor.remove(SystemParams.ui_withdraw_alis);
        editor.remove(SystemParams.ui_withdraw_wx);
        editor.remove(SystemParams.ui_invitation_code);
        editor.remove(SystemParams.ui_pay_password);
        editor.remove(SystemParams.ui_integral);
        editor.remove(SystemParams.ui_wool);
        editor.remove(SystemParams.ui_lamb_coupon);
        editor.remove(SystemParams.ubbr_price);
        editor.remove(SystemParams.gwccnt);
        editor.remove(SystemParams.incomeMoney);
        editor.remove(SystemParams.dfkcnt);
        editor.remove(SystemParams.dfhcnt);
        editor.remove(SystemParams.dshcnt);
        editor.remove(SystemParams.dpjcnt);
        editor.remove(SystemParams.tkcnt);
        editor.remove(SystemParams.ui_fruit);
        editor.remove(SystemParams.game_url);
        editor.remove(SystemParams.wxPayImg);
        editor.remove(SystemParams.aliPayImg);
        editor.commit();

    }

}
