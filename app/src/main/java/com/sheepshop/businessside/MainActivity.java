package com.sheepshop.businessside;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.sheepshop.businessside.entity.UserInfoBean;
import com.sheepshop.businessside.okhttp.BaseResp;
import com.sheepshop.businessside.okhttp.HttpUtils;
import com.sheepshop.businessside.okhttp.SSHCallBackNew;
import com.sheepshop.businessside.service.ApiService;
import com.sheepshop.businessside.service.DemoIntentService;
import com.sheepshop.businessside.ui.mycenter.CenterActivity;
import com.sheepshop.businessside.ui.myshop.LoginActivity;
import com.sheepshop.businessside.ui.myshop.MyShopActivity;
import com.sheepshop.businessside.ui.opencenter.CenterAuditActivity;
import com.sheepshop.businessside.ui.opencenter.CenterLoginActivity;
import com.sheepshop.businessside.ui.opencenter.OpenCenterActivity;
import com.sheepshop.businessside.ui.openshop.AuditActivity;
import com.sheepshop.businessside.ui.openshop.OpenShopActivity;
import com.sheepshop.businessside.weight.ButtonUtils;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import androidx.annotation.Nullable;
import ch.chtool.base.BaseActivity;
import retrofit2.Call;
import retrofit2.Response;

/**
 * 首页
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {
    private SharedPreferences npt;
    private SharedPreferences.Editor editor;
    private int buId;
    private String buToken;
    private String buName;
    private String buCenterToken;
    private boolean isExit;
    private Button mMineShopBtn;
    private Button mOperatingCenterBtn;
    private boolean isFirst = true;
    private TextView exitText;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(getResources().getColor(R.color.f3));
        }
        npt = getSharedPreferences("user_npt", MODE_PRIVATE);
        editor = npt.edit();
        buId = npt.getInt("buId", 0);
        buToken = npt.getString("buToken", "");
        buName = npt.getString("buName", "");
        if ("".equals(buToken)) {
            exitText.setVisibility(View.GONE);
        } else {
            exitText.setVisibility(View.VISIBLE);
        }
        // DemoPushService 为【步骤2.8】自定义推送服务
        com.igexin.sdk.PushManager.getInstance().initialize(getApplicationContext(), DemoIntentService.class);

    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }

    };


    @Override
    public void initData() {

    }


    @Override
    public int initLayout() {
        AndPermission.with(this)
                .runtime()
                .permission(Permission.ACCESS_COARSE_LOCATION
                        , Permission.ACCESS_FINE_LOCATION
                        , Permission.WRITE_EXTERNAL_STORAGE
                        , Permission.READ_PHONE_STATE
                        , Permission.READ_EXTERNAL_STORAGE
                        , Permission.WRITE_EXTERNAL_STORAGE
                )
                .onGranted(permissions -> {
                    // Storage permission are allowed.
                })
                .onDenied(permissions -> {
                    // Storage permission are not allowed.
                })
                .start();
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        mMineShopBtn = findViewById(R.id.btn_mine_shop);
        mOperatingCenterBtn = findViewById(R.id.btn_operating_center);
        mMineShopBtn.setOnClickListener(this);
        mOperatingCenterBtn.setOnClickListener(this);
        exitText = findViewById(R.id.main_exit);
        exitText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new XPopup.Builder(MainActivity.this).asConfirm("提示", "是否退出绵羊牧场商家版？",
                        new OnConfirmListener() {
                            @Override
                            public void onConfirm() {
                                editor.clear();
                                editor.apply();
                                startActivity(new Intent(MainActivity.this, MainActivity.class));
                            }
                        }).show();

            }
        });

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            System.gc();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    public void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            System.exit(0);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_mine_shop:
                if (!ButtonUtils.isFastDoubleClick(R.id.btn_mine_shop)) {
                    if ("".equals(buToken)) {
                        startActivity(LoginActivity.class);
                    } else {
                        getShopInfo();
                    }
                }
                break;
            case R.id.btn_operating_center:
//                new XPopup.Builder(MainActivity.this).asConfirm("提示", "暂时未开通该模块!",
//                        new OnConfirmListener() {
//                            @Override
//                            public void onConfirm() {
//
//                            }
//                        }).hideCancelBtn().show();
                if (!ButtonUtils.isFastDoubleClick(R.id.btn_mine_shop)) {
                    if ("".equals(buToken)) {
                        startActivity(CenterLoginActivity.class);
                    } else {
                        getCenterInfo();
                    }
                }
                break;
            default:
                break;
        }
    }

    private void getShopInfo() {
        Call<BaseResp<UserInfoBean>> call = HttpUtils.getInstance().getApiService(ApiService.class).getUserInfo(String.valueOf(buId), buToken);
        Log.d("MainActivity", String.valueOf(buId));
        Log.d("MainActivity", buToken);
        call.enqueue(new SSHCallBackNew<BaseResp<UserInfoBean>>() {
            @Override
            public void onSuccess(Response<BaseResp<UserInfoBean>> response) throws Exception {
                UserInfoBean bean = response.body().getData();
                int buStoreType = bean.getBuStoreType();
                Log.d("MainActivity", "buStoreType" + buStoreType);
                if (buStoreType == 0) {
                    startActivity(OpenShopActivity.class);
                } else if (buStoreType == 1) {
                    editor.clear();
                    editor.apply();
                    startActivity(AuditActivity.class);
                } else if (buStoreType == 2) {
                    startActivity(OpenShopActivity.class);
                } else if (buStoreType == 3) {
                    startActivity(MyShopActivity.class);
                } else if (buStoreType == 4) {
                    startActivity(MyShopActivity.class);
                }
            }

            @Override
            public void onFail(String message) {
                Log.d("MainActivity", message);

            }
        });

    }

    private void getCenterInfo() {
        Call<BaseResp<UserInfoBean>> call = HttpUtils.getInstance().getApiService(ApiService.class).getUserInfo(String.valueOf(buId), buToken);
        Log.d("MainActivity", String.valueOf(buId));
        Log.d("MainActivity", buToken);
        call.enqueue(new SSHCallBackNew<BaseResp<UserInfoBean>>() {
            @Override
            public void onSuccess(Response<BaseResp<UserInfoBean>> response) throws Exception {
                UserInfoBean bean = response.body().getData();
                int buOperationType = bean.getBuOperationType();
                Log.d("MainActivity", "buOperationType" + buOperationType);
                if (buOperationType == 0) {
                    startActivity(OpenCenterActivity.class);
                } else if (buOperationType == 1) {
                    editor.clear();
                    editor.apply();
                    startActivity(CenterAuditActivity.class);
                } else if (buOperationType == 2) {
                    startActivity(OpenCenterActivity.class);
                } else if (buOperationType == 3) {
                    startActivity(CenterActivity.class);
                } else if (buOperationType == 4) {
//                    startActivity(MyShopActivity.class);
                    // 修改信息审核中
                }
            }

            @Override
            public void onFail(String message) {
                Log.d("MainActivity1", message);

            }
        });

    }
}
