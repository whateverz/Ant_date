package com.ant.antdate.view.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ViewGroup;


import androidx.core.content.ContextCompat;

import com.ant.antdate.R;

import lib.frame.utils.UIHelper;
import lib.frame.view.widget.WgActionBarBase;

/**
 * Created by lwxkey on 16/6/3.
 */
public class WgActionBar extends WgActionBarBase {

    protected void initThis() {
//        Context appContext = mContext.getApplicationContext();
//        if (appContext instanceof App)
//            mApp = (App) appContext;
        vTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, mContext.getResources().getDimensionPixelOffset(R.dimen.new_48px));
        vTitle.setTextColor(ContextCompat.getColor(mContext, R.color.text_normal_black));
        vTitle.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        vLeftText.setTextAppearance(mContext, R.style.TEXT_WHITE_26PX_100A);
        vRightText.setTextAppearance(mContext, R.style.TEXT_WHITE_26PX_100A);
        UIHelper.setView(vLeftIcon, mContext.getResources().getDimensionPixelOffset(R.dimen.new_168px), mContext.getResources().getDimensionPixelOffset(R.dimen.new_168px));
        UIHelper.setView(vLeft, mContext.getResources().getDimensionPixelOffset(R.dimen.new_168px), mContext.getResources().getDimensionPixelOffset(R.dimen.new_168px));
        UIHelper.setView(vRight, mContext.getResources().getDimensionPixelOffset(R.dimen.new_100px), ViewGroup.LayoutParams.MATCH_PARENT);
//        setBackgroundResource(R.color.white);
    }

    public WgActionBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initThis();
    }

    public WgActionBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initThis();
    }

    public WgActionBar(Context context) {
        super(context);
        initThis();
    }
}
