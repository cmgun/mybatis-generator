package ${serviceImpl.classPackage};

import org.springframework.stereotype.Service;
import ${dao.classPackage}.${dao.className};
import ${entity.classPackage}.${entity.className};
import ${service.classPackage}.${service.className};

@Service
public class ManageRoleServiceImpl extends ${serviceImpl.baseName}<${entity.className}, ${dao.className}> implements ${service.className} {

}