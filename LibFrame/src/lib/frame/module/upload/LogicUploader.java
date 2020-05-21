package lib.frame.module.upload;

import android.content.Context;
import androidx.annotation.NonNull;

import java.io.File;
import java.io.IOException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lib.frame.R;
import lib.frame.base.AppBase;
import lib.frame.base.IdConfigBase;
import lib.frame.module.http.CountingRequestBody;
import lib.frame.module.http.TwitterRestClient;
import lib.frame.utils.FileUtils;
import lib.frame.utils.ImageUtils;
import lib.frame.utils.Log;
import lib.frame.utils.PictureUtil;
import lib.frame.utils.UIHelper;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class LogicUploader {

    private Call mCurCall;

    public static String FILE_UPLOAD_URL = "";// 文件上传

    private String TAG = "LogicUploader";
    private static LogicUploader logicUploader;

    private AppBase mAppBase;

    private OnFilesUploadCallBack mListener;

    private Object object;

    private LogicUploader(Context context) {
        mAppBase = (AppBase) context.getApplicationContext();
    }

    private LogicUploader() {
    }

    public static LogicUploader getInstance(Context context) {
        if (logicUploader == null) {
            logicUploader = new LogicUploader(context);
        }
        return logicUploader;
    }

    public static LogicUploader getInstance() {
        if (logicUploader == null) {
            logicUploader = new LogicUploader();
        }
        return logicUploader;
    }

    public void uploadFile(String key, Map<String, String> map, File file, Object o, OnFilesUploadCallBack callBack) {
        mListener = callBack;
        object = o;
        List<File> fileList = new ArrayList<>();
        fileList.add(file);
        uploadFile(key, map, fileList, o, callBack);
    }

    public void uploadFile(String key, Map<String, String> map, List<File> fileList, Object o, OnFilesUploadCallBack callBack) {
        mListener = callBack;
        object = o;
        List<String> fileNameList = new ArrayList<>();
        for (int i = 0; i < fileList.size(); i++) {
            if (fileList.get(i).exists()) {
                fileNameList.add(fileList.get(i).getName());
            } else {
                UIHelper.ToastMessage(mAppBase, mAppBase.getResources().getString(R.string.file_not_exist) + "\n" + fileList.get(i).getName());
                return;
            }
        }
        mCurCall = TwitterRestClient.getInstance().upLoad(makeUploadRequest(key, FILE_UPLOAD_URL, map, fileList, fileNameList, callBack), new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                if (mListener != null) {
                    mListener.onUploadeError();
                }
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull final Response response) throws IOException {
                if (mListener != null) {
                    final String result = response.body().string();
                    Log.i("lwxkey", result);

                    TwitterRestClient.getInstance().getmDelivery().execute(new Runnable() {

                        @Override
                        public void run() {
                            mListener.onUploadedSuccess(result);
                        }
                    });
                    response.close();

                }
            }
        });
    }

    public void uploadImage(String key, Map<String, String> map, String path, Object o, OnFilesUploadCallBack callBack) {
        List<String> fileList = new ArrayList<>();
        fileList.add(path);
        uploadImages(key, map, fileList, o, callBack);
    }

    public void uploadImages(String key, Map<String, String> map, List<String> fileList, Object o, OnFilesUploadCallBack callBack) {
        List<File> mFiles = new ArrayList<>();
        FileUtils.delAllFile(IdConfigBase.TEMP_FILE);// 清空本地临时图片缓存
        for (int i = 0; i < fileList.size(); i++) {
            if (new File(fileList.get(i)).exists()) {
                String str = IdConfigBase.TEMP_FILE + i;
                PictureUtil.saveSmallImg2Path(fileList.get(i), str);
                File file = new File(str);
                if (file.exists()) {
                    mFiles.add(file);
                }
            } else {
                UIHelper.ToastMessage(mAppBase, mAppBase.getResources().getString(R.string.file_not_exist) + "\n" + fileList.get(i));
                return;
            }
        }
        if (mFiles.size() > 0) {
            uploadImgs(key, map, mFiles, o, callBack);
        }
    }

    public void uploadImgs(String key, Map<String, String> map, List<File> fileList, Object o, OnFilesUploadCallBack callBack) {
        mListener = callBack;
        object = o;
        List<String> fileNameList = new ArrayList<>();
        for (int i = 0; i < fileList.size(); i++) {
            if (fileList.get(i).exists()) {
                fileNameList.add(fileList.get(i).getName() + ".png");
            } else {
                UIHelper.ToastMessage(mAppBase, mAppBase.getResources().getString(R.string.file_not_exist) + "\n" + fileList.get(i).getName());
                return;
            }
        }
        mCurCall = TwitterRestClient.getInstance().upLoad(makeImageUploadRequest(key, FILE_UPLOAD_URL, map, fileList, fileNameList, callBack), new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                if (mListener != null) {
                    mListener.onUploadeError();
                }
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull final Response response) throws IOException {
                mCurCall = null;
                FileUtils.delAllFile(IdConfigBase.TEMP_FILE);// 清空本地临时图片缓存
                ImageUtils.scanPhoto(mAppBase, IdConfigBase.TEMP_FILE);
                final String result = response.body().string();

                if (mListener != null) {
                    TwitterRestClient.getInstance().getmDelivery().execute(new Runnable() {
                        @Override
                        public void run() {
                            mListener.onUploadedSuccess(result);
                        }
                    });
                }
                response.close();
            }
        });
    }

    private Request makeImageUploadRequest(String key, String url, Map<String, String> map, List<File> files, List<String> fileName, final OnFilesUploadCallBack callBack) {
        Request.Builder requestBuilder = new Request.Builder();
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        for (int i = 0; i < files.size(); i++) {
            File uploadFile = files.get(i);
            builder.addFormDataPart(key, fileName.get(i), RequestBody.create(MediaType.parse("image/png"), uploadFile));
        }
        if (map != null) {
            for (String keyMap : map.keySet()) {
                builder.addFormDataPart(keyMap, map.get(keyMap));
            }
        }
        requestBuilder.url(url).post(new CountingRequestBody(builder.build(), new CountingRequestBody.Listener() {
            @Override
            public void onRequestProgress(long bytesWritten, long contentLength) {
                callBack.onUploadedSize((bytesWritten * 100 / contentLength), bytesWritten, contentLength);
            }
        }));
        return requestBuilder.build();
    }

    private Request makeUploadRequest(String key, String url, Map<String, String> map, List<File> files, List<String> fileName, final OnFilesUploadCallBack callBack) {
        Request.Builder requestBuilder = new Request.Builder();
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        for (int i = 0; i < files.size(); i++) {
            File uploadFile = files.get(i);
            builder.addFormDataPart(key, fileName.get(i), RequestBody.create(MediaType.parse(guessMimeType(uploadFile.getPath())), uploadFile));
        }
        if (map != null) {
            for (String keyMap : map.keySet()) {
                builder.addFormDataPart(keyMap, map.get(keyMap));
            }
        }
        requestBuilder.url(url).post(new CountingRequestBody(builder.build(), new CountingRequestBody.Listener() {
            @Override
            public void onRequestProgress(long bytesWritten, long contentLength) {
                callBack.onUploadedSize((bytesWritten * 100 / contentLength), bytesWritten, contentLength);
            }
        }));
        return requestBuilder.build();
    }

    private String guessMimeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(path);
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }

    public void cancel() {
        if (mCurCall != null && mCurCall.isExecuted() && !mCurCall.isCanceled())
            mCurCall.cancel();
    }

    public interface OnFilesUploadCallBack {
        void onUploadedSuccess(String content);

        void onUploadedSize(long position, long bytesWritten, long contentLength);

        void onUploadeError();
    }
}
