package org.wspring.chapter2.helper;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wspring.chapter2.util.CollectionUtil;
import org.wspring.chapter2.util.PropsUtil;

import java.sql.Connection;
import java.sql.DriverManager;
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
    private static final Logger log = LoggerFactory.getLogger(DatabaseHelper.class);
    private static final QueryRunner QUERY_RUNNER = new QueryRunner();
    private static final ThreadLocal<Connection> CONNECTION_HOLDER = new ThreadLocal<Connection>();

    private static final String DRIVER;
    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD;

    static {
        Properties props = PropsUtil.loadProps("config.properties");
        DRIVER = PropsUtil.getString(props, "jdbc.driver");
        URL = PropsUtil.getString(props, "jdbc.url");
        USERNAME = PropsUtil.getString(props, "jdbc.username");
        PASSWORD = PropsUtil.getString(props, "jdbc.password");
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            log.error("加载jdbc驱动异常", e);
        }
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
        } finally {
            closeConnection();
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
        } finally {
            closeConnection();
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
        } finally {
            closeConnection();
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
        } finally {
            closeConnection();
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
                con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            } catch (SQLException e) {
                log.error("获取数据库连接异常", e);
            } finally {
                CONNECTION_HOLDER.set(con);
            }
        }
        return con;
    }


    /**
     * 关闭数据库连接
     *
     * @return
     */
    public static void closeConnection() {
        Connection con = CONNECTION_HOLDER.get();
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                log.error("关闭数据库连接异常", e);
            } finally {
                CONNECTION_HOLDER.remove();
            }
        }
    }

}
