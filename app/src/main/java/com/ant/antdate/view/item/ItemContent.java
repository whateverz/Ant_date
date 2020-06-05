package com.ant.antdate.view.item;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.ant.antdate.R;
import com.ant.antdate.base.BaseItem;
import com.ant.antdate.bean.ContentInfo;
import com.ant.antdate.utils.ImageUtils;
import com.ant.antdate.utils.TimeUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lib.frame.adapter.AdapterBase;
import lib.frame.module.ui.BindView;

public class ItemContent extends BaseItem<ContentInfo> {
    private ArrayList<Map<String,String>>  urls;
    @BindView(R.id.iv_avatar)
    ImageView iv_avatar;
    public ItemContent(Context context) {
        super(context);
    }

    public ItemContent(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ItemContent(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getLayout() {
        return R.layout.item_topten;
    }

    @Override
    public void setInfo(ContentInfo info) {
        urls = new ArrayList<>();
        TextView tv_content = findViewById(R.id.tv_content);
        TextView  nikename = findViewById(R.id.tv_nikename);
        TextView  tv_time = findViewById(R.id.tv_time);
        TextView  tv_bankuai = findViewById(R.id.tv_bankuai);
        TextView  tv_on_line = findViewById(R.id.tv_on_line);
        TextView  tv_number_see = findViewById(R.id.tv_number_see);
        GridView gv_image = findViewById(R.id.gv_image);
        nikename.setText(info.getNickname());
        tv_time.setText(TimeUtils.progressDate(info.getCreated_time()));
        tv_bankuai.setText(info.getTheme_name()+"");
        tv_on_line.setText(info.getOnline()+"人在线");
        tv_number_see.setText(info.getViews()+"人浏览");
        tv_content.setText(info.getTitle());
//        ImageUtils.get().loadCircle(iv_avatar,R.mipmap.icon_camera,R.mipmap.icon_camera, info.getAvatar());
        Glide.with(this).load(info.getAvatar()).placeholder(R.mipmap.default_photo)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(iv_avatar);
        for (int i=0 ;i<info.getCovers().size();i++){
            Map<String,String> map = new HashMap<>();
            map.put("img",info.getCovers().get(i).getUrl());
            urls.add(map);
        }
       FAdapter fAdapter = new FAdapter(getContext());
       fAdapter.setData(info.getCovers());
       gv_image.setAdapter(fAdapter);

    }
    class FAdapter extends BaseAdapter {

        private Context context;
        private ArrayList<ContentInfo.CoversBean>  urls;

        public FAdapter(Context context) {
            this.context = context;
            urls = new ArrayList<>();
        }
        public void setData(List<ContentInfo.CoversBean> resultsBeans) {
            this.urls.clear();
            if (resultsBeans != null){
                this.urls.addAll(resultsBeans);
            }
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return this.urls.size();
        }

        @Override
        public Object getItem(int i) {
            return this.urls.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder vh = null;
            if (view == null){
                vh = new ViewHolder();
                view = View.inflate(context, R.layout.item_picture, null);
                vh.gv_imageleft = (ImageView) view.findViewById(R.id.img);
                view.setTag(vh);
            }else {
                vh = (ViewHolder) view.getTag();
            }
            Glide.with(getContext()).load(urls.get(i).getUrl()).placeholder(R.mipmap.default_photo).into(vh.gv_imageleft);
            return view;
        }

    /*private String https2http(String url) {
       String result = url.replace("https", "http");
        Log.i("TEST", result);
       return result;
    }*/

        class ViewHolder{
            ImageView gv_imageleft;
        }

    }

}
