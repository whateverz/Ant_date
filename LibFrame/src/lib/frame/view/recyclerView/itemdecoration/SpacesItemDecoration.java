package lib.frame.view.recyclerView.itemdecoration;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.view.View;

/**
 * Created by lwxkey on 16/3/11.
 */
public class SpacesItemDecoration extends RecyclerView.ItemDecoration {

    private int spanCount;
    private int spacing;
    private boolean includeEdge;
    private int headerCount = 0;
    private int footerCount = 0;
    private int color;

    public SpacesItemDecoration(int spanCount, int spacing, boolean includeEdge) {
        this(spanCount, spacing, 0, includeEdge);
    }

    public SpacesItemDecoration(int spanCount, int spacing, int color, boolean includeEdge) {
        this.spanCount = spanCount;
        this.spacing = spacing;
        this.includeEdge = includeEdge;
        this.color = color;
    }

    public SpacesItemDecoration setHeaderCount(int headerCount) {
        this.headerCount = headerCount;
        return this;
    }

    public SpacesItemDecoration setFooterCount(int footerCount) {
        this.footerCount = footerCount;
        return this;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (color == 0)
            return;
        Paint paint = new Paint();
        paint.setColor(color);
        Rect outRect = new Rect();
        int totalCount = parent.getAdapter().getItemCount();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            int pos = parent.getChildAdapterPosition(child);
            int position = pos - headerCount;
            if (position < 0 || pos + footerCount >= totalCount)
                continue;

            final int top = child.getTop() - params.topMargin;
            final int bottom = child.getBottom() + params.bottomMargin;
            final int left = child.getLeft() - params.leftMargin;
            final int right = child.getRight() + params.rightMargin;

            if (includeEdge) {
                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
            if (outRect.top != 0) {
                Rect topRect = new Rect(left - outRect.left, top - outRect.top, right + outRect.right, top);
                c.drawRect(topRect, paint);
            }
            if (outRect.bottom != 0) {
                Rect bottomRect = new Rect(left - outRect.left, bottom + outRect.bottom, right + outRect.right, bottom);
                c.drawRect(bottomRect, paint);
            }
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, androidx.recyclerview.widget.RecyclerView parent, androidx.recyclerview.widget.RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildAdapterPosition(view); // item position
        int childCount = parent.getLayoutManager().getItemCount();
        if (position >= childCount - footerCount)
            return;
        position -= headerCount;
        if (position < 0)
            return;
        if (includeEdge) {
            if (position < spanCount) { // top edge
                outRect.top = spacing;
            }
            outRect.bottom = spacing; // item bottom
        } else {
            if (position >= spanCount) {
                outRect.top = spacing; // item top
            }
        }
    }

    private int getSpanCount(lib.frame.view.recyclerView.RecyclerView parent) {
        // 列数
        int spanCount = -1;
        lib.frame.view.recyclerView.RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            spanCount = ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
        }
        return spanCount;
    }
}