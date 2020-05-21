package lib.frame.view.recyclerView;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

/**
 * Created by lwxkey on 16/1/7.
 */
public class ItemRecyclerView extends RecyclerView.ViewHolder {

    private View itemView;

    public View getItemView() {
        return itemView;
    }

    public ItemRecyclerView(View itemView) {
        super(itemView);
        this.itemView = itemView;
    }
}
