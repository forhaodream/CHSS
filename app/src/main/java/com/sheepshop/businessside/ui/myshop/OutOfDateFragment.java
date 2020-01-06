package com.sheepshop.businessside.ui.myshop;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sheepshop.businessside.MyApp;
import com.sheepshop.businessside.R;
import com.sheepshop.businessside.entity.CouponListBean;
import com.sheepshop.businessside.okhttp.BaseResp;
import com.sheepshop.businessside.okhttp.HttpUtils;
import com.sheepshop.businessside.okhttp.SSHCallBackNew;
import com.sheepshop.businessside.service.ApiService;
import com.sheepshop.businessside.tool.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ch.chtool.utils.RecyclerAdapter;
import ch.chtool.utils.RecyclerViewHolder;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by CH
 * on 2019/11/6 15:52
 */
public class OutOfDateFragment extends Fragment {
    private View mView;
    private RecyclerView mFragmentOfferingRecycler;
    private RecyclerAdapter mAdapter;
    private List<CouponListBean.ListBean> mCouponListBean;
    private List<CouponListBean.ListBean> mCouponListPageBean;
    private SmartRefreshLayout mRefreshLayout;
    private int itemId = 1;
    private int bdId;
    private SharedPreferences readInfo;
    private SharedPreferences.Editor editor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_outofdate, null);
        initView();
        return mView;
    }

    private void initView() {
        readInfo = getActivity().getSharedPreferences("user_npt", Context.MODE_PRIVATE);
        editor = readInfo.edit();
        bdId = readInfo.getInt("bdId", 0);
        getOutDateCouponList();
        mRefreshLayout = mView.findViewById(R.id.refresh_layout);
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()));
        mRefreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()));
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh(1000);
                getOutDateCouponList();
                itemId = 1;
            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishLoadMore();
                itemId += 1;
                getOutDateCouponPageList(itemId);
            }
        });
    }

    private void initList(List<CouponListBean.ListBean> listBeans) {
        mFragmentOfferingRecycler = mView.findViewById(R.id.fragment_offering_recycler);
        mAdapter = new RecyclerAdapter<CouponListBean.ListBean>(getActivity(), R.layout.item_voucher_outofdate, listBeans) {
            @Override
            public void convert(RecyclerViewHolder holder, CouponListBean.ListBean itemData, int position) {
                TextView titleText = holder.getView(R.id.my_shop_title);
                TextView nameText = holder.getView(R.id.item_add_voucher_title);
                TextView totalText = holder.getView(R.id.can_change_total);
                TextView timeText = holder.getView(R.id.item_add_voucher_time);
                ImageView headImg = holder.getView(R.id.item_add_voucher_head);
                titleText.setText("兑换数量" + itemData.getCouponExchangeNum() + "张");
                nameText.setText(itemData.getCouponName());
                timeText.setText("有效期: " + itemData.getCouponStartTime() + "-" + itemData.getCouponEndTime());
                totalText.setText("每人每天限使用个数" + itemData.getCouponUseNum() + "个");
                Glide.with(getActivity()).load(itemData.getCouponPic()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(headImg);
            }
        };
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mFragmentOfferingRecycler.setLayoutManager(manager);
        mFragmentOfferingRecycler.setAdapter(mAdapter);
    }

    private void getOutDateCouponList() {
        Call<BaseResp<CouponListBean>> call = HttpUtils.getInstance().getApiService(ApiService.class).searchCouponList("1", 1, 10, String.valueOf(MyApp.getShopInfoBean().getShopId()), "1");
        call.enqueue(new SSHCallBackNew<BaseResp<CouponListBean>>() {
            @Override
            public void onSuccess(Response<BaseResp<CouponListBean>> response) throws Exception {
                mCouponListBean = response.body().getData().getList();
                if (mCouponListBean != null) {
                    initList(mCouponListBean);
                } else {
                    ToastUtils.showToast("没有数据!");
                }
            }

            @Override
            public void onFail(String message) {
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getOutDateCouponPageList(int itemId) {
        Call<BaseResp<CouponListBean>> call = HttpUtils.getInstance().getApiService(ApiService.class).searchCouponList("1", itemId, 10, String.valueOf(MyApp.getShopInfoBean().getShopId()), "1");
        call.enqueue(new SSHCallBackNew<BaseResp<CouponListBean>>() {
            @Override
            public void onSuccess(Response<BaseResp<CouponListBean>> response) throws Exception {
                mCouponListPageBean = response.body().getData().getList();
                if (mCouponListPageBean != null) {
                    if (mCouponListPageBean.size() > 0) {
                        mCouponListBean.addAll(mCouponListPageBean);
                        mAdapter.notifyDataSetChanged();
                    } else {
                        ToastUtils.showToast("没有更多数据!");
                    }
                } else {
                    ToastUtils.showToast("没有更多数据!");
                }
            }

            @Override
            public void onFail(String message) {
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
