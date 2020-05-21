package com.ant.antdate.base;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import lib.frame.base.BaseFrameDialog;

/**
 * Created by shuaqq on 2017/10/31.
 */

public class BaseDialog extends BaseFrameDialog {

    protected App mApp;

    public BaseDialog(@NonNull Context context) {
        super(context);
    }

    public BaseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected BaseDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }


    @Override
    protected void initBase() {
        super.initBase();
        Context context = mContext.getApplicationContext();
        if (context instanceof App)
            mApp = (App) context;
    }
}
