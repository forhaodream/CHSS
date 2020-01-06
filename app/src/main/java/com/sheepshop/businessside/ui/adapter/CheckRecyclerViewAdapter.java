package com.sheepshop.businessside.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.sheepshop.businessside.R;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by CH
 * on 2019/11/6 14:16
 */
public class CheckRecyclerViewAdapter extends RecyclerView.Adapter<CheckRecyclerViewAdapter.MyViewHolder> implements View.OnClickListener {
    private Context context;
    private List<String> list;
    private List<Boolean> booleanlist = new ArrayList<>();

    public CheckRecyclerViewAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    public CheckRecyclerViewAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
        for (int i = 0; i < list.size(); i++) {
            //设置默认的显示
            booleanlist.add(false);
        }
    }

    public void addData(List<String> strings) {

        list.addAll(strings);
        for (int i = 0; i < strings.size(); i++) {
            booleanlist.add(false);
        }
        notifyDataSetChanged();
    }

    //更改集合内部存储的状态
    public void initCheck(boolean flag) {

        for (int i = 0; i < list.size(); i++) {
            //更改指定位置的数据
            booleanlist.set(i, flag);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_add_voucher, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.tv.setText(list.get(position));

        holder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //用集合保存当前的状态
                booleanlist.set(position, isChecked);
            }
        });

        holder.cb.setChecked(booleanlist.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //清空所有数据
    public void deleteAllData() {

        list.clear();
        booleanlist.clear();
        notifyDataSetChanged();

    }

    //删除选中的数据
    public void deletingData() {

        int y = 0;
        for (int i = 0; i < list.size(); i++) {
            if (booleanlist.get(i) != null && booleanlist.get(i)) {
                list.remove(i);
                booleanlist.remove(i);
                y++;
                i--;
            }
        }
        notifyDataSetChanged();

        if (y == 0) {
            Toast.makeText(context, "没有选中的要删除的数据", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public void onClick(View v) {

    }


    /**
     * ViewHolder的类，用于缓存控件
     */
    class MyViewHolder extends RecyclerView.ViewHolder {

        CheckBox cb;
        TextView tv;

        public MyViewHolder(View view) {
            super(view);
            cb = view.findViewById(R.id.item_add_voucher_check);

        }

    }
}
