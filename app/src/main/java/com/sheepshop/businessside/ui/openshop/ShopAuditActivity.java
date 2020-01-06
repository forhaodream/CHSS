package com.sheepshop.businessside.ui.openshop;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.XPopupImageLoader;
import com.sheepshop.businessside.R;
import com.sheepshop.businessside.entity.ShopAuditBean;
import com.sheepshop.businessside.okhttp.BaseResp;
import com.sheepshop.businessside.okhttp.HttpUtils;
import com.sheepshop.businessside.okhttp.SSHCallBackNew;
import com.sheepshop.businessside.service.ApiService;
import com.sheepshop.businessside.ui.myshop.ShopManagementActivity;
import com.sheepshop.businessside.utils.ToastUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import ch.chtool.base.BaseActivity;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by CH
 * on 2019/11/19 09:10
 */
public class ShopAuditActivity extends BaseActivity implements View.OnClickListener {
    private ImageView titleBack;
    private RelativeLayout title;
    private TextView shopName;
    private TextView shopState;
    private TextView shopKinds;
    private TextView shopOwner;
    private TextView titleShopTel;
    private TextView shopTel;
    private TextView shopAddress;
    private TextView textLogo;
    private ImageView imageLogo;
    private TextView textDoor;
    private ImageView imageDoor;
    private TextView titleStore;
    private ImageView imageInDoorOne;
    private ImageView imageInDoorTwo;
    private ImageView imageInDoorThree;
    private TextView titleShopTime;
    private TextView edStoreOpenTime;
    private TextView edStoreCloseTime;
    private TextView titleShopRemark;
    private EditText shopRemark;
    private ImageView imageFront;
    private ImageView imageReverse;
    private ImageView imageLicense;
    private ImageView imageLicence;
    private Button btnMineShop;
    private Context context;
    private ShopAuditBean bean;
    private ImageView mTitleBack;
    private RelativeLayout mTitle;
    private TextView mShopName;
    private TextView mShopState;
    private TextView mShopKinds;
    private TextView mShopOwner;
    private TextView mTitleShopTel;
    private TextView mShopTel;
    private TextView mShopAddress;
    private TextView mTextLogo;
    private ImageView mImageLogo;
    private TextView mTextDoor;
    private ImageView mImageDoor;
    private TextView mTitleStore;
    private ImageView mImageInDoorOne;
    private ImageView mImageInDoorTwo;
    private ImageView mImageInDoorThree;
    private TextView mTitleShopTime;
    private TextView mEdStoreOpenTime;
    private TextView mEdStoreCloseTime;
    private TextView mTitleShopRemark;
    private TextView mShopRemark;
    private ImageView mImageFront;
    private ImageView mImageReverse;
    private ImageView mImageLicense;
    private ImageView mImageLicence;
    private Button mBtnMineShop;
    private List<String> picLists = new ArrayList<>();
    private int buId;
    private String buToken;
    private int bdId;
    private SharedPreferences readInfo;
    private SharedPreferences.Editor editor;

    @Override
    public int initLayout() {
        return R.layout.activity_shop_audit;
    }

    @Override
    public void initView() {
        context = this;
        mTitleBack = findViewById(R.id.title_back);
        mTitle = findViewById(R.id.title);
        mShopName = findViewById(R.id.shop_name);
        mShopState = findViewById(R.id.shop_state);
        mShopKinds = findViewById(R.id.shop_kinds);
        mShopOwner = findViewById(R.id.shop_owner);
        mTitleShopTel = findViewById(R.id.title_shop_tel);
        mShopTel = findViewById(R.id.shop_tel);
        mShopAddress = findViewById(R.id.shop_address);
        mTextLogo = findViewById(R.id.text_logo);
        mImageLogo = findViewById(R.id.image_logo);
        mTextDoor = findViewById(R.id.text_door);
        mImageDoor = findViewById(R.id.image_door);
        mTitleStore = findViewById(R.id.title_store);
        mImageInDoorOne = findViewById(R.id.image_in_door_one);
        mImageInDoorTwo = findViewById(R.id.image_in_door_two);
        mImageInDoorThree = findViewById(R.id.image_in_door_three);
        mTitleShopTime = findViewById(R.id.title_shop_time);
        mEdStoreOpenTime = findViewById(R.id.ed_store_open_time);
        mEdStoreCloseTime = findViewById(R.id.ed_store_close_time);
        mTitleShopRemark = findViewById(R.id.title_shop_remark);
        mShopRemark = findViewById(R.id.shop_remark);
        mImageFront = findViewById(R.id.image_front);
        mImageReverse = findViewById(R.id.image_reverse);
        mImageLicense = findViewById(R.id.image_license);
        mImageLicence = findViewById(R.id.image_licence);
        mBtnMineShop = findViewById(R.id.btn_mine_shop);
        mTitleBack.setOnClickListener(this);
        mBtnMineShop.setOnClickListener(this);

    }

    @Override
    public void initData() {
        readInfo = getSharedPreferences("user_npt", Context.MODE_PRIVATE);
        editor = readInfo.edit();
        buId = readInfo.getInt("buId", 0);
        buToken = readInfo.getString("buToken", "");
        bdId = readInfo.getInt("bdId", 0);
        Call<BaseResp<ShopAuditBean>> call = HttpUtils.getInstance().getApiService(ApiService.class).shopAudit(String.valueOf(bdId), buToken, String.valueOf(buId));
        call.enqueue(new SSHCallBackNew<BaseResp<ShopAuditBean>>() {
            @Override
            public void onSuccess(Response<BaseResp<ShopAuditBean>> response) throws Exception {
                bean = response.body().getData();
                mShopName.setText(bean.getBcName());
                mShopState.setText("休息中");
                mShopKinds.setText(bean.getBcCuisineName());
                mShopOwner.setText(bean.getBcStorekeeper());
                mShopTel.setText(bean.getBcPhone());
                mShopAddress.setText(bean.getBcAddress());
                mEdStoreOpenTime.setText(bean.getBcOpentime());
                mEdStoreCloseTime.setText(bean.getBcClosetime());
                Glide.with(context).load(bean.getBcIdpreUrl()).into(mImageFront);
                Glide.with(context).load(bean.getBcIdbackUrl()).into(mImageReverse);
                Glide.with(context).load(bean.getBcLicenseUrl()).into(mImageLicense);
                Glide.with(context).load(bean.getBcExequaturUrl()).into(mImageLicence);
                Glide.with(context).load(bean.getBcLogo()).into(mImageLogo);
                Glide.with(context).load(bean.getBcMainpic()).into(mImageDoor);
                mImageLogo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new XPopup.Builder(context).asImageViewer(mImageLogo, bean.getBcLogo(), new ImageLoader()).isShowSaveButton(false).show();
                    }
                });
                mImageDoor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new XPopup.Builder(context).asImageViewer(mImageDoor, bean.getBcMainpic(), new ImageLoader()).isShowSaveButton(false).show();
                    }
                });
                mImageFront.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new XPopup.Builder(context).asImageViewer(mImageFront, bean.getBcIdpreUrl(), new ImageLoader()).isShowSaveButton(false).show();
                    }
                });
                mImageReverse.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new XPopup.Builder(context).asImageViewer(mImageReverse, bean.getBcIdbackUrl(), new ImageLoader()).isShowSaveButton(false).show();
                    }
                });
                mImageLicense.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new XPopup.Builder(context).asImageViewer(mImageLicense, bean.getBcLicenseUrl(), new ImageLoader()).isShowSaveButton(false).show();
                    }
                });
                mImageLicence.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new XPopup.Builder(context).asImageViewer(mImageLicence, bean.getBcExequaturUrl(), new ImageLoader()).isShowSaveButton(false).show();
                    }
                });
                if (!TextUtils.isEmpty(bean.getBcDetailpic())) {
                    picLists = Arrays.asList(bean.getBcDetailpic().split(","));
                    if (picLists != null) {
                        Glide.with(context).load(picLists.get(0)).into(mImageInDoorOne);
                        Glide.with(context).load(picLists.get(1)).into(mImageInDoorTwo);
                        Glide.with(context).load(picLists.get(2)).into(mImageInDoorThree);
                        mImageInDoorOne.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                new XPopup.Builder(context).asImageViewer(mImageInDoorOne, picLists.get(0), new ImageLoader()).isShowSaveButton(false).show();
                            }
                        });
                        mImageInDoorTwo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                new XPopup.Builder(context).asImageViewer(mImageInDoorTwo, picLists.get(1), new ImageLoader()).isShowSaveButton(false).show();
                            }
                        });
                        mImageInDoorThree.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                new XPopup.Builder(context).asImageViewer(mImageInDoorThree, picLists.get(2), new ImageLoader()).isShowSaveButton(false).show();
                            }
                        });
                    }
                } else {
                    ToastUtils.showToast("图片加载异常");
                }
            }

            @Override
            public void onFail(String message) {

            }
        });
    }

    @Override
    public void onClick(View var1) {
        switch (var1.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.btn_mine_shop:
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
            //必须实现这个方法，返回uri对应的缓存文件，可参照下面的实现，内部保存图片会用到。如果你不需要保存图片这个功能，可以返回null。
            return null;
        }
    }
}
