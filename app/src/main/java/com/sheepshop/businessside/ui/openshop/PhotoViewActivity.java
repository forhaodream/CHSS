package com.sheepshop.businessside.ui.openshop;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.sheepshop.businessside.R;
import com.sheepshop.businessside.adapter.NewsPhotoViewActivityAdapter;
import com.sheepshop.businessside.barlibrary.BarHide;
import com.sheepshop.businessside.barlibrary.ImmersionBar;
import com.sheepshop.businessside.utils.ScreenUtils;
import com.sheepshop.businessside.weight.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import ch.chtool.base.BaseActivity;

/**
 * <pre>
 *     Created by ppW
 *     e-mail : wangpanpan05@163.com
 *     time   : 2018/6/1 17:12
 *     desc   : 图集单个图片  可以横竖屏切换   version: 1.0     初始化
 *     params:  key:        value:
 *      mCurrentPosition = getIntent().getIntExtra("mCurrentPosition", 0);
 * mDataUrls = getIntent().getStringArrayListExtra("mDataUrls");
 *  <pre>
 */
public class PhotoViewActivity extends BaseActivity {

    @BindView(R.id.tv_pictureDetail_number)
    TextView tvPictureDetailNumber;
    @BindView(R.id.view_pager_photo)
    PhotoViewPager viewPagerPhoto;
    private NewsPhotoViewActivityAdapter mAdapter;
    private int mCurrentPosition;
    private ArrayList<String> mDataUrls;
    //    private Boolean[] mDataSaves;
    private ViewPager.OnPageChangeListener mOnPageChangeListener;
    private String uri;
    private Bundle bundle;


    @Override
    public int initLayout() {
        return R.layout.activity_atlas_details;
    }

    @Override
    public void initView() {

    }


    @Override
    public void initData() {

    }


    @Override
    protected void onDestroy() {
        viewPagerPhoto.removeOnPageChangeListener(mOnPageChangeListener);
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("mCurrentPosition", mCurrentPosition);
        outState.putStringArrayList("mDataUrls", mDataUrls);
    }

}
