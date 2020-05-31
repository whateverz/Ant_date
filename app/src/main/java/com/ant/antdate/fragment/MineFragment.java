package com.ant.antdate.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ant.antdate.R;
import com.ant.antdate.base.BaseFragment;
import com.ant.antdate.mine.AboutActivity;
import com.ant.antdate.mine.SettingActivity;

import lib.frame.module.ui.BindView;

public class MineFragment extends BaseFragment implements View.OnClickListener {
    private View view;

   private LinearLayout set_linl;
    @BindView(R.id.topic_linl)
    LinearLayout topic_linl;
    LinearLayout about_linl;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_mine,container,false);
        set_linl = view.findViewById(R.id.set_linl);
        about_linl = view.findViewById(R.id.about_linl);
        set_linl.setOnClickListener(this::onClick);
        about_linl.setOnClickListener(this::onClick);

        return view;
    }

    @Override
    protected void initView() {
        super.initView();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        Toast.makeText(getActivity(), "我的", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.set_linl:
                goToActivity(SettingActivity.class);
                break;
            case R.id.topic_linl:
                break;
            case R.id.about_linl:
                goToActivity(AboutActivity.class);
                break;

            default:
                break;
        }
    }
}
