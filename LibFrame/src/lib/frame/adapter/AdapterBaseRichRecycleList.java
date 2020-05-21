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
public abstract class AdapterBaseRichRecycleList<A, B> extends AdapterRecyclerView {

    protected String TAG = getClass().getSimpleName();

    protected List<B> list = new ArrayList<>();

    protected boolean isEmpty;

    protected A dataHeader;

    public void setDataHeader(A dataHeader) {
        this.dataHeader = dataHeader;
        notifyDataSetChanged();
    }

    public AdapterBaseRichRecycleList(Context context) {
        super(context);
    }

    public A getDataHeader() {
        return dataHeader;
    }

    public void setList(List<B> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (isEmpty) {
            return -1;
        }
        return super.getItemViewType(position);
    }

    public abstract void setData(View view, int position);

    public abstract ItemRecyclerView createView(ViewGroup parent, int viewType);

    @Override
    public abstract int getItemCount();

}
