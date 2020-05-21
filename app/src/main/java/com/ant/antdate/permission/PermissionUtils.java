package com.ant.antdate.permission;


import androidx.appcompat.app.AppCompatActivity;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.ant.antdate.R;

import lib.frame.view.dlg.DlgSys;

/**
 * Created by shuaqq on 2017/4/7.
 */

public class PermissionUtils {

    public static void dealPermission(AppCompatActivity activity, PermissionListener listener, String... strings) {
        new RxPermissions(activity)
                .request(strings)
                .subscribe(aBoolean -> {
//                    UIHelper.ToastMessage(activity, aBoolean + "");
                    if (aBoolean) {
                        listener.permissionGranted(strings);
                    } else {
                        DlgSys.show(activity, R.string.permission_denied,  R.string.permission_denied_content, R.string.cancel
                                , (dialog, which) -> listener.permissionDenied(strings), R.string.settings
                                , (dialog, which) -> lib.frame.utils.PermissionUtils.startPermissionManager(activity));
                    }
                });
    }

}
