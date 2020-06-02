package com.ant.antdate.fragment.home;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.ant.antdate.R;
import com.ant.antdate.base.BaseListFragment;
import com.ant.antdate.bean.BannerInfo;
import com.ant.antdate.bean.CommonListInfo;
import com.ant.antdate.bean.ContentInfo;
import com.ant.antdate.logic.LogicRequest;
import com.ant.antdate.view.block.BlockBanner;
import com.ant.antdate.view.item.ItemContent;

import java.util.ArrayList;
import java.util.List;

import lib.frame.adapter.AdapterBaseList;
import lib.frame.adapter.WgAdapter;
import lib.frame.module.http.HttpHelper;
import lib.frame.module.http.HttpResult;
import lib.frame.utils.UIHelper;
import lib.frame.view.item.ItemBase;
import lib.frame.view.recyclerView.WgList;

public class HotFragment extends BaseListFragment<ContentInfo> {
    BlockBanner vBanner;
    @Override
    protected AdapterBaseList<ContentInfo> setAdapter() {
        return new WgAdapter<ContentInfo>(mContext) {
            @Override
            protected ItemBase<ContentInfo> createItem(@NonNull Context context) {
                return new ItemContent(context);
            }

        };
    }

    @Override
    protected void initList(WgList<ContentInfo> list) {
        vBanner = new BlockBanner(mContext);
        vBanner.setPadding(getResources().getDimensionPixelOffset(R.dimen.new_54px),getResources().getDimensionPixelOffset(R.dimen.new_36px),getResources().getDimensionPixelOffset(R.dimen.new_54px),getResources().getDimensionPixelOffset(R.dimen.new_36px));
        UIHelper.setView(vBanner, ViewGroup.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelOffset(R.dimen.new_360px));
        List<BannerInfo> infos = new ArrayList<>();
        infos.add(new BannerInfo());
        vBanner.setList(infos);
        vList.addHeader(vBanner);
    }


    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected WgList.OnHandleDataListener<ContentInfo> setOnHandleDataListener() {
        return httpResult -> {
            CommonListInfo<ContentInfo> listInfo = HttpResult.getResults(httpResult);
            return listInfo.getList();
        };
    }

    @Override
    protected void loadData(int page, HttpHelper httpHelper) {
        LogicRequest.Hotlist(1, httpHelper);
    }
}
