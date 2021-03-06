package com.ant.antdate.view.item;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ant.antdate.R;
import com.ant.antdate.base.BaseItem;
import com.ant.antdate.bean.ContentInfo;
import com.ant.antdate.utils.TimeUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

public class ItemContentOne extends BaseItem<ContentInfo> {
    public ItemContentOne(Context context) {
        super(context);
    }

    public ItemContentOne(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ItemContentOne(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getLayout() {
        return R.layout.item_topten_one;
    }

    @Override
    public void setInfo(ContentInfo info) {
        TextView tv_content = findViewById(R.id.tv_content);
        ImageView  iv_avatar = findViewById(R.id.iv_avatar);
        TextView  nikename = findViewById(R.id.tv_nikename);
        TextView  tv_time = findViewById(R.id.tv_time);
        TextView  tv_bankuai = findViewById(R.id.tv_bankuai);
        TextView  tv_on_line = findViewById(R.id.tv_on_line);
        TextView  tv_number_see = findViewById(R.id.tv_number_see);
        ImageView gv_image = findViewById(R.id.gv_image);
        nikename.setText(info.getNickname()+"");
        tv_time.setText(TimeUtils.progressDate(info.getCreated_time()));
        tv_bankuai.setText(info.getTheme_name()+"");
        tv_on_line.setText(info.getOnline()+"人在线");
        tv_number_see.setText(info.getViews()+"人浏览");
        tv_content.setText(info.getTitle());
        Glide.with(this).load(info.getAvatar()).placeholder(R.mipmap.default_photo)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(iv_avatar);
       String imageurl = info.getCovers().get(0).getUrl();
       if(imageurl.isEmpty()){
           gv_image.setVisibility(View.GONE);
       }else {
           Glide.with(this).load(imageurl).placeholder(R.mipmap.default_photo).into(gv_image);
       }

    }
}
