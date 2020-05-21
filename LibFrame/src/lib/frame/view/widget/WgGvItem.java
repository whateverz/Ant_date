package lib.frame.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import lib.frame.R;
import lib.frame.base.AppBase;


public class WgGvItem extends LinearLayout implements OnGlobalLayoutListener {

	private ImageView vGvImg;
	private TextView vTitle;

	private Context mContext;
	private AppBase mAppBase;

	private int vGvW;
	private int vGvH;

	private int vWgW;
	private int vWgH;

	private void initThis() {
		LayoutInflater.from(mContext).inflate(R.layout.wg_gv_item, this);
		vGvImg = (ImageView) findViewById(R.id.wg_gv_item_img);
		vTitle = (TextView) findViewById(R.id.wg_gv_item_text);
		getViewTreeObserver().addOnGlobalLayoutListener(this);
	}

	public ImageView getGvImg() {
		return vGvImg;
	}

	public TextView getTitle() {
		return vTitle;
	}


	public WgGvItem(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mContext = context;
		initThis();
	}

	public WgGvItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		initThis();
	}

	public WgGvItem(Context context) {
		super(context);
		mContext = context;
		initThis();
	}

	@Override
	public void onGlobalLayout() {
		if (vWgW == 0) {
			vWgW = getWidth();
			vWgH = getHeight();
		}
	}
}
