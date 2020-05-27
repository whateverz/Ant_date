package com.ant.antdate.register;

import com.ant.antdate.base.Urls;
import com.ant.antdate.bean.RegisterInfo;
import com.ant.antdate.http.HttpResult;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Map;

import lib.frame.module.http.HttpHelper;

public class RegisterRequest {
    public static void Register(int reqId, String phone,String code,String psd, HttpHelper httpHelper) {
        Map<String, Object> map = new HashMap<>();
        map.put("phone", phone);
        map.put("code", code);
        map.put("password", psd);
        httpHelper.post(reqId, Urls.Register, map, null, false, new TypeToken<HttpResult<RegisterInfo>>() {
        });

    }
}
