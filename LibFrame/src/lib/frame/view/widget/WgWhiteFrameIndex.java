package lib.frame.view.widget;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import lib.frame.utils.UIHelper;

/**
 * Created by lwxkey on 2017/7/17.
 */

public class WgWhiteFrameIndex extends LinearLayout {

    private Context mContext;

    private int itemCount;
    private int curPosition;
    private List<ItemIndex> views = new ArrayList<>();

    private int normalStyle;
    private int selectedStyle;

    public void setData(int itemCount, int curPosition, int itemSize, int itemMargin,
                        int normalStyle, int selectedStyle) {
        this.itemCount = itemCount;
        this.curPosition = curPosition;
        this.normalStyle = normalStyle;
        this.selectedStyle = selectedStyle;
        removeAllViews();
        views.clear();
        LayoutParams lp = new LayoutParams(itemSize, itemSize);
        lp.leftMargin = itemMargin;
        lp.rightMargin = itemMargin;
        for (int i = 0; i < itemCount; i++) {
            ItemIndex item = new ItemIndex(mContext);
            item.setLayoutParams(lp);
            item.setView(normalStyle, i == curPosition ? selectedStyle : 0);
            addView(item);
            views.add(item);
        }
    }

    public void setPosition(int position) {
        curPosition = position;
        for (int i = 0; i < itemCount; i++) {
            ItemIndex item = views.get(i);
            item.setView(normalStyle, i == position ? selectedStyle : 0);
        }
    }

    private void initThis() {
        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER);
    }

    public WgWhiteFrameIndex(Context context) {
        super(context);
        mContext = context;
        initThis();
    }

    public WgWhiteFrameIndex(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initThis();
    }

    public WgWhiteFrameIndex(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initThis();
    }


    private class ItemIndex extends LinearLayout {

        private int broder;

        private View vInside;

        private int size = 0;

        public void setView(int outsideStyle, int insideStyle) {
            setBackgroundResource(outsideStyle);
            if (insideStyle > 0) {
                vInside.setBackgroundResource(insideStyle);
            } else {
                vInside.setBackgroundColor(0);
            }
        }

        public ItemIndex(@NonNull Context context) {
            super(context);
            vInside = new View(mContext);
            addView(vInside);
            getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    size = getWidth();
                    if (size > 0) {
                        UIHelper.setView(vInside, size - mContext.getResources().getDimensionPixelSize(lib.frame.R.dimen.new_8px),
                                size - mContext.getResources().getDimensionPixelSize(lib.frame.R.dimen.new_8px));
                        setGravity(Gravity.CENTER);
                    }
                }
            });
        }

    }

}
