package lib.frame.adapter;

import android.database.DataSetObserver;
import androidx.viewpager.widget.PagerAdapter;
import android.view.View;


public class AdapterBaseViewPager extends PagerAdapter {

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        if (observer != null) {
            super.unregisterDataSetObserver(observer);
        }
    }
}

