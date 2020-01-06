package com.sheepshop.businessside.utils;


import com.sheepshop.businessside.network.netbean.HttpBean;
import com.sheepshop.businessside.network.netbean.HttpExecutor;
import com.sheepshop.businessside.network.netinterface.BaseCallBack;

import java.util.Map;
import java.util.Set;

import okhttp3.MediaType;

/**
 * <pre>
 * Created by ppW e-mail : wangpanpan05@163.com time : 2017/08/16 desc : 封装的请求类
 * version: 1.0 初始化 params:
 *
 * <pre>
 */

public class NetUtils {

    public static void post(String url, Map<String, String> params, Class clz, int beanType,
                            BaseCallBack baseCallBack) {
        HttpBean.Builder builder = new HttpBean.Builder();
        builder.setaClass(clz).setUrl(url).setResDataType(beanType);
        if (params != null) {
            Set<String> strings = params.keySet();
            for (String key : strings) {
                builder.addReqBody(key, params.get(key));
            }
        }
        HttpExecutor.execute(builder.build(), baseCallBack);
    }

}
