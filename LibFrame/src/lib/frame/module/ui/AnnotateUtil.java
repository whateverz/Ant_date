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
package lib.frame.module.ui;

import android.app.Activity;
import android.content.Context;

import android.view.View;
import android.view.View.OnClickListener;

import androidx.fragment.app.Fragment;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 注解工具类<br>
 * <p/>
 * <b>创建时间</b> 2014-6-5
 *
 * @author kymjs(kymjs123@gmail.com)
 * @version 1.1
 */
public class AnnotateUtil {
    /**
     * @param currentClass 当前类，一般为Activity或Fragment
     * @param sourceView   待绑定控件的直接或间接父控件
     */
    public static void initBindView(Object currentClass, View sourceView) {
        // 通过反射获取到全部属性，反射的字段可能是一个类（静态）字段或实例字段
        Field[] fields = currentClass.getClass().getDeclaredFields();
        if (fields != null && fields.length > 0) {
            for (Field field : fields) {
                // 返回BindView类型的注解内容
//                Log.i(currentClass.getClass().getSimpleName(), field.getName());
                BindView bindView = field.getAnnotation(BindView.class);
                if (bindView != null) {
                    int viewId = bindView.value();
                    if (viewId == View.NO_ID)
                        viewId = bindView.id();
                    boolean clickLis = bindView.click();
                    try {
                        field.setAccessible(true);
                        View mView = sourceView.findViewById(viewId);
                        if (clickLis) {
                            mView.setOnClickListener(
                                    (OnClickListener) currentClass);
                        }
                        // 将currentClass的field赋值为sourceView.findViewById(viewId)
                        field.set(currentClass, mView);
//                        Log.i("lwxkey", "initBindView -- "
//                                + "  " + field.getName());
                    } catch (Exception e) {
                        e.printStackTrace();
//                        Log.i("lwxkey", "initBindView -- "
//                                + "  " + field.getName());
                    }
                }
            }
        }
        initClick(currentClass, sourceView);
    }

    public static void initClick(Object currentClass, View sourceView) {
        try {
            Method method = currentClass.getClass().getDeclaredMethod("onClick", View.class);
            if (method == null)
                return;
            OnClick click = method.getAnnotation(OnClick.class);
            if (click == null)
                return;
            int[] ids = click.value();
            if (currentClass instanceof OnClickListener)
                for (int id : ids) {
                    if (id != View.NO_ID) {
                        View view = sourceView.findViewById(id);
                        if (view != null)
                            view.setOnClickListener((OnClickListener) currentClass);
                    }
                }
        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
        }
    }

    public static void initClick(Activity aty) {
        initClick(aty, aty.getWindow().getDecorView());
    }

    public static void initClick(View view) {
        Context cxt = view.getContext();
        if (cxt instanceof Activity) {
            initClick((Activity) cxt);
        } else {
            throw new RuntimeException("view must into Activity");
        }
    }

    public static void initClick(Fragment frag) {
        initClick(frag, frag.getActivity().getWindow().getDecorView());
    }

    public static void initClickWidget(View view) {
        initClick(view, view.getRootView());
    }

    /**
     * 必须在setContentView之后调用
     *
     * @param aty
     */

    public static void initBindView(Activity aty) {
        initBindView(aty, aty.getWindow().getDecorView());
    }

    /**
     * 必须在setContentView之后调用
     *
     * @param view 侵入式的view，例如使用inflater载入的view
     */
    public static void initBindView(View view) {
        Context cxt = view.getContext();
        if (cxt instanceof Activity) {
            initBindView((Activity) cxt);
        } else {
            throw new RuntimeException("view must into Activity");
        }
    }

    /**
     * 必须在setContentView之后调用
     *
     * @param frag
     */
    public static void initBindView(Fragment frag) {
        initBindView(frag, frag.getActivity().getWindow().getDecorView());
    }

    // 自定义控件资源绑定，在导入资源后调用
    public static void initBindWidget(View view) {
        initBindView(view, view.getRootView());
    }

}
