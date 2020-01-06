package com.sheepshop.businessside.network.okhttpframework.data;

import android.text.TextUtils;


import com.sheepshop.businessside.network.netinterface.PullRequest;

import java.util.Iterator;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Request;

/**
 * Created by Administrator on 2017/7/31.
 * 解析请求数据的类
 */

public class OKHttpPullRequest implements PullRequest {

    @Override
    public Object pullRequestHeadData(Object... data) {
        if (data.length >= 2) {
            final Map<String, Object> o = (Map<String, Object>) data[0];
            Request.Builder builder = (Request.Builder) data[1];
            if (o.size() > 0) {
                Iterator<Map.Entry<String, Object>> entryIterator = o.entrySet().iterator();
                while (entryIterator.hasNext()) {
                    Map.Entry<String, Object> entry = entryIterator.next();
                    builder.addHeader(TextUtils.isEmpty(entry.getKey()) ? "key" : entry.getKey(), TextUtils.isEmpty(entry.getValue().toString()) ? "" : entry.getValue().toString());
                }
            }

        }
        return null;
    }

    @Override
    public Object pullRequestBodyData(Object... data) {
        if (data.length >= 2) {
            final Map<String, Object> o = (Map<String, Object>) data[0];
            FormBody.Builder builder = (FormBody.Builder) data[1];
            if (o.size() > 0) {
                Iterator<Map.Entry<String, Object>> entryIterator = o.entrySet().iterator();
                while (entryIterator.hasNext()) {
                    Map.Entry<String, Object> entry = entryIterator.next();
                    builder.add(TextUtils.isEmpty(entry.getKey()) ? "key" : entry.getKey(), TextUtils.isEmpty(entry.getValue().toString()) ? "" : entry.getValue().toString());
                }
            }

        }
        return null;
    }
}
