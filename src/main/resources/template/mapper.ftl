<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper>

    <sql id="query">
        SELECT
    <#list columns as result>
        <#if result_index == 0>
            a.${result.dbName} AS ${result.javaName}
        <#else>
            , a.${result.dbName} AS ${result.javaName}
        </#if>
    </#list>
        from ${tableName}
    </sql>

    <insert id="save" parameterType="${entityPackage}.${entityName}">
        insert into ${tableName}(
    <#list columns as result>
        <#if result_index == 0>
        ${result.dbName}
        <#else>
            , ${result.dbName}
        </#if>
    </#list>
        )
        values(
    <#list columns as result>
        <#if result_index == 0>
        ${r"#"}{${result.javaName}}
        <#else>
            , ${r"#"}{${result.javaName}}
        </#if>
    </#list>
    </insert>

    <update id="update" parameterType="${entityPackage}.${entityName}">
        update ${tableName}
        set(
    <#list columns as result>
        <#if result_index == 0>
        ${result.dbName} = ${r"#"}{${result.javaName}}
        <#else>
            , ${result.dbName} = ${r"#"}{${result.javaName}}
        </#if>
    </#list>)
        where
    <#list primaryKeys as result>
        <#if result_index == 0>
        ${result.dbName} = ${r"#"}{${result.javaName}}
        <#else>
            and ${result.dbName} = ${r"#"}{${result.javaName}}
        </#if>
    </#list>
    </update>

    <delete id="delete" parameterType="map">
        delete from ${tableName}
        where
    <#list primaryKeys as result>
        <#if result_index == 0>
        ${result.dbName} = ${r"#"}{${result.javaName}}
        <#else>
            and ${result.dbName} = ${r"#"}{${result.javaName}}
        </#if>
    </#list>
    </delete>

    <select id="findById" parameterType="map" resultType="${entityPackage}.${entityName}">
        <include refid="query"/>
        where
    <#list primaryKeys as result>
        <#if result_index == 0>
        ${result.dbName} = ${r"#"}{${result.javaName}}
        <#else>
            and ${result.dbName} = ${r"#"}{${result.javaName}}
        </#if>
    </#list>
    </select>

    <select id="find" resultType="${entityPackage}.${entityName}">
        <include refid="query"/>
        <where>
            <if test="_parameter != null">
                <!-- custom sql -->
            </if>
        </where>
    </select>


    <!-- custom sql -->

</mapper>