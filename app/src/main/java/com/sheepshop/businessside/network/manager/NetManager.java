package com.sheepshop.businessside.network.manager;

import android.app.Application;

import com.sheepshop.businessside.network.netinterface.NetFramework;


/**
 * Created by chufeng on 2017/7/31.
 * 网络请求管理者
 */

public class NetManager {
    private static NetFramework sFramework;

    private NetManager() {
    }

    public static NetManager getInstance() {
        return NetManagerBean.sManager;
    }

    private static class NetManagerBean {
        public static NetManager sManager = new NetManager();
    }

    /**
     * 初始化网络请求框架
     */
    public void initNetFramework(NetFramework netFramework, Application application) {
        //判断为空抛出异常
        if (netFramework == null)
            throw new NullPointerException();
        //将网络框架赋值
        sFramework = netFramework;
        //初始化网络框架
        sFramework.init(application);
    }

    /**
     * 获取网络框架用于发送请求
     * @return
     */
    public  NetFramework getFramework() {
        if (sFramework == null)
            throw new NullPointerException();
        return sFramework;
    }
}
