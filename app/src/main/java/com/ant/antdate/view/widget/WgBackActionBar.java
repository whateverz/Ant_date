package com.ant.antdate.view.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.ant.antdate.R;
import com.ant.antdate.base.BaseActivity;


/**
 * Created by shuaqq on 2017/9/10.
 */

public class WgBackActionBar extends WgActionBar {
    public WgBackActionBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public WgBackActionBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WgBackActionBar(Context context) {
        super(context);
    }

    @Override
    protected void initThis() {
        super.initThis();
        setOnWgActionBarBaseListener(index -> {
            switch (index) {
                case WgActionBar.TYPE_LEFT:
                    ((BaseActivity) mContext).onBackPressed();
                    break;
            }
        });
    }
}
