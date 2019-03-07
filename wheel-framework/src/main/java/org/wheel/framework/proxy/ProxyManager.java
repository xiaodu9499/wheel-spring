package org.wheel.framework.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

/**
 * DESCRIPTION : 代理管理器
 *
 * @author ducf
 * @create 2019-03-06 下午 4:36
 */
public class ProxyManager {

    /**
     * 创建代理对象
     *
     * @param targetClass
     * @param proxyList
     * @param <T>
     * @return
     */
    public static <T> T createProxy(final Class<?> targetClass, final List<Proxy> proxyList) {
        // 使用cglib创建代理对象
        return (T) Enhancer.create(targetClass, new MethodInterceptor() {
            @Override
            public Object intercept(Object targetObject, Method targetMethod, Object[] objects, MethodProxy methodProxy) throws Throwable {
                return new ProxyChain(targetClass, targetObject, targetMethod, methodProxy, objects, proxyList).doProxyChain();
            }
        });
    }

}
