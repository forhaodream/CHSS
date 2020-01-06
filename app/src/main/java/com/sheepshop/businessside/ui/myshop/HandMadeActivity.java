package com.sheepshop.businessside.ui.myshop;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.github.cirno_poi.verifyedittextlibrary.VerifyEditText;
import com.maning.pswedittextlibrary.MNPasswordEditText;
import com.sheepshop.businessside.R;
import com.sheepshop.businessside.entity.BeanRespReqEmpty;
import com.sheepshop.businessside.okhttp.BaseResp;
import com.sheepshop.businessside.okhttp.HttpUtils;
import com.sheepshop.businessside.okhttp.SSHCallBackNew;
import com.sheepshop.businessside.service.ApiService;
import com.sheepshop.businessside.tool.ToastUtils;
import com.sheepshop.businessside.zxing.android.CaptureActivity;

import ch.chtool.base.BaseActivity;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by CH
 * on 2019/11/7 16:52
 */
public class HandMadeActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mTitleBack;
    private RelativeLayout mTitle;
    private PhoneCode mPhoneCode;
    private MNPasswordEditText input;
    private SharedPreferences readInfo;
    private SharedPreferences.Editor editor;
    private int bdId;

    @Override
    public int initLayout() {
        return R.layout.activity_hand_made;
    }

    @Override
    public void initView() {
        showSoftInput(this, input);
        readInfo = getSharedPreferences("user_npt", Context.MODE_PRIVATE);
        editor = readInfo.edit();
        bdId = readInfo.getInt("bdId", 0);
        mTitleBack = findViewById(R.id.title_back);
        mTitle = findViewById(R.id.title);
        mTitleBack.setOnClickListener(this);
        input = findViewById(R.id.activity_verify_edit_text);
        input.setOnTextChangeListener(new MNPasswordEditText.OnTextChangeListener() {
            @Override
            public void onTextChange(String text, boolean isComplete) {
                if (isComplete) {
                    Call<BaseResp<BeanRespReqEmpty>> call = HttpUtils.getInstance().getApiService(ApiService.class).doVerification(text, String.valueOf(bdId));
                    call.enqueue(new SSHCallBackNew<BaseResp<BeanRespReqEmpty>>() {
                        @Override
                        public void onSuccess(Response<BaseResp<BeanRespReqEmpty>> response) throws Exception {
                            String msg = response.body().getMsg();
                            // if 成功 finish
                            finish();
                            Toast.makeText(HandMadeActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFail(String message) {
                            Toast.makeText(HandMadeActivity.this, message, Toast.LENGTH_SHORT).show();

                        }
                    });
                }
//                else {
//                    ToastUtils.showToast("请正确输入验证码!");
//                }
            }
        });


    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            /*隐藏软键盘*/
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager.isActive()) {
                inputMethodManager.hideSoftInputFromWindow(HandMadeActivity.this.getCurrentFocus().getWindowToken(), 0);
            }

            Toast.makeText(this, input.getText(), Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.dispatchKeyEvent(event);
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
        }
    }

    public static void setEditTextInhibitInputSpace(EditText editText) {
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (source.equals(" ")) return "";
                else return null;
            }
        };
        editText.setFilters(new InputFilter[]{filter});
    }

    public static void showSoftInput(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (view != null && imm != null) {
            imm.showSoftInput(view, 0);
            // imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT); // 或者第二个参数传InputMethodManager.SHOW_IMPLICIT
        }
    }
}
