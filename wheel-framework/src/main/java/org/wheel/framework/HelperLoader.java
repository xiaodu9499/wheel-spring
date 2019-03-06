package org.wheel.framework;

import org.wheel.framework.helper.BeanHelper;
import org.wheel.framework.helper.ClassHelper;
import org.wheel.framework.helper.ControllerHelper;
import org.wheel.framework.helper.IocHelper;
import org.wheel.framework.util.ClassUtil;

/**
 * DESCRIPTION : 加载相应的Helper类
 *
 * @author ducf
 * @create 2019-03-05 下午 7:57
 */
public final class HelperLoader {

    public static void init() {
        Class<?>[] classList = {
                ClassHelper.class,
                BeanHelper.class,
                IocHelper.class,
                ControllerHelper.class
        };
        for (Class<?> cls : classList) {
            ClassUtil.loadClass(cls.getName(), true);
        }
    }

}
