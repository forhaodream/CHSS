package com.sheepshop.businessside.ui.myshop;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sheepshop.businessside.MyApp;
import com.sheepshop.businessside.R;
import com.sheepshop.businessside.entity.ExchangeListBean;
import com.sheepshop.businessside.okhttp.BaseResp;
import com.sheepshop.businessside.okhttp.HttpUtils;
import com.sheepshop.businessside.okhttp.SSHCallBackNew;
import com.sheepshop.businessside.service.ApiService;
import com.sheepshop.businessside.tool.ToastUtils;
import com.sheepshop.businessside.ui.adapter.HasRefundAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by CH
 * on 2019/11/7 10:27
 */
public class HasRefundFragment extends Fragment {
    private View mView;
    private ExpandableListView ex;
    //声明一个ExpandableListView 用的数据源
    private List<ExpandInfo> list = new ArrayList<ExpandInfo>();
    private HasRefundAdapter adapter;
    private List<ExchangeListBean.ListBean> mListBeans;
    private List<ExchangeListBean.ListBean> mListPageBeans;
    private SmartRefreshLayout mRefreshLayout;
    private int itemId = 1;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_has_change, null);
        ex = mView.findViewById(R.id.expand);
        mRefreshLayout = mView.findViewById(R.id.refresh_layout);
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()));
        mRefreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()));
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh(1000);
                getExchangeList();
                itemId = 1;
            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishLoadMore();
                itemId += 1;

            }
        });
        getExchangeList();
        //初始化数据源

        return mView;
    }

    private void getExchangeList() {
        Call<BaseResp<ExchangeListBean>> call = HttpUtils.getInstance().getApiService(ApiService.class).searchExchangeList(1, 10, String.valueOf(MyApp.getShopInfoBean().getShopId()), "0");
        call.enqueue(new SSHCallBackNew<BaseResp<ExchangeListBean>>() {
            @Override
            public void onSuccess(Response<BaseResp<ExchangeListBean>> response) throws Exception {
                ExchangeListBean bean = response.body().getData();
                mListBeans = response.body().getData().getList();
                Log.d("1111112", mListBeans.size() + "");
                if (mListBeans != null) {
                    initList(mListBeans);
                } else {
                    ToastUtils.showToast("数据异常!");
                }

            }

            @Override
            public void onFail(String message) {

            }
        });
    }

    private void getExchangePageList(int itemId) {
        Call<BaseResp<ExchangeListBean>> call = HttpUtils.getInstance().getApiService(ApiService.class).searchExchangeList(itemId, 1, String.valueOf(MyApp.getShopInfoBean().getShopId()), "0");
        call.enqueue(new SSHCallBackNew<BaseResp<ExchangeListBean>>() {
            @Override
            public void onSuccess(Response<BaseResp<ExchangeListBean>> response) throws Exception {
                ExchangeListBean bean = response.body().getData();
                mListBeans = response.body().getData().getList();
                if (mListBeans != null) {
                    if (mListBeans.size() > 0) {
                        mListBeans.addAll(mListBeans);
                        adapter.notifyDataSetChanged();
                    } else {
                        ToastUtils.showToast("没有更多数据!");
                    }
                } else {
                    ToastUtils.showToast("没有更多数据!");
                }

            }

            @Override
            public void onFail(String message) {

            }
        });
    }

    private void initList(List<ExchangeListBean.ListBean> lists) {
        adapter = new HasRefundAdapter(getActivity(), lists);
        ex.setAdapter(adapter);
        int groupCount = ex.getCount();
        for (int i = 0; i < groupCount; i++) {
            ex.expandGroup(i);
        }
        //ExpandableListView子条目点击事件
        ex.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @SuppressLint("WrongConstant")
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                return false;
            }
        });
    }
}
