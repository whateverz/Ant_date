package com.ant.antdate.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import lib.frame.adapter.WgMutiAdapter;
import lib.frame.view.item.ItemBase;
import lib.frame.view.recyclerView.ItemRecyclerView;

public abstract class BaseExpandableAdapter<T, K> extends WgMutiAdapter<T> {

    private List<Integer[]> expandableMap = new ArrayList<>();

    public BaseExpandableAdapter(Context context) {
        super(context);
    }

    @Override
    protected void setData(View view, int position) {
        Integer[] result = getExpanablePostion(position);
        if (result[1] == -1)
            setItemData(view, result[0]);
        else
            setSubItemData(view, result[0], result[1]);
    }

    protected void setItemData(View view, int position) {
        ItemBase<T> itemBase = (ItemBase<T>) view;
        T item = getItem(position);
        itemBase.setInfo(item, position, mList.size());
        itemBase.setInfo(item);

    }

    protected void setSubItemData(View view, int position, int subPosition) {
        ItemBase<K> itemBase = (ItemBase<K>) view;
        K subItem = getSubItem(position, subPosition);
        itemBase.setInfo(subItem, subPosition, mList.size());
        itemBase.setInfo(subItem);
    }

    @Override
    protected ItemRecyclerView createView(ViewGroup parent, int viewType) {
        if (viewType == -1)
            return new ItemRecyclerView(createItem(mContext, viewType));
        else
            return new ItemRecyclerView(createSubItem(mContext, viewType));
    }

    @Override
    protected abstract ItemBase<T> createItem(Context context, int type);

    protected abstract ItemBase<K> createSubItem(Context context, int type);


    private Integer[] getExpanablePostion(int position) {
        return expandableMap.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        Integer[] result = getExpanablePostion(position);
        return result[1] == -1 ? -1 : 0;
    }

    protected abstract int getGroupCount();

    protected abstract int getItemCount(int position);

    protected abstract T getItem(int position);

    protected abstract K getSubItem(int position, int subPosition);


    @Override
    public int getItemCount() {
        return expandableMap.size();
    }

    private void buildExpandableMap() {
        expandableMap.clear();
        int groupCount = getGroupCount();
        for (int i = 0; i < groupCount; i++) {
            expandableMap.add(new Integer[]{i, -1});
            for (int j = 0; j < getItemCount(i); j++)
                expandableMap.add(new Integer[]{i, j});
        }
    }

    @Override
    public void setList(List<T> list) {
        mList = list;
        buildExpandableMap();
        notifyDataSetChanged();
    }

    @Override
    public void setLists(List<T> list) {
        mList = list;
        buildExpandableMap();
    }
}
