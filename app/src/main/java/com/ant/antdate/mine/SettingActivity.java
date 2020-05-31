package com.ant.antdate.mine;

import android.content.Intent;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ant.antdate.R;
import com.ant.antdate.activity.ForgetPassActivity;
import com.ant.antdate.activity.LoginActivity;
import com.ant.antdate.activity.MainActivity;
import com.ant.antdate.activity.RegistActivity;
import com.ant.antdate.activity.VtifyPhoneActivity;
import com.ant.antdate.base.BaseActivity;
import com.ant.antdate.bean.LoginInfo;
import com.ant.antdate.logic.LogicRequest;
import com.ant.antdate.utils.Util;

import lib.frame.module.http.HttpResult;
import lib.frame.module.ui.BindView;

public class SettingActivity extends BaseActivity implements View.OnClickListener {
    private LoginInfo loginInfo;
    @BindView(R.id.back_iv)
    ImageView back_iv;
    @BindView(R.id.ll_logout)
    LinearLayout ll_logout;
    @BindView(R.id.tv_changemessage)
    TextView tv_changemessage;
    @BindView(R.id.tv_changepass)
    TextView tv_changepass;
    @BindView(R.id.tv_change_phone)
    TextView tv_change_phone;
    @BindView(R.id.tv_change_email)
    TextView tv_change_email;
    private String tag;
    @Override
    protected void setRootView() {
        super.setRootView();
        rootViewId = R.layout.activity_setting;
    }

    @Override
    protected void initView() {
        super.initView();
        back_iv.setOnClickListener(this::onClick);
        ll_logout.setOnClickListener(this::onClick);
        tv_changemessage.setOnClickListener(this::onClick);
        tv_changepass.setOnClickListener(this::onClick);
        tv_change_phone.setOnClickListener(this::onClick);
        tv_change_email.setOnClickListener(this::onClick);
    }

    @Override
    protected void loadData() {
        super.loadData();
    }

    @Override
    public <T> void onHttpCallBack(int resultType, int reqId, String resContent, Object reqObject, HttpResult<T> httpResult) {
        super.onHttpCallBack(resultType, reqId, resContent, reqObject, httpResult);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.back_iv:
                finish();
                break;
            case R.id.tv_changemessage:
                goToActivity(ChangePersonalMessageActivity.class);
                break;
            case R.id.tv_changepass:
                //修改密码
                tag = "changepass";
                Intent intent = new Intent();
                intent.setClass(this, ChangeMinePassActivity.class);
                intent.putExtra("key", tag);
                startActivity(intent);
               // goToActivity(ChangeMinePassActivity.class,tag);
                break;
            case R.id.tv_change_phone:
                tag = "phone";
                Intent intent1 = new Intent();
                intent1.setClass(this, ChangeMinePassActivity.class);
                intent1.putExtra("key", tag);
                startActivity(intent1);
                break;
            case R.id.tv_change_email:
                tag = "email";
                Intent intent2 = new Intent();
                intent2.setClass(this, ChangeMinePassActivity.class);
                intent2.putExtra("key", tag);
                startActivity(intent2);
                break;
            case R.id.ll_logout:
                finish();
               // mApp.logout();
                goToActivity(LoginActivity.class);
                break;
        }
    }
}
