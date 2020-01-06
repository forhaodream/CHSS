package com.sheepshop.businessside.ui.openshop;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.XPopupImageLoader;
import com.sheepshop.businessside.MyApp;
import com.sheepshop.businessside.R;
import com.sheepshop.businessside.ui.myshop.ShopInfoBean;
import com.sheepshop.businessside.utils.ImageUtils;

import java.io.File;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.PermissionChecker;
import ch.chtool.base.BaseActivity;

/**
 * 店铺信息 营业资格
 *
 * @author Hm
 */
public class ShopBusinessInformationActivity extends BaseActivity implements View.OnClickListener {
    private final int CODE_IMAGE = 1;
    private final int CODE_LOCATION = 2;
    private File mFileivIdentityCardFront, mFileivIdentityCardBehind, mFileivBusinessLicense, mFileivLicense;
    private ArrayList<String> urls = new ArrayList<>();
    private ImageView ivIdentityCardFront;
    private ImageView ivIdentityCardFrontShowPic;
    private ImageView ivIdentityCardDeletePic;
    private RelativeLayout relaIdentityCardFront;
    private ImageView ivIdentityCardBehind;
    private ImageView ivIdentityCardBehindShowPic;
    private ImageView ivIdentityCardBehindDeletePic;
    private RelativeLayout relaIdentityCardBehind;
    private ImageView ivBusinessLicense;
    private ImageView ivBusinessLicenseShowPic;
    private ImageView ivBusinessLicenseDeletePic;
    private RelativeLayout relaBusinessLicense;
    private ImageView ivLicense;
    private ImageView ivLicenseShowPic;
    private ImageView ivLicenseDeletePic;
    private RelativeLayout relaLicense;
    private Button btnNextInfo;
    private ShopInfoBean bean;
    private ImageView returnImg;
    private Context c;

    @Override
    public void initData() {
    }

    /**
     * 检测是否有权限
     */
    private void checkPermission(int type) {
        if (Build.VERSION.SDK_INT > 22) {
            int cameraPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
            int recordPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
            ArrayList<String> permissions = new ArrayList<>();
            if (cameraPermission != PermissionChecker.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.CAMERA);
            }
            if (recordPermission != PermissionChecker.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
            if (permissions.size() > 0) {
                requestPermissions(permissions.toArray(new String[permissions.size()]), 0);
            } else {
                ImageUtils.takeOrChoosePhoto(this, type, type);
            }
        } else {
            ImageUtils.takeOrChoosePhoto(this, type, type);
        }
    }


    @Override
    public int initLayout() {
        return R.layout.activity_shop_business_information;
    }

    @Override
    public void initView() {
        c = ShopBusinessInformationActivity.this;
        returnImg = findViewById(R.id.title_back);
        returnImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ivIdentityCardFront = findViewById(R.id.iv_identity_card_front);
        ivIdentityCardFrontShowPic = findViewById(R.id.iv_identity_card_front_showPic);
        ivIdentityCardDeletePic = findViewById(R.id.iv_identity_card_deletePic);
        relaIdentityCardFront = findViewById(R.id.rela_identity_card_front);
        ivIdentityCardBehind = findViewById(R.id.iv_identity_card_behind);
        ivIdentityCardBehindShowPic = findViewById(R.id.iv_identity_card_behind_showPic);
        ivIdentityCardBehindDeletePic = findViewById(R.id.iv_identity_card_behind_deletePic);
        relaIdentityCardBehind = findViewById(R.id.rela_identity_card_behind);
        ivBusinessLicense = findViewById(R.id.iv_business_license);
        ivBusinessLicenseShowPic = findViewById(R.id.iv_business_license_showPic);
        ivBusinessLicenseDeletePic = findViewById(R.id.iv_business_license_deletePic);
        relaBusinessLicense = findViewById(R.id.rela_business_license);
        ivLicense = findViewById(R.id.iv_license);
        ivLicenseShowPic = findViewById(R.id.iv_license_showPic);
        ivLicenseDeletePic = findViewById(R.id.iv_license_deletePic);
        relaLicense = findViewById(R.id.rela_license);
        btnNextInfo = findViewById(R.id.btn_next_info);
        ivIdentityCardFront.setOnClickListener(this);
        ivIdentityCardFrontShowPic.setOnClickListener(this);
        ivIdentityCardDeletePic.setOnClickListener(this);
        relaIdentityCardFront.setOnClickListener(this);
        ivIdentityCardBehind.setOnClickListener(this);
        ivIdentityCardBehindShowPic.setOnClickListener(this);
        ivIdentityCardBehindDeletePic.setOnClickListener(this);
        relaIdentityCardBehind.setOnClickListener(this);
        ivBusinessLicense.setOnClickListener(this);
        ivBusinessLicenseShowPic.setOnClickListener(this);
        ivBusinessLicenseDeletePic.setOnClickListener(this);
        relaBusinessLicense.setOnClickListener(this);
        ivLicense.setOnClickListener(this);
        ivLicenseShowPic.setOnClickListener(this);
        ivLicenseDeletePic.setOnClickListener(this);
        relaLicense.setOnClickListener(this);
        btnNextInfo.setOnClickListener(this);
        bean = new ShopInfoBean();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        /**
         *  type = 1 身份证正面 ,type = 2 身份证反面, type =3 营业执照 ,
         *  type =4 许可证 , type = 5 店铺logo, type = 6 店铺logo
         *  type = 7店内环境照片
         */
        if (requestCode == 1) {
            mFileivIdentityCardFront = ImageUtils.getPhotoFromResult(this, data);
            relaIdentityCardFront.setVisibility(View.VISIBLE);
            ImageUtils.loadNormalImage(this, mFileivIdentityCardFront, ivIdentityCardFrontShowPic);
            bean.setIdCardFront(mFileivIdentityCardFront);
            MyApp.setShopInfoBean(bean);
        } else if (requestCode == 2) {
            mFileivIdentityCardBehind = ImageUtils.getPhotoFromResult(this, data);
            relaIdentityCardBehind.setVisibility(View.VISIBLE);
            ImageUtils.loadNormalImage(this, mFileivIdentityCardBehind, ivIdentityCardBehindShowPic);
            bean.setIdCardBehind(mFileivIdentityCardBehind);
            MyApp.setShopInfoBean(bean);
        } else if (requestCode == 3) {
            mFileivBusinessLicense = ImageUtils.getPhotoFromResult(this, data);
            relaBusinessLicense.setVisibility(View.VISIBLE);
            ImageUtils.loadNormalImage(this, mFileivBusinessLicense, ivBusinessLicenseShowPic);
            bean.setLicense(mFileivBusinessLicense);
            MyApp.setShopInfoBean(bean);
        } else if (requestCode == 4) {
            mFileivLicense = ImageUtils.getPhotoFromResult(this, data);
            relaLicense.setVisibility(View.VISIBLE);
            ImageUtils.loadNormalImage(this, mFileivLicense, ivLicenseShowPic);
            bean.setLicence(mFileivLicense);
            MyApp.setShopInfoBean(bean);
        }
    }


    @Override
    public void onClick(View var1) {
        switch (var1.getId()) {
            case R.id.iv_identity_card_front:
                checkPermission(1);
                break;
            case R.id.iv_identity_card_front_showPic:
                new XPopup.Builder(c).asImageViewer(ivIdentityCardFrontShowPic, mFileivIdentityCardFront, new ImageLoader()).isShowSaveButton(false).show();
                break;
            case R.id.iv_identity_card_deletePic:
                relaIdentityCardFront.setVisibility(View.GONE);
                mFileivIdentityCardFront = null;
                break;
            case R.id.rela_identity_card_front:
                break;
            case R.id.iv_identity_card_behind:
                checkPermission(2);
                break;
            case R.id.iv_identity_card_behind_showPic:
                new XPopup.Builder(c).asImageViewer(ivIdentityCardBehindShowPic, mFileivIdentityCardBehind, new ImageLoader()).isShowSaveButton(false).show();
                break;
            case R.id.iv_identity_card_behind_deletePic:
                relaIdentityCardBehind.setVisibility(View.GONE);
                mFileivIdentityCardBehind = null;
                break;
            case R.id.rela_identity_card_behind:
                break;
            case R.id.iv_business_license:
                checkPermission(3);
                break;
            case R.id.iv_business_license_showPic:
                new XPopup.Builder(c).asImageViewer(ivBusinessLicenseShowPic, mFileivBusinessLicense, new ImageLoader()).isShowSaveButton(false).show();
                break;
            case R.id.iv_business_license_deletePic:
                relaBusinessLicense.setVisibility(View.GONE);
                mFileivBusinessLicense = null;
                break;
            case R.id.rela_business_license:
                break;
            case R.id.iv_license:
                checkPermission(4);
                break;
            case R.id.iv_license_showPic:
                new XPopup.Builder(c).asImageViewer(ivLicenseShowPic, mFileivLicense, new ImageLoader()).isShowSaveButton(false).show();
                break;
            case R.id.iv_license_deletePic:
                relaLicense.setVisibility(View.GONE);
                mFileivLicense = null;
                break;
            case R.id.rela_license:
                break;
            case R.id.btn_next_info:
                if (mFileivLicense != null && mFileivBusinessLicense != null && mFileivIdentityCardBehind != null && mFileivIdentityCardFront != null) {
                    startActivity(StoreInfoActivity.class);
                } else {
                    Toast.makeText(this, "请选择图片!", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    class ImageLoader implements XPopupImageLoader {
        @Override
        public void loadImage(int position, @NonNull Object uri, @NonNull ImageView imageView) {
            Glide.with(imageView).load(uri).into(imageView);
        }

        @Override
        public File getImageFile(@NonNull Context context, @NonNull Object uri) {
            return null;
        }
    }


}
