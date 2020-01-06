package com.sheepshop.businessside.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sheepshop.businessside.R;
import com.sheepshop.businessside.utils.ImageUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * <pre>
 *     Created by ppW
 *     e-mail : wangpanpan05@163.com
 *     time   : 2017/08/13
 *     desc   : 发表评论图片的适配器
 *     version: 1.0   初始化
 *     params:
 *  <pre>
 */

public class PublishCommentAdapter extends BaseQuickAdapter<String, PublishCommentAdapter.PublishCommentViewHolder> {
    public PublishCommentAdapter(@Nullable List<String> data) {
        super(R.layout.item_activity_publish_comment_adapter, data);
    }

    @Override
    protected void convert(PublishCommentViewHolder holder, String item) {
        if (TextUtils.isEmpty(item)) {
            holder.ivItemActivityPublishCommentShowPic.setVisibility(View.GONE);
            holder.ivItemActivityPublishCommentDeletePic.setVisibility(View.GONE);
            holder.rlItemActivityPublishCommentAddPic.setVisibility(View.VISIBLE);
        } else {
            holder.ivItemActivityPublishCommentDeletePic.setVisibility(View.VISIBLE);
            holder.ivItemActivityPublishCommentShowPic.setVisibility(View.VISIBLE);
            holder.rlItemActivityPublishCommentAddPic.setVisibility(View.GONE);
            ImageUtils.loadNormalImage(mContext, item, holder.ivItemActivityPublishCommentShowPic);
        }
        // TODO: 2017/8/13 点击加号 则跳转到选择图片  否则就是查看图页面 或者是预览页面
        holder.addOnClickListener(R.id.iv_item_activity_publishComment_showPic)
                .addOnClickListener(R.id.iv_item_activity_publishComment_deletePic)
                .addOnClickListener(R.id.rl_item_activity_publishComment_addPic);
    }

    public static class PublishCommentViewHolder extends BaseViewHolder {
        @BindView(R.id.iv_item_activity_publishComment_showPic)
        ImageView ivItemActivityPublishCommentShowPic;
        @BindView(R.id.rl_item_activity_publishComment_addPic)
        RelativeLayout rlItemActivityPublishCommentAddPic;
        @BindView(R.id.iv_item_activity_publishComment_deletePic)
        ImageView ivItemActivityPublishCommentDeletePic;

        public PublishCommentViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

}
