package lib.frame.module.http;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import lib.frame.base.AppBase;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class TwitterRestClient {
    private static TwitterRestClient mTwitterRestClient;
    private OkHttpClient okHttpClient;
    private OkHttpClient.Builder builder;
    private Platform mPlatform;

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private TwitterRestClient() {
        builder = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(AppBase.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE))
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS);
        okHttpClient = builder.build();
        mPlatform = Platform.get();
//        mDelivery = new Handler(Looper.getMainLooper());
    }

    public static TwitterRestClient getInstance() {
        if (mTwitterRestClient == null) {
            mTwitterRestClient = new TwitterRestClient();
        }
        return mTwitterRestClient;
    }

    public Call okPost(String url, RequestParams params,
                       Callback responseHandler) {
        return okPost(buildOkRequest(url, params), responseHandler);
    }

    public Response okPost(String url, RequestParams params) throws IOException {
        return okPost(buildOkRequest(url, params));
    }

    public Call okPost(int method, String url, RequestParams params, Headers.Builder header, Callback responseHandler) {
        return okPost(buildOkRequest(method, url, params, header), responseHandler);
//        okHttpClient.newCall(buildOkRequest(url, params, header)).enqueue(responseHandler);
    }

    public Call okPost(String url, String params, Headers.Builder header, Callback responseHandler) {
        return okPost(buildOkRequest(url, params, header), responseHandler);
//        okHttpClient.newCall(buildOkRequest(url, params, header)).enqueue(responseHandler);
    }

    public Response okPost(int method, String url, RequestParams params, Headers.Builder header) throws IOException {
        return okPost(buildOkRequest(method, url, params, header));
//        okHttpClient.newCall(buildOkRequest(url, params, header)).enqueue(responseHandler);
    }

    public Call okPost(Request request, Callback responseHandler) {
        Call call = okHttpClient.newCall(request);
        call.enqueue(responseHandler);
        return call;
    }

    public Response okPost(Request request) throws IOException {
        Call call = okHttpClient.newCall(request);
        return call.execute();
    }

    public Call upLoad(Request request, final Callback responseHandler) {
        OkHttpClient cloneClient = builder.build();
        Call call = cloneClient.newCall(request);
        call.enqueue(responseHandler);
        return call;
    }

    public OkHttpClient getOkHttpClientClone() {
        return builder.build();
    }

    private Request buildOkRequest(String url, RequestParams params) {
        return buildOkRequest(HttpHelper.POST, url, params, null);
    }

    private Request buildOkRequest(String url, String params, Headers.Builder header) {
        Request.Builder builder = new Request.Builder();
        if (header != null) {
            builder.headers(header.build());
        }
        if (params != null) {
            builder.url(url).post(RequestBody.create(params, JSON));
        } else {
            builder.url(url);
        }
        return builder.build();
    }

    private Request buildOkRequest(int method, String url, RequestParams params, Headers.Builder header) {
        Request.Builder builder = new Request.Builder();
        if (header != null) {
            builder.headers(header.build());
        }
        if (params != null) {
            switch (method) {
                case HttpHelper.POST:
                    builder.url(url).post(params.getRequestBody());
                    break;
                case HttpHelper.GET:
                    builder.url(url + "?" + params.toString()).get();
                    break;
                case HttpHelper.PUT:
                    builder.url(url).put(params.getRequestBody());
                    break;
                case HttpHelper.DELETE:
                    builder.url(url).delete(params.getRequestBody());
                    break;
            }
        } else {
            builder.url(url);
        }
//        Request request = new Request().newBuilder().build();
        return builder.build();
    }

    public Platform getmDelivery() {
        return mPlatform;
    }
}
