package com.sheepshop.businessside.ui.opencenter;

import androidx.annotation.Nullable;
import ch.chtool.base.BaseActivity;
import retrofit2.Call;
import retrofit2.Response;

import android.content.Intent;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.sheepshop.businessside.MainActivity;
import com.sheepshop.businessside.R;
import com.sheepshop.businessside.base.WebViewActivity;
import com.sheepshop.businessside.entity.BeanRespReqEmpty;
import com.sheepshop.businessside.okhttp.BaseResp;
import com.sheepshop.businessside.okhttp.HttpUtils;
import com.sheepshop.businessside.okhttp.SSHCallBackNew;
import com.sheepshop.businessside.service.ApiService;
import com.sheepshop.businessside.tool.ToastUtils;
import com.sheepshop.businessside.ui.bean.LoginBean;
import com.sheepshop.businessside.ui.mycenter.CenterActivity;
import com.sheepshop.businessside.weight.ButtonUtils;

public class CenterLoginActivity extends BaseActivity implements View.OnClickListener {

    boolean isTimerStart = false;
    private EditText mEdtPhone;
    private EditText mEdtCode;
    private Button mBtnSendCode;
    private CheckBox mCbAgreement;
    private TextView mTvRead;
    private TextView mTvShop;
    private TextView mTvPrivacy;
    private Button mBtnLogin;
    private SharedPreferences.Editor userInfo;

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
        return R.layout.activity_center_login;
    }

    @Override
    public void initView() {

        mEdtPhone = findViewById(R.id.edt_phone);
        mEdtCode = findViewById(R.id.edt_code);
        mBtnSendCode = findViewById(R.id.btn_send_code);
        mCbAgreement = findViewById(R.id.cb_agreement);
        mTvRead = findViewById(R.id.tv_read);
        mTvShop = findViewById(R.id.tv_shop);
        mTvPrivacy = findViewById(R.id.tv_privacy);
        mBtnLogin = findViewById(R.id.btn_login);
        mBtnSendCode.setOnClickListener(this);
        mBtnLogin.setOnClickListener(this);
        mTvShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CenterLoginActivity.this, WebViewActivity.class);
                intent.putExtra("web_url", "http://register.lnhcsk.com/business.html");
                intent.putExtra("web_title", "店铺协议");
                startActivity(intent);
            }
        });
        mTvPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CenterLoginActivity.this, WebViewActivity.class);
                intent.putExtra("web_url", "http://register.lnhcsk.com/businessPri.html");
                intent.putExtra("web_title", "隐私政策");
                startActivity(intent);
            }
        });
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
                    mBtnSendCode.setBackgroundResource(R.drawable.bg_send_code_red);
                } else {
                    mBtnSendCode.setFocusable(false);
                    mBtnSendCode.setBackgroundResource(R.drawable.bg_send_code_gray);
                }
            }
        });
    }

    @Override
    public void initData() {

    }

    private void getCode() {
        isTimerStart = true;
        timer.start();
        mBtnSendCode.setEnabled(false);
        mBtnSendCode.setBackgroundResource(R.drawable.bg_send_code_gray);
        Call<BaseResp<BeanRespReqEmpty>> call = HttpUtils.getInstance().getApiService(ApiService.class).sendCode(mEdtPhone.getText().toString(), "10");
        call.enqueue(new SSHCallBackNew<BaseResp<BeanRespReqEmpty>>() {
            @Override
            public void onSuccess(Response<BaseResp<BeanRespReqEmpty>> response) throws Exception {
                String msg = response.body().getMsg();
                Log.d("LoginActivity1", msg);

            }

            @Override
            public void onFail(String message) {
                Log.d("LoginActivity2", message);

            }
        });

    }

    private void login() {
        Call<BaseResp<LoginBean>> call = HttpUtils.getInstance().getApiService(ApiService.class).login(mEdtPhone.getText().toString(), mEdtCode.getText().toString(), "qeqw21");
        call.enqueue(new SSHCallBackNew<BaseResp<LoginBean>>() {
            @Override
            public void onSuccess(Response<BaseResp<LoginBean>> response) throws Exception {
                String code = response.body().getCode();
                String msg = response.body().getMsg();
                LoginBean loginBean = response.body().getData();
                if ("0".equals(code)) {
                    userInfo = getSharedPreferences("user_npt", MODE_PRIVATE).edit();
                    /**
                     *  "buOperationId": "",
                     *     "buOperationType": 0,//运营中心状态（0无运营中心，需提交审核 1初次审核中 2初次审核失败需再次提交 3已有运营中心且通过审核 //4修改信息审核中
                     *     "buSendCode": "qeqw21",//推送预留码
                     *     "operationRefuseReason": ""//运营中心拒绝理由（状态为2时显示）
                     */
                    userInfo.putInt("buId", loginBean.getBuId());
                    userInfo.putString("buName", loginBean.getBuName());
                    userInfo.putString("buMobile", loginBean.getBuMobile());
                    userInfo.putString("buToken", loginBean.getBuToken());
                    userInfo.putInt("buState", loginBean.getBuState());
                    userInfo.putInt("isFirstLogin", loginBean.getIsFirstLogin());
                    userInfo.putInt("buStoreType", loginBean.getBuStoreType());
                    userInfo.putString("buOperationId", loginBean.getBuOperationId());//运营中心id
                    userInfo.putInt("buOperationType", loginBean.getBuStoreType());//运营中心状态（0无运营中心，需提交审核 1初次审核中 2初次审核失败需再次提交 3已有运营中心且通过审核 4修改信息审核中
                    userInfo.putString("operationRefuseReason", loginBean.getBuToken());//运营中心拒绝理由
                    userInfo.apply();
                    ToastUtils.showToast(msg);
                    startActivity(MainActivity.class);
//                    getUserInfo(loginBean.getBuId(), loginBean.getBuToken());
                } else {
                    new XPopup.Builder(CenterLoginActivity.this).asConfirm(msg, "", new OnConfirmListener() {
                        @Override
                        public void onConfirm() {
                            ToastUtils.showToast(msg + "--");
                        }
                    }).show();
                }
            }

            @Override
            public void onFail(String message) {
                Toast.makeText(CenterLoginActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private CountDownTimer timer = new CountDownTimer(60000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            if (mBtnSendCode != null) {
                mBtnSendCode.setText("倒计时(" + (millisUntilFinished / 1000) + ")");
                mBtnSendCode.setEnabled(false);
                mBtnSendCode.setBackgroundResource(R.drawable.bg_send_code_gray);
            }
        }

        @Override
        public void onFinish() {
            if (mBtnSendCode != null) {
                mBtnSendCode.setText("发送验证码");
                mBtnSendCode.setEnabled(true);
                isTimerStart = false;
                mBtnSendCode.setBackgroundResource(R.drawable.bg_send_code_red);
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
    public void onClick(View var1) {
        switch (var1.getId()) {
            case R.id.btn_send_code:
                getCode();
                break;
            case R.id.btn_login:
//                startActivity(OpenCenterActivity.class);
                if (TextUtils.isEmpty(mEdtPhone.getText().toString())) {
                    ToastUtils.showToast("请输入手机号");
                    return;
                }
                if (TextUtils.isEmpty(mEdtCode.getText().toString())) {
                    ToastUtils.showToast("请输入验证码");
                    return;
                }
                if (!mCbAgreement.isChecked()) {
                    ToastUtils.showToast("请阅读并勾选店铺协议和隐私政策");
                    return;
                }
                if (!ButtonUtils.isFastDoubleClick(R.id.btn_login)) {
                    login();
                }
                break;
            default:
                break;
        }
    }
}
