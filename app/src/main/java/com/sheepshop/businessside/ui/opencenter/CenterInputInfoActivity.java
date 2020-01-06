package com.sheepshop.businessside.ui.opencenter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.bumptech.glide.Glide;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citypickerview.CityPickerView;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.lxj.xpopup.interfaces.XPopupImageLoader;
import com.lxj.xpopup.util.XPopupUtils;
import com.nanchen.compresshelper.CompressHelper;
import com.sheepshop.businessside.MyApp;
import com.sheepshop.businessside.R;
import com.sheepshop.businessside.entity.BankListBean;
import com.sheepshop.businessside.entity.BeanRespReqEmpty;
import com.sheepshop.businessside.entity.FilesUpload;
import com.sheepshop.businessside.entity.ProvinceBean;
import com.sheepshop.businessside.okhttp.BaseResp;
import com.sheepshop.businessside.okhttp.HttpUtils;
import com.sheepshop.businessside.okhttp.SSHCallBackNew;
import com.sheepshop.businessside.service.ApiService;
import com.sheepshop.businessside.tool.ToastUtils;
import com.sheepshop.businessside.ui.bean.CenterInfoBean;
import com.sheepshop.businessside.ui.openshop.MapActivity;
import com.sheepshop.businessside.utils.ImageUtils;
import com.sheepshop.businessside.weight.LoadingDialog;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.PermissionChecker;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ch.chtool.base.BaseActivity;
import ch.chtool.utils.RecyclerAdapter;
import ch.chtool.utils.RecyclerViewHolder;
import cn.addapp.pickers.picker.TimePicker;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author CH
 */
public class CenterInputInfoActivity extends BaseActivity implements View.OnClickListener, OnGetGeoCoderResultListener {
    @Override
    public int initLayout() {
        return R.layout.activity_center_input_info;
    }

    private final int CODE_IMAGE = 1;
    private final int CODE_LOCATION = 2;
    private File mFileivIdentityCardFront, mFileivIdentityCardBehind, mFileivBusinessLicense, mFileivLicense;
    private ArrayList<String> urls = new ArrayList<>();
    private ImageView ivIdentityCardFront;
    private ImageView ivIdentityCardFrontShowPic;
    private ImageView ivIdentityCardDeletePic;
    private RelativeLayout relaIdentityCardFront;
    private ImageView ivIdentityCardBehindShowPic;
    private RelativeLayout relaIdentityCardBehind;
    private ImageView ivBusinessLicenseShowPic;
    private RelativeLayout relaBusinessLicense;
    private ImageView ivLicenseShowPic;
    private RelativeLayout relaLicense;
    private CenterInfoBean bean;
    private ImageView returnImg;
    private Context c;
    private ImageView mTitleBack;
    private RelativeLayout mTitle;
    private EditText mEdStoreUserName;
    private EditText mEdStoreUserPhone;
    private EditText mEdStoreName;
    private TextView mEdStorePlace;
    private EditText mEdStoreDetailedPlace;
    private BaiduMap mBaiduMap;
    private Button mBtnLocation;
    private TextView mCenterDistance;
    private TextView mEdStoreOpenTime;
    private TextView mEdStoreCloseTime;
    private TextView mCenterSelectorBank;

    private String defaultProvinceName = "辽宁省";
    private String defaultCityName = "沈阳市";
    private String defaultDistrict = "皇姑区";
    private CityConfig.WheelType mWheelType = CityConfig.WheelType.PRO_CITY_DIS;
    private CityPickerView mCityPickerView = new CityPickerView();
    private String provinceName;
    private String cityName;
    private String areaName;
    private String areaCode;
    private String bankName;
    private MapView mMapView = null;
    private int mCurrentDirection = 0;
    private double mCurrentLat = 0.0;
    private double mCurrentLon = 0.0;
    private MyLocationData locData;
    private double latitude;
    private double longitude;
    private String openHour, closeHour;
    private String openTime, closeTime;
    private GeoCoder mCoder = null;
    private SharedPreferences.Editor shopInfo;
    public CenterInputInfoActivity me = this;
    private String occName;
    private String occOwner;
    private String occKeepername;
    private String occAreacode;
    private String occAddress;
    private String occLongitude;
    private String occLatitude;
    private String occLogo;
    private String occPhone;
    private String occOpentime;
    private String occClosetime;
    private String occBank;
    private String occBankcard;
    private String occIdpreUrl;
    private String occIdbackUrl;
    private String occLicenseUrl;
    private String occExequaturUrl;
    private String occDistance;
    private String occIsCity;
    private String occContain;
    private EditText mEdCenterBankNumber;
    private List<String> picUrlList;
    private List<File> mFileList = new ArrayList<>();
    private String picUrl;
    private List<ProvinceBean> provinceBeans;
    private List<String> areaList;
    private List<String> codeList;
    private SharedPreferences npt;
    private int buId;

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

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCoder = GeoCoder.newInstance();
        mCoder.setOnGetGeoCodeResultListener(this);
        shopInfo = getSharedPreferences("shop_info", MODE_PRIVATE).edit();
        mCityPickerView.init(this);
        npt = getSharedPreferences("user_npt", MODE_PRIVATE);
        buId = npt.getInt("buId", 0);
    }


    @Override
    public void initView() {
        c = CenterInputInfoActivity.this;
        returnImg = findViewById(R.id.title_back);
        ivIdentityCardFront = findViewById(R.id.iv_identity_card_front);
        ivIdentityCardFrontShowPic = findViewById(R.id.iv_identity_card_front_showPic);
        ivIdentityCardDeletePic = findViewById(R.id.iv_identity_card_deletePic);
        relaIdentityCardFront = findViewById(R.id.rela_identity_card_front);
        ImageView ivIdentityCardBehind = findViewById(R.id.iv_identity_card_behind);
        ivIdentityCardBehindShowPic = findViewById(R.id.iv_identity_card_behind_showPic);
        ImageView ivIdentityCardBehindDeletePic = findViewById(R.id.iv_identity_card_behind_deletePic);
        relaIdentityCardBehind = findViewById(R.id.rela_identity_card_behind);
        ImageView ivBusinessLicense = findViewById(R.id.iv_business_license);
        ivBusinessLicenseShowPic = findViewById(R.id.iv_business_license_showPic);
        ImageView ivBusinessLicenseDeletePic = findViewById(R.id.iv_business_license_deletePic);
        relaBusinessLicense = findViewById(R.id.rela_business_license);
        ImageView ivLicense = findViewById(R.id.iv_license);
        ivLicenseShowPic = findViewById(R.id.iv_license_showPic);
        ImageView ivLicenseDeletePic = findViewById(R.id.iv_license_deletePic);
        relaLicense = findViewById(R.id.rela_license);
        Button btnNextInfo = findViewById(R.id.btn_next_info);
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
        mTitle = findViewById(R.id.title);
        mEdStoreUserName = findViewById(R.id.ed_store_user_name);
        mEdStoreUserPhone = findViewById(R.id.ed_store_user_phone);
        mEdStoreName = findViewById(R.id.ed_store_name);
        mEdStorePlace = findViewById(R.id.ed_store_place);
        mEdStoreDetailedPlace = findViewById(R.id.ed_store_detailed_place);
        mBtnLocation = findViewById(R.id.btn_location);
        mCenterDistance = findViewById(R.id.center_distance);
        mEdStoreOpenTime = findViewById(R.id.ed_store_open_time);
        mEdStoreCloseTime = findViewById(R.id.ed_store_close_time);
        mCenterSelectorBank = findViewById(R.id.center_selector_bank);
        mEdCenterBankNumber = findViewById(R.id.ed_center_bank_number);

        mEdStorePlace.setOnClickListener(this);
        mBtnLocation.setOnClickListener(this);
        mCenterDistance.setOnClickListener(this);
        mEdStoreOpenTime.setOnClickListener(this);
        mEdStoreCloseTime.setOnClickListener(this);
        mCenterSelectorBank.setOnClickListener(this);
        mMapView = findViewById(R.id.baidu_map);
        mBaiduMap = mMapView.getMap();
        mMapView.showZoomControls(false);
        mBtnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mEdStoreDetailedPlace.getText().toString())) {
                    Toast.makeText(CenterInputInfoActivity.this, "请填写详细地址!", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(CenterInputInfoActivity.this, MapActivity.class);
                    intent.putExtra("latitude", latitude);
                    intent.putExtra("longitude", longitude);
                    startActivityForResult(intent, MyApp.REQUEST_CODE);
                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == 1) {
            mFileivIdentityCardFront = ImageUtils.getPhotoFromResult(this, data);
            relaIdentityCardFront.setVisibility(View.VISIBLE);
            ImageUtils.loadNormalImage(this, mFileivIdentityCardFront, ivIdentityCardFrontShowPic);
            bean.setIdCardFront(mFileivIdentityCardFront);
            mFileList.add(CompressHelper.getDefault(this).compressToFile(mFileivIdentityCardFront));
        } else if (requestCode == 2) {
            mFileivIdentityCardBehind = ImageUtils.getPhotoFromResult(this, data);
            relaIdentityCardBehind.setVisibility(View.VISIBLE);
            ImageUtils.loadNormalImage(this, mFileivIdentityCardBehind, ivIdentityCardBehindShowPic);
            bean.setIdCardBehind(mFileivIdentityCardBehind);
            mFileList.add(CompressHelper.getDefault(this).compressToFile(mFileivIdentityCardBehind));
        } else if (requestCode == 3) {
            mFileivBusinessLicense = ImageUtils.getPhotoFromResult(this, data);
            relaBusinessLicense.setVisibility(View.VISIBLE);
            ImageUtils.loadNormalImage(this, mFileivBusinessLicense, ivBusinessLicenseShowPic);
            bean.setLicense(mFileivBusinessLicense);
            mFileList.add(CompressHelper.getDefault(this).compressToFile(mFileivBusinessLicense));
        } else if (requestCode == 4) {
            mFileivLicense = ImageUtils.getPhotoFromResult(this, data);
            relaLicense.setVisibility(View.VISIBLE);
            ImageUtils.loadNormalImage(this, mFileivLicense, ivLicenseShowPic);
            bean.setLicence(mFileivLicense);
            mFileList.add(CompressHelper.getDefault(this).compressToFile(mFileivLicense));
        }
    }

    class BankPopup extends BottomPopupView {
        private RecyclerView recyclerView;
        private Context c;
        private List<String> lists;
        private TextView title;

        public BankPopup(Context context) {
            super(context);
            this.c = context;
        }

        @Override
        protected int getImplLayoutId() {
            return R.layout.custom_bottom_popup;
        }

        @Override
        protected void onCreate() {
            super.onCreate();
            title = findViewById(R.id.popup_title);
            recyclerView = findViewById(R.id.recyclerView);
            title.setText("请选择银行");
            Call<BaseResp<List<BankListBean>>> call = HttpUtils.getInstance().getApiService(ApiService.class).bankList();
            call.enqueue(new SSHCallBackNew<BaseResp<List<BankListBean>>>() {
                @Override
                public void onSuccess(Response<BaseResp<List<BankListBean>>> response) throws Exception {
                    List<BankListBean> lists = response.body().getData();
                    RecyclerAdapter<BankListBean> adapter = new RecyclerAdapter<BankListBean>(c, R.layout.item_store_type, lists) {
                        @Override
                        public void convert(RecyclerViewHolder holder, BankListBean itemData, int position) {
                            TextView title = holder.getView(R.id.title);
                            title.setText(itemData.getBankName());
                            title.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mCenterSelectorBank.setText(itemData.getBankName());
                                    bankName = itemData.getBankName();
                                    dismiss();
                                }
                            });
                        }


                    };
                    LinearLayoutManager manager = new LinearLayoutManager(c);
                    manager.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(manager);
                    recyclerView.setAdapter(adapter);
                }

                @Override
                public void onFail(String message) {
                    Log.d("1111111111", message);
                }
            });

        }

        //完全可见执行
        @Override
        protected void onShow() {
            super.onShow();
        }

        //完全消失执行
        @Override
        protected void onDismiss() {

        }

        @Override
        protected int getMaxHeight() {
            return (int) (XPopupUtils.getWindowHeight(getContext()) * .30f);
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
                    LoadingDialog.showRoundProcessDialog(me);
                    uploadPic();
                } else {
                    Toast.makeText(this, "请选择图片!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.title_back:
                finish();
                break;
            case R.id.ed_store_place:
                hideInPut();
                selectorCity();
                break;
            case R.id.btn_location:
                break;
            case R.id.center_distance:
                new XPopup.Builder(me).asCustom(new DistancePopup(me)).show();
                break;
            case R.id.ed_store_open_time:
                timePicker1();
                break;
            case R.id.ed_store_close_time:
                timePicker2();
                break;
            case R.id.center_selector_bank:
                new XPopup.Builder(me).asCustom(new BankPopup(me)).show();
                break;
            default:
                break;
        }
    }


    private void timePicker1() {
        TimePicker picker = new TimePicker(CenterInputInfoActivity.this, TimePicker.HOUR_24);
        picker.setRangeStart(0, 00);
        picker.setRangeEnd(23, 59);
        picker.setTopLineVisible(false);
        picker.setLineVisible(false);
        picker.setWheelModeEnable(false);

        picker.setOnTimePickListener(new TimePicker.OnTimePickListener() {
            @Override
            public void onTimePicked(String hour, String minute) {
                openHour = hour;
                openTime = hour + ":" + minute;
                mEdStoreOpenTime.setText(hour + ":" + minute);
                if (closeTime != null) {
                    timeCompare(openTime, closeTime);
                }
            }
        });
        picker.show();
    }


    private void timePicker2() {
        TimePicker picker = new TimePicker(CenterInputInfoActivity.this, TimePicker.HOUR_24);
        picker.setRangeStart(00, 00);
        picker.setRangeEnd(23, 59);
        picker.setTopLineVisible(false);
        picker.setLineVisible(false);
        picker.setWheelModeEnable(false);

        picker.setOnTimePickListener(new TimePicker.OnTimePickListener() {
            @Override
            public void onTimePicked(String hour, String minute) {
                closeHour = hour;
                closeTime = hour + ":" + minute;
                mEdStoreCloseTime.setText(hour + ":" + minute);
                if (openTime != null) {
                    timeCompare(openTime, closeTime);
                }


            }
        });
        picker.show();
    }

    private void timeCompare(String startTime, String endTime) {
        int i = 0;
        //注意：传过来的时间格式必须要和这里填入的时间格式相同
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        try {
            Date date1 = dateFormat.parse(startTime);
            Date date2 = dateFormat.parse(endTime);//结束时间
            // 1 结束时间小于开始时间 2 开始时间与结束时间相同 3 结束时间大于开始时间
            if (date2.getTime() < date1.getTime()) {
                new XPopup.Builder(me).asConfirm("提示", "休息时间不能小于营业时间!",
                        new OnConfirmListener() {
                            @Override
                            public void onConfirm() {
                            }
                        }).hideCancelBtn().show();
            } else if (date2.getTime() == date1.getTime()) {
                new XPopup.Builder(me).asConfirm("提示", "营业时间不能与休息时间相同!",
                        new OnConfirmListener() {
                            @Override
                            public void onConfirm() {
                            }
                        }).hideCancelBtn().show();
            } else if (date2.getTime() > date1.getTime()) {
                //结束时间大于开始时间
                i = 3;
            }
        } catch (Exception e) {

        }
    }


    /**
     * 隐藏软键盘
     */
    protected void hideInPut() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        View v = getWindow().peekDecorView();
        if (null != v) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    /**
     * 选择省市区
     */
    private void selectorCity() {
        CityConfig cityConfig = new CityConfig.Builder()
                .title("选择城市")
                .visibleItemsCount(5)
                .province(defaultProvinceName)
                .city(defaultCityName)
                .district(defaultDistrict)
                .provinceCyclic(true)
                .cityCyclic(true)
                .districtCyclic(true)
                .setCityWheelType(mWheelType)
                .setCustomItemLayout(R.layout.item_city)//自定义item的布局
                .setCustomItemTextViewId(R.id.item_city_name_tv)
                .setShowGAT(true)
                .build();

        mCityPickerView.setConfig(cityConfig);
        mCityPickerView.setOnCityItemClickListener(new OnCityItemClickListener() {
            @Override
            public void onSelected(com.lljjcoder.bean.ProvinceBean province, CityBean city, DistrictBean district) {
                StringBuilder sb = new StringBuilder();
                if (province != null) {
                    sb.append(province.getName() + "-");
                }
                if (city != null) {
                    sb.append(city.getName() + "-");
                }
                if (district != null) {
                    sb.append(district.getName());
                }
                mEdStorePlace.setText("" + sb.toString());
                provinceName = province.getName();
                cityName = city.getName();
                areaName = district.getName();
                mCoder.geocode(new GeoCodeOption().city(city.getName()).address(district.getName()));
                areaCode = district.getId();
                areaList = new ArrayList<>();
                codeList = new ArrayList<>();
                for (int i = 0; i < city.getCityList().size(); i++) {
                    areaList.add(city.getCityList().get(i).getName());
                    codeList.add(city.getCityList().get(i).getId());
                }
                Log.d("CenterInputInfoActivity", areaList.toString());
                Log.d("CenterInputInfoActivity", codeList.toString());


            }

            @Override
            public void onCancel() {
                com.lljjcoder.style.citylist.Toast.ToastUtils.showLongToast(CenterInputInfoActivity.this, "已取消");
            }
        });
        mCityPickerView.showCityPicker();
    }

    @Override
    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
        if (null != geoCodeResult && null != geoCodeResult.getLocation()) {
            if (geoCodeResult == null || geoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
                //没有检索到结果
                return;
            } else {
                latitude = geoCodeResult.getLocation().latitude;
                longitude = geoCodeResult.getLocation().longitude;
                LatLng ll = new LatLng(latitude, longitude);
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
        }
    }

    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {

    }


    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }
            mCurrentLat = location.getLatitude();
            mCurrentLon = location.getLongitude();
            locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
            MapStatus.Builder builder = new MapStatus.Builder();
            builder.target(ll).zoom(18.0f);
//            BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_shop);
//            OverlayOptions option = new MarkerOptions().position(ll).icon(bitmap);
//            mBaiduMap.addOverlay(option);
            mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));

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

        //必须实现这个方法，返回uri对应的缓存文件，可参照下面的实现，内部保存图片会用到。如果你不需要保存图片这个功能，可以返回null。

    }

    @Override
    public void initData() {
        bean = new CenterInfoBean();
    }

    private void uploadPic() {
        List<MultipartBody.Part> files2 = new ArrayList<>();
        //TODO 上传多张图片
        for (int i = 0; i < mFileList.size(); i++) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), mFileList.get(i));
            MultipartBody.Part form = MultipartBody.Part.createFormData("files", mFileList.get(i).getName(), requestBody);
            files2.add(form);
        }
        Call<FilesUpload> call = HttpUtils.getInstance().getApiService(ApiService.class).upload(files2);
        call.enqueue(new Callback<FilesUpload>() {
            @Override
            public void onResponse(Call<FilesUpload> call, Response<FilesUpload> response) {
                mFileList.clear();
                picUrl = response.body().getData();
                Log.d("StoreInfoActivity", picUrl + "");
                if (!TextUtils.isEmpty(picUrl)) {
                    picUrlList = Arrays.asList(picUrl.split(","));
                    if (picUrlList != null) {
                        addCenter();
                        Log.d("CenterInputInfoActivity", picUrl);
                    } else {
                        Log.d("StoreInfoActivity", "*****************************");
                    }
                } else {
                    LoadingDialog.dismissDialog();
                    ToastUtils.showToast("图片上传失败");
                }
            }

            @Override
            public void onFailure(Call<FilesUpload> call, Throwable t) {
                mFileList.clear();
                LoadingDialog.dismissDialog();
                Log.d("StoreInfoActivity", "*************ERROR****************");
            }
        });
    }

    class DistancePopup extends BottomPopupView {
        private RecyclerView recyclerView;
        private Context c;
        private List<String> lists;
        private TagAdapter<String> mTagAdapter;
        private CheckBox cityCheck, inputCheck;
        private TextView tv;
        private Button save;
        private EditText distanceEdit;
        private ImageView cancelImage;

        public DistancePopup(Context context) {
            super(context);
            this.c = context;
        }

        @Override
        protected int getImplLayoutId() {
            return R.layout.popup_bottom_distance;
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        protected void onCreate() {
            super.onCreate();
            List<String> outAreaList = new ArrayList<>();
            List<String> outCodeList = new ArrayList<>();
            distanceEdit = findViewById(R.id.edit_distance);
            cancelImage = findViewById(R.id.image_close);
            cancelImage.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
            save = findViewById(R.id.text_save);
            save.setOnClickListener(new OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onClick(View v) {
                    if (cityCheck.isChecked()) {
                        HashSet outAreaSet = new HashSet(outAreaList);
                        HashSet finalAreaSet = new HashSet(areaList);
                        HashSet outCodeSet = new HashSet(outCodeList);
                        HashSet finalCodeSet = new HashSet(codeList);
                        finalAreaSet.removeAll(outAreaSet);
                        finalCodeSet.removeAll(outCodeSet);
                        occDistance = "";//String.join(",", finalAreaSet)
                        occContain = String.join(",", finalCodeSet);
                        Log.d("DistancePopup--", occDistance);
                        Log.d("DistancePopup--", occContain);
                        mCenterDistance.setText("全城配送");
                    } else {
                        occDistance = distanceEdit.getText().toString().trim();
                        mCenterDistance.setText(occDistance + "KM");
                    }
                    dismiss();
                }
            });
            cityCheck = findViewById(R.id.check_city_check);
            cityCheck.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (cityCheck.isChecked()) {
                        occIsCity = "1";
                        Set<Integer> set = new ArraySet<>();
                        for (int i = 0; i < areaList.size(); i++) {
                            set.add(i);
                        }
                        Log.d("DistancePopup", set.toString());
                        mTagAdapter.setSelectedList(set);
                        inputCheck.setChecked(false);
                        editTextEnable(false, distanceEdit);
                    } else {
                        editTextEnable(true, distanceEdit);
                        inputCheck.setChecked(true);
                        mTagAdapter.setSelectedList(-1);
                    }
                }
            });
            inputCheck = findViewById(R.id.check_distance_check);
            inputCheck.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (inputCheck.isChecked()) {
                        occIsCity = "0";
                        editTextEnable(true, distanceEdit);
                        cityCheck.setChecked(false);
                        mTagAdapter.setSelectedList(-1);
                    } else {
                        editTextEnable(false, distanceEdit);
                        cityCheck.setChecked(true);
                        Set<Integer> set = new ArraySet<>();
                        for (int i = 0; i < areaList.size(); i++) {
                            set.add(i);
                        }
                        Log.d("DistancePopup", set.toString());
                        mTagAdapter.setSelectedList(set);
                    }
                }
            });

            TagFlowLayout flowLayout = findViewById(R.id.id_flowlayout);
            LayoutInflater mInflater = LayoutInflater.from(c);

            flowLayout.setAdapter(mTagAdapter = new TagAdapter<String>(areaList) {
                @Override
                public View getView(FlowLayout parent, int position, String user) {
                    tv = (TextView) mInflater.inflate(R.layout.tv, flowLayout, false);
                    tv.setText(areaList.get(position));
                    return tv;
                }
            });
//            flowLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
//                @Override
//                public void onSelected(Set<Integer> selectPosSet) {
//                    Log.d("DistancePopup", "selectPosSet:" + selectPosSet);
//                    for (int i = 0; i < selectPosSet.size(); i++) {
//                        strings1.add(list.get(i));
//                    }
//
//
//                }
//            });
            flowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                @Override
                public boolean onTagClick(View view, int position, FlowLayout parent) {
                    outAreaList.add(areaList.get(position));
                    outCodeList.add(codeList.get(position));
                    Log.d("DistancePopup", "outAreaList:" + outAreaList);
                    Log.d("DistancePopup", "outCodeList:" + outCodeList);
                    return false;
                }
            });
        }

        //完全可见执行
        @Override
        protected void onShow() {
            super.onShow();
        }

        //完全消失执行
        @Override
        protected void onDismiss() {

        }

        @Override
        protected int getMaxHeight() {
            return getPopupHeight();//XPopupUtils.getWindowHeight(getContext()) * .50f
        }
    }

    // 设置是否可编辑
    public void editTextEnable(boolean enable, EditText editText) {
        editText.setFocusable(enable);
        editText.setFocusableInTouchMode(enable);
        editText.setLongClickable(enable);
        editText.setInputType(enable ? InputType.TYPE_CLASS_TEXT : InputType.TYPE_NULL);
    }

    private void addCenter() {
        occAddress = provinceName + cityName + areaName + mEdStoreDetailedPlace.getText().toString();
        Call<BaseResp<BeanRespReqEmpty>> call = HttpUtils.getInstance().getApiService(ApiService.class).addCenter(
                mEdStoreName.getText().toString(), String.valueOf(buId), mEdStoreUserName.getText().toString(), areaCode, occAddress, String.valueOf(longitude), String.valueOf(latitude),
                "", mEdStoreUserPhone.getText().toString(), openTime, closeTime, bankName, mEdCenterBankNumber.getText().toString(),
                picUrlList.get(0), picUrlList.get(1), picUrlList.get(2), picUrlList.get(3), occDistance, occIsCity, occContain);
        call.enqueue(new SSHCallBackNew<BaseResp<BeanRespReqEmpty>>() {
            @Override
            public void onSuccess(Response<BaseResp<BeanRespReqEmpty>> response) throws Exception {
                String message = response.body().getMsg();
                ToastUtils.showToast(message);
                Log.d("CenterInputInfoActivity", message);
                LoadingDialog.dismissDialog();
                startActivity(CenterAuditActivity.class);
            }

            @Override
            public void onFail(String message) {
                ToastUtils.showToast(message);
                Log.d("CenterInputInfoActivity", message);
                LoadingDialog.dismissDialog();

            }
        });
    }


}
