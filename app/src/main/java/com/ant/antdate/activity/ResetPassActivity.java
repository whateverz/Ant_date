package com.ant.antdate.activity;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.ant.antdate.R;
import com.ant.antdate.base.BaseActivity;
import com.ant.antdate.bean.RegisterInfo;
import com.ant.antdate.logic.LogicRequest;
import com.ant.antdate.register.RegisterRequest;
import com.ant.antdate.utils.Util;

import lib.frame.module.http.HttpResult;
import lib.frame.module.ui.BindView;
import lib.frame.utils.StringUtils;


public class ResetPassActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.et_pass)
    EditText et_pass;
    @BindView(R.id.iv_eyes)
    ImageView iv_eyes;
    @BindView (R.id.btn_next)
    Button btn_next;
    private String pass;
    private boolean passswitch;
    @Override
    protected void setRootView() {
        super.setRootView();
        rootViewId = R.layout.activity_reset_pass;
    }

    @Override
    protected void initView() {
        super.initView();
        passswitch = false;
        iv_back .setOnClickListener(this::onClick);
        iv_eyes .setOnClickListener(this::onClick);
        btn_next.setOnClickListener(this::onClick);
        et_pass.addTextChangedListener(new TextWatcher() {
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
                pass = et_pass.getText().toString();

                if (pass.length()>=4){
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
    public <T> void onHttpCallBack(int resultType, int reqId, String resContent, Object reqObject, HttpResult<T> httpResult) {
        super.onHttpCallBack(resultType, reqId, resContent, reqObject, httpResult);
        if (reqId==2){
            if (httpResult.getCode()==1000){
                goToActivity(MainActivity.class);
                Toast.makeText(this,httpResult.getMsg(),Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(this,httpResult.getMsg(),Toast.LENGTH_LONG).show();
            }
        }

        }

    @Override
    public <T> void onHttpErrorCallBack(int resultType, int reqId, String resContent, Object reqObject, HttpResult<T> httpResult) {
        super.onHttpErrorCallBack(resultType, reqId, resContent, reqObject, httpResult);
        if (reqId==2){
                String phone = Util.readIni(this,"phone","");
                Toast.makeText(this,"即将去注册",Toast.LENGTH_LONG).show();
                LogicRequest.sendSMS(1,phone, 1, getHttpHelper());
                goToActivity(SendVetifyCodeRegisterActivity.class);

        }

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_next:
                if (StringUtils.isPassword(et_pass.getText().toString())){
                    String phone = Util.readIni(this,"phone","");
                    String vertify = Util.readIni(this,"code","");
                    String psd = et_pass.getText().toString();
                    LogicRequest.ResetPsd(2,phone,vertify,psd,getHttpHelper());
                }else {
                    Toast.makeText(this,"密码格式有误",Toast.LENGTH_LONG).show();
                }


                break;
            case R.id.iv_eyes:
                if (!passswitch){
                    iv_eyes.setImageResource(R.mipmap.open_eye);
                    et_pass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    passswitch=true;
                }else {
                    iv_eyes.setImageResource(R.mipmap.icon_cloeye);
                    et_pass.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD|InputType.TYPE_CLASS_TEXT);
                    passswitch=false;
                }

                break;

        }
    }
}
