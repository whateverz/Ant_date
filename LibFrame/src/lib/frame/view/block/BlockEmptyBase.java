package lib.frame.view.block;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import lib.frame.R;
import lib.frame.utils.UIHelper;

/**
 * Created by lwxkey on 16/8/19.
 */
public class BlockEmptyBase extends RelativeLayout {


    private TextView vSpace;

    private LinearLayout vBody;

    private Context mContext;

    public void setHeight(int height) {
        UIHelper.setView(vBody, LayoutParams.MATCH_PARENT, height);
    }

    public LinearLayout getvBody() {
        return vBody;
    }

    private void initThis() {
        LayoutInflater.from(mContext).inflate(R.layout.block_empty_base, this);
        vBody = (LinearLayout) findViewById(R.id.block_empty_body);
    }

    public BlockEmptyBase(Context context) {
        super(context);
        mContext = context;
        initThis();
    }

    public BlockEmptyBase(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initThis();
    }

    public BlockEmptyBase(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initThis();
    }


}
