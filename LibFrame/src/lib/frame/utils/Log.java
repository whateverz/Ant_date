package lib.frame.utils;


import lib.frame.base.AppBase;

/**
 * Created by gzvs on 2015/3/10.
 */
public class Log {

    public static int i(String tag, String str) {
//        if (AppBase.DEBUG)
            return android.util.Log.i(tag, str);
//        else
//            return 0;
    }

    public static int i(String tag, String str, Throwable arg) {
        if (AppBase.DEBUG)
            return android.util.Log.i(tag, str, arg);
        else
            return 0;
    }

    public static int d(String tag, String str) {
        if (AppBase.DEBUG)
            return android.util.Log.d(tag, str);
        else
            return 0;
    }

    public static int d(String tag, String str, Throwable arg) {
        if (AppBase.DEBUG)
            return android.util.Log.d(tag, str, arg);
        else
            return 0;
    }

    public static int e(String tag, String str) {
        if (AppBase.DEBUG)
            return android.util.Log.e(tag, str);
        else
            return 0;
    }

    public static int e(String tag, String str, Throwable arg) {
        if (AppBase.DEBUG)
            return android.util.Log.e(tag, str, arg);
        else
            return 0;
    }

    public static int v(String tag, String str) {
        if (AppBase.DEBUG)
            return android.util.Log.v(tag, str);
        else
            return 0;
    }

    public static int v(String tag, String str, Throwable arg) {
        if (AppBase.DEBUG)
            return android.util.Log.v(tag, str, arg);
        else
            return 0;
    }

    public static int w(String tag, String str) {
        if (AppBase.DEBUG)
            return android.util.Log.wtf(tag, str);
        else
            return 0;
    }

    public static int w(String tag, String str, Throwable arg) {
        if (AppBase.DEBUG)
            return android.util.Log.w(tag, str, arg);
        else
            return 0;
    }

    public static int wtf(String tag, String str) {
        if (AppBase.DEBUG)
            return android.util.Log.wtf(tag, str);
        else
            return 0;
    }
}
