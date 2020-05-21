package com.ant.antdate.db;

import android.content.Context;

import com.ant.antdate.base.App;

import java.io.File;
import java.lang.reflect.Method;

import javax.annotation.Nonnull;

import io.objectbox.Box;
import io.objectbox.BoxStore;

/**
 * Created by shuaqq on 2017/11/7.
 */

public class LogicDB {
    private final static String TAG = LogicDB.class.getSimpleName();

    private final static String[] BOXNAME = new String[]{"objectbox", "boxobject"};

    private static LogicDB logicDB;

    private BoxStore mBoxStore;

    private LogicDB(App app) {
        initBox(app);
//        if (App.DEBUG) {
//            new AndroidObjectBrowser(mBoxStore).start(app);
//        }
    }

    private void initBox(App app) {
//        int namePos = SpHelper.getInstance(app).getInt("boxname", 0);
//        try {
//            mBoxStore = MyObjectBox.builder().androidContext(app).name(BOXNAME[namePos]).build();
//            Log.i(TAG, "Build " + BOXNAME[namePos] + " DB success!");
//        } catch (DbException e) {
//            e.printStackTrace();
//            Log.e(TAG, "Build " + BOXNAME[namePos] + " DB fail,try to build another one");
//            int finalNamePos = namePos;
//            Flowable.create((FlowableOnSubscribe<String>) e1 -> BoxStore.deleteAllFiles(new File(androidContext(app), BOXNAME[finalNamePos])), BackpressureStrategy.BUFFER).subscribe();
//            namePos = 1 - namePos;
//            BoxStoreBuilder builder = MyObjectBox.builder().androidContext(app).name(BOXNAME[namePos]);
//            mBoxStore = builder.build();
//            SpHelper.getInstance(app).setInt("boxname", namePos);
//            Log.i(TAG, "Build " + BOXNAME[namePos] + " DB success");
//        }
    }

    public File androidContext(Context context) {
        if (context == null) {
            throw new NullPointerException("Context may not be null");
        }
        File filesDir = getAndroidFilesDir(context);
        File baseDir = new File(filesDir, "objectbox");
        if (!baseDir.exists()) {
            baseDir.mkdir();
            if (!baseDir.exists()) { // check baseDir.exists() because of potential concurrent processes
                throw new RuntimeException("Could not init Android base dir at " + baseDir.getAbsolutePath());
            }
        }
        if (!baseDir.isDirectory()) {
            throw new RuntimeException("Android base dir is not a dir: " + baseDir.getAbsolutePath());
        }
        return baseDir;
    }

    @Nonnull
    private File getAndroidFilesDir(Context context) {
        File filesDir;
        try {
            Method getFilesDir = context.getClass().getMethod("getFilesDir");
            filesDir = (File) getFilesDir.invoke(context);
            if (filesDir == null) {
                // Race condition in Android before 4.4: https://issuetracker.google.com/issues/36918154 ?
                System.err.println("getFilesDir() returned null - retrying once...");
                filesDir = (File) getFilesDir.invoke(context);
            }
        } catch (Exception e) {
            throw new RuntimeException(
                    "Could not init with given Android context (must be sub class of android.content.Context)", e);
        }
        if (filesDir == null) {
            throw new IllegalStateException("Android files dir is null");
        }
        if (!filesDir.exists()) {
            throw new IllegalStateException("Android files dir does not exist");
        }
        return filesDir;
    }

    public static void init(App app) {
        if (logicDB == null)
            logicDB = new LogicDB(app);
        else
            logicDB.initBox(app);
    }

    public static <T> Box<T> getBox(Class<T> clazz) {
        return logicDB.mBoxStore.boxFor(clazz);
    }
}
