package lib.frame.module.downloadnew;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 下载模块-service
 * 
 * @author zhangbp
 * 
 */

public class EasyDownloadService extends Service {

	private EasyExecutorService mEasyExecutorService;
	private Map<String, EasyDownloadTask> mEasyDownloadTasks = Collections.synchronizedMap(new HashMap<String, EasyDownloadTask>());
	private List<EasyDownloadInfo> mDownloadLists = new ArrayList<EasyDownloadInfo>();
	private Map<String, EasyDownloadInfo> mDownloadMaps = new HashMap<String, EasyDownloadInfo>();
	private EasyDownloadDao mEasyDownloadDao;
	private InternalHandler sHandler;
	private DataReceiver mDataReceiver;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		sHandler = new InternalHandler();
		mDataReceiver = new DataReceiver();
		mEasyExecutorService = new EasyExecutorService(3, 3);
		mEasyDownloadDao = new EasyDownloadDao(getApplicationContext());
		registerReceiver(mDataReceiver, new IntentFilter(EasyDownloadManager.BACKGROUND_ACTION));
		getDownloadList();
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		if (mDataReceiver != null) {
			unregisterReceiver(mDataReceiver);
		}
		stopAllTask();
		super.onDestroy();
	}

	private void stopAllTask(){
		
		for(EasyDownloadInfo mEasyDownloadInfo : mDownloadLists){
			if(mEasyDownloadInfo.status == EasyDownloadInfo.State.DOWNLOAD_STATE_DOWNLOADING){
				mEasyDownloadInfo.status = EasyDownloadInfo.State.DOWNLOAD_STATE_STOP;
			}
		}
	}
	
	/**
	 * 添加新的下载和继续下载时调用
	 * 
	 * @param mEasyDownloadInfo
	 */
	private void addTask(String url) {
		EasyDownloadInfo mEasyDownloadInfo = mDownloadMaps.get(url);
		if (mEasyDownloadInfo != null) {
			EasyDownloadTask mEasyDownloadTask = new EasyDownloadTask(getApplicationContext(), mEasyDownloadInfo, mEasyDownloadTasks,this);
			mEasyExecutorService.submit(mEasyDownloadTask);
			mEasyDownloadTasks.put(mEasyDownloadInfo.url, mEasyDownloadTask);
		}
	}

	/**
	 * 返回下载的列表
	 * 
	 * @return
	 */

	public List<EasyDownloadInfo> getDownloadList() {
		// TODO Auto-generated method stub
		if (mDownloadLists.size() == 0) {
			mDownloadLists = mEasyDownloadDao.getDownloadList();
			if (mDownloadLists != null) {
				for (EasyDownloadInfo mEasyDownloadInfo : mDownloadLists) {
					mDownloadMaps.put(mEasyDownloadInfo.url, mEasyDownloadInfo);
				}
			}
		}
		return mDownloadLists;
	}

	/**
	 * 返回下载的Maps
	 * 
	 * @return
	 */
	public Map<String, EasyDownloadInfo> getDownloadMap() {
		return mDownloadMaps;
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
			Bundle data = (Bundle) msg.obj;

			EasyDownloadInfo mEasyDownloadInfo = null;
			String url = data.getString(EasyDownloadManager.DownloadInfoKey.url);
			if (mDownloadMaps.containsKey(url)) {
				mEasyDownloadInfo = mDownloadMaps.get(url);
				mEasyDownloadInfo.status = data.getInt(EasyDownloadManager.DownloadInfoKey.status);
			} else {
				mEasyDownloadInfo = new EasyDownloadInfo();
				mEasyDownloadInfo.url = url;
				mEasyDownloadInfo.fileDir = data.getString(EasyDownloadManager.DownloadInfoKey.fileDir);
				mEasyDownloadInfo.fileName = data.getString(EasyDownloadManager.DownloadInfoKey.fileName);
				mEasyDownloadInfo.description = data.getString(EasyDownloadManager.DownloadInfoKey.description);
				mEasyDownloadInfo.status = EasyDownloadInfo.State.DOWNLOAD_STATE_WAIT;
				mEasyDownloadInfo.createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
				mDownloadMaps.put(url, mEasyDownloadInfo);
				mDownloadLists.add(mEasyDownloadInfo);
				mEasyDownloadDao.addDownload(mEasyDownloadInfo);
			}

			if (mEasyDownloadInfo.status == EasyDownloadInfo.State.DOWNLOAD_STATE_WAIT) {
				addTask(mEasyDownloadInfo.url);
			} else if (mEasyDownloadInfo.status == EasyDownloadInfo.State.DOWNLOAD_STATE_CANCEL) {
				mEasyDownloadDao.delDownload(url);
				String filepath = mEasyDownloadInfo.fileDir + mEasyDownloadInfo.fileName;
				File mfile = new File(filepath);
				if(mfile.exists()){
					mfile.delete();
				}
				mDownloadMaps.remove(url);
				mDownloadLists.remove(mEasyDownloadInfo);
				if(mEasyDownloadTasks.get(url) == null){
					Bundle mBundle = new Bundle();
					mBundle.putString(EasyDownloadManager.DownloadInfoKey.url, mEasyDownloadInfo.url);
					mBundle.putInt(EasyDownloadManager.DownloadInfoKey.status, mEasyDownloadInfo.status);
					sendDataToFront(mBundle);
				}
			}
		}
	}

	/**
	 * 发送数据用到EasyDownloadManager
	 * @param mContext
	 * @param mBundle
	 */
	
	public void sendDataToFront(Bundle mBundle){
		Intent mIntent = new Intent();
		mIntent.setAction(EasyDownloadManager.FRONT_ACTION);
		mIntent.putExtra("data", mBundle);
		sendBroadcast(mIntent);
	}
	
	/**
	 * 接收前台发过来的数据
	 * @author zhangbp
	 */

	class DataReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			Bundle mBundle = intent.getBundleExtra("data");
			Message message = sHandler.obtainMessage(mBundle.getInt(EasyDownloadManager.DownloadInfoKey.status), mBundle);
			message.sendToTarget();
		}
	}

}
