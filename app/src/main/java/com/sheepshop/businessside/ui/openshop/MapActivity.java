package com.sheepshop.businessside.ui.openshop;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.sheepshop.businessside.MyApp;
import com.sheepshop.businessside.R;

/**
 * Created by CH
 * on 2019/11/11 19:35
 */
public class MapActivity extends Activity implements View.OnClickListener, OnGetGeoCoderResultListener {

    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private ImageView mTitleBack;
    private TextView mMapTrue;
    private TextView mMapDetail;
    private GeoCoder mCoder = null;
    private double latitude;
    private double longitude;
    private String address;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        initView();
        // 地图初始化
        mMapView = findViewById(R.id.map);
        mBaiduMap = mMapView.getMap();
        mMapView.showZoomControls(false);
        mCoder = GeoCoder.newInstance();
        mCoder.setOnGetGeoCodeResultListener(this);
        Intent intent = getIntent();
        latitude = intent.getDoubleExtra("latitude", 0);
        longitude = intent.getDoubleExtra("longitude", 0);
        LatLng ll = new LatLng(latitude, longitude);
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(ll).zoom(18.0f);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        mCoder.reverseGeoCode(new ReverseGeoCodeOption().location(ll).radius(500));
        //设置地图单击事件监听
        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus) {

            }

            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus, int i) {

            }

            @Override
            public void onMapStatusChange(MapStatus mapStatus) {

            }

            @Override
            public void onMapStatusChangeFinish(MapStatus mapStatus) {
                LatLng latLng = mapStatus.target;
                latitude = latLng.latitude;
                longitude = latLng.longitude;
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(latLng);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                mCoder.reverseGeoCode(new ReverseGeoCodeOption().location(latLng).radius(500));
            }
        });

    }

    private void initView() {
        mTitleBack = findViewById(R.id.title_back);
        mMapTrue = findViewById(R.id.map_true);
        mMapDetail = findViewById(R.id.map_detail);
        mTitleBack.setOnClickListener(this);
        mMapTrue.setOnClickListener(this);
    }

    @Override
    public void onClick(View var1) {
        switch (var1.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.map_true:
                Intent intent = new Intent();
                intent.putExtra("latitude", latitude);
                intent.putExtra("longitude", longitude);
                intent.putExtra("address", address);
                setResult(MyApp.RESULT_CODE, intent);
                finish();
                break;
        }
    }


    @Override
    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

    }

    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
        if (reverseGeoCodeResult == null || reverseGeoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
            return;
        } else {
            //详细地址
            address = reverseGeoCodeResult.getAddress();
            mMapDetail.setText(address);
        }
    }
}
