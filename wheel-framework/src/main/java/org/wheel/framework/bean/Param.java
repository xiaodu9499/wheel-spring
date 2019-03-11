package org.wheel.framework.bean;

import org.wheel.framework.util.CastUtil;
import org.wheel.framework.util.CollectionUtil;
import org.wheel.framework.util.StringUtil;
import sun.rmi.runtime.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DESCRIPTION : 请求参数对象
 *
 * @author ducf
 * @create 2019-03-05 下午 8:05
 */
public class Param {

    private List<FormParam> formParamList;

    private List<FileParam> fileParamList;

    public Param(List<FormParam> formParamList) {
        this.formParamList = formParamList;
    }

    public Param(List<FormParam> formParamList, List<FileParam> fileParamList) {
        this.formParamList = formParamList;
        this.fileParamList = fileParamList;
    }

    /**
     * 获取请求参数映射
     *
     * @return
     */
    public Map<String, Object> getFieldMap() {
        Map<String, Object> fieldMap = new HashMap<>();
        if (CollectionUtil.isNotEmpty(formParamList)) {
            for (FormParam formParam : formParamList) {
                String fieldName = formParam.getFieldName();
                Object fieldValue = formParam.getFieldValue();
                if (fieldMap.containsKey(fieldName)) {
                    fieldValue = fieldMap.get(fieldName) + StringUtil.SEPARATOR + fieldValue;
                }
                fieldMap.put(fieldName, fieldValue);
            }
        }
        return fieldMap;
    }

    /**
     * 获取上传文件映射
     *
     * @return
     */
    public Map<String, List<FileParam>> getFileMap() {
        Map<String, List<FileParam>> fileMap = new HashMap<>();
        if (CollectionUtil.isNotEmpty(fileParamList)) {
            for (FileParam fileParam : fileParamList) {
                String fieldName = fileParam.getFieldName();
                List<FileParam> fileParamList;
                if (fileMap.containsKey(fieldName)) {
                    fileParamList = fileMap.get(fieldName);
                } else {
                    fileParamList = new ArrayList<>();
                }
                fileParamList.add(fileParam);
                fileMap.put(fieldName, fileParamList);
            }
        }
        return fileMap;
    }

    /**
     * 获取所有上传文件
     *
     * @return
     */
    public List<FileParam> getFileList(String fieldName) {
        return getFileMap().get(fieldName);
    }

    /**
     * 获取唯一上传文件
     *
     * @param filedName
     * @return
     */
    public FileParam getFile(String filedName) {
        List<FileParam> fileParamList = getFileList(filedName);
        if (CollectionUtil.isNotEmpty(fileParamList) && fileParamList.size() == 1) {
            return fileParamList.get(0);
        }
        return null;
    }

    /**
     * 验证参数非空
     *
     * @return
     */
    public boolean isEmpty() {
        return CollectionUtil.isEmpty(formParamList) && CollectionUtil.isEmpty(fileParamList);
    }

    /**
     * 根据参数名获取String类型餐数据
     * @param name
     * @return
     */
    public String getString(String name){
        return CastUtil.castString(getFileMap().get(name));
    }

    /**
     * 根据参数名获取double类型餐数据
     * @param name
     * @return
     */
    public double getDouble(String name){
        return CastUtil.castDouble(getFileMap().get(name));
    }

    /**
     * 根据参数名获取long类型餐数据
     * @param name
     * @return
     */
    public long getLong(String name){
        return CastUtil.castLong(getFileMap().get(name));
    }

    /**
     * 根据参数名获取int类型餐数据
     * @param name
     * @return
     */
    public int getInt(String name){
        return CastUtil.castInt(getFileMap().get(name));
    }

    /**
     * 根据参数名获取boolean类型餐数据
     * @param name
     * @return
     */
    public boolean getBoolean(String name){
        return CastUtil.castBoolean(getFileMap().get(name));
    }

}
