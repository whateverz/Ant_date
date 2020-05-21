package lib.frame.view.dlg;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import lib.frame.R;

public class DlgDateTimePickUtils implements OnValueChangeListener, OnClickListener {

    private View dateTimeLayout;

    private TextView vBtnLeft;
    private TextView vBtnRight;
    private TextView vTime;

    private NumberPicker vDatePicker;
    private NumberPicker vHourPicker;
    private NumberPicker vMinutePicker;

    private String initDateTime;
    private Context mContext;
    private Calendar calendar;
    private String[] minuteArrs;
    private String hourStr;
    private String minuteStr;
    private String dateStr;
    private Dialog dialog;
    private String[] dayArrays;
    private long currentTimeMillis;

    private int dayCount = 7;

    private OnDlgDateTimePickUtilsListener mListener;

    public void setDayCount(int dayCount) {
        this.dayCount = dayCount;
    }

    private void initThis() {
        dateTimeLayout = View.inflate(mContext, R.layout.dlg_datetime_picker_utils, null);

        vBtnLeft = (TextView) dateTimeLayout.findViewById(R.id.dlg_datetime_picker_btn_left);
        vBtnRight = (TextView) dateTimeLayout.findViewById(R.id.dlg_datetime_picker_btn_right);
        vTime = (TextView) dateTimeLayout.findViewById(R.id.dlg_datetime_picker_time);

        vBtnLeft.setOnClickListener(this);
        vBtnRight.setOnClickListener(this);

        vDatePicker = (NumberPicker) dateTimeLayout.findViewById(R.id.dlg_datetime_picker_date);
        vHourPicker = (NumberPicker) dateTimeLayout.findViewById(R.id.dlg_datetime_picker_hour);
        vMinutePicker = (NumberPicker) dateTimeLayout.findViewById(R.id.dlg_datetime_picker_minute);
        vDatePicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        vHourPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        vMinutePicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        initPicker();

        dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dateTimeLayout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = ((Activity) mContext).getWindowManager().getDefaultDisplay().getHeight();
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        dialog.onWindowAttributesChanged(wl);
        // 设置点击外围隐藏
        dialog.setCanceledOnTouchOutside(true);
    }

    public DlgDateTimePickUtils(Context context, String initDateTime) {
        this.mContext = context;
        this.initDateTime = initDateTime;
        initThis();
    }

    public TextView getvBtnLeft() {
        return vBtnLeft;
    }

    public TextView getvBtnRight() {
        return vBtnRight;
    }

    public void initPicker() {
        Calendar calendar = Calendar.getInstance();

        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        if (55 <= minutes)
            minutes = 0;
        else if (50 <= minutes)
            minutes = 11;
        else if (45 <= minutes)
            minutes = 10;
        else if (40 <= minutes)
            minutes = 9;
        else if (35 <= minutes)
            minutes = 8;
        else if (30 <= minutes)
            minutes = 7;
        else if (25 <= minutes)
            minutes = 6;
        else if (20 <= minutes)
            minutes = 5;
        else if (15 <= minutes)
            minutes = 4;
        else if (10 <= minutes)
            minutes = 3;
        else if (5 <= minutes)
            minutes = 2;
        else
            minutes = 1;

        // 设置日期 dayCount天以内
        dayArrays = new String[dayCount];
        for (int i = 0; i < dayCount; i++) {
            currentTimeMillis = System.currentTimeMillis() + ((long) i * 24 * 3600 * 1000);
            calendar.setTime(new Date(currentTimeMillis));
            dayArrays[i] = calendar.get(Calendar.MONTH) + 1 + "月" + calendar.get(Calendar.DAY_OF_MONTH) + "日";
        }
        currentTimeMillis = System.currentTimeMillis();// 设置当前时间的毫秒值
        vDatePicker.setOnValueChangedListener(this);
        vDatePicker.setDisplayedValues(dayArrays);
        vDatePicker.setMinValue(0);
        vDatePicker.setMaxValue(dayArrays.length - 1);
        vDatePicker.setValue(0);
        dateStr = dayArrays[0];// 初始值

        // 设置小时 预约两个小时以后
        vHourPicker.setOnValueChangedListener(this);
        vHourPicker.setMaxValue(24);
        vHourPicker.setMinValue(1);
        if (minutes == 0) {
            vHourPicker.setValue(hours + 3);
            hourStr = hours + 3 + "";// 初始值
        } else {
            vHourPicker.setValue(hours + 2);
            hourStr = hours + 2 + "";// 初始值
        }

        // 设置分钟
        minuteArrs = new String[]{"00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55"};
        vMinutePicker.setOnValueChangedListener(this);
        vMinutePicker.setDisplayedValues(minuteArrs);
        vMinutePicker.setMinValue(0);
        vMinutePicker.setMaxValue(minuteArrs.length - 1);
        vMinutePicker.setValue(minutes);
        minuteStr = minuteArrs[minutes];// 初始值
    }

    public void dismiss() {
        dialog.dismiss();
    }

    /**
     * 弹出日期时间选择框方法
     *
     * @return
     */
    @SuppressWarnings("deprecation")
    public Dialog dateTimePicKDialog() {
        dialog.show();
        onDateChanged();
        return dialog;
    }

    @SuppressWarnings("deprecation")
    public void onDateChanged() {
        // 获得日历实例
        calendar = Calendar.getInstance();
        calendar.setTime(new Date(currentTimeMillis));
        Date date = calendar.getTime();
        date.setHours(Integer.parseInt(hourStr));
        date.setMinutes(Integer.parseInt(minuteStr));
        calendar.setTime(date);
//        if (calendar.getTimeInMillis() <= System.currentTimeMillis() + 2 * 3600000
//                || calendar.getTimeInMillis() > System.currentTimeMillis() + dayCount
//                * 24 * 3600 * 1000) {
//            vTime.setText(initDateTime);
//        } else {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        initDateTime = sdf.format(calendar.getTime()) + " " + hourStr + ":" + minuteStr;
        vTime.setText(initDateTime);
//        }
    }

    /**
     * 截取子串
     *
     * @param srcStr      源串
     * @param pattern     匹配模式
     * @param indexOrLast
     * @param frontOrBack
     * @return
     */
    public static String spliteString(String srcStr, String pattern, String indexOrLast, String frontOrBack) {
        String result = "";
        int loc = -1;
        if (indexOrLast.equalsIgnoreCase("index")) {
            loc = srcStr.indexOf(pattern); // 取得字符串第一次出现的位置
        } else {
            loc = srcStr.lastIndexOf(pattern); // 最后一个匹配串的位置
        }
        if (frontOrBack.equalsIgnoreCase("front")) {
            if (loc != -1)
                result = srcStr.substring(0, loc); // 截取子串
        } else {
            if (loc != -1)
                result = srcStr.substring(loc + 1, srcStr.length()); // 截取子串
        }
        return result;
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        if (picker == vDatePicker) {
            currentTimeMillis = System.currentTimeMillis() + newVal * 24 * 3600 * 1000;
            dateStr = dayArrays[newVal];
            onDateChanged();
        } else if (picker == vHourPicker) {
            hourStr = newVal + "";
            onDateChanged();
        } else if (picker == vMinutePicker) {
            minuteStr = minuteArrs[newVal];
            onDateChanged();
        }
    }

    @Override
    public void onClick(View v) {
        if (mListener != null) {
            if (v == vBtnLeft) {
                mListener.onBtnClick(0, null);
            } else if (v == vBtnRight) {
//                if (calendar.getTimeInMillis() <= System.currentTimeMillis() + 2 * 3600000
//                        || calendar.getTimeInMillis() > System.currentTimeMillis() + dayCount * 24 * 3600 * 1000) {
//                    vTime.setText("请预约两小时以后的有效时间");
//                } else {
//                    dialog.dismiss();
//                }
                mListener.onBtnClick(1, initDateTime);
            }
        }
    }

    public interface OnDlgDateTimePickUtilsListener {

        void onBtnClick(int index, String dateStr);//0：左边按钮；1：右边按钮

    }

    public void setOnDlgDateTimePickUtilsListener(OnDlgDateTimePickUtilsListener l) {
        mListener = l;
    }

}
