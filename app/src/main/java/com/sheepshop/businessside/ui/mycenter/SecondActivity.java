package com.sheepshop.businessside.ui.mycenter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.ypx.imagepicker.ImagePicker;
import com.ypx.imagepicker.bean.ImageItem;
import com.ypx.imagepicker.utils.PStatusBarUtil;

import java.util.ArrayList;

import androidx.annotation.Nullable;

/**
 * Created by CH
 * on 2019/12/30 14:30
 */
public class SecondActivity extends Activity {
    ArrayList<ImageItem> imageItems = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PStatusBarUtil.fullScreen(this);
        imageItems = (ArrayList<ImageItem>) getIntent().getSerializableExtra(ImagePicker.INTENT_KEY_PICKER_RESULT);
        ImagePicker.closePickerWithCallback(imageItems);
        finish();

    }

}