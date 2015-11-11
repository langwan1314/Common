package com.blue.leaves.util;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;

import java.io.File;
import java.io.IOException;

/**
 * App相关目录操作的工具类
 *
 * @author gengeng
 */
public class StorageUtil {
    private final static String TAG = "FileUtil";

    // 有sd卡时应用程序的存储根目录
    public static final String APP_SDCARD_AMOUNT_ROOT_PATH = "/common";
    // 无sd卡时应用程序的存储根目录
    public static final String APP_SDCARD_UNAMOUNT_ROOT_PATH = "/common";
    // 有sd卡时应用程序缓存文件的存储跟目录
    public static final String APP_SDCARD_AMOUNT_TMP_ROOT_PATH = "/tmp";
    // Apk保存目录
    public static final String APK_DIT_PATH = "/apk";
    // 日志文件保存目录
    public static final String LOG_DIR_PATH = "/log";
    // 图片保存目录
    public static final String PIC_DIR_PATH = "/pic";
    // tab页面预加载数据缓存目录
    public static final String CACHE_DIR_PATH = "/cache";

    public static final int DATASIZE = 20 * 1024 * 1024;

    // 判断SDCard是否存在并且是可写的
    public static boolean isSDCardExistAndCanWrite() {
        boolean result = false;
        try {
            result = Environment.MEDIA_MOUNTED
                    .equals(Environment
                            .getExternalStorageState())
                    && Environment
                    .getExternalStorageDirectory()
                    .canWrite();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        return result;
    }

    /**
     * 获取程序运行期间文件保存的根目录
     *
     * @return SD卡可用的时候返回的是
     * /mnt/sdcard/tencent/assistant，SD卡不可用返回的是内存的路径data/data
     * /packagename/files
     */
    public static String getCommonRootDir(Context context) {
        String dirPath = null;

        // 判断SDCard是否存在并且是可用的
        if (isSDCardExistAndCanWrite()) {
            dirPath = Environment
                    .getExternalStorageDirectory()
                    .getPath()
                    + APP_SDCARD_AMOUNT_ROOT_PATH;
        } else {
            dirPath = context
                    .getFilesDir().getAbsolutePath()
                    + APP_SDCARD_UNAMOUNT_ROOT_PATH;
        }
        L.d(TAG, "getCommonRootDir dirPath:" + dirPath);
        File file = new File(dirPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }

    /**
     * 获取常驻文件路径 常驻根目录 + 业务所需路径
     *
     * @param path
     * @return
     */
    public static String getCommonPath(Context context, String path) {
        final String rootDir = getCommonRootDir(context);
        String fullPath = null;
        if (!TextUtils.isEmpty(path)) {
            fullPath = rootDir + path;
        } else {
            fullPath = rootDir;
        }
        L.d(TAG, "getCommonPath fullPath:" + fullPath);
        return getPath(fullPath, false);
    }

    /**
     * 指定路径创建目录, 并提供指定momedia的接口
     *
     * @param nomedia : 是否需要nomedia文件，只有存图片的目录需要，有nomedia图片在相册不可见
     * @return 完整路径
     */
    private static String getPath(String path,
                                  boolean nomedia) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
            if (nomedia) {
                File nomediaFile = new File(path
                        + File.separator + ".nomedia");
                try {
                    nomediaFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file.getAbsolutePath();
    }

    // 取得apk保存目录
    public static String getAPKDir(Context context) {
        return getCommonPath(context, APK_DIT_PATH);
    }

    // 取得log保存目录
    public static String getLogDir(Context context) {
        return getCommonPath(context, LOG_DIR_PATH);
    }

    // 取得图片保存目录
    public static String getPicDir(Context context) {
        return getPath(getCommonRootDir(context) + PIC_DIR_PATH,
                true);
    }

    // 取得tab页面预加载数据缓存目录
    public static String getCacheDir(Context context) {
        return getCommonPath(context, CACHE_DIR_PATH);
    }

    // 得到系统分配的程序缓存目录
    public static String getInternalCachePath(Context context) {
        String dirPath = context
                .getCacheDir() + File.separator;
        File file = new File(dirPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return dirPath;
    }

    // 清空缓存目录
    public static void clearInternalCache(Context context,String dir) {
        File f = new File(getInternalCachePath(context) + dir);
        if (f.exists()) {
            File files[] = f.listFiles();
            if (files != null) {
                for (int i = 0; i < files.length; i++) {
                    files[i].delete();
                }
            }
        }
    }

    /* 计算sdcard上的剩余空间，返回单位为MB，整型 */
    public static int freeSpaceOnSd() {
        StatFs stat = new StatFs(Environment
                .getExternalStorageDirectory().getPath());
        double sdFreeMB = ((double) stat
                .getAvailableBlocks() * (double) stat
                .getBlockSize())
                / (1024 * 1024);

        return (int) sdFreeMB;
    }

    /**
     * Clear both internal and external cache.
     *
     * @param context
     */
    public static void clearCache(final Context context) {
        FileUtil.recursiveDelete(context
                .getExternalCacheDir());
        FileUtil.recursiveDelete(context.getCacheDir());
    }

    /**
     * 存储卡存储空间不多，需要清理缓存
     *
     * @return
     */
    public static boolean isClearCard(Context context) {
        String sdcard = StorageUtil.getCacheDir(context);
        StatFs statFs = null;
        if (sdcard != null) {
            try {
                statFs = new StatFs(sdcard);
            } catch (Exception e) {
                return false;
            }
            long blockSize = statFs.getBlockSize();
            long blocks = statFs.getAvailableBlocks();
            long availableSpare = blocks * blockSize;
            if ((DATASIZE * 3) > availableSpare) {
                return true;
            } else {
                return false;
            }
        }

        return false;
    }

    public static boolean isSDCardFull(Context context) {
        String sdcard = StorageUtil.getCacheDir(context);
        StatFs statFs = null;
        if (!StringUtil.isNullOrEmpty(sdcard)) {
            try {
                statFs = new StatFs(sdcard);
            } catch (IllegalArgumentException e) {
                return true;
            }
            long blockSize = statFs.getBlockSize();
            long blocks = statFs.getAvailableBlocks();
            long availableSpare = blocks * blockSize;
            L.v(TAG, "isSDCardFull availableSpare:"
                    + availableSpare);
            if (DATASIZE > availableSpare) {
                return true;
            } else {
                return false;
            }
        }

        return false;
    }

}
