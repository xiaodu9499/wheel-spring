package org.wspring.chapter3.aspect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wheel.framework.annotation.Aspect;
import org.wheel.framework.annotation.Controller;
import org.wheel.framework.proxy.AspectProxy;

import java.lang.reflect.Method;

/**
 * DESCRIPTION : 拦截所有Controller方法
 *
 * @author ducf
 * @create 2019-03-06 下午 5:15
 */
@Aspect(Controller.class)
public class ControllerAspect extends AspectProxy {

    private static final Logger log = LoggerFactory.getLogger(ControllerAspect.class);

    private static ThreadLocal<Long> threadLocal = new ThreadLocal();

    @Override
    public void before(Class<?> targetClass, Method targetMethod, Object[] params) {
        log.debug(" ---- begin ----");
        log.debug(String.format("class: %s", targetClass.getName()));
        log.debug(String.format("method: %s", targetMethod.getName()));
        threadLocal.set(System.currentTimeMillis());
    }

    @Override
    public void after(Class<?> targetClass, Method targetMethod, Object[] params) {
        log.debug(String.format("time: %dms", System.currentTimeMillis() - threadLocal.get()));
        log.debug("---- end ----");
    }
}
