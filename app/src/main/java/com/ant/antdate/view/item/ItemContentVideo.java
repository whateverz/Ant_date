package com.ant.antdate.view.item;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ant.antdate.R;
import com.ant.antdate.activity.ViedeoActivity;
import com.ant.antdate.base.BaseItem;
import com.ant.antdate.bean.ContentInfo;
import com.ant.antdate.mine.ChangeMinePassActivity;
import com.ant.antdate.utils.TimeUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

public class ItemContentVideo extends BaseItem<ContentInfo> {
    public ItemContentVideo(Context context) {
        super(context);
    }

    public ItemContentVideo(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ItemContentVideo(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getLayout() {
        return R.layout.item_topten_video;
    }

    @Override
    public void setInfo(ContentInfo info) {

        TextView tv_content = findViewById(R.id.tv_content);
        ImageView  iv_avatar = findViewById(R.id.iv_avatar);
        ImageView  iv_photo = findViewById(R.id.iv_photo);

        TextView  nikename = findViewById(R.id.tv_nikename);
        TextView  tv_time = findViewById(R.id.tv_time);
        TextView  tv_bankuai = findViewById(R.id.tv_bankuai);
        TextView  tv_on_line = findViewById(R.id.tv_on_line);
        TextView  tv_number_see = findViewById(R.id.tv_number_see);
        nikename.setText(info.getNickname());
        tv_time.setText(TimeUtils.progressDate(info.getCreated_time()));
        tv_bankuai.setText(info.getTopic_type()+"");
        tv_on_line.setText(info.getOnline()+"人在线");
        tv_number_see.setText(info.getViews()+"人浏览");
        tv_content.setText(info.getTitle());
        String imageurl = info.getCovers().get(0).getUrl();
        Glide.with(this).load(imageurl).placeholder(R.mipmap.default_photo).into(iv_photo);
        iv_photo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                goToActivity(ViedeoActivity.class,"imageurl",imageurl);

            }
        });
        Glide.with(this).load(info.getAvatar()).placeholder(R.mipmap.default_photo)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(iv_avatar);
        if(info.getCovers().get(0).getType()==1){
           // setLayo
        }

    }
}
