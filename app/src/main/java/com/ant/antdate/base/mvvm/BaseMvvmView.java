package com.ant.antdate.base.mvvm;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;
import androidx.databinding.PropertyChangeRegistry;
import androidx.databinding.ViewDataBinding;

import com.ant.antdate.base.BaseView;


/**
 * Created by shuaqq on 2017/9/10.
 */

public class BaseMvvmView<T extends ViewDataBinding> extends BaseView implements Observable {

    private transient PropertyChangeRegistry mCallbacks;

    protected T mBinding;

    public BaseMvvmView(Context context) {
        super(context);
    }

    public BaseMvvmView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseMvvmView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected View getLayoutView() {
        if (isInEditMode())
            return super.getLayoutView();
        else {
            mBinding = DataBindingUtil.bind(super.getLayoutView());
            return mBinding.getRoot();
        }
    }

    @Override
    public void addOnPropertyChangedCallback(Observable.OnPropertyChangedCallback callback) {
        synchronized (this) {
            if (mCallbacks == null) {
                mCallbacks = new PropertyChangeRegistry();
            }
        }
        mCallbacks.add(callback);
    }

    @Override
    public void removeOnPropertyChangedCallback(Observable.OnPropertyChangedCallback callback) {
        synchronized (this) {
            if (mCallbacks == null) {
                return;
            }
        }
        mCallbacks.remove(callback);
    }

    /**
     * Notifies listeners that all properties of this instance have changed.
     */
    public void notifyChange() {
        synchronized (this) {
            if (mCallbacks == null) {
                return;
            }
        }
        mCallbacks.notifyCallbacks(this, 0, null);
    }

    /**
     * Notifies listeners that a specific property has changed. The getter for the property
     * that changes should be marked with {@link Bindable} to generate a field in
     * <code>BR</code> to be used as <code>fieldId</code>.
     *
     * @param fieldId The generated BR id for the Bindable field.
     */
    public void notifyPropertyChanged(int fieldId) {
        synchronized (this) {
            if (mCallbacks == null) {
                return;
            }
        }
        mCallbacks.notifyCallbacks(this, fieldId, null);
    }
}
