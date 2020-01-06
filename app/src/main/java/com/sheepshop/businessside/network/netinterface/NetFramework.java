package com.sheepshop.businessside.network.netinterface;

import android.app.Application;

import java.util.Map;

/**
 * Created by chufeng on 2017/7/31.
 * 网络框架实现的方法，才能具体使用
 */

public interface NetFramework {
    /**
     * 初始化网络框架
     * @param application
     */
    void init(Application application);

    /**
     * 发送一个post请求
     */

    void postData(Map<String, Object> data, Map<String, Object> header, String url, BaseCallBack baseCallBack);


    /**
     * 发送一个带缓存请求
     * @param data
     * @param header
     * @param url
     * @param baseCallBack
     */
    void cachePostData(Map<String, Object> data, Map<String, Object> header, String url, BaseCallBack baseCallBack);

    /**
     * 发送一个get请求
     */

    void getData(Map<String, Object> data, Map<String, Object> header, String url, BaseCallBack baseCallBack);

    /**
     * 取消一个请求
     */

    void cancleReq(String... urls);

    /**
     * 应用退出取消所有请求
     */
    void cancleAllRequest();
}
