package org.wheel.framework.bean;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * DESCRIPTION : 封装请求信息
 *
 * @author ducf
 * @create 2019-03-05 下午 7:30
 */
public class Request {

    /*请求方法*/
    private String requestMethod;

    /*请求路径*/
    private String requestPath;

    public Request(String requestMethod, String requestPath) {
        this.requestMethod = requestMethod;
        this.requestPath = requestPath;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public String getRequestPath() {
        return requestPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Request request = (Request) o;

        return new EqualsBuilder()
                .append(requestMethod, request.requestMethod)
                .append(requestPath, request.requestPath)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(requestMethod)
                .append(requestPath)
                .toHashCode();
    }
}
