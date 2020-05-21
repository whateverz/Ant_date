package lib.frame.base;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import lib.frame.R;
import lib.frame.utils.UIHelper;


public class FmWebView extends BaseFrameFragment {

    public interface OnReceiveTitleListener {
        public void receiveTitle(String title);
    }

    private Activity mActivity;

    private FrameLayout vPbbody;
    private TextView vPb;

    private FrameLayout vBody;// 全屏时视频加载view
    private WebView vWebView;
    private View xCustomView;
    private LinearLayout vErrPage;
    private TextView vErrMsg;

    private OnReceiveTitleListener mTitleReceiver;

    private xWebChromeClient xwebchromeclient;
    private WebChromeClient.CustomViewCallback xCustomViewCallback;

    private String curUrl = "";// 当前访问的链接

    public void setCurUrl(String curUrl) {
        this.curUrl = curUrl;
    }

    public String getCurUrl() {
        return curUrl;
    }

    public void setOnTitleReceiver(OnReceiveTitleListener mTitleReceiver) {
        this.mTitleReceiver = mTitleReceiver;
    }

    public LinearLayout getErrPage() {
        return vErrPage;
    }

    public TextView getErrMsg() {
        return vErrMsg;
    }

    public TextView getPb() {
        return vPb;
    }

    public FrameLayout getPbbody() {
        return vPbbody;
    }

    public WebView getWebView() {
        return vWebView;
    }

    protected void initwidget() {
        // TODO Auto-generated method stub
        WebSettings ws = vWebView.getSettings();
        /**
         * setAllowFileAccess 启用或禁止WebView访问文件数据 setBlockNetworkImage 是否显示网络图像
         * setBuiltInZoomControls 设置是否支持缩放 setCacheMode 设置缓冲的模式
         * setDefaultFontSize 设置默认的字体大小 setDefaultTextEncodingName 设置在解码时使用的默认编码
         * setFixedFontFamily 设置固定使用的字体 setJavaSciptEnabled 设置是否支持Javascript
         * setLayoutAlgorithm 设置布局方式 setLightTouchEnabled 设置用鼠标激活被选项
         * setSupportZoom 设置是否支持变焦
         * */
        ws.setAppCacheEnabled(true);
        ws.setBuiltInZoomControls(true);// 隐藏缩放按钮
        ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);// 排版适应屏幕
        ws.setUseWideViewPort(true);// 可任意比例缩放
        ws.setLoadWithOverviewMode(true);// setUseWideViewPort方法设置webview推荐使用的窗口。setLoadWithOverviewMode方法是设置webview加载的页面的模式。
        ws.setSavePassword(true);
        ws.setSaveFormData(true);// 保存表单数据
        ws.setJavaScriptEnabled(true);
        ws.setGeolocationEnabled(true);// 启用地理定位
        ws.setGeolocationDatabasePath("/data/data/org.itri.html5webview/databases/");// 设置定位的数据库路径
        ws.setDomStorageEnabled(true);
        ws.setCacheMode(WebSettings.LOAD_DEFAULT);

        xwebchromeclient = new xWebChromeClient();
        vWebView.setWebChromeClient(xwebchromeclient);
//        vWebView.addJavascriptInterface(new JavaScriptinterface(mContext),
//                "android");
    }

    public class JavaScriptinterface {

        private Context mContext;

        public JavaScriptinterface(Context c) {
            mContext = c;
        }

        public void showToast(String toast) {
            Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 判断是否是全屏
     *
     * @return
     */
    public boolean inCustomView() {
        return (xCustomView != null);
    }

    /**
     * 全屏时按返加键执行退出全屏方法
     */
    public void hideCustomView() {
        xwebchromeclient.onHideCustomView();
    }

    private void setFullScreen() {
        mActivity.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void quitFullScreen() {
        final WindowManager.LayoutParams attrs = mActivity.getWindow()
                .getAttributes();
        attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mActivity.getWindow().setAttributes(attrs);
        mActivity.getWindow().clearFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    /**
     * 处理Javascript的对话框、网站图标、网站标题以及网页加载进度等
     *
     * @author
     */
    public class xWebChromeClient extends WebChromeClient {
        private Bitmap xdefaltvideo;
        private View xprogressvideo;

        @Override
        // 播放网络视频时全屏会被调用的方法
        public void onShowCustomView(View view,
                                     CustomViewCallback callback) {
            mActivity
                    .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            vWebView.setVisibility(View.GONE);
            // 如果一个视图已经存在，那么立刻终止并新建一个
            if (xCustomView != null) {
                callback.onCustomViewHidden();
                return;
            }
            vBody.addView(view);
            xCustomView = view;
            xCustomViewCallback = callback;
            vBody.setVisibility(View.VISIBLE);
        }

        @Override
        // 视频播放退出全屏会被调用的
        public void onHideCustomView() {
            if (xCustomView == null)// 不是全屏播放状态
                return;
            // Hide the custom view.
            mActivity
                    .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            xCustomView.setVisibility(View.GONE);

            // Remove the custom view from its container.
            vBody.removeView(xCustomView);
            xCustomView = null;
            vBody.setVisibility(View.GONE);
            xCustomViewCallback.onCustomViewHidden();

            vWebView.setVisibility(View.VISIBLE);

            // Log.i(LOGTAG, "set it to webVew");
        }

        // 视频加载添加默认图标
        @Override
        public Bitmap getDefaultVideoPoster() {
            // Log.i(LOGTAG, "here in on getDefaultVideoPoster");
            if (xdefaltvideo == null && mActivity != null) {
//                xdefaltvideo = BitmapFactory.decodeResource(getResources(),
//                        R.drawable.bg_news_img_default);
            }
            return xdefaltvideo;
        }

        // 视频加载时进程loading
        @Override
        public View getVideoLoadingProgressView() {
            // Log.i(LOGTAG, "here in on getVideoLoadingPregressView");

            if (xprogressvideo == null) {
                LayoutInflater inflater = LayoutInflater.from(mActivity);
                xprogressvideo = inflater.inflate(R.layout.dlg_process_bar,
                        null);
            }
            return xprogressvideo;
        }

        // 网页标题
        @Override
        public void onReceivedTitle(WebView view, String title) {
            mActivity.setTitle(title);
            if (mTitleReceiver != null) {
                mTitleReceiver.receiveTitle(title);
            }
        }

        // @Override
        // // 当WebView进度改变时更新窗口进度
        // public void onProgressChanged(WebView view, int newProgress) {
        // //
        // (MainActivity.this).getWindow().setFeatureInt(Window.FEATURE_PROGRESS,
        // // newProgress*100);
        // onProgressChanged(view, newProgress);
        // }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            // TODO Auto-generated method stub
            super.onProgressChanged(view, newProgress);
            onWebviewProgress(view, newProgress);
            if (newProgress == 0) {
                vPbbody.setVisibility(View.VISIBLE);
            } else if (newProgress == 100) {
                vPbbody.setVisibility(View.GONE);
            }
            vPb.setX(newProgress * UIHelper.scrW / 100 - UIHelper.scrW);
        }
    }

    public void onWebviewProgress(WebView view, int newProgress) {
    }

    /**
     * 当横竖屏切换时会调用该方法
     *
     * @author
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setFullScreen();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            quitFullScreen();
        }
    }

    @Override
    protected void initView() {
        super.initView();
        vBody = (FrameLayout) rootView.findViewById(R.id.fm_news_detail_body);
        vWebView = (WebView) rootView.findViewById(R.id.fm_news_detail_webview);
        vPbbody = (FrameLayout) rootView.findViewById(R.id.fm_webview_pb_body);
        vPb = (TextView) rootView.findViewById(R.id.fm_webview_pb);
        vErrPage = (LinearLayout) rootView.findViewById(R.id.fm_webview_err);
        vErrMsg = (TextView) rootView.findViewById(R.id.fm_webview_err_msg);
    }

    @Override
    protected void initData() {
        super.initData();
        initwidget();
    }

    @Override
    public void setRootView() {
        super.setRootView();
        rootViewId = R.layout.fm_webview;
    }

    public FmWebView() {
        super();
    }

}
