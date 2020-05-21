package lib.frame.view.widget;

import com.google.android.material.appbar.AppBarLayout;

/**
 * Created by shuaqq on 2017/1/16.
 */

public abstract class AppBarStateChangeListener implements AppBarLayout.OnOffsetChangedListener {

    protected final static int EXPANDED = 1;
    protected final static int COLLAPSED = 2;
    protected final static int IDLE = 3;

    private int mCurrentState = IDLE;

    @Override
    public final void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        if (i == 0) {
            if (mCurrentState != EXPANDED) {
                onStateChanged(appBarLayout, EXPANDED);
            }
            mCurrentState = EXPANDED;
        } else if (Math.abs(i) >= appBarLayout.getTotalScrollRange()) {
            if (mCurrentState != COLLAPSED) {
                onStateChanged(appBarLayout, COLLAPSED);
            }
            mCurrentState = COLLAPSED;
        } else {
            if (mCurrentState != IDLE) {
                onStateChanged(appBarLayout, IDLE);
            }
            mCurrentState = IDLE;
        }
    }

    public abstract void onStateChanged(AppBarLayout appBarLayout, int state);
}
