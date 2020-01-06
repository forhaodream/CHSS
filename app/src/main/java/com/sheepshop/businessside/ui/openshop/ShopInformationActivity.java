package com.sheepshop.businessside.ui.openshop;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
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
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citypickerview.CityPickerView;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.util.XPopupUtils;
import com.sheepshop.businessside.MyApp;
import com.sheepshop.businessside.R;
import com.sheepshop.businessside.base.Constant;
import com.sheepshop.businessside.entity.DicEntity;
import com.sheepshop.businessside.entity.ProvinceBean;
import com.sheepshop.businessside.network.netbean.HttpBean;
import com.sheepshop.businessside.network.netbean.ResponseBean;
import com.sheepshop.businessside.network.netinterface.BaseCallBack;
import com.sheepshop.businessside.okhttp.BaseResp;
import com.sheepshop.businessside.okhttp.HttpUtils;
import com.sheepshop.businessside.okhttp.SSHCallBackNew;
import com.sheepshop.businessside.service.ApiService;
import com.sheepshop.businessside.tool.ToastUtils;
import com.sheepshop.businessside.ui.myshop.ShopInfoBean;
import com.sheepshop.businessside.utils.HttpRequestParamsBuilder;
import com.sheepshop.businessside.utils.NetUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ch.chtool.utils.CommonAdaper;
import ch.chtool.utils.CommonViewHolder;
import ch.chtool.utils.RecyclerAdapter;
import ch.chtool.utils.RecyclerViewHolder;
import cn.addapp.pickers.picker.TimePicker;
import retrofit2.Call;
import retrofit2.Response;

/**
 * 店铺信息
 *
 * @author Hm
 */
public class ShopInformationActivity extends FragmentActivity implements View.OnClickListener, OnGetGeoCoderResultListener {

    private List<DicEntity> dicList = new ArrayList<>();
    private ImageView returnImg;
    private Button locationBtn;
    private EditText mEdStoreName;
    private EditText mEdStoreUserName;
    private EditText mEdStoreUserPhone;
    private TextView mEdStoreTypeName;
    private TextView mEdStorePlace;
    private EditText mEdStoreDetailedPlace;
    private TextView mEdStoreOpeanTime;
    private TextView mEdStoreCloseTime;
    private Button mBtnNextInfo;
    private ListView mLeftList;
    private ListView mRightList;
    private ListView mMiddleList;
    private ListAdapter pAdapter;
    private ListAdapter cAdapter;
    private ListAdapter aAdapter;
    private int leftId;
    private PopupWindow popupWindow;
    private String provinceName;
    private String cityName;
    private String areaName;
    private String areaCode;
    private int kindsType;
    private MapView mMapView = null;
    private BaiduMap mBaiduMap;
    private int mCurrentDirection = 0;
    private double mCurrentLat = 0.0;
    private double mCurrentLon = 0.0;
    private MyLocationData locData;
    private double latitude;
    private double longitude;
    private String openHour, closeHour;
    private String openTime, closeTime;
    private GeoCoder mCoder = null;
    private ShopInfoBean shopInfoBean;
    private SharedPreferences.Editor shopInfo;
    private String defaultProvinceName = "辽宁省";
    private String defaultCityName = "沈阳市";
    private String defaultDistrict = "皇姑区";
    private CityConfig.WheelType mWheelType = CityConfig.WheelType.PRO_CITY_DIS;
    private CityPickerView mCityPickerView = new CityPickerView();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_information);
        mCoder = GeoCoder.newInstance();
        mCoder.setOnGetGeoCodeResultListener(this);
        initView();
        shopInfoBean = new ShopInfoBean();
        shopInfo = getSharedPreferences("shop_info", MODE_PRIVATE).edit();
        mCityPickerView.init(this);
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

    private void getDis() {
        Map<String, String> params = new HttpRequestParamsBuilder().build();
        params.put("type", "0");
        NetUtils.post(Constant.URL_DIC, params, DicEntity.class, HttpBean.getResDatatypeBeanlist(), new BaseCallBack() {
            @Override
            public void onRequesting() {

            }

            @Override
            public void onSuccess(ResponseBean data) {
                if (data.getData() == null) {
                    return;
                }
                dicList = (List<DicEntity>) data.pullData();
                List<String> mdata = new ArrayList<>();
                for (int i = 0; i < dicList.size(); i++) {
                    mdata.add(dicList.get(i).getCuisineName());
                }
                new XPopup.Builder(ShopInformationActivity.this).asCustom(new StoreTypePopup(ShopInformationActivity.this, mdata)).show();
            }

            @Override
            public void onError(ResponseBean msg) {
                ToastUtils.showToast(msg.getMsg());
            }

            @Override
            public void onErrorForOthers(ResponseBean msg) {
                ToastUtils.showToast(msg.getMsg());
            }
        });

    }


    class StoreTypePopup extends BottomPopupView {
        private RecyclerView recyclerView;
        private Context c;
        private List<String> lists;
        private TextView title;

        public StoreTypePopup(Context context, List<String> lists) {
            super(context);
            this.c = context;
            this.lists = lists;
        }

        @Override
        protected int getImplLayoutId() {
            return R.layout.custom_bottom_popup;
        }

        @Override
        protected void onCreate() {
            super.onCreate();
            title = findViewById(R.id.popup_title);
            title.setText("请选择店铺类型");
            recyclerView = findViewById(R.id.recyclerView);
            RecyclerAdapter<String> adapter = new RecyclerAdapter<String>(c, R.layout.item_store_type, lists) {
                @Override
                public void convert(RecyclerViewHolder holder, String itemData, int position) {
                    TextView title = holder.getView(R.id.title);
                    title.setText(itemData);
                    title.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mEdStoreTypeName.setText(itemData);
                            kindsType = dicList.get(position).getCuisineId();
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


    private void selectorCity() {
        View view = LayoutInflater.from(this).inflate(R.layout.popup_three_list, null);
        mLeftList = view.findViewById(R.id.left_list);
        mMiddleList = view.findViewById(R.id.middle_list);
        mRightList = view.findViewById(R.id.right_list);
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels / 3;
        popupWindow = new PopupWindow(view, width, height);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        //popupWindow在弹窗的时候背景半透明
        final WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.5f;
        getWindow().setAttributes(params);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                params.alpha = 1.0f;
                getWindow().setAttributes(params);
            }
        });
        getProvinceData();
    }

    List<ProvinceBean> provinceBeans;

    private void getProvinceData() {
        Call<BaseResp<List<ProvinceBean>>> call = HttpUtils.getInstance().getApiService(ApiService.class).getProvince(0);
        call.enqueue(new SSHCallBackNew<BaseResp<List<ProvinceBean>>>() {
            @Override
            public void onSuccess(Response<BaseResp<List<ProvinceBean>>> response) throws Exception {
                provinceBeans = response.body().getData();
                pAdapter = new LeftAdapter(ShopInformationActivity.this, R.layout.item_popup_left, provinceBeans);
                mLeftList.setAdapter(pAdapter);
                mLeftList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        leftId = provinceBeans.get(position).getId();
                        provinceName = provinceBeans.get(position).getName();
                        getCityData(leftId);
                    }
                });
            }

            @Override
            public void onFail(String message) {

            }
        });
    }

    class LeftAdapter extends CommonAdaper<ProvinceBean> {

        public LeftAdapter(Context mContext, int mLayoutId, List<ProvinceBean> mDatas) {
            super(mContext, mLayoutId, mDatas);
        }

        @Override
        public void convert(CommonViewHolder commonViewHolder, ProvinceBean provinceBean, int i) {
            TextView textView = commonViewHolder.getView(R.id.textView);
            textView.setText(provinceBean.getName());
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }
    }

    List<ProvinceBean> cityBean;

    private void getCityData(int pid) {
        Call<BaseResp<List<ProvinceBean>>> call = HttpUtils.getInstance().getApiService(ApiService.class).getCity(pid);
        call.enqueue(new SSHCallBackNew<BaseResp<List<ProvinceBean>>>() {
            @Override
            public void onSuccess(Response<BaseResp<List<ProvinceBean>>> response) throws Exception {
                cityBean = response.body().getData();
                cAdapter = new LeftAdapter(ShopInformationActivity.this, R.layout.item_popup_left, cityBean);
                mMiddleList.setAdapter(cAdapter);
                mMiddleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        cityName = cityBean.get(position).getName();
                        getAreaData(cityBean.get(position).getId());
                    }
                });
            }

            @Override
            public void onFail(String message) {

            }
        });

    }

    List<ProvinceBean> areaBean;

    private void getAreaData(int cid) {
        Call<BaseResp<List<ProvinceBean>>> call = HttpUtils.getInstance().getApiService(ApiService.class).getArea(cid);
        call.enqueue(new SSHCallBackNew<BaseResp<List<ProvinceBean>>>() {
            @Override
            public void onSuccess(Response<BaseResp<List<ProvinceBean>>> response) throws Exception {
                areaBean = response.body().getData();
                aAdapter = new LeftAdapter(ShopInformationActivity.this, R.layout.item_popup_left, areaBean);
                mRightList.setAdapter(aAdapter);
                mRightList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        popupWindow.dismiss();
                        areaName = areaBean.get(position).getName();
//                        areaCode = areaBean.get(position).getAreaCode();
                        Log.d("ShopInformationActivity", "areaCode:" + areaCode);
                        mEdStorePlace.setText(provinceName + "-" + cityName + "-" + areaName);
                        mCoder.geocode(new GeoCodeOption().city(cityName).address(areaName));
                    }
                });
            }

            @Override
            public void onFail(String message) {

            }
        });
    }


    /**
     * 选择省市区
     */
    private void seleCity() {
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
            }

            @Override
            public void onCancel() {
                com.lljjcoder.style.citylist.Toast.ToastUtils.showLongToast(ShopInformationActivity.this, "已取消");
            }
        });
        mCityPickerView.showCityPicker();
    }


    private void timePicker1() {
        TimePicker picker = new TimePicker(ShopInformationActivity.this, TimePicker.HOUR_24);
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
                mEdStoreOpeanTime.setText(hour + ":" + minute);
                if (closeTime != null) {
                    timeCompare(openTime, closeTime);
                }
            }
        });
        picker.show();
    }


    private void timePicker2() {
        TimePicker picker = new TimePicker(ShopInformationActivity.this, TimePicker.HOUR_24);
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
//                if (!TextUtils.isEmpty(openHour)) {
//                    if (Integer.valueOf(closeHour) < Integer.valueOf(openHour)) {
//                        Toast.makeText(ShopInformationActivity.this, "休息时间不能早于营业时间", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    Toast.makeText(ShopInformationActivity.this, "请选择营业时间", Toast.LENGTH_SHORT).show();
//                }
                if (openTime != null) {
                    timeCompare(openTime, closeTime);
                }


            }
        });
        picker.show();
    }

    private void initView() {
        mEdStoreName = findViewById(R.id.ed_store_name);
        mEdStoreName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 15) {
                    ToastUtils.showToast("不得超出15个文字");
                }
            }
        });
        mEdStoreUserName = findViewById(R.id.ed_store_user_name);
        mEdStoreUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 10) {
                    ToastUtils.showToast("不得超出10个文字");
                }
            }
        });
        mEdStoreUserPhone = findViewById(R.id.ed_store_user_phone);
        mEdStoreTypeName = findViewById(R.id.ed_store_type_name);
        mEdStorePlace = findViewById(R.id.ed_store_place);
        mEdStoreDetailedPlace = findViewById(R.id.ed_store_detailed_place);
        mEdStoreDetailedPlace.setMaxEms(30);
        mEdStoreDetailedPlace.setInputType(EditorInfo.TYPE_CLASS_TEXT);
        mEdStoreDetailedPlace.setImeOptions(EditorInfo.IME_ACTION_DONE);
        mEdStoreDetailedPlace.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    View view = getWindow().peekDecorView();
                    if (null != view) {
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                    if (areaCode != null) {
                        mBaiduMap.setMyLocationEnabled(false);
                        mCoder.geocode(new GeoCodeOption().city(cityName).address(mEdStoreDetailedPlace.getText().toString()));
                    } else {
                        Toast.makeText(ShopInformationActivity.this, "请先选择省市区", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
                return false;
            }
        });
        mEdStoreDetailedPlace.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 30) {
                    ToastUtils.showToast("不得超出30个文字");
                    return;
                }
                if (areaCode != null) {
                    mBaiduMap.setMyLocationEnabled(false);
                    mCoder.geocode(new GeoCodeOption().city(cityName).address(mEdStoreDetailedPlace.getText().toString()));
                } else {
                    Toast.makeText(ShopInformationActivity.this, "请先选择省市区", Toast.LENGTH_SHORT).show();
                }

            }
        });
        mEdStoreOpeanTime = findViewById(R.id.ed_store_open_time);
        mEdStoreCloseTime = findViewById(R.id.ed_store_close_time);
        mBtnNextInfo = findViewById(R.id.btn_next_info);
        mEdStorePlace.setOnClickListener(this);
        mEdStoreTypeName.setOnClickListener(this);
        mEdStoreOpeanTime.setOnClickListener(this);
        mEdStoreCloseTime.setOnClickListener(this);
        mBtnNextInfo.setOnClickListener(this);
        mMapView = findViewById(R.id.baidu_map);
        mBaiduMap = mMapView.getMap();
        mMapView.showZoomControls(false);
        locationBtn = findViewById(R.id.btn_location);
        locationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mEdStoreDetailedPlace.getText().toString())) {
                    Toast.makeText(ShopInformationActivity.this, "请填写详细地址!", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(ShopInformationActivity.this, MapActivity.class);
                    intent.putExtra("latitude", latitude);
                    intent.putExtra("longitude", longitude);
                    startActivityForResult(intent, MyApp.REQUEST_CODE);
                }
            }
        });
        returnImg = findViewById(R.id.title_back);
        returnImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == MyApp.RESULT_CODE && requestCode == MyApp.REQUEST_CODE) {
            double latitude = data.getDoubleExtra("latitude", 0.00);
            double longitude = data.getDoubleExtra("longitude", 0.00);
            String address = data.getStringExtra("address");
//            mEdStoreDetailedPlace.setText(address);
            shopInfo.putString("latitude", String.valueOf(latitude));
            shopInfo.putString("longitude", String.valueOf(longitude));
            shopInfo.commit();
            LatLng ll = new LatLng(latitude, longitude);
            MapStatus.Builder builder = new MapStatus.Builder();
            builder.target(ll).zoom(18.0f);
//            BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_shop);
//            OverlayOptions option = new MarkerOptions().position(ll).icon(bitmap);
//            mBaiduMap.addOverlay(option);
            mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ed_store_place:
                hideInPut();
                seleCity();
                break;
            case R.id.ed_store_open_time:
                timePicker1();
                break;
            case R.id.ed_store_close_time:
                timePicker2();
                break;
            case R.id.ed_store_type_name:
                hideInPut();
                getDis();
                break;
            case R.id.btn_next_info:
                if (TextUtils.isEmpty(mEdStoreName.getText().toString())) {
                    ToastUtils.showToast("请输入店铺名称");
                    return;
                }
                if (TextUtils.isEmpty(mEdStoreUserName.getText().toString())) {
                    ToastUtils.showToast("请输入店主姓名");
                    return;
                }
                if (TextUtils.isEmpty(mEdStoreUserPhone.getText().toString())) {
                    ToastUtils.showToast("请输入联系电话");
                    return;
                }
                if (TextUtils.isEmpty(mEdStoreDetailedPlace.getText().toString())) {
                    ToastUtils.showToast("请输入详细地址");
                    return;
                }
                if (!isMobileNO(mEdStoreUserPhone.getText().toString())) {
                    ToastUtils.showToast("请输入正确的手机号");
                    return;
                }
                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
                try {
                    //开始时间
                    Date date1 = dateFormat.parse(openTime);
                    //结束时间
                    Date date2 = dateFormat.parse(closeTime);
                    // 1 结束时间小于开始时间 2 开始时间与结束时间相同 3 结束时间大于开始时间
                    if (date2.getTime() < date1.getTime()) {
                        showPopup("休息时间不能小于营业时间");
                    } else if (date2.getTime() == date1.getTime()) {
                        showPopup("营业时间不能与休息时间相同");
                    } else if (date2.getTime() > date1.getTime()) {
                        shopInfo.putString("shop_name", mEdStoreName.getText().toString());
                        shopInfo.putString("shop_owner", mEdStoreUserName.getText().toString());
                        shopInfo.putString("shop_tel", mEdStoreUserPhone.getText().toString());
                        shopInfo.putString("shop_address", provinceName + cityName + areaName + mEdStoreDetailedPlace.getText().toString());
                        shopInfo.putInt("shop_kinds", kindsType);
                        shopInfo.putString("open_time", openTime);
                        shopInfo.putString("close_time", closeTime);
                        shopInfo.putString("latitude", String.valueOf(latitude));
                        shopInfo.putString("longitude", String.valueOf(longitude));
                        shopInfo.putString("area_code", areaCode);
                        shopInfo.commit();
                        startActivity(new Intent(ShopInformationActivity.this, ShopBusinessInformationActivity.class));
                    }
                } catch (Exception e) {
                }
                break;
            default:
                break;
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

    public boolean isMobileNO(String mobileNums) {
        /**
         * 判断字符串是否符合手机号码格式
         * 移动号段: 134,135,136,137,138,139,147,150,151,152,157,158,159,170,178,182,183,184,187,188
         * 联通号段: 130,131,132,145,155,156,170,171,175,176,185,186
         * 电信号段: 133,149,153,170,173,177,180,181,189
         * @param str
         * @return 待检测的字符串
         */
        String telRegex = "^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\\d{8}$";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobileNums)) {
            return false;
        } else {
            return mobileNums.matches(telRegex);
        }
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
        mBaiduMap.setMyLocationEnabled(false);
        mMapView = null;
        mCoder.destroy();
        super.onDestroy();
    }

    private void timeCompare(String startTime, String endTime) {
        int i = 0;
        //注意：传过来的时间格式必须要和这里填入的时间格式相同
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        try {
            Date date1 = dateFormat.parse(startTime);//开始时间
            Date date2 = dateFormat.parse(endTime);//结束时间
            // 1 结束时间小于开始时间 2 开始时间与结束时间相同 3 结束时间大于开始时间
            if (date2.getTime() < date1.getTime()) {
                showPopup("休息时间不能小于营业时间");
            } else if (date2.getTime() == date1.getTime()) {
                showPopup("营业时间不能与休息时间相同");
            } else if (date2.getTime() > date1.getTime()) {
                //结束时间大于开始时间
                i = 3;
            }
        } catch (Exception e) {

        }
    }

    private void showPopup(String title) {
        View view = LayoutInflater.from(this).inflate(R.layout.popup_title, null);
        TextView titleText = view.findViewById(R.id.popup_title_text);
        titleText.setText(title);
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels * 1 / 3;
        popupWindow = new PopupWindow(view, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        //popupWindow在弹窗的时候背景半透明
        final WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.5f;
        getWindow().setAttributes(params);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                params.alpha = 1.0f;
                getWindow().setAttributes(params);
            }
        });
    }
}
