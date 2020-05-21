package lib.frame.module.download;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.StatFs;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.MalformedURLException;
import java.net.URL;

import lib.frame.base.AppBase;
import lib.frame.module.http.TwitterRestClient;
import lib.frame.utils.Log;
import okhttp3.Call;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownloadTask extends AsyncTask<Void, Integer, Long> {

    private AppBase mAppbase;

    public final static int ERROR_NONE = 0;
    public final static int ERROR_SD_NO_MEMORY = 1;
    public final static int ERROR_BLOCK_INTERNET = 2;
    public final static int ERROR_UNKONW = 3;
    public final static int TIME_OUT = 30000;
    private final static int BUFFER_SIZE = 1024 * 8;

    private URL URL;
    private File file;
    private String url;
    private Throwable exception;
    private RandomAccessFile outputStream;
    private DownloadTaskListener listener;

    private Context context;
    private OkHttpClient mOkHttpClient;
    private Call downLoadCall;

    private long downloadSize;
    private long previousFileSize;
    private long totalSize;
    private long downloadPercent;
    private long networkSpeed; // 网速
    private long previousTime;
    private long totalTime;
    private int errStausCode = ERROR_NONE;
    private boolean interrupt = false;

    private final class ProgressReportingRandomAccessFile extends
            RandomAccessFile {
        private int progress = 0;

        public ProgressReportingRandomAccessFile(File file, String mode)
                throws FileNotFoundException {
            super(file, mode);
        }

        @Override
        public void write(byte[] buffer, int offset, int count)
                throws IOException {
            super.write(buffer, offset, count);
            progress += count;
            publishProgress(progress);
        }
    }

    public DownloadTask(AppBase context, String url, String path,
                        String fileName) throws MalformedURLException {
        this(context, url, path, fileName, null);
        mAppbase = context;
    }

    public DownloadTask(AppBase context, String url, String path,
                        String fileName, DownloadTaskListener listener)
            throws MalformedURLException {
        super();
        mAppbase = context;
        this.url = url;
        this.URL = new URL(url);
        this.listener = listener;
        this.file = new File(path, fileName);
        this.context = context;
        this.mOkHttpClient = TwitterRestClient.getInstance().getOkHttpClientClone();
    }

    public File getFile() {
        return file;
    }

    public String getUrl() {
        return url;
    }

    public long getDownloadPercent() {
        return downloadPercent;
    }

    public long getDownloadSize() {
        return downloadSize + previousFileSize;
    }

    public long getTotalSize() {
        return totalSize;
    }

    public long getDownloadSpeed() {
        return this.networkSpeed;
    }

    public long getTotalTime() {
        return this.totalTime;
    }

    @Override
    protected void onPreExecute() {
        previousTime = System.currentTimeMillis();
        if (listener != null)
            listener.preDownload();
    }

    @Override
    protected Long doInBackground(Void... params) {
        try {
            return download();
        } catch (Exception e) {
            if (downLoadCall != null) {
                downLoadCall.cancel();
            }
            exception = e;
            errStausCode = ERROR_UNKONW;
            return null;
        }
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        if (progress.length > 1) {
            totalSize = progress[1];
            if (totalSize == -1) {
                if (listener != null)
                    listener.errorDownload(this, ERROR_UNKONW);
            } else {

            }
        } else {
            totalTime = System.currentTimeMillis() - previousTime;
            downloadSize = progress[0];
            downloadPercent = (downloadSize + previousFileSize) * 100
                    / totalSize;
            networkSpeed = downloadSize / totalTime;
            if (listener != null) {
                listener.updateProcess(DownloadTask.this);
            }
        }
    }

    @Override
    protected void onPostExecute(Long result) {
        if (interrupt) {
            if (errStausCode != ERROR_NONE) {
                if (listener != null)
                    listener.errorDownload(this, errStausCode);
            }
        } else if (totalSize != file.length()) {
            if (errStausCode != ERROR_NONE) {
                if (listener != null)
                    listener.errorDownload(this, errStausCode);
            }
        } else {
            if (exception != null) {
                Log.v(null, "Download failed.", exception);
            }
            if (listener != null) {
                listener.finishDownload(this);
            }
        }
        LogicDownload.getInstance(mAppbase).getTaskMap().remove(url);
    }

    @Override
    public void onCancelled() {
        super.onCancelled();
        interrupt = true;
    }

    private long download() throws Exception {
        Log.v(null, "totalSize: " + totalSize);

        Request.Builder builder = new Request.Builder();
        builder.url(url);
        downLoadCall = mOkHttpClient.newCall(builder.build());
        Response response = downLoadCall.execute();
        totalSize = response.body().contentLength();

        if (file.length() > 0 && totalSize > 0 && totalSize > file.length()) {
            Headers.Builder headerBuilder = new Headers.Builder();
            headerBuilder.add("Range", "bytes=" + file.length() + "-");
            previousFileSize = file.length();

            downLoadCall.cancel();

            builder.headers(headerBuilder.build());

            downLoadCall = mOkHttpClient.newCall(builder.build());
            response = downLoadCall.execute();
            Log.v(null, "File is not complete, download now.");
            Log.v(null, "File length:" + file.length() + " totalSize:"
                    + totalSize);
        } else if (file.exists() && totalSize == file.length()) {
            Log.v(null, "Output file already exists. Skipping download.");
            return 0L;
        }

        long storage = getAvailableStorage();
        if (totalSize - file.length() > storage) {
            errStausCode = ERROR_SD_NO_MEMORY;
            interrupt = true;
            downLoadCall.cancel();
            return 0L;
        }

        try {
            outputStream = new ProgressReportingRandomAccessFile(file, "rw");
        } catch (FileNotFoundException e) {
            Log.v(null, "OutputStream Error");
        }

        publishProgress(0, (int) totalSize);

        InputStream input;
        input = response.body().byteStream();

        int bytesCopied = copy(input, outputStream);

        if ((previousFileSize + bytesCopied) != totalSize && totalSize != -1
                && !interrupt) {
            throw new IOException("Download incomplete: " + bytesCopied
                    + " != " + totalSize);
        }

        outputStream.close();
        downLoadCall.cancel();
        downLoadCall = null;
        Log.v(null, "Download completed successfully.");
        return bytesCopied;
    }

    public void stop() {
        if (downLoadCall != null && outputStream != null) {
            try {
                outputStream.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            downLoadCall.cancel();
            downLoadCall = null;
        }
    }

    public int copy(InputStream input, RandomAccessFile out) throws Exception {
        byte[] buffer = new byte[BUFFER_SIZE];

        BufferedInputStream in = new BufferedInputStream(input, BUFFER_SIZE);
        Log.v(null, "length" + out.length());
        out.seek(out.length());

        int count = 0, n = 0;
        long errorBlockTimePreviousTime = -1, expireTime = 0;
        try {
            while (!interrupt) {
                n = in.read(buffer, 0, BUFFER_SIZE);
                if (n == -1) {
                    break;
                }

                out.write(buffer, 0, n);

                count += n;
                if (!isOnline()) {
                    interrupt = true;
                    errStausCode = ERROR_BLOCK_INTERNET;
                    break;
                }
                if (networkSpeed == 0) {
                    if (errorBlockTimePreviousTime > 0) {
                        expireTime = System.currentTimeMillis()
                                - errorBlockTimePreviousTime;
                        if (expireTime > TIME_OUT) {
                            errStausCode = ERROR_BLOCK_INTERNET;
                            interrupt = true;
                        }
                    } else {
                        errorBlockTimePreviousTime = System.currentTimeMillis();
                    }
                } else {
                    expireTime = 0;
                    errorBlockTimePreviousTime = -1;
                }
            }
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                errStausCode = ERROR_UNKONW;
                Log.e(null, e.getMessage(), e);
            }
            try {
                in.close();
            } catch (IOException e) {
                errStausCode = ERROR_UNKONW;
                Log.e(null, e.getMessage(), e);
            }
        }
        return count;
    }

    /*
     * 获取 SD 卡内存
     */
    public static long getAvailableStorage() {
        String storageDirectory = null;
        storageDirectory = Environment.getExternalStorageDirectory().toString();

        Log.v(null, "getAvailableStorage. storageDirectory : "
                + storageDirectory);

        try {
            StatFs stat = new StatFs(storageDirectory);
            long avaliableSize = ((long) stat.getAvailableBlocks() * (long) stat
                    .getBlockSize());
            Log.v(null, "getAvailableStorage. avaliableSize : " + avaliableSize);
            return avaliableSize;
        } catch (RuntimeException ex) {
            Log.e(null, "getAvailableStorage - exception. return 0");
            return 0;
        }
    }

    private boolean isOnline() {
        try {
            ConnectivityManager cm = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo ni = cm.getActiveNetworkInfo();
            return ni != null && ni.isConnectedOrConnecting();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
