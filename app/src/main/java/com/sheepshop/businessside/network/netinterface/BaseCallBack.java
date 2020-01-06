package com.sheepshop.businessside.network.netinterface;


import com.sheepshop.businessside.network.netbean.ResponseBean;

/**
 * Created by chufeng on 2017/7/31.
 * 请求后的回掉
 */

public interface BaseCallBack {

    /**
     * 正在加载
     */
    void onRequesting();
    /**
     * 请求成功
     */
    void onSuccess(ResponseBean data);

    /**
     * 请求失败
     */
    void onError(ResponseBean msg);

    /**
     * 网络等其他原因导致失败
     */
    void onErrorForOthers(ResponseBean msg);

}
