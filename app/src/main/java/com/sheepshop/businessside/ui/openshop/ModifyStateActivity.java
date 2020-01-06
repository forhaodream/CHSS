package com.sheepshop.businessside.ui.openshop;

import android.os.Bundle;
import android.widget.TextView;

import com.sheepshop.businessside.R;
import com.sheepshop.businessside.weight.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import ch.chtool.base.BaseActivity;

/**
 * @author Hm
 * 修改状态界面
 */
public class ModifyStateActivity extends BaseActivity {
    @BindView(R.id.tv_modify_state)
    TextView tvModifyState;
    @BindView(R.id.tv_countdown)
    TextView tvCountdown;
    @BindView(R.id.tv_reason_fail)
    TextView tvReasonFail;


    @Override
    public int initLayout() {
        return R.layout.activity_modify_state;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }
}
