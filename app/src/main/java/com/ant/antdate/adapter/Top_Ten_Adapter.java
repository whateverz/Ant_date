package com.ant.antdate.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.ant.antdate.fragment.SquareFragment;

import java.util.ArrayList;
import java.util.List;

public class Top_Ten_Adapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList = new ArrayList<Fragment>();
    private String[] strings = new String[]{"top10","B","C","D"};
    public Top_Ten_Adapter(@NonNull FragmentManager fm,List<Fragment> fragmentList) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return strings[position];
    }
}
