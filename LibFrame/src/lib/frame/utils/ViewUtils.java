/*
 * Copyright (c) 2014,KJFrameForAndroid Open Source Project,张涛.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package lib.frame.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.text.InputFilter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lib.frame.R;
import lib.frame.view.recyclerView.AdapterRecyclerView;
import lib.frame.view.recyclerView.RecyclerView;

/**
 * 系统界面工具类<br>
 * <p/>
 * <b>创建时间</b> 2014-8-14
 *
 * @author kymjs(kymjs123 @ gmail.com)
 * @version 1.1
 */
public class ViewUtils {
    /**
     * 截图
     *
     * @param v 需要进行截图的控件
     * @return 该控件截图的Bitmap对象。
     */
    public static Bitmap captureView(View v) {
        v.setDrawingCacheEnabled(true);
        v.buildDrawingCache();
        return v.getDrawingCache();
    }

    /**
     * 创建快捷方式
     *
     * @param cxt   Context
     * @param icon  快捷方式图标
     * @param title 快捷方式标题
     * @param cls   要启动的类
     */
    public void createDeskShortCut(Context cxt, int icon, String title,
                                   Class<?> cls) {
        // 创建快捷方式的Intent
        Intent shortcutIntent = new Intent(
                "com.android.launcher.action.INSTALL_SHORTCUT");
        // 不允许重复创建
        shortcutIntent.putExtra("duplicate", false);
        // 需要现实的名称
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, title);
        // 快捷图片
        Parcelable ico = Intent.ShortcutIconResource.fromContext(
                cxt.getApplicationContext(), icon);
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, ico);
        Intent intent = new Intent(cxt, cls);
        // 下面两个属性是为了当应用程序卸载时桌面上的快捷方式会删除
        intent.setAction("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.LAUNCHER");
        // 点击快捷图片，运行的程序主入口
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);
        // 发送广播。OK
        cxt.sendBroadcast(shortcutIntent);
    }

    public static void boldText(TextView tv) {
        Paint tp = tv.getPaint();
        tp.setFakeBoldText(true);
    }

    public static boolean isListViewReachTopEdge(ListView listView) {
        boolean result = false;
        if (listView.getFirstVisiblePosition() == 0) {
            View topChildView = listView.getChildAt(0);
            result = topChildView.getTop() == 0;
        }
        return result;
    }


    public static void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0); // 计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }

    public static void setListViewHeightBasedOnChildren(RecyclerView listView, int dividerHeight) {
        // 获取ListView对应的Adapter
        AdapterRecyclerView listAdapter = (AdapterRecyclerView) listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getItemCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目
            View listItem = listView.getChildAt(i);//  listAdapter.(i, null, listView);
            listItem.measure(0, 0); // 计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (dividerHeight * (listAdapter.getItemCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }

    public static void setGridViewHeightBasedOnChildren(GridView gView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = gView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int nLine = listAdapter.getCount() / gView.getNumColumns();
        if (listAdapter.getCount() % gView.getNumColumns() > 0) {
            nLine++;
        }
        for (int i = 0, len = nLine; i < len; i++) { // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, gView);
            listItem.measure(0, 0); // 计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
        }

        ViewGroup.LayoutParams params = gView.getLayoutParams();
        params.height = totalHeight;
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        gView.setLayoutParams(params);
    }

    public static void setTextViewLeftImg(Context context, TextView textView, int resId) {
        if (resId > 0) {
            Drawable drawable = context.getResources().getDrawable(resId);
/// 这一步必须要做,否则不会显示.
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            textView.setCompoundDrawablePadding(context.getResources().getDimensionPixelSize(R.dimen.new_8px));
            textView.setCompoundDrawables(drawable, null, null, null);
        } else {
            textView.setCompoundDrawables(null, null, null, null);
        }
    }

    public static void setTextViewRightImg(Context context, TextView textView, int resId) {
        if (resId > 0) {
            Drawable drawable = context.getResources().getDrawable(resId);
/// 这一步必须要做,否则不会显示.
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            textView.setCompoundDrawablePadding(context.getResources().getDimensionPixelSize(R.dimen.new_8px));
            textView.setCompoundDrawables(null, null, drawable, null);
        } else {
            textView.setCompoundDrawables(null, null, null, null);
        }
    }

    public static void setTextViewTopImg(Context context, TextView textView, int resId) {
        if (resId > 0) {
            Drawable drawable = context.getResources().getDrawable(resId);
/// 这一步必须要做,否则不会显示.
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            textView.setCompoundDrawablePadding(context.getResources().getDimensionPixelSize(R.dimen.new_8px));
            textView.setCompoundDrawables(null, drawable, null, null);
        } else {
            textView.setCompoundDrawables(null, null, null, null);
        }
    }

    /**
     * 禁止EditText输入空格
     *
     * @param editText
     */
    public static void setEditTextInhibitInputSpace(EditText editText) {
        InputFilter filter = (source, start, end, dest, dstart, dend) -> {
            if (source.equals(" "))
                return "";
            else
                return null;
        };
        editText.setFilters(new InputFilter[]{filter});
    }

    /**
     * 禁止EditText输入特殊字符
     *
     * @param editText
     */
    public static void setEditTextInhibitInputSpeChat(EditText editText, int length) {

        InputFilter filter = (source, start, end, dest, dstart, dend) -> {
            String speChat = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？ ]";
            Pattern pattern = Pattern.compile(speChat);
            Matcher matcher = pattern.matcher(source.toString());
            if (matcher.find()) return "";
            else return null;
        };
        if (length == 0)
            editText.setFilters(new InputFilter[]{filter});
        else
            editText.setFilters(new InputFilter[]{filter, new InputFilter.LengthFilter(length)});

    }
}
