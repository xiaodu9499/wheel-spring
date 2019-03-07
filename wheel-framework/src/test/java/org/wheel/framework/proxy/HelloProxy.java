package org.wheel.framework.proxy;

/**
 * DESCRIPTION :
 *
 * @author ducf
 * @create 2019-03-06 上午 11:29
 */
public class HelloProxy implements Hello {

    private Hello hello;

    public HelloProxy() {
        hello = new HelloImpl();
    }

    @Override
    public void sayHell(String name) {
        before();
        hello.sayHell(name);
        after();
    }

    private void after() {
        System.out.println("before!");
    }

    private void before() {
        System.out.println("after!");
    }
}
