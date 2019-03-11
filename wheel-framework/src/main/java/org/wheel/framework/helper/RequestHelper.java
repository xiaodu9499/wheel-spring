package org.wheel.framework.helper;

import org.wheel.framework.bean.FormParam;
import org.wheel.framework.bean.Param;
import org.wheel.framework.util.ArrayUtil;
import org.wheel.framework.util.CodecUtil;
import org.wheel.framework.util.StreamUtil;
import org.wheel.framework.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;

/**
 * DESCRIPTION : 请求助手类
 *
 * @author ducf
 * @create 2019-03-09 下午 5:25
 */
public class RequestHelper {

    public static Param createParam(HttpServletRequest request) throws IOException {
        List<FormParam> formParams = new ArrayList<>();
        formParams.addAll(parseParameterNames(request));
        formParams.addAll(parseInputStream(request));
        return new Param(formParams);
    }


    private static Collection<? extends FormParam> parseInputStream(HttpServletRequest request) throws IOException {
        List<FormParam> formParams = new ArrayList<>();
        String body = CodecUtil.decodeURL(StreamUtil.getString(request.getInputStream()));
        if (StringUtil.isNotEmpty(body)) {
            String[] kvs = StringUtil.splitString(body, "&");
            if (ArrayUtil.isNotEmpty(kvs)) {
                for (String kv : kvs) {
                    String[] param = StringUtil.splitString(kv, "=");
                    if (ArrayUtil.isNotEmpty(param) && param.length == 2) {
                        formParams.add(new FormParam(param[0], param[1]));
                    }
                }
            }
        }
        return formParams;
    }

    /*获取表单字段*/
    private static Collection<? extends FormParam> parseParameterNames(HttpServletRequest request) {
        List<FormParam> formParams = new ArrayList<>();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String fieldName = parameterNames.nextElement();
            String[] values = request.getParameterValues(fieldName);
            if (ArrayUtil.isNotEmpty(values)) {
                Object fieldValue;
                if (values.length == 1) {
                    fieldValue = values[0];
                } else {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < values.length; i++) {
                        sb.append(values[i]);
                        if (i != values.length - 1) {
                            sb.append(StringUtil.SEPARATOR);
                        }
                    }
                    fieldValue = sb.toString();
                }
                formParams.add(new FormParam(fieldName, fieldValue));
            }
        }
        return formParams;
    }
}
