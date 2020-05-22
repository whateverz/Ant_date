package com.ant.antdate.activity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ant.antdate.R;
import com.ant.antdate.base.BaseActivity;
import com.ant.antdate.logic.LogicRequest;
import com.ant.antdate.register.RegisterRequest;

import lib.frame.module.ui.BindView;
import lib.frame.utils.Log;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    @BindView (R.id.btn_login)
    Button btn_login;
    @BindView(R.id.tv_loginby_user)
    TextView tv_loginby_user;
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.iv_delete)
    ImageView iv_delete;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    private String phone;
    @Override
    protected void setRootView() {
        super.setRootView();
        rootViewId = R.layout.activity_login;
    }

    @Override
    protected void initView() {
        super.initView();
        btn_login.setOnClickListener(this::onClick);
        tv_loginby_user.setOnClickListener(this::onClick);
        iv_delete.setOnClickListener(this::onClick);
      //  iv_back.setOnClickListener(this::onClick);


        et_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                iv_delete.setVisibility(View.GONE);
                btn_login.setBackgroundResource((R.mipmap.bg_btn_login));
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                iv_delete.setVisibility(View.VISIBLE);

            }

            @Override
            public void afterTextChanged(Editable editable) {
                phone = et_phone.getText().toString();

                if (phone.length()==11){
                    btn_login.setEnabled(true);
                    btn_login.setBackgroundResource((R.mipmap.default_photo));
                }else {
                    btn_login.setEnabled(false);
                    btn_login.setBackgroundResource((R.mipmap.bg_btn_login));
                }
            }
        });

    }

    @Override
    protected void loadData() {
        super.loadData();
        RegisterRequest.Register(2, "13581714368", "1696", getHttpHelper());
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.btn_login:
                phone = et_phone.getText().toString();
               /* Log.e("ZWX",phone.length()+"");
                if (phone.length()==11){
                    btn_login.setEnabled(true);
                }else {
                    btn_login.setEnabled(false);
                }*/
                LogicRequest.sendSMS(1, "13581714368", 1, getHttpHelper());
                goToActivity(VetifyCodeActivity.class);
                break;
            case R.id.tv_loginby_user:
                goToActivity(MainActivity.class);
                break;
            case R.id.iv_delete:
               et_phone.setText("");
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
