package com.ant.antdate.utils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TimeUtils {

    /**
     * 传递进来时间戳秒，返回天数，大于14天则显示具体发帖时间
     * @param releasseDate：1409900705
     * @return  10天前
     */
    public static String secondToTime(String releasseDate) {
        if (isBlank(releasseDate))
            return "";
        Date now = new Date();
        BigDecimal db = new BigDecimal(releasseDate);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String data = format.format(db.longValue() * 1000);      //根据时间戳毫秒获取格式为yyyy-MM-dd的时间

        long between = (now.getTime()/1000 - db.longValue() + 8*60*60);  //8*60*60这个是为了处理时区问题
        long day = between / (24 * 3600);

        long hour = between % (24 * 3600) / 3600;

        long minute = between % 3600 / 60;

        long second=between % 60;

        String result = "";

        if (day > 3) {
            result = data;
        } else if (day <= 3 && day > 0) {
            result = day + "天前";
        } else if (hour > 0) {
            result = hour + "小时前";
        } else if (minute > 0) {
            result = minute + "分钟前";
        } else if (second > 0){
            result = second + "秒前";
        }

        return result;
    }

    public static String getTime(){
        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(now);
    }

    public static Long getLongTime(){
        Date now = new Date();
        return now.getTime();
    }
    public static String getTime(Calendar calendar){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(calendar.getTime());
    }
    public static Date getTime(String time){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return simpleDateFormat.parse(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static long getLongTime(String time){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return simpleDateFormat.parse(time).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
    public static List<String> getTime(String startTime,String endTime){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Calendar today = Calendar.getInstance();
            Calendar startTimeC = Calendar.getInstance();
            Calendar endTimeC = Calendar.getInstance();
            startTimeC.setTime(simpleDateFormat.parse(startTime));
            endTimeC.setTime(simpleDateFormat.parse(endTime));
            int startYear = startTimeC.get(Calendar.YEAR);
            int tYear = today.get(Calendar.YEAR);
            int startMonth = startTimeC.get(Calendar.MONTH)+1;
            int startDayOfMonth = startTimeC.get(Calendar.DAY_OF_MONTH);
            int startHour = startTimeC.get(Calendar.HOUR_OF_DAY);
            int startMinute = startTimeC.get(Calendar.MINUTE);
            int endHour = endTimeC.get(Calendar.HOUR_OF_DAY);
            int endMinute = endTimeC.get(Calendar.MINUTE);
            String time = String.format(Locale.getDefault(), "%02d月%02d日   %02d:%02d - %02d:%02d",  startMonth, startDayOfMonth, startHour, startMinute,endHour, endMinute);
            List<String> times = new ArrayList<>();
            if (isToday(startTimeC)){
                times.add("今");
                times.add(time);

            }else if (isTomorrow(startTimeC)){
                times.add("明");
                times.add(time);
            }else{
                if (startYear != tYear){
                    times.add(startYear+"");
                    times.add(time);
                }else{
                    String day = String.format(Locale.getDefault(), "%02d",startDayOfMonth);
                    String month = String.format(Locale.getDefault(), "%d月",startMonth);
                    times.add(day);
                    times.add(time);
                    times.add(month);

                }
            }
            return times;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    static Calendar getCurrentTime() {
        return Calendar.getInstance();
    }
    public static boolean isToday(Calendar calendar) {
        Calendar current = getCurrentTime();
        return current.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)
                && current.get(Calendar.DAY_OF_YEAR) == calendar.get(Calendar.DAY_OF_YEAR);
    }
    public static boolean isTomorrow(Calendar calendar) {
        Calendar current = getCurrentTime();
        return current.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)
                && current.get(Calendar.DAY_OF_YEAR)+1 == calendar.get(Calendar.DAY_OF_YEAR);
    }

    public static String getFormatTime(String time){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy年MM月dd日");
        try {

            return simpleDateFormat2.format(simpleDateFormat.parse(time));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    public static String getFormatNTime(String time){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy年MM月dd日  HH:mm");
        try {

            return simpleDateFormat2.format(simpleDateFormat.parse(time));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     *
     * @param releaseDate format: 2012-04-24T10:00:10+08:00
     * @return 返回天数，大于14天则显示具体发帖时间
     */
    public static String progressDate(String releaseDate) {
        // releaseDate format: 2012-04-24T10:00:10+08:00
        String dateStr = "";
        if (isBlank(releaseDate) || releaseDate.length() < 19)
            return "";
        if (releaseDate.indexOf("+") == -1) {
            dateStr = releaseDate;
        } else {
            dateStr = releaseDate.substring(0, releaseDate.indexOf("+"));
        }

        Date date = new Date();
        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            date = simpleDateFormat.parse(dateStr);
        } catch (Exception e) {
            return "";
        }
        long between = (now.getTime() - date.getTime()) / 1000; // 2个时间相差多少秒

        long day = between / (24 * 3600);

        long hour = between % (24 * 3600) / 3600;

        long minute = between % 3600 / 60;

        long second=between%60;

        String result = "";


        if (day > 14) {
            result = releaseDate.substring(0, releaseDate.indexOf(" "));
        } else if (day <= 14 && day > 0) {
            result = day + "天前";
        } else if (hour > 0) {
            result = hour + "小时前";
        } else if (minute > 0) {
            result = minute + "分钟前";
        } else if (second > 0){
            result = second + "秒前";
        }

        return result;
    }

    /**
     * 根据传来的毫秒字符串，转换成"yyyy-MM-dd"格式日期
     *
     * @param msStr
     * @return
     */
    public static String progressDateUseMSReturnWithYear(String msStr) {
        long ms = progressMS(msStr);
        if (ms == 0)
            return "";
        Date date = new Date(ms);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = simpleDateFormat.format(date);
        return dateStr;
    }

    private static long progressMS(String ms) {
        // ms format: /Date(512546554)/
        if (ms == null || ms.length() == 0) {
            return 0;
        }
        String msStr = null;
        if (ms.indexOf("/Date(") < 0 || ms.indexOf(")/") < 0) {
            msStr = ms;
        } else {
            msStr = ms.replace("/Date(", "").replace(")/", "");
        }

        long millisecond = 0L;
        try {
            millisecond = Long.parseLong(msStr);
        } catch (Exception e) {
            return 0;
        }
        return millisecond;
    }

    public static boolean isBlank(String str) {
        return str == null || str.trim().length() == 0;
    }


    public static String getOtherDay(int day){
        return "";
    }

    /**
     * 判断是否为日期
     * @param date
     * @return
     */
    public static boolean isDate(String date){
        boolean flag = false;
        int d = 8;
        //分割截取0年份1月份2日
        String[] dateSplit = date.split("-");
        //判断输入的月份是否是二月，输入的是二月的话，还需要判断该年是否是闰年
        if("02".equals(dateSplit[1]) || "2".equals(dateSplit[1])) {
            int year = Integer.parseInt(dateSplit[0]);
            // 不是闰年,需要判断日是否大于28
            if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
                d = 9;
            }
        }
        //年月日的正则表达式，此次没有理会2月份闰年
        //String regex = "^((19|20)[0-9]{2})-((0?2-((0?[1-9])|(1[0-9]|2[0-"+d+"])))|(0?(1|3|5|7|8|10|12)-((0?[1-9])|([1-2][0-9])|(3[0-1])))|(0?(4|6|9|11))-((0?[1-9])|([1-2][0-9])|30)";
        String regex = "^((19|20)[0-9]{2})-((0?2-((0?[1-9])|(1[0-9]|2[0-"+d+"])))|(0?(1|3|5|7|8|10|12)-((0?[1-9])|([1-2][0-9])|(3[0-1])))|(0?(4|6|9|11)-((0?[1-9])|([1-2][0-9])|30)))";

        //开始判断,且符合正则表达式
        if(date.matches(regex)) {
            flag = true;
        }
        return flag;
    }
}