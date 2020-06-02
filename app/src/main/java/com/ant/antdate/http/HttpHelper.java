package com.ant.antdate.http;

import android.content.Context;

import com.ant.antdate.BuildConfig;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Map;

import lib.frame.module.http.RequestParams;
import okhttp3.Headers;

/**
 * Created by shuaqq on 2017/10/31.
 */

public class HttpHelper extends lib.frame.module.http.HttpHelper {

    public HttpHelper(Context context) {
        super(context);
    }

    @Override
    public <T> void post(int reqId, String url, Map<String, String> map, Object object, boolean hint, Headers.Builder builder, TypeToken<T> token) {
        url = BuildConfig.HOST + url;
        if (mAppBase.isLogin()) {
            if (builder == null)
                builder = new Headers.Builder();
            builder.add("Authorgetization", "Bearer " + mAppBase.getUserInfo().getToken());
        }
        super.postRequest(reqId, url, new Gson().toJson(map), object, hint, builder, token);

    }

    @Override
    public <T> void get(int reqId, String url, RequestParams params, Object object, boolean hint, Headers.Builder builder, TypeToken<T> token) {
        url = BuildConfig.HOST + url;
        if (mAppBase.isLogin()) {
            if (builder == null)
                builder = new Headers.Builder();
            builder.add("Authorgetization", "Bearer " + mAppBase.getUserInfo().getToken());
        }
        super.get(reqId, url, params, object, hint, builder, token);
    }

    @Override
    public <T> void put(int reqId, String url, RequestParams params, Object object, boolean hint, Headers.Builder builder, TypeToken<T> token) {
        url = BuildConfig.HOST + url;
        if (mAppBase.isLogin()) {
            if (builder == null)
                builder = new Headers.Builder();
            builder.add("Authorgetization", "Bearer " + mAppBase.getUserInfo().getToken());
        }
        super.put(reqId, url, params, object, hint, builder, token);
    }

    @Override
    public <T> void delete(int reqId, String url, RequestParams params, Object object, boolean hint, Headers.Builder builder, TypeToken<T> token) {
        url = BuildConfig.HOST + url;
        if (mAppBase.isLogin()) {
            if (builder == null)
                builder = new Headers.Builder();
            builder.add("Authorgetization", "Bearer " + mAppBase.getUserInfo().getToken());
        }
        super.delete(reqId, url, params, object, hint, builder, token);
    }
}
