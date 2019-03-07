package org.wheel.framework.proxy;

/**
 * DESCRIPTION : jdk 动态代理
 *
 * @author ducf
 * @create 2019-03-06 上午 11:38
 */
public class main{

    public static void main(String[] args) {
        /*    Hello proxy = new DynamicProxy(new HelloImpl()).getProxy();
                proxy.sayHell("jj ");*/

        HelloImpl proxy = CGlibProxy.getInstance().getProxy(HelloImpl.class);
        proxy.sayHell("haha ");

    }

}
