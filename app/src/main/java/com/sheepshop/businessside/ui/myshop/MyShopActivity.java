package com.sheepshop.businessside.ui.myshop;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnCancelListener;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.makeramen.roundedimageview.RoundedImageView;
import com.nex3z.flowlayout.FlowLayout;
import com.sheepshop.businessside.MainActivity;
import com.sheepshop.businessside.MyApp;
import com.sheepshop.businessside.R;
import com.sheepshop.businessside.entity.ShopFirstInfoBean;
import com.sheepshop.businessside.entity.ShopStateBean;
import com.sheepshop.businessside.entity.UserInfoBean;
import com.sheepshop.businessside.okhttp.BaseResp;
import com.sheepshop.businessside.okhttp.HttpUtils;
import com.sheepshop.businessside.okhttp.SSHCallBackNew;
import com.sheepshop.businessside.service.ApiService;
import com.sheepshop.businessside.ui.openshop.AuditActivity;
import com.sheepshop.businessside.ui.openshop.OpenShopActivity;
import com.sheepshop.businessside.ui.openshop.ShopAuditActivity;
import com.sheepshop.businessside.ui.setting.AboutActivity;
import com.sheepshop.businessside.ui.setting.CallCenterActivity;
import com.sheepshop.businessside.ui.setting.ModifyNumberActivity;
import com.sheepshop.businessside.utils.GlidRoundUtils;
import com.sheepshop.businessside.utils.ToastUtils;
import com.sheepshop.businessside.weight.ButtonUtils;
import com.sheepshop.businessside.weight.MyRatingBar;
import com.sheepshop.businessside.zxing.android.CaptureActivity;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ch.chtool.base.BaseActivity;
import ch.chtool.utils.RecyclerAdapter;
import ch.chtool.utils.RecyclerViewHolder;
import ch.ielse.view.SwitchView;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by CH
 * on 2019/11/5 14:09
 */
public class MyShopActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mTitleBack;
    private ImageView mTitleShopManager;
    private ImageView mTitleShopMsg;
    private RelativeLayout mTitle;
    private TextView mMyShopTitle;
    private SwitchView mMyShopSwitch;
    private ImageView mMyShopImage;
    private MyRatingBar mMyShopRatingbar;
    private TextView mMyShopRatingbarText;
    private TextView mMyShopRatingbarAddress;
    private TextView mMyShopCanUse;
    private RecyclerView mMyShopRecycler;
    private RecyclerAdapter mAdapter;
    private RelativeLayout mListCard;
    private TextView mAllEvaluate;
    private LinearLayout mInput;
    private LinearLayout mScanCode;
    private TextView mMyShopAllUse;
    private TextView mItemMyShopTitle;
    private TextView mShopState;
    private String shopStateStr;
    private int shopState;
    private SharedPreferences readInfo;
    private SharedPreferences.Editor editor;
    private SharedPreferences npt;
    private int buId;
    private String buToken;
    private int bdId;
    private PopupWindow settingPopup;
    private RelativeLayout addCard;
    private RelativeLayout managerCard;

    @Override
    public int initLayout() {
        return R.layout.activity_my_shop;
    }

    @Override
    public void initView() {
        mTitleBack = findViewById(R.id.title_back);
        mTitleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTitleShopManager = findViewById(R.id.title_shop_manager);
        mTitle = findViewById(R.id.title);
        mMyShopTitle = findViewById(R.id.my_shop_title);
        mMyShopSwitch = findViewById(R.id.my_shop_switch);
        mMyShopImage = findViewById(R.id.my_shop_image);
        mMyShopRatingbar = findViewById(R.id.my_shop_ratingbar);
        mMyShopRatingbarText = findViewById(R.id.my_shop_ratingbar_text);
        mMyShopRatingbarAddress = findViewById(R.id.my_shop_ratingbar_address);
        mMyShopCanUse = findViewById(R.id.my_shop_can_use);
        mMyShopRecycler = findViewById(R.id.my_shop_recycler);
        addCard = findViewById(R.id.add_card);
        managerCard = findViewById(R.id.manager_card);
        mListCard = findViewById(R.id.list_card);
        addCard.setOnClickListener(this);
        managerCard.setOnClickListener(this);
        mListCard.setOnClickListener(this);
        mAllEvaluate = findViewById(R.id.all_evaluate);
        mTitleShopManager.setOnClickListener(this);
        mAllEvaluate.setOnClickListener(this);
        mInput = findViewById(R.id.input);
        mInput.setOnClickListener(this);
        mScanCode = findViewById(R.id.scan_code);
        mScanCode.setOnClickListener(this);
        mMyShopAllUse = findViewById(R.id.my_shop_all_use);
        mItemMyShopTitle = findViewById(R.id.item_my_shop_title);
        mShopState = findViewById(R.id.shop_state);
        mMyShopSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ButtonUtils.isFastDoubleClick(R.id.my_shop_switch)) {
                    if (shopState == 0) {
                        new XPopup.Builder(MyShopActivity.this).dismissOnTouchOutside(false).asConfirm("是否关闭店铺休息", "",
                                new OnConfirmListener() {
                                    @Override
                                    public void onConfirm() {
                                        switchState();
                                    }
                                }, new OnCancelListener() {
                                    @Override
                                    public void onCancel() {
                                        getShopInfo();
                                    }
                                }).show();

                    } else {
                        new XPopup.Builder(MyShopActivity.this).dismissOnTouchOutside(false).asConfirm("是否开店营业", "",
                                new OnConfirmListener() {
                                    @Override
                                    public void onConfirm() {
                                        switchState();
                                    }
                                }, new OnCancelListener() {
                                    @Override
                                    public void onCancel() {
                                        getShopInfo();
                                    }
                                }).show();
                    }
                }
            }
        });

    }


    private void showPopup(String title) {
        View view = LayoutInflater.from(this).inflate(R.layout.popup_title, null);
        TextView titleText = view.findViewById(R.id.popup_title_text);
        titleText.setText(title);
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels / 3;
        PopupWindow popupWindow = new PopupWindow(view, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        //popupWindow在弹窗的时候背景半透明
        final WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.5f;
        getWindow().setAttributes(params);
        popupWindow.setOnDismissListener(() -> {
            params.alpha = 1.0f;
            getWindow().setAttributes(params);
        });
    }

    @Override
    public void initData() {
        readInfo = getSharedPreferences("user_npt", Context.MODE_PRIVATE);
        editor = readInfo.edit();
        buId = readInfo.getInt("buId", 0);
        buToken = readInfo.getString("buToken", "");
        getShopInfo();
    }

    private TextView buildLabel(String text) {
        TextView textView = new TextView(MyShopActivity.this);
        textView.setText(text);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
//        textView.setPadding((int) dpToPx(8), (int) dpToPx(4), (int) dpToPx(8), (int) dpToPx(4));
        textView.setPadding((int) dpToPx(4), (int) dpToPx(2), (int) dpToPx(4), (int) dpToPx(2));
        textView.setBackgroundResource(R.drawable.bg_gray_line);
        return textView;
    }

    private float dpToPx(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    @Override
    public void onClick(View var1) {
        switch (var1.getId()) {
            case R.id.add_card:
                startActivity(AddVoucherActivity.class);
                break;
            case R.id.manager_card:
                startActivity(ManagerVoucherActivity.class);
                break;
            case R.id.list_card:
                startActivity(ChangeListActivity.class);
                break;
            case R.id.all_evaluate:
                startActivity(ShopEvaluationActivity.class);
                break;
            case R.id.title_shop_manager:
                settingPopup(MyShopActivity.this, mTitleShopManager);
                break;
//            case R.id.title_shop_msg:
//                break;
            case R.id.input:
                if (shopState == 0) {
                    startActivity(HandMadeActivity.class);
                } else {
                    new XPopup.Builder(MyShopActivity.this).asConfirm("提示", "店铺休息期间,该功能不能使用!",
                            new OnConfirmListener() {
                                @Override
                                public void onConfirm() {

                                }
                            }).hideCancelBtn().show();
                }
                break;
            case R.id.scan_code:
                if (shopState == 0) {
                    AndPermission.with(this)
                            .runtime()
                            .permission(Permission.CAMERA, Permission.READ_EXTERNAL_STORAGE)
                            .onGranted(permissions -> {
                                // Storage permission are allowed.
                                startActivity(CaptureActivity.class);
                            })
                            .onDenied(permissions -> {
                                // Storage permission are not allowed.
                            })
                            .start();
                } else {
                    new XPopup.Builder(MyShopActivity.this).asConfirm("提示", "店铺休息期间,该功能不能使用!",
                            new OnConfirmListener() {
                                @Override
                                public void onConfirm() {

                                }
                            }).hideCancelBtn().show();
                }
                break;
            default:
                break;
        }
    }


    private void getShopInfo() {
        Call<BaseResp<ShopFirstInfoBean>> call = HttpUtils.getInstance().getApiService(ApiService.class).shopFirstInfo(buToken, String.valueOf(buId));
        call.enqueue(new SSHCallBackNew<BaseResp<ShopFirstInfoBean>>() {

            @Override
            public void onSuccess(Response<BaseResp<ShopFirstInfoBean>> response) throws Exception {
                String code = response.body().getCode();
                String msg = response.body().getMsg();
                if ("0".equals(code)) {
                    ShopFirstInfoBean shopBean = response.body().getData();
                    ShopInfoBean shopInfoBean = new ShopInfoBean();
                    shopInfoBean.setShopId(shopBean.getBdId());
                    Log.d("1111111111", shopBean.getBdId() + "");
                    MyApp.setShopInfoBean(shopInfoBean);
                    bdId = shopBean.getBdId();
                    editor.putInt("bdId", bdId);
                    editor.commit();
                    // 店铺信息
                    mMyShopTitle.setText(shopBean.getBdName());
                    mMyShopRatingbar.setStar(Float.parseFloat(shopBean.getBdStar()) / 2);
                    mMyShopRatingbarText.setText(shopBean.getBdScore());
                    mMyShopRatingbarAddress.setText(shopBean.getBdAddress());
                    StringBuffer sb = new StringBuffer("今天兑换");
                    sb.append(shopBean.getCouponUserNumNow());
                    sb.append("张");
                    mMyShopCanUse.setText(sb);
                    mMyShopAllUse.setText("总计" + shopBean.getCouponUserNumAll() + "张");
                    Log.d("11111111", "state:" + shopBean.getBdStatus());
                    if (shopBean.getBdStatus() == 0) {
                        shopState = 0;
                        mShopState.setText("营业中");
                        mMyShopSwitch.toggleSwitch(true);

                    } else {
                        shopState = 1;
                        mShopState.setText("休息中");
                        mMyShopSwitch.toggleSwitch(false);
                        addCard.setFocusable(false);
                        mListCard.setFocusable(false);
                        managerCard.setFocusable(false);
                    }
                    Glide.with(MyShopActivity.this).load(shopBean.getBdLogo()).placeholder(R.mipmap.fl_wujieguo_icon).apply(RequestOptions.bitmapTransform(new GlidRoundUtils(6))).into(mMyShopImage);
                    // 店铺评价
                    mAdapter = new RecyclerAdapter<ShopFirstInfoBean.CommentListBean>(MyShopActivity.this, R.layout.item_my_shop, shopBean.getCommentList()) {
                        @Override
                        public void convert(RecyclerViewHolder holder, ShopFirstInfoBean.CommentListBean itemData, int position) {
                            RoundedImageView headImg = holder.getView(R.id.item_my_shop_head);
                            TextView item_my_shop_nickname = holder.getView(R.id.item_my_shop_nickname);
                            MyRatingBar star = holder.getView(R.id.item_my_shop_ratingbar);
                            TextView score = holder.getView(R.id.item_my_shop_ratingbar_text);
                            TextView content = holder.getView(R.id.item_my_shop_content);
                            TextView cardName = holder.getView(R.id.my_shop_card_name);
                            TextView time = holder.getView(R.id.my_shop_time);
                            time.setText(itemData.getBucTime());
                            cardName.setText(itemData.getBucCouponName());
                            content.setText(itemData.getBucText());
                            score.setText(itemData.getBucStar() / 2 + "分");
                            star.setStar(((float) itemData.getBucStar()) / 2);
                            if (itemData.getBucHeadurl() != null) {
                                String picUrl = itemData.getBucHeadurl();
                                List<String> picUrlList = Arrays.asList(picUrl.split(","));
                            }
                            Glide.with(MyShopActivity.this).load(itemData.getBucHeadurl()).placeholder(R.mipmap.fl_wujieguo_icon).into(headImg);
                            item_my_shop_nickname.setText(itemData.getBucNickname());
                            FlowLayout flowLayout = holder.getView(R.id.item_my_shop_flow);
                            if (itemData.getBucLabelList().size() > 2) {
                                for (int i = 0; i < 3; i++) {
                                    TextView textView = buildLabel(itemData.getBucLabelList().get(i));
                                    flowLayout.addView(textView);
                                }
                            } else {
                                for (int i = 0; i < itemData.getBucLabelList().size(); i++) {
                                    TextView textView = buildLabel(itemData.getBucLabelList().get(i));
                                    flowLayout.addView(textView);
                                }
                            }

                        }
                    };
                    LinearLayoutManager layoutManager = new LinearLayoutManager(MyShopActivity.this);
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    mMyShopRecycler.setLayoutManager(layoutManager);
                    mMyShopRecycler.setAdapter(mAdapter);
                } else {
                    Toast.makeText(MyShopActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFail(String message) {
                Toast.makeText(MyShopActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
//
    }

    private void getUserInfo() {
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
                    startActivity(AuditActivity.class);
                } else if (buStoreType == 2) {
                    startActivity(OpenShopActivity.class);
                } else if (buStoreType == 3) {
                    startActivity(ShopManagementActivity.class);
                } else if (buStoreType == 4) {
                    Log.d("MainActivity", "ShopAuditActivity");
                    startActivity(ShopAuditActivity.class);
                }
            }

            @Override
            public void onFail(String message) {
                Log.d("MainActivity", message);

            }
        });

    }


    private void switchState() {
        Call<BaseResp<ShopStateBean>> call = HttpUtils.getInstance().getApiService(ApiService.class).switchState(String.valueOf(bdId), buToken, String.valueOf(buId));
        call.enqueue(new SSHCallBackNew<BaseResp<ShopStateBean>>() {
            @Override
            public void onSuccess(Response<BaseResp<ShopStateBean>> response) throws Exception {
                String msg = response.body().getMsg();
                String stateStr = response.body().getData().getState();
                ShopStateBean bean = response.body().getData();
                if (bean != null) {
                    getShopInfo();
//                    shopStateStr = bean.getState();
//                    if ("0".equals(shopStateStr)) {
//                        mMyShopSwitch.setChecked(true);
//                    } else {
//                        mMyShopSwitch.setChecked(false);
//                    }
                }
                ToastUtils.showToast(msg);

            }

            @Override
            public void onFail(String message) {
                Toast.makeText(MyShopActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void settingPopup(Activity activity, View view) {
        View popView = View.inflate(activity, R.layout.popup_setting, null);
        RecyclerView recyclerView = popView.findViewById(R.id.popup_setting_recycler);
        List<String> lists = new ArrayList<>();
        lists.add("修改店铺信息");
//        lists.add("修改登录手机号码");
//        lists.add("客服中心");
//        lists.add("关于绵羊商城卖家版");
        lists.add("退出登录");
        RecyclerAdapter adapter = new RecyclerAdapter<String>(MyShopActivity.this, R.layout.item_popup_setting, lists) {
            @Override
            public void convert(RecyclerViewHolder holder, String itemData, int position) {
                TextView title = holder.getView(R.id.item_title);
                LinearLayout layout = holder.getView(R.id.layout);
                title.setText(itemData);
                layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (position) {
                            case 0:
                                getUserInfo();
                                settingPopup.dismiss();
                                break;
//                            case 1:
//                                startActivity(ModifyNumberActivity.class);
//                                settingPopup.dismiss();
//                                break;
//                            case 2:
//                                startActivity(CallCenterActivity.class);
//                                settingPopup.dismiss();
//                                break;
//                            case 3:
//                                startActivity(AboutActivity.class);
//                                settingPopup.dismiss();
//                                break;
                            case 1:
                                settingPopup.dismiss();
                                new XPopup.Builder(MyShopActivity.this).asConfirm("提示", "退出后将不能使用我的店铺内的各项功能,确定退出吗？",
                                        new OnConfirmListener() {
                                            @Override
                                            public void onConfirm() {
                                                editor.clear();
                                                editor.apply();
                                                startActivity(MainActivity.class);
                                            }
                                        }).show();
                                break;
                            default:
                                break;
                        }
                    }
                });
            }


        };
        LinearLayoutManager layoutManager = new LinearLayoutManager(MyShopActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        //获取屏幕宽高
        int width = activity.getResources().getDisplayMetrics().widthPixels * 1 / 2;
        int height = activity.getResources().getDisplayMetrics().heightPixels * 1 / 3;
        settingPopup = new PopupWindow(popView, width, RelativeLayout.LayoutParams.WRAP_CONTENT);
        settingPopup.setFocusable(true);
        //点击外部popueWindow消失
        settingPopup.setOutsideTouchable(true);
        //popupWindow出现屏幕变为半透明
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = 0.9f;
        activity.getWindow().setAttributes(lp);
        settingPopup.showAsDropDown(view, -480, 36);
        settingPopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
                lp.alpha = 1f; //0.0-1.0
                activity.getWindow().setAttributes(lp);
            }
        });

    }

}
