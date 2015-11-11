/*
 * Copyright (C) 2010-2012 TENCENT Inc.All Rights Reserved.
 *
 * FileName: QQLiveLog.java
 *
 * Description:  LOG函数的封装
 *
 * History:
 *  1.0   kodywu (kodytx@gmail.com) 2011-1-6   Create
 */
package com.blue.leaves.util.log;

import android.os.Environment;

import com.blue.leaves.util.EmptyUtils;
import com.blue.leaves.util.Utils;

import java.io.File;

public final class Log {
    private static final String GLOABLE_TAG = "WepushLog";

    // debug
    private static final boolean RELEASE = false;
    // 发布时设置
    // private static final boolean RELEASE = true;
    private static final int NOT_SUPPORT = -1;
    public static final String CRASH_SAVE_FOLDER = Environment
            .getExternalStorageDirectory() + "/.Utopia";
    private static final String CRASH_SAVE_PATH = CRASH_SAVE_FOLDER
            + "/Utopia.log";
    public static final int DISABLED = 0;
    public static final int ERROR = 1;
    public static final int WARNING = 2;
    public static final int SYSTEM = 3;
    public static final int INFO = 4;
    public static final int DEBUG = 5;
    public static final int VERBOSE = 6;

    public static void printMessage(String file, int line,
                                    int level, String tag, String message) {
        android.util.Log.d(tag, message);
    }

    private Log() {

    }

    public static boolean isDebug() {
        return !RELEASE;
    }

    public static int d(String tag, String msg) {
        if (!RELEASE) {
            return logd(GLOABLE_TAG, "[=" + tag + "=] "
                    + msg);
        } else {
            return NOT_SUPPORT;
        }
    }

    public static int e(String tag, String msg) {
        if (!RELEASE) {
            return loge(GLOABLE_TAG, "[=" + tag + "=] "
                    + msg);
        } else {
            return NOT_SUPPORT;
        }

    }

    public static int e(String tag, Exception t) {
        return e(tag, t, "");
    }

    public static int e(String tag, Throwable t) {
        return e(tag, t, "");
    }

    public static int e(String tag, Throwable t, String msg) {
        String logMsg = "";
        if (!EmptyUtils.isEmpty(msg)) {
            logMsg = msg + "\n";
        }
        if (t != null) {
            logMsg = logMsg
                    + android.util.Log
                    .getStackTraceString(t);
        }
        return e(tag, logMsg);
    }

    public static int v(String tag, String msg) {
        if (!RELEASE) {
            return logv(GLOABLE_TAG, "[=" + tag + "=] "
                    + msg);
        } else {
            return NOT_SUPPORT;
        }
    }

    public static int i(String tag, String msg) {
        if (!RELEASE) {
            return logi(GLOABLE_TAG, "[=" + tag + "=] "
                    + msg);

        } else {
            return NOT_SUPPORT;
        }

    }

    public static int w(String tag, String msg) {
        if (!RELEASE) {
            return logw(GLOABLE_TAG, "[=" + tag + "=] "
                    + msg);
        } else {
            return NOT_SUPPORT;
        }
    }

    public static int s(String tag, String msg) {
        return logs(GLOABLE_TAG, "[=" + tag + "=] " + msg);
    }

    public static int consume(String msg) {
        return logs("consume", msg);
    }

    public static int t(String tag, String msg) {
        return logs(tag, msg);
    }

    public static int stopLog() {
        return NOT_SUPPORT;
    }

    public static String getLogPath() {
        return CRASH_SAVE_PATH;
    }

    // public static Logger mLog = null;
    public static int sequence;

    public static void clearCrashLog() {
        File folder = new File(CRASH_SAVE_FOLDER);
        if (folder != null) {
            File[] list = folder.listFiles();
            /**
             * crash日志目录下有icon和images两个目录用户存储图片，不用考虑
             */
            if (list != null && list.length > 2) {
                for (File f : list) {
                    if (f.isFile()
                            && f.getName() != null
                            && f.getName().endsWith(
                            ".stacktrace")) {
                        f.delete();
                    }
                }
            }
        }
    }

    private static int logd(String tag, String msg) {
        if (Utils.isLogInitialize) {
            Log.printMessage(null, 0, Log.DEBUG, tag, msg);
        }
        return 0;
    }

    private static int loge(String tag, String msg) {
        if (Utils.isLogInitialize) {
            Log.printMessage(null, 0, Log.ERROR, tag, msg);
        }
        return 0;
    }

    private static int logi(String tag, String msg) {
        if (Utils.isLogInitialize) {
            Log.printMessage(null, 0, Log.INFO, tag, msg);
        }
        return 0;
    }

    private static int logv(String tag, String msg) {
        if (Utils.isLogInitialize) {
            Log.printMessage(null, 0, Log.VERBOSE, tag, msg);
        }
        return 0;
    }

    private static int logw(String tag, String msg) {
        if (Utils.isLogInitialize) {
            Log.printMessage(null, 0, Log.WARNING, tag, msg);
        }
        return 0;
    }

    private static int logs(String tag, String msg) {
        if (Utils.isLogInitialize) {
            Log.printMessage(null, 0, Log.SYSTEM, tag, msg);
        }
        return 0;
    }

}
