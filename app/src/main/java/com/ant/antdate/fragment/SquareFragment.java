package com.ant.antdate.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.viewpager.widget.ViewPager;

import com.ant.antdate.R;
import com.ant.antdate.adapter.Top_Ten_Adapter;
import com.ant.antdate.base.BaseFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import lib.frame.module.ui.BindView;

public class SquareFragment extends BaseFragment {

    private TabLayout tablayout;
    private  ViewPager viewpager;
    private ArrayList<String> mTabs = new ArrayList<>();
    private Top_Ten_Adapter top_ten_adapter;
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_squre,container,false);
        viewpager = view.findViewById(R.id.viewpager);
        tablayout = view.findViewById(R.id.tablayout);
      //  initTabView();
        return view;
    }
    private void initTabView() {
        mTabs.add("top10");
        mTabs.add("蚂蚁热区");
        top_ten_adapter = new Top_Ten_Adapter(getContext(), getChildFragmentManager(), mTabs);

        viewpager.setAdapter(top_ten_adapter);

        viewpager.setOffscreenPageLimit(0);

        tablayout.setupWithViewPager(viewpager);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        Toast.makeText(getActivity(), "广场", Toast.LENGTH_SHORT).show();
    }
}
