package com.cmgun.code.entity;/*
 * Copyright (c) 2017 UCSMY.
 * All rights reserved.
 * Created on 2017/9/11

 * Contributors:
 *      - initial implementation
 */

import lombok.Data;

/**
 * 生成文件配置
 *
 * @author chenqilin
 * @since 2017/9/11
 */
@Data
public class GeneratorConfig {

    private String dao;

    private String entity;

    private String mapper;

    private String service;

    private String controller;
}