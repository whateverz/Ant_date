package lib.frame.view.widget;

import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lwxkey on 2017/7/17.
 */

public class WgIndex extends LinearLayout {

    private Context mContext;

    private int itemCount;
    private int curPosition;
    private List<ImageView> views = new ArrayList<>();

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
            ImageView item = new ImageView(mContext);
            item.setLayoutParams(lp);
            item.setBackgroundResource(i == curPosition ? selectedStyle : normalStyle);
            addView(item);
            views.add(item);
        }
    }

    public void setPosition(int position) {
        curPosition = position;
        for (int i = 0; i < itemCount; i++) {
            ImageView item = views.get(i);
            item.setBackgroundResource(i == curPosition ? selectedStyle : normalStyle);
        }
    }

    private void initThis() {
        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER);
    }

    public WgIndex(Context context) {
        super(context);
        mContext = context;
        initThis();
    }

    public WgIndex(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initThis();
    }

    public WgIndex(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initThis();
    }

}
