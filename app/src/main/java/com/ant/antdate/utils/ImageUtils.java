package com.ant.antdate.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class ImageUtils {

    private static ImageUtils utils;
    private Context context;

    public ImageUtils(Context context) {
        this.context = context;
    }


    public static void init(Context context) {
        utils = new ImageUtils(context);
    }

    /**
     * 获取glide操作helper取名为ImageUtils
     *
     * @result ImageUtils
     **/
    public static ImageUtils get() {
        return utils;
    }

    /**
     * 获取glide普通加载图片
     *
     * @param iv  要加载图片的ImageView
     * @param url 加载图片的地址
     * @result void
     **/
    public void load(ImageView iv, String url) {
        this.load(iv, 0, 0, url);
    }

    /**
     * 获取glide普通加载图片
     *
     * @param iv  要加载图片的ImageView
     * @param url 加载图片的id
     * @result void
     **/
    public void load(ImageView iv, int url) {
        this.load(iv, 0, 0, url);
    }

    /**
     * 获取glide普通加载图片
     *
     * @param iv   要加载图片的ImageView
     * @param data 加载图片的地址
     * @result void
     **/
    public void load(ImageView iv, byte[] data) {
        this.load(iv, 0, 0, data);
    }

    /**
     * 获取glide普通加载图片
     *
     * @param iv   要加载图片的ImageView
     * @param file 加载图片的地址
     * @result void
     **/
    public void load(ImageView iv, File file) {
        this.load(iv, 0, 0, file);
    }

    /**
     * 获取glide普通加载图片
     *
     * @param iv     要加载图片的ImageView
     * @param corner 设置加载图片圆角度数，imageview需要设置固定大小
     * @param url    加载图片的地址
     * @result void
     **/
    public void load(ImageView iv, int corner, String url) {
        this.load(iv, 0, 0, corner, url);
    }

    /**
     * 获取glide普通加载图片
     *
     * @param iv     要加载图片的ImageView
     * @param corner 设置加载图片圆角度数，imageview需要设置固定大小
     * @param file   加载图片的地址
     * @result void
     **/
    public void load(ImageView iv, int corner, File file) {
        this.load(iv, 0, 0, corner, file);
    }


    /**
     * 获取glide加载圆形图片
     *
     * @param iv  要加载图片的ImageView
     * @param url 加载图片的地址
     * @result void
     **/
    public void loadCircle(ImageView iv, String url) {
        this.loadCircle(iv, 0, 0, url);
    }

    public void loadCircle(ImageView iv, int defaultResId) {
        this.loadCircle(iv, 0, 0, defaultResId);
    }


    /**
     * 获取glide加载圆形图片
     *
     * @param iv   要加载图片的ImageView
     * @param file 加载图片的地址
     * @result void
     **/
    public void loadCircle(ImageView iv, File file) {
        this.loadCircle(iv, 0, 0, file);
    }


    /**
     * 获取glide普通加载图片
     *
     * @param iv     要加载图片的ImageView
     * @param preimg 未加载之前显示的图片，参数为资源id
     * @param errimg 加载图片失败显示的图片，参数为资源id
     * @param url    加载图片的地址
     * @result void
     **/
    public void load(ImageView iv, int preimg, int errimg, String url) {
        Glide.with(context)
                .asBitmap()
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(errimg)
                .placeholder(preimg)
                .into(iv);
    }
    /**
     * 获取glide普通加载图片
     *
     * @param iv     要加载图片的ImageView
     * @param url    加载图片的地址
     * @result void
     **/
    public void load(ImageView iv,  Uri url) {
        Glide.with(context)
                .asBitmap()
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(iv);
    }

    /**
     * 获取glide普通加载图片
     *
     * @param iv     要加载图片的ImageView
     * @param preimg 未加载之前显示的图片，参数为资源id
     * @param errimg 加载图片失败显示的图片，参数为资源id
     * @param url    加载图片id
     * @result void
     **/
    public void load(ImageView iv, int preimg, int errimg, int url) {
        Glide.with(context)
                .asBitmap()
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(errimg)
                .placeholder(preimg)
                .into(iv);
    }

    /**
     * 获取glide普通加载图片
     *
     * @param iv     要加载图片的ImageView
     * @param preimg 未加载之前显示的图片，参数为资源id
     * @param errimg 加载图片失败显示的图片，参数为资源id
     * @param data   加载图片的数组
     * @result void
     **/
    public void load(ImageView iv, int preimg, int errimg, byte[] data) {
        Glide.with(context)
                .asBitmap()
                .load(data)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(errimg)
                .placeholder(preimg)
                .into(iv);
    }

    /**
     * 获取glide普通加载图片
     *
     * @param iv     要加载图片的ImageView
     * @param preimg 未加载之前显示的图片，参数为资源id
     * @param errimg 加载图片失败显示的图片，参数为资源id
     * @param file   加载图片的地址
     * @result void
     **/
    public void load(ImageView iv, int preimg, int errimg, File file) {
        Glide.with(context)
                .asBitmap()
                .load(file)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(errimg)
                .placeholder(preimg)
                .into(iv);
    }

    /**
     * 获取glide普通加载图片
     *
     * @param iv      要加载图片的ImageView
     * @param preimg  未加载之前显示的图片，参数为资源id
     * @param errimg  加载图片失败显示的图片，参数为资源id
     * @param corners 设置加载图片圆角度数，imageview需要设置固定大小
     * @param url     加载图片的地址
     * @result void
     **/
    public void load(ImageView iv, int preimg, int errimg, int corners, String url) {
        //设置图片圆角角度
        RoundedCorners roundedCorners = new RoundedCorners(corners);
        //通过RequestOptions扩展功能
        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners).override(iv.getWidth(), iv.getHeight());

        Glide.with(context)
                .asBitmap()
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(errimg)
                .placeholder(preimg)
                .apply(options)
                .into(iv);
    }

    /**
     * 获取glide普通加载图片
     *
     * @param iv      要加载图片的ImageView
     * @param preimg  未加载之前显示的图片，参数为资源id
     * @param errimg  加载图片失败显示的图片，参数为资源id
     * @param corners 设置加载图片圆角度数，imageview需要设置固定大小
     * @param file    加载图片的地址
     * @result void
     **/
    public void load(ImageView iv, int preimg, int errimg, int corners, File file) {
        //设置图片圆角角度
        RoundedCorners roundedCorners = new RoundedCorners(corners);
        //通过RequestOptions扩展功能
        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners).override(iv.getWidth(), iv.getHeight());

        Glide.with(context)
                .asBitmap()
                .load(file)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(errimg)
                .placeholder(preimg)
                .apply(options)
                .into(iv);
    }

    /**
     * 获取glide普通加载圆形图片
     *
     * @param iv     要加载图片的ImageView
     * @param preimg 未加载之前显示的图片，参数为资源id
     * @param errimg 加载图片失败显示的图片，参数为资源id
     * @param file   加载图片的地址
     * @result void
     **/
    public void loadCircle(ImageView iv, int preimg, int errimg, File file) {
        RequestOptions requestOptions = RequestOptions.bitmapTransform(new CircleCrop());
        Glide.with(context)
                .asBitmap()
                .load(file)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(errimg)
                .placeholder(preimg)
                .apply(requestOptions)
                .into(iv);
    }

    /**
     * 获取glide普通加载圆形图片
     *
     * @param iv     要加载图片的ImageView
     * @param preimg 未加载之前显示的图片，参数为资源id
     * @param errimg 加载图片失败显示的图片，参数为资源id
     * @param url    加载图片的地址
     * @result void
     **/
    public void loadCircle(ImageView iv, int preimg, int errimg, String url) {
        RequestOptions requestOptions = RequestOptions.bitmapTransform(new CircleCrop());
        Glide.with(context)
                .asBitmap()
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(errimg)
                .placeholder(preimg)
                .apply(requestOptions)
                .into(iv);
    }

    /**
     * 获取glide普通加载圆形图片
     *
     * @param iv           要加载图片的ImageView
     * @param preimg       未加载之前显示的图片，参数为资源id
     * @param errimg       加载图片失败显示的图片，参数为资源id
     * @param defaultResId 加载图片的资源文件
     * @result void
     **/
    public void loadCircle(ImageView iv, int preimg, int errimg, int defaultResId) {
        RequestOptions requestOptions = RequestOptions.bitmapTransform(new CircleCrop());
        Glide.with(context)
                .asBitmap()
                .load(defaultResId)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(errimg)
                .placeholder(preimg)
                .apply(requestOptions)
                .into(iv);
    }

    /**
     * 根据路径获得突破并压缩返回bitmap用于显示
     *
     * @return
     */
    public static Bitmap getSmallBitmap(String filePath, int newWidth, int newHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, newWidth, newHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
        Bitmap newBitmap = compressImage(bitmap, 500);
        if (bitmap != null) {
            bitmap.recycle();
        }
        return newBitmap;
    }

    /**
     * 质量压缩
     *
     * @param image
     * @param maxSize
     */
    public static Bitmap compressImage(Bitmap image, int maxSize) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        // scale
        int options = 80;
        // Store the bitmap into output stream(no compress)
        image.compress(Bitmap.CompressFormat.JPEG, options, os);
        // Compress by loop
        while (os.toByteArray().length / 1024 > maxSize) {
            // Clean up os
            os.reset();
            // interval 10
            options -= 10;
            image.compress(Bitmap.CompressFormat.JPEG, options, os);
        }

        Bitmap bitmap = null;
        byte[] b = os.toByteArray();
        if (b.length != 0) {
            bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
        }
        return bitmap;
    }

    /**
     * 计算图片的缩放值
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and
            // width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will
            // guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }


}
