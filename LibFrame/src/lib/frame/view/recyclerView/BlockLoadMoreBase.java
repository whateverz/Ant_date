package lib.frame.view.recyclerView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import lib.frame.R;
import lib.frame.base.BaseFrameView;

/**
 * Created by shuaq on 2016/10/29.
 */

public abstract class BlockLoadMoreBase extends BaseFrameView {

    public static final int STATE_LOADING = 0;
    public static final int STATE_NODATA = 1;
    public static final int STATE_ERROR = 2;

    private int mState = 0;

    public BlockLoadMoreBase(Context context) {
        super(context);
    }

    public BlockLoadMoreBase(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BlockLoadMoreBase(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void setLoading() {
        mState = STATE_LOADING;
    }

    public void setNoMoreData() {
        mState = STATE_NODATA;
    }

    public void setLoadMoreError() {
        mState = STATE_ERROR;
    }

    public int getState() {
        return mState;
    }
}
