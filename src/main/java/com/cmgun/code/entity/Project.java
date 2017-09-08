package com.cmgun.code.entity;/*
 * Copyright (c) 2017 UCSMY.
 * All rights reserved.
 * Created on 2017/9/8

 * Contributors:
 *      - initial implementation
 */

import lombok.Data;

/**
 * 目标输出项目信息
 *
 * @author chenqilin
 * @since 2017/9/8
 */
@Data
public class Project {

    private String packageName;

    private String entitySuffix;

    private String split;
}