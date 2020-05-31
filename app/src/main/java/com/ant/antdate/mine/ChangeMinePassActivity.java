package com.ant.antdate.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ant.antdate.R;
import com.ant.antdate.base.BaseActivity;
import com.ant.antdate.bean.LoginInfo;

import lib.frame.base.IdConfigBase;
import lib.frame.module.http.HttpResult;
import lib.frame.module.ui.AnnotateUtil;
import lib.frame.module.ui.BindView;
import lib.frame.utils.Log;

public class ChangeMinePassActivity extends BaseActivity implements View.OnClickListener {
    private LoginInfo loginInfo;
    @BindView(R.id.back_iv)
    ImageView back_iv;
    @BindView(R.id.title_tv)
    TextView title_tv;
    @BindView(R.id.tv_emailbd)
    TextView tv_emailbd;

    @Override
    protected void setRootView() {
        super.setRootView();
        rootViewId = R.layout.mine_change_pass;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        intentMsg = intent.getStringExtra("key");
        if (intent.getSerializableExtra("values") != null) {
            objects = (Object[]) intent.getSerializableExtra("values");
        }
        if (objects == null)
            objects = new Object[]{};

        setRootView();
        attchView();
        AnnotateUtil.initBindView(this);
        initView();// 初始化页面元素
        handleObject(intentMsg, objects);// 处理传递过来的数据
    }

    @Override
    public void handleObject(String tag, Object... objects) {
        Log.e("ZZZ",tag);
        if (tag == null) {

            tag = IdConfigBase.INTENT_TAG;
        } else if(tag.equals("changepass")){
            tv_emailbd.setVisibility(View.INVISIBLE);
            title_tv.setText("修改密码");
        }else if (tag.equals("email")){
            title_tv.setText("绑定邮箱");
            tv_emailbd.setVisibility(View.INVISIBLE);
        }else if (tag.equals("phone")){
            tv_emailbd.setVisibility(View.VISIBLE);
            title_tv.setText("修改绑定手机");
        }
    }

    @Override
    protected void initView() {
        super.initView();
        title_tv.setText("修改密码");
        back_iv.setOnClickListener(this::onClick);
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
        }
    }
}
