package lib.frame.view.pickerview;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

import lib.frame.R;
import lib.frame.utils.DateUtil;
import lib.frame.view.pickerview.adapter.DateAdapter;
import lib.frame.view.pickerview.adapter.NumericWheelAdapter;
import lib.frame.view.pickerview.lib.WheelView;


/**
 * Created by shuai on 2016/6/6.
 */
public class DlgTimePicker extends Dialog implements View.OnClickListener {

    private TextView vTitle;
    private WheelView vDate;
    private WheelView vHour;
    private WheelView vMin;
    private TextView vOk;
    private Date mDate;
    private OnTimeSelectListener timeSelectListener;

    public DlgTimePicker(Context context) {
        super(context);
        initThis();
    }

    public DlgTimePicker(Context context, int themeResId) {
        super(context, themeResId);
        initThis();
    }

    protected DlgTimePicker(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initThis();
    }

    private void initThis() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dlg_time_picker);
        vTitle = (TextView) findViewById(R.id.dlg_time_picker_title);
        vDate = (WheelView) findViewById(R.id.dlg_time_picker_date);
        vHour = (WheelView) findViewById(R.id.dlg_time_picker_hour);
        vMin = (WheelView) findViewById(R.id.dlg_time_picker_min);
        vOk = (TextView) findViewById(R.id.dlg_time_picker_ok);
        vOk.setOnClickListener(this);
        setTime(new Date());
    }

    public TextView getvTitle() {
        return vTitle;
    }

    public TextView getvOk() {
        return vOk;
    }

    /**
     * 设置选中时间
     *
     * @param date
     */
    public void setTime(Date date) {
        mDate = date;
        Calendar calendar = Calendar.getInstance();
        if (date == null)
            calendar.setTimeInMillis(System.currentTimeMillis());
        else
            calendar.setTime(date);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        vDate.setAdapter(new DateAdapter(mDate, 14));
        vDate.setCurrentItem(0);
        vHour.setAdapter(new NumericWheelAdapter(0, 23));
        vHour.setCurrentItem(hours);
        vMin.setAdapter(new NumericWheelAdapter(0, 59));
        vMin.setCurrentItem(minute);
    }

    /**
     * 设置是否循环滚动
     *
     * @param cyclic
     */
    public void setCyclic(boolean cyclic) {
        vDate.setCyclic(cyclic);
        vHour.setCyclic(cyclic);
        vMin.setCyclic(cyclic);
    }

    @Override
    public void onClick(View v) {
        if (v == vOk) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(DateUtil.addDay(mDate, vDate.getCurrentItem()));
            calendar.set(Calendar.HOUR_OF_DAY, vHour.getCurrentItem());
            calendar.set(Calendar.MINUTE, vMin.getCurrentItem());
            Date date = calendar.getTime();
            timeSelectListener.onTimeSelect(date);
            dismiss();
        }
    }

    public void setOnTimeSelectListener(OnTimeSelectListener timeSelectListener) {
        this.timeSelectListener = timeSelectListener;
    }

    public interface OnTimeSelectListener {
        void onTimeSelect(Date date);
    }
}
