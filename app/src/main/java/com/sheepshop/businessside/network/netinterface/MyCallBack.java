package com.sheepshop.businessside.network.netinterface;


import com.sheepshop.businessside.network.netbean.ResponseBean;

//callback扩展  只有成功和失败两种回调
public abstract class MyCallBack implements BaseCallBack {
    @Override
    public void onErrorForOthers(ResponseBean msg) {

    }

    @Override
    public void onRequesting() {

    }
}
