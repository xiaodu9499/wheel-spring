package org.wheel.framework.bean;

import java.io.InputStream;

/**
 * DESCRIPTION : 封装文件上传参数
 *
 * @author ducf
 * @create 2019-03-09 上午 10:29
 */
public class FileParam {

    /*表单中文件的字段名*/
    private String fieldName;
    /*上传文件的名字*/
    private String fileName;
    /*文件大小*/
    private long fileSize;
    /*文件上传的content-Type,可用来判断文件的类型*/
    private String contentType;
    /*上传文件的字节输入流*/
    private InputStream inputStream;

    public FileParam(String fieldName, String fileName, long fileSize, String contentType, InputStream inputStream) {
        this.fieldName = fieldName;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.contentType = contentType;
        this.inputStream = inputStream;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getFileName() {
        return fileName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public String getContentType() {
        return contentType;
    }

    public InputStream getInputStream() {
        return inputStream;
    }
}
