package lib.frame.view.DynamicPage;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import lib.frame.base.AppBase;
import lib.frame.view.widget.WgGvItem;

public class AdapterDynamic extends BaseAdapter {

	private Context mContext;
	private AppBase mAppBase;

	private List<Integer> iconIds;// item的图片资源id
	private List<String> titles;// item的title
	private int count;

	public AdapterDynamic(Context context) {
		super();
		mContext = context;
		mAppBase = (AppBase) mContext.getApplicationContext();
	}

	public void clean() {
		count = 0;
		notifyDataSetChanged();
	}

	public void setData(List<Integer> iconIds, List<String> titles) {
		this.iconIds = iconIds;
		this.titles = titles;
		count = iconIds.size();
	}

	@Override
	public int getCount() {
		return count;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		WgGvItem wgGvItem = null;
		if (arg1 == null) {
			wgGvItem = new WgGvItem(mContext);
		} else {
			wgGvItem = (WgGvItem) arg1;
		}
		wgGvItem.getGvImg().setBackgroundResource(iconIds.get(arg0));
		if (titles != null) {
			wgGvItem.getTitle().setVisibility(View.VISIBLE);
			wgGvItem.getTitle().setText(titles.get(arg0));
		} else {
			wgGvItem.getTitle().setVisibility(View.GONE);
		}
		return wgGvItem;
	}

}
