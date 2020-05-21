package lib.frame.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import androidx.annotation.DrawableRes;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import lib.frame.R;


public class WgActionBarBase extends LinearLayout implements OnClickListener {

    protected LinearLayout vLeft;
    protected ImageView vLeftIcon;
    protected TextView vLeftText;

    protected LinearLayout vRight;
    protected ImageView vRightIcon;
    protected TextView vRightText;

    protected TextView vTitle;

    protected Context mContext;

    private String mTitle;
    private String mTitleRight;
    private String mTitleLeft;

    private int imgLeftId = 0;
    private int imgRightId = 0;

    private OnWgActionBarBaseListener mListener;

    public static final int TYPE_LEFT = 1;
    public static final int TYPE_MIDLE = 2;
    public static final int TYPE_RIGHT = 3;

    public TextView getvLeftText() {
        return vLeftText;
    }

    public LinearLayout getvRight() {
        return vRight;
    }

    public TextView getvRightText() {
        return vRightText;
    }

    public ImageView getvRightIcon() {
        return vRightIcon;
    }

    public TextView getvTitle() {
        return vTitle;
    }

    public void setTitle(String title) {
        vTitle.setText(title);
        vTitle.setCursorVisible(false);
    }

    public void setBarLeft(int iconId, String text) {
        if (iconId > 0) {
            vLeftIcon.setVisibility(VISIBLE);
            vLeftIcon.setImageResource(iconId);
        } else {
            vLeftIcon.setVisibility(GONE);
        }
        if (!TextUtils.isEmpty(text)) {
            vLeftText.setVisibility(VISIBLE);
            vLeftText.setText(text);
        } else {
            vLeftText.setVisibility(GONE);
        }
    }

    public void setBarLeft(Drawable icon, String text) {
        if (icon != null) {
            vLeftIcon.setVisibility(VISIBLE);
            vLeftIcon.setImageDrawable(icon);
        } else {
            vLeftIcon.setVisibility(GONE);
        }
        if (!TextUtils.isEmpty(text)) {
            vLeftText.setVisibility(VISIBLE);
            vLeftText.setText(text);
        } else {
            vLeftText.setVisibility(GONE);
        }
    }

    public void setBarRight(int iconId, String text) {
        if (iconId > 0) {
            vRightIcon.setVisibility(VISIBLE);
            vRightIcon.setImageResource(iconId);
        } else {
            vRightIcon.setVisibility(GONE);
        }
        if (!TextUtils.isEmpty(text)) {
            vRightText.setVisibility(VISIBLE);
            vRightText.setText(text);
        } else {
            vRightText.setVisibility(GONE);
        }
    }

    public void setBarRight(Drawable icon, String text) {
        if (icon != null) {
            vRightIcon.setVisibility(VISIBLE);
            vRightIcon.setImageDrawable(icon);
        } else {
            vRightIcon.setVisibility(GONE);
        }
        if (!TextUtils.isEmpty(text)) {
            vRightText.setVisibility(VISIBLE);
            vRightText.setText(text);
        } else {
            vRightText.setVisibility(GONE);
        }
    }

    public void setTitleLeft(String mTitleLeft) {
        setBarLeft(imgLeftId, mTitleLeft);
    }

    public void setTitleRight(String mTitleRight) {
        setBarRight(imgRightId, mTitleRight);
    }

    public void setImgLeft(@DrawableRes int imgLeftId) {
        setBarRight(imgLeftId, mTitleLeft);
    }

    public void setImgRight(@DrawableRes int imgRightId) {
        setBarRight(imgRightId, mTitleRight);
    }

    public void setTextColor(int color) {
        vTitle.setTextColor(color);
        vRightText.setTextColor(color);
        vLeftText.setTextColor(color);
    }

    public View getRightBar() {
        return vRight;
    }

    public View getLeftBar() {
        return vLeft;
    }

    protected void initThis(AttributeSet attrs) {
        LayoutInflater.from(mContext).inflate(R.layout.wg_action_bar_base, this);

        vLeft = (LinearLayout) findViewById(R.id.wg_action_bar_left);
        vLeftIcon = (ImageView) findViewById(R.id.wg_action_bar_left_icon);
        vLeftText = (TextView) findViewById(R.id.wg_action_bar_left_text);

        vRight = (LinearLayout) findViewById(R.id.wg_action_bar_right);
        vRightIcon = (ImageView) findViewById(R.id.wg_action_bar_right_icon);
        vRightText = (TextView) findViewById(R.id.wg_action_bar_right_text);

        vLeftIcon.setVisibility(GONE);
        vLeftText.setVisibility(GONE);

        vRightIcon.setVisibility(GONE);
        vRightText.setVisibility(GONE);

        vTitle = (TextView) findViewById(R.id.wg_action_bar_title);

        vLeft.setOnClickListener(this);
        vTitle.setOnClickListener(this);
        vRight.setOnClickListener(this);
        initView(attrs);

    }

    private void initView(AttributeSet attrs) {
        TypedArray ta = mContext.obtainStyledAttributes(attrs, R.styleable.WgActionBarBase);
        int count = ta.getIndexCount();
        for (int i = 0; i < count; i++) {
            int itemId = ta.getIndex(i);
            if (itemId == R.styleable.WgActionBarBase_actionTitle) {
                mTitle = ta.getString(itemId);
            } else if (itemId == R.styleable.WgActionBarBase_actionTitleLeft) {
                mTitleLeft = ta.getString(itemId);
            } else if (itemId == R.styleable.WgActionBarBase_actionTitleRight) {
                mTitleRight = ta.getString(itemId);
            } else if (itemId == R.styleable.WgActionBarBase_actionImgLeft) {
                imgLeftId = ta.getResourceId(itemId, 0);
            } else if (itemId == R.styleable.WgActionBarBase_actionImgRight) {
                imgRightId = ta.getResourceId(itemId, 0);
            }
        }
        ta.recycle();
        setTitle(mTitle);
        setBarLeft(imgLeftId, mTitleLeft);
        setBarRight(imgRightId, mTitleRight);
    }

    public WgActionBarBase(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        initThis(attrs);
    }

    public WgActionBarBase(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initThis(attrs);
    }

    public WgActionBarBase(Context context) {
        super(context);
        mContext = context;
        initThis(null);
    }

    @Override
    public void onClick(View arg0) {
        if (mListener != null) {
            if (arg0 == vLeft) {
                mListener.onActionBarClick(TYPE_LEFT);
            } else if (arg0 == vTitle) {
                mListener.onActionBarClick(TYPE_MIDLE);
            } else if (arg0 == vRight) {
                mListener.onActionBarClick(TYPE_RIGHT);
            }
        }
    }

    public interface OnWgActionBarBaseListener {
        void onActionBarClick(int index);
    }

    public void setOnWgActionBarBaseListener(OnWgActionBarBaseListener l) {
        mListener = l;
    }

}
