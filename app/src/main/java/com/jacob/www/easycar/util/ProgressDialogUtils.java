package com.jacob.www.easycar.util;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by 张兴锐 on 2017/11/12.
 */

public class ProgressDialogUtils {
    private static ProgressDialog dialog;
    private static ProgressDialogUtils utils;

    private ProgressDialogUtils() {
      
    }

    public static ProgressDialogUtils getInstance() {
        if (utils == null) {
            utils = new ProgressDialogUtils();
        }
        return utils;
    }

    public synchronized void showProgress(Context context, String msg) {
        if (dialog == null) {
            dialog = new ProgressDialog(context);
        }
        dialog.setMessage(msg);
        dialog.show();
    }

    public synchronized static void hideProgress() {
        if (dialog != null) {
            dialog.dismiss();
        }
        dialog = null;
    }

 
}
