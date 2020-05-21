package com.ant.antdate.base;

import android.os.Bundle;

import androidx.annotation.Nullable;


import lib.frame.base.BaseFrameFragment;

/**
 * Created by shuaqq on 2017/10/31.
 */

public abstract class BaseFragment extends BaseFrameFragment {

    protected App mApp;

    @Override
    protected void initBase() {
        super.initBase();
        mApp = (App) mContext.getApplicationContext();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        UserViewModel model = ViewModelProviders.of(this).get(UserViewModel.class);
//        model.getUserLiveData(LogicDB.getBox(UserInfo.class)).observe(this, userInfos -> {
//            if (userInfos != null && userInfos.size() != 0)
//                onUserInfoChanged(userInfos.get(0));
//            else
//                onUserInfoChanged(new UserInfo());
//        });
    }

//    protected void onUserInfoChanged(UserInfo userInfo) {
//
//    }
}
