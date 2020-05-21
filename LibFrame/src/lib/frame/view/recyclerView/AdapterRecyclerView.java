package lib.frame.view.recyclerView;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by lwxkey on 16/1/7.
 */
public abstract class AdapterRecyclerView extends RecyclerView.Adapter<ItemRecyclerView> {

    protected String TAG = getClass().getSimpleName();

    protected Context mContext;

    protected boolean isEmpty;

    public AdapterRecyclerView(Context context) {
        this.mContext = context;
    }

    @Override
    public ItemRecyclerView onCreateViewHolder(ViewGroup parent, int viewType) {
        return createView(parent, viewType);
    }

    @Override
    public void onBindViewHolder(ItemRecyclerView holder, int position) {
        setData(holder.getItemView(), position);
    }

    @Override
    public int getItemViewType(int position) {
        if (isEmpty) {
            return -1;
        }
        return super.getItemViewType(position);
    }

    protected abstract void setData(View view, int position);

    protected abstract ItemRecyclerView createView(ViewGroup parent, int viewType);

    @Override
    public abstract int getItemCount();

}
