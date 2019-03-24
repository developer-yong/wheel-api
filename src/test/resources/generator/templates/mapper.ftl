package ${package}.mapper;

import ${package}.mapper.provider.${className}SelectProvider;
import ${package}.model.${className};
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

public interface ${className}Mapper extends IMapper<${className}> {

    /**
     * 查询单条记录
     *
     * @param selectModel 查询条件集合对象信息
     * @return 单条记录结果
     */
    @SelectProvider(type = ${className}SelectProvider.class, method = "selectOne")
    Map<String, Object> selectBy(Map<String, Object> selectModel);

    /**
     * 查询记录列表
     *
     * @param selectModel 查询条件集合对象信息
     * @return 记录集合
     */
    @SelectProvider(type = ${className}SelectProvider.class, method = "selectList")
    List<Map<String, Object>> selectListBy(Map<String, Object> selectModel);

    /**
     * 获取集合数量
     *
     * @param selectModel 查询条件集合对象信息
     * @return 集合数量
     */
    @SelectProvider(type = ${className}SelectProvider.class, method = "count")
    int count(Map<String, Object> selectModel);
}