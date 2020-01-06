package com.sheepshop.businessside.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sheepshop.businessside.R;
import com.sheepshop.businessside.entity.GoodsLabelBean;
import com.sheepshop.businessside.entity.PackageGoodsClassListBean;

import java.util.List;

/**
 * Created by CH
 * on 2020/1/4 16:33
 */
public class TopAdapter extends BaseAdapter {
    private Context context;
    private List<GoodsLabelBean> mList;
    private int selectItem = 0;

    public TopAdapter(Context context, List<GoodsLabelBean> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_tag_top, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setCornerRadius(60);
        drawable.setColor(Color.parseColor(mList.get(position).getBgColorCode()));
//        holder.title
        holder.layout.setBackground(drawable);
        holder.text.setTextColor(Color.parseColor(mList.get(position).getWordColorCode()));
        holder.text.setText(mList.get(position).getName());
        return convertView;
    }

    public static class ViewHolder {
        View rootView;
        TextView text;
        RelativeLayout layout;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            text = rootView.findViewById(R.id.item_tag_text);
            layout = rootView.findViewById(R.id.top_layout);
        }
    }
}
