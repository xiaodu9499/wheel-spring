package org.wheel.framework.util;

import org.apache.commons.lang3.ArrayUtils;

/**
 * DESCRIPTION : 数组工具类
 *
 * @author ducf
 * @create 2019-03-05 下午 7:16
 */
public final class ArrayUtil {

    /**
     * 判断数组是否为空
     *
     * @param arr
     * @return
     */
    public static boolean isEmpty(Object[] arr) {
        return ArrayUtils.isEmpty(arr);
    }

    /**
     * 判断数组是否非空
     *
     * @param arr
     * @return
     */
    public static boolean isNotEmpty(Object[] arr) {
        return !isEmpty(arr);
    }
}
