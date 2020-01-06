package com.sheepshop.businessside.ui.mycenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.tabs.TabLayout;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sheepshop.businessside.R;
import com.sheepshop.businessside.entity.BeanRespReqEmpty;
import com.sheepshop.businessside.entity.GoodsListTypeBean;
import com.sheepshop.businessside.entity.PackageGoodsClassListBean;
import com.sheepshop.businessside.okhttp.BaseResp;
import com.sheepshop.businessside.okhttp.HttpUtils;
import com.sheepshop.businessside.okhttp.SSHCallBackNew;
import com.sheepshop.businessside.service.ApiService;
import com.sheepshop.businessside.tool.ToastUtils;
import com.sheepshop.businessside.ui.adapter.LeftAdapter;
import com.sheepshop.businessside.utils.GlidRoundUtils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import ch.chtool.utils.RecyclerAdapter;
import ch.chtool.utils.RecyclerViewHolder;
import ch.ielse.view.SwitchView;
import retrofit2.Call;
import retrofit2.Response;

public class GoodsManageActivity extends AppCompatActivity implements View.OnClickListener {
    private List<Fragment> mFragmentList;
    private ImageView mTitleBack;
    private RelativeLayout mTitle;
    private ListView leftListView;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private RecyclerAdapter leftAdapter;
    private Context me;
    private LeftAdapter mLeftAdapter;
    private int position = 0;
    private int prePosition = 0;
    private TextView mGoodsAdd;
    private TextView mGoodsManage;
    private SharedPreferences npt;
    private SharedPreferences.Editor editor;
    private String odId;
    private List<GoodsListTypeBean.GoodsListBean> goodsListBeans;
    private GoodsListTypeBean.TopMapNumListBean topMapNumListBeans;
    private RecyclerView mRecyclerView;
    private RecyclerAdapter mAdapter;
    private ListView mRecyclerLeft;
    private TextView mText1;
    private TextView mText2;
    private TextView mText3;
    private TextView mText4;
    private RecyclerView mRightRecycler;
    private String classId = "0";
    private String type = "1";
    private String cuisineId;
    private String maxSwitch;
    private String autoSwitch;
    private String repertoryNum;
    private String repertoryMax;
    private ImageView mImageClose;
    private SwitchView mMaxSwitch;
    private EditText mTextRepertoryNum;
    private EditText mTextRepertoryMax;
    private SwitchView mAutoSwitch;
    private Button mTextSave;
    private String colorStr = "#b3b3b3";
    private RepertoryPopup mRepertoryPopup;
    private String stateStr;
    private SmartRefreshLayout mRefreshLayout;
    private Intent intent;

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_manager);
        me = this;
        initView();
        npt = getSharedPreferences("user_npt", MODE_PRIVATE);
        editor = npt.edit();
        odId = npt.getString("buOperationId", "");
        Log.d("buOperationId", "" + odId);
        getPackageGoodsClassList();
        getGoodsListByType("1", "0");
    }

    private void initView() {
        mRepertoryPopup = new RepertoryPopup(me);
        mTitleBack = findViewById(R.id.title_back);
        mTitle = findViewById(R.id.title);
        mRecyclerLeft = findViewById(R.id.recycler_left);
        mText1 = findViewById(R.id.text1);
        mText2 = findViewById(R.id.text2);
        mText3 = findViewById(R.id.text3);
        mText4 = findViewById(R.id.text4);
        mRightRecycler = findViewById(R.id.right_recycler);
        mGoodsAdd = findViewById(R.id.goods_add);
        mGoodsManage = findViewById(R.id.goods_manage);
        mTitleBack.setOnClickListener(this);
        mText1.setOnClickListener(this);
        mText2.setOnClickListener(this);
        mText3.setOnClickListener(this);
        mText4.setOnClickListener(this);
        mGoodsAdd.setOnClickListener(this);
        mGoodsManage.setOnClickListener(this);
        mRefreshLayout = findViewById(R.id.refresh_layout);
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(this));
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh(1000);
                getGoodsListByType(type, classId);
            }
        });
    }


    @Override
    public void onClick(View var1) {
        switch (var1.getId()) {
            case R.id.goods_add:
                startActivity(new Intent(me, AddGoodsActivity.class));
                break;
            case R.id.goods_manage:
                startActivity(new Intent(me, GoodsSortingActivity.class));
                break;
            case R.id.title_back:
                finish();
                break;
            case R.id.text1:
                type = "1";
                getGoodsListByType(type, classId);
                mText1.setTextColor(getResources().getColor(R.color.red_45));
                mText2.setTextColor(getResources().getColor(R.color.black_3));
                mText3.setTextColor(getResources().getColor(R.color.black_3));
                mText4.setTextColor(getResources().getColor(R.color.black_3));
                break;
            case R.id.text2:
                type = "2";
                getGoodsListByType(type, classId);
                mText1.setTextColor(getResources().getColor(R.color.black_3));
                mText2.setTextColor(getResources().getColor(R.color.red_45));
                mText3.setTextColor(getResources().getColor(R.color.black_3));
                mText4.setTextColor(getResources().getColor(R.color.black_3));
                break;
            case R.id.text3:
                type = "3";
                getGoodsListByType(type, classId);
                mText1.setTextColor(getResources().getColor(R.color.black_3));
                mText2.setTextColor(getResources().getColor(R.color.black_3));
                mText3.setTextColor(getResources().getColor(R.color.red_45));
                mText4.setTextColor(getResources().getColor(R.color.black_3));
                break;
            case R.id.text4:
                type = "4";
                getGoodsListByType(type, classId);
                Log.d("GoodsManageActivity", type);
                Log.d("GoodsManageActivity", classId);
                mText1.setTextColor(getResources().getColor(R.color.black_3));
                mText2.setTextColor(getResources().getColor(R.color.black_3));
                mText3.setTextColor(getResources().getColor(R.color.black_3));
                mText4.setTextColor(getResources().getColor(R.color.red_45));
                break;
        }
    }

    private void getPackageGoodsClassList() {
        Call<BaseResp<List<PackageGoodsClassListBean>>> call = HttpUtils.getInstance().getApiService(ApiService.class).getPackageGoodsClassList(odId);
        call.enqueue(new SSHCallBackNew<BaseResp<List<PackageGoodsClassListBean>>>() {
            @Override
            public void onSuccess(Response<BaseResp<List<PackageGoodsClassListBean>>> response) throws Exception {
                if (response != null) {
                    List<PackageGoodsClassListBean> listBeans = response.body().getData();
                    mLeftAdapter = new LeftAdapter(me, listBeans);
                    mRecyclerLeft.setAdapter(mLeftAdapter);
                    mRecyclerLeft.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                            mLeftAdapter.setSelectItem(pos);
                            mLeftAdapter.notifyDataSetChanged();
                            position = pos;
                            classId = String.valueOf(listBeans.get(pos).getId());
                            getGoodsListByType("1", classId);
                            Log.d("GoodsManageActivity", classId);
                        }
                    });
                }
            }

            @Override
            public void onFail(String message) {

            }
        });
    }

    private void getGoodsListByType(String type, String classId) {
        Call<BaseResp<GoodsListTypeBean>> call = HttpUtils.getInstance().getApiService(ApiService.class).getGoodsListByType(odId, type, classId);
        call.enqueue(new SSHCallBackNew<BaseResp<GoodsListTypeBean>>() {
            @Override
            public void onSuccess(Response<BaseResp<GoodsListTypeBean>> response) throws Exception {
                if (response != null) {
                    GoodsListTypeBean bean = response.body().getData();
                    goodsListBeans = bean.getGoodsList();
                    topMapNumListBeans = bean.getTopMapNumList();
                    initRecycler(goodsListBeans);
                    mText1.setText("已上架 (" + topMapNumListBeans.get_$1() + ")");
                    mText2.setText("已售空 (" + topMapNumListBeans.get_$2() + ")");
                    mText3.setText("待上架 (" + topMapNumListBeans.get_$3() + ")");
                    mText4.setText("已下架 (" + topMapNumListBeans.get_$4() + ")");
                }
            }

            @Override
            public void onFail(String message) {

            }
        });
    }

    private void initRecycler(List<GoodsListTypeBean.GoodsListBean> lists) {
        mAdapter = new RecyclerAdapter<GoodsListTypeBean.GoodsListBean>(me, R.layout.item_goods_right, lists) {
            @Override
            public void convert(RecyclerViewHolder holder, GoodsListTypeBean.GoodsListBean bean, int position) {
                RelativeLayout layout = holder.getView(R.id.layout);
                layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (bean.getIsRelease() == 1) {
                            intent = new Intent(me, UpModifyGoodsActivity.class);
                            intent.putExtra("odId", odId);
                            intent.putExtra("pgiId", String.valueOf(bean.getId()));
                            startActivity(intent);
                        } else {
                            intent = new Intent(me, DownModifyGoodsActivity.class);
                            intent.putExtra("odId", odId);
                            intent.putExtra("pgiId", String.valueOf(bean.getId()));
                            startActivity(intent);
                        }

                    }
                });
                ImageView head = holder.getView(R.id.item_right_head);
                TextView title = holder.getView(R.id.item_right_title);
                TextView typeText = holder.getView(R.id.item_right_type);
                TextView score = holder.getView(R.id.item_right_score);
                TextView volume = holder.getView(R.id.item_right_volume);
                TextView repertory = holder.getView(R.id.item_right_repertory);
                TextView state = holder.getView(R.id.item_right_state);
                TextView putaway = holder.getView(R.id.item_right_putaway);
                TextView price = holder.getView(R.id.item_right_price);
                Glide.with(me).load(bean.getShowUrl()).apply(RequestOptions.bitmapTransform(new GlidRoundUtils(8))).into(head);
                title.setText(bean.getName());
                typeText.setText(bean.getPackageClassName());
                Log.d("GoodsManageActivity", bean.getPackageClassName() + "");
                score.setText(bean.getScore() + "分");
                volume.setText("月销 " + bean.getMonthSales());
                repertory.setText("库存" + bean.getStockNum());
                price.setText("¥ " + bean.getMoney());
                if (bean.getIsRelease() == 0) {
                    state.setText("已下架");
                    stateStr = "上架";
                    putaway.setText(stateStr);
                    putaway.setTextColor(getResources().getColor(R.color.white));
                    putaway.setBackgroundResource(R.drawable.bg_login_red);
                } else if (bean.getIsRelease() == 1) {
                    state.setText("已上架");
                    stateStr = "下架";
                    putaway.setText(stateStr);
                    putaway.setTextColor(getResources().getColor(R.color.red_45));
                    putaway.setBackgroundResource(R.drawable.shape_red_45);
                } else if (bean.getIsRelease() == 2) {
                    state.setText("待上架");
                    stateStr = "上架";
                    putaway.setText(stateStr);
                    putaway.setTextColor(getResources().getColor(R.color.white));
                    putaway.setBackgroundResource(R.drawable.bg_login_red);
                } else {
                    state.setText("已售空");
                    stateStr = "下架";
                    putaway.setText(stateStr);
                    putaway.setTextColor(getResources().getColor(R.color.red_45));
                    putaway.setBackgroundResource(R.drawable.shape_red_45);
                }
                repertory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cuisineId = String.valueOf(bean.getId());
                        new XPopup.Builder(me).asCustom(mRepertoryPopup).show();
                    }
                });
                putaway.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cuisineId = String.valueOf(bean.getId());
                        if ("上架".equals(stateStr)) {
                            new XPopup.Builder(me).asConfirm("提示", "确认将该商品下架?",
                                    new OnConfirmListener() {
                                        @Override
                                        public void onConfirm() {
                                            editReleaseStatus(cuisineId, "1");
                                            getGoodsListByType(type, classId);
                                        }
                                    }).show();
                        } else {
                            new XPopup.Builder(me).asConfirm("提示", "确认将该商品上架?",
                                    new OnConfirmListener() {
                                        @Override
                                        public void onConfirm() {
                                            editReleaseStatus(cuisineId, "0");
                                            getGoodsListByType(type, classId);
                                        }
                                    }).show();
                        }

                    }
                });

            }

        };
        mRightRecycler.setLayoutManager(new LinearLayoutManager(me));
        mRightRecycler.setAdapter(mAdapter);
    }


    class RepertoryPopup extends BottomPopupView {

        public RepertoryPopup(@NonNull Context context) {
            super(context);
        }

        @Override
        protected int getImplLayoutId() {
            return R.layout.popup_repertory;
        }

        @Override
        protected void onCreate() {
            super.onCreate();
            initView();
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
            return getPopupHeight();
        }

        private void initView() {
            mImageClose = findViewById(R.id.image_close);
            mMaxSwitch = findViewById(R.id.max_switch);
            mTextRepertoryNum = findViewById(R.id.text_repertory_num);
            mAutoSwitch = findViewById(R.id.auto_switch);
            mTextRepertoryMax = findViewById(R.id.text_repertory_max);
            mTextSave = findViewById(R.id.text_save);
            mImageClose.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
            mMaxSwitch.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
                @Override
                public void toggleToOn(SwitchView view) {
                    view.setOpened(true);
                    mAutoSwitch.setOpened(false);
                    maxSwitch = "1";
                }

                @Override
                public void toggleToOff(SwitchView view) {
                    view.setOpened(false);
                    mAutoSwitch.setOpened(true);
                    maxSwitch = "0";
                }
            });
            if (mMaxSwitch.isOpened()) {
                maxSwitch = "1";
            } else {
                maxSwitch = "0";
            }
            mAutoSwitch.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
                @Override
                public void toggleToOn(SwitchView view) {
                    view.setOpened(true);
                    mMaxSwitch.setOpened(false);
                    autoSwitch = "1";
                }

                @Override
                public void toggleToOff(SwitchView view) {
                    view.setOpened(false);
                    mMaxSwitch.setOpened(true);
                    autoSwitch = "0";
                }
            });
            if (mAutoSwitch.isOpened()) {
                autoSwitch = "1";
            } else {
                autoSwitch = "0";
            }
            repertoryNum = mTextRepertoryNum.getText().toString();
            repertoryMax = mTextRepertoryMax.getText().toString();
            mTextSave.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    editStock(maxSwitch, mTextRepertoryNum.getText().toString(), autoSwitch, mTextRepertoryMax.getText().toString());
                }
            });

        }


    }

    private void editReleaseStatus(String pgiId, String isRelease) {
        Call<BaseResp<BeanRespReqEmpty>> call = HttpUtils.getInstance().getApiService(ApiService.class).editReleaseStatus(odId, pgiId, isRelease);
        call.enqueue(new SSHCallBackNew<BaseResp<BeanRespReqEmpty>>() {
            @Override
            public void onSuccess(Response<BaseResp<BeanRespReqEmpty>> response) throws Exception {
                if (response != null) {
                    String msg = response.body().getMsg();
                    ToastUtils.showToast(msg);
                }
            }

            @Override
            public void onFail(String message) {
                ToastUtils.showToast(message);

            }
        });
    }

    private void editStock(String maxSwitch, String repertoryNum, String autoSwitch, String repertoryMax) {
        Call<BaseResp<BeanRespReqEmpty>> call = HttpUtils.getInstance().getApiService(ApiService.class).editStock(cuisineId, odId, maxSwitch, repertoryNum, autoSwitch, repertoryMax);
        call.enqueue(new SSHCallBackNew<BaseResp<BeanRespReqEmpty>>() {
            @Override
            public void onSuccess(Response<BaseResp<BeanRespReqEmpty>> response) throws Exception {
                if (response != null) {
                    String msg = response.body().getMsg();
                    ToastUtils.showToast(msg);
                    mRepertoryPopup.dismiss();
                    getGoodsListByType(type, classId);
                    Log.d("GoodsManageActivity1", type);
                    Log.d("GoodsManageActivity1", classId);
                }
            }

            @Override
            public void onFail(String message) {
                Log.d("GoodsManageActivity", message);

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPackageGoodsClassList();
        getGoodsListByType(type, classId);
    }
}
