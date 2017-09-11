package ${dao.classPackage};

import ${dao.basePackage};
import ${entity.classPackage}.${entity.className};
import org.springframework.stereotype.Repository;

@Repository
public interface ${dao.className} extends ${dao.baseName}<${entity.className}> {

}