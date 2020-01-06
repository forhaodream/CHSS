package com.sheepshop.businessside.api;


import com.sheepshop.businessside.base.Constant;
import com.sheepshop.businessside.net.XApi;

public class APPApi {
    private static AppService appService;

    public static AppService getHttpService() {
        if (appService == null) {
            synchronized (APPApi.class) {
                if (appService == null) {
                    appService = XApi.getInstance().getRetrofit(Constant.BASE_URL, true).create(AppService.class);
                }
            }
        }
        return appService;
    }
    public static AppService getHttpServiceNo() {
        if (appService == null) {
            synchronized (APPApi.class) {
                if (appService == null) {
                    appService = XApi.getInstance().getRetrofit(Constant.BASE_URL, true).create(AppService.class);
                }
            }
        }
        return appService;
    }

    public static boolean isSelectHostDebug = false;

}
