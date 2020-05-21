package lib.frame.view.dlg;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import lib.frame.R;
import lib.frame.base.BaseFrameActivity;
import lib.frame.module.ui.AnnotateUtil;

/**
 * Created by shuaqq on 2017/1/17.
 */

public abstract class DlgFullBase extends Dialog implements View.OnClickListener {

    protected BaseFrameActivity mContext;

    public DlgFullBase(@NonNull Context context) {
        super(context, R.style.AppFullTheme);
        mContext = (BaseFrameActivity) context;
        initDlg();
    }

    protected DlgFullBase(@NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        mContext = (BaseFrameActivity) context;
        initDlg();
    }

    public DlgFullBase(@NonNull Context context, @StyleRes int theme) {
        super(context, theme);
        mContext = (BaseFrameActivity) context;
        initDlg();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initThis();
    }

    protected void initDlg() {

    }

    protected void initThis() {
        Window window = getWindow();
        if (window == null)
            return;
        window.setBackgroundDrawableResource(R.color.transparent);
        WindowManager m = window.getWindowManager();
        Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
        WindowManager.LayoutParams p = getWindow().getAttributes(); //获取对话框当前的参数值
        Point point = new Point();
        d.getSize(point);
        p.height = point.y; //高度设置为屏幕的0.3
        p.width = point.x; //宽度设置为屏幕的0.5
        getWindow().setAttributes(p); //设置生效
        View layout = LayoutInflater.from(mContext).inflate(setLayout(), null);
        if (layout != null) {
            setContentView(layout);
            AnnotateUtil.initBindView(this, layout);
        }
        setCanceledOnTouchOutside(true);
    }

    protected abstract int setLayout();

    @Override
    public void onClick(View v) {

    }
}
