package ${package}.mapper;

import ${package}.mapper.provider.${className}SqlProvider;
import ${package}.model.${className};
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ${className}Mapper {

    /**
     * 插入单条${className}记录
     *
     * @param ${variableName} ${className}数据实体对象
     * @return 影响记录条数（0-失败，1-成功）
     */
    @InsertProvider(type = ${className}SqlProvider.class, method = "insert")
    int insert(${className} ${variableName});

    /**
     * 删除单条或多条${className}记录
     *
     * @param ${variableName}Ids ${className}主键数组
     * @return 影响记录条数（0-失败，大于0-成功）
     */
    @DeleteProvider(type = ${className}SqlProvider.class, method = "deleteByIds")
    int deleteByIds(@Param("primaryKeys") String... ${variableName}Ids);

    /**
     * 更新单条${className}记录
     *
     * @param ${variableName} ${className}数据实体对象
     * @return 影响记录条数（0-失败，1-成功）
     */
    @UpdateProvider(type = ${className}SqlProvider.class, method = "update")
    int update(${className} ${variableName});

    /**
     * 查询单条${className}记录
     *
     * @param parameter 查询条件参数对象
     * @return ${className}记录结果
     */
    @SelectProvider(type = ${className}SqlProvider.class, method = "selectOne")
    ${className} selectBy(${className} parameter);

    /**
     * 查询${className}记录列表
     *
     * @param parameter 查询条件参数对象
     * @return ${className}记录列表结果
     */
    @SelectProvider(type = ${className}SqlProvider.class, method = "selectList")
    List<${className}> selectListBy(${className} parameter);

    /**
     * 统计${className}记录列表数量
     *
     * @param parameter 查询条件参数对象
     * @return ${className}记录列表数量
     */
    @SelectProvider(type = ${className}SqlProvider.class, method = "countList")
    int count(${className} parameter);
}