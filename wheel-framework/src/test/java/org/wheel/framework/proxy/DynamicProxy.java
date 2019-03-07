package org.wheel.framework.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * DESCRIPTION :
 *
 * @author ducf
 * @create 2019-03-06 上午 11:45
 */
public class DynamicProxy implements InvocationHandler {

    private Object target;

    public DynamicProxy(Object object) {
        this.target = object;
    }

    public <T> T getProxy(){
        return (T) Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object invoke = method.invoke(target, args);
        after();
        return invoke;
    }

    private void after() {
        System.out.println("before!");
    }

    private void before() {
        System.out.println("after!");
    }
}
