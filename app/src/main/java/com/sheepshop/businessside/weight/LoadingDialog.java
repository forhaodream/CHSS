package com.sheepshop.businessside.weight;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;

import com.sheepshop.businessside.R;


/**
 * Created by Administrator on 2017/3/16 0016.
 */

public class LoadingDialog {
    public static Dialog mDialog;

    public static void showRoundProcessDialog(Context mContext) {
        if (LoadingDialog.mDialog != null && LoadingDialog.mDialog.isShowing()) {
            LoadingDialog.mDialog.cancel();
        }
        DialogInterface.OnKeyListener keyListener = new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                return i == KeyEvent.KEYCODE_HOME || i == KeyEvent.KEYCODE_SEARCH || i == KeyEvent.KEYCODE_BACK;
            }
        };

        mDialog = new AlertDialog.Builder(mContext, R.style.NoBackGroundDialog).create();
        mDialog.setCancelable(false);
        mDialog.setOnKeyListener(keyListener);
        mDialog.show();
        // 注意此处要放在show之后 否则会报异常
        mDialog.setContentView(R.layout.loading_process_dialog_anim);
    }

    public static void dismissDialog() {
        mDialog.dismiss();
    }
}
