package org.wheel.framework.bean;

/**
 * DESCRIPTION : 封装表单参数
 *
 * @author ducf
 * @create 2019-03-09 上午 10:33
 */
public class FormParam {

    private String fieldName;
    private Object fieldValue;

    public FormParam(String fieldName, Object fieldValue) {
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getFieldValue() {
        return fieldValue;
    }
}
