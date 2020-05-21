package lib.frame.module.downloadnew;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

/**
 * 下载管理
 * 
 * @author zhangbp
 * 
 */
public class EasyDownloadManager extends Observable {

	private Context mApplicationContext;
	private static EasyDownloadManager mDownloadManager;
	public static final String BACKGROUND_ACTION = "DOWNLOAD_BACKGROUND_ACTION";
	public static final String FRONT_ACTION = "FRONT_BACKGROUND_ACTION";

	public static class DownloadInfoKey {
		public static String url = "url";
		public static String fileDir = "fileDir";
		public static String fileName = "fileName";
		public static String totalSize = "totalSize";
		public static String completeSize = "completeSize";
		public static String description = "description";
		public static String status = "status";
		public static String createTime = "createTime";
		public static String speed = "speed";
	}

	private InternalHandler sHandler;
	private DataReceiver mDataReceiver;
	private List<EasyDownloadInfo> mDownloadLists = null;
	private Map<String, EasyDownloadInfo> mDownloadMaps = new HashMap<String, EasyDownloadInfo>();

	/**
	 * 返回mEasyDownloadManager
	 * 
	 * @return
	 * 
	 */

	public static synchronized EasyDownloadManager getInstance() {
		if (mDownloadManager == null) {
			mDownloadManager = new EasyDownloadManager();
		}
		return mDownloadManager;
	}

	/**
	 * 启动SerVice
	 * 
	 * @author zhangbp
	 */

	private void startSerVice() {
		Intent mIntent = new Intent(mApplicationContext, EasyDownloadService.class);
		mApplicationContext.getApplicationContext().startService(mIntent);
	}


	public void create(Context mContext) {
		mApplicationContext = mContext.getApplicationContext();
		sHandler = new InternalHandler();
		mDataReceiver = new DataReceiver();
		mApplicationContext.registerReceiver(mDataReceiver, new IntentFilter(FRONT_ACTION));
		amendmentData();
		startSerVice();
		getDownloadList();
	}

	public void destroy() {
		if (mApplicationContext != null) {
			mApplicationContext.stopService(new Intent(mApplicationContext, EasyDownloadService.class));
		}
		mDownloadManager = null;
	}

	/**
	 * 添加新的下载任务
	 * 
	 * @param url
	 * @param saveDir
	 * @param description
	 */
	public void addNewDownload(String url, String saveDir, String description) {
		Bundle mBundle = new Bundle();
		mBundle.putString(EasyDownloadManager.DownloadInfoKey.url, url);
		mBundle.putString(EasyDownloadManager.DownloadInfoKey.fileDir, saveDir);
		mBundle.putString(EasyDownloadManager.DownloadInfoKey.description, description);
		mBundle.putInt(EasyDownloadManager.DownloadInfoKey.status, EasyDownloadInfo.State.DOWNLOAD_STATE_WAIT);
		sendDataToBackground(mBundle);
	}

	/**
	 * 开始下载
	 *
	 * @param mEasyDownloadInfo
	 */

	public void startDownload(String... downloadUrls) {
		if (downloadUrls != null) {
			for (String url : downloadUrls) {
				Bundle mBundle = new Bundle();
				mBundle.putInt(EasyDownloadManager.DownloadInfoKey.status, EasyDownloadInfo.State.DOWNLOAD_STATE_WAIT);
				mBundle.putString(EasyDownloadManager.DownloadInfoKey.url, url);
				sendDataToBackground(mBundle);
			}
		}
	}

	/**
	 * 暂停下载
	 *
	 * @param downloadUrl
	 */

	public void pauseDownload(String... downloadUrls) {

		if (downloadUrls != null) {
			for (String url : downloadUrls) {
				Bundle mBundle = new Bundle();
				mBundle.putInt(EasyDownloadManager.DownloadInfoKey.status, EasyDownloadInfo.State.DOWNLOAD_STATE_STOP);
				mBundle.putString(EasyDownloadManager.DownloadInfoKey.url, url);
				sendDataToBackground(mBundle);
			}
		}
	}

	/**
	 * 取消下载
	 *
	 * @param downloadUrl
	 */

	public void cancelDownloadUrl(String... downloadUrls) {

		if (downloadUrls != null) {
			for (String url : downloadUrls) {
				Bundle mBundle = new Bundle();
				mBundle.putInt(EasyDownloadManager.DownloadInfoKey.status, EasyDownloadInfo.State.DOWNLOAD_STATE_CANCEL);
				mBundle.putString(EasyDownloadManager.DownloadInfoKey.url, url);
				sendDataToBackground(mBundle);
			}
		}
	}

	/**
	 * 修正数据
	 * @author zhangbp
	 */

	private void amendmentData(){
		EasyDownloadDao mEasyDownloadDao = new EasyDownloadDao(mApplicationContext);
		mEasyDownloadDao.amendmentData();
		mEasyDownloadDao.closeConnection();
	}

	/**
	 * 读取所有的下载信息
	 *
	 * @return
	 * @author zhangbp
	 *
	 */

	public List<EasyDownloadInfo> getDownloadList() {

		if (mDownloadLists == null || mDownloadLists.size() == 0) {
			mDownloadLists = new ArrayList<EasyDownloadInfo>();
			EasyDownloadDao mEasyDownloadDao =  new EasyDownloadDao(mApplicationContext);
			mDownloadLists = mEasyDownloadDao.getDownloadList();
			if (mDownloadLists != null) {
				for (EasyDownloadInfo mEasyDownloadInfo : mDownloadLists) {
					mDownloadMaps.put(mEasyDownloadInfo.url, mEasyDownloadInfo);
				}
			}
			mEasyDownloadDao.closeConnection();
		}

		return mDownloadLists;
	}

	/**
	 * 读取所有的下载信息
	 *
	 * @return
	 * @author zhangbp
	 *
	 */

	public Map<String, EasyDownloadInfo> getDownloadMap() {
		return mDownloadMaps;
	}

	/**
	 * 数据发生改变
	 *
	 * @param EasyDownloadInfo
	 * @author zhangbp
	 */

	private void onChange(EasyDownloadInfo mEasyDownloadInfo) {
		setChanged();
		notifyObservers(mEasyDownloadInfo);
	}

	/**
	 * 发送数据用下载sercvice
	 *
	 * @param mContext
	 * @param mBundle
	 */

	public void sendDataToBackground(Bundle mBundle) {
		Intent mIntent = new Intent();
		mIntent.setAction(BACKGROUND_ACTION);
		mIntent.putExtra("data", mBundle);
		mApplicationContext.sendBroadcast(mIntent);
	}

	/**
	 * 处理BroadcastReceiver数据
	 *
	 * @author zhangbp
	 *
	 */
	private class InternalHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			Bundle mBundle = (Bundle) msg.obj;
			EasyDownloadInfo mEasyDownloadInfo = null;
			String url = mBundle.getString(EasyDownloadManager.DownloadInfoKey.url);
			if (mDownloadMaps.containsKey(url)) {
				mEasyDownloadInfo = mDownloadMaps.get(url);
			} else {
				mEasyDownloadInfo = new EasyDownloadInfo();
				mDownloadMaps.put(url, mEasyDownloadInfo);
				mDownloadLists.add(mEasyDownloadInfo);
			}
			mEasyDownloadInfo.url = mBundle.getString(EasyDownloadManager.DownloadInfoKey.url);
			mEasyDownloadInfo.fileDir = mBundle.getString(EasyDownloadManager.DownloadInfoKey.fileDir);
			mEasyDownloadInfo.fileName = mBundle.getString(EasyDownloadManager.DownloadInfoKey.fileName);
			mEasyDownloadInfo.totalSize = mBundle.getLong(EasyDownloadManager.DownloadInfoKey.totalSize);
			mEasyDownloadInfo.completeSize = mBundle.getLong(EasyDownloadManager.DownloadInfoKey.completeSize);
			mEasyDownloadInfo.description = mBundle.getString(EasyDownloadManager.DownloadInfoKey.description);
			mEasyDownloadInfo.status = mBundle.getInt(EasyDownloadManager.DownloadInfoKey.status);
			mEasyDownloadInfo.createTime = mBundle.getString(EasyDownloadManager.DownloadInfoKey.createTime);
			mEasyDownloadInfo.speed = mBundle.getString(EasyDownloadManager.DownloadInfoKey.speed);
			if (mEasyDownloadInfo.status == EasyDownloadInfo.State.DOWNLOAD_STATE_CANCEL) {
				mDownloadMaps.remove(mEasyDownloadInfo.url);
				mDownloadLists.remove(mEasyDownloadInfo);
			}
			onChange(mEasyDownloadInfo);
		}
	}

	/**
	 * 接收后台发过来的数据
	 * 
	 * @author zhangbp
	 */

	class DataReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			Bundle mBundle = intent.getBundleExtra("data");
			if (mBundle != null) {
				Message message = sHandler.obtainMessage(0, mBundle);
				message.sendToTarget();
			}
		}
	}

}
