package com.blue.leaves.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.util.Base64;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

/**
 * Created by
 * Author: wswenyue
 * Email: wswenyue@163.com
 * Date: 2015/10/28
 * GitHub: https://github.com/wswenyue
 */

/**
 * 通用的工具类、放一些常用的工具
 */
public final class Common {
    /**
     * 对给定的字符串返回唯一的标记字符串<br>
     * 主要应用于网络url的标记，将url转换映射成唯一的标识字符串<br>
     * 写法参考Volley中的写法<br>
     *
     * @param str 需要转换的字符串
     * @return 返回唯一的标识
     */
    public static String toHash(String str) {
        String ret = null;
        if (str != null && str.length() > 0) {
            int len = str.length();
            String part1 = str.substring(0, len / 2).hashCode() + "";
            String part2 = str.substring(len / 2).hashCode() + "";
            ret = part1 + part2;
        }
        return ret;
    }

    /**
     * 对数据（字节）进行Base64编码
     *
     * @param data 要编码的数据（字节数组）
     * @return 返回编码后的字符串
     */
    public static String Base64Encode(byte[] data) {
        String ret = null;
        if (data != null && data.length > 0) {
            ret = Base64.encodeToString(data, Base64.NO_WRAP);
        }
        return ret;
    }

    /**
     * 对Base64编码后的数据进行还原
     *
     * @param data 使用Base64编码过的数据
     * @return 返回还原后的数据（字节数组）
     */
    public static byte[] Base64Decode(String data) {
        byte[] ret = null;
        if (data != null && data.length() > 0) {
            ret = Base64.decode(data, Base64.NO_WRAP);
        }
        return ret;
    }

    /**
     * 使用MD5获取数据的摘要信息
     *
     * @param data 数据
     * @return 摘要信息
     */
    public static String toMD5(byte[] data) {
        String ret = null;
        try {
            byte[] digest = MessageDigest.getInstance("md5").digest(data);
            ret = Base64Encode(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 打开或关闭WIFI
     *
     * @param mContext Context
     * @param action   打开使用on 关闭使用off
     */
    public static void onWifi(Context mContext, String action) {
        WifiManager wm = ((WifiManager) mContext
                .getSystemService(Context.WIFI_SERVICE));
        if (action.toLowerCase().equalsIgnoreCase("on")) {
            if (!wm.isWifiEnabled()) {
                wm.setWifiEnabled(true);
            }
        }

        if (action.toLowerCase().equalsIgnoreCase("off")) {
            if (wm.isWifiEnabled()) {
                wm.setWifiEnabled(false);
            }
        }
    }

    /**
     * 隐藏键盘
     *
     * @param mContext
     * @param v
     */
    public static void hideKeyboard(Context mContext, View v) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static final String FILE_PATH_HEADER = "file://";

    public static final String APP_PACKAGE_ARCHIVE = "application/vnd.android.package-archive";
    public static final String EXTRA_INSTALLER_PACKAGE_NAME = "android.intent.extra.INSTALLER_PACKAGE_NAME";

    public static void installApk(Context context,
                                  String apkPath) {
        L.d("file", "ApkUtil installApk:" + apkPath);
        if (new File(apkPath).exists()) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(
                    Uri.parse(FILE_PATH_HEADER + apkPath),
                    APP_PACKAGE_ARCHIVE);
            intent.putExtra(EXTRA_INSTALLER_PACKAGE_NAME,
                    apkPath);
            context.startActivity(intent);
        } else {
        }
    }

    /*获取内存大小*/
    private static long getPhoneMemFromFile() {
        long mTotal = 0L;
        String path = "/proc/meminfo";
        String content = null;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(path), 8);
            String line;
            if ((line = br.readLine()) != null) {
                content = line;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                return 0L;
            }
        }

        if (content != null) {
            // beginIndex
            int begin = content.indexOf(':');
            // endIndex
            int end = content.indexOf('k');

            content = content.substring(begin + 1, end)
                    .trim();
            mTotal = Integer.parseInt(content);
        }
        return mTotal;
    }

    /*获取CPU数量*/
    public static int getNumCores() {
        class CpuFilter implements FileFilter {
            @Override
            public boolean accept(File pathname) {
                if (Pattern.matches("cpu[0-9]",
                        pathname.getName())) {
                    return true;
                }
                return false;
            }
        }

        try {
            File dir = new File("/sys/devices/system/cpu/");
            // Filter to only list the devices we care about
            File[] files = dir.listFiles(new CpuFilter());
            // Return the number of cores (virtual CPU devices)
            if (files != null) {
                return files.length;
            } else {
                return 1;
            }
        } catch (Exception e) {
            // Default to return 1 core
            return 1;
        }
    }

    /*获取堆栈大小*/
    public static int getHeapSize(Context context) {
        return ((ActivityManager) context.getSystemService(
                Context.ACTIVITY_SERVICE))
                .getMemoryClass();
    }

    public static int getLargeMemoryClass(Context context) {
        return ((ActivityManager) context.getSystemService(
                Context.ACTIVITY_SERVICE))
                .getLargeMemoryClass();
    }
}
