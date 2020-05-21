package lib.frame.view.wgwebkitbase;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;

import lib.frame.R;
import lib.frame.base.BaseFrameView;


/**
 * Created by shuai on 2015/11/11.
 */
public class WgSwipeWebView extends BaseFrameView {

    private final static String TAG = "WgSwipeWebView";

    private WgWebViewBase vWebView;
    private ProgressBar vProgress;

    private String mUrl;

    private int mProgress = 0;

    public WgSwipeWebView(Context context) {
        super(context);
    }

    public WgSwipeWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WgSwipeWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public WgWebViewBase getvWebView() {
        return vWebView;
    }

    @Override
    protected int getLayout() {
        return R.layout.wg_webview;
    }


    protected void initThis() {

        vWebView = (WgWebViewBase) findViewById(R.id.wg_webview);
        vProgress = (ProgressBar) findViewById(R.id.wg_webview_progress);

        vWebView.setProgressBar(vProgress);
        vWebView.setHorizontalScrollBarEnabled(false);//水平不显示
        vWebView.setVerticalScrollBarEnabled(false); //垂直不显示

    }

    @Override
    public void setOverScrollMode(int overScrollMode) {
        super.setOverScrollMode(overScrollMode);
        if (vWebView != null)
            vWebView.setOverScrollMode(View.OVER_SCROLL_NEVER);
    }

    public void setProgress(int progress) {
        mProgress = progress;
        vProgress.setProgress(progress);
    }

    public void loadUrl(String url) {
        mUrl = url;
        vWebView.loadUrl(url);
    }

    public String getTitle() {
        return vWebView.getTitle();
    }

    public void reLoad() {
        vWebView.reload();
    }

    public void setWgInterface(WgWebViewInterface wgWebViewInterface) {
        vWebView.setClientInterface(wgWebViewInterface);

    }

    public void loadJavascriptMethod(String str) {
        str = "javascript:" + str;
        vWebView.loadUrl(str);
    }

    public void addJavascriptInterface(Object javascriptObj, String interfaceName) {
//        wgWebViewBase.addJavascriptInterface(javascriptObj, interfaceName);
    }

    public void pause() {
        if (vWebView != null)
            vWebView.onPause();
    }

    public void resume() {
        if (vWebView != null)
            vWebView.onResume();
    }

    public boolean keyDown() {
        if (vWebView.canGoBack()) {
            vWebView.goBack();
            return false;
        }
        return true;
    }

    public void destory() {
        if (vWebView != null) {
            vWebView.destroy();
        }
    }

    public void clearHistory() {
        vWebView.clearHistory();
    }

    @Override
    public void onClick(View v) {

    }

    public void setProgressbarEnable(boolean enable) {
        vWebView.setProgressBar(enable ? vProgress : null);
    }
}
