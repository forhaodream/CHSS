package com.sheepshop.businessside.ui.myshop;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.android.material.tabs.TabLayout;
import com.sheepshop.businessside.MyApp;
import com.sheepshop.businessside.R;
import com.sheepshop.businessside.entity.CouponListBean;
import com.sheepshop.businessside.okhttp.BaseResp;
import com.sheepshop.businessside.okhttp.HttpUtils;
import com.sheepshop.businessside.okhttp.SSHCallBackNew;
import com.sheepshop.businessside.service.ApiService;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by CH
 * on 2019/11/6 15:40
 */
public class ManagerVoucherActivity extends AppCompatActivity implements View.OnClickListener, OfferingFragment.UpdateUI {
    private ImageView mTitleBack;
    private RelativeLayout mTitle;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<Fragment> mFragmentList;
    private TabLayout tab;
    private ViewPager pager;
    private MyAdapter adapter;
    private List<CouponListBean.ListBean> mOfferingList;
    private CouponListBean mCouponListBean;
    private List<CouponListBean.ListBean> mOutDateList;
    private String titleOne = "发行中";
    private String titleTwo = "已过期";
    private String[] titles = {titleOne, titleTwo};
    private OfferingFragment offeringFragment = new OfferingFragment();
    private OutOfDateFragment outOfDateFragment = new OutOfDateFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_voucher);
        pager = findViewById(R.id.viewPager);
        tab = findViewById(R.id.tabLayout);
        //页面，数据源
        mFragmentList = new ArrayList<>();
        mFragmentList.add(offeringFragment);
        mFragmentList.add(outOfDateFragment);
        //ViewPager的适配器
        adapter = new MyAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        //绑定
        tab.setupWithViewPager(pager);
        initView();
    }


    public void initView() {
        mTitleBack = findViewById(R.id.title_back);
        mTitle = findViewById(R.id.title);
        mTabLayout = findViewById(R.id.tabLayout);
        mViewPager = findViewById(R.id.viewPager);
        mTitleBack.setOnClickListener(this);
        getOfferingCouponList();
        getOutDateCouponList();
    }

    private void getOfferingCouponList() {
        Call<BaseResp<CouponListBean>> call = HttpUtils.getInstance().getApiService(ApiService.class).searchCouponList("1", 1, 10, String.valueOf(MyApp.getShopInfoBean().getShopId()), "0");
        call.enqueue(new SSHCallBackNew<BaseResp<CouponListBean>>() {
            @Override
            public void onSuccess(Response<BaseResp<CouponListBean>> response) throws Exception {
                mCouponListBean = response.body().getData();
                mOfferingList = response.body().getData().getList();
                titleOne = "发行中 (" + mCouponListBean.getTotalNum() + ")";
                titles = new String[]{titleOne, titleTwo};
                //ViewPager的适配器
                adapter = new MyAdapter(getSupportFragmentManager());
                pager.setAdapter(adapter);
                //绑定
                tab.setupWithViewPager(pager);
            }

            @Override
            public void onFail(String message) {

            }
        });
    }

    private void getOutDateCouponList() {
        Call<BaseResp<CouponListBean>> call = HttpUtils.getInstance().getApiService(ApiService.class).searchCouponList("1", 1, 100, String.valueOf(MyApp.getShopInfoBean().getShopId()), "1");
        call.enqueue(new SSHCallBackNew<BaseResp<CouponListBean>>() {
            @Override
            public void onSuccess(Response<BaseResp<CouponListBean>> response) throws Exception {
                mCouponListBean = response.body().getData();
                mOutDateList = response.body().getData().getList();
                titleTwo = "已过期 (" + mCouponListBean.getTotalNumLast() + ")";
                titles = new String[]{titleOne, titleTwo};

                //ViewPager的适配器
                adapter = new MyAdapter(getSupportFragmentManager());
                pager.setAdapter(adapter);
                //绑定
                tab.setupWithViewPager(pager);
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
            default:
                break;
        }
    }

    @Override
    public void update(String value) {
//        if ("刷新".equals(value)) {
//            getOfferingCouponList();
//            getOutDateCouponList();
//        }
    }


    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        //重写这个方法，将设置每个Tab的标题
        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }
}
