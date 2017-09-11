package ${entity.classPackage};

import ${entity.basePackage};
import lombok.Data;

@Data
public class ${entity.className} extends ${entity.baseName} {
<#list columns as result>
    /**
     * ${result.todo}
     */
    private ${result.javaType} ${result.javaName};

</#list>
}