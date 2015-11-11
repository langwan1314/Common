package com.blue.leaves.util;

import android.content.Context;

/**
 * Created by Administrator on 2015/7/31.
 */
public class Utils {
    private static Context mContext;
    public static boolean isLogInitialize = true;

    public static void init() {
    }


    void setAppContext(Context context) {
        mContext = context;
    }

    public static Context getAppContext() {
        return mContext;
    }
}