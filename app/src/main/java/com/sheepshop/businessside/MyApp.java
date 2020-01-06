package com.sheepshop.businessside;

import android.app.Application;
import android.content.Context;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.igexin.sdk.PushManager;
import com.sheepshop.businessside.net.NetError;
import com.sheepshop.businessside.net.NetProvider;
import com.sheepshop.businessside.net.RequestHandler;
import com.sheepshop.businessside.net.XApi;
import com.sheepshop.businessside.network.manager.NetManager;
import com.sheepshop.businessside.network.okhttpframework.data.OKHttpPullRequest;
import com.sheepshop.businessside.network.okhttpframework.frameworkimpl.OkHttpFramework;
import com.sheepshop.businessside.service.DemoIntentService;
import com.sheepshop.businessside.service.DemoPushService;
import com.sheepshop.businessside.ui.bean.LoginBean;
import com.sheepshop.businessside.ui.myshop.ShopInfoBean;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.crashreport.CrashReport;

import androidx.multidex.MultiDex;
import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

public class MyApp extends Application {
    public static final int REQUEST_CODE = 333;
    public static final int RESULT_CODE = 666;

    private static Context context;

    public static Context getContext() {
        return context;
    }

    // 保存店铺信息
    public static ShopInfoBean shopInfoBean = null;
    // 保存用户信息
    public static LoginBean loginBean = null;

    public static LoginBean getLoginBean() {
        return loginBean;
    }

    public static void setLoginBean(LoginBean loginBean) {
        MyApp.loginBean = loginBean;
    }

    // 清除用户信息
    public static void clearLoginInfo() {
        loginBean = null;
    }

    public static ShopInfoBean getShopInfoBean() {
        return shopInfoBean;
    }

    public static void setShopInfoBean(ShopInfoBean shopInfoBean) {
        MyApp.shopInfoBean = shopInfoBean;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        // Tencent Bugly crashReport
        Bugly.init(getApplicationContext(), "f457ff0e39", false);
        // 个推 可以在多个地方初始化
        com.igexin.sdk.PushManager.getInstance().registerPushIntentService(getApplicationContext(), DemoIntentService.class);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.GCJ02);

        //初始化网络加载框架
        NetManager.getInstance().initNetFramework(new OkHttpFramework(new OKHttpPullRequest()), this);
        //网络请求配置
        XApi.registerProvider(new NetProvider() {

            @Override
            public Interceptor[] configInterceptors() {
                return new Interceptor[0];
            }

            @Override
            public void configHttps(OkHttpClient.Builder builder) {

            }

            @Override
            public CookieJar configCookie() {
                return null;
            }

            @Override
            public RequestHandler configHandler() {
                return null;
            }

            @Override
            public long configConnectTimeoutMills() {
                return 0;
            }

            @Override
            public long configReadTimeoutMills() {
                return 0;
            }

            @Override
            public boolean configLogEnable() {
                return true;
            }

            @Override
            public boolean handleError(NetError error) {
                return false;
            }

            @Override
            public boolean dispatchProgressEnable() {
                return false;
            }
        });
    }

}
