package lib.frame.base;

import android.content.Context;
import androidx.annotation.IdRes;
import androidx.annotation.StringRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import lib.frame.module.http.HttpHelper;
import lib.frame.module.http.HttpResult;
import lib.frame.module.ui.AnnotateUtil;
import lib.frame.utils.Log;
import lib.frame.utils.UIHelper;
import lib.frame.view.item.ItemBase;

/**
 * Created by shuaq on 2016/8/25.
 */

public class BaseFrameView extends RelativeLayout implements View.OnClickListener, HttpHelper.OnHttpCallBack {

    protected ItemBase.ItemCallback mCallBack;

    protected AppBase mAppBase;

    protected String TAG = getClass().getSimpleName();

    protected Context mContext;

    protected HttpHelper mAsyncHttpHelper;

    protected View rootView;

    protected BaseFrameFragment fragment;

    protected int flags;

    public BaseFrameView(Context context) {
        super(context);
        init(context, null);

    }

    public BaseFrameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public BaseFrameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    protected void init(Context context, AttributeSet attrs) {
        mContext = context;
        if (!isInEditMode())
            mAppBase = (AppBase) context.getApplicationContext();
        initBase();
        attchView();
        AnnotateUtil.initBindWidget(this);
        setClick(initClickView(), initClickViewId());
        if (attrs != null)
            dealAttr(attrs);
        initThis();
    }

    private void attchView() {
        rootView = getLayoutView();
        if (rootView != null) {
            addView(rootView);
        }
    }

    public HttpHelper getHttpHelper() {
        if (mAsyncHttpHelper == null) {
            mAsyncHttpHelper = mAppBase.createHttpHelper(mContext);
            mAsyncHttpHelper.setOnHttpCallBack(this);
        }
        return mAsyncHttpHelper;
    }

    protected int getLayout() {
        return 0;
    }

    protected View getLayoutView() {
        int layoutRes = getLayout();
        if (layoutRes != 0) {
            rootView = LayoutInflater.from(mContext).inflate(layoutRes, this, false);
        }
        return rootView;
    }

    protected void initBase() {

    }

    protected void initThis() {

    }

    protected void dealAttr(AttributeSet attrs) {

    }

    @Override
    public void onClick(View v) {

    }

    protected void DisplayToast(String str) {
        UIHelper.ToastMessage(mContext, str);
    }

    protected void DisplayToast(int strRes) {
        UIHelper.ToastMessage(mContext, mContext.getString(strRes));
    }

    protected <V extends View> V $(@IdRes int id) {
        return rootView.findViewById(id);
    }


    @Override
    public <T> void onHttpCallBack(int resultType, int reqId, String resContent, Object reqObject, HttpResult<T> httpResult) {

    }

    @Override
    public <T> void onHttpErrorCallBack(int resultType, int reqId, String resContent, Object reqObject, HttpResult<T> httpResult) {

    }

    @Override
    public <T> void onHttpFinishCallBack(int resultType, int reqId, String resContent, Object reqObject, HttpResult<T> httpResult) {

    }

    public void goToActivity(Class<?> cls) {
        goToActivity(cls, IdConfigBase.INTENT_TAG);
    }

    public void goToActivity(Class<?> cls, Object... object) {
        goToActivity(cls, IdConfigBase.INTENT_TAG, object);
    }

    public void goToActivity(Class<?> cls, String tag, Object... object) {
        goToActivity(cls, tag, object, 0);
    }

    public void goToActivity(Class<?> cls, String tag, Object[] objects, int request) {
        goToActivity(cls, tag, flags, request, objects);
        flags = 0;
    }

    public void goToActivity(Class<?> cls, int flag, Object... object) {
        goToActivity(cls, IdConfigBase.INTENT_TAG, flag, 0, object);
    }

    public void goToActivity(Class<?> cls, int flag, int requestCode, Object... object) {
        goToActivity(cls, IdConfigBase.INTENT_TAG, flag, requestCode, object);
    }

    public void goToActivity(Class<?> cls, String tag, int flag, int requestCode, Object... object) {
        if (fragment == null)
            ((BaseFrameActivity) mContext).goToActivity(cls, tag, flag, requestCode, object);
        else
            fragment.goToActivity(cls, tag, flag, requestCode, object);
    }

    public void mLog(Object object) {
        Log.i(TAG, TAG + "   " + object);
        Log.i(AppBase.TAG, TAG + "    " + object);
    }

    protected String getString(@StringRes int resId, Object... formatArgs) {
        return mContext.getString(resId, formatArgs);
    }

    protected String getString(@StringRes int resId) {
        return mContext.getString(resId);
    }

    protected View[] initClickView() {
        return null;
    }

    protected int[] initClickViewId() {
        return null;
    }

    private void setClick(View[] views, int[] ids) {
        if (views != null)
            for (View view : views) if (view != null) view.setOnClickListener(this);
        if (ids != null)
            for (int id : ids) {
                View view = $(id);
                if (view != null)
                    view.setOnClickListener(this);
            }
    }

    public BaseFrameView setCallBack(ItemBase.ItemCallback mCallBack) {
        this.mCallBack = mCallBack;
        return this;
    }

    public BaseFrameView setFragment(BaseFrameFragment fragment) {
        this.fragment = fragment;
        return this;
    }
}
