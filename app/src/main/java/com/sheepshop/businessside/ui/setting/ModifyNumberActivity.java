package com.sheepshop.businessside.ui.setting;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.sheepshop.businessside.R;
import com.sheepshop.businessside.entity.BeanRespReqEmpty;
import com.sheepshop.businessside.okhttp.BaseResp;
import com.sheepshop.businessside.okhttp.HttpUtils;
import com.sheepshop.businessside.okhttp.SSHCallBackNew;
import com.sheepshop.businessside.service.ApiService;
import com.sheepshop.businessside.tool.ToastUtils;

import androidx.annotation.Nullable;
import ch.chtool.base.BaseActivity;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by CH
 * on 2019/11/21 13:36
 */
public class ModifyNumberActivity extends BaseActivity implements View.OnClickListener {
    boolean isTimerStart = false;
    private ImageView mTitleBack;
    private RelativeLayout mTitle;
    private EditText mEdtPhone;
    private EditText mEdtCode;
    private Button mBtnSendCode;
    private Button mBtnLogin;
    private SharedPreferences readInfo;
    private SharedPreferences.Editor editor;
    private SharedPreferences npt;
    private int buId;
    private String buToken;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(getResources().getColor(R.color.f3));
        }
    }

    @Override
    public int initLayout() {
        return R.layout.activity_modify_number;
    }

    @Override
    public void initView() {
        mTitleBack = findViewById(R.id.title_back);
        mTitle = findViewById(R.id.title);
        mEdtPhone = findViewById(R.id.edt_phone);
        mEdtCode = findViewById(R.id.edt_code);
        mBtnSendCode = findViewById(R.id.btn_send_code);
        mBtnLogin = findViewById(R.id.btn_login);
        mTitleBack.setOnClickListener(this);
        mBtnSendCode.setOnClickListener(this);
        mBtnLogin.setOnClickListener(this);
        mEdtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                mBtnSendCode.setFocusable(false);
                mBtnSendCode.setBackgroundResource(R.drawable.bg_send_code_gray);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mBtnSendCode.setFocusable(false);
                mBtnSendCode.setBackgroundResource(R.drawable.bg_send_code_gray);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 11) {
                    mBtnSendCode.setFocusable(true);
                    mBtnSendCode.setBackgroundResource(R.drawable.bg_send_code_green);
                } else {
                    mBtnSendCode.setFocusable(false);
                    mBtnSendCode.setBackgroundResource(R.drawable.bg_send_code_gray);
                }
            }
        });
    }

    private void getCode() {
        Log.d("ModifyNumberActivity", mEdtPhone.getText().toString());
        Call<BaseResp<BeanRespReqEmpty>> call = HttpUtils.getInstance().getApiService(ApiService.class).sendCode(mEdtPhone.getText().toString(), "11");
        call.enqueue(new SSHCallBackNew<BaseResp<BeanRespReqEmpty>>() {
            @Override
            public void onSuccess(Response<BaseResp<BeanRespReqEmpty>> response) throws Exception {
                ToastUtils.showToast(response.body().getMsg());
                Log.d("ModifyNumberActivity", response.body().getMsg());
                isTimerStart = true;
                timer.start();
                mBtnSendCode.setEnabled(false);
            }

            @Override
            public void onFail(String message) {

//                ToastUtils.showToast(message);
            }
        });

    }

    private void modify() {
        Call<BaseResp<BeanRespReqEmpty>> call = HttpUtils.getInstance().getApiService(ApiService.class).modify(mEdtPhone.getText().toString(), String.valueOf(buId), buToken, mEdtCode.getText().toString());
        call.enqueue(new SSHCallBackNew<BaseResp<BeanRespReqEmpty>>() {
            @Override
            public void onSuccess(Response<BaseResp<BeanRespReqEmpty>> response) throws Exception {
                String code = response.body().getCode();
                String msg = response.body().getMsg();
                if ("0".equals(code)) {
                    ToastUtils.showToast(msg);
                } else {
                    new XPopup.Builder(ModifyNumberActivity.this).asConfirm("提示", msg, new OnConfirmListener() {
                        @Override
                        public void onConfirm() {
                            ToastUtils.showToast(msg);
                        }
                    }).show();
                }
            }

            @Override
            public void onFail(String message) {
                Toast.makeText(ModifyNumberActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private CountDownTimer timer = new CountDownTimer(60000, 1000) {

        @SuppressLint("SetTextI18n")
        @Override
        public void onTick(long millisUntilFinished) {
            if (mBtnSendCode != null) {
                mBtnSendCode.setText("倒计时(" + (millisUntilFinished / 1000) + ")");
                mBtnSendCode.setEnabled(false);
            }
        }

        @Override
        public void onFinish() {
            if (mBtnSendCode != null) {
                mBtnSendCode.setText("发送验证码");
                mBtnSendCode.setEnabled(true);
                isTimerStart = false;
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
            timer = null;
            isTimerStart = false;
        }
    }

    @Override
    public void initData() {
        readInfo = getSharedPreferences("user_npt", Context.MODE_PRIVATE);
        editor = readInfo.edit();
        buId = readInfo.getInt("buId", 0);
        buToken = readInfo.getString("buToken", "");
    }

    @Override
    public void onClick(View var1) {
        switch (var1.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.btn_send_code:
                getCode();
                break;
            case R.id.btn_login:
                if (TextUtils.isEmpty(mEdtPhone.getText().toString())) {
                    ToastUtils.showToast("请输入手机号");
                    return;
                }
                if (TextUtils.isEmpty(mEdtCode.getText().toString())) {
                    ToastUtils.showToast("请输入验证码");
                    return;
                }
                modify();
                break;
        }
    }
}
