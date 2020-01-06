package com.sheepshop.businessside.utils;

import java.util.HashMap;
import java.util.Map;


/**
 * <pre>
 *     Created by ppW
 *     e-mail : wangpanpan05@163.com
 *     time   : 2017/08/10
 *     desc   : 请求参数封装类
 *     version: 1.0   初始化
 *     params:
 *  <pre>
 */

public class HttpRequestParamsBuilder {
    private Map<String, String> mMap;

    public HttpRequestParamsBuilder() {
        if (mMap == null) {
            mMap = new HashMap<>();

        } else {
            mMap.clear();
        }
    }

    public HttpRequestParamsBuilder putParams(String key, String value) {
        mMap.put(key, value);
        return this;
    }

    public HttpRequestParamsBuilder putUrl(String value) {
        mMap.put("url", value);
        return this;
    }

    public Map build() {

        return mMap;
    }
}
