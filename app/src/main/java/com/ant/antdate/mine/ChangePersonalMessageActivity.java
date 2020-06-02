package com.ant.antdate.mine;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ant.antdate.R;
import com.ant.antdate.activity.LoginActivity;
import com.ant.antdate.activity.MainActivity;
import com.ant.antdate.base.BaseActivity;
import com.ant.antdate.bean.LoginInfo;

import lib.frame.module.http.HttpResult;
import lib.frame.module.ui.BindView;

public class ChangePersonalMessageActivity extends BaseActivity implements View.OnClickListener {
    private LoginInfo loginInfo;
    @BindView(R.id.back_iv)
    ImageView back_iv;
    @BindView(R.id.title_tv)
    TextView title_tv;
    @BindView(R.id.btn_btn2)
    Button btn_btn2;
    @BindView(R.id.realname_linl)
    LinearLayout realname_linl;
    @BindView(R.id.tv_realname)
    TextView tv_realname;
    @BindView(R.id.Nikename_linl)
    LinearLayout Nikename_linl;
    @BindView(R.id.nikename_tv)
    TextView nikename_tv;
    @BindView(R.id.old_linl)
    LinearLayout old_linl;
    @BindView(R.id.old_tv)
    TextView old_tv;
    @BindView(R.id.weight_linl)
    LinearLayout weight_linl;
    @BindView(R.id.weight_tv)
    TextView weight_tv;
    @BindView(R.id.hight_linl)
    LinearLayout height_linl;
    @BindView(R.id.hight_tv)
    TextView hight_tv;
    @BindView(R.id.ll_live)
    LinearLayout ll_live;
    @BindView(R.id.live_place)
    TextView live_place;
    @BindView(R.id.ll_hujidi)
    LinearLayout ll_hujidi;
    @BindView(R.id.tv_huji)
    TextView tv_huji;
    @BindView(R.id.ll_married)
    LinearLayout ll_married;
    @BindView(R.id.tv_ifmarried)
    TextView tv_ifmarried;
    @BindView(R.id.ll_education)
    LinearLayout ll_education;
    @BindView(R.id.tv_education)
    TextView tv_education;
    @BindView(R.id.ll_school)
    LinearLayout ll_school;
    @BindView(R.id.tv_school)
    TextView tv_school;
    @Override
    protected void setRootView() {
        super.setRootView();
        rootViewId = R.layout.changepersonal;
    }

    @Override
    protected void initView() {
        super.initView();
        title_tv.setText("个人资料");
        btn_btn2.setVisibility(View.VISIBLE);
        back_iv.setOnClickListener(this::onClick);
        realname_linl.setOnClickListener(this::onClick);
        Nikename_linl.setOnClickListener(this::onClick);
        height_linl.setOnClickListener(this::onClick);
        weight_linl.setOnClickListener(this::onClick);
        old_linl.setOnClickListener(this::onClick);
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
            case R.id.btn_btn2:
                commit();
                break;
            case R.id.realname_linl:
                dialogIntput(tv_realname,"真实姓名","请输入真实姓名");
                break;
            case R.id.Nikename_linl:
                dialogIntput(nikename_tv,"昵称","请输入昵称");
                break;
            case R.id.old_linl:
                dialogIntput(old_tv,"年龄","请输入年龄");
                break;
            case R.id.hight_linl:
                dialogIntput(hight_tv,"身高","请输入身高(cm)");
                break;
            case R.id.weight_linl:
                dialogIntput(weight_tv,"体重","请输入体重（kg）");
                break;
        }
    }

    private void commit() {

    }

    public void dialogIntput(TextView v,String title,String hint){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                 builder.setTitle(title);
                final EditText et = new EditText(this);
                 et.setHint(hint);
                 et.setSingleLine(true);
                 builder.setView(et);
                 builder.setNegativeButton("取消",null);
                 builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                                String content = et.getText().toString();
                                v.setText(content);
                                 /*if (password.equals("123456")) {
                                       Toast.makeText(ChangePersonalMessageActivity.this, "连接成功", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(ChangePersonalMessageActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                                    }*/
                            }
                     });
                       AlertDialog alertDialog = builder.create();
                       alertDialog.show();

               }
}
