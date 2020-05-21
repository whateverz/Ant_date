package lib.frame.base;

import lib.frame.adapter.AdapterBaseList;
import lib.frame.module.http.HttpHelper;
import lib.frame.view.recyclerView.WgList;

/**
 * Created by shuaq on 2016/11/1.
 */

public abstract class BaseListFragment<T> extends BaseLazyFragment {

    protected WgList<T> vList;

    protected AdapterBaseList<T> mAdapter;

    protected int mCurPage = 1;

    @Override
    protected void setRootView() {
        super.setRootView();
        vList = createList();
        rootView = vList;
    }

    @Override
    protected void initView() {
        super.initView();
        if (mAdapter == null)
            mAdapter = setAdapter();
        vList.setPage(mCurPage);
        vList.setAdapter(mAdapter);
        initList(vList);
    }

    @Override
    protected void initListener() {
        super.initListener();
        vList.setLoadDataListener(new WgList.OnLoadDataListener() {
            @Override
            public void onLoadData(HttpHelper httpHelper, int page) {
                mCurPage = page;
                loadData(page, httpHelper);
            }
        });
        vList.setHandleDataListener(setOnHandleDataListener());
    }

    protected WgList<T> createList() {
        return new WgList<>(mContext);
    }

    protected abstract AdapterBaseList<T> setAdapter();

    protected abstract void initList(WgList<T> list);

    protected abstract void loadData(int page, HttpHelper httpHelper);

    protected WgList.OnHandleDataListener<T> setOnHandleDataListener() {
        return null;
    }

    protected void lazyLoad() {
        vList.loadData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        vList.removeAllViews();
    }
}
