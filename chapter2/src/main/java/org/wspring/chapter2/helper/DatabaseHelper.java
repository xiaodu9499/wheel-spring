package org.wspring.chapter2.helper;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wspring.chapter2.util.CollectionUtil;
import org.wspring.chapter2.util.PropsUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * DESCRIPTION : 数据库操作助手类
 *
 * @author ducf
 * @create 2019-03-05 下午 1:51
 */
public class DatabaseHelper {
    private static final Logger log;
    private static final QueryRunner QUERY_RUNNER;
    private static final ThreadLocal<Connection> CONNECTION_HOLDER;
    private static final BasicDataSource DATA_SOURCE;


    private static final String DRIVER;
    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD;

    static {
        log = LoggerFactory.getLogger(DatabaseHelper.class);
        QUERY_RUNNER = new QueryRunner();
        CONNECTION_HOLDER = new ThreadLocal<Connection>();

        Properties props = PropsUtil.loadProps("config.properties");
        DRIVER = PropsUtil.getString(props, "jdbc.driver");
        URL = PropsUtil.getString(props, "jdbc.url");
        USERNAME = PropsUtil.getString(props, "jdbc.username");
        PASSWORD = PropsUtil.getString(props, "jdbc.password");

        DATA_SOURCE = new BasicDataSource();
        DATA_SOURCE.setDriverClassName(DRIVER);
        DATA_SOURCE.setUrl(URL);
        DATA_SOURCE.setUsername(USERNAME);
        DATA_SOURCE.setPassword(PASSWORD);

    }


    /**
     * 查询实体列表
     *
     * @param entityClass
     * @param sql
     * @param params
     * @param <T>
     * @return
     */
    public static <T> List<T> queryEntityList(Class<T> entityClass, String sql, Object... params) {
        List<T> entityList;
        try {
            Connection con = getConnection();
            entityList = QUERY_RUNNER.query(con, sql, new BeanListHandler<T>(entityClass), params);
        } catch (SQLException e) {
            log.error("查询数据列表异常", e);
            throw new RuntimeException(e);
        }
        return entityList;
    }

    /**
     * 查询单个对象
     *
     * @param entityClass
     * @param sql
     * @param params
     * @param <T>
     * @return
     */
    public static <T> T queryEntity(Class<T> entityClass, String sql, Object... params) {
        T entity;
        try {
            Connection con = getConnection();
            entity = QUERY_RUNNER.query(con, sql, new BeanHandler<T>(entityClass), params);
        } catch (SQLException e) {
            log.error("查询数据对象异常", e);
            throw new RuntimeException(e);
        }
        return entity;
    }

    /**
     * 执行联合查询
     *
     * @param sql
     * @param params
     * @return
     */
    public static List<Map<String, Object>> executeQuery(String sql, Object... params) {
        List<Map<String, Object>> result = null;
        try {
            Connection con = getConnection();
            result = QUERY_RUNNER.query(con, sql, new MapListHandler(), params);
        } catch (SQLException e) {
            log.error("执行联合查询异常", e);
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * 插入实体
     *
     * @param entityClass
     * @param fieldMap
     * @param <T>
     * @return
     */
    public static <T> boolean insertEntity(Class<T> entityClass, Map<String, Object> fieldMap) {
        if (CollectionUtil.isEmpty(fieldMap)) {
            log.error("新增失败:新增字段数据为空");
            return false;
        }
        // 拼接sql
        String sql = "insert into " + getTableName(entityClass);
        StringBuilder columns = new StringBuilder("(");
        StringBuilder values = new StringBuilder("(");
        for (String fileName : fieldMap.keySet()) {
            columns.append(fileName).append(", ");
            values.append("?, ");
        }
        columns.replace(columns.lastIndexOf(", "), columns.length(), ")");
        values.replace(values.lastIndexOf(", "), values.length(), ")");
        sql += columns + " values " + values;
        Object[] objects = fieldMap.values().toArray();
        return executeUpdate(sql, objects) == 1;
    }

    /**
     * 更新实体
     *
     * @param entityClass
     * @param fieldMap
     * @param <T>
     * @return
     */
    public static <T> boolean updateEntity(Class<T> entityClass, long id, Map<String, Object> fieldMap) {
        if (CollectionUtil.isEmpty(fieldMap)) {
            log.error("更新失败:更新字段数据为空");
            return false;
        }
        // 拼接sql
        String sql = "update " + getTableName(entityClass) + " set ";
        StringBuilder columns = new StringBuilder();
        for (String fileName : fieldMap.keySet()) {
            columns.append(fileName).append("=?, ");
        }
        sql += columns.substring(0, columns.lastIndexOf(", ")) + " where id = ? ";
        List<Object> paramList = new ArrayList<Object>();
        paramList.addAll(fieldMap.values());
        paramList.add(id);
        Object[] params = paramList.toArray();
        return executeUpdate(sql, params) == 1;
    }

    /**
     * 根据id删除数据
     *
     * @param entityClass
     * @param id
     * @param <T>
     * @return
     */
    public static <T> boolean deleteEntity(Class<T> entityClass, long id) {
        String sql = "delete from " + getTableName(entityClass) + " where id = ?";
        return executeUpdate(sql, id) == 1;
    }

    // 获取表明(类名)
    private static <T> String getTableName(Class<T> entityClass) {
        return entityClass.getSimpleName();
    }

    public static void exeuteSqlFile(String filePath) {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            String sql;
            while ((sql = reader.readLine()) != null) {
                DatabaseHelper.executeUpdate(sql);
            }
        } catch (IOException e) {
            log.error("执行sql文件异常", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 执行通用更新(update,insert,delete)
     *
     * @param sql
     * @param params
     * @return
     */
    public static int executeUpdate(String sql, Object... params) {
        int rows = 0;
        try {
            Connection con = getConnection();
            rows = QUERY_RUNNER.update(con, sql, params);
        } catch (SQLException e) {
            log.error("更新或删除异常", e);
            throw new RuntimeException(e);
        }
        return rows;
    }


    /**
     * 获取数据库连接
     *
     * @return
     */
    public static Connection getConnection() {
        Connection con = CONNECTION_HOLDER.get();
        if (con == null) {
            try {
                con = DATA_SOURCE.getConnection();
            } catch (SQLException e) {
                log.error("获取数据库连接异常", e);
                throw new RuntimeException(e);
            } finally {
                CONNECTION_HOLDER.set(con);
            }
        }
        return con;
    }


}
