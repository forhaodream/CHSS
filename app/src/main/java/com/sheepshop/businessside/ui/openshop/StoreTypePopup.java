package com.sheepshop.businessside.ui.openshop;

import android.content.Context;
import android.widget.TextView;

import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.util.XPopupUtils;
import com.sheepshop.businessside.R;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ch.chtool.utils.RecyclerAdapter;
import ch.chtool.utils.RecyclerViewHolder;

/**
 * Created by CH
 * on 2019/11/27 11:43
 */
public class StoreTypePopup extends BottomPopupView {
    RecyclerView recyclerView;
    private ArrayList<String> data;
    private Context c;
    private List<String> lists;

    public StoreTypePopup(Context context, List<String> lists) {
        super(context);
        this.c = context;
        this.lists = lists;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.custom_bottom_popup;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        recyclerView = findViewById(R.id.recyclerView);
        RecyclerAdapter adapter = new RecyclerAdapter<String>(c, R.layout.item_store_type, lists) {
            @Override
            public void convert(RecyclerViewHolder holder, String itemData, int position) {
                TextView title = holder.getView(R.id.title);
                title.setText(itemData);
            }


        };
        LinearLayoutManager manager = new LinearLayoutManager(c);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

    //完全可见执行
    @Override
    protected void onShow() {
        super.onShow();
    }

    //完全消失执行
    @Override
    protected void onDismiss() {

    }

    @Override
    protected int getMaxHeight() {
        return (int) (XPopupUtils.getWindowHeight(getContext()) * .30f);
    }
}