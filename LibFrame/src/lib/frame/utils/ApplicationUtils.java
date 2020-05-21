package lib.frame.utils;

import android.app.Application;

import androidx.annotation.NonNull;

import lib.frame.BuildConfig;

public class ApplicationUtils {

    private static Application sApplication;

    private static boolean sDebug = BuildConfig.DEBUG;

    private ApplicationUtils() {
        throw new UnsupportedOperationException("do not instantiate me , please.");
    }

    /**
     * 设置Application
     *
     * @param application Application
     */
    public static void init(@NonNull Application application) {
        sApplication = application;
    }

    /**
     * 获取Application
     *
     * @return Application实例
     * @throws NullPointerException 当Application为空时
     */
    public static Application getApplication() {
        if (sApplication == null) {
            throw new NullPointerException("application is null ,call init() please");
        }

        return sApplication;
    }

    /**
     * 初始化debug状态
     *
     * @param debug debug状态
     */
    public static void initDebug(boolean debug) {
        sDebug = debug;
    }

    /**
     * 判断是否是debug状态
     *
     * @return true ，debug状态；false ，非debug状态
     */
    public static boolean isDebug() {
        return sDebug;
    }
}
