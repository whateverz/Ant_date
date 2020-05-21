package lib.frame.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import lib.frame.view.recyclerView.AdapterRecyclerView;
import lib.frame.view.recyclerView.ItemRecyclerView;

/**
 * Created by lwxkey on 16/8/19.
 */
public abstract class AdapterBaseList<T> extends AdapterRecyclerView {

    public List<T> mList;

    protected Context mContext;


    public void setLists(List<T> list) {
        this.mList = list;
    }

    public void setList(List<T> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    public List<T> getList() {
        return mList;
    }

    public AdapterBaseList(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected abstract void setData(View view, int position);

    @Override
    protected abstract ItemRecyclerView createView(ViewGroup parent, int viewType);

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }
}
