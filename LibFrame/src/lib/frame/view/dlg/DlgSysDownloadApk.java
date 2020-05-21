package lib.frame.view.dlg;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.NonNull;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.io.File;

import lib.frame.R;
import lib.frame.base.AppBase;
import lib.frame.module.download.DownloadTask;
import lib.frame.module.download.DownloadTaskListener;
import lib.frame.module.download.LogicDownload;
import lib.frame.utils.FileUtils;

public class DlgSysDownloadApk {

    private Context mContext;
    private AppBase mAppBase;

    private DlgDefault downloadDialog;

    private ProgressBar vProgressBar;
    private TextView vTextView;

    private LogicDownload logicDownload;
    private OnDlgSysDownloadApkListener mListener;

    private String p;// 路径
    private String f;// 文件名
    private Object passObj;

    public boolean isFinish;

    public boolean isFinish() {
        return isFinish;
    }

    public DlgSysDownloadApk(Context context) {
        super();
        this.mContext = context;
        mAppBase = (AppBase) mContext.getApplicationContext();
        initDlg(context);
    }

    public void doShow(String title) {
//        vProgressBar.setProgress(0);
//        vTextView.setText("");
        downloadDialog.setTitle(title);
        new Handler(mContext.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                downloadDialog.show();
            }
        }.sendEmptyMessage(0);
    }

    public void doDismiss() {
        if (downloadDialog.isShowing()) {
            new Handler(mContext.getMainLooper()) {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    downloadDialog.dismiss();
                }
            }.sendEmptyMessage(0);
        }
    }

    public void startDownload(String title, String url, final String path, final String fileName) {
        startDownload(title, url, path, fileName, null);
    }

    public void startDownload(String title, String url, final String path, final String fileName,
                              final Object passObj) {
        this.p = path;
        this.f = fileName;
        this.passObj = passObj;
        isFinish = false;
        logicDownload = LogicDownload.getInstance(mAppBase);
        logicDownload.setDownloadTaskListener(new DownloadTaskListener() {

            @Override
            public void updateProcess(DownloadTask mgr) {
//                vProgressBar.setProgress((int) mgr.getDownloadPercent());
                downloadDialog.setProgressNumberFormat("%1d k/%2d k");
                downloadDialog.setMaxProgress(byteToKB(mgr.getTotalSize()));
                downloadDialog.setProgress(byteToKB(mgr.getDownloadSize()));
//                vTextView.setText(Utils.size(mgr.getDownloadSize()) + "/"
//                        + Utils.size(mgr.getTotalSize()));
//                if (downloadDialog.getCurrentProgress() == 100) {
//                    isFinish = true;
////                    vProgressBar.setProgress(0);
////                    vTextView.setText("");
//                    doDismiss();
//                }
            }

            @Override
            public void preDownload() {

            }

            @Override
            public void finishDownload(DownloadTask mgr) {
                isFinish = true;
                if (f.endsWith("apk")) {
                    installApk(p + "/" + f);
                }
                if (mListener != null) {
                    mListener.onDownloadFinish(mgr, passObj);
                }
                doDismiss();
            }

            @Override
            public void errorDownload(DownloadTask mgr, int error) {
                FileUtils.delFile(fileName);
                if (mListener != null) {
                    mListener.onDownloadErr(mgr, passObj);
                }
            }
        });
        logicDownload.startDownload(url, path, fileName);
        doShow(title);
    }

    private void installApk(String apkFilePath) {
        File apkfile = new File(apkFilePath);
        if (!apkfile.exists()) {
            return;
        }
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
                "application/vnd.android.package-archive");
        mContext.startActivity(i);
    }

    public void initDlg(Context context) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(context);
//        View v = inflater.inflate(R.layout.update_progress, null);
//        vProgressBar = (ProgressBar) v.findViewById(R.id.update_progress);
//        vTextView = (TextView) v.findViewById(R.id.update_progress_text);
        builder.negativeText(mContext.getString(R.string.cancel)).onNegative(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                materialDialog.dismiss();
            }
        }).progress(false, 100, true).dismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (!isFinish) {
                    logicDownload.stopCurDownload();
                }
            }
        }).title("").content("");
//        builder.setView(v);
//        builder.setNegativeButton("取消", new OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//            }
//        });
//        builder.setOnCancelListener(new OnCancelListener() {
//            @Override
//            public void onCancel(DialogInterface dialog) {
//                dialog.dismiss();
//            }
//        });
        downloadDialog = new DlgDefault(builder);
        downloadDialog.setCanceledOnTouchOutside(false);
    }

    public interface OnDlgSysDownloadApkListener {
        void onDownloadFinish(DownloadTask mgr, Object passObj);

        void onDownloadErr(DownloadTask mgr, Object passObj);
    }

    public void setOnDlgSysDownloadApkListener(OnDlgSysDownloadApkListener l) {
        mListener = l;
    }

    private int byteToKB(long bt) {
        return Math.round(bt / 1024);
    }
}
