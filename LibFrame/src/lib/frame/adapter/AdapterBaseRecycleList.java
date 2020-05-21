package lib.frame.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import lib.frame.view.recyclerView.AdapterRecyclerView;
import lib.frame.view.recyclerView.ItemRecyclerView;

/**
 * Created by lwxkey on 16/1/7.
 */
public abstract class AdapterBaseRecycleList<T> extends AdapterBaseList<T> {

    protected String TAG = getClass().getSimpleName();

    protected List<T> list = new ArrayList<>();

    protected boolean isEmpty;

    public AdapterBaseRecycleList(Context context) {
        super(context);
    }

    public void setList(List<T> list) {
        this.list = list;
        super.setList(list);
    }

    @Override
    public int getItemViewType(int position) {
        if (isEmpty) {
            return -1;
        }
        return super.getItemViewType(position);
    }

    @Override
    public abstract int getItemCount();

}
