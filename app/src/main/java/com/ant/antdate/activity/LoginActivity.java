package com.ant.antdate.activity;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ant.antdate.R;
import com.ant.antdate.base.BaseActivity;
import com.ant.antdate.bean.LoginInfo;
import com.ant.antdate.bean.Userinfo;
import com.ant.antdate.logic.LogicRequest;
import com.ant.antdate.register.RegisterRequest;
import com.ant.antdate.utils.Util;

import lib.frame.module.http.HttpResult;
import lib.frame.module.ui.BindView;
import lib.frame.utils.Log;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    @BindView (R.id.btn_login)
    Button btn_login;
    @BindView(R.id.tv_loginby_user)
    TextView tv_loginby_user;
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.et_pwd)
    EditText et_pwd;
    @BindView(R.id.iv_delete)
    ImageView iv_delete;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.iv_eyes)
    ImageView iv_eyes;
    @BindView(R.id.tv_forget_pass)
    TextView tv_forget_pass;

    @BindView(R.id.tv_login_by_vertify)
    TextView tv_login_by_vertify;
    @BindView(R.id.tv_registe)
    TextView tv_registe;

    private String phone;
    private boolean passswitch;
    private boolean isLogin;
    private LoginInfo loginInfo;
    @Override
    protected void setRootView() {
        super.setRootView();
        rootViewId = R.layout.activity_login_new;
    }

    @Override
    protected void initView() {
        super.initView();
        if (mApp.getUserInfo()!=null){
            goToActivity(MainActivity.class);
        }
        passswitch = false;

        btn_login.setOnClickListener(this::onClick);
        tv_loginby_user.setOnClickListener(this::onClick);
        iv_delete.setOnClickListener(this::onClick);
        tv_forget_pass.setOnClickListener(this::onClick);
      //  iv_back.setOnClickListener(this::onClick);
        tv_login_by_vertify.setOnClickListener(this::onClick);
        tv_registe.setOnClickListener(this::onClick);
        iv_eyes.setOnClickListener(this::onClick);
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
                    btn_login.setBackgroundResource((R.mipmap.bg_canlogin));
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
      //  RegisterRequest.Register(2, "13581714368", "1696", getHttpHelper());
    }

    @Override
    public <T> void onHttpCallBack(int resultType, int reqId, String resContent, Object reqObject, HttpResult<T> httpResult) {
        super.onHttpCallBack(resultType, reqId, resContent, reqObject, httpResult);
        loginInfo = new LoginInfo();
        Userinfo userinfo = new Userinfo();
        if (reqId==1){
            if (httpResult.getCode()==1000){
                loginInfo = HttpResult.getResults(httpResult);
                Toast.makeText(this,httpResult.getMsg(),Toast.LENGTH_LONG).show();
                mApp.setUserInfo(loginInfo.getUser());
                Util.writeIni(this,"isLogin",true);
                goToActivity(MainActivity.class);
            }else {
                Toast.makeText(this,httpResult.getMsg(),Toast.LENGTH_LONG).show();
            }

        }
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
                LogicRequest.Login(1, et_phone.getText().toString(), et_pwd.getText().toString(), getHttpHelper());
               // LogicRequest.sendSMS(1, "13581714368", 1, getHttpHelper());

                break;
            case R.id.tv_loginby_user:
                goToActivity(MainActivity.class);
                break;
            case R.id.iv_delete:
               et_phone.setText("");
                break;
            case R.id.tv_forget_pass:
                Util.writeIni(this,"CodeType","forgetpass");
                goToActivity(ForgetPassActivity.class);
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_login_by_vertify:
                goToActivity(VtifyPhoneActivity.class);
                break;
            case R.id.tv_registe:
                Util.writeIni(this,"CodeType","register");
                goToActivity(RegistActivity.class);
                break;
            case R.id.iv_eyes:
                if (!passswitch){
                    iv_eyes.setImageResource(R.mipmap.open_eye);
                    et_pwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    passswitch=true;
                }else {
                    iv_eyes.setImageResource(R.mipmap.icon_cloeye);
                    et_pwd.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD|InputType.TYPE_CLASS_TEXT);
                    passswitch=false;
                }

                break;
        }
    }
}
