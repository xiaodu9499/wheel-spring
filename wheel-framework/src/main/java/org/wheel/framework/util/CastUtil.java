package org.wheel.framework.util;

/**
 * DESCRIPTION : 数据类型转换工具类
 *
 * @author ducf
 * @create 2019-03-05 下午 1:14
 */
public final class CastUtil {

    /**
     * 转换为字符串默认空字符串
     *
     * @param obj
     * @return
     */
    public static String castString(Object obj) {
        return CastUtil.castString(obj, "");
    }

    /**
     * 转换为字符串(可以输入默认值)
     *
     * @param obj
     * @param defaultValue
     * @return
     */
    public static String castString(Object obj, String defaultValue) {
        return obj == null ? defaultValue : String.valueOf(obj);
    }

    /**
     * 转为dubbo类型,默认0
     *
     * @param obj
     * @return
     */
    public static double castDouble(Object obj) {
        return castDouble(obj, 0);
    }

    /**
     * 转为dubbo类型,(可以输入默认值)
     *
     * @param obj
     * @return
     */
    public static double castDouble(Object obj, double defaultValue) {
        double res = defaultValue;
        if (obj != null) {
            String s = castString(obj);
            if (StringUtil.isNotEmpty(s)) {
                try {
                    res = Double.parseDouble(s);
                } catch (NumberFormatException e) {
                    res = defaultValue;
                }
            }
        }
        return res;
    }


    /**
     * 转为long类型,默认0
     *
     * @param obj
     * @return
     */
    public static long castLong(Object obj) {
        return castLong(obj, 0);
    }

    /**
     * 转为long类型,(可以输入默认值)
     *
     * @param obj
     * @return
     */
    public static long castLong(Object obj, long defaultValue) {
        long res = defaultValue;
        if (obj != null) {
            String s = castString(obj);
            if (StringUtil.isNotEmpty(s)) {
                try {
                    res = Long.parseLong(s);
                } catch (NumberFormatException e) {
                    res = defaultValue;
                }
            }
        }
        return res;
    }


    /**
     * 转为int类型,默认0
     *
     * @param obj
     * @return
     */
    public static int castInt(Object obj) {
        return castInt(obj, 0);
    }

    /**
     * 转为int类型,(可以输入默认值)
     *
     * @param obj
     * @return
     */
    public static int castInt(Object obj, int defaultValue) {
        int res = defaultValue;
        if (obj != null) {
            String s = castString(obj);
            if (StringUtil.isNotEmpty(s)) {
                try {
                    res = Integer.parseInt(s);
                } catch (NumberFormatException e) {
                    res = defaultValue;
                }
            }
        }
        return res;
    }


    /**
     * 转为bool类型,默认false
     *
     * @param obj
     * @return
     */
    public static boolean castBoolean(Object obj) {
        return castBoolean(obj, false);
    }

    /**
     * 转为long类型,(可以输入默认值)
     *
     * @param obj
     * @return
     */
    public static boolean castBoolean(Object obj, boolean defaultValue) {
        boolean res = defaultValue;
        if (obj != null) {
            res = Boolean.parseBoolean(castString(obj));
        }
        return res;
    }


}
