package ${package}.service.impl;

import ${package}.mapper.${className}Mapper;
import ${package}.model.${className};
import ${package}.service.${className}Service;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class ${className}ServiceImpl implements ${className}Service {

    @Resource
    private ${className}Mapper ${variableName}Mapper;

    @Override
    public String save(${className} ${variableName}) {
        return ${variableName}Mapper.insert(${variableName}) > 0 ? "" : "保存失败";
    }

    @Override
    public String delete(String... ${variableName}Id) {
        return ${variableName}Mapper.deleteByIds(${className}.class, ${variableName}Id) > 0 ? "" : "删除失败";
    }

    @Override
    public String update(${className} ${variableName}) {
        return ${variableName}Mapper.update(${variableName}) > 0 ? "" : "更新失败";
    }

    @Override
    public Object findById(String ${variableName}) {
        return findBy(new HashMap<String, Object>(){
            {
                put("${variableName}", ${variableName});
            }
        });
    }

    @Override
    public Object findBy(Map<String, Object> selectModel) {
        return ${variableName}Mapper.selectBy(selectModel);
    }

    @Override
    public Object findListBy(Map<String, Object> selectModel) {
        return ${variableName}Mapper.selectListBy(selectModel);
    }

    @Override
    public int count(Map<String, Object> selectModel) {
        return ${variableName}Mapper.count(selectModel);
    }
}

