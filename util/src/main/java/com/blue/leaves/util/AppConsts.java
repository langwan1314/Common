/**
 *
 */
package com.blue.leaves.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;


import com.blue.leaves.util.log.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author zhixiongdu
 */
public class AppConsts {
    private static final String TAG = "AppConsts";

    // 终端设备唯一标识
    private static String guid;

    // app的版本号
    private static String appVersionName;

    // app
    private static int appVersionCode = 0;

    // 系统参数 start
    private static String IMEI;
    private static String IMSI;
    private static String MAC;
    private static int MCC;
    private static int MNC;
    private static String mAndroidVersion;
    private static String mModel;

    // 系统参数 end
    public static String getGuid() {
        return guid;
    }

    public static void setGuid(String guid) {
        if (!TextUtils.equals(AppConsts.guid, guid)) {
            AppConsts.guid = guid;
        }
    }

    /**
     * 返回设备id，按道理设备应该是终端的imei号或者mac地址，但此处为了跟后台对齐，deviceid都表示是guid
     */
    public static String getDevicId() {
        return getGuid();
    }

    private static String DOT = "\\.";

    public static String getAppVersionName() {
        if (EmptyUtils.isEmpty(appVersionName)) {
            try {
                Context ctx = Utils
                        .getAppContext();
                PackageManager pm = ctx.getPackageManager();
                String pkgName = ctx.getPackageName();
                PackageInfo pkgInfo = pm.getPackageInfo(
                        pkgName, 0);
                appVersionName = pkgInfo.versionName;
            } catch (Exception e) {
                Log.e("AppConsts", e);
            }
        }
        return appVersionName;
    }

    public static synchronized int getAppVersionCode() {
        if (0 == appVersionCode) {
            try {
                Context ctx = Utils
                        .getAppContext();
                PackageManager pm = ctx.getPackageManager();
                String pkgName = ctx.getPackageName();
                PackageInfo pkgInfo = pm.getPackageInfo(
                        pkgName, 0);
                appVersionCode = pkgInfo.versionCode;

            } catch (Exception e) {
                Log.e(TAG, e);
            }
        }
        return appVersionCode;
    }

    public static String getAppVersion(String versionName) {
        String tempVersion = "1.0.0";
        if (!TextUtils.isEmpty(versionName)) {
            String[] tempStrings = versionName.split(DOT);
            if (tempStrings != null && tempStrings.length == 4) {
                tempVersion = versionName.substring(0, versionName.lastIndexOf("."));
            } else {
                tempVersion = versionName;
            }
        }
        Log.d(TAG, "temp is" + tempVersion);
        return tempVersion;
    }

    public static String getIMEI_MD5() {
        if (!EmptyUtils.isEmpty(IMEI)) {
            try {
                // 拿到一个MD5转换器（如果想要SHA1参数换成”SHA1”）
                MessageDigest messageDigest = MessageDigest
                        .getInstance("MD5");
                // inputByteArray是输入字符串转换得到的字节数组
                messageDigest.update(IMEI.getBytes());
                // 转换并返回结果，也是字节数组，包含16个元素
                byte[] resultByteArray = messageDigest
                        .digest();
                // 字符数组转换成字符串返回
                return byteArrayToHex(resultByteArray);
            } catch (NoSuchAlgorithmException e) {
                return null;
            }
        }
        return null;
    }

    public static String byteArrayToHex(byte[] byteArray) {
        if (byteArray != null) {
            // 首先初始化一个字符数组，用来存放每个16进制字符
            char[] hexDigits = {'0', '1', '2', '3', '4',
                    '5', '6', '7', '8', '9', 'A', 'B', 'C',
                    'D', 'E', 'F'};
            // new一个字符数组，这个就是用来组成结果字符串的（解释一下：一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方））
            char[] resultCharArray = new char[byteArray.length * 2];
            // 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去
            int index = 0;
            for (byte b : byteArray) {
                resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
                resultCharArray[index++] = hexDigits[b & 0xf];
            }
            // 字符数组组合成字符串返回
            return new String(resultCharArray);
        }
        return null;
    }

    public static String getIMEI() {
        return IMEI;
    }

    public static void setIMEI(String iMEI) {
        IMEI = iMEI;
    }

    public static String getIMSI() {
        return IMSI;
    }

    public static void setIMSI(String iMSI) {
        IMSI = iMSI;
    }

    public static String getMAC() {
        return MAC;
    }

    public static void setMAC(String mAC) {
        MAC = mAC;
    }

    public static int getMCC() {
        return MCC;
    }

    public static void setMCC(int mCC) {
        MCC = mCC;
    }

    public static int getMNC() {
        return MNC;
    }

    public static void setMNC(int mNC) {
        MNC = mNC;
    }

    public static String getAndroidVersion() {
        return mAndroidVersion;
    }

    public static void setAndroidVersion(
            String mAndroidVersion) {
        AppConsts.mAndroidVersion = mAndroidVersion;
    }

    public static String getModel() {
        return mModel;
    }

    public static void setModel(String mModel) {
        AppConsts.mModel = mModel;
    }

    public static final int READ_BUFFER = 1024;

    public static final String UTF8 = "utf-8";

    // SharedPreference 文件名定义
    public static final String SP_SHORT_CUT = "sp_short_cut";
    public static final String SP_APP = "com.leaves.blue_sp";
}
