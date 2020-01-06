package com.sheepshop.businessside.ui.myshop;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import com.sheepshop.businessside.BuildConfig;
import com.sheepshop.businessside.MyApp;
import com.sheepshop.businessside.R;
import com.sheepshop.businessside.entity.BeanRespReqEmpty;
import com.sheepshop.businessside.entity.CouponListBean;
import com.sheepshop.businessside.okhttp.BaseResp;
import com.sheepshop.businessside.okhttp.HttpUtils;
import com.sheepshop.businessside.okhttp.SSHCallBackNew;
import com.sheepshop.businessside.service.ApiService;
import com.sheepshop.businessside.tool.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ch.chtool.base.BaseActivity;
import ch.chtool.utils.RecyclerAdapter;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by CH
 * on 2019/11/6 10:58
 */
public class AddVoucherActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mTitleBack;
    private RelativeLayout mTitle;
    private TextView mAddVoucherTitle;
    private RecyclerView mAddVoucherRecycler;
    private Button mAddVoucherBtn;
    private RecyclerAdapter mAdaper;
    private CheckAdapter checkAdapter;
    private List<String> lists;
    private List<String> listDatas = new ArrayList<>();
    private List<CouponListBean.ListBean> mOutDateList;
    private List<CouponListBean.ListBean> mOutDatePageList;
    private List<String> idList = new ArrayList<>();
    private SmartRefreshLayout mRefreshLayout;
    private LinearLayoutManager linearLayoutManager;
    private int itemId = 1;
    private int bdId;
    private SharedPreferences readInfo;
    private SharedPreferences.Editor editor;

    @Override
    public int initLayout() {
        return R.layout.activity_add_voucher;
    }

    @Override
    public void initView() {
        linearLayoutManager = new LinearLayoutManager(AddVoucherActivity.this);
        mTitleBack = findViewById(R.id.title_back);
        mTitle = findViewById(R.id.title);
        mAddVoucherTitle = findViewById(R.id.add_voucher_title);
        mAddVoucherRecycler = findViewById(R.id.add_voucher_recycler);
        mAddVoucherBtn = findViewById(R.id.add_voucher_btn);
        mTitleBack.setOnClickListener(this);
        mAddVoucherBtn.setOnClickListener(this);
        mRefreshLayout = findViewById(R.id.refresh_layout);
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(this));
        mRefreshLayout.setRefreshFooter(new ClassicsFooter(this));
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                itemId = 1;
                refreshLayout.finishRefresh(1000);
                addVoucher();
            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                itemId++;
                addVoucherPage(itemId);
                refreshLayout.finishLoadMore();
            }
        });
//        mAddVoucherRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                switch (newState) {
//                    /*正在拖拽*/
//                    case RecyclerView.SCROLL_STATE_DRAGGING:
//                        mAddVoucherBtn.setVisibility(View.GONE);
//                        break;
//                    /*滑动停止*/
//                    case RecyclerView.SCROLL_STATE_IDLE:
//                        mAddVoucherBtn.setVisibility(View.VISIBLE);
//                        break;
//                    /*惯性滑动中*/
//                    case RecyclerView.SCROLL_STATE_SETTLING:
//                        mAddVoucherBtn.setVisibility(View.GONE);
//                        break;
//                }
//            }
//        });
    }

    @Override
    public void initData() {
        readInfo = getSharedPreferences("user_npt", Context.MODE_PRIVATE);
        editor = readInfo.edit();
        bdId = readInfo.getInt("bdId", 0);
        addVoucher();
    }

    public void removeDuplicate(List list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).equals(list.get(i))) {
                    list.remove(j);
                }
            }
        }
    }

    public List<String> getNewList(List<String> li) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < li.size(); i++) {
            String str = li.get(i);  //获取传入集合对象的每一个元素
            if (!list.contains(str)) {   //查看新集合中是否有指定的元素，如果没有则加入
                list.add(str);
            }
        }
        return list;  //返回集合
    }

    private void addVoucher() {
        Call<BaseResp<CouponListBean>> call = HttpUtils.getInstance().getApiService(ApiService.class).searchCouponList("0", 1, 10, String.valueOf(bdId), "");
        call.enqueue(new SSHCallBackNew<BaseResp<CouponListBean>>() {
            @Override
            public void onSuccess(Response<BaseResp<CouponListBean>> response) throws Exception {
                mOutDateList = response.body().getData().getList();
                Log.d("addVoucher", itemId + "");
                Log.d("addVoucher", mOutDateList.size() + "");
                checkAdapter = new CheckAdapter(AddVoucherActivity.this, mOutDateList);
                mAddVoucherRecycler.setLayoutManager(linearLayoutManager);
                mAddVoucherRecycler.setAdapter(checkAdapter);
                //子条目监听
                checkAdapter.setItemClickListener(new RecyclerViewOnItemClickListener() {
                    @Override
                    public void onItemClickListener(View view, int position) {
                        //设置选中的项
                        checkAdapter.setSelectItem(position);
                    }
                });
            }

            @Override
            public void onFail(String message) {
                Toast.makeText(AddVoucherActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addVoucherPage(int first) {
        Call<BaseResp<CouponListBean>> call = HttpUtils.getInstance().getApiService(ApiService.class).searchCouponList("0", first, 10, String.valueOf(bdId), "");
        call.enqueue(new SSHCallBackNew<BaseResp<CouponListBean>>() {
            @Override
            public void onSuccess(Response<BaseResp<CouponListBean>> response) throws Exception {
                mOutDatePageList = response.body().getData().getList();
                Log.d("addVoucher1", itemId + "");
                Log.d("addVoucher1", mOutDateList.size() + "");
                if (mOutDatePageList != null) {
                    if (mOutDatePageList.size() > 0) {
                        mOutDateList.addAll(mOutDatePageList);
                        mAdaper.notifyDataSetChanged();
                    } else {
                        ToastUtils.showToast("没有更多数据!");
                    }
                } else {
                    ToastUtils.showToast("没有更多数据!");
                }

            }

            @Override
            public void onFail(String message) {
                ToastUtils.showToast(message);
            }
        });
    }

    @Override
    public void onClick(View var1) {
        switch (var1.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.add_voucher_btn:
                if (idList.size() == 0) {
                    Toast.makeText(this, "请选择代金券", Toast.LENGTH_SHORT).show();
                    return;
                }
                createCoupon(TextUtils.join(", ", getNewList(idList)));
                break;
        }
    }

    private void createCoupon(String couponId) {
        if (BuildConfig.DEBUG) Log.d("AddVoucherActivity", couponId);
        Call<BaseResp<BeanRespReqEmpty>> call = HttpUtils.getInstance().getApiService(ApiService.class).createCoupon(couponId, String.valueOf(MyApp.getShopInfoBean().getShopId()));
        call.enqueue(new SSHCallBackNew<BaseResp<BeanRespReqEmpty>>() {
            @Override
            public void onSuccess(Response<BaseResp<BeanRespReqEmpty>> response) throws Exception {
                idList.clear();
                for (int i = 0; i < mOutDateList.size(); i++) {
                    Maps.put(i, false);
                }
                checkAdapter.notifyDataSetChanged();
                String msg = response.body().getMsg();
                String code = response.body().getCode();
//                if ("0".equals(code)) {
//                    finish();
                Toast.makeText(AddVoucherActivity.this, msg, Toast.LENGTH_SHORT).show();
//                }
            }

            @Override
            public void onFail(String message) {
                idList.clear();
                for (int i = 0; i < mOutDateList.size(); i++) {
                    Maps.put(i, false);
                }
                checkAdapter.notifyDataSetChanged();
                Toast.makeText(AddVoucherActivity.this, message, Toast.LENGTH_SHORT).show();

            }
        });
    }

    public HashMap<Integer, Boolean> Maps = new HashMap<Integer, Boolean>();

    class CheckAdapter extends RecyclerView.Adapter<CheckAdapter.CheckViewHolder> {
        private Context mContext;
        private List<CouponListBean.ListBean> lists;
        private HashMap<Integer, Boolean> AllMaps = new HashMap<Integer, Boolean>();
        private HashMap<String, Boolean> idMaps = new HashMap<String, Boolean>();


        public void initMap() {
            for (int i = 0; i < lists.size(); i++) {
                Maps.put(i, false);
            }
        }

        public RecyclerViewOnItemClickListener onItemClickListener;

        public CheckAdapter(Context context, List<CouponListBean.ListBean> lists) {
            this.mContext = context;
            this.lists = lists;
            initMap();
        }


        public Map<Integer, Boolean> getMap() {
            return Maps;
        }

        public Map<Integer, Boolean> getAllMap() {
            return AllMaps;
        }

        public List<String> getIdList() {
            return idList;
        }


        void setSelectItem(int position) {
            if (Maps.get(position)) {
                Maps.put(position, false);
            } else {
                Maps.put(position, true);
            }
            notifyItemChanged(position);
        }

        @Override
        public CheckAdapter.CheckViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            CheckAdapter.CheckViewHolder checkViewHolder = new CheckAdapter.CheckViewHolder(LayoutInflater.from(AddVoucherActivity.this).inflate(R.layout.item_add_voucher, parent, false), onItemClickListener);
            return checkViewHolder;
        }

        @Override
        public void onBindViewHolder(CheckAdapter.CheckViewHolder holder, final int position) {
            holder.mName.setText(lists.get(position).getCouponName());
            Glide.with(mContext).load(lists.get(position).getCouponPic()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(holder.headImg);
            holder.totalNum.setText("每人每天限使用个数" + lists.get(position).getCouponUseNum() + "个");
            holder.mTime.setText("有效期: " + lists.get(position).getCouponStartTime() + "-" + lists.get(position).getCouponEndTime());
            holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Maps.put(position, isChecked);
                    idMaps.put(lists.get(position).getCouponId(), isChecked);
                    if (isChecked) {
                        idList.add(lists.get(position).getCouponId());
                    } else {
                        idList.remove(lists.get(position).getCouponId());
                    }
                }
            });

            if (Maps.get(position) == null) {
                Maps.put(position, false);
            }
            holder.mCheckBox.setChecked(Maps.get(position));
            AllMaps.put(position, true);
        }


        @Override
        public int getItemCount() {
            return lists == null ? 0 : lists.size();
        }

        public void setItemClickListener(RecyclerViewOnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }

        class CheckViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            private RecyclerViewOnItemClickListener mListener;
            private TextView mName;
            private CheckBox mCheckBox;
            private RoundedImageView headImg;
            private TextView mTime;
            private TextView totalNum;

            public CheckViewHolder(View itemView, RecyclerViewOnItemClickListener onItemClickListener) {
                super(itemView);
                this.mListener = onItemClickListener;
                itemView.setOnClickListener(this);
                mName = itemView.findViewById(R.id.item_add_voucher_title);
                mCheckBox = itemView.findViewById(R.id.item_add_voucher_check);
                headImg = itemView.findViewById(R.id.item_add_voucher_head);
                mTime = itemView.findViewById(R.id.item_add_voucher_time);
                totalNum = itemView.findViewById(R.id.total_num);
            }

            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClickListener(v, getPosition());
                }
            }
        }

    }

    public interface RecyclerViewOnItemClickListener {
        void onItemClickListener(View view, int position);
    }
}
