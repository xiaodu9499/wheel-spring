package org.wheel.framework;

import org.wheel.framework.helper.*;
import org.wheel.framework.util.ClassUtil;

/**
 * DESCRIPTION : 加载相应的Helper类
 *
 * @author ducf
 * @create 2019-03-05 下午 7:57
 */
public final class HelperLoader {

    // aophelper 需要在 iochelper 之前加载,引文首选需要通过aopHelper 获取代理对象,然后才能通过iochelper进行依赖注入
    public static void init() {
        Class<?>[] classList = {
                ClassHelper.class,
                BeanHelper.class,
                ControllerHelper.class,
                AopHelper.class,
                IocHelper.class
        };
        for (Class<?> cls : classList) {
            ClassUtil.loadClass(cls.getName(), true);
        }
    }

}
