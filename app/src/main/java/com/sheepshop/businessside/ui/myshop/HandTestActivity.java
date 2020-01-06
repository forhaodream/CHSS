package com.sheepshop.businessside.ui.myshop;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dalimao.corelibrary.VerificationCodeInput;
import com.github.cirno_poi.verifyedittextlibrary.VerifyEditText;
import com.sheepshop.businessside.R;

import ch.chtool.base.BaseActivity;
import me.leefeng.libverify.VerificationView;

/**
 * Created by CH
 * on 2019/11/7 19:12
 */
public class HandTestActivity extends BaseActivity {
    @Override
    public int initLayout() {
        return R.layout.test_hand;
    }

    @Override
    public void initView() {
//        VerificationView verificationView = findViewById(R.id.verificationView3);
        VerifyEditText input = findViewById(R.id.vet);

//        input.setOnCompleteListener(new VerificationCodeInput.Listener() {
//            @Override
//            public void onComplete(String content) {
//                Toast.makeText(HandTestActivity.this, "完成输入：" + content, Toast.LENGTH_SHORT).show();
//            }
//        });
        Button button = findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HandTestActivity.this, input.getContent(), Toast.LENGTH_SHORT).show();
                //  Toast.makeText(HandTestActivity.this, "verificationView.getFinish():" + verificationView.getFinish(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void initData() {

    }
}
