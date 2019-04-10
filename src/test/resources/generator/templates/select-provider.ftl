package ${package}.mapper.provider;

import ${package}.core.IProvider;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class ${className}SelectProvider implements IProvider {

    private String tableName = "${tableName}";

    /**
     * 表名
     *
     * @return 表名
     */
    @Override
    public String tableName() {
        return tableName;
    }

    /**
     * 查询单条记录
     *
     * @param selectModel 查询需要的字段
     * @return 查询Sql
     */
    public String selectOne(Map<String, Object> selectModel) {
        SQL sql = new SQL();
        sql.SELECT("*").FROM(tableName).WHERE("1=1");
        return sql.toString();
    }
}