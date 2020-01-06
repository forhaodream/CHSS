package com.sheepshop.businessside.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sheepshop.businessside.R;
import com.sheepshop.businessside.entity.PackageClassListBean;
import com.sheepshop.businessside.entity.PackageGoodsClassListBean;

import java.util.List;

/**
 * Created by CH
 * on 2019/12/21 11:31
 */
public class LeftAdapter extends BaseAdapter {
    private Context context;
    private List<PackageGoodsClassListBean> mList;
    private int selectItem = 0;

    public LeftAdapter(Context context, List<PackageGoodsClassListBean> list) {
        this.context = context;
        this.mList = list;
    }


    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setSelectItem(int selectItem) {
        this.selectItem = selectItem;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_goods_left, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.title.setText(mList.get(position).getName() + " (" + mList.get(position).getNum() + ")");
        if (position == selectItem) {
            holder.rootView.setBackgroundResource(R.color.white);
            holder.view.setVisibility(View.VISIBLE);
            holder.title.setTextColor(context.getResources().getColor(R.color.black_3));
        } else {
            holder.rootView.setBackgroundResource(R.color.gray_f5);
            holder.view.setVisibility(View.GONE);
            holder.title.setTextColor(context.getResources().getColor(R.color.gray_80));
        }
        return convertView;
    }

    public static class ViewHolder {
        View rootView;
        public TextView title;
        public View view;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.title = rootView.findViewById(R.id.item_goods_left_title);
            this.view = rootView.findViewById(R.id.view);
        }
    }
}