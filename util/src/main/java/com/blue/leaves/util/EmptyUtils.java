/*
 * Copyright (C) 2010-2013 TENCENT Inc.All Rights Reserved.
 *
 * Description:  与应用和业务无关的工具类
 *
 * History:
 *  1.0   dancycai (dancycai@tencent.com) 2013-7-10   Created
 */

package com.blue.leaves.util;

import java.util.Collection;
import java.util.Formatter;
import java.util.Locale;
import java.util.Map;

public class EmptyUtils {
    private static final String TAG = "Utils";
    private static final boolean DEBUG = false;

    public static boolean isEmpty(final String str) {
        return str == null || str.length() <= 0;
    }

    public static boolean isEmpty(
            final Collection<? extends Object> collection) {
        return collection == null || collection.size() <= 0;
    }

    public static boolean isEmpty(
            final Map<? extends Object, ? extends Object> list) {
        return list == null || list.size() <= 0;
    }

    public static boolean isEmpty(final byte[] bytes) {
        return bytes == null || bytes.length <= 0;
    }

    public static boolean isEmpty(final String[] strArr) {
        return strArr == null || strArr.length <= 0;
    }

    public static int nullAs(final Integer obj,
            final int def) {
        return obj == null ? def : obj;
    }

    public static long nullAs(final Long obj, final long def) {
        return obj == null ? def : obj;
    }

    public static boolean nullAs(final Boolean obj,
            final boolean def) {
        return obj == null ? def : obj;
    }

    public static String nullAs(final String obj,
            final String def) {
        return obj == null ? def : obj;
    }

    public static String emptyAs(final String obj,
            final String def) {
        return isEmpty(obj) ? def : obj;
    }

    public static int nullAsNil(final Integer obj) {
        return obj == null ? 0 : obj;
    }

    public static long nullAsNil(final Long obj) {
        return obj == null ? 0L : obj;
    }

    public static String nullAsNil(final String obj) {
        return obj == null ? "" : obj;
    }

    public static int optInt(final String string,
            final int def) {
        try {
            if (!isEmpty(string)) {
                return Integer.parseInt(string);
            }
        } catch (NumberFormatException e) {
            L.e(TAG, e.toString());
        }
        return def;
    }

    public static long optLong(final String string,
            final long def) {
        try {
            if (!isEmpty(string)) {
                return Long.parseLong(string);
            }
        } catch (NumberFormatException e) {
            L.e(TAG, e.toString());
        }
        return def;
    }

    // 时间转字符串函数
    public static String time2String(int timeMs) {
        if (timeMs == 0) {
            return "";
        }
        StringBuffer mStringBuffer = new StringBuffer();
        int seconds = timeMs % 60;
        int minutes = (timeMs / 60) % 60;
        int hours = timeMs / 3600;

        if (hours > 0) {
            return extracted(mStringBuffer)
                    .format("%d:%02d:%02d", hours, minutes,
                            seconds).toString();
        } else {
            return extracted(mStringBuffer).format(
                    "%02d:%02d", minutes, seconds)
                    .toString();
        }
    }

    public static Formatter extracted(
            StringBuffer mStringBuffer) {
        return new Formatter(mStringBuffer,
                Locale.getDefault());
    }

    /*
     * 把int型转换为带分隔符的string，如：2456转化为2,456
     */
    public static String intToSpecialString(int num) {
        String ret = num + "";
        int length = ret.length();
        if (length > 0) {
            int delimiterNum = (length - 1) / 3;
            for (int i = 0; i < delimiterNum; i++) {
                int midPos = length - (i + 1) * 3;
                ret = ret.substring(0, midPos)
                        + ","
                        + ret.substring(midPos,
                                ret.length());
            }
        }
        return ret;
    }

    /**
     * 使用java正则表达式去掉多余的.与0
     * 
     * @param s
     * @return
     */
    public static String subZeroAndDot(String s) {
        if (s.indexOf(".") > 0) {
            s = s.replaceAll("0+?$", "");// 去掉多余的0
            s = s.replaceAll("[.]$", "");// 如最后一位是.则去掉
        }
        return s;
    }

}
