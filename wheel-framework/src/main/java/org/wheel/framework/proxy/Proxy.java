package org.wheel.framework.proxy;

/**
 * DESCRIPTION : 代理接口
 *
 * @author ducf
 * @create 2019-03-06 下午 4:00
 */
public interface Proxy {

    /**
     * 执行链式代理
     * @param proxyChain
     * @return
     * @throws Throwable
     */
    Object doProxy(ProxyChain proxyChain) throws Throwable;

}
