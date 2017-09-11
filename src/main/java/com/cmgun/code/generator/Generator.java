package com.cmgun.code.generator;/*
 * Copyright (c) 2017 UCSMY.
 * All rights reserved.
 * Created on 2017/9/8

 * Contributors:
 *      - initial implementation
 */

import com.cmgun.code.commons.*;
import com.cmgun.code.entity.DataSource;
import com.cmgun.code.entity.GeneratorConfig;
import com.cmgun.code.entity.MetaInfo;
import com.cmgun.code.entity.Project;

import java.util.List;
import java.util.Map;

/**
 * 暂无描述
 *
 * @author chenqilin
 * @since 2017/9/8
 */

public class Generator {

    public static void main(String[] args) {
        // read properties
        GeneratorConfig config = (GeneratorConfig) PropertiesUtil.loadProperties(Constant.GENERATOR_CONFIG_PATH, GeneratorConfig.class);
        DataSource dataSource = (DataSource) PropertiesUtil.loadProperties(Constant.JDBC_CONFIG_PATH, DataSource.class);
        Project project = (Project) PropertiesUtil.loadProperties(Constant.PROJECT_CONFIG_PATH, Project.class);
        FreeMarkerUtil.setProject(project);
        if (config == null) {
            throw new NullPointerException("Generator config is null.");
        }

        // jdbc connection
        DBUtil.connection(dataSource);

        List<String> tablesNames = DBUtil.getAllTableName();
        for (String tableName : tablesNames) {
            Map<String, Object> dataModol = FreeMarkerUtil.getDataModel(tableName);
            // generate mapper
            if (StringUtil.isTrue(config.getMapper())) {
                FreeMarkerUtil.generateTemplate(Constant.TEMPLATE_PATH, Constant.MAPPER_TEMPLATE, Constant.OUTPUT_MAPPER_PATH, ((MetaInfo) dataModol.get("mapper")).getFileName(), dataModol);
            }
            // generate entity
            if (StringUtil.isTrue(config.getEntity())) {
                FreeMarkerUtil.generateTemplate(Constant.TEMPLATE_PATH, Constant.ENTITY_TEMPLATE, Constant.OUTPUT_ENTITY_PATH, ((MetaInfo) dataModol.get("entity")).getFileName(), dataModol);
            }
            // generate dao
            if (StringUtil.isTrue(config.getDao())) {
                FreeMarkerUtil.generateTemplate(Constant.TEMPLATE_PATH, Constant.DAO_TEMPLATE, Constant.OUTPUT_DAO_PATH, ((MetaInfo) dataModol.get("dao")).getFileName(), dataModol);
            }
            // generate service
            if (StringUtil.isTrue(config.getService())) {
                FreeMarkerUtil.generateTemplate(Constant.TEMPLATE_PATH, Constant.SERVICE_TEMPLATE, Constant.OUTPUT_SERVICE_PATH, ((MetaInfo) dataModol.get("service")).getFileName(), dataModol);
                FreeMarkerUtil.generateTemplate(Constant.TEMPLATE_PATH, Constant.SERVICEIMPL_TEMPLATE, Constant.OUTPUT_SERVICEIMPL_PATH, ((MetaInfo) dataModol.get("serviceImpl")).getFileName(), dataModol);
            }
            // generate controller
            if (StringUtil.isTrue(config.getController())) {
                FreeMarkerUtil.generateTemplate(Constant.TEMPLATE_PATH, Constant.CONTROLLER_TEMPLATE, Constant.OUTPUT_CONTROLLER_PATH, ((MetaInfo) dataModol.get("controller")).getFileName(), dataModol);
            }
        }

        // close connection
        DBUtil.close();
    }
}