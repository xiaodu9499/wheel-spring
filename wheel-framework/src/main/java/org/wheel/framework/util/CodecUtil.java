package org.wheel.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * DESCRIPTION : 编码与解码操作工具类
 *
 * @author ducf
 * @create 2019-03-05 下午 8:47
 */
public final class CodecUtil {

    private static final Logger log = LoggerFactory.getLogger(CodecUtil.class);

    /**
     * 将url编码
     *
     * @param source
     * @return
     */
    public static String encodeURL(String source) {
        String target;
        try {
            target = URLEncoder.encode(source, "UTF-8");

        } catch (Exception e) {
            log.error("编码失败", e);
            throw new RuntimeException(e);
        }
        return target;
    }

    /**
     * 将url解码
     *
     * @param source
     * @return
     */
    public static String decodeURL(String source) {
        String target;
        try {
            target = URLDecoder.decode(source, "UTF-8");

        } catch (Exception e) {
            log.error("解码失败", e);
            throw new RuntimeException(e);
        }
        return target;
    }
}
