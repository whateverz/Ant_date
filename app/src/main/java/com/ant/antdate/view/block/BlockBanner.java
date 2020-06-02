package com.ant.antdate.view.block;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.ant.antdate.R;
import com.ant.antdate.bean.BannerInfo;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;

import java.util.List;

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
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, BannerInfo data) {
            imageView.setImageResource(R.mipmap.app_icon);
        }
    }

}
