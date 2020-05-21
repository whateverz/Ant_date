package lib.frame.view.item;

import android.content.Context;

import androidx.annotation.NonNull;

import android.util.AttributeSet;

import lib.frame.base.BaseFrameFragment;
import lib.frame.base.BaseFrameView;

/**
 * Created by shuaqq on 2017/8/29.
 */

public abstract class ItemBase<T> extends BaseFrameView {

    protected T mInfo;

    protected int mCurPos;

    protected int mTotal;

    public ItemBase(Context context) {
        super(context);
    }

    public ItemBase(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ItemBase(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setInfo(T info, int curPos, int total) {
        this.mInfo = info;
        this.mCurPos = curPos;
        this.mTotal = total;
    }

    public abstract void setInfo(T info);

    public T getInfo() {
        return mInfo;
    }

    public int getCurPos() {
        return mCurPos;
    }

    public int getTotal() {
        return mTotal;
    }

    public ItemBase<T> setCallBack(ItemCallback mCallBack) {
        this.mCallBack = mCallBack;
        return this;
    }

    public interface ItemCallback {
        void callback(int id, int pos, Object... objects);
    }

    @Override
    public ItemBase<T> setFragment(BaseFrameFragment fragment) {
        this.fragment = fragment;
        return this;
    }
}
