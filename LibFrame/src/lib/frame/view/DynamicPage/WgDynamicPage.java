package lib.frame.view.DynamicPage;

import android.content.Context;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import lib.frame.adapter.AdapterBaseViewPager;
import lib.frame.base.AppBase;
import lib.frame.base.IdConfigBase;
import lib.frame.bean.EventBase;

import lib.frame.R;

public class WgDynamicPage extends LinearLayout implements OnGlobalLayoutListener, OnItemClickListener {

    private ViewPager viewPager;
    private LinearLayout vIndexBar;

    private Context mContext;
    private AppBase mAppBase;

    private int itemSize;// 页面子控件大小
    private int padding;// 行间距

    private int vWgW;// 控件页面宽
    private int vWgH;// 控件页面高

    private int type;// 当前显示的内容的类型
    private int numColumn;// 列数
    private int line;// 行数
    private List<Integer> iconIds = new ArrayList<>();// item的图片资源id
    private List<String> titles = new ArrayList<>();// item的title

    private int nPage;// 计算共有多少页
    private List<GridView> gridViews = new ArrayList<>();
    private List<AdapterDynamic> adapterDynamics = new ArrayList<>();

    private AdapterPager mAdapter;

    private final int ID_START = 1920;
    public static final int TYPE_ITEM_CLICK = 1234;

    private void initWg() {

        itemSize = (vWgH - mContext.getResources().getDimensionPixelSize(
                R.dimen.new_106px)) / 2;
        nPage = iconIds.size() / (numColumn * line)
                + (iconIds.size() % (numColumn * line) > 0 ? 1 : 0);
        for (int i = 0; i < adapterDynamics.size(); i++) {
            adapterDynamics.get(i).clean();
        }
        adapterDynamics.clear();
        gridViews.clear();

        initAdapterList(nPage - 1);
        for (int i = 0; i < nPage; i++) {
            initAdapterList(i);
        }
        initAdapterList(0);

        //
        if (mAdapter == null) {
            mAdapter = new AdapterPager();
        }
//        viewPager.initUI(mAdapter, false);
//		viewPager.setIslock(nPage <= 1);
        viewPager.setAdapter(mAdapter);
        // handler.sendEmptyMessageDelayed(100, 300);
    }

    private void initAdapterList(int i) {
        GridView gridView = new GridView(mContext);
        gridView.setNumColumns(numColumn);

        AdapterDynamic adapterDynamic = new AdapterDynamic(mContext);
        int start = i * (numColumn * line);
        int end = (iconIds.size() - start) > (numColumn * line) ? (i + 1)
                * (numColumn * line) : iconIds.size();
        adapterDynamic.setData(iconIds.subList(start, end),
                titles.subList(start, end));
        gridView.setAdapter(adapterDynamic);
        gridView.setId(ID_START + i);
        gridView.setOnItemClickListener(this);
        gridViews.add(gridView);
        adapterDynamics.add(adapterDynamic);
    }

    private class AdapterPager extends AdapterBaseViewPager {
        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            if (arg1 < gridViews.size()) {
                ((ViewPager) arg0).removeView(gridViews.get(arg1));
            }
        }

        @Override
        public int getCount() {
            return gridViews.size();
        }

        @Override
        public Object instantiateItem(View arg0, int arg1) {
            ((ViewPager) arg0).addView(gridViews.get(arg1));
            return gridViews.get(arg1);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }
    }

    public void setData(int type, int numColumn, int line, int[] ids) {
        this.type = type;
        this.numColumn = numColumn;
        this.line = line;
        iconIds.clear();
        titles.clear();
        for (int id : ids) {
            iconIds.add(id);
            titles.add("");
        }
        setData(type, numColumn, line, iconIds, titles);
    }

    public void setData(int type, int numColumn, int line, List<Integer> ids) {
        this.type = type;
        this.numColumn = numColumn;
        this.line = line;
        iconIds.clear();
        iconIds.addAll(ids);
        titles.clear();
        for (int i = 0; i < iconIds.size(); i++) {
            titles.add("");
        }
        setData(type, numColumn, line, iconIds, titles);
    }

    public void setData(int type, int numColumn, int line, List<Integer> ids,
                        List<String> strs) {
        this.type = type;
        this.numColumn = numColumn;
        this.line = line;
        if (ids != iconIds) {
            iconIds.clear();
            iconIds.addAll(ids);
        }
        if (strs != titles) {
            titles.clear();
            titles.addAll(strs);
        }
        if (vWgH == 0) {
            invalidate();
        } else {
            initWg();
        }
    }

    public void setData(int type, int numColumn, int line, int[] ids,
                        String[] strs) {
        this.type = type;
        this.numColumn = numColumn;
        this.line = line;
        iconIds.clear();
        titles.clear();
        for (int i = 0; i < ids.length; i++) {
            iconIds.add(ids[i]);
            titles.add(strs[i]);
        }
        setData(type, numColumn, line, iconIds, titles);
    }

    private void initThis() {
        mAppBase = (AppBase) mContext.getApplicationContext();
        LayoutInflater.from(mContext).inflate(R.layout.wg_dynamic_page, this);
        viewPager = (ViewPager) findViewById(R.id.wg_dynamic_page_viewpager);
        vIndexBar = (LinearLayout) findViewById(R.id.wg_dynamic_page_index_bar);

        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    public WgDynamicPage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initThis();
    }

    public WgDynamicPage(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initThis();
    }

    public WgDynamicPage(Context context) {
        super(context);
        mContext = context;
        initThis();
    }

    @Override
    public void onGlobalLayout() {
        if (vWgH == 0) {
            vWgW = getWidth();
            vWgH = getHeight();
            if (vWgH > 0) {
                initWg();
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        Object[] objects = {type, (arg0.getId() - ID_START) * (numColumn * line) + arg2};
        EventBus.getDefault().post(new EventBase(IdConfigBase.EXPRESSION_CLICK, objects));
        mAppBase.doAction(IdConfigBase.ACTION_OPTION_CLICK, objects);
    }
}
