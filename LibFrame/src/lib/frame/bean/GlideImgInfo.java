package lib.frame.bean;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

/**
 * Created by shuaqq on 2017/8/11.
 */

public class GlideImgInfo {

    private boolean isRound = false;

    private int borderWidth = 0;
    private int borderColor = 0x00000000; // 边框颜色

    private int roundingRadius = 0;

    private ImageView.ScaleType scaleType;

    private int placeHolderRes = 0;
    private Drawable placeHolder;

    private int errorImgRes = 0;
    private Drawable errorImg;

    private boolean isAdjustBounds = false;

    public boolean isRound() {
        return isRound;
    }

    public GlideImgInfo setRound(boolean round) {
        isRound = round;
        return this;
    }

    public int getBorderWidth() {
        return borderWidth;
    }

    public GlideImgInfo setBorderWidth(int borderWidth) {
        this.borderWidth = borderWidth;
        return this;
    }

    public int getBorderColor() {
        return borderColor;
    }

    public GlideImgInfo setBorderColor(int borderColor) {
        this.borderColor = borderColor;
        return this;
    }

    public int getRoundingRadius() {
        return roundingRadius;
    }

    public GlideImgInfo setRoundingRadius(int roundingRadius) {
        this.roundingRadius = roundingRadius;
        return this;
    }

    public int getPlaceHolderRes() {
        return placeHolderRes;
    }

    public GlideImgInfo setPlaceHolderRes(int placeHolderRes) {
        this.placeHolderRes = placeHolderRes;
        return this;
    }

    public Drawable getPlaceHolder() {
        return placeHolder;
    }

    public GlideImgInfo setPlaceHolder(Drawable placeHolder) {
        this.placeHolder = placeHolder;
        return this;
    }

    public int getErrorImgRes() {
        return errorImgRes;
    }

    public GlideImgInfo setErrorImgRes(int errorImgRes) {
        this.errorImgRes = errorImgRes;
        return this;
    }

    public Drawable getErrorImg() {
        return errorImg;
    }

    public GlideImgInfo setErrorImg(Drawable errorImg) {
        this.errorImg = errorImg;
        return this;
    }

    public ImageView.ScaleType getScaleType() {
        return scaleType;
    }

    public GlideImgInfo setScaleType(ImageView.ScaleType scaleType) {
        this.scaleType = scaleType;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof GlideImgInfo
                && (((GlideImgInfo) obj).getBorderWidth() == borderWidth)
                && (((GlideImgInfo) obj).getBorderColor() == borderColor)
                && (((GlideImgInfo) obj).getScaleType() == scaleType)
                && (((GlideImgInfo) obj).getRoundingRadius() == roundingRadius)
                && (((GlideImgInfo) obj).isRound() == isRound)
                && (((GlideImgInfo) obj).isAdjustBounds() == isAdjustBounds);
    }

    public boolean isAdjustBounds() {
        return isAdjustBounds;
    }

    public void setAdjustBounds(boolean adjustBounds) {
        isAdjustBounds = adjustBounds;
    }
}