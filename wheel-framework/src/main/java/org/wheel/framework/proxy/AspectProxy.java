package org.wheel.framework.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * DESCRIPTION : 切面代理
 *
 * @author ducf
 * @create 2019-03-06 下午 4:55
 */
public abstract class AspectProxy implements Proxy {

    private static final Logger log = LoggerFactory.getLogger(AspectProxy.class);

    @Override
    public Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result = null;

        Class<?> targetClass = proxyChain.getTargetClass();
        Method targetMethod = proxyChain.getTargetMethod();
        Object[] params = proxyChain.getMethodParams();

        begin();
        try {
            //判断
            if (intercept(targetClass, targetMethod, params)) {
                before(targetClass, targetMethod, params);
                result = proxyChain.doProxyChain();
                after(targetClass, targetMethod, params);
            } else {
                result = proxyChain.doProxyChain();
            }
        } catch (Exception e) {
            log.error("代理失败", e);
            error(targetClass, targetMethod, params);
            throw e;
        } finally {
            end();
        }

        return result;
    }

    protected void end() {

    }

    protected void error(Class<?> targetClass, Method targetMethod, Object[] params) {
    }

    protected void after(Class<?> targetClass, Method targetMethod, Object[] params) {
    }

    protected void before(Class<?> targetClass, Method targetMethod, Object[] params) {
    }


    private boolean intercept(Class<?> targetClass, Method targetMethod, Object[] params) {
        return true;
    }

    protected void begin() {
    }

}
