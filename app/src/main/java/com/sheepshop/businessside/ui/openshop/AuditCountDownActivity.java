package com.sheepshop.businessside.ui.openshop;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.sheepshop.businessside.MainActivity;
import com.sheepshop.businessside.R;

import ch.chtool.base.BaseActivity;

/**
 * Created by CH
 * on 2019/11/21 13:14
 */
public class AuditCountDownActivity extends BaseActivity implements View.OnClickListener {
    private View mView;
    private ImageView mTitleBack;
    private RelativeLayout mTitle;
    private ImageView mImgAudit;
    private TextView mTvAudit;
    private ImageView mImgState;
    private TextView mContent;
    private SharedPreferences readInfo;
    private SharedPreferences.Editor editor;

    @Override
    public int initLayout() {
        return R.layout.activity_audit_count_down;
    }

    @Override
    public void initView() {

        mView = findViewById(R.id.view);
        mTitleBack = findViewById(R.id.title_back);
        mTitle = findViewById(R.id.title);
        mImgAudit = findViewById(R.id.img_audit);
        mTvAudit = findViewById(R.id.tv_audit);
        mImgState = findViewById(R.id.img_state);
        mContent = findViewById(R.id.content);
        mTitleBack.setOnClickListener(this);
        readInfo = getSharedPreferences("user_npt", Context.MODE_PRIVATE);
        editor = readInfo.edit();
        timer.start();
    }

    private CountDownTimer timer = new CountDownTimer(5000, 1000) {

        @SuppressLint("SetTextI18n")
        @Override
        public void onTick(long millisUntilFinished) {
            if (mContent != null) {
                mContent.setText("信息提交成功，请您耐心等待\n1-3个工作日后台进行审核\n" +
                        "返回至首页（" + (millisUntilFinished / 1000) + "s）");
            }
        }

        @Override
        public void onFinish() {
            editor.clear();
            editor.apply();
            startActivity(MainActivity.class);
        }
    };

    private void exitPopup() {
        new XPopup.Builder(AuditCountDownActivity.this).asConfirm("您将退出登录", "是否退出?",
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
    public void initData() {

    }

    @Override
    public void onClick(View var1) {
        switch (var1.getId()) {
            case R.id.title_back:
                exitPopup();
                break;
        }
    }
}
