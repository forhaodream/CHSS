package com.sheepshop.businessside.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.ImageViewerPopupView;
import com.lxj.xpopup.interfaces.OnSrcViewUpdateListener;
import com.lxj.xpopup.interfaces.XPopupImageLoader;
import com.lzy.imagepicker.loader.ImageLoader;
import com.makeramen.roundedimageview.RoundedImageView;
import com.nex3z.flowlayout.FlowLayout;
import com.previewlibrary.GPreviewBuilder;
import com.sheepshop.businessside.R;
import com.sheepshop.businessside.entity.EvaluateBean;
import com.sheepshop.businessside.ui.bean.UserViewInfo;
import com.sheepshop.businessside.weight.MyRatingBar;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ch.chtool.utils.RecyclerAdapter;
import ch.chtool.utils.RecyclerViewHolder;

/**
 * Created by CH
 * on 2019/12/16 17:20
 *
 * @author Administrator
 */
public class EvaluateAdapter extends RecyclerView.Adapter<EvaluateAdapter.ViewHolder> {

    private List<EvaluateBean.ListBean> mListBeans;
    private final Context context;
    private List<Object> objectList = new ArrayList<>();
    private GridLayoutManager gridLayoutManager;
    private ArrayList<UserViewInfo> mThumbViewInfoList;
    private Activity mActivity;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nickname;
        public MyRatingBar star;
        public TextView starText;
        public TextView content;
        public TextView cardName;
        public TextView time;
        public RoundedImageView headImg;
        public RecyclerView picRecycler;
        public FlowLayout flowLayout;

        public ViewHolder(@NonNull View holder) {
            super(holder);
            nickname = holder.findViewById(R.id.item_my_shop_nickname);
            star = holder.findViewById(R.id.item_my_shop_ratingbar);
            starText = holder.findViewById(R.id.item_my_shop_ratingbar_text);
            content = holder.findViewById(R.id.item_my_shop_content);
            cardName = holder.findViewById(R.id.card_name);
            time = holder.findViewById(R.id.my_shop_time);
            headImg = holder.findViewById(R.id.shop_head);
            picRecycler = holder.findViewById(R.id.item_shop_evaluate_pic);
            flowLayout = holder.findViewById(R.id.item_my_shop_flow);
        }
    }


    public EvaluateAdapter(Context context, List<EvaluateBean.ListBean> mDatas, Activity activity) {
        this.context = context;
        this.mListBeans = mDatas;
        this.mActivity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_shop_evaluate, null);
        return new ViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.nickname.setText(mListBeans.get(i).getBucNickname());
        viewHolder.star.setStar(mListBeans.get(i).getBucStar() / 2);
        viewHolder.starText.setText(mListBeans.get(i).getBucStar() / 2 + "分");
        viewHolder.content.setText(mListBeans.get(i).getBucText());
        viewHolder.cardName.setText(mListBeans.get(i).getBucCouponName());
        viewHolder.time.setText(mListBeans.get(i).getBucTime());
        Glide.with(context).load(mListBeans.get(i).getBucHeadurl()).placeholder(R.mipmap.fl_wujieguo_icon).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(viewHolder.headImg);
        if (!TextUtils.isEmpty(mListBeans.get(i).getBucPic())) {
            objectList.clear();
            objectList.addAll(Arrays.asList(mListBeans.get(i).getBucPic().split(",")));
//            mThumbViewInfoList = new ArrayList<>();
//            for (int a = 0; a < mListBeans.get(a).getBucPic().split(",").length; a++) {
//                mThumbViewInfoList.add(new UserViewInfo(mListBeans.get(a).getBucPic().split(",")[a]));
//            }
//            MyBaseQuickAdapter adapter = new MyBaseQuickAdapter(context);
//            adapter.addData(mThumbViewInfoList);
//            gridLayoutManager = new GridLayoutManager(context, 5);
//            gridLayoutManager.setOrientation(GridLayoutManager.HORIZONTAL);
//            viewHolder.picRecycler.setLayoutManager(gridLayoutManager);
//            viewHolder.picRecycler.setAdapter(adapter);
//            adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//                @Override
//                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                 // computeBoundsBackward(gridLayoutManager.findFirstVisibleItemPosition());
//                    GPreviewBuilder.from(mActivity)
//                            .setData(mThumbViewInfoList)
//                            .setCurrentIndex(position)
//                            .setSingleFling(true)
//                            .setType(GPreviewBuilder.IndicatorType.Number)
//                            .start();
//                }
//            });
            RecyclerAdapter adapter = new RecyclerAdapter<Object>(context, R.layout.item_image, objectList) {
                @Override
                public void convert(RecyclerViewHolder holder, Object itemData, int position) {
                    ImageView img = holder.getView(R.id.item_image_img);
                    Glide.with(context).load(itemData).into(img);
                    img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            computeBoundsBackward(gridLayoutManager.findFirstVisibleItemPosition());
//                            GPreviewBuilder.from(mActivity)
//                                    .setData(mThumbViewInfoList)
//                                    .setCurrentIndex(position)
//                                    .setType(GPreviewBuilder.IndicatorType.Number)
//                                    .start();
//                            new XPopup.Builder(context).asImageViewer(img, position, objectList, new OnSrcViewUpdateListener() {
//                                @Override
//                                public void onSrcViewUpdate(ImageViewerPopupView view, int pos) {
//                                    RecyclerView rv = (RecyclerView) holder.itemView.getParent();
//                                    view.updateSrcView((ImageView) rv.getChildAt(pos));
////                                    view.updateSrcView(img);
//                                }
//                            }, new ImageLoader()).isShowSaveButton(false).show();
                            new XPopup.Builder(context).asImageViewer(img, itemData, new ImageLoader()).isShowSaveButton(false).show();

                        }
                    });
                }

            };
//            ImageAdapter imageAdapter = new ImageAdapter(context, objectList);
            gridLayoutManager = new GridLayoutManager(context, 5);
            gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
            viewHolder.picRecycler.setLayoutManager(gridLayoutManager);
            viewHolder.picRecycler.setAdapter(adapter);

        } else {
            viewHolder.picRecycler.setVisibility(View.GONE);
        }
        // 有标签
        if (mListBeans.get(i).getBucLabelList() != null) {
            for (int b = 0; b < mListBeans.get(i).getBucLabelList().size(); b++) {
                TextView textView = buildLabel(mListBeans.get(i).getBucLabelList().get(b));
                viewHolder.flowLayout.addView(textView);
            }

        } else {
            viewHolder.flowLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mListBeans.size();
    }

    private void computeBoundsBackward(int firstCompletelyVisiblePos) {
        for (int i = firstCompletelyVisiblePos; i < mThumbViewInfoList.size(); i++) {
            View itemView = gridLayoutManager.findViewByPosition(i);
            Rect bounds = new Rect();
            if (itemView != null) {
                ImageView thumbView = itemView.findViewById(R.id.item_image_img);
                thumbView.getGlobalVisibleRect(bounds);
            }
            mThumbViewInfoList.get(i).setBounds(bounds);
        }
    }

    private TextView buildLabel(String text) {
        TextView textView = new TextView(context);
        textView.setText(text);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        textView.setPadding((int) dpToPx(4), (int) dpToPx(2), (int) dpToPx(4), (int) dpToPx(2));
        textView.setBackgroundResource(R.drawable.bg_gray_line);
        return textView;
    }

    private float dpToPx(float dp) {
        return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    class ImageLoader implements XPopupImageLoader {
        @Override
        public void loadImage(int position, @NonNull Object uri, @NonNull ImageView imageView) {
            Glide.with(imageView).load(uri).into(imageView);
        }

        @Override
        public File getImageFile(@NonNull Context context, @NonNull Object uri) {
            return null;
        }

        //必须实现这个方法，返回uri对应的缓存文件，可参照下面的实现，内部保存图片会用到。如果你不需要保存图片这个功能，可以返回null。

    }
}