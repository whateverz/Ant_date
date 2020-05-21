package com.ant.antdate.base;

import android.content.Context;
import android.util.AttributeSet;

import lib.frame.base.BaseFrameView;

/**
 * Created by shuaqq on 2017/10/31.
 */

public class BaseView extends BaseFrameView {

    protected App mApp;

    public BaseView(Context context) {
        super(context);
        mApp = (App) mAppBase;
    }

    public BaseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mApp = (App) mAppBase;
    }

    public BaseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mApp = (App) mAppBase;
    }

}
