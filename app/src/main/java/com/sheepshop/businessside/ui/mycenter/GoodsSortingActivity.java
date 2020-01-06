package com.sheepshop.businessside.ui.mycenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
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
import com.sheepshop.businessside.ui.adapter.SortRightAdapter;
import com.sheepshop.businessside.ui.bean.SaveDataBean;
import com.sheepshop.businessside.ui.helper.MyItemTouchCallback;
import com.sheepshop.businessside.ui.helper.OnRecyclerItemClickListener;
import com.sheepshop.businessside.ui.helper.VibratorUtil;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;
import com.yanzhenjie.recyclerview.touch.OnItemMoveListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import ch.chtool.utils.RecyclerAdapter;
import retrofit2.Call;
import retrofit2.Response;


public class GoodsSortingActivity extends AppCompatActivity implements View.OnClickListener {
    private ListView leftListView;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private RecyclerAdapter rightAdapter;
    private Context me;
    private LeftAdapter mLeftAdapter;
    private int position = 0;
    private TextView mGoodsAdd;
    private TextView mGoodsManage;
    private SwipeRecyclerView mRecyclerView;
    private List<GoodsListTypeBean.GoodsListBean> goodsListBeans;
    private GoodsListTypeBean.TopMapNumListBean topMapNumListBeans;
    private String classId = "1";
    private String type = "1";
    public static String TOP_STATES = "TOP";
    private SortRightAdapter adapter;
    private SharedPreferences npt;
    private SharedPreferences.Editor editor;
    private String odId;
    private Map<String, String> jsonMap = new HashMap<>();
    private List<PackageGoodsClassListBean> listBeans;
    private SaveDataBean saveDataBean = new SaveDataBean();
    private List<SaveDataBean> mSaveDataBeanList = new ArrayList<>();
    private Map<String, String> map = new HashMap<>();
    private List<String> idLists = new ArrayList<>();

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_sorting);
        me = this;
        npt = getSharedPreferences("user_npt", MODE_PRIVATE);
        editor = npt.edit();
        odId = npt.getString("buOperationId", "");
        initView();
        getPackageGoodsClassList();
        getGoodsListByType("0", "0");
    }

    private void initView() {
        ImageView titleBack = findViewById(R.id.title_back);
        leftListView = findViewById(R.id.recycler_left);
        mViewPager = findViewById(R.id.viewPager);
        mTabLayout = findViewById(R.id.tabLayout);
        mRecyclerView = findViewById(R.id.goods_sort_recycler);
        mRecyclerView.setLongPressDragEnabled(true);
        titleBack.setOnClickListener(this);
        Button saveBtn = findViewById(R.id.btn_save);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                String aaa = gson.toJson(jsonMap);
                Log.d("GoodsSortingActivity", "jsonMap:" + jsonMap.toString());
                Log.d("GoodsSortingActivity", "jsonMap:" + aaa);
                editSort();
            }
        });
    }

    private int prePosition = 0;


    @Override
    public void onClick(View var1) {
        switch (var1.getId()) {
            case R.id.title_back:
                finish();
                break;
        }

    }


    private void getPackageGoodsClassList() {
        Call<BaseResp<List<PackageGoodsClassListBean>>> call = HttpUtils.getInstance().getApiService(ApiService.class).getSortGoodsClassList(odId);
        call.enqueue(new SSHCallBackNew<BaseResp<List<PackageGoodsClassListBean>>>() {
            @Override
            public void onSuccess(Response<BaseResp<List<PackageGoodsClassListBean>>> response) throws Exception {
                if (response != null) {
                    listBeans = response.body().getData();

                    mLeftAdapter = new LeftAdapter(me, listBeans);
                    leftListView.setAdapter(mLeftAdapter);
                    leftListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                            mLeftAdapter.setSelectItem(pos);
                            mLeftAdapter.notifyDataSetChanged();
                            position = pos;
                            classId = String.valueOf(listBeans.get(pos).getId());
                            jsonMap.put(classId, "");

                            saveDataBean.setKey(classId);
                            Log.d("GoodsSortingActivity", classId);
                            getGoodsListByType("0", classId);
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
                    initRecycler();

                }
            }

            @Override
            public void onFail(String message) {

            }
        });
    }


    private void initRecycler() {
//        rightAdapter = new RecyclerAdapter<GoodsListTypeBean.GoodsListBean>(me, R.layout.item_goods_sort, lists) {
//            @Override
//            public void convert(RecyclerViewHolder holder, GoodsListTypeBean.GoodsListBean bean, int position) {
//                ImageView head = holder.getView(R.id.item_right_head);
//                TextView title = holder.getView(R.id.item_right_title);
//                TextView type = holder.getView(R.id.item_right_type);
//                TextView score = holder.getView(R.id.item_right_score);
//                TextView volume = holder.getView(R.id.item_right_volume);
//                TextView state = holder.getView(R.id.item_right_state);
//                ImageView up = holder.getView(R.id.image_up);
//                Glide.with(me).load(bean.getShowUrl()).apply(RequestOptions.bitmapTransform(new GlidRoundUtils(5))).into(head);
//                title.setText(bean.getName());
//                type.setText(bean.getPackageClassName());
//                score.setText(bean.getScore() + "分");
//                volume.setText("月销 " + bean.getMonthSales());
//                up.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                    }
//                });
//
//
//            }
//
//        };
        adapter = new SortRightAdapter(me, goodsListBeans);
        OnItemMoveListener mItemMoveListener = new OnItemMoveListener() {
            @SuppressLint("NewApi")
            @Override
            public boolean onItemMove(RecyclerView.ViewHolder srcHolder, RecyclerView.ViewHolder targetHolder) {
                // 此方法在Item拖拽交换位置时被调用。
                // 第一个参数是要交换为之的Item，第二个是目标位置的Item。

                // 交换数据，并更新adapter。
                int fromPosition = srcHolder.getAdapterPosition();
                int toPosition = targetHolder.getAdapterPosition();
                Collections.swap(goodsListBeans, fromPosition, toPosition);
                adapter.notifyItemMoved(fromPosition, toPosition);
                for (int i = 0; i < goodsListBeans.size(); i++) {
                    idLists.add(String.valueOf(goodsListBeans.get(i).getId()));
                }
                if (jsonMap.containsKey(classId)) {
                    jsonMap.put(classId, String.join(",", idLists));
                    saveDataBean.setValue(String.join(",", idLists));
                    mSaveDataBeanList.add(saveDataBean);
                }

                // 返回true，表示数据交换成功，ItemView可以交换位置。
                return true;
            }

            @Override
            public void onItemDismiss(RecyclerView.ViewHolder srcHolder) {

            }
        };
        mRecyclerView.setOnItemMoveListener(mItemMoveListener);// 监听拖拽，更新UI。
        mRecyclerView.setLayoutManager(new LinearLayoutManager(me));
        adapter.setItemListener(new SortRightAdapter.ItemOnLongClickListener() {
            @Override
            public void itemLongClick(GoodsListTypeBean.GoodsListBean goodsListBean) {
                Bundle bundle = new Bundle();
                bundle.putInt(TOP_STATES, goodsListBean.getTop());
                PopupDialogFragment popupDialog = new PopupDialogFragment();
                popupDialog.setArguments(bundle);
                popupDialog.setItemOnClickListener(new PopupDialogFragment.DialogItemOnClickListener() {
                    @Override
                    public void onTop() {
                        //置顶
                        goodsListBean.setTop(1);
                        goodsListBean.setTime(System.currentTimeMillis());
                        refreshView();
                    }

                    @Override
                    public void onCancel() {
                        //取消
                        goodsListBean.setTop(0);
                        goodsListBean.setTime(System.currentTimeMillis());
                        refreshView();
                    }
                });
                popupDialog.show(getFragmentManager(), "popup");
            }
        });
        mRecyclerView.setAdapter(adapter);
//        ItemTouchHelper.Callback callback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.RIGHT) {
//            @Override
//            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
//                int fromPosition = viewHolder.getAdapterPosition();
//                int toPosition = target.getAdapterPosition();
//                if (fromPosition < toPosition) {
//                    for (int i = fromPosition; i < toPosition; i++) {
//                        Collections.swap(lists, i, i + 1);
//                    }
//                } else {
//                    for (int i = toPosition; i < fromPosition; i++) {
//                        Collections.swap(lists, i, i + 1);
//                    }
//                }
//                adapter.notifyItemMoved(fromPosition, toPosition);
//                return true;
//            }
//
//            @Override
//            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
//
//            }
//        };
        MyItemTouchCallback callback = new MyItemTouchCallback(adapter, goodsListBeans);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
        mRecyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(mRecyclerView) {
            @Override
            public void onLongClick(RecyclerView.ViewHolder vh) {
                if (vh.getLayoutPosition() != goodsListBeans.size() - 1) {
                    itemTouchHelper.startDrag(vh);
                    VibratorUtil.Vibrate(GoodsSortingActivity.this, 70);   //震动70ms
                }
            }
        });

    }


    private void refreshView() {
        //如果不调用sort方法，是不会进行排序的，也就不会调用compareTo
        Collections.sort(goodsListBeans);
        for (int i = 0; i < goodsListBeans.size(); i++) {
            idLists.add(String.valueOf(goodsListBeans.get(i).getId()));
        }
        if (jsonMap.containsKey(classId)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                jsonMap.put(classId, String.join(",", idLists));
                saveDataBean.setValue(String.join(",", idLists));
            }
            mSaveDataBeanList.add(saveDataBean);
        }
        adapter.notifyDataSetChanged();
    }

    private void editSort() {

        Gson gson = new Gson();
        String saveData = gson.toJson(saveDataBean);
        String jsonStr = gson.toJson(mSaveDataBeanList);
        Log.d("GoodsSortingActivity", "map:" + saveData);
        Log.d("GoodsSortingActivity", "map:" + jsonStr);
        Call<BaseResp<BeanRespReqEmpty>> call = HttpUtils.getInstance().getApiService(ApiService.class).editSort(odId, jsonStr);
        call.enqueue(new SSHCallBackNew<BaseResp<BeanRespReqEmpty>>() {
            @Override
            public void onSuccess(Response<BaseResp<BeanRespReqEmpty>> response) throws Exception {
                if (response != null) {
                    String message = response.body().getMsg();
                    ToastUtils.showToast(message);
                    finish();
                }
            }

            @Override
            public void onFail(String message) {
                ToastUtils.showToast(message);
            }
        });
    }
}
