package com.sheepshop.businessside.ui.openshop;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.ImageViewerPopupView;
import com.lxj.xpopup.interfaces.OnSrcViewUpdateListener;
import com.lxj.xpopup.interfaces.XPopupImageLoader;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.nanchen.compresshelper.CompressHelper;
import com.sheepshop.businessside.MyApp;
import com.sheepshop.businessside.R;
import com.sheepshop.businessside.adapter.PublishCommentAdapter;
import com.sheepshop.businessside.adapter.StoreQuickinputAdapter;
import com.sheepshop.businessside.base.Constant;
import com.sheepshop.businessside.entity.BeanRespReqEmpty;
import com.sheepshop.businessside.entity.FilesUpload;
import com.sheepshop.businessside.entity.QuickinputEntity;
import com.sheepshop.businessside.network.netbean.HttpBean;
import com.sheepshop.businessside.network.netbean.ResponseBean;
import com.sheepshop.businessside.network.netinterface.BaseCallBack;
import com.sheepshop.businessside.okhttp.BaseResp;
import com.sheepshop.businessside.okhttp.HttpUtils;
import com.sheepshop.businessside.okhttp.SSHCallBackNew;
import com.sheepshop.businessside.service.ApiService;
import com.sheepshop.businessside.tool.ToastUtils;
import com.sheepshop.businessside.utils.HttpRequestParamsBuilder;
import com.sheepshop.businessside.utils.ImageUtils;
import com.sheepshop.businessside.utils.NetUtils;
import com.sheepshop.businessside.weight.LoadingDialog;

import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ch.chtool.base.BaseActivity;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sheepshop.businessside.ui.openshop.TestPicActivity.IMAGE_PICKER;
import static com.sheepshop.businessside.utils.ToastUtils.showShort;

/**
 * Created by CH
 * on 2019/11/13 14:38
 */
public class StoreInfoActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mIvShopLogo;
    private ImageView returnImg;
    private ImageView mIvShopLogoShowPic;
    private ImageView mIvShopLogoDeletePic;
    private RelativeLayout mRelaShopLogo;
    private ImageView mIvShopFront;
    private ImageView mIvShopFrontShowPic;
    private ImageView mIvShopFrontDeletePic;
    private RelativeLayout mRelaShopFront;
    private ImageView mIvStoreInside;
    private RecyclerView mRvStoreInsideShowPic;
    private EditText mEdStoreNote;
    private RecyclerView mRvQuickInput;
    private Button mBtnNextInfo;
    private File mFileivShopLogo, mFileivShopFront;
    private PublishCommentAdapter mAdapter;
    private StoreQuickinputAdapter storeQuickinputAdapter;
    private List<String> mDataFileString;
    private static final int GET_ADD_PIC = 101;
    private static final int GET_OPEN_CAMERA = 102;
    private File mediaFile;
    private List<QuickinputEntity> quickinputEntityList = new ArrayList<>();
    private SharedPreferences npt;
    private SharedPreferences shopInfo;
    private int buId;
    private String picUrl;
    private List<String> picUrlList;
    private List<File> mFileList;
    private String shop_name, shop_owner, shop_tel, shop_address, open_time, close_time, latitude, longitude, area_code;
    private int shop_kinds;
    private List<String> strList = new ArrayList<>();
    private String aaa;
    private LoadingDialog mDialog;
    private long nowCount;
    private int maxPhotoNum = 3;

    @Override
    public int initLayout() {
        return R.layout.activity_shop_store_information;
    }

    @Override
    public void initView() {
        returnImg = findViewById(R.id.title_back);
        returnImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mIvShopLogo = findViewById(R.id.iv_shop_logo);
        mIvShopLogoShowPic = findViewById(R.id.iv_shop_logo_showPic);
        mIvShopLogoDeletePic = findViewById(R.id.iv_shop_logo_deletePic);
        mRelaShopLogo = findViewById(R.id.rela_shop_logo);
        mIvShopFront = findViewById(R.id.iv_shop_front);
        mIvShopFrontShowPic = findViewById(R.id.iv_shop_front_showPic);
        mIvShopFrontDeletePic = findViewById(R.id.iv_shop_front_deletePic);
        mRelaShopFront = findViewById(R.id.rela_shop_front);
        mIvStoreInside = findViewById(R.id.iv_store_inside);
        mRvStoreInsideShowPic = findViewById(R.id.rv_store_inside_showPic);
        mEdStoreNote = findViewById(R.id.ed_store_note);
        mRvQuickInput = findViewById(R.id.rv_Quick_input);
        mBtnNextInfo = findViewById(R.id.btn_next_info);
        mIvShopLogo.setOnClickListener(this);
        mIvShopLogoShowPic.setOnClickListener(this);
        mIvShopLogoDeletePic.setOnClickListener(this);
        mRelaShopLogo.setOnClickListener(this);
        mIvShopFront.setOnClickListener(this);
        mIvShopFrontShowPic.setOnClickListener(this);
        mIvShopFrontDeletePic.setOnClickListener(this);
        mRelaShopFront.setOnClickListener(this);
        mIvStoreInside.setOnClickListener(this);
        mEdStoreNote.setOnClickListener(this);
        mRvQuickInput.setOnClickListener(this);
        mBtnNextInfo.setOnClickListener(this);
        mFileList = new ArrayList<>();
        mFileList.add(CompressHelper.getDefault(this).compressToFile(MyApp.getShopInfoBean().getIdCardFront()));
        mFileList.add(CompressHelper.getDefault(this).compressToFile(MyApp.getShopInfoBean().getIdCardBehind()));
        mFileList.add(CompressHelper.getDefault(this).compressToFile(MyApp.getShopInfoBean().getLicense()));
        mFileList.add(CompressHelper.getDefault(this).compressToFile(MyApp.getShopInfoBean().getLicence()));
        mDialog = new LoadingDialog();
        TextView nowCountTv = findViewById(R.id.now_count);
        mEdStoreNote.setSelection(mEdStoreNote.getText().toString().length());
        mEdStoreNote.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                mEdStoreNote.setSelection(s.toString().length());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mEdStoreNote.setSelection(s.toString().length());
            }

            @Override
            public void afterTextChanged(Editable s) {
                nowCount = calculateLengthIgnoreCnOrEn(s.toString());
                if (nowCount < 100) {
                    nowCountTv.setText(nowCount + "/100");
                } else {
                    nowCountTv.setText("100/100");
                }
                strList.clear();
                strList.add(s.toString());
                mEdStoreNote.setSelection(s.toString().length());
            }
        });
        initPhoto();
    }

    private void initPhoto() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new PicassoImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(false);  //显示拍照按钮
        imagePicker.setCrop(true);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setSelectLimit(maxPhotoNum);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素
    }


    private int calculateLengthIgnoreCnOrEn(CharSequence c) {
        int len = 0;
        for (int i = 0; i < c.length(); i++) {
            len++;
        }
        return len;
    }

    @Override
    public void initData() {
        npt = getSharedPreferences("user_npt", MODE_PRIVATE);
        buId = npt.getInt("buId", 0);
        shopInfo = getSharedPreferences("shop_info", MODE_PRIVATE);
        shop_name = shopInfo.getString("shop_name", "");
        shop_owner = shopInfo.getString("shop_owner", "");
        shop_tel = shopInfo.getString("shop_tel", "");
        shop_address = shopInfo.getString("shop_address", "");
        shop_name = shopInfo.getString("shop_name", "");
        open_time = shopInfo.getString("open_time", "");
        close_time = shopInfo.getString("close_time", "");
        latitude = shopInfo.getString("latitude", "");
        longitude = shopInfo.getString("longitude", "");
        shop_kinds = shopInfo.getInt("shop_kinds", 0);
        area_code = shopInfo.getString("area_code", "");

        mDataFileString = new ArrayList<>();
        mAdapter = new PublishCommentAdapter(mDataFileString);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false);
        //添加自定义的分割线
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(this, R.drawable.rv_divider)));
        mRvStoreInsideShowPic.addItemDecoration(divider);
        mRvStoreInsideShowPic.setLayoutManager(layoutManager);
        mRvStoreInsideShowPic.setAdapter(mAdapter);
        mAdapter.bindToRecyclerView(mRvStoreInsideShowPic);
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.rl_item_activity_publishComment_addPic) {
                //添加图片
                onIvActivityPublishCommentAddPicClicked();
            } else if (view.getId() == R.id.iv_item_activity_publishComment_showPic) {
                ImageView imageView = findViewById(R.id.iv_item_activity_publishComment_showPic);
                List<Object> mLists = new ArrayList<>(mDataFileString);
                new XPopup.Builder(StoreInfoActivity.this).asImageViewer(imageView, position, mLists, new OnSrcViewUpdateListener() {
                    @Override
                    public void onSrcViewUpdate(ImageViewerPopupView popupView, int position) {
                        popupView.updateSrcView(imageView);
                    }
                }, new ImageLoader()).isShowSaveButton(false).show();
            } else {
                //删除图片
//                if (!"".equals(mDataFileString.get(mDataFileString.size() - 1))) {
//                    mDataFileString.add("");
//                }
                mDataFileString.remove(position);
                maxPhotoNum = 3 - mDataFileString.size();
                initPhoto();
                mAdapter.notifyDataSetChanged();
            }
        });
        getQuickInput();
    }

    class ImageLoader implements XPopupImageLoader {
        @Override
        public void loadImage(int position, @NonNull Object uri, @NonNull ImageView imageView) {
            Glide.with(imageView).load(uri).into(imageView);
        }

        @Override
        public File getImageFile(@NonNull Context context, @NonNull Object uri) {
            //必须实现这个方法，返回uri对应的缓存文件，可参照下面的实现，内部保存图片会用到。如果你不需要保存图片这个功能，可以返回null。
            return null;
        }
    }

    @Override
    public void onClick(View var1) {
        switch (var1.getId()) {
            case R.id.iv_shop_logo:
                checkPermission(5);
                break;
            case R.id.iv_shop_logo_showPic:
                new XPopup.Builder(StoreInfoActivity.this).asImageViewer(mIvShopLogoShowPic, mFileivShopLogo, new ImageLoader()).isShowSaveButton(false).show();
                break;
            case R.id.iv_shop_logo_deletePic:
                mRelaShopLogo.setVisibility(View.GONE);
                break;
            case R.id.rela_shop_logo:
                break;
            case R.id.iv_shop_front:
                checkPermission(6);
                break;
            case R.id.iv_shop_front_showPic:
                new XPopup.Builder(StoreInfoActivity.this).asImageViewer(mIvShopFrontShowPic, mFileivShopFront, new ImageLoader()).isShowSaveButton(false).show();
                break;
            case R.id.iv_shop_front_deletePic:
                mRelaShopFront.setVisibility(View.GONE);
                break;
            case R.id.rela_shop_front:
                break;
            case R.id.iv_store_inside:
                onIvActivityPublishCommentAddPicClicked();
//                ToastUtils.showToast("1111111");
                break;
            case R.id.ed_store_note:
                break;
            case R.id.rv_Quick_input:
                break;
            case R.id.btn_next_info:
                for (int i = 0; i < mDataFileString.size(); i++) {
                    mFileList.add(CompressHelper.getDefault(this).compressToFile(new File(mDataFileString.get(i))));
                }
                if (mFileList.size() == 9) {
                    LoadingDialog.showRoundProcessDialog(StoreInfoActivity.this);
                    uploadPic();
                } else {
                    ToastUtils.showToast("请选择图片!");
                }
                break;
            default:
                break;
        }
    }

    public String encode(String value) throws Exception {
        return URLEncoder.encode(value, "utf-8");
    }

    private void uploadPic() {
        List<MultipartBody.Part> files2 = new ArrayList<>();
        //TODO 上传多张图片
        for (int i = 0; i < mFileList.size(); i++) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), mFileList.get(i));
            MultipartBody.Part form = null;
            try {
                form = MultipartBody.Part.createFormData("files", encode(mFileList.get(i).getName()), requestBody);
            } catch (Exception e) {
                e.printStackTrace();
            }
            files2.add(form);
        }
        Call<FilesUpload> call = HttpUtils.getInstance().getApiService(ApiService.class).upload(files2);
        call.enqueue(new Callback<FilesUpload>() {
            @Override
            public void onResponse(Call<FilesUpload> call, Response<FilesUpload> response) {
                mFileList.clear();
                picUrl = response.body().getData();
                Log.d("StoreInfoActivity", picUrl + "");
                if (!TextUtils.isEmpty(picUrl)) {
                    picUrlList = Arrays.asList(picUrl.split(","));
                    addStore();
                } else {
                    LoadingDialog.dismissDialog();
                    ToastUtils.showToast("图片上传失败");
                }
            }

            @Override
            public void onFailure(Call<FilesUpload> call, Throwable t) {
                mFileList.clear();
                LoadingDialog.dismissDialog();
                Log.d("StoreInfoActivity", "*************ERROR****************");
            }
        });
    }


    private void addStore() {
        String dcDetailpic = picUrlList.get(6) + "," + picUrlList.get(7) + "," + picUrlList.get(8);

        Call<BaseResp<BeanRespReqEmpty>> call = HttpUtils.getInstance().getApiService(ApiService.class).addStore(
                shop_name, buId, shop_owner, area_code
                , shop_address, longitude, latitude
                , picUrlList.get(4), picUrlList.get(5), dcDetailpic
                , shop_tel, open_time, close_time
                , shop_kinds, picUrlList.get(0), picUrlList.get(1), picUrlList.get(3), picUrlList.get(2)
                , mEdStoreNote.getText().toString());
        call.enqueue(new SSHCallBackNew<BaseResp<BeanRespReqEmpty>>() {
            @Override
            public void onSuccess(Response<BaseResp<BeanRespReqEmpty>> response) throws Exception {
                LoadingDialog.dismissDialog();
                String msg = response.body().getMsg();
                String code = response.body().getCode();
                if ("0".equals(code)) {
                    startActivity(AuditCountDownActivity.class);
                } else {
                    ToastUtils.showToast(msg);
                }
                Log.d("StoreInfoActivity", "msg:" + msg);

            }

            @Override
            public void onFail(String message) {
                LoadingDialog.dismissDialog();
                Log.d("StoreInfoActivity", "message:" + message);
            }
        });
    }

    private void getQuickInput() {
        Map<String, String> params = new HttpRequestParamsBuilder().build();
        params.put("type", "1");
        NetUtils.post(Constant.URL_DIC, params, QuickinputEntity.class, HttpBean.getResDatatypeBeanlist(), new BaseCallBack() {
            @Override
            public void onRequesting() {

            }

            @Override
            public void onSuccess(ResponseBean data) {
                if (data.getData() == null) {
                    return;
                }
                quickinputEntityList = (List<QuickinputEntity>) data.pullData();
                storeQuickinputAdapter = new StoreQuickinputAdapter(quickinputEntityList);
                RecyclerView.LayoutManager layoutManagerQuickInput = new LinearLayoutManager(StoreInfoActivity.this, LinearLayoutManager.VERTICAL, false);
                mRvQuickInput.setLayoutManager(layoutManagerQuickInput);
                mRvQuickInput.setAdapter(storeQuickinputAdapter);
                storeQuickinputAdapter.bindToRecyclerView(mRvQuickInput);
                storeQuickinputAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                        mEdStoreNote.setText(quickinputEntityList.get(position).getLabelDetail());
                        strList.add(quickinputEntityList.get(position).getLabelDetail());
                        if (strList != null) {
                            aaa = String.join("\n", getNewList(strList));
                        } else {
                            aaa = String.join("", getNewList(strList));
                        }
                        mEdStoreNote.setText(aaa);
                    }
                });

            }

            @Override
            public void onError(ResponseBean msg) {
                ToastUtils.showToast(msg.getMsg());
            }

            @Override
            public void onErrorForOthers(ResponseBean msg) {
                ToastUtils.showToast(msg.getMsg());
            }
        });

    }

    public List<String> getNewList(List<String> li) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < li.size(); i++) {
            String str = li.get(i);  //获取传入集合对象的每一个元素
            if (!list.contains(str)) {   //查看新集合中是否有指定的元素，如果没有则加入
                list.add(str);
            }
        }
        return list;  //返回集合
    }

    /**
     * 检测是否有权限
     */
    private void checkPermission(int type) {
        if (Build.VERSION.SDK_INT > 22) {
            int cameraPermission = ActivityCompat.checkSelfPermission(StoreInfoActivity.this, Manifest.permission.CAMERA);
            int recordPermission = ActivityCompat.checkSelfPermission(StoreInfoActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
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

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
//            if (data != null && requestCode == IMAGE_PICKER) {
//                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
//                Log.d("11111131111", images.get(0).toString());
//                Log.d("111111311112", images.get(0).path);
//                for (int i = 0; i < images.size(); i++) {
//                    mDataFileString.add(images.get(i).path);
//                    mFileList.add(CompressHelper.getDefault(this).compressToFile(new File(images.get(i).path)));
//                }
//                judgeAddPic();
//            } else {
//                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//
//        if (resultCode != Activity.RESULT_OK) {
//            return;
//        }

        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == IMAGE_PICKER) {
//                mDataFileString.clear();
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                for (int i = 0; i < images.size(); i++) {
                    mDataFileString.add(images.get(i).path);

                }

                judgeAddPic();
            } else {
                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == 5) {
            mFileivShopLogo = ImageUtils.getPhotoFromResult(this, data);
            mRelaShopLogo.setVisibility(View.VISIBLE);
            ImageUtils.loadNormalImage(this, mFileivShopLogo, mIvShopLogoShowPic);
            mFileList.add(CompressHelper.getDefault(this).compressToFile(mFileivShopLogo));
        } else if (requestCode == 6) {
            mFileivShopFront = ImageUtils.getPhotoFromResult(this, data);
            mRelaShopFront.setVisibility(View.VISIBLE);
            ImageUtils.loadNormalImage(this, mFileivShopFront, mIvShopFrontShowPic);
            mFileList.add(CompressHelper.getDefault(this).compressToFile(mFileivShopFront));
        }
//        } else if (data != null && requestCode == IMAGE_PICKER) {
//            Log.d("1111111111111", "images.get(i).path");
//            ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
////                String filePath = ImageUtils.getPhotoFromResult(this, data).getAbsolutePath();
////                if (filePath.endsWith("webp")) {
////                    showShort(this, "文件格式暂不支持");
////                    return;
////                }
//            for (int i = 0; i < images.size(); i++) {
//                mDataFileString.add(images.get(i).path);
//                mFileList.add(CompressHelper.getDefault(this).compressToFile(new File(images.get(i).path)));
//                Log.d("1111111111111", images.get(i).path);
//            }
////                    for (int i = 0; i < mDataFileString.size(); i++) {
////                    }
//            judgeAddPic();
//        } else {
//            Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
//
//        }
//        switch (requestCode) {
//            case GET_ADD_PIC:
//                if (data != null) {
//                    String filePath = ImageUtils.getPhotoFromResult(this, data).getAbsolutePath();
//                    if (filePath.endsWith("webp")) {
//                        showShort(this, "文件格式暂不支持");
//                        return;
//                    }
//                    mDataFileString.add(filePath);
////                    for (int i = 0; i < mDataFileString.size(); i++) {
//                    mFileList.add(CompressHelper.getDefault(this).compressToFile(new File(filePath)));
////                    }
//                    judgeAddPic();
//                }
//                break;
//            case GET_OPEN_CAMERA:
//                mDataFileString.add(mediaFile.getAbsolutePath());
//                judgeAddPic();
//                break;
//            default:
//                break;
//        }
    }

    private void judgeAddPic() {
        for (int i = 0; i < mDataFileString.size(); i++) {
            if ("".equals(mDataFileString.get(i))) {
                mDataFileString.remove(i);
            }
        }
        if (mDataFileString.size() < 3 && !"".equals(mDataFileString.get(mDataFileString.size() - 1))) {
            mDataFileString.add("");
        }
        mAdapter.notifyDataSetChanged();
    }

    private void choosePic() {
        if (mDataFileString.size() > 2 && !"".equals(mDataFileString.get(mDataFileString.size() - 1))) {
            showShort(this, "最多只能上传三张图片哦");
            return;
        }
        final Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(galleryIntent, GET_ADD_PIC);
    }

    public void onIvActivityPublishCommentAddPicClicked() {
        if (Build.VERSION.SDK_INT > 22) {
            int permission = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            if (permission != PermissionChecker.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, GET_ADD_PIC);
            } else if (mDataFileString.size() < 3) {
                Intent intent = new Intent(StoreInfoActivity.this, ImageGridActivity.class);
                startActivityForResult(intent, IMAGE_PICKER);
            } else {
                ToastUtils.showToast("最多选择三张图片");
            }
        } else if (mDataFileString.size() < 3) {
            Intent intent = new Intent(StoreInfoActivity.this, ImageGridActivity.class);
            startActivityForResult(intent, IMAGE_PICKER);
        } else {
            ToastUtils.showToast("最多选择三张图片");
        }
    }

    public static MultipartBody filesToMultipartBody(List<File> files) {
        MultipartBody.Builder builder = new MultipartBody.Builder();

        for (File file : files) {
            // TODO: 16-4-2  这里为了简单起见，没有判断file的类型
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
            builder.addFormDataPart("file", file.getName(), requestBody);
        }

        builder.setType(MultipartBody.FORM);
        MultipartBody multipartBody = builder.build();
        return multipartBody;
    }

    public static List<MultipartBody.Part> filesToMultipartBodyParts(List<File> files) {
        List<MultipartBody.Part> parts = new ArrayList<>(files.size());
        for (File file : files) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
            parts.add(part);
        }
        return parts;
    }


}


