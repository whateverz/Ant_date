package com.ant.antdate.activity;

import com.ant.antdate.R;
import com.ant.antdate.base.BaseActivity;
import com.ant.antdate.logic.LogicRequest;
import com.ant.antdate.register.RegisterRequest;

public class MainActivity extends BaseActivity {

    @Override
    protected void setRootView() {
        super.setRootView();
        rootViewId = R.layout.activity_main;
    }

    @Override
    protected void loadData() {
        super.loadData();
        LogicRequest.sendSMS(1, "13581714368", 1, getHttpHelper());
        RegisterRequest.Register(2, "13581714368", "1696", getHttpHelper());
    }
}
