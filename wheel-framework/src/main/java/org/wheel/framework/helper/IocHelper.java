package org.wheel.framework.helper;

import org.wheel.framework.annotation.Inject;
import org.wheel.framework.util.ArrayUtil;
import org.wheel.framework.util.CollectionUtil;
import org.wheel.framework.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * DESCRIPTION : 依赖注入助手类 (处理bean 实例中的 Inject注解)
 *
 * @author ducf
 * @create 2019-03-05 下午 7:11
 */
public final class IocHelper {

    static {
        // 获取所有的Bean类与Bean实例之间的映射关系
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        if (CollectionUtil.isNotEmpty(beanMap)) {
            // 遍历bean map
            for (Map.Entry<Class<?>, Object> beanEntry : beanMap.entrySet()) {
                // 从beanMap中获取Bean类与Bean实例
                Class<?> beanClass = beanEntry.getKey();
                Object beanInstance = beanEntry.getValue();
                // 获取bean类定义的所有成员变量(简称bean field)
                Field[] beanFields = beanClass.getDeclaredFields();
                if (ArrayUtil.isNotEmpty(beanFields)) {
                    for (Field beanField : beanFields) {
                        // 判断当前bean field是否带有 Inject注解
                        if (beanField.isAnnotationPresent(Inject.class)) {
                            // 在bean Map中获取bean field对应的实例
                            Class<?> beanFieldClass = beanField.getType();
                            Object beanFieldInstance = beanMap.get(beanFieldClass);
                            if (beanFieldInstance != null) {
                                // 通过反射初始化beanfield值
                                ReflectionUtil.setField(beanInstance, beanField, beanFieldInstance);
                            }
                        }
                    }
                }
            }
        }
    }

}
