package com.blue.leaves.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * 文件相关操作的工具类
 *
 * @author gengeng
 */
public class FileUtil {
    private static final String TAG = "FileHandler";
    private FileInputStream mIs;
    private FileOutputStream mOs;
    private int iotype;
    private int fsize;

    private static final Map<String, Object> fileLocks = new WeakHashMap<String, Object>();

    public synchronized static Object getLockForFile(
            String path) {

        if (path == null) {
            path = "";
        }

        Object lock = fileLocks.get(path);
        if (lock == null) {
            lock = new Object();
            fileLocks.put(path, lock);
        }
        return lock;
    }

    /**
     * 从文件中直接读一个对象
     *
     * @param srcStream
     */
    public synchronized static Object readSerObjectFromInStream(
            InputStream srcStream) {
        Object b = null;
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(srcStream);
            b = in.readObject();
        } catch (Exception e) {
            L.e("从文件中直接读一个对象错误", e.toString());
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    // Loger.e("从文件中直接读一个对象，关闭io错误", e.toString());
                }
            }
        }

        return b;
    }

    /*asset目录中是否存在某文件*/
    public static boolean isFileExistInAsserts(Context context,
                                               String folder, String fileName) {
        AssetManager am = context.getAssets();
        InputStream is = null;
        try {
            if (!EmptyUtils.isEmpty(folder)) {
                is = am.open(folder + File.separator
                        + fileName);
            } else {
                is = am.open(fileName);
            }

            if (is != null)
                return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
        }
        return false;
    }


    /**
     * 读取asset文件
     * 从asset中读取Object返回
     *
     * @param context
     * @param fileName
     * @return
     */
    public static synchronized Object getObjectFileFromAssets(
            Context context, String folder, String fileName) {
        if (fileName == null || fileName.equals("")) {
            return null;
        }
        InputStream stream = null;
        Object object = null;
        try {
            if (!EmptyUtils.isEmpty(folder)) {
                stream = context.getAssets().open(
                        folder + File.separator + fileName);
            } else {
                stream = context.getAssets().open(fileName);
            }
            object = FileUtil
                    .readSerObjectFromInStream(stream);
        } catch (IOException e) {
            L.e(TAG, "exception 1: " + e.toString());
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                    stream = null;
                } catch (IOException e) {
                    L.e(TAG,
                            "从文件中直接读一个对象，关闭io错误"
                                    + e.toString());
                }
            }
        }
        return object;
    }

    /**
     * 删除指定文件
     * 删除指定路径的文件，path可以是文件或文件夹或文件不存在
     *
     * @param sPath
     * @return
     */
    public static synchronized boolean removeFileAtPath(
            String sPath) {
        try {
            boolean flag = false;
            File file = new File(sPath);
            // 判断目录或文件是否存在
            if (!file.exists()) { // 不存在返回 false
                return flag;
            } else {
                // 判断是否为文件
                if (file.isFile()) { // 为文件时调用删除文件方法
                    return deleteFile(sPath);
                } else { // 为目录时调用删除目录方法
                    return deleteDirectory(sPath);
                }
            }
        } catch (Exception e) {
            // Log.e("removeFileAtPath", e.getMessage());
        }
        return false;
    }

    /**
     * 判断文件/文件夹是否存在
     * 文件或目录是否存在
     *
     * @param path
     * @return
     */
    public static boolean isDirFileExist(String path) {
        if (path == null || path.length() == 0)
            return false;

        File dirFile = new File(path);
        boolean exist = dirFile.exists();
        return exist;
    }

    /**
     * 删除指定文件夹下面的扩展名为ext的所有文件
     *
     * @param path
     * @param ext
     */
    public static void removeFilesAtDirPath(String path,
                                            String ext) {
        File file = new File(path);
        String[] list = file.list();
        int count = list.length;

        for (int i = 0; i < count; i++) {
            if (list[i].endsWith(ext)) {
                File delFile = new File(list[i]);
                delFile.delete();
            }
        }
    }

    /**
     * 取得指定文件夹下面的所有文件Name
     *
     * @param path
     * @return
     */
    public static String[] getContentsbyDir(String path) {
        File file = new File(path);
        return file.list();
    }

    /**
     * 取得指定文件的modification日期
     *
     * @param path
     * @return
     */
    public static long getFileModificationDate(String path) {
        File file = new File(path);
        return file.lastModified();
    }

    /**
     * 创建文件夹
     *
     * @param path
     * @return
     */
    public static boolean createDirs(String path) {
        File file = new File(path);
        return file.mkdirs();
    }

    /**
     * 重命名
     *
     * @param srcFilePath
     * @param destFilePath
     * @return
     */
    public static boolean rename(String srcFilePath,
                                 String destFilePath) {
        File srcfile = new File(srcFilePath);
        File desfile = new File(destFilePath);
        return srcfile.renameTo(desfile);
    }

    /**
     * 将data完全写入文件，如果同名文件已存在，则先删除文件
     *
     * @param data
     * @param path
     * @return
     */
    public static synchronized void writeByteToFilePath(
            byte[] data, String path, boolean append) {
        FileOutputStream os = null;
        try {
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            os = new FileOutputStream(path, append);
            os.write(data, 0, data.length);
            os.flush();
        } catch (Exception e) {

        } finally {
            if (os != null) {
                try {
                    os.close();
                    os = null;
                } catch (IOException e) {
                    // Loger.e("从文件中直接读一个对象，关闭io错误", e.toString());
                }
            }
        }
    }

    /**
     * 将对象写入文件，append为true则追加到文件，false则先删除文件
     *
     * @param obj
     * @param path
     * @return
     */
    public static synchronized boolean writeObjectToPath(
            Object obj, String path, boolean append) {
        FileOutputStream os = null;
        ObjectOutputStream oos = null;
        try {
            File file = new File(path);
            if (!append)
                file.delete();
            if (!file.exists()) {
                file.createNewFile();
            }
            os = new FileOutputStream(path);
            oos = new ObjectOutputStream(os);
            oos.writeObject(obj);
            oos.close();
            os.close();
        } catch (Exception e) {
            L.e("写文件错误", e.toString());
            return false;
        } finally {
            if (os != null) {
                try {
                    os.close();
                    os = null;
                } catch (IOException e) {
                    L.e("从文件中直接读一个对象，关闭io错误",
                            e.toString());
                }
            }
            if (oos != null) {
                try {
                    oos.close();
                    oos = null;
                } catch (IOException e) {
                    L.e("从文件中直接读一个对象，关闭io错误",
                            e.toString());
                }
            }
        }
        return true;
    }

    /**
     * 将文件中的内容完全读出
     *
     * @param path
     * @return
     */
    public static synchronized byte[] readDataFromPath(
            String path) {
        File file = new File(path);
        int filesize = (int) file.length();

        if (filesize == 0) {
            return null;
        }

        byte[] buffer = new byte[filesize + 1];
        FileInputStream is = null;
        try {
            is = new FileInputStream(path); // 读入原文件
            is.read(buffer, 0, filesize);
            is.close();
        } catch (Exception e) {
            return null;
        } finally {
            if (is != null) {
                try {
                    is.close();
                    is = null;
                } catch (IOException e) {
                    // Loger.e("从文件中直接读一个对象，关闭io错误", e.toString());
                }
            }
        }

        return buffer;
    }

    /**
     * 将文件内容读取为Object
     *
     * @param path
     * @return
     */
    public static synchronized Object readObjectFromPath(
            String path) {
        Object obj = null;
        FileInputStream is = null;
        ObjectInputStream ois = null;
        try {
            File file = new File(path);
            if (file.exists()) {
                is = new FileInputStream(file); // 读入原文件
                ois = new ObjectInputStream(is);
                obj = ois.readObject();
                ois.close();
                is.close();
            }
        } catch (Exception e) {
            // Loger.e("从文件中直接读一个对象错误", e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                    is = null;
                } catch (IOException e) {
                    // Loger.e("从文件中直接读一个对象，关闭io错误", e.toString());
                }
            }
            if (ois != null) {
                try {
                    ois.close();
                    ois = null;
                } catch (IOException e) {
                    // Loger.e("从文件中直接读一个对象，关闭io错误", e.toString());
                }
            }
        }
        return obj;
    }

    /**
     * 同步化读/写文件，如果传入object为空，则为读，如果非空，则为写
     *
     * @param path
     * @param writeObj
     * @return
     */
    public static synchronized Object synchronized_RW_Object(
            String path, Object writeObj) {
        Object obj = null;

        if (writeObj == null) {
            // read
            obj = FileUtil.readObjectFromPath(path);
        } else {
            // write
            FileUtil.writeObjectToPath(writeObj, path,
                    false);
        }

        return obj;
    }

    /*删除文件*/
    private static synchronized boolean deleteFile(
            String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }

    /*删除目录*/
    private static synchronized boolean deleteDirectory(
            String sPath) {
        // 如果sPath不以文件分隔符结尾，自动添加文件分隔符
        if (!sPath.endsWith(File.separator)) {
            sPath = sPath + File.separator;
        }
        File dirFile = new File(sPath);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        boolean flag = true;
        // 删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            // 删除子文件
            if (files[i].isFile()) {
                flag = deleteFile(files[i]
                        .getAbsolutePath());
                if (!flag)
                    break;
            } // 删除子目录
            else {
                flag = deleteDirectory(files[i]
                        .getAbsolutePath());
                if (!flag)
                    break;
            }
        }
        if (!flag)
            return false;
        // 删除当前目录
        if (dirFile.delete()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 创建目录和文件， 如果目录或文件不存在，则创建出来
     *
     * @param filePath 文件路径
     * @return 创建后的文件
     * @throws IOException
     * @author
     * @since 2011-10-24
     */
    public static synchronized File makeDIRAndCreateFile(
            String filePath) throws Exception {
        // Auto-generated method stub

        File file = new File(filePath);
        String parent = file.getParent();
        File parentFile = new File(parent);
        if (!parentFile.exists()) {
            if (parentFile.mkdirs()) {
                file.createNewFile();
            } else {
                throw new IOException("创建目录失败！");
            }
        } else {
            if (!file.exists()) {
                file.createNewFile();
            }
        }
        return file;
    }

    /**
     * 把str写入文件
     *
     * @param filePath 文件路径
     * @param str      json字符串
     * @return 写入成功与否
     * @author haiyandu
     * @since 2011-10-24
     */
    public static boolean writeString(String filePath,
                                      String str, boolean isAppend) {
        synchronized (getLockForFile(filePath)) {
            FileOutputStream out = null;
            try {
                File file = makeDIRAndCreateFile(filePath);
                out = new FileOutputStream(file, isAppend);
                out.write(str.getBytes());
                out.flush();
            } catch (Exception e) {
                L.e(TAG, e.toString());
                return false;
            } finally {
                if (out != null) {
                    try {
                        out.close();
                        out = null;
                    } catch (Exception e) {
                        L.e(TAG, e.toString());
                        return false;
                    }
                }
            }
            return true;
        }
    }

    public static void renameAndDelete(String path) {
        File srcFile = new File(path);
        File delFile = new File(srcFile.getPath()
                + System.currentTimeMillis() + "_del");
        srcFile.renameTo(delFile);
        removeFileAtPath(path);
    }

    /**
     * 创建一个指定路径的文件，并且指定大小
     *
     * @param path
     * @param size
     * @throws IOException
     */
    public static boolean createFileWithSpecialSize(
            String path, long size) {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
        RandomAccessFile accessFile = null;
        try {
            accessFile = new RandomAccessFile(path, "rw");
            accessFile.setLength(size);
            return true;
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } catch (OutOfMemoryError error) {
        } finally {
            if (accessFile != null) {
                try {
                    accessFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /*
     * 更新指定路径文件最近修改时间
     * 
     * @param path 文件的完整路径，包括文件名和后缀
     * 
     * @param lastModifiedTime 文件最后修改时间
     * 
     * @return boolean，true代表成功 Q&D ：很多Android系统不支持对文件lastmodified的修改，此接口。。。
     */
    public static boolean updateFileLastModified(
            String path, Long lastModifiedTime) {
        File file = new File(path);
        boolean ret = false;

        if (file.exists()) {
            ret = file.setLastModified(lastModifiedTime);
        }

        return ret;
    }

    /**
     * 获得指定文件路径的文件名
     *
     * @param path 文件的完整路径，包括文件名和后缀
     * @return 文件名
     */
    public static String getFileName(String path) {
        String file = path
                .substring(path.lastIndexOf("/") + 1);
        if (file.contains(".")) {
            file = file.substring(0, file.indexOf("."));
        }
        return file;
    }

    /**
     * 获得指定文件路径的文件后缀名
     *
     * @param path 文件的完整路径，包括文件名和后缀
     * @return 文件后缀，如果无后缀return null
     */
    public static String getFileExtension(String path) {
        String file = path
                .substring(path.lastIndexOf("/") + 1);
        if (file.contains(".")) {
            return file.substring(file.indexOf(".") + 1);
        } else {
            return null;
        }
    }

    /*
     * 获取特定文件夹下的总文件大小
     * 
     * @param path 文件夹的完整路径
     */
    public static long getDirecotrySize(String path) {
        File dir = new File(path);
        return getDirecotrySize(dir);
    }

    /*
     * 获取特定文件夹下的总文件大小
     * 
     * @param file 文件夹
     */
    public static long getDirecotrySize(File file) {
        long fileSize = 0;
        if (!file.exists()) {
            return fileSize;
        }

        if (file.isDirectory()) {
            File[] flist = file.listFiles();
            if (flist != null) {
                for (int i = 0; i < flist.length; i++) {
                    fileSize = fileSize
                            + getDirecotrySize(flist[i]);
                }
            }
        } else {
            fileSize = file.length();
        }

        return fileSize;
    }

    /**
     * 获取制定路径文件的大小
     *
     * @param path 文件的完整路径，包括文件名和后缀
     * @return 文件大小，文件不存在return 0
     */
    public static long getFileSize(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return 0;
        } else {
            return file.length();
        }
    }

    /**
     * 指定要扫描的文件路径与文件扩展名列表, 返回指定扩展名的文件名的列表, 调用方不能是主线程
     *
     * @param path           文件路径,可以是文件目录或文件
     * @param extensionNames 文件扩展名列表
     * @return 特殊情况, 如果文件扩展 名列表为null则返回所有文件
     */
    public static List<String> scanFile(String root,
                                        List<String> suffixList) {
        // 优化中
        if (TextUtils.isEmpty(root)) {
            return null;
        }

        File rootDir = new File(root);
        if (rootDir != null && rootDir.exists()) {
            List<String> scanResultList = new ArrayList<String>();

            File[] files = rootDir.listFiles();

            if (files != null) {
                for (int i = 0; i < files.length; i++) {
                    File file = files[i];

                    String path = "";
                    try {
                        path = file.getCanonicalPath();
                    } catch (IOException e) {
                    }

                    if (file.isFile()) {
                        if (isSpecfiedSuffixExist(path,
                                suffixList)) {
                            scanResultList.add(path);
                        }
                    } else if (file.isDirectory()
                            && path.indexOf("/.") == -1) {
                        // 忽略点文件（隐藏文件/文件夹）
                        List<String> childList = scanFile(
                                path, suffixList);
                        if (childList != null
                                && !childList.isEmpty()) {
                            scanResultList
                                    .addAll(childList);
                        }
                    }
                }
            }

            return scanResultList;
        } else {
            return null;
        }
    }

    /**
     * 判断一个文件是否是指定的后缀类型的文件
     *
     * @param path       文件路径
     * @param suffixList 扩展名列表
     * @return
     */
    public static boolean isSpecfiedSuffixExist(
            String path, List<String> suffixList) {
        if (TextUtils.isEmpty(path) || suffixList == null
                || suffixList.isEmpty()) {
            return true;
        }

        for (String suffix : suffixList) {
            try {
                if (path.endsWith(suffix)) {
                    return true;
                }
            } catch (NullPointerException e) {
                return false;
            }
        }
        return false;
    }

    public static boolean copy(String from, String dest) {
        if (TextUtils.isEmpty(from)
                || TextUtils.isEmpty(dest)) {
            return false;
        }
        File file = new File(from);
        if (!file.exists()) {
            return false;
        }
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        try {
            inBuff = new BufferedInputStream(
                    new FileInputStream(file));
            outBuff = new BufferedOutputStream(
                    new FileOutputStream(new File(dest)));

            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            // 刷新此缓冲的输出流
            outBuff.flush();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭流
            if (inBuff != null)
                try {
                    inBuff.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            if (outBuff != null)
                try {
                    outBuff.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return false;
    }

    public static boolean saveBitmap(Context context, Bitmap bmp,
                                     String bitName) {
        String path = StorageUtil.getPicDir(context) + "/"
                + bitName + ".png";
        File f = new File(path);
        L.d(TAG, "saveBitmap path:" + path);
        boolean flag = false;
        try {
            f.createNewFile();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
            bmp.compress(Bitmap.CompressFormat.PNG, 100,
                    fOut);
            flag = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }

    /*读取指定目录的文件图片为bitmap*/
    public static Bitmap readBitmp(String path) {

        File file = new File(path);
        if (file.exists() == false) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            readFile(path, baos);
            byte[] bytes = baos.toByteArray();
            Bitmap bmp = BitmapFactory.decodeByteArray(
                    bytes, 0, bytes.length);
            try {
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bmp;
        }

        return null;
    }

    /**
     * 读取文件到字节流 baos baos从外部来，外部close，这里不要close baos
     *
     * @param baos 字节流数据
     * @param dest 要读取的文件完整路径，包括文件名和后缀
     */
    public static boolean readFile(String dest,
                                   ByteArrayOutputStream baos) {
        File file = new File(dest);
        if (null == baos || !file.exists()) {

            return false;
        }
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            byte[] buf = new byte[1024];
            while (true) {
                int numread = fis.read(buf);
                if (-1 == numread) {
                    break;
                }
                baos.write(buf, 0, numread);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (baos.size() > 0) {
            return true;
        } else {
            return false;
        }

    }

    /*
     * 以指定的格式压缩图片，返回压缩后的byte[]
     */
    public static byte[] compressBitmap(Bitmap bitmap,
                                        Bitmap.CompressFormat format, int bitRate) {
        ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(format, bitRate,
                localByteArrayOutputStream);
        byte[] arrayOfByte = localByteArrayOutputStream
                .toByteArray();
        try {
            localByteArrayOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arrayOfByte;
    }

    /**
     * Delete file recursively. If it's a file, delete file; If it's a
     * directory, delete the directory and its sub-directories and files.
     *
     * @param file
     */
    public static void recursiveDelete(final File file) {
        try {
            if (file != null && file.exists()) {
                if (file.isDirectory()) {
                    File[] tempFiles = file.listFiles();
                    if (tempFiles == null) {
                        return;
                    }
                    for (final File tmp : tempFiles) {
                        recursiveDelete(tmp);
                    }
                }
                file.delete();
            }
        } catch (final Exception e) {

        }
    }

}
