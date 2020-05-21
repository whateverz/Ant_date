package com.ant.antdate.base;

import android.content.Context;
import android.util.AttributeSet;


import lib.frame.view.item.ItemBase;

/**
 * Created by shuaqq on 2017/8/29.
 */

public abstract class BaseItem<T> extends ItemBase<T> {
    
    protected App mApp;

    public BaseItem(Context context) {
        super(context);
    }

    public BaseItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initThis() {
        super.initThis();
        Context appContext = mContext.getApplicationContext();
        if (appContext instanceof App)
            mApp = (App) appContext;
    }
}
