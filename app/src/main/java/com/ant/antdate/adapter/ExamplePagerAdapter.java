package com.ant.antdate.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import com.ant.antdate.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

import lib.frame.view.recyclerView.RecyclerView;

/**
 * Created by hackware on 2016/9/10.
 */

public class ExamplePagerAdapter extends PagerAdapter {
    private List<String> mDataList;

    public ExamplePagerAdapter(List<String> dataList) {
        mDataList = dataList;
    }

    @Override
    public int getCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                SmartRefreshLayout.LayoutParams.FILL_PARENT, SmartRefreshLayout.LayoutParams.WRAP_CONTENT);
        LayoutInflater inflater = LayoutInflater.from(container.getContext());
        View view = null;

        if (position==0){
          view = inflater.inflate(R.layout.view_top_ten, null);
            RecyclerView recyclerView = view.findViewById(R.id.rv_top_ten);
            //recyclerView.setAdapter(null);
        }else if (position==1){
            view = inflater.inflate(R.layout.view_top_ten, null);
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        TextView textView = (TextView) object;
        String text = textView.getText().toString();
        int index = mDataList.indexOf(text);
        if (index >= 0) {
            return index;
        }
        return POSITION_NONE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mDataList.get(position);
    }
}
