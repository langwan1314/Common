package com.blue.leaves.util;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;

import java.lang.reflect.Field;

public class AppUIUtils {
    /**
     * @return 屏幕密度
     */
    public static float getDensity(Context context) {
        final float scale = context.getResources()
                .getDisplayMetrics().density;
        return scale;
    }

    /**
     * @return 屏幕宽度
     */
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * @return 屏幕高度
     */
    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }



    public static int dip2px(float density, float dipValue) {
        return (int) (dipValue * density + 0.5f);
    }

    public static int px2dip(float density, float pxValue) {
        return (int) (pxValue / density + 0.5f);
    }

    /**
     * @return dip转px
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources()
                .getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * @return px转dip
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources()
                .getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * @return dp转dip
     */
    public static int dpToPx(Context context, int dp) {
        return (int) (dp
                * getDeviceDisplayMetrics(context).density + 0.5f);
    }


    /**
     * 获取屏幕分辨率信息
     * */
    public static DisplayMetrics getDeviceDisplayMetrics(
            Context context) {
        android.view.WindowManager windowsManager = (android.view.WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        android.view.Display display = windowsManager
                .getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        return outMetrics;
    }

    /**
     * 获取手机状态栏高度
     * */
    public static int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class
                    .forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources()
                    .getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }

    /**
     * View width:Macth_Parent Height:wrap_content
     * */
    public static void measureView(final View child) {
        ViewGroup.LayoutParams p = child.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        int childWidthSpec = ViewGroup.getChildMeasureSpec(
                0, 0, p.width);
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

    // 转换dip为px
    public static int convertDipToPx(Context context,
            int dip) {
        float scale = context.getResources()
                .getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f * (dip >= 0 ? 1
                : -1));
    }

    // 转换px为dip
    public static int convertPxToDip(Context context, int px) {
        float scale = context.getResources()
                .getDisplayMetrics().density;
        return (int) (px / scale + 0.5f * (px >= 0 ? 1 : -1));
    }

    // 转换px为sp
    public static int px2sp(Context context, float pxValue) {
        float scale = context.getResources()
                .getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    // 转换sp为px
    public static int sp2px(Context context, float spValue) {
        float scale = context.getResources()
                .getDisplayMetrics().density;
        return (int) (spValue * scale + 0.5f);
    }

    /**
     * 隐藏控制栏
     * 
     * @param acticity
     */
    public static void hideSystemBars(Activity acticity) {
        if (Build.VERSION.SDK_INT >= 19) {
            acticity.getWindow()
                    .getDecorView()
                    .setSystemUiVisibility(
                            0x100 | 0x2 | 0x400 | 0x200
                                    | 0x4 | 0x00001000);
        }

    }


    /**
     * 显示控制栏
     * 
     * @param acticity
     */
    public static void showSystemBars(Activity activity) {
        if (Build.VERSION.SDK_INT >= 19) {
            activity.getWindow().getDecorView()
                    .setSystemUiVisibility(0x100);
        }
    }

    /**
     * 获取屏幕的物理尺寸(以寸为单位)
     * 
     * @return
     */
    public static double getScreenInch(Context context) {
        // 算出屏幕真实尺寸
        DisplayMetrics dm = new DisplayMetrics();
        dm = context.getResources().getDisplayMetrics();

        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;

        float xdpi = dm.xdpi;
        float ydpi = dm.ydpi;

        float widthInch = ((float) screenWidth) / xdpi;
        float heightInch = ((float) screenHeight) / ydpi;

        // 勾股定理
        double screenInch = Math
                .sqrt((widthInch * widthInch)
                        + (heightInch * heightInch));

        return screenInch;
    }

    public static double getScreenDP(Context context) {
        // 算出屏幕真实尺寸
        DisplayMetrics dm = new DisplayMetrics();
        dm = context.getResources().getDisplayMetrics();

        int screenWidthDP = AppUIUtils.px2dip(context,
                dm.widthPixels);
        int screenHeightDP = AppUIUtils.px2dip(context,
                dm.heightPixels);
        ;

        // 勾股定理
        double screenSizeDP = Math
                .sqrt((screenWidthDP * screenWidthDP)
                        + (screenHeightDP * screenHeightDP));

        return screenSizeDP;
    }

}
