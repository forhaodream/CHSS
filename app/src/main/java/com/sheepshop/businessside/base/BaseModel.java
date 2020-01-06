package com.sheepshop.businessside.base;

import com.sheepshop.businessside.net.IModel;

import java.io.Serializable;

/**
 * Created by wanglei on 2016/12/11.
 */

public class BaseModel implements IModel, Serializable {
//    public String message;
//    public String result_code;

    //新的
    public String msg;   //请重新登录 提示的时候
    public String code; //0 成功 其他都是失败

    protected boolean error;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    @Override
    public boolean isNull() {
        return false;
    }

    @Override
    public boolean isAuthError() {
//        if (result_code != null && result_code.equals("1"))
//            return false;
//        if (result_code != null && result_code.equals("2"))
//            return false;

        if (code != null && code.equals("0"))
            return false;
        if ("请重新登录".equals(msg)) {
            return false;
        }
        return !code.equals("-5");
    }

    @Override
    public boolean isBizError() {
        return error;
    }

    @Override
    public boolean isRepeatLogin() {
//        if (result_code != null && result_code.equals("2")) {
//            return true;
//        }

        return "请重新登录".equals(msg);
    }

    @Override
    public boolean isServerData() {
        return false;
    }

    @Override
    public String getErrorMsg() {
        return msg;
    }
}
