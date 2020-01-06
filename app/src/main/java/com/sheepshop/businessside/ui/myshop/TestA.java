package com.sheepshop.businessside.ui.myshop;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.sheepshop.businessside.R;

import ch.chtool.base.BaseActivity;

/**
 * Created by CH
 * on 2019/11/7 17:20
 */
public class TestA extends BaseActivity {
    @Override
    public int initLayout() {
        return R.layout.a_test;
    }

    @Override
    public void initView() {
        PhoneCode pc_1 = findViewById(R.id.pc_1);
        Button button = findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TestA.this, pc_1.getPhoneCode(), Toast.LENGTH_SHORT).show();
            }
        });
        //注册事件回调（根据实际需要，可写，可不写）
        pc_1.setOnInputListener(new PhoneCode.OnInputListener() {
            @Override
            public void onSucess(String code) {
                Toast.makeText(TestA.this, code, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onInput() {

            }
        });
        pc_1.setOnInputListener(new PhoneCode.OnInputListener() {
            @Override
            public void onSucess(String code) {
                //TODO: 例如底部【下一步】按钮可点击
            }

            @Override
            public void onInput() {
                //TODO:例如底部【下一步】按钮不可点击
            }
        });
    }

    @Override
    public void initData() {

    }
}
