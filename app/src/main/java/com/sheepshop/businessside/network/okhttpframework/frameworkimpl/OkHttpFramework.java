package com.sheepshop.businessside.network.okhttpframework.frameworkimpl;


import android.app.Application;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sheepshop.businessside.network.disklrucache.DiskLruCacheHelper;
import com.sheepshop.businessside.network.netbean.ResponseBean;
import com.sheepshop.businessside.network.netinterface.BaseCallBack;
import com.sheepshop.businessside.network.netinterface.NetFramework;
import com.sheepshop.businessside.network.okhttpframework.data.OKHttpPullRequest;
import com.sheepshop.businessside.utils.LOG;
import com.sheepshop.businessside.utils.NetStatusUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okio.Buffer;


/**
 * Created by Administrator on 2017/7/31.
 */

public class OkHttpFramework implements NetFramework {

    private static OkHttpClient okHttpClient;
    private static final int CACHE_SIZE = 1024 * 1024 * 10;//缓存大小为10m
    private static OKHttpPullRequest PULL_REQUEST;//请求数据解析类
    private static Application CONTEXT;
    private static DiskLruCacheHelper mDiskLruCache;
    private static Map<String, Call> sCallMap = new HashMap<>();

    public OkHttpFramework (OKHttpPullRequest okHttpPullRequest) {
        PULL_REQUEST = okHttpPullRequest;
    }

    @Override
    public void init (Application application) {
        CONTEXT = application;
        if (okHttpClient == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            //设置okhttp的拦截策略
            setInterceptStrategy(builder, application);
            //添加缓存策略
            setCacheStrategy(application);
            builder.readTimeout(10, TimeUnit.SECONDS);
            builder.writeTimeout(5, TimeUnit.MINUTES);
            okHttpClient = builder.build();
        }
    }

    @Override
    public void postData (Map<String, Object> data, Map<String, Object> header, String url, final BaseCallBack baseCallBack) {
        //添加请求体
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        if (data != null) {
            PULL_REQUEST.pullRequestBodyData(data, formBodyBuilder);
        }
        //添加请求的头
        Request.Builder builder = new Request.Builder();
        if (header != null) {
            PULL_REQUEST.pullRequestHeadData(header, builder);
        }

        builder.post(formBodyBuilder.build()).url(url);
        final Call call = okHttpClient.newCall(builder.build());
        sCallMap.put(url, call);
        if (! call.isCanceled()) {
            call.enqueue(new Callback() {
                @Override
                public void onFailure (Call call, IOException e) {
                    LOG.e(e.toString());
                    if ("Canceled".equals(e.getMessage())) {
                        return;
                    }
                    baseCallBack.onErrorForOthers(new ResponseBean("网络出错,请检查网络", "网络出错,请检查网络", "-1"));
                    sCallMap.remove(getCacheKey(call));

                }

                @Override
                public void onResponse (Call call, Response response) {
                    resloveOnResponse(call, response, baseCallBack);
                    response.body().close();
                    response.close();
                    sCallMap.remove(getCacheKey(call));
                }
            });
        }
    }



    private void resloveOnResponse (Call call, Response response, BaseCallBack baseCallBack) {
        String string = "";
        try {
            string = response.body().string();
            LOG.e("------------------------------>onSuccess" + "  " + string);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (response.isSuccessful()) {
            if (! TextUtils.isEmpty(string)) {
                try {
                    pullData(string, baseCallBack);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } else {
            LOG.e("网络出错,请检查网络");
            baseCallBack.onErrorForOthers(new ResponseBean("网络出错,请检查网络", "网络出错,请检查网络", "-1"));
        }
    }

    @Override
    public void cachePostData (Map<String, Object> data, Map<String, Object> header, String url, final BaseCallBack
            baseCallBack) {
        //添加请求体
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        if (data != null) {
            PULL_REQUEST.pullRequestBodyData(data, formBodyBuilder);
        }
        //添加请求的头
        Request.Builder builder = new Request.Builder();
        if (header != null) {
            PULL_REQUEST.pullRequestHeadData(header, builder);
        }

        builder.post(formBodyBuilder.build()).url(url);
        builder.tag(url);
        final Call call = okHttpClient.newCall(builder.build());
        sCallMap.put(url, call);
        if (! call.isCanceled()) {
            call.enqueue(new Callback() {
                @Override
                public void onFailure (Call call, IOException e) {
                    LOG.e(e.toString());
                    sCallMap.remove(getCacheKey(call));
                    if (NetStatusUtils.isConnected(CONTEXT)) {
                        baseCallBack.onErrorForOthers(new ResponseBean("网络出错,请检查网络", "网络出错,请检查网络", "-1"));
                    } else {
                        String key = getCacheKey(call);
                        //没有网络的情况下，去获取key对应的缓存
                        String json = null;
                        json = mDiskLruCache.getAsString(key);
                        try {
                            pullData(json, baseCallBack);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        LOG.e("disklrucache:" + json);
                        //如果缓存不存在，那么久回调失败接口
                    }

                }

                @Override
                public void onResponse (Call call, Response response) throws IOException {
                    resloveCacheOnResponse(call, response, baseCallBack);
                    response.body().close();
                    response.close();
                    sCallMap.remove(getCacheKey(call));
                }
            });
        }
    }

    @Override
    public void getData (Map<String, Object> data, Map<String, Object> header, String url, BaseCallBack baseCallBack) {

    }

    @Override
    public void cancleReq (String... urls) {
        for (String url : urls) {
            Call call = sCallMap.get(url);
            if (call != null) {
                call.cancel();
                call = null;
                sCallMap.remove(url);
            }
        }
    }

    @Override
    public void cancleAllRequest () {
        for (Map.Entry entry : sCallMap.entrySet()) {
            ((Call) entry.getValue()).cancel();
        }
        sCallMap.clear();

    }

    /**
     * 设置okhttp 的缓存策略
     *
     * @param application
     */
    private void setCacheStrategy (Application application) {
        //外部存储的缓存路径 application.getExternalCacheDir().getAbsolutePath()
        try {
            File cacheDir = new File(application.getExternalCacheDir().getAbsolutePath());
            if (cacheDir == null) {
                cacheDir = new File(application.getCacheDir().getAbsolutePath());
            }
            if (cacheDir != null) {
                //判断路径是否存在
                if (! cacheDir.exists())
                    cacheDir.mkdirs();//路径不存在创建路径
                try {
                    mDiskLruCache = new DiskLruCacheHelper(application, cacheDir, CACHE_SIZE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加请求的拦截器，做好数据过滤
     *
     * @param builder
     * @param application
     */
    private void setInterceptStrategy (OkHttpClient.Builder builder, final Application application) {

    }


    /**
     * 处理返回的结果
     *
     * @param call
     * @param response
     * @param baseCallBack
     * @throws IOException
     */
    private static void resloveCacheOnResponse (Call call, Response response, BaseCallBack baseCallBack) throws IOException {
        if (! call.isCanceled()) {
            final String resp = response.body().string();
            String key = getCacheKey(call);
            if (NetStatusUtils.isConnected(CONTEXT)) {
                //如果返回数据为null
                if (TextUtils.isEmpty(resp)) {
                    LOG.e("网络出错,请检查网络");
                    baseCallBack.onErrorForOthers(new ResponseBean("网络出错,请检查网络", "网络出错,请检查网络", "-1"));
                } else {
                    if (response.isSuccessful()) {
                        pullData(resp, baseCallBack);
                        if (mDiskLruCache != null) {
                            mDiskLruCache.remove(key);
                            //当需要缓存的数据不为空的时候，并且需要缓存的时候，通过diskLruCacheHelper进行缓存                                   if(!TextUtils
                            // .isEmpty
                            // (key)&&!TextUtils.isEmpty(cacheResponse)&&isCache){
                            mDiskLruCache.put(key, resp);
                            //然后就是回调成功接口
                        }

                    } else {
                        //这个是请求失败，那么就回调失败接口
                        LOG.e("网络出错,请检查网络");
                        baseCallBack.onErrorForOthers(new ResponseBean("网络出错,请检查网络", "网络出错,请检查网络", "-1"));
                    }
                }
                return;
            } else {
                if (mDiskLruCache != null) {
                    //没有网络的情况下，去获取key对应的缓存
                    String json = mDiskLruCache.getAsString(key);
                    //如果缓存不存在，那么久回调失败接口
                    LOG.e("disklrucache:" + json);
                }
            }
        }
    }

    @NonNull
    private static String getCacheKey (Call call) {
        String key = "";
        MediaType contentType = call.request().body().contentType();
        Charset charset = Charset.forName("UTF-8");
        if (contentType != null) {
            charset = contentType.charset(Charset.forName("UTF-8"));
        }
        Buffer buffer = new Buffer();
        try {
            call.request().body().writeTo(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String requestUrl = call.request().url().toString();
        key = requestUrl + buffer.readString(charset);
        key = key.replace(":", "").replace("//", "").replace(".", "").replace("/", "").toLowerCase();
        buffer.close();
//        if (TextUtils.isEmpty(key)) {
//            //获取请求的url
//            String requestUrl = call.request().url().toString();
//            key = requestUrl.replace(":", "").replace("//", "").replace(".", "").replace("/", "").toLowerCase();
//        } else if (key.length() >= 120) {
//            key = key.substring(0, 119);
//        }
//        String requestUrl = call.request().url().toString();
//        key = requestUrl.replace(":", "").replace("//", "").replace(".", "").replace("/", "").toLowerCase();
//        if (key.length() >= 120) {
//            key = key.substring(0, 119);
//        }
        return key;
    }

    /**
     * 将数据转化为对象
     *
     * @param string
     * @param baseCallBack
     * @throws IOException
     */
    private static void pullData (String string, BaseCallBack baseCallBack) throws IOException {
        try {
            JSONObject jsonObject = JSON.parseObject(string);
            ResponseBean responseBean = new ResponseBean();
            for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
                if (entry.getKey().equals("code")) {
                    responseBean.setCode(entry.getValue().toString());
                } else if (entry.getKey().equals("msg")) {
                    responseBean.setMsg(entry.getValue().toString());
                } else if (entry.getKey().equals("data")) {
                    responseBean.setData(entry.getValue().toString());
                }
            }
//            //判断是否token过期
//            if ("10001" .equals(responseBean.getMsgCode())) {
//                if (! ConstantUtils.hasToLogin) {
//                    ConstantUtils.hasToLogin = true;
//                    Bundle bundle = new Bundle();
//                    bundle.putBoolean("otherLogin", true);
//                    ARouterUtils.goActivityNoInterceptor(UserRouter.USER_LOGINMAIN, bundle);
//                }
//                return;
//            }
//            ConstantUtils.hasToLogin = false;
            if ( "0" .equals(responseBean.getCode())) {
                baseCallBack.onSuccess(responseBean);
            } else {
                baseCallBack.onError(responseBean);
            }
        } catch (Exception e) {
            LOG.e("网络出错,请检查网络");
            baseCallBack.onErrorForOthers(new ResponseBean("网络出错,请检查网络", "网络出错,请检查网络", "-1"));
        }
    }


}
