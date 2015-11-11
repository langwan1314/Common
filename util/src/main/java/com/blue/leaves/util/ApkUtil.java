package com.blue.leaves.util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;


import com.blue.leaves.util.log.Log;

import java.io.File;

@SuppressWarnings("unused")
public class ApkUtil {

    public static final String FILE_PATH_HEADER = "file://";

    public static final String APP_PACKAGE_ARCHIVE = "application/vnd.android.package-archive";

    public static final String APK_ACTION_DELETE = "android.intent.action.DELETE";

    public static final String EXTRA_INSTALLER_PACKAGE_NAME = "android.intent.extra.INSTALLER_PACKAGE_NAME";

    public static final String SELF_APPLICATION = "com.tencent.news";

    private static final String PREFERENCE_KEY_SHORTCUT_EXISTS = "IsShortCutExists";

    private static final String CREATE_SHORTCUT_ACTION = "com.android.launcher.action.INSTALL_SHORTCUT";

    private static final String DROP_SHORTCUT_ACTION = "com.android.launcher.action.UNINSTALL_SHORTCUT";

    private static SharedPreferences sharedPreferences;
    private static boolean exists;

    public static void installApk(Context context,
                                  String apkPath) {
        Log.d("file", "ApkUtil installApk:" + apkPath);
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
//            CommonToast.showToastLong(Utils.getAppContext(),
//                    Utils.getAppContext()
//                            .getString(
//                                    R.string.file_not_exists));
        }
    }

    public static void createShortcut(
            final Context context, Handler observableHandler) {
        sharedPreferences = AppSPUtils
                .getSharedPreferences(AppConsts.SP_SHORT_CUT);
        exists = AppSPUtils.getValueFromPrefrences(
                sharedPreferences,
                PREFERENCE_KEY_SHORTCUT_EXISTS, false);

        if (!exists) {
            if (isShortcutExisted()) {
                setShortCutPreference(sharedPreferences,
                        true);
            } else {
                creatOrDelShortcut(context, false);
                observableHandler.postDelayed(
                        new Runnable() {
                            @Override
                            public void run() {
                                creatOrDelShortcut(context,
                                        true);
                                setShortCutPreference(
                                        sharedPreferences,
                                        true);
                            }
                        }, 3500);

            }
        }

    }

    private static void setShortCutPreference(
            SharedPreferences preference, boolean bSet) {
        AppSPUtils.setValueToPrefrences(preference,
                PREFERENCE_KEY_SHORTCUT_EXISTS, bSet);
    }

    /**
     * @param context
     * @param isInstall true creat, false delete
     */
    public static void creatOrDelShortcut(Context context,
                                          boolean isInstall) {
        int resId;
        if (AndroidUtils.getDebugMode()) {
            resId = R.drawable.icon; // 应该替换为icon——test
        } else {
            resId = R.drawable.icon;
        }
        Intent shortcutIntent = null;
        shortcutIntent = new Intent(Intent.ACTION_MAIN);
        shortcutIntent
                .addCategory(Intent.CATEGORY_LAUNCHER);

        Intent installIntent = new Intent();
        installIntent.putExtra(
                Intent.EXTRA_SHORTCUT_INTENT,
                shortcutIntent);
        installIntent.putExtra(
                Intent.EXTRA_SHORTCUT_NAME,
                context.getResources().getString(
                        R.string.app_name));
        installIntent.putExtra(
                Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
                Intent.ShortcutIconResource.fromContext(
                        context, resId));
        installIntent.putExtra("duplicate", false);

        if (isInstall) {
            shortcutIntent
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            shortcutIntent
                    .addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
            installIntent
                    .setAction("com.android.launcher.action.INSTALL_SHORTCUT");
        } else {
            installIntent
                    .setAction("com.android.launcher.action.UNINSTALL_SHORTCUT");
            shortcutIntent
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            shortcutIntent
                    .addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
            installIntent
                    .setAction("com.android.launcher.action.UNINSTALL_SHORTCUT");
        }
        context.sendOrderedBroadcast(installIntent, null);
    }

    private static boolean isShortcutExisted() {
        Context cx = Utils.getAppContext()
                .getApplicationContext();
        boolean result = false;
        String title = null;
        try {
            final PackageManager pm = cx
                    .getPackageManager();
            title = pm.getApplicationLabel(
                    pm.getApplicationInfo(
                            cx.getPackageName(),
                            PackageManager.GET_META_DATA))
                    .toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String uriStr = "content://com.android.launcher2.settings/favorites?notify=true";
        Uri CONTENT_URI = Uri.parse(uriStr);
        Cursor c = cx.getContentResolver().query(
                CONTENT_URI, null, "title=?",
                new String[]{title}, null);
        if (c != null && c.getCount() > 0) {
            result = true;
            c.close();
        }

        if (false == result) {
            uriStr = "content://com.android.launcher.settings/favorites?notify=true";
            CONTENT_URI = Uri.parse(uriStr);
            c = cx.getContentResolver().query(CONTENT_URI,
                    null, "title=?",
                    new String[]{title}, null);
            if (c != null && c.getCount() > 0) {
                result = true;
                c.close();
            }
        }
        return result;
    }

}
