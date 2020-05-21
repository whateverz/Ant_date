package com.ant.antdate.downloader;


import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * Desc:
 * Created by ${junhua.li} on 2016/10/25 13:41.
 * Email: lijunhuayc@sina.com
 */
@Entity
public class ThreadData {

    @Id
    private long id;
    private int threadId;
    private int downloadLength;
    private int fileSize;
    private String url;

    public ThreadData() {

    }

    public ThreadData(int threadId, int fileSize, String url) {
        this.threadId = threadId;
        this.fileSize = fileSize;
        this.url = url;
    }

    public ThreadData(long id, int threadId, int downloadLength, int fileSize,
            String url) {
        this.id = id;
        this.threadId = threadId;
        this.downloadLength = downloadLength;
        this.fileSize = fileSize;
        this.url = url;
    }

    public int getThreadId() {
        return threadId;
    }

    public void setThreadId(int threadId) {
        this.threadId = threadId;
    }

    public int getDownloadLength() {
        return downloadLength;
    }

    public void setDownloadLength(int downloadLength) {
        this.downloadLength = downloadLength;
    }

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    @Override
    public String toString() {
        return "ThreadData{" +
                "threadId=" + threadId +
                ", downloadLength=" + downloadLength +
                ", fileSize=" + fileSize +
                '}';
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
