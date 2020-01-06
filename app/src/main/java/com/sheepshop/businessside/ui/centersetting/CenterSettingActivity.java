package com.sheepshop.businessside.ui.centersetting;

import androidx.appcompat.app.AppCompatActivity;
import ch.chtool.base.BaseActivity;
import ch.ielse.view.SwitchView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sheepshop.businessside.R;

public class CenterSettingActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mTitleBack;
    private RelativeLayout mTitle;
    private TextView mTitle1;
    private TextView mCenterDistance;
    private SwitchView mSwitchWlan;
    private SwitchView mSwitchPrinter;
    private SwitchView mSwitchAuto;
    private SwitchView mSwitchVibration;
    private RelativeLayout mLayoutAuditInfo;
    private RelativeLayout mLayoutAbout;
    private Button mSettingExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_center_setting;
    }

    @Override
    public void initView() {

        mTitleBack = (ImageView) findViewById(R.id.title_back);
        mTitle = (RelativeLayout) findViewById(R.id.title);
        mTitle1 = (TextView) findViewById(R.id.title1);
        mCenterDistance = (TextView) findViewById(R.id.center_distance);
        mSwitchWlan = (SwitchView) findViewById(R.id.switch_wlan);
        mSwitchPrinter = (SwitchView) findViewById(R.id.switch_printer);
        mSwitchAuto = (SwitchView) findViewById(R.id.switch_auto);
        mSwitchVibration = (SwitchView) findViewById(R.id.switch_vibration);
        mLayoutAuditInfo = (RelativeLayout) findViewById(R.id.layout_audit_info);
        mLayoutAbout = (RelativeLayout) findViewById(R.id.layout_about);
        mSettingExit = (Button) findViewById(R.id.setting_exit);
        mTitleBack.setOnClickListener(this);
        mSettingExit.setOnClickListener(this);
        mLayoutAuditInfo.setOnClickListener(this);
        mLayoutAbout.setOnClickListener(this);
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
            case R.id.setting_exit:
                break;
            case R.id.layout_audit_info:
                break;
            case R.id.layout_about:
                startActivity(CenterAboutActivity.class);
                break;
        }
    }

}
