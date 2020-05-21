package com.ant.antdate.base;


import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import com.ant.antdate.R;
import com.ant.antdate.db.LogicDB;
import com.ant.antdate.db.UserViewModel;

import lib.frame.base.BaseFrameActivity;
import lib.frame.module.http.HttpResult;
import lib.frame.utils.Log;

/**
 * Created by shuaqq on 2017/10/31.
 */

public abstract class BaseActivity extends BaseFrameActivity {

    protected App mApp;

    @Override
    protected void setRootView() {
        super.setRootView();
    }

    @Override
    protected void initBase() {
        super.initBase();
        mAppBase.isActivityStart = true;
        mApp = (App) mContext.getApplicationContext();
//        UserViewModel model = ViewModelProviders.of(this).get(UserViewModel.class);
//        model.getUserLiveData(LogicDB.getBox(UserInfo.class)).observe(this, userInfos -> {
//            if (userInfos != null && userInfos.size() != 0)
//                onUserInfoChanged(userInfos.get(0));
//            else
//                onUserInfoChanged(new UserInfo());
//        });
//        setNavigationBarColor(ContextCompat.getColor(mContext, R.color.black));
    }

//    protected void onUserInfoChanged(UserInfo userInfo) {
//
//    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume activity");
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public <T> void onHttpErrorCallBack(int resultType, int reqId, String resContent, Object reqObject, HttpResult<T> httpResult) {
        super.onHttpErrorCallBack(resultType, reqId, resContent, reqObject, httpResult);
        String msg = "";
        if (httpResult != null)
            msg = httpResult.getCode() + " " + httpResult.getCode();
    }
}
