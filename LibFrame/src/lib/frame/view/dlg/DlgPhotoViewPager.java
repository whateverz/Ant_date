package lib.frame.view.dlg;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.text.TextUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.github.chrisbanes.photoview.OnPhotoTapListener;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.List;

import lib.frame.R;
import lib.frame.utils.UIHelper;
import lib.frame.view.widget.WgActionBarBase;

/**
 * Created by shuaq on 2016/8/23.
 */

public class DlgPhotoViewPager extends Dialog {

    protected Context mContext;

    private ViewPager vPager;
    private PhotoView vImg;
    private PagerAdapter mAdapter;

    protected WgActionBarBase vActionBar;

    private List<String> mList;

    private DrawableTransitionOptions mDrawableDrawableResource;
    private RequestOptions mRequestOptions;

    //    private Animation fadin;
//    private Animation fadout;
    public DlgPhotoViewPager(@NonNull Context context) {
        super(context, R.style.AppFullTheme);
        mContext = context;
    }

    public DlgPhotoViewPager(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        mContext = context;
    }

    protected DlgPhotoViewPager(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initThis();
    }

    private void initThis() {
        Window window = getWindow();
        if (window == null)
            return;
        mRequestOptions = new RequestOptions().fitCenter();
        mDrawableDrawableResource = new DrawableTransitionOptions().crossFade();
        window.setBackgroundDrawableResource(R.color.transparent);
        window.setWindowAnimations(R.style.DlgMenu);
        WindowManager m = window.getWindowManager();
        Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
        WindowManager.LayoutParams p = getWindow().getAttributes(); //获取对话框当前的参数值
        Point point = new Point();
        d.getSize(point);
        p.height = point.y; //高度设置为屏幕的0.3
        p.width = point.x; //宽度设置为屏幕的0.5
        getWindow().setAttributes(p); //设置生效
        View layout = LayoutInflater.from(mContext).inflate(R.layout.wg_photoview_pager, null);
        setContentView(layout);
        setCanceledOnTouchOutside(true);
        vImg = (PhotoView) findViewById(R.id.dlg_photoview_img);
        vPager = (ViewPager) findViewById(R.id.wg_photoview_pager);
        vActionBar = (WgActionBarBase) findViewById(R.id.dlg_photoview_actionbar);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            UIHelper.setView(findViewById(R.id.dlg_photoview_space), FrameLayout.LayoutParams.WRAP_CONTENT, UIHelper.statusBarH);
        }
        vActionBar.setOnWgActionBarBaseListener(new WgActionBarBase.OnWgActionBarBaseListener() {
            @Override
            public void onActionBarClick(int index) {
                switch (index) {
                    case WgActionBarBase.TYPE_LEFT:
                        dismiss();
                        break;
                    case WgActionBarBase.TYPE_RIGHT:
                        break;
                }
            }
        });
        vPager.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mAdapter = new PhotoViewPager();
        vPager.setAdapter(mAdapter);
        setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                if (vPager.getVisibility() == View.VISIBLE)
                    vPager.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.popup_dl_in));
                else
                    vImg.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.popup_dl_in));
            }
        });
    }


    private class PhotoViewPager extends PagerAdapter {
        @Override
        public int getCount() {
            return mList != null ? mList.size() : 0;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            PhotoView view = new PhotoView(mContext);
            view.setScaleType(ImageView.ScaleType.FIT_CENTER);
            if (TextUtils.isEmpty(mList.get(position)))
                Glide.with(mContext).load(R.mipmap.ic_launcher).apply(mRequestOptions).transition(mDrawableDrawableResource).thumbnail(0.2f).into(view);
            else
                Glide.with(mContext).load(mList.get(position)).apply(mRequestOptions).transition(mDrawableDrawableResource).thumbnail(0.2f).into(view);
//            view.setImageResource(imgsId[position]);
//            view.animaFrom();
            view.setOnPhotoTapListener(new OnPhotoTapListener() {
                @Override
                public void onPhotoTap(ImageView view, float x, float y) {
                    dismiss();

                }
            });
            container.addView(view, ViewPager.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.MATCH_PARENT);
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }

    public void show(String url) {
        super.show();
        vImg.setSystemUiVisibility(View.INVISIBLE);
        vPager.setVisibility(View.GONE);
        vImg.setVisibility(View.VISIBLE);
        if (TextUtils.isEmpty(url))
            Glide.with(mContext).load(R.mipmap.ic_launcher).apply(mRequestOptions).transition(mDrawableDrawableResource).thumbnail(0.2f).into(vImg);
        else
            Glide.with(mContext).load(url).apply(mRequestOptions).transition(mDrawableDrawableResource).thumbnail(0.2f).into(vImg);
        vImg.setOnPhotoTapListener(new OnPhotoTapListener() {
            @Override
            public void onPhotoTap(ImageView view, float x, float y) {
                dismiss();

            }
        });
    }

    public void show(List<String> urls, int position) {
        super.show();
        mList = urls;
        vPager.setSystemUiVisibility(View.INVISIBLE);
        vPager.setVisibility(View.VISIBLE);
        vImg.setVisibility(View.GONE);
        mAdapter.notifyDataSetChanged();
        vPager.setCurrentItem(position, false);
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    //    @Override
//    public void dismiss() {
//        vImg.animaTo(info, new Runnable() {
//            @Override
//            public void run() {
//                DlgPhotoViewPager.super.dismiss();
//            }
//        });
//        vBg.setBackgroundResource(R.color.transparent);
//    }

//    private void initAnimate() {
//        fadin = AnimationUtils.loadAnimation(mContext, R.anim.fade_in);
//        fadout = AnimationUtils.loadAnimation(mContext, R.anim.fade_out);
//    }
}
