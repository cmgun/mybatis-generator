package com.cmgun.code.entity;/*
 * Copyright (c) 2017 UCSMY.
 * All rights reserved.
 * Created on 2017/9/11

 * Contributors:
 *      - initial implementation
 */

import lombok.Builder;
import lombok.Data;

/**
 * 实体类信息
 *
 * @author chenqilin
 * @since 2017/9/11
 */
@Data
public class MetaInfo {

    private String basePackage;

    private String baseName;

    private String className;

    private String classPackage;

    private String fileName;

    @Builder
    public MetaInfo(String basePackage, String baseName, String className, String classPackage, String fileName) {
        this.basePackage = basePackage;
        this.baseName = baseName;
        this.className = className;
        this.classPackage = classPackage;
        this.fileName = fileName;
    }
}