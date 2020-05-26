package com.ant.antdate.activity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.ant.antdate.R;
import com.ant.antdate.base.BaseActivity;
import com.ant.antdate.logic.LogicRequest;
import com.ant.antdate.utils.Util;

import lib.frame.module.ui.BindView;


public class VtifyPhoneActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.iv_delete)
    ImageView iv_delete;
    @BindView (R.id.btn_next)
    Button btn_next;
    private String phone;
    @Override
    protected void setRootView() {
        super.setRootView();
        rootViewId = R.layout.activity_phone_number;
    }

    @Override
    protected void initView() {
        super.initView();
        iv_back .setOnClickListener(this::onClick);
        iv_delete .setOnClickListener(this::onClick);
        btn_next.setOnClickListener(this::onClick);
        et_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                iv_delete.setVisibility(View.GONE);
                btn_next.setBackgroundResource((R.mipmap.bg_btn_login));
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                iv_delete.setVisibility(View.VISIBLE);

            }

            @Override
            public void afterTextChanged(Editable editable) {
                phone = et_phone.getText().toString();

                if (phone.length()==11){
                    btn_next.setEnabled(true);
                    btn_next.setBackgroundResource((R.mipmap.bg_canlogin));
                }else {
                    btn_next.setEnabled(false);
                    btn_next.setBackgroundResource((R.mipmap.bg_btn_login));
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_delete:
                et_phone.setText("");
                break;
            case R.id.btn_next:
                Util.writeIni(this,"phone",et_phone.getText().toString());
                goToActivity(SendVetifyCodeLogoActivity.class);
                LogicRequest.sendSMS(3,et_phone.getText().toString(), 2, getHttpHelper());
                break;

        }
    }
}