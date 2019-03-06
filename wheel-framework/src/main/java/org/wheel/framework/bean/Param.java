package org.wheel.framework.bean;

import org.wheel.framework.util.CastUtil;

import java.util.Map;

/**
 * DESCRIPTION : 请求参数对象
 *
 * @author ducf
 * @create 2019-03-05 下午 8:05
 */
public class Param {

    private Map<String, Object> paramMap;

    public Param(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    /**
     * 根据请求参数获取long类型数值
     *
     * @param name
     * @return
     */
    public long getLong(String name) {
        return CastUtil.castLong(paramMap.get(name));
    }

    /**
     * 获取所有字段信息
     *
     * @return
     */
    public Map<String, Object> getMap() {
        return paramMap;
    }

}
