package com.sheepshop.businessside.ui.myshop;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.makeramen.roundedimageview.RoundedImageView;
import com.sheepshop.businessside.MyApp;
import com.sheepshop.businessside.R;
import com.sheepshop.businessside.entity.BeanRespReqEmpty;
import com.sheepshop.businessside.entity.CouponDetailBean;
import com.sheepshop.businessside.entity.SuccessBean;
import com.sheepshop.businessside.okhttp.BaseResp;
import com.sheepshop.businessside.okhttp.HttpUtils;
import com.sheepshop.businessside.okhttp.SSHCallBackNew;
import com.sheepshop.businessside.service.ApiService;
import com.sheepshop.businessside.tool.ToastUtils;

import ch.chtool.base.BaseActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by CH
 * on 2019/11/7 13:37
 */
public class ExchangeDetailActivity extends BaseActivity implements View.OnClickListener {
    private String coupon_id;
    private Intent mIntent;
    private ImageView mTitleBack;
    private RelativeLayout mTitle;
    private RoundedImageView mItemMyShopHead;
    private TextView mItemMyShopNickname;
    private RoundedImageView mItemAddVoucherHead;
    private TextView mMyShopTitle;
    private TextView mItemAddVoucherTitle;
    private TextView mItemAddVoucherSub;
    private TextView mItemAddVoucherTime;
    private TextView mExchangeDetailTime;
    private RelativeLayout mItemAddVoucherCard;
    private View mViewLine;
    private Button mReturnTicket;
    public static String couponId;
    private int bdId;
    private String user_pic;
    private String user_name;
    private String coupon_user_id;
    private String time;
    private SharedPreferences readInfo;
    private SharedPreferences.Editor editor;

    @Override
    public int initLayout() {
        return R.layout.activity_exchange_detail;
    }

    @Override
    public void initView() {

        mTitleBack = findViewById(R.id.title_back);
        mTitle = findViewById(R.id.title);
        mItemMyShopHead = findViewById(R.id.item_my_shop_head);
        mItemMyShopNickname = findViewById(R.id.item_my_shop_nickname);
        mMyShopTitle = findViewById(R.id.my_shop_title);
        mItemAddVoucherHead = findViewById(R.id.item_add_voucher_head);
        mItemAddVoucherTitle = findViewById(R.id.item_add_voucher_title);
        mItemAddVoucherSub = findViewById(R.id.item_add_voucher_sub);
        mItemAddVoucherTime = findViewById(R.id.item_add_voucher_time);
        mItemAddVoucherCard = findViewById(R.id.item_add_voucher_card);
        mExchangeDetailTime = findViewById(R.id.exchange_detail_time);
        mViewLine = findViewById(R.id.view_line);
        mTitleBack.setOnClickListener(this);
        mReturnTicket = findViewById(R.id.return_ticket);
        mReturnTicket.setOnClickListener(this);
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        coupon_id = intent.getStringExtra("coupon_id");
        user_pic = intent.getStringExtra("user_pic");
        user_name = intent.getStringExtra("user_name");
        coupon_user_id = intent.getStringExtra("coupon_user_id");
        time = intent.getStringExtra("time");
        mExchangeDetailTime.setText("兑换时间:" + time);
        mItemMyShopNickname.setText(user_name);
        Glide.with(ExchangeDetailActivity.this).load(user_pic).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(mItemMyShopHead);
        readInfo = getSharedPreferences("user_npt", Context.MODE_PRIVATE);
        editor = readInfo.edit();
        bdId = readInfo.getInt("bdId", 0);
        Call<BaseResp<CouponDetailBean>> call = HttpUtils.getInstance().getApiService(ApiService.class).couponDetail(coupon_id, "1", String.valueOf(bdId));
        call.enqueue(new SSHCallBackNew<BaseResp<CouponDetailBean>>() {
            @Override
            public void onSuccess(Response<BaseResp<CouponDetailBean>> response) throws Exception {
                String msg = response.body().getMsg();
                CouponDetailBean bean = response.body().getData();
                Log.d("ExchangeDetailActivity", coupon_id);
                Log.d("ExchangeDetailActivity", " - " + MyApp.getShopInfoBean().getShopId());

                if (bean != null) {
                    mItemMyShopNickname.setText(bean.getCouponName());
                    Glide.with(ExchangeDetailActivity.this).load(bean.getCouponPic()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(mItemAddVoucherHead);
                    mItemAddVoucherTitle.setText(bean.getCouponName());
                    mItemAddVoucherTime.setText("有效期: " + bean.getCouponStartTime() + " - " + bean.getCouponEndTime());
                } else {
                    ToastUtils.showToast("数据异常!");
                }

            }

            @Override
            public void onFail(String message) {
                ToastUtils.showToast(message);
            }
        });

    }

    @Override
    public void onClick(View var1) {
        switch (var1.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.return_ticket:
                if (!TextUtils.isEmpty(coupon_id)) {
                    userTicket();
                } else {
                    ToastUtils.showToast("数据异常!");
                }
                break;
        }
    }

    private void userTicket() {
        Call<BaseResp<BeanRespReqEmpty>> call = HttpUtils.getInstance().getApiService(ApiService.class).userState(coupon_user_id);
        call.enqueue(new SSHCallBackNew<BaseResp<BeanRespReqEmpty>>() {
            @Override
            public void onSuccess(Response<BaseResp<BeanRespReqEmpty>> response) throws Exception {
                String msg = response.body().getMsg();
                ToastUtils.showToast(msg);
                finish();
            }

            @Override
            public void onFail(String message) {
                ToastUtils.showToast(message);
            }
        });
    }
}