package com.sheepshop.businessside.ui.mycenter;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.PermissionChecker;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ch.chtool.base.BaseActivity;
import ch.chtool.utils.RecyclerAdapter;
import ch.chtool.utils.RecyclerViewHolder;
import ch.ielse.view.SwitchView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.lxj.xpopup.interfaces.XPopupImageLoader;
import com.lxj.xpopup.util.XPopupUtils;
import com.sheepshop.businessside.R;
import com.sheepshop.businessside.entity.ArgsBean;
import com.sheepshop.businessside.entity.BeanRespReqEmpty;
import com.sheepshop.businessside.entity.FilesUpload;
import com.sheepshop.businessside.entity.GoodsDetailBean;
import com.sheepshop.businessside.entity.GoodsKindBean;
import com.sheepshop.businessside.entity.GoodsLabelBean;
import com.sheepshop.businessside.entity.PackageClassListBean;
import com.sheepshop.businessside.okhttp.BaseResp;
import com.sheepshop.businessside.okhttp.HttpUtils;
import com.sheepshop.businessside.okhttp.SSHCallBackNew;
import com.sheepshop.businessside.service.ApiService;
import com.sheepshop.businessside.tool.ToastUtils;
import com.sheepshop.businessside.utils.ImageUtils;
import com.sheepshop.businessside.weight.LoadingDialog;
import com.ypx.imagepicker.ImagePicker;
import com.ypx.imagepicker.bean.ImageItem;
import com.ypx.imagepicker.bean.MimeType;
import com.ypx.imagepicker.bean.selectconfig.CropConfig;
import com.ypx.imagepicker.data.OnImagePickCompleteListener;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DownModifyGoodsActivity extends BaseActivity implements View.OnClickListener {

    private TextView mTextCategory;
    private TextView mCenterKinds;
    private EditText mEdProductName;
    private EditText mEdProductIntroduction;
    private ImageView mImageInDoorOne;
    private ImageView mImageStoreCameraOne;
    private ImageView mImageInDoorTwo;
    private ImageView mImageStoreCameraTwo;
    private ImageView mImageInDoorThree;
    private EditText mEditPrice;
    private EditText mEditOriginalPrice;
    private EditText mEditSpecification;
    private SwitchView mInfiniteSwitch;
    private EditText mEditRepertory;
    private SwitchView mAutoSwitch;
    private EditText mEditRepertoryMax;
    private EditText mEditBoxes;
    private EditText mEditBoxesPrice;
    private SwitchView mEliteSwitch;
    private TextView mTextTag;
    private Button mBtnSavePutaway;
    private DownModifyGoodsActivity me;
    private TextView unitText;
    private String odId;
    private String packageClassId;
    private String goodsClassId;
    private String name;
    private String details;
    private String showUrl;
    private String money;
    private String originalPrice;
    private String attr;
    private String arg;
    private String stockStatus;
    private String stockNum;
    private String stockAutoStatus;
    private String maxStockNum;
    private String boxNum;
    private String boxTotalPrice;
    private String isRelease;
    private String isRecommend;
    private String labelIds;
    private File mFileOne, mFileTwo, mFileThree;
    private ArrayList<ImageItem> picItems = new ArrayList<>();
    private List<File> fileList = new ArrayList<>();
    private ArrayList<ImageItem> imageItems = new ArrayList<>();
    private String picUrl;
    private List<String> picUrlList;
    private String imageOne;
    private String imageTwo;
    private String imageThree;
    private TagFlowLayout mAddGoodsTagFlow;
    private TextView mTextView;
    private TextView mTextMonthSale;
    private TextView mTextTotalSale;
    private TextView mTextDelete;
    private String pgiId;
    private TagAdapter mTagAdapter;
    private String mainUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        me = this;
    }

    @Override
    public int initLayout() {
        return R.layout.activity_down_modify_goods;
    }

    @Override
    public void initView() {
        unitText = findViewById(R.id.text_unit);
        unitText.setOnClickListener(this);
        ImageView titleBack = findViewById(R.id.title_back);
        RelativeLayout title = findViewById(R.id.title);
        mTextCategory = findViewById(R.id.text_category);
        mCenterKinds = findViewById(R.id.center_kinds);
        mEdProductName = findViewById(R.id.ed_product_name);
        mEdProductIntroduction = findViewById(R.id.ed_product_introduction);
        TextView titleStore = findViewById(R.id.title_store);
        mImageInDoorOne = findViewById(R.id.image_in_door_one);
        mImageStoreCameraOne = findViewById(R.id.image_store_camera_one);
        mImageInDoorTwo = findViewById(R.id.image_in_door_two);
        mImageStoreCameraTwo = findViewById(R.id.image_store_camera_two);
        mImageInDoorThree = findViewById(R.id.image_in_door_three);
        ImageView imageStoreCameraThree = findViewById(R.id.image_store_camera_three);
        TextView textPrice = findViewById(R.id.text_price);
        mEditPrice = findViewById(R.id.edit_price);
        TextView textOriginalPrice = findViewById(R.id.text_original_price);
        mEditOriginalPrice = findViewById(R.id.edit_original_price);
        TextView textSpecification = findViewById(R.id.text_specification);
        mEditSpecification = findViewById(R.id.edit_specification);
        mInfiniteSwitch = findViewById(R.id.infinite_switch);
        TextView textRepertory = findViewById(R.id.text_repertory);
        mEditRepertory = findViewById(R.id.edit_repertory);
        mAutoSwitch = findViewById(R.id.auto_switch);
        TextView textRepertoryMax = findViewById(R.id.text_repertory_max);
        mEditRepertoryMax = findViewById(R.id.edit_repertory_max);
        TextView textBoxes = findViewById(R.id.text_boxes);
        mEditBoxes = findViewById(R.id.edit_boxes);
        TextView textBoxesPrice = findViewById(R.id.text_boxes_price);
        mEditBoxesPrice = findViewById(R.id.edit_boxes_price);
        mEliteSwitch = findViewById(R.id.elite_switch);
        mTextTag = findViewById(R.id.text_tag);
        mBtnSavePutaway = findViewById(R.id.btn_save_putaway);
        titleBack.setOnClickListener(this);
        mTextCategory.setOnClickListener(this);
        mCenterKinds.setOnClickListener(this);
        titleStore.setOnClickListener(this);
        mImageStoreCameraOne.setOnClickListener(this);
        mImageStoreCameraTwo.setOnClickListener(this);
        imageStoreCameraThree.setOnClickListener(this);
        textPrice.setOnClickListener(this);
        textOriginalPrice.setOnClickListener(this);
        textSpecification.setOnClickListener(this);
        textRepertory.setOnClickListener(this);
        textRepertoryMax.setOnClickListener(this);
        textBoxes.setOnClickListener(this);
        textBoxesPrice.setOnClickListener(this);
        mTextTag.setOnClickListener(this);
        mBtnSavePutaway.setOnClickListener(this);
        mImageInDoorOne.setOnClickListener(this);
        mImageInDoorTwo.setOnClickListener(this);
        mImageInDoorThree.setOnClickListener(this);
        mAddGoodsTagFlow = findViewById(R.id.add_goods_tag_flow);
        mTextMonthSale = findViewById(R.id.text_month_sale);
        mTextTotalSale = findViewById(R.id.text_total_sale);
        mTextDelete = findViewById(R.id.text_delete);
        mTextDelete.setOnClickListener(this);
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        odId = intent.getStringExtra("odId");
        pgiId = intent.getStringExtra("pgiId");
        getGoodsInfo(odId, pgiId);
    }

    private GoodsDetailBean bean;

    private void getGoodsInfo(String odId, String pgiId) {
        Log.d("UpModifyGoodsActivity", odId);
        Log.d("UpModifyGoodsActivity", pgiId);
        Call<BaseResp<GoodsDetailBean>> call = HttpUtils.getInstance().getApiService(ApiService.class).getGoodsInfo(odId, pgiId);
        call.enqueue(new SSHCallBackNew<BaseResp<GoodsDetailBean>>() {
            @Override
            public void onSuccess(Response<BaseResp<GoodsDetailBean>> response) throws Exception {
                if (response != null) {
                    bean = response.body().getData();
                    packageClassId = String.valueOf(bean.getPackageClassId());
                    goodsClassId = String.valueOf(bean.getGoodsClassId());
                    showUrl = bean.getShowUrl();
                    mainUrl = bean.getMainUrl();
                    arg = bean.getArg();
                    mTextMonthSale.setText("月销量: " + bean.getMonthSales());
                    mTextTotalSale.setText("总销量: " + bean.getTotalSales());
                    mTextCategory.setText(bean.getGoodsClassName() + "");
                    Log.d("UpModifyGoodsActivity", bean.getPackageClassName());
                    mCenterKinds.setText(bean.getPackageClassName() + "");
                    mEdProductName.setText(bean.getName() + "");
                    mEdProductIntroduction.setText(bean.getDetails() + "");
                    mEditPrice.setText(bean.getMoney() + "");
                    mEditOriginalPrice.setText(bean.getOriginalPrice() + "");
                    mEditSpecification.setText(bean.getAttr() + "");
                    unitText.setText(bean.getArg() + "");
                    mEditRepertoryMax.setText(bean.getMaxStockNum() + "");
                    mEditBoxes.setText(bean.getBoxNum() + "");
                    mEditBoxesPrice.setText(bean.getBoxTotalPrice() + "");
                    mEditRepertory.setText(bean.getStockNum() + "");
                    if (bean.getStockStatus() == 1) {
                        mInfiniteSwitch.setOpened(true);
                    } else {
                        mInfiniteSwitch.setOpened(false);
                    }
                    if (bean.getStockAutoStatus() == 1) {
                        mAutoSwitch.setOpened(true);
                    } else {
                        mAutoSwitch.setOpened(false);
                    }
                    if (bean.getIsRecommend() == 1) {
                        mEliteSwitch.setOpened(true);
                    } else {
                        mEliteSwitch.setOpened(false);
                    }
                    List<String> stringList;
                    stringList = Arrays.asList(bean.getMainUrl().split(","));
                    Log.d("UpModifyGoodsActivity", "stringList:" + stringList);
                    if (stringList.size() == 3) {
                        Glide.with(me).load(stringList.get(0)).into(mImageInDoorOne);
                        Glide.with(me).load(stringList.get(1)).into(mImageInDoorTwo);
                        Glide.with(me).load(stringList.get(2)).into(mImageInDoorThree);
                    } else if (stringList.size() == 2) {
                        Glide.with(me).load(stringList.get(0)).into(mImageInDoorOne);
                        Glide.with(me).load(stringList.get(1)).into(mImageInDoorTwo);
                    } else {
                        Glide.with(me).load(stringList.get(0)).into(mImageInDoorOne);
                    }

                }
            }

            @Override
            public void onFail(String message) {
                Log.d("UpModifyGoodsActivity", message);
            }
        });
    }

    @Override
    public void onClick(View var1) {
        switch (var1.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.text_category:
                new XPopup.Builder(me).asCustom(new CategoryPopup(me)).show();
                break;
            case R.id.center_kinds:
                new XPopup.Builder(me).asCustom(new ComboPopup(me)).show();
                break;
            case R.id.title_store:
                break;
            case R.id.image_store_camera_one:
                ImagePicker.withMulti(new CustomImgPickerPresenter())
                        .mimeTypes(MimeType.ofImage())
                        .cropSaveInDCIM(false)
                        .setCropRatio(16, 9)
                        .cropRectMinMargin(50)
                        .cropStyle(CropConfig.STYLE_FILL)
                        .cropGapBackgroundColor(Color.TRANSPARENT)
                        .crop(this, new OnImagePickCompleteListener() {
                            @Override
                            public void onImagePickComplete(ArrayList<ImageItem> items) {
                                picItems.add(items.get(0));
                                ImageUtils.loadNormalImage(me, picItems.get(0).getPath(), mImageInDoorOne);
                                imageOne = picItems.get(0).getPath();
//                                CompressHelper.getDefault(this).compressToFile(picItems.get(0).getPath());
                            }
                        });
                break;
            case R.id.image_store_camera_two:
//                if (picItems.size() < 1) {
//                    ToastUtils.showToast("请按顺序选择图片");
//                    return;
//                }
                ImagePicker.withMulti(new CustomImgPickerPresenter())
                        .mimeTypes(MimeType.ofImage())
                        .cropSaveInDCIM(false)
                        .setCropRatio(16, 9)
                        .cropRectMinMargin(50)
                        .cropStyle(CropConfig.STYLE_FILL)
                        .cropGapBackgroundColor(Color.TRANSPARENT)
                        .crop(this, new OnImagePickCompleteListener() {
                            @Override
                            public void onImagePickComplete(ArrayList<ImageItem> items) {
                                picItems.add(items.get(0));
                                ImageUtils.loadNormalImage(me, picItems.get(1).getPath(), mImageInDoorTwo);
                                imageTwo = picItems.get(1).getPath();
                            }
                        });
                break;
            case R.id.image_store_camera_three:
//                if (picItems.size() < 2) {
//                    ToastUtils.showToast("请按顺序选择图片");
//                    return;
//                }
                ImagePicker.withMulti(new CustomImgPickerPresenter())
                        .mimeTypes(MimeType.ofImage())
                        .cropSaveInDCIM(false)
                        .setCropRatio(16, 9)
                        .cropRectMinMargin(50)
                        .cropStyle(CropConfig.STYLE_FILL)
                        .cropGapBackgroundColor(Color.TRANSPARENT)
                        .crop(this, new OnImagePickCompleteListener() {
                            @Override
                            public void onImagePickComplete(ArrayList<ImageItem> items) {
                                picItems.add(items.get(0));
                                ImageUtils.loadNormalImage(me, picItems.get(2).getPath(), mImageInDoorThree);
                                imageThree = picItems.get(2).getPath();
                            }
                        });
                break;
            case R.id.text_price:
                break;
            case R.id.text_original_price:
                break;
            case R.id.text_specification:
                break;
            case R.id.text_repertory:
                break;
            case R.id.text_repertory_max:
                break;
            case R.id.text_boxes:
                break;
            case R.id.text_boxes_price:
                break;
            case R.id.text_tag:
                new XPopup.Builder(me).asCustom(new TabPopup(me)).show();
                break;
            case R.id.btn_save:
                for (int i = 0; i < picItems.size(); i++) {
                    fileList.add(new File(picItems.get(i).getPath()));
                }
                LoadingDialog.showRoundProcessDialog(me);
                uploadPic(fileList, "2");
                break;
            case R.id.btn_save_putaway:
                for (int i = 0; i < picItems.size(); i++) {
                    fileList.add(new File(picItems.get(i).getPath()));
                }
                LoadingDialog.showRoundProcessDialog(me);
                if (picItems.size() > 0) {
                    uploadPic(fileList, "1");
                } else {
                    addGoodsInfo(bean.getShowUrl(), mainUrl, "1");
                }
                break;
            case R.id.text_unit:
                new XPopup.Builder(me).asCustom(new ArgsPopup(me)).show();
                break;

            case R.id.image_in_door_one:
                if (TextUtils.isEmpty(imageOne)) {
                    return;
                }
                new XPopup.Builder(me).asImageViewer(mImageInDoorOne, picItems.get(0).getPath(), new ImageLoader()).isShowSaveButton(false).show();
                break;
            case R.id.image_in_door_two:
                if (TextUtils.isEmpty(imageTwo)) {
                    return;
                }
                new XPopup.Builder(me).asImageViewer(mImageInDoorTwo, picItems.get(1).getPath(), new ImageLoader()).isShowSaveButton(false).show();
                break;
            case R.id.image_in_door_three:
                if (TextUtils.isEmpty(imageThree)) {
                    return;
                }
                new XPopup.Builder(me).asImageViewer(mImageInDoorThree, picItems.get(2).getPath(), new ImageLoader()).isShowSaveButton(false).show();
                break;
            default:
                break;
            case R.id.text_delete:
                new XPopup.Builder(me).asConfirm("提示", "确认删除这个菜品?",
                        new OnConfirmListener() {
                            @Override
                            public void onConfirm() {
                                deleteGoods();
                            }
                        }).show();

                break;
        }
    }

    private void deleteGoods() {
        Call<BaseResp<BeanRespReqEmpty>> call = HttpUtils.getInstance().getApiService(ApiService.class).deleteGoods(odId, pgiId);
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


    class ImageLoader implements XPopupImageLoader {
        @Override
        public void loadImage(int position, @NonNull Object uri, @NonNull ImageView imageView) {
            Glide.with(imageView).load(uri).into(imageView);
        }

        @Override
        public File getImageFile(@NonNull Context context, @NonNull Object uri) {
            return null;
        }
    }

    class CategoryPopup extends BottomPopupView {
        private RecyclerView recyclerView;
        private Context c;
        private List<String> lists;
        private TextView title;

        public CategoryPopup(Context context) {
            super(context);
            this.c = context;
        }

        @Override
        protected int getImplLayoutId() {
            return R.layout.custom_bottom_popup;
        }

        @Override
        protected void onCreate() {
            super.onCreate();
            title = findViewById(R.id.popup_title);
            recyclerView = findViewById(R.id.recyclerView);
            title.setText("请选择商品分类");
            Call<BaseResp<List<GoodsKindBean>>> call = HttpUtils.getInstance().getApiService(ApiService.class).getPackageGoodsClassList();
            call.enqueue(new SSHCallBackNew<BaseResp<List<GoodsKindBean>>>() {
                @Override
                public void onSuccess(Response<BaseResp<List<GoodsKindBean>>> response) throws Exception {
                    List<GoodsKindBean> lists = response.body().getData();
                    RecyclerAdapter<GoodsKindBean> adapter = new RecyclerAdapter<GoodsKindBean>(c, R.layout.item_store_type, lists) {
                        @Override
                        public void convert(RecyclerViewHolder holder, GoodsKindBean bean, int position) {
                            TextView title = holder.getView(R.id.title);
                            title.setText(bean.getName());
                            title.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mTextCategory.setText(bean.getName());
                                    packageClassId = String.valueOf(bean.getId());
                                    dismiss();
                                }
                            });
                        }
                    };
                    LinearLayoutManager manager = new LinearLayoutManager(c);
                    manager.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(manager);
                    recyclerView.setAdapter(adapter);
                }

                @Override
                public void onFail(String message) {
                    Log.d("1111111111", message);
                }

            });

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
            return (int) (XPopupUtils.getWindowHeight(getContext()) * .30f);
        }
    }

    class ComboPopup extends BottomPopupView {
        private RecyclerView recyclerView;
        private Context c;
        private List<String> lists;
        private TextView title;

        public ComboPopup(Context context) {
            super(context);
            this.c = context;
        }

        @Override
        protected int getImplLayoutId() {
            return R.layout.custom_bottom_popup;
        }

        @Override
        protected void onCreate() {
            super.onCreate();
            title = findViewById(R.id.popup_title);
            recyclerView = findViewById(R.id.recyclerView);
            title.setText("请选择商品种类");
            Call<BaseResp<List<PackageClassListBean>>> call = HttpUtils.getInstance().getApiService(ApiService.class).getPackageClassList();
            call.enqueue(new SSHCallBackNew<BaseResp<List<PackageClassListBean>>>() {
                @Override
                public void onSuccess(Response<BaseResp<List<PackageClassListBean>>> response) throws Exception {
                    List<PackageClassListBean> lists = response.body().getData();
                    RecyclerAdapter<PackageClassListBean> adapter = new RecyclerAdapter<PackageClassListBean>(c, R.layout.item_store_type, lists) {
                        @Override
                        public void convert(RecyclerViewHolder holder, PackageClassListBean bean, int position) {
                            TextView title = holder.getView(R.id.title);
                            title.setText(bean.getName());
                            title.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mCenterKinds.setText(bean.getName());
                                    goodsClassId = String.valueOf(bean.getId());
                                    dismiss();
                                }
                            });
                        }


                    };
                    LinearLayoutManager manager = new LinearLayoutManager(c);
                    manager.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(manager);
                    recyclerView.setAdapter(adapter);
                }

                @Override
                public void onFail(String message) {
                    Log.d("1111111111", message);
                }
            });

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
            return (int) (XPopupUtils.getWindowHeight(getContext()) * .30f);
        }
    }

    class ArgsPopup extends BottomPopupView {
        private RecyclerView recyclerView;
        private Context c;
        private List<String> lists;
        private TextView title;
        private ImageView closeImg;

        public ArgsPopup(Context context) {
            super(context);
            this.c = context;
        }

        @Override
        protected int getImplLayoutId() {
            return R.layout.custom_bottom_popup;
        }

        @Override
        protected void onCreate() {
            super.onCreate();
            title = findViewById(R.id.popup_title);
            recyclerView = findViewById(R.id.recyclerView);
            title.setText("请选择规格");
            Call<ArgsBean> call = HttpUtils.getInstance().getApiService(ApiService.class).getPackageArgsList();
            call.enqueue(new Callback<ArgsBean>() {
                @Override
                public void onResponse(Call<ArgsBean> call, Response<ArgsBean> response) {
                    List<String> lists = response.body().getData();
                    RecyclerAdapter<String> adapter = new RecyclerAdapter<String>(c, R.layout.item_store_type, lists) {
                        @Override
                        public void convert(RecyclerViewHolder holder, String bean, int position) {
                            TextView title = holder.getView(R.id.title);
                            title.setText(bean);
                            title.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    unitText.setText(bean);
                                    arg = bean;
                                    dismiss();
                                }
                            });
                        }
                    };
                    LinearLayoutManager manager = new LinearLayoutManager(c);
                    manager.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(manager);
                    recyclerView.setAdapter(adapter);
                }

                @Override
                public void onFailure(Call<ArgsBean> call, Throwable t) {

                }
            });

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
            return (int) (XPopupUtils.getWindowHeight(getContext()) * .30f);
        }
    }

    class TabPopup extends BottomPopupView {

        private RecyclerView recyclerView;
        private List<String> lists;
        private TextView tv;
        private ImageView closeImg;
        private Context c;
        private TagAdapter<String> mTagAdapter;
        private TagAdapter<String> mTagAdapter2;
        private TagFlowLayout flowLayout2;
        private Button saveBtn;

        public TabPopup(@NonNull Context context) {
            super(context);
            this.c = context;
        }

        @Override
        protected int getImplLayoutId() {
            return R.layout.popup_tab;
        }

        @Override
        protected void onCreate() {
            super.onCreate();
            recyclerView = findViewById(R.id.recyclerView);
            closeImg = findViewById(R.id.image_close);
            flowLayout2 = findViewById(R.id.tab_up_tab);
            saveBtn = findViewById(R.id.text_save);
            closeImg.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
            Call<BaseResp<List<GoodsLabelBean>>> call = HttpUtils.getInstance().getApiService(ApiService.class).getPackageGoodsLabelList();
            call.enqueue(new SSHCallBackNew<BaseResp<List<GoodsLabelBean>>>() {
                @Override
                public void onSuccess(Response<BaseResp<List<GoodsLabelBean>>> response) throws Exception {
                    List<GoodsLabelBean> bean = response.body().getData();
                    List<String> lists = new ArrayList<>();
                    for (int i = 0; i < bean.size(); i++) {
                        lists.add(bean.get(i).getName());
                    }
                    TagFlowLayout flowLayout = findViewById(R.id.tab_bottom_tab);
                    LayoutInflater mInflater = LayoutInflater.from(c);
                    flowLayout.setAdapter(mTagAdapter = new TagAdapter<String>(lists) {
                        @Override
                        public View getView(FlowLayout parent, int position, String user) {
                            tv = (TextView) mInflater.inflate(R.layout.tv, flowLayout, false);
                            tv.setText(lists.get(position));
                            return tv;
                        }
                    });
                    newLists = new ArrayList<>();
                    flowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                        @Override
                        public boolean onTagClick(View view, int position, FlowLayout parent) {
                            newLists.add(lists.get(position));
                            idLists.add(bean.get(position).getId());
                            LayoutInflater mInflater = LayoutInflater.from(c);
                            flowLayout2.setAdapter(mTagAdapter2 = new TagAdapter<String>(newLists) {
                                @Override
                                public View getView(FlowLayout parent, int position, String user) {
                                    tv = (TextView) mInflater.inflate(R.layout.tv, flowLayout2, false);
                                    tv.setText(newLists.get(position));
                                    return tv;
                                }
                            });

                            return false;
                        }
                    });
                }

                @Override
                public void onFail(String message) {
                    Log.d("AddGoodsActivity", message);
                }


            });
            saveBtn.setOnClickListener(new OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onClick(View v) {
                    mAddGoodsTagFlow.setVisibility(VISIBLE);
                    mTextTag.setVisibility(View.GONE);
                    mAddGoodsTagFlow.setAdapter(mTagAdapter = new TagAdapter<String>(newLists) {
                        @Override
                        public View getView(FlowLayout parent, int position, String user) {
                            LayoutInflater mInflater = LayoutInflater.from(c);
                            mTextView = (TextView) mInflater.inflate(R.layout.tv, mAddGoodsTagFlow, false);
                            mTextView.setText(newLists.get(position));
                            return mTextView;
                        }
                    });
                    labelIds = String.join(",", idLists);
                    dismiss();
                }
            });

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
            return (int) (XPopupUtils.getWindowHeight(getContext()) * .60f);
        }
    }

    List<String> newLists;
    List<String> idLists = new ArrayList<>();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == 001) {
            mFileOne = ImageUtils.getPhotoFromResult(this, data);
            ImageUtils.loadNormalImage(this, mFileOne, mImageInDoorOne);
        } else if (requestCode == 002) {
            mFileTwo = ImageUtils.getPhotoFromResult(this, data);
            ImageUtils.loadNormalImage(this, mFileTwo, mImageInDoorTwo);
        } else if (requestCode == 003) {
            mFileThree = ImageUtils.getPhotoFromResult(this, data);
            ImageUtils.loadNormalImage(this, mFileTwo, mImageInDoorThree);
        } else if (requestCode == 1433) {
            Log.d("AddGoodsActivity", "");

        }
    }


    private void addGoodsInfo(String showUrl, String mainUrl, String isRelease) {
        if (mInfiniteSwitch.isOpened()) {
            stockStatus = "1";
        } else {
            stockStatus = "0";
        }
        if (mAutoSwitch.isOpened()) {
            stockAutoStatus = "1";
        } else {
            stockAutoStatus = "2";
        }
        if (mEliteSwitch.isOpened()) {
            isRecommend = "1";
        } else {
            isRecommend = "0";
        }
        Call<BaseResp<BeanRespReqEmpty>> call = HttpUtils.getInstance().getApiService(ApiService.class).editGoodsInfo(
                pgiId, odId, packageClassId, goodsClassId, mEdProductName.getText().toString(), mEdProductIntroduction.getText().toString()
                , showUrl, mainUrl, mEditPrice.getText().toString(), mEditOriginalPrice.getText().toString(), mEditSpecification.getText().toString()
                , arg, stockStatus, mEditRepertory.getText().toString(), stockAutoStatus, mEditRepertoryMax.getText().toString()
                , mEditBoxes.getText().toString(), mEditBoxesPrice.getText().toString(), isRelease, isRecommend, labelIds);
        call.enqueue(new SSHCallBackNew<BaseResp<BeanRespReqEmpty>>() {
            @Override
            public void onSuccess(Response<BaseResp<BeanRespReqEmpty>> response) throws Exception {
                if (response != null) {
                    String msg = response.body().getMsg();
                    ToastUtils.showToast(msg);
                    LoadingDialog.dismissDialog();
                    finish();
                }
            }

            @Override
            public void onFail(String message) {
                ToastUtils.showToast(message);
                LoadingDialog.dismissDialog();
                Log.d("failed", message);
            }
        });
    }

    /**
     * 检测是否有权限
     */
    private void checkPermission(int type) {
        if (Build.VERSION.SDK_INT > 22) {
            int cameraPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
            int recordPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
            ArrayList<String> permissions = new ArrayList<>();
            if (cameraPermission != PermissionChecker.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.CAMERA);
            }
            if (recordPermission != PermissionChecker.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
            if (permissions.size() > 0) {
                requestPermissions(permissions.toArray(new String[permissions.size()]), 0);
            } else {
                ImageUtils.takeOrChoosePhoto(this, type, type);
            }
        } else {
            ImageUtils.takeOrChoosePhoto(this, type, type);
        }
    }

    public String encode(String value) throws Exception {
        return URLEncoder.encode(value, "utf-8");
    }


    private void uploadPic(List<File> files, String isRelease) {
        List<MultipartBody.Part> files2 = new ArrayList<>();
        //TODO 上传多张图片
        for (int i = 0; i < files.size(); i++) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), files.get(i));
            MultipartBody.Part form = null;
            try {
                form = MultipartBody.Part.createFormData("files", encode(files.get(i).getName()), requestBody);
            } catch (Exception e) {
                e.printStackTrace();
            }
            files2.add(form);
        }
        Call<FilesUpload> call = HttpUtils.getInstance().getApiService(ApiService.class).upload(files2);
        call.enqueue(new Callback<FilesUpload>() {
            @Override
            public void onResponse(Call<FilesUpload> call, Response<FilesUpload> response) {
                picItems.clear();
                if (response.body().getData() != null) {
                    picUrl = response.body().getData();
                    if (!TextUtils.isEmpty(picUrl)) {
                        picUrlList = Arrays.asList(picUrl.split(","));
                        showUrl = picUrlList.get(0);
                        Log.d("AddGoodsActivity", showUrl);
                        Log.d("AddGoodsActivity", picUrl);
                        addGoodsInfo(showUrl, picUrl, isRelease);
                    } else {
                        LoadingDialog.dismissDialog();
                        ToastUtils.showToast("图片上传失败");
                    }
                }

            }

            @Override
            public void onFailure(Call<FilesUpload> call, Throwable t) {

            }
        });
    }
}