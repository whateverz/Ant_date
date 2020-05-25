package com.ant.antdate.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ant.antdate.R;
import com.ant.antdate.base.BaseActivity;
import com.ant.antdate.logic.LogicRequest;

import lib.frame.module.ui.BindView;

public class PassWordActivity extends BaseActivity {
    @BindView(R.id.tv_loginby_user)
    TextView tv_loginby_user;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @Override
    protected void setRootView() {
        super.setRootView();
        rootViewId = R.layout.activity_vetify_code;
    }

    @Override
    protected void initView() {
        super.initView();
        tv_loginby_user.setVisibility(View.GONE);
        iv_back.setOnClickListener(this::onClick);
    }

    @Override
    protected void loadData() {
        super.loadData();
    }
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
           /* case R.id.btn_login:
                phone = et_phone.getText().toString();
               *//* Log.e("ZWX",phone.length()+"");
                if (phone.length()==11){
                    btn_login.setEnabled(true);
                }else {
                    btn_login.setEnabled(false);
                }*//*
                LogicRequest.sendSMS(1, "13581714368", 1, getHttpHelper());
                goToActivity(VetifyCodeActivity.class);
                break;*/
            case R.id.tv_loginby_user:
                goToActivity(MainActivity.class);
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
