package lib.frame.view.dlg;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lib.frame.R;
import lib.frame.adapter.AdapterBase;
import lib.frame.base.AppBase;
import lib.frame.utils.UIHelper;

public class DlgRightTopBox extends PopupWindow implements OnClickListener {
    private Context mContext;
    private AppBase mAppBase;
    private String TAG = "WgPopupRightTopBox";
    private View popupForView;
    private FloPopup floPopup;
    private int wgH;
    private List<String> popupList = new ArrayList<>();
    private List<Integer> resList = new ArrayList<>();
    public PopupListView mPopupListView;
    private OnWgPopupListListener mListener;
    private int width = -1;
    private int height = -1;
    //    private int bgVId = 10001;
    private int clickVId = -1;
    private int popupBoxWidth = 200;
    private Animation fade_in;

    private int defaultFrame = R.drawable.popup_box_right_top;
    private int defaultTextStyle = R.style.TEXT_BLACK_22PX_54A;

    public int getPopupBoxWidth() {
        return popupBoxWidth;
    }

    /**************************************************/



    public void setDefaultFrame(int defaultFrame) {
        this.defaultFrame = defaultFrame;
    }

    public void setDefaultTextStyle(int defaultTextStyle) {
        this.defaultTextStyle = defaultTextStyle;
    }

    public DlgRightTopBox(Context context, View view) {
        this(context, view, context.getResources().getDimensionPixelSize(
                R.dimen.default_right_top_box_width), LayoutParams.WRAP_CONTENT);
    }

    public DlgRightTopBox(Context context, View view, int width, int height) {
        super();
        mContext = context;
        mAppBase = (AppBase) mContext.getApplicationContext();
        popupForView = view;
        if (width > 0) {
            popupBoxWidth = width;
        }
        wgH = height;
        this.width = width;
        this.height = height;
        clickVId = view.getId();
        view.measure(0, 0);
        view.getMeasuredHeight();

        setWidth(LayoutParams.MATCH_PARENT);
        setHeight(LayoutParams.MATCH_PARENT);
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        floPopup = new FloPopup(mContext);
        floPopup.setLayoutParams(lp);
        floPopup.setOnClickListener(this);

        setContentView(floPopup);
        setFocusable(true);
        update();
        setBackgroundDrawable(new BitmapDrawable());
        setOutsideTouchable(true);

        fade_in = AnimationUtils.loadAnimation(mContext, R.anim.fade_in);
        setAnimationStyle(R.style.DlgMenu);
    }

    public void popup() {
        int[] location = {0, 0};
        popupForView.getLocationOnScreen(location);
        floPopup.setBackgroundResource(android.R.color.transparent);
        floPopup.startAnimation(fade_in);
        showAtLocation(popupForView.getRootView(), Gravity.CENTER, 0, 0);
    }

    public void addContent(View view) {
        floPopup.removeAllViews();
        view.setX(UIHelper.scrW - popupBoxWidth);
        view.setY(mContext.getResources().getDimension(R.dimen.action_bar_h));
        view.setBackgroundResource(defaultFrame);
        floPopup.addView(view);
    }

    public void setPopupList(List<String> l, List<Integer> res) {
        popupList = l;
        if (res != null) {
            resList = res;
        }
        if (mPopupListView == null) {
            mPopupListView = new PopupListView(mContext);
        }
        mPopupListView.mAdapter.notifyDataSetChanged();
        addContent(mPopupListView);
    }

    public PopupListView getmPopupListView() {
        return mPopupListView;
    }

    public void setPopupList(String[] l, Integer[] res) {
        popupList.clear();
        resList.clear();
        popupList.addAll(Arrays.asList(l));
        if (res != null) {
            resList.addAll(Arrays.asList(res));
        }
        if (mPopupListView == null) {
            mPopupListView = new PopupListView(mContext);
        }
        mPopupListView.mAdapter.notifyDataSetChanged();
        addContent(mPopupListView);
    }

    public void setPopupList(Integer[] strRes, Integer[] imgRes) {
        popupList.clear();
        resList.clear();
        for (Integer strRe : strRes) {
            popupList.add(mContext.getString(strRe));
        }
        if (imgRes != null) {
            resList.addAll(Arrays.asList(imgRes));
        }
        if (mPopupListView == null) {
            mPopupListView = new PopupListView(mContext);
        }
        mPopupListView.mAdapter.notifyDataSetChanged();
        addContent(mPopupListView);
    }

    private class FloPopup extends FrameLayout {
        public FloPopup(Context context) {
            super(context);
        }
    }

    public class PopupListView extends FrameLayout implements
            AdapterView.OnItemClickListener {
        public LvAdapter mAdapter;
        private ListView lvThis;
        private int item_height;
        private int selected = 0;

        public PopupListView(Context context) {
            super(context);
            item_height = getResources().getDimensionPixelSize(
                    R.dimen.normal_item_height);
            setLayoutParams(new LayoutParams(width, height));
            lvThis = new ListView(mContext);
            lvThis.setPadding(5, 5, 5, 5);
            lvThis.setOverScrollMode(ListView.OVER_SCROLL_NEVER);
//            lvThis.setDivider(new ColorDrawable(0x00000000));
//            lvThis.setDividerHeight(7);
            lvThis.setSelector(android.R.color.transparent);
            mAdapter = new LvAdapter(mContext);
            lvThis.setAdapter(mAdapter);
            lvThis.setOnItemClickListener(this);
            addView(lvThis);
        }

        public class LvAdapter extends AdapterBase {
            private int selected = 0;

            public LvAdapter(Context context) {
                mContext = context;
            }

            public void setTheme(int[] theme) {
                notifyDataSetChanged();
            }

            @Override
            public int getCount() {
                return popupList.size();
            }

            @Override
            public Object getItem(int arg0) {
                return null;
            }

            @Override
            public long getItemId(int arg0) {
                return 0;
            }

            @Override
            public View getView(int position, View covertView, ViewGroup arg2) {
                LinearLayout rootView;
                TextView tv;
                ImageView iv;
                if (covertView == null) {
                    rootView = new LinearLayout(mContext);
                    rootView.setOrientation(LinearLayout.HORIZONTAL);
                    rootView.setGravity(Gravity.CENTER);
                    tv = new TextView(mContext);
                    iv = new ImageView(mContext);
                    tv.setTextAppearance(mContext, defaultTextStyle);
                    tv.setGravity(Gravity.CENTER);
                    AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
                            LayoutParams.MATCH_PARENT, item_height);
                    tv.setLayoutParams(lp);
                    rootView.addView(iv);
                    rootView.addView(tv);
                    tv.setTag(iv);
                    rootView.setTag(tv);
                    lp = new AbsListView.LayoutParams(
                            LayoutParams.MATCH_PARENT,
                            LayoutParams.WRAP_CONTENT);
                    rootView.setLayoutParams(lp);
                    rootView.setPadding(2, 2, 2, 2);

                } else {
                    rootView = (LinearLayout) covertView;
                    tv = (TextView) rootView.getTag();
                    iv = (ImageView) tv.getTag();
                }
                if (resList != null && resList.size() == popupList.size()) {
                    iv.setImageResource(resList.get(position));
                }
                tv.setText(popupList.get(position));
                return rootView;
            }

            public void updateSelected(int position) {
                if (selected != position) {
                    selected = position;
                    notifyDataSetChanged();
                }
            }
        }

        @Override
        public void onItemClick(AdapterView<?> adapter, View view,
                                int position, long id) {
            dismiss();
            if (mListener != null) {
                mListener.OnListItemClick(clickVId, position);
            }
            mAdapter.updateSelected(position);
        }
    }

    /*****************************/
    public interface OnWgPopupListListener {
        void OnListItemClick(int vId, int position);
    }

    public void setOnWgPopupListListener(OnWgPopupListListener l) {
        mListener = l;
    }

    @Override
    public void onClick(View v) {
        if (v == floPopup) {
            dismiss();
        }
    }

}
