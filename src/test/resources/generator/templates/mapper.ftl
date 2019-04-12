package ${package}.mapper;

import ${package}.mapper.provider.${className}SelectProvider;
import ${package}.model.${className};
import ${package}.parameter.${className}SelectParameter;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

public interface ${className}Mapper {

    /**
     * 插入单条记录
     *
     * @param ${variableName} 插入对象
     * @return 影响数据条数
     */
    @InsertProvider(type = ${className}SelectProvider.class, method = "insert")
    int insert(${className} ${variableName});

    /**
     * 插入多条记录
     *
     * @param ${variableName}s 插入对象数组
     * @return 影响数据条数
     */
    @InsertProvider(type = ${className}SelectProvider.class, method = "inserts")
    int inserts(@Param("arg0") ${className}... ${variableName}s);

    /**
     * 删除单条或多条记录
     *
     * @param ${variableName}Ids 主键数组
     * @return 影响数据条数
     */
    @DeleteProvider(type = ${className}SelectProvider.class, method = "deleteByIds")
    int deleteByIds(@Param("arg0") String... ${variableName}Ids);

    /**
     * 更新单条记录
     *
     * @param ${variableName} 更新对象
     * @return 影响数据条数
     */
    @UpdateProvider(type = ${className}SelectProvider.class, method = "update")
    int update(${className} ${variableName});

    /**
     * 查询单条记录
     *
     * @param ${variableName}SelectParameter 查询条件集合对象信息
     * @return 单条记录结果
     */
    @SelectProvider(type = ${className}SelectProvider.class, method = "selectOne")
    Map<String, Object> selectBy(${className}SelectParameter parameter);

    /**
     * 查询记录列表
     *
     * @param ${variableName}SelectParameter 查询条件集合对象信息
     * @return 记录集合
     */
    @SelectProvider(type = ${className}SelectProvider.class, method = "selectList")
    List<Map<String, Object>> selectListBy(${className}SelectParameter parameter);

    /**
     * 获取记录数量
     *
     * @param ${variableName}SelectParameter 查询条件集合对象信息
     * @return 记录数量
     */
    @SelectProvider(type = ${className}SelectProvider.class, method = "countList")
    int count(${className}SelectParameter parameter);
}