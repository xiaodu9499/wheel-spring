package org.wspring.chapter2.util;

import org.apache.commons.lang3.StringUtils;

/**
 * DESCRIPTION : 字符串工具
 *
 * @author ducf
 * @create 2019-03-05 下午 1:29
 */
public final class StringUtil {
    /**
     * 判断字符串是否为空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        if (str != null) {
            str = str.trim();
        }
        return StringUtils.isEmpty(str);
    }

    /**
     * 判断字符串非空
     *
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }
}
