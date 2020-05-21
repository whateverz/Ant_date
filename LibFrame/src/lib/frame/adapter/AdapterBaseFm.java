package lib.frame.adapter;

import android.content.Context;
import android.database.DataSetObserver;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lib.frame.base.AppBase;
import lib.frame.base.BaseFrameFragment;

public class AdapterBaseFm extends FragmentPagerAdapter {

    private FragmentManager fm;
    private Context mContext;
    private AppBase mAppBase;
    protected List<BaseFrameFragment> fragmentList = new ArrayList<>();

    private String[] title;


    public void setTitle(int[] title) {
        String[] strs = new String[title.length];
        for (int i = 0; i < title.length; i++) {
            strs[i] = mContext.getString(title[i]);
        }
        setTitle(strs);
    }

    public void setTitle(String[] title) {
        this.title = title;
    }

    public AdapterBaseFm(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
        this.fm = fm;
        fragmentList.clear();
    }

    public void setFragmentList(List<BaseFrameFragment> fragmentList) {
        if (this.fragmentList != null && this.fragmentList.size() > 0) {
            FragmentTransaction ft = fm.beginTransaction();
            for (Fragment f : this.fragmentList) {
                ft.remove(f);
            }
            ft.commit();
            fm.executePendingTransactions();
        }
        this.fragmentList = fragmentList;
        notifyDataSetChanged();
    }

    public void setFragmentList(BaseFrameFragment[] fragments) {
        fragmentList.clear();
        Collections.addAll(this.fragmentList, fragments);
        notifyDataSetChanged();
    }

    /**
     * 得到每个页面
     */
    @Override
    public Fragment getItem(int arg0) {
        return (fragmentList == null || fragmentList.size() == 0) ? null
                : fragmentList.get(arg0);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    /**
     * 每个页面的title
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return (title != null && position < getCount()) ? title[position] : "";
    }

    /**
     * 页面的总个数
     */
    @Override
    public int getCount() {
        return fragmentList == null ? 0 : fragmentList.size();
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        if (observer != null) {
            super.unregisterDataSetObserver(observer);
        }
    }

}
