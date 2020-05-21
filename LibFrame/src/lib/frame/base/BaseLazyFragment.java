package lib.frame.base;

/**
 * Created by shuaq on 2016/11/1.
 */

public abstract class BaseLazyFragment extends BaseFrameFragment {


    protected boolean isVisible;
    protected boolean isPrepared;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isLazy()) {
            if (getUserVisibleHint()) {
                isVisible = true;
                onVisible();
            } else {
                isVisible = false;
                onInvisible();
            }
        }
    }

    protected void onVisible() {
        load();
    }

    @Override
    protected void loadData() {
        super.loadData();
        isPrepared = true;
        load();
    }

    protected void load() {
        if (isLazy() && (!isPrepared || !isVisible)) {
            return;
        }
        lazyLoad();
    }

    protected abstract void lazyLoad();

    protected void onInvisible() {
    }

    protected boolean isLazy() {
        return true;
    }
}
