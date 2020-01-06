package com.sheepshop.businessside.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.sheepshop.businessside.R;
import com.sheepshop.businessside.ui.myshop.ExpandInfo;

import java.util.List;

/**
 * Created by CH
 * on 2019/11/6 17:02
 */
public class HasChangeAdapter extends BaseExpandableListAdapter  {
    private List<ExpandInfo> list;
    private Context ctx;
    public ChildOnClick mChildOnClick;

    public HasChangeAdapter(Context ctx, List<ExpandInfo> list) {
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
        return list.get(groupPosition).childList.size();
    }

    //组的对象
    @Override
    public Object getGroup(int groupPosition) {
        return list.get(groupPosition);
    }

    //获得子的对象
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return list.get(groupPosition).childList.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
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
        TextView groupTv = convertView.findViewById(R.id.item_total_title);
        groupTv.setText(list.get(groupPosition).title);
//        //组是否展开   如果展开，组变颜色
//        if (isExpanded) {
//            groupTv.setTextColor(Color.BLUE);
//        } else {
//            groupTv.setTextColor(Color.BLACK);
//        }
        return convertView;
    }

    //isLastChild:是否是该组最后子条目
    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(ctx, R.layout.item_voucher_change, null);
            holder.img = convertView.findViewById(R.id.item_voucher_change_head);
            holder.child_tv = convertView.findViewById(R.id.item_voucher_change_title);
            holder.confirm_btn = convertView.findViewById(R.id.item_voucher_change_confirm);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.img.setImageResource(list.get(groupPosition).childList.get(childPosition).headID);
        holder.child_tv.setText(list.get(groupPosition).childList.get(childPosition).NickName);

        //如果是最后一条，最后最后一条变色
        if (isLastChild) {
            holder.child_tv.setTextColor(Color.GREEN);
        } else {
            holder.child_tv.setTextColor(Color.BLACK);
        }
        return convertView;
    }

    //子条目是否可以被点击/选中/选择
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return true;
    }




    public class ViewHolder {
        private RoundedImageView img;
        private TextView child_tv;
        private Button confirm_btn;

    }

    public void setItemClickListener(ChildOnClick clickListener) {
        this.mChildOnClick = clickListener;
    }

    public interface ChildOnClick {
        void onChildClickListener(View view, int position);
    }

}
