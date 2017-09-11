package ${controller.classPackage};

import ${controller.basePackage};
import ${service.classPackage}.${service.className};
import ${entity.classPackage}.${entity.className};
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("")
public class ${controller.className} extends ${controller.baseName}<${entity.className}, ${service.className}> {
}