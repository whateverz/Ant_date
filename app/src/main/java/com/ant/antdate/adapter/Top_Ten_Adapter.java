package com.ant.antdate.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.ant.antdate.fragment.SquareFragment;

import java.util.ArrayList;

public class Top_Ten_Adapter extends FragmentStatePagerAdapter {
    private ArrayList<String> mList;
    private ArrayList<Fragment> fragmentsList;

    protected Context mContext;

    public Top_Ten_Adapter(Context context, FragmentManager fm, ArrayList<String> arrayList) {
        super(fm);
        this.mContext = context;
        this.mList = arrayList;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mList.size();
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return mList.get(position);
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {

        return null;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        if (observer != null) {
            super.unregisterDataSetObserver(observer);
        }
    }
}
