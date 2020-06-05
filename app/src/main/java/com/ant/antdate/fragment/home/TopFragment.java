package com.ant.antdate.fragment.home;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.ant.antdate.R;
import com.ant.antdate.base.BaseListFragment;
import com.ant.antdate.bean.BannerInfo;
import com.ant.antdate.bean.ContentInfo;
import com.ant.antdate.logic.LogicRequest;
import com.ant.antdate.view.block.BlockBanner;
import com.ant.antdate.view.item.ItemContent;
import com.ant.antdate.view.item.ItemContentAtticle;
import com.ant.antdate.view.item.ItemContentOne;
import com.ant.antdate.view.item.ItemContentVideo;

import java.util.ArrayList;
import java.util.List;

import lib.frame.adapter.AdapterBaseList;
import lib.frame.adapter.WgAdapter;
import lib.frame.adapter.WgMutiAdapter;
import lib.frame.module.http.HttpHelper;
import lib.frame.utils.UIHelper;
import lib.frame.view.item.ItemBase;
import lib.frame.view.recyclerView.WgList;

public class TopFragment extends BaseListFragment<ContentInfo> {



    @Override
    protected AdapterBaseList<ContentInfo> setAdapter() {
        return new WgMutiAdapter<ContentInfo>(mContext) {


            @Override
            protected ItemBase<ContentInfo> createItem(Context context, int i) {
                ItemBase base = null;
                switch (i){
                    case 1:
                        base = new ItemContentVideo(context);
                        break;
                    case 2:
                        base = new ItemContentOne(context);
                        break;
                    case 3://大于一张
                        base = new ItemContent(context);
                        break;
                    case 0:
                        base = new ItemContentAtticle(context);
                        break;
                }
                return base;
            }

            @Override
            public int getItemViewType(int i) {
                ContentInfo  contentInfo = new ContentInfo();
                if (mList.get(i).getCovers().size()>0){
                    if (mList.get(i).getCovers().get(0).getType()==1){
                        return 1;//视频
                    }else if (mList.get(i).getCovers().get(0).getType()==2){
                        if (mList.get(i).getCovers().size()>1){
                            return 3;//大于一张图片
                        }else {
                            return 2;//一张图片
                        }

                    }
                    return 0;//文章
                }
                return 5;//文章
            }

        };
    }

    @Override
    protected void initList(WgList<ContentInfo> wgList) {

    }


//    @Override
//    protected WgList.OnHandleDataListener<ContentInfo> setOnHandleDataListener() {
//        return httpResult -> {
//            CommonListInfo<ContentInfo> listInfo = HttpResult.getResults(httpResult);
//            return listInfo.getList();
//        };
//    }


    @Override
    protected void loadData(int page, HttpHelper httpHelper) {
        LogicRequest.Toplist(1, httpHelper);
    }
}
