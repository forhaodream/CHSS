package com.sheepshop.businessside.network.netinterface;

/**
 * Created by Administrator on 2017/7/31.
 */

public interface PullRequest {
    /**
     * 解析请求头的数据
     * 第一个参数是请求头的数据
     * 第二个参数是设置请求头的容器
     */
    Object pullRequestHeadData(Object... data);

    /**
     * 解析请求体的数据
     *  第一个参数是请求体的数据
     * 第二个参数是设置请求体的容器
     */
    Object pullRequestBodyData(Object... data);
}
