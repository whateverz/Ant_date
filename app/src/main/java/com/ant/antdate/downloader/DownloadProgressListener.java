package com.ant.antdate.downloader;

/**
 * Desc:
 * Created by ${junhua.li} on 2016/08/25 17:38.
 * Email: lijunhuayc@sina.com
 */
public interface DownloadProgressListener {
    void onDownloadTotalSize(int totalSize);

    /**
     * Real-time update downloading progress
     *
     * @param size    downloading progress(Byte)
     * @param percent downloading percent(%)
     * @param speed   downloading speed(KB/S)
     */
    void updateDownloadProgress(int size, float percent, float speed);

    void onDownloadSuccess(String apkPath);

    void onDownloadFailed();

    void onPauseDownload();

    void onStopDownload();
}
