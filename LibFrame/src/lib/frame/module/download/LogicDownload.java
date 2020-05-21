package lib.frame.module.download;

import android.os.AsyncTask;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lib.frame.base.AppBase;
import lib.frame.utils.Log;
import lib.frame.utils.UIHelper;

public class LogicDownload {

    private static final String TAG = "lwxkey";
    private AppBase mAppBase;
    private static LogicDownload logicDownload;
    private List<DownloadTask> mTasks = new ArrayList<>();
    private DownloadTaskListener downloadTaskListener;

    private Map<String, DownloadTask> taskMap = new HashMap<>();

    public Map<String, DownloadTask> getTaskMap() {
        return taskMap;
    }

    private LogicDownload(AppBase appBase) {
        this.mAppBase = appBase;

    }

    public static LogicDownload getInstance(AppBase context) {
        if (logicDownload == null) {
            logicDownload = new LogicDownload(context);
        }
        return logicDownload;
    }

    /**
     * @param url      下载文件的链接
     * @param path     本地
     * @param fileName 文件名
     */
    public void startDownload(String url, String path, String fileName) {
        startDownload(url, path, fileName, downloadTaskListener);
    }

    public void startDownload(String url, String path, String fileName, DownloadTaskListener downloadTaskListener) {
        if (!Utils.isSDCardPresent()) {
            UIHelper.ToastMessage(mAppBase, "未发现SD卡");
            return;
        }

        if (!Utils.isSdCardWrittenable()) {
            UIHelper.ToastMessage(mAppBase, "SD卡不能读写");
            return;
        }

        // if (downloadTaskListener == null) {
        // UIHelper.ToastMessage(mContext, "未设置下载监听器");
        // return;
        // }

        File file = new File(path + "/" + fileName);
        if (file.exists())
            file.delete();
        try {
            DownloadTask downloadTask = new DownloadTask(mAppBase, url, path, fileName,
                    downloadTaskListener);
            mTasks.add(downloadTask);
            downloadTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            taskMap.put(url, downloadTask);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.i(TAG, "下载错误 -- " + e.toString());
        }
    }

    public void stopDownload(String url) {
        DownloadTask downloadTask = taskMap.get(url);
        if (downloadTask != null) {
            downloadTask.stop();
            mTasks.remove(downloadTask);
            taskMap.remove(url);
        }
    }

    public void stopCurDownload() {
        File file = new File(mTasks.get(0).getFile().getPath());
        if (file.exists())
            file.delete();
        mTasks.get(0).stop();
        mTasks.remove(0);
    }

    public void setDownloadTaskListener(
            DownloadTaskListener downloadTaskListener) {
        this.downloadTaskListener = downloadTaskListener;
    }

}
