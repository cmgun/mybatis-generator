package com.cmgun.code.commons;/*
 * Copyright (c) 2017 UCSMY.
 * All rights reserved.
 * Created on 2017/9/8

 * Contributors:
 *      - initial implementation
 */

import com.cmgun.code.entity.ColumnInfo;
import com.cmgun.code.entity.Project;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import lombok.Cleanup;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * FreeMarker工具类
 *
 * @author chenqilin
 * @since 2017/9/8
 */

public class FreeMarkerUtil {

    private static Project project;

    /**
     * 生成模板所需的DataModel
     * @return
     */
    public static Map<String, Object> getDataModel(String tableName) {
        Map<String, Object> dataModel = new HashMap<>();
        // table name
        dataModel.put("tableName", tableName);
        // table primary key
        List<ColumnInfo> primaryKeys = DBUtil.getPrimaryKeys(tableName);
        for (ColumnInfo columnInfo: primaryKeys) {
            columnInfo.setJavaName(StringUtil.toHumpRule(columnInfo.getDbName(), project.getEntitySuffix()));
        }
        dataModel.put("primaryKeys", primaryKeys);
        // columns info
        List<ColumnInfo> columns = DBUtil.getColumns(tableName);
        for (ColumnInfo columnInfo: columns) {
            columnInfo.setJavaName(StringUtil.toHumpRule(columnInfo.getDbName(), project.getEntitySuffix()));
            columnInfo.setJavaType(StringUtil.toJavaType(columnInfo.getDbType()));
        }
        dataModel.put("columns", columns);

        String humpName = StringUtil.toHumpRule(tableName, project.getSplit());
        String firstCharUpperName = StringUtil.upperFirstChar(humpName);
        // entity info
        String entityPackage = project.getPackageName().concat(".entity");
        String entityName = firstCharUpperName.concat(StringUtil.isEmpty(project.getEntitySuffix()) ? "" : project.getEntitySuffix());
        dataModel.put("entityPackage", entityPackage);
        dataModel.put("entityName", entityName);
        // mapper info
        String mapperName = firstCharUpperName.concat(".xml");
        dataModel.put("mapperName", mapperName);
        // dao info
        String daoPackage = project.getPackageName().concat(".dao");
        String daoName = firstCharUpperName.concat("Dao");
        dataModel.put("daoPackage", daoPackage);
        dataModel.put("daoName", daoName);
        // service info
        String servicePackage = project.getPackageName().concat(".service");
        String serviceName = firstCharUpperName.concat("Service");
        dataModel.put("servicePackage", servicePackage);
        dataModel.put("serviceName", serviceName);
        // controller info
        String controllerPackage = project.getPackageName().concat(".controller");
        String controllerName = firstCharUpperName.concat("Controller");
        dataModel.put("controllerPackage", controllerPackage);
        dataModel.put("controllerName", controllerName);
        return dataModel;
    }

    public static void generateTemplate(String templatePath, String templateName, String outputPath, String fileName, Map<String, Object> root) {
        try {
            // config
            Configuration cfg = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
            cfg.setDirectoryForTemplateLoading(new File(templatePath));
            cfg.setDefaultEncoding("UTF-8");
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            cfg.setLogTemplateExceptions(false);
            // 'root' is data-model

            // get a template
            Template temp = cfg.getTemplate(templateName);

            // create file
            File output = new File(outputPath);
            if(!output.exists()){
                // directory doesn't exist
                output.mkdirs();
            }

            // merging template with data-model
            @Cleanup FileOutputStream fos = new FileOutputStream(outputPath + fileName);
            @Cleanup OutputStreamWriter out = new OutputStreamWriter(fos, "UTF-8");
            temp.process(root, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setProject(Project conf) {
        project = conf;
    }
}