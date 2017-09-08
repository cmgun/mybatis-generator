package com.cmgun.code.commons;/*
 * Copyright (c) 2017 UCSMY.
 * All rights reserved.
 * Created on 2017/9/8

 * Contributors:
 *      - initial implementation
 */

import lombok.Cleanup;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * 读取properties文件工具类
 *
 * @author chenqilin
 * @since 2017/9/8
 */

public class PropertiesUtil {

    /**
     * 读取properties并转换成指定类型
     * @param path
     * @param target
     * @return
     */
    public static Object loadProperties(String path, Class target) {
        Properties properties = new Properties();
        try {
            @Cleanup InputStream in = new BufferedInputStream(new FileInputStream(path));
            properties.load(in);
            // get target bean info
            BeanInfo beanInfo = Introspector.getBeanInfo(target);
            Object obj = target.newInstance();
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor descriptor : propertyDescriptors) {
                String propertyName = descriptor.getName();
                if (properties.containsKey(propertyName)) {
                    descriptor.getWriteMethod().invoke(obj, properties.get(propertyName));
                }
            }
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}