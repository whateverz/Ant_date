package com.ant.antdate.base;

import android.content.Context;
import android.os.Build;

import androidx.multidex.MultiDex;

import com.ant.antdate.bean.Userinfo;
import com.ant.antdate.db.LogicDB;
import com.meituan.android.walle.WalleChannelReader;

import java.util.List;
import java.util.Locale;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import lib.frame.base.AppBase;
import lib.frame.base.IdConfigBase;
import lib.frame.bean.BaseHeaderInfo;
import lib.frame.bean.UserBaseInfo;
import lib.frame.module.http.HttpHelper;
import lib.frame.utils.SysTools;
import okhttp3.Headers;

/**
 * Created by shuaqq on 2017/10/31.
 */

public class App extends AppBase {
    private Userinfo mUserInfo;

    @Override
    public void onCreate() {
        super.onCreate();
        initDao();
    }

    private void initDao() {
        LogicDB.init(this);
        getUserInfo();
    }


    private static String getChannelFromApk(Context context) {
        return WalleChannelReader.getChannel(context.getApplicationContext(), "step");
    }

    @Override
    protected String getBaseDirName() {
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
//            baseHeaderInfo.setDevice_no(SysTools.getDeviceID(this));
//            baseHeaderInfo.setModel_name(Build.MODEL);
        }
        return super.getBaseDirName();
    }

    @Override
    public Headers.Builder getDefBuilder() {
        return new Headers.Builder().add("Content-Type", "application/json");
    }

    @Override
    public void setUserInfo(UserBaseInfo userInfo) {
        if (userInfo instanceof Userinfo) {
            mUserInfo = (Userinfo) userInfo;
            Flowable.create((FlowableOnSubscribe<Userinfo>) e -> {
                LogicDB.getBox(Userinfo.class).removeAll();
                LogicDB.getBox(Userinfo.class).put(mUserInfo);
//                MobclickAgent.onProfileSignIn(mUserInfo.getNickName());
//                CrashReport.setUserId(mUserInfo.getNickName());
                e.onNext(mUserInfo);
            }, BackpressureStrategy.BUFFER)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe();
        }
    }

    @Override
    public void logout() {
        doLogout();
    }

    @Override
    public UserBaseInfo createUserinfo() {
        return new Userinfo();
    }

    private Flowable<UserBaseInfo> getLogoutObservable() {
        return Flowable.create(e -> {
            mUserInfo = new Userinfo();
            LogicDB.getBox(Userinfo.class).removeAll();
            e.onNext(mUserInfo);
        }, BackpressureStrategy.BUFFER);
    }

    public void doLogout() {
        if (mUserInfo != null)
            getLogoutObservable()
                    .subscribeOn(Schedulers.io())
                    .subscribe();
    }

    @Override
    public HttpHelper createHttpHelper(Context context) {
        return new com.ant.antdate.http.HttpHelper(context);
    }

    @Override
    public UserBaseInfo getUserInfo() {
        if (mUserInfo == null) {
            List<Userinfo> userInfos = LogicDB.getBox(Userinfo.class).getAll();
            if (userInfos.size() > 0) {
                mUserInfo = userInfos.get(userInfos.size() - 1);
                if (userInfos.size() > 1) {
                    LogicDB.getBox(Userinfo.class).removeAll();
                }
                setUserInfo(mUserInfo);
            } else {
                mUserInfo = new Userinfo();
            }
        }
        return mUserInfo;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


}
