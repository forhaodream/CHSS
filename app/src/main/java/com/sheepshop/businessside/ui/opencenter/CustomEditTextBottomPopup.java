package com.sheepshop.businessside.ui.opencenter;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

import com.lxj.xpopup.core.BottomPopupView;
import com.sheepshop.businessside.R;

import androidx.annotation.NonNull;

/**
 * Created by CH
 * on 2019/12/24 10:02
 */
public class CustomEditTextBottomPopup extends BottomPopupView {
    public CustomEditTextBottomPopup(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.custom_edittext_bottom_popup;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onShow() {
        super.onShow();
//        Log.e("tag", "CustomEditTextBottomPopup  onShow");
        findViewById(R.id.btn_finish).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                dismiss();

//                new XPopup.Builder(getContext()).atView(v).asAttachList(new String[]{"aa", "bbb"}, null, null).show();
            }
        });
    }

    @Override
    protected void onDismiss() {
        super.onDismiss();
//        Log.e("tag", "CustomEditTextBottomPopup  onDismiss");
    }

    public String getComment() {
        EditText et = findViewById(R.id.et_comment);
        return et.getText().toString();
    }

//    @Override
//    protected int getMaxHeight() {
//        return (int) (XPopupUtils.getWindowHeight(getContext())*0.75);
//    }
}
