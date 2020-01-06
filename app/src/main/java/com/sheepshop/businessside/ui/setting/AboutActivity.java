package com.sheepshop.businessside.ui.setting;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.sheepshop.businessside.R;
import com.tencent.bugly.beta.Beta;

import ch.chtool.base.BaseActivity;

/**
 * Created by CH
 * on 2019/11/20 10:59
 */
public class AboutActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mTitleBack;
    private RelativeLayout mGiveALike;
    private RelativeLayout mTease;
    private RelativeLayout mUpdate;

    @Override
    public int initLayout() {
        return R.layout.activity_about;
    }

    @Override
    public void initView() {
        mTitleBack = findViewById(R.id.title_back);
        mGiveALike = findViewById(R.id.give_a_like);
        mTease = findViewById(R.id.tease);
        mUpdate = findViewById(R.id.update);
        mTitleBack.setOnClickListener(this);
        mUpdate.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View var1) {
        switch (var1.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.update:
                Beta.checkUpgrade(true, false);
                break;
        }
    }
}
