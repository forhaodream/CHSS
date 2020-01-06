package com.sheepshop.businessside.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sheepshop.businessside.R;
import com.sheepshop.businessside.entity.DicEntity;
import com.sheepshop.businessside.entity.QuickinputEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Demo class
 *
 * @author Administrator
 * @date 2019/11/4
 * @decs：
 */
public class StoreQuickinputAdapter extends BaseQuickAdapter<QuickinputEntity, StoreQuickinputAdapter.StoreQuickinputViewHolder> {
    public StoreQuickinputAdapter(@Nullable List<QuickinputEntity> data) {
        super(R.layout.item_quick_input, data);
    }

    @Override
    protected void convert(StoreQuickinputViewHolder helper, QuickinputEntity item) {
        helper.txtQuickInput.setText(item.getLabelDetail());
    }

    public static class StoreQuickinputViewHolder extends BaseViewHolder {
        @BindView(R.id.txt_Quick_input)
        TextView txtQuickInput;
        public StoreQuickinputViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
    @Override
    public int getItemCount() {
        return super.getItemCount();
    }
}
