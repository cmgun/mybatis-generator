package com.cmgun.code.entity;/*
 * Copyright (c) 2017 UCSMY.
 * All rights reserved.
 * Created on 2017/9/8

 * Contributors:
 *      - initial implementation
 */

import lombok.Data;

/**
 * 数据源
 *
 * @author chenqilin
 * @since 2017/9/8
 */
@Data
public class DataSource {
    private String driver;

    private String url;

    private String username;

    private String password;
}