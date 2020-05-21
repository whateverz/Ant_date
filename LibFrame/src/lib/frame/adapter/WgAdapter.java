package lib.frame.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import lib.frame.view.item.ItemBase;
import lib.frame.view.recyclerView.ItemRecyclerView;


/**
 * Created by lwxkey on 16/8/19.
 */
public abstract class WgAdapter<T> extends AdapterBaseList<T> {

    public WgAdapter(Context context) {
        super(context);
    }

    @Override
    protected void setData(View view, int position) {
        ItemBase<T> itemBase = (ItemBase<T>) view;
        if (mList == null || mList.size() <= position || mList.get(position) == null)
            return;
        itemBase.setInfo(mList.get(position), position, mList.size());
        itemBase.setInfo(mList.get(position));
    }

    @Override
    protected ItemRecyclerView createView(ViewGroup parent, int viewType) {
        return new ItemRecyclerView(createItem(mContext));
    }

    protected abstract ItemBase<T> createItem(@NonNull Context context);

}
