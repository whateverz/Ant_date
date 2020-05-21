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
public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

    private int spanCount;
    private int spacing;
    private boolean includeEdge;
    private int headerCount = 0;
    private int footerCount = 0;
    private int color;

    public GridSpacingItemDecoration(int spanCount, int spacing, int color, boolean includeEdge) {
        this.spanCount = spanCount;
        this.spacing = spacing;
        this.includeEdge = includeEdge;
        this.color = color;
    }

    public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
        this(spanCount, spacing, 0, includeEdge);
    }

    public GridSpacingItemDecoration setHeaderCount(int headerCount) {
        this.headerCount = headerCount;
        return this;
    }

    public GridSpacingItemDecoration setFooterCount(int footerCount) {
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

            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - getPercentSpace(column); // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = getPercentSpace(column + 1); // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = getPercentSpace(column); // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - getPercentSpace(column + 1); // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
            if (outRect.left != 0) {
                c.drawRect(left - outRect.left, top - outRect.top, left, bottom + outRect.bottom, paint);
            }
            if (outRect.right != 0) {
                c.drawRect(right, top - outRect.top, right + outRect.right, bottom + outRect.bottom, paint);
            }
            if (outRect.top != 0) {
                c.drawRect(left - outRect.left, top - outRect.top, right + outRect.right, top, paint);
            }
            if (outRect.bottom != 0) {
                c.drawRect(left - outRect.left, bottom + outRect.bottom, right + outRect.right, bottom, paint);
            }
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        int childCount = parent.getLayoutManager().getItemCount();
        if (position >= childCount - footerCount)
            return;
        position -= headerCount;
        if (position < 0)
            return;
        int column = position % spanCount; // item column

        if (includeEdge) {
            outRect.left = spacing - getPercentSpace(column); // spacing - column * ((1f / spanCount) * spacing)
            outRect.right = getPercentSpace(column + 1); // (column + 1) * ((1f / spanCount) * spacing)

            if (position < spanCount) { // top edge
                outRect.top = spacing;
            }
            outRect.bottom = spacing; // item bottom
        } else {
            outRect.left = getPercentSpace(column); // column * ((1f / spanCount) * spacing)
            outRect.right = spacing - getPercentSpace(column + 1); // spacing - (column + 1) * ((1f /    spanCount) * spacing)
            if (position >= spanCount) {
                outRect.top = spacing; // item top
            }
        }
    }

    private int getPercentSpace(int column) {
        return (int) (column * spacing / spanCount * 1.0);
    }

    private int getSpanCount(RecyclerView parent) {
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