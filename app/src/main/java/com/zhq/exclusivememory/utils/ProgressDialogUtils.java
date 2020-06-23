package com.zhq.exclusivememory.utils;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by Huiqiang Zhang
 * on 2019/1/19.
 */

public class ProgressDialogUtils {

    private static ProgressDialog progressDialog;

    public static void showDialog(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("正在加载...");
        progressDialog.show();
    }

    public static void showDialog(Context context, String msg) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(msg);
        progressDialog.show();
    }

    public static void cancelDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
