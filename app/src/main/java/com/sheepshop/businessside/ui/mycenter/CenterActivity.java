package com.sheepshop.businessside.ui.mycenter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.next.easynavigation.view.EasyNavigationBar;
import com.sheepshop.businessside.R;
import com.sheepshop.businessside.ui.myshop.EvaluateFragment;

import java.util.ArrayList;
import java.util.List;

public class CenterActivity extends AppCompatActivity implements View.OnClickListener {
    private String[] tabText = {"订单处理", "订单管理", "我的"};
    //未选中icon
    private int[] normalIcon = {R.mipmap.order_gray, R.mipmap.manager_gray, R.mipmap.mine_gray};
    //选中时icon
    private int[] selectIcon = {R.mipmap.order_red, R.mipmap.manager_red, R.mipmap.mine_red};

    private List<Fragment> fragments = new ArrayList<>();
    private ImageView mTitleBack;
    private TextView mCenterTitle;
    private ImageView mTitleSetting;
    private ImageView mTitleMsg;
    private RelativeLayout mTitle;
    private EasyNavigationBar mNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_center);
        initView();

    }

    @SuppressLint("CutPasteId")
    private void initView() {
        EasyNavigationBar navigationBar = findViewById(R.id.navigationBar);
        fragments.add(new CenterMineFragment());
        fragments.add(new CenterMineFragment());
        fragments.add(new CenterMineFragment());
        mTitleBack = findViewById(R.id.title_back);
        mCenterTitle = findViewById(R.id.center_title);
        mTitleSetting = findViewById(R.id.title_setting);
        mTitleSetting.setVisibility(View.GONE);
        mTitleMsg = findViewById(R.id.title_msg);
        mTitle = findViewById(R.id.title);
        mNavigationBar = findViewById(R.id.navigationBar);
        mTitleBack.setOnClickListener(this);
        mTitleSetting.setOnClickListener(this);
        mTitleMsg.setOnClickListener(this);
        navigationBar.titleItems(tabText)
                .normalIconItems(normalIcon)
                .selectIconItems(selectIcon)
                .fragmentList(fragments)
                .canScroll(false)
                .fragmentManager(getSupportFragmentManager())
                .onTabClickListener(new EasyNavigationBar.OnTabClickListener() {
                    @Override
                    public boolean onTabClickEvent(View view, int position) {
                        switch (position) {
                            case 0:
                                mCenterTitle.setText("订单处理");
                                mTitleSetting.setVisibility(View.GONE);
                                break;
                            case 1:
                                mCenterTitle.setText("订单管理");
                                mTitleSetting.setVisibility(View.GONE);
                                break;
                            case 2:
                                mCenterTitle.setText("我的");
                                mTitleSetting.setVisibility(View.VISIBLE);
                                break;
                            default:
                                break;
                        }
                        return false;
                    }
                })
                .build();
    }

    @Override
    public void onClick(View var1) {
        switch (var1.getId()) {
            case R.id.title_back:
                break;
            case R.id.title_setting:
                break;
            case R.id.title_msg:
                break;
        }
    }
}
