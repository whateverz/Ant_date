package lib.frame.module.http;

import android.content.Context;

import androidx.annotation.NonNull;

import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.Map;

import lib.frame.R;
import lib.frame.base.AppBase;
import lib.frame.base.BaseFrameActivity;
import lib.frame.base.IdConfigBase;
import lib.frame.bean.EventBase;
import lib.frame.logic.LogicBase;
import lib.frame.utils.CipherUtils;
import lib.frame.utils.Log;
import lib.frame.utils.UIHelper;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.Response;


public class HttpHelper {

    public static int CODE_NEED_RELOGIN = 1104;

    public static String API_KEY = "";
    public static String API_SECRET = "";


    public static final int SUCCESS = 1;
    public static final int ERROR = 2;

    public static String HEADER = "";

    private String TAG = HttpHelper.class.getSimpleName();
    protected AppBase mAppBase;

    public final static int POST = 0;
    public final static int GET = 1;
    public final static int PUT = 2;
    public final static int DELETE = 3;

    protected OnHttpCallBack mCallBack;

    private void mLogResult(Object object) {
        if (AppBase.DEBUG) {
            Log.i("mLogResult", TAG + "   " + object);
        }
    }

    private void mLogRequest(Object object) {
        if (AppBase.DEBUG) {
            Log.i("mLogRequest", TAG + "   " + object);
        }
    }

    protected String getSignStr(Map<String, String> map) {
        StringBuilder value = new StringBuilder();
        for (String o : map.keySet()) {
            if (!TextUtils.isEmpty(map.get(o))) {
                value.append(o).append("=").append(map.get(o)).append("&");
            }
        }
        value.append("&").append(API_SECRET);
        Log.i("lwxkey", "value -- " + value);
        return CipherUtils.md5(value.toString());//value.substring(0, value.length() - 1));
    }

//    public void setDefBuilder(Headers.Builder defBuilder) {
//        this.defBuilder = defBuilder;
//    }

    public HttpHelper(Context context) {
        super();
        Context appContext = context.getApplicationContext();
        if (appContext instanceof AppBase)
            mAppBase = (AppBase) context.getApplicationContext();

//        if (defBuilder == null) {//使用默认的设置
//            defBuilder = new Headers.Builder();
//            defBuilder.add("X-Client-Event", mAppBase.getBaseHeaderInfo().getJsonStr());
//        }
    }

    public <T> void post(int reqId, String url, Map<String, Object> map, Object object, boolean hint) {
        post(reqId, url, map, object, hint, null);
    }

    public <T> void post(int reqId, String url, Map<String, Object> map, Object object, boolean hint,
                         TypeToken<T> token) {
        post(reqId, url, map, object, hint, null, token);
    }

    public <T> void post(int reqId, String url, Map<String, Object> map, Object object, boolean hint,
                         Headers.Builder builder, TypeToken<T> token) {
        post(reqId, url, new RequestParams(map), object, hint, builder, token);
    }




    public <T> void post(int reqId, String url, RequestParams params, Object object, boolean hint, TypeToken<T> token) {
        post(reqId, url, params, object, hint, null, token);
    }

    public <T> void post(int reqId, String url, RequestParams params, Object object, boolean hint, Headers.Builder builder, TypeToken<T> token) {
        post(reqId, POST, url, params, object, hint, builder, token);
    }

    public <T> void get(int reqId, String url, Map<String, String> map, Object object, boolean hint) {
        get(reqId, url, map, object, hint, null);
    }

    public <T> void get(int reqId, String url, Map<String, String> map, Object object, boolean hint,
                        TypeToken<T> token) {
        get(reqId, url, map, object, hint, null, token);
    }

    public <T> void get(int reqId, String url, Map<String, String> map, Object object, boolean hint,
                        Headers.Builder builder, TypeToken<T> token) {
        get(reqId, url, new RequestParams(map), object, hint, builder, token);
    }

    public <T> void get(int reqId, String url, RequestParams params, Object object, boolean hint, TypeToken<T> token) {
        get(reqId, url, params, object, hint, null, token);
    }

    public <T> void get(int reqId, String url, RequestParams params, Object object, boolean hint, Headers.Builder builder, TypeToken<T> token) {
        post(reqId, GET, url, params, object, hint, builder, token);
    }

    public <T> void put(int reqId, String url, Map<String, String> map, Object object, boolean hint) {
        put(reqId, url, map, object, hint, null);
    }

    public <T> void put(int reqId, String url, Map<String, String> map, Object object, boolean hint,
                        TypeToken<T> token) {
        put(reqId, url, map, object, hint, null, token);
    }

    public <T> void put(int reqId, String url, Map<String, String> map, Object object, boolean hint,
                        Headers.Builder builder, TypeToken<T> token) {
        put(reqId, url, new RequestParams(map), object, hint, builder, token);
    }

    public <T> void put(int reqId, String url, RequestParams params, Object object, boolean hint, TypeToken<T> token) {
        put(reqId, url, params, object, hint, null, token);
    }

    public <T> void put(int reqId, String url, RequestParams params, Object object, boolean hint, Headers.Builder builder, TypeToken<T> token) {
        post(reqId, PUT, url, params, object, hint, builder, token);
    }

    public <T> void delete(int reqId, String url, Map<String, String> map, Object object, boolean hint) {
        delete(reqId, url, map, object, hint, null);
    }

    public <T> void delete(int reqId, String url, Map<String, String> map, Object object, boolean hint,
                           TypeToken<T> token) {
        delete(reqId, url, map, object, hint, null, token);
    }

    public <T> void delete(int reqId, String url, Map<String, String> map, Object object, boolean hint,
                           Headers.Builder builder, TypeToken<T> token) {
        delete(reqId, url, new RequestParams(map), object, hint, builder, token);
    }

    public <T> void delete(int reqId, String url, RequestParams params, Object object, boolean hint, TypeToken<T> token) {
        delete(reqId, url, params, object, hint, null, token);
    }

    public <T> void delete(int reqId, String url, RequestParams params, Object object, boolean hint, Headers.Builder builder, TypeToken<T> token) {
        post(reqId, DELETE, url, params, object, hint, builder, token);
    }

    public <T> void post(int reqId, int method, String url, RequestParams params, Object object, boolean hint, Headers.Builder builder, TypeToken<T> token) {
        postRequest(reqId, method, url, params, object, hint, builder, token);
    }

    protected <T> void postRequest(int reqId, String url, RequestParams params, Object object, boolean hint, Headers.Builder builder, TypeToken<T> token) {
        postRequest(reqId, POST, url, params, object, hint, builder, token);
    }

    protected <T> void postRequest(int reqId, int method, String url, RequestParams params, Object object, boolean hint, Headers.Builder builder, TypeToken<T> token) {
        if (hint) {
            EventBus.getDefault().post(new EventBase(IdConfigBase.SHOW_PROCESS_BAR));
        }
        MyHttpResponse<T> myHttpResponse = new MyHttpResponse<>(token);
        myHttpResponse.setReqId(reqId);
        myHttpResponse.setPassObj(object);
        if (params != null) {
            mLogRequest(url + "?" + params.toString() + "  token = " + mAppBase.getBaseHeaderInfo().getJsonStr());
        } else {
            mLogRequest(url);
        }
        TwitterRestClient.getInstance().okPost(method, url, params, builder == null ? mAppBase.getDefBuilder() : builder, myHttpResponse);
    }

    protected <T> void postRequest(int reqId, String url, String params, Object object, boolean hint, Headers.Builder builder, TypeToken<T> token) {
        if (hint) {
            EventBus.getDefault().post(new EventBase(IdConfigBase.SHOW_PROCESS_BAR));
        }
        MyHttpResponse<T> myHttpResponse = new MyHttpResponse<>(token);
        myHttpResponse.setReqId(reqId);
        myHttpResponse.setPassObj(object);
        if (params != null) {
            mLogRequest(url + "?" + params + "  token = " + mAppBase.getBaseHeaderInfo().getJsonStr());
        } else {
            mLogRequest(url);
        }
        TwitterRestClient.getInstance().okPost(url, params, builder == null ? mAppBase.getDefBuilder() : builder, myHttpResponse);
    }

    protected <T> HttpResult<T> postRequests(int reqId, String url, RequestParams params, Object object, boolean hint, Headers.Builder builder, TypeToken<T> token) throws IOException {
        return postRequests(reqId, POST, url, params, object, hint, builder, token);
    }

    protected <T> HttpResult<T> postRequests(int reqId, int method, String url, RequestParams params, Object object, boolean hint, Headers.Builder builder, TypeToken<T> token) throws IOException {
        if (hint) {
            EventBus.getDefault().post(new EventBase(IdConfigBase.SHOW_PROCESS_BAR));
        }
        MyHttpResponse<T> myHttpResponse = new MyHttpResponse<>(token);
        myHttpResponse.setReqId(reqId);
        myHttpResponse.setPassObj(object);
        if (params != null) {
            mLogRequest(url + "?" + params.toString() + "  token = " + mAppBase.getBaseHeaderInfo().getJsonStr());
        } else {
            mLogRequest(url);
        }
        Response response = TwitterRestClient.getInstance().okPost(method, url, params, builder == null ? mAppBase.getDefBuilder() : builder);
        if (response.isSuccessful()) {
            String content = response.body().string();

            if (!TextUtils.isEmpty(content)) {
                mLogResult("content -- " + content);
            }
            return LogicBase.getInstance(mAppBase).handleHttpDatas(content, token);

        } else {
            return null;

        }
    }

    protected class MyHttpResponse<T> implements Callback {
        private int reqId;
        private Object passObj;
        private TypeToken<T> token;

        public void setReqId(int reqId) {
            this.reqId = reqId;
        }

        public void setPassObj(Object passObj) {
            this.passObj = passObj;
        }

        public MyHttpResponse(TypeToken<T> token) {
            this.token = token;
        }

        @Override
        public void onFailure(@NonNull Call call, @NonNull IOException e) {
            if (mCallBack instanceof BaseFrameActivity && ((BaseFrameActivity) mCallBack).isDestoryed) {
                return;
            }
            EventBus.getDefault().post(new EventBase(IdConfigBase.DISMISS_PROCESS_BAR));

            mLogResult("Error:" + e.getMessage());
            if (AppBase.DEBUG) {
                e.printStackTrace();
            }
            UIHelper.ToastMessage(mAppBase, mAppBase.getString(R.string.network_fail));
            if (mCallBack != null) {
                onCallBack(ERROR, reqId, e.getMessage(), passObj, null);
            }
        }

        @Override
        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
            if (mCallBack instanceof BaseFrameActivity && ((BaseFrameActivity) mCallBack).isDestoryed) {
                return;
            }
            EventBus.getDefault().post(new EventBase(IdConfigBase.DISMISS_PROCESS_BAR));
            if (mCallBack != null) {
                String content = response.body().string();
                if (!TextUtils.isEmpty(content)) {
                    mLogResult("content -- " + content);
//                    FileUtils.saveFileCache(content.getBytes(), IdConfigBase.APP_FILE, "file.txt");
                    if (token != null) {//体系内的请求
                        HttpResult<T> result = LogicBase.getInstance(mAppBase).handleHttpDatas(content, token);
                        if (result != null) {
                            if (result.isSuccess()) {
                                if (!TextUtils.isEmpty(result.getUserMsg())) {
                                    UIHelper.ToastMessage(mAppBase, result.getUserMsg());
                                }
                                onCallBack(SUCCESS, reqId, content, passObj, result);
                            } else {
                                if (result.isNeedlogin()) {
//                                    UIHelper.ToastMessage(mAppBase, mAppBase.getString(R.string.please_relogin));
                                    mAppBase.logout();
//                                    EventBus.getDefault().post(new EventBase(IdConfigBase.EVENT_NEED_RELOGIN));
                                } else {
                                    if (!TextUtils.isEmpty(result.getUserMsg())) {
                                        UIHelper.ToastMessage(mAppBase, result.getUserMsg());
                                    } else if (!TextUtils.isEmpty(result.getMsg())) {
                                        UIHelper.ToastMessage(mAppBase, result.getMsg());
                                    }
                                }
                                onCallBack(ERROR, reqId, content, passObj, result);
                            }
                        } else {
                            UIHelper.ToastMessage(mAppBase, mAppBase.getString(R.string.data_err));
                            onCallBack(ERROR, reqId, content, passObj, null);
                        }
                    } else {
                        onCallBack(SUCCESS, reqId, content, passObj, null);
                        mLogResult("token == null ");
                    }
                } else
                    onCallBack(ERROR, reqId, content, passObj, null);
                response.close();
            }
        }
    }

    private <T> void onCallBack(final int resultType, final int reqId, final String resContent,
                                final Object reqObject, final HttpResult<T> httpResult) {
        if (mCallBack != null) {
            TwitterRestClient.getInstance().getmDelivery().execute(new Runnable() {
                @Override
                public void run() {
                    if (resultType == SUCCESS)
                        mCallBack.onHttpCallBack(resultType, reqId, resContent, reqObject, httpResult);
                    else
                        mCallBack.onHttpErrorCallBack(resultType, reqId, resContent, reqObject, httpResult);
                    mCallBack.onHttpFinishCallBack(resultType, reqId, resContent, reqObject, httpResult);
                }
            });
        }
    }

    // type 0:网络访问失败；1：网络请求失败；2：网络访问成功
    public interface OnHttpCallBack {
        <T> void onHttpCallBack(int resultType, int reqId, String resContent, Object reqObject, HttpResult<T> httpResult);

        <T> void onHttpErrorCallBack(int resultType, int reqId, String resContent, Object reqObject, HttpResult<T> httpResult);

        <T> void onHttpFinishCallBack(int resultType, int reqId, String resContent, Object reqObject, HttpResult<T> httpResult);
    }

    public interface OnHttpProgress {
        <T> void onHttpProgressCallBack(int resultType, int reqId, int cureent, int total);
    }

    public void setOnHttpCallBack(OnHttpCallBack callBack) {
        mCallBack = callBack;
    }


}
