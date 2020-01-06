package com.sheepshop.businessside.ui.myshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.android.material.tabs.TabLayout;
import com.sheepshop.businessside.MyApp;
import com.sheepshop.businessside.R;
import com.sheepshop.businessside.entity.ExchangeListBean;
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
 * on 2019/11/7 09:58
 */
public class ChangeListActivity extends AppCompatActivity {
    private ImageView mTitleBack;
    private RelativeLayout mTitle;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<Fragment> mFragmentList;
    private TabLayout tab;
    private ViewPager pager;
    private String titleOne = "已兑换";
    private String titleTwo = "已退券";
    private String[] titles = {titleOne, titleTwo};
    private MyAdapter adapter;
    private ExchangeListBean mExchangeListBean;
    private List<ExchangeListBean.ListBean> hasChangeListBean;
    private List<ExchangeListBean.ListBean> hasRefundListBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_list);
        pager = findViewById(R.id.viewPager);
        tab = findViewById(R.id.tabLayout);
        mTitleBack = findViewById(R.id.title_back);
        mTitle = findViewById(R.id.title);
        mTabLayout = findViewById(R.id.tabLayout);
        mViewPager = findViewById(R.id.viewPager);
        mTitleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //页面，数据源
        mFragmentList = new ArrayList<>();
        mFragmentList.add(new HasChangeFragment());
        mFragmentList.add(new HasRefundFragment());

        getExchangeList();
        Intent intent = getIntent();
        String title = intent.getStringExtra("title_name");
        int pagers = intent.getIntExtra("pager", 0);
        pager.setCurrentItem(pagers);
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

    private void getExchangeList() {
        Call<BaseResp<ExchangeListBean>> call = HttpUtils.getInstance().getApiService(ApiService.class).searchExchangeList(1, 10, String.valueOf(MyApp.getShopInfoBean().getShopId()), "1");
        //String.valueOf(MyApp.getShopInfoBean().getShopId())
        call.enqueue(new SSHCallBackNew<BaseResp<ExchangeListBean>>() {
            @Override
            public void onSuccess(Response<BaseResp<ExchangeListBean>> response) throws Exception {
                ExchangeListBean bean = response.body().getData();
                titleOne = "已兑换 (" + bean.getExchangeNum() + ")";
                titleTwo = "已退券 (" + bean.getIsBackNum() + ")";
                titles = new String[]{titleOne, titleTwo};
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
    protected void onResume() {
        super.onResume();
        getExchangeList();
    }
}
