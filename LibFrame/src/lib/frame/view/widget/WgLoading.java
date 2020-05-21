package lib.frame.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import lib.frame.R;
import lib.frame.base.BaseFrameView;
import lib.frame.module.ui.BindView;

/**
 * Created by lwxkey on 16/5/13.
 */
public class WgLoading extends BaseFrameView {

    private TextView vLoading;

    private String mLoadingText;

    public WgLoading(Context context) {
        super(context);
    }

    public WgLoading(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WgLoading(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getLayout() {
        return R.layout.wg_loading;
    }

    @Override
    protected void dealAttr(AttributeSet attrs) {
        super.dealAttr(attrs);
        TypedArray ta = mContext.obtainStyledAttributes(attrs,
                R.styleable.WgLoading);
        mLoadingText = ta.getString(R.styleable.WgLoading_wgloading_text);
        ta.recycle();
    }

    protected void initThis() {
        vLoading = $(R.id.wg_loading_text);
        if (!TextUtils.isEmpty(mLoadingText))
            vLoading.setText(mLoadingText);
    }
}
