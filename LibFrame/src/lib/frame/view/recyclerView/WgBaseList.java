package lib.frame.view.recyclerView;

import android.content.Context;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ItemDecoration;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import lib.frame.R;
import lib.frame.base.BaseFrameView;
import lib.frame.view.recyclerView.itemdecoration.GridSpacingItemDecoration;
import lib.frame.view.recyclerView.itemdecoration.SpacesItemDecoration;
import lib.frame.view.recyclerView.wrapper.HeaderAndFooterWrapper;

/**
 * Created by shuaq on 2016/8/19.
 */

public class WgBaseList extends BaseFrameView {

    protected SwipeRefreshLayout vBody;
    protected RecyclerView vList;
    protected FrameLayout vLoading;
    protected FrameLayout vError;

    protected Adapter mAdapter;

//    private DiffUtil.Callback mDiffCallBack;

    protected int mHeaderNum = 0;
    protected int mFootNum = 0;

    public WgBaseList(Context context) {
        super(context);
    }

    public WgBaseList(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WgBaseList(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getLayout() {
        return R.layout.list_base;
    }


    @Override
    protected void initThis() {
        vBody = $(R.id.base_list_body);
        vList = $(R.id.base_list);
        vLoading = $(R.id.base_list_loding);
        vError = $(R.id.base_list_error);
        vBody.setColorSchemeResources(R.color.theme_color);
        vList.initList();
    }

    public void initList(int numColumn) {
        vList.initList(numColumn);
    }

    public void initList(int numColumn, int orientation) {
        vList.initList(numColumn, orientation);
    }

    public void setGridSpace(int space, boolean includeEdge) {
        setGridSpace(space, 0, includeEdge);
    }

    public void setGridSpace(int space, int color, boolean includeEdge) {
        vList.addItemDecoration(new GridSpacingItemDecoration(vList.getNumColumn(), space, color, includeEdge)
                .setHeaderCount(mHeaderNum)
                .setFooterCount(mFootNum));
    }

    public void setLineSpace(int space, boolean includeEdge) {
        vList.addItemDecoration(new SpacesItemDecoration(vList.getNumColumn(), space, includeEdge)
                .setHeaderCount(mHeaderNum)
                .setFooterCount(mFootNum));
    }

    public void setLineSpace(int space, int color, boolean includeEdge) {
        vList.addItemDecoration(new SpacesItemDecoration(vList.getNumColumn(), space, color, includeEdge)
                .setHeaderCount(mHeaderNum)
                .setFooterCount(mFootNum));
    }

    public void setAdapter(Adapter adapter) {
        mAdapter = adapter;
        vList.setAdapter(mAdapter);
    }

    public void setRefresh(SwipeRefreshLayout.OnRefreshListener listener) {
        vBody.setOnRefreshListener(listener);
    }

    public void setLoadMore(RecyclerView.OnRecyclerViewListener listener) {
        vList.setOnRecyclerViewListener(listener);
    }

    public RecyclerView getListView() {
        return vList;
    }

    public void setRefreshing(boolean refreshing) {
        vBody.setRefreshing(refreshing);

    }

    public boolean isRefreshing() {
        return vBody.isRefreshing();
    }

    public void setDivider(int color, int size) {
        vList.setDivider(color, size);
    }

    public void setDefaultDivider() {
        vList.setDefaultDivider();
    }

    public void setDividerDecration(ItemDecoration itemDecoration) {
        vList.setDividerDecration(itemDecoration);
    }

    public void addHeader(View view) {
        HeaderAndFooterWrapper headerAndFooterWrapper;
        mHeaderNum++;
        if (!(mAdapter instanceof HeaderAndFooterWrapper)) {
            headerAndFooterWrapper = new HeaderAndFooterWrapper(mAdapter);
            headerAndFooterWrapper.addHeaderView(view);
            setAdapter(headerAndFooterWrapper);
        } else {
            headerAndFooterWrapper = (HeaderAndFooterWrapper) mAdapter;
            headerAndFooterWrapper.addHeaderView(view);
            notifyDataSetChanged();
        }
    }

    public void addFooter(View view) {
        HeaderAndFooterWrapper headerAndFooterWrapper;
        mFootNum++;
        if (!(mAdapter instanceof HeaderAndFooterWrapper)) {
            headerAndFooterWrapper = new HeaderAndFooterWrapper(mAdapter);
            headerAndFooterWrapper.addFootView(view);
            setAdapter(headerAndFooterWrapper);
        } else {
            headerAndFooterWrapper = (HeaderAndFooterWrapper) mAdapter;
            headerAndFooterWrapper.addFootView(view);
            notifyDataSetChanged();
        }
    }

    public void setRefreshEnable(boolean enable) {
        vBody.setEnabled(enable);
    }

    public void setStackFromEnd(boolean stackFromEnd) {
        vList.setStackFromEnd(stackFromEnd);
    }

    public void notifyDataSetChanged() {
        mAdapter.notifyDataSetChanged();
    }

    public void goToPosition(int position) {
        vList.goToPosition(position);
    }

}
