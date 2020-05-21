package com.ant.antdate.logic;

import com.ant.antdate.base.Urls;
import com.ant.antdate.http.HttpResult;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Map;

import lib.frame.module.http.HttpHelper;

public class LogicRequest {
    public static void sendSMS(int reqId, String phone, int userType, HttpHelper httpHelper) {
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("use_type", String.valueOf(userType));
        httpHelper.post(reqId, Urls.SENDSMS, map, null, false, new TypeToken<HttpResult<Object>>() {
        });

    }
}
