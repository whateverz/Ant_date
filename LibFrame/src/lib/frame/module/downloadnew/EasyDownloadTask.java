package lib.frame.module.downloadnew;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.URL;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import lib.frame.utils.SysTools;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 下载task
 *
 * @author zhangbp
 */
public class EasyDownloadTask implements Runnable {

    private final int BUFFER_SIZE = 32 * 1024;
    public EasyDownloadInfo mEasyDownloadInfo;
    public Context mApplicationContext;
    private Map<String, EasyDownloadTask> mEasyDownloadTasks;
    private EasyDownloadDao mEasyDownloadDao;
    private EasyDownloadService mEasyDownloadService;
    private Bundle mBundle;
    private int downloadNetWorkType;
    private long lasttime;
    private long lastCompleteSize;

    public EasyDownloadTask(Context mApplicationContext, EasyDownloadInfo mEasyDownloadInfo, Map<String, EasyDownloadTask> mEasyDownloadTasks, EasyDownloadService mEasyDownloadService) {
        this.mEasyDownloadInfo = mEasyDownloadInfo;
        this.mApplicationContext = mApplicationContext;
        this.mEasyDownloadTasks = mEasyDownloadTasks;
        mEasyDownloadDao = new EasyDownloadDao(mApplicationContext);
        this.mEasyDownloadService = mEasyDownloadService;
        mEasyDownloadInfo.status = EasyDownloadInfo.State.DOWNLOAD_STATE_WAIT;
        mBundle = new Bundle();
        refeshDataToBb();
        lastCompleteSize = mEasyDownloadInfo.completeSize;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        if (mEasyDownloadInfo.status == EasyDownloadInfo.State.DOWNLOAD_STATE_WAIT) {
            try {
//                if (mEasyDownloadInfo.completeSize == 0) {
//                    StatisticsAction.TD_addDownloadTask(mEasyDownloadInfo.description);
//                }
                downloadNetWorkType = getNetWordType(mApplicationContext);
                startDownload();
                downloadEnd();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                if (downloadNetWorkType == getNetWordType(mApplicationContext)) {
                    try {
                        startDownload();
                        downloadEnd();
                    } catch (Exception e1) {
                        // TODO Auto-generated catch block
                        downloadException(e1);
                    }
                } else {
                    downloadException(e);
                }
            }
        }
    }

    /**
     * 下载异常
     *
     * @param e
     * @author zhangbp
     */
    private void downloadException(final Exception e) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                String msg = e.getMessage();
                if (msg != null) {
                    msg = "";
                }
//                Toast.makeText(mApplicationContext, "下载失败，请重试...\n" + msg, Toast.LENGTH_LONG).show();
            }
        });
        downloadEnd();
//        StatisticsAction.TD_onDownloadError(mEasyDownloadInfo.description, e.getMessage());
//        StatisticsAction.TD_ex_send(e);
        e.printStackTrace();
    }

    /**
     * 下载结束
     *
     * @author zhangbp
     */

    private void downloadEnd() {
        if (mEasyDownloadInfo.completeSize >= mEasyDownloadInfo.totalSize && mEasyDownloadInfo.totalSize > 0) {
            mEasyDownloadInfo.status = EasyDownloadInfo.State.DOWNLOAD_STATE_FINISH;
            mEasyDownloadInfo.completeSize = mEasyDownloadInfo.totalSize;
            mApplicationContext.sendBroadcast(new Intent("EasyDownloadService_complete_download").putExtra("url", mEasyDownloadInfo.url).putExtra("filePath", mEasyDownloadInfo.fileDir + mEasyDownloadInfo.fileName));
//            StatisticsAction.TD_onDownloadEnd(mEasyDownloadInfo.description);
        } else if (mEasyDownloadInfo.status != EasyDownloadInfo.State.DOWNLOAD_STATE_CANCEL) {
            mEasyDownloadInfo.status = EasyDownloadInfo.State.DOWNLOAD_STATE_STOP;
        }
        refeshDataToBb();
        mEasyDownloadTasks.remove(mEasyDownloadInfo.url);
        mEasyDownloadDao.closeConnection();
    }

    /**
     * 刷新数据到BD
     *
     * @author zhangbp
     */

    public void refeshDataToBb() {
        if (mEasyDownloadInfo.status != EasyDownloadInfo.State.DOWNLOAD_STATE_CANCEL) {
            mEasyDownloadDao.updateDownload(mEasyDownloadInfo);
        }

        mBundle.putString(EasyDownloadManager.DownloadInfoKey.url, mEasyDownloadInfo.url);
        mBundle.putString(EasyDownloadManager.DownloadInfoKey.fileDir, mEasyDownloadInfo.fileDir);
        mBundle.putString(EasyDownloadManager.DownloadInfoKey.fileName, mEasyDownloadInfo.fileName);
        mBundle.putLong(EasyDownloadManager.DownloadInfoKey.totalSize, mEasyDownloadInfo.totalSize);
        mBundle.putLong(EasyDownloadManager.DownloadInfoKey.completeSize, mEasyDownloadInfo.completeSize);
        mBundle.putString(EasyDownloadManager.DownloadInfoKey.description, mEasyDownloadInfo.description);
        mBundle.putInt(EasyDownloadManager.DownloadInfoKey.status, mEasyDownloadInfo.status);
        mBundle.putString(EasyDownloadManager.DownloadInfoKey.createTime, mEasyDownloadInfo.createTime);
        mBundle.putString(EasyDownloadManager.DownloadInfoKey.speed, mEasyDownloadInfo.speed);
        mEasyDownloadService.sendDataToFront(mBundle);
    }

    /**
     * 开始下载
     *
     * @throws IOException
     * @author zhangbp
     */

    private void startDownload() throws IOException {
        URL mURL = new URL(Uri.encode(mEasyDownloadInfo.url, "@#&=*+-_.,:!?()/~'%"));
        Request.Builder builder = new Request.Builder();
        builder.url(mURL);
        builder.addHeader("Range", "bytes=" + mEasyDownloadInfo.completeSize + "-");
        builder.addHeader("X-Client-Event", getClientDataJSONObject(mApplicationContext).toString());
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS).build();
        Call call = okHttpClient.newCall(builder.build());
        Response response = call.execute();
        if (mEasyDownloadInfo.totalSize <= 0) {
            mEasyDownloadInfo.totalSize = response.body().contentLength();
        }
        FlushedInputStream is = new FlushedInputStream(response.body().byteStream(), BUFFER_SIZE);
        String url = response.request().url().toString();

//        HttpURLConnection conn = (HttpURLConnection) mURL.openConnection();
//        conn.setRequestProperty("Range", "bytes=" + mEasyDownloadInfo.completeSize + "-");
//        conn.setConnectTimeout(5 * 1000);
//        conn.setReadTimeout(5 * 1000);
//        conn.addRequestProperty("X-Client-Event", BaseAction.getClientDataJSONObject(MarketApplication.getInstance().getApplicationContext()).toString());
//        if (mEasyDownloadInfo.totalSize <= 0) {
//            mEasyDownloadInfo.totalSize = conn.getContentLength();
//        }
//        FlushedInputStream is = new FlushedInputStream(conn.getInputStream(), BUFFER_SIZE);
//        String url = conn.getURL().toString();
        String suffix = SysTools.getSuffix(url);
        mEasyDownloadInfo.fileName = suffix + "_" + SysTools.md5(url) + "." + suffix;
        saveFile(is);
    }

    /**
     * 保存文件
     *
     * @param is
     * @throws IOException
     */

    public void saveFile(InputStream is) throws IOException {
        String filePath = mEasyDownloadInfo.fileDir + mEasyDownloadInfo.fileName;
        File mFile = new File(filePath);
        File parent = mFile.getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
        }
        if (mEasyDownloadInfo.completeSize == 0 && mFile.exists()) {
            mFile.delete();
        }
        RandomAccessFile mRandomAccessFile = new RandomAccessFile(filePath, "rw");
        mRandomAccessFile.seek(mEasyDownloadInfo.completeSize);

        mEasyDownloadInfo.status = EasyDownloadInfo.State.DOWNLOAD_STATE_DOWNLOADING;

        refeshDataToBb();

        byte[] bytes = new byte[BUFFER_SIZE];

        while (true) {

            int count = is.read(bytes);

            if (count == -1 || mEasyDownloadInfo.status == EasyDownloadInfo.State.DOWNLOAD_STATE_STOP || mEasyDownloadInfo.status == EasyDownloadInfo.State.DOWNLOAD_STATE_FINISH || mEasyDownloadInfo.status == EasyDownloadInfo.State.DOWNLOAD_STATE_CANCEL || mEasyDownloadInfo.completeSize >= mEasyDownloadInfo.totalSize) {
                break;
            }

            if (mEasyDownloadInfo.status == EasyDownloadInfo.State.DOWNLOAD_STATE_DOWNLOADING) {
                mRandomAccessFile.write(bytes, 0, count);
                mEasyDownloadInfo.completeSize += count;
                long currentTime = System.currentTimeMillis();
                if (currentTime - lasttime > 1000) {
                    refeshDataToBb();
                    lasttime = currentTime;
                    mEasyDownloadInfo.speed = SysTools.getSuitableSize(mEasyDownloadInfo.completeSize - lastCompleteSize) + "/S";
                    lastCompleteSize = mEasyDownloadInfo.completeSize;
                }
            }
        }
    }

    /**
     * ConnectivityManager.TYPE_WIFI 网络的类型
     *
     * @param mContext
     * @return
     */
    private int getNetWordType(Context mContext) {
        ConnectivityManager connectMgr = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectMgr.getActiveNetworkInfo();
        if (info != null) {
            return info.getType();
        }
        return -1;
    }

    public static JSONObject getClientDataJSONObject(Context context) {

        TelephonyManager tm = (TelephonyManager) (context.getSystemService(Context.TELEPHONY_SERVICE));
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displaysMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(displaysMetrics);
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        JSONObject clientData = new JSONObject();
        try {

            clientData.put("os_version", SysTools.getOsVersion());
            clientData.put("platform", "android");
            clientData.put("language", Locale.getDefault().getLanguage());

            clientData.put("imei", tm.getDeviceId() == null ? 0 : tm.getDeviceId());//
            clientData.put("manufacturer", Build.MANUFACTURER);
            clientData.put("module_name", Build.PRODUCT);
            clientData.put("model_name", Build.MODEL);
            clientData.put("device_name", SysTools.getDeviceName());

            clientData.put("resolution", displaysMetrics.widthPixels + "x" + displaysMetrics.heightPixels);
            clientData.put("is_mobiledevice", true);
            clientData.put("phone_type", tm.getPhoneType());//
            clientData.put("imsi", tm.getSubscriberId());
            clientData.put("network", SysTools.getNetworkTypeWIFI2G3G(context));
            clientData.put("version_name", SysTools.getVersion(context));
            clientData.put("version_code", SysTools.getVersionCode(context));
            clientData.put("package_name", SysTools.getPackageName(context));
            clientData.put("cell", SysTools.getCellInfo(context));

            clientData.put("wifi_mac", wifiManager.getConnectionInfo().getMacAddress());
            clientData.put("have_bt", adapter != null);
            clientData.put("have_wifi", SysTools.isWiFiActive(context));
            clientData.put("have_gps", locationManager != null);
            clientData.put("have_gravity", SysTools.isHaveGravity(context));
            clientData.put("channel_name", SysTools.getMetaData(context, "channel_name"));
        } catch (Exception e) {
            e.printStackTrace();
            //e.printStackTrace();
        }
        return clientData;
    }
}
