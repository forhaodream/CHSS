package com.sheepshop.businessside.network.netbean;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;

/**
 * Created by Administrator on 2017/7/31.
 */

public class ResponseBean {

    /**
     * data : data
     * msg : msg
     * msgCode : msgCode
     */

    private String data;
    private String msg;
    private String code;
    private Class mClass;
    public static final int RES_DATATYPE_BEAN = 0xcf;//请求返回的是对象类型
    public static final int RES_DATATYPE_BEANLIST = 0xff;//请求返回的是数组
    private int resDatatype = -1;

    public void setClass(Class aClass) {
        mClass = aClass;
    }

    public void setResDatatype(int resDatatype) {
        this.resDatatype = resDatatype;
    }

    public ResponseBean(String data, String msg, String code) {
        this.data = data;
        this.msg = msg;
        this.code = code;
    }

    public ResponseBean() {
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code == null ? "" : code;
    }

    public void setCode(String code) {
        this.code = code == null ? "" : code;
    }

    public Object pullData() {
        try {

            if (resDatatype == -1 || resDatatype == RES_DATATYPE_BEAN) {
                if (!TextUtils.isEmpty(data)) {
                    return JSON.parseObject(data, mClass);
                } else {
                    return null;
                }
            } else {
                if (!TextUtils.isEmpty(data)) {
                    return JSON.parseArray(data, mClass);
                } else {
                    return null;
                }
            }
        } catch (Exception e) {
            return null;
        }

    }


    @Override
    public String toString() {
        return "ResponseBean{" +
                "data='" + data + '\'' +
                ", msg='" + msg + '\'' +
                ", mClass=" + mClass +
                ", resDatatype=" + resDatatype +
                '}';
    }


}
