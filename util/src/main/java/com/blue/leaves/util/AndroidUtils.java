package com.blue.leaves.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings.Secure;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;
import java.util.regex.Pattern;

@SuppressWarnings("deprecation")
public class AndroidUtils {

    private static final String TAG = "MobileUtil";

    public static final String PT = "8";

    private static String imei = null;
    private static String mac = null;

    private static String systemVersion = null;

    private static String packageName = null;

    private static int versionCode = 0;

    private static String versionName = null;

    private static String sIMSI = null;
    private static String firstImsi = null;
    private static String secondImsi = null;
    private static String productType = null;
    private static long phoneMemory = 0L;

    public static final int START_FROM_NORMAL = 0;
    public static final int START_FROM_NEW_INSTALL = 1;
    public static final int START_FROM_UPDATE = 2;

    /**
     * 移动步径大小
     */
    private static int scaledTouchSlop = -1;

    public static boolean hasFroyo() {
        // Can use static final constants like FROYO, declared in later versions
        // of the OS since they are inlined at compile time. This is guaranteed
        // behavior.
        return Build.VERSION.SDK_INT >= 8;
    }

    public static boolean hasGingerbread() {
        return Build.VERSION.SDK_INT >= 9;
    }

    public static boolean hasHoneycomb() {
        return Build.VERSION.SDK_INT >= 11;
    }

    public static boolean hasHoneycombMR1() {
        return Build.VERSION.SDK_INT >= 12;
    }

    public static boolean hasHoneycombMR2() {
        return Build.VERSION.SDK_INT >= 13;
    }

    public static boolean hasIceCreamSandwich() {
        return Build.VERSION.SDK_INT >= 14;
    }

    public static boolean hasJellyBean() {
        return Build.VERSION.SDK_INT >= 16;
    }

    public static boolean hasJellyBeanMR1() {
        return Build.VERSION.SDK_INT >= 17;
    }

    public static boolean hasJellyBeanMR2() {
        return Build.VERSION.SDK_INT >= 18;
    }

    public static boolean hasKitKat() {
        return Build.VERSION.SDK_INT >= 19;
    }

    public static boolean hasLollipop() {
        return Build.VERSION.SDK_INT >= 21;
    }

    public static synchronized String getLocalMacAddress() {

        if (mac == null) {
            WifiManager wifi = (WifiManager) Utils.getAppContext()
                    .getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifi.getConnectionInfo();
            mac = info.getMacAddress();
            if (mac != null && !mac.equals("")) {
                // SLog.i(SLog.TENCENT, "[System]本机MAC为:" + mac);
            } else {
                mac = "mac unknown";
            }
        }
        return mac;
    }



    /**
     * 用android版本号代替
     *
     * @return
     * @see getSystemSdk()
     */
    @Deprecated
    public static String getSystemVersion() {
        if (systemVersion == null) {
            systemVersion = Build.VERSION.RELEASE;
        }

        return systemVersion;
    }

    public static int getVersionCode() {
        if (versionCode == 0) {
            setVersionInfo();
        }
        return versionCode;
    }

    public static String getVersionName() {
        if (versionName == null) {
            setVersionInfo();
        }
        return versionName;
    }

    public static String getPackageName() {
        if (packageName == null) {
            setVersionInfo();
        }
        return packageName;
    }

    public static void setVersionInfo() {
        packageName = Utils.getAppContext()
                .getPackageName();
        try {
            PackageInfo pm = Utils.getAppContext()
                    .getPackageManager()
                    .getPackageInfo(
                            packageName,
                            PackageManager.GET_CONFIGURATIONS);
            versionCode = pm.versionCode;
            versionName = pm.versionName;
        } catch (NameNotFoundException e) {
        }
    }

    public static String getStringVersionCode() {
        int code = getVersionCode();
        StringBuffer versionCode = new StringBuffer(
                String.valueOf(code));
        versionCode.insert(versionCode.length() - 1, ".");
        versionCode.insert(versionCode.length() - 3, ".");
        return versionCode.toString();
    }

    /**
     * 获得生产厂商
     *
     * @return
     * @author junzhangcheng
     * @since 2011-12-7
     */
    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }

    /**
     * 获取手机android操作系统版本
     *
     * @return
     * @author junzhangcheng
     * @since 2011-12-7
     */
    public static int getSystemSdk() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 获取手机型号
     *
     * @return
     * @author junzhangcheng
     * @since 2011-12-7
     */
    public static synchronized String getProductType() {

        if (productType == null) {
            productType = Build.MODEL;
            productType = productType.replaceAll(
                    "[:{} \\[\\]\"']*", "");
            if (productType == null) {
                productType = "";
            }
        }

        return productType;
    }

    /**
     * 隐藏软键盘
     *
     * @param activity
     */
    public static void hideKeyboard(Activity activity) {
        InputMethodManager localInputMethodManager = (InputMethodManager) activity
                .getSystemService("input_method");

        if (activity.getCurrentFocus() != null) {
            IBinder localIBinder = activity
                    .getCurrentFocus().getWindowToken();
            localInputMethodManager
                    .hideSoftInputFromWindow(localIBinder,
                            0);
        }
    }

    public static void measureView(View child) {
        ViewGroup.LayoutParams p = child.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        int childWidthSpec = ViewGroup.getChildMeasureSpec(
                0, 0 + 0, p.width);
        int lpHeight = p.height;
        int childHeightSpec;
        if (lpHeight > 0) {
            childHeightSpec = MeasureSpec.makeMeasureSpec(
                    lpHeight, MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = MeasureSpec.makeMeasureSpec(
                    0, MeasureSpec.UNSPECIFIED);
        }
        child.measure(childWidthSpec, childHeightSpec);
    }

    /**
     * 获取移动步径
     *
     * @return
     */
    public static int getScaledTouchSlop() {
        if (scaledTouchSlop == -1) {
            scaledTouchSlop = ViewConfiguration.get(
                    Utils.getAppContext())
                    .getScaledTouchSlop();
        }
        return scaledTouchSlop;
    }

    public static boolean isForgroundRunning() {
        try {
            ActivityManager activityManager = (ActivityManager) Utils.getAppContext().getSystemService(
                    Context.ACTIVITY_SERVICE);
            List<RunningTaskInfo> tasksInfo = activityManager
                    .getRunningTasks(1);
            if (tasksInfo != null && tasksInfo.size() > 0) {
                if (Utils.getAppContext()
                        .getPackageName()
                        .equals(tasksInfo.get(0).topActivity
                                .getPackageName())) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isForgroundRunningTipsToast() {
        try {
            ActivityManager activityManager = (ActivityManager) Utils.getAppContext().getSystemService(
                    Context.ACTIVITY_SERVICE);
            List<RunningTaskInfo> tasksInfo = activityManager
                    .getRunningTasks(1);
            if (tasksInfo.size() > 0) {
                if (Utils.getAppContext()
                        .getPackageName()
                        .equals(tasksInfo.get(0).topActivity
                                .getPackageName())) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    private static Boolean ISDEBUG = true;

    public static boolean getDebugMode() {
        try {
            if (ISDEBUG == null) {
                PackageManager pm = Utils.getAppContext()
                        .getApplicationContext()
                        .getPackageManager();
                ApplicationInfo info = pm
                        .getApplicationInfo(Utils.getAppContext()
                                .getApplicationContext()
                                .getPackageName(), 0);
                ISDEBUG = (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
            }
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            ISDEBUG = false;
        }
        return ISDEBUG;
    }

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


    public static int getHeapSize() {
        return ((ActivityManager) Utils.getAppContext().getSystemService(
                Context.ACTIVITY_SERVICE))
                .getMemoryClass();
    }

    public static int getLargeMemoryClass() {
        return ((ActivityManager) Utils.getAppContext().getSystemService(
                Context.ACTIVITY_SERVICE))
                .getLargeMemoryClass();
    }

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

    @SuppressLint("TrulyRandom")
    public static String generateOpenUDID(Context mContext) {
        String OpenUDID = Secure.getString(
                mContext.getContentResolver(),
                Secure.ANDROID_ID);
        if (OpenUDID == null
                || OpenUDID.equals("9774d56d682e549c")
                || OpenUDID.length() < 15) {
            // if ANDROID_ID is null, or it's equals to the GalaxyTab generic
            // ANDROID_ID or bad, generates a new one
            final SecureRandom random = new SecureRandom();
            OpenUDID = new BigInteger(64, random)
                    .toString(16);
        }
        return OpenUDID;
    }

}
