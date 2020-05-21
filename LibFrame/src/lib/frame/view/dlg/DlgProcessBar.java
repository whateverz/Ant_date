package lib.frame.view.dlg;

import android.content.Context;

import androidx.appcompat.app.AppCompatDialog;

import lib.frame.R;
import lib.frame.base.AppBase;


public class DlgProcessBar {

    private Context mContext;
    private AppBase mAppBase;
    private String TAG;
    private AppCompatDialog dlg;

    public boolean isShowing() {
        return dlg != null && dlg.isShowing();
    }

    public void doShow() {
        if (mContext != null) {
            mAppBase = (AppBase) mContext.getApplicationContext();
            TAG = mAppBase.getCurActivity().TAG;
            if (dlg == null) {
                if (mContext != null) {
                    dlg = new AppCompatDialog(mContext, R.style.dialog_pb);
                    dlg.setCanceledOnTouchOutside(true);
                    dlg.setContentView(R.layout.dlg_process_bar);
                }
            }
            if (dlg != null && !dlg.isShowing()) {
                dlg.show();
            }
        }
    }

    public DlgProcessBar(Context context) {
        mContext = context;
    }


    public void doDismiss() {
        if (mContext != null && dlg != null && dlg.isShowing()) {
            dlg.dismiss();
        }
    }
}
