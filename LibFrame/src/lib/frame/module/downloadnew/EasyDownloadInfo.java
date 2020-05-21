package lib.frame.module.downloadnew;

/**
 * 下载infos
 *
 * @author zhangbp
 */

public class EasyDownloadInfo {

    /**
     * 下载状态
     *
     * @author zhangbp
     */
    public static class State {
        public static final int DOWNLOAD_STATE_WAIT = 0x01;
        public static final int DOWNLOAD_STATE_DOWNLOADING = 0x02;
        public static final int DOWNLOAD_STATE_STOP = 0x03;
        public static final int DOWNLOAD_STATE_FINISH = 0x04;
        public static final int DOWNLOAD_STATE_CANCEL = 0x05;
    }

    /**
     * 下载的地址
     */
    public String url;

    /**
     * 文件保存目录
     */
    public String fileDir;


    /**
     * 文件名
     */
    public String fileName;

    /**
     * 文件总大小(单位字节)
     */
    public long totalSize;
    /**
     * 已下载的大小(单位字节)
     */
    public long completeSize;
    /**
     * 下载描述
     */
    public String description;

    /**
     * 下载状态
     */
    public int status;
    /**
     * 创建下载的时间
     */
    public String createTime;
    /**
     * 下载速度
     */
    public String speed;

}
