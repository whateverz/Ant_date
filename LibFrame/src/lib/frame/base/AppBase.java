package lib.frame.base;

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.os.Build;
import android.text.TextUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import lib.frame.bean.BaseHeaderInfo;
import lib.frame.bean.EventBase;
import lib.frame.bean.UserBaseInfo;
import lib.frame.module.http.HttpHelper;
import lib.frame.utils.ApplicationUtils;
import lib.frame.utils.FileUtils;
import lib.frame.utils.NetWorkUtils;
import lib.frame.utils.SysTools;
import okhttp3.Headers;


public abstract class AppBase extends Application {

    public static String TAG = AppBase.class.getSimpleName();

    // 配置
    public static boolean DEBUG = true;

    protected Headers.Builder defBuilder;
    // 手机基本参数
    public PackageInfo packageInfo;
    public static String ipStr;// 外网ip地址
    public static String diviceId = "";// 机器设备ID

    public boolean isActivityStart;//标识页面已经启动,在启动页设置标记
    //    private BaseFrameActivity mainActivity;// 设置APP主界面
    private BaseFrameActivity curActivity;// 当前页面

    private BcrBase mBcrBase;

    public int curRunningStatus = -1;// 当前APP的状态

    public static int networkStatus = 0;

    public BaseHeaderInfo baseHeaderInfo;

    public BaseHeaderInfo getBaseHeaderInfo() {
        if (baseHeaderInfo == null) {
            baseHeaderInfo = new BaseHeaderInfo();
            baseHeaderInfo.setMac_address(SysTools.getMac());
            baseHeaderInfo.setDevice_no(getDiviceId());
            baseHeaderInfo.setOs_version(Build.VERSION.SDK_INT);
            baseHeaderInfo.setChannel_code(IdConfigBase.CHANNEL);
            baseHeaderInfo.setClient_bundle_id(getPackageName());
            baseHeaderInfo.setClient_version_code(SysTools.getVersionCode(this));
            baseHeaderInfo.setClient_version_name(SysTools.getVersionName(this));
            baseHeaderInfo.setLang(Locale.getDefault().getLanguage());
            baseHeaderInfo.setDevice_no(SysTools.getDeviceID(this));
//            baseHeaderInfo.setModel_name(Build.MODEL);
        }
        return baseHeaderInfo;
    }

    public Headers.Builder getDefBuilder() {
//        if (defBuilder == null) {//使用默认的设置
        creatNewHeadBuilder();
//        }
        return defBuilder;
    }

    public void doAction(int type, Object[] objects) {
        if (getCurActivity() != null)
            getCurActivity().doAction(type, objects);
    }

    public void doGoBack(Object[] objects) {
        getCurActivity().doGoBackWithData(objects);
    }

    protected void creatNewHeadBuilder() {
        defBuilder = new Headers.Builder();
        defBuilder.add("X-Client-Event", getBaseHeaderInfo().getJsonStr());
    }

    public String getDiviceId() {
//        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//        if (tm != null) {
//            diviceId = tm.getDeviceId();
//        }
        return diviceId;
    }

    public BaseFrameActivity getCurActivity() {
        return curActivity;
    }

    public void setCurActivity(BaseFrameActivity curActivity) {
        this.curActivity = curActivity;
    }

    private void initBcr() {
        if (mBcrBase == null) {
            mBcrBase = new BcrBase();
            IntentFilter iFilter = new IntentFilter();
            iFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            registerReceiver(mBcrBase, iFilter);
        }
    }

    public void initBase() {
        Locale locale = getResources().getConfiguration().locale;
        IdConfigBase.language = locale.getLanguage();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //加载热修复的patch
        ApplicationUtils.init(this);
        getPackageInfo();
        DEBUG = isDebug();
        initBaseFile();// 初始化本地文件夹
        initBcr();
//        InitImageLoader(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public PackageInfo getPackageInfo() {
        if (packageInfo == null) {
            try {
                packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                IdConfigBase.cVersion = packageInfo.versionCode;
            } catch (NameNotFoundException e) {
                e.printStackTrace(System.err);
            }
        }
        return packageInfo;
    }

    public void goToActivity(Class<?> cls) {
        goToActivity(cls, IdConfigBase.INTENT_TAG);
    }

    public void goToActivity(Activity mActivity, Class<?> cls) {
        goToActivity(mActivity, cls, IdConfigBase.INTENT_TAG, null);
    }

    public void goToActivity(Class<?> cls, Object... object) {
        goToActivity(curActivity, cls, IdConfigBase.INTENT_TAG, object, 0);
    }

    public void goToActivity(Class<?> cls, String tag, Object... object) {
        goToActivity(curActivity, cls, tag, object, 0);
    }

    public void goToActivity(Class<?> cls, String tag, Object[] objects, int requestCode) {
        goToActivity(curActivity, cls, tag, objects, requestCode);
    }

    public void goToActivity(Activity mActivity, Class<?> cls, String tag,
                             Object[] object) {
        goToActivity(mActivity, cls, tag, object, 0);
    }

    public void goToActivity(Class<?> cls, Object[] object, int requestCode) {
        goToActivity(curActivity, cls, IdConfigBase.INTENT_TAG, object, requestCode);
    }

    public void goToActivity(Activity mActivity, Class<?> cls, String tag, Object[] object, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        intent.putExtra("key", tag);
        if (object != null) {
            intent.putExtra("values", object);
        }
        if (mActivity == null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            if (requestCode > 0) {
                mActivity.startActivityForResult(intent, requestCode);
            } else {
                mActivity.startActivity(intent);
            }
        }
    }

    public void initBaseFile() {
        IdConfigBase.APP_FILE_NAME = getPackageName();//getResources().getString(R.string.app_name);
        IdConfigBase.APP_FILE = getBaseDirName()
                + IdConfigBase.APP_FILE_NAME;// 存放图片的文件夹
        IdConfigBase.IMGS_FILE = IdConfigBase.APP_FILE + "/IMGS/";// 存放图片的文件夹
        IdConfigBase.CROP_FILE = IdConfigBase.APP_FILE + "/CROP/";// 存放裁剪图片的文件夹
        IdConfigBase.TEMP_FILE = IdConfigBase.APP_FILE + "/TEMP/";// 存放临时文件的文件夹
        IdConfigBase.MUSIC_FILE = IdConfigBase.APP_FILE + "/MUSIC/";// 存放音乐文件的文件夹
        IdConfigBase.RECORD_FILE = IdConfigBase.APP_FILE + "/RECORD/";// 存放录音文件的文件夹
        IdConfigBase.CACHE_FILE = IdConfigBase.APP_FILE + "/CACHE/";// 存放缓存的文件夹
        IdConfigBase.DOWNLOAD_FILE = IdConfigBase.APP_FILE + "/DOWNLOAD/";// 存放下载文件的文件夹
        IdConfigBase.PATCH_FILE = IdConfigBase.APP_FILE + "/PATCH/";// 存放增量下载文件的文件夹

        FileUtils.createPath(IdConfigBase.BASE_FILE_NAME);
        FileUtils.createPath(IdConfigBase.APP_FILE);
        FileUtils.createPath(IdConfigBase.IMGS_FILE);
        FileUtils.createPath(IdConfigBase.CROP_FILE);
        FileUtils.createPath(IdConfigBase.TEMP_FILE);
        FileUtils.createPath(IdConfigBase.MUSIC_FILE);
        FileUtils.createPath(IdConfigBase.RECORD_FILE);
        FileUtils.createPath(IdConfigBase.CACHE_FILE);
        FileUtils.createPath(IdConfigBase.DOWNLOAD_FILE);
        FileUtils.createPath(IdConfigBase.PATCH_FILE);
    }

    public boolean isDebug() {
        ApplicationInfo info = getApplicationInfo();
        return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
    }

    public Map<String, String> getUrlMap(String url) {
        Map<String, String> map = new HashMap<>();
        if (url.contains("?")) {
            String result = url.substring(url.indexOf("?") + 1);
            if (!TextUtils.isEmpty(result)) {
                if (result.indexOf("&") > 0) {
                    String[] key = result.split("&");
                    for (String aKey : key) {
                        String[] kv = aKey.split("=");
                        if (kv.length > 1) {
                            map.put(kv[0], kv[1]);
                        }
                    }
                } else {
                    String[] kv = result.split("=");
                    if (kv.length > 1) {
                        map.put(kv[0], kv[1]);
                    }
                }
            }
        }
        return map;
    }

    private void initNetworkInfos() {
        networkStatus = NetWorkUtils.getNetWorkType(this);
        EventBus.getDefault().post(new EventBase(IdConfigBase.NETWORK_CHANGED));
    }

    private class BcrBase extends BroadcastReceiver {
        @Override
        public void onReceive(Context arg0, Intent arg1) {
            String action = arg1.getAction();
            if (action != null && action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {// 网络变化
                initNetworkInfos();
            }
        }
    }

    protected String getBaseDirName() {
        return IdConfigBase.BASE_FILE_NAME;
    }

    public HttpHelper createHttpHelper(Context context) {
        return new HttpHelper(context);
    }

    public abstract UserBaseInfo getUserInfo();

    public abstract void setUserInfo(UserBaseInfo mUserinfo);

    public void updateUserInfo() {
        setUserInfo(getUserInfo());
    }


    public boolean isLogin() {
        return getUserInfo() != null && !TextUtils.isEmpty(getUserInfo().getToken());
    }

    public void logout() {


    }

    public abstract UserBaseInfo createUserinfo();
}
