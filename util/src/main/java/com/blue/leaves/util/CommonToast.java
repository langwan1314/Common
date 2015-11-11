package com.blue.leaves.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


/**
 * toast工具类 1、显示时间长短可以灵活设置 2、防止重复点击、重复显示 3、工具类可以确保在ui线程显示 4、支持传入字符串资源id
 * 
 * @author fredliao
 */

public class CommonToast {
    private static Toast mToast;
    private static Handler mHandler = new Handler(
            Looper.getMainLooper());
    private static long lastShowTime;
    private static String showText;
    private static Runnable showToastRunnable = new Runnable() {
        public void run() {
            mToast.show();
        }
    };

    /**
     * 短时间显示toast
     * 
     * @param context
     *            ,上下文
     * @param text
     *            ，显示文本
     */
    public static void showToastShort(Context context,
            String text) {
        showToast(context, text, Toast.LENGTH_SHORT);
    }

    /**
     * 长时间显示toast
     * 
     * @param context
     *            ,上下文
     * @param text
     *            ，显示文本
     */
    public static void showToastLong(Context context,
            String text) {
        showToast(context, text, Toast.LENGTH_LONG);
    }

    /**
     * 长时间显示toast
     * 
     * @param context
     *            ,上下文
     * @param resId
     *            ，文本资源id
     */
    public static void showToastLong(Context context,
            int resId) {
        showToast(context, context.getResources()
                .getString(resId), Toast.LENGTH_LONG);
    }

    /**
     * 短时间显示toast
     * 
     * @param context
     *            ,上下文
     * @param resId
     *            ，文本资源id
     */
    public static void showToastShort(Context context,
            int resId) {
        showToast(context, context.getResources()
                .getString(resId), Toast.LENGTH_SHORT);
    }

    /**
     * 短时间显示toast
     * 
     * @param context
     *            ,上下文
     * @param resId
     *            ，文本资源id
     */
    public static void showToast(Context context,
            int resId, int duration) {
        showToast(context, context.getResources()
                .getString(resId), duration);
    }

    /*
     * 短时间显示带图片的toast
     * 
     * @param context,上下文
     * 
     * @param resId，文本资源id
     */
    public static void showToastWithImage(Context context,
            int resId, int drawableId) {
        Toast toast = Toast.makeText(
                Utils.getAppContext(), resId,
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP
                | Gravity.CENTER_HORIZONTAL, 0, 200);
        LinearLayout toastView = (LinearLayout) toast
                .getView();
        ImageView imageCodeProject = new ImageView(context);
        imageCodeProject.setImageResource(drawableId);
        toastView.addView(imageCodeProject, 0);
        toast.show();
    }

    /**
     * 显示toast，防止重复显示
     * 
     * @param context
     *            ，上下文
     * @param resId
     *            ，文本资源id
     * @param duration
     *            ，显示时间长短
     */
    public static void showToast(Context context,
            String text, int duration) {
        // 第一次尽情显示
        if (mToast == null) {
            mToast = Toast
                    .makeText(context, text, duration);
            mHandler.post(showToastRunnable);
            lastShowTime = System.currentTimeMillis();
            showText = text;
        } else if (showText != null
                && !showText.equals(text)) {
            // 如果包含的内容不同则重新显示
            mToast.setText(text);
            mHandler.post(showToastRunnable);
            lastShowTime = System.currentTimeMillis();
            showText = text;
        } else {
            // 相同内容，上个失效后才可以重复显示
            long delay = duration == Toast.LENGTH_LONG ? 3550
                    : 2050;/* Long:3500+50;Short:2000+50 */
            final long current = System.currentTimeMillis();
            if (current - lastShowTime > delay) {
                mToast.setText(text);
                mHandler.post(showToastRunnable);
                lastShowTime = System.currentTimeMillis();
                showText = text;
            }
        }
    }

    public static void showToastShortAtTop(final Context a,
            final int resId) {
        // Toast toast = Toast.makeText(a, resId, Toast.LENGTH_SHORT);
        // toast.setGravity(Gravity.TOP, 100, 50);
        // toast.show();

        if (mToast != null) {
            mToast.cancel();
        }
        mToast = Toast.makeText(a, resId,
                Toast.LENGTH_SHORT);
        mToast.setGravity(Gravity.TOP, 100, 50);
        mToast.show();
    }

}
