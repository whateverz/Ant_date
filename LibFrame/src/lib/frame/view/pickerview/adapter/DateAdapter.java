package lib.frame.view.pickerview.adapter;

import java.util.Date;

import lib.frame.utils.DateUtil;

/**
 * Created by shuai on 2016/6/6.
 */
public class DateAdapter implements WheelAdapter {

    private Date mDate;
    private int mRange;

    public DateAdapter(Date date, int range) {
        mDate = date;
        mRange = range;
    }


    @Override
    public int getItemsCount() {
        return mRange;
    }

    @Override
    public Object getItem(int index) {
        Date curDate = DateUtil.addDay(mDate, index);
        return DateUtil.getWeek(curDate) + " " + DateUtil.getMonthEN(DateUtil.getMonth(curDate) + 1) + " " + DateUtil.getDay(curDate);
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }
}
