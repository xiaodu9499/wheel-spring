package org.wheel.framework.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * DESCRIPTION : servlet 助手类
 *
 * @author ducf
 * @create 2019-03-11 下午 7:53
 */
public class ServletHelper {
    private static final Logger log = LoggerFactory.getLogger(ServletHelper.class);

    private static final ThreadLocal<ServletHelper> SERVLET_HELPER_HOLDER = new ThreadLocal<>();

    private HttpServletRequest request;

    private HttpServletResponse response;

    public ServletHelper(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    /**
     * 初始化
     *
     * @param request
     * @param response
     */
    public static void init(HttpServletRequest request, HttpServletResponse response) {
        SERVLET_HELPER_HOLDER.set(new ServletHelper(request, response));
    }

    /**
     * 销毁
     */
    public static void destory() {
        SERVLET_HELPER_HOLDER.remove();
    }

    /**
     * 获取request
     *
     * @return
     */
    public static HttpServletRequest getRequest() {
        return SERVLET_HELPER_HOLDER.get().request;
    }

    /**
     * 获取response
     *
     * @return
     */
    public static HttpServletResponse getResponse() {
        return SERVLET_HELPER_HOLDER.get().response;
    }

    /**
     * 获取session
     *
     * @return
     */
    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    /**
     * 获取servletContext
     *
     * @return
     */
    public static ServletContext getServletContext() {
        return getRequest().getServletContext();
    }

    /**
     * 将属性放入request中
     *
     * @param key
     * @param value
     */
    public static void setRequestAttribute(String key, Object value) {
        getRequest().setAttribute(key, value);
    }

    /**
     * 从request中取值
     */
    public static <T> T getRequestAttribute(String key) {
        return (T) getRequest().getAttribute(key);
    }

    /**
     * 从request中移除属性
     *
     * @param key
     */
    public static void remoteRequestAttribute(String key) {
        getRequest().removeAttribute(key);
    }

    /**
     * 发送重定向响应
     *
     * @param location
     */
    public static void sendRedirect(String location) {
        try {
            getResponse().sendRedirect(getRequest().getContextPath() + location);
        } catch (IOException e) {
            log.error("重定向失败", e);
        }
    }

    /**
     * 将属性放入session中
     *
     * @param key
     * @param value
     */
    public static void setSessionAttribute(String key, Object value) {
        getSession().setAttribute(key, value);
    }

    /**
     * 从session中获取属性
     *
     * @param key
     */
    public static <T> T getSessionAttribute(String key) {
        return (T) getSession().getAttribute(key);
    }

    /**
     * 从session中移除属性
     *
     * @param key
     */
    public static void removeSessionAttribute(String key) {
        getRequest().getSession().removeAttribute(key);
    }

    /**
     * 使session失效
     */
    public static void invalidateSession() {
        getRequest().getSession().invalidate();

    }

}
