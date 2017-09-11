package com.cmgun.code.commons;/*
 * Copyright (c) 2017 UCSMY.
 * All rights reserved.
 * Created on 2017/9/8

 * Contributors:
 *      - initial implementation
 */

/**
 * 一般常量
 *
 * @author chenqilin
 * @since 2017/9/8
 */

public class Constant {

    private static final String CONFIG_PATH = "src/main/resources/config/";
    public static final String JDBC_CONFIG_PATH = CONFIG_PATH + "jdbc.properties";
    public static final String PROJECT_CONFIG_PATH = CONFIG_PATH + "project.properties";

    public static final String TEMPLATE_PATH = "src/main/resources/template/";
    public static final String MAPPER_TEMPLATE = "mapper.ftl";
    public static final String ENTITY_TEMPLATE = "entity.ftl";
    public static final String DAO_TEMPLATE = "dao.ftl";
    public static final String SERVICE_TEMPLATE = "service.ftl";
    public static final String SERVICEIMPL_TEMPLATE = "serviceImpl.ftl";
    public static final String CONTROLLER_TEMPLATE = "controller.ftl";

    private static final String OUTPUT_PATH = "src/main/resources/output/";
    public static final String OUTPUT_MAPPER_PATH = OUTPUT_PATH + "mapper/";
    public static final String OUTPUT_ENTITY_PATH = OUTPUT_PATH + "entity/";
    public static final String OUTPUT_DAO_PATH = OUTPUT_PATH + "dao/";
    public static final String OUTPUT_SERVICE_PATH = OUTPUT_PATH + "service/";
    public static final String OUTPUT_SERVICEIMPL_PATH = OUTPUT_PATH + "service/impl/";
    public static final String OUTPUT_CONTROLLER_PATH = OUTPUT_PATH + "controller/";
}