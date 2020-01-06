package com.sheepshop.businessside.ui.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sheepshop.businessside.R;
import com.sheepshop.businessside.ui.bean.UserViewInfo;

/**
 * Created by CH
 * on 2019/12/16 14:38
 */
public class MyBaseQuickAdapter extends BaseQuickAdapter<UserViewInfo, BaseViewHolder> {
    public static final String TAG = "MyBaseQuickAdapter";
    private Context context;

    public MyBaseQuickAdapter(Context context) {
        super(R.layout.item_quick_image);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, UserViewInfo item) {
        final ImageView thumbView = helper.getView(R.id.iv);
        Glide.with(context)
                .load(item.getUrl())
                .error(R.mipmap.ic_iamge_zhanwei)
                .into(thumbView);
        helper.getView(R.id.iv).setTag(R.id.iv, item.getUrl());
    }
}