package lib.frame.view.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import lib.frame.base.AppBase;
import lib.frame.logic.LogicImgShow;

/**
 * Created by lwxkey on 16/9/22.
 */
public class WgImageViewTx extends WgScalemageView implements ViewTreeObserver.OnGlobalLayoutListener {

    private static final Bitmap.Config BITMAP_CONFIG = Bitmap.Config.ARGB_8888;
    private static final int COLOR_DRAWABLE_DIMENSION = 1;

    private int width;
    private int height;

    private String imgUrl = "";
    private boolean isNeedReload;//是否需要重新加载
    private boolean isNeedCrop = true;//是否需要裁剪
    private int type = TYPE_NORMAL;//0:正常; 1:圆形; 2:圆角
    private int cornerSize = 0;
    private int borderWidth = 0;

    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_ROUND = 1;
    public static final int TYPE_CORNER = 2;
    public static final int TYPE_HALF_TOP_CORNER = 3;

    private final int MSG_REFRESH = 1;

    public void reset() {
        imgUrl = "";
    }


    public boolean isNeedCrop() {
        return isNeedCrop;
    }

    public void setNeedCrop(boolean needCrop) {
        isNeedCrop = needCrop;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_REFRESH:
                    width = getWidth();
                    height = getHeight();
                    if (width == 0) {
                        handler.sendEmptyMessageDelayed(MSG_REFRESH, 200);
                    } else {
                        show();
                    }
                    break;
            }
        }
    };

    public int[] getImgSize(String imgUrl) {
        int[] size = {0, 0};
        if (imgUrl.startsWith("http") && imgUrl.indexOf("myqcloud.com") > 0 && imgUrl.indexOf("img_") > 0) {
            String[] temp0 = imgUrl.split("img_");
            String[] temp1 = temp0[1].split("_");
            size[0] = Integer.parseInt(temp1[1]);
            size[1] = Integer.parseInt(temp1[2]);
        }
        return size;
    }

    public int[] getImgSize(String imgUrl, int width, int height) {
        int[] size = getImgSize(imgUrl);
        int w = width;
        int h = height;
        if (width > size[0] && size[0] > 0) {
            w = size[0];
            h = (w * height) / width;
            if (h > size[1]) {
                w = (w * size[1]) / h;
                h = size[1];
            }
            size[0] = w;
            size[1] = h;
        } else if (height > size[1] && size[1] > 0) {
            h = size[1];
            w = (h * width) / height;
            if (w > size[0]) {
                h = (h * size[0]) / w;
                w = size[0];
            }
            size[0] = w;
            size[1] = h;
        }
        return size;
    }

    public void show() {
        if (!TextUtils.isEmpty(imgUrl) && isNeedReload) {
            isNeedReload = false;
            String url = imgUrl;
            if (isNeedCrop && imgUrl.startsWith("http") && url.indexOf("myqcloud.com") > 0 && width > 0 && height > 0) {
                int[] size = getImgSize(imgUrl);
                int w = width;
                int h = height;
                if (getScaleType() == ScaleType.FIT_CENTER) {
                    w = width;
                    h = height * size[1] / size[0];
                    if (h > height) {
                        h = height;
                        w = width * size[0] / size[1];
                    }
                } else if (width > size[0] && size[0] > 0) {
                    w = size[0];
                    h = (w * height) / width;
                    if (h > size[1]) {
                        w = (w * size[1]) / h;
                        h = size[1];
                    }
                } else if (height > size[1] && size[1] > 0) {
                    h = size[1];
                    w = (h * width) / height;
                    if (w > size[0]) {
                        h = (h * size[0]) / w;
                        w = size[0];
                    }
                }
                url = imgUrl.replace("https", "http") + "?imageMogr2/scrop/" + w + "x" + h + "/crop/" + w + "x" + h;
            }
            LogicImgShow.getInstance(mContext).showImage(url, this);

//            if (type == TYPE_NORMAL) {
//                LogicImgShow.getInstance(mContext).showImage(url, this);
//            } else if (type == TYPE_ROUND) {
//                LogicImgShow.getInstance(mContext).showRoundImage(url, borderWidth, Color.WHITE, this);
//            } else if (type == TYPE_CORNER || type == TYPE_HALF_TOP_CORNER) {
//                LogicImgShow.getInstance(mContext).showRoundCornerImage(url, cornerSize, borderWidth, this);
//            } else if (type == TYPE_HALF_TOP_CORNER) {
//                LogicImgShow.getInstance(mContext).showHalfTopRoundCornerImage(url, cornerSize, borderWidth, this);
//            }
//            Log.i("lwxkey", url);
        }
    }

    public void setOriginalImgUrl(String imgUrl) {
        LogicImgShow.getInstance(mContext).showImage(imgUrl, this);
    }

    public void setImgUrl(String imgUrl) {
        if (TextUtils.isEmpty(imgUrl)) {
            setImageBitmap(null);
//            if (type != TYPE_ROUND) {
//                setImageResource(R.color.default_bg);
//            }
        } else if (imgUrl != null && !imgUrl.equals(this.imgUrl)) {
            isNeedReload = true;
//            if (type != TYPE_ROUND) {
//                setImageResource(R.color.default_bg);
//            }
            this.imgUrl = imgUrl;
            if (width == 0) {
                handler.sendEmptyMessageDelayed(MSG_REFRESH, 200);
            } else {
                show();
            }
        }
    }

    public void setRoundImg(String imgUrl) {
        setRoundImg(imgUrl, borderWidth);
    }

    public void setRoundImg(String imgUrl, int borderWidth) {
        this.type = TYPE_ROUND;
        this.borderWidth = borderWidth;
        setImgUrl(imgUrl);
    }

    public void setRoundCornerImg(String imgUrl, int cornerSize) {
        setRoundCornerImg(imgUrl, cornerSize, 0);
    }

    public void setRoundCornerImg(String imgUrl, int cornerSize, int borderWidth) {
        this.type = TYPE_CORNER;
        this.borderWidth = borderWidth;
        this.cornerSize = cornerSize;
        setImgUrl(imgUrl);
    }

    public void setHalfTopRoundCornerImg(String imgUrl, int cornerSize, int borderWidth) {
        this.type = TYPE_HALF_TOP_CORNER;
        this.borderWidth = borderWidth;
        this.cornerSize = cornerSize;
        setImgUrl(imgUrl);
    }

    @Override
    protected void initThis(AttributeSet attrs) {
        super.initThis(attrs);
        getViewTreeObserver().addOnGlobalLayoutListener(this);
        setScaleType(ScaleType.CENTER_CROP);
    }

    @Override
    public void setLayoutParams(ViewGroup.LayoutParams params) {
        width = 0;
        super.setLayoutParams(params);
    }

    public WgImageViewTx(Context context) {
        super(context);
    }

    public WgImageViewTx(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WgImageViewTx(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void onGlobalLayout() {
        if (width == 0) {
            width = getWidth();
            height = getHeight();
            if (width > 0) {
                show();
//                setScaleType(ScaleType.CENTER_CROP);
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();
        if (drawable == null) {
            return;
        }
        if (type == TYPE_CORNER || type == TYPE_ROUND) {
            drawDrawable(canvas, getBitmapFromDrawable(drawable));
        } else {
            super.onDraw(canvas);
        }
    }

    private void drawDrawable(Canvas canvas, Bitmap bitmap) {
        Paint paint = new Paint();
        paint.setColor(0xffffffff);
        paint.setAntiAlias(true);

        int saveFlags = 0;


        canvas.saveLayer(0, 0, width, height, null, saveFlags);

        if (type == TYPE_CORNER) {
            RectF rectf = new RectF(borderWidth / 2, borderWidth / 2, getWidth() - borderWidth / 2, getHeight() - borderWidth / 2);
            canvas.drawRoundRect(rectf, cornerSize, cornerSize, paint);
        } else {
            canvas.drawCircle(width / 2, height / 2, width / 2 - borderWidth, paint);
        }

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN)); // SRC_IN 只显示两层图像交集部分的上层图像

        if (bitmap != null) {
            //Bitmap缩放
            float scaleWidth = ((float) getWidth()) / bitmap.getWidth();
            float scaleHeight = ((float) getHeight()) / bitmap.getHeight();
            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleHeight);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            canvas.drawBitmap(bitmap, 0, 0, paint);
            canvas.restore();
        }
    }

    private Bitmap getBitmapFromDrawable(Drawable drawable) {
        try {
            Bitmap bitmap;
            if (drawable instanceof BitmapDrawable) {
                return ((BitmapDrawable) drawable).getBitmap();
            } else if (drawable instanceof ColorDrawable) {
                bitmap = Bitmap.createBitmap(COLOR_DRAWABLE_DIMENSION, COLOR_DRAWABLE_DIMENSION, BITMAP_CONFIG);
            } else {
                bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), BITMAP_CONFIG);
            }
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }
    }
}
