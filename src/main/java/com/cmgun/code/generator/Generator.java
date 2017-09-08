package com.cmgun.code.generator;/*
 * Copyright (c) 2017 UCSMY.
 * All rights reserved.
 * Created on 2017/9/8

 * Contributors:
 *      - initial implementation
 */

import com.cmgun.code.commons.Constant;
import com.cmgun.code.commons.DBUtil;
import com.cmgun.code.commons.FreeMarkerUtil;
import com.cmgun.code.entity.DataSource;
import com.cmgun.code.commons.PropertiesUtil;
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
        DataSource dataSource = (DataSource) PropertiesUtil.loadProperties(Constant.JDBC_CONFIG_PATH, DataSource.class);
        Project project = (Project) PropertiesUtil.loadProperties(Constant.PROJECT_CONFIG_PATH, Project.class);
        FreeMarkerUtil.setProject(project);

        // jdbc connection
        DBUtil.connection(dataSource);

        List<String> tablesNames = DBUtil.getAllTableName();
        for (String tableName: tablesNames) {
            Map<String, Object> dataModol = FreeMarkerUtil.getDataModel(tableName);
            // generate mapper
            FreeMarkerUtil.generateTemplate(Constant.TEMPLATE_PATH, Constant.MAPPER_TEMPLATE, Constant.OUTPUT_MAPPER_PATH, (String) dataModol.get("mapperName"), dataModol);
            // generate entity
            // generate dao
            // generate service
            // generate controller
        }

        // close connection
        DBUtil.close();
    }
}