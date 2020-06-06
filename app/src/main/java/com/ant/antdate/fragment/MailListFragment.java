package com.ant.antdate.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ant.antdate.R;
import com.ant.antdate.base.BaseFragment;
import com.bumptech.glide.Glide;

import lib.frame.module.ui.BindView;

public class MailListFragment extends BaseFragment {
    private View view;
    @BindView(R.id.iv)
    ImageView iv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_maillist,container,false);
        iv = view.findViewById(R.id.iv);
        Glide.with(this).load("https://oss.antdate.cn/be53cc1d-d5e7-430f-8bcd-0ed803659a04.png?x-oss-process=image/interlace,1/resize,p_60/quality,q_75/format,jpeg").placeholder(R.mipmap.default_photo).into(iv);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        Toast.makeText(getActivity(), "通讯录", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void initView() {
        super.initView();
        Glide.with(getActivity()).load("http://oss.antdate.cn/v2-f8db7402dfcb8dd9cb993273370ea7ef.jpeg").placeholder(R.mipmap.default_photo).into(iv);
    }
}
