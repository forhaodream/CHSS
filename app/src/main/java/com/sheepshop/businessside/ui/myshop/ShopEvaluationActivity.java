package com.sheepshop.businessside.ui.myshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.android.material.tabs.TabLayout;
import com.sheepshop.businessside.R;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

/**
 * Created by CH
 * on 2019/11/7 14:04
 */
public class ShopEvaluationActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mTitleBack;
    private RelativeLayout mTitle;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<Fragment> mFragmentList;
    private TabLayout tab;
    private ViewPager pager;
    private String[] titles = {"全部", "好评", "有图"};
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_evaluation);
        pager =   findViewById(R.id.viewPager);
        tab =  findViewById(R.id.tabLayout);
        //页面，数据源
        mFragmentList = new ArrayList<>();
        mFragmentList.add(new EvaluateFragment("0"));
        mFragmentList.add(new EvaluateFragment("1"));
        mFragmentList.add(new EvaluateFragment("2"));
        //ViewPager的适配器
        adapter = new MyAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        //绑定
        tab.setupWithViewPager(pager);
        Intent intent = getIntent();
        String title = intent.getStringExtra("title_name");
        int pagers = intent.getIntExtra("pager", 0);
        pager.setCurrentItem(pagers);
        initView();
    }

    public void initView() {

        mTitleBack = findViewById(R.id.title_back);
        mTitle = findViewById(R.id.title);
        mTabLayout = findViewById(R.id.tabLayout);
        mViewPager = findViewById(R.id.viewPager);
        mTitleBack.setOnClickListener(this);
    }


    @Override
    public void onClick(View var1) {
        switch (var1.getId()) {
            case R.id.title_back:
                finish();
                break;
        }
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
