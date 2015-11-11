package com.blue.leaves.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

public class AppSPUtils {

    // 0x0004 = Context.MODE_MULTI_PROCESS
    private final static int MODE_SPEC = android.os.Build.VERSION.SDK_INT <= 10 ? 0
            : Context.MODE_MULTI_PROCESS;

    public static SharedPreferences getSharedPreferences(
            String name) {
        return Utils.getAppContext()
                .getSharedPreferences(name,
                        Context.MODE_PRIVATE | MODE_SPEC);
    }

    public static SharedPreferences getAppSharedPreferences() {
        return getSharedPreferences(AppConsts.SP_APP);
    }

    public static boolean getValueFromPrefrences(
            SharedPreferences preferences, String key,
            boolean defaultValue) {
        boolean result = defaultValue;
        try {
            if (null != preferences) {
                result = preferences.getBoolean(key,
                        defaultValue);
            }
        } catch (Exception e) {
        }

        return result;
    }

    public static int getValueFromPrefrences(
            SharedPreferences preferences, String key,
            int defaultValue) {
        int result = defaultValue;
        try {
            if (null != preferences) {
                result = preferences.getInt(key,
                        defaultValue);
            }
        } catch (Exception e) {

        }
        return result;
    }

    public static long getValueFromPrefrences(
            SharedPreferences preferences, String key,
            long defaultValue) {
        long result = defaultValue;
        try {
            if (null != preferences) {
                result = preferences.getLong(key,
                        defaultValue);
            }
        } catch (Exception e) {

        }
        return result;
    }

    public static String getValueFromPrefrences(
            SharedPreferences preferences, String key,
            String defaultValue) {
        String result = defaultValue;
        try {
            if (null != preferences) {
                result = preferences.getString(key,
                        defaultValue);
            }
        } catch (Exception e) {

        }

        return result;
    }

    public static boolean getValueFromPrefrences(
            String key, boolean defaultValue) {
        return getValueFromPrefrences(
                getAppSharedPreferences(), key,
                defaultValue);
    }

    public static int getValueFromPrefrences(String key,
            int defaultValue) {
        return getValueFromPrefrences(
                getAppSharedPreferences(), key,
                defaultValue);
    }

    public static long getValueFromPrefrences(String key,
            long defaultValue) {
        return getValueFromPrefrences(
                getAppSharedPreferences(), key,
                defaultValue);
    }

    public static String getValueFromPrefrences(String key,
            String defaultValue) {
        return getValueFromPrefrences(
                getAppSharedPreferences(), key,
                defaultValue);
    }

    public static void setValueToPrefrences(
            SharedPreferences preferences, String key,
            boolean value) {
        try {
            if (null != preferences) {
                preferences.edit().putBoolean(key, value)
                        .commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setValueToPrefrences(
            SharedPreferences preferences, String key,
            long value) {
        try {
            if (null != preferences) {
                preferences.edit().putLong(key, value)
                        .commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setValueToPrefrences(
            SharedPreferences preferences, String key,
            int value) {
        try {
            if (null != preferences) {
                preferences.edit().putInt(key, value)
                        .commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setValueToPrefrences(
            SharedPreferences preferences, String key,
            String value) {
        try {
            if (null != preferences) {
                preferences.edit().putString(key, value)
                        .commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setValueToPrefrences(String key,
            boolean value) {
        setValueToPrefrences(getAppSharedPreferences(),
                key, value);
    }

    public static void setValueToPrefrences(String key,
            long value) {
        setValueToPrefrences(getAppSharedPreferences(),
                key, value);
    }

    public static void setValueToPrefrences(String key,
            int value) {
        setValueToPrefrences(getAppSharedPreferences(),
                key, value);
    }

    public static void setValueToPrefrences(String key,
            String value) {
        setValueToPrefrences(getAppSharedPreferences(),
                key, value);
    }

    public static void putValueToPrefrences(Editor editor,
            String key, boolean value) {
        try {
            if (null != editor) {
                editor.putBoolean(key, value);
            }
        } catch (Exception e) {

        }
    }

    public static void putValueToPrefrences(Editor editor,
            String key, long value) {
        try {
            if (null != editor) {
                editor.putLong(key, value);
            }
        } catch (Exception e) {

        }
    }

    public static void putValueToPrefrences(Editor editor,
            String key, int value) {
        try {
            if (null != editor) {
                editor.putInt(key, value);
            }
        } catch (Exception e) {
        }
    }

    public static void putValueToPrefrences(Editor editor,
            String key, String value) {
        try {
            if (null != editor) {
                editor.putString(key, value);
            }
        } catch (Exception e) {

        }
    }

    public static void removeSharePrefrenceKey(
            Editor editor, String key) {
        try {
            if (!TextUtils.isEmpty(key) && null != editor) {
                editor.remove(key);
            }
        } catch (Exception e) {

        }
    }

    public static void removeSharePrefrenceKey(String key) {
        try {
            if (!TextUtils.isEmpty(key)) {
                getAppSharedPreferences().edit()
                        .remove(key).commit();
            }
        } catch (Exception e) {

        }
    }

    /**
     * *************************************************************************
     * *************************************************************************
     * ****** 各业务模块的配置存取可以从这开始定义
     * ************************************************
     * **************************
     * ************************************************
     * ******************************
     */

    // 播放器屏幕选择相关
    private static final String IS_LAUNCH_AUTO_ROATE_SCREEN = "is_launch_auto_roate_screen";

    public static boolean isAutoRoateScreen() {
        return getValueFromPrefrences(
                IS_LAUNCH_AUTO_ROATE_SCREEN, true);
    }

    public static void setAutoRoateScreen(boolean open) {
        setValueToPrefrences(IS_LAUNCH_AUTO_ROATE_SCREEN,
                open);
    }

    private static final String FULL_SCREEN_LOCK_ORIENTATION = "full_screen_lock_orientation";

    public static int isFullScreenOrientation() {
        return getValueFromPrefrences(
                FULL_SCREEN_LOCK_ORIENTATION, 0);
    }

    public static void setFullScreenOrientation(
            int orientation) {
        setValueToPrefrences(FULL_SCREEN_LOCK_ORIENTATION,
                orientation);
    }

    // 牛奶瓶的开始时间标签
    private static final String LIVE_MILK_BOTTLE_TIME_STAMP = "live_milk_bottle_time_stamp";

    public static long getLiveMilkBottleStartTime() {
        return getValueFromPrefrences(
                LIVE_MILK_BOTTLE_TIME_STAMP, 0L);
    }

    public static void setLiveMilkBottleStartTime(long time) {
        setValueToPrefrences(LIVE_MILK_BOTTLE_TIME_STAMP,
                time);
    }

    // 牛奶瓶的检查次数
    private static final String LIVE_MILK_BOTTLE_SHOW_TIMES = "live_milk_bottle_show_times";

    public static int getLiveMilkBottleShowTimes() {
        return getValueFromPrefrences(
                LIVE_MILK_BOTTLE_SHOW_TIMES, 0);
    }

    public static void setLiveMilkBottleShowTimes(int times) {
        setValueToPrefrences(LIVE_MILK_BOTTLE_SHOW_TIMES,
                times);
    }

    // 牛奶瓶的检查次数
    private static final String LIVE_PLAY_TOTAL_TIME = "live_play_total_time";

    public static long getLivePlayTotalTime() {
        return getValueFromPrefrences(LIVE_PLAY_TOTAL_TIME,
                0L);
    }

    public static void setLivePlayTotalTime(long time) {
        setValueToPrefrences(LIVE_PLAY_TOTAL_TIME, time);
    }

    // 牛奶瓶的检查次数
    private static final String DANMUN_SWITCH = "danmun_switch";

    public static boolean getDanmunSwitch() {
        return getValueFromPrefrences(DANMUN_SWITCH, false);
    }

    public static void setDanmunSwitch(boolean isOpen) {
        setValueToPrefrences(DANMUN_SWITCH, isOpen);
    }
}
