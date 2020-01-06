package com.sheepshop.businessside.weight;

import android.view.View;

/**
 * Created by CH
 * on 2019/11/19 10:41
 */
public abstract class CustomClickListener implements View.OnClickListener {
    private long lastClickTime;
    private long interval = 1500L;

    public CustomClickListener() {

    }

    public CustomClickListener(Long timeL) {
        this.interval = timeL;
    }

    @Override
    public void onClick(View view) {
        long nowTime = System.currentTimeMillis();
        if (nowTime - lastClickTime > interval) {
            onSingleClick(view);
            lastClickTime = nowTime;
        } else {
            onFastClick(view);
        }
    }

    protected abstract void onSingleClick(View view);

    protected abstract void onFastClick(View view);

}
