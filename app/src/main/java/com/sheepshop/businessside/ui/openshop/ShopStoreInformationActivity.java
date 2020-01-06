package com.sheepshop.businessside.ui.openshop;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.core.app.ActivityCompat;
import androidx.core.content.PermissionChecker;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sheepshop.businessside.R;
import com.sheepshop.businessside.adapter.PublishCommentAdapter;
import com.sheepshop.businessside.adapter.StoreQuickinputAdapter;
import com.sheepshop.businessside.base.Constant;
import com.sheepshop.businessside.entity.QuickinputEntity;
import com.sheepshop.businessside.network.netbean.HttpBean;
import com.sheepshop.businessside.network.netbean.ResponseBean;
import com.sheepshop.businessside.network.netinterface.BaseCallBack;
import com.sheepshop.businessside.tool.ToastUtils;
import com.sheepshop.businessside.utils.HttpRequestParamsBuilder;
import com.sheepshop.businessside.utils.ImageUtils;
import com.sheepshop.businessside.utils.NetUtils;
import com.sheepshop.businessside.weight.TitleBar;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import ch.chtool.base.BaseActivity;

import static com.sheepshop.businessside.utils.ToastUtils.showShort;

/**
 * 店铺信息 营业资格
 *
 * @author Hm
 */
public class ShopStoreInformationActivity extends BaseActivity {
    @BindView(R.id.btn_next_info)
    Button btnNextInfo;
    @BindView(R.id.iv_shop_logo)
    ImageView ivShopLogo;
    @BindView(R.id.iv_shop_logo_showPic)
    ImageView ivShopLogoShowPic;
    @BindView(R.id.iv_shop_logo_deletePic)
    ImageView ivShopDeletePic;
    @BindView(R.id.rela_shop_logo)
    RelativeLayout relaShopLogo;
    @BindView(R.id.iv_shop_front)
    ImageView ivShopFront;
    @BindView(R.id.iv_shop_front_showPic)
    ImageView ivShopFrontShowPic;
    @BindView(R.id.iv_shop_front_deletePic)
    ImageView ivShopFrontDeletePic;
    @BindView(R.id.rela_shop_front)
    RelativeLayout relaShopFront;
    @BindView(R.id.iv_store_inside)
    ImageView ivStoreInside;
    @BindView(R.id.rv_store_inside_showPic)
    RecyclerView rvStoreInsideShowPic;
    @BindView(R.id.ed_store_note)
    EditText edStoreNote;
    @BindView(R.id.rv_Quick_input)
    RecyclerView rvQuickInput;
    //  type = 5 店铺logo, type = 6 店铺门门脸照
    private File mFileivShopLogo, mFileivShopFront;
    private PublishCommentAdapter mAdapter;
    private StoreQuickinputAdapter storeQuickinputAdapter;

    private List<String> mDataFileString;
    private static final int GET_ADD_PIC = 101;
    private static final int GET_OPEN_CAMERA = 102;
    private File mediaFile;
    private List<QuickinputEntity> quickinputEntityList = new ArrayList<>();


    protected void addListener() {
        //下一步
        btnNextInfo.setOnClickListener(v -> {
            startActivity(new Intent(ShopStoreInformationActivity.this, AuditActivity.class));

        });
        //店铺LOGO
        ivShopLogo.setOnClickListener(v -> checkPermission(5));
        //店铺门脸
        ivShopFront.setOnClickListener(v -> checkPermission(6));

        //店铺店内照
        ivStoreInside.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onIvActivityPublishCommentAddPicClicked();
            }
        });

        //店铺LOGO删除
        ivShopDeletePic.setOnClickListener(v -> {
            ivShopLogo.setVisibility(View.VISIBLE);
            relaShopLogo.setVisibility(View.GONE);
        });
        //店铺门脸删除
        ivShopFrontDeletePic.setOnClickListener(v -> {
            ivShopFront.setVisibility(View.VISIBLE);
            relaShopFront.setVisibility(View.GONE);
        });

    }


    @Override
    public void initData() {

    }


    protected void initData(Bundle savedInstanceState) {
        mDataFileString = new ArrayList<>();
        mAdapter = new PublishCommentAdapter(mDataFileString);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false);
        rvStoreInsideShowPic.setLayoutManager(layoutManager);
        rvStoreInsideShowPic.setAdapter(mAdapter);
        mAdapter.bindToRecyclerView(rvStoreInsideShowPic);


        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.rl_item_activity_publishComment_addPic) {
                //添加图片
                onIvActivityPublishCommentAddPicClicked();
            } else if (view.getId() == R.id.iv_item_activity_publishComment_showPic) {
                //点击图片
//                    Bundle bundlePhoto = new Bundle();
//                    bundlePhoto.putInt("currentPosition", position);
//                    bundlePhoto.putStringArrayList("urls", new ArrayList<String>(mDataFileString));
//                    ARouterUtils.goActivityNoInterceptor(SocialCircleRouter.PHOTO_VIEW_ACTIVITY, bundlePhoto);
            } else {
                //删除图片
                if (!mDataFileString.get(mDataFileString.size() - 1).equals("")) {
                    mDataFileString.add("");
                }
                mDataFileString.remove(position);
                mAdapter.notifyDataSetChanged();
            }
        });
        getQuickInput();
    }

    /**
     * 检测是否有权限
     */
    private void checkPermission(int type) {
        if (Build.VERSION.SDK_INT > 22) {
            int cameraPermission = ActivityCompat.checkSelfPermission(ShopStoreInformationActivity.this, Manifest.permission.CAMERA);
            int recordPermission = ActivityCompat.checkSelfPermission(ShopStoreInformationActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
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
        /**
         *    type = 5 店铺logo
         *    type = 6 店铺logo
         *    type = 7店内环境照片
         */
        if (requestCode == 5) {
            mFileivShopLogo = ImageUtils.getPhotoFromResult(this, data);
            ivShopLogo.setVisibility(View.GONE);
            relaShopLogo.setVisibility(View.VISIBLE);
            ImageUtils.loadNormalImage(this, mFileivShopLogo, ivShopLogoShowPic);
        } else if (requestCode == 6) {
            mFileivShopFront = ImageUtils.getPhotoFromResult(this, data);
            ivShopFront.setVisibility(View.GONE);
            relaShopFront.setVisibility(View.VISIBLE);
            ImageUtils.loadNormalImage(this, mFileivShopFront, ivShopFrontShowPic);
        }
        switch (requestCode) {
            case GET_ADD_PIC:
                if (data != null) {
                    ivStoreInside.setVisibility(View.GONE);
                    String filePath = ImageUtils.getPhotoFromResult(this, data).getAbsolutePath();
                    if (filePath.endsWith("webp")) {
                        showShort(this, "文件格式暂不支持");
                        return;
                    }
                    mDataFileString.add(filePath);
                    judgeAddPic();
                }
                break;
            case GET_OPEN_CAMERA:
                mDataFileString.add(mediaFile.getAbsolutePath());
                judgeAddPic();
                break;
        }
    }

    private void judgeAddPic() {
        for (int i = 0; i < mDataFileString.size(); i++) {
            if (mDataFileString.get(i).equals("")) {
                mDataFileString.remove(i);
            }
        }
        if (mDataFileString.size() < 3 && !mDataFileString.get(mDataFileString.size() - 1).equals("")) {
            mDataFileString.add("");
        }
        mAdapter.notifyDataSetChanged();
    }

    private void choosePic() {
        if (mDataFileString.size() > 2 && !mDataFileString.get(mDataFileString.size() - 1).equals("")) {
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
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        GET_ADD_PIC);
            } else {
                choosePic();
            }
        } else {
            choosePic();
        }
    }

    /**
     * @author Administrator
     * @time 2019/11/4  16:20
     * @decs：获取便捷输入信息
     */
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
                RecyclerView.LayoutManager layoutManagerQuick_input = new LinearLayoutManager(ShopStoreInformationActivity.this, LinearLayoutManager.VERTICAL, false);
                rvQuickInput.setLayoutManager(layoutManagerQuick_input);
                rvQuickInput.setAdapter(storeQuickinputAdapter);
                storeQuickinputAdapter.bindToRecyclerView(rvQuickInput);
                storeQuickinputAdapter.setOnItemClickListener((adapter, view, position) -> edStoreNote.setText(quickinputEntityList.get(position).getLabelDetail()));

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


    @Override
    public int initLayout() {
        return R.layout.activity_shop_store_information;
    }

    @Override
    public void initView() {

    }


}
