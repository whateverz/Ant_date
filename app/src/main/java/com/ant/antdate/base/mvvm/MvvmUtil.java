package com.ant.antdate.base.mvvm;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;
import androidx.databinding.InverseBindingListener;

import lib.frame.logic.LogicImgShow;
import lib.frame.view.widget.WgImageRatingView;
import lib.frame.view.widget.WgShapeImageView;

/**
 * Created by shuaqq on 2017/9/10.
 */

public class MvvmUtil {

    @BindingAdapter({"image"})
    public static void imageLoader(ImageView imageView, String url) {
        if (!TextUtils.isEmpty(url))
            LogicImgShow.getInstance(imageView.getContext()).showImage(url, imageView);
        else
            imageView.setImageResource(0);
    }

    @BindingAdapter({"image"})
    public static void imageLoader(WgShapeImageView imageView, String url) {
        if (!TextUtils.isEmpty(url))
            imageView.setUrl(url);
        else
            imageView.setImageResource(0);
    }

    @BindingAdapter({"background"})
    public static void background(View view, int res) {
        view.setBackgroundResource(res);
    }

    @InverseBindingAdapter(attribute = "rating", event = "ratingAttrChanged")
    public static float getRating(WgImageRatingView view) {
        return view.getRating();
    }

    @BindingAdapter("ratingAttrChanged")
    public static void setOnRefreshListener(final WgImageRatingView view, final InverseBindingListener refreshingAttrChanged) {

        if (refreshingAttrChanged == null) {
            view.setOnRatingChangedListener(null);
        } else {
            view.setOnRatingChangedListener(rating -> refreshingAttrChanged.onChange());
        }
    }


}
