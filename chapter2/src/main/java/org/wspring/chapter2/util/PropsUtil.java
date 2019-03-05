package org.wspring.chapter2.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * DESCRIPTION : 配置文件属性读取工具类
 *
 * @author ducf
 * @create 2019-03-05 下午 12:56
 */
public final class PropsUtil {
    private static final Logger log = LoggerFactory.getLogger(PropsUtil.class);

    /**
     * 加载项目中的配置文件
     *
     * @param fileName
     * @return
     */
    public static Properties loadProps(String fileName) {
        Properties props = null;
        InputStream is = null;
        try {
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
            if (is == null) {
                throw new FileNotFoundException(fileName + "文件未找到");
            }
            props = new Properties();
            props.load(is);
        } catch (IOException e) {
            log.error("加载配置文件异常", e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    log.error("输入流关闭异常", e);
                }
            }
        }
        return props;
    }

    /**
     * 获取配置文件的属性值(默认空字符串)
     *
     * @param props
     * @param key
     * @return
     */
    public static String getString(Properties props, String key) {
        return getString(props, key, "");
    }

    /**
     * 获取配置文件的属性值(默认字符串类型,可指定默认值)
     *
     * @return
     */
    public static String getString(Properties props, String key, String defaultValue) {
        if (props.containsKey(key)) {
            return props.getProperty(key);
        }
        return defaultValue;
    }

    /**
     * 获取配置文件的属性值(默认0)
     *
     * @param props
     * @param key
     * @return
     */
    public static int getInt(Properties props, String key) {
        return getInt(props, key, 0);
    }

    /**
     * 获取配置文件的属性值(默认字符串类型,可指定默认值)
     *
     * @return
     */
    public static int getInt(Properties props, String key, int defaultValue) {
        if (props.containsKey(key)) {
            return CastUtil.castInt(props.getProperty(key));
        }
        return defaultValue;
    }

    /**
     * 获取配置文件的属性值(默认false)
     *
     * @param props
     * @param key
     * @return
     */
    public static boolean getBoolean(Properties props, String key) {
        return getBoolean(props, key, false);
    }

    /**
     * 获取配置文件的属性值(默认boolean类型,可指定默认值)
     *
     * @return
     */
    public static boolean getBoolean(Properties props, String key, boolean defaultValue) {
        if (props.containsKey(key)) {
            return CastUtil.castBoolean(props.getProperty(key));
        }
        return defaultValue;
    }

}
