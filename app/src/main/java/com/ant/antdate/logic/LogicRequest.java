package com.ant.antdate.logic;

import com.ant.antdate.base.Urls;
import com.ant.antdate.bean.CommonListInfo;
import com.ant.antdate.bean.ContentInfo;
import com.ant.antdate.bean.LoginInfo;
import com.ant.antdate.bean.RegisterInfo;
import com.ant.antdate.http.HttpResult;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lib.frame.module.http.HttpHelper;

public class LogicRequest {
    public static void sendSMS(int reqId, String phone, int userType, HttpHelper httpHelper) {
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("use_type", userType + "");
        httpHelper.post(reqId, Urls.SENDSMS, map, null, false, new TypeToken<HttpResult<Object>>() {
        });
    }

    public static void Login(int reqId, String phone, String psd, HttpHelper httpHelper) {
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("password", psd);
        httpHelper.post(reqId, Urls.LOGIN, map, null, false, new TypeToken<HttpResult<LoginInfo>>() {
        });
    }

    public static void LoginbyCode(int reqId, String phone, String code, HttpHelper httpHelper) {
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("code", code);
        httpHelper.post(reqId, Urls.LOGIN, map, null, false, new TypeToken<HttpResult<LoginInfo>>() {
        });
    }

    public static void send(int reqId, String phone, int userType, HttpHelper httpHelper) {
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("use_type", String.valueOf(userType));
        httpHelper.post(reqId, Urls.SENDSMS, map, null, false, new TypeToken<HttpResult<Object>>() {
        });
    }

    public static void ResetPsd(int reqId, String phone, String code, String psd, HttpHelper httpHelper) {
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("new_password", psd);
        map.put("code", code);
        httpHelper.post(reqId, Urls.ResetPsd, map, null, false, new TypeToken<HttpResult<Object>>() {
        });
    }

    public static void Hotlist(int reqId, HttpHelper httpHelper) {
        Map<String, String> map = new HashMap<>();
        httpHelper.get(reqId, Urls.HOTLIST, map, null, false, new TypeToken<HttpResult<CommonListInfo<ContentInfo>>>() {
        });
    }
    public static void Toplist(int reqId, HttpHelper httpHelper) {
        Map<String, String> map = new HashMap<>();
        httpHelper.get(reqId, Urls.TopTen, map, null, false, new TypeToken<HttpResult<List<ContentInfo>>>() {
        });
    }
    public static void ChangeUserMessage(int reqId, HttpHelper httpHelper) {
        Map<String, String> map = new HashMap<>();
        httpHelper.post(reqId, Urls.ChangeUserMessage, map, null, false, new TypeToken<HttpResult<Object>>() {
        });
    }
}
