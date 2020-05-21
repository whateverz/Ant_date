package com.ant.antdate.base;

import android.content.Context;
import android.os.Build;

import androidx.multidex.MultiDex;

import com.ant.antdate.bean.Userinfo;
import com.ant.antdate.db.LogicDB;
import com.meituan.android.walle.WalleChannelReader;

import java.util.Locale;

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

    }

    @Override
    public void logout() {

    }

    @Override
    public UserBaseInfo createUserinfo() {
        return new Userinfo();
    }

    public void doLogout() {

    }

    @Override
    public HttpHelper createHttpHelper(Context context) {
        return new com.ant.antdate.http.HttpHelper(context);
    }

    @Override
    public UserBaseInfo getUserInfo() {
        return new Userinfo();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


}
