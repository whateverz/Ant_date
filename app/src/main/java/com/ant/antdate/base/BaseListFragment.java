package com.ant.antdate.base;


import lib.frame.view.recyclerView.WgList;

/**
 * Created by shuaqq on 2017/9/11.
 */

public abstract class BaseListFragment<T> extends lib.frame.base.BaseListFragment<T> {

    protected App mApp;

    @Override
    protected void initView() {
        mApp = (App) mContext.getApplicationContext();
        super.initView();
    }

    @Override
    protected WgList<T> createList() {
        return new WgList<>(mContext);
    }

}
