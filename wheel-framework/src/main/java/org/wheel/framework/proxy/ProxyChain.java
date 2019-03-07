package org.wheel.framework.proxy;

import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * DESCRIPTION : 代理链
 *
 * @author ducf
 * @create 2019-03-06 下午 4:01
 */
public class ProxyChain {

    /*目标类*/
    private final Class<?> targetClass;
    /*目标对象*/
    private final Object targetObject;
    /*目标方法*/
    private final Method targetMethod;
    /*代理方法*/
    private final MethodProxy methodProxy;
    /*方法参数*/
    private final Object[] methodParams;
    /*代理列表*/
    private List<Proxy> proxyList = new ArrayList<>();
    /*代理索引*/
    private int proxyIndex = 0;

    public ProxyChain(Class<?> targetClass, Object targetObject, Method targetMethod, MethodProxy methodProxy, Object[] methodParams, List<Proxy> proxyList) {
        this.targetClass = targetClass;
        this.targetObject = targetObject;
        this.targetMethod = targetMethod;
        this.methodProxy = methodProxy;
        this.methodParams = methodParams;
        this.proxyList = proxyList;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    public Method getTargetMethod() {
        return targetMethod;
    }

    public Object[] getMethodParams() {
        return methodParams;
    }

    // todo: 理解代码
    public Object doProxyChain() throws Throwable {
        Object methodResult;
        if (proxyIndex < proxyList.size()) {
            methodResult = proxyList.get(proxyIndex++).doProxy(this);
        } else {
            // 使用cglib动态代理
            methodResult = methodProxy.invokeSuper(targetObject, methodParams);
        }
        return methodResult;
    }
}
