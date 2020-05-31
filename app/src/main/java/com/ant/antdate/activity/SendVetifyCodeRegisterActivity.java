package com.ant.antdate.activity;

import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ant.antdate.R;
import com.ant.antdate.base.BaseActivity;
import com.ant.antdate.logic.LogicRequest;
import com.ant.antdate.utils.Util;

import java.util.Timer;

import lib.frame.module.ui.BindView;

public class SendVetifyCodeRegisterActivity extends BaseActivity {
    @BindView(R.id.tv_loginby_user)
    TextView tv_loginby_user;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.btn_next)
    Button btn_next;
    @BindView(R.id.et_vertify)
    EditText et_vertify;
    @BindView(R.id.btn_timer)
    Button btn_timer;
    String verifycode;
    private Timer timer;

    @Override
    protected void setRootView() {
        super.setRootView();
        rootViewId = R.layout.activity_sendver_register;
    }

    @Override
    protected void initView() {
        super.initView();
        countDown();
        // tv_loginby_user.setVisibility(View.GONE);
        btn_timer.setOnClickListener(this::onClick);
        iv_back.setOnClickListener(this::onClick);
        btn_next.setOnClickListener(this::onClick);
        et_vertify.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //  iv_delete.setVisibility(View.GONE);
                btn_next.setBackgroundResource((R.mipmap.bg_btn_login));
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // iv_delete.setVisibility(View.VISIBLE);

            }

            @Override
            public void afterTextChanged(Editable editable) {
                verifycode = et_vertify.getText().toString();

                if (verifycode.length() == 4) {
                    btn_next.setEnabled(true);
                    btn_next.setBackgroundResource((R.mipmap.bg_canlogin));
                } else {
                    btn_next.setEnabled(false);
                    btn_next.setBackgroundResource((R.mipmap.bg_btn_login));
                }
            }
        });
    }

    @Override
    protected void loadData() {
        super.loadData();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
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
            case R.id.btn_next:
                Util.writeIni(this, "code", et_vertify.getText().toString());
                goToActivity(RegisterSetPassActivity.class);
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_timer:
                countDown();
                String phonenumber = Util.readIni(this, "phone", "");
                LogicRequest.sendSMS(3, phonenumber, 2, getHttpHelper());
                break;
        }
    }

    /**
     * 倒计时显示
     */
    private void countDown() {

        CountDownTimer timer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                btn_timer.setEnabled(false);
                btn_timer.setText("重新发送" + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                btn_timer.setEnabled(true);
                btn_timer.setText("重新获取");

            }
        }.start();


    }

}
