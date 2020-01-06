package com.sheepshop.businessside.ui.mycenter;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sheepshop.businessside.R;
import com.sheepshop.businessside.entity.GoodsListTypeBean;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ch.chtool.utils.RecyclerAdapter;
import ch.chtool.utils.RecyclerViewHolder;

/**
 * Created by CH
 * on 2019/12/21 14:36
 */
public class GoodsManageFragment extends Fragment {
    private View view;
    private RecyclerView mRecyclerView;
    private RecyclerAdapter mAdapter;
    private List<GoodsListTypeBean.GoodsListBean> mGoodsListBeans;
    private int type;
    private String odId;

    //    GoodsManageFragment(List<GoodsListTypeBean.GoodsListBean> lists) {
//        this.mGoodsListBeans = lists;
//    }
    GoodsManageFragment(String odId, int type) {
        this.type = type;
        this.odId = odId;
    }


    @SuppressLint("InflateParams")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_goods_manage, null);
        mRecyclerView = view.findViewById(R.id.right_recycler);
        initList();
        return view;
    }

    private void initList() {
        List<String> lists = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            lists.add("");
        }

    }


}
