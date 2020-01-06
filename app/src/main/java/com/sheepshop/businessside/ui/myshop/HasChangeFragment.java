package com.sheepshop.businessside.ui.myshop;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.makeramen.roundedimageview.RoundedImageView;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by CH
 * on 2019/11/7 10:08
 */
public class HasChangeFragment extends Fragment {
    private View mView;
    private ExpandableListView ex;
    //声明一个ExpandableListView 用的数据源
    private List<ExpandInfo> list = new ArrayList<ExpandInfo>();
    private HasChangeAdapter adapter;
    private List<ExchangeListBean.ListBean> mListBeans;
    private List<ExchangeListBean.ListBean> mListPageBeans;
    private SmartRefreshLayout mRefreshLayout;
    private int itemId = 1;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_has_change, null);
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
        ex = mView.findViewById(R.id.expand);
        getExchangeList();
        return mView;
    }


    private void getExchangeList() {
        Call<BaseResp<ExchangeListBean>> call = HttpUtils.getInstance().getApiService(ApiService.class).searchExchangeList(1, 10, String.valueOf(MyApp.getShopInfoBean().getShopId()), "1");
        call.enqueue(new SSHCallBackNew<BaseResp<ExchangeListBean>>() {
            @Override
            public void onSuccess(Response<BaseResp<ExchangeListBean>> response) throws Exception {
                mListBeans = response.body().getData().getList();
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

    private void getExchangePageList() {
        Call<BaseResp<ExchangeListBean>> call = HttpUtils.getInstance().getApiService(ApiService.class).searchExchangeList(1, 10, String.valueOf(MyApp.getShopInfoBean().getShopId()), "1");
        call.enqueue(new SSHCallBackNew<BaseResp<ExchangeListBean>>() {
            @Override
            public void onSuccess(Response<BaseResp<ExchangeListBean>> response) throws Exception {
                if (response.body() != null) {
                    ExchangeListBean bean = response.body().getData();
                }
                mListPageBeans = response.body().getData().getList();
                if (mListPageBeans != null) {
                    if (mListPageBeans.size() > 0) {
                        mListBeans.addAll(mListPageBeans);
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

    @Override
    public void onResume() {
        super.onResume();
        getExchangeList();
    }

    private void initList(List<ExchangeListBean.ListBean> lists) {
        adapter = new HasChangeAdapter(getActivity(), lists);
        ex.setAdapter(adapter);
        int groupCount = ex.getCount();

        for (int i = 0; i < groupCount; i++) {
            ex.expandGroup(i);
        }
    }


    class HasChangeAdapter extends BaseExpandableListAdapter {
        private List<ExchangeListBean.ListBean> list;
        private Context ctx;

        public HasChangeAdapter(Context ctx, List<ExchangeListBean.ListBean> list) {
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
            return childPosition;
        }

        //当子条目ID相同时是否复用
        @Override
        public boolean hasStableIds() {
            return true;
        }

        //isExpanded:展开
        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(ctx, R.layout.item_total, null);
            }
            TextView groupTv = convertView.findViewById(R.id.item_total_title);
            Date today = new Date();
            String nowDate = dateFormater2.get().format(today);
            if (nowDate.equals(list.get(groupPosition).getDateTime())) {
                groupTv.setText("今天 (" + list.get(groupPosition).getCouponUserList().size() + ")");
            } else {
                groupTv.setText(list.get(groupPosition).getDateTime() + " (" + list.get(groupPosition).getCouponUserList().size() + ")");
            }
            convertView.setClickable(true);
            return convertView;
        }


        //isLastChild:是否是该组最后子条目
        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(ctx, R.layout.item_voucher_change, null);
                holder.img = convertView.findViewById(R.id.item_voucher_change_head);
                holder.child_tv = convertView.findViewById(R.id.item_voucher_change_title);
                holder.timeTv = convertView.findViewById(R.id.item_voucher_change_time);
                holder.subTv = convertView.findViewById(R.id.item_voucher_change_sub);
                holder.confirm_btn = convertView.findViewById(R.id.item_voucher_change_confirm);
                holder.lineView = convertView.findViewById(R.id.view_line);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            Glide.with(getActivity()).load(list.get(groupPosition).getCouponUserList().get(childPosition).getUiHeadurl()).placeholder(R.mipmap.fl_wujieguo_icon).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(holder.img);

            holder.child_tv.setText(list.get(groupPosition).getCouponUserList().get(childPosition).getUiNickname());
            holder.timeTv.setText(list.get(groupPosition).getCouponUserList().get(childPosition).getCouponExchangeTime());
            holder.subTv.setText(list.get(groupPosition).getCouponUserList().get(childPosition).getCouponName());
            TextView groupTv = convertView.findViewById(R.id.item_total_title);
            Date today = new Date();
            String nowDate = dateFormater2.get().format(today);
            if (nowDate.equals(list.get(groupPosition).getDateTime())) {
                holder.confirm_btn.setText("去退券");
                holder.confirm_btn.setTextColor(getContext().getResources().getColor(R.color.white));
                holder.confirm_btn.setBackgroundResource(R.drawable.btn_login_green_theme);
                holder.confirm_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), ExchangeDetailActivity.class);
                        intent.putExtra("coupon_id", list.get(groupPosition).getCouponUserList().get(childPosition).getCouponId());
                        intent.putExtra("coupon_user_id", list.get(groupPosition).getCouponUserList().get(childPosition).getCouponUserId());
                        intent.putExtra("user_pic", list.get(groupPosition).getCouponUserList().get(childPosition).getUiHeadurl());
                        intent.putExtra("user_name", list.get(groupPosition).getCouponUserList().get(childPosition).getUiNickname());
                        intent.putExtra("time", list.get(groupPosition).getCouponUserList().get(childPosition).getCouponExchangeTime());
                        startActivity(intent);
                    }
                });
            } else {
                holder.confirm_btn.setText("不可退券");
                holder.confirm_btn.setTextColor(getContext().getResources().getColor(R.color.black));
                holder.confirm_btn.setBackgroundResource(R.color.white);
            }

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


        public class ViewHolder {
            private RoundedImageView img;
            private TextView child_tv;
            private TextView confirm_btn;
            private View lineView;
            private TextView timeTv;
            private TextView subTv;
        }


        /**
         * 将字符串转位日期类型
         *
         * @param sdate
         * @return
         */
        public Date toDate(String sdate) {
            try {
                return dateFormater.get().parse(sdate);
            } catch (ParseException e) {
                return null;
            }
        }


        public ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
            @Override
            protected SimpleDateFormat initialValue() {
                return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            }
        };


        public ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
            @Override
            protected SimpleDateFormat initialValue() {
                return new SimpleDateFormat("yyyy-MM-dd");
            }
        };


    }
}
