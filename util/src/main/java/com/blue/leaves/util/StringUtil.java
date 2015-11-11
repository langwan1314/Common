package com.blue.leaves.util;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class StringUtil {
    private static int seq = 1;

    public static synchronized String getSeq() {
        return String
                .valueOf((seq++)
                        + (System.currentTimeMillis() / 1000) << 32);
    }

    private static Random rnd = new Random();

    public static String getRandomNum() {
        return rnd.nextInt(Integer.MAX_VALUE) + "";
    }

    public static String stringToColor(String str) {
        String strColor = "";
        try {
            if (str.contains("#")) {
                strColor = str;
            } else {
                // strColor = String.format("#%06x", str);
                strColor = "#"
                        + Long.toHexString(Long
                        .parseLong(str));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return strColor;
        }
        return strColor;
    }

    // MD5 加密
    public static String toMd5(String src) {
        try {
            MessageDigest algorithm = MessageDigest
                    .getInstance("MD5");
            algorithm.reset();
            algorithm.update(src.getBytes());
            return toHexString(algorithm.digest(), "");
        } catch (NoSuchAlgorithmException e) {
            // SLog.e("Md5 encode failed!", e.getMessage());
            return "error";
        }
    }

    public static String getFileMD5(File file) {
        if (!file.isFile()) {
            return null;
        }
        MessageDigest digest = null;
        FileInputStream in = null;
        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return toHexString(digest.digest(), "");
    }

    public static String toHexString(byte[] bytes,
                                     String separator) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            int byteValue = 0xFF & b;
            if (byteValue < 0x10) {
                hexString
                        .append("0"
                                + Integer
                                .toHexString(0xFF & b))
                        .append(separator);
            } else {
                hexString.append(
                        Integer.toHexString(0xFF & b))
                        .append(separator);
            }
        }
        return hexString.toString();
    }

    public static byte[] appendBytes(byte[] src,
                                     byte[] append, int start, int length) {
        if (src == null) {
            src = new byte[0];
        }
        if (append == null || length == 0) {
            return src;
        }
        int totalLength = src.length + length;
        byte[] returnBytes = new byte[totalLength];
        for (int i = 0; i < totalLength; i++) {
            if (i < src.length) {
                returnBytes[i] = src[i];
            } else {
                returnBytes[i] = append[i - src.length];
            }
        }
        return returnBytes;
    }

    public static final String UTF8 = "utf-8";

    public static String urlEncode(String str)
            throws UnsupportedEncodingException {
        if (str == null) {
            str = "";
        }
        return URLEncoder.encode(str, UTF8)
                .replaceAll("\\+", "%20")
                .replaceAll("%7E", "~")
                .replaceAll("\\*", "%2A");
    }

    public static String urlDecode(String str)
            throws UnsupportedEncodingException {
        return URLDecoder.decode(str, UTF8);
    }

    public static String[] list2Array(List<String> list) {
        if (list == null || list.size() == 0) {
            return null;
        }
        int size = list.size();
        String[] strings = new String[size];
        for (int i = 0; i < size; i++) {
            strings[i] = list.get(i);
        }
        return strings;
    }

    public static String cutOffString100(String str) {
        return cutOffString(str, 100);
    }

    public static String cutOffString(String str, int maxlen) {
        if (!TextUtils.isEmpty(str)) {
            str = str.length() > maxlen ? str.substring(0,
                    maxlen) + "..." : str;
        }
        return str;
    }

    public static String cutDateString(String str) {
        if (!TextUtils.isEmpty(str)) {
            str = str.substring(5, str.length() - 3);
        }
        return str;
    }

    public static String StringFilter(String str) {
        Matcher m = null;
        try {
            str = str.replaceAll("【", "[")
                    .replaceAll("】", "]")
                    .replaceAll("！", "!");// 替换中文标号
            String regEx = "[『』]"; // 清除掉特殊字符
            Pattern p = Pattern.compile(regEx);
            m = p.matcher(str);
        } catch (PatternSyntaxException e) {
            return str;
        }
        return m.replaceAll("").trim();
    }

    // 将字符串转换为半角
    public static String ToDBC(String input) {
        if (isNullOrEmpty(input)) {
            return "";
        }
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    // 将字符串转换为全角
    public static String ToSBC(String input) {
        if (isNullOrEmpty(input)) {
            return "";
        }
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 32) {
                c[i] = (char) 12288;
                continue;
            }
            if (c[i] < 127 && c[i] > 32)
                c[i] = (char) (c[i] + 65248);
        }
        return new String(c);
    }

    // 判断一个字符是汉字、数字、字母还是其他（如符号）
    public static int getTypeOfChar(char a) {
        if (StringUtil.isCnHz(a)) { // 汉字
            return 0;
        } else if (Character.isDigit(a)) {
            return 1;
        } else if (Character.isLetter(a)) {
            return 2;
        } else if (StringUtil.isChinese(a)
                && !StringUtil.isCnHz(a)) {// 汉字符号
            return 4;
        } else {
            return 3;
        }
    }

    // 判断是否是汉字
    public static boolean isCnHz(char a) {
        // return String.valueOf(a).matches("[\u4E00-\u9FA5]"); //
        // 利用正则表达式，经测试可以区分开中文符号
        return String.valueOf(a).matches("[\u4E00-\u9FBF]");
    }

    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock
                .of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            return true;
        }
        return false;
    }

    public static String getNextWord(String str,
                                     int charType) {
        String retStr = "";
        char[] c = str.toCharArray();
        int typeOfChar = 100;
        for (int i = 0; i < c.length; i++) {
            typeOfChar = StringUtil.getTypeOfChar(c[i]);
            if (typeOfChar != charType) {
                break;
            }
            retStr += String.valueOf(c[i]);
        }
        return retStr;
    }

    // 去除空格回车换行
    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    /**
     * 截取指定长度中文字符串（一个中文长度为2个英文字符长度）
     *
     * @param str
     * @param subLength
     * @return
     */
    public static String subString(String str, int subLength) {
        int n = 0;
        int i = 0;
        int j = 0;
        int byteNum = subLength * 2;
        boolean flag = true;
        if (str == null) {
            return "";
        }

        for (i = 0; i < str.length(); i++) {
            if ((int) (str.charAt(i)) < 128) {
                n += 1;
            } else {
                n += 2;
            }
            if (n > byteNum && flag) {
                j = i;
                flag = false;
            }
            if (n >= byteNum + 2) {
                break;
            }
        }

        if (n >= byteNum + 2 && i != str.length() - 1) {
            str = str.substring(0, j);
            str += "...";
        }
        return str;
    }

    /**
     * 字符串的显示长度
     *
     * @param str
     * @return
     */
    public static int showLength(String str) {
        int length = 0;
        int i = 0;
        for (i = 0; i < str.length(); i++) {
            if ((int) (str.charAt(i)) < 128) {
                length += 1;
            } else {
                length += 2;
            }
        }
        return length;
    }

    private static DecimalFormat df = new DecimalFormat(
            "0.0");

    public static String tenTh2wan(long num) {
        if (num >= 10000 && num < 10000000) {
            String str = df.format(num / 10000.0D);

            return (!str.contains(".0") ? str : str
                    .substring(0, str.length() - 2)) + "万";
        } else if (num >= 10000000) {
            String str = df.format(num / 100000000.0D);

            return (!str.contains(".0") ? str : str
                    .substring(0, str.length() - 2)) + "亿";
        } else {
            return num + "";
        }
    }

    public static String tenTh2wan(String number) {
        if (number != null) {

            long num = Long.parseLong(number);
            return tenTh2wan(num);
        } else {
            return "0";
        }
    }

    public static String List2String(Object list) {
        if (list == null) {
            return "";
        }
        String result = list.toString().replaceAll(
                "[\\[| |\\]]", "");
        return result;
    }

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static final boolean hasChinese(String str) {
        if (str == null) {
            return false;
        }
        char[] ch = str.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (isChinese(c)) {
                return true;
            }
        }
        return false;
    }

    public static long paseLong(String num) {
        long ret = 0;
        try {
            ret = Long.valueOf(num);
        } catch (Exception e) {
        }
        return ret;
    }

    /**
     * 将time代表的时间转化为一个合适的文字描述具体的描述逻辑如下: 时间间隔 = T） T < 1分钟：刚刚 1分钟 <= T <
     * 60分钟：x分钟前 1小时 <= T < 24小时，并且日期为今天：x小时前 1小时 <= T < 12小时，并且日期为昨天：x小时前 T >=
     * 12小时，并且日期为昨天：昨天 日期为前天：前天 3天 <= T < 8天：x天前 T >= 8天，并且日期为今年：M月d日 （用系统格式） T
     * >= 8天，并且日期不是今年：yyyy年M月d日 （用系统格式）
     *
     * @param seconds 1970年1月1日零时零分零秒至time的秒数，注意不是毫秒数
     * @return 返回一个描述该事件的字符串用于评论和影视圈内容发表
     */
    @SuppressLint("SimpleDateFormat")
    public static String getTimeDisplayNameNormal(
            long ctimelong) {
        String r = "";

        Calendar currentTime = Calendar.getInstance();
        long currentTimelong = System.currentTimeMillis();

        Calendar publicCal = Calendar.getInstance();
        publicCal.setTimeInMillis(ctimelong);

        long timeDelta = currentTimelong - ctimelong;

        if (timeDelta < 60 * 1000L) {

            r = "刚刚";

        } else if (timeDelta < 60 * 60 * 1000L) {

            r = timeDelta / (60 * 1000L) + "分钟前";

        } else if (timeDelta < 24 * 60 * 60 * 1000L) {

            // if (currentTime.get(Calendar.DAY_OF_YEAR) ==
            // publicCal.get(Calendar.DAY_OF_YEAR)) {
            r = timeDelta / (60 * 60 * 1000L) + "小时前";
            // } else {
            // r = "昨天";
            // }

        } else if (timeDelta < 2 * 24 * 60 * 60 * 1000L) {

            if (currentTime.get(Calendar.DAY_OF_YEAR) == publicCal
                    .get(Calendar.DAY_OF_YEAR) + 1) {
                r = "昨天";
            } else {
                r = "前天";
            }

        } else if (timeDelta < 3 * 24 * 60 * 60 * 1000L) {

            if (currentTime.get(Calendar.DAY_OF_YEAR) == publicCal
                    .get(Calendar.DAY_OF_YEAR) + 2) {
                r = "前天";
            } else {
                r = new SimpleDateFormat("MM月dd日")
                        .format(ctimelong);
            }
        } else {
            r = new SimpleDateFormat("MM月dd日")
                    .format(ctimelong);
        }
        return r;
    }

    @SuppressLint("SimpleDateFormat")
    public static String getTimeDisplayNameCompact(
            long ctimelong) {
        String r = "";

        Calendar currentTime = Calendar.getInstance();
        long currentTimelong = System.currentTimeMillis();

        Calendar publicCal = Calendar.getInstance();
        publicCal.setTimeInMillis(ctimelong);

        long timeDelta = currentTimelong - ctimelong;

        if (timeDelta <= 0L) {

            r = "刚刚";

        } else if (timeDelta < 60 * 1000L) {

            r = timeDelta / 1000L + "秒前";

        } else if (timeDelta < 60 * 60 * 1000L) {

            r = timeDelta / (60 * 1000L) + "分钟前";

        } else if (timeDelta < 24 * 60 * 60 * 1000L) {

            // if (currentTime.get(Calendar.DAY_OF_YEAR) ==
            // publicCal.get(Calendar.DAY_OF_YEAR)) {
            r = timeDelta / (60 * 60 * 1000L) + "小时前";
            // } else {
            // r = "昨天 " + new SimpleDateFormat("HH:mm").format(ctimelong);
            // }

        } else if (timeDelta < 2 * 24 * 60 * 60 * 1000L) {

            if (currentTime.get(Calendar.DAY_OF_YEAR) == publicCal
                    .get(Calendar.DAY_OF_YEAR) + 1) {
                r = "昨天"
                        + new SimpleDateFormat("HH:mm")
                        .format(ctimelong);
            } else {
                r = "前天"
                        + new SimpleDateFormat("HH:mm")
                        .format(ctimelong);
            }

        } else if (timeDelta < 3 * 24 * 60 * 60 * 1000L) {

            if (currentTime.get(Calendar.DAY_OF_YEAR) == publicCal
                    .get(Calendar.DAY_OF_YEAR) + 2) {
                r = "前天"
                        + new SimpleDateFormat("HH:mm")
                        .format(ctimelong);
            } else {
                r = new SimpleDateFormat("MM月dd日")
                        .format(ctimelong);
            }

        } else {
            r = new SimpleDateFormat("MM月dd日")
                    .format(ctimelong);
        }
        return r;
    }

    /**
     * long类型time转成今天、昨天、前天....，没有上面的方法那么精确
     *
     * @param time
     * @return
     */
    public static String changeTimeToDescRoughly(long time) {
        String timeDesc = null;
        Calendar nowCal = Calendar.getInstance();
        nowCal.setTime(new Date());
        Calendar calendar = Calendar.getInstance();
        Date date = new Date(time * 1000);
        calendar.setTime(date);

        if (nowCal.get(Calendar.YEAR) != calendar
                .get(Calendar.YEAR)) {
            SimpleDateFormat dfs = new SimpleDateFormat(
                    "yyyy年M月d日");
            timeDesc = dfs.format(date);
            return timeDesc;
        }

        int day = nowCal.get(Calendar.DAY_OF_YEAR)
                - calendar.get(Calendar.DAY_OF_YEAR);
        if (day == 0) {
            timeDesc = "今天";
        } else if (day == 1) {
            timeDesc = "昨天 ";
        } else if (day == 2) {
            timeDesc = "前天 ";
        } else {
            timeDesc = new SimpleDateFormat("M月d日")
                    .format(date);
        }
        return timeDesc;
    }

    /**
     * 版本号对比
     *
     * @param version1
     * @param version2
     * @return 如果version1>version2返回1 如果version1《version2返回-1 如果相等返回0
     */
    public static int CompareVersion(String version1,
                                     String version2) {
        if (version1 == null) {
            version1 = "";
        }
        if (version2 == null) {
            version2 = "";
        }
        String letter_pattern = "[^0-9]";
        String[] versionLefStrs = version1.split("\\.");
        String[] versionRigStrs = version2.split("\\.");
        int splitNum = 0;
        if (versionLefStrs.length > versionRigStrs.length) {
            splitNum = versionRigStrs.length;
        } else {
            splitNum = versionLefStrs.length;
        }

        for (int i = 0; i < splitNum; i++) {
            int leftInt = Integer.valueOf("0"
                    + versionLefStrs[i].replaceAll(
                    letter_pattern, ""));
            int rightInt = Integer.valueOf("0"
                    + versionRigStrs[i].replaceAll(
                    letter_pattern, ""));
            if (leftInt > rightInt) {
                return 1;
            } else if (leftInt < rightInt) {
                return -1;
            }
        }

        if (versionLefStrs.length > versionRigStrs.length) {
            return 1;
        } else if (versionLefStrs.length < versionRigStrs.length) {
            return -1;
        } else {
            return 0;
        }
    }

    public static String getNonNullString(String str) {
        if (str == null) {
            str = "";
        }
        return str;
    }

    public static String getNonNullNumString(String str) {
        if (str == null) {
            str = "0";
        }
        return str;
    }

    public static int getNumberInt(String str,
                                   int defaultVlaue) {
        int value = defaultVlaue;
        try {
            value = Integer.valueOf(str);
        } catch (Exception e) {
        }
        return value;
    }

    public static long getNumberLong(String str,
                                     long defaultVlaue) {
        long value = defaultVlaue;
        try {
            value = Long.valueOf(str);
        } catch (Exception e) {
        }
        return value;
    }

    public static String getNonNullFloatString(String str) {
        return (str == null ? "0.0" : str);
    }

    public static String[] getNonNullStringArray(
            String[] str) {
        if (str == null) {
            str = new String[0];
        }
        return str;
    }

    /**
     * join方法在java中的实现
     *
     * @param countParams
     * @param flag
     * @return
     */
    public static String join(List<String> countParams,
                              String flag) {
        StringBuffer str_buff = new StringBuffer();

        for (int i = 0, len = countParams.size(); i < len; i++) {
            str_buff.append(String.valueOf(countParams
                    .get(i)));
            if (i < len - 1) {
                str_buff.append(flag);
            }
        }

        return str_buff.toString();
    }

    public static boolean startWithIgnoreCase(String str,
                                              String prefix) {
        if (str == null || prefix == null) {
            return false;
        }

        str = str.toLowerCase(Locale.US);
        prefix = prefix.toLowerCase(Locale.US);

        return str.startsWith(prefix);
    }

    /**
     * 验证手机号码
     *
     * @param phone
     * @return
     */
    public static boolean isPhoneNumber(String phone) {
        if (TextUtils.isEmpty(phone))
            return false;
        return phone
                .matches("^0{0,1}(13[0-9]|14[0-9]|15[0-9]|18[0-9])[0-9]{8}");
    }

    public static String formattedStringFromSimpleDate(
            long time) {
        String timeDesc = null;

        Calendar nowCal = Calendar.getInstance();
        nowCal.setTime(new Date());
        Calendar calendar = Calendar.getInstance();
        Date date = new Date(time * 1000);
        calendar.setTime(date);

        timeDesc = new SimpleDateFormat("M月d日")
                .format(date);

        return timeDesc;
    }

    public static String formattedStringFromDate(long time) {
        String timeDesc = null;

        Calendar nowCal = Calendar.getInstance();
        nowCal.setTime(new Date());
        Calendar calendar = Calendar.getInstance();
        Date date = new Date(time * 1000);
        calendar.setTime(date);

        timeDesc = new SimpleDateFormat("yyyy.M.d")
                .format(date);

        return timeDesc;
    }

    private static Formatter extracted(
            StringBuffer mStringBuffer) {
        return new Formatter(mStringBuffer,
                Locale.getDefault());
    }

    public static String Time2String(long timeMs) {
        StringBuffer mStringBuffer = new StringBuffer();

        long seconds = timeMs % 60;
        long minutes = (timeMs / 60) % 60;
        long hours = timeMs / 3600;

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

}
