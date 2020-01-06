package com.sheepshop.businessside.ui.openshop;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.sheepshop.businessside.MainActivity;
import com.sheepshop.businessside.R;

import androidx.annotation.Nullable;
import butterknife.BindView;
import ch.chtool.base.BaseActivity;

/**
 * 审核界面
 *
 * @author Hm
 */
public class AuditActivity extends BaseActivity {
    @BindView(R.id.img_audit)
    ImageView imgAudit;
    @BindView(R.id.tv_audit)
    TextView tvAudit;
    @BindView(R.id.img_state)
    ImageView imgState;
    private ImageView returnImg;
    private SharedPreferences readInfo;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        }
        readInfo = getSharedPreferences("user_npt", Context.MODE_PRIVATE);
        editor = readInfo.edit();
    }

    @Override
    public int initLayout() {
        return R.layout.activity_audit;
    }

    @Override
    public void initView() {
        returnImg = findViewById(R.id.title_back);
        returnImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitPopup();
            }
        });

    }

    private void exitPopup() {
        new XPopup.Builder(AuditActivity.this).asConfirm("您将退出登录", "是否退出?",
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
