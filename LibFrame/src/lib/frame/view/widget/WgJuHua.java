package lib.frame.view.widget;

import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import lib.frame.R;

/**
 * Created by lwxkey on 2017/7/4.
 */

public class WgJuHua extends ImageView {

    private Context mContext;

    private Animation animation;

    private void initThis() {

        animation = AnimationUtils.loadAnimation(mContext, R.anim.rotate);
        animation.setDuration(1000);
        setImageResource(R.mipmap.juhua);
        startAnimation(animation);
    }


    public WgJuHua(Context context) {
        super(context);
        mContext = context;
        initThis();
    }

    public WgJuHua(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initThis();
    }

    public WgJuHua(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initThis();
    }

}
