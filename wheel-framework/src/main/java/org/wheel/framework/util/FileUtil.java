package org.wheel.framework.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * DESCRIPTION : 文件操作工具类
 *
 * @author ducf
 * @create 2019-03-09 下午 3:08
 */
public final class FileUtil {

    private static final Logger log = LoggerFactory.getLogger(FileUtil.class);


    /**
     * 创建文件
     * @param filePath
     */
    public static File createFile(String filePath) {
        File file;
        try {
            file = new File(filePath);
            File parentDir = file.getParentFile();
            if(!parentDir.exists()){
                FileUtils.forceMkdir(parentDir);
            }
        }catch (Exception e){
            log.error("创建文件失败", e);
            throw new RuntimeException(e);
        }
        return file;
    }

    /**
     * 获取真实文件名(自动去掉文件路径)
     * @param s
     * @return
     */
    public static String getRealFileName(String s) {
        return FilenameUtils.getName(s);
    }

}
