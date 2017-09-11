package com.cmgun.code.commons;/*
 * Copyright (c) 2017 UCSMY.
 * All rights reserved.
 * Created on 2017/9/8

 * Contributors:
 *      - initial implementation
 */

import com.cmgun.code.entity.ColumnInfo;
import com.cmgun.code.entity.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据库工具类：<br>
 * 1. 连接、关闭资源 <br>
 * 2. 查所有表 <br>
 * 3. 查指定表的列名和列属性 <br>
 *
 * @author chenqilin
 * @since 2017/9/8
 */

public class DBUtil {

    private static Connection connection = null;
    private static Statement statement = null;
    private static ResultSet resultSet = null;
    private static DatabaseMetaData databaseMetaData = null;

    /**
     * 连接数据库
     *
     * @param dataSource
     * @return
     */
    public static void connection(DataSource dataSource) {
        try {
            Class.forName(dataSource.getDriver());
            connection = DriverManager.getConnection(dataSource.getUrl(),
                    dataSource.getUsername(), dataSource.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭资源
     *
     * @param resultSet
     * @param statement
     * @param connection
     */
    private static void close(ResultSet resultSet, Statement statement, Connection connection) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭资源
     */
    public static void close() {
        close(resultSet, statement, connection);
    }


    /**
     * 查所有表名
     * @return
     */
    public static List<String> getAllTableName() {
        List<String> tableNames = new ArrayList<>();
        try {
            databaseMetaData = connection.getMetaData();
            resultSet = databaseMetaData.getTables(null, databaseMetaData.getUserName(), null, null);
            while (resultSet.next()) {
                tableNames.add(resultSet.getString("TABLE_NAME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        close(resultSet, statement, null);
        return tableNames;
    }

    /**
     * 查询指定表的所有列信息
     * @param tableName
     * @return
     */
    public static List<ColumnInfo> getColumns(String tableName) {
        List<ColumnInfo> columnInfos = new ArrayList<>();
        try {
            resultSet = databaseMetaData.getColumns(null, databaseMetaData.getUserName(), tableName, null);
            while (resultSet.next()) {
                ColumnInfo info = new ColumnInfo();
                info.setDbName(resultSet.getString("COLUMN_NAME"));
                info.setDbType(resultSet.getString("DATA_TYPE"));
                info.setTodo(resultSet.getString("REMARKS"));
                columnInfos.add(info);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        close(resultSet, statement, null);
        return columnInfos;
    }

    /**
     * 查询指定表的主键
     * @param tableName
     * @return
     */
    public static List<ColumnInfo> getPrimaryKeys(String tableName) {
        List<ColumnInfo> columnInfos = new ArrayList<>();
        try {
            resultSet = databaseMetaData.getPrimaryKeys(null, databaseMetaData.getUserName(), tableName);
            while (resultSet.next()) {
                ColumnInfo info = new ColumnInfo();
                info.setDbName(resultSet.getString("COLUMN_NAME"));
                columnInfos.add(info);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        close(resultSet, statement, null);
        return columnInfos;
    }

}