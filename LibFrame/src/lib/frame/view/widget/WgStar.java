package lib.frame.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import lib.frame.R;
import lib.frame.utils.UIHelper;

/**
 * Created by lwxkey on 16/6/13.
 */
public class WgStar extends LinearLayout implements View.OnClickListener, ViewTreeObserver.OnGlobalLayoutListener {

    private Context mContext;

    private int normalResId = R.mipmap.star_normal;
    private int selectedResId = R.mipmap.star_selected;
    private int selectedHalf = R.mipmap.star_half;

    private int num = 0;//设置多少星

    private int startId = 3455;
    private int size = 0;
    private int padding = 0;

    private List<ImageButton> stars = new ArrayList<>();
    private int position = 0;
    private float score = 0;

    private boolean clickable = true;

    public int getProsition() {
        return position;
    }

    @Override
    public void setClickable(boolean clickable) {
        this.clickable = clickable;
    }

    public void setNum(int num) {
        this.num = num;
        stars.clear();
        invalidate();
    }

    public void setSize(int size, int padding) {
        this.size = size;
        this.padding = padding;
        initView();
    }

    private void initView() {
        removeAllViews();
        stars.clear();
        for (int i = 0; i < num; i++) {
            ImageButton iBtn = new ImageButton(mContext);
            iBtn.setId(startId + i);
            iBtn.setOnClickListener(this);
            iBtn.setBackgroundColor(0);
            iBtn.setPadding(padding > 0 ? padding : mContext.getResources().getDimensionPixelSize(R.dimen.new_20px), 0,
                    padding > 0 ? padding : mContext.getResources().getDimensionPixelSize(R.dimen.new_20px), 0);
            addView(iBtn);
            if (size > 0) {
                UIHelper.setView(iBtn, size, size);
            }
            stars.add(iBtn);
        }
        setStar(position);
    }

    private void initThis() {
        setOrientation(LinearLayout.HORIZONTAL);
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    public WgStar(Context context) {
        super(context);
        mContext = context;
        initThis();
    }

    public WgStar(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initThis();
    }

    public WgStar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initThis();
    }

    public void setStar(int position) {
        this.position = position;
        int size = position <= stars.size() ? position : stars.size();
        for (int i = 0; i < num; i++) {
            stars.get(i).setBackgroundResource(i <= position ? selectedResId : normalResId);
        }
    }

    public void setStar(float score) {
        this.score = score;
        int a = (int) score * 10 / 10;
        int b = (int) (score * 10) % 10;
        for (int i = 0; i < stars.size(); i++) {
            if (i < a) {
                stars.get(i).setBackgroundResource(selectedResId);
            } else if (b > 0) {
                if (i == a) {
                    stars.get(i).setBackgroundResource(selectedHalf);
                } else {
                    stars.get(i).setBackgroundResource(normalResId);
                }
            }
        }
    }


    @Override
    public void onClick(View v) {
        if (clickable) {
            int positon = v.getId() - startId;
            setStar(positon);
        }
    }

    @Override
    public void onGlobalLayout() {
        if (stars.size() == 0) {
            initView();
        }
    }
}
