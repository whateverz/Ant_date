package lib.frame.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import lib.frame.R;
import lib.frame.bean.EventBase;
import lib.frame.module.http.HttpHelper;
import lib.frame.module.http.HttpResult;
import lib.frame.module.ui.AnnotateUtil;
import lib.frame.utils.Log;
import lib.frame.utils.UIHelper;


public class BaseFrameFragment extends Fragment implements View.OnClickListener, HttpHelper.OnHttpCallBack {

    protected String TAG = getClass().getSimpleName();
    public AppBase mAppBase;
    public BaseFrameActivity mContext;

    public View rootView;// Fragment的根页面
    public int rootViewId;// Fragment的根页面ID

    public boolean isInited;
    public boolean isFirstIn = true;// 标识是否第一次进入
    private final int PAGESWITCHDELAYMSG = -12309;// 页面切换延时消息通知
    public boolean isActivity;//标识页面是否在活动

    protected HttpHelper mAsyncHttpHelper;

    protected BaseFrameFragment mCurFragment;

    private final int MSG_ID_PASS_EVENT = 987;

    protected int flags;

    protected Callback mCallback;

    public BaseFrameFragment() {
        super();
    }

    public void setData(int type, Object[] objects) {// 上层往下传递
    }

    public void handleSelectedImgs(List<String> imgs) {// 上层activity往下传递
    }

    public void doPause() {
        isActivity = false;
    }

    public void doResume() {
        isActivity = true;
    }

    public void mLog(Object object) {
        Log.i(TAG, TAG + "   " + object);
        Log.i(AppBase.TAG, TAG + "    " + object);
    }

    public void doAction(int type, Object[] objects) {

    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        mContext = (BaseFrameActivity) activity;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        isActivity = false;
    }

    protected void onPageInit() {
        isFirstIn = false;
    }

    protected void onPageChanged() {
        isInited = true;
    }

    protected void handleMsg(Message msg) {
        switch (msg.what) {
            case MSG_ID_PASS_EVENT:
                EventBase event = (EventBase) msg.getData().get("event");
                if (event == null)
                    break;
                if (!TextUtils.isEmpty(event.getActTag())) {
                    if (TAG.equals(event.getActTag())) {
                        handleEvent(event.getType(), event.getObjs());
                    }
                } else {
                    if (event.getType() < 10000) {//小于10000的为全局广播
                        handleEvent(event.getType(), event.getObjs());
                    } else if (isActivity) {//大于10000的为当前页面广播
                        handleEvent(event.getType(), event.getObjs());
                    }
                }
                break;
        }
    }

    public Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (getActivity() != null) {
                int what = msg.what;
                if (what == PAGESWITCHDELAYMSG) {
                    if (isFirstIn) {
                        onPageInit();
                    }
                    onPageChanged();
                } else {
                    handleMsg(msg);
                }
            }
        }
    };

    protected void initView() {
    }

    protected void initData() {
    }

    protected void handleObject(Object... objects) {

    }

    protected void setRootView() {
    }

    protected void handlePassImgs(List<String> list) {

    }

    public void handleEvent(int type, Object[] objects) {
        if (type == IdConfigBase.SHOW_PROCESS_BAR) {
            mContext.showProcessBar();
        } else if (type == IdConfigBase.DISMISS_PROCESS_BAR) {
            mContext.dismissProcessBar();
        } else if (type == IdConfigBase.PASS_IMG_PATH) {
            if (objects != null && objects.length > 0) {
                List<String> list = (List<String>) objects[0];
                if (list != null && list.size() > 0) {
                    handlePassImgs(list);
                }
            }
        }
    }

    @Subscribe
    public void onEvent(EventBase event) {
        Message message = new Message();
        message.what = MSG_ID_PASS_EVENT;
        Bundle bundle = new Bundle();
        bundle.putSerializable("event", event);
        message.setData(bundle);
        handler.sendMessage(message);
    }

    protected <V extends View> V $(@IdRes int id) {
        return rootView.findViewById(id);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        if (savedInstanceState != null) {
            return;
        }
        mAppBase = (AppBase) mContext.getApplicationContext();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        isActivity = false;
//        pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        isActivity = true;
//        resume();
        handler.sendEmptyMessageDelayed(PAGESWITCHDELAYMSG, 500);
    }

    public BaseFrameFragment setObject(Object... objects) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", objects);
        setArguments(bundle);
        return this;
    }

    public Object[] getObject() {
        Bundle bundle = getArguments();
        if (bundle != null && bundle.get("data") != null) {
            return (Object[]) bundle.get("data");
        }
        return null;
    }

    protected void initBase() {
    }

    protected void initListener() {
    }

    protected void loadData() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mContext == null)
            mContext = (BaseFrameActivity) getActivity();

        setRootView();
        rootView = attchView(container);
        if (rootView != null) {
            return rootView;
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mAppBase == null)
            mAppBase = (AppBase) mContext.getApplicationContext();
        if (rootView != null) {
            AnnotateUtil.initBindView(this, rootView);
            initBase();
            initView();
            Object[] objects = getObject();
            if (objects != null) {
                handleObject(objects);
            }
            initData();
            setClick(initClickView(), initClickViewId());
            initListener();
            loadData();
        }
    }

    protected View attchView(@Nullable ViewGroup container) {
        if (rootViewId > 0) {
            return LayoutInflater.from(getActivity()).inflate(rootViewId, container, false);
        }
        return rootView;
    }

    public void goToActivity(Class<?> cls) {
        goToActivity(cls, "", null);
    }

    public void goToActivity(Class<?> cls, Object object) {
        Object[] objects = {object};
        goToActivity(cls, IdConfigBase.INTENT_TAG, objects);
    }

    public void goToActivity(Class<?> cls, String tag, Object object) {
        Object[] objects = {object};
        goToActivity(cls, tag, objects);
    }

    public void goToActivity(Class<?> cls, String tag, Object object, int requestCode) {
        Object[] objects = {object};
        goToActivity(cls, tag, objects, requestCode);
    }

    public void goToActivity(Class<?> cls, String tag, Object[] object) {
        goToActivity(cls, tag, object, 0);
    }

    public void goToActivity(Class<?> cls, String tag, Object[] object, int requestCode) {
        goToActivity(cls, tag, flags, requestCode, object);
        flags = 0;
    }

    public void goToActivity(Class<?> cls, int flag, Object... object) {
        goToActivity(cls, IdConfigBase.INTENT_TAG, flag, 0, object);
    }

    public void goToActivity(Class<?> cls, int flag, int requestCode, Object... object) {
        goToActivity(cls, IdConfigBase.INTENT_TAG, flag, requestCode, object);
    }

    public void goToActivity(Class<?> cls, String tag, int flag, int requestCode, Object... object) {
        Intent intent = new Intent();
        intent.setClass(mContext, cls);
        intent.putExtra("key", tag);
        if (flag != 0)
            intent.setFlags(flag);
        if (object != null) {
            intent.putExtra("values", object);
        }
        if (requestCode > 0) {
            startActivityForResult(intent, requestCode);
        } else {
            startActivity(intent);
        }
    }


    @Override
    public void onClick(View v) {
    }

    public boolean keyDown() {
        return true;
    }

    public void pause() {
        if (mCurFragment != null)
            mCurFragment.pause();
    }

    public void resume() {
        if (mCurFragment != null)
            mCurFragment.resume();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public HttpHelper getHttpHelper() {
        if (mAsyncHttpHelper == null) {
            mAsyncHttpHelper = mAppBase.createHttpHelper(mContext);
            mAsyncHttpHelper.setOnHttpCallBack(this);
        }
        return mAsyncHttpHelper;
    }

    public void addFm(int bodyId, BaseFrameFragment fm) {
        mCurFragment = fm;
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.add(bodyId, fm);
        if (!mContext.isDestoryed) {
            fragmentTransaction.commit();
            fm.resume();
        }
    }

    protected void DisplayToast(String str) {
        UIHelper.ToastMessage(mAppBase, str);
    }

    protected void DisplayToast(int strRes) {
        UIHelper.ToastMessage(mAppBase, strRes);
    }

    @Override
    public <T> void onHttpCallBack(int resultType, int reqId, String resContent, Object reqObject, HttpResult<T> httpResult) {
        if (mContext.isDestoryed) {
        }
    }

    @Override
    public <T> void onHttpErrorCallBack(int resultType, int reqId, String resContent, Object reqObject, HttpResult<T> httpResult) {

    }

    @Override
    public <T> void onHttpFinishCallBack(int resultType, int reqId, String resContent, Object reqObject, HttpResult<T> httpResult) {

    }

    public void goToFm(int bodyId, BaseFrameFragment to) {
        goToFm(bodyId, to, false);
    }

    public void goToFm(int bodyId, BaseFrameFragment to, boolean isBack) {
        if (mCurFragment != to) {
            FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
            if (isBack) {
                fragmentTransaction.setCustomAnimations(
                        R.anim.slip_in_from_left, R.anim.slip_out_to_right);
            } else {
                fragmentTransaction.setCustomAnimations(
                        R.anim.slip_in_from_right, R.anim.slip_out_to_left);
            }
            mCurFragment.pause();
            if (!to.isAdded()) {
                fragmentTransaction.hide(mCurFragment).add(bodyId, to);
                // 先判断是否被add过
                if (!mContext.isDestoryed) {
                    fragmentTransaction.commit(); // 隐藏当前的fragment，add下一个到Activity中
                    to.resume();
                }
            } else {
                fragmentTransaction.hide(mCurFragment).show(to);
                if (!mContext.isDestoryed) {
                    fragmentTransaction.commit(); // 隐藏当前的fragment，显示下一个
                    to.resume();
                }
            }
            mCurFragment = to;
        }
    }

    public void HideKeyboard(View v) {
        InputMethodManager imm;
        if (v == null) {
            return;
        }
        imm = (InputMethodManager) v.getContext().getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
        v.clearFocus();
    }

    // 显示虚拟键盘
    public void ShowKeyboard(View v) {
        InputMethodManager imm;
        if (v == null) {
            return;
        }
        v.requestFocus();
        imm = (InputMethodManager) v.getContext().getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(v, InputMethodManager.SHOW_FORCED);
    }

    protected View[] initClickView() {
        return null;
    }

    protected int[] initClickViewId() {
        return null;
    }

    private void setClick(View[] views, int[] ids) {
        if (views != null)
            for (View view : views) view.setOnClickListener(this);
        if (ids != null)
            for (int id : ids) $(id).setOnClickListener(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
            if (data != null && data.getSerializableExtra("formUpAct") != null) {
                handleResultFromUpAct(requestCode, (Object[]) data.getSerializableExtra("formUpAct"));
            }
    }

    protected void handleResultFromUpAct(int reqId, Object[] objects) {//把值传回上一层Activity
        switch (reqId) {
            case IdConfigBase.REQ_ID_GET_IMG:
                doAction(IdConfigBase.ACTION_IMG_PICK, objects);
                break;
        }
    }

    public BaseFrameFragment setCallback(Callback callback) {
        this.mCallback = callback;
        return this;
    }

    public interface Callback {
        void callback(int id, Object... objects);
    }
}
