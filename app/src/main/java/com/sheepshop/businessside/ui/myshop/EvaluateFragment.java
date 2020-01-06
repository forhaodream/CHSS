package com.sheepshop.businessside.ui.myshop;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.makeramen.roundedimageview.RoundedImageView;
import com.nex3z.flowlayout.FlowLayout;
import com.previewlibrary.GPreviewBuilder;
import com.previewlibrary.ZoomMediaLoader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sheepshop.businessside.MyApp;
import com.sheepshop.businessside.R;
import com.sheepshop.businessside.entity.EvaluateBean;
import com.sheepshop.businessside.entity.UserInfoBean;
import com.sheepshop.businessside.okhttp.BaseResp;
import com.sheepshop.businessside.okhttp.HttpUtils;
import com.sheepshop.businessside.okhttp.SSHCallBackNew;
import com.sheepshop.businessside.service.ApiService;
import com.sheepshop.businessside.tool.ToastUtils;
import com.sheepshop.businessside.ui.adapter.EvaluateAdapter;
import com.sheepshop.businessside.ui.adapter.MyBaseQuickAdapter;
import com.sheepshop.businessside.ui.adapter.TestImageLoader;
import com.sheepshop.businessside.ui.bean.UserViewInfo;
import com.sheepshop.businessside.weight.MyRatingBar;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ch.chtool.utils.RecyclerAdapter;
import ch.chtool.utils.RecyclerViewHolder;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by CH
 * on 2019/11/7 16:09
 */
public class EvaluateFragment extends Fragment {
    private RecyclerView mFragmentEvaluateRecycler;
    private View mView;
    private RecyclerAdapter mAdapter;
    private String type;
    private List<EvaluateBean.ListBean> mLists;
    private List<EvaluateBean.ListBean> mPageLists;
    private int endId = 0;
    private List<String> picLists;
    private int itemId = 1;

    public EvaluateFragment(String type) {
        this.type = type;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_evaluate, null);
        ZoomMediaLoader.getInstance().init(new TestImageLoader());
        initView();
        getList();
        return mView;
    }

    private void initView() {
        mFragmentEvaluateRecycler = mView.findViewById(R.id.fragment_evaluate_recycler);
        SmartRefreshLayout refreshLayout = mView.findViewById(R.id.refresh_layout);
        refreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()));
        refreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                itemId = 1;
                refreshLayout.finishRefresh(1000);
                getList();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                itemId++;
                getPageList(itemId);
                refreshLayout.finishLoadMore();
            }
        });
}

    List<Object> objectList = new ArrayList<>();


    private void initList(List<EvaluateBean.ListBean> lists) {
        EvaluateAdapter adapter = new EvaluateAdapter(getActivity(), lists, getActivity());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFragmentEvaluateRecycler.setLayoutManager(layoutManager);
        mFragmentEvaluateRecycler.setAdapter(adapter);
//        mAdapter = new RecyclerAdapter<EvaluateBean.ListBean>(getActivity(), R.layout.item_shop_evaluate, lists) {
//            @Override
//            public void convert(RecyclerViewHolder holder, EvaluateBean.ListBean itemData, int position) {
//                TextView nickname = holder.getView(R.id.item_my_shop_nickname);
//                MyRatingBar star = holder.getView(R.id.item_my_shop_ratingbar);
//                TextView starText = holder.getView(R.id.item_my_shop_ratingbar_text);
//                TextView content = holder.getView(R.id.item_my_shop_content);
//                TextView cardName = holder.getView(R.id.card_name);
//                TextView time = holder.getView(R.id.my_shop_time);
//                RoundedImageView headImg = holder.getView(R.id.shop_head);
//                RecyclerView picRecycler = holder.getView(R.id.item_shop_evaluate_pic);
//                FlowLayout flowLayout = holder.getView(R.id.item_my_shop_flow);
//                nickname.setText(itemData.getBucNickname());
//                star.setStar(itemData.getBucStar() / 2);
//                starText.setText(itemData.getBucStar() / 2 + "分");
//                content.setText(itemData.getBucText());
//                cardName.setText(itemData.getBucCouponName());
//                time.setText(itemData.getBucTime());
//                Glide.with(getActivity()).load(itemData.getBucHeadurl()).placeholder(R.mipmap.fl_wujieguo_icon).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(headImg);
//                // 有图片
//                if (!TextUtils.isEmpty(itemData.getBucPic())) {
//                    objectList.clear();
////                    picLists = Arrays.asList(itemData.getBucPic().split(","));
//
//                    objectList.addAll(Arrays.asList(itemData.getBucPic().split(",")));
//                    RecyclerAdapter adapter = new RecyclerAdapter<Object>(getActivity(), R.layout.item_image, objectList) {
//                        @Override
//                        public void convert(RecyclerViewHolder holder, Object itemData, int position) {
//                            ImageView img = holder.getView(R.id.item_image_img);
//                            Glide.with(getActivity()).load(itemData).into(img);
//                            img.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
////                                    new XPopup.Builder(getActivity()).asImageViewer(img, itemData, new ImageLoader()).isShowSaveButton(false).show();
////                                    new XPopup.Builder(getActivity()).asImageViewer(img, position, objectList, new OnSrcViewUpdateListener() {
////                                        @Override
////                                        public void onSrcViewUpdate(ImageViewerPopupView view, int pos) {
////                                            RecyclerView rv = (RecyclerView) holder.itemView.getParent();
////                                            view.updateSrcView((ImageView)rv.getChildAt(pos));
//////                                            view.updateSrcView(img);
//////                                            Log.d("EvaluateFragment", "position:" + position);
////                                        }
////                                    }, new ImageLoader()).isShowSaveButton(false).show();
//                                    computeBoundsBackward(gridLayoutManager.findFirstVisibleItemPosition());
//                                    GPreviewBuilder.from(getActivity())
//                                            .setData(mThumbViewInfoList)
//                                            .setCurrentIndex(position)
//                                            .setType(GPreviewBuilder.IndicatorType.Number)
//                                            .start();
//                                }
//                            });
//                        }
//
//                    };
//                    mThumbViewInfoList = new ArrayList<>();
//                    for (int i = 0; i < itemData.getBucPic().split(",").length; i++) {
//                        mThumbViewInfoList.add(new UserViewInfo(itemData.getBucPic().split(",")[i]));
//                    }
//                    gridLayoutManager = new GridLayoutManager(getActivity(), 5);
//                    gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
//                    picRecycler.setLayoutManager(gridLayoutManager);
//                    picRecycler.setAdapter(adapter);
//
////                    picLists = new ArrayList<>();
////                    picLists = Arrays.asList(itemData.getBucPic().split(","));
////                    List<UserViewInfo> userViewInfos = new ArrayList<>();
////                    mThumbViewInfoList = new ArrayList<>();
////                    for (int i = 0; i < itemData.getBucPic().split(",").length; i++) {
////                        mThumbViewInfoList.addAll(new UserViewInfo(itemData.getBucPic().split(",")[i]));
////                    }
//                    MyBaseQuickAdapter adapter = new MyBaseQuickAdapter(getActivity());
//                    adapter.addData(mThumbViewInfoList);
//
//                    adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                            // computeBoundsBackward(gridLayoutManager.findFirstVisibleItemPosition());
//                            GPreviewBuilder.from(getActivity())
//                                    .setData(mThumbViewInfoList)
//                                    .setCurrentIndex(position)
//                                    .setSingleFling(true)
//                                    .setType(GPreviewBuilder.IndicatorType.Number)
//                                    .start();
//                        }
//                    });
//                } else {
//                    picRecycler.setVisibility(View.GONE);
//                }
//                // 有标签
//                if (itemData.getBucLabelList() != null) {
//                    for (int i = 0; i < itemData.getBucLabelList().size(); i++) {
//                        TextView textView = buildLabel(itemData.getBucLabelList().get(i));
//                        flowLayout.addView(textView);
//                    }
//                } else {
//                    flowLayout.setVisibility(View.GONE);
//                }
//            }
//        };
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        mFragmentEvaluateRecycler.setLayoutManager(layoutManager);
//        mFragmentEvaluateRecycler.setAdapter(mAdapter);
    }

    private ArrayList<UserViewInfo> mThumbViewInfoList;
    private GridLayoutManager gridLayoutManager;

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
        TextView textView = new TextView(getActivity());
        textView.setText(text);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
//        textView.setPadding((int) dpToPx(8), (int) dpToPx(4), (int) dpToPx(8), (int) dpToPx(4));
        textView.setPadding((int) dpToPx(4), (int) dpToPx(2), (int) dpToPx(4), (int) dpToPx(2));
        textView.setBackgroundResource(R.drawable.bg_gray_line);
        return textView;
    }

    private float dpToPx(float dp) {
        return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
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

    private void getList() {
        Call<BaseResp<EvaluateBean>> call = HttpUtils.getInstance().getApiService(ApiService.class).evaluate("10", String.valueOf(MyApp.getShopInfoBean().getShopId()), String.valueOf(endId), type);//String.valueOf(MyApp.getShopInfoBean().getShopId())
        call.enqueue(new SSHCallBackNew<BaseResp<EvaluateBean>>() {
            @Override
            public void onSuccess(Response<BaseResp<EvaluateBean>> response) throws Exception {
                EvaluateBean bean = response.body().getData();
                mLists = bean.getList();
                if (bean != null) {
                    initList(mLists);
                } else {
                    ToastUtils.showToast("数据异常!");
                }

            }

            @Override
            public void onFail(String message) {
                ToastUtils.showToast(message);
            }
        });
    }

    private void getPageList(int a) {
        Call<BaseResp<EvaluateBean>> call = HttpUtils.getInstance().getApiService(ApiService.class).evaluate("10", String.valueOf(MyApp.getShopInfoBean().getShopId()), String.valueOf(a), type);
        call.enqueue(new SSHCallBackNew<BaseResp<EvaluateBean>>() {
            @Override
            public void onSuccess(Response<BaseResp<EvaluateBean>> response) throws Exception {
                mPageLists = response.body().getData().getList();
                if (mPageLists != null) {
                    if (mPageLists.size() > 0) {
                        mLists.addAll(mPageLists);
                        mAdapter.notifyDataSetChanged();
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
}
