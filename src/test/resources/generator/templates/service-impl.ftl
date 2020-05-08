package ${package}.service.impl;

import ${package}.mapper.${className}Mapper;
import ${package}.model.${className};
import ${package}.parameter.${className}SelectParameter;
import ${package}.service.${className}Service;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ${className}ServiceImpl implements ${className}Service {

    @Resource
    private ${className}Mapper ${variableName}Mapper;

    @Override
    public String save(${className}... ${variableName}s) {
        return ${variableName}Mapper.inserts(${variableName}s) > 0 ? "" : "保存失败";
    }

    @Override
    public String delete(String... ${variableName}Id) {
        return ${variableName}Mapper.deleteByIds(${variableName}Id) > 0 ? "" : "删除失败";
    }

    @Override
    public String update(${className} ${variableName}) {
        return ${variableName}Mapper.update(${variableName}) > 0 ? "" : "更新失败";
    }

    @Override
    public Object findById(String ${variableName}Id) {
        return ${variableName}Mapper.selectBy(new ${className}SelectParameter(${variableName}Id));
    }

    @Override
    public Object findBy(${className}SelectParameter ${variableName}SelectParameter) {
        return ${variableName}Mapper.selectBy(${variableName}SelectParameter);
    }

    @Override
    public Object findListBy(${className}SelectParameter ${variableName}SelectParameter) {
        return ${variableName}Mapper.selectListBy(${variableName}SelectParameter);
    }

    @Override
    public int count(${className}SelectParameter ${variableName}SelectParameter) {
        return ${variableName}Mapper.count(${variableName}SelectParameter);
    }
}

