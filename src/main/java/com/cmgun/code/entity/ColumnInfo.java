package com.cmgun.code.entity;/*
 * Copyright (c) 2017 UCSMY.
 * All rights reserved.
 * Created on 2017/9/8

 * Contributors:
 *      - initial implementation
 */

import lombok.Data;

/**
 * 列信息
 *
 * @author chenqilin
 * @since 2017/9/8
 */
@Data
public class ColumnInfo {

    private String dbName;

    private String dbType;

    private String javaName;

    private String javaType;

    private String todo;
}