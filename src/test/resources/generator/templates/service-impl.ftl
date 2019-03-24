package ${package}.service.impl;

import ${package}.mapper.${className}Mapper;
import ${package}.model.${className};
import ${package}.service.${className}Service;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class ${className}ServiceImpl implements ${className}Service {

    @Resource
    private ${className}Mapper ${variableName}Mapper;

    @Override
    public boolean save(${className} ${variableName}) {
        return ${variableName}Mapper.insert(${variableName}) > 0;
    }

    @Override
    public boolean delete(String... ${variableName}Id) {
        return ${variableName}Mapper.delete(${className}.class, ${variableName}Id) > 0;
    }

    @Override
    public boolean update(${className} ${variableName}) {
        return ${variableName}Mapper.update(${variableName}) > 0;
    }

    @Override
    public ${className} findById(String ${variableName}Id) {
        return ${variableName}Mapper.selectOne(${className}.class, ${variableName}Id);
    }

    @Override
    public List<${className}> findAll() {
        return ${variableName}Mapper.selectAll(${className}.class);
    }

    @Override
    public Map<String, Object> findBy(Map<String, Object> selectModel) {
        return ${variableName}Mapper.selectBy(selectModel);
    }

    @Override
    public List<Map<String, Object>> findListBy(Map<String, Object> selectModel) {
        return ${variableName}Mapper.selectListBy(selectModel);
    }

    @Override
    public int count(Map<String, Object> selectModel) {
        return ${variableName}Mapper.count(selectModel);
    }
}

