package com.ant.antdate.view.item;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.ant.antdate.R;
import com.ant.antdate.base.BaseItem;
import com.ant.antdate.bean.ContentInfo;
import com.ant.antdate.utils.TimeUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lib.frame.module.ui.BindView;

import static com.ant.antdate.R.mipmap.default_photo;

public class ItemImage extends BaseItem<String> {

    public ItemImage(Context context) {
        super(context);
    }

    public ItemImage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ItemImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getLayout() {
        return R.layout.item_topic_img;
    }

    @Override
    public void setInfo(String info) {
        ImageView  Img = findViewById(R.id.ivDisPlayItemPhoto);
        Img.setImageDrawable(getResources().getDrawable(default_photo));
    }

}
