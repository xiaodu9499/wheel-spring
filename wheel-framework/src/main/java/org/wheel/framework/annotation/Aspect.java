package org.wheel.framework.annotation;

import java.lang.annotation.*;

/**
 * DESCRIPTION : 切面注解
 *
 * @author ducf
 * @create 2019-03-06 下午 3:57
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {

    /**
     * 只能用于注解类 例如 @Service @Controller 等
     *
     * @return
     */
    Class<? extends Annotation> value();
}
