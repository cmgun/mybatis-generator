package com.cmgun.code.commons;/*
 * Copyright (c) 2017 UCSMY.
 * All rights reserved.
 * Created on 2017/9/8

 * Contributors:
 *      - initial implementation
 */

import com.cmgun.code.entity.ColumnInfo;
import com.cmgun.code.entity.MetaInfo;
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
     * 实体类元数据信息
     * @param name entity名称
     *
     * @return
     */
    private static MetaInfo getEntityMetaInfo(String name) {
        return MetaInfo.builder()
                .baseName(project.getBaseEntityName())
                .basePackage(project.getBaseEntityPackage().concat(project.getBaseEntityName()))
                .className(name.concat(StringUtil.isEmpty(project.getEntitySuffix()) ? "" : project.getEntitySuffix()))
                .classPackage(project.getPackageName().concat(".entity"))
                .fileName(name.concat(StringUtil.isEmpty(project.getEntitySuffix()) ? "" : project.getEntitySuffix()).concat(".java"))
                .build();
    }

    /**
     * mapper元数据信息
     * @param name mapper名称
     * @return
     */
    private static MetaInfo getMapperMetaInfo(String name) {
        return MetaInfo.builder()
                .fileName(name.concat(".xml"))
                .build();
    }

    /**
     * Dao元数据信息
     * @param name
     * @return
     */
    private static MetaInfo getDaoMetaInfo(String name) {
        return MetaInfo.builder()
                .basePackage(project.getBaseDaoPackage().concat(project.getBaseDaoName()))
                .baseName(project.getBaseDaoName())
                .className(name.concat("Dao"))
                .classPackage(project.getPackageName().concat(".dao"))
                .fileName(name.concat("Dao.java"))
                .build();
    }

    /**
     * Service元数据信息
     * @param name
     * @return
     */
    private static MetaInfo getServiceMetaInfo(String name) {
        return MetaInfo.builder()
                .basePackage(project.getBaseServicePackage().concat(project.getBaseServiceName()))
                .baseName(project.getBaseServiceName())
                .className(name.concat("Service"))
                .classPackage(project.getPackageName().concat(".service"))
                .fileName(name.concat("Service.java"))
                .build();
    }

    /**
     * ServiceImpl元数据信息
     * @param name
     * @return
     */
    private static MetaInfo getServiceImplMetaInfo(String name) {
        return MetaInfo.builder()
                .baseName(project.getBaseServiceImplName())
                .className(name.concat("ServiceImpl"))
                .classPackage(project.getPackageName().concat(".service.impl"))
                .fileName(name.concat("ServiceImpl.java"))
                .build();
    }

    /**
     * Controller元数据信息
     * @param name
     * @return
     */
    private static MetaInfo getControllerMetaInfo(String name) {
        return MetaInfo.builder()
                .basePackage(project.getBaseControllerPackage().concat(project.getBaseControllerName()))
                .baseName(project.getBaseControllerName())
                .className(name.concat("Controller"))
                .classPackage(project.getPackageName().concat(".web"))
                .fileName(name.concat("Controller.java"))
                .build();
    }

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
            columnInfo.setJavaName(StringUtil.toHumpRule(columnInfo.getDbName(), project.getSplit()));
        }
        dataModel.put("primaryKeys", primaryKeys);
        // columns info
        List<ColumnInfo> columns = DBUtil.getColumns(tableName);
        for (ColumnInfo columnInfo: columns) {
            columnInfo.setJavaName(StringUtil.toHumpRule(columnInfo.getDbName(), project.getSplit()));
            columnInfo.setJavaType(StringUtil.toJavaType(columnInfo.getDbType()));
        }
        dataModel.put("columns", columns);

        String humpName = StringUtil.toHumpRule(tableName, project.getSplit());
        String firstCharUpperName = StringUtil.upperFirstChar(humpName);
        // entity info
        dataModel.put("entity", getEntityMetaInfo(firstCharUpperName));
        // mapper info
        dataModel.put("mapper", getMapperMetaInfo(firstCharUpperName));
        // dao info
        dataModel.put("dao", getDaoMetaInfo(firstCharUpperName));
        // service info
        dataModel.put("service", getServiceMetaInfo(firstCharUpperName));
        dataModel.put("serviceImpl", getServiceImplMetaInfo(firstCharUpperName));
        // controller info
        dataModel.put("controller", getControllerMetaInfo(firstCharUpperName));
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