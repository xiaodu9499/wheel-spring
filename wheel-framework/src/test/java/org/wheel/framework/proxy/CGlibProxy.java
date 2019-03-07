package org.wheel.framework.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * DESCRIPTION : cglib 动态代理
 *
 * @author ducf
 * @create 2019-03-06 下午 1:33
 */
public class CGlibProxy implements MethodInterceptor {

    private static CGlibProxy instance = new CGlibProxy();

    private CGlibProxy(){

    }

    public static CGlibProxy getInstance(){
        return instance;
    }

    public <T> T getProxy(Class<T> cls) {
        return (T) Enhancer.create(cls, this);
    }

    /**
     *
     * @param o 目标类对象
     * @param method 目标方法
     * @param objects 参数
     * @param methodProxy 代理方法
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        before();
        Object result = methodProxy.invokeSuper(o, objects);
        after();
        return result;
    }


    private void after() {
        System.out.println("before!");
    }

    private void before() {
        System.out.println("after!");
    }


}
