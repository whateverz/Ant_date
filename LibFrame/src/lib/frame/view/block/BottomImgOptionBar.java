package lib.frame.view.block;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import lib.frame.R;
import lib.frame.utils.UIHelper;


/**
 * Created by lwxkey on 16/3/10.
 */
public class BottomImgOptionBar extends LinearLayout implements ViewTreeObserver.OnGlobalLayoutListener, View.OnClickListener {

    private Context mContext;

    private String[] strsNormal;//正常状态的文字
    private int[] iconsNormal;//正常状态的图标
    private int styleIdNormal;//正常状态的文字样式

    private String[] strsPressed;//点击状态的文字
    private int[] iconsPressed;//点击状态的图标
    private int styleIdPressed;//点击状态的文字样式

    private List<View> items = new ArrayList<>();

    private int itemW, itemH;
    private int startId = 12345;

    private int mPadding = -1;
    private int drawablePadding = 0;
    private int drawableSize = 0;

    private int mMargin = 0;

    private OnBottomOptionBarListener mListener;

    public void setPadding(int padding) {
        mPadding = padding;
    }

    public void setDrawablePadding(int drawablePadding) {
        this.drawablePadding = drawablePadding;
    }

    public void setDrawableSize(int drawableSize) {
        this.drawableSize = drawableSize;
    }

    public void initNormal(int[] strsNormal, int[] iconsNormal, int styleIdNormal) {
        String[] strs = new String[strsNormal.length];
        for (int i = 0; i < strsNormal.length; i++) {
            strs[i] = mContext.getString(strsNormal[i]);
        }
        initNormal(strs, iconsNormal, styleIdNormal);
    }

    public List<View> getItems() {
        return items;
    }

    public void initNormal(String[] strsNormal, int[] iconsNormal, int styleIdNormal) {
        boolean needReload = false;
        if (this.strsNormal == null || this.strsNormal.length != strsNormal.length) {
            needReload = true;
        }
        this.strsNormal = strsNormal;
        this.iconsNormal = iconsNormal;
        this.styleIdNormal = styleIdNormal;
        if (needReload) {
            removeAllViews();
            items.clear();
            for (int i = 0; i < strsNormal.length; i++) {
                View tv;
                if (TextUtils.isEmpty(strsNormal[i])) {
                    tv = new ImageView(mContext);
                } else {
                    tv = new TextView(mContext);
                }
                tv.setId(startId + i);
                tv.setOnClickListener(this);
                if (mPadding < 0) {
                    mPadding = getResources().getDimensionPixelSize(R.dimen.new_12px);
                }
                tv.setPadding(mPadding, mPadding, mPadding, mPadding);
                addView(tv);
                UIHelper.setView(tv, LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
                items.add(tv);
            }
        }
        setPosition(0);
    }

    public void initNormal(List<String> strsNormal, int[] iconsNormal, int styleIdNormal) {
        this.strsNormal = new String[strsNormal.size()];
        for (int i = 0; i < strsNormal.size(); i++) {
            this.strsNormal[i] = strsNormal.get(i);
        }
        initNormal(this.strsNormal, iconsNormal, styleIdNormal);
    }

    public void initPressed(int[] strsPressed, int[] iconsPressed, int styleIdPressed) {
        String[] strs = new String[strsPressed.length];
        for (int i = 0; i < strsPressed.length; i++) {
            strs[i] = mContext.getString(strsPressed[i]);
        }
        initPressed(strs, iconsPressed, styleIdPressed);
    }

    public void initPressed(String[] strsPressed, int[] iconsPressed, int styleIdPressed) {
        this.strsPressed = strsPressed;
        this.iconsPressed = iconsPressed;
        this.styleIdPressed = styleIdPressed;
    }

    public void initPressed(List<String> strsPressed, int[] iconsPressed, int styleIdPressed) {
        this.strsPressed = new String[strsPressed.size()];
        for (int i = 0; i < strsPressed.size(); i++) {
            this.strsPressed[i] = strsPressed.get(i);
        }
        initPressed(this.strsPressed, iconsPressed, styleIdPressed);
    }

    public void initPressed(List<String> strsPressed, List<String> iconsPressed, int styleIdPressed) {

    }

    private void initThis() {
        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);
        getViewTreeObserver().addOnGlobalLayoutListener(this);
        setVisibility(INVISIBLE);
    }

    public BottomImgOptionBar(Context context) {
        super(context);
        mContext = context;
        initThis();
    }

    public BottomImgOptionBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initThis();
    }

    @Override
    public void onGlobalLayout() {
        if (getWidth() == 0 || strsNormal == null || strsNormal.length == 0)
            return;
        itemW = (getWidth() - mMargin * 2) / strsNormal.length;
        itemH = getHeight();
        for (int i = 0; i < items.size(); i++) {
            UIHelper.setView(items.get(i), itemW, LayoutParams.MATCH_PARENT);
        }
        if (items.get(0).getWidth() > 0) {
            setVisibility(VISIBLE);
        }
    }

    public void setPosition(int position) {
        strsPressed = strsPressed == null ? strsNormal : strsPressed;
        iconsPressed = iconsPressed == null ? iconsNormal : iconsPressed;
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i) instanceof ImageView) {
                ImageView iv = (ImageView) items.get(i);
                if (i == position) {
                    iv.setImageResource(iconsPressed[i]);
                } else {
                    iv.setImageResource(iconsNormal[i]);
                }
            } else if (items.get(i) instanceof TextView) {
                TextView tv = (TextView) items.get(i);
                tv.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
                tv.setCompoundDrawablePadding(drawablePadding);
                tv.setIncludeFontPadding(false);
                if (i == position) {
                    tv.setText(strsPressed[i]);
                    if (drawableSize != 0) {
                        Drawable drawable = ContextCompat.getDrawable(mContext, iconsPressed[i]);
                        drawable.setBounds(0, 0, drawableSize, drawableSize);
                        tv.setCompoundDrawables(null, drawable, null, null);
                    } else
                        tv.setCompoundDrawablesWithIntrinsicBounds(0, iconsPressed[i], 0, 0);
                    if (styleIdPressed > 0) {
                        tv.setTextAppearance(mContext, styleIdPressed);
                    }
                } else {
                    tv.setText(strsNormal[i]);
                    if (drawableSize != 0) {
                        Drawable drawable = ContextCompat.getDrawable(mContext, iconsNormal[i]);
                        drawable.setBounds(0, 0, drawableSize, drawableSize);
                        tv.setCompoundDrawables(null, drawable, null, null);
                    } else
                        tv.setCompoundDrawablesWithIntrinsicBounds(0, iconsNormal[i], 0, 0);
                    if (styleIdPressed > 0) {
                        tv.setTextAppearance(mContext, styleIdNormal);
                    }
                }
            }
        }
    }

    public void setMargin(int margin) {
        mMargin = margin;
    }

    @Override
    public void onClick(View v) {
        int position = v.getId() - startId;
        setPosition(position);
        if (mListener != null) {
            mListener.onPositionClick(position);
        }
    }

    public interface OnBottomOptionBarListener {
        void onPositionClick(int position);
    }

    public void setOnBottomOptionBarListener(OnBottomOptionBarListener l) {
        mListener = l;
    }

}
