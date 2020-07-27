package ${package}.service.impl;

import ${package}.mapper.${className}Mapper;
import ${package}.model.${className};
import ${package}.service.${className}Service;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * ${className}业务逻辑实现类
 */
@Service
public class ${className}ServiceImpl implements ${className}Service {

    /**
     * ${className}数据库交互对象
     */
    @Resource
    private ${className}Mapper ${variableName}Mapper;

    /**
     * ${className}添加业务逻辑实现
     *
     * @param ${variableName} ${className}数据实体对象
     * @return ${className}添加逻辑处理结果
     */
    @Override
    public String save(${className} ${variableName}) {
        return ${variableName}Mapper.insert(${variableName}) > 0 ? "" : "添加失败";
    }

    /**
     * ${className}删除业务逻辑实现
     *
     * @param ${variableName}Ids ${className}主键标识数组
     * @return ${className}删除逻辑处理结果
     */
    @Override
    public String delete(String... ${variableName}Ids) {
        return ${variableName}Mapper.deleteByIds(${variableName}Ids) > 0 ? "" : "删除失败";
    }

    /**
     * ${className}修改业务逻辑实现
     *
     * @param ${variableName} ${className}数据实体对象
     * @return ${className}改逻辑处理结果
     */
    @Override
    public String update(${className} ${variableName}) {
        return ${variableName}Mapper.update(${variableName}) > 0 ? "" : "修改失败";
    }

    /**
     * ${className}详情查询业务逻辑实现
     *
     * @param ${variableName}Id ${className}主键标识
     * @return ${className}记录详情查询结果
     */
    @Override
    public ${className} findById(String ${variableName}Id) {
        return ${variableName}Mapper.selectBy(new ${className}(${variableName}Id));
    }

    /**
     * ${className}列表查询业务逻辑实现
     *
     * @param parameter 查询参数字段对象
     * @return ${className}记录列表查询结果
     */
    @Override
    public List<${className}> findListBy(${className} parameter) {
        return ${variableName}Mapper.selectListBy(parameter);
    }

    /**
     * ${className}列表数量统计业务逻辑实现
     *
     * @param parameter 查询参数字段对象
     * @return ${className}记录列表数量
     */
    @Override
    public int count(${className} parameter) {
        return ${variableName}Mapper.count(parameter);
    }
}

