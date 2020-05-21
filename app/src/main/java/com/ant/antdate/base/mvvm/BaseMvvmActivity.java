package com.ant.antdate.base.mvvm;


import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;
import androidx.databinding.PropertyChangeRegistry;
import androidx.databinding.ViewDataBinding;

import com.ant.antdate.base.BaseActivity;


/**
 * Created by shuaqq on 2017/9/10.
 */

public abstract class BaseMvvmActivity<T extends ViewDataBinding> extends BaseActivity implements Observable {

    private transient PropertyChangeRegistry mCallbacks;

    protected T mBinding;

    @Override
    protected void attchView() {
        if (rootViewId > 0) {
            mBinding = DataBindingUtil.setContentView(this, rootViewId);
        }
        if (rootView != null) {
            mBinding = DataBindingUtil.bind(rootView);
        }
    }

    @Override
    public void addOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        synchronized (this) {
            if (mCallbacks == null) {
                mCallbacks = new PropertyChangeRegistry();
            }
        }
        mCallbacks.add(callback);
    }

    @Override
    public void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
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
