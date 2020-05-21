package lib.frame.view.recyclerView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import lib.frame.R;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

/**
 * Created by shuaq on 2016/10/29.
 */

public class BlockLoadMore extends BlockLoadMoreBase {

    private MaterialProgressBar vProgres;

    private TextView vText;

    public BlockLoadMore(Context context) {
        super(context);
    }

    public BlockLoadMore(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BlockLoadMore(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getLayout() {
        return R.layout.block_loadmore;
    }

    @Override
    protected void initThis() {
        super.initThis();
        vProgres = $(R.id.block_loadmore_progress);
        vText = $(R.id.block_loadmore_text);
    }


    @Override
    public void setLoading() {
        super.setLoading();
        vProgres.setVisibility(View.VISIBLE);
        vText.setText(R.string.loadmore_loading);
    }

    @Override
    public void setNoMoreData() {
        super.setNoMoreData();
        vProgres.setVisibility(View.GONE);
        vText.setText(R.string.loadmore_nomore_data);
    }

    @Override
    public void setLoadMoreError() {
        super.setLoadMoreError();
        vProgres.setVisibility(View.GONE);
        vText.setText(R.string.loadmore_fail);
    }

    public MaterialProgressBar getProgres() {
        return vProgres;
    }

    public TextView getText() {
        return vText;
    }
}
