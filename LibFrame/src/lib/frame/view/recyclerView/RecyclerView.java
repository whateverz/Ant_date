package lib.frame.view.recyclerView;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.AttributeSet;
import android.view.View;

import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.yqritc.recyclerviewflexibledivider.VerticalDividerItemDecoration;

import lib.frame.R;
import lib.frame.base.IdConfigBase;
import lib.frame.view.recyclerView.itemdecoration.GridSpacingItemDecoration;
import lib.frame.view.recyclerView.itemdecoration.SpacesItemDecoration;


/**
 * Created by lwxkey on 16/1/7.
 */
public class RecyclerView extends androidx.recyclerview.widget.RecyclerView {

    private Context mContext;
    private Adapter mAdapter;

    private boolean isLoadingMore;
    private int[] lastPositions;
    public static final int VERTICAL = StaggeredGridLayoutManager.VERTICAL;
    public static final int HORIZONTAL = StaggeredGridLayoutManager.HORIZONTAL;

    private LayoutManager layoutManager;

    private OnRecyclerViewListener mListener;
    private int itemCount;
    private int numColumn;

    private boolean isCanScrollVertical = true;
    private boolean isCanScrollHorizontal = true;

    public int getNumColumn() {
        return numColumn;
    }

    public int getCurPosition() {
//        layoutManager.getc
        return 0;
    }

    protected void initThis() {
        initList();
        addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull androidx.recyclerview.widget.RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
//                switch (newState) {
//                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
//                        LogicImgShow.getInstance(mContext).getImageLoader().pause();
//                        break;
//                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
//                        LogicImgShow.getInstance(mContext).getImageLoader().resume();
//                        break;
//                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
//                        LogicImgShow.getInstance(mContext).getImageLoader().pause();
//                        break;
//                    default:
//                        break;
//                }
            }

            @Override
            public void onScrolled(@NonNull androidx.recyclerview.widget.RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItem = getLastVisiblaPosition();
                int totalItemCount = layoutManager.getItemCount();
                // dy>0 表示向下滑动
                if (totalItemCount != itemCount) {//item数量变化,代表已经加载完数据
                    isLoadingMore = false;
                }
                if (!isLoadingMore && lastVisibleItem >= totalItemCount - IdConfigBase.PAGESIZE / 2 && dy > 0) {
                    if (mListener != null) {
                        isLoadingMore = true;
                        itemCount = totalItemCount;
                        mListener.loadMore();
                    }
                }
            }
        });
    }

    public int getLastVisiblaPosition() {
        if (layoutManager instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
            if (lastPositions == null) {
                lastPositions = new int[staggeredGridLayoutManager.getSpanCount()];
            }
            staggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions);
            return findMax(lastPositions);
        }
        return 0;
    }

    public int getFirstVisiblaPosition() {
        if (layoutManager instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
            if (lastPositions == null) {
                lastPositions = new int[staggeredGridLayoutManager.getSpanCount()];
            }
            staggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions);
            return findMin(lastPositions);
        }
        return 0;
    }

    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    private int findMin(int[] lastPositions) {
        int min = lastPositions[0];
        for (int value : lastPositions) {
            if (value < min) {
                min = value;
            }
        }
        return min;
    }

    public void initList() {
        initList(1, VERTICAL);
    }

    public void initList(int numColumn) {
        initList(numColumn, VERTICAL);
    }

    //设置item间距
    public void setSpace(int space, boolean includeEdge) {
        addItemDecoration(new SpacesItemDecoration(numColumn, space, includeEdge));
    }

    public void setGridSpace(int space, boolean includeEdge) {
        addItemDecoration(new GridSpacingItemDecoration(numColumn, space, includeEdge));
    }

    public void initList(int numColumn, int orientation) {
        initList(numColumn, orientation, false);
    }

    public void initList(int numColumn, int orientation, boolean reverseLayout) {
        this.numColumn = numColumn;
        if (numColumn == 1) {
            layoutManager = new LinearLayoutManager(mContext, orientation, reverseLayout) {
                @Override
                public boolean canScrollVertically() {
                    return isCanScrollVertical && super.canScrollVertically();
                }

                @Override
                public boolean canScrollHorizontally() {
                    return isCanScrollHorizontal && super.canScrollHorizontally();
                }
            };
        } else {
            layoutManager = new GridLayoutManager(mContext, numColumn, orientation, reverseLayout) {
                @Override
                public boolean canScrollVertically() {
                    return isCanScrollVertical && super.canScrollVertically();
                }

                @Override
                public boolean canScrollHorizontally() {
                    return isCanScrollHorizontal && super.canScrollHorizontally();
                }
            };
        }
        setLayoutManager(layoutManager);

    }

    public int getScollYDistance() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) this.getLayoutManager();
        int position = layoutManager.findFirstVisibleItemPosition();
        View firstVisiableChildView = layoutManager.findViewByPosition(position);
        int itemHeight = firstVisiableChildView.getHeight();
        return (position) * itemHeight - firstVisiableChildView.getTop();
    }

    public void setStackFromEnd(boolean stackFromEnd) {
        if (layoutManager instanceof LinearLayoutManager) {
            ((LinearLayoutManager) layoutManager).setStackFromEnd(stackFromEnd);
        }
    }

    public void goToPosition(int position) {
        if (layoutManager instanceof LinearLayoutManager) {
            ((LinearLayoutManager) layoutManager).scrollToPositionWithOffset(position, 0);
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            ((StaggeredGridLayoutManager) layoutManager).scrollToPositionWithOffset(position, 0);
        }
    }

    @Override
    public void setAdapter(Adapter adapter) {
        mAdapter = adapter;
        super.setAdapter(adapter);
    }

    public void setVerticalScrollEnable(boolean verticalScrollEnable) {
        isCanScrollVertical = verticalScrollEnable;
    }

    public void setHorizontalScrollEnable(boolean horizontalScrollEnable) {
        isCanScrollHorizontal = horizontalScrollEnable;
    }


    public void setDivider(int color, int size) {
        addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext)
                .color(color).size(size).build());
    }

    public void setDefaultDivider() {
        addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext)
                .colorResId(R.color.divider).size(1).build());
    }

    public void setVDefaultDivider() {
        addItemDecoration(new VerticalDividerItemDecoration.Builder(mContext)
                .colorResId(R.color.divider).size(1).build());
    }

    public void setDividerDecration(ItemDecoration dividerDecration) {
        addItemDecoration(dividerDecration);
    }

    public RecyclerView(Context context) {
        super(context);
        mContext = context;
        initThis();
    }

    public RecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initThis();
    }

    public RecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        initThis();
    }

    public interface OnRecyclerViewListener {
        void loadMore();
    }

    public void setOnRecyclerViewListener(OnRecyclerViewListener l) {
        mListener = l;
    }


}
