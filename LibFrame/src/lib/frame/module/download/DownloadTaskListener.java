package lib.frame.module.download;

public interface DownloadTaskListener {
    void updateProcess(DownloadTask mgr); // 更新进度

    void finishDownload(DownloadTask mgr); // 完成下载

    void preDownload(); // 准备下载

    void errorDownload(DownloadTask mgr, int error); // 下载错误
}
