package lib.frame.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import lib.frame.R;


public class WgSlideButton extends View {

    public interface OnToggleStateChangeListener {
        /**
         * 当开关状态改变回调此方法
         *
         * @param b 当前开关的最新状态
         */
        void onToggleStateChange(boolean b);
    }

    /**
     * 打开时的背景
     */
    private Bitmap slideBgOn;
    /**
     * 关闭时的背景
     */
    private Bitmap slideBgOff;
    /**
     * 滑动块的背景
     */
    private Bitmap slideBtn;

    /**
     * 设置开关的状态，打开/关闭。 默认：关闭
     */
    private boolean isOpen = false;
    /**
     * 当前滑动块的移动距离
     */
    private int currentX;
    /**
     * 记录当前滑动块滑动的状态。默认，false
     */
    private boolean isSliding = false;
    /**
     * 开关状态改变监听
     */
    private OnToggleStateChangeListener mListener;

    public WgSlideButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        // 设置默认值
        int bgOnId = R.mipmap.base_wg_switch_on;
        int bgOffId = R.mipmap.base_wg_switch_off;
        int btnId = R.mipmap.base_wg_switch_btn;

        // 获得自定义属性
        TypedArray ta = context.obtainStyledAttributes(attrs,
                R.styleable.WgSlideButton);

        int count = ta.getIndexCount();
        for (int i = 0; i < count; i++) {
            int itemId = ta.getIndex(i); // 获取某个属性的Id值
            if (itemId == R.styleable.WgSlideButton_isOpen) {
                // 设置当前按钮的状态
                isOpen = ta.getBoolean(itemId, false);
            } else if (itemId == R.styleable.WgSlideButton_slideBgOn) {
                // 开的背景
                bgOnId = ta.getResourceId(itemId, -1);
                if (bgOnId == -1) {
                    throw new RuntimeException("资源没有被找到，请设置背景图");
                }
            } else if (itemId == R.styleable.WgSlideButton_slideBgOff) {
                // 关的背景
                bgOffId = ta.getResourceId(itemId, -1);
                if (bgOffId == -1) {
                    throw new RuntimeException("资源没有被找到，请设置背景图");
                }
            } else if (itemId == R.styleable.WgSlideButton_slideBtn) {
                // 设置按钮图片
                btnId = ta.getResourceId(itemId, -1);
                if (btnId == -1) {
                    throw new RuntimeException("资源没有被找到，请设置背景图");
                }
            }
        }

        initBitmap(bgOnId, bgOffId, btnId);
    }

    public void initBitmap(int bgOnId, int bgOffId, int btnId) {
        slideBgOn = BitmapFactory.decodeResource(getResources(), bgOnId);
        slideBgOff = BitmapFactory.decodeResource(getResources(), bgOffId);
        slideBtn = BitmapFactory.decodeResource(getResources(), btnId);
    }

    /**
     * 移动效果的处理
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: // 手指按下
                currentX = (int) event.getX();
                isSliding = true;
                break;
            case MotionEvent.ACTION_MOVE: // 手指移动
                currentX = (int) event.getX();
                break;
            case MotionEvent.ACTION_UP: // 手指抬起
                isSliding = false;
                // 判断当前滑动块，偏向于哪一边，如果滑动块的中心点<背景的中心点，设置为关闭状态
                int bgCenter = slideBtn.getWidth() / 2;
                boolean state = currentX >= bgCenter; // 改变后的状态
                // 手指抬起时，回调监听，返回当前的开关状态
                if (state != isOpen && mListener != null) {
                    mListener.onToggleStateChange(state);
                }
                isOpen = state;
                break;
            default:
                break;
        }
        invalidate(); // 刷新控件，该方法会调用onDraw(Canvas canvas)方法
        return true; // 自己处理事件，不让父类负责消耗事件
    }

    /**
     * 测量当前控件宽高时回调
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 设置开关的宽和高
        setMeasuredDimension(slideBgOn.getWidth(), slideBgOn.getHeight());
    }

    /**
     * 绘制控件
     */
    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        // 1,滑动开关背景绘制到控件
        canvas.drawBitmap(isOpen ? slideBgOn : slideBgOff, 0, 0, null);
        // 2，绘制滑动块显示的位置，开启或关闭
        if (isSliding) {
            int left = currentX - slideBtn.getWidth() / 2; // 处理手指触点，将触点从slidingButton的左边移动到中间
            if (left < 0) {
                left = 0;
            } else if (left > slideBgOn.getWidth() - slideBtn.getWidth()) {
                left = slideBgOn.getWidth() - slideBtn.getWidth();
            }
            canvas.drawBitmap(slideBtn, left, 0, null);
        } else {
            if (isOpen) {
                // 绘制打开状态
                canvas.drawBitmap(slideBtn,
                        slideBgOn.getWidth() - slideBtn.getWidth(), 0, null);
            } else {
                // 绘制关闭状态
                canvas.drawBitmap(slideBtn, 0, 0, null);
            }
        }
    }

    public void setStatus(boolean b) {
        isOpen = b;
    }

    public boolean isOpen() {
        return isOpen;
    }

    /**
     * 对外设置监听方法
     *
     * @param listener
     */
    public void setOnSwitchChangeListener(OnToggleStateChangeListener listener) {
        this.mListener = listener;
    }

}
