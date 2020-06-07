package com.ant.antdate.view.block;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ant.antdate.R;
import com.ant.antdate.bean.BannerInfo;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;

import java.util.List;

import lib.frame.utils.UIHelper;

public class BlockBanner extends ConvenientBanner<BannerInfo> {
    public BlockBanner(Context context) {
        super(context);
    }

    public BlockBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BlockBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setList(List<BannerInfo> bannerInfos) {
        setPages((CBViewHolderCreator<LocalImageHolderView>) LocalImageHolderView::new, bannerInfos);
    }

    static class LocalImageHolderView implements Holder<BannerInfo> {
        ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setMaxHeight(R.dimen.new_350px);
            imageView.setMaxWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            UIHelper.setView(imageView, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, BannerInfo data) {
            Glide.with(context).load(data.getBanner_image()).placeholder(R.mipmap.default_photo).into(imageView);
           // imageView.setImageResource(R.mipmap.app_icon);
        }
    }

}
