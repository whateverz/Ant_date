package lib.frame.base;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.ColorInt;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.reflect.Field;
import java.util.List;

import lib.frame.R;
import lib.frame.bean.EventBase;
import lib.frame.module.http.HttpHelper;
import lib.frame.module.http.HttpResult;
import lib.frame.module.ui.AnnotateUtil;
import lib.frame.utils.Log;
import lib.frame.utils.SystemBarTintManager;
import lib.frame.utils.UIHelper;
import lib.frame.view.dlg.DlgProcessBar;
import lib.frame.view.dlg.DlgSys;


public class BaseFrameActivity extends AppCompatActivity implements OnClickListener, HttpHelper.OnHttpCallBack {

    public String TAG = getClass().getSimpleName();

    public View rootView;// 根页面
    public int rootViewId;// 根页面ID
    protected DlgProcessBar mDlgProcessBar;

    protected Object[] objects;// 传递的数据
    protected String intentMsg;// 传递数据的TAG

    protected BaseFrameActivity mContext;
    protected AppBase mAppBase;

    protected BaseFrameFragment mCurFragment;

    /*记录是不是MIUI*/
    protected static boolean sIsMIUIV6 = true;

    protected boolean isFirstIn = true;// 标识是否第一次进入
    private final int PAGESWITCHDELAYMSG = -12309;// 页面切换延时消息通知
    private final int ID_PREMISSION_REQUEST = -12308;//权限申请
    private final int MSG_ID_PASS_EVENT = 987;

    protected boolean isInited;// 标识是否已经初始化
    private boolean isFinishPage = false;// 标识需要杀掉页面
    public boolean isActivity;//标识页面是否在活动
    public boolean isDestoryed = false;//页面是否被销毁

    protected int flags = 0;

    protected HttpHelper mAsyncHttpHelper;

    public void doAction(int type, Object[] objects) {

    }

    public void mLog(Object object) {
        Log.i(TAG, TAG + "   " + object);
//        Log.i(AppBase.TAG, TAG + "    " + object);
    }

    public boolean isActivity() {
        return isActivity;
    }

    protected void onPageInit() {
        isFirstIn = false;
    }

    protected void onPageChanged() {
        isInited = true;
    }

    public void handleMsg(android.os.Message msg) {
        switch (msg.what) {
            case MSG_ID_PASS_EVENT:
                EventBase event = (EventBase) msg.getData().get("event");
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
        public void handleMessage(android.os.Message msg) {
            if (!isFinishPage) {// 如果页面需要杀掉，则不处理handler的事件
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
        mDlgProcessBar = new DlgProcessBar(mContext);
    }

    protected void initData() {

    }

    public void showProcessBar() {
        if (isActivity) {
            if (mDlgProcessBar == null) {
                mDlgProcessBar = new DlgProcessBar(mContext);
            }
            mDlgProcessBar.doShow();
        }
    }

    public void dismissProcessBar() {
        if (isActivity) {
            if (mDlgProcessBar == null) {
                mDlgProcessBar = new DlgProcessBar(mContext);
            }
            mDlgProcessBar.doDismiss();
        }
    }

    public HttpHelper getHttpHelper() {
        if (mAsyncHttpHelper == null) {
            mAsyncHttpHelper = mAppBase.createHttpHelper(this);
            mAsyncHttpHelper.setOnHttpCallBack(this);
        }
        return mAsyncHttpHelper;
    }

    protected void setRootView() {
    }

    @Override
    protected void onPause() {
        super.onPause();
        mAppBase.curRunningStatus = IdConfigBase.RUNNING_BACKGROUND;
        isActivity = false;
        HideKeyboard(getCurrentFocus());
        if (mCurFragment != null)
            mCurFragment.pause();
    }

    @Override
    protected void onDestroy() {
        isDestoryed = true;
        if (mDlgProcessBar != null)
            mDlgProcessBar.doDismiss();
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    public void handlePassImgs(List<String> list) {

    }

    public void handleEvent(int type, Object[] objects) {
        if (type == IdConfigBase.SHOW_PROCESS_BAR) {
            showProcessBar();
        } else if (type == IdConfigBase.DISMISS_PROCESS_BAR) {
            dismissProcessBar();
        } else if (type == IdConfigBase.PASS_IMG_PATH) {
            if (objects != null && objects.length > 0) {
                List<String> list = (List<String>) objects[0];
                if (list != null && list.size() > 0) {
                    handlePassImgs(list);
                }
            }
        }
    }

    public void handleObject(String tag, Object... objects) {
        if (tag == null) {
            tag = IdConfigBase.INTENT_TAG;
        }
    }

    @Override
    public void finish() {
        super.finish();
    }

    public void finishPage() {
        isFinishPage = true;
        finish();
    }

    protected void initBase() {
    }

    protected void reBuildePage(Bundle savedInstanceState) {
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        intentMsg = intent.getStringExtra("key");
        if (intent.getSerializableExtra("values") != null) {
            objects = (Object[]) intent.getSerializableExtra("values");
        }
        if (isFinishPage) {
            return;
        }
//        handleObject(intentMsg, objects);// 处理传递过来的数据
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        if (UIHelper.scrW == 0) {
            UIHelper.getScreenInfo(this);
        }
        mContext = this;
        mAppBase = (AppBase) getApplicationContext();
        mAppBase.setCurActivity(this);

        initBase();
        if (!mAppBase.isActivityStart && savedInstanceState != null) {
            reBuildePage(savedInstanceState);
            savedInstanceState.clear();
            finishPage();
            return;
        }
        if (savedInstanceState != null) {
            objects = (Object[]) savedInstanceState.getSerializable("value");
        }
        mAppBase.isActivityStart = true;

        Intent intent = getIntent();
        intentMsg = intent.getStringExtra("key");
        if (intent.getSerializableExtra("values") != null) {
            objects = (Object[]) intent.getSerializableExtra("values");
        }
        if (objects == null)
            objects = new Object[]{};

        setRootView();
        attchView();
        AnnotateUtil.initBindView(this);
        if (isFinishPage) {
            return;
        }
        initView();// 初始化页面元素
        handleObject(intentMsg, objects);// 处理传递过来的数据
        initData();// 处理数据
        setClick(initClickView(), initClickViewId());
        initListener();
        loadData();
    }

    protected void attchView() {
        if (rootViewId > 0) {
            rootView = LayoutInflater.from(mContext).inflate(rootViewId, null);
        }
        if (rootView != null) {
            setContentView(rootView);
        }
    }

    protected void loadData() {

    }

    protected void initListener() {
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        isActivity = false;
        outState.putString("app", "app");
        outState.putSerializable("value", objects);
        mLog("TAG -- " + TAG + " onSaveInstanceState IN ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAppBase.curRunningStatus = IdConfigBase.RUNNING_OTHER;
        isActivity = true;
        handler.sendEmptyMessageDelayed(PAGESWITCHDELAYMSG, 500);
        if (mAppBase != null) {
            mAppBase.setCurActivity(this);
        }
        if (mCurFragment != null)
            mCurFragment.resume();
    }

    public void goToActivity(Class<?> cls) {
        goToActivity(cls, IdConfigBase.INTENT_TAG, null);
    }

    public void goToActivity(Class<?> cls, Object[] objects) {
        goToActivity(cls, IdConfigBase.INTENT_TAG, objects);
    }

    public void goToActivity(Class<?> cls, Object object) {
        goToActivity(cls, IdConfigBase.INTENT_TAG, object);
    }

    public void goToActivity(Class<?> cls, String tag, Object object) {
        Object[] objects = {object};
        goToActivity(cls, tag, objects);
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
        intent.setClass(this, cls);
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

    public void addFm(int bodyId, BaseFrameFragment fm) {
        ViewGroup v = findViewById(bodyId);
        v.removeAllViews();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (fm.isAdded())
            fragmentTransaction.show(fm);
        else
            fragmentTransaction.add(bodyId, fm);
        if (!mContext.isDestoryed) {
            fragmentTransaction.commit();
            fm.resume();
        }
        mCurFragment = fm;
    }

    @Override
    public void onClick(View v) {

    }

    public void goToFmNoAnim(int bodyId, BaseFrameFragment to) {
        if (mCurFragment != to) {
            mCurFragment.pause();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            if (!to.isAdded()) { // 先判断是否被add过

                fragmentTransaction.hide(mCurFragment).add(bodyId, to);
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

    public void goToFm(int bodyId, BaseFrameFragment to) {
        goToFm(bodyId, to, false);
    }

    public void goToFm(int bodyId, BaseFrameFragment to, boolean isBack) {
        if (mCurFragment != to) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
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

    public void switchToFm(int bodyId, BaseFrameFragment to) {
        if (mCurFragment != to) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
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

    public void backToFm(int bodyId, BaseFrameFragment to) {
        goToFm(bodyId, to, true);
    }

    public void destoryFragment(BaseFrameFragment[] frameFragments) {
        if (frameFragments != null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            for (BaseFrameFragment frameFragment : frameFragments)
                fragmentTransaction.remove(frameFragment);
            fragmentTransaction.commitNow();
        }
    }

    public void HideKeyboard() {
        HideKeyboard(getCurrentFocus());
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

    public void DisplayToast(String str) {
        UIHelper.ToastMessage(this, str);
    }

    public void DisplayToast(int strRes) {
        UIHelper.ToastMessage(mAppBase, strRes);
    }

    public boolean checkPremission(final String premission) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (checkSelfPermission(premission) != PackageManager.PERMISSION_GRANTED) {
                DlgSys.show(mContext, "温馨提示 ", "您需要打开权限", getString(R.string.no), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mContext.finish();
                            }
                        }, getString(R.string.yes),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    String[] premissions = {premission};
                                    requestPermissions(premissions, ID_PREMISSION_REQUEST);
                                }
                            }
                        });
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == ID_PREMISSION_REQUEST) {
            mLog("onRequestPermissionsResult");
        }
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

    protected <V extends View> V $(@IdRes int id) {
        return findViewById(id);
    }

    /**
     * 设置状态栏背景状态
     */
    protected void setTranslucentStatus(int resId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            winParams.flags |= bits;
            win.setAttributes(winParams);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(resId);
    }

    protected void setTranslucentStatus() {
        setTranslucentStatus(R.color.transparent);
    }

    protected void setTranslucentNavigation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
            winParams.flags |= bits;
            win.setAttributes(winParams);
        }
    }

    /**
     * 设置状态栏顶部字的颜色
     */
    public boolean setStatusColor(final Activity act, boolean isDark) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View rootview = act.getWindow().getDecorView();
            int vis = rootview.getSystemUiVisibility();
            if (isDark) {
                vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            } else {
                vis &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }
            rootview.setSystemUiVisibility(vis);
            return true;
        } else
            return false;
    }

    private static boolean setStatusColorForMeizu(Activity act, boolean isDark) {
        Log.d("BaseActivity", "setStatusColorForMeizu statusBar isDark=" + isDark);
        try {
            WindowManager.LayoutParams lp = act.getWindow().getAttributes();
            Field darkFlag = WindowManager.LayoutParams.class
                    .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
            Field meizuFlags = WindowManager.LayoutParams.class
                    .getDeclaredField("meizuFlags");
            darkFlag.setAccessible(true);
            meizuFlags.setAccessible(true);
            int bit = darkFlag.getInt(null);
            int value = meizuFlags.getInt(lp);
            if (isDark) {
                value |= bit;
            } else {
                value &= ~bit;
            }
            meizuFlags.setInt(lp, value);
            act.getWindow().setAttributes(lp);
            return true;
        } catch (Exception ignored) {
        }
        return false;
    }

    protected void setStatuBarColor(@ColorInt int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);
        }
    }

    protected void setNavigationBarColor(@ColorInt int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            //底部导航栏
            window.setNavigationBarColor(color);
        }
    }

    public void startDIAL(String tel) {
        if (tel.equals(""))
            return;
        Intent localIntent = new Intent("android.intent.action.DIAL", Uri.parse("tel:" + tel));
        startActivity(localIntent);
    }

    public void hideStatusBar(boolean enable) {
        if (enable) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            getWindow().setAttributes(lp);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else {
            WindowManager.LayoutParams attr = getWindow().getAttributes();
            attr.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setAttributes(attr);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    protected void doWithData(Object... objects) {//把值传回上一层Activity
        Intent intent = getIntent();
        intent.putExtra("formUpAct", objects);
        setResult(RESULT_OK, intent);
    }

    public void doGoBackWithData(Object[] objects) {//把值传回上一层Activity
        doWithData(objects);
        finishPage();
    }


    protected void handleResultFromUpAct(int reqId, Object[] objects) {//把值传回上一层Activity
        switch (reqId) {
            case IdConfigBase.REQ_ID_GET_IMG:
                doAction(IdConfigBase.ACTION_IMG_PICK, objects);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK)
            onActivityResult(requestCode, data);
    }

    public void onActivityResult(int requestCode, Intent data) {
        if (data != null && data.getSerializableExtra("formUpAct") != null) {
            handleResultFromUpAct(requestCode, (Object[]) data.getSerializableExtra("formUpAct"));
        }
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
}
