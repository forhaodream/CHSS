package com.sheepshop.businessside.ui.mycenter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sheepshop.businessside.R;
import com.sheepshop.businessside.entity.CenterHomeBean;
import com.sheepshop.businessside.entity.CenterStateBean;
import com.sheepshop.businessside.okhttp.BaseResp;
import com.sheepshop.businessside.okhttp.HttpUtils;
import com.sheepshop.businessside.okhttp.SSHCallBackNew;
import com.sheepshop.businessside.service.ApiService;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import ch.chtool.utils.RecyclerAdapter;
import ch.ielse.view.SwitchView;
import retrofit2.Call;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by CH
 * on 2019/12/12 17:49
 */
public class CenterMineFragment extends Fragment implements View.OnClickListener {
    private View view;
    private SwitchView switchView;
    private TextView mMyCenterTitle;
    private SwitchView mCenterSwitch;
    private TextView mCenterState;
    private View mLine1;
    private RelativeLayout mLayoutTime;
    private RelativeLayout mLayoutDistance;
    private RelativeLayout mLayoutMoney;
    private View mLine2;
    private RecyclerView mCenterRecycler;
    private TextView mTextMore;
    private View mLine3;
    private TextView mTodayOrdersSum;
    private TextView mTodayMoneySum;
    private TextView mTimeText;
    private TextView mDistanceText;
    private RelativeLayout mLayoutCall;
    private View mLine4;
    private RelativeLayout mLayoutSuggest;
    private RecyclerAdapter adapter;
    private RelativeLayout mLayout2;
    private RelativeLayout mLayout3;
    private SharedPreferences npt;
    private SharedPreferences.Editor editor;
    private int buId;
    private String buToken;

    @SuppressLint("CommitPrefEdits")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_center_mine, null);
        npt = Objects.requireNonNull(getActivity()).getSharedPreferences("user_npt", MODE_PRIVATE);
        editor = npt.edit();
        buId = npt.getInt("buId", 0);
        buToken = npt.getString("buToken", "");
        initView();
        getData();
        return view;
    }

    private void initView() {
        mMyCenterTitle = view.findViewById(R.id.my_center_title);
        mCenterState = view.findViewById(R.id.center_state);
        mLayoutTime = view.findViewById(R.id.layout_time);
        mLayoutDistance = view.findViewById(R.id.layout_distance);
        mLayoutMoney = view.findViewById(R.id.layout_money);
        mTextMore = view.findViewById(R.id.text_more);
        mTodayOrdersSum = view.findViewById(R.id.today_orders_sum);
        mTodayMoneySum = view.findViewById(R.id.today_money_sum);
        mLayoutCall = view.findViewById(R.id.layout_call);
        mLayoutSuggest = view.findViewById(R.id.layout_suggest);
        mTimeText = view.findViewById(R.id.text_time);
        mDistanceText = view.findViewById(R.id.text_distance);
        mLayoutTime.setOnClickListener(this);
        mLayoutDistance.setOnClickListener(this);
        mLayoutMoney.setOnClickListener(this);
        mTextMore.setOnClickListener(this);
        mLayoutCall.setOnClickListener(this);
        mLayoutSuggest.setOnClickListener(this);
        switchView = view.findViewById(R.id.center_switch);
        switchView.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(SwitchView view) {
                view.toggleSwitch(true);

            }

            @Override
            public void toggleToOff(SwitchView view) {
                view.toggleSwitch(false);
            }
        });
        mLayout2 = view.findViewById(R.id.layout2);
        mLayout3 = view.findViewById(R.id.layout3);
        mLayout2.setOnClickListener(this);
        mLayout3.setOnClickListener(this);
    }

    @Override
    public void onClick(View var1) {
        switch (var1.getId()) {
            case R.id.layout_time:
                break;
            case R.id.layout_distance:
                break;
            case R.id.layout_money:
                break;
            case R.id.text_more:
                break;
            case R.id.layout_call:
                if (!TextUtils.isEmpty(ocdManagerPhone)) {
                    Intent Intent = new Intent(android.content.Intent.ACTION_DIAL, Uri.parse("tel:" + ocdManagerPhone));//跳转到拨号界面，同时传递电话号码
                    startActivity(Intent);
                }
                break;
            case R.id.layout_suggest:
                break;
            default:
                break;
            case R.id.layout2:
                startActivity(new Intent(getActivity(), GoodsManageActivity.class));
                break;
            case R.id.layout3:
                break;
        }
    }

    private String ocdManagerPhone;

    private void getData() {
        Call<BaseResp<CenterHomeBean>> call = HttpUtils.getInstance().getApiService(ApiService.class).getCenterHome(buToken, String.valueOf(buId));
        call.enqueue(new SSHCallBackNew<BaseResp<CenterHomeBean>>() {
            @Override
            public void onSuccess(Response<BaseResp<CenterHomeBean>> response) throws Exception {
                if (response != null) {
                    CenterHomeBean bean = response.body().getData();
                    ocdManagerPhone = bean.getOcdManagerPhone();
                    /**
                     *  {
                     *         "ocdManagerPhone": "18304511111",//客户经理电话
                     *         "ocdAddress": "辽宁省沈阳市沈河区222",//地址
                     *         "ocdContain": "210103,210106,210111,210123",//包含的全城配送分区
                     *         "ocdName": "店铺名称02",//运营中心名称
                     *         "ocdLogo": "http://testimage.lnhcsk.com/avatar.png",//logo
                     *         "ocdPhone": "18822222222",//运营中心电话
                     *         "ocdIsShutdown": 0,//强制关停状态 0非关停 1后台关店
                     *         "ocdManager": 1//客户经理id
                     *     }
                     */
                    // 运营中心名称
                    mMyCenterTitle.setText(bean.getOcdName());
                    // 营业状态
                    if (bean.getOcdStatus() == 0) {
                        switchView.setOpened(true);
                        mCenterState.setText("营业中");
                    } else {
                        switchView.setOpened(false);
                        mCenterState.setText("休息");
                    }
                    // 营业时间
                    mTimeText.setText(bean.getOcdOpenTime() + "至" + bean.getOcdCloseTime());
                    if (bean.getOcdIsCity() == 1) {
                        mDistanceText.setText("全程配送");
                    } else {
                        mDistanceText.setText(bean.getOcdDistance() + "KM");
                    }

                }

            }

            @Override
            public void onFail(String message) {

            }
        });
    }

    private void changeStatus() {
        Call<BaseResp<CenterStateBean>> call = HttpUtils.getInstance().getApiService(ApiService.class).getCenterState("", "", "");
        call.enqueue(new SSHCallBackNew<BaseResp<CenterStateBean>>() {
            @Override
            public void onSuccess(Response<BaseResp<CenterStateBean>> response) throws Exception {

            }

            @Override
            public void onFail(String message) {

            }
        });
    }

}
