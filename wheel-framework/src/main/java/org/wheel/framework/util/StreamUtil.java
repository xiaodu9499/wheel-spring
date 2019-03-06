package org.wheel.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * DESCRIPTION : 流操作工具类
 *
 * @author ducf
 * @create 2019-03-05 下午 8:47
 */
public final class StreamUtil {

    private static final Logger log = LoggerFactory.getLogger(StreamUtil.class);

    /**
     * 从输入流中获取字符串
     *
     * @param inputStream
     * @return
     */
    public static String getString(InputStream inputStream) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            log.error(" 从输入流中读取字符串失败", e);
            throw new RuntimeException(e);
        }
        return sb.toString();
    }

}
