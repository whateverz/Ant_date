package lib.frame.view.recyclerView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lib.frame.R;
import lib.frame.adapter.AdapterBaseList;
import lib.frame.base.IdConfigBase;
import lib.frame.module.http.HttpHelper;
import lib.frame.module.http.HttpResult;
import lib.frame.utils.UIHelper;
import lib.frame.view.recyclerView.wrapper.EmptyWrapper;
import lib.frame.view.recyclerView.wrapper.LoadmoreWrapper;

/**
 * Created by shuaq on 2016/10/14.
 */
public class WgList<K> extends WgBaseList {

    private int mCurPage = 1;

    private int mPageSize = IdConfigBase.PAGESIZE;

    private List<K> mList;

    private AdapterBaseList<K> mAdapterBase;

    private OnHandleDataListener<K> mHandleDataListener;

    private OnLoadDataListener mLoadDataListener;

    private OnLoadFinishListener mLoadFinishListener;

    private LoadmoreWrapper mLoadMoreAdapter;

    private BlockLoadMoreBase vLoadMore;

    private EmptyWrapper mEmptyWrapper;

    private boolean isRunning = false;

    public WgList(Context context) {
        super(context);
    }

    public WgList(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WgList(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initThis() {
        super.initThis();
        mAsyncHttpHelper = setHttpHelper();
        mAsyncHttpHelper.setOnHttpCallBack(this);
        setRefresh(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
    }

    public HttpHelper setHttpHelper() {
        return getHttpHelper();
    }

    public void setAdapter(AdapterBaseList<K> adapter) {
        super.setAdapter(adapter);
        mAdapterBase = adapter;
        if (mAdapterBase.getList() == null) {
            mList = new ArrayList<>();
            mAdapterBase.setList(mList);
        } else {
            mList = mAdapterBase.getList();
//            mCurPage = mList.size() / mPageSize + (mList.size() % mPageSize == 0 ? 0 : 1);
        }
    }

    public void refresh() {
        if (vLoading.getVisibility() != VISIBLE && mLoadDataListener != null) {
            vBody.setRefreshing(true);
        }
        loadData(1);
    }

    public void refreshNoProgress() {
        loadData(1);
    }

    public void loadDataNoProgress() {
        if ((mList == null || mList.size() == 0)) {
            showLoadView();
            refreshNoProgress();
        }
    }

    public void loadMore() {
        loadData(mCurPage + 1);
    }

    public void loadData() {
        if ((mList == null || mList.size() == 0)) {
            showLoadView();
            refresh();
        }
    }

    public void loadData(int page) {
        if (!isRunning) {
            mCurPage = page;
            isRunning = true;
            if (mLoadDataListener != null)
                mLoadDataListener.onLoadData(mAsyncHttpHelper, page);
            else {
                setRefreshing(false);
            }
            if (vLoadMore != null && vLoadMore.getState() != BlockLoadMore.STATE_LOADING)
                vLoadMore.setLoading();
        }
    }

    @Override
    public <T> void onHttpCallBack(int resultType, int reqId, String resContent, Object reqObject, HttpResult<T> httpResult) {
        super.onHttpCallBack(resultType, reqId, resContent, reqObject, httpResult);
        hideErrorView();
        int type;
        if (reqObject instanceof Integer)
            type = (int) reqObject;
        else
            type = IdConfigBase.REFRESH;
        List<K> tempList = null;

        if (mHandleDataListener != null)
            tempList = mHandleDataListener.dealData(httpResult);
        else if (httpResult != null) {
            tempList = HttpResult.getResults(httpResult);
        }

        if (tempList == null)
            tempList = Collections.emptyList();

        if (mLoadMoreAdapter != null && tempList.size() < mPageSize) {
            vLoadMore.setNoMoreData();
        }
        if (type != IdConfigBase.REFRESH && type != 1) {
            mList.addAll(tempList);
            tempList = mList;
        }
        handleData(tempList);
    }

    @Override
    public <T> void onHttpErrorCallBack(int resultType, int reqId, String resContent, Object reqObject, HttpResult<T> httpResult) {
        super.onHttpErrorCallBack(resultType, reqId, resContent, reqObject, httpResult);
        mCurPage--;
        if (vLoadMore != null)
            vLoadMore.setLoadMoreError();
    }

    @Override
    public <T> void onHttpFinishCallBack(int resultType, int reqId, String resContent, Object reqObject, HttpResult<T> httpResult) {
        super.onHttpFinishCallBack(resultType, reqId, resContent, reqObject, httpResult);
        setRefreshing(false);
        hideLoadView();
        if (mLoadFinishListener != null)
            mLoadFinishListener.onLoadFinish(resultType);

    }

    public void setLoadMore() {
        BlockLoadMore blockLoadMore = new BlockLoadMore(mContext);
        blockLoadMore.setBackgroundResource(R.drawable.selector_setting_item);
        setLoadMore(blockLoadMore);
    }

    public void setLoadMore(BlockLoadMoreBase blockLoadMore) {
        vLoadMore = blockLoadMore;
        mLoadMoreAdapter = new LoadmoreWrapper(vList.getAdapter());
        vLoadMore.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        vLoadMore.setOnClickListener(this);
        mLoadMoreAdapter.setLoadMoreView(vLoadMore);
        mLoadMoreAdapter.setOnLoadMoreListener(new LoadmoreWrapper.OnLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                if (mList.size() != 0 && vLoadMore.getState() == BlockLoadMore.STATE_LOADING)
                    loadMore();
            }
        });
        if (mList.size() > mPageSize)
            setAdapter(mLoadMoreAdapter);
    }

    public void handleData(List<K> list) {
        if (list == null)
            list = Collections.emptyList();
        mList = list;
        mAdapterBase.setLists(mList);
        if (mList.size() == 0 && mEmptyWrapper != null) {
            setAdapter(mEmptyWrapper);
        } else if (mLoadMoreAdapter != null) {
            if (!(vList.getAdapter() instanceof LoadmoreWrapper)) {
                if (mList.size() < mPageSize) {
                    notifyDataSetChanged();
                } else {
                    setAdapter(mLoadMoreAdapter);
                    mFootNum++;
                }
            } else {
                if (mCurPage == 1 && mList.size() < mPageSize) {
                    setAdapter(mLoadMoreAdapter.getInnerAdapter());
                    mFootNum--;
                } else {
                    notifyDataSetChanged();
                }
            }
        } else {
            notifyDataSetChanged();
        }

    }

    public void setHandleDataListener(OnHandleDataListener<K> mListener) {
        this.mHandleDataListener = mListener;
    }

    public void setLoadDataListener(OnLoadDataListener listener) {
        mLoadDataListener = listener;
    }

    public void setLoadFinishListener(OnLoadFinishListener mLoadFinishListener) {
        this.mLoadFinishListener = mLoadFinishListener;
    }

    public interface OnHandleDataListener<T> {
        List<T> dealData(HttpResult httpResult);
    }

    public interface OnLoadDataListener {
        void onLoadData(HttpHelper httpHelper, int page);
    }

    public interface OnLoadFinishListener {
        void onLoadFinish(int resultType);
    }

    @Override
    public void removeAllViews() {
        super.removeAllViews();
        vList.removeAllViews();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (vLoadMore.getState() == BlockLoadMore.STATE_ERROR)
            loadMore();
    }

    public void setPage(int mCurPage) {
        this.mCurPage = mCurPage;
    }

    public void setLoadingView(View view) {
        if (view != null) {
            vLoading.removeAllViews();
            view.setOnClickListener(this);
            vLoading.addView(view);
            if (mList.size() == 0)
                showLoadView();
        }
    }

    public void setLoadingView(int layoutRes) {
        setLoadingView(LayoutInflater.from(mContext).inflate(layoutRes, this, false));
    }

    public void setErrorView(View view) {
        if (view != null) {
            vError.removeAllViews();
            vError.addView(view);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadData();
                }
            });
        }
    }

    public void setErrorView(int layoutRes) {
        setErrorView(LayoutInflater.from(mContext).inflate(layoutRes, this, false));
    }

    public void showErrorView() {
        if (vError.getChildCount() > 0)
            vError.setVisibility(VISIBLE);
    }

    public void hideErrorView() {
        vError.setVisibility(GONE);
    }

    public void showLoadView() {
        if (vLoading.getChildCount() > 0)
            vLoading.setVisibility(VISIBLE);
    }

    public void hideLoadView() {
        vLoading.setVisibility(GONE);
    }

    public List<K> getList() {
        if (mList == null)
            return new ArrayList<>();
        return mList;
    }

    public void clear() {
        mList.clear();
        notifyDataSetChanged();
    }

    public void setEmptyView(View emptyView) {
        UIHelper.setView(emptyView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mEmptyWrapper = new EmptyWrapper(mAdapterBase);
        mEmptyWrapper.setEmptyView(emptyView);
        setAdapter(mEmptyWrapper);
    }

    public void setEmptyView(int layoutRes) {
        setEmptyView(LayoutInflater.from(mContext).inflate(layoutRes, this, false));
    }

    @Override
    public void setRefreshing(boolean refreshing) {
        super.setRefreshing(refreshing);
        isRunning = refreshing;
    }

    public void setPageSize(int mPageSize) {
        this.mPageSize = mPageSize;
    }
}
