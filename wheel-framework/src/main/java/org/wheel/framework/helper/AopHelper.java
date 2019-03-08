package org.wheel.framework.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wheel.framework.annotation.Aspect;
import org.wheel.framework.annotation.Service;
import org.wheel.framework.proxy.AspectProxy;
import org.wheel.framework.proxy.Proxy;
import org.wheel.framework.proxy.ProxyManager;
import org.wheel.framework.proxy.TransactionProxy;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * DESCRIPTION : aop bean 管理类,创建代理bean
 *
 * @author ducf
 * @create 2019-03-06 下午 5:27
 */
public final class AopHelper {

    private static final Logger log = LoggerFactory.getLogger(AopHelper.class);

    /*初始化aop框架*/
    static {
        try {
            Map<Class<?>, Set<Class<?>>> proxyMap = createProxyMap();
            Map<Class<?>, List<Proxy>> targetMap = createTargetMap(proxyMap);
            for (Map.Entry<Class<?>, List<Proxy>> targetEntry : targetMap.entrySet()) {
                Class<?> targetClass = targetEntry.getKey();
                List<Proxy> proxyList = targetEntry.getValue();
                // 获取代理对象
                Object proxy = ProxyManager.createProxy(targetClass, proxyList);
                BeanHelper.setBean(targetClass, proxy);
            }

        } catch (Exception e) {
            log.error("aop 初始化失败", e);
        }
    }


    /**
     * 获取所有Aspect注解中设置的类 例如 @Aspect(Controller.class) 取所有Controller bean
     *
     * @param aspect
     * @return
     * @throws Exception
     */
    public static Set<Class<?>> createTargetClassSet(Aspect aspect) throws Exception {
        Set<Class<?>> targetClassSet = new HashSet<>();
        Class<? extends Annotation> annotation = aspect.value();
        if (annotation != null && !annotation.equals(Aspect.class)) {
            targetClassSet.addAll(ClassHelper.getClassSetByAnnotation(annotation));
        }
        return targetClassSet;
    }

    /**
     * 获取切面类和目标类之间的映射关系
     *
     * @return
     * @throws Exception
     */
    private static Map<Class<?>, Set<Class<?>>> createProxyMap() throws Exception {
        HashMap<Class<?>, Set<Class<?>>> proxyMap = new HashMap<>();

        addAspectProxy(proxyMap);
        addTransactionProxy(proxyMap);
        return proxyMap;
    }

    /**
     * 事物注解类
     *
     * @param proxyMap
     */
    private static void addTransactionProxy(HashMap<Class<?>, Set<Class<?>>> proxyMap) {
        Set<Class<?>> serviceClassSet = ClassHelper.getClassSetByAnnotation(Service.class);
        proxyMap.put(TransactionProxy.class, serviceClassSet);
    }

    /**
     * 切面代理类
     *
     * @param proxyMap
     * @throws Exception
     */
    private static void addAspectProxy(HashMap<Class<?>, Set<Class<?>>> proxyMap) throws Exception {
        // 切面类必须实现AspectProxy
        Set<Class<?>> proxyClassSet = ClassHelper.getClassSetBySuper(AspectProxy.class);
        for (Class<?> proxyClass : proxyClassSet) {
            // 切面类必须有@Aspect注解
            if (proxyClass.isAnnotationPresent(Aspect.class)) {
                Aspect aspect = proxyClass.getAnnotation(Aspect.class);
                // 获取所有目标类
                Set<Class<?>> targetClassSet = createTargetClassSet(aspect);
                proxyMap.put(proxyClass, targetClassSet);
            }
        }
    }

    /**
     * 获取目标类与代理对象之间的映射关系
     *
     * @param proxyMap
     * @return
     * @throws Exception
     */
    private static Map<Class<?>, List<Proxy>> createTargetMap(Map<Class<?>, Set<Class<?>>> proxyMap) throws Exception {
        Map<Class<?>, List<Proxy>> targetMap = new HashMap<>();
        for (Map.Entry<Class<?>, Set<Class<?>>> proxyEntry : proxyMap.entrySet()) {
            Class<?> proxyClass = proxyEntry.getKey();
            Set<Class<?>> targetClassSet = proxyEntry.getValue();
            for (Class<?> targetClass : targetClassSet) {
                Proxy proxy = (Proxy) proxyClass.newInstance();
                if (targetMap.containsKey(targetClass)) {
                    // 放入映射列表
                    targetMap.get(targetClass).add(proxy);
                } else {
                    List<Proxy> proxyList = new ArrayList<>();
                    proxyList.add(proxy);
                    targetMap.put(targetClass, proxyList);
                }
            }
        }
        return targetMap;
    }

}
