package com.cmgun.code.commons;/*
 * Copyright (c) 2017 UCSMY.
 * All rights reserved.
 * Created on 2017/9/8

 * Contributors:
 *      - initial implementation
 */

import java.lang.reflect.Type;
import java.math.BigInteger;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

/**
 * 字符转换
 *
 * @author chenqilin
 * @since 2017/9/8
 */

public class StringUtil {

    public static boolean isEmpty(String str) {
        return str == null || "".equals(str.trim());
    }

    /**
     * 根据分隔符将字符串转成驼峰规则
     *
     * @param str   带转换的字符串
     * @param split 分隔符
     * @return
     */
    public static String toHumpRule(String str, String split) {
        if (StringUtil.isEmpty(str)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        boolean upperFlag = false;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (split.equals(String.valueOf(c))) {
                upperFlag = true;
            } else if (upperFlag) {
                sb.append(Character.toUpperCase(c));
                upperFlag = false;
            } else {
                sb.append(Character.toLowerCase(c));
            }
        }
        return sb.toString();
    }

    /**
     * 将第一个字符大写
     * @param str
     * @return
     */
    public static String upperFirstChar(String str) {
        if (StringUtil.isEmpty(str)) {
            return null;
        }
        return Character.toTitleCase(str.charAt(0)) + str.substring(1);
    }

    /**
     * 将数据库类型转换为Java类型
     * @param columnType
     * @return
     */
    public static String toJavaType(String columnType) {
        if (StringUtil.isEmpty(columnType)) {
            return null;
        }
        Integer dataType = Integer.parseInt(columnType);
        return ColumnType.getJavaType(dataType);
    }

    /**
     * 数据库类型-Java类型映射
     */
    enum ColumnType {
        BIGINT(Types.BIGINT, "java.math.BigInteger"),
        BIT(Types.BIT, "Byte"),
        SMALLINT(Types.SMALLINT, "Integer"),
        INTEGER(Types.INTEGER, "INTEGER"),
        FLOAT(Types.FLOAT, "Float"),
        DOUBLE(Types.DOUBLE, "Double"),
        NUMERIC(Types.NUMERIC, "Float"),
        DECIMAL(Types.DECIMAL, "java.math.BigDecimal"),
        CHAR(Types.CHAR, "String"),
        VARCHAR(Types.VARCHAR, "String"),
        LONGVARCHAR(Types.LONGVARCHAR, "String"),
        DATE(Types.DATE, "java.util.Date"),
        TIME(Types.TIME, "java.util.Date"),
        TIMESTAMP(Types.TIMESTAMP, "java.util.Date");

        private int dbType;
        private String javaType;
        private static Map<Integer, String> map = new HashMap<>();

        static {
            for (ColumnType columnType : ColumnType.values()) {
                map.put(columnType.dbType, columnType.javaType);
            }
        }

        private ColumnType(int dbType, String javaType) {
            this.dbType = dbType;
            this.javaType = javaType;
        }

        public static String getJavaType(Integer type) {
            if (map.containsKey(type)) {
                return map.get(type);
            } else {
                return "ColumnUnknown";
            }
        }
    }
}