package com.ant.antdate.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.view.View;

import com.ant.antdate.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by 左金栋 on 2017/8/18.
 */

public class Util {

    /**
     * 获取两点距离
     * @param startX
     * @param startY
     * @param endX
     * @param endY
     * @return
     */
    public static double getDistance(float startX , float startY , float endX , float endY){
        double x1 = Double.parseDouble(String.valueOf(startX)) ;
        double y1 = Double.parseDouble(String.valueOf(startY)) ;
        double x2 = Double.parseDouble(String.valueOf(endX)) ;
        double y2 = Double.parseDouble(String.valueOf(endY)) ;
        return Math.sqrt(Math.pow(x1-x2,2)+Math.pow(y1-y2,2));
    }

    /**
     * 是否在区域内
     * @param left
     * @param top
     * @param right
     * @param bottom
     * @return
     */
    public static boolean isInside(float x , float y , float left , float top , float right , float bottom){
        if(x>left&&x<right&&y>top&&y<bottom){
            return true;
        }
        return false;
    }

    public static final String REGEX_MOBILE = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$";

    public static boolean isMobileNO(String mobileNums) {
        /**
         * 判断字符串是否符合手机号码格式
         * 移动号段: 134,135,136,137,138,139,147,150,151,152,157,158,159,170,178,182,183,184,187,188
         * 联通号段: 130,131,132,145,155,156,170,171,175,176,185,186
         * 电信号段: 133,149,153,170,173,177,180,181,189
         * @param str
         * @return 待检测的字符串
         */
        String telRegex = "^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\\d{8}$";// "[1]"代表下一位为数字可以是几，"[0-9]"代表可以为0-9中的一个，"[5,7,9]"表示可以是5,7,9中的任意一位,[^4]表示除4以外的任何一个,\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobileNums))
            return false;
        else
            return mobileNums.matches(telRegex);
    }
    public static void setvisibility(View visible, View gone){
        visible.setVisibility(View.VISIBLE);
        gone.setVisibility(View.GONE);
    }

  /*  public static String getImShowTime(Context context , Long time) {
        if (time != null) {
            Date date = new Date(time);

            Calendar c = Calendar.getInstance();
            c.setTime(date);
            int hour = c.get(Calendar.HOUR_OF_DAY); //获取当前小时数

            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);

            long msgTime = c.getTimeInMillis();

            long twoTime = 1000 * 60 * 60 * 24 * 2;
            Long diffTime = System.currentTimeMillis() - msgTime;
            if (diffTime > twoTime) { //超过一天
                SimpleDateFormat sdf = new SimpleDateFormat(context.getResources().getString(R.string.timeFormat));
                return sdf.format(date);
            }
            long dayTime = 1000 * 60 * 60 * 24;
            if (diffTime > dayTime) { //超过一天
                return context.getString(R.string.yesterday);
            }

            SimpleDateFormat sdf = new SimpleDateFormat(context.getString(R.string.detailTimeFormat));
            String formatTime = sdf.format(date);
            if (hour < 6) {
                return context.getString(R.string.earlyMorning) + formatTime;
            }
            if (hour < 11) {
                return context.getString(R.string.morning) + formatTime;
            }
            if (hour < 13) {
                return context.getString(R.string.noon) + formatTime;
            }
            if (hour < 19) {
                return context.getString(R.string.afternoon) + formatTime;
            }
            return context.getString(R.string.evening) + formatTime;
        }
        return "";
    }*/

    /**
     * [获取应用程序版本名称信息]
     * @param context
     * @return 当前应用的版本名称
     */
    public static synchronized String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
