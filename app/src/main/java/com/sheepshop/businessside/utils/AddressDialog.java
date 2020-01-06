package com.sheepshop.businessside.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.google.android.material.tabs.TabLayout;
import com.sheepshop.businessside.R;
import com.sheepshop.businessside.api.APPApi;
import com.sheepshop.businessside.base.Constant;
import com.sheepshop.businessside.entity.AreaSelectBean;
import com.sheepshop.businessside.net.XApi;
import com.sheepshop.businessside.network.netbean.HttpBean;
import com.sheepshop.businessside.network.netbean.ResponseBean;
import com.sheepshop.businessside.network.netinterface.BaseCallBack;
import com.sheepshop.businessside.ui.openshop.ShopInformationActivity;
import com.sheepshop.businessside.weight.LoadingDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 省市区选择对话框
 */
public final class AddressDialog {

    public static final class Builder
            extends BaseDialogFragment.Builder<Builder>
            implements BaseRecyclerViewAdapter.OnItemClickListener,
            View.OnClickListener, TabLayout.BaseOnTabSelectedListener, Runnable {

        private static final Handler HANDLER = new Handler(Looper.getMainLooper());

        //        private TextView mTitleView;
        private ImageView mCloseView;
        private TextView mSureView;
        private TabLayout mTabLayout;

        private RecyclerView mRecyclerView1;
        private RecyclerView mRecyclerView2;
        private RecyclerView mRecyclerView3;
//        private ImageView mHintView;

        private AddressDialogAdapter mAdapter1;
        private AddressDialogAdapter mAdapter2;
        private AddressDialogAdapter mAdapter3;

        private OnListener mListener;
        private OnCityListener mCityListener;

        private String mProvince = "";
        private String mCity = "";
        private Integer mCityId ;
        private String mArea = "";
        private Integer mAreaId ;

        private boolean mIgnoreArea;

        private int level = 3;//默认显示三级

        public Builder(ShopInformationActivity activity, String jsonCity) {
            super(activity);
            setContentView(R.layout.dialog_address);
            setGravity(Gravity.BOTTOM);
            setAnimStyle(BaseDialog.AnimStyle.LEFT);

            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);
            setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            setHeight(displayMetrics.heightPixels * 2 / 3);  //2:3的比例显示在屏幕上

//            mTitleView = findViewById(R.id.tv_dialog_address_title);
            mCloseView = findViewById(R.id.iv_dialog_address_closer);
            mSureView = findViewById(R.id.iv_dialog_address_sure);
            mTabLayout = findViewById(R.id.tb_dialog_address_tab);

            mRecyclerView1 = findViewById(R.id.rv_dialog_address_list1);
            mRecyclerView2 = findViewById(R.id.rv_dialog_address_list2);
            mRecyclerView3 = findViewById(R.id.rv_dialog_address_list3);
//            mHintView = findViewById(R.id.iv_dialog_address_hint);

            mAdapter1 = new AddressDialogAdapter(getContext());
            mAdapter2 = new AddressDialogAdapter(getContext());
            mAdapter3 = new AddressDialogAdapter(getContext());

            mCloseView.setOnClickListener(this);
            mSureView.setOnClickListener(this);
            mSureView.setVisibility(View.GONE);

            mAdapter1.setOnItemClickListener(this);
            mAdapter2.setOnItemClickListener(this);
            mAdapter3.setOnItemClickListener(this);

            mRecyclerView1.setAdapter(mAdapter1);
            mRecyclerView2.setAdapter(mAdapter2);
            mRecyclerView3.setAdapter(mAdapter3);

            mTabLayout.addTab(mTabLayout.newTab().setText("请选择"), true);
            mTabLayout.addOnTabSelectedListener(this);

            // 显示省份列表
            mAdapter1.setData(ProvinceUtils.getProvinceList(getContext(), jsonCity));
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
                mRecyclerView1.setMotionEventSplittingEnabled(false);
                mRecyclerView2.setMotionEventSplittingEnabled(false);
                mRecyclerView3.setMotionEventSplittingEnabled(false);
            }
        }


//        public Builder setTitle(int resId) {
//            return setTitle(getString(resId));
//        }
//        public Builder setTitle(CharSequence text) {
////            mTitleView.setText(text);
//            return this;
//        }

        /**
         * 不选择县级区域
         */
        public Builder setIgnoreArea() {
            mIgnoreArea = true;
            return this;
        }

        public Builder setListener(OnListener l) {
            mListener = l;
            return this;
        }

        public Builder setCityListener(OnCityListener l) {
            mCityListener = l;
            return this;
        }

        public Builder setLevel(int i) {
            level = i;
            return this;
        }

        /**
         * {@link BaseRecyclerViewAdapter.OnItemClickListener}
         */

        @Override
        public void onItemClick(RecyclerView recyclerView, View itemView, int position) {
            if (recyclerView == mRecyclerView1) {

                // 记录当前选择的省份
                mProvince = mAdapter1.getItem(position).getName();
                mTabLayout.getTabAt(mTabLayout.getSelectedTabPosition()).setText(mProvince);
                mAdapter1.selectIndex = position;
                mAdapter1.notifyDataSetChanged();
                if (level == 1) return;//限制显示的等级
                mTabLayout.addTab(mTabLayout.newTab().setText("请选择"), true);

//                mAdapter2.setData(ProvinceUtils.getCityList(mAdapter1.getItem(position).getNext()));
                mRecyclerView1.setVisibility(View.GONE);
                mRecyclerView2.setVisibility(View.VISIBLE);
                loadAreaData(true,mAdapter1.getItem(position).getAreaId());
//                // 如果当前选择的是直辖市，就直接跳过选择城市，直接选择区域
//                if (mAdapter2.getItemCount() == 1) {
//                    onItemClick(mRecyclerView2, null, 0);
//                }

            } else if (recyclerView == mRecyclerView2) {
                // 记录当前选择的城市
                mCity = mAdapter2.getItem(position).getName();
                mCityId = mAdapter2.getItem(position).getAreaId();
                mTabLayout.getTabAt(mTabLayout.getSelectedTabPosition()).setText(mCity);
                mAdapter2.selectIndex = position;
                mAdapter2.notifyDataSetChanged();
                if (level == 2) {
                    if (null != mCityListener) {
                        mCityListener.onSelected(getDialog(), mProvince, mCity, mArea, mCityId);
                        dismiss();
                    }
                    return;//限制显示的等级
                }
                mTabLayout.addTab(mTabLayout.newTab().setText("请选择"), true);
//                mAdapter3.setData(ProvinceUtils.getAreaList(mAdapter2.getItem(position).getNext()));
                mRecyclerView2.setVisibility(View.GONE);
                mRecyclerView3.setVisibility(View.VISIBLE);
                loadAreaData(false,mCityId);


            } else if (recyclerView == mRecyclerView3) {

                // 记录当前选择的区域
                mArea = mAdapter3.getItem(position).getName();
                mAreaId = mAdapter3.getItem(position).getAreaId();
                mTabLayout.getTabAt(mTabLayout.getSelectedTabPosition()).setText(mArea);
                mAdapter3.selectIndex = position;
                mAdapter3.notifyDataSetChanged();

                if (null != mListener) {
                    mListener.onSelected(getDialog(), mProvince, mCity, mArea, mAreaId);
                    dismiss();
                }

            }
        }


        @Override
        public void run() {
            if (getDialogFragment() != null &&
                    getDialogFragment().isAdded() &&
                    getDialog() != null &&
                    getDialog().isShowing()) {
                dismiss();
            }
        }

        /**
         * {@link View.OnClickListener}
         */

        @Override
        public void onClick(View v) {
            if (v == mCloseView) {
                dismiss();
                if (mListener != null) {
                    mListener.onCancel(getDialog());
                }
            }
            if (v == mSureView) {
                if (mListener != null) {
                    if ("".equals(mArea) || TextUtils.isEmpty(mArea)) {
                        ToastUtils.showSingleToast("请选择城市区域");
                        return;
                    }
                    mListener.onSelected(getDialog(), mProvince, mCity, mArea, mAreaId);

                    dismiss();
//                    mListener.onCancel(getDialog());
                }
                if (mCityListener != null) {

                    if ("".equals(mCity) || TextUtils.isEmpty(mCity)) {
                        ToastUtils.showSingleToast("请选择城市");
                        return;
                    }
                    mCityListener.onSelected(getDialog(), mProvince, mCity, mArea, mCityId);

                    dismiss();
                }
            }
        }

        /**
         * {@link TabLayout.OnTabSelectedListener}
         */

        // Tab条目被选中
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            tab.setText("请选择");
            switch (tab.getPosition()) {
                case 0:
                    mProvince = "";
                    mCity = "";
                    mArea = "";
                    mCityId = 0;
                    mAreaId = 0;
                    mAdapter1.selectIndex = -1;
                    while (mTabLayout.getTabAt(2) != null) {
                        mTabLayout.removeTabAt(2);
                    }
                    while (mTabLayout.getTabAt(1) != null) {
                        mTabLayout.removeTabAt(1);
                    }
                    mRecyclerView1.setVisibility(View.VISIBLE);
                    mRecyclerView2.setVisibility(View.GONE);
                    mRecyclerView3.setVisibility(View.GONE);
                    break;
                case 1:
                    mCity = "";
                    mCityId = 0;
                    mAreaId = 0;
                    mAdapter2.selectIndex = -1;
                    while (mTabLayout.getTabAt(2) != null) {
                        mTabLayout.removeTabAt(2);
                    }
                    mRecyclerView1.setVisibility(View.GONE);
                    mRecyclerView2.setVisibility(View.VISIBLE);
                    mRecyclerView3.setVisibility(View.GONE);
                    break;
                case 2:
                    mArea = "";
                    mAreaId = 0;
                    mAdapter3.selectIndex = -1;
                    mRecyclerView1.setVisibility(View.GONE);
                    mRecyclerView2.setVisibility(View.GONE);
                    mRecyclerView3.setVisibility(View.VISIBLE);
                    break;
                default:
                    break;
            }
        }

        // Tab条目被取消选中
        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
        }

        // Tab条目被重复点击
        @Override
        public void onTabReselected(TabLayout.Tab tab) {
        }

        private void loadAreaData(boolean isCity, Integer id) {
            LoadingDialog.showRoundProcessDialog(getContext());

            NetUtils.post(Constant.URL_SENDCODE, null,  AreaSelectBean.class, HttpBean.getResDatatypeBeanlist(), new BaseCallBack() {
                @Override
                public void onRequesting() {

                }

                @Override
                public void onSuccess(ResponseBean data) {
                    LoadingDialog.mDialog.cancel();
                    List<AreaSelectBean> selectBeanList = (List<AreaSelectBean>) data.pullData();
                    List<AddressBean> list = new ArrayList<>();
                    for (int i = 0; i <selectBeanList.size(); i++) {
                        list.add(new AddressBean(selectBeanList.get(i).getName(),selectBeanList.get(i).getId(),null));
                    }
                    if (isCity) {
                        Log.i("城市这嘎达", "onNext: " + JSON.toJSONString(selectBeanList));

                        mAdapter2.setData(list);
                    }else {
                        Log.i("城区这地方", "onNext: " + JSON.toJSONString(selectBeanList));

                        mAdapter3.setData(list);
                    }
                }


                @Override
                public void onError(ResponseBean msg) {
                    LoadingDialog.mDialog.cancel();
                }

                @Override
                public void onErrorForOthers(ResponseBean msg) {
                }
            });
        }

    }

    private static final class AddressDialogAdapter extends BaseRecyclerViewAdapter<AddressBean, BaseRecyclerViewAdapter.ViewHolder> {
        int selectIndex = -1;

        AddressDialogAdapter(Context context) {
            super(context);
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
            TextView textView = new TextView(parent.getContext());
            textView.setGravity(Gravity.CENTER_VERTICAL);
            TypedValue typedValue = new TypedValue();
            if (getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground, typedValue, true)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    textView.setBackground(getDrawable(typedValue.resourceId));
                } else {
                    textView.setBackgroundDrawable(getDrawable(typedValue.resourceId));
                }
            }
//            textView.setTextColor(0xFF222222);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            textView.setPadding((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics()),
                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics()),
                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics()),
                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics()));
            return new ViewHolder(textView);
        }

        @Override
        public void onBindViewHolder(@NonNull BaseRecyclerViewAdapter.ViewHolder holder, int position) {
            ((TextView) holder.itemView).setText(getItem(position).getName());
            if (selectIndex == position) {
                ((TextView) holder.itemView).setTextColor(getColor(R.color.color_green_theme));
            } else {
                ((TextView) holder.itemView).setTextColor(0xFF222222);
            }
        }
    }

    private static final class AddressBean {

        private String name; // 省、市、区的名称
        private Integer areaId; // 省、市、区的名称
        private JSONObject next; // 下一级的 Json

        private AddressBean(String name, Integer areaId, JSONObject next) {
            this.name = name;
            this.areaId = areaId;
            this.next = next;
        }

        private String getName() {
            return name;
        }

        private Integer getAreaId() {
            return areaId;
        }

        private JSONObject getNext() {
            return next;
        }
    }

    /**
     * 省市区读取工具类
     */
    private static final class ProvinceUtils {

        /**
         * 获取省列表
         */
        private static List<AddressBean> getProvinceList(Context context, String jsonCity) {
            Log.i("城市", "getProvinceList: " + jsonCity);
            try {
                JSONObject jsonObject1 = new JSONObject(jsonCity);
                JSONArray jsonArray = jsonObject1.getJSONArray("data");

                int length = jsonArray.length();

                ArrayList<AddressBean> list = new ArrayList<>(length);

                for (int i = 0; i < length; i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    list.add(new AddressBean(jsonObject.getString("name"), jsonObject.getInt("id"), jsonObject));
                }

                return list;

            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        /**
         * 获取城市列表
         *
         * @param jsonObject 城市Json
         */
        private static List<AddressBean> getCityList(JSONObject jsonObject) {
            try {
                JSONArray listCity = jsonObject.getJSONArray("childList");
                int length = listCity.length();

                ArrayList<AddressBean> list = new ArrayList<>(length);

                for (int i = 0; i < length; i++) {
                    list.add(new AddressBean(listCity.getJSONObject(i).getString("name1"), listCity.getJSONObject(i).getInt("area_code1"), listCity.getJSONObject(i)));
                }

                return list;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        /**
         * 获取区域列表
         *
         * @param jsonObject 区域 Json
         */
        private static List<AddressBean> getAreaList(JSONObject jsonObject) {
            try {
                JSONArray listArea = jsonObject.getJSONArray("childList");
                int length = listArea.length();

                ArrayList<AddressBean> list = new ArrayList<>(length);

                for (int i = 0; i < length; i++) {
                    String string = listArea.getJSONObject(i).getString("name2");
                    Integer area_code2 = listArea.getJSONObject(i).getInt("area_code2");
                    list.add(new AddressBean(string, area_code2, null));
                }
                return list;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

    }

    public interface OnListener {

        /**
         * 选择完成后回调
         *
         * @param province 省
         * @param city     市
         * @param area     区
         */
        void onSelected(Dialog dialog, String province, String city, String area, Integer areaId);

        /**
         * 点击取消时回调
         */
        void onCancel(Dialog dialog);
    }

    public interface OnCityListener {

        /**
         * 选择完成后回调
         *
         * @param province 省
         * @param city     市
         * @param area     区
         */
        void onSelected(Dialog dialog, String province, String city, String area, Integer cityId);

    }
}