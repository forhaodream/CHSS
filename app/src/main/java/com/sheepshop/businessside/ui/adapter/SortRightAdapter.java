package com.sheepshop.businessside.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.sheepshop.businessside.R;
import com.sheepshop.businessside.entity.GoodsListTypeBean;
import com.sheepshop.businessside.ui.helper.MyItemTouchCallback;
import com.sheepshop.businessside.utils.GlidRoundUtils;

import java.util.Collections;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by CH
 * on 2019/12/31 17:31
 */
public class SortRightAdapter extends RecyclerView.Adapter<SortRightAdapter.RecyclerViewHolder> implements MyItemTouchCallback.ItemTouchAdapter {

    private LayoutInflater mInflater;

    private ItemOnLongClickListener itemListener;

    private List<GoodsListTypeBean.GoodsListBean> mGoodsListBeans;
    private Context mContext;

    public SortRightAdapter(Context context, List<GoodsListTypeBean.GoodsListBean> lists) {
        this.mGoodsListBeans = lists;
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemCount() {
        return mGoodsListBeans.size();
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, final int position) {
        GoodsListTypeBean.GoodsListBean bean = mGoodsListBeans.get(position);
        Glide.with(mContext).load(bean.getShowUrl()).apply(RequestOptions.bitmapTransform(new GlidRoundUtils(5))).into(holder.head);
        holder.title.setText(bean.getName());
        holder.type.setText(bean.getPackageClassName());
        holder.score.setText(bean.getScore() + "分");
        holder.volume.setText("月销 " + bean.getMonthSales());
        holder.up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemListener.itemLongClick(mGoodsListBeans.get(position));
            }
        });
        if (bean.getTop() == 1) {
            holder.up.setVisibility(View.INVISIBLE);
        } else {
            holder.up.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onMove(int fromPosition, int toPosition) {
        if (fromPosition == mGoodsListBeans.size() - 1 || toPosition == mGoodsListBeans.size() - 1) {
            return;
        }
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(mGoodsListBeans, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(mGoodsListBeans, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onSwiped(int position) {
        mGoodsListBeans.remove(position);
        notifyItemRemoved(position);
    }



    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //  使用这种解析方式 RecyclerView的match parent 属性才不会失效
        return new RecyclerViewHolder(mInflater.inflate(R.layout.item_goods_sort, parent, false));
    }


    public void setItemListener(ItemOnLongClickListener listener) {
        itemListener = listener;
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        View mItemView;
        ImageView head;
        TextView title;
        TextView type;
        TextView score;
        TextView volume;
        TextView state;
        ImageView up;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            mItemView = itemView;
            head = itemView.findViewById(R.id.item_right_head);
            title = itemView.findViewById(R.id.item_right_title);
            type = itemView.findViewById(R.id.item_right_type);
            score = itemView.findViewById(R.id.item_right_score);
            volume = itemView.findViewById(R.id.item_right_volume);
            state = itemView.findViewById(R.id.item_right_state);
            up = itemView.findViewById(R.id.image_up);
        }
    }


    public interface ItemOnLongClickListener {

        void itemLongClick(GoodsListTypeBean.GoodsListBean goodsListBean);
    }


}
