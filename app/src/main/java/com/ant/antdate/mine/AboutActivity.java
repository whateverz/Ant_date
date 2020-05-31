package com.ant.antdate.mine;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ant.antdate.R;
import com.ant.antdate.base.BaseActivity;
import com.ant.antdate.bean.LoginInfo;

import lib.frame.module.http.HttpResult;
import lib.frame.module.ui.BindView;

public class AboutActivity extends BaseActivity implements View.OnClickListener {
    private LoginInfo loginInfo;
    @BindView(R.id.back_iv)
    ImageView back_iv;
    @BindView(R.id.title_tv)
    TextView title_tv;
    @Override
    protected void setRootView() {
        super.setRootView();
        rootViewId = R.layout.activity_about;
    }

    @Override
    protected void initView() {
        super.initView();
        title_tv.setText("关于");
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
