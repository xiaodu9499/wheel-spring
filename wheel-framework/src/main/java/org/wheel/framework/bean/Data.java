package org.wheel.framework.bean;

/**
 * DESCRIPTION :返回数据对象
 *
 * @author ducf
 * @create 2019-03-05 下午 8:14
 */
public class Data {

    /*数据模型*/
    private Object model;

    public Data(Object model) {
        this.model = model;
    }

    public Object getModel() {
        return model;
    }
}
