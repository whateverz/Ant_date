package com.ant.antdate.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.RequiresApi;

import org.json.JSONObject;

import java.io.File;
import java.io.Serializable;

import static android.os.Environment.MEDIA_MOUNTED;

public class Util {
    public static String iniName = "ini";
    //加载框变量
    public static ProgressDialog progressDialog;
    public static void writeIni(Context ctx, String name, String key, String val) {
        SharedPreferences sp = ctx.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if(val==null)val="";
        editor.putString(key, val);
        editor.apply();
        editor.commit();
    }


    public static String readIni(Context ctx, String name, String key, String def) {
        SharedPreferences sp = ctx.getSharedPreferences(name, Context.MODE_PRIVATE);
        return sp.getString(key, def);
    }
    public static void remove(Context ctx, String key) {
        SharedPreferences sp = ctx.getSharedPreferences(iniName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.apply();
        editor.commit();
    }
    public static void delete(Context ctx) {
        SharedPreferences sp = ctx.getSharedPreferences("name", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }

    public static void writeIni(Context ctx, String key, String val) {
        writeIni(ctx, iniName, key, val);
    }
    public static void writeIniPass(Context ctx, String key, String val) {
        writeIniPass(ctx, "pass", key, val);
    }
    public static void writeIniPass(Context ctx, String name, String key, String val) {
        SharedPreferences sp = ctx.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if(val==null)val="";
        editor.putString(key, val);
        editor.apply();
        editor.commit();
    }
    public static void writeIniName(Context ctx, String name, String key, String val) {
        SharedPreferences sp = ctx.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if(val==null)val="";
        editor.putString(key, val);
        editor.apply();
        editor.commit();
    }
    public static String readIniImage(Context ctx, String key, String def) {
        return readIni(ctx, "name", key, def);
    }
    public static void writeIniImage(Context ctx, String key, String val) {
        writeIniPass(ctx, "name", key, val);
    }
    public static String readIni(Context ctx, String key, String def) {
        return readIni(ctx, iniName, key, def);
    }

    public static void log(String log) {
        System.out.println("oglog:"+log);
    }

    public static void redirect(Context ctx, Class next) {
        redirect(ctx, next, null);
    }

    public static void redirect(Context ctx, Class next, Serializable obj) {
        Intent intent = new Intent(ctx, next);
        if(obj!=null) intent.putExtra("data", obj);
        ctx.startActivity(intent);
    }
    public static void redirect(Context ctx, Class next, Serializable obj, Serializable obj1) {
        Intent intent = new Intent(ctx, next);
        if(obj!=null) intent.putExtra("data", obj);
        ctx.startActivity(intent);
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static void isEditTextNull(Button btn, EditText editText1, EditText editText2, EditText editText3, Drawable drawable, Drawable drawable2){
        if (editText1.getText().toString().length()==0||editText3.getText().toString().length()==0||editText2.getText().toString().length()==0){
            btn.setBackground(drawable2);
            btn.setEnabled(false);
        }else {

            btn.setBackground(drawable);
            btn.setEnabled(true);
        }

    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static void isEditTextNull(Button btn, EditText editText1, EditText editText2, Drawable drawable, Drawable drawable2){
        if (editText1.getText().toString().length()==0||editText2.getText().toString().length()==0){
            btn.setBackground(drawable2);
            btn.setEnabled(false);
        }else {
            btn.setEnabled(true);
            btn.setBackground(drawable);
        }

    }
    public static boolean isMobileNO(String mobileNums) {
        /**
         * 判断字符串是否符合手机号码格式
         * 移动号段: 134,135,136,137,138,139,147,150,151,152,157,158,159,170,178,182,183,184,187,188
         * 联通号段: 130,131,132,145,155,156,170,171,175,176,185,186
         * 电信号段: 133,149,153,170,173,177,180,181,189
         * @param str
         * @return 待检测的字符串
         */
        String telRegex = "^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\\d{8}$";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobileNums))
            return false;
        else
            return mobileNums.matches(telRegex);
    }
    public static String getFilePath(Context context, String dir) {
        String directoryPath = "";
        if (MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {//判断外部存储是否可用
            directoryPath = context.getExternalFilesDir(dir).getAbsolutePath();
        } else {//没外部存储就使用内部存储
            directoryPath = context.getFilesDir() + File.separator + dir;
        }
        File file = new File(directoryPath);
        if (!file.exists()) {//判断文件目录是否存在
            file.mkdirs();
        }
        return directoryPath;
    }
    /**
     * 检测当的网络（WLAN、3G/2G）状态
     * @param context Context
     * @return true 表示网络可用
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected())
            {
                // 当前网络是连接的
                if (info.getState() == NetworkInfo.State.CONNECTED)
                {
                    // 当前所连接的网络可用
                    return true;
                }
            }
        }
        return false;
    }

    public static String str(String str) {
        return str==null?"":str;
    }

    public static void showProgressDialog(Context mContext, String text) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        }
        progressDialog.setMessage(text);	//设置内容
       //progressDialog.setCancelable(false);//点击屏幕和按返回键都不能取消加载框
        progressDialog.show();


    }

    public static void dissmissProgressDialog() {

        progressDialog.dismiss();


    }
    public static String get_Number(String str){

        str=str.trim();
        String str2="";
        if(str != null && !"".equals(str)){
            for(int i=0;i<str.length();i++){
                if(str.charAt(i)>=48 && str.charAt(i)<=57){
                    str2+=str.charAt(i);
                }
            }

        }
        return str2;
    }
    public static String jstr(JSONObject json, String key) {
        try {
            return str(json.getString(key));
        } catch (Exception e) {
        }
        return "";
    }
}
