package com.gwt.idworker.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.math.BigDecimal;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: gewentao
 * @date: 2019/8/21 15:20
 */

public class Tools {
    private static final Log logger = LogFactory.getLog(Tools.class);
    public static final long HOUR_MILLIS = 3600000L;
    private static ThreadLocal<DecimalFormat> NUMBER_FORMAT_LOCAL = new ThreadLocal();
    private static final Pattern Pattern_Mobile = Pattern.compile("^1[0-9]{10}$");
    private static final Pattern Pattern_Number = Pattern.compile("\\d+");
    private static final Pattern Pattern_DateTime = Pattern.compile("^[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}$");
    private static final Pattern Pattern_Int = Pattern.compile("^[-]?[\\d]+$");
    private static ThreadLocal<SimpleDateFormat> DATE_FORMAT_LOCAL = new ThreadLocal();
    private static ThreadLocal<SimpleDateFormat> DATE_TIME_FORMAT_LOCAL = new ThreadLocal();

    private Tools() {
    }

    public static SimpleDateFormat getDateFormat() {
        SimpleDateFormat df = (SimpleDateFormat)DATE_FORMAT_LOCAL.get();
        if (df == null) {
            df = new SimpleDateFormat("yyyy-MM-dd");
            DATE_FORMAT_LOCAL.set(df);
        }

        return df;
    }

    public static SimpleDateFormat getDateTimeFormat() {
        SimpleDateFormat df = (SimpleDateFormat)DATE_TIME_FORMAT_LOCAL.get();
        if (df == null) {
            df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            DATE_TIME_FORMAT_LOCAL.set(df);
        }

        return df;
    }

    public static String getFormatDate(long time, String format) {
        SimpleDateFormat sf = new SimpleDateFormat(format);
        Date date = new Date(time);
        return sf.format(date);
    }

    public static String getDateTime() {
        return getDateTime(new Date());
    }

    public static String getDateTime(Date date) {
        return date == null ? "" : getDateTimeFormat().format(date);
    }

    public static Date getDateTime(String date) {
        if (isNull(date)) {
            return null;
        } else {
            try {
                return getDateTimeFormat().parse(date);
            } catch (ParseException var2) {
                logger.error(var2.getMessage(), var2);
                return null;
            }
        }
    }

    public static long getDateTimeMillis(String date, String format) {
        SimpleDateFormat sf = new SimpleDateFormat(format);

        try {
            return sf.parse(date).getTime();
        } catch (ParseException var4) {
            logger.error(var4.getMessage(), var4);
            return 0L;
        }
    }

    private static int getMondayPlus() {
        Calendar cd = Calendar.getInstance();
        int dayOfWeek = cd.get(7);
        return dayOfWeek == 1 ? 6 : dayOfWeek - 2;
    }

    public static double getDouble(double d, int scale) {
        BigDecimal bd = BigDecimal.valueOf(d + 1.0E-5D);
        return bd.setScale(scale, 4).doubleValue();
    }

    public static float getFloat(float f, int scale) {
        BigDecimal bd = BigDecimal.valueOf((double)f);
        return bd.setScale(scale, 4).floatValue();
    }

    public static float getFloat(Float f, int scale) {
        if (f == null) {
            return getFloat(0.0F, scale);
        } else {
            BigDecimal bd = BigDecimal.valueOf((double)f);
            return bd.setScale(scale, 4).floatValue();
        }
    }

    public static String formatString(String str, String defaultStr) {
        return isNull(str) ? defaultStr : str;
    }

    public static String formatString(String str) {
        return formatString(str, "");
    }

    public static boolean isNull(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static boolean isNotNull(String str) {
        return str != null && str.trim().length() != 0;
    }

    public static boolean isNull(List<?> list) {
        return list == null || list.isEmpty();
    }

    public static boolean isNotNull(List<?> list) {
        return list != null && !list.isEmpty();
    }

    public static boolean isNull(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    public static boolean isNotNull(Map<?, ?> map) {
        return map != null && !map.isEmpty();
    }

    public static boolean isNull(Object obj) {
        return obj == null;
    }

    public static boolean isNotNull(Object obj) {
        return obj != null;
    }

    public static boolean isNotNull(Set<?> set) {
        return set != null && !set.isEmpty();
    }

    public static boolean isNull(Set<?> set) {
        return set == null || set.isEmpty();
    }

    public static boolean isNull(Object[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isNotNull(Object[] array) {
        return array != null && array.length != 0;
    }

    public static boolean isDouble(String str) {
        return str != null && str.length() != 0 ? Pattern.matches("^[-]?[\\d]+([.]?[\\d]*)$", str) : false;
    }

    public static boolean isInt(String str) {
        if (isNull(str)) {
            return false;
        } else {
            Matcher m = Pattern_Int.matcher(str);
            return m.matches();
        }
    }

    public static boolean isMobile(String str) {
        if (str != null && str.length() == 11) {
            Matcher m = Pattern_Mobile.matcher(str);
            return m.matches();
        } else {
            return false;
        }
    }

    public static String numberFormatMoney(BigDecimal number) {
        NumberFormat nf = new DecimalFormat(",###.##");
        return nf.format(number);
    }

    public static boolean isTel(String str) {
        return str == null ? false : Pattern.matches("^(\\d{3,4}-)?\\d{7,9}$", str);
    }

    public static boolean isNumber(String str) {
        if (str == null) {
            return false;
        } else {
            Matcher m = Pattern_Number.matcher(str);
            return m.matches();
        }
    }

    public static boolean isDateTime(String str) {
        if (str == null) {
            return false;
        } else {
            Matcher m = Pattern_DateTime.matcher(str);
            return m.matches();
        }
    }

    public static String trim(String str) {
        return str == null ? "" : str.trim();
    }

    public static int parseInt(String s) {
        return parseInt(s, 0);
    }

    public static int parseInt(String s, int defaultValue) {
        if (s == null) {
            return defaultValue;
        } else {
            try {
                return Integer.parseInt(s);
            } catch (Exception var3) {
                return defaultValue;
            }
        }
    }

    public static long parseLong(String s) {
        return parseLong(s, 0L);
    }

    public static long parseLong(String s, long defaultValue) {
        if (s == null) {
            return defaultValue;
        } else {
            try {
                return Long.parseLong(s);
            } catch (Exception var4) {
                return defaultValue;
            }
        }
    }

    public static float parseFloat(String s, float defaultValue) {
        if (s == null) {
            return defaultValue;
        } else {
            try {
                return Float.parseFloat(s);
            } catch (Exception var3) {
                return defaultValue;
            }
        }
    }

    public static double parseDouble(String s) {
        return parseDouble(s, 0.0D);
    }

    public static double parseDouble(String s, double defaultValue) {
        if (s == null) {
            return defaultValue;
        } else {
            try {
                return Double.parseDouble(s);
            } catch (Exception var4) {
                return defaultValue;
            }
        }
    }

    public static boolean parseBoolean(String s, boolean defaultValue) {
        if (s == null) {
            return defaultValue;
        } else {
            try {
                return Boolean.parseBoolean(s);
            } catch (Exception var3) {
                return defaultValue;
            }
        }
    }

    public static long longValue(Long l, long defaultValue) {
        return l == null ? defaultValue : l;
    }

    public static int intValue(Integer i, int defaultValue) {
        return i == null ? defaultValue : i;
    }

    public static float floatValue(Float f, float defaultValue) {
        return f == null ? defaultValue : f;
    }

    public static double doubleValue(Double d, double defaultValue) {
        return d == null ? defaultValue : d;
    }

    public static boolean booleanValue(Boolean b, boolean defaultValue) {
        return b == null ? defaultValue : b;
    }

    public static DecimalFormat getDecimalFormat() {
        DecimalFormat df = (DecimalFormat)NUMBER_FORMAT_LOCAL.get();
        if (df == null) {
            df = new DecimalFormat("#0.00");
            NUMBER_FORMAT_LOCAL.set(df);
        }

        return df;
    }

    public static String getFloat2(float number) {
        DecimalFormat df = getDecimalFormat();
        return df.format((double)number);
    }

    public static String getDouble2(double number) {
        DecimalFormat df = getDecimalFormat();
        return df.format(number);
    }

    public static String getDouble2(double number, boolean zero) {
        String r = getDouble2(number);
        int indexOf;
        if (!zero && r != null && (indexOf = r.lastIndexOf(".")) > -1) {
            String r0 = r.substring(indexOf);
            int loc = r.length();

            for(int i = r0.length() - 1; i >= 0 && (r0.charAt(i) == '0' || r0.charAt(i) == '.'); --i) {
                --loc;
            }

            return r.substring(0, loc);
        } else {
            return r;
        }
    }

    public static String getLocalIP() {
        String ip = "";

        try {
            Enumeration e1 = NetworkInterface.getNetworkInterfaces();

            while(e1.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface)e1.nextElement();
                Enumeration e2 = ni.getInetAddresses();

                while(e2.hasMoreElements()) {
                    InetAddress ia = (InetAddress)e2.nextElement();
                    if (ia != null && ia instanceof Inet4Address) {
                        String t = ia.getHostAddress();
                        if (isNotNull(t) && !t.startsWith("127.0")) {
                            ip = t;
                            return ip;
                        }
                    }
                }
            }
        } catch (SocketException var6) {
            logger.error(var6.getMessage(), var6);
        }

        return ip;
    }
}
