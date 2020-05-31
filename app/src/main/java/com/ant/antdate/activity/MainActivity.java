package com.ant.antdate.activity;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ant.antdate.R;
import com.ant.antdate.base.BaseActivity;
import com.ant.antdate.base.BaseFragment;
import com.ant.antdate.fragment.MailListFragment;
import com.ant.antdate.fragment.MineFragment;
import com.ant.antdate.fragment.SquareFragment;
import com.ant.antdate.logic.LogicRequest;
import com.ant.antdate.register.RegisterRequest;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    public static final int HOME_PAGE_INDEX = 0;
    public static final int MY_ACCOUNT_PAGE_INDEX = 1;
    public static final int ZONE_PAGE_INDEX = 2;
    public static final int MORE_PAGE_INDEX = 3;
    private FragmentManager fm;
    private BaseFragment[] listFragments;
    private String[] tabArray = {"广场", "通讯录", "我的"};
    private int index = 0;
    private ColorStateList title_color;
    private ColorStateList theme_green;
    private TextView titleName;
    private TextView tv_homePage, tv_myAccountPage, tv_zonePage;

    @Override
    protected void setRootView() {
        super.setRootView();
        rootViewId = R.layout.activity_main;
        //init();
    }

    @Override
    protected void loadData() {
        super.loadData();
        // LogicRequest.sendSMS(1, "13581714368", 1, getHttpHelper());
        //RegisterRequest.Register(2, "13581714368", "1696", getHttpHelper());
    }

    @Override
    protected void initView() {
        super.initView();
        // TODO Auto-generated method stub
        // 先初始化变量、控件并设置点击事件监听
        title_color = getResources().getColorStateList(R.color.title_color);
        theme_green = getResources().getColorStateList(R.color.theme_color_green);
        titleName = (TextView) findViewById(R.id.title_name);

        tv_homePage = (TextView) findViewById(R.id.home_page);
        tv_myAccountPage = (TextView) findViewById(R.id.my_account_page);
        tv_zonePage = (TextView) findViewById(R.id.zone_page);

        tv_homePage.setOnClickListener(this);
        tv_myAccountPage.setOnClickListener(this);
        tv_zonePage.setOnClickListener(this);
        // 将四个Fragment全部放入BaseFragment的数组中，后续就用listFragments索引 的形式操纵Fragment
        listFragments = new BaseFragment[tabArray.length];
        listFragments[HOME_PAGE_INDEX] = new SquareFragment();
        listFragments[MY_ACCOUNT_PAGE_INDEX] = new MailListFragment();
        listFragments[ZONE_PAGE_INDEX] = new MineFragment();

        // 获取事务处理的FragmentTransaction，FragmentTransaction的相关api可以对fragment进行添加、移除、隐藏、显示等操作
        fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        // 初始化时先看listFragments[0](即首页HomePageFragment)是否已经被添加
        // 如果没有添加，则通过FragmentTransaction向activity提交一个添加HomePageFragment的事务
        // 如果已经添加过，则通过FragmentTransaction向activity提交隐藏之前fragment并显示HomePageFragment的请求
        // 从这里可以看出，FragmentTransaction可以提交一套事务让activity处理
        if (!listFragments[0].isAdded()) {
            ft.add(R.id.container_layout, listFragments[0]).commit();
        } else {
            ft.hide(listFragments[index]).show(listFragments[0]).commit();
        }
        setPageStyle(HOME_PAGE_INDEX);

    }


    private void setPageStyle(int pageIndex) {
        tv_homePage.setTextColor(title_color);
        tv_myAccountPage.setTextColor(title_color);
        tv_zonePage.setTextColor(title_color);

        tv_homePage.setCompoundDrawablesWithIntrinsicBounds(0,
                R.mipmap.squre_no, 0, 0);
        tv_myAccountPage.setCompoundDrawablesWithIntrinsicBounds(0,
                R.mipmap.mail_list_no, 0, 0);
        tv_zonePage.setCompoundDrawablesWithIntrinsicBounds(0,
                R.mipmap.mine_no, 0, 0);

        switch (pageIndex) {
            case HOME_PAGE_INDEX:
                titleName.setText("广场");
                //  titleName.setText(getResources().getString(R.string.app_name));
                tv_homePage.setTextColor(theme_green);
                tv_homePage.setCompoundDrawablesWithIntrinsicBounds(0,
                        R.mipmap.squre_checked, 0, 0);
                break;
            case MY_ACCOUNT_PAGE_INDEX:
                titleName.setText("通讯录");
                // titleName.setText(getResources().getString(R.string.my_account));
                tv_myAccountPage.setTextColor(theme_green);
                tv_myAccountPage.setCompoundDrawablesWithIntrinsicBounds(0,
                        R.mipmap.maillist_checked, 0, 0);
                break;
            case ZONE_PAGE_INDEX:
                titleName.setText("我的");
                //titleName.setText(getResources().getString(R.string.zone_page));
                tv_zonePage.setTextColor(theme_green);
                tv_zonePage.setCompoundDrawablesWithIntrinsicBounds(0,
                        R.mipmap.mine_checked, 0, 0);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            new AlertDialog.Builder(this)
                    .setMessage("确定退出系统吗？")
                    .setNegativeButton("取消",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                }
                            })
                    .setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int whichButton) {
                                    finish();
                                }
                            }).show();

            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.home_page:
                showPageFragment(HOME_PAGE_INDEX);
                break;
            case R.id.my_account_page:
                showPageFragment(MY_ACCOUNT_PAGE_INDEX);
                break;

            case R.id.zone_page:
                showPageFragment(ZONE_PAGE_INDEX);
                break;
            default:
                break;
        }
    }
    // 该函数根据不同的索引值，去显示相应的Fragment
    private void showPageFragment(int pageIndex) {
        setPageStyle(pageIndex);
        // 关于Fragment的事务处理就要使用FragmentTransaction
        FragmentTransaction ft = fm.beginTransaction();
        switch (pageIndex) {
            case HOME_PAGE_INDEX:
            case MY_ACCOUNT_PAGE_INDEX:
            case ZONE_PAGE_INDEX:
            case MORE_PAGE_INDEX:
                // 如果即将打开的Fragment没有被添加过，则将上一个Fragment隐藏，并将即将要显示的Fragment添加
                // 如果即将打开的Fragment已经被添加过，则将上一个Fragment隐藏，并将即将要显示的Fragment显示
                int nextIndex = pageIndex;
                if (!listFragments[nextIndex].isAdded()) {
                    ft.hide(listFragments[index]).add(R.id.container_layout,
                            listFragments[nextIndex]);
                } else {
                    ft.hide(listFragments[index]).show(listFragments[nextIndex]);
                }
                index = pageIndex;
                break;
            default:
                break;
        }
        // 最后不要忘了将事务提交(提交给activity处理)
        ft.commit();
    }
}
