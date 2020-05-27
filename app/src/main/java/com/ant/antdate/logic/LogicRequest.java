package com.ant.antdate.logic;

import com.ant.antdate.base.Urls;
import com.ant.antdate.bean.LoginInfo;
import com.ant.antdate.bean.RegisterInfo;
import com.ant.antdate.http.HttpResult;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Map;

import lib.frame.module.http.HttpHelper;

public class LogicRequest {
    public static void sendSMS(int reqId, String phone, int userType, HttpHelper httpHelper) {
        Map<String, Object> map = new HashMap<>();
        map.put("phone", phone);
        map.put("use_type", userType);
        httpHelper.post(reqId, Urls.SENDSMS, map, null, false, new TypeToken<HttpResult<Object>>() {
        });
    }
    public static void Login(int reqId, String phone, String psd, HttpHelper httpHelper) {
        Map<String, Object> map = new HashMap<>();
        map.put("phone", phone);
        map.put("password", psd);
        httpHelper.post(reqId, Urls.LOGIN, map, null, false, new TypeToken<HttpResult<LoginInfo>>() {
        });
    }
    public static void LoginbyCode(int reqId, String phone, String code, HttpHelper httpHelper) {
        Map<String, Object> map = new HashMap<>();
        map.put("phone", phone);
        map.put("code", code);
        httpHelper.post(reqId, Urls.LOGIN, map, null, false, new TypeToken<HttpResult<LoginInfo>>() {
        });
    }
    public static void send(int reqId, String phone, int userType, HttpHelper httpHelper) {
        Map<String, Object> map = new HashMap<>();
        map.put("phone", phone);
        map.put("use_type", String.valueOf(userType));
        httpHelper.post(reqId, Urls.SENDSMS, map, null, false, new TypeToken<HttpResult<Object>>() {
        });
    }
    public static void ResetPsd(int reqId, String phone,String code,String psd, HttpHelper httpHelper) {
        Map<String, Object> map = new HashMap<>();
        map.put("phone", phone);
        map.put("new_password", psd);
        map.put("code", code);
        httpHelper.post(reqId, Urls.ResetPsd, map, null, false, new TypeToken<HttpResult<Object>>() {
        });

    }
}
