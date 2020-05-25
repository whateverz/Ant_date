package com.ant.antdate.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.ant.antdate.R;
import com.ant.antdate.base.BaseActivity;

import lib.frame.module.ui.BindView;

public class RegistActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @Override
    protected void setRootView() {
        super.setRootView();
        rootViewId = R.layout.activity_register;
    }

    @Override
    protected void initView() {
        super.initView();
        iv_back.setOnClickListener(this::onClick);
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
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
