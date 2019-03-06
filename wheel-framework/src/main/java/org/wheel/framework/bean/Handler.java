package org.wheel.framework.bean;

import java.lang.reflect.Method;

/**
 * DESCRIPTION : 封装Action信息
 *
 * @author ducf
 * @create 2019-03-05 下午 7:34
 */
public class Handler {

    /*controller类*/
    private Class<?> controllerClass;

    /*Action方法*/
    private Method actionMethod;

    public Handler(Class<?> controllerClass, Method actionMethod) {
        this.controllerClass = controllerClass;
        this.actionMethod = actionMethod;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public Method getActionMethod() {
        return actionMethod;
    }
}
