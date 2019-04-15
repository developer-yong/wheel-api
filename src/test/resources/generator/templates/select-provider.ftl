package ${package}.mapper.provider;

import ${package}.core.IProvider;
import ${package}.model.${className};
import ${package}.parameter.${className}SelectParameter;
import org.apache.ibatis.jdbc.SQL;

public class ${className}SelectProvider implements IProvider<${className}, ${className}SelectParameter> {

    /**
     * 查询单条记录
     *
     * @param parameter 查询需要的字段
     * @return 查询Sql
     */
    public String selectOne(${className}SelectParameter parameter) {
        SQL sql = new SQL();
        sql.SELECT(createSelectSql()).FROM(tableName())
                .WHERE("${variableName}_id = " + parameter.get${className}Id());
        return sql.toString();
    }
}