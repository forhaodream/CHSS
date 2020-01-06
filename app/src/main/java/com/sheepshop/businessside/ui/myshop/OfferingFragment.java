package com.sheepshop.businessside.ui.myshop;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnCancelListener;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sheepshop.businessside.MyApp;
import com.sheepshop.businessside.R;
import com.sheepshop.businessside.entity.CouponListBean;
import com.sheepshop.businessside.entity.OfferingBean;
import com.sheepshop.businessside.okhttp.BaseResp;
import com.sheepshop.businessside.okhttp.HttpUtils;
import com.sheepshop.businessside.okhttp.SSHCallBackNew;
import com.sheepshop.businessside.service.ApiService;
import com.sheepshop.businessside.tool.ToastUtils;
import com.sheepshop.businessside.weight.ButtonUtils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ch.chtool.utils.RecyclerAdapter;
import ch.chtool.utils.RecyclerViewHolder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by CH
 * on 2019/11/6 15:52
 */
public class OfferingFragment extends Fragment {
    private View mView;
    private RecyclerView mFragmentOfferingRecycler;
    private RecyclerAdapter mAdapter;
    private List<CouponListBean.ListBean> mCouponListBean;
    private List<CouponListBean.ListBean> mCouponPageListBean;
    private CheckBox mCheckBox;
    private int itemId = 1;
    private SmartRefreshLayout mRefreshLayout;
    private UpdateUI mUpdateUI;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mUpdateUI = (UpdateUI) getActivity();
    }

    public interface UpdateUI {
        void update(String value);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_offering, null);
        mRefreshLayout = mView.findViewById(R.id.refresh_layout);
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()));
        mRefreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()));
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh(1000);
                getList();
                itemId = 1;
            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishLoadMore();
                itemId += 1;
                getPageList(itemId);
            }
        });
        getList();

        return mView;
    }

    private void getList() {
        Call<BaseResp<CouponListBean>> call = HttpUtils.getInstance().getApiService(ApiService.class).searchCouponList("1", 1, 10, String.valueOf(MyApp.getShopInfoBean().getShopId()), "0");
        call.enqueue(new SSHCallBackNew<BaseResp<CouponListBean>>() {
            @Override
            public void onSuccess(Response<BaseResp<CouponListBean>> response) throws Exception {
                mCouponListBean = response.body().getData().getList();
                Log.d("1111111111", String.valueOf(MyApp.getShopInfoBean().getShopId()));
                if (mCouponListBean != null) {
                    initView(mCouponListBean);
                } else {
                    ToastUtils.showToast("没有数据!");
                }
            }

            @Override
            public void onFail(String message) {

            }
        });
    }

    private void getPageList(int itemId) {
        Call<BaseResp<CouponListBean>> call = HttpUtils.getInstance().getApiService(ApiService.class).searchCouponList("1", itemId, 10, String.valueOf(MyApp.getShopInfoBean().getShopId()), "0");
        call.enqueue(new SSHCallBackNew<BaseResp<CouponListBean>>() {
            @Override
            public void onSuccess(Response<BaseResp<CouponListBean>> response) throws Exception {
                mCouponPageListBean = response.body().getData().getList();
                if (mCouponPageListBean != null) {
                    if (mCouponPageListBean.size() > 0) {
                        mCouponListBean.addAll(mCouponPageListBean);
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

            }
        });
    }

    private void initView(List<CouponListBean.ListBean> listBeans) {
        mFragmentOfferingRecycler = mView.findViewById(R.id.fragment_offering_recycler);
        mAdapter = new RecyclerAdapter<CouponListBean.ListBean>(getActivity(), R.layout.item_voucher_offering, listBeans) {
            @Override
            public void convert(RecyclerViewHolder holder, CouponListBean.ListBean itemData, int position) {
                /**
                 * couponId 代金券ID
                 * couponExchangeNum 成功兑换张数（代金券管理列表使用） isOpen 代金券开关 0开1关（代金券管理列表使用） totalPage 总页数
                 * totalNum 发行中总条数 totalNumLast 已过期总条数
                 */
                TextView titleText = holder.getView(R.id.my_shop_title);
                titleText.setText("成功兑换" + itemData.getCouponExchangeNum() + "张");
                TextView nameText = holder.getView(R.id.item_add_voucher_title);
                nameText.setText(itemData.getCouponName());
                TextView subText = holder.getView(R.id.item_add_voucher_sub);
                TextView totalText = holder.getView(R.id.can_change_total);
                TextView timeText = holder.getView(R.id.item_add_voucher_time);
                timeText.setText("有效期: " + itemData.getCouponStartTime() + "-" + itemData.getCouponEndTime());
                totalText.setText("每人每天限使用个数" + itemData.getCouponUseNum() + "个");
                ImageView headImg = holder.getView(R.id.item_add_voucher_head);
                Glide.with(getActivity()).load(itemData.getCouponPic()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(headImg);
                mCheckBox = holder.getView(R.id.my_shop_switch);
                if ("0".equals(itemData.getIsOpen())) {
                    mCheckBox.setChecked(true);
                } else {
                    mCheckBox.setChecked(false);
                }
                mCheckBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!ButtonUtils.isFastDoubleClick(R.id.my_shop_switch)) {
                            if ("0".equals(itemData.getIsOpen())) {
                                new XPopup.Builder(getActivity()).dismissOnTouchOutside(false).asConfirm("提示", "您确定关闭代金券使用?",
                                        new OnConfirmListener() {
                                            @Override
                                            public void onConfirm() {
                                                switchState(position, itemData.getCouponId(), String.valueOf(MyApp.getShopInfoBean().getShopId()), "1");
                                            }
                                        }, new OnCancelListener() {
                                            @Override
                                            public void onCancel() {
                                                mCheckBox.setChecked(true);
                                                mAdapter.notifyItemChanged(position);
                                            }
                                        }).show();
                            } else {
                                new XPopup.Builder(getActivity()).dismissOnTouchOutside(false).asConfirm("提示", "您确定开启代金券使用?",
                                        new OnConfirmListener() {
                                            @Override
                                            public void onConfirm() {
                                                switchState(position, itemData.getCouponId(), String.valueOf(MyApp.getShopInfoBean().getShopId()), "0");
                                            }
                                        }, new OnCancelListener() {
                                            @Override
                                            public void onCancel() {
                                                mCheckBox.setChecked(false);
                                                mAdapter.notifyItemChanged(position);
                                            }
                                        }).show();
                            }
                        }

                    }
                });
            }
        };
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mFragmentOfferingRecycler.setLayoutManager(manager);
        mFragmentOfferingRecycler.setAdapter(mAdapter);
    }

    private void switchState(int pos, String couponId, String businessId, String isOpen) {
        Call<OfferingBean> call = HttpUtils.getInstance().getApiService(ApiService.class).modifyCouponState(couponId, businessId, isOpen);
        call.enqueue(new Callback<OfferingBean>() {
            @Override
            public void onResponse(Call<OfferingBean> call, Response<OfferingBean> response) {
                String code = response.body().getCode();
                Log.d("OfferingFragment-1-", couponId);
                Log.d("OfferingFragment-1-", businessId);
                Log.d("OfferingFragment-1-", "pos: " + pos);
                Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                if ("0".equals(code)) {
                    ToastUtils.showToast("修改成功!");
                    mUpdateUI.update("刷新");
                } else {
                    ToastUtils.showToast("修改失败!");
                }
//                if ("0".equals(code)) {
//                    mCouponListBean.get(pos).setCheck(true);
//                    initView(mCouponListBean);
//                    ToastUtils.showToast("修改成功!");
////                    mCheckBox.setChecked(true);
//                } else {
////                    mCheckBox.setChecked(false);
//                    mCouponListBean.get(pos).setCheck(false);
//                    initView(mCouponListBean);
//                    ToastUtils.showToast("修改成功!");
//                }
            }

            @Override
            public void onFailure(Call<OfferingBean> call, Throwable t) {
                Log.d("OfferingcouponId", couponId);
                Log.d("OfferingbusinessId", businessId);
            }
        });

    }
}
