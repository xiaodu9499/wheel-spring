package org.wheel.framework.helper;

import org.wheel.framework.annotation.Controller;
import org.wheel.framework.annotation.Service;
import org.wheel.framework.util.ClassUtil;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

/**
 * DESCRIPTION : 类操作助手类
 *
 * @author ducf
 * @create 2019-03-05 下午 6:13
 */
public final class ClassHelper {

    /**
     * 定义类集合 (用于存放所加载的类)
     */
    private static final Set<Class<?>> CLASS_SET;

    static {
        String appBasePackage = ConfigHelper.getAppBasePackage();
        CLASS_SET = ClassUtil.getClassSet(appBasePackage);
    }

    /**
     * 获取应用包名下的所有类
     *
     * @return
     */
    public static Set<Class<?>> getClassSet() {
        return CLASS_SET;
    }

    /**
     * 获取应用包下的所有service类
     *
     * @return
     */
    public static Set<Class<?>> getServiceClassSet() {
        Set<Class<?>> classeSet = new HashSet<>();
        for (Class<?> cls : CLASS_SET) {
            if (cls.isAnnotationPresent(Service.class)) {
                classeSet.add(cls);
            }
        }
        return classeSet;
    }

    /**
     * 获取应用包下的所有controller类
     *
     * @return
     */
    public static Set<Class<?>> getControllerClassSet() {
        Set<Class<?>> classeSet = new HashSet<>();
        for (Class<?> cls : CLASS_SET) {
            if (cls.isAnnotationPresent(Controller.class)) {
                classeSet.add(cls);
            }
        }
        return classeSet;
    }

    /**
     * 获取应用包下的所有bean类(包括service controller 等)
     *
     * @return
     */
    public static Set<Class<?>> getBeanClassSet() {
        Set<Class<?>> beanClassSet = new HashSet<Class<?>>();
        beanClassSet.addAll(getServiceClassSet());
        beanClassSet.addAll(getControllerClassSet());
        return beanClassSet;
    }

    /**
     * 获取应用包名下某父类(或接口)的所有子类(或实现类)
     *
     * @param superClass
     * @return
     */
    public static Set<Class<?>> getClassSetBySuper(Class<?> superClass) {
        HashSet<Class<?>> classSet = new HashSet<>();
        for (Class<?> aClass : CLASS_SET) {
            if (superClass.isAssignableFrom(aClass) && !superClass.equals(aClass)) {
                classSet.add(aClass);
            }
        }
        return classSet;
    }

    /**
     * 获取应用包下带有某注解的所有类
     *
     * @param annotationClass
     * @return
     */
    public static Set<Class<?>> getClassSetByAnnotation(Class<? extends Annotation> annotationClass) {
        HashSet<Class<?>> classSet = new HashSet<>();
        for (Class<?> aClass : CLASS_SET) {
            if (aClass.isAnnotationPresent(annotationClass)) {
                classSet.add(aClass);
            }
        }
        return classSet;
    }


}
