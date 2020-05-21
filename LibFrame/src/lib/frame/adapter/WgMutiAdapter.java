package lib.frame.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import lib.frame.view.item.ItemBase;
import lib.frame.view.recyclerView.ItemRecyclerView;


/**
 * Created by lwxkey on 16/8/19.
 */
public abstract class WgMutiAdapter<T> extends AdapterBaseList<T> {

    public WgMutiAdapter(Context context) {
        super(context);
    }

    @Override
    protected void setData(View view, int position) {
        ItemBase<T> itemBase = (ItemBase<T>) view;
        itemBase.setInfo(mList.get(position), position, mList.size());
        itemBase.setInfo(mList.get(position));
    }

    @Override
    protected ItemRecyclerView createView(ViewGroup parent, int viewType) {
        return new ItemRecyclerView(createItem(mContext, viewType));
    }

    protected abstract ItemBase<T> createItem(Context context, int type);

    @Override
    public abstract int getItemViewType(int position);
}
