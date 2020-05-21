package lib.frame.logic;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import lib.frame.R;
import lib.frame.adapter.AdapterBaseRecycleList;
import lib.frame.adapter.AdapterBaseRichRecycleList;
import lib.frame.base.AppBase;
import lib.frame.base.IdConfigBase;
import lib.frame.bean.RichListInfo;
import lib.frame.module.http.HttpResult;
import lib.frame.utils.JSONUtils;
import lib.frame.utils.Log;
import lib.frame.utils.UIHelper;
import lib.frame.view.recyclerView.AdapterRecyclerView;
import lib.frame.view.swipeRefresh.SwipyRefreshLayout;
import lib.frame.view.swipeRefresh.SwipyRefreshLayoutDirection;

public class LogicBase {

    private String TAG = "LogicBase";
    private static LogicBase logicBase;

    private AppBase mAppBase;

    private View vRoot;

    private View vScrollToView;

    private LogicBase(Context context) {
        mAppBase = (AppBase) context.getApplicationContext();
    }

    private LogicBase() {
    }

    public static LogicBase getInstance(Context context) {
        if (logicBase == null) {
            logicBase = new LogicBase(context);
        }
        return logicBase;
    }

    public static LogicBase getInstance() {
        if (logicBase == null) {
            logicBase = new LogicBase();
        }
        return logicBase;
    }


    public <T> T handleData(String result, Class<T> cls) {
        return JSONUtils.fromJson(result, cls);//new Gson().fromJson(result, cls);//
    }

    public <T> T handleData(String result, TypeToken<T> cls) {
        return JSONUtils.fromJson(result, cls);//new Gson().fromJson(result, cls);//
    }

//    public List<Object> handleDatas(TypeToken cls, String result) {
//        return (List<Object>) JSONUtils.fromJson(result, cls);
//    }

    @SuppressWarnings("unchecked")
    public <T> List<T> handleDatas(String result, TypeToken cls) {
        return (List<T>) JSONUtils.fromJson(result, cls);
    }

    @SuppressWarnings("unchecked")
    public <T> HttpResult<T> handleHttpDatas(String result, TypeToken cls) {
        return (HttpResult<T>) JSONUtils.fromJson(result, cls);
    }

    public <T> List<T> handleListResult(SwipyRefreshLayout vBody, AdapterRecyclerView mAdater, int refreshType,
                                        List<T> listObject, HttpResult httpResult) {
        if (vBody.isRefreshing()) {
            vBody.setRefreshing(false);
        }
        List<T> listNew = HttpResult.getResults(httpResult);
        if (listNew == null) {
            listNew = new ArrayList<>();
        }
        if (listNew != null) {
            if (refreshType == IdConfigBase.REFRESH) {//下拉刷新
                listObject.clear();
            } else {
                if (listNew.size() == 0) {//上拉加载,如果加载数据为0
                    vBody.setDirection(SwipyRefreshLayoutDirection.TOP);
                    return listObject;
                }
            }
            //判断拉取的数据是否还有可能有下一页
            vBody.setDirection(listNew.size() == IdConfigBase.PAGESIZE ?
                    SwipyRefreshLayoutDirection.BOTH : SwipyRefreshLayoutDirection.TOP);
            listObject.addAll(listNew);
            mAdater.notifyDataSetChanged();
        } else {
            UIHelper.ToastMessage(mAppBase, mAppBase.getString(R.string.data_err));
        }
        return listObject;
    }

    public <T> List<T> handleListResult(SwipyRefreshLayout vBody, AdapterBaseRecycleList mAdater, int refreshType,
                                        List<T> listObject, HttpResult httpResult) {
        if (vBody.isRefreshing()) {
            vBody.setRefreshing(false);
        }
        List<T> listNew = null;
        if (httpResult.getResults() != null) {
            listNew = HttpResult.getResults(httpResult);
            if (listNew == null) {
                listNew = new ArrayList<>();
            }
            if (listNew != null) {
                if (refreshType == IdConfigBase.REFRESH) {//下拉刷新
                    listObject.clear();
                } else {
                    if (listNew.size() == 0) {//上拉加载,如果加载数据为0
                        vBody.setDirection(SwipyRefreshLayoutDirection.TOP);
                        return listObject;
                    }
                }
                //判断拉取的数据是否还有可能有下一页
                vBody.setDirection(listNew.size() == IdConfigBase.PAGESIZE ?
                        SwipyRefreshLayoutDirection.BOTH : SwipyRefreshLayoutDirection.TOP);
                listObject.addAll(listNew);
                mAdater.notifyDataSetChanged();
            } else {
                UIHelper.ToastMessage(mAppBase, mAppBase.getString(R.string.data_err));
            }
        }
        return listObject;
    }

    public <A, B> RichListInfo<A, B> handleRichListResult(SwipyRefreshLayout vBody, AdapterBaseRichRecycleList<A, B> mAdater, int refreshType,
                                                          List<B> listObject, HttpResult httpResult) {
        if (vBody.isRefreshing()) {
            vBody.setRefreshing(false);
        }
        RichListInfo richListInfo = null;
        if (httpResult.getResults() != null) {
            richListInfo = (RichListInfo) httpResult.getResults();
            List<B> listNew = richListInfo.getList();
            if (listNew == null) {
                listNew = new ArrayList<>();
            }
            if (listNew != null) {
                if (refreshType == IdConfigBase.REFRESH) {//下拉刷新
                    listObject.clear();
                    mAdater.setDataHeader((A) richListInfo.getDetail());
                } else {
                    if (listNew.size() == 0) {//上拉加载,如果加载数据为0
                        vBody.setDirection(SwipyRefreshLayoutDirection.TOP);
                        return richListInfo;
                    }
                }
                //判断拉取的数据是否还有可能有下一页
                vBody.setDirection(listNew.size() == IdConfigBase.PAGESIZE ?
                        SwipyRefreshLayoutDirection.BOTH : SwipyRefreshLayoutDirection.TOP);
                listObject.addAll(listNew);
                mAdater.notifyDataSetChanged();
            } else {
                UIHelper.ToastMessage(mAppBase, mAppBase.getString(R.string.data_err));
            }
        }
        return richListInfo;
    }

    public <A, B> RichListInfo<A, B> handleRichListResult(SwipyRefreshLayout vBody, AdapterBaseRecycleList<B> mAdater, int refreshType,
                                                          List<B> listObject, HttpResult httpResult) {
        if (vBody.isRefreshing()) {
            vBody.setRefreshing(false);
        }
        RichListInfo richListInfo = null;
        if (httpResult.getResults() != null) {
            richListInfo = (RichListInfo) httpResult.getResults();
            List<B> listNew = richListInfo.getList();
            if (listNew == null) {
                listNew = new ArrayList<>();
            }
            if (listNew != null) {
                if (refreshType == IdConfigBase.REFRESH) {//下拉刷新
                    listObject.clear();
                } else {
                    if (listNew.size() == 0) {//上拉加载,如果加载数据为0
                        vBody.setDirection(SwipyRefreshLayoutDirection.TOP);
                        return richListInfo;
                    }
                }
                //判断拉取的数据是否还有可能有下一页
                vBody.setDirection(listNew.size() == IdConfigBase.PAGESIZE ?
                        SwipyRefreshLayoutDirection.BOTH : SwipyRefreshLayoutDirection.TOP);
                listObject.addAll(listNew);
                mAdater.notifyDataSetChanged();
            } else {
                UIHelper.ToastMessage(mAppBase, mAppBase.getString(R.string.data_err));
            }
        }
        return richListInfo;
    }

    public void doAction(int type, Object[] objects) {
        mAppBase.doAction(type, objects);
    }


    public void unregistKeyboardLayout() {
        vRoot.getViewTreeObserver().removeOnGlobalLayoutListener(globalLayoutListener);
    }

    private int rootInvisibleHeight;

    private ViewTreeObserver.OnGlobalLayoutListener globalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            Rect rect = new Rect();
            // 获取root在窗体的可视区域
            vRoot.getWindowVisibleDisplayFrame(rect);
            // 当前视图最外层的高度减去现在所看到的视图的最底部的y坐标
            if (rootInvisibleHeight != vRoot.getRootView().getHeight() - rect.bottom) {
                rootInvisibleHeight = vRoot.getRootView().getHeight() - rect.bottom;
                Log.i("tag", "最外层的高度" + vRoot.getRootView().getHeight() + "   rect.bottom == " + rect.bottom +
                        "  rootInvisibleHeight == " + rootInvisibleHeight);
                // 若rootInvisibleHeight高度大于100，则说明当前视图上移了，说明软键盘弹出了
                if (rootInvisibleHeight > 300) {
                    //软键盘弹出来的时候
                    int[] location = new int[2];
                    // 获取scrollToView在窗体的坐标
                    vScrollToView.getLocationInWindow(location);
                    // 计算root滚动高度，使scrollToView在可见区域的底部
                    int srollHeight = (location[1] + vScrollToView.getHeight()) - rect.bottom;
                    vRoot.scrollTo(0, srollHeight);
                    LogicBase.getInstance().doAction(IdConfigBase.ACTION_KEYBOARD_SHOW, null);
                } else {
                    // 软键盘没有弹出来的时候
                    vRoot.scrollTo(0, 0);
                    LogicBase.getInstance().doAction(IdConfigBase.ACTION_KEYBOARD_HIDE, null);
                }
            }
        }
    };

    /**
     * @param root         最外层布局，需要调整的布局
     * @param scrollToView 被键盘遮挡的scrollToView，滚动root,使scrollToView在root可视区域的底部
     */
    public void controlKeyboardLayout(final View root, final View scrollToView) {
        if (this.vRoot != null) {
            vRoot.getViewTreeObserver().removeOnGlobalLayoutListener(globalLayoutListener);
        }
        this.vRoot = root;
        this.vScrollToView = scrollToView;

        // 注册一个回调函数，当在一个视图树中全局布局发生改变或者视图树中的某个视图的可视状态发生改变时调用这个回调函数。
        vRoot.getViewTreeObserver().addOnGlobalLayoutListener(globalLayoutListener);
    }
}