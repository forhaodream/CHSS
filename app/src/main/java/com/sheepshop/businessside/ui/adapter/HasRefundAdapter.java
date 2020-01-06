package com.sheepshop.businessside.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.makeramen.roundedimageview.RoundedImageView;
import com.sheepshop.businessside.R;
import com.sheepshop.businessside.entity.ExchangeListBean;
import com.sheepshop.businessside.ui.myshop.ExpandInfo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by CH
 * on 2019/11/7 10:29
 */
public class HasRefundAdapter extends BaseExpandableListAdapter {
    private List<ExchangeListBean.ListBean> list;
    private Context ctx;

    public HasRefundAdapter(Context ctx, List<ExchangeListBean.ListBean> list) {
        this.ctx = ctx;
        this.list = list;
    }

    //组数
    @Override
    public int getGroupCount() {
        return list.size();
    }

    //子数
    @Override
    public int getChildrenCount(int groupPosition) {
        return list.get(groupPosition).getCouponUserList().size();
    }

    //组的对象
    @Override
    public Object getGroup(int groupPosition) {
        return list.get(groupPosition);
    }

    //获得子的对象
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return list.get(groupPosition).getCouponUserList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return childPosition;
    }

    //当子条目ID相同时是否复用
    @Override
    public boolean hasStableIds() {
        return true;
    }

    //isExpanded:展开
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = View.inflate(ctx, R.layout.item_total, null);
        convertView.setClickable(true);
        TextView groupTv = convertView.findViewById(R.id.item_total_title);
//        groupTv.setText(list.get(groupPosition).getDateTime());
        Date today = new Date();
        String nowDate = dateFormater2.get().format(today);
        if (nowDate.equals(list.get(groupPosition).getDateTime())) {
            groupTv.setText("今天 (" + list.get(groupPosition).getCouponUserList().size() + ")");
        } else {
            groupTv.setText(list.get(groupPosition).getDateTime() + " (" + list.get(groupPosition).getCouponUserList().size() + ")");
        }
        return convertView;
    }

    //isLastChild:是否是该组最后子条目
    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(ctx, R.layout.item_voucher_refund, null);
            holder.img = convertView.findViewById(R.id.item_voucher_change_head);
            holder.child_tv = convertView.findViewById(R.id.item_voucher_change_title);
            holder.lineView = convertView.findViewById(R.id.view_line);
            holder.subText = convertView.findViewById(R.id.item_voucher_change_sub);
            holder.startTime = convertView.findViewById(R.id.item_voucher_change_exchange_time);
            holder.endTime = convertView.findViewById(R.id.item_voucher_change_refund_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Glide.with(ctx).load(list.get(groupPosition).getCouponUserList().get(childPosition).getUiHeadurl()).placeholder(R.mipmap.fl_wujieguo_icon).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(holder.img);
        holder.child_tv.setText(list.get(groupPosition).getCouponUserList().get(childPosition).getUiNickname());
        holder.subText.setText(list.get(groupPosition).getCouponUserList().get(childPosition).getCouponName());
        holder.startTime.setText("兑换时间：" + list.get(groupPosition).getCouponUserList().get(childPosition).getCouponStartTime());
        holder.endTime.setText("退券时间：" + list.get(groupPosition).getCouponUserList().get(childPosition).getCouponEndTime());

        if (isLastChild) {
            holder.lineView.setVisibility(View.GONE);
        } else {
            holder.lineView.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    //子条目是否可以被点击/选中/选择
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return true;
    }

    private class ViewHolder {
        private RoundedImageView img;
        private TextView child_tv;
        private View lineView;
        private TextView subText;
        private TextView startTime;
        private TextView endTime;
    }

    private ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };
}

