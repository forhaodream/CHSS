package com.sheepshop.businessside.ui.opencenter;

import ch.chtool.base.BaseActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.sheepshop.businessside.MainActivity;
import com.sheepshop.businessside.R;
import com.sheepshop.businessside.ui.mycenter.CenterActivity;
import com.sheepshop.businessside.ui.mycenter.GoodsManageActivity;

public class OpenCenterActivity extends BaseActivity implements View.OnClickListener {

    private Button mBtnOpeanShop;
    private TextView mTxtCommonProblemsTitle;
    private TextView mTxtCommonProblems;
    private ImageView mTitleBack;
    private RelativeLayout mTitle;
    private SharedPreferences readInfo;
    private SharedPreferences.Editor editor;

    @Override
    public int initLayout() {
        return R.layout.activity_open_center;
    }

    @Override
    public void initView() {
        mBtnOpeanShop = findViewById(R.id.btn_opean_shop);
        mTxtCommonProblemsTitle = findViewById(R.id.txt_common_problems_title);
        mTxtCommonProblems = findViewById(R.id.txt_common_problems);
        mBtnOpeanShop.setOnClickListener(this);
        mTitleBack = findViewById(R.id.title_back);
        mTitle = findViewById(R.id.title);
        mTitleBack.setOnClickListener(this);
    }

    @SuppressLint("CommitPrefEdits")
    @Override
    public void initData() {
        readInfo = getSharedPreferences("user_npt", Context.MODE_PRIVATE);
        editor = readInfo.edit();
    }

    @Override
    public void onClick(View var1) {
        switch (var1.getId()) {
            case R.id.title_back:
                exitPopup();
                break;
            case R.id.btn_opean_shop:
                startActivity(new Intent(OpenCenterActivity.this, CenterInputInfoActivity.class));
                break;
            default:
                break;

        }
    }

    private void exitPopup() {
        new XPopup.Builder(OpenCenterActivity.this).asConfirm("您将退出登录", "是否退出?",
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
