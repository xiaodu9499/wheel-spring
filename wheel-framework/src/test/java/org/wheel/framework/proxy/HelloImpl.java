package org.wheel.framework.proxy;

/**
 * DESCRIPTION :
 *
 * @author ducf
 * @create 2019-03-06 上午 11:28
 */
public class HelloImpl implements Hello {
    @Override
    public void sayHell(String name) {
        System.out.println(name + " say Hello!");
    }
}
