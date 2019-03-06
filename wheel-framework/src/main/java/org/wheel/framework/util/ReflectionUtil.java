package org.wheel.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * DESCRIPTION : 反射工具类
 *
 * @author ducf
 * @create 2019-03-05 下午 6:45
 */
public final class ReflectionUtil {

    private static final Logger log = LoggerFactory.getLogger(ReflectionUtil.class);

    /**
     * 创建实例
     *
     * @param cls
     * @return
     */
    public static Object newInstance(Class<?> cls) {
        Object instance;
        try {
            instance = cls.newInstance();
        } catch (Exception e) {
            log.error("实例化对象失败", e);
            throw new RuntimeException(e);
        }
        return instance;
    }

    /**
     * 调用方法
     *
     * @param obj
     * @param method
     * @param args
     * @return
     */
    public static Object invokeMethod(Object obj, Method method, Object... args) {
        Object result;
        try {
            // 可以访问private修饰的方法
            method.setAccessible(true);
            result = method.invoke(obj, args);
        } catch (Exception e) {
            log.error("反射调用方法失败", e);
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * 设置成员变量的值
     *
     * @param obj   目标对象
     * @param field 目标对象中的字段
     * @param value 值
     */
    public static void setField(Object obj, Field field, Object value) {
        try {
            // 可以访问private修饰的字段
            field.setAccessible(true);
            field.set(obj, value);
        } catch (Exception e) {
        log.error("字段赋值失败", e);
        throw new RuntimeException(e);
        }
    }

}
