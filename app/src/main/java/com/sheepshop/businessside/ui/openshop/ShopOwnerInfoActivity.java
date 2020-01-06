package com.sheepshop.businessside.ui.openshop;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.sheepshop.businessside.R;

import ch.chtool.base.BaseActivity;

/**
 * Created by CH
 * on 2019/11/12 20:57
 */
public class ShopOwnerInfoActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mIvIdentityCardFront;
    private ImageView mIvIdentityCardFrontShowPic;
    private ImageView mIvIdentityCardDeletePic;
    private RelativeLayout mRelaIdentityCardFront;
    private ImageView mIvIdentityCardBehind;
    private ImageView mIvIdentityCardBehindShowPic;
    private ImageView mIvIdentityCardBehindDeletePic;
    private RelativeLayout mRelaIdentityCardBehind;
    private ImageView mIvBusinessLicense;
    private ImageView mIvBusinessLicenseShowPic;
    private ImageView mIvBusinessLicenseDeletePic;
    private RelativeLayout mRelaBusinessLicense;
    private ImageView mIvLicense;
    private ImageView mIvLicenseShowPic;
    private ImageView mIvLicenseDeletePic;
    private RelativeLayout mRelaLicense;
    private Button mBtnNextInfo;

    @Override
    public int initLayout() {
        return R.layout.activity_shop_business_information;
    }

    @Override
    public void initView() {
        mIvIdentityCardFront = findViewById(R.id.iv_identity_card_front);
        mIvIdentityCardFrontShowPic = findViewById(R.id.iv_identity_card_front_showPic);
        mIvIdentityCardDeletePic = findViewById(R.id.iv_identity_card_deletePic);
        mRelaIdentityCardFront = findViewById(R.id.rela_identity_card_front);
        mIvIdentityCardBehind = findViewById(R.id.iv_identity_card_behind);
        mIvIdentityCardBehindShowPic = findViewById(R.id.iv_identity_card_behind_showPic);
        mIvIdentityCardBehindDeletePic = findViewById(R.id.iv_identity_card_behind_deletePic);
        mRelaIdentityCardBehind = findViewById(R.id.rela_identity_card_behind);
        mIvBusinessLicense = findViewById(R.id.iv_business_license);
        mIvBusinessLicenseShowPic = findViewById(R.id.iv_business_license_showPic);
        mIvBusinessLicenseDeletePic = findViewById(R.id.iv_business_license_deletePic);
        mRelaBusinessLicense = findViewById(R.id.rela_business_license);
        mIvLicense = findViewById(R.id.iv_license);
        mIvLicenseShowPic = findViewById(R.id.iv_license_showPic);
        mIvLicenseDeletePic = findViewById(R.id.iv_license_deletePic);
        mRelaLicense = findViewById(R.id.rela_license);
        mBtnNextInfo = findViewById(R.id.btn_next_info);
        mIvIdentityCardFront.setOnClickListener(this);
        mIvIdentityCardFrontShowPic.setOnClickListener(this);
        mIvIdentityCardDeletePic.setOnClickListener(this);
        mIvIdentityCardBehind.setOnClickListener(this);
        mIvIdentityCardBehindShowPic.setOnClickListener(this);
        mIvIdentityCardBehindDeletePic.setOnClickListener(this);
        mIvBusinessLicense.setOnClickListener(this);
        mIvBusinessLicenseShowPic.setOnClickListener(this);
        mIvBusinessLicenseDeletePic.setOnClickListener(this);
        mIvLicense.setOnClickListener(this);
        mIvLicenseShowPic.setOnClickListener(this);
        mIvLicenseDeletePic.setOnClickListener(this);
        mBtnNextInfo.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View var1) {
        switch (var1.getId()) {
            case R.id.iv_identity_card_front:

                break;
            case R.id.iv_identity_card_front_showPic:
                break;
            case R.id.iv_identity_card_deletePic:
                break;
            case R.id.iv_identity_card_behind:
                break;
            case R.id.iv_identity_card_behind_showPic:
                break;
            case R.id.iv_identity_card_behind_deletePic:
                break;
            case R.id.iv_business_license:
                break;
            case R.id.iv_business_license_showPic:
                break;
            case R.id.iv_business_license_deletePic:
                break;
            case R.id.iv_license:
                break;
            case R.id.iv_license_showPic:
                break;
            case R.id.iv_license_deletePic:
                break;
            case R.id.btn_next_info:
                break;
        }
    }
}
