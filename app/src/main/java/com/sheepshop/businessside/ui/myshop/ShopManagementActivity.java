package com.sheepshop.businessside.ui.myshop;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.liquorslib.view.LTextView;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.animator.PopupAnimator;
import com.lxj.xpopup.core.CenterPopupView;
import com.lxj.xpopup.interfaces.XPopupImageLoader;
import com.nanchen.compresshelper.CompressHelper;
import com.sheepshop.businessside.MyApp;
import com.sheepshop.businessside.R;
import com.sheepshop.businessside.base.Constant;
import com.sheepshop.businessside.entity.BeanRespReqEmpty;
import com.sheepshop.businessside.entity.FilesUpload;
import com.sheepshop.businessside.entity.InfoBean;
import com.sheepshop.businessside.entity.QuickinputEntity;
import com.sheepshop.businessside.network.netbean.HttpBean;
import com.sheepshop.businessside.network.netbean.ResponseBean;
import com.sheepshop.businessside.network.netinterface.BaseCallBack;
import com.sheepshop.businessside.okhttp.BaseResp;
import com.sheepshop.businessside.okhttp.HttpUtils;
import com.sheepshop.businessside.okhttp.SSHCallBackNew;
import com.sheepshop.businessside.service.ApiService;
import com.sheepshop.businessside.utils.HttpRequestParamsBuilder;
import com.sheepshop.businessside.utils.ImageUtils;
import com.sheepshop.businessside.utils.NetUtils;
import com.sheepshop.businessside.utils.ToastUtils;
import com.sheepshop.businessside.weight.LinesEditView;
import com.sheepshop.businessside.weight.LoadingDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.PermissionChecker;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ch.chtool.base.BaseActivity;
import ch.chtool.utils.RecyclerAdapter;
import ch.chtool.utils.RecyclerViewHolder;
import ch.chtool.view.MyGridView;
import cn.addapp.pickers.picker.TimePicker;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by CH
 * on 2019/11/8 09:43
 */
public class ShopManagementActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mTitleBack;
    private RelativeLayout mTitle;
    private LTextView mTitleText;
    private TextView mShopName;
    private TextView mShopState;
    private TextView mShopKinds;
    private TextView mShopOwner;
    private TextView mTitleShopTel;
    private TextView mShopTel;
    private TextView mShopAddress;
    private ImageView mImageLogo;
    private ImageView mImageLogoCamera;
    private ImageView mImageDoor;
    private ImageView mImageDoorCamera;
    private TextView mTitleStore;
    private MyGridView mImageStoreGrid;
    private ImageView mImageStoreCamera;
    private TextView mTitleShopTime;
    private EditText mShopTime;
    private TextView mTitleShopRemark;
    private TextView mShopRemark;
    private ImageView mImageFront;
    private ImageView mImageReverse;
    private ImageView mImageLicense;
    private ImageView mImageLicence;
    private Button mBtnMineShop;
    private TextView mTextLogo;
    private TextView mTextDoor;
    private ImageView mImageInDoorOne;
    private ImageView mImageStoreCameraOne;
    private ImageView mImageInDoorTwo;
    private ImageView mImageStoreCameraTwo;
    private ImageView mImageInDoorThree;
    private ImageView mImageStoreCameraThree;
    private List<File> mFileList = new ArrayList<>();
    private File mFileIvLogo;
    private File mFileIvDoor;
    private File mFileIvInDoorOne;
    private File mFileIvInDoorTwo;
    private File mFileIvInDoorThree;
    private String picUrl;
    private List<String> picUrlList;
    private String logoUrl = "";
    private String doorUrl = "";
    private String iDUrlOne = "";
    private String iDUrlTwo = "";
    private String iDUrlThree = "";
    private TextView mEdStoreOpenTime;
    private TextView mEdStoreCloseTime;
    private String openTime, closeTime;
    private InfoBean bean;
    private ImageView mImgUpdateTel;
    private int urlType;
    private List<String> imgUrlList = new ArrayList<>();
    private List<Integer> typeList = new ArrayList<>();
    private List<QuickinputEntity> quickinputEntityList = new ArrayList<>();
    private RecyclerAdapter storeQuickinputAdapter;
    private RecyclerView mRvQuickInput;
    private EditText mRemarkEdit;
    private ImageView mImgRemark;
    private int bdId;
    private SharedPreferences readInfo;
    private SharedPreferences.Editor editor;
    private List<String> picLists;
    private ImageView timeImg;


    @Override
    public int initLayout() {
        return R.layout.activity_shop_managerment;
    }

    @Override
    public void initView() {
        mTitleBack = findViewById(R.id.title_back);
        mTitle = findViewById(R.id.title);
        mTitleBack.setOnClickListener(this);
        mShopName = findViewById(R.id.shop_name);
        mShopState = findViewById(R.id.shop_state);
        mShopKinds = findViewById(R.id.shop_kinds);
        mShopOwner = findViewById(R.id.shop_owner);
        mTitleShopTel = findViewById(R.id.title_shop_tel);
        mShopTel = findViewById(R.id.shop_tel);
        mShopAddress = findViewById(R.id.shop_address);
        mImageLogo = findViewById(R.id.image_logo);
        mImageLogo.setOnClickListener(this);
        mImageLogoCamera = findViewById(R.id.image_logo_camera);
        mImageDoor = findViewById(R.id.image_door);
        mImageDoor.setOnClickListener(this);
        mImageDoorCamera = findViewById(R.id.image_door_camera);
        mTitleStore = findViewById(R.id.title_store);
        mTitleShopTime = findViewById(R.id.title_shop_time);
        mTitleShopRemark = findViewById(R.id.title_shop_remark);
        mShopRemark = findViewById(R.id.shop_remark);
        mImageFront = findViewById(R.id.image_front);
        mImageReverse = findViewById(R.id.image_reverse);
        mImageLicense = findViewById(R.id.image_license);
        mImageLicence = findViewById(R.id.image_licence);
        mBtnMineShop = findViewById(R.id.btn_mine_shop);
        mBtnMineShop.setOnClickListener(this);
        mTextLogo = findViewById(R.id.text_logo);
        mTextDoor = findViewById(R.id.text_door);
        mImageInDoorOne = findViewById(R.id.image_in_door_one);
        mImageStoreCameraOne = findViewById(R.id.image_store_camera_one);
        mImageInDoorTwo = findViewById(R.id.image_in_door_two);
        mImageStoreCameraTwo = findViewById(R.id.image_store_camera_two);
        mImageInDoorThree = findViewById(R.id.image_in_door_three);
        mImageStoreCameraThree = findViewById(R.id.image_store_camera_three);
        mImageLogoCamera.setOnClickListener(this);
        mImageDoorCamera.setOnClickListener(this);
        mImageStoreCameraOne.setOnClickListener(this);
        mImageStoreCameraTwo.setOnClickListener(this);
        mImageStoreCameraThree.setOnClickListener(this);
        mImageInDoorOne.setOnClickListener(this);
        mImageInDoorTwo.setOnClickListener(this);
        mImageInDoorThree.setOnClickListener(this);
        mEdStoreOpenTime = findViewById(R.id.ed_store_open_time);
        mEdStoreCloseTime = findViewById(R.id.ed_store_close_time);
        mEdStoreOpenTime.setOnClickListener(this);
        mEdStoreCloseTime.setOnClickListener(this);
        mImgUpdateTel = findViewById(R.id.img_update_tel);
        mImgUpdateTel.setOnClickListener(this);
        mImgRemark = findViewById(R.id.img_remark);
        mImgRemark.setOnClickListener(this);
        timeImg = findViewById(R.id.img_time);
        timeImg.setOnClickListener(this);

    }

    @Override
    public void initData() {
        readInfo = getSharedPreferences("user_npt", Context.MODE_PRIVATE);
        editor = readInfo.edit();
        bdId = readInfo.getInt("bdId", 0);
        Log.d("ShopManagementActivity", "MyApp.getShopInfoBean().getShopId():" + bdId);
        getInfo();
    }

    @Override
    public void onClick(View var1) {
        switch (var1.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.btn_mine_shop:
                LoadingDialog.showRoundProcessDialog(ShopManagementActivity.this);
                Log.d("ShopManagementActivity", "mFileList:" + mFileList.size());
                if (mFileList != null && mFileList.size() > 0) {
                    uploadPic();
                } else {
                    editInfo();
                }
                break;
            case R.id.image_logo_camera:
                checkPermission(5);
                urlType = 0;
                typeList.add(urlType);
                break;
            case R.id.image_door_camera:
                checkPermission(6);
                urlType = 1;
                typeList.add(urlType);
                break;
            case R.id.image_store_camera_one:
                checkPermission(7);
                urlType = 2;
                typeList.add(urlType);
                break;
            case R.id.image_store_camera_two:
                checkPermission(8);
                urlType = 3;
                typeList.add(urlType);
                break;
            case R.id.image_store_camera_three:
                checkPermission(9);
                urlType = 4;
                typeList.add(urlType);
                break;
            case R.id.ed_store_open_time:

                break;
            case R.id.ed_store_close_time:
//                timePicker2();
                break;
            case R.id.img_update_tel:
                new XPopup.Builder(ShopManagementActivity.this).asCustom(new phonePopup(ShopManagementActivity.this)).show();
                break;
            case R.id.img_time:
                timePicker1();
                break;
            case R.id.img_remark:
                new XPopup.Builder(ShopManagementActivity.this).asCustom(new remarkPopup(ShopManagementActivity.this)).show();
                break;
        }
    }

    class phonePopup extends CenterPopupView {
        //注意：自定义弹窗本质是一个自定义View，但是只需重写一个参数的构造，其他的不要重写，所有的自定义弹窗都是这样。
        public phonePopup(@NonNull Context context) {
            super(context);
        }

        // 返回自定义弹窗的布局
        @Override
        protected int getImplLayoutId() {
            return R.layout.popup_phone_update;
        }

        // 执行初始化操作，比如：findView，设置点击，或者任何你弹窗内的业务逻辑
        @Override
        protected void onCreate() {
            super.onCreate();
            EditText edit = findViewById(R.id.edit);
            findViewById(R.id.cancel).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss(); // 关闭弹窗
                }
            });
            findViewById(R.id.update_info).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isMobileNO(edit.getText().toString())) {
                        ToastUtils.showToast("请输入正确的手机号");
                        return;
                    }
                    if (!TextUtils.isEmpty(edit.getText().toString())) {
                        mShopTel.setText(edit.getText().toString());
                        dismiss();
                    } else {
                        ToastUtils.showToast("请输入手机号!");
                    }
                }
            });

        }

        // 设置最大宽度，看需要而定
        @Override
        protected int getMaxWidth() {
            return super.getMaxWidth();
        }

        // 设置最大高度，看需要而定
        @Override
        protected int getMaxHeight() {
            return super.getMaxHeight();
        }

        // 设置自定义动画器，看需要而定
        @Override
        protected PopupAnimator getPopupAnimator() {
            return super.getPopupAnimator();
        }


    }

    long nowCount;

    private int calculateLengthIgnoreCnOrEn(CharSequence c) {
        int len = 0;
        for (int i = 0; i < c.length(); i++) {
            len++;
        }
        return len;
    }

    class remarkPopup extends CenterPopupView {

        public remarkPopup(@NonNull Context context) {
            super(context);
        }

        // 返回自定义弹窗的布局
        @Override
        protected int getImplLayoutId() {
            return R.layout.popup_remark;
        }

        // 执行初始化操作，比如：findView，设置点击，或者任何你弹窗内的业务逻辑
        @SuppressLint("ClickableViewAccessibility")
        @Override
        protected void onCreate() {
            RelativeLayout popup_remark = findViewById(R.id.popup_layout);
            TextView nowCountTv = findViewById(R.id.now_count);
            mRemarkEdit = findViewById(R.id.ed_store_note);
            mRvQuickInput = findViewById(R.id.rv_Quick_input);
            mRemarkEdit.setText(mShopRemark.getText().toString());
            mRemarkEdit.setSelection(mShopRemark.getText().toString().length());
            strList.add(mShopRemark.getText().toString());
            mRemarkEdit.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    mRemarkEdit.setSelection(s.toString().length());
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    mRemarkEdit.setSelection(s.toString().length());
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
                    mRemarkEdit.setSelection(s.toString().length());
                }
            });

            getQuickInput();
            findViewById(R.id.cancel).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss(); // 关闭弹窗
                }
            });
            findViewById(R.id.update_info).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!TextUtils.isEmpty(mRemarkEdit.getText().toString())) {
                        mShopRemark.setText(mRemarkEdit.getText().toString());
                        dismiss();
                    } else {
                        ToastUtils.showToast("请输入备注!");
                    }
                }
            });
        }

        @Override
        protected void onDismiss() {
            strList.clear();
            super.onDismiss();
        }
    }

    List<String> strList = new ArrayList<>();
    String aaa;

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
                }// 功能暂未开放
                quickinputEntityList = (List<QuickinputEntity>) data.pullData();
                storeQuickinputAdapter = new RecyclerAdapter<QuickinputEntity>(ShopManagementActivity.this, R.layout.item_popup_remark, quickinputEntityList) {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void convert(RecyclerViewHolder holder, QuickinputEntity itemData, int position) {
                        TextView title = holder.getView(R.id.txt_Quick_input);
                        LinearLayout layout = holder.getView(R.id.layout);
                        title.setText(itemData.getLabelDetail());
                        layout.setOnClickListener(new View.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.O)
                            @Override
                            public void onClick(View v) {
                                strList.add(itemData.getLabelDetail());
                                if (strList != null) {
                                    aaa = String.join("\n", getNewList(strList));
                                } else {
                                    aaa = String.join("", getNewList(strList));
                                }
                                mRemarkEdit.setText(aaa);
                            }
                        });
                    }

                };
                RecyclerView.LayoutManager layoutManagerQuick_input = new LinearLayoutManager(ShopManagementActivity.this, LinearLayoutManager.VERTICAL, false);
                mRvQuickInput.setLayoutManager(layoutManagerQuick_input);
                mRvQuickInput.setAdapter(storeQuickinputAdapter);


            }

            @Override
            public void onError(ResponseBean msg) {
                com.sheepshop.businessside.tool.ToastUtils.showToast(msg.getMsg());
            }

            @Override
            public void onErrorForOthers(ResponseBean msg) {
                com.sheepshop.businessside.tool.ToastUtils.showToast(msg.getMsg());
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

    public boolean isMobileNO(String mobileNums) {
        /**
         * 判断字符串是否符合手机号码格式
         * 移动号段: 134,135,136,137,138,139,147,150,151,152,157,158,159,170,178,182,183,184,187,188
         * 联通号段: 130,131,132,145,155,156,170,171,175,176,185,186
         * 电信号段: 133,149,153,170,173,177,180,181,189
         * @param str
         * @return 待检测的字符串
         */
        String telRegex = "^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\\d{8}$";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobileNums)) {
            return false;
        } else {
            return mobileNums.matches(telRegex);
        }
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

    private void timePicker1() {
        TimePicker picker = new TimePicker(ShopManagementActivity.this, TimePicker.HOUR_24);
        picker.setRangeStart(0, 0);//09:00
        picker.setRangeEnd(23, 59);//18:30
        picker.setTopLineVisible(false);
        picker.setLineVisible(false);
        picker.setWheelModeEnable(false);
        picker.setTitleText("请选择营业时间");

        picker.setOnTimePickListener(new TimePicker.OnTimePickListener() {
            @Override
            public void onTimePicked(String hour, String minute) {
                openTime = hour + ":" + minute;
                mEdStoreOpenTime.setText(hour + ":" + minute);
                timePicker2();
            }
        });
        picker.show();
    }

    private void timePicker2() {
        TimePicker picker = new TimePicker(ShopManagementActivity.this, TimePicker.HOUR_24);
        picker.setRangeStart(0, 0);//09:00
        picker.setRangeEnd(23, 59);//18:30
        picker.setTopLineVisible(false);
        picker.setLineVisible(false);
        picker.setWheelModeEnable(false);
        picker.setTitleText("请选择休息时间");
        picker.setOnTimePickListener(new TimePicker.OnTimePickListener() {
            @Override
            public void onTimePicked(String hour, String minute) {
                closeTime = hour + ":" + minute;
                mEdStoreCloseTime.setText(hour + ":" + minute);
            }
        });
        picker.show();
    }


    /**
     * @param type type =1 logo
     *             type =2 door
     *             type =3 indoor1
     */
    private void checkPermission(int type) {
        if (Build.VERSION.SDK_INT > 22) {
            int cameraPermission = ActivityCompat.checkSelfPermission(ShopManagementActivity.this, Manifest.permission.CAMERA);
            int recordPermission = ActivityCompat.checkSelfPermission(ShopManagementActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == 5) {
            mFileIvLogo = ImageUtils.getPhotoFromResult(this, data);
            ImageUtils.loadNormalImage(this, mFileIvLogo, mImageLogo);
            mFileList.add(CompressHelper.getDefault(this).compressToFile(mFileIvLogo));
        } else if (requestCode == 6) {
            mFileIvDoor = ImageUtils.getPhotoFromResult(this, data);
            ImageUtils.loadNormalImage(this, mFileIvDoor, mImageDoor);
            mFileList.add(CompressHelper.getDefault(this).compressToFile(mFileIvDoor));
        } else if (requestCode == 7) {
            mFileIvInDoorOne = ImageUtils.getPhotoFromResult(this, data);
            ImageUtils.loadNormalImage(this, mFileIvInDoorOne, mImageInDoorOne);
            mFileList.add(CompressHelper.getDefault(this).compressToFile(mFileIvInDoorOne));
        } else if (requestCode == 8) {
            mFileIvInDoorTwo = ImageUtils.getPhotoFromResult(this, data);
            ImageUtils.loadNormalImage(this, mFileIvInDoorTwo, mImageInDoorTwo);
            mFileList.add(CompressHelper.getDefault(this).compressToFile(mFileIvInDoorTwo));
        } else if (requestCode == 9) {
            mFileIvInDoorThree = ImageUtils.getPhotoFromResult(this, data);
            ImageUtils.loadNormalImage(this, mFileIvInDoorThree, mImageInDoorThree);
            mFileList.add(CompressHelper.getDefault(this).compressToFile(mFileIvInDoorThree));
        }
    }


    private void getInfo() {

        Call<BaseResp<InfoBean>> call = HttpUtils.getInstance().getApiService(ApiService.class).info(String.valueOf(bdId));
        call.enqueue(new SSHCallBackNew<BaseResp<InfoBean>>() {
            @Override
            public void onSuccess(Response<BaseResp<InfoBean>> response) throws Exception {
                int b = response.body().getData().getBdId();
                bean = response.body().getData();
                mShopName.setText(bean.getBdName());
                if (bean.getBdStatus() == 0) {
                    mShopState.setText("营业中");
                } else {
                    mShopState.setText("休息中");
                }
                mShopKinds.setText(bean.getBdCuisineName());
                mShopOwner.setText(bean.getBdStorekeeper());
                mShopTel.setText(bean.getBdPhone());
                mShopAddress.setText(bean.getBdAddress());
                mEdStoreOpenTime.setText(bean.getBdOpentime());
                mEdStoreCloseTime.setText(bean.getBdClosetime());
                Glide.with(ShopManagementActivity.this).load(bean.getBdIdpreUrl()).into(mImageFront);
                Glide.with(ShopManagementActivity.this).load(bean.getBdIdbackUrl()).into(mImageReverse);
                Glide.with(ShopManagementActivity.this).load(bean.getBdLicenseUrl()).into(mImageLicense);
                Glide.with(ShopManagementActivity.this).load(bean.getBdExequaturUrl()).into(mImageLicence);
                Glide.with(ShopManagementActivity.this).load(bean.getBdLogo()).into(mImageLogo);
                Glide.with(ShopManagementActivity.this).load(bean.getBdMainpic()).into(mImageDoor);
                mImageLogo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mFileIvLogo != null) {
                            new XPopup.Builder(ShopManagementActivity.this).asImageViewer(mImageLogo, mFileIvLogo, new ImageLoader()).isShowSaveButton(false).show();
                        } else {
                            new XPopup.Builder(ShopManagementActivity.this).asImageViewer(mImageLogo, bean.getBdLogo(), new ImageLoader()).isShowSaveButton(false).show();
                        }
                    }
                });
                mImageDoor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mFileIvLogo != null) {
                            new XPopup.Builder(ShopManagementActivity.this).asImageViewer(mImageDoor, mFileIvDoor, new ImageLoader()).isShowSaveButton(false).show();
                        } else {
                            new XPopup.Builder(ShopManagementActivity.this).asImageViewer(mImageDoor, bean.getBdMainpic(), new ImageLoader()).isShowSaveButton(false).show();
                        }
                    }
                });
                mImageFront.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new XPopup.Builder(ShopManagementActivity.this).asImageViewer(mImageFront, bean.getBdIdpreUrl(), new ImageLoader()).isShowSaveButton(false).show();
                    }
                });
                mImageReverse.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new XPopup.Builder(ShopManagementActivity.this).asImageViewer(mImageReverse, bean.getBdIdbackUrl(), new ImageLoader()).isShowSaveButton(false).show();
                    }
                });
                mImageLicense.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new XPopup.Builder(ShopManagementActivity.this).asImageViewer(mImageLicense, bean.getBdLicenseUrl(), new ImageLoader()).isShowSaveButton(false).show();
                    }
                });
                mImageLicence.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new XPopup.Builder(ShopManagementActivity.this).asImageViewer(mImageLicence, bean.getBdExequaturUrl(), new ImageLoader()).isShowSaveButton(false).show();
                    }
                });
                if (!TextUtils.isEmpty(bean.getBdDetailpic())) {
                    picLists = Arrays.asList(bean.getBdDetailpic().split(","));
                    imgUrlList.add(bean.getBdLogo());
                    imgUrlList.add(bean.getBdMainpic());
                    imgUrlList.add(picLists.get(0));
                    imgUrlList.add(picLists.get(1));
                    imgUrlList.add(picLists.get(2));
                    if (picLists != null) {
                        Glide.with(ShopManagementActivity.this).load(picLists.get(0)).into(mImageInDoorOne);
                        Glide.with(ShopManagementActivity.this).load(picLists.get(1)).into(mImageInDoorTwo);
                        Glide.with(ShopManagementActivity.this).load(picLists.get(2)).into(mImageInDoorThree);
                        mImageInDoorOne.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (mFileIvInDoorOne != null) {
                                    new XPopup.Builder(ShopManagementActivity.this).asImageViewer(mImageInDoorOne, mFileIvInDoorOne, new ImageLoader()).isShowSaveButton(false).show();
                                } else {
                                    new XPopup.Builder(ShopManagementActivity.this).asImageViewer(mImageInDoorOne, picLists.get(0), new ImageLoader()).isShowSaveButton(false).show();
                                }
                            }
                        });
                        mImageInDoorTwo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (mFileIvInDoorTwo != null) {
                                    new XPopup.Builder(ShopManagementActivity.this).asImageViewer(mImageInDoorTwo, mFileIvInDoorTwo, new ImageLoader()).isShowSaveButton(false).show();
                                } else {
                                    new XPopup.Builder(ShopManagementActivity.this).asImageViewer(mImageInDoorTwo, picLists.get(1), new ImageLoader()).isShowSaveButton(false).show();
                                }
                            }
                        });
                        mImageInDoorThree.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (mFileIvInDoorThree != null) {
                                    new XPopup.Builder(ShopManagementActivity.this).asImageViewer(mImageInDoorThree, mFileIvInDoorThree, new ImageLoader()).isShowSaveButton(false).show();
                                } else {
                                    new XPopup.Builder(ShopManagementActivity.this).asImageViewer(mImageInDoorThree, picLists.get(2), new ImageLoader()).isShowSaveButton(false).show();
                                    Log.d("mImageInDoorThree", picLists.get(2));
                                }
                            }
                        });
                    }
                } else {
                    ToastUtils.showToast("图片加载异常");
                }


            }

            @Override
            public void onFail(String message) {

            }
        });

    }

    private void uploadPic() {
        List<MultipartBody.Part> files2 = new ArrayList<>();
        //TODO 上传多张图片
        for (int i = 0; i < mFileList.size(); i++) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), mFileList.get(i));
            MultipartBody.Part form = MultipartBody.Part.createFormData("files", mFileList.get(i).getName(), requestBody);
            files2.add(form);
        }
        Call<FilesUpload> call = HttpUtils.getInstance().getApiService(ApiService.class).upload(files2);
        call.enqueue(new Callback<FilesUpload>() {
            @Override
            public void onResponse(Call<FilesUpload> call, Response<FilesUpload> response) {
                picUrl = response.body().getData();
                Log.d("StoreInfoActivity", picUrl);
                picUrlList = Arrays.asList(picUrl.split(","));
                if (picUrlList != null) {
                    for (int i = 0; i < picUrlList.size(); i++) {
                        imgUrlList.set(typeList.get(i), picUrlList.get(i));
                    }
                    editInfo();
                } else {
                    Log.d("StoreInfoActivity", "*****************************");
                }
            }

            @Override
            public void onFailure(Call<FilesUpload> call, Throwable t) {
                LoadingDialog.dismissDialog();
                ToastUtils.showToast("修改提交失败");
                Log.d("StoreInfoActivity", "*************ERROR****************");
            }
        });
    }

    Call<BaseResp<BeanRespReqEmpty>> call;

    private void editInfo() {
        String bannerUrl = imgUrlList.get(2) + "," + imgUrlList.get(3) + "," + imgUrlList.get(4);
        call = HttpUtils.getInstance().getApiService(ApiService.class).editInfo(String.valueOf(bdId), mShopTel.getText().toString(), mEdStoreOpenTime.getText().toString(), mEdStoreCloseTime.getText().toString(), imgUrlList.get(0), bannerUrl, imgUrlList.get(1), mShopRemark.getText().toString());
        Log.d("1-------------", MyApp.getShopInfoBean().getShopId() + "tel: " + mShopTel.getText().toString() + "opentime:" + mEdStoreOpenTime.getText().toString());
        Log.d("1-------------", imgUrlList.get(0));
        Log.d("1-------------", imgUrlList.get(1));
        Log.d("1-------------", bannerUrl);
        call.enqueue(new SSHCallBackNew<BaseResp<BeanRespReqEmpty>>() {
            @Override
            public void onSuccess(Response<BaseResp<BeanRespReqEmpty>> response) throws Exception {
                LoadingDialog.dismissDialog();
                String msg = response.body().getMsg();
                ToastUtils.showToast(msg);
                finish();
                Log.d("22222222222`", msg);
            }

            @Override
            public void onFail(String message) {
                Log.d("22222222222", message);
                LoadingDialog.dismissDialog();
                ToastUtils.showToast(message);
            }
        });
    }


}
