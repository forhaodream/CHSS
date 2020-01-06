package com.sheepshop.businessside.ui.openshop;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.sheepshop.businessside.MainActivity;
import com.sheepshop.businessside.R;

import ch.chtool.base.BaseActivity;

public class AuditFailActivity extends BaseActivity implements View.OnClickListener {

    private View mView;
    private ImageView mTitleBack;
    private RelativeLayout mTitle;
    private ImageView mImgAudit;
    private TextView mTvAudit;
    private ImageView mImgState;
    private SharedPreferences readInfo;
    private SharedPreferences.Editor editor;
    private String reason;
    private TextView mContent;

    @Override
    public int initLayout() {
        return R.layout.activity_audit_fail;
    }

    @Override
    public void initView() {

        mView = findViewById(R.id.view);
        mTitleBack = findViewById(R.id.title_back);
        mTitle = findViewById(R.id.title);
        mImgAudit = findViewById(R.id.img_audit);
        mTvAudit = findViewById(R.id.tv_audit);
        mImgState = findViewById(R.id.img_state);
        mTitleBack.setOnClickListener(this);
        mContent = findViewById(R.id.content);
    }

    @Override
    public void initData() {
        readInfo = getSharedPreferences("user_npt", Context.MODE_PRIVATE);
        editor = readInfo.edit();
        reason = readInfo.getString("buRefuseReason", "");
        if (!TextUtils.isEmpty(reason) || "".equals(reason)) {
            mContent.setText("失败原因: 无");
        } else {
            mContent.setText("失败原因: " + reason);

        }
    }

    @Override
    public void onClick(View var1) {
        switch (var1.getId()) {
            case R.id.title_back:
                exitPopup();
                break;
        }
    }

    private void exitPopup() {
        new XPopup.Builder(AuditFailActivity.this).asConfirm("您将退出登录", "是否退出?",
                new OnConfirmListener() {
                    @Override
                    public void onConfirm() {
                        editor.clear();
                        editor.apply();
                        startActivity(MainActivity.class);
                    }
                }).show();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitPopup();
            System.gc();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
