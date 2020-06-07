package com.ant.antdate.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.ant.antdate.R;
import com.ant.antdate.base.BaseFragment;
import com.ant.antdate.fragment.home.HotFragment;
import com.ant.antdate.fragment.home.TopFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.badge.BadgePagerTitleView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lib.frame.adapter.AdapterBaseFm;
import lib.frame.base.BaseFrameFragment;

public class SquareFragment extends BaseFragment implements View.OnClickListener {

    MagicIndicator magicIndicator;

    private ViewPager viewpager;
    private static final String[] CHANNELS = new String[]{"TOP.10", "蚂蚁热区"};
    private List<String> mDataList = Arrays.asList(CHANNELS);

    private AdapterBaseFm adapterBaseFm;
    private FloatingActionButton fab;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_squre, container, false);
        viewpager = view.findViewById(R.id.viewpager);
        magicIndicator = view.findViewById(R.id.magic_indicator);
        fab = view.findViewById(R.id.fab);
        adapterBaseFm = new AdapterBaseFm(mContext, getChildFragmentManager());
        adapterBaseFm.setFragmentList(new BaseFrameFragment[]{new TopFragment(), new HotFragment()});
        viewpager.setAdapter(adapterBaseFm);
        initMagicIndicator4();
        fab.setOnClickListener(this);
        return view;
    }

    private void initMagicIndicator4() {
        CommonNavigator commonNavigator = new CommonNavigator(getContext());
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                BadgePagerTitleView badgePagerTitleView = new BadgePagerTitleView(context);

                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setNormalColor(Color.GRAY);
                simplePagerTitleView.setSelectedColor(getResources().getColor(R.color.theme_color_green));
                simplePagerTitleView.setText(mDataList.get(index));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewpager.setCurrentItem(index);
                    }
                });
                badgePagerTitleView.setInnerPagerTitleView(simplePagerTitleView);

                return badgePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
                linePagerIndicator.setColors(getResources().getColor(R.color.theme_color_green));
                linePagerIndicator.setLineWidth(90);
                return linePagerIndicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        LinearLayout titleContainer = commonNavigator.getTitleContainer(); // must after setNavigator
        titleContainer.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        titleContainer.setDividerDrawable(new ColorDrawable() {
            @Override
            public int getIntrinsicWidth() {
                return UIUtil.dip2px(getContext(), 5);
            }
        });
        ViewPagerHelper.bind(magicIndicator, viewpager);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        Toast.makeText(getActivity(), "广场", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.fab:
                getActivity().findViewById(R.id.rl_topic).setVisibility(View.VISIBLE);
                break;
        }

    }
}
