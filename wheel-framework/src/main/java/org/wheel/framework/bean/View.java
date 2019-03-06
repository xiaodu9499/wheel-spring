package org.wheel.framework.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * DESCRIPTION : 返回视图对象
 *
 * @author ducf
 * @create 2019-03-05 下午 8:09
 */
public class View {

    /*视图路径*/
    private String path;

    /*数据模型*/
    private Map<String, Object> model;

    public View(String path) {
        this.path = path;
        model = new HashMap<String, Object>();
    }

    public View addModel(String key, Object value) {
        model.putIfAbsent(key, value);
        return this;
    }

    public String getPath() {
        return path;
    }

    public Map<String, Object> getModel() {
        return model;
    }
}
